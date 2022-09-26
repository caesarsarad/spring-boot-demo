package com.example.springbootdemo.mapper;

import com.example.springbootdemo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LoginMapper {
    @Select("SELECT user_id,user_name,user_name_chs,user_grade FROM user where user_name = #{userName} and password = #{password}")
    @ResultType(com.example.springbootdemo.entity.User.class)
    @Results({
            @Result(property = "userId",  column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userNameChs", column = "user_name_chs"),
            @Result(property = "userGrade", column = "user_grade"),
    })
    User findUserByNamePass(@Param("userName") String userName, @Param("password") String password);

}
