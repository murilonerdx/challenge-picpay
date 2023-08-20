package com.picpay.simplificado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDTO {
    private String suid;
    private String email;
    private String password;
    private Double value;
}
