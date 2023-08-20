package com.picpay.simplificado.service;

import com.picpay.simplificado.dto.UserDTO;
import com.picpay.simplificado.dto.UserRequestDTO;
import com.picpay.simplificado.dto.UserResponseDTO;
import com.picpay.simplificado.dto.WalletDTO;
import com.picpay.simplificado.model.UserModel;
import com.picpay.simplificado.model.WalletModel;
import com.picpay.simplificado.model.enums.UserType;
import com.picpay.simplificado.repository.UserRepository;
import com.picpay.simplificado.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    final UserRepository repository;
    final WalletRepository walletRepository;

    @Autowired
    public UserService(UserRepository repository, WalletRepository walletRepository) {
        this.repository = repository;
        this.walletRepository = walletRepository;
    }

    public UserModel create(UserDTO userDTO) {
        WalletModel wallet = new WalletModel(null, 0.00, false, null);
        UserModel user = new UserModel(
                null,
                userDTO.getEmail(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getPassword(),
                wallet,
                userDTO.getSuid(),
                UserType.ORDINARY
        );

        return repository.save(user);
    }

    public WalletDTO getWallet(UserRequestDTO user) {
        return repository.findByEmailAndSuidAndPassword(user.getEmail(), user.getSuid(), user.getPassword()).getWallet().toDTO();
    }

    public UserModel findById(Long id){
        return repository.findById(id).get();
    }

    public UserResponseDTO update(Long id, UserDTO userDTO){
        UserModel userModel = repository.findById(id).get();

        userModel.setEmail(userDTO.getEmail());
        userModel.setFirstName(userDTO.getFirstName());
        userModel.setLastName(userDTO.getLastName());
        return repository.save(userModel).toDTO();
    }

    public void delete(Long id){
        UserModel userModel = repository.findById(id).get();
        repository.delete(userModel);
    }

}
