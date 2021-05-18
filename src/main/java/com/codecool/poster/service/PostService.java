package com.codecool.poster.service;

import com.codecool.poster.model.Post;
import com.codecool.poster.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//TODO: Make Pageable for limit offset requests
@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public Post findById(int id) {
        return postRepository.findById(id).orElse(null);
    }

    public void savePost(Post post) {
        post.setPostDate(LocalDateTime.now());
        postRepository.save(post);
    }

}
