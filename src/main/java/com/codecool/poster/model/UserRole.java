package com.codecool.poster.model;

import com.codecool.poster.model.key.RoleKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "auth_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(RoleKey.class)
public class UserRole {
    @Id
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @Id
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRole;
}
