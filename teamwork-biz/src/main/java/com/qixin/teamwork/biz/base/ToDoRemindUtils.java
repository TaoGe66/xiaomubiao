package com.qixin.teamwork.biz.base;


import org.framework.utils.Constant;
import org.framework.utils.DateUtils;
import org.framework.utils.StrUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 根据事件类型和提前提醒设置生成待办任务开始时间
 * Created by
 * Author:xiehuilin
 * Date:2017/6/13 17:16
 * version:V0.0.1
 */
public class ToDoRemindUtils {
    /** 
    *  根据事件类型和提前提醒设置生成待办任务开始时间
    * @author xiehuilin
     * @param  date
     * @param   remindType  分钟
     * @return  Date
    * @version V0.0.1
    * @date 2017/6/13 17:19
    */
    public  static Timestamp   getToDoRemindTime(Timestamp date, Integer remindType){
        Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date.getTime());
            cal.add(Calendar.MINUTE,remindType);
        long ts = cal.getTimeInMillis();
        Timestamp s=new Timestamp(ts);
        return s;
    }
    
    /**
     * 今明两天最大毫秒数和最小毫秒数  
     * 			type 	0，当前毫秒	 			 1今天零点零分零秒的毫秒数
     * 					2 今天23点59分59秒的毫秒数  	 3明天23点59分59秒的毫秒数
     * @author wuchao
     * @date 2017年8月9日
     * @time 上午10:27:32
     * @version V0.0.1
     * @param type
     * @return
     */
    public  static Long   getTimeCheck(Integer type){
    Long time=null;
   	 long current=System.currentTimeMillis()+1000*3600*8;//当前时间毫秒数  
     long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数  
     long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数  
     long yesterday=twelve+24*60*60*1000;//明天23点59分59秒的毫秒数 
     long yesterzero=zero+24*60*60*1000;//明天零点零分零秒的毫秒数 
     if (type==0) {
    	 time=current;
     }else if (type==1) {
    	 time=zero;
     }else if (type==2) {
    	 time=twelve;
     }else if (type==3) {
    	 time=yesterday;
     }else if (type==4) {
    	 time=yesterzero;
     }
     return time;
     
    }
    /**
     * 对应日期的零点零分零秒
     * @author wuchao
     * @date 2017年8月9日
     * @time 上午11:29:28
     * @version V0.0.1
     * @param date
     * @return
     */
    public  static Timestamp   getWeeHoursTime(Timestamp date){
        Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date.getTime());
        long ts = cal.getTimeInMillis()+1000*3600*8;
        long zero=ts/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数  
        Timestamp s=new Timestamp(zero);
        return s;
    }
    
    /**
     *  对应日期的23点59分59秒的毫秒数
     *@author wuchao
     *@data 2017年11月3日
     *@version V0.0.5
     * @param date
     * @return
     */
    public  static Timestamp   getWeeHoursEndTime(Timestamp date){
        Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date.getTime());
        long ts = cal.getTimeInMillis()+1000*3600*8;
        long zero=ts/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数  
        zero =zero+24*60*60*1000-1;
        Timestamp s=new Timestamp(zero);
        return s;
    }
    /**
     * 获取指定日期的下周一时间
     * @author wuchao
     * @date 2017年8月9日
     * @time 下午5:19:39
     * @version V0.0.1
     * @param date
     * @return
     */
    public static Date getNowWeekMonday(Date date) {  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(date);    
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
           cal.add(Calendar.DAY_OF_MONTH, -1);  
        } 
       cal.add(Calendar.DAY_OF_MONTH, +7); //解决周日会出现 并到下一周的情况    
       cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
       
            return cal.getTime();    
    }
    /**
     * 根据周几获取对应的日期 		day是当前周的第几天
     * @author wuchao
     * @date 2017年8月10日
     * @time 上午9:36:08
     * @version V0.0.1
     * @param day
     * @return
     */
    public static Date getWeeksYTD(int day){
    	day++;
    	Calendar cal = Calendar.getInstance();
    	int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
           cal.add(Calendar.DAY_OF_MONTH, -1);  
        } 
    	int date = cal.get(Calendar.DAY_OF_MONTH);
    	int n = cal.get(Calendar.DAY_OF_WEEK);
    	cal.set(Calendar.DAY_OF_MONTH, date + day - n);
    	return cal.getTime();
    }


    /**
     * 根据日期判断是周几
     * @author wuchao
     * @date 2017年8月10日
     * @time 上午11:14:57
     * @version V0.0.1
     * @param day
     * @return
     */
    public static int getYTDWeeks(Date day){
    	 Calendar c = Calendar.getInstance();
      	  c.setTime(day);
      	  int dayForWeek = 0;
      	  if(c.get(Calendar.DAY_OF_WEEK) == 1){
      	   dayForWeek = 7;
      	  }else{
      	   dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
      	  }
      	return dayForWeek;
    }

   /**
    * 根据日期和星期几，获取对应周日期
    * @author wuchao
    * @date 2017年8月10日
    * @time 下午6:38:55
    * @version V0.0.1
    * @param day
    * @param time
    * @return
    */
    public static Date getDayWeeksYTD(int day,Date time){
    	day++;
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(time); 
    	int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
           cal.add(Calendar.DAY_OF_MONTH, -1);  
        } 
    	int date = cal.get(Calendar.DAY_OF_MONTH);
    	int n = cal.get(Calendar.DAY_OF_WEEK);
    	cal.set(Calendar.DAY_OF_MONTH, date + day - n);
    	return cal.getTime();
    }


    private static final int FIRST_DAY = Calendar.MONDAY;

    private static void printWeekdays(Date date,Date date1) {
        long days = DateUtils.getWeekDays(date1);
        System.out.println(days);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.DATE,1);
        boolean b = StrUtils.compareDate(StrUtils.getDateHour(date, Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN), StrUtils.getDateHour(date1, Constant.DEFAULT_DATE_TIME_FORMAT_PATTERN));
        for (int i = 0; i < days; i++) {
            printDay(calendar);
            calendar.add(Calendar.DATE, 1);
     }
    }
    private static void printDay(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd EE");
        System.out.println(dateFormat.format(calendar.getTime()));
    }
    
    /**
     * 延迟 年
     * @author wuchao
     * @date 2017年9月20日
     * @time 下午1:55:00
     * @version V0.0.3
     * @param date
     * @param remindType
     * @return
     */
    public  static Timestamp   getLagAge(Timestamp date, Integer remindType){
        Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date.getTime());
            cal.add(Calendar.YEAR,remindType);
        long ts = cal.getTimeInMillis();
        Timestamp s=new Timestamp(ts);
        return s;
    }
    
    
    /**
     * 判断两个日期是否在同一周内
     *@author wuchao
     *@data 2017年10月27日
     *@version V0.0.5
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2)
    {
     Calendar cal1 = Calendar.getInstance();
     Calendar cal2 = Calendar.getInstance();
     cal1.setFirstDayOfWeek(Calendar.MONDAY);//西方周日为一周的第一天，咱得将周一设为一周第一天
     cal2.setFirstDayOfWeek(Calendar.MONDAY);
     cal1.setTime(date1);
     cal2.setTime(date2);
     int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
     if (subYear == 0)// subYear==0,说明是同一年
     {
      if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
       return true;
     }
     else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11) //subYear==1,说明cal比cal2大一年;java的一月用"0"标识，那么12月用"11"
     {
      if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
       return true;
     }
     else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11)//subYear==-1,说明cal比cal2小一年
     {
      if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
       return true;
     }
     return false;
    }

  
}
