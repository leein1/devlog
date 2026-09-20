package kr.leeinwon.devlog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain chain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 인프라
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // 프리플라이트
                        .requestMatchers("/error").permitAll()
                        // 인증 API
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                        // 공개 조회
                        .requestMatchers(HttpMethod.GET, "/api/posts/**", "/api/tags", "/api/categories/**", "/api/series/**").permitAll()
                        // 구체적 경로 - 댓글은 회원
                        .requestMatchers(HttpMethod.POST, "/api/posts/*/comments").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/posts/*/comments/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/posts/*/comments/**").authenticated()
                        // 게시글-태그  관리자만
                        .requestMatchers(HttpMethod.POST, "/api/posts/*/tags/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/posts/*/tags/**").hasRole("ADMIN")
                        // 넓은 경로
                        // 포스트
                        .requestMatchers(HttpMethod.POST, "/api/posts").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/posts/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/posts/**").hasRole("ADMIN")
                        // 태그, 카테고리, 시리즈
                        .requestMatchers(HttpMethod.POST, "/api/tags/**", "/api/categories/**", "/api/series/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/tags/**", "/api/categories/**", "/api/series/**").hasRole("ADMIN")

                        // 나머지 차단
                        .anyRequest().denyAll()
                );

        return http.build();
    }
}
