package com.codecool.poster.service;

import com.codecool.poster.model.Media;
import com.codecool.poster.model.MediaTypeEnum;
import com.codecool.poster.model.Person;
import com.codecool.poster.model.PersonMedia;
import com.codecool.poster.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService implements UserDetailsService {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MediaService mediaService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public String signUpUser(Person person) {
        if (person.getUsername() == null || person.getEmail() == null || person.getBirthDate() == null || person.getPassword() == null)
            return "Registration failed!";

        if (person.getBirthDate().isAfter(LocalDate.from(LocalDate.now()))) {
            return "Birth date is not valid";
        }

        boolean emailExists = personRepository
                .findByEmail(person.getEmail())
                .isPresent();

        boolean usernameExists = personRepository
                .findByUsername(person.getUsername())
                .isPresent();

        if (emailExists || usernameExists)
            return "Email or username already taken!";

        String encodedPassword = bCryptPasswordEncoder.encode(person.getPassword());

        person.setPassword(encodedPassword);

        personRepository.save(person);

        return "success";
    }

    public String editPerson(int id, MultipartFile newProfileImageRoute, MultipartFile newProfileBackgroundImageRoute, String newUsername, String newBio) {
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

        return "success";
    }
}
