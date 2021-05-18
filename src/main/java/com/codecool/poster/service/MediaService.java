package com.codecool.poster.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class MediaService {

    public String submit(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String route = "/home/gergo/projects/javaAdvanced/poster-backend-java-spring/src/main/resources/media/" + UUID.randomUUID() + "." + extension;
        try {
            file.transferTo(new File(route));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return route;
    }

    public Collection<String> submit(MultipartFile[] files) {
        List<String> fileRoutes = new ArrayList<>(4);
        for (MultipartFile file: files) {
            fileRoutes.add(submit(file));
        }
        return fileRoutes;
    }
}
