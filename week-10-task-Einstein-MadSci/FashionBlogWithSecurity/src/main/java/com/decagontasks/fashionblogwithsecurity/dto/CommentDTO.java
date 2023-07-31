package com.decagontasks.fashionblogwithsecurity.dto;

import com.decagontasks.fashionblogwithsecurity.model.PostModel;
import com.decagontasks.fashionblogwithsecurity.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String comment;
    private UserModel user;
    private PostModel post;
}