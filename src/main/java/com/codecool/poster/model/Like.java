package com.codecool.poster.model;

import com.codecool.poster.model.key.LikeKey;
import com.codecool.poster.model.post.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adom")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(LikeKey.class)
public class Like {
    @Id
    private long postId;
    @Id
    private long personId;

    @Column(name = "adom_date")
    private LocalDateTime likeDate;
}
