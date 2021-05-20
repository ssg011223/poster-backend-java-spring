package com.codecool.poster.service;

import com.codecool.poster.model.Post;
import com.codecool.poster.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    private final int MAX_POST_MESSAGE_LENGTH = 250;
    
    public Collection<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(int id) {
        return postRepository.findById(id).orElse(null);
    }

    public boolean savePost(Post post) {
        if (!checkPostMessageLength(post.getMessage())) return false;
        post.setPostDate(LocalDateTime.now());
        postRepository.save(post);
        return true;
    }

    private boolean checkPostMessageLength(String message) {
        return message.length() <= MAX_POST_MESSAGE_LENGTH;
    }

}
