package com.stamptour.stampback.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class QrStamp {

    @Id
    private String usrid;

    @OneToOne
    @MapsId("usrid")
    @JoinColumn(name = "usrid", referencedColumnName = "userid")
    private User user;


    @Column(name = "qr1")
    private boolean qr1 = false;
    @Column(name = "qr2")
    private boolean qr2 = false;
    @Column(name = "qr3")
    private boolean qr3 = false;
    @Column(name = "qr4")
    private boolean qr4 = false;
    @Column(name = "qr5")
    private boolean qr5 = false;
    @Column(name = "qr6")
    private boolean qr6 = false;
    @Column(name = "qr7")
    private boolean qr7 = false;
    @Column(name = "qr8")
    private boolean qr8 = false;
    @Column(name = "qr9")
    private boolean qr9 = false;
    @Column(name = "qr10")
    private boolean qr10 = false;

    @Builder
    public QrStamp(User user) {
        this.usrid = user.getUserid();
        this.user = user;
    }

    @Override
    public String toString() {
        return "QrStamp{" +
                "usrid='" + usrid + '\'' +
                ", qr1=" + qr1 +
                ", qr2=" + qr2 +
                ", qr3=" + qr3 +
                ", qr4=" + qr4 +
                ", qr5=" + qr5 +
                ", qr6=" + qr6 +
                ", qr7=" + qr7 +
                ", qr8=" + qr8 +
                ", qr9=" + qr9 +
                ", qr10=" + qr10 +
                '}';
    }
}
