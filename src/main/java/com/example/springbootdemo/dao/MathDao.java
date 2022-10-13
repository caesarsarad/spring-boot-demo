package com.example.springbootdemo.dao;

import com.example.springbootdemo.dto.MathDto;
import com.example.springbootdemo.entity.Math;
import com.example.springbootdemo.mapper.MathMapper;
import com.example.springbootdemo.util.Constant;
import com.example.springbootdemo.vo.MathVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    public PageInfo<Math> findMathByCondition(MathVo mathVo, Integer pageNum) {
        PageHelper.startPage(pageNum, Constant.INTEGER_MATH_PAGE_SIZE);
        List<Math> result = mapper.findMathByCondition(mathVo);
        PageInfo<Math> pageInfo = new PageInfo<>(result);
//        return mapper.findMathByCondition(mathVo);
        return  pageInfo;
    }


    public Math findMathById(Integer questionId) {
        return mapper.findMathById(questionId);
    }

    public int updateOneMath(MathDto mathDto) {
        return mapper.updateOneMath(mathDto);
    }

    public List<Math> findMathByIdList(List<String> printList) {
        return mapper.findMathByIdList(printList);
    }
}
