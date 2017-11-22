var sTime=localStorage.getItem("team_sTime");
var eTime=localStorage.getItem("team_eTime");
var maxDate=new Date();
var saTime=new Date(); 
var echo_duty=null;
var week="";//重复周期
var cycleState=0;// 0 单次  1周期
var cycle_freq=null;//周期频率:0 天、1 周
if (new Date().getTime()>saTime.getTime()) {
	saTime=new Date();
}
$(function(){
	var d = new Date();
	var y = d.getFullYear();
	var m = d.getMonth() + 1;
	var date = d.getDate();
	var nowtime = y+"-"+m+"-"+date;
	$(".time_ts").attr("data-lcalendar",nowtime+",2050-12-31");
	$(".time_ts").click(function(){
		event.stopPropagation();
		$(".summary_time").css("display","block");
		$(".summary_time1").css("display","block");
		$(".gearDatetime").css("display","block");
		
	})
	$(".time_ts").focus(function(){
        document.activeElement.blur();
    });
	$(".summary_time").click(function(){
		$(".summary_time").css("display","none");
		$(".summary_time4").css("display","none");
		$(".summary_time5").css("display","none");
		$(".summary_time6").css("display","none");
		$(".summary_time7").css("display","none");
	})
	//	周期自定义
	$(".Add_nine").on("click",function(event){
		event.stopPropagation();
		$(".summary_time").css("display","none");
		$(".summary_time6").css("display","none");
//		$(".time_ts_one").attr("id","appDateTime");
		datetimeone();
		$(".time_ts_three").click();
		$(".summary_time").css("display","none");
		$(".summary_time6").css("display","none");
//		$(".time_ts_one").removeAttr("id");
	})
	//	周期自定义
	$(".end_nine").on("click",function(event){
		event.stopPropagation();
		$(".summary_time").css("display","none");
		$(".summary_time7").css("display","none");
//		$(".time_ts_one").attr("id","appDateTime");
		datetimetwo();
		$(".time_ts_four").click();
		$(".summary_time").css("display","none");
		$(".summary_time7").css("display","none");
//		$(".time_ts_one").removeAttr("id");
	})
//		edit by zhengjunting 2017/08/23 周期开始时间
	function datetimeone(){
//		$(this).focus();
		var calendardatetime1 = new lCalendar();
		calendardatetime1.init({
		    'trigger': '.time_ts_three',
		    'type': 'time'
		});	
	}
	
	//	edit by zhengjunting 2017/08/23 周期截止时间
	function datetimetwo(){
//		$(this).focus();
	var calendardatetime2 = new lCalendar();
	calendardatetime2.init({
	    'trigger': '.time_ts_four',
	    'type': 'time'
	});
	}
//	点击周期开始时间
	$(".time_ts_three").click(function(){
		$(".summary_time").css("display","block");
		$(".summary_time6").css("display","block");
	});
//	点击周期结束时间
	$(".time_ts_four").click(function(){
		$(".summary_time").css("display","block");
		$(".summary_time7").css("display","block");
	});
	//	edit by lixiaobin 2017/08/11 选择自定义周期
	$(".summary_time4 .custom4").click(function(){
		$(".summary_time5").css("display","block");
		$(".summary_time4").css("display","none");
	})
	//	edit by lixiaobin 2017/08/11 设置选择自定义
	$(".summary_time5 .ul1 li").click(function(){
		if($(this).hasClass("active2")){
			$(this).removeClass("active2");
		}else{
			$(this).addClass("active2");
		}
	})
	//	edit by lixiaobin 2017/08/11 选择自定义周期 点击取消返回
	$(".summary_time5_qx").click(function(){
		$(".summary_time5").css("display","none");
		$(".summary_time").css("display","none");
	})
	//	edit by lixiaobin 2017/08/11 选择重复周期
	$(".time_ts_two").click(function(){
		$(".summary_time").css("display","block");
		$(".summary_time4").css("display","block");
	})
	$(".time_ts_two").focus(function(){
        document.activeElement.blur();
    });
    $(".time_ts_three").focus(function(){
        document.activeElement.blur();
    });
    $(".time_ts_four").focus(function(){
        document.activeElement.blur();
    });
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
$("body").click(function(){
//	$(".gearDatetime").remove();
	$(".gearDate").remove();
})
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
	
	var str = $(".Add_time_one input").val();
	var arr = [];
	arr = str.split(":");
//	console.log(arr);
	var hour = arr[0]*60*60*1000;
	var min = arr[1]*60*1000;
	//var sed = arr[2]*1000;
	var Millised = hour + min;
	console.log(Millised);
	var str2 = $(".Add_time_two input").val();
	var arr2 = [];
	arr2 = str2.split(":");
//	console.log(arr);
	var hour2 = arr2[0]*60*60*1000;
	var min2 = arr2[1]*60*1000;
	//var sed2 = arr2[2]*1000;
	var Millised2 = hour2 + min2;
	console.log(Millised2);
	if(isnull(Millised) ||isnull(Millised) || Millised2<=Millised){
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
		save_item(Millised,Millised2);
	}
}

//保存事项
function save_item(Millised,Millised2){
	var  depict=$(".describe_box").val();
	depict=_trim(depict);
	var eId=localStorage.getItem("team_eId");
	var userId=localStorage.getItem("team_userId");
	var eiId=localStorage.getItem("team_eiId");
	$(".event_summary").show();
	$(".event_summary1").show();
	var Json='"eId":"'
		+eId
		+'","circleStartTime":"'
		+Millised
		+'","circleFinishTime":"'
		+Millised2
		+'","eiId":"'
		+eiId
		+'","eiDesc":"'
		+depict
		+'","createBy":"'
		+userId
		+'","dutyId":"'
		+userId
		+'","cycle":"'
		+week
		+'","cycleFreq":"'
		+cycle_freq
		+'","isCycle":"'
		+1
		+'","cycleState":"'
		+0
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	var data='{'+start+'}';
	$.ajax({
		  url:dataLink+"item/updateCycItem",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     contentType: "application/json; charset=utf-8",
	     success:function(result){
	   	  if(result.msg=="success"){
	   		localStorage.removeItem("team_fgId");
	   		var type=localStorage.getItem("team_type");
	   	  if (result.succeed==0) {
	   			setTimeout(function(){$(".event_summary").hide();
				$(".event_summary1").hide();$(".tishi").show();},1000);
				setTimeout(function(){$(".tishi").hide();},2000);
			}else{
				var type=localStorage.getItem("team_type");
				if (type==0) {
					localStorage.removeItem("team_type");
					window.location.href="eventdetailsone.html";
				}else if(type==1){
					localStorage.removeItem("team_type");
					window.location.href="svc_red_eventdetails.html";
				}
			}
	   	  }
	     },  
	     timeout:3000  
	});
	
}

echo();
//回显
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
	    		  var templet = json.templet;
	    		  var week_name="";
	    		  if (!isnull(templet.cycle)) {
	    			  week=templet.cycle;
	    			  cycle_freq=1;
	    			  if (week=="MO,TU,WE,TH,FR") {
	    				  week_name="周一至周五";
					}else{
						var  arr=week.split(",");
						for (var i = 0; i < arr.length; i++) {
							var str= arr[i];
							if (!isnull(week_name)) {
								week_name=week_name+","+frequency.week(str);
							}else{
								week_name=frequency.week(str);
							}
						}
					}
	    		 
	    		  }else{
	    			  cycle_freq=0; 
	    			  week_name="每天";
	    		  }
	    		  $(".time_ts_two").val(week_name);
	    		  $(".time_ts_four").val(TimeIt(item.finishTime));
	    		  $(".time_ts_three").val(TimeIt(item.startTime));
	    		  var reg=new RegExp("<br>","g"); //创建正则RegExp对象    
				  var newstr=item.eiDesc.replace(reg,"\n"); 
	    		  $(".describe_box").val(newstr);
	    	  }
	      },  
	      timeout:3000  
	 }); 
}



//每天
$(".every_day").click(function(){
	cycle_freq=0;
	 $(".time_ts_two").val("每天");
	$(".summary_time").css("display","none");
	$(".summary_time4").css("display","none");
});

//周一至周五
$(".working_day").click(function(){
	cycle_freq=1;
	 week="MO,TU,WE,TH,FR";
	var name= $(".working_day").find("p").html();
	 $(".time_ts_two").val(name);
	$(".summary_time").css("display","none");
	$(".summary_time4").css("display","none");
});

//自定义选择
$(".choice").click(function(){
	cycle_freq=1;
	 week="";
	 var week_name="";
	$(".active2").each(function(){
		var cycle= $(this).find("i").html();
		var name= $(this).find("span").html();
		if (!isnull(week)) {
			week=week+","+cycle;
		}else{
			week=cycle;
		}
		if (!isnull(week_name)) {
			week_name=week_name+","+name;
		}else{
			week_name=name;
		}
	});
	 week=week;
	 $(".time_ts_two").val(week_name);
	$(".summary_time").css("display","none");
	$(".summary_time5").css("display","none");
});

//周期开始时间——————————
//上午8点
$(".Add_one").click(function(){
	$(".time_ts_three").val(TimeIt(TimeInfo(8)));
	$(".summary_time").css("display","none");
	$(".summary_time6").css("display","none");
});
//上午9点
$(".Add_two").click(function(){
	$(".time_ts_three").val(TimeIt(TimeInfo(9)));
	$(".summary_time").css("display","none");
	$(".summary_time6").css("display","none");
});
//上午10点
$(".Add_three").click(function(){
	$(".time_ts_three").val(TimeIt(TimeInfo(10)));
	$(".summary_time").css("display","none");
	$(".summary_time6").css("display","none");
});
//中午12点
$(".Add_four").click(function(){
	$(".time_ts_three").val(TimeIt(TimeInfo(12)));
	$(".summary_time").css("display","none");
	$(".summary_time6").css("display","none");
});
//下午15点
$(".Add_five").click(function(){
	$(".time_ts_three").val(TimeIt(TimeInfo(15)));
	$(".summary_time").css("display","none");
	$(".summary_time6").css("display","none");
});
//下午16点
$(".Add_six").click(function(){
	$(".time_ts_three").val(TimeIt(TimeInfo(16)));
	$(".summary_time").css("display","none");
	$(".summary_time6").css("display","none");
});
//下午18点
$(".Add_seven").click(function(){
	$(".time_ts_three").val(TimeIt(TimeInfo(18)));
	$(".summary_time").css("display","none");
	$(".summary_time6").css("display","none");
});
//晚上22点
$(".Add_eight").click(function(){
	$(".time_ts_three").val(TimeIt(TimeInfo(22)));
	$(".summary_time").css("display","none");
	$(".summary_time6").css("display","none");
});
//周期结束时间——————————
//上午8点
$(".end_one").click(function(){
	$(".time_ts_four").val(TimeIt(TimeInfo(8)));
	$(".summary_time").css("display","none");
	$(".summary_time7").css("display","none");
});
//上午9点
$(".end_two").click(function(){
	$(".time_ts_four").val(TimeIt(TimeInfo(9)));
	$(".summary_time").css("display","none");
	$(".summary_time7").css("display","none");
});
//上午10点
$(".end_three").click(function(){
	$(".time_ts_four").val(TimeIt(TimeInfo(10)));
	$(".summary_time").css("display","none");
	$(".summary_time7").css("display","none");
});
//中午12点
$(".end_four").click(function(){
	$(".time_ts_four").val(TimeIt(TimeInfo(12)));
	$(".summary_time").css("display","none");
	$(".summary_time7").css("display","none");
});
//下午15点
$(".end_five").click(function(){
	$(".time_ts_four").val(TimeIt(TimeInfo(15)));
	$(".summary_time").css("display","none");
	$(".summary_time7").css("display","none");
});
//下午16点
$(".end_six").click(function(){
	$(".time_ts_four").val(TimeIt(TimeInfo(16)));
	$(".summary_time").css("display","none");
	$(".summary_time7").css("display","none");
});
//下午18点
$(".end_seven").click(function(){
	$(".time_ts_four").val(TimeIt(TimeInfo(18)));
	$(".summary_time").css("display","none");
	$(".summary_time7").css("display","none");
});
//晚上22点
$(".end_eight").click(function(){
	$(".time_ts_four").val(TimeIt(TimeInfo(22)));
	$(".summary_time").css("display","none");
	$(".summary_time7").css("display","none");
});


function TimeIt(long,siem){
	var remindTime = new Date(long);
	if(siem){
	var Times = remindTime.getFullYear()+"-"+Substr(remindTime.getMonth()+1)+"-"+Substr(remindTime.getDate());
		Times=Times+" "+Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes());
	}else{
		var Times = Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes());
	}
	return Times;
};

//add by wuchao 2017/7/11 获取今天或明天什么时候时间
function TimeInfo(hr){
	var nw = new Date();
	//小时,分钟，秒，毫秒
	nw.setHours(hr, 00,00, 0);
	console.log(TimeIt(nw.getTime()));
	return nw.getTime();

}
