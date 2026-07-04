package org.github.httpcode202.spring.infrastructure.configuration.property;

import java.util.Map;

public class DataSourceConfigurationProperties {
    private boolean generateDdl = false;
    private boolean showSql = true;
    private Map<String, String> jpaProperties;
    private String databasePlatform;
    private String driverClassName;
    private String jdbcUrl;
    private String packagesToScan;
    private String password;
    private String username;

    public boolean generateDdl() {
        return generateDdl;
    }

    public void setGenerateDdl(boolean generateDdl) {
        this.generateDdl = generateDdl;
    }

    public boolean showSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public Map<String, String> jpaProperties() {
        return jpaProperties;
    }

    public void setJpaProperties(Map<String, String> jpaProperties) {
        this.jpaProperties = jpaProperties;
    }

    public String databasePlatform() {
        return databasePlatform;
    }

    public void setDatabasePlatform(String databasePlatform) {
        this.databasePlatform = databasePlatform;
    }

    public String driverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String jdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String packagesToScan() {
        return packagesToScan;
    }

    public void setPackagesToScan(String packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    public String password() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String username() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}