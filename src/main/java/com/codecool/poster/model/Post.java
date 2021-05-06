package com.codecool.poster.model;

import javax.persistence.*;
import java.time.LocalDateTime;

//TODO: Change personId from int to Person
@Entity
public class Post {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Person person;

    private String message;

    private boolean hasImage;

    private boolean hasVideo;

    private LocalDateTime postDate;

    private int adomCount;

    private int commentCount;

    private int shareCount;

    private int imageCount;

    public Post() {
    }

    public Post(int id, Person person, String message, boolean hasImage, boolean hasVideo, LocalDateTime postDate, int adomCount, int commentCount, int shareCount, int imageCount) {
        this.id = id;
        this.person = person;
        this.message = message;
        this.hasImage = hasImage;
        this.hasVideo = hasVideo;
        this.postDate = postDate;
        this.adomCount = adomCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.imageCount = imageCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public int getAdomCount() {
        return adomCount;
    }

    public void setAdomCount(int adomCount) {
        this.adomCount = adomCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }
}
