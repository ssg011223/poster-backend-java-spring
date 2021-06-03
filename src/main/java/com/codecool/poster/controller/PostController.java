package com.codecool.poster.controller;

import com.codecool.poster.model.*;
import com.codecool.poster.model.follow.FollowWithFollowed;
import com.codecool.poster.model.post.Post;
import com.codecool.poster.model.post.SendPost;
import com.codecool.poster.repository.FollowRepository;
import com.codecool.poster.security.jwt.JwtService;
import com.codecool.poster.service.MediaService;
import com.codecool.poster.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private FollowRepository followRepository;


    @GetMapping
    public ResponseEntity getAllPostsPersonalized(HttpServletRequest req, HttpServletResponse res) {
        String token = jwtService.getTokenFromRequest(req);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        long id = jwtService.parseIdFromTokenInfo(token);
        List<FollowWithFollowed> follows = followRepository.findAllByFollowerPersonId(id);
        Collection<Post> posts = postService.findAllByPersonIdIn(follows
                .stream()
                .map(FollowWithFollowed::getFollowedPerson)
                .map(Person::getId)
                .collect(Collectors.toList()));

        Collection<Post> interactionPosts = postService.getPostsWithInteractions(posts);

        Collection<SendPost> mediaPosts = postService.getPostsWithMedia(interactionPosts);

        Map<Object, Object> postsMap = new HashMap<>();
        postsMap.put("posts", mediaPosts);

        return ResponseEntity.ok(postsMap);
    }

    @GetMapping("/{id}")
    public Post getPostWithId(@PathVariable long id) {
        return postService.findById(id);
    }

    @PostMapping(value = "/add")
    public boolean savePost(HttpServletRequest req,
                            @RequestParam String message,
                            @RequestParam(value = "files", required = false) MultipartFile[] files) {

        String token = jwtService.getTokenFromRequest(req);
        long personId = jwtService.parseIdFromTokenInfo(token);

        boolean hasImage = false;
        boolean hasVideo = false;
        List<Media> media = new ArrayList<>(4);
        Post.PostBuilder postBuilder = Post
                .builder()
                .message(message)
                .person(Person.builder()
                        .id(personId)
                        .build());

        Post finalPost = postBuilder.build();

        if (!(files == null)) {
            for (MultipartFile file: files) {
                switch (file.getContentType()) {
                    case "image/png":
                        hasImage = true;
                        break;
                    case "image/gif":
                        hasImage = true;
                        break;
                    case "image/jpeg":
                        hasImage = true;
                        break;
                    case "image/jpg":
                        hasImage = true;
                        break;
                    case "video/mp4":
                        hasVideo = true;
                        break;
                }
                if ((hasImage && hasVideo) || (!hasImage && !hasVideo) || (hasVideo && files.length > 1) || (hasImage && files.length > 4)) return false;
            }
                finalPost = postBuilder
                    .hasImage(hasImage)
                    .hasVideo(hasVideo)
                    .imageCount(hasImage ? files.length : 0)
                    .build();

            MediaTypeEnum mediaType = MediaTypeEnum.IMAGE;
            if (hasVideo) mediaType = MediaTypeEnum.VIDEO;
            Collection<String> routes = mediaService.submit(files);
            for (String route: routes) {
                Media tempMedia = Media.builder()
                        .post(finalPost)
                        .mediaRoute(route)
                        .mediaType(mediaType)
                        .build();
                media.add(tempMedia);
            }
        }

        postService.savePost(finalPost);
        mediaService.saveAll(media);
        return true;
    }

}
