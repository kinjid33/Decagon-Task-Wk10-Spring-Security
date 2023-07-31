package com.decagontasks.fashionblogwithsecurity.controller;

import com.decagontasks.fashionblogwithsecurity.dto.PostDTO;
import com.decagontasks.fashionblogwithsecurity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
//@RequiredArgsConstructor
public class PostController {
    private PostService postService;

    @Autowired
    public PostController (PostService postService){
        this.postService = postService;
    }

    @GetMapping("/all-posts")
    public ResponseEntity<List<PostDTO>> viewAllPosts(){
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/single-post/{id}")
    public ResponseEntity<PostDTO> viewSinglePost(@PathVariable("id") Long id){
        return ResponseEntity.ok(postService.findById(id));
    }
}
