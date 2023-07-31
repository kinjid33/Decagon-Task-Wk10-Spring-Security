package com.decagontasks.fashionblogwithsecurity.service;

import com.decagontasks.fashionblogwithsecurity.dto.CommentDTO;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO, Long adminId, Long postId);
    String deleteComment(Long adminId, Long commentId);
}
