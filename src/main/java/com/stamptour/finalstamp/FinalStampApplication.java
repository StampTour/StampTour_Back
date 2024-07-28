package com.stamptour.finalstamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FinalStampApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalStampApplication.class, args);
    }

}
