package com.charles.zodrac.controller;

import com.charles.zodrac.ZodracApplication;
import com.charles.zodrac.commons.annotations.user.RunWithMockCustomUser;
import com.charles.zodrac.model.dto.UserDTO;
import com.charles.zodrac.service.mock.UserMock;
import com.charles.zodrac.utils.SerializationUtils;
import com.charles.zodrac.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWithMockCustomUser
@SpringBootTest(classes = ZodracApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest extends CommonIntTest {

    private static final String BASE_URL = "/user";

    @Autowired
    private UserMock userMock;

    @BeforeEach
    public void init() {
        UserDTO dto = userMock.getUserMock();
        userMock.createUser(dto);
    }

    @AfterEach
    public void close() {
        userMock.deleteAllUser();
    }

    @Order(1)
    @Test
    @DisplayName("Should create user")
    void shouldCreateUser() throws Exception {
        UserDTO dto = userMock.getUserMock(TestUtils.generateRandomString().concat("@email.com"), TestUtils.generateRandomString());
        this.getMockMvc()
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SerializationUtils.convertObjectToJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("user_success"));
    }

    @Test
    @DisplayName("Should get user detail")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldGetUserDetail() throws Exception {
        this.getMockMvc()
                .perform(get(buildUrl(BASE_URL, "detail")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should get all users")
    @RunWithMockCustomUser(authorities = "ROLE_ADMIN")
    void shouldGetAllUser() throws Exception {
        this.getMockMvc()
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @DisplayName("Should get user")
    @RunWithMockCustomUser(authorities = "ROLE_ADMIN")
    void shouldGetUser() throws Exception {
        this.getMockMvc()
                .perform(get(buildUrl(BASE_URL, userMock.getUser().toString())))
                .andExpect(status().isOk());
    }
}
