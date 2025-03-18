package com.uplus.ureka.dto.notification;

import lombok.Data;

@Data
public class NotificationReadRequestDTO {
    private Long notificationId;  // 읽음 처리할 알림 ID
    private Long userId;          // 신청하는 사용자 ID
}
