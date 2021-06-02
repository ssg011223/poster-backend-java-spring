package com.codecool.poster.service;

import com.codecool.poster.model.*;
import com.codecool.poster.repository.FollowRepository;
import com.codecool.poster.repository.PersonRepository;
import com.codecool.poster.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final MediaService mediaService;
    private final FollowRepository followRepository;
    private final JwtService jwtService;

    public ResponseEntity getPerson(String id, String bearerToken) {
        long newId = Long.parseLong(String.valueOf(id));
        Optional<Person> person = personRepository.findById(newId);
        Map<Object, Object> result = new HashMap();

        if (bearerToken != null) {
            String token = jwtService.getTokenWithoutBearer(bearerToken);

            if (jwtService.parseIdFromTokenInfo(token) == newId && person.isPresent()) {
                result.put("person", person.get());
                result.put("isLoggedPerson", true);
                return ResponseEntity.ok(result);
            }
            else if (person.isPresent()) {
                result.put("person", person.get());
                result.put("isLoggedPerson", false);
                return ResponseEntity.ok(result);
            }

            return ResponseEntity.badRequest().body("Username not found!");
        }
        else
            return ResponseEntity.badRequest().body("Token can not be null");
    }

    public void editPerson(int id, MultipartFile newProfileImageRoute, MultipartFile newProfileBackgroundImageRoute, String newUsername, String newBio) {
        if (personRepository.findById(Long.parseLong(String.valueOf(id))).isPresent()) {
            Person personToEdit = personRepository.findById(Long.parseLong(String.valueOf(id))).get();

            if (newUsername != null)
                personToEdit.setUsername(newUsername);

            if (newBio != null)
                personToEdit.setDescription(newBio);

            if (newProfileImageRoute != null) {
                String route = mediaService.submit(newProfileImageRoute);

                PersonMedia newImage = PersonMedia.builder()
                        .person(personToEdit)
                        .mediaRoute(route)
                        .mediaType(MediaTypeEnum.IMAGE)
                        .build();

                personToEdit.setProfileImageId(newImage.getId());
            }

            if (newProfileBackgroundImageRoute != null) {
                String route = mediaService.submit(newProfileBackgroundImageRoute);

                PersonMedia newBackgroundImage = PersonMedia.builder()
                        .person(personToEdit)
                        .mediaRoute(route)
                        .mediaType(MediaTypeEnum.IMAGE)
                        .build();

                personToEdit.setProfileBackgroundImageId(newBackgroundImage.getId());
            }

            personRepository.save(personToEdit);
        }
    }

    public void followPerson(String followedId, String followerId) {
        if (personRepository.findById(Long.parseLong(followedId)).isPresent() && personRepository.findById(Long.parseLong(followerId)).isPresent()) {
            Person followedPerson = personRepository.findById(Long.parseLong(followedId)).get();
            Person followerPerson = personRepository.findById(Long.parseLong(followerId)).get();

            if (!followRepository.findByFollowerPerson_IdEqualsAndAndFollowedPerson_IdEquals(followerPerson.getId(), followedPerson.getId())) {
                Follow follow = Follow.builder()
                        .followerPerson(followerPerson)
                        .followedPerson(followedPerson)
                        .followDate(LocalDateTime.now())
                        .build();

                followRepository.save(follow);
            }

            throw new IllegalArgumentException("User already followed");
        }

        throw new UsernameNotFoundException("Username not found!");
    }

    public Collection<Person> searchPeople(String searchPhrase) { return personRepository.findAllByUsernameLike("%" + searchPhrase + "%"); }

    public Person getPersonByUsername(String username) {
        Optional<Person> person = personRepository.findByUsername(username);

        if (person.isPresent())
            return person.get();
        else
            throw new IllegalStateException("Username not found!");
    }
}
