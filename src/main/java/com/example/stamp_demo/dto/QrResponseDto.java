package com.example.stamp_demo.dto;

import com.example.stamp_demo.domain.QrStamp;
import com.example.stamp_demo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class QrResponseDto {
    private String usrpw;
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

        User user = qrStamp.getUser();
        if (user != null) {
            this.usrpw = user.getPassword();
        } else {
            this.usrpw = null; // 또는 적절한 기본값 설정
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QrResponseDto that = (QrResponseDto) o;
        return qr1 == that.qr1 &&
                qr2 == that.qr2 &&
                qr3 == that.qr3 &&
                qr4 == that.qr4 &&
                qr5 == that.qr5 &&
                qr6 == that.qr6 &&
                qr7 == that.qr7 &&
                qr8 == that.qr8 &&
                qr9 == that.qr9 &&
                qr10 == that.qr10 &&
                Objects.equals(usrpw, that.usrpw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qr1, qr2, qr3, qr4, qr5, qr6, qr7, qr8, qr9, qr10, usrpw);
    }
}