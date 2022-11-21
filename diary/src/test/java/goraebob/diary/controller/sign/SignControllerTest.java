package goraebob.diary.controller.sign;

import com.fasterxml.jackson.databind.ObjectMapper;
import goraebob.diary.dto.sign.SignInRequest;
import goraebob.diary.dto.sign.SignInResponse;
import goraebob.diary.dto.sign.SignUpRequest;
import goraebob.diary.service.sign.SignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static goraebob.diary.factory.dto.RefreshTokenResponseFactory.createRefreshTokenResponse;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class SignControllerTest {
    @InjectMocks
    SignController signController;
    @Mock
    SignService signService;
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper(); // 1

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(signController).build();
    }

    @Test
    void signUpTest() throws Exception {
        // given
        SignUpRequest req = new SignUpRequest("email@email.com", "123456a!", "username", "nickname");

        // when, then
        mockMvc.perform(
                        post("/api/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))) // 2
                .andExpect(status().isCreated());

        verify(signService).signUp(req);
    }

    @Test
    void signInTest() throws Exception {
        // given
        SignInRequest req = new SignInRequest("email@email.com", "123456a!");
        given(signService.signIn(req)).willReturn(new SignInResponse("access", "refresh"));

        // when, then
        mockMvc.perform(
                        post("/api/sign-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.data.accessToken").value("access")) // 3
                .andExpect(jsonPath("$.result.data.refreshToken").value("refresh"));

        verify(signService).signIn(req);
    }

    @Test
    void ignoreNullValueInJsonResponseTest() throws Exception { // 4
        // given
        SignUpRequest req = new SignUpRequest("email@email.com", "123456a!", "username", "nickname");

        // when, then
        mockMvc.perform(
                        post("/api/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.result").doesNotExist());

    }
    @Test
    void refreshTokenTest() throws Exception {
        // given
        given(signService.refreshToken("refreshToken")).willReturn(createRefreshTokenResponse("accessToken"));

        // when, then
        mockMvc.perform(
                        post("/api/refresh-token")
                                .header("Authorization", "refreshToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.data.accessToken").value("accessToken"));
    }
}