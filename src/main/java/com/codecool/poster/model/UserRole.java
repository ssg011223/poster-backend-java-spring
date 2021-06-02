package com.codecool.poster.model;

import com.codecool.poster.model.key.RoleKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "auth_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(RoleKey.class)
public class UserRole {
    @Id
    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonIgnore
    private Person person;
    @Id
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRole;
}
