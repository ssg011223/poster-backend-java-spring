package com.codecool.poster.repository;

import com.codecool.poster.model.PersonMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonMediaRepository extends JpaRepository<PersonMedia, Long> {
}
