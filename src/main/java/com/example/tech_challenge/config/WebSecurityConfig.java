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
                            .requestMatchers("/users/create").permitAll()
                            .requestMatchers("/users/update").authenticated()
                            .requestMatchers("/users/admin/update/{id}").hasAuthority(AuthorityEnum.ADMIN.toString())
                            .requestMatchers("/users/delete").authenticated()
                            .requestMatchers("/users/admin/delete/{login}").hasAuthority(AuthorityEnum.ADMIN.toString())
                            .requestMatchers("/users/updatePassword").authenticated()
                            .requestMatchers("/users/home").authenticated())

                .httpBasic(Customizer.withDefaults())

                .formLogin(formLoginConfigurer ->
                        formLoginConfigurer
                            .loginPage("/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/users/home", true)
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
