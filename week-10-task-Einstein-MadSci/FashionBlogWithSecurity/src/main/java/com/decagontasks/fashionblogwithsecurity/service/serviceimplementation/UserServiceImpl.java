package com.decagontasks.fashionblogwithsecurity.service.serviceimplementation;

import com.decagontasks.fashionblogwithsecurity.dto.UserDTO;
import com.decagontasks.fashionblogwithsecurity.enums.Role;
import com.decagontasks.fashionblogwithsecurity.model.UserModel;
import com.decagontasks.fashionblogwithsecurity.repository.UserRepo;
import com.decagontasks.fashionblogwithsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
    @Autowired
    public UserServiceImpl(UserRepo userRepo){
        this.userRepo = userRepo;
    }

//    @Override
//    public String saveUser(UserDTO userDTO) {
//        UserModel users = new UserModel(userDTO);
//        try {
//            userRepo.save(users);
//        }catch(Exception e){
//            throw new RuntimeException("Username is already taken");
//        }
//        return "user registration successful";
//    }
//
//    @Override
//    public UserDTO logInUser(UserDTO userDTO) {
//        UserModel users = userRepo.findUserModelByUserName(userDTO.getUserName())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        if(users.getPassword().equals(userDTO.getPassword())){
//            if(users.isBlocked()){
//                throw new RuntimeException("You can not log in because you are blocked");
//            }
//            if(users.isDeleted()){
//                throw new RuntimeException("Account has been deleted");
//            }
//            return userEntityToDTO(users);
//        }
//        throw new RuntimeException("password is incorrect");
//    }

    @Override
    public UserDTO blockUser(Long adminId, Long userIdToBlock){
        UserModel adminUser = userRepo.findById(adminId).orElseThrow(
                () -> new NullPointerException("No user with id " + adminId));
        UserModel userToBlock = userRepo.findById(userIdToBlock).orElseThrow(
                () -> new NullPointerException("No user with id " + userIdToBlock));
        if(!adminUser.getRole().equals(Role.ADMIN)){
            throw new RuntimeException("You can not delete a user");
        }
        userToBlock.setBlocked(!userToBlock.isBlocked());
        userRepo.save(userToBlock);
        return userEntityToDTO(userToBlock);
    }

    @Override
    public String deleteUser(Long adminId, Long userIdToDelete) {
        UserModel adminUser = userRepo.findById(adminId).orElseThrow(
                () -> new NullPointerException("No user with id " + adminId));
        UserModel userToDelete = userRepo.findById(userIdToDelete).orElseThrow(
                () -> new NullPointerException("No user with id " + userIdToDelete));
        if(!adminUser.getRole().equals(Role.ADMIN)){
            throw new RuntimeException("You can not delete a user");
        }
        userToDelete.setDeleted(!userToDelete.isDeleted());
        userRepo.save(userToDelete);
        return "User deleted!";
    }

    @Override
    public List<UserDTO> findAll(){
        List<UserModel> allUsers = userRepo.findAll();
        return allUsers.stream().map(this::userEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUserDetails(Long adminId, Long idToUpdate, Map<String, Object> userDetailsUpdate) {
        UserModel adminUser = userRepo.findById(adminId).orElseThrow(
                () -> new NullPointerException("No user with id " + adminId));
        if(!adminUser.getRole().equals(Role.ADMIN)){
            throw new RuntimeException("You can not update a user's details");
        }
        Optional<UserModel> userForUpdate = userRepo.findById(idToUpdate);
        userForUpdate.ifPresent(userModel -> userDetailsUpdate.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(UserModel.class, key);
            ReflectionUtils.makeAccessible((field));
            ReflectionUtils.setField(field, userForUpdate.get(), value);
        }));
        UserModel updatedUser = userRepo.save(userForUpdate.get());
        return userEntityToDTO(updatedUser);
    }

    public UserDTO userEntityToDTO(UserModel userModel){
        return UserDTO.builder()
                .id(userModel.getId())
                .userName(userModel.getUsername())
                .isBlocked(userModel.isBlocked())
                .isDeleted(userModel.isDeleted())
                .role(userModel.getRole())
                .password(userModel.getPassword())
                .build();
    }
}