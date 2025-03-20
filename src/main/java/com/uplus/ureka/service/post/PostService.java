package com.uplus.ureka.service.post;

import com.uplus.ureka.dto.post.PostRequestDTO;
import com.uplus.ureka.dto.post.PostResponseDTO;
import com.uplus.ureka.dto.PageResponseDTO;
import com.uplus.ureka.exception.CustomExceptions;
import com.uplus.ureka.repository.post.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.apache.ibatis.session.RowBounds;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostMapper postMapper;

    //모집 글 작성
    public PostResponseDTO insertPost(PostRequestDTO requestDTO) {
        Long userId = requestDTO.getUserId();

        //사용자 확인
        if (!postMapper.checkUserExists(requestDTO.getUserId())) {
            throw new CustomExceptions("존재하지 않는 회원입니다.");
        }

        // 모집 글 삽입 후 자동 생성된 id를 requestDTO에 설정
        postMapper.insertPost(requestDTO);

        // insertPost 후, 자동 생성된 id를 이용하여 해당 게시글을 찾아 반환
        return postMapper.findPostById(requestDTO.getPostId());  // 여기서 requestDTO.getId()를 사용하여 해당 글의 상세 조회
    }

    //모집 글 삭제
    public void removePost(Long postId, Long userId) {
        //작성한 유저가 맞는 지 확인
        //모집 글 존재 확인
        if (!postMapper.checkExistPost(postId)) {
            throw new CustomExceptions("해당 모집 글이 존재하지 않습니다.");
        }

        postMapper.removePost(postId);
    }

    //모집 글 수정
    public PostResponseDTO updatePost(PostRequestDTO requestDTO) {
        //모집 글 존재 확인
        if (!postMapper.checkExistPost(requestDTO.getPostId())) {  // 수정할 때는 id를 사용
            throw new CustomExceptions("해당 모집 글이 존재하지 않습니다.");
        }
        postMapper.updatePost(requestDTO);

        return postMapper.findPostById(requestDTO.getPostId());
    }


    //모집 글 조회
    public PageResponseDTO<PostResponseDTO> findPostsWithFilters(
            Long categoryId, String status, String location,
            LocalDateTime scheduleStartAfter, LocalDateTime scheduleEndBefore,
            String sortField, String sortOrder, Pageable pageable) {

        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();
        RowBounds rowBounds = new RowBounds(offset, limit);

        List<PostResponseDTO> posts = postMapper.findPostsWithFilters(
                categoryId, status, location, scheduleStartAfter, scheduleEndBefore,
                sortField, sortOrder, rowBounds
        );

        long totalElements = postMapper.countPostsWithFilters(
                categoryId, status, location, scheduleStartAfter, scheduleEndBefore
        );
        Page<PostResponseDTO> page = new PageImpl<>(posts, pageable, totalElements);
        return new PageResponseDTO<>(page);
    }

    //모집 글 상세 조회
    public PostResponseDTO findPostById(Long postId) {
        if (!postMapper.checkExistPost(postId)) {
            throw new CustomExceptions("해당 모집 글이 존재하지 않습니다.");
        }
        return postMapper.findPostById(postId);
    }

}