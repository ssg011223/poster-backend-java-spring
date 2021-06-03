package com.codecool.poster.repository;

import com.codecool.poster.model.Like;
import com.codecool.poster.model.key.LikeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface LikeRepository extends JpaRepository<Like, LikeKey> {
    Collection<Like> findAllByPostIdIn(Collection<Long> postId);
}
