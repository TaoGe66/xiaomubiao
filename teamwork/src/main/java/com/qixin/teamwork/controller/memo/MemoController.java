package com.qixin.teamwork.controller.memo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.DateUtils;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.teamwork.biz.memo.model.MemoList;
import com.qixin.teamwork.biz.memo.svc.MemoListSvc;

/**
 * 随手记控制层
 *@author wuchao
 *@data 2017年10月30日
 *@version V0.0.5
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/memo")
public class MemoController {


	@Resource
	private LogWriter log;
	@Autowired
	private MemoListSvc memoListSvc;
	
	/**
	 * 保存备忘录列表
	 *@author wuchao
	 *@data 2017年10月30日
	 *@version V0.0.5
	 * @param request
	 * @param memoList
	 * @return
	 */
	 @RequestMapping(path ="/saveMemoList",method = RequestMethod.POST)
	    public Map<String,Object> saveGroup(HttpServletRequest request, MemoList memoList){
	        Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if(memoList.getUserId() !=null){
	        	memoListSvc.saveMemoList(memoList);
	        	msg="success";
	        }
	        resMap.put("msg", msg);
	        log.saveSystemLog(request,"", "",beginDate, "", "保存备忘录列表", 0);
	        return resMap;
	    }
	 
	 /**
	  * 删除备忘录列表
	  *@author wuchao
	  *@data 2017年10月30日
	  *@version V0.0.5
	  * @param request
	  * @param memoList
	  * @return
	  */
	 @RequestMapping(path ="/updateMemoList",method = RequestMethod.POST)
	    public Map<String,Object> updateMemoList(HttpServletRequest request, MemoList memoList){
	        Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if( !StrUtils.isEmpty(memoList.getMemoIdStr())){
	        	String memoIdStr=memoList.getMemoIdStr();
	        	String[] cyList = memoIdStr.split(",");
	        	for (int i = 0; i < cyList.length; i++) {
	        		MemoList memo=new MemoList();
	        		memo.setId( Long.valueOf(cyList[i]));
	        		memo.setIsValid(memoList.getIsValid());
	        		memoListSvc.updateMemoList(memo);
	        	}
	        	msg="success";
	        }
	        resMap.put("msg", msg);
	        log.saveSystemLog(request,"", "",beginDate, "", "删除备忘录列表", 0);
	        return resMap;
	    }
	 
	 /**
	  * 备忘录列表
	  *@author wuchao
	  *@data 2017年10月30日
	  *@version V0.0.5
	  * @param request
	  * @param memoList
	  * @return
	  */
	    @RequestMapping(path ="/listMemoList",method = RequestMethod.GET)
	    public  Map<String,Object> listMemoList(HttpServletRequest request, MemoList memoList){
	        Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if(memoList.getUserId() !=null){
	        	List<MemoList> list=new ArrayList<MemoList>();
	        	List<MemoList> mList=memoListSvc.listMemoList(memoList);
	        	if (mList.size()>0) {
					for (int i = 0; i < mList.size(); i++) {
						MemoList me=mList.get(i);
						me.setStrTime(DateUtils.getDateDetail(me.getCreateTime().toString()));
						list.add(me);
					}
				}
	        	
	        	resMap.put("list",list);
	        }
	        resMap.put("msg",msg);
	        log.saveSystemLog(request,"", "",beginDate, "", "备忘录列表", 0);
	        return resMap;
	    }
	    
	    /**
	     * 备忘录列表详情
	     *@author wuchao
	     *@data 2017年10月30日
	     *@version V0.0.5
	     * @param request
	     * @param memoList
	     * @return
	     */
	    @RequestMapping(path ="/infoMemoList",method = RequestMethod.GET)
	    public  Map<String,Object> infoMemoList(HttpServletRequest request, MemoList memoList){
	        Map<String,Object> resMap=new HashMap<String, Object>();
	        Date beginDate=new Date();
	        String msg="error";
	        if(memoList.getId() !=null){
	        	MemoList info=memoListSvc.infoMemoList(memoList);
	        	resMap.put("item",info);
	        }
	        resMap.put("msg",msg);
	        log.saveSystemLog(request,"", "",beginDate, "", "备忘录列表详情", 0);
	        return resMap;
	    }
}
