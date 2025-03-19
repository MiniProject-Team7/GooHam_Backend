package com.uplus.ureka.repository.user.delete;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper_delete {

    void deleteByIdAndPassword(@Param("id") String id, @Param("password") String password);

    String findUserById(@Param("id")String id);

    String getPasswordById(@Param("id")String id);
}