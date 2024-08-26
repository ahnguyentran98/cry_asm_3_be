package org.example.back_end.securityConfig;

import org.example.back_end.constants.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserSecurityInfoService userSecurityInfoService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenService, userSecurityInfoService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/user/login",
                                "/api/user/sign-up")
                        .permitAll()
                        .requestMatchers("/api/user-plan/update-user-plan-monthly")
                        .hasRole(UserRole.ADMIN.getRole())
                        .requestMatchers("/api/user/information/{userId}")
                        .hasAnyRole(UserRole.USER.getRole())
                        .anyRequest().authenticated())
                .exceptionHandling()
                .accessDeniedHandler(new AppAccessDeniedHandler())
        ;
        return http.build();
    }

}

