package com.uplus.ureka.service.user.id;

import com.uplus.ureka.repository.user.id.MemberMapper_id;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class MemberService_id {
    private final MemberMapper_id memberMapperId;

    public MemberService_id(MemberMapper_id memberMapperId) {
        this.memberMapperId = memberMapperId;
    }

    public String findId(String name, String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);

        return memberMapperId.findMemberIdByNameAndEmail(params);
    }
}