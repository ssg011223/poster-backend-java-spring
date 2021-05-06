package com.codecool.poster.service;

import com.codecool.poster.model.Post;
import com.codecool.poster.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    Post findById(int id) {
        return postRepository.findById(id).orElse(null);
    }

}
