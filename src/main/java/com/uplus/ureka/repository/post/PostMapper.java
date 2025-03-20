package com.uplus.ureka.repository.post;

import com.uplus.ureka.dto.post.PostRequestDTO;
import com.uplus.ureka.dto.post.PostResponseDTO;
import java.time.LocalDateTime;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.*;


@Mapper
public interface PostMapper {
    //사용자 존재 확인
    @Select("SELECT EXISTS(SELECT 1 FROM USERS WHERE ID = #{userId})")
    boolean checkUserExists(@Param("userId") Long userId);

    //모집 글 존재 확인_id로 식별
    @Select("SELECT EXISTS(SELECT 1 FROM POSTS WHERE ID = #{postId} AND STATUS = '모집중')")
    boolean checkExistPost(@Param("postId") Long postId);


    //모집 글 작성_id 자동 증가, userId는 자동 입력
    @Insert("INSERT INTO POSTS (USER_ID, TITLE, CONTENT, MAX_PARTICIPANTS, CURRENT_PARTICIPANTS, CATEGORY_ID, STATUS, SCHEDULE_START, SCHEDULE_END, LOCATION, CREATED_AT, UPDATED_AT) " +
            "VALUES (#{userId},  #{title}, #{content}, #{maxParticipants}, 1, #{categoryId}, '모집중', #{scheduleStart}, #{scheduleEnd}, #{location}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "postId", keyColumn = "id")
    void insertPost(PostRequestDTO requestDTO);

    //모집 글 삭제_id 자동 처리
    @Delete("DELETE FROM POSTS WHERE ID = #{postId}")
    void removePost(@Param("postId") Long postId);

    //모집 글 수정_id 자동 처리, 수정할 내용은 DTO에서 가져옴.
    @Update("""
        UPDATE POSTS 
        SET 
            TITLE = COALESCE(#{title}, TITLE), 
            CONTENT = COALESCE(#{content}, CONTENT), 
            MAX_PARTICIPANTS = COALESCE(#{maxParticipants}, MAX_PARTICIPANTS),
            CURRENT_PARTICIPANTS = COALESCE(#{curParticipants}, CURRENT_PARTICIPANTS), 
            CATEGORY_ID = COALESCE(#{categoryId}, CATEGORY_ID), 
            STATUS = COALESCE(#{status}, STATUS), 
            SCHEDULE_START = COALESCE(#{scheduleStart}, SCHEDULE_START), 
            SCHEDULE_END = COALESCE(#{scheduleEnd}, SCHEDULE_END), 
            LOCATION = COALESCE(#{location}, LOCATION), 
            UPDATED_AT = NOW()
        WHERE ID = #{postId}
        """)
    void updatePost(PostRequestDTO requestDTO);

    //모집 글 조회
    List<PostResponseDTO> findPostsWithFilters(
            @Param("categoryId") Long categoryId,
            @Param("status") String status,
            @Param("location") String location,
            @Param("scheduleStartAfter") LocalDateTime scheduleStartAfter,
            @Param("scheduleEndBefore") LocalDateTime scheduleEndBefore,
            @Param("sortField") String sortField,
            @Param("sortOrder") String sortOrder,
            RowBounds rowBounds
    );

    long countPostsWithFilters(
            @Param("categoryId") Long categoryId,
            @Param("status") String status,
            @Param("location") String location,
            @Param("scheduleStartAfter") LocalDateTime scheduleStartAfter,
            @Param("scheduleEndBefore") LocalDateTime scheduleEndBefore
    );

    //모집 글 상세 조회
    @Select("""
        SELECT 
            P.ID AS ID,
            U.MEMBER_NICKNAME AS userName,  -- AS 뒤에 별칭 수정
            P.TITLE AS title,
            P.CONTENT AS content,
            P.MAX_PARTICIPANTS AS maxParticipants,
            P.CURRENT_PARTICIPANTS AS currentParticipants,
            C.NAME AS categoryName,
            P.STATUS AS status,
            P.SCHEDULE_START AS scheduleStart,
            P.SCHEDULE_END AS scheduleEnd,
            P.LOCATION AS location,
            P.CREATED_AT AS createdAt,
            P.UPDATED_AT AS updatedAt
        FROM 
            POSTS P
        LEFT JOIN 
            USERS U ON P.USER_ID = U.ID
        LEFT JOIN 
            CATEGORIES C ON C.ID = P.CATEGORY_ID
        WHERE 
            P.ID = #{postId}
        """)
    PostResponseDTO findPostById(@Param("postId") Long postId);
}