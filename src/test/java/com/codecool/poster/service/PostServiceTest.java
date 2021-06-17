package com.codecool.poster.service;

import com.codecool.poster.model.Like;
import com.codecool.poster.model.Media;
import com.codecool.poster.model.Share;
import com.codecool.poster.model.post.Post;
import com.codecool.poster.model.post.SendPost;
import com.codecool.poster.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private MediaService mediaService;
    @Mock
    private LikeService likeService;
    @Mock
    private ShareService shareService;

    @InjectMocks
    private PostService postService;

    @Test
    void savePostShouldReturnTrueMaxLength() {
        Post post = new Post();
        post.setMessage("{!dD8*wSk9y.,Kf$-!Cq_);*W-F=$yBj6:dZWn.4#4.(cE*VS_g9vAvYuVXQi*rue?Gfm)-nejXnB!#_GwEFgrNFqquFiypgxj)[9zKTrT[rG43qM;?K8GdGHJ$#v$J##gZ,g[__5DRnGQ4abr9Hwi$v,5ty:;)H-LL%WbgGb!Rj!QmTTFQBk4+Ug]mM_tn@ErZ#pwT[eb_wB5T&3b6JBTATJtzm#!u{;PjS?QDg]#Y]r5HCL@@]g:(md=");

        assertTrue(postService.savePost(post));
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void savePostShouldReturnFalseMoreThanMaxLength() {
        Post post = new Post();
        post.setMessage("{!dD8*wSk9y.,Kf$-!Cq_);*W-F=$yBj6:dZWn.4#4.(cE*VS_g9vAvYuVXQi*rue?Gfm)-nejXnB!#_GwEFgrNFqquFiypgxj)[9zKTrT[rG43qM;?K8GdGHJ$#v$J##gZ,g[__5DRnGQ4abr9Hwi$v,5ty:;)H-LL%WbgGb!Rj!QmTTFQBk4+Ug]mM_tn@ErZ#pwT[eb_wB5T&3b6JBTATJtzm#!u{;PjS?QDg]#Y]r5HCL@@]g:(md4z=");

        assertFalse(postService.savePost(post));
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void getPostsWithMediaShouldReturnCollectionOfSendPosts() {
        Media media = new Media();
        Post post = new Post();
        Collection<Media> mediaCollection = new ArrayList<>();
        media.setPost(post);
        mediaCollection.add(media);
        Collection<Post> postCollection = new ArrayList<>();
        postCollection.add(post);

        when(mediaService.findAllByPostIdIn(any())).thenReturn(mediaCollection);

        assertEquals(List.of(new SendPost(post, mediaCollection)), postService.getPostsWithMedia(postCollection));
    }

    @Test
    void getPostsWithInteractionsShouldAddSetInteractionBooleansToTrue() {
        Post post = new Post();
        post.setId(1L);
        Like like = new Like();
        like.setPostId(1L);
        Share share = new Share();
        share.setPostId(1L);

        when(likeService.findAllByPostIdIn(any())).thenReturn(List.of(like));
        when(shareService.findAllByPersonIdIn(any())).thenReturn(List.of(share));

        Collection<Post> posts = postService.getPostsWithInteractions(List.of(post));
        Post res = null;
        for (Post post1: posts) res = post1;

        assert res != null;
        assertTrue(res.isLiked());
        assertTrue(res.isShared());

    }

    @Test
    void getPostsWithInteractionsShouldAddSetInteractionBooleansToFalse() {
        Post post = new Post();
        post.setId(1L);
        Like like = new Like();
        like.setPostId(2L);
        Share share = new Share();
        share.setPostId(2L);

        when(likeService.findAllByPostIdIn(any())).thenReturn(List.of(like));
        when(shareService.findAllByPersonIdIn(any())).thenReturn(List.of(share));

        Collection<Post> posts = postService.getPostsWithInteractions(List.of(post));
        Post res = null;
        for (Post post1: posts) res = post1;

        assert res != null;
        assertFalse(res.isLiked());
        assertFalse(res.isShared());

    }
}