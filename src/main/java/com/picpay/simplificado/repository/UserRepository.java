package com.picpay.simplificado.repository;

import com.picpay.simplificado.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmailAndSuidAndPassword(String email, String suid, String password);
    UserModel findByEmailAndPassword(String email, String  password);
}
