package com.qixin.teamwork.controller.enterprise;

import java.sql.Timestamp; 
import java.util.Date; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.framework.iInterface.logs.LogWriter;
import org.framework.utils.StrUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.teamwork.biz.census.model.LightWeekly;
import com.qixin.teamwork.biz.census.model.WeeklyList;
import com.qixin.teamwork.biz.census.model.WeeklyRecord;
import com.qixin.teamwork.biz.census.svc.LightWeeklySvc;
import com.qixin.teamwork.biz.census.svc.WeeklyListSvc;
import com.qixin.teamwork.biz.census.svc.WeeklyRecordSvc;

/**
 * 轻企周报统计控制层
 * @author wuchao
 * @date 2017年9月12日
 * @time 下午2:56:23
 * @version V0.0.3
 */
@RestController
@CrossOrigin(origins="*")
@RequestMapping("/census")
public class CensusController {

	@Autowired
	private LogWriter logWriter;
	@Autowired
	private LightWeeklySvc lightWeeklySvc;
	@Autowired
	private WeeklyListSvc weeklyListSvc;
	@Autowired
	private WeeklyRecordSvc weeklyRecordSvc;
	
	/**
	 * 周报详情
	 * @author wuchao
	 * @date 2017年8月28日
	 * @time 上午12:10:12
	 * @version V0.0.3
	 * @param request
	 * @param lightWeekly
	 * @return
	 */
	@RequestMapping(path = "/infoWeekly", method = RequestMethod.GET)
	public Map<String, Object> infoWeekly(HttpServletRequest request, LightWeekly lightWeekly) {
		Map<String, Object> reslutMap = new HashMap<String, Object>();
		 Date beginDate=new Date();
		String msg = "error";
		if (lightWeekly.getUserId() != null && lightWeekly.getLeId() != null) {
			lightWeekly.setStartTime(new Timestamp(lightWeekly.getStrTime()));
			//周报信息
			LightWeekly item=lightWeeklySvc.infoWeekly(lightWeekly);
			reslutMap.put("item", item);
			msg = "success";
		}
		reslutMap.put("msg", msg);
		 logWriter.saveSystemLog(request,"", "",beginDate, "", "周报详情", 0);
		return reslutMap;

	}
	
	/**
	 * 用户清单列表
	 * @author wuchao
	 * @date 2017年9月23日
	 * @time 下午2:47:59
	 * @version V0.0.3
	 * @param request
	 * @param weeklyList
	 * @return
	 */
	@RequestMapping(path = "/listWeekly", method = RequestMethod.GET)
	public Map<String, Object> listWeekly(HttpServletRequest request, WeeklyList weeklyList) {
		Map<String, Object> reslutMap = new HashMap<String, Object>();
		 Date beginDate=new Date();
		String msg = "error";
		if (weeklyList.getUserId() != null && weeklyList.getLeId() != null) {
			//周报信息
			weeklyList.setStartTime(new Timestamp(weeklyList.getStrTime()));
			List<WeeklyList> list=weeklyListSvc.listWeeklyList(weeklyList);
			reslutMap.put("list", list);
			msg = "success";
		}
		reslutMap.put("msg", msg);
		 logWriter.saveSystemLog(request,"", "",beginDate, "", "用户清单列表", 0);
		return reslutMap;

	}
	
	/**
	 * 更新清单
	 * @author wuchao
	 * @date 2017年9月23日
	 * @time 下午4:02:20
	 * @version V0.0.3
	 * @param request
	 * @param weeklyList
	 * @return
	 */
	@RequestMapping(path = "/updateBill", method = RequestMethod.POST)
	public Map<String, Object> updateBill(HttpServletRequest request,WeeklyList weeklyList) {
		Map<String, Object> reslutMap = new HashMap<String, Object>();
		String errorCode = "";
		String msg ="success";
		if ("success".equals(msg)) {
			//删除周报清单记录
			WeeklyRecord weeklyRecord=new WeeklyRecord();
			weeklyRecord.setLeId(weeklyList.getLeId());
			weeklyRecord.setUserId(weeklyList.getUserId());
			weeklyRecord.setStartTime(new Timestamp(weeklyList.getStrTime()));
			weeklyRecordSvc.deleteWeeklyRecord(weeklyRecord);
			if (!StrUtils.isEmpty(weeklyList.getAllBill())) {
				String[] allBill = weeklyList.getAllBill().split(",");
				for (int i = 0; i < allBill.length; i++) {
					Long id=Long.valueOf(allBill[i]);
					WeeklyList billAll=new WeeklyList();
					billAll.setId(id);
					billAll.setIsRecord((byte)0);
					weeklyListSvc.updateWeeklyList(billAll);
				}
			}
			if (!StrUtils.isEmpty(weeklyList.getBill())) {
				String[] bill = weeklyList.getBill().split(",");
				for (int j = 0; j < bill.length; j++) {
					Long id=Long.valueOf(bill[j]);
					WeeklyList bills=new WeeklyList();
					bills.setId(id);
					bills.setIsRecord((byte)1);
					weeklyListSvc.updateWeeklyList(bills);
					WeeklyList wInfo=new WeeklyList();
					wInfo.setId(id);
					//新增周报清单记录
					WeeklyList wList=weeklyListSvc.getWeeklyInfo(weeklyList);
					WeeklyRecord wr=new WeeklyRecord();
					BeanUtils.copyProperties(wList, wr);
					weeklyRecordSvc.saveWeeklyRecord(wr);
					
				}
			}
		}
		reslutMap.put("msg", msg);
		reslutMap.put("errorCode", errorCode);
		return reslutMap;

	}
	
	/**
	 * 是否生成周报
	 * @author wuchao
	 * @date 2017年9月26日
	 * @time 上午9:49:33
	 * @version V0.0.3
	 * @param request
	 * @param weeklyList
	 * @return
	 */
	@RequestMapping(path = "/isMakeWeekly", method = RequestMethod.GET)
	public Map<String, Object> isMakeWeekly(HttpServletRequest request,WeeklyList weeklyList) {
		Map<String, Object> reslutMap = new HashMap<String, Object>();
		 Date beginDate=new Date();
		String msg = "error";
		if (weeklyList.getUserId() != null && weeklyList.getLeId() != null) {
			//周报信息
			weeklyList.setWeeklyDate(new Timestamp(weeklyList.getStrTime()));
			WeeklyList item=weeklyListSvc.isMakeWeekly(weeklyList);
			reslutMap.put("item", item);
			msg = "success";
		}
		reslutMap.put("msg", msg);
		 logWriter.saveSystemLog(request,"", "",beginDate, "", "是否生成周报", 0);
		return reslutMap;

	}
	
	/**
	 * 周报生成确认信息
	 * @author wuchao
	 * @date 2017年9月26日
	 * @time 上午10:24:07
	 * @version V0.0.3
	 * @param request
	 * @param weeklyList
	 * @return
	 */
	@RequestMapping(path = "/saveWeekly", method = RequestMethod.POST)
	public Map<String, Object> saveWeekly(HttpServletRequest request,WeeklyList weeklyList) {
		Map<String, Object> reslutMap = new HashMap<String, Object>();
		String errorCode = "";
		String msg = !StrUtils.isEmpty(weeklyList.getLeId()) ? "success" : "error";
		if ("success".equals(msg)) {
			weeklyList.setWeeklyDate(new Timestamp(weeklyList.getStrTime()));
			weeklyListSvc.saveWeekly(weeklyList);
			
			//新增周报清单记录
			WeeklyRecord weeklyRecord=new WeeklyRecord();
			BeanUtils.copyProperties(weeklyList, weeklyRecord);
			weeklyRecordSvc.saveWeeklyRecord(weeklyRecord);
		}
		reslutMap.put("msg", msg);
		reslutMap.put("errorCode", errorCode);
		return reslutMap;

	}
}
