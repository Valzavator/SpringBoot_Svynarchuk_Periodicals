package com.gmail.maxsvynarchuk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PeriodicalsApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(PeriodicalsApplication.class, args);
    }
}