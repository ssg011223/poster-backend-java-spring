package com.codecool.poster.model;

import com.codecool.poster.model.post.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Post post;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String mediaRoute;

    @Enumerated(EnumType.STRING)
    private MediaTypeEnum mediaType;

}
