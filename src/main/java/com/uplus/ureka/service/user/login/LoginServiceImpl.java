package com.uplus.ureka.service.user.login;

import com.uplus.ureka.dto.user.login.LoginDTO;
import com.uplus.ureka.dto.user.member.MemberDTO;
import com.uplus.ureka.exception.LoginException;
import  com.uplus.ureka.repository.user.login.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service("loginServiceImpl")
@Transactional
public class LoginServiceImpl implements LoginService, UserDetailsService {

    private final LoginMapper loginMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginServiceImpl(LoginMapper loginMapper, PasswordEncoder passwordEncoder) {
        this.loginMapper = loginMapper;
        this.passwordEncoder = passwordEncoder; // PasswordEncoder 초기화
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        LoginDTO user = loginMapper.findByUsername(username);

        if (user == null) {
            // user가 null인 경우 예외 발생
            throw new UsernameNotFoundException("유저를 찾을 수 없습니다.");
        }

        // 유저의 권한을 설정하는 부분
        return new org.springframework.security.core.userdetails.User(user.getMember_email(), user.getMember_password(), new ArrayList<>());
    }


    public MemberDTO checkLoin(String username, String password) throws LoginException {
        MemberDTO user = loginMapper.findByUsername2(username);

        if (user == null) {
            // user가 null인 경우 예외 발생
            throw new LoginException("유저를 찾을 수 없습니다.");
        }


        if (user.getDelflag() == 1) { // 추가: delflag가 1인 경우 예외 발생
            throw new LoginException("이미 삭제된 계정입니다.");
        }
        // password check

        if(!password.equals(user.getMember_password()))
            throw new LoginException("password error");

 /*      // 비밀번호 확인(암호화된 것 )
        if(!passwordEncoder.matches(password, user.getMember_password()))
            throw new LoginException("password error");*/

        return user;
    }

}