package com.picpay.simplificado.dto;

import com.picpay.simplificado.model.enums.BalanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceResponseDTO {
    private String email;
    private Double value;
    private BalanceType type;
}
