package com.suho.book.springboot.config.auth;

import com.suho.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정들을 활성화 시켜 준다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // h2-console 화면을 사용하기 위해 해당 옵션들을 disable 합니다.
                .csrf().disable()
                .headers().frameOptions().disable()
                
                .and()
                    //URL별 권한 관리를 설정하는 옵션의 시작점
                    .authorizeRequests()
                    //권한 관리 대상을 지정하는 옵션
                    .antMatchers("/","/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    // anyRequest : 설정된 값들 이외 나머지 URL들을 나타낸다.
                    // authenticated : 나머지 URL들을 모두 인증된 사용자들에게 만 허용 한다.
                    .anyRequest().authenticated()

                .and()
                    // logout : 로그아웃 기능에 대한 여러 설정의 진입점
                    // logoutSuccessUrl : 로그아웃 성공 시 / 주소로 이동
                    .logout()
                        .logoutSuccessUrl("/")

                .and()
                    // oauth2Login : OAuth2 로그인 기능에 대한 여러 설정의 진입점
                    // userInfoEndpoint : OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당한다.
                    // userService : 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체 등록
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
