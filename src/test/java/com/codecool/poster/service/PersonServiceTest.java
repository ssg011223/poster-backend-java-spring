package com.codecool.poster.service;

import com.codecool.poster.model.Person;
import com.codecool.poster.model.PersonMedia;
import com.codecool.poster.model.follow.Follow;
import com.codecool.poster.repository.FollowRepository;
import com.codecool.poster.repository.PersonMediaRepository;
import com.codecool.poster.repository.PersonRepository;
import com.codecool.poster.security.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private MediaService mediaService;
    @Mock
    private PersonMediaRepository personMediaRepository;
    @Mock
    private FollowRepository followRepository;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private PersonService personService;

    @Test
    void getPersonByIdShouldReturnResponseEntityOkLoggedPersonTrue() {
        Person person = new Person();
        when(personRepository.findById(any())).thenReturn(Optional.of(person));
        when(jwtService.getTokenWithoutBearer(any())).thenReturn("token");
        when(jwtService.parseIdFromTokenInfo("token")).thenReturn(1L);

        Map<Object, Object> res = new HashMap<>();
        res.put("person", person);
        res.put("isLoggedPerson", true);
        ResponseEntity expected = ResponseEntity.ok(res);

        assertEquals(expected, personService.getPersonById("1", "bearer token"));
    }

    @Test
    void getPersonByIdShouldReturnResponseEntityOkLoggedPersonFalse() {
        Person person = new Person();
        when(personRepository.findById(any())).thenReturn(Optional.of(person));
        when(jwtService.getTokenWithoutBearer(any())).thenReturn("token");
        when(jwtService.parseIdFromTokenInfo("token")).thenReturn(1L);

        Map<Object, Object> res = new HashMap<>();
        res.put("person", person);
        res.put("isLoggedPerson", false);
        ResponseEntity expected = ResponseEntity.ok(res);

        assertEquals(expected, personService.getPersonById("2", "bearer token"));
    }

    @Test
    void getPersonByIdShouldReturnResponseEntityBadRequestUsernameNotFound() {
        when(personRepository.findById(any())).thenReturn(Optional.empty());

        ResponseEntity expected = ResponseEntity.badRequest().body("Username not found!");

        assertEquals(expected, personService.getPersonById("1", "bearer token"));
    }

    @Test
    void getPersonByIdShouldReturnRsponseEntityBadRequestTokenCannotBeNull() {
        when(personRepository.findById(any())).thenReturn(Optional.empty());

        ResponseEntity expected = ResponseEntity.badRequest().body("Token can not be null");

        assertEquals(expected, personService.getPersonById("1", null));
    }

    @Test
    void editPersonShouldReturnResponseEntityOk() {
        when(jwtService.getTokenWithoutBearer(any())).thenReturn("token");
        when(jwtService.parseIdFromTokenInfo("token")).thenReturn(1L);
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(new Person()));

        ResponseEntity result = personService.editPerson("bearer token",
                "1",
                mock(MultipartFile.class),
                mock(MultipartFile.class),
                "username",
                "description");

        verify(personRepository, times(1)).save(any(Person.class));
        verify(mediaService, times(2)).submit(any(MultipartFile.class));
        verify(personMediaRepository, times(2)).save(any(PersonMedia.class));
        assertEquals(ResponseEntity.ok().build(), result);
    }

    @Test
    void editPersonShouldReturnResponseEntityBadRequestInvalidId() {
        when(jwtService.getTokenWithoutBearer(any())).thenReturn("token");
        when(jwtService.parseIdFromTokenInfo("token")).thenReturn(1L);
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(new Person()));

        ResponseEntity result = personService.editPerson("bearer token",
                "2",
                mock(MultipartFile.class),
                mock(MultipartFile.class),
                "username",
                "description");

        ResponseEntity expected = ResponseEntity.badRequest().body("Token or Id are not valid!");

        verify(personRepository, never()).save(any(Person.class));
        verify(mediaService, never()).submit(any(MultipartFile.class));
        verify(personMediaRepository, never()).save(any(PersonMedia.class));
        assertEquals(expected, result);
    }

    @Test
    void getPersonDetailsShouldReturnResponseEntityOkWithPerson() {
        Person person = new Person();

        when(jwtService.getTokenWithoutBearer(any())).thenReturn("token");
        when(jwtService.parseIdFromTokenInfo("token")).thenReturn(1L);
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));

        ResponseEntity expected = ResponseEntity.ok(person);

        assertEquals(expected, personService.getPersonDetails("bearer token"));
    }

    @Test
    void getPersonDetailsShouldReturnResponseEntityBadRequestUsernameNotFound() {
        when(jwtService.getTokenWithoutBearer(any())).thenReturn("token");
        when(jwtService.parseIdFromTokenInfo("token")).thenReturn(1L);
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity expected = ResponseEntity.badRequest().body("Username not found!");

        assertEquals(expected, personService.getPersonDetails("bearer token"));
    }

    @Test
    void getPersonDetailsShouldReturnResponseEntityBadRequestTokenCannotBeNull() {

        ResponseEntity expected = ResponseEntity.badRequest().body("Token can not be null");

        assertEquals(expected, personService.getPersonDetails(null));
    }

    @Test
    void getAllPeopleExpectLoggedPersonShouldReturnResponseEntityOkWithPerson() {
        Person person = new Person();

        when(jwtService.getTokenWithoutBearer(any())).thenReturn("token");
        when(jwtService.parseIdFromTokenInfo("token")).thenReturn(1L);
        when(personRepository.findAllExpectOnePerson(anyLong())).thenReturn(List.of(person));

        ResponseEntity expected = ResponseEntity.ok(List.of(person));

        assertEquals(expected, personService.getAllPeopleExpectLoggedPerson("bearer token"));
    }

    @Test
    void getAllPeopleExpectLoggedPersonShouldReturnResponseEntityBadRequestTokenCannotBeNull() {

        ResponseEntity expected = ResponseEntity.badRequest().body("Token can not be null!");

        assertEquals(expected, personService.getAllPeopleExpectLoggedPerson(null));
    }

    @Test
    void followPersonShouldSaveFollow() {
        Person person = new Person();

        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));
        when(followRepository.findByFollowerPerson_IdEqualsAndAndFollowedPerson_IdEquals(anyLong(), anyLong())).thenReturn(false);

        personService.followPerson("1", "1");

        verify(followRepository, times(1)).save(any(Follow.class));
    }

    @Test
    void followPersonShouldThrowExceptionUserAlreadyFollowed() {
        Person person = new Person();

        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));
        when(followRepository.findByFollowerPerson_IdEqualsAndAndFollowedPerson_IdEquals(anyLong(), anyLong())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> personService.followPerson("1", "1"));
    }

    @Test
    void followPersonShouldThrowExceptionUsernameNotFound() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> personService.followPerson("1", "1"));
    }
}