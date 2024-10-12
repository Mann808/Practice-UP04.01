package com.example.onlinestore.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(length = 20)
    private String name; // Например, "ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN"

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    // Конструкторы
    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
