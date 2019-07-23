package com.telran.springdiiocexceptionhandling.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DBConfig {
    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.username}")
    private String dbUser;
    @Value("${db.password}")
    private String dbPassword;

    @Bean
    DataSource getHikariCp() {
        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl(dbUrl);
        source.setUsername(dbUser);
        source.setPassword(dbPassword);
        return source;
    }
}
