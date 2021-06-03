package com.codecool.poster.service;

import com.codecool.poster.model.Like;
import com.codecool.poster.model.Person;
import com.codecool.poster.model.post.Post;
import com.codecool.poster.repository.LikeRepository;
import com.codecool.poster.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private JwtService jwtService;

    public void saveLike(HttpServletRequest req, long postId) {
        long id = jwtService.parseIdFromTokenInfo(jwtService.getTokenFromRequest(req));
        Person person = Person.builder().id(id).build();
        Post post = Post.builder().id(postId).build();
        Like like = new Like(postId, id, LocalDateTime.now());
        likeRepository.save(like);
    }
}
