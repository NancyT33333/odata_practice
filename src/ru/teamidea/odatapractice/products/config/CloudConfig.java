package ru.teamidea.odatapractice.products.config;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.RuntimeException;

import javax.persistence.EntityManagerFactory;
import javax.servlet.DispatcherType;
import javax.sql.DataSource;

import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.cloud.util.UriInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

import com.sap.hana.cloud.hcp.service.common.HANAServiceInfo;
import com.sap.hcp.cf.logging.servlet.filter.RequestLoggingFilter;
import com.zaxxer.hikari.HikariDataSource;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.jdbc.CfJdbcEnv;

@Configuration
@EnableJpaRepositories
@Profile("cf")
@ComponentScan(basePackages = "ru.teamidea.odatapractice.products")
public class CloudConfig extends AbstractCloudConfig {

    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestLoggingFilter());
        registration.addUrlPatterns("/*");
        registration.setName("logFilter");
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return registration;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        // make environment variables available for Spring's @Value annotation
        return new PropertySourcesPlaceholderConfigurer();
    }

    // private static final String HANA_SVC = "hana-schema-svc";
    //
    private static final Logger LOG = LoggerFactory.getLogger(CloudConfig.class);

    @Bean
    @Primary
    @Profile("cf")
    public DataSourceProperties dataSourceProperties() {
        CfJdbcEnv cfJdbcEnv = new CfJdbcEnv();
        DataSourceProperties properties = new DataSourceProperties();
        CfCredentials hanaCredentials = cfJdbcEnv.findCredentialsByName("hana-schema-svc");

        if (hanaCredentials != null) {

            String uri = hanaCredentials.getUri("hana-schema-svc");
            properties.setUrl(uri);
            properties.setUsername(hanaCredentials.getUsername());
            properties.setPassword(hanaCredentials.getPassword());
        }

        return properties;
    }

    @Bean
    @Profile("cf")
    public DataSource dataSource() {
        String user = dataSourceProperties().getUsername();
        String password = dataSourceProperties().getPassword();
        String url = dataSourceProperties().getUrl();
        // HANAServiceInfo serviceInfo = (HANAServiceInfo) cloud().getServiceInfo("hanaservice2");
        //
        // String host = serviceInfo.getHost();
        // int port = serviceInfo.getPort();
        //
        // String user = serviceInfo.getUserName();
        // String password = serviceInfo.getPassword();
        // String schema = serviceInfo.getUserName(); // The schemaname matches the username
        //
        // String url = new UriInfo("jdbc:sap", host, port, null, null, null,
        // "currentschema=" + schema + "&encrypt=true&validateCertificate=true").toString();

        return DataSourceBuilder.create().type(HikariDataSource.class)
                .driverClassName(com.sap.db.jdbc.Driver.class.getName()).url(url).username(user).password(password)
                .build();

    }

    /**
     * Create Eclipselink EMF from the dataSource bean. JPAvendor and datasource will be set here. rest will be taken
     * from persistence.xml
     * 
     * @return EntityManagerFactory
     */
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean springEMF = new LocalContainerEntityManagerFactoryBean();
        springEMF.setPersistenceUnitName("ODataSpring");
        springEMF.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());
        springEMF.setDataSource(dataSource());
        springEMF.afterPropertiesSet();
        return springEMF.getObject();

    }

   
    /**
     * Registers OData servlet bean with Spring Application context to handle ODataRequests.
     * 
     * @return
     */
    @Bean
    public ServletRegistrationBean odataServlet() {
        ServletRegistrationBean odataServRegstration = null;
            try {
        odataServRegstration = new ServletRegistrationBean(new CXFNonSpringJaxrsServlet(),
                "/odata.svc/*");
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("javax.ws.rs.Application", "org.apache.olingo.odata2.core.rest.app.ODataApplication");
        initParameters.put("org.apache.olingo.odata2.service.factory",
                "ru.teamidea.odatapractice.products.context.JPAServiceFactory");
        odataServRegstration.setInitParameters(initParameters);

       
        } catch ( Exception e ) {
            LOG.error(e.getMessage());
        }
            return odataServRegstration; 
    }

}
