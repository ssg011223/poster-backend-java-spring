package com.codecool.poster.repository;

import com.codecool.poster.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {
}
