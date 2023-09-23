package com.bettingApp.appConfig;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class OpenApiConfig {

    private static final String DESCRIPTION = "The Telegram Bot Betting App Platform API provides a set of endpoints for managing bets," +
            " user accounts, and betting services via a Telegram bot interface." +
            " It allows users to place bets, check their balance, and access various betting services.";

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Telegram Bot Betting App Platform API")
                        .description(DESCRIPTION)
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Abdi Tiruneh")
                                .email("abditrnhdev@gmail.com")
                                .url("https://github.com/Abdi-Tiruneh")
                        )
                        .license(new License()
                                .name("owner name")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/Abdi-Tiruneh")
                )
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                        )
                )
                .servers(Collections.singletonList(
                        new Server()
                                .description("Local Test Server")
                ));
    }
}
