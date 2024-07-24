package com.stamptour.stampback.dto;

import lombok.Getter;
import lombok.Setter;

// 클라이언트로 보낼 응답 객체를 나타내는 클래스
@Getter
@Setter
public class UserResponseDto {
    private String userid;
    private String message;

    // 생성자
    public UserResponseDto(String userid, String message) {
        this.userid = userid;
        this.message = message;
    }
}