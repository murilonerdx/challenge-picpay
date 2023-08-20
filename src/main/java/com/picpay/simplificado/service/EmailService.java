package com.picpay.simplificado.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.picpay.simplificado.dto.TransactionDTO;
import com.picpay.simplificado.model.EmailModel;
import com.picpay.simplificado.model.UserModel;
import com.picpay.simplificado.model.enums.EmailType;
import com.picpay.simplificado.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailService{

    final EmailRepository repository;

    private JavaMailSender emailSender;

    final String ownerRef = "PICPAY";
    final String emailFrom = "picpay@payments.com.br";
    final String subject = "Picpay transfer";

    public EmailModel buildEmail(UserModel userModelReceive, UserModel userModelSend, TransactionDTO transactionDTO){
        return new EmailModel(
                null,
                ownerRef,
                emailFrom,
                userModelReceive.getEmail(),
                subject,
                String.format("You received a transfer from %s of the %s", transactionDTO.getValue(), userModelSend.getFirstName()),
                LocalDateTime.now(),
                null
        );
    }

    public EmailModel sendEmail(UserModel userModelReceive, UserModel userModelSend, TransactionDTO transactionDTO) {
        EmailModel emailModel = buildEmail(userModelReceive, userModelSend, transactionDTO);
        try{
            emailModel.setStatusEmail(EmailType.SENT);
        }catch(MailException e){
            emailModel.setStatusEmail(EmailType.ERROR);
        }

        return repository.save(emailModel);
    }

    public Page<EmailModel> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<EmailModel> findById(Long emailId) {
        return repository.findById(emailId);
    }
}
