package com.charles.zodrac.config;

import com.charles.zodrac.enums.RoleEnum;
import com.charles.zodrac.model.entity.UserEntity;
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
                createAdminUser();
                createUser();
            }
        };
    }

    private void createAdminUser() {
        UserEntity entity = new UserEntity();
        entity.setEmail("admin@admin.com");
        entity.setPassword(encoder.encode("123456"));
        entity.setRole(RoleEnum.ADMIN);
        userRepository.save(entity);
    }

    private void createUser() {
        UserEntity entity = new UserEntity();
        entity.setEmail("user@user.com");
        entity.setPassword(encoder.encode("123456"));
        userRepository.save(entity);
    }
}
