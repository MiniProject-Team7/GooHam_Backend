package com.uplus.ureka.dto.post;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter

public class CommentsResponseDTO {
    private Long id;
    private Long postId;
    private Long userId;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isModified;

    public CommentsResponseDTO(Long id, Long postId, Long userId, String nickname, String content,
                               LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isModified)
    {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isModified = isModified;
    }

}
