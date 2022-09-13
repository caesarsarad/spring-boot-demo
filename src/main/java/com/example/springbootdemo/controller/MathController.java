package com.example.springbootdemo.controller;

import com.example.springbootdemo.service.MathService;
import com.example.springbootdemo.vo.MathVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class MathController {

    public final MathService mathService;

    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    @GetMapping("/addmath")
    public String addmath(){
        return "study/addmath";
    }

    @GetMapping("/mathlist")
    public String mathlist(){
        return "study/mathlist";
    }



    @PostMapping("/mathupload")
    public String addmath(String questionarea, @RequestParam("image")MultipartFile multipartFile, HttpServletRequest req) throws IOException {
//        String time = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
//        String filePath = Constant.MATH_IMAGE_PATH+Constant.STRING_MATH +Constant.STRING_UNDERLINE+time+multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
//        File file = new File(filePath);
//        multipartFile.transferTo(file);
        req.setAttribute("msg","提交成功");
        mathService.addMath("","");
        int a= 0;
        return "study/addmath";
    }

    @PostMapping("/mathsearch")
    public String mathsearch(MathVo mathVo , Model model, ModelAndView mov){
        MathVo result = mathService.searchmath(mathVo);
        model.addAttribute("mathList",result.getMathList());
        model.addAttribute("conditionQuestionId",mathVo.getConditionQuestionId());
        model.addAttribute("conditionGrade",mathVo.getConditionGrade());
        model.addAttribute("conditionWrongCountFrom",mathVo.getConditionWrongCountFrom());
        model.addAttribute("conditionWrongCountTo",mathVo.getConditionWrongCountTo());
        return "study/mathlist";
    }

}
