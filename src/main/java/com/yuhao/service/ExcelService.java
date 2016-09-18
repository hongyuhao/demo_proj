package com.yuhao.service;

import com.yuhao.response.Response;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hongyuhao
 * @Description: TODO
 * @date 16-9-4 下午9:37
 */
public interface ExcelService {

    Response doUpload(HttpServletRequest request);
}
