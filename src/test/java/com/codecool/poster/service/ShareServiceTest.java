package com.codecool.poster.service;

import com.codecool.poster.model.Share;
import com.codecool.poster.model.key.ShareKey;
import com.codecool.poster.repository.ShareRepository;
import com.codecool.poster.security.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShareServiceTest {

    @Mock
    private ShareRepository shareRepository;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private ShareService shareService;

    @Captor
    private ArgumentCaptor<Share> shareArgumentCaptor;

    @Captor
    private ArgumentCaptor<ShareKey> shareKeyArgumentCaptor;

    @BeforeEach
    public void jwtServiceSetup() {
        when(jwtService.getTokenFromRequest(any())).thenReturn("token");
        when(jwtService.parseIdFromTokenInfo("token")).thenReturn(1L);
    }

    @Test
    void shouldSaveLike() {
        shareService.saveShare(mock(HttpServletRequest.class), 2L);

        verify(shareRepository, times(1)).save(shareArgumentCaptor.capture());

        assertEquals(1L, shareArgumentCaptor.getValue().getPersonId());
        assertEquals(2L, shareArgumentCaptor.getValue().getPostId());
    }

    @Test
    void shouldDeleteLike() {
        shareService.deleteShare(mock(HttpServletRequest.class), 2L);

        verify(shareRepository, times(1)).deleteById(shareKeyArgumentCaptor.capture());

        assertEquals(1L, shareKeyArgumentCaptor.getValue().getPersonId());
        assertEquals(2L, shareKeyArgumentCaptor.getValue().getPostId());
    }
}