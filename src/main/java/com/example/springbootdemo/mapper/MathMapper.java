package com.example.springbootdemo.mapper;

import com.example.springbootdemo.dto.MathDto;
import com.example.springbootdemo.entity.Math;
import com.example.springbootdemo.vo.MathVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MathMapper {
    @Insert("insert into question (subject_id,user_id,grade,question_text,question_image,answer,wrong_count,add_timestamp) values(#{subjectId},#{userId},#{grade},#{questionText},#{questionImage},#{answer},#{wrongCount},#{addTimestamp})")
    @Options(useGeneratedKeys = true, keyProperty = "question_id")
    public int insertOneMath(MathDto mathDto);

    @Select(
//            "<script> select count(*) " +
            "<script> select question_id" +
            ",subject_id " +
            ",grade " +
            ",question_text " +
            ",question_image" +
            ",answer " +
            ",wrong_count " +
            ",add_timestamp " +
            "from question "+
            "where " +
            "1 = 1" +
            "<when test ='conditionQuestionId != null and conditionQuestionId != \"\" '  >" +
            "and question_id = ${conditionQuestionId} "+
            "</when> " +
            "<when test ='conditionGrade != null and conditionGrade != \"\" '  >" +
            "and grade = ${conditionGrade} " +
            "</when> " +
            "<when test ='conditionWrongCountFrom != null and conditionWrongCountFrom != \"\" '  >" +
            "and wrong_count >= ${conditionWrongCountFrom} " +
            "</when> " +
            "<when test ='conditionWrongCountTo != null and conditionWrongCountTo != \"\" '  >" +
            "and wrong_count >= ${conditionWrongCountTo} " +
            "</when> "
            + "</script>"
    )
    @Results({
            @Result(property = "questionId",  column = "question_id"),
            @Result(property = "subjectId", column = "subject_id"),
            @Result(property = "questionText", column = "question_text"),
            @Result(property = "questionImage", column = "question_image"),
            @Result(property = "answer", column = "answer"),
            @Result(property = "wrongCount", column = "wrong_count"),
            @Result(property = "addTimestamp", column = "add_timestamp")
    })
    public List<Math> findMathByCondition(MathVo mathVo);
}
