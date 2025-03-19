package com.uplus.ureka.service.user.change;

import com.uplus.ureka.repository.user.change.MemberMapper_change_pw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService_change_pw {

    private final MemberMapper_change_pw mapper;

    @Autowired
    public MemberService_change_pw(MemberMapper_change_pw mapper){
        this.mapper = mapper;

    }

    public String changePassword(String email, String currentPassword, String newPassword){
        if (emailNotExist(email)) {
            throw new IllegalArgumentException("현재 이메일로 가입된 계정이 없습니다. 다시 이메일을 확인해주세요");
        }

        if (!verifyCurrentPassWord(email,currentPassword)) {
            throw new IllegalArgumentException("현재 패스워드가 일치하지 않습니다. 다시 입력해주세요.");
        }

        mapper.updateNewPassWordByEmail(email,newPassword);

        return "비밀번호가 변경되었습니다.";
    }

    private boolean verifyCurrentPassWord(String email,String currentPass){
        // 현재 패스워드가 맞는지 확인하는 코드 작성
        String passwordFromDB = mapper.getPasswordByEmail(email);
        if(passwordFromDB == null) {
            throw new IllegalArgumentException("현재 이메일과 비밀번호가 일치하지 않습니다.");
        }

        return currentPass.equals(passwordFromDB);
    }

    private boolean emailNotExist(String email) {
        // 이메일이 존재하는지 확인하는 코드 작성

        return mapper.findUserByEmail(email) == null;

    }

}