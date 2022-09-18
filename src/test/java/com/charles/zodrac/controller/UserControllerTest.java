package com.charles.zodrac.controller;

import com.charles.zodrac.config.ConfigTestClass;
import com.charles.zodrac.model.dto.UserDTO;
import com.charles.zodrac.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest extends ConfigTestClass {

    private final String path = "/user";

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Order(1)
    @Test
    @DisplayName("Should create user")
    void shouldCreateUser() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setEmail(TestUtils.generateRandomEmail());
        dto.setPassword(TestUtils.generateRandomString());

        mockMvc.perform(post(path)
                        .content(requestBody(dto))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    @DisplayName("Should get all users")
    @WithMockUser(username = "admin@admin.com", password = "123456", roles = "ADMIN")
    void shouldGetAllUsers() throws Exception {
        mockMvc.perform(get(path)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)));
    }

    @Order(3)
    @Test
    @DisplayName("Should get user")
    @WithMockUser(username = "admin@admin.com", password = "123456", roles = "ADMIN")
    void shouldGetUser() throws Exception {
        mockMvc.perform(get(path.concat("/1"))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(4)
    @Test
    @DisplayName("Should get user detail")
    @WithMockUser(username = "user@user.com", password = "123456", roles = "USER")
    void shouldGetUserDetail() throws Exception {
        mockMvc.perform(get(path.concat("/detail"))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
