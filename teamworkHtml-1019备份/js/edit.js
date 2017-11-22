var sTime=localStorage.getItem("team_sTime");
var week="";//重复周期
tab(".Add_tab li","Add_current_tab",".Add_list");
$(function(){
	var d = new Date();
	var y = d.getFullYear();
	var m = d.getMonth() + 1;
	var date = d.getDate();
	var hour = d.getHours(); //获取系统时，
	var min = d.getMinutes(); //分
	var nowtime = y+"-"+m+"-"+date;
	m<10?m = "0"+m:m;
	min<10?min = "0"+min:min;
	hour<10?hour = "0"+hour:hour;
	$(".time_ts").attr("data-lcalendar",nowtime+",2050-12-31");
	$(".time_ts").attr("flage","true");
	$(".control-group2").on("click",".time_ts",function(event){
		event.stopPropagation();
		$('.appDateTimetwo').datetimepicker('remove');
		if($(this).attr("flage") == "false"){
			$(".summary_time").css("display","block");
			$(".summary_time1").css("display","block");
		}else{
			if($(".time_ts_one").val() != ""){
				$(".summary_time").css("display","block");
				$(".summary_time3").css("display","none");
				$(".summary_time1").css("display","block");
				$(".bgtime3").html($(".time_ts_one").val());
			}else{
				$(".summary_time").css("display","block");
				$(".summary_time1").css("display","block");
			}
		}
	});
	$(".summary_time3 ul li").click(function(){
		var time = $(this).find("p").html();
		var numbers = time.substring(0,1);
		var starttime = $(".bgtime3").html();
		starttime = starttime.replace(new RegExp("-","gm"),"/");
		var starttimeHaoMiao = (new Date(starttime)).getTime(); //得到毫秒数
		if($(this).index() == 0){
			getYear(4,starttimeHaoMiao);
			$(".summary_time").css("display","none");
			$(".summary_time3").css("display","none");
			$(".time_ts").val(getYear(4,starttimeHaoMiao));
		}else if($(this).index() == 1){
			getYear(8,starttimeHaoMiao);
			$(".summary_time").css("display","none");
			$(".summary_time3").css("display","none");
			$(".time_ts").val(getYear(8,starttimeHaoMiao));
		}else if($(this).index() == 2){
			getYear(24,starttimeHaoMiao);
			$(".summary_time").css("display","none");
			$(".summary_time3").css("display","none");
			$(".time_ts").val(getYear(24,starttimeHaoMiao));
		}else if($(this).index() == 3){
			getYear(48,starttimeHaoMiao);
			$(".summary_time").css("display","none");
			$(".summary_time3").css("display","none");
			$(".time_ts").val(getYear(48,starttimeHaoMiao));
		}else if($(this).index() == 4){
			$(".btn_qd").click(function(){
				$(".shuzi").val()
				getYear($(".shuzi").val(),starttimeHaoMiao);
				$(".summary_time").css("display","none");
				$(".summary_time3").css("display","none");
				$(".time_ts").val(getYear($(".shuzi").val(),starttimeHaoMiao));
			})
		}
	})
	$(".time_ts").focus(function(){
        document.activeElement.blur();
    });
    $(".time_ts_one").focus(function(){
        document.activeElement.blur();
    });
	$(".summary_time").click(function(){
		$(".summary_time").css("display","none");
		$(".summary_time1").css("display","none");
		$(".summary_time2").css("display","none");
		$(".summary_time3").css("display","none");
		$('.form_datetime').datetimepicker('remove');
		$('.form_datetimetwo').datetimepicker('remove');
	})
	$(".custom").click(function(event){
		event.stopPropagation();
		$(".summary_time").css("display","none");
		$(".summary_time1").css("display","none");
		$(".summary_time3").css("display","none");
		$('.form_datetimetwo').datetimepicker('show');
	})
	$(".custom2").click(function(event){
		event.stopPropagation();
		$(".summary_time").css("display","none");
		$(".summary_time2").css("display","none");
		$('.form_datetime').datetimepicker('show');
	});
	$(".appDateTime").click(function(){
//		$(this).focus();
		var d = new Date();
		var hour = d.getHours(); //获取系统时
		var min = d.getMinutes(); //分
		hour<10?m = "0"+hour:hour;
		min<10?min = "0"+min:min;
		$(".now_time").html(hour+":"+min);
		$(".summary_time").css("display","block");
		$(".summary_time2").css("display","block");
		$('.form_datetime').datetimepicker('hide');
		 $('.form_datetime').datetimepicker().on('changeDate', function(ev){
				//alert(1);
				//return false;
				$(".form_datetime").datetimepicker("hide");
				$(".form_datetimetwo").datetimepicker("remove");
	 	});
	});
	//	edit by lixiaobin 2017/08/11 选择单次截至时间
	$(".appDateTimetwo").click(function(){
//		$(this).focus();
		var d = new Date();
		var hour = d.getHours(); //获取系统时
		var min = d.getMinutes(); //分
		hour<10?m = "0"+hour:hour;
		min<10?min = "0"+min:min;
		$(".now_time").html(hour+":"+min);
		$(".summary_time").css("display","block");
		$(".summary_time1").css("display","block");
		//$('.form_datetimetwo').datetimepicker('hide');
		$('.form_datetimetwo').datetimepicker().on('changeDate', function(ev){
			//alert(1);
			//return false;
			$(".form_datetimetwo").datetimepicker("hide");
			$(".form_datetime").datetimepicker("remove");
		});
	});
	function datatime(){
		var calendardatetime = new lCalendar();
		calendardatetime.init({
		    'trigger': '.time_ts',
		    'type': 'datetime'
		});
	}
	function datatime3(){
		var calendardatetime = new lCalendar();
		calendardatetime.init({
		    'trigger': '.time_ts_one',
		    'type': 'datetime'
		});
	}
//	edit by zhengjunting 2017/06/19 点击委托他们完成弹出选择成员弹窗
	$(".top button").click(function(){
		$(".Additems_alert").show();
		getGroup();
	});
	//	edit by zhengjunting 2017/06/12 点击新好友跳转
	$(".Select_new").click(function(){
		window.location.href="newfriend.html";
	});
	//	edit by lixiaobin 2017/08/11 截止时间持续时长选择
	$(".summary_time3 ul li p").click(function(){
		$(".summary_time3 ul li p").removeClass("active");
		$(this).addClass("active");
		if($(this).hasClass("custom3")){
			$(this).hide();
			$(this).parent().css("width","55%");
			$(".shuzi").css("display","block");
			$(".btn_qd").css("display","block");
		}else{
			$(".summary_time1").css("display","none");
			$('.form_datetimetwo').datetimepicker('remove');
		}
	})
	//	edit by lixiaobin 2017/08/11 选择开始时间
	$(".time_ts_one").click(function(){
		$(".summary_time").css("display","block");
		$(".summary_time2").css("display","block");
	})
})

//取消添加事项
$("#btn1").click(function(){
	var type=localStorage.getItem("team_type");
	if (type==0) {
			//localStorage.removeItem("team_type");
			window.location.href="eventdetailsone.html";
	}else if(type==1){
			//localStorage.removeItem("team_type");
			window.location.href="svc_red_eventdetails.html";
	}
});

//返回首页
$(".return_index").click(function(){
	window.location.href="index.html";
});

//确认添加事项
$("#btn2").click(function(){
	check_info();
});

//校验添加信息
function check_info(){
	via=true;
	var  depict=$(".describe_box").val();
	depict=_trim(depict);
	var finishTime=$(".time_ts").val();//完成时间
	var fgId=localStorage.getItem("team_fgId");
	var strStartTime=$(".time_ts_one").val();//开始时间
	
	if(isnull(strStartTime) || strStartTime<sTime){
		$(".time_tishi").html("开始时间应大于项目开始时间");
		$(".time_tishi").css("display","block");
		setTimeout(function(){
			$(".time_tishi").css("display","none");
		},2000)
		via=false;
		return false;
	}
	if(isnull(strStartTime) || strStartTime>finishTime){
		$(".time_tishi").html("完成时间应大于开始时间");
		$(".time_tishi").css("display","block");
		setTimeout(function(){
			$(".time_tishi").css("display","none");
		},2000)
		via=false;
		return false;
	}
	if (!centen(depict,2,200)) {
		$(".time_tishi").html("描述内容1~100汉字");
		$(".time_tishi").show();
		setTimeout(function(){$(".time_tishi").hide();},2000);
		via=false;
		return false;
	}
	if (via) {
		save_item();
	}
}

//保存事项
function save_item(){
	var  depict=$(".describe_box").val();
	depict=_trim(depict);
	var eId=localStorage.getItem("team_eId");
	var finishTime=$(".time_ts").val();//完成时间
	var strStartTime=$(".time_ts_one").val();//开始时间
	var userId=localStorage.getItem("team_userId");
	var eiId=localStorage.getItem("team_eiId");
	$(".event_summary").show();
	$(".event_summary1").show();
	var Json='"eId":"'
		+eId
		+'","strFinishTime":"'
		+finishTime
		+'","strStartTime":"'
		+strStartTime
		+'","eiId":"'
		+eiId
		+'","dutyId":"'
		+userId
		+'","eiDesc":"'
		+depict
		+'","createBy":"'
		+userId
		+'","state":"'
		+0
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	var data='{'+start+'}';
	$.ajax({
		url : dataLink + "item/updateItem",
		type : "POST",
		data : data,
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		success : function(result) {
			if (result.msg == "success") {
				localStorage.removeItem("team_fgId");
				var type = localStorage.getItem("team_type");
				if (type == 0) {
					localStorage.removeItem("team_type");
					window.location.href = "eventdetailsone.html";
				} else if (type == 1) {
					localStorage.removeItem("team_type");
					window.location.href = "svc_red_eventdetails.html";
				}
			}
		},
		timeout : 3000
	});
	
}

echo();
// 回显
function  echo(){
	var eiId=localStorage.getItem("team_eiId");
	var userId=localStorage.getItem("team_userId");
	var data={"eiId":eiId}
	$.ajax({
		  type : "GET",
	      url:dataLink+"item/getActionItem",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  var item = json.item;
	    		  localStorage.setItem("team_fgId",item.dutyId);
	    		  headUrl=isnull(item.headUrl)?"img/img03.png":imageHeadSrc+item.headUrl;
	    		  $(".time_ts").val(TimeIt(item.finishTime,1));
	    		  $(".time_ts_one").val(TimeIt(item.startTime,1));
	    		  var reg=new RegExp("<br>","g"); //创建正则RegExp对象    
				  var newstr=item.eiDesc.replace(reg,"\n");  
	    		  $(".describe_box").val(newstr);
	    		  echo_duty=item.dutyId;
	    	  }
	      },  
	      timeout:3000  
	 }); 
}

//今天下午六点
$(".now_noon").click(function(){
	$(".time_ts").val(TimeIt(TimeInfo(18),1));
	$(".summary_time").css("display","none");
	$(".summary_time1").css("display","none");
});
//明天上午十点
$(".now_arvo").click(function(){
	$(".time_ts").val(TimeIt(TimeInfo(34),1));
	$(".summary_time").css("display","none");
	$(".summary_time1").css("display","none");
});

//明天下午六点
$(".morn_noon").click(function(){
	$(".time_ts").val(TimeIt(TimeInfo(42),1));
	$(".summary_time").css("display","none");
	$(".summary_time1").css("display","none");
});
//本周五下班
$(".morn_arvo").click(function(){
	$(".time_ts").val(TimeIt(next_week(-30),1));
	$(".summary_time").css("display","none");
	$(".summary_time1").css("display","none");
});
//下周一
$(".below_Monday").click(function(){
	$(".time_ts").val(TimeIt(next_week(34),1));
	$(".summary_time").css("display","none");
	$(".summary_time1").css("display","none");
});
$("body").click(function(){
	$(".gearDatetime").remove();
})
$(".gearDatetime").click(function(){
	$(".summary_time").css("display","none");
})

//现在
$(".now_noon2").click(function(){
	var newTime = new Date(); //就得到普通的时间了 
	var year = newTime.getFullYear();   //获取系统的年；
	var mour = newTime.getMonth()+1;   //获取系统月份，由于月份是从0开始计算，所以要加1
	var day = newTime.getDate(); // 获取系统日，
	var hour = newTime.getHours(); //获取系统时，
	var min = newTime.getMinutes(); //分
	mour<10?mour = "0"+mour:mour;
	min<10?min = "0"+min:min;
	hour<10?hour = "0"+hour:hour;
	$(".time_ts_one").val(year+"-"+mour+"-"+day+" "+hour+":"+min);
	$(".summary_time").css("display","none");
	$(".summary_time2").css("display","none");
})
//今天中午 十二点
$(".now_arvo2").click(function(){
	$(".time_ts_one").val(TimeIt(TimeInfo(12),1));
	$(".summary_time").css("display","none");
	$(".summary_time2").css("display","none");
});
//明天早上八点
$(".morn_noon2").click(function(){
	$(".time_ts_one").val(TimeIt(TimeInfo(32),1));
	$(".summary_time").css("display","none");
	$(".summary_time2").css("display","none");
});
//后天早上八点
$(".morn_arvo2").click(function(){
	$(".time_ts_one").val(TimeIt(TimeInfo(56),1));
	$(".summary_time").css("display","none");
	$(".summary_time2").css("display","none");
});
//下周一早上八点
$(".below_Monday2").click(function(){
	$(".time_ts_one").val(TimeIt(next_week(32),1));
	$(".summary_time").css("display","none");
	$(".summary_time2").css("display","none");
});



//周一至周五
$(".working_day").click(function(){
	 week="MO,TU,WE,TH,FR";
	$(".summary_time").css("display","none");
	$(".summary_time4").css("display","none");
});

//自定义选择
$(".choice").click(function(){
	 week="";
	$(".active2").each(function(){
		var cycle= $(this).find("i").html();
		if (!isnull(week)) {
			week=week+","+cycle;
		}else{
			week=cycle;
		}
	});
	 week=week;
	//$(".summary_time").css("display","none");
	//$(".summary_time4").css("display","none");
});

function TimeIt(long,siem){
	var remindTime = new Date(long);
	var Times = remindTime.getFullYear()+"-"+Substr(remindTime.getMonth()+1)+"-"+Substr(remindTime.getDate());
	if(siem){
		Times=Times+" "+Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes());
	};
	return Times;
};

//add by wuchao 2017/7/11 获取今天或明天什么时候时间
function TimeInfo(hr){
	var nw = new Date();
	//小时,分钟，秒，毫秒
	nw.setHours(hr, 00,00, 0);
	console.log(TimeIt(nw.getTime(),1));
	return nw.getTime();

}
//add by wuchao 2017/7/11 下周一十点
//next_week(-30);
function next_week(long){
	var now = new Date(); 
	var nowTime = now.getTime() ; 
	var day = now.getDay();
	var oneDayLong = 24*60*60*1000 ; 
	var SundayTime =  nowTime + (7-day)*oneDayLong ; 
	var sunday = new Date(SundayTime);
	sunday.setHours(long, 00,00, 0);
	console.log(TimeIt(sunday.getTime(),1)) ; 
	return sunday.getTime();

}

