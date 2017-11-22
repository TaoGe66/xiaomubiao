$(function(){
	$("body").height($(window).height());
	var now = new Date();
//	$("#appDateTime").val(TimeMin(now));
	
//	show_time();
//	edit by zhengjunting 2017/06/13 事件公开范围
	$(".Servetwo_footertwo li").click(function(){
		$(this).addClass("Servetwo_current_two").siblings().removeClass("Servetwo_current_two");
	});
//	edit by zhengjunting 2017/06/13 事项提醒
	$(".Servetwo_footer li").click(function(){
		$(this).addClass("Servetwo_current").siblings().removeClass("Servetwo_current");
	});
})
var leId = localStorage.getItem("team_leId");
if(!isnull(leId)){
	$("title").html("创建轻企活动");
	$(".Servetwo_footertwo").hide();
}else{
	$(".Servetwo_footertwo").show();
}
//选择地点
$(".address_bar").click(function(){
//	window.location.href="Venue.html";
	$(".Servetwo_Venue").show();
});

//发布事件组织活动
$(".Servetwo_con_save").click(function(){
	check_name();
});

//保存组织活动事件
function save_erve_event(start){
	$(".event_summary").show();
	$(".event_summary1").show();
	var startTime=$("#appDateTime").val();//开始时间
	var data='{'+start+'}';
	$.ajax({
		  url:dataLink+"event/saveEvent",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     contentType: "application/json; charset=utf-8",
	     success:function(result){
	   	  if(result.msg=="success"){
	   		var item=result.info;
	   		localStorage.setItem("team_startTime",startTime);
	   		localStorage.setItem("team_eId",item.eId);
	   		localStorage.removeItem("team_lng");//经度
	   		localStorage.removeItem("team_lat");//纬度
	   		localStorage.removeItem("team_province");//省
	   		localStorage.removeItem("team_city");//市
	   		localStorage.removeItem("team_address");//详细地址
	   		window.location.href="publish_event.html";
	   	  }
	     },  
	     timeout:3000  
	});
	
}

//校验事件名称是否存在
function check_name(){
	var createBy=localStorage.getItem("team_userId");
	var name=$(".organize_affair").val();//事件名称
	name=_trim(name);
	if (!centen(name,2,40)) {
		$(".Servetwo_alert_con").html("名称1~20个汉字");
		$(".Servetwo_alert").show();
		setTimeout(function(){$(".Servetwo_alert").hide();},2000);
		via=false;
		return false;
	}else{
		var data={"createBy":createBy,"name":name}
		$.ajax({
			type : "GET",
			url:dataLink+"event/checkEvent",  
			dataType:'json',  
			data:data, 
			contentType: "application/json; charset=utf-8",
			success:function(json){
				if(json.msg=="success"){
					var count= json.count;
					if (count==1) {
						$(".Servetwo_alert_con").html("名称已存在");
						$(".Servetwo_alert").show();
						setTimeout(function(){$(".Servetwo_alert").hide();},2000);
						return false;
					}else{
						check_info();
					}
					
				}
			},  
			timeout:3000  
		}); 
	}
}

/**
 * 校验信息
 */
function check_info(){

	var via=true;
	var lng=localStorage.getItem("team_lng");//经度
	var lat=localStorage.getItem("team_lat");//纬度
	var	province=localStorage.getItem("team_province");//省
	var	city=localStorage.getItem("team_city");//市
	var district=localStorage.getItem("team_district");//区
	var	address=localStorage.getItem("team_address");//详细地址
	var createBy=localStorage.getItem("team_userId");
	var name=$(".organize_affair").val();//事件名称
	var target=$(".organize_aim").val();//事件目标
	var startTime=$(".appDateTime").val();//开始时间
	var endTime=$(".appDateTimetwo").val();//结束时间
	var frequency=$(".Servetwo_current").find("i").html();//提醒状态
	var pubRange=$(".Servetwo_current_two").find("i").html();//可见范围
	var closeTime=$(".appDateTimethree").val();//截至时间
//	var leId = localStorage.getItem("team_leId");
	if (isnull(leId)) {
		leId=null;
	}else{
		pubRange=0;
	}
	name=_trim(name);
	target=_trim(target);
	if (!centen(name,2,40)) {
		$(".Servetwo_alert_con").html("名称1~20个汉字");
		$(".Servetwo_alert").show();
		setTimeout(function(){$(".Servetwo_alert").hide();},2000);
		via=false;
		return false;
	}
	if (!centen(target,2,80)) {
		$(".Servetwo_alert_con").html("描述1~40个汉字");
		$(".Servetwo_alert").show();
		setTimeout(function(){$(".Servetwo_alert").hide();},2000);
		via=false;
		return false;
	}
	
	if (isnull(startTime)) {
		$(".Servetwo_alert_con").html("开始时间不能为空");
		$(".Servetwo_alert").show();
		setTimeout(function(){$(".Servetwo_alert").hide();},2000);
		via=false;
		return false;
	}
	if (isnull(endTime)) {
		$(".Servetwo_alert_con").html("结束时间不能为空");
		$(".Servetwo_alert").show();
		setTimeout(function(){$(".Servetwo_alert").hide();},2000);
		via=false;
		return false;
	}
	if (!isnull(closeTime) && closeTime>endTime) {
		$(".Servetwo_alert_con").html("截止时间不能大于结束时间");
		$(".Servetwo_alert").show();
		setTimeout(function(){$(".Servetwo_alert").hide();},2000);
		via=false;
		return false;
	}
	if (startTime > endTime || startTime == endTime ) {
		$(".Servetwo_alert_con").html("结束时间应大于开始时间");
		$(".Servetwo_alert").show();
		setTimeout(function(){$(".Servetwo_alert").hide();},2000);
		via=false;
		return false;
	}
	//地址经纬度校验
	if (isnull(lng) || isnull(lat)) {
		$(".dz_ts").show();
		setTimeout(function(){$(".dz_ts").hide();},2000);
		via=false;
		return false;
	}
	//参与人数校验
	var maxCount=$("#number").val();
	if (isnull(maxCount) ||(!isnull(maxCount) && Regexs.num.test(maxCount)) ) {
		$(".rs_ts").show();
		setTimeout(function(){$(".rs_ts").hide();},2000);
		 via=false;
		 return false;
	}
	var eId=localStorage.getItem("team_eId");
	if (frequency==1) {
		frequency=-15;
	}else if(frequency==2){
		frequency=-60;
	}else if(frequency==3){
		frequency=-60*3;
	}else if(frequency==4){
		frequency=-60*24;
	}
	if (isnull(closeTime)) {
		closeTime=endTime;
	}
	
	var Json='"name":"'
		+name
		+'","target":"'
		+target
		+'","eId":"'
		+eId
		+'","leId":"'
		+leId
		+'","createBy":"'
		+createBy
		+'","lng":"'
		+lng
		+'","lat":"'
		+lat
		+'","province":"'
		+province
		+'","city":"'
		+city
		+'","district":"'
		+district
		+'","address":"'
		+address
		+'","strStartTime":"'
		+startTime
		+'","strEndTime":"'
		+endTime
		+'","strCloseTime":"'
		+closeTime
		+'","maxCount":"'
		+maxCount
		+'","lessCount":"'
		+0
		+'","isValid":"'
		+0
		+'","state":"'
		+0
		+'","frequency":"'
		+frequency
		+'","pubRange":"'
		+pubRange
		+'","type":"'
		+2
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	 if (via) {
		 if (isnull(eId)) {
			 save_erve_event(start);
		}else{
			updateInfo(start);
		}
	}


}

//更新组织信息
function updateInfo(start){
	var startTime=$("#appDateTime").val();//开始时间
	var data='{'+start+'}';
	$.ajax({
		  url:dataLink+"event/updateEnvet",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     contentType: "application/json; charset=utf-8",
	     success:function(result){
	   	  if(result.msg=="success"){
	   		localStorage.setItem("team_startTime",startTime);
	   		localStorage.removeItem("team_partyLogo");
	   		window.location.href="publish_event.html";
	   	  }
	     },  
	     timeout:3000  
	});
}

