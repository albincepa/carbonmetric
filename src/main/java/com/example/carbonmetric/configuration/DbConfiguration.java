package com.example.carbonmetric.configuration;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfiguration {

    private final DbProperties dbProperties;


    public DbConfiguration(DbProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Bean
    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(dbProperties.getUrl(), dbProperties.getToken().toCharArray(), dbProperties.getOrg(), dbProperties.getBucket());
    }

    @Bean
    public WriteApi dbWriteApi(InfluxDBClient dbClient) {
        return dbClient.makeWriteApi();
    }


}
