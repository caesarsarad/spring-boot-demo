package com.example.springbootdemo.vo;

import com.example.springbootdemo.entity.Math;

import java.util.List;

public class MathVo {
    private List<Math> mathList;

    private String conditionQuestionId;

    private String conditionGrade;

    private String conditionWrongCountFrom;

    private String conditionWrongCountTo;

    public String getConditionQuestionId() {
        return conditionQuestionId;
    }

    public void setConditionQuestionId(String conditionQuestionId) {
        this.conditionQuestionId = conditionQuestionId;
    }

    public String getConditionGrade() {
        return conditionGrade;
    }

    public void setConditionGrade(String conditionGrade) {
        this.conditionGrade = conditionGrade;
    }

    public String getConditionWrongCountFrom() {
        return conditionWrongCountFrom;
    }

    public void setConditionWrongCountFrom(String conditionWrongCountFrom) {
        this.conditionWrongCountFrom = conditionWrongCountFrom;
    }

    public String getConditionWrongCountTo() {
        return conditionWrongCountTo;
    }

    public void setConditionWrongCountTo(String conditionWrongCountTo) {
        this.conditionWrongCountTo = conditionWrongCountTo;
    }

    public List<Math> getMathList() {
        return mathList;
    }

    public void setMathList(List<Math> mathList) {
        this.mathList = mathList;
    }

}
