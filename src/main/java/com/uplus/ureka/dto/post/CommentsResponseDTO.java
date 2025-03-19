package com.uplus.ureka.dto.post;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter

public class CommentsResponseDTO {
    private Long postId;
    private Long userId;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isModified;

    public CommentsResponseDTO(Long postId, Long userId, String nickname, String content,
                               LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isModified)
    {
        this.postId = postId;
        this.userId = userId;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isModified = isModified;
    }

}
