package com.codecool.poster.model;

import com.codecool.poster.model.key.FollowKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "follow")
@IdClass(FollowKey.class)
public class Follow {

    @Id
    @ManyToOne
    @JoinColumn(name = "follower_person_id")
    private Person followerPerson;

    @Id
    @ManyToOne
    @JoinColumn(name = "followed_person_id")
    private Person followedPerson;

    private LocalDateTime followDate;
}
