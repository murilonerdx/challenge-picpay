package com.picpay.simplificado.model;

import com.picpay.simplificado.model.enums.EmailType;
import jakarta.persistence.Column;
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
@Table(name = "tb_email")
public class EmailModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ownerRef;
    @Column(length = 255)
    private String emailFrom;
    @Column(length = 255)
    private String emailTo;
    @Column(length = 255)
    private String subject;
    @Column(length = 255)
    private String text;
    private LocalDateTime sendDateEmail;

    @Enumerated(EnumType.STRING)
    private EmailType statusEmail;
}