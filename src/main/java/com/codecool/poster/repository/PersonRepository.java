package com.codecool.poster.repository;

import com.codecool.poster.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmail(String email);

    Optional<Person> findByUsername(String username);

    Collection<Person> findAllByUsernameLike(String searchPhrase);
}
