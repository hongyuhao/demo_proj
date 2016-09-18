package com.yuhao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.frogs.framework.util.StringUtil;
import com.yuhao.response.Response;
import com.yuhao.response.ShowResponse;
import com.yuhao.service.ExcelService;
import com.yuhao.util.BaiduMapUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hongyuhao
 * @Description: TODO
 * @date 16-9-4 下午9:38
 */
@Service
@Transactional
public class ExcelServiceImpl implements ExcelService {

    private static final Logger log = LoggerFactory.getLogger(ExcelServiceImpl.class);

    @Override
    public Response doUpload(HttpServletRequest request) {
        ShowResponse<String> response = new ShowResponse<String>();
        try {
            MultipartHttpServletRequest muiltipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = muiltipartRequest.getFileMap();
            String basePath = request.getSession().getServletContext().getRealPath("/upload/excel/");


            File file = new File(basePath);
            if(!file.exists()) {
                file.mkdirs();
            }

            MultipartFile multipartFile = muiltipartRequest.getFile("file");
            String fileType = this.getFileType(multipartFile.getOriginalFilename());
            String fileName = basePath + "/" + System.currentTimeMillis() + fileType;
            String outputFileName = basePath + "/output_" + System.currentTimeMillis() + fileType;
            File uploadFile = new File(fileName);
            File outputFile = new File(outputFileName);
            multipartFile.transferTo(uploadFile);

            this.handleFile(uploadFile, outputFile);
            response.setInfo("/upload/excel/" + outputFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private void handleFile(File uploadFile, File ouputFile) throws Exception {
        InputStream inputStream;
        List<String> list = new ArrayList<String>();
        inputStream = new FileInputStream(uploadFile);
        Workbook book = (Workbook) WorkbookFactory.create(inputStream);// 获取临时文件workbook

        Sheet sheet = book.getSheetAt(0);// 获取临时文件workbook的第一个sheet
        int rowsCount = sheet.getLastRowNum();// 获取临时文件workbook的第一个sheet的总条数
        if (rowsCount < 0) {
            throw new Exception("excel不能为空");
        } else {

            checkFormate(sheet.getRow(0));

            for (int i = 1; i <= rowsCount; i++) {
                String province = "";
                String city = "";
                String address = "";
                boolean needCheck = false;

                Row row = sheet.getRow(i);
                province = row.getCell(0).getStringCellValue().trim();
                city = row.getCell(1).getStringCellValue().trim();
                address = row.getCell(2).getStringCellValue().trim();
                log.info("func[handleFile] handle province:{}, city:{}, address:{}", new Object[]{province, city, address});

                JSONObject location = BaiduMapUtil.searchLocation(address, province + city );
                log.info("func[handleFile] result from baidu:{}", new Object[]{location});
                if(location.containsKey("result") && location.getJSONObject("result").containsKey("location")) {

                    JSONObject locationObject = location.getJSONObject("result").getJSONObject("location");
                    log.info("func[handleFile] result from baidu[result.location]:{}", new Object[]{locationObject});
                    if(StringUtil.isEmpty(locationObject.getString("lat")) || StringUtil.isEmpty(locationObject.getString("lng"))) {
                        needCheck = true;
                    }

                    JSONObject addressDetail = BaiduMapUtil.getAddressDetail(locationObject.getString("lat") , locationObject.getString("lng"));
                    log.info("func[handleFile] queryAddressDetail from baidu:{}", new Object[]{addressDetail});
                    JSONObject addressJson = addressDetail.getJSONObject("result").getJSONObject("addressComponent");
                    String baiduCity = addressJson.getString("city");
                    String baiduProvince = addressJson.getString("province");
                    String baiduDistrict = addressJson.getString("district");
                    row.createCell(6);
                    row.getCell(6).setCellValue(baiduProvince);
                    row.createCell(7);
                    row.getCell(7).setCellValue(baiduCity);
                    row.createCell(8);
                    row.getCell(8).setCellValue(baiduDistrict);

                    String baiduAdd = address.replaceFirst(province + "省", "").replaceFirst(province, "").replaceFirst(city + "市", "").replaceFirst(city, "").replaceFirst(baiduDistrict, "");
                    row.createCell(9);
                    row.getCell(9).setCellValue(baiduAdd);
                    if(baiduCity.indexOf(city) == -1 || baiduProvince.indexOf(province) == -1) {
                        needCheck = true;
                    }
                } else {
                    needCheck = true;
                }

                if(needCheck) {
                    row.createCell(10);
                    row.getCell(10).setCellValue("需要人工校验");
                }

            }
            book.write(new FileOutputStream(ouputFile));
        }
    }

    public void checkFormate(Row row) throws Exception {
        String provinceTitle = row.getCell(0).getStringCellValue();
        String cityTitle = row.getCell(1).getStringCellValue();
        String addressTitle = row.getCell(2).getStringCellValue();
        if(StringUtil.isEmpty(provinceTitle) || StringUtil.isEmpty(cityTitle) || StringUtil.isEmpty(addressTitle)) {
            throw  new Exception("格式错误");
        }
    }


    /**
     * 获取文件类型
     * @param fileName
     * @return
     */
    private String getFileType(String fileName) {
        if(fileName.indexOf(".") != -1) {
            return fileName.substring(fileName.indexOf("."));
        }
        return "";
    }
}
