package com.example.springbootdemo.controller;

import com.example.springbootdemo.entity.Math;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.MathService;
import com.example.springbootdemo.util.Constant;
import com.example.springbootdemo.vo.MathVo;
import com.github.pagehelper.PageInfo;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.properties.AreaBreakType;
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

    @PostMapping("mathupdate")
    public String mathupdate(String questionarea, String answer, String hiddenQuestionId,@RequestParam("image")MultipartFile multipartFile) throws IOException {
        String filePath = null;
        String hostFilePath = null;
        if(multipartFile != null && multipartFile.getOriginalFilename() != null && !("".equals(multipartFile.getOriginalFilename()))){
            String time = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
            filePath = Constant.STRING_MATH_IMAGE_PATH+Constant.STRING_MATH +Constant.STRING_UNDERLINE+time+multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            File file = new File(filePath);
            hostFilePath = Constant.STRING_IMAGE_HOST_APP_PATH + Constant.STRING_IMAGE_FILE_SAVE_PATH + Constant.STRING_MATH +Constant.STRING_UNDERLINE+time+multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            multipartFile.transferTo(file);
        }
        mathService.updateMath(questionarea,answer,hostFilePath,hiddenQuestionId);
        return null;
    }
    @PostMapping("/mathupload")
    public String addmath(String questionarea, String answer,@RequestParam("image")MultipartFile multipartFile, HttpServletRequest req) throws IOException {
        String filePath = null;
        String hostFilePath = null;
        if(multipartFile != null && multipartFile.getOriginalFilename() != null && !("".equals(multipartFile.getOriginalFilename()))) {
            String time = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
            filePath = Constant.STRING_MATH_IMAGE_PATH + Constant.STRING_MATH + Constant.STRING_UNDERLINE + time + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            File file = new File(filePath);
            hostFilePath = Constant.STRING_IMAGE_HOST_APP_PATH + Constant.STRING_IMAGE_FILE_SAVE_PATH + Constant.STRING_MATH + Constant.STRING_UNDERLINE + time + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            multipartFile.transferTo(file);
        }
        req.setAttribute("msg","提交成功");


        HttpSession session =  req.getSession();
        User user = (User)session.getAttribute("user");

        mathService.addMath(questionarea,answer,hostFilePath,user);
        int a= 0;
        return "study/addmath";
    }

    @RequestMapping(value = "/mathsearch")
    public String mathsearch(@RequestParam(value="pageNum",required = false,defaultValue = "1")Integer pageNum
            ,MathVo mathVo , Model model, ModelAndView mov,String checkedParam,String uncheckedParam,HttpServletRequest req){

        HttpSession session = req.getSession();

        PageInfo<Math> pageInfo = mathService.searchmath(mathVo,pageNum);
        setCheckBox(session,checkedParam,uncheckedParam,pageInfo);
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
    public String printmath(MathVo mathVo , HttpServletResponse response,HttpServletRequest request) throws IOException {
        setPrintCheck(mathVo,request.getSession());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String pdfFilePath= Constant.PDF_FILE_PATH + Constant.STRING_MATH + Constant.STRING_UNDERLINE + sdf.format(new Date()) + Constant.STRING_PDF_SUFFIX;


        PdfDocument pdfDocument = new PdfDocument(new PdfReader(Constant.PDF_TEMP_PATH), new PdfWriter(pdfFilePath));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument,false);
        PdfFont font = PdfFontFactory.createFont(Constant.PDF_FONT_PATH);
        DeviceRgb color = new DeviceRgb(0,0,0);
        List<String> printList = getCheckList(request);
        List<Math> mathList = mathService.getMathByIdList(printList);

        Map<String,String> map = new HashMap<>();

//        List<Math> mathList = mathVo.getMathList();
        int questionCount = 1;
        int pageNum = 1;
        for(Math math : mathList){
                if(questionCount%Constant.INTEGER_MATH_PDF_PAGE_SIZE == 0){
                    pageNum++;
                    map.put("question"+String.valueOf(Constant.INTEGER_MATH_PDF_PAGE_SIZE),math.getQuestionText());
                    PdfDocument tempDocument = new PdfDocument(new PdfReader(Constant.PDF_TEMP_PATH));
                    PdfAcroForm tempForm = PdfAcroForm.getAcroForm(tempDocument,false);
                    Map<String, PdfFormField> tempFormFields = tempForm.getFormFields();
                    PdfFormField question1 = tempFormFields.get("question1");
                    question1.setFieldName("question4");
                    PdfFormField question2 = tempFormFields.get("question2");
                    question2.setFieldName("question5");
                    PdfFormField question3 = tempFormFields.get("question3");
                    question3.setFieldName("question6");

                    tempDocument.copyPagesTo(1,1,pdfDocument);


                    int i  = 0;



                }else {
                    map.put("question" + String.valueOf(questionCount % Constant.INTEGER_MATH_PDF_PAGE_SIZE), math.getQuestionText());
                }
                questionCount++;
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




    private void setCheckBox(HttpSession session, String checkedParam, String uncheckedParam, PageInfo<Math> pageInfo) {
        Map checkBoxMap = (Map) session.getAttribute(Constant.STRING_CHECKBOX_MAP);
        if(checkBoxMap == null){
            checkBoxMap = new HashMap();
        }
        if(checkedParam != null && uncheckedParam != null) {
            String[] checkedQuestionId = checkedParam.split(Constant.STRING_UNDERLINE);
            String[] uncheckedQuestionId = uncheckedParam.split(Constant.STRING_UNDERLINE);
            for(String checked : checkedQuestionId){
                checkBoxMap.put(checked,Constant.STRING_TRUE);
            }
            for(String unchecked : uncheckedQuestionId){
                checkBoxMap.put(unchecked,Constant.STRING_FALSE);
            }

        }else {
            session.setAttribute(Constant.STRING_CHECKBOX_MAP,null);
            return;
        }

        List<Math> mathList = pageInfo.getList();
        for(Math math:mathList){
            String checkBox = (String) checkBoxMap.get(math.getQuestionId().toString());
            if(checkBox == null || checkBox.equals(Constant.STRING_FALSE)){
                math.setCheckbox(false);
            }else{
                math.setCheckbox(true);
            }
        }
        session.setAttribute(Constant.STRING_CHECKBOX_MAP,checkBoxMap);
    }

    private List<String> getCheckList(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<String> list = new ArrayList<>();
        Map<String,String> checkBoxMap = (Map<String,String>) session.getAttribute(Constant.STRING_CHECKBOX_MAP);
        for(Map.Entry<String,String> entry: checkBoxMap.entrySet()){
            if(Constant.STRING_TRUE.equals(entry.getValue())){
                list.add(entry.getKey());
            }
        }
        return list;
    }

    private void setPage(int pageNum, PdfDocument pdfDocument) {
       PdfPage page  = pdfDocument.getPage(pageNum);
    }

    private void setPrintCheck(MathVo mathVo ,HttpSession session) {
        Map checkBoxMap = (Map) session.getAttribute(Constant.STRING_CHECKBOX_MAP);
        if(checkBoxMap == null){
            checkBoxMap = new HashMap();
        }
        if (mathVo != null) {
            List<Math> mathList = mathVo.getMathList();
            if (mathList != null) {
                for (Math math : mathList) {
                    if (math.getCheckbox()) {
                        checkBoxMap.put(math.getQuestionId().toString(),Constant.STRING_TRUE);
                    }else{
                        checkBoxMap.put(math.getQuestionId().toString(),Constant.STRING_FALSE);
                    }
                }
            }

        }
        session.setAttribute(Constant.STRING_CHECKBOX_MAP,checkBoxMap);
    }
}
