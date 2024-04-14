package memos.tutorials.customvalidation.controller;

import memos.tutorials.customvalidation.controller.dto.UserRequestDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("invalidDTOProvider")
    void shouldReturn400(UserRequestDTO dto) throws Exception {
        mockMvc.perform(post("/validation-examples")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getObjectMapper().writeValueAsString(dto)))
               .andExpect(status().isBadRequest());
    }

    private static Stream<UserRequestDTO> invalidDTOProvider() {
        List<UserRequestDTO> requests = new ArrayList<>();

        // Invalid lucky number
        UserRequestDTO dto = getValidUserRequestDTO();
        dto.setLuckyNumber(13);
        requests.add(dto);

        // Invalid max data size
        dto = getValidUserRequestDTO();
        dto.setMaxDataSize(64);
        requests.add(dto);

        // Invalid fibonacci
        dto = getValidUserRequestDTO();
        dto.setFibonacci(4L);
        requests.add(dto);

        // Invalid selective mandatory
        dto = getValidUserRequestDTO();
        dto.setPassport("");
        dto.setId(null);
        dto.setDrivingLicence("");
        requests.add(dto);

        // Invalid country
        dto = getValidUserRequestDTO();
        dto.setCountry("XXX");
        requests.add(dto);

        // Invalid age - younger
        dto = getValidUserRequestDTO();
        dto.setBirthDate(LocalDate.now());
        requests.add(dto);

        // Invalid age - older
        dto = getValidUserRequestDTO();
        dto.setBirthDate(LocalDate.of(1900, 1, 1));
        requests.add(dto);

        // Invalid min, max, excluded number
        dto = getValidUserRequestDTO();
        dto.setCombinedValidation(6);
        requests.add(dto);

        // Invalid number divisible by 3
        dto = getValidUserRequestDTO();
        dto.setSubscriptionDuration(2);
        requests.add(dto);

        // Invalid company name
        dto = getValidUserRequestDTO();
        dto.setType(UserRequestDTO.AccountType.BUSINESS);
        dto.setCompanyName("");
        requests.add(dto);

        // Invalid personal first name
        dto = getValidUserRequestDTO();
        dto.setType(UserRequestDTO.AccountType.PERSONAL);
        dto.setFirstName("");
        requests.add(dto);

        // Invalid personal last name
        dto = getValidUserRequestDTO();
        dto.setType(UserRequestDTO.AccountType.PERSONAL);
        dto.setLastName("");
        requests.add(dto);

        return requests.stream();
    }

    private static UserRequestDTO getValidUserRequestDTO() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setType(UserRequestDTO.AccountType.PERSONAL);
        dto.setFirstName("Memo's");
        dto.setLastName("Tutorials");
        dto.setId("790916a2-75db-4744-a2c5-6127c1271e31");
        dto.setCountry("HR");
        dto.setLuckyNumber(7);
        dto.setMaxDataSize(32);
        dto.setFibonacci(13L);
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setCombinedValidation(7);
        dto.setSubscriptionDuration(12);
        return dto;
    }
}
