package com.uplus.ureka.service.user.mypage;

import com.uplus.ureka.dto.user.Mypage.MyPageDTO;
import com.uplus.ureka.repository.user.mypage.MyPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class MyPageServicelmpl implements MyPageService{

    @Autowired
    private MyPageMapper mypageMapper;

    //회원 데이터베이스에 저장된 데이터들을 가져옴
    @Override
    public MyPageDTO getMemberDetails(String memberId) {
        return mypageMapper.selectMemberDetailsById(memberId);
    }

    // 회원의 프로필 이미지를 업데이트
    @Override
    public String updateProfileImage(String memberId, MultipartFile profileImage) {
        int result = 0;
        try {
            String origFilename = profileImage.getOriginalFilename();
//            String filename = new MD5Generator(origFilename).toString();
            String filename = origFilename;
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
//            String savePath = System.getProperty("user.dir") + "\\files";
            //String savePath = "C:\\rcp\\teamproject_test_1\\src\\main\\reactfront\\public";
            //금주님에 한것 String savePath = "C:\\rcp\\teamproject_test_1\\src\\main\\resources\\static\\uploadimg"; // 배포시 경로 바꿔야함
            String savePath = "C:\\rcp\\teamproject_test_1\\src\\main\\resources\\static\\uploadimg";

            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try{
                    new File(savePath).mkdir();
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            profileImage.transferTo(new File(filePath)); // 파일 저장

            MyPageDTO myPageDTO = new MyPageDTO();
            myPageDTO.setMember_id(memberId);
            //myPageDTO.setMember_profile_image(filename);
            System.out.println("==============================");
            System.out.println("memberId:" + memberId + ",filename:" + filename);
            //result = mypageMapper.updateProfileImage(myPageDTO);
            return filename;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
        //return mypageMapper.updateProfileImageByMemberId(memberId, profileImage);
    }


    //회원의 정보를 업데이트(수정)
    @Override
    @Transactional
    public int updateMemberInfo(MyPageDTO memberInfo) {
        mypageMapper.updateMemberInfo(memberInfo);
//        mypageMapper.updateMemberInfoDetail(memberInfo);
        return 1;
    }

    // 네비게이션에 이미지 띄우기
    @Override
    public String getProfileImageByMemberId(String memberId) {
        return mypageMapper.selectProfileImageByMemberId(memberId);
    }
}