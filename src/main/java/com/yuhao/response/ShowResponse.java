package com.yuhao.response;

/**
 * @author hongyuhao
 * @Description: TODO
 * @date 16-9-6 上午12:58
 */
public class ShowResponse<T> extends  Response{

    private T info;

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
}
