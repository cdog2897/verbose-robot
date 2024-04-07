package com.gcu.registrationlogin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RegistrationLoginApplication {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationLoginApplication.class);

    public static void main(String[] args) {
        logger.info("Starting RegistrationLoginApplication...");
        SpringApplication.run(RegistrationLoginApplication.class, args);
        logger.info("RegistrationLoginApplication started successfully.");
    }
}
