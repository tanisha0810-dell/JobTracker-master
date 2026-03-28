package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example", "dao", "model", "security", "controller", "dto"})
@EnableJpaRepositories(basePackages = {"dao"})
@EntityScan(basePackages = {"model"})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Job Application Tracking System started...");
        System.out.println("Use POST /auth/signup to register users.");
        System.out.println("Use POST /auth/login to authenticate and get JWT.");
        System.out.println("Then call protected APIs with Authorization: Bearer <token>");
    }
}


