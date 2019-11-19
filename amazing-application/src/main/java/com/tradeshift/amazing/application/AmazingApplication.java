package com.tradeshift.amazing.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"com.tradeshift.amazing.application",  "com.tradeshift.amazing.domain", "com.tradeshift.amazing.persistence", "com.tradeshift.amazing.core"})
@EnableJpaRepositories(basePackages = "com.tradeshift.amazing.persistence.repository" )
@EntityScan("com.tradeshift.amazing.domain.entity")
public class AmazingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmazingApplication.class, args);
    }

}
