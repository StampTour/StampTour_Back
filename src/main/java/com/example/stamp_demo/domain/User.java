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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userid;

    @Builder
    public User(String userid) {
        this.userid = userid;
    }

    public User update(String userid) {
        this.userid = userid;
        return this;
    }
}