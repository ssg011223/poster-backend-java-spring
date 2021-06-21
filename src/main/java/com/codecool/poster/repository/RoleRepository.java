package com.codecool.poster.repository;

import com.codecool.poster.model.UserRole;
import com.codecool.poster.model.key.RoleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, RoleKey> {

    public List<UserRole> findAllByPersonId(long person_id);

}
