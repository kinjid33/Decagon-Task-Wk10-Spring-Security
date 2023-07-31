package com.decagontasks.fashionblogwithsecurity.dto;

import com.decagontasks.fashionblogwithsecurity.model.UserModel;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;

    private String title;

    private String post;

    private Long likes;

    @ManyToOne
    private UserModel user;
}