package com.charles.zodrac.controller;

import com.charles.zodrac.config.ConfigTestClass;
import com.charles.zodrac.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest extends ConfigTestClass {

    private final String path = "/api/user";

    @Autowired
    private UserRepository repository;

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Order(1)
    @Test
    @DisplayName("Should be true")
    void shouldBeTrue() {
        Assertions.assertTrue(true);
    }
}
