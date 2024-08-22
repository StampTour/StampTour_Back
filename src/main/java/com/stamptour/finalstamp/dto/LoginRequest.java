package com.stamptour.finalstamp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private UserRequest userRequest;
    private StampRequest stampRequest;
}

