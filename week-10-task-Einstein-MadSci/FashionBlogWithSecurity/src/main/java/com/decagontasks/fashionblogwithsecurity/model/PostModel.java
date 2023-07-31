package com.decagontasks.fashionblogwithsecurity.model;

import com.decagontasks.fashionblogwithsecurity.dto.PostDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts_table")
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String post;

    private Long likes;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "post_user_fk"
            )
    )
//    @JsonBackReference
    private UserModel user;

    public PostModel (PostDTO postDTO){
        this.title = postDTO.getTitle();
        this.post = postDTO.getPost();
        this.likes = postDTO.getLikes();
        this.user = postDTO.getUser();
    }
}