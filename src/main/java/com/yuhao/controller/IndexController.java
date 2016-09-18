package com.yuhao.controller;

import com.yuhao.response.Response;
import com.yuhao.service.ExcelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author hongyuhao
 * @Description: TODO
 * @date 16-9-4 下午8:49
 */
@Controller
public class IndexController {

    private static final String EXCEL = "/excel";
    private static final String EXCEL_VIEW = "doExcel";

    private static final String UPLOAD = "/regionExcel/upload";

    @Resource
    ExcelService excelService;

    @RequestMapping(value = EXCEL)
    public String excel(HttpServletRequest request) {

        return EXCEL_VIEW;
    }

    @RequestMapping(value = UPLOAD)
    public Response upload(HttpServletRequest request) {
        Response response = excelService.doUpload(request);

        return response;
    }

}
