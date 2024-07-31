package com.stamptour.finalstamp.dto;

import com.stamptour.finalstamp.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private String userid;
    private String token;
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

    private String message;
    private boolean allowRetry;

    // 기존 User 객체를 사용하여 생성하는 생성자
    public UserResponseDto(User user) {
        this.userid = user.getUserid();
        this.token = user.getToken();
        this.qr1 = user.isQr1();
        this.qr2 = user.isQr2();
        this.qr3 = user.isQr3();
        this.qr4 = user.isQr4();
        this.qr5 = user.isQr5();
        this.qr6 = user.isQr6();
        this.qr7 = user.isQr7();
        this.qr8 = user.isQr8();
        this.qr9 = user.isQr9();
        this.qr10 = user.isQr10();
    }

    public UserResponseDto(String message) {
        this.message = message;
    }
}
