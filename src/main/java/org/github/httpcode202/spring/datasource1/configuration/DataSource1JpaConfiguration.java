package org.github.httpcode202.spring.datasource1.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.github.httpcode202.spring.infrastructure.configuration.property.DataSourceConfigurationProperties;
import org.github.httpcode202.spring.infrastructure.configuration.property.RootConfigurationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

@EnableJpaRepositories(
        basePackages = "org.github.httpcode202.spring.datasource1.repository",
        entityManagerFactoryRef = "ds1EntityManagerFactory",
        transactionManagerRef = "ds1TransactionManager"
)
@Configuration
public class DataSource1JpaConfiguration {
    private final DataSourceConfigurationProperties dataSourceConfigurationProperties;

    public DataSource1JpaConfiguration(RootConfigurationProperties rootConfigurationProperties) {
        this.dataSourceConfigurationProperties = rootConfigurationProperties.datasource1();
    }

    @Bean("ds1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform(dataSourceConfigurationProperties.databasePlatform());
        vendorAdapter.setGenerateDdl(dataSourceConfigurationProperties.generateDdl());
        vendorAdapter.setShowSql(dataSourceConfigurationProperties.showSql());

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setDataSource(getDataSource());
        factory.setPackagesToScan(dataSourceConfigurationProperties.packagesToScan());
        factory.setJpaPropertyMap(dataSourceConfigurationProperties.jpaProperties());

        return factory;
    }

    @Bean("ds1TransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("ds1EntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryBean.getObject()));
    }

    private DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(dataSourceConfigurationProperties.driverClassName());
        dataSource.setJdbcUrl(dataSourceConfigurationProperties.jdbcUrl());
        dataSource.setUsername(dataSourceConfigurationProperties.username());
        dataSource.setPassword(dataSourceConfigurationProperties.password());
        return dataSource;
    }
}