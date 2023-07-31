package com.decagontasks.fashionblogwithsecurity.controller;

import com.decagontasks.fashionblogwithsecurity.dto.CommentDTO;
import com.decagontasks.fashionblogwithsecurity.dto.UserDTO;
import com.decagontasks.fashionblogwithsecurity.service.CommentService;
import com.decagontasks.fashionblogwithsecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private CommentService commentService;
    @Autowired
    public UserController(UserService userService, CommentService commentService){
        this.userService = userService;
        this.commentService = commentService;
    }



    @PostMapping("/write-comment/{id}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable("id") Long id, @RequestBody CommentDTO commentDTO, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        Long adminId = (Long) session.getAttribute("user_id");
        return ResponseEntity.ok(commentService.createComment(commentDTO, adminId, id));
    }

    @DeleteMapping("/delete-comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long commentId, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("user_id");
        return ResponseEntity.ok(commentService.deleteComment(userId, commentId));
    }
}
