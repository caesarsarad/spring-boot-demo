package com.example.springbootdemo.service;

import com.example.springbootdemo.dao.MathDao;
import com.example.springbootdemo.dto.MathDto;
import com.example.springbootdemo.entity.Math;
import com.example.springbootdemo.vo.MathVo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@Component
public class MathService {

    public  MathDao mathDao;

    public MathService(MathDao mathDao) {
        this.mathDao = mathDao;
    }

    public int addMath(String questionarea, String filePath){
        MathDto mathDto = new MathDto();
        mathDto.setSubjectId(2);
        mathDto.setGrade(1);
        mathDto.setUserId(1);
        mathDto.setQuestionText("第一题");
        mathDto.setQuestionImage("D:\\");
        mathDto.setAnswer("答案很好");
        mathDto.setWrongCount(0);
        mathDto.setAddTimestamp(new Timestamp(System.currentTimeMillis()));
        return mathDao.insertOneMath(mathDto);
    }

    public MathVo searchmath(MathVo mathVo) {
        List<Math> result = mathDao.findMathByCondition(mathVo);
        mathVo.setMathList(result);
        return mathVo;

    }
}
