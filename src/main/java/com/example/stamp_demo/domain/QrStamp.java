package com.example.stamp_demo.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(QrStampId.class)
@NoArgsConstructor
public class QrStamp {
    @Id
    private Long qrId;

    @Id
    private Long usr;

    @OneToOne
    @MapsId("usr")
    @JoinColumn(name = "usr", referencedColumnName = "id")
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
    public QrStamp(Long qrId, Long usr, User user) {
        this.qrId = qrId;
        this.usr = usr;
        this.user = user;
    }

    public QrStamp(QrStampId id, User user) {
        this.qrId = id.getQrId();
        this.usr = id.getUsr();
        this.user = user;
    }
}
