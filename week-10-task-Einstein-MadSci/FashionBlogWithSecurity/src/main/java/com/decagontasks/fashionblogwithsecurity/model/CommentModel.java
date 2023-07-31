package com.decagontasks.fashionblogwithsecurity.model;

import com.decagontasks.fashionblogwithsecurity.dto.CommentDTO;
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
@Table(name = "comments_table")
public class CommentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "comment_user_fk"
            )
    )
//    @JsonBackReference
    private UserModel user;

    @ManyToOne
    @JoinColumn(
            name = "post_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "comment_post_fk"
            )
    )
//    @JsonBackReference
    private PostModel post;

    public CommentModel (CommentDTO commentDTO){
        this.comment = commentDTO.getComment();
    }
}