package com.codecool.poster.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Media {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Post post;

    private String mediaRoute;

    private MediaTypeEnum mediaType;

}
