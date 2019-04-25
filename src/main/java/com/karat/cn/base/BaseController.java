package com.karat.cn.base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karat.cn.util.constants.ResponseCodeEnum;
import com.karat.cn.util.vo.ResponseData;


public class BaseController {
	
    public Logger LOG= LoggerFactory.getLogger(BaseController.class);


    protected String redirectTo(String url) {
        StringBuffer rto = new StringBuffer("redirect:");
        rto.append(url);
        return rto.toString();
    }

    protected ResponseData setEnumResult(ResponseCodeEnum anEnum, Object data){
        ResponseData res =new ResponseData();
        res.setCode(anEnum.getCode());
        res.setData(data);
        res.setMessage(anEnum.getMsg());
        return res;
    }
}
