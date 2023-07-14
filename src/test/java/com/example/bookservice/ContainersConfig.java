package com.example.bookservice;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
//import org.testcontainers.containers.MySQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfig {

 /*  @Bean
   @ServiceConnection
   public MySQLContainer mySQLContainer() {
        return new MySQLContainer("mysql:8.0.33");
   }*/

//   @Bean
//   @ServiceConnection
//   public KafkaContainer kafkaContainer() {
//       return new KafkaContainer(
//                    DockerImageName.parse("kafka/cp-kafka:7.2.1"));
//   }

//   @Bean
//   public GenericContainer mailhogContainer(DynamicPropertyRegistry registry) {
//       GenericContainer container = new GenericContainer("custom-image")
//                                            .withExposedPorts(8080);
//       return container;
//   }
}