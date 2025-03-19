package com.uplus.ureka.dto.user.Mypage;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MyPageDTO {

    private int details_id; //설문으로 받는 정보들을 판별할 id

    private String member_id;   //아이디

    private String member_name; //이름

    private String member_nickname;     //닉네임

    //private String member_profile_image;    //프로필사진

    //private String ageGroup; // 연령대

    //private String category_name; //카테고리 이름

    private String member_introduce; //멤버 소개



}