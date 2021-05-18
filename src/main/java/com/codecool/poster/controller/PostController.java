package com.codecool.poster.controller;

import com.codecool.poster.model.Post;
import com.codecool.poster.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    public Post getPostWithId(@PathVariable Integer id) {
        return postService.findById(id);
    }

    @PostMapping("/add")
    public void savePost(@RequestBody Post post) {
        postService.savePost(post);
    }

}
