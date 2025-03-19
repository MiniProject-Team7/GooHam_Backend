package com.uplus.ureka.dto.participation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class ParticipationResponseDTO {
    private Long userId;
    private Long postId;
    private String userName;
    private String title;
    private String status;
    private LocalDateTime joinedAt;

    public ParticipationResponseDTO(Long userId, Long postId, String userName,
                                    String title, String status, LocalDateTime joinedAt) {
       this.userId = userId;
       this.postId = postId;
       this.userName = userName;
       this.title = title;
       this.status = status;
       this.joinedAt = joinedAt;
    }
}