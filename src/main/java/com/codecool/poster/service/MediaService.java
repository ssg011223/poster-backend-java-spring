package com.codecool.poster.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class MediaService {

    public boolean submit(MultipartFile file, String newFileName) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            file.transferTo(new File("/home/gergo/projects/javaAdvanced/poster-backend-java-spring/src/main/resources/media/" + newFileName + "." + extension));
            System.out.println(file.getContentType() + file.getSize());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
