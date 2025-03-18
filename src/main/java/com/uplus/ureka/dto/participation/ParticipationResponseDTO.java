package com.uplus.ureka.dto.participation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class ParticipationResponseDTO {
    private Long userId;
    private Long postId;
    private String nickname;
    private String title;
    private String status;
    private LocalDateTime joinedAt;

    public ParticipationResponseDTO(Long userId, Long postId, String nickname,
                                    String title, String status, LocalDateTime joinedAt) {
       this.userId = userId;
       this.postId = postId;
       this.nickname = nickname;
       this.title = title;
       this.status = status;
       this.joinedAt = joinedAt;
    }
}