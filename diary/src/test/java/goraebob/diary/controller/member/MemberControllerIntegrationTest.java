package goraebob.diary.controller.member;

import goraebob.diary.dto.sign.SignInRequest;
import goraebob.diary.dto.sign.SignInResponse;
import goraebob.diary.entity.member.Member;
import goraebob.diary.exception.MemberNotFoundException;
import goraebob.diary.init.TestInitDB;
import goraebob.diary.repository.member.MemberRepository;
import goraebob.diary.service.sign.SignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static goraebob.diary.factory.dto.SignInRequestFactory.createSignInRequest;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 어노테이션

@SpringBootTest // 협력 Bean
@AutoConfigureMockMvc // 가짜 웹환경 만듬
@ActiveProfiles(value = "test") // 통합 테스트를 진행하기 위한 데이터를 별도로 삽입 -> 충돌 방지
@Transactional
class MemberControllerIntegrationTest {
    @Autowired //MockMvc를 빌드하기 위해 WebApplicationContext를 주입
    WebApplicationContext context;
    @Autowired  //API 요청을 보내고 테스트하기 위해 주입
    MockMvc mockMvc;

    @Autowired //통합 테스트에서 사용될 데이터 초기화를 위한 빈입니다. 테스트에 필요한 데이터들을 삽입하여 데이터베이스를 초기화
    TestInitDB initDB;
    @Autowired
    SignService signService;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        initDB.initDB();
    }

    @Test //mockMvc 초기화
    void readTest() throws Exception {
        // given
        Member member = memberRepository.findByEmail(initDB.getMember1Email()).orElseThrow(MemberNotFoundException::new);

        // when, then
        mockMvc.perform(
                        get("/api/members/{id}", member.getId()))
                .andExpect(status().isOk());
    }

    @Test //사용자 정보 삭제
    void deleteTest() throws Exception {
        // given
        Member member = memberRepository.findByEmail(initDB.getMember1Email()).orElseThrow(MemberNotFoundException::new);
        SignInResponse signInRes = signService.signIn(new SignInRequest(initDB.getMember1Email(), initDB.getPassword()));

        // when, then
        mockMvc.perform(
                        delete("/api/members/{id}", member.getId()).header("Authorization", signInRes.getAccessToken()))
                .andExpect(status().isOk());
    }

    @Test ///관리자에 의한 삭제 요청
    void deleteByAdminTest() throws Exception {
        // given
        Member member = memberRepository.findByEmail(initDB.getMember1Email()).orElseThrow(MemberNotFoundException::new);
        SignInResponse adminSignInRes = signService.signIn(new SignInRequest(initDB.getAdminEmail(), initDB.getPassword()));

        // when, then
        mockMvc.perform(
                        delete("/api/members/{id}", member.getId()).header("Authorization", adminSignInRes.getAccessToken()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUnauthorizedByNoneTokenTest() throws Exception {
        // given
        Member member = memberRepository.findByEmail(initDB.getMember1Email()).orElseThrow(MemberNotFoundException::new);

        // when, then
        mockMvc.perform(
                        delete("/api/members/{id}", member.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/exception/entry-point"));
    }

    @Test
    void deleteAccessDeniedByNotResourceOwnerTest() throws Exception {
        // given
        Member member = memberRepository.findByEmail(initDB.getMember1Email()).orElseThrow(MemberNotFoundException::new);
        SignInResponse attackerSignInRes = signService.signIn(new SignInRequest(initDB.getMember2Email(), initDB.getPassword()));

        // when, then
        mockMvc.perform(
                        delete("/api/members/{id}", member.getId()).header("Authorization", attackerSignInRes.getAccessToken()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/exception/access-denied"));
    }

    @Test
    void deleteUnauthorizedByRefreshTokenTest() throws Exception {
        // given
        Member member = memberRepository.findByEmail(initDB.getMember1Email()).orElseThrow(MemberNotFoundException::new);
        SignInResponse signInRes = signService.signIn(createSignInRequest(initDB.getMember1Email(), initDB.getPassword()));

        // when, then
        mockMvc.perform(
                delete("/api/members/{id}", member.getId()).header("Authorization", signInRes.getRefreshToken()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/exception/entry-point"));
    }
}