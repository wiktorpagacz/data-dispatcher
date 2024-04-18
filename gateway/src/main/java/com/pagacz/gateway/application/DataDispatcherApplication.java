package com.pagacz.gateway.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.pagacz.database.model")
@EnableJpaRepositories("com.pagacz.database.repository")
@SpringBootApplication(scanBasePackages = "com.pagacz")
public class DataDispatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataDispatcherApplication.class, args);
    }

}
