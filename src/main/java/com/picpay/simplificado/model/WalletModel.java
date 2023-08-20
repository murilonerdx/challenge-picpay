package com.picpay.simplificado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.picpay.simplificado.dto.WalletDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_wallet")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WalletModel  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double balance;
    private boolean wallerBlock = false;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,mappedBy="wallet")
    private UserModel user;

    public void changeBalance(Double balance){
        this.balance = this.balance + balance;
    }

    public void rollBackBalance(Double balance){
        if(this.balance >= balance){
            this.balance = this.balance - balance;

            return;
        }

        this.balance -= balance;
    }

    public WalletDTO toDTO(){
        return new WalletDTO(
                this.balance
        );
    }

    public void negativeBalance(Double balance){
        this.balance = this.balance - balance;
    }
}
