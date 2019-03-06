package com.karat.cn.util;

import java.util.List;

import com.karat.cn.vo.ResultVo;

public class ResultVOUtil {
	//成功返回对象
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResultVo success(Object object) {
    	ResultVo resultVO = new ResultVo();
        resultVO.setData(object);
        resultVO.setCode(200);
        resultVO.setMsg("成功");
        return resultVO;
    }
	//成功返回对象集合
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResultVo seccess(List<Object> list){
		ResultVo resultVO = new ResultVo();
        resultVO.setList(list);
        resultVO.setCode(200);
        resultVO.setMsg("成功");
        return resultVO;
	}
    //成功返回空
    @SuppressWarnings("rawtypes")
	public static ResultVo success() {
        return success(null);
    }
    //失败
    @SuppressWarnings("rawtypes")
	public static ResultVo error(Integer code, String msg) {
    	ResultVo resultVO = new ResultVo();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
