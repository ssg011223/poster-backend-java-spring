package com.codecool.poster.repository;

import com.codecool.poster.model.follow.Follow;
import com.codecool.poster.model.follow.FollowWithFollowed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean findByFollowerPerson_IdEqualsAndAndFollowedPerson_IdEquals(long followerId, long followedId);

    @Query("SELECT new com.codecool.poster.model.follow.FollowWithFollowed(f.followedPerson, f.followDate) FROM Follow f WHERE f.followerPerson.id = :#{#followerPersonId}")
    public List<FollowWithFollowed> findAllByFollowerPersonId(@Param("followerPersonId") long followerPersonId);
}
