package com.codecool.poster.controller;

import com.codecool.poster.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/{postId}")
    public void like(HttpServletRequest req,
                     @PathVariable(name = "postId") long postId) {
        likeService.saveLike(req, postId);
    }

    @DeleteMapping("/{postId}")
    public void unLike(HttpServletRequest req,
                     @PathVariable(value = "postId") long postId) {
        likeService.deleteLike(req,postId);
    }
}
