package com.uplus.ureka.service.user.delete;

import com.uplus.ureka.repository.user.delete.MemberMapper_delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService_delete {

    private final MemberMapper_delete mapper;

    @Autowired
    public MemberService_delete(MemberMapper_delete mapper){
        this.mapper = mapper;
    }

    public String deleteAccount(String id, String password){
        if (accountNotExist(id)) {
            throw new IllegalArgumentException("현재 아이디로 가입된 계정이 없습니다. 다시 아이디를 확인해주세요");
        }

        if (!verifyPassword(id, password)) {
            throw new IllegalArgumentException("현재 패스워드가 일치하지 않습니다. 다시 입력해주세요.");
        }

        mapper.deleteByIdAndPassword(id, password);

        return "계정이 성공적으로 삭제되었습니다.";
    }

    private boolean verifyPassword(String id,String password){
        // 현재 패스워드가 맞는지 확인하는 코드 작성
        String passwordFromDB = mapper.getPasswordById(id);

        if(passwordFromDB == null) {
            throw new IllegalArgumentException("현재 아이디와 비밀번호가 일치하지 않습니다.");
        }

        return password.equals(passwordFromDB);
    }

    private boolean accountNotExist(String id) {
        // 아이디가 존재하는지 확인하는 코드 작성

        return mapper.findUserById(id) == null;
    }
}