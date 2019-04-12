package com.karat.cn.dto;
import java.io.Serializable;

import com.karat.cn.abs.AbstractResponse;

/**
 * 注册返回信息
 * @author 开发
 *
 */
public class UserRegisterResponse extends AbstractResponse implements Serializable{

    private static final long serialVersionUID = -7690077437344492561L;

    private Integer uid;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
