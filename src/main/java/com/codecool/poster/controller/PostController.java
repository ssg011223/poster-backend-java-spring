package com.codecool.poster.controller;

import com.codecool.poster.model.Media;
import com.codecool.poster.model.MediaTypeEnum;
import com.codecool.poster.model.Person;
import com.codecool.poster.model.Post;
import com.codecool.poster.service.MediaService;
import com.codecool.poster.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private MediaService mediaService;

    @GetMapping("/{id}")
    public Post getPostWithId(@PathVariable Integer id) {
        return postService.findById(id);
    }

    @PostMapping(value = "/add")
    public boolean savePost(@RequestParam String message,
                            @RequestParam int person_id,
                            @RequestParam("file") MultipartFile[] files) {

        boolean hasImage = false;
        boolean hasVideo = false;
        List<Media> media = new ArrayList<>(4);

        if (files.length > 0) {
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
                    case "image/jng":
                        hasImage = true;
                        break;
                    case "video/mp4":
                        hasVideo = true;
                        break;
                }
                if ((hasImage && hasVideo) || (!hasImage && !hasVideo) || (hasVideo && files.length > 1)) return false;
            }
            MediaTypeEnum mediaType = MediaTypeEnum.IMAGE;
            if (hasVideo) mediaType = MediaTypeEnum.VIDEO;
            Collection<String> routes = mediaService.submit(files);
            for (String route: routes) {
                Media tempMedia = Media.builder()
                        .mediaRoute(route)
                        .mediaType(mediaType)
                        .build();
                media.add(tempMedia);
            }
        }
        Post post = Post.builder()
                .message(message)
                .person(Person.builder()
                        .id(person_id)
                        .build())
                .hasImage(hasImage)
                .hasVideo(hasVideo)
                .imageCount(hasImage ? media.size() : 0)
                .build();
        return postService.savePost(post);
    }

}
