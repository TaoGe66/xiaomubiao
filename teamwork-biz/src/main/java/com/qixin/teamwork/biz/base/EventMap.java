package com.qixin.teamwork.biz.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/6/13 15:04
 * version:V0.0.1
 */
public class EventMap {
    public static Map<String,String> aimStateMap; //目标事件状态
    public static Map<String,String> stateMap; //需求事件状态
    public static Map<String,String>  itemStateMap;//事项状态
    public static Map<String,String>  desMap;//描述
    public static Map<String,String>  eventDesMap;//事件状态描述
    public static Map<String,String>  newsMap;//消息待办内容
    
    public static Map<String,String>  itemDate;//行动日期
    static{
        stateMap=new HashMap<String,String>();
        itemStateMap=new HashMap<String,String>();
        desMap=new HashMap<String, String>();
        eventDesMap=new HashMap<String, String>();
        aimStateMap=new HashMap<String, String>();
        itemDate=new HashMap<String, String>();
        newsMap=new HashMap<String, String>();
        //事件状态 start 目标 
        aimStateMap.put("0", "目标待计划");
        aimStateMap.put("1", "目标待执行");
        aimStateMap.put("2","目标已完成");
        aimStateMap.put("3","目标待关闭");
        aimStateMap.put("4","签到提醒");
        aimStateMap.put("5","已签到");
        aimStateMap.put("6","目标待评价");
        //事件状态  end 目标
        //事件状态 start 需求
        stateMap.put("0", "需求待计划");
        stateMap.put("1", "需求待执行");
        stateMap.put("2","需求已完成");
        stateMap.put("3","需求待关闭");
        stateMap.put("4","签到提醒");
        stateMap.put("5","已签到");
        stateMap.put("6","需求待评价");
      //事件状态  end 需求
      //事项状态 start
        itemStateMap.put("0","做完了");//计划待完成  
        itemStateMap.put("1","待接受");
        itemStateMap.put("2","未接受");
        itemStateMap.put("3","正常完成");
        itemStateMap.put("4","延期完成");
        itemStateMap.put("5","异常终止");
        itemStateMap.put("6","已拒绝");
        itemStateMap.put("7","已接受");
        itemStateMap.put("8","拒绝已查看");
        itemStateMap.put("9","取消委托");
        itemStateMap.put("10","行动跟踪");
        itemStateMap.put("11","周委托待接受");
        itemStateMap.put("12","周委托未接受");
        itemStateMap.put("13","周委托已拒绝");
        itemStateMap.put("14","周委托已接受");
        itemStateMap.put("15","周委托拒绝已查看");
        itemStateMap.put("16","开始处理");
        itemStateMap.put("17","奖惩执行");
       //事项状态  end
       //事件状态描述 start
        eventDesMap.put("0","目标缺少行动计划!");
        eventDesMap.put("1","需求缺少行动计划!");
        eventDesMap.put("2","目标到期，及时关闭");
        eventDesMap.put("3","需求到期，及时关闭");
        eventDesMap.put("4","请及时签到");
        eventDesMap.put("5","活动到期，及时关闭");
       //事件状态描述 end
       //事项计划描述 start
        desMap.put("0","计划待完成！");
        desMap.put("1","行动跟踪记录");
        //事项计划描述 end

      //行动日期
        itemDate.put("MO","1");//周一
        itemDate.put("TU","2");//周二
        itemDate.put("WE","3");//周三
        itemDate.put("TH","4");//周四
        itemDate.put("FR","5");//周五
        itemDate.put("SA","6");//周六
        itemDate.put("SU","7");//周日

        //消息待办内容
        newsMap.put("0", "目标待计划");
        newsMap.put("1", "需求待计划");
        newsMap.put("2", "目标待关闭");
        newsMap.put("3", "需求待关闭"); 
        newsMap.put("4", "行动待完成");
        newsMap.put("5", "指派您一项行动");
        newsMap.put("6", "指派您一项周期行动");
        newsMap.put("7", "待签到");
        newsMap.put("8", "签到失败");
        newsMap.put("9", "此行动已经异常终止");
        newsMap.put("10","邀请你成为任务参与人");
        
    }
}
