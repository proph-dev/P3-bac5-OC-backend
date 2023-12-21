package com.openclassrooms.rentals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class RentalsApplication {
    public static void main(String[] args) {
        SpringApplication.run(RentalsApplication.class, args);
    } 
}