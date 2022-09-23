package com.charles.zodrac;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ZodracApplication {

    @Bean
    public OpenAPI customOpenApi(@Value("${app.version}") String appVersion,
                                 @Value("${app.description}") String appDescription) {
        return new OpenAPI().components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info().title("Zodrac API")
                        .version(appVersion)
                        .description(appDescription)
                        .license(new License().name("Zodrac by Charles Lana")
                                .url("https://github.com/charleslana")));
    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(ZodracApplication.class, args);
    }

}
