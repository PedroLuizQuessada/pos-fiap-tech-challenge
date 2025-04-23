package com.example.tech_challenge.config;

import com.example.tech_challenge.component.CustomAuthenticationFailureHandler;
import com.example.tech_challenge.enums.AuthorityEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((authorizeHttpRequestsConfigurer) ->
                        authorizeHttpRequestsConfigurer
                            .requestMatchers("/login").permitAll()
                            .requestMatchers("/api/v1/users/create").permitAll()
                            .requestMatchers("/api/v1/users/update").authenticated()
                            .requestMatchers("/api/v1/users/admin/update/{id}").hasAuthority(AuthorityEnum.ADMIN.toString())
                            .requestMatchers("/api/v1/users/delete").authenticated()
                            .requestMatchers("/api/v1/users/admin/delete/{login}").hasAuthority(AuthorityEnum.ADMIN.toString())
                            .requestMatchers("/api/v1/users/updatePassword").authenticated()
                            .requestMatchers("/api/v1/users/home").authenticated())

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
