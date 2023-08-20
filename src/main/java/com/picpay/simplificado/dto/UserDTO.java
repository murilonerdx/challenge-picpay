package com.picpay.simplificado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "First Name cannot be null")
    private String firstName;

    @NotNull(message = "LastName Name cannot be null")
    private String lastName;

    @Size(min = 8, max = 18)
    private String password;
    @CPF
    private String suid;
}
