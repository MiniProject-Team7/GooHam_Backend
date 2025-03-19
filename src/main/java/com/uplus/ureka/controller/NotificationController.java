package com.uplus.ureka.controller;

import com.uplus.ureka.domain.notification.Notification;
import com.uplus.ureka.dto.CustomResponseDTO;
import com.uplus.ureka.dto.notification.NotificationRequestDTO;
import com.uplus.ureka.dto.notification.NotificationResponseDTO;
import com.uplus.ureka.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gooham/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    //  알림 생성
    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody NotificationRequestDTO notificationRequestDTO) {
        NotificationResponseDTO responseDTO = notificationService.createNotification(notificationRequestDTO);
        return ResponseEntity.ok("알림이 생성되었습니다.");
    }

    // 알림 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getNotification(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        if (notifications != null) {
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // 읽은 알림 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long userId) {
        notificationService.deleteNotifications(userId);
        return ResponseEntity.ok("읽은 알림이 삭제되었습니다.");
    }


    // 알림 읽음 처리
    @PatchMapping("/{id}")
    public ResponseEntity<String> markAsRead(@PathVariable Long id, @RequestParam Long userId) {
        notificationService.markNotificationAsRead(id, userId);
        return ResponseEntity.ok("알림을 읽음처리했습니다.");
    }
}