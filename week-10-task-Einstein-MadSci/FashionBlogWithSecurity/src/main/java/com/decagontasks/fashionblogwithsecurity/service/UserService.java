package com.decagontasks.fashionblogwithsecurity.service;

import com.decagontasks.fashionblogwithsecurity.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface UserService {
//    String saveUser(UserDTO user);

    String deleteUser(Long adminId, Long userIdtoDelete);

    List<UserDTO> findAll();

//    UserDTO logInUser(UserDTO userDTO);

    UserDTO updateUserDetails(Long adminId, Long id, Map<String, Object> userDetailsUpdate);

    UserDTO blockUser(Long adminId, Long userIdToBlock);
}