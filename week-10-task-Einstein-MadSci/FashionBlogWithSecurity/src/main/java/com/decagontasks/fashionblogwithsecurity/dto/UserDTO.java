package com.decagontasks.fashionblogwithsecurity.dto;

import com.decagontasks.fashionblogwithsecurity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String userName;
    private String password;
    private Role role;
    private boolean isBlocked;
    private boolean isDeleted;
}