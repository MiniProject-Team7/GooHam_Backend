package com.uplus.ureka.domain.participation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Participation {
    private Long id;
    private Long userId;     // 신청자 ID
    private Long postId;     // 신청한 게시글 ID
    private String status;   // 신청 상태 (예: "신청", "취소")
    private LocalDateTime joinedAt; // 신청 시간
}