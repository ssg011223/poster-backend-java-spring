package com.codecool.poster.model.post;

import com.codecool.poster.model.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

}
