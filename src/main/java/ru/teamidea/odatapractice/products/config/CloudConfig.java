package ru.teamidea.odatapractice.products.config;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.servlet.DispatcherType;
import javax.sql.DataSource;

import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

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
