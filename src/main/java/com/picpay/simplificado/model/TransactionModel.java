package com.picpay.simplificado.model;

import com.picpay.simplificado.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_transaction")
public class TransactionModel  implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idSender;
    private Long idReceive;
    private Double value;
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
