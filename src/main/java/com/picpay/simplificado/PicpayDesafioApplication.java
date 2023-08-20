package com.picpay.simplificado;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class PicpayDesafioApplication {

    public static void main(String[] args) {
        SpringApplication.run(PicpayDesafioApplication.class, args);
    }

}
