package com.example.stamp_demo.controller;

import com.example.stamp_demo.domain.User;
import com.example.stamp_demo.repository.UserRepository;
import com.example.stamp_demo.service.QrService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static java.util.Optional.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private QrService qrService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockHttpSession session;

    @BeforeEach
    public void setup() {
        session = new MockHttpSession();
    }


    //비번이 있을 경우 Login 작동 확인 코드
    @Test
    public void testLoginWithExistingUser() throws Exception {
        // Given
        User user = new User("existingPassword");
        given(userRepository.findByPassword("existingPassword")).willReturn(user);

        // When & Then
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string("로그인 성공"));

        verify(userRepository, times(1)).findByPassword("existingPassword");
    }

}
