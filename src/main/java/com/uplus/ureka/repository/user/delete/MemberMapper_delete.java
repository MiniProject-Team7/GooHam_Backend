package com.uplus.ureka.repository.user.delete;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper_delete {

    void deleteByIdAndPassword(@Param("email") String id, @Param("password") String password);

    String findUserById(@Param("email")String email);

    String getPasswordById(@Param("email")String email);
}