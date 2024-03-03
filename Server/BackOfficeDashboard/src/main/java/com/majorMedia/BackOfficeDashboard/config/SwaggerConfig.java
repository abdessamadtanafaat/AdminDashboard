package com.majorMedia.BackOfficeDashboard.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
            info = @Info(
                    contact = @Contact(
                            name = "Tanafaat Abdessamad",
                            email = "abdessamad.tanafaat@edu.uiz.ac.ma",
                            url = "https://ma.linkedin.com/in/abdessamad-tanafaat-924534222"
                    ),
            description = "Admin Dashboard For The Application Satisnap",
            title = "Admin Dashboard For The Application Satisnap",
            version = "1.0"
    ),
        servers =  {
                    @Server(
                            description = "LOCAL ENVIRONNEMENT",
                            url = "http://localhost:8080"
                    ),
        },
        security = {
                    @SecurityRequirement(
                            name = "bearerAuth"
                    )
        }
)
@SecurityScheme(
        name = "Bearer Token Authentication",
        description = "Use Bearer token for authentication. Include the token in the 'Authorization' header.",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

public class SwaggerConfig{

}

