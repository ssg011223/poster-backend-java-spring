package com.codecool.poster.model.post;

import com.codecool.poster.model.Media;
import com.codecool.poster.model.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendPost {
    private Post post;
    private Collection<Media> media;
}
