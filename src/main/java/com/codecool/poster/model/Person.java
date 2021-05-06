package com.codecool.poster.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Person {
    @Id
    private int id;

    private String username;

    @JsonIgnore
    private String password;

    private String description;

    private LocalDateTime birthDate;

    private LocalDateTime registrationDate;

    private int followersCount;

    private int followedCount;

    private String profileImageRoute;

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followerCount) {
        this.followersCount = followerCount;
    }

    public int getFollowedCount() {
        return followedCount;
    }

    public void setFollowedCount(int followedCount) {
        this.followedCount = followedCount;
    }

    public String getProfileImageRoute() {
        return profileImageRoute;
    }

    public void setProfileImageRoute(String profileImageRoute) {
        this.profileImageRoute = profileImageRoute;
    }
}
