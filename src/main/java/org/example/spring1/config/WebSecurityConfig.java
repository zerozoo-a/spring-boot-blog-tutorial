package org.example.spring1.config;

import lombok.RequiredArgsConstructor;
import org.example.spring1.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
	private final UserDetailService userService;

	// 스프링 시큐리티 기능 비활성화
	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring()
				.requestMatchers(toH2Console()) // h2 data를 확인하는데 사용하는 console 하위 url을 대상으로 ignore
				.requestMatchers("/static/**"); // static 파일에 대해 springSecurity 비활성화
	}

	// 특정 HTTP 요청에 대한 웹 기반 보안 구성
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests() // 인증 인가 설정
				.requestMatchers("/login", "/signup", "/user").permitAll() // login, signup, user에 대해 보안 구성
				.anyRequest().authenticated() // 위에 설정한 세 요청 이외의 인가는 필요하지 않지만 인증이 접근할 수 있음
				.and()
				.formLogin() // 폼 기반 로그인 설정
				.loginPage("/login")
				.defaultSuccessUrl("/articles") // 성공시 이동 할 url
				.and()
				.logout()
				.logoutSuccessUrl("/login") // 로그아웃시 이동 할 url
				.invalidateHttpSession(true)
				.and()
				.csrf().disable()
				.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http,
	                                                   BCryptPasswordEncoder bCryptPasswordEncoder,
	                                                   UserDetailService userDetailService) throws Exception {

		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userService)
				.passwordEncoder(bCryptPasswordEncoder)
				.and()
				.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
