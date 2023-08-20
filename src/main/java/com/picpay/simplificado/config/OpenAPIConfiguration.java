package com.picpay.simplificado.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class OpenAPIConfiguration {
    @Value("${murilonerdx.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Picpay simplificado");

        Contact contact = new Contact();
        contact.setEmail("mu-silva@outlook.com");
        contact.setName("Murilo P.S");
        contact.setUrl("www.github.com/murilonerdx");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Picpay simplificado desafio")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage demo.").termsOfService("www.github.com/murilonerdx")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}