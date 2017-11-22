$(function(){
	$("body").height($(window).height());
	$(".form_datetime").datetimepicker({
        format: "yyyy MM dd - HH:ii P",
        showMeridian: true,
        autoclose: true,
        todayBtn: true
    });
	
	var physics=localStorage.getItem("team_physics");
	if (physics !=1) {
		window.location.href="index.html";
	}
	$(".Serve_footer li").click(function(){
		$(this).addClass("Serve_current").siblings().removeClass("Serve_current");
	});
})

var leId = localStorage.getItem("team_leId");
if(!isnull(leId)){
	$("title").html("创建轻企目标");
}

//发布事件时间管理

$(".Serve_con_save").click(function(){
	check_name();
});


//保存时间管理事件
function save_time_event(start){
	$(".event_summary").show();
	$(".event_summary1").show();
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
	   		localStorage.setItem("team_eId",item.eId);
	   		localStorage.removeItem("team_physics");
	   		window.location.href="eventdetailsone.html";
	   	  }
	     },  
	     timeout:3000  
	});
	
}

//校验事件名称是否存在
function check_name(){
	var createBy=localStorage.getItem("team_userId");
	var name=$(".time_affair").val();//事件名称
	name=_trim(name);
	if (centen(name,2,40)) {
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
						$(".Serve_alert_con").html("名称已存在");
						$(".Serve_alert").show();
						setTimeout(function(){$(".Serve_alert").hide();},2000);
						return false;
					}else{
						check_info();
					}
					
				}
			},  
			timeout:3000  
		}); 
	}else{
		$(".Serve_alert_con").html("名称1~20个汉字");
		$(".Serve_alert").show();
		setTimeout(function(){$(".Serve_alert").hide();},2000);
		return false;
	}
}

/**
 * 校验信息
 */
function check_info(){
	var via=true;
	var createBy=localStorage.getItem("team_userId");
	var name=$(".time_affair").val();//事件名称
	var target=$(".time_aim").val();//事件目标
	var promise=$(".time_acceptance").val();//事件承诺
	var startTime=$(".appDateTime").val();//开始时间
	var endTime=$(".appDateTimetwo").val();//结束时间
	var frequency=$(".Serve_current").find("i").html();//提醒状态
	
	if (isnull(leId)) {
		leId=null;
	}
	name=_trim(name);
	target=_trim(target);
	if (frequency==1) {
		frequency=-30;
	}else if(frequency==2){
		frequency=-60;
	}else if(frequency==3){
		frequency=-60*4;
	}
	var pubRange=2;//可见范围	
	if (!centen(name,2,40)) {
		$(".Serve_alert_con").html("名称1~20个汉字");
		$(".Serve_alert").show();
		setTimeout(function(){$(".Serve_alert").hide();},2000);
		via=false;
		return false;
	}
	if (!centen(target,2,80)) {
		$(".Serve_alert_con").html("描述1~40个汉字");
		$(".Serve_alert").show();
		setTimeout(function(){$(".Serve_alert").hide();},2000);
		via=false;
		return false;
	}
	if (isnull(endTime)) {
		$(".Serve_alert_con").html("结束时间不能为空");
		$(".Serve_alert").show();
		setTimeout(function(){$(".Serve_alert").hide();},2000);
		via=false;
		return false;
	}
	if (startTime > endTime || startTime == endTime ) {
		$(".Serve_alert_con").html("结束时间应大于开始时间");
		$(".Serve_alert").show();
		setTimeout(function(){$(".Serve_alert").hide();},2000);
		via=false;
		return false;
	}
	
	var Json='"name":"'
		+name
		+'","target":"'
		+target
		+'","leId":"'
		+leId
		+'","promise":"'
		+promise
		+'","createBy":"'
		+createBy
		+'","strStartTime":"'
		+startTime
		+'","strEndTime":"'
		+endTime
		+'","strCloseTime":"'
		+endTime
		+'","isValid":"'
		+1
		+'","state":"'
		+0
		+'","frequency":"'
		+frequency
		+'","pubRange":"'
		+pubRange
		+'","type":"'
		+0
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	 if (via) {
		 save_time_event(start);
	}


}