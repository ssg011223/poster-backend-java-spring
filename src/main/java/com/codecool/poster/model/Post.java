package com.codecool.poster.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

//TODO: Change personId from int to Person
@Entity
public class Post {
    @Id
    int id;

    int personId;

    String message;

    boolean hasImage;

    boolean hasVideo;

    LocalDateTime postDate;

    int adomCount;

    int commentCount;

    int shareCount;

    int imageCount;

    public Post() {
    }

    public Post(int id, int personId, String message, boolean hasImage, boolean hasVideo, LocalDateTime postDate, int adomCount, int commentCount, int shareCount, int imageCount) {
        this.id = id;
        this.personId = personId;
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

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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
