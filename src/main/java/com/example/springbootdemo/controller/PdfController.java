package com.example.springbootdemo.controller;

import com.example.springbootdemo.entity.Math;
import com.example.springbootdemo.service.MathService;
import com.example.springbootdemo.util.Constant;
import com.example.springbootdemo.vo.MathVo;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PdfController {

    public final MathService mathService;


    public PdfController(MathService mathService) {
        this.mathService = mathService;
    }

    @PostMapping("/printmath")
    public String prinmath(MathVo mathVo){
        for(Math math : mathVo.getMathList()){
            if(math.getCheckbox() == true){
                System.out.println(math.getQuestionId());
            }

        }
        return "/mathlist";
    }

    @RequestMapping(value = "/mathpdfpreview", method = RequestMethod.GET)
    public void pdfStreamHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        String pdfFilePath= Constant.PDF_FILE_PATH + Constant.STRING_MATH + Constant.STRING_UNDERLINE + sdf.format(new Date());
//        PdfDocument pdfDocument = new PdfDocument(new PdfReader(Constant.PDF_TEMP_PATH), new PdfWriter(pdfFilePath));
//        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument,false);
//        PdfFont font = PdfFontFactory.createFont(Constant.PDF_FONT_PATH);
//        DeviceRgb color = new DeviceRgb(0,0,0);
//        Map<String,String> map = new HashMap<>();
//        map.put("question1","第1题");
//        map.put("question2","第2题");
//        map.put("question3","第3题");
//        map.put("question4","第4题");
//        map.put("question5","第5题");
//
//        for(Map.Entry<String,String> entry: map.entrySet()){
//            form.getFormFields().get(entry.getKey()).setValue(entry.getValue()).setColor(color).setFont(font);
//        }
//        form.flattenFields();
//        pdfDocument.close();
//
//        //PDF文件地址
//        File file = new File(pdfFilePath);
//        if (file.exists()) {
//            byte[] data = null;
//            FileInputStream input = null;
//            try {
//                input = new FileInputStream(file);
//                data = new byte[input.available()];
//                input.read(data);
//                response.getOutputStream().write(data);
//            } catch (Exception e) {
//                System.out.println("pdf文件处理异常：" + e);
//            } finally {
//                try {
//                    if (input != null) {
//                        input.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//

    }
}
