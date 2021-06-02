package com.codecool.poster.controller;

import com.codecool.poster.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping(path = "/settings/profile")
public class EditPersonController {

    private final PersonService personService;

    @PutMapping
    public ResponseEntity<String> editPerson(@RequestParam int id,
                                     @RequestParam(required = false) MultipartFile newProfileImageRoute,
                                     @RequestParam(required = false) MultipartFile newProfileBackgroundImageRoute,
                                     @RequestParam(required = false) String newUsername,
                                     @RequestParam(required = false) String newBio) {
        personService.editPerson(id, newProfileImageRoute, newProfileBackgroundImageRoute, newUsername, newBio);
        return ResponseEntity.ok("");
    }
}
