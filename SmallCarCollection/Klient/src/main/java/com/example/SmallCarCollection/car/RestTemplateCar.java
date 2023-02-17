package com.example.SmallCarCollection.car;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateCar {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
