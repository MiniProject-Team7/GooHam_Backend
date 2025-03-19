package com.uplus.ureka.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentsRequestDTO {
    private String content; // 댓글 내용
    private Long userId; // 작성자 ID
    private Long postId; // 게시글 ID
    private Long commentId; // 댓글 ID (PK)
}