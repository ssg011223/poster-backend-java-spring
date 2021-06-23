package com.codecool.poster.controller;

import com.codecool.poster.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/media")
@CrossOrigin(origins = {"${cross.origin.port.number}"})
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody byte[] getImage(@PathVariable String id) { return mediaService.getImageById(Long.parseLong(id)); }

    @GetMapping(value = "/default-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] defaultImage() { return mediaService.getDefaultImage(); }

    @GetMapping(value = "/background-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] defaultBackgroundImage() { return mediaService.getBackgroundImage(); }

}
