$(function(){
	var eventPartake=localStorage.getItem("team_eventPartake");
	if (!isnull(eventPartake)) {
		$(".Serve_footer_delete").show();
		user_list(eventPartake);
	}
	$("body").height($(window).height());
	
	var physics=localStorage.getItem("team_physics");
	if (physics !=1) {
		window.location.href="index.html";
	}
	$(".Serve_footer li").click(function(){
		$(this).addClass("Serve_current").siblings().removeClass("Serve_current");
	});
//	点击删除
$(".Serve_footer_delete").click(function(){
		window.location.href="delete_personthree.html";
	});
	//实时监控input值 
$(".Serve_con_title input").on("input propertychange",function(){  
     if($(this).val()==""){  
     	$(".Serve_save_noclick").show();
           $(".Serve_con_save").hide();
        
     }else{
     	$(".Serve_save_noclick").hide();
           $(".Serve_con_save").show();
     }
})  
	
})

var leId = localStorage.getItem("team_leId");
if(!isnull(leId)){
	$("title").html("创建轻企任务");
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
	   		localStorage.removeItem("team_eventPartake");
	   		localStorage.removeItem("team_eventName");
	   		localStorage.removeItem("team_startTime");
	   		localStorage.removeItem("team_endTime");
	   		window.location.href="taskDetails.html";
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
	var startTime=$("#tests").val();
	var endTime=$("#test").val();//结束时间
	if (isnull(leId)) {
		leId=null;
	}
	name=_trim(name);
	
	if (!centen(name,2,40)) {
		$(".Serve_alert_con").html("名称1~20个汉字");
		$(".Serve_alert").show();
		setTimeout(function(){$(".Serve_alert").hide();},2000);
		via=false;
		return false;
	}
	if (!isnull(endTime) && (startTime > endTime || startTime == endTime) ) {
		$(".Serve_alert_con").html("结束时间应大于开始时间");
		$(".Serve_alert").show();
		setTimeout(function(){$(".Serve_alert").hide();},2000);
		via=false;
		return false;
	}
	var eventPartake=localStorage.getItem("team_eventPartake");
		/* var eventPartake="";
		$(".Serve_footer_list").each(function(){
			var cycle= $(this).find("i").html();
			if (!isnull(eventPartake)) {
				eventPartake=eventPartake+","+cycle;
			}else{
				eventPartake=cycle;
			}
		});*/
		$(".summary_time").css("display","none");
		$(".summary_time5").css("display","none");
	
	var Json='"name":"'
		+name
		+'","leId":"'
		+leId
		+'","eventPartake":"'
		+eventPartake
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



//添加参与人
$(".Serve_footer_add").click(function(){
	var name=$(".time_affair").val();//事件名称
	var startTime=$("#tests").val();//开始时间
	var endTime=$("#test").val();//结束时间
	localStorage.setItem("team_eventName",name);
	localStorage.setItem("team_startTime",startTime);
	localStorage.setItem("team_endTime",endTime);
	if (isnull(leId)) {
		window.location.href="choiceFriendsthree.html";
	}else{
		window.location.href="choiceFriendsfour.html";
	}
});


//用户列表
function user_list(eventPartake) {
	var data = {"userIdStr":eventPartake}
	$.ajax({
		type : "get",
		url : dataLink + "userwx/partakeEvent",
		data : data,
		dataType : "json",
		success : function(json) {
			if (json.msg = "success") {
				var list = json.list;
				if (list.length>0) {
					for (var i = 0; i < list.length; i++) {
						var item=list[i];
						var	headUrl=isnull(item.headUrl)?"img/img03.png":imageHeadSrc+item.headUrl;
						var html ='<li class="Serve_footer_list">';
							html +='<img src="'+headUrl+'" alt="" />';
							html +='<p>'+item.userName+'</p></li>';
						$(".partake_list").append(html);
					}
				}
			}
		}
	});
}



 function showtime(){
   var mydate = new Date();
   var str = "" + mydate.getFullYear() + "-";
   str += (mydate.getMonth()+1) + "-";
   str += mydate.getDate();
   return str;
  }
//时间插件
$(function () {
	

	showTimeone();
	showTimetwo();
	$("#tests").attr("placeholder",showtime());
	$("#tests").val(showtime());
	//alert($("#tests").val());
	//回显内容
	var team_eventName= localStorage.getItem("team_eventName");
	var team_startTime= localStorage.getItem("team_startTime");
	var team_endTime= localStorage.getItem("team_endTime");
	if (!isnull(team_eventName)) {
		$(".time_affair").val(team_eventName);//事件名称
		$(".Serve_save_noclick").hide();
        $(".Serve_con_save").show();
	}
	if (!isnull(team_startTime)) {
		$("#tests").val(team_startTime);
		$("#tests").attr("placeholder",team_startTime);
	}
	if (!isnull(team_endTime)) {
		$("#test").val(team_endTime);//结束时间
		$("#tests").attr("placeholder",team_endTime);
	}


	
});
function showTimeone(){
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
//      mintime:$(".tests").val()
	};
	opt.defaulttwo = {
		theme: 'android-holo light', //皮肤样式
        display: 'modal', //显示方式 
        mode: 'scroller', //日期选择模式
		dateFormat: 'yyyy-mm-dd',//yyyy-mm-dd
		lang: 'zh',
		showNow: true,
		nowText: "今天",
		stepMinute: 10,
//      startYear: curr - 10, //开始年份
        endYear: curr + 50 //结束年份
//      minDate:$(".tests").val()
	};
   $('.settings').bind('change', function() {
        var demo = 'datetime';
        if (!demo.match(/select/i)) {
            $('.demo-test-' + demo).val('');
        }
//              $('.demo-test-' + demo).scroller('destroy').scroller($.extend(opt['time'], opt['default']));
        $("#tests").scroller('destroy').scroller($.extend(opt['date'], opt['default']));
//      $("#test").scroller('destroy').scroller($.extend(opt['date'], opt['defaulttwo']));
        $('.demo').hide();
        $('.demo-' + demo).show();
    });
    $('#demo').trigger('change');
}
function showTimetwo(){
	var curr = new Date().getFullYear();
	var current = new Date();
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
//      startYear: curr - 10, //开始年份
        endYear: curr + 50, //结束年份
        minDate:current
	};
   $('.settings').bind('change', function() {
        var demo = 'datetime';
        if (!demo.match(/select/i)) {
            $('.demo-test-' + demo).val('');
        }
//              $('.demo-test-' + demo).scroller('destroy').scroller($.extend(opt['time'], opt['default']));
//      $("#tests").scroller('destroy').scroller($.extend(opt['date'], opt['default']));
        $("#test").scroller('destroy').scroller($.extend(opt['date'], opt['default']));
        $('.demo').hide();
        $('.demo-' + demo).show();
    });
    $('#demo').trigger('change');
}			
        