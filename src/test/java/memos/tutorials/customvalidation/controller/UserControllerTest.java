package memos.tutorials.customvalidation.controller;

import memos.tutorials.customvalidation.controller.dto.UserRequestDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static memos.tutorials.customvalidation.TestUtils.buildMockMvc;
import static memos.tutorials.customvalidation.TestUtils.getObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    AutoCloseable closeable;

    private MockMvc mockMvc;

    @InjectMocks
    private UserController controllerUnderTest;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = buildMockMvc(controllerUnderTest);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void shouldReturn204() throws Exception {
        // given
        UserRequestDTO dto = getValidUserRequestDTO();

        // when
        mockMvc.perform(post("/validation-examples")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getObjectMapper().writeValueAsString(dto)))
               .andExpect(status().isNoContent());

    }

    @Test
    void shouldReturn400WhenLuckyNumberNotValid() throws Exception {
        // given
        UserRequestDTO dto = getValidUserRequestDTO();
        dto.setPassport("790916a2-75db-4744-a2c5-6127c1271e31");
        dto.setLuckyNumber(13);

        // when
        mockMvc.perform(post("/validation-examples")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getObjectMapper().writeValueAsString(dto)))
               .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturn400WhenMaxDataSizeNotValid() throws Exception {
        // given
        UserRequestDTO dto = getValidUserRequestDTO();
        dto.setDrivingLicence("790916a2-75db-4744-a2c5-6127c1271e31");
        dto.setFibonacci(null);

        dto.setMaxDataSize(64);

        // when
        mockMvc.perform(post("/validation-examples")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getObjectMapper().writeValueAsString(dto)))
               .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturn400WhenFibonacciNotValid() throws Exception {
        // given
        UserRequestDTO dto = getValidUserRequestDTO();
        dto.setLuckyNumber(null);

        dto.setFibonacci(4L);

        // when
        mockMvc.perform(post("/validation-examples")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getObjectMapper().writeValueAsString(dto)))
               .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturn400WhenSelectiveMandatoryFieldsNotPresent() throws Exception {
        // given
        UserRequestDTO dto = getValidUserRequestDTO();

        dto.setPassport("");
        dto.setId(null);
        dto.setDrivingLicence("");

        // when
        mockMvc.perform(post("/validation-examples")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getObjectMapper().writeValueAsString(dto)))
               .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturn400WhenCountryNotValid() throws Exception {
        // given
        UserRequestDTO dto = getValidUserRequestDTO();

        dto.setCountry("TUR");

        // when
        mockMvc.perform(post("/validation-examples")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getObjectMapper().writeValueAsString(dto)))
               .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenAgeNotValid() throws Exception {
        // given
        UserRequestDTO dto = getValidUserRequestDTO();

        dto.setBirthDate(LocalDate.now());

        // when
        mockMvc.perform(post("/validation-examples")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getObjectMapper().writeValueAsString(dto)))
               .andExpect(status().isBadRequest());

        // given
        dto = getValidUserRequestDTO();

        dto.setBirthDate(LocalDate.of(1900, 1, 1));

        // when
        mockMvc.perform(post("/validation-examples")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getObjectMapper().writeValueAsString(dto)))
               .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenCombinedValidationNotValid() throws Exception {
        // given
        UserRequestDTO dto = getValidUserRequestDTO();

        dto.setCombinedValidation(6);

        // when
        mockMvc.perform(post("/validation-examples")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getObjectMapper().writeValueAsString(dto)))
               .andExpect(status().isBadRequest());
    }

    private UserRequestDTO getValidUserRequestDTO() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setFirstName("Memo's");
        dto.setLastName("Tutorials");
        dto.setId("790916a2-75db-4744-a2c5-6127c1271e31");
        dto.setCountry("HR");
        dto.setLuckyNumber(7);
        dto.setMaxDataSize(32);
        dto.setFibonacci(13L);
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setCombinedValidation(7);
        return dto;
    }
}
