package com.example.stamp_demo.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @Column(nullable = false)
    private String password;

    @Builder
    public User(String password) {
        this.password = password;
    }
}