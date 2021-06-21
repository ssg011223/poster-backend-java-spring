package com.codecool.poster.repository;

import com.codecool.poster.model.Like;
import com.codecool.poster.model.key.LikeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeKey> {
    Collection<Like> findAllByPostIdIn(Collection<Long> postId);
}
