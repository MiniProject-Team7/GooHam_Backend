package com.uplus.ureka.controller.user.id;

import com.uplus.ureka.service.user.id.MemberService_id;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/gooham/users")
public class MemberController_id {

    private final MemberService_id memberServiceId;

    public MemberController_id(MemberService_id memberServiceId) {
        this.memberServiceId = memberServiceId;
    }

    @GetMapping("/find-id")
    public String findId(@RequestParam(required = false) String name,
                         @RequestParam(required = false) String email,
                         Model model) {

        if (name != null && email != null) {
            model.addAttribute("message", "회원님의 ID는 " +  this.memberServiceId.findId(name,email) + "입니다");
        }

        return "find-id"; // HTML 파일 이름 (확장자 제외)
    }

    @GetMapping("/api/find-id")
    @ResponseBody
    public Map<String, String> findIdApi(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String email,
                                         Model model) {
        Map<String, String> result = new HashMap<>();
        if (name != null && email != null) {
            String foundId = this.memberServiceId.findId(name,email);
            if (foundId != null) {
                result.put("message", "회원님의 ID는 " + foundId + "입니다");
            } else if (foundId == null){
                result.put("message", "입력하신 이름과 이메일로 등록된 회원 정보를 찾을 수 없습니다.");
            }
        } else {
            result.put("message", "올바른 이름과 이메일을 입력해주세요.");
        }
        return result;
    }
}