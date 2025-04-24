package com.guen.book.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Guenengafo",
                        email = "contact@guenengafo.fr",
                        url = "https://guenengafo.fr"
                ),
                description = "Documentation OpenApi pour l'API book-social-network",
                title = "Spécification OpenApi - book-social-network-api",
                version = "1.0",
                license = @License(
                        name = "Licence book-social-network",
                        url = "https://guenengafo.fr/licences"
                ),
                termsOfService = "Conditions d'utilisation"
        ),
        servers = {
                @Server(
                        description = "Environnement de développement : Local",
                        url = "http://localhost:8088/api/v1"
                ),
                @Server(
                        description = "Environnement de production : Prod",
                        url = "https://guenengafo.fr/projets"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig
{
}
