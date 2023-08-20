package com.picpay.simplificado.model;

import com.picpay.simplificado.dto.UserDTO;
import com.picpay.simplificado.dto.UserResponseDTO;
import com.picpay.simplificado.model.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class UserModel  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="wallet_id")
    private WalletModel wallet;

    @Column(name = "suid", unique = true)
    private String suid;

    @Column(name = "user_type")
    private UserType userType;

    public UserResponseDTO toDTO() {
        return new UserResponseDTO(
                this.email,
                this.firstName,
                this.lastName
        );
    }
}
