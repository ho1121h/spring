package goraebob.diary.config.security;


import goraebob.diary.config.token.TokenHelper;
import goraebob.diary.service.sign.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenHelper accessTokenHelper; //accessTokenHelper를 이용하여 토큰을 검증합니다.
    private final CustomUserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/exception/**",
                "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**"); //Swagger 로 만ㄷ르어진 api 문서를 사용하기 위함
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers(HttpMethod.POST, "/api/sign-in", "/api/sign-up", "/api/refresh-token").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/posts").authenticated()
                        .antMatchers(HttpMethod.PUT, "/api/posts/{id}").access("@postGuard.check(#id)")
                        .antMatchers(HttpMethod.DELETE, "/api/posts/{id}").access("@postGuard.check(#id)")
                        .antMatchers(HttpMethod.DELETE, "/api/members/{id}/**").access("@memberGuard.check(#id)")
                        .anyRequest().hasAnyRole("ADMIN")

                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(accessTokenHelper, userDetailsService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
/**
 * 클라이언트가 api 요청 -> 로그인해서 발급받은 액세스토큰을 http aythorization 헤더에 담음
 *  필터를 거침
 * 요청한 url에 따라서 접근 허용 여부를 검사 -> Guard ->AuthHelper
 * 인증되지않은 사용자라면 401 응답 / 요청한 자원에 접근 권한이 없다면 403 응답
 */
