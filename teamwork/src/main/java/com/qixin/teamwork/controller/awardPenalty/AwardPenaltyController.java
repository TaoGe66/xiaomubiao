package com.qixin.teamwork.controller.awardPenalty;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/7/6 15:03
 * version:V0.0.1
 */

import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyList;
import com.qixin.teamwork.biz.awardPenalty.model.AwardPenaltyRule;
import com.qixin.teamwork.biz.awardPenalty.svc.AwardPenaltyListSvc;
import com.qixin.teamwork.biz.awardPenalty.svc.AwardPenaltyRuleSvc;
import com.qixin.teamwork.biz.base.APIErrorMap;
import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.model.JoinEvent;
import com.qixin.teamwork.biz.envet.svc.EventItemSvc;
import com.qixin.teamwork.biz.envet.svc.EventSvc;
import com.qixin.teamwork.biz.envet.svc.JoinEventSvc;
import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.user.svc.WxuserSvc;

import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.PaginationParameter;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.*;

/** 事件评论api
 * Created by
 * Author:xiehuilin
 * Date:2017/6/13 14:03
 * version:V0.0.1
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/ap")
public class AwardPenaltyController {
    @Autowired
    private AwardPenaltyRuleSvc awardPenaltyRuleSvc;
    @Autowired
    private AwardPenaltyListSvc  aplSvc;
    @Autowired
    private EventItemSvc eventItemSvc;
    @Autowired
    private EventSvc eventSvc;

    @Autowired
    private LogWriter logWriter;
    @Autowired
    private JoinEventSvc joinEventSvc;
    @Autowired
    private WxuserSvc  wxuserSvc;
    /**
     * 存储奖惩规则
     * @author xiehuilin
     * @param request
     * @param awardPenaltyRule
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @version V0.0.1
     * @date 2017/7/6 15:05
     */
    @RequestMapping(path ="/save",method = RequestMethod.POST)
    public Map<String,Object> insertRule(HttpServletRequest request, @RequestBody AwardPenaltyRule awardPenaltyRule ){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(awardPenaltyRule);
        boolean isNull=resMap.size()>0?false:true;
        String msg="success";
        String errorCode="";
        if(isNull){
            List<AwardPenaltyRule> ruleList = awardPenaltyRule.getRuleList();
            List<AwardPenaltyRule> awardPenaltyRuleList = awardPenaltyRuleSvc.getAwardPenaltyRuleList(awardPenaltyRule);
            if(null==awardPenaltyRuleList||awardPenaltyRuleList.size()==0){
                if(!ruleList.isEmpty()){
                    for (AwardPenaltyRule rule:ruleList){
                        if(rule.getRpMoney().compareTo(BigDecimal.valueOf(5000))<=0){
                        rule.seteId(awardPenaltyRule.geteId());
                        rule.setCreateBy(awardPenaltyRule.getCreateBy());
                         awardPenaltyRuleSvc.insert(rule);
                        }else{
                            msg="error";
                            errorCode=APIErrorMap.errorMap.get("5");
                            break;

                        }
                    }
                }
            }else{
                msg="error";
                errorCode=APIErrorMap.errorMap.get("7");
            }
        }
        resMap.put("msg",msg);
        resMap.put("errorCode",errorCode);
        logWriter.saveSystemLog(request,"", "",beginDate, "", "保存事务奖惩记录", 0);
        return resMap;
    }
     /**
     * 根据事件id和奖惩类型获取事件下的奖惩名单
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/7/6 17:13
     */
     @RequestMapping(path ="/getList",method = RequestMethod.GET)
    public Map<String,Object> getList(HttpServletRequest request, AwardPenaltyRule awardPenaltyRule,PaginationParameter parameter){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(awardPenaltyRule);
        boolean isNull=resMap.size()>0?false:true;
        List<AwardPenaltyList> penaltyList=new ArrayList<AwardPenaltyList>();
        String msg="error";
        if(isNull){
            AwardPenaltyList penalty=new AwardPenaltyList();
            penalty.seteId(awardPenaltyRule.geteId());
            penalty.setRpType(awardPenaltyRule.getRpType());
            penalty.setPaper(parameter);
            penaltyList = aplSvc.getPenaltyList(penalty);
            if(null!=penaltyList&&penaltyList.size()>0){
                for(int x=0;x<penaltyList.size();x++){
                    AwardPenaltyList apl= penaltyList.get(x);
                    apl.setcTime(  StrUtils.getDate(apl.getCreateTime()));
                    apl.settTime(StrUtils.getDateHour(apl.getTaskTime()));

                }
            }
            msg="success";
        }
        resMap.put("penaltyList",penaltyList);
        resMap.put("msg",msg);
        logWriter.saveSystemLog(request,"", "",beginDate, "", "获取事件下的奖惩名单列表", 0);
        return resMap;
    }
    /**
     * 根据事件id获取奖惩规则
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/7/6 17:13
     */
    @RequestMapping(path ="/getRuleList",method = RequestMethod.GET)
    public Map<String,Object> getRuleList(HttpServletRequest request, AwardPenaltyRule awardPenaltyRule){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(awardPenaltyRule);
        boolean isNull=resMap.size()>0?false:true;
        List<AwardPenaltyRule> ruleList=new ArrayList<AwardPenaltyRule>();
        String msg="error";
        if(isNull){
            ruleList = awardPenaltyRuleSvc.getAwardPenaltyRuleList(awardPenaltyRule);
            //根据事件id获取事件详情
            Event event=new Event();
            event.seteId(awardPenaltyRule.geteId());
            Event envetInfo = eventSvc.getEnvetInfo(event);
            String dutyName=envetInfo.getUserName();
            //获取项目责任人
            if(envetInfo.getType()==1){
            	 JoinEvent je=new JoinEvent();
                 je.seteId(envetInfo.geteId());
                 je.setType(new Byte("1"));
                 je.setState(new Byte("1"));
                 JoinEvent relust = joinEventSvc.JoinEventInfo(je);
                 User user=new User();
                 user.setUserId(relust.getUserId());
                 user=wxuserSvc.getUserInfo(user);
                 dutyName=relust.getDutyName();
            }
            resMap.put("dutyName", dutyName);
            msg="success";
        }
        resMap.put("ruleList",ruleList);
        resMap.put("msg",msg);
        logWriter.saveSystemLog(request,"", "",beginDate, "", "获取事件下的奖惩规则", 0);
        return resMap;
    }



    /**
     *  根据行动id和奖惩类别获取奖惩金额
     *  根据行动id获取奖惩类型
     * @author xiehuilin
     * @param [request, penaltyList]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @version V0.0.3
     * @date 2017/9/14 17:25
     */
    public  Map<String,Object> getPenalty(HttpServletRequest request,AwardPenaltyList penaltyList){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        String msg=!StrUtils.isEmpty(penaltyList.getEiId())?"success":"error";
        String errorCode=!StrUtils.isEmpty(penaltyList.getEiId())?"":APIErrorMap.errorMap.get("18");
        if("success".equals(msg)){
            //根据行动id获取奖惩类型
            EventItem eventItem=new EventItem();
            eventItem.setEiId(penaltyList.getEiId());
            EventItem itemInfo = eventItemSvc.getItemInfo(eventItem);
            //根据行动id和奖惩类别获取奖惩金额
            penaltyList.setRpCategory(itemInfo.getRpCategory());
            AwardPenaltyList awardPenaltyListInfo = aplSvc.getAwardPenaltyListInfo(penaltyList);
            resMap.put("itemInfo",itemInfo);
            resMap.put("penaltyList",awardPenaltyListInfo);
        }
        resMap.put("msg",msg);
        resMap.put("errorCode",errorCode);
        return  resMap;
    }



    /**
     * 校验客户端请求参数合法性
     * @param awardPenaltyRule
     * @return
     */
    private Map<String, Object> checkPara(AwardPenaltyRule awardPenaltyRule) {
        String start="";
        String end="";
        Map<String, Object>  reslutMap=new HashMap<String, Object>();
        String errorCode="";
        String msg="success";
        String state="1";  // 1 成功  0 失败
        if(null==awardPenaltyRule){
            errorCode= APIErrorMap.errorMap.get("0");
            state="0";
            msg="error";
            return returnMap(reslutMap, errorCode, msg, state,false);
        }
        //事件id为空
        if (StrUtils.isEmpty(String.valueOf(awardPenaltyRule.geteId()))){
            errorCode= APIErrorMap.errorMap.get("2");
            state="0";
            msg="error";
            return returnMap(reslutMap, errorCode, msg, state,false);
        }
        //用户id
        if (StrUtils.isEmpty(String.valueOf(awardPenaltyRule.getCreateBy()))){
            errorCode= APIErrorMap.errorMap.get("3");
            state="0";
            msg="error";
            return returnMap(reslutMap, errorCode, msg, state,false);
        }
        return reslutMap;
    }
    private Map<String, Object> returnMap(Map<String, Object> reslutMap,String errorCode, String msg, String state,boolean isface) {
        reslutMap.put("errorCode", errorCode);
        reslutMap.put("msg", msg);
        reslutMap.put("state", state);
        return reslutMap;
    }
}
