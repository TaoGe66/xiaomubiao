/* 提醒时间
 *add by xiehuilin 2017/06/21 
 */
var frequency={
	svcManageTime:function(type){
		var html="";
		switch (type){ 
			case -15:
			html="15分钟前";
			break; 
			case -30:
			html="30分钟前";
			break; 
			case -60:
			html="1小时前";
			break; 
			case -180:
			html="3小时前";
			break; 
			case -240:
			html="4小时前";
			break; 
			case -1440:
			html="一天前";
			break; 
			case -4320:
			html="三天前";
			break; 
			default : 
			 
		} 
		return html;
	},
	pubRange:function(type){
		var html="";
		switch (type){ 
			case 0:
			html="好友可以见";
			break; 
			case 1:
			html="平台公开";
			break; 
			case 2:
			html="个人可见";
			break; 
		} 
		return html;
	},
	week:function(type){
		var html="";
		switch (type){ 
			case "MO":
			html="周一";
			break; 
			case "TU":
			html="周二";
			break; 
			case "WE":
			html="周三";
			break; 
			case "TH":
			html="周四";
			break; 
			case "FR":
			html="周五";
			break; 
			case "SA":
			html="周六";
			break; 
			case "SU":
			html="周日";
			break; 
		} 
		return html;
	}
}