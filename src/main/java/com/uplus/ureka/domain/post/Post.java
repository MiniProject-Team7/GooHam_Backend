package com.uplus.ureka.domain.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Long id;                  //모집 글 ID
    private Long userId;              //작성자 ID
    private String title;             //모집 글 제목
    private String content;           //모집 글 내용
    private Integer maxParticipants;  //최대 참여자 수
    private Integer currentParticipants; //현재 참여자 수
    private Long categoryId;          //카테고리 ID
    private String status;            //모집 상태
    private LocalDateTime scheduleStart; //일정 시작 시간
    private LocalDateTime scheduleEnd;   //일정 종료 시간
    private String location;          //모집 장소
    private LocalDateTime createdAt;  //모집 글 생성 시간
    private LocalDateTime updatedAt;  //모집 글 수정 시간
}