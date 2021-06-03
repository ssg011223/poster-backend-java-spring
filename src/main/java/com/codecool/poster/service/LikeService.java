package com.codecool.poster.service;

import com.codecool.poster.model.Like;
import com.codecool.poster.model.key.LikeKey;
import com.codecool.poster.repository.LikeRepository;
import com.codecool.poster.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private JwtService jwtService;

    public void saveLike(HttpServletRequest req, long postId) {
        String token = jwtService.getTokenFromRequest(req);
        long personId = jwtService.parseIdFromTokenInfo(token);
        Like like = new Like(postId, personId, LocalDateTime.now());
        likeRepository.save(like);
    }

    public void deleteLike(HttpServletRequest req, long postId) {
        String token = jwtService.getTokenFromRequest(req);
        long personId = jwtService.parseIdFromTokenInfo(token);
        likeRepository.deleteById(new LikeKey(personId, postId));
    }

    public Collection<Like> findAllByPostIdIn(Collection<Long> postIds) {
        return likeRepository.findAllByPostIdIn(postIds);
    }
}
