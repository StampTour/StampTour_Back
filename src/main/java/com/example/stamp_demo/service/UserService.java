package com.example.stamp_demo.service;

import com.example.stamp_demo.domain.User;
import com.example.stamp_demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUserid(String userid) {
        return userRepository.findByUserid(userid);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
