package com.uplus.ureka.controller.user;

import com.uplus.ureka.dto.user.member.MemberDTO;
import com.uplus.ureka.exception.CustomExceptions;
import com.uplus.ureka.exception.LoginException;
import com.uplus.ureka.jwt.JwtUtils;
import com.uplus.ureka.service.user.login.LoginService;
import com.uplus.ureka.service.user.login.LoginServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/gooham/users")
public class LoginController {

    // AuthenticationManager를 스프링에서 자동으로 주입받아 사용
    // 사용자 인증을 위해 필요합니다.
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    LoginService loginService;

    // JWT 토큰 생성을 위해 필요
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private LoginServiceImpl loginServiceImpl;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody MemberDTO memberDTO){
        try {
            // member_id > member_email, password 체크
            MemberDTO authenticatedMember = loginService.checkLoin(memberDTO.getMember_email(), memberDTO.getMember_password());

            // JWT 토큰 생성 및 반환
            String jwt = jwtUtils.createAccessToken(authenticatedMember.getMember_email(), authenticatedMember.getMember_name());

            // 여기에 토큰 저장 코드 추가
            loginServiceImpl.saveVerificationToken(authenticatedMember.getMember_email(), jwt);

            // 원하는 응답 형식으로 구성
            Map<String, Object> userData = new HashMap<>();
            userData.put("member_email", authenticatedMember.getMember_email());
            //userData.put("member_nickname", authenticatedMember.getMember_nickname()); // 닉네임 필드가 있다고 가정
            userData.put("member_name", authenticatedMember.getMember_name());

            Map<String, Object> data = new HashMap<>();
            data.put("token", jwt);
            data.put("user", userData);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "로그인 성공");
            response.put("data", data);

            System.out.println("Authenticated Member ID: " + authenticatedMember.getMember_email());
            return ResponseEntity.ok(response);
        }
        catch (LoginException e){
            System.out.println(e);
            // 로그인 실패 시 형식 맞춤
            Map<String, Object> response = new HashMap<>();
            response.put("status", "fail");
            response.put("message", e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        catch (CustomExceptions e){
            System.out.println(e);
            // 인증 실패 형식 맞춤
            Map<String, Object> response = new HashMap<>();
            response.put("status", "fail");
            response.put("message", "인증 실패 : 이메일이나 비밀번호를 확인해주세요");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        catch(Exception e){
            e.printStackTrace(); // 콘솔에 전체 스택 트레이스 출력
            System.out.println("로그인 오류 상세: " + e.getMessage()); // 간략한 메시지 출력

            // 서버 오류 형식 맞춤
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "서버 내부 오류");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // JWT 토큰을 담을 내부 클래스를 정의
    @Getter
    @Setter
    class JwtResponse {
        private String token;

        // 생성자를 통해 토큰을 초기화
        public JwtResponse(String token) {
            this.token = token;
        }
    }

    @GetMapping("test")
    public String test() {
        return "토큰 테스트";
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("member_email");

            // 토큰 삭제
            loginServiceImpl.logout(email);

            // 응답 생성
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "로그아웃 성공");
            response.put("data", null);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();

            // 오류 응답
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "로그아웃 중 오류 발생: " + e.getMessage());
            response.put("data", null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}