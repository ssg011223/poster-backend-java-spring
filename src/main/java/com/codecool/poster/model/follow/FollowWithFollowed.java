package com.codecool.poster.model.follow;

import com.codecool.poster.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FollowWithFollowed {
    private Person followedPerson;

    private LocalDateTime followDate;
}
