package com.uplus.ureka.dto.participation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationResponseDTO {
    private Long id;
    private Long userId;
    private Long postId;
    private String nickname;
    private String title;
    private String status;
    private LocalDateTime joinedAt;
}