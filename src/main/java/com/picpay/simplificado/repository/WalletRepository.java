package com.picpay.simplificado.repository;

import com.picpay.simplificado.model.WalletModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletModel, Long> {
}
