package com.wuyunlong.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class Result {
  String retCode;
    String retMsg;
    String retData;

    public String getRetData() {
        return retData;
    }

    public void setRetData(String retData) {
        this.retData = retData;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                ", retData='" + retData + '\'' +
                '}';
    }
}
