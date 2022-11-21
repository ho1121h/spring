package goraebob.diary.config.security.guard;

import goraebob.diary.config.security.CustomAuthenticationToken;
import goraebob.diary.config.security.CustomUserDetails;
import goraebob.diary.entity.member.RoleType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
//NoArgsConstructor: 기본생성자의 접근 권한을 protected로 제한한다.
//생성자로 protected Posts() {}와 같은 효과
//
//Entity Class를 프로젝트 코드상에서 기본생성자로 생성하는 것은 금지하고, JPA에서 Entity 클래스를 생성하는것은 허용하기 위해 추가한다.
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthHelper {

    public static boolean isAuthenticated() {
        return getAuthentication() instanceof CustomAuthenticationToken &&
                getAuthentication().isAuthenticated();
    }

    public static Long extractMemberId() {
        return Long.valueOf(getUserDetails().getUserId());
    }

    public static Set<RoleType> extractMemberRoles() {
        return getUserDetails().getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .map(strAuth -> RoleType.valueOf(strAuth))
                .collect(Collectors.toSet());
    }

    private static CustomUserDetails getUserDetails() {
        return (CustomUserDetails) getAuthentication().getPrincipal();
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
/**
 * 사용자 인증 정보를 추출하기 위해 도움을 줄 클래스입니다.
 *
 *
 */
