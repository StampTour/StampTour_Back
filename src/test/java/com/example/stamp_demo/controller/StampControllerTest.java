package com.example.stamp_demo.controller;

import com.example.stamp_demo.domain.User;
import com.example.stamp_demo.dto.QrResponseDto;
import com.example.stamp_demo.domain.QrStamp;
import com.example.stamp_demo.repository.UserRepository;
import com.example.stamp_demo.service.QrService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StampControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private QrService qrService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private StampController stampController;

    private QrStamp qrStamp;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setPassword("testPassword");
        qrStamp = new QrStamp();
        qrStamp.setUsrpw("testPassword");
        qrStamp.setUser(user);
    }

    //QrStamp 엔티티와 연결 확인
    @Test
    public void testUpdateDataNotFound() {
        // 세션에서 패스워드 가져오기
        when(session.getAttribute("password")).thenReturn("testPassword");
        // QrService에서 해당 패스워드로 QR 엔티티 찾기 실패
        when(qrService.findByUsrpw("testPassword")).thenReturn(Optional.empty());

        // param 값 1로 테스트
        ResponseEntity<QrResponseDto> response = stampController.updateData(1, qrStamp, session);

        // Qr 엔티티를 찾지 못했으므로 404 Not Found 응답 확인
        assertEquals(ResponseEntity.notFound().build(), response);
    }

    //패스워드가 QrStamp에서도 만들어졌는지 확인
    @Test
    public void testUpdateDataBadRequest() {
        // 세션에서 패스워드 가져오기
        when(session.getAttribute("password")).thenReturn("testPassword");
        // QrService에서 해당 패스워드로 QR 엔티티 찾기
        when(qrService.findByUsrpw("testPassword")).thenReturn(Optional.of(qrStamp));

        // 잘못된 param 값 테스트
        ResponseEntity<QrResponseDto> response = stampController.updateData(11, qrStamp, session);

        // 400 Bad Request 응답 확인
        assertEquals(ResponseEntity.badRequest().build(), response);
    }

    //특정 패스워드의 QrStamp qr 데이터 변경 확인
    @Test
    public void testUpdateData() {
        // 세션에서 패스워드 가져오기
        when(session.getAttribute("password")).thenReturn("testPassword");
        // QrService에서 해당 패스워드로 QR 엔티티 찾기
        when(qrService.findByUsrpw("testPassword")).thenReturn(Optional.of(qrStamp));

        // param 값 1로 테스트
        ResponseEntity<QrResponseDto> response = stampController.updateData(1, qrStamp, session);

        // Qr1이 true로 설정되었는지 확인
        assertTrue(qrStamp.isQr1());
        assertEquals(ResponseEntity.ok(new QrResponseDto(qrStamp)), response);

        // param 값 5로 테스트
        response = stampController.updateData(5, qrStamp, session);

        // Qr5가 true로 설정되었는지 확인
        assertTrue(qrStamp.isQr5());
        assertEquals(ResponseEntity.ok(new QrResponseDto(qrStamp)), response);

        // qrService.saveQrStamp가 호출되었는지 확인
        verify(qrService, times(2)).saveQrStamp(qrStamp);
    }


}
