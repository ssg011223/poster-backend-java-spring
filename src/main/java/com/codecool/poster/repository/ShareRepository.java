package com.codecool.poster.repository;

import com.codecool.poster.model.Share;
import com.codecool.poster.model.key.ShareKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRepository extends JpaRepository<Share, ShareKey> {
}
