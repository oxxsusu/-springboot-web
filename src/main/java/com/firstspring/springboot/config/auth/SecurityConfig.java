package com.firstspring.springboot.config.auth;

import com.firstspring.springboot.domain.posts.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .and()
                    .authorizeRequests() // URL별 권한 관리를 설정하는 옵션의 시작점
                    // authorizeRequests를 선언해야 antMachers 옵션을 사용할 수 있다
                    .antMatchers("/", "/css/", "/images/**", "/js/**", "/h2/**", "/profile").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    /* antMachers : 권한 관리 대상을 지정하는 옵션.
                     * URL, HTTP 메소드별로 관리가 가능하다.
                     * "/"등 지정된 URL들을 permitAll() 옵션을 통해 전체 열람 권한을 주고,
                     * "/api/v1/**" 주소를 가진 API 는 USER 권한을 가진 사람만 가능하도록 설정했다 */
                    .anyRequest().authenticated() // 지정한 URL이 아닌 URL들에 대해 인증된 사용자들을 대상으로 허용 (authenticated)
                .and()
                    .logout()
                    .logoutSuccessUrl("/")// 로그아웃 설정의 진입점을 표시하며, 로그아웃 성공시 "/" 주소로 이동하게 했음
                .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService);
                /* oauth2 로그인 기능에 대한 설정의 진입점.
                   userService 는 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
                   리소스 서버(소셜 서비스들..) 에서 사용자 정보를 가져온 상태에서,
                   추가로 진행하고자 하는 기능을 명시할 수 있다. */
    }
}