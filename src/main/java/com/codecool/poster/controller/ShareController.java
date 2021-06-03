package com.codecool.poster.controller;

import com.codecool.poster.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/share")
public class ShareController {
    @Autowired
    private ShareService shareService;

    @PostMapping("/{postId}")
    public void share(HttpServletRequest req,
                      @PathVariable("postId") long postId) {
        shareService.saveShare(req, postId);
    }

    @DeleteMapping("/{postId}")
    public void unShare(HttpServletRequest req,
                      @PathVariable("postId") long postId) {
        shareService.deleteShare(req, postId);
    }

}
