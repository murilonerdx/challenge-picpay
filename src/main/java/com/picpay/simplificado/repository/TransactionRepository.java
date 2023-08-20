package com.picpay.simplificado.repository;

import com.picpay.simplificado.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
    List<TransactionModel> findByIdSender(Long idSender);
    List<TransactionModel> findByIdReceive(Long idReceive);
    List<TransactionModel> findByIdSenderAndIdReceiveAndValue(Long idSender, Long idReceive, Double value);
    TransactionModel findFirstById(Long id);
}
