package com.stamptour.finalstamp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String userid;

    // 기본 생성자
    public UserRequest() {}

    // 생성자
    public UserRequest(String userid) {
        this.userid = userid;
    }

}