package com.decagontasks.fashionblogwithsecurity.repository;

import com.decagontasks.fashionblogwithsecurity.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findUserModelByUserName (String userName);
}
