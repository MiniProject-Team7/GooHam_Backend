package com.uplus.ureka.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uplus.ureka.dto.notification.NotificationType;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDTO implements Serializable {
    private Long id;
    private Long userId;
    private Long postId;
    private Long postUserId;
    private Long participantId;
    private NotificationType type;
}