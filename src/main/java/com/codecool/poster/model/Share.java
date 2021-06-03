package com.codecool.poster.model;

import com.codecool.poster.model.key.ShareKey;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "share")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ShareKey.class)
public class Share {
    @Id
    private long postId;
    @Id
    private long personId;

    private LocalDateTime shareDate;
}
