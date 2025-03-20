package com.uplus.ureka.dto.notification;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class NotificationDTO implements Serializable {
    private Long id;
    private Long userId;
    private Long postId;
    private Long postUserId;
    private Long participantId;
    private NotificationType type;
    private boolean isRead;
    private LocalDateTime createdAt;

}


