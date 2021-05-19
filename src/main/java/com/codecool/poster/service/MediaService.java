package com.codecool.poster.service;

import com.codecool.poster.model.Media;
import com.codecool.poster.repository.MediaRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class MediaService {
    @Autowired
    private MediaRepository mediaRepository;

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

    public void saveAll(Iterable<Media> media) {
        mediaRepository.saveAll(media);
    }

    public InputStream getImageInputStreamById(int id) {
        Media media = mediaRepository.findById(id).orElse(null);
        if (media == null) return null;
        String route = media.getMediaRoute();
        InputStream in = null;
        try {
            in = new FileInputStream(route);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return in;
    }
}
