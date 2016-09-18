package com.yuhao.response;

/**
 * @author hongyuhao
 * @Description: TODO
 * @date 16-9-4 下午9:35
 */
public class Response {

    private int code = 1;

    private String tip = "请求成功";

    public Response() {
        this.code = 1;
        this.tip = "请求成功";
    }

    public Response(int code, String tip) {
        this.code = code;
        this.tip = tip;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
