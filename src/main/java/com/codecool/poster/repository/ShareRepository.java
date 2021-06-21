package com.codecool.poster.repository;

import com.codecool.poster.model.Share;
import com.codecool.poster.model.key.ShareKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ShareRepository extends JpaRepository<Share, ShareKey> {
    Collection<Share> findAllByPostIdIn(Collection<Long> postId);
}
