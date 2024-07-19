package com.example.stamp_demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StampController {
    @GetMapping("/get")
    public ResponseEntity<?> getHelloWorld() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @PostMapping("/post")
    ResponseEntity<?> postHelloWorld() {
        return new ResponseEntity<>("Hello World By Post Method", HttpStatus.OK);
    }
}
