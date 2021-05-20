package com.codecool.poster.repository;

import com.codecool.poster.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean findByFollowerPerson_IdEqualsAndAndFollowedPerson_IdEquals(long followerId, long followedId);
}
