package com.decagontasks.fashionblogwithsecurity.repository;

import com.decagontasks.fashionblogwithsecurity.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepo extends JpaRepository<CommentModel, Long> {
    Optional<CommentModel> findCommentModelByUser_Id(Long userId);
}
