$(function(){
	$("body").height($(window).height());
	var now = new Date();
	
//	edit by zhengjunting 2017/06/13 事件公开范围
	$(".Servethree_footertwo li").click(function(){
		$(this).addClass("Servethree_current_two").siblings().removeClass("Servethree_current_two");
	});
//	edit by zhengjunting 2017/06/13 事项提醒
	$(".Servethree_footer li").click(function(){
		$(this).addClass("Servethree_current").siblings().removeClass("Servethree_current");
	});
})
//发布事件服务请求
$(".Servethree_con_save").click(function(){
	check_name();
});
var leId = localStorage.getItem("team_leId");
if(!isnull(leId)){
	$("title").html("创建轻企需求");
	$(".Servethree_footertwo").hide();
}else{
	$(".Servethree_footertwo").show();
}

//保存服务请求事件
function save_erve_event(start){
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
	   		localStorage.removeItem("team_partyLogo");
	   		window.location.href="upload_cover.html";
	   	  }
	     },  
	     timeout:3000  
	});
	
}
//校验事件名称是否存在
function check_name(){
	var createBy=localStorage.getItem("team_userId");
	var name=$(".erve_affair").val();//事件名称
	name=_trim(name);
	if (!centen(name,2,40)) {
		$(".Servethree_alert_con").html("名称1~20个汉字");
		$(".Servethree_alert").show();
		setTimeout(function(){$(".Servethree_alert").hide();},2000);
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
		    			 $(".Servethree_alert_con").html("名称已存在");
						$(".Servethree_alert").show();
						setTimeout(function(){$(".Servethree_alert").hide();},2000);
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
	var createBy=localStorage.getItem("team_userId");
	var name=$(".erve_affair").val();//事件名称
	var target=$(".erve_aim").val();//事件目标
	var startTime=$(".appDateTime").val();//开始时间
	var endTime=$(".appDateTimetwo").val();//结束时间
	var frequency=$(".Servethree_current").find("i").html();//提醒状态
	var pubRange=$(".Servethree_current_two").find("i").html();//可见范围
	//var leId = localStorage.getItem("team_leId");
	if (isnull(leId)) {
		leId=null;
	}else{
		pubRange=0;
	}
	name=_trim(name);
	target=_trim(target);
	if (!centen(name,2,40)) {
		$(".Servethree_alert_con").html("名称1~20个汉字");
		$(".Servethree_alert").show();
		setTimeout(function(){$(".Servethree_alert").hide();},2000);
		via=false;
		return false;
	}
	if (!centen(target,2,80)) {
		$(".Servethree_alert_con").html("描述1~40个汉字");
		$(".Servethree_alert").show();
		setTimeout(function(){$(".Servethree_alert").hide();},2000);
		via=false;
		return false;
	}
	if (isnull(startTime)) {
		$(".Servethree_alert_con").html("开始时间不能为空");
		$(".Servethree_alert").show();
		setTimeout(function(){$(".Servethree_alert").hide();},2000);
		via=false;
		return false;
	}
	if (isnull(endTime)) {
		$(".Servethree_alert_con").html("结束时间不能为空");
		$(".Servethree_alert").show();
		setTimeout(function(){$(".Servethree_alert").hide();},2000);
		via=false;
		return false;
	}
	if (startTime > endTime || startTime == endTime ) {
		$(".Servethree_alert_con").html("结束时间应大于开始时间");
		$(".Servethree_alert").show();
		setTimeout(function(){$(".Servethree_alert").hide();},2000);
		via=false;
		return false;
	}
	if (frequency==1) {
		frequency=-30;
	}else if(frequency==2){
		frequency=-60;
	}else if(frequency==3){
		frequency=-60*4;
	}
	var eId=localStorage.getItem("team_eId");
	var Json='"name":"'
		+name
		+'","leId":"'
		+leId
		+'","eId":"'
		+eId
		+'","target":"'
		+target
		+'","createBy":"'
		+createBy
		+'","strStartTime":"'
		+startTime
		+'","strEndTime":"'
		+endTime
		+'","strCloseTime":"'
		+endTime
		+'","isValid":"'
		+0
		+'","state":"'
		+0
		+'","frequency":"'
		+frequency
		+'","pubRange":"'
		+pubRange
		+'","type":"'
		+1
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

//更新服务请求信息
function updateInfo(start){
	var data='{'+start+'}';
	$.ajax({
		  url:dataLink+"event/updateEnvet",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     contentType: "application/json; charset=utf-8",
	     success:function(result){
	   	  if(result.msg=="success"){
	   		window.location.href="upload_cover.html";
	   	  }
	     },  
	     timeout:3000  
	});
}
//时间插件
$(function () {
    var curr = new Date().getFullYear();
    var opt={};
	opt.date = {preset : 'date'};
	opt.datetime = {preset : 'datetime'};
	opt.time = {preset : 'time'};

  opt.default = {
		theme: 'android-holo light', //皮肤样式
        display: 'modal', //显示方式 
        mode: 'scroller', //日期选择模式
		dateFormat: 'yyyy-mm-dd',//yyyy-mm-dd
		lang: 'zh',
		showNow: true,
		nowText: "今天",
		stepMinute: 10,
        startYear: curr - 10, //开始年份
        endYear: curr + 50 //结束年份
	};
   $('.settings').bind('change', function() {
        var demo = 'datetime';
        if (!demo.match(/select/i)) {
            $('.demo-test-' + demo).val('');
        }
//              $('.demo-test-' + demo).scroller('destroy').scroller($.extend(opt['time'], opt['default']));
        $("#tests").scroller('destroy').scroller($.extend(opt['datetime'], opt['default']));
        $("#test").scroller('destroy').scroller($.extend(opt['datetime'], opt['default']));
        $('.demo').hide();
        $('.demo-' + demo).show();
    });
    $('#demo').trigger('change');
});