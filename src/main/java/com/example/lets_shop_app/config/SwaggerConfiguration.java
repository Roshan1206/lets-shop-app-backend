package com.example.lets_shop_app.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for OpenAPI Swagger <br>
 * Application details and security are configured for documentation and testing.
 *
 * @author Roshan
 */
@Configuration
public class SwaggerConfiguration {

    /**
     * Configures {@link OpenAPI} bean for Application documentation and API testing in swagger.
     *
     * @return configured {@link OpenAPI} instance
     */
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Lets Shop App")
                                .description("Lets shop app documentation")
                                .version("v1"))
                .components(
                        new Components()
                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .security(List.of(new SecurityRequirement().addList("bearerAuth")));
    }
}
