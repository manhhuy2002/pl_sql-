package com.example.sql_injection.model;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
