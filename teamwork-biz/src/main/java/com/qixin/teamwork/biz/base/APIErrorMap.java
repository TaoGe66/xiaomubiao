package com.qixin.teamwork.biz.base;

import java.util.HashMap;
import java.util.Map;


/**
 * 系统错误参数
 * @author zyting
 * @date 2015/11/26
 * V1.0
 */
public class APIErrorMap {
    public static Map<String,String> errorMap;
    static{
        errorMap=new HashMap<String,String>();
        //系统参数
        errorMap.put("0", "参数异常");
        errorMap.put("1", "验证码已发送，请稍后再试");
        //用户
        errorMap.put("2","用户id异常");
        //事件
        errorMap.put("3","事件id异常");
        errorMap.put("4","你已签到");
        errorMap.put("5","金额不能超过5000元");
        errorMap.put("6","活动已结束");
        errorMap.put("7","奖惩规则已存在");
    	//上传图片
        errorMap.put("11", "网络错误，请稍后重试");	//系统异常
        errorMap.put("12", "网络错误，请稍后重试"); //上传的formdata中的数据超出了规定大小  8M post请求总数据大小
        errorMap.put("13", "图片超出规定大小,请选择小于1M的图片"); //上传的文件超出了规定大小 4M 单个文件限制
        errorMap.put("14", "网络错误，请稍后重试");//获取不到file
        errorMap.put("15", "只能上传图片文件");//图片类型错误
        errorMap.put("16","行动已失效!");
        //轻企
        errorMap.put("17","轻企id异常");
        errorMap.put("18","行动id异常");


    }
}
