package com.example.tech_challenge.config;

import com.example.tech_challenge.component.security.CustomAccessDeniedHandler;
import com.example.tech_challenge.component.security.CustomAuthenticationFailureHandler;
import com.example.tech_challenge.enums.AuthorityEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] ENDPOINTS_PERMIT_ALL = {
            "/login",
            "/api/v1/users/create"
    };

    private static final String[] ENDPOINTS_AUTHENTICATED = {
            "/api/v1/users/home",
            "/api/v1/users/update",
            "/api/v1/users/delete",
            "/api/v1/users/updatePassword"
    };

    private static final String[] ENDPOINTS_ADMIN = {
            "/api/v1/users/admin/update/{id}",
            "/api/v1/users/admin/delete/{login}"
    };

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        CustomAccessDeniedHandler customAccessDeniedHandler = new CustomAccessDeniedHandler();
        customAccessDeniedHandler.setEndpoints(List.of(List.of(ENDPOINTS_PERMIT_ALL),
                List.of(ENDPOINTS_AUTHENTICATED), List.of(ENDPOINTS_ADMIN)));
        return customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorizeHttpRequestsConfigurer ->
                        authorizeHttpRequestsConfigurer
                                .requestMatchers(ENDPOINTS_PERMIT_ALL).permitAll()
                                .requestMatchers(ENDPOINTS_AUTHENTICATED).authenticated()
                                .requestMatchers(ENDPOINTS_ADMIN).hasAuthority(AuthorityEnum.ADMIN.toString()))

                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .accessDeniedHandler(accessDeniedHandler()))

                .httpBasic(Customizer.withDefaults())

                .formLogin(formLoginConfigurer ->
                        formLoginConfigurer
                            .loginPage("/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/api/v1/users/home", true)
                            .failureHandler(authenticationFailureHandler())
                            .permitAll()
                )

                .logout(logoutConfigurer ->
                        logoutConfigurer
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                                .invalidateHttpSession(true)
                )

                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer
                                .invalidSessionUrl("/login?expiredSession")
                );

        return http.build();
    }
}
