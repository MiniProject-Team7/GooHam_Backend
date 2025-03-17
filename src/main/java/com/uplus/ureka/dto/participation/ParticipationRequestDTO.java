package com.uplus.ureka.dto.participation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDTO {
    private Long userId;       // 신청자 ID
    private Long postId;       // 게시글 ID
    private Long participantId; // 참여 ID (필요할 경우 추가)
}
