package com.codecool.poster.controller;

import com.codecool.poster.model.Post;
import com.codecool.poster.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping("/{id}")
    Post getPostWithId(@PathVariable Integer id) {
        return postService.findById(id);
    }

}
