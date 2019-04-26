package com.karat.cn.abs;
import java.io.Serializable;
/**
 * 返回信息状态码
 * @author 开发
 *
 */
public abstract class AbstractResponse implements Serializable{

    private static final long serialVersionUID = 8122890624127584187L;
    //请求信息返回码
    private String code;
    //请求信息错误描述
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AbstractResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
