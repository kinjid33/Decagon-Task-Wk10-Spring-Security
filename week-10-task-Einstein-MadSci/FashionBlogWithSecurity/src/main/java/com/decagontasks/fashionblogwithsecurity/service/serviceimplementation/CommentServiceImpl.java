package com.decagontasks.fashionblogwithsecurity.service.serviceimplementation;

import com.decagontasks.fashionblogwithsecurity.dto.CommentDTO;
import com.decagontasks.fashionblogwithsecurity.enums.Role;
import com.decagontasks.fashionblogwithsecurity.model.CommentModel;
import com.decagontasks.fashionblogwithsecurity.model.PostModel;
import com.decagontasks.fashionblogwithsecurity.model.UserModel;
import com.decagontasks.fashionblogwithsecurity.repository.CommentRepo;
import com.decagontasks.fashionblogwithsecurity.repository.PostRepo;
import com.decagontasks.fashionblogwithsecurity.repository.UserRepo;
import com.decagontasks.fashionblogwithsecurity.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private UserRepo userRepo;
    private PostRepo postRepo;
    private CommentRepo commentRepo;

    public CommentServiceImpl(UserRepo userRepo, PostRepo postRepo, CommentRepo commentRepo){
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Long adminId, Long postId) {
        UserModel adminUser = userRepo.findById(adminId).orElseThrow(
                () -> new NullPointerException("No user with id " + adminId));
        PostModel postToCommentOn = postRepo.findById(postId).get();
        CommentModel comment = new CommentModel(commentDTO);
        comment.setUser(adminUser);
        comment.setPost(postToCommentOn);
        commentRepo.save(comment);
        return commentEntityToDTO(comment);
    }

    @Override
    public String deleteComment(Long adminId, Long commentId){
        UserModel adminUser = userRepo.findById(adminId).orElseThrow(
                () -> new NullPointerException("No user with id " + adminId));
        CommentModel comment = commentRepo.findById(commentId).orElseThrow(
                () -> new NullPointerException("No comment with id " + commentId));
        if(adminUser.getRole().equals(Role.ADMIN)){
            return deleteLogic(commentId);
        }
        CommentModel owner = commentRepo.findCommentModelByUser_Id(adminId).orElseThrow(
                () -> new NullPointerException("No user with id " + adminId)
        );
        if(!(owner == comment)){
            throw new RuntimeException("You can not delete another user's comment");
        }
        return deleteLogic(commentId);
    }

    public CommentDTO commentEntityToDTO(CommentModel commentModel){
        return CommentDTO.builder()
                .id(commentModel.getId())
                .comment(commentModel.getComment())
                .user(commentModel.getUser())
                .post(commentModel.getPost())
                .build();
    }

    public String deleteLogic(Long commentId){
        try {
            commentRepo.deleteById(commentId);
        } catch (Exception ex) {
            return "Post not found";
        }
        return "Post deleted";
    }
}