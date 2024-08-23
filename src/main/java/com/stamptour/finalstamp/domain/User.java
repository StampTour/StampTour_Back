package com.stamptour.finalstamp.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid", nullable = false, unique = true)
    private String userid;

    @Column(name = "sessionId")
    private String sessionId;

    @Column(name = "qr1", nullable = false)
    private boolean qr1;

    @Column(name = "qr2", nullable = false)
    private boolean qr2;

    @Column(name = "qr3", nullable = false)
    private boolean qr3;

    @Column(name = "qr4", nullable = false)
    private boolean qr4;

    @Column(name = "qr5", nullable = false)
    private boolean qr5;

    @Column(name = "qr6", nullable = false)
    private boolean qr6;

    @Column(name = "qr7", nullable = false)
    private boolean qr7;

    @Column(name = "qr8", nullable = false)
    private boolean qr8;

    @Column(name = "qr9", nullable = false)
    private boolean qr9;

    @Column(name = "qr10", nullable = false)
    private boolean qr10;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public User(String userid) {
        this.userid = userid;
        this.createdAt = LocalDateTime.now();
    }

}