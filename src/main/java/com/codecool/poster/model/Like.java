package com.codecool.poster.model;

import com.codecool.poster.model.key.LikeKey;
import com.codecool.poster.model.post.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(LikeKey.class)
public class Like {
    @Id
    @ManyToOne
    private Person person;
    @Id
    @ManyToOne
    private Post post;

    private LocalDateTime likeDate;
}
