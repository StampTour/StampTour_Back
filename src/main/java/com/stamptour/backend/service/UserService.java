package com.stamptour.backend.service;

import com.stamptour.backend.repository.UserRepository;
import com.stamptour.backend.user.User;

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
