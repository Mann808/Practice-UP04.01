package com.example.onlinestoreapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role {
    @Id
    @Column(length = 20)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties("roles")
    private Set<User> users;
}