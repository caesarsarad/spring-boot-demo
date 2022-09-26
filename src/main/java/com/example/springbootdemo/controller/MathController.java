package com.example.springbootdemo.controller;

import com.example.springbootdemo.entity.Math;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.MathService;
import com.example.springbootdemo.util.Constant;
import com.example.springbootdemo.vo.MathVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    public String mathlist(Model model){
        model.addAttribute("mathVo",new MathVo());

        PageInfo<Math> pageInfo = new PageInfo<>(new ArrayList<Math>());
        model.addAttribute("mathPageInfo",pageInfo);

        return "study/mathlist";
    }

    @GetMapping("mathfile")
    public String mathfile(){
        return "file";
    }

    @GetMapping("/getdetail")
    @ResponseBody
    public Math getDetail(HttpServletRequest req, HttpServletResponse resp) {
        Integer questionId = Integer.valueOf(req.getParameter("questionid"));
        Math math = mathService.getMathDetail(questionId);
//        resp.setCharacterEncoding("UTF-8");
//        try {
//            resp.getWriter().write(math.getQuestionText());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return math;

    }

    @PostMapping("/mathupload")
    public String addmath(String questionarea, String answer,@RequestParam("image")MultipartFile multipartFile, HttpServletRequest req) throws IOException {
        String time = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        String filePath = Constant.STRING_MATH_IMAGE_PATH+Constant.STRING_MATH +Constant.STRING_UNDERLINE+time+multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        File file = new File(filePath);
        String hostFilePath = Constant.STRING_IMAGE_HOST_APP_PATH + Constant.STRING_IMAGE_FILE_SAVE_PATH + Constant.STRING_MATH +Constant.STRING_UNDERLINE+time+multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        multipartFile.transferTo(file);
        req.setAttribute("msg","提交成功");


        HttpSession session =  req.getSession();
        User user = (User)session.getAttribute("user");

        mathService.addMath(questionarea,answer,hostFilePath,user);
        int a= 0;
        return "study/addmath";
    }

    @RequestMapping(value = "/mathsearch")
    public String mathsearch(@RequestParam(value="pageNum",required = false,defaultValue = "1")Integer pageNum
            ,MathVo mathVo , Model model, ModelAndView mov){

        PageInfo<Math> pageInfo = mathService.searchmath(mathVo,pageNum);
        mathVo.setMathList(pageInfo.getList());

        model.addAttribute("mathPageInfo",pageInfo);
        model.addAttribute("mathVo",mathVo);
        model.addAttribute("mathList",pageInfo.getList());
        model.addAttribute("conditionQuestionId",mathVo.getConditionQuestionId());
        model.addAttribute("conditionGrade",mathVo.getConditionGrade());
        model.addAttribute("conditionWrongCountFrom",mathVo.getConditionWrongCountFrom());
        model.addAttribute("conditionWrongCountTo",mathVo.getConditionWrongCountTo());
        return "study/mathlist";
    }

    @PostMapping(value = "/mathsearch",params = "action=mathprint")
    public String printmath(MathVo mathVo , HttpServletResponse response) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String pdfFilePath= Constant.PDF_FILE_PATH + Constant.STRING_MATH + Constant.STRING_UNDERLINE + sdf.format(new Date()) + Constant.STRING_PDF_SUFFIX;
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(Constant.PDF_TEMP_PATH), new PdfWriter(pdfFilePath));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument,false);
        PdfFont font = PdfFontFactory.createFont(Constant.PDF_FONT_PATH);
        DeviceRgb color = new DeviceRgb(0,0,0);
        Map<String,String> map = new HashMap<>();


        List<Math> mathList = mathVo.getMathList();
        int questionCount = 1;
        for(Math math : mathList){
            if(math.getCheckbox() == true){
                map.put("question"+String.valueOf(questionCount%5),math.getQuestionText());
                questionCount++;
            }

        }

        for(Map.Entry<String,String> entry: map.entrySet()){
            form.getFormFields().get(entry.getKey()).setValue(entry.getValue()).setColor(color).setFont(font);
        }
        form.flattenFields();
        pdfDocument.close();

        //PDF文件地址
        File file = new File(pdfFilePath);
        if (file.exists()) {
            byte[] data = null;
            FileInputStream input = null;
            try {
                input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
            } catch (Exception e) {
                System.out.println("pdf文件处理异常：" + e);
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "study/mathlist";
    }

}
