package com.codecool.poster.model.key;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class ShareKey implements Serializable {
    private long postId;
    private long personId;
}
