package com.example.letprakt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/phonetest")
                .username("root")
                .password("75MinskRB")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}
