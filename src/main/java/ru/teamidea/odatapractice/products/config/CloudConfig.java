package ru.teamidea.odatapractice.products.config;



import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import ru.teamidea.odatapractice.products.models.Product;
import ru.teamidea.odatapractice.products.util.EntityManagerFactoryProvider;

@Configuration
@Profile("cloud")
@EnableJpaRepositories
@ComponentScan(basePackages = "ru.teamidea.odatapractice.products")
public class CloudConfig extends AbstractCloudConfig {

//    private static final String HANA_SVC = "hana-schema-svc";

    private static final Logger LOG = LoggerFactory.getLogger(CloudConfig.class);

    /**
     * Create dataSource bean from SAP CF
     * 
     * @return dataSource dataSoruce created from HANA Service.
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        DataSource dataSource = null;
        try {
            List<String> dataSourceNames = Arrays.asList("BasicDbcpPooledDataSourceCreator",
                    "TomcatJdbcPooledDataSourceCreator", "HikariCpPooledDataSourceCreator",
                    "TomcatDbcpPooledDataSourceCreator");
            DataSourceConfig dbConfig = new DataSourceConfig(dataSourceNames);
            
        dataSource = connectionFactory().dataSource(dbConfig);
        } catch (CloudException ex) {
            LOG.error(" ", ex);
        }
        return dataSource;
    }

    /**
     * Create Eclipselink EMF from the dataSource bean. JPAvendor and datasource
     * will be set here. rest will be taken from persistence.xml
     * 
     * @return EntityManagerFactory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        return EntityManagerFactoryProvider.get(dataSource, Product.class.getPackage().getName());
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
 

}
