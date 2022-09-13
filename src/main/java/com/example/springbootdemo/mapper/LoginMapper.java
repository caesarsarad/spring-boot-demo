package com.example.springbootdemo.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LoginMapper {
    @Select("SELECT count(*) FROM user where user_name = #{userName} and password = #{password}")
    @ResultType(java.lang.Integer.class)
    int findUserByNamePass(@Param("userName") String userName, @Param("password") String password);

}
