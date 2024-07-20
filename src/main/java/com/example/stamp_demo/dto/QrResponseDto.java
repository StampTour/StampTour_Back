package com.example.stamp_demo.dto;

import com.example.stamp_demo.domain.QrStamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QrResponseDto {
    private Long qrId;
    private Long usrid;
    private boolean qr1;
    private boolean qr2;
    private boolean qr3;
    private boolean qr4;
    private boolean qr5;
    private boolean qr6;
    private boolean qr7;
    private boolean qr8;
    private boolean qr9;
    private boolean qr10;

    public QrResponseDto(QrStamp qrStamp) {
        this.usrid = qrStamp.getUser().getId();
        this.qr1 = qrStamp.isQr1();
        this.qr2 = qrStamp.isQr2();
        this.qr3 = qrStamp.isQr3();
        this.qr4 = qrStamp.isQr4();
        this.qr5 = qrStamp.isQr5();
        this.qr6 = qrStamp.isQr6();
        this.qr7 = qrStamp.isQr7();
        this.qr8 = qrStamp.isQr8();
        this.qr9 = qrStamp.isQr9();
        this.qr10 = qrStamp.isQr10();
    }
}