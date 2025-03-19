package com.uplus.ureka.repository.user.mypage;

import com.uplus.ureka.dto.user.Mypage.MyPageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MyPageMapper {

    // 회원 정보를 ID를 통해 조회(MemberDetail 및 Member_table 조인)
    MyPageDTO selectMemberDetailsById(String memberId);

    // 이미지 업로드
//    int updateProfileImageByMemberId(String memberId, byte[] profileImage);
    int updateProfileImage(MyPageDTO myPageDTO);

    // 회원 정보 수정(두개의 테이블 업데이트로 인해 2번)
    int updateMemberInfo(MyPageDTO memberInfo);
//    int updateMemberInfoDetail(MyPageDTO memberInfo);

    // 네비게이션에 이미지 띄우기
    String selectProfileImageByMemberId(String memberId);

}