package com.codecool.poster.service;

import com.codecool.poster.model.Media;
import com.codecool.poster.repository.MediaRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;

    public String submit(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String route = "/poster-backend-java-spring/src/main/resources/media/" + UUID.randomUUID() + "." + extension;
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

    public Collection<Media> findAllByPostIdIn(Collection<Long> id) {
        return mediaRepository.findAllByPostIdIn(id);
    }

    public InputStream getImageInputStreamById(long id) {
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

    public InputStream getDefaultImage() {
        InputStream in = null;
        try {
            in = new FileInputStream("src/main/resources/media/basic_wallaper.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return in;
    }
}
