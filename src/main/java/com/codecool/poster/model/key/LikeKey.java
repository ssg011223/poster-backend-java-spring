package com.codecool.poster.model.key;

import com.codecool.poster.model.Person;
import com.codecool.poster.model.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeKey implements Serializable {
    private long personId;
    private long postId;
}
