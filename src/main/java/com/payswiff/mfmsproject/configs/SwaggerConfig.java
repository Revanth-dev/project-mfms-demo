package com.payswiff.mfmsproject.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // Define the OpenAPI bean with JWT security scheme
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Merchant Feedback Management System")
                .description("Feedback collection from merchant about POS devices")
                .version("MFMS_0.0.1")
                .contact(new Contact()
                    .name("MFMS")
                    .url("www.mfms.com")
                    .email("mfmsproject7@gmail.com"))
                .license(new License().name("MFMS License").url("www.mfms.com")))
            .components(new Components()  // Add JWT security scheme
                .addSecuritySchemes("bearer-key", 
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")
                ))
            .addSecurityItem(new SecurityRequirement().addList("bearer-key")); // Apply the security scheme
    }

    // Optional: Grouped API if needed for specific path matching
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("public")
            .pathsToMatch("/**")
            .build();
    }
}
