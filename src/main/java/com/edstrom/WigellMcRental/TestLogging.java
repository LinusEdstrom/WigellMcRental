package com.edstrom.WigellMcRental;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestLogging implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TestLogging.class);

    public static void main(String[] args) {
        SpringApplication.run(TestLogging.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("INFO: This is a test log for CREATE operation");
        logger.warn("WARN: This is a test log for UPDATE operation");
        logger.error("ERROR: This is a test log for DELETE operation");
    }
}
