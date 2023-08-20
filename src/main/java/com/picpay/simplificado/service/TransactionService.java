package com.picpay.simplificado.service;

import com.picpay.simplificado.dto.*;
import com.picpay.simplificado.exception.ResourceNotFoundException;
import com.picpay.simplificado.model.TransactionModel;
import com.picpay.simplificado.model.UserModel;
import com.picpay.simplificado.model.enums.BalanceType;
import com.picpay.simplificado.model.enums.TransactionType;
import com.picpay.simplificado.model.enums.UserType;
import com.picpay.simplificado.repository.TransactionRepository;
import com.picpay.simplificado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionService {

    public final String authorizationUrl = "https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6";
    public final String messageResponse = "Autorizado";

    private final UserRepository repository;
    private final RestTemplate restTemplate;
    private final TransactionRepository transactionRepository;

    final EmailService emailService;

    public Optional<UserModel> haveBalance(Double balance, Long id) {
        return repository.findById(id)
                .filter(userModel ->
                        userModel.getWallet()
                                .getBalance() >= balance);
    }

    public TransactionModel receiveTransferAndRollBackThrowSendEmail(TransactionDTO transactionDTO) {
        return receiveTransfer(transactionDTO);
    }

    public TransactionModel rollBackTransaction(Long idSender, Long idReceive, Long idTransaction){
        return rollbackTransfer(idSender,idReceive, idTransaction);
    }

    public TransactionModel receiveTransfer(TransactionDTO transactionDTO) {
        Optional<UserModel> userSend = haveBalance(transactionDTO.getValue(), transactionDTO.getPayer());
        Optional<UserModel> userReceive = haveBalance(0.00, transactionDTO.getPayee());

        try {
            if (userSend.isPresent() && authorizationTransation()
                    && !userSend.get().getWallet().isWallerBlock()
                    && !userReceive.get().getWallet().isWallerBlock()
            ) {
                if (userReceive.filter(userModel -> userModel.getUserType() == UserType.ORDINARY).isPresent()) {
                    userReceive.get().getWallet().changeBalance(transactionDTO.getValue());

                    userSend.ifPresent(userModel ->
                            userModel.getWallet().negativeBalance(transactionDTO.getValue())
                    );

                    repository.save(userSend.orElseThrow(IllegalAccessError::new));
                    repository.save(userReceive.orElseThrow(IllegalAccessError::new));
                    TransactionModel transactionModel = transactionRepository.save(new TransactionModel(null,
                            userSend.get().getId(),
                            userReceive.get().getId(),
                            transactionDTO.getValue(),
                            LocalDateTime.now(), TransactionType.SUCCESS
                    ));
                    emailService.sendEmail(userReceive.get(), userSend.get(), transactionDTO);

                    return transactionModel;
                }

            }
            throw new IllegalAccessError();
        } catch (IllegalAccessError e) {
            validWallet(userSend.get(), userReceive.get(), true);
            return null;
        }
    }

    public void validWallet(UserModel userModelSend, UserModel userModelReceive, Boolean valid){
        userModelSend.getWallet().setWallerBlock(valid);
        userModelReceive.getWallet().setWallerBlock(valid);

        repository.saveAll(List.of(userModelSend, userModelReceive));
    }

    public TransactionModel rollbackTransfer(Long idSender, Long idReceive, Long idTransaction) {
        UserModel userSend = haveBalance(0.00, idSender).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        UserModel userReceive = haveBalance(0.00, idReceive).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        TransactionModel transactionModel = transactionRepository.findById(idTransaction).get();

        if (transactionModel.getTransactionType().equals(TransactionType.SUCCESS)) {
            userReceive.getWallet().rollBackBalance(transactionModel.getValue());
            userSend.getWallet().changeBalance(transactionModel.getValue());

            repository.saveAll(List.of(userReceive, userSend));
        }


        transactionModel.setTransactionType(TransactionType.ROLLBACK);
        transactionRepository.save(transactionModel);
        validWallet(userSend, userReceive, false);

        return transactionModel;
    }

    public BalanceResponseDTO putBalance(BalanceDTO balance){
        UserModel userModel = repository.findByEmailAndPassword(balance.getEmail(), balance.getPassword());

        userModel.getWallet().changeBalance(balance.getValue());
        try{
            repository.save(userModel);
            return new BalanceResponseDTO(userModel.getEmail(), balance.getValue(), BalanceType.VALUE_SENT);
        }catch(Exception e){
            return new BalanceResponseDTO(userModel.getEmail(), balance.getValue(), BalanceType.VALUE_ERROR);
        }

    }

    public List<TransactionModel> lastTransactionsSender(Long userSendId) {
        return transactionRepository
                .findByIdSender(userSendId);
    }

    public List<TransactionModel> lastTransactionsReceive(Long userReceiveId) {
        return transactionRepository
                .findByIdReceive(userReceiveId);
    }

    public boolean authorizationTransation() {
        ResponseEntity<TransactionResponseDTO> getMessage =
                restTemplate.getForEntity(authorizationUrl, TransactionResponseDTO.class);

        return getMessage.getStatusCode().equals(HttpStatus.OK) && Objects.requireNonNull(getMessage.getBody()).getMessage().equals(messageResponse);
    }

    public List<TransactionModel> lastTransactions(Long idSender, Long idReceive, Double value) {
        UserModel userModelSend = haveBalance(0.00, idSender).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        UserModel userModelReceive = haveBalance(0.00, idReceive).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        return transactionRepository
                .findByIdSenderAndIdReceiveAndValue(userModelSend.getId(), userModelReceive.getId(), value);
    }
}
