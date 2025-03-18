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

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("post_user_id")
    private Long postUserId;

    @JsonProperty("participant_id")
    private Long participantId;

    private NotificationType type;
}