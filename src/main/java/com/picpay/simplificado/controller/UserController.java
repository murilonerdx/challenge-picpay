package com.picpay.simplificado.controller;

import com.picpay.simplificado.dto.UserDTO;
import com.picpay.simplificado.dto.UserRequestDTO;
import com.picpay.simplificado.dto.UserResponseDTO;
import com.picpay.simplificado.dto.WalletDTO;
import com.picpay.simplificado.model.UserModel;
import com.picpay.simplificado.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserModel> create(@Valid @RequestBody UserDTO user){
        return ResponseEntity.ok(userService.create(user));
    }

    @PostMapping("/wallet")
    public ResponseEntity<WalletDTO> getWallet(@RequestBody UserRequestDTO user){
        return ResponseEntity.ok(userService.getWallet(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id).toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@RequestBody UserDTO user, @PathVariable("id") Long id){
        return ResponseEntity.ok(userService.update(id, user));
    }
}
