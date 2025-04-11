package com.example.tech_challenge.config;

import com.example.tech_challenge.enums.AuthorityEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((authorizeHttpRequestsConfigurer) -> authorizeHttpRequestsConfigurer
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/users/create").hasAuthority(AuthorityEnum.ADMIN.toString())
                        .requestMatchers("/users/update").authenticated()
                        .requestMatchers("/home").authenticated())

                .httpBasic(Customizer.withDefaults())

                .formLogin(formLoginConfigurer ->
                        formLoginConfigurer
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
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
