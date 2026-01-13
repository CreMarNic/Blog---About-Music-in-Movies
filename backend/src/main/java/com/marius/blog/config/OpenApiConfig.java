package com.marius.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Blog Platform API - About Music in Movies",
        version = "1.0.0",
        description = "RESTful API for a blog platform focused on music in movies. Features include post management, categories, tags, comments, and user authentication.",
        contact = @Contact(
            name = "Marius",
            email = "contact@example.com"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Local development server"),
        @Server(url = "https://your-production-url.com", description = "Production server")
    }
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "JWT Authentication"
)
public class OpenApiConfig {
}
