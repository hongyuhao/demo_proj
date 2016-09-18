package com.yuhao.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frogs.framework.util.httpclient.ContentTypeEnum;
import com.frogs.framework.util.httpclient.HttpClientUtil;
import com.frogs.framework.util.httpclient.PostObject;

/**
 * @author hongyuhao
 * @Description: TODO
 * @date 16-9-4 下午10:49
 */
public class BaiduMapUtil {

    private static String akey = "tUvexGQfoGSeM2RwvR5z1rh9UUCF3FGG";

    private static final String SEARCH_URL = "http://api.map.baidu.com/place/v2/search?output=json&ak=#AK#&q=#ADDRESS#&region=#REGION#";

    private static final String SEARCH_ADDRESS_URL = "http://api.map.baidu.com/geocoder/v2/?output=json&ak=#AK#&address=#ADDRESS#&city=#CITY#";

    private static final String SEARCH_ADDRESS_DETAIL_URL = "http://api.map.baidu.com/geocoder/v2/?output=json&ak=#AK#&location=#LOCATION#";

    /**
     * 获取经纬度
     * @param address
     * @return
     */
    public static JSONObject getLatitudeAndLongitude(String region, String address) {
        PostObject po = new PostObject();
        po.setContentType(ContentTypeEnum.WWW_FORM.toString());
        po.setUrl(SEARCH_URL.replace("#ADDRESS#", address).replace("#REGION#", region).replace("#AK#", akey));
        Object object = HttpClientUtil.get(po);
        JSONObject jsonObject = JSON.parseObject(object.toString());
        return jsonObject;
    }

    /**
     * 获取经纬度
     * @param address
     * @param city
     * @return
     */
    public static JSONObject searchLocation(String address, String city) {
        PostObject po = new PostObject();
//        po.setContent(SEARCH_ADDRESS_PARAM.replace("#ADDRESS#", address).replace("#CITY#", city).replace("#AK#", akey));
        po.setContentType(ContentTypeEnum.WWW_FORM.toString());
        po.setUrl(SEARCH_ADDRESS_URL.replace("#ADDRESS#", address).replace("#CITY#", city).replace("#AK#", akey));
        Object object = HttpClientUtil.get(po);
        JSONObject jsonObject = JSON.parseObject(object.toString());
        return jsonObject;
    }

    /**
     * 根据经纬度获取地址详情信息
     * @param latitude
     * @param longitude
     * @return
     */
    public static JSONObject getAddressDetail(String latitude, String longitude) {
        //location:33.14813680671912,111.49169177052103  latitude,longitude
        String location = latitude + "," + longitude;
        PostObject po = new PostObject();
        po.setContentType(ContentTypeEnum.WWW_FORM.toString());
        System.out.println(SEARCH_ADDRESS_DETAIL_URL.replace("#LOCATION#", location).replace("#AK#", akey));
        po.setUrl(SEARCH_ADDRESS_DETAIL_URL.replace("#LOCATION#", location).replace("#AK#", akey));
        Object object = HttpClientUtil.get(po);
        JSONObject jsonObject = JSON.parseObject(object.toString());
        return jsonObject;
    }



}
