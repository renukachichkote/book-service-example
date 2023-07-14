package com.example.bookservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

@Configuration
public class TestBookServiceApplication {

    @Bean
    @RestartScope
    @ServiceConnection
    PostgreSQLContainer postgreSQLContainer() {
        return new PostgreSQLContainer("postgres:15.3-alpine");
    }

    public static void main(String[] args) {
        SpringApplication.from(BookServiceApplication::main)
                .with(TestBookServiceApplication.class)
                .run(args);
    }

}
