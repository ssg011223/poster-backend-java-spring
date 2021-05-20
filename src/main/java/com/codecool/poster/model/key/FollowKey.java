package com.codecool.poster.model.key;

import com.codecool.poster.model.Person;

import java.io.Serializable;

public class FollowKey implements Serializable {

    private Person followerPerson;

    private Person followedPerson;
}
