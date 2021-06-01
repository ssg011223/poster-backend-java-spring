package com.codecool.poster.model;

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
public class UserRole {
    @Id
    @ManyToOne
    private Person personId;
    @Id
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRole;
}
