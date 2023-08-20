package com.picpay.simplificado.controller;

import com.picpay.simplificado.dto.BalanceDTO;
import com.picpay.simplificado.dto.BalanceResponseDTO;
import com.picpay.simplificado.dto.TransactionDTO;
import com.picpay.simplificado.model.TransactionModel;
import com.picpay.simplificado.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionController {
    final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionModel> makeTransaction(@RequestBody TransactionDTO transaction){
        return ResponseEntity.ok(transactionService.receiveTransferAndRollBackThrowSendEmail(transaction));
    }

    @PutMapping("/put-balance")
    public ResponseEntity<BalanceResponseDTO> putBalance(@RequestBody BalanceDTO balanceDTO){
        return ResponseEntity.ok(transactionService.putBalance(balanceDTO));
    }

    @GetMapping("/last-transactions/payer/{idSender}/payee/{idReceive}")
    public ResponseEntity<List<TransactionModel>> lastTransactions( @PathVariable Long idSender, @PathVariable Long idReceive, @RequestParam Double value){
        return ResponseEntity.ok(transactionService.lastTransactions(idSender, idReceive, value));
    }

    @PostMapping("/rollback/{idTransaction}")
    public ResponseEntity<TransactionModel> rollBack(@RequestBody TransactionDTO transaction, Long idTransaction){
        return ResponseEntity.ok(transactionService.rollbackTransfer(transaction.getPayer(), transaction.getPayee(), idTransaction));
    }
    @GetMapping("/sender/{id}")
    public ResponseEntity<List<TransactionModel>> transactionsSender(@PathVariable("id") Long idSender){
        return ResponseEntity.ok(transactionService.lastTransactionsSender(idSender));
    }

    @GetMapping("/receive/{id}")
    public ResponseEntity<List<TransactionModel>> transactionsReceive(@PathVariable("id") Long idReceive){
        return ResponseEntity.ok(transactionService.lastTransactionsReceive(idReceive));
    }
}
