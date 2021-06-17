package com.codecool.poster.service;

import com.codecool.poster.model.Like;
import com.codecool.poster.model.key.LikeKey;
import com.codecool.poster.repository.LikeRepository;
import com.codecool.poster.security.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LikeService likeService;

    @Captor
    private ArgumentCaptor<Like> likeArgumentCaptor;

    @Captor
    private ArgumentCaptor<LikeKey> likeKeyArgumentCaptor;

    @BeforeEach
    public void jwtServiceSetup() {
        when(jwtService.getTokenFromRequest(any())).thenReturn("token");
        when(jwtService.parseIdFromTokenInfo("token")).thenReturn(1L);
    }

    @Test
    void shouldSaveLike() {
        likeService.saveLike(mock(HttpServletRequest.class), 2L);

        verify(likeRepository, times(1)).save(likeArgumentCaptor.capture());

        assertEquals(1L, likeArgumentCaptor.getValue().getPersonId());
        assertEquals(2L, likeArgumentCaptor.getValue().getPostId());
    }

    @Test
    void shouldDeleteLike() {
        likeService.deleteLike(mock(HttpServletRequest.class), 2L);

        verify(likeRepository, times(1)).deleteById(likeKeyArgumentCaptor.capture());

        assertEquals(1L, likeKeyArgumentCaptor.getValue().getPersonId());
        assertEquals(2L, likeKeyArgumentCaptor.getValue().getPostId());
    }

}