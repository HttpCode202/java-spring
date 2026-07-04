package org.github.httpcode202.spring.infrastructure.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("httpcode202")
public class RootConfigurationProperties {
    private DataSourceConfigurationProperties datasource1;
    private DataSourceConfigurationProperties datasource2;

    public DataSourceConfigurationProperties datasource1() {
        return datasource1;
    }

    public void setDatasource1(DataSourceConfigurationProperties datasource1) {
        this.datasource1 = datasource1;
    }

    public DataSourceConfigurationProperties datasource2() {
        return datasource2;
    }

    public void setDatasource2(DataSourceConfigurationProperties datasource2) {
        this.datasource2 = datasource2;
    }
}