package com.codecool.poster.service;

import com.codecool.poster.model.Like;
import com.codecool.poster.model.Media;
import com.codecool.poster.model.Share;
import com.codecool.poster.model.post.Post;
import com.codecool.poster.model.post.SendPost;
import com.codecool.poster.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private ShareService shareService;
    private final int MAX_POST_MESSAGE_LENGTH = 250;
    
    public Collection<Post> findAll() {
        return postRepository.findAll();
    }

    public Collection<Post> findAllByPersonIdIn(Collection<Long> personId){
        return postRepository.findAllByPersonIdIn(personId);
    }

    public Post findById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public boolean savePost(Post post) {
        if (!checkPostMessageLength(post.getMessage())) return false;
        post.setPostDate(LocalDateTime.now());
        postRepository.save(post);
        return true;
    }

    public Collection<SendPost> getPostsWithMedia(Collection<Post> posts) {
        Collection<Long> postIds = posts.stream().map(Post::getId).collect(Collectors.toList());
        Collection<Media> media = mediaService.findAllByPostIdIn(postIds);

        Collection<SendPost> result = new ArrayList<>();

        for (Post post: posts) {
            Collection<Media> tempMedia = media.stream().filter(m -> m.getPost().equals(post)).collect(Collectors.toList());
            result.add(new SendPost(post, tempMedia));
        }

        return result;
    }

    public Collection<Post> getPostsWithInteractions(Collection<Post> posts) {
        Collection<Long> postIds = posts.stream().map(Post::getId).collect(Collectors.toList());
        Collection<Like> likes = likeService.findAllByPostIdIn(postIds);
        Collection<Share> shares = shareService.findAllByPersonIdIn(postIds);

        Collection<Long> likePostIds = likes.stream().map(Like::getPostId).collect(Collectors.toList());
        Collection<Long> sharePostIds = shares.stream().map(Share::getPostId).collect(Collectors.toList());

        Collection<Post> result = new ArrayList<>();

        for (Post post: posts) {
            long id = post.getId();
            post.setLiked(likePostIds.contains(id));
            post.setShared(sharePostIds.contains(id));
            result.add(post);
        }

        return result;
    }

    private boolean checkPostMessageLength(String message) {
        return message.length() <= MAX_POST_MESSAGE_LENGTH;
    }

}
