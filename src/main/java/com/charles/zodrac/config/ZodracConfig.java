package com.charles.zodrac.config;

import com.charles.zodrac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ZodracConfig {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            if (userRepository.count() == 0) {
                System.out.println("create user");
            }
        };
    }
}
