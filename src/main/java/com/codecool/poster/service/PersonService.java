package com.codecool.poster.service;

import com.codecool.poster.model.*;
import com.codecool.poster.model.follow.Follow;
import com.codecool.poster.repository.FollowRepository;
import com.codecool.poster.repository.PersonMediaRepository;
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
    private final PersonMediaRepository personMediaRepository;
    private final FollowRepository followRepository;
    private final JwtService jwtService;

    public ResponseEntity getPersonById(String id, String bearerToken) {
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

    public Optional<Person> getUser(String username) {
        return personRepository.findByUsername(username);
    }

    public ResponseEntity editPerson(String bearerToken, String id, MultipartFile newProfileImage, MultipartFile newProfileBackgroundImage, String newUsername, String newBio) {
        String token = jwtService.getTokenWithoutBearer(bearerToken);
        long newId = Long.parseLong(String.valueOf(id));
        Optional<Person> person = personRepository.findById(newId);

        if (jwtService.parseIdFromTokenInfo(token) == newId && person.isPresent()) {
            Person personToEdit = person.get();

            if (newUsername != null)
                personToEdit.setUsername(newUsername);

            if (newBio != null)
                personToEdit.setDescription(newBio);

            if (newProfileImage != null) {
                String route = mediaService.submit(newProfileImage);

                PersonMedia newImage = PersonMedia.builder()
                        .person(personToEdit)
                        .mediaRoute(route)
                        .mediaType(MediaTypeEnum.IMAGE)
                        .build();

                personMediaRepository.save(newImage);
                personToEdit.setProfileImageId(newImage.getId());
            }

            if (newProfileBackgroundImage != null) {
                String route = mediaService.submit(newProfileBackgroundImage);

                PersonMedia newBackgroundImage = PersonMedia.builder()
                        .person(personToEdit)
                        .mediaRoute(route)
                        .mediaType(MediaTypeEnum.IMAGE)
                        .build();

                personMediaRepository.save(newBackgroundImage);
                personToEdit.setProfileBackgroundImageId(newBackgroundImage.getId());
            }

            personRepository.save(personToEdit);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body("Token or Id are not valid!");
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
                return;
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

    public ResponseEntity getPersonDetails(String bearerToken) {
        if (bearerToken != null) {
            String token = jwtService.getTokenWithoutBearer(bearerToken);
            long id = jwtService.parseIdFromTokenInfo(token);
            Optional<Person> person = personRepository.findById(id);

            if (person.isPresent())
                return ResponseEntity.ok(person.get());

            return ResponseEntity.badRequest().body("Username not found!");
        }

        return ResponseEntity.badRequest().body("Token can not be null");
    }

    public ResponseEntity getAllPeopleExpectLoggedPerson(String bearerToken) {
        if (bearerToken != null) {
            String token = jwtService.getTokenWithoutBearer(bearerToken);
            long id = jwtService.parseIdFromTokenInfo(token);

            return ResponseEntity.ok(personRepository.findAllExpectOnePerson(id));
        }

        return ResponseEntity.badRequest().body("Token can not be null!");
    }
}
