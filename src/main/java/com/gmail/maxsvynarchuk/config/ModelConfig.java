package com.gmail.maxsvynarchuk.config;

import com.gmail.maxsvynarchuk.util.PasswordManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ModelConfig implements WebMvcConfigurer {
    @Bean
    public PasswordManager passwordManager() {
        return new PasswordManager();
    }
}
