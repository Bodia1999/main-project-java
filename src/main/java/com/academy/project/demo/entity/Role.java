package com.academy.project.demo.entity;

import lombok.Getter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "roles")

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;
}
