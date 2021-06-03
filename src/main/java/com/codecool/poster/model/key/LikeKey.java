package com.codecool.poster.model.key;

import com.codecool.poster.model.Person;
import com.codecool.poster.model.post.Post;

import java.io.Serializable;

public class LikeKey implements Serializable {
    private Person person;
    private Post post;
}
