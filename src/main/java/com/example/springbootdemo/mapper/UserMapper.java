package com.example.springbootdemo.mapper;

import com.example.springbootdemo.vo.UserVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface UserMapper {
    @Select("SELECT * FROM user")
    @Results({
            @Result(property = "id",  column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "address", column = "address"),
            @Result(property = "phone", column = "phone")
    })
    List<UserVo> getAllUser();
}
