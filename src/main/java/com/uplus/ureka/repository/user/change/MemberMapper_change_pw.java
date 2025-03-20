package com.uplus.ureka.repository.user.change;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper_change_pw {
    void updateNewPassWordByEmail(@Param("email")String email, @Param("password")String password);
    String findUserByEmail(@Param("email") String email);
    String getPasswordByEmail(@Param("email")String email);
}