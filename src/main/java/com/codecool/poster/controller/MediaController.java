package com.codecool.poster.controller;

import com.codecool.poster.service.MediaService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody byte[] getImage(@PathVariable String id) throws IOException {
        InputStream in = mediaService.getImageInputStreamById(Long.parseLong(id));
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/default-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] defaultImage() throws IOException { return IOUtils.toByteArray(mediaService.getDefaultImage()); }
}
