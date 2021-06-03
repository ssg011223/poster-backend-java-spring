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

    @GetMapping
    public ResponseEntity getPersonDetails(@CookieValue("token") String token) {
        return personService.getPersonDetails(token); }

    @PutMapping
    public ResponseEntity editPerson(@CookieValue("token") String token,
                                     @RequestParam String id,
                                     @RequestParam(required = false, value = "profileImageRoute") MultipartFile newProfileImageRoute,
                                     @RequestParam(required = false, value = "profileBackgroundImageRoute") MultipartFile newProfileBackgroundImageRoute,
                                     @RequestParam(required = false, value = "username") String newUsername,
                                     @RequestParam(required = false, value = "description") String newBio) {
        return personService.editPerson(token, id, newProfileImageRoute, newProfileBackgroundImageRoute, newUsername, newBio);
    }
}
