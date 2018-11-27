package com.zhilink.common.bean.local;

/**
 * 返回基本封装
 *
 * @author xiemeng
 * @date 2018-8-24 15:42
 */
public class BaseResponseBean<T> {


    private String resultCode;
    private String resultMsg;
    private T data;
    private int count;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
