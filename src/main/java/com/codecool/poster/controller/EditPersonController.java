package com.codecool.poster.controller;

import com.codecool.poster.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/settings/profile")
public class EditPersonController {

    private final PersonService personService;

    @PostMapping
    public String editPerson(@RequestParam int id,
                             @RequestParam(required = false) MultipartFile newProfileImageRoute,
                             @RequestParam(required = false) MultipartFile newProfileBackgroundImageRoute,
                             @RequestParam(required = false) String newUsername,
                             @RequestParam(required = false) String newBio) {
        return personService.editPerson(id, newProfileImageRoute, newProfileBackgroundImageRoute, newUsername, newBio);
    }
}
