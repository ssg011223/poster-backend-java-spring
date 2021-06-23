package com.codecool.poster.service;

import com.codecool.poster.model.Media;
import com.codecool.poster.model.PersonMedia;
import com.codecool.poster.repository.MediaRepository;
import com.codecool.poster.repository.PersonMediaRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;
    private final PersonMediaRepository personMediaRepository;

    private static final String DEFAULT_MEDIA_PATH = "./src/main/resources/defaultMedia/";

    public String submit(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String route = "./src/main/resources/media/" + UUID.randomUUID() + "." + extension;
        try {
            Files.write(Paths.get(route), file.getBytes());
            return route;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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

    public Collection<Media> findAllByPostIdIn(Collection<Long> id) {
        return mediaRepository.findAllByPostIdIn(id);
    }

    public byte[] getImageById(long id) {
        Media media = mediaRepository.findById(id).orElse(null);
        PersonMedia personMedia;
        String route = "";

        if (media != null)
            route = media.getMediaRoute();
        else {
            personMedia = personMediaRepository.findById(id).orElse(null);

            if (personMedia == null)
                return null;

            route = personMedia.getMediaRoute();
        }

        try {
            return Files.readAllBytes(Paths.get(route));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getDefaultImage() {
        try {
            return Files.readAllBytes(Paths.get(DEFAULT_MEDIA_PATH + "basic_wallpaper.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getBackgroundImage() {
        try {
            return Files.readAllBytes(Paths.get(DEFAULT_MEDIA_PATH + "background_wallpaper.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
