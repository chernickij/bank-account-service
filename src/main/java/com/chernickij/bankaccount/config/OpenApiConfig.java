package com.chernickij.bankaccount.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${app.name}")
    private String appName;

    @Value("${app.apiVersion}")
    private String apiVersion;

    @Value("${app.description}")
    private String appDescription;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                    .title(appName)
                    .description(appDescription)
                    .version(apiVersion))
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("Authorization", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi usersOpenApi() {
        return GroupedOpenApi.builder()
                .group("User API")
                .pathsToMatch("/users/**")
                .build();
    }
}
