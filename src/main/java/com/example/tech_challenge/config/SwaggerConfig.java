package com.example.tech_challenge.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
        info = @Info(title = "API usuários", version = "v1.0.0",
                description = "API para gerenciamento de usuários proposta como tech-challenge da pós-tech de " +
                        "Arquitetura e desenvolvimento em Java da FIAP")
)
public class SwaggerConfig {


}
