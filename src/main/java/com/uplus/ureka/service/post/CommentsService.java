package com.uplus.ureka.service.post;

import com.uplus.ureka.dto.PageResponseDTO;
import com.uplus.ureka.exception.CustomExceptions;
import com.uplus.ureka.dto.post.CommentsRequestDTO;
import com.uplus.ureka.dto.post.CommentsResponseDTO;
import com.uplus.ureka.repository.post.CommentsMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class CommentsService {
    private final CommentsMapper commentsMapper;

    //댓글 작성
    public CommentsResponseDTO createComment(CommentsRequestDTO requestDTO){
        Long postId = requestDTO.getPostId();
        Long userId = requestDTO.getUserId();
        String content = requestDTO.getContent();

        commentsMapper.createComment(requestDTO);

        CommentsResponseDTO responseDTO = commentsMapper.findCommentwithCommentId(requestDTO.getCommentId());
        return responseDTO;
    }

    //댓글 수정
    public CommentsResponseDTO updateComment(CommentsRequestDTO requestDTO){
        Long postId = requestDTO.getPostId();
        Long userId = requestDTO.getUserId();
        Long commentId = requestDTO.getCommentId();
        String content = requestDTO.getContent();

        commentsMapper.updateComment(commentId, postId, userId, content);
        CommentsResponseDTO responseDTO = commentsMapper.findCommentwithCommentId(commentId);
        return responseDTO;
    }

    // 댓글 삭제
    public void deleteComment(CommentsRequestDTO requestDTO){
        Long postId = requestDTO.getPostId();
        Long userId = requestDTO.getUserId();
        Long commentId = requestDTO.getCommentId();

        commentsMapper.deleteComment(commentId, postId, userId);
    }

    //댓글 목록 조회
    public PageResponseDTO<CommentsResponseDTO> getCommentsByPostId(Long postId, Pageable pageable) {
        int offset = pageable.getPageNumber()* pageable.getPageSize();
        int limit = pageable.getPageSize();

        List<CommentsResponseDTO> comments =
                commentsMapper.findCommentsByPostId(postId, new RowBounds(offset, limit));

        // 2. 총 댓글 개수 조회
        long totalElements = commentsMapper.countCommentsByPostId(postId);

        // 3. 페이징 응답 객체 생성 및 반환
        Page<CommentsResponseDTO> page = new PageImpl<>(comments, pageable, totalElements);
        return new PageResponseDTO<>(page);
    }
}
