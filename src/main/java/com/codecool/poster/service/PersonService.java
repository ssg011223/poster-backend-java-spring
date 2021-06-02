package com.codecool.poster.service;

import com.codecool.poster.model.*;
import com.codecool.poster.model.follow.Follow;
import com.codecool.poster.repository.FollowRepository;
import com.codecool.poster.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final MediaService mediaService;
    private final FollowRepository followRepository;

    public Optional<Person> getUser(String username) {
        return personRepository.findByUsername(username);
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
}
