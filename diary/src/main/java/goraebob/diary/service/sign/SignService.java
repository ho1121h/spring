package goraebob.diary.service.sign;


import goraebob.diary.config.token.TokenHelper;
import goraebob.diary.dto.sign.RefreshTokenResponse;
import goraebob.diary.dto.sign.SignInRequest;
import goraebob.diary.dto.sign.SignInResponse;
import goraebob.diary.dto.sign.SignUpRequest;
import goraebob.diary.entity.member.Member;
import goraebob.diary.entity.member.RoleType;
import goraebob.diary.exception.*;
import goraebob.diary.repository.member.MemberRepository;
import goraebob.diary.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// 로그인 서비스
@Service
@RequiredArgsConstructor //필수인자를 생성함
public class SignService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenHelper accessTokenHelper; // 1
    private final TokenHelper refreshTokenHelper; // 1

    @Transactional // 만약 실패하면 다시 롤백
    public void signUp(SignUpRequest req) { // 회원가입 서비스
        validateSignUpInfo(req);
        memberRepository.save(SignUpRequest.toEntity(req,
                roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new),// 예외 추가
                passwordEncoder));
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest req) { // 로그인 서비스 | 토큰 확인
        Member member = memberRepository.findByEmail(req.getEmail()).orElseThrow(LoginFailureException::new);
        validatePassword(req, member);
        String subject = createSubject(member);
        String accessToken = accessTokenHelper.createToken(subject);
        String refreshToken = refreshTokenHelper.createToken(subject);
        return new SignInResponse(accessToken, refreshToken);
    }

    public RefreshTokenResponse refreshToken(String rToken) { // 리프레쉬 토큰
        validateRefreshToken(rToken);
        String subject = refreshTokenHelper.extractSubject(rToken);
        String accessToken = accessTokenHelper.createToken(subject);
        return new RefreshTokenResponse(accessToken);
    }

    private void validateRefreshToken(String rToken) { // 토큰검증
        if(!refreshTokenHelper.validate(rToken)) {
            throw new AuthenticationEntryPointException();
        }
    }

    private void validateSignUpInfo(SignUpRequest req) { // 회우너 검증
        if(memberRepository.existsByEmail(req.getEmail()))
            throw new MemberEmailAlreadyExistsException(req.getEmail());
        if(memberRepository.existsByNickname(req.getNickname()))
            throw new MemberNicknameAlreadyExistsException(req.getNickname());
    }

    private void validatePassword(SignInRequest req, Member member) { //패스워드 검증
        if(!passwordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw new LoginFailureException();
        }
    }

    private String createSubject(Member member) {
        return String.valueOf(member.getId());
    }
}