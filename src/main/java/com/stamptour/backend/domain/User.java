package com.stamptour.backend.domain;

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
    private String userid;


    @Builder
    public User(String userid) {
        this.userid = userid;
    }

}
