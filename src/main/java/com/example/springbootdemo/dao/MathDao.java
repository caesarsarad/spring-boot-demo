package com.example.springbootdemo.dao;

import com.example.springbootdemo.dto.MathDto;
import com.example.springbootdemo.entity.Math;
import com.example.springbootdemo.mapper.MathMapper;
import com.example.springbootdemo.vo.MathVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MathDao {
    public final MathMapper mapper;

    public MathDao(MathMapper mapper) {
        this.mapper = mapper;
    }

    public int insertOneMath(MathDto mathDto) {

        return mapper.insertOneMath(mathDto);

    }

    public List<Math> findMathByCondition(MathVo mathVo) {
        List<Math> result = mapper.findMathByCondition(mathVo);
//        return mapper.findMathByCondition(mathVo);
        return  result;
    }
}
