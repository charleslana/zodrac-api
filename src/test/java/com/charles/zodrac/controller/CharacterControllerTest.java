package com.charles.zodrac.controller;

import com.charles.zodrac.ZodracApplication;
import com.charles.zodrac.commons.annotations.user.RunWithMockCustomUser;
import com.charles.zodrac.model.dto.CharacterDTO;
import com.charles.zodrac.model.dto.UserDTO;
import com.charles.zodrac.service.mock.CharacterMock;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWithMockCustomUser
@SpringBootTest(classes = ZodracApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CharacterControllerTest extends CommonIntTest {

    private static final String BASE_URL = "/character";
    private String randomString;

    @Autowired
    private CharacterMock characterMock;

    @Autowired
    private UserMock userMock;

    @BeforeEach
    public void init() {
        UserDTO userDTO = userMock.getUserMock();
        userMock.createUser(userDTO);
        randomString = TestUtils.generateRandomString();
        CharacterDTO characterDTO = characterMock.getCharacterMock(randomString);
        characterMock.createCharacter(characterDTO, userMock.getUser());
    }

    @AfterEach
    public void close() {
        characterMock.deleteAllCharacter();
        userMock.deleteAllUser();
    }

    @Order(1)
    @Test
    @DisplayName("Should create character")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldCreateCharacter() throws Exception {
        CharacterDTO dto = characterMock.getCharacterMock(TestUtils.generateRandomString());
        this.getMockMvc()
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SerializationUtils.convertObjectToJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("character_success"));
    }

    @Test
    @DisplayName("Should get all characters")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldGetAllCharacters() throws Exception {
        this.getMockMvc()
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    @DisplayName("Should search characters")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldSearchCharacters() throws Exception {
        this.getMockMvc()
                .perform(get(buildUrl(BASE_URL, String.format("search?searchTerm=%s", randomString))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @DisplayName("Should select character")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldSelectCharacter() throws Exception {
        this.getMockMvc()
                .perform(post(buildUrl(BASE_URL, characterMock.getCharacter().toString())))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    @DisplayName("Should get character detail")
    @RunWithMockCustomUser(authorities = "ROLE_USER", characterId = "3")
    void shouldGetCharacterDetail() throws Exception {
        this.getMockMvc()
                .perform(get(buildUrl(BASE_URL, "detail")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should logout character")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldLogoutCharacter() throws Exception {
        this.getMockMvc()
                .perform(post(buildUrl(BASE_URL, "logout")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should delete character")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldDeleteCharacter() throws Exception {
        this.getMockMvc()
                .perform(delete(buildUrl(BASE_URL, characterMock.getCharacter().toString())))
                .andExpect(status().isOk());
    }
}
