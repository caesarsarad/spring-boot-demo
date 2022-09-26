package com.example.springbootdemo.service;

import com.example.springbootdemo.dao.MathDao;
import com.example.springbootdemo.dto.MathDto;
import com.example.springbootdemo.entity.Math;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.vo.MathVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Component
public class MathService {

    public  MathDao mathDao;

    public MathService(MathDao mathDao) {
        this.mathDao = mathDao;
    }

    public int addMath(String questionarea, String answer,String filePath, User user){
        MathDto mathDto = new MathDto();
        mathDto.setSubjectId(2);
        mathDto.setGrade(user.getUserGrade());
        mathDto.setUserId(user.getUserId());
        mathDto.setQuestionText(questionarea);
        mathDto.setQuestionImage(filePath);
        mathDto.setAnswer(answer);
        mathDto.setWrongCount(0);
        mathDto.setAddTimestamp(new Timestamp(System.currentTimeMillis()));
        return mathDao.insertOneMath(mathDto);
    }

    public PageInfo<Math> searchmath(MathVo mathVo, Integer pageNum) {

        return mathDao.findMathByCondition(mathVo,pageNum);


    }

    public Math getMathDetail(Integer questionId) {
        Math math= mathDao.findMathById(questionId);
        return math;
    }
}
