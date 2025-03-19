package com.uplus.ureka.domain.notification;

import com.uplus.ureka.dto.notification.NotificationType;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Long id;

    private Long userId;
    private Long postId;
    private Long postUserId;
    private Long participantId;
    private NotificationType type;
    private boolean isRead;
    private LocalDateTime createdAt = LocalDateTime.now();
}
