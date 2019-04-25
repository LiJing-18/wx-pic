package com.karat.cn.util.constants;
/**
 * 返回状态码枚举
 * @author 开发
 *
 */
public enum ResponseEnum {

    SUCCESS("0","成功"),
    FAILED("1","系统繁忙,请稍后重试"),
    ;
    private String code;
    private String msg;
    private ResponseEnum(String code,String msg){
        this.msg = msg;
        this.code = code;
    }
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
}
