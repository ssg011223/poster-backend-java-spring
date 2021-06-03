package com.codecool.poster.service;

import com.codecool.poster.model.Share;
import com.codecool.poster.model.key.ShareKey;
import com.codecool.poster.repository.ShareRepository;
import com.codecool.poster.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class ShareService {
    @Autowired
    private ShareRepository shareRepository;
    @Autowired
    private JwtService jwtService;

    public void saveShare(HttpServletRequest req, long postId) {
        String token = jwtService.getTokenFromRequest(req);
        long personId = jwtService.parseIdFromTokenInfo(token);
        Share share = new Share(postId, personId, LocalDateTime.now());
        shareRepository.save(share);
    }

    public void deleteShare(HttpServletRequest req, long postId) {
        String token = jwtService.getTokenFromRequest(req);
        long personId = jwtService.parseIdFromTokenInfo(token);
        shareRepository.deleteById(new ShareKey(postId, personId));
    }
}
