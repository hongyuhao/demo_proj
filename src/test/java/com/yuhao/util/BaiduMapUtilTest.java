package com.yuhao.util;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * @author hongyuhao
 * @Description: TODO
 * @date 16-9-4 下午11:28
 */
public class BaiduMapUtilTest {

    @Test
    public void testSearchAddress() {
//        JSONObject result = BaiduMapUtil.searchLocation("南宁市城东开发区步行街152号","南宁市");
        JSONObject res = BaiduMapUtil.getLatitudeAndLongitude("广西南宁", "城东开发区步行街152号");
        System.out.println(res);
    }

    @Test
    public void testAddressDetail() {
        String latitude = "22.80649293560261";
        String longitude = "108.29723355586639";
        JSONObject result = BaiduMapUtil.getAddressDetail(latitude, longitude);
        System.out.println(result);
    }
}
