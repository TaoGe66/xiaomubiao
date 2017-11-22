package com.qixin.teamwork.controller.comment;

import com.qixin.teamwork.biz.base.APIErrorMap;
import com.qixin.teamwork.biz.comment.model.Comment;
import com.qixin.teamwork.biz.comment.svc.CommentSvc;
import com.qixin.teamwork.biz.dto.CommentDto;
import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.PaginationParameter;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 事件评论api
 * Created by
 * Author:xiehuilin
 * Date:2017/6/13 14:03
 * version:V0.0.1
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/com")
public class CommentController {
    @Autowired
    private CommentSvc commentSvc;
    @Autowired
    private LogWriter logWriter;
    /**
    * 保存评论记录
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/6/13 14:06
    */
    @RequestMapping(path ="/save",method = RequestMethod.POST)
    public Map<String,Object> commntList(HttpServletRequest request, Comment comment){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(comment);
        boolean isNull=resMap.size()>0?false:true;
        if(isNull){
         return commentSvc.insert(comment);
        }
        return resMap;
    }

    /**
     *  根据事件id获取事件下的评论列表
     * @author xiehuilin
     * @param  comment 评论实体
     * @param  parameter 分页实体
     * @return  map 集合
     * @version V0.0.1
     * @date 2017/6/13 11:25
     */
    @RequestMapping(path ="/getCommntList",method = RequestMethod.GET)
    public Map<String,Object> commntList(HttpServletRequest request, Comment comment, PaginationParameter parameter){
        Map<String,Object> resMap=new HashMap<String, Object>();
        Date beginDate=new Date();
        resMap= this.checkPara(comment);
        boolean isNull=resMap.size()>0?false:true;
        if(isNull){
            List<CommentDto> comments = commentSvc.listCommentByEid(comment);
            if(null!=comments&&comments.size()>0){
                for(int x=0;x<comments.size();x++){
                    CommentDto commentDto = comments.get(x);
                    commentDto.setCtime(StrUtils.formatDate(commentDto.getCreateTime()));
                }
            }
            resMap.put("msg","success");
            resMap.put("errorCode","");
            resMap.put("comments",comments);
        }
        logWriter.saveSystemLog(request,"", "",beginDate, "", "获取事件评论列表", 0);
        return resMap;
    }

    /**
     * 校验客户端请求参数合法性
     * @param comment
     * @return
     */
    private Map<String, Object> checkPara(Comment comment) {
        String start="";
        String end="";
        Map<String, Object>  reslutMap=new HashMap<String, Object>();
        String errorCode="";
        String msg="success";
        String state="1";  // 1 成功  0 失败
        if(null==comment){
            errorCode= APIErrorMap.errorMap.get("0");
            state="0";
            msg="error";
            return returnMap(reslutMap, errorCode, msg, state,false);
        }
        //事件id为空
        if (StrUtils.isEmpty(String.valueOf(comment.geteId()))){
            errorCode= APIErrorMap.errorMap.get("2");
            state="0";
            msg="error";
            return returnMap(reslutMap, errorCode, msg, state,false);
        }
        //用户id
        if (StrUtils.isEmpty(String.valueOf(comment.getUserId()))){
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
