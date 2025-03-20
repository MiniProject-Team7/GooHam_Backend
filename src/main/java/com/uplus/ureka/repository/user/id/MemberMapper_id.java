package com.uplus.ureka.repository.user.id;

import org.apache.ibatis.annotations.Mapper;
import java.util.Map;

@Mapper
public interface MemberMapper_id {
    String findMemberIdByNameAndEmail(Map<String, Object> params);
}