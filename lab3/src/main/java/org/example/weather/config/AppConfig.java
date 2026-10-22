package org.example.weather.config;

import org.example.weather.component.WeatherValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    // Анотація @Bean, створення біна типу singleton
    @Bean
    @Scope("singleton")
    public WeatherValidator weatherValidator() {
        return new WeatherValidator(-90, 60);
    }
}
