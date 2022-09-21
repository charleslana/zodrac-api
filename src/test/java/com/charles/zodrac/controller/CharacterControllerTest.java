package com.charles.zodrac.controller;

import com.charles.zodrac.config.ConfigTestClass;
import com.charles.zodrac.enums.GenderEnum;
import com.charles.zodrac.model.dto.CharacterDTO;
import com.charles.zodrac.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CharacterControllerTest extends ConfigTestClass {

    private final String path = "/character";
    private static String name;

    @BeforeAll
    public static void setUp() {
        name = TestUtils.generateRandomString();
    }

    @AfterEach
    public void tearDown() {
    }

    @Order(1)
    @Test
    @DisplayName("Should create character")
    void shouldCreateCharacter() throws Exception {
        CharacterDTO dto = new CharacterDTO();
        dto.setName(name);
        dto.setBirthDate(LocalDate.now());
        dto.setGender(GenderEnum.MALE);

        mockMvc.perform(post(path)
                        .content(requestBody(dto))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    @DisplayName("Should get all characters")
    void shouldGetAllCharacters() throws Exception {
        mockMvc.perform(get(path)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Order(3)
    @Test
    @DisplayName("Should search characters")
    void shouldSearchCharacters() throws Exception {
        mockMvc.perform(get(path.concat(String.format("/search?searchTerm=%s", name)))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Order(4)
    @Test
    @DisplayName("Should select character")
    void shouldSelectCharacter() throws Exception {
        mockMvc.perform(post(path.concat("/1"))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(5)
    @Test
    @DisplayName("Should get character detail")
    void shouldGetCharacterDetail() throws Exception {
        mockMvc.perform(post(path.concat("/1"))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get(path.concat("/detail"))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(6)
    @Test
    @DisplayName("Should logout character")
    void shouldLogoutCharacter() throws Exception {
        mockMvc.perform(post(path.concat("/1"))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post(path.concat("/logout"))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(7)
    @Test
    @DisplayName("Should delete character")
    void shouldDeleteCharacter() throws Exception {
        mockMvc.perform(delete(path.concat("/1"))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
