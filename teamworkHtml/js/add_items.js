
var sTime=localStorage.getItem("team_sTime");
var eTime=localStorage.getItem("team_eTime");
var team_type=localStorage.getItem("team_type");
var founder=0;//0否 1是
if (isnull(team_type)) {
//	window.location.href="index.html";
}
var week="";//重复周期
var cycleState=0;// 0 单次  1周期
var cycle_freq=null;//周期频率:0 天、1 周
//选择好友
/* $(".Additems_global").on("click","li",function(){
 	$(".Additems_global em").removeClass("Additems_global_current");
	$(this).find("em").addClass("Additems_global_current");
});*/
 $(".Select_global").on("click","li",function(){
 	$(".Select_global em").removeClass("Select_global_current");
	$(this).find("em").toggleClass("Select_global_current");	
});
$(function(){
	$(".Additems_alert_people ").css("min-height",$("body").height());
	$(".Additems_alert_friend ").css("min-height",$("body").height());
	
//	点击公开私有是否选中
$(".Serve_bottom_open span").click(function(){
	$(this).toggleClass("Serve_bottom_open_current");
});
//点击轻企成员
$(".Additems_cloicetwo").click(function(){
	ligth_all();
	
});
//点击好友
$(".Additems_cloice").click(function(){
	getFriendGroup();
	
});
//点击周期重复
$(".Serve_con_btn").click(function(){
	$(".summary_time").show();
	$(".summary_time4").show();
});
//点击行动负责人弹出弹窗
$(".Serve_bottom_btn").click(function(){
	var leId = localStorage.getItem("team_leId");
	if (isnull(leId) && founder==1) {
		$(".Additems_cloice").show();
	}else if (!isnull(leId) && founder==1) {
		$(".Additems_cloicetwo").show();
	}
	list_partake();
});
//点击取消
$(".Additems_btn_cancel").click(function(){
	$(".Additems_alert_people").hide();
});
//点击周期重复弹窗选项
$(".summary_time4 li").click(function(){
	$(".summary_time").hide();
	$(".summary_time4").hide();
	$(".Serve_con_btn i").html($(this).find("p").html());
	if($(".Serve_con_btn i").html() == $(".no_day .no_repeat").html()){
		$(".Serve_con_time").hide();
		$(".Serve_con_timetwo").show();
	}else{
		
		$(".Serve_con_timetwo").hide();
		$(".Serve_con_time").show();
	}
});
//点击提醒时间
$(".Serve_footer li").click(function(){
		$(this).addClass("Serve_current").siblings().removeClass("Serve_current");
	});
//	edit by zhengjunting 2017/11/1 选择自定义周期
$(".summary_time4 .custom4").click(function(){
	$(".summary_time").show();
	$(".summary_time5").css("display","block");
	$(".summary_time4").css("display","none");
});
$(".summary_time").click(function(){
	$(".summary_time").css("display","none");
	$(".summary_time1").css("display","none");
	$(".summary_time2").css("display","none");
	$(".summary_time3").css("display","none");
	$(".summary_time4").css("display","none");
	$(".summary_time5").css("display","none");
	$(".summary_time6").css("display","none");
	$(".summary_time7").css("display","none");
	$('.form_datetime').datetimepicker('remove');
	$('.form_datetimetwo').datetimepicker('remove');
})
	$(".Add_tab_two").click(function(){
		localStorage.removeItem("team_fgId");
		window.location.href="add_items_two.html";
	});
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
//	$(".control-group").on("click",".time_ts",function(event){
//		event.stopPropagation();
//		$(".summary_time").css("display","block");
//		$(".summary_time1").css("display","block");
//		$('.appDateTimetwo').datetimepicker('remove');
//		if($(this).attr("flage") == "false"){
//			$(".summary_time").css("display","block");
//			$(".summary_time1").css("display","block");
//		}else{
//			if($(".time_ts_one").val() != ""){
//				$(".summary_time").css("display","block");
//				$(".summary_time3").css("display","none");
//				$(".summary_time1").css("display","block");
//				$(".bgtime3").html($(".time_ts_one").val());
//			}else{
//				$(".summary_time").css("display","block");
//				$(".summary_time1").css("display","block");
//			}
//		}
//	});
	
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
	$(".shuzi").bind("input propertychange",function(){
		if($(this).val() > 999){
			$(this).val($(this).val().substr(0,3));
		}
	})
	$(".time_ts").focus(function(){
        document.activeElement.blur();
    });
    $(".time_ts_one").focus(function(){
        document.activeElement.blur();
    });
    $(".time_ts_two").focus(function(){
        document.activeElement.blur();
    });
    $(".time_ts_three").focus(function(){
        document.activeElement.blur();
    });
    $(".time_ts_four").focus(function(){
        document.activeElement.blur();
    });
   
	
//	$(".custom").click(function(event){
//		event.stopPropagation();
//		$(".summary_time").css("display","none");
//		$(".summary_time1").css("display","none");
//		$(".summary_time3").css("display","none");
//		$('.form_datetimetwo').datetimepicker('show');
//	})
//	$(".custom2").click(function(event){
//		event.stopPropagation();
//		$(".summary_time").css("display","none");
//		$(".summary_time2").css("display","none");
//		$('.form_datetime').datetimepicker('show');
//	});
	
//	$(".form_datetime").click(function(){
//	//		$(this).focus();
//			var d = new Date();
//			var hour = d.getHours(); //获取系统时
//			var min = d.getMinutes(); //分
//			hour<10?m = "0"+hour:hour;
//			min<10?min = "0"+min:min;
//			$(".now_time").html(hour+":"+min);
//			$(".summary_time").css("display","block");
//			$(".summary_time2").css("display","block");
//			$('.form_datetime').datetimepicker('hide');
//			$('.form_datetime').datetimepicker().on('changeDate', function(ev){
//					//alert(1);
//					//return false;
//					$(".form_datetime").datetimepicker("hide");
//					$(".form_datetimetwo").datetimepicker("remove");
//		 	});
//		});
		//	edit by lixiaobin 2017/08/11 选择单次截至时间
//		$(".appDateTimetwo").click(function(){
//	//		$(this).focus();
//			var d = new Date();
//			var hour = d.getHours(); //获取系统时
//			var min = d.getMinutes(); //分
//			hour<10?m = "0"+hour:hour;
//			min<10?min = "0"+min:min;
//			$(".now_time").html(hour+":"+min);
//			$(".summary_time").css("display","block");
//			$(".summary_time1").css("display","block");
//			//$('.form_datetimetwo').datetimepicker('hide');
//			$('.form_datetimetwo').datetimepicker().on('changeDate', function(ev){
//				//alert(1);
//				//return false;
//				$(".form_datetimetwo").datetimepicker("hide");
//				$(".form_datetime").datetimepicker("remove");
//			});
//		});

	
//	周期自定义
	$(".Add_nine").on("click",function(event){
		event.stopPropagation();
		$(".summary_time").css("display","none");
		$(".summary_time6").css("display","none");
		datetimeone();
		$(".time_ts_three").click();
		$(".summary_time").css("display","none");
		$(".summary_time6").css("display","none");
	})
	//	周期自定义
	$(".end_nine").on("click",function(event){
		event.stopPropagation();
		$(".summary_time").css("display","none");
		$(".summary_time7").css("display","none");
		datetimetwo();
		$(".time_ts_four").click();
		$(".summary_time").css("display","none");
		$(".summary_time7").css("display","none");
	})
	function datatime(){
		var calendardatetime = new lCalendar();
		calendardatetime.init({
		    'trigger': '.appDateTimetwo',
		    'type': 'datetime'
		});
	}
	function datatime3(){
		var calendardatetime = new lCalendar();
		calendardatetime.init({
		    'trigger': '.appDateTime',
		    'type': 'datetime'
		});
	}
//		edit by lixiaobin 2017/06/19 周期开始时间
	function datetimeone(){
		var calendardatetime1 = new lCalendar();
		calendardatetime1.init({
		    'trigger': '.time_ts_three',
		    'type': 'time'
		});	
	}
	//	edit by lixiaobin 2017/06/19 周期截止时间
	function datetimetwo(){
	var calendardatetime2 = new lCalendar();
	calendardatetime2.init({
	    'trigger': '.time_ts_four',
	    'type': 'time'
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
			$(this).parent().css("width","60%");
			$(".shuzi").css("display","block");
			$(".btn_qd").css("display","block");
		}else{
			$(".summary_time1").css("display","none");
			$('.form_datetimetwo').datetimepicker('remove');
		}
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
	$(".time_ts_three").click(function(){
		$(".summary_time").css("display","block");
		$(".summary_time6").css("display","block");
	});
	$(".time_ts_four").click(function(){
		$(".summary_time").css("display","block");
		$(".summary_time7").css("display","block");
	});
})

	var team_fgName="";
	var team_fgLogo="";
	var team_note="";
//用户资料
getUserInfo();
function getUserInfo() {
	var userId = localStorage.getItem("team_userId");
	var data = {"userId":userId}
	$.ajax({
		type : "get",
		url : dataLink + "userwx/getUserInfoById",
		data : data,
		dataType : "json",
		success : function(json) {
			if (json.msg = "success") {
				var userInfo = json.user;
				team_fgLogo=isnull(userInfo.headUrl)?"img/img03.png":imageHeadSrc+userInfo.headUrl;
				team_fgName=isnull(userInfo.userName)?"我":userInfo.userName;
				team_note=isnull(userInfo.note)?"":userInfo.note;
				$(".Serve_bottom_btn").html('<span>行动负责人</span><i>'+team_fgName+'</i><em style="display:none;">'+userId+'</em>');
			}
		}
	});
}
//校验是否是创建人
check_founder();
function check_founder() {
	var userId = localStorage.getItem("team_userId");
	var eId=localStorage.getItem("team_eId");
	var data = {"userId":userId,"eId":eId}
	$.ajax({
		type : "get",
		url : dataLink + "join/checkFounder",
		data : data,
		dataType : "json",
		success : function(json) {
			if (json.msg = "success") {
				var item = json.item;
				if (!isnull(item)) {
					founder=1;
				}
			}
		}
	});
}



// 好友分组
function getGroup(){
	var userId=localStorage.getItem("team_userId");
	var data = {"userId":userId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"friend/getGroupByUserId", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    		   $(".Select_ul").html("");
	    		   $(".Additems_alert_null").hide();
	    		   if(!isnull(json.group)&&json.group.length>0){
	    			   var list=json.group;
	    			   if(list.length>0){
	    				   var html =' <li class="Select_global Select_globaltwo"><ul>';
						 	html +='<li><em><i style="display:none;">'+userId+'</i>';
	    	   	      		html +='<i style="display:none;">'+team_fgName+'</i>';
	    	   	      		html +='<i style="display:none;">'+team_fgLogo+'</i>';
	    	   	      		html +='</em><img src="'+team_fgLogo+'" alt="" />';
						 	html +='<div class="Select_list"><span>'+team_fgName+'</span>';
						 	html +=' <i>'+team_note+'</i></div></li></ul></li>';
						 	html +='<li class=" Select_global" id=""><p>最近联系</p><ul class="globalTen"></ul></li>';
	    				    html +='<li class=" Select_global" id=""><p>全部好友</p><ul class="globalAll"></ul></li>';
	    				   for (var i = 0; i < list.length; i++) {
	    					   var item=list[i];
	    					   html +='<li class=" Select_global" id="'+item.gId+'"><p>'+item.name+'</p><ul class="global'+i+'"></ul></li>';
	    				   }
	    				   $(".Select_ul").append(html);
	    			   }
	    			   $(".Select_global p").click(function(){
	    				   $(this).toggleClass(".Select_current");
	    				   if($(this).hasClass("Select_current")){
	    					   $(this).next().hide();
	    					   $(this).removeClass("Select_current").css({"border-bottom":"1px solid #e5e5e5"});
	    					   $(this).parent().css({"height":"3.167rem"});
	    				   }else{
	    					   $(this).next().show();
	    					   $(this).addClass("Select_current").css({"border":"0 none"});
	    					   $(this).parent().css({"height":"auto"});
	    				   }
	    				   var clana = "."+$(this).parents(".Select_global").find("ul").attr("class");
	    				   var id = $(this).parents(".Select_global").attr("id");
	    				   if(clana==".globalTen"){
	                			getLatelyFriendList();
	                		}else{
	                			getFriendGroup(clana,id);
	                		}
	    			   });
	    		   }else{
					var html =' <li class="Select_global Select_globaltwo"><ul>';
					 	html +='<li><em><i style="display:none;">'+userId+'</i>';
    	   	      		html +='<i style="display:none;">'+team_fgName+'</i>';
    	   	      		html +='<i style="display:none;">'+team_fgLogo+'</i>';
    	   	      		html +='</em><img src="'+team_fgLogo+'" alt="" />';
					 	html +='<div class="Select_list"><span>'+team_fgName+'</span>';
					 	html +=' <i>'+team_note+'</i></div></li></ul></li>';
					 	html += '<li class="Select_global" id=""><p>最近联系</p><ul class="globalTen"></ul></li>';
						html +='<li class=" Select_global" id=""><p>全部好友</p><ul class="globalAll"></ul></li>';
	    			   $(".Select_ul").append(html);
	    			   $(".Select_global p").click(function(){
	    				   $(this).toggleClass(".Select_current");
	    				   if($(this).hasClass("Select_current")){
	    					   $(this).next().hide();
	    					   $(this).removeClass("Select_current").css({"border-bottom":"1px solid #e5e5e5"});
	    					   $(this).parent().css({"height":"3.167rem"});
	    				   }else{
	    					   $(this).next().show();
	    					   $(this).addClass("Select_current").css({"border":"0 none"});
	    					   $(this).parent().css({"height":"auto"});
	    				   }
	    				   var id = $(this).parents(".Select_global").attr("id");
	    				   var clana = "."+$(this).parents(".Select_global").find("ul").attr("class");
	    				   if(clana==".globalTen"){
	                			getLatelyFriendList();
	                		}else{
	                			getFriendGroup(clana,id);
	                		}
	    			   });
	    		   }
	    		   
	    		 //选择好友
		    	   	 $(".Select_global li").click(function(){
		    	 		$(".Select_ul em").removeClass("Select_global_current");
		    	 		$(this).find("em").addClass("Select_global_current");
		    	 	});
	             }
	    	  }
	    });
}


//分组下好友列表
function getFriendGroup(clana,gId){
	var userId=localStorage.getItem("team_userId");
	var data = {"userId":userId,"state":1,"gId":gId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"friend/getFriendGroupById", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	$(clana).html("");
	    	   	  if(json.groupFriend.length>0){
	    	   	  	for(i=0;i<json.groupFriend.length;i++){
	    	   	      	var item = json.groupFriend[i];
	    	   	      	var headUrl = isnull(item.headUrl)? "img/img03.png" : (imageHeadSrc+item.headUrl);
	    	   	      	var note = isnull(item.note)?"":item.note;
	    	   	      	var html = '<li><em><i style="display:none;">'+item.fId+'</i>';
	    	   	      		html +='<i style="display:none;">'+item.userName+'</i>';
	    	   	      		html +='<i style="display:none;">'+headUrl+'</i>';
	    	   	      		html +='</em><img src="'+headUrl+'" alt="" /><div class="Select_list">';
	    	   	      		html += '<span>'+item.userName+'</span><i>'+note+'</i></div></li>';
	    	   	      	$(clana).append(html);
	    	   	  	}
	    	   	  	//选择好友
		    	   	 $(".Select_global li").click(function(){
		    	 		$(".Select_ul em").removeClass("Select_global_current");
		    	 		$(this).find("em").addClass("Select_global_current");
		    	 	});
	    	   	  }
	    	   	
	    	   }
	    	  }
	    });
	
	
}


function getLatelyFriendList(){
	var userId=localStorage.getItem("team_userId");
	var data = {"userId":userId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"friend/getLatelyFriendList", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	$(".globalTen").html("");
	    	   	  if(!isnull(json.friendsList)&&json.friendsList.length>0){
	    	   	  	for(i=0;i<json.friendsList.length;i++){
	    	   	      	var item = json.friendsList[i];
	    	   	      	var headUrl = isnull(item.headUrl)? "img/img03.png" : (imageHeadSrc+item.headUrl);
	    	   	      	var note = isnull(item.note)?"":item.note;
	    	   	      	
	    	   	     var html = '<li><em><i style="display:none;">'+item.fId+'</i>';
	 	   	      		html +='<i style="display:none;">'+item.userName+'</i>';
	 	   	      		html +='<i style="display:none;">'+headUrl+'</i>';
	 	   	      		html +='</em><img src="'+headUrl+'" alt="" /><div class="Select_list">';
	 	   	      		html += '<span>'+item.userName+'</span><i>'+note+'</i></div></li>';
	 	   	      	$(".globalTen").append(html);
	    	   	  	}
	    	   	//选择好友
		    	   	 $(".Select_global li").click(function(){
		    	 		$(".Select_ul em").removeClass("Select_global_current");
		    	 		$(this).find("em").addClass("Select_global_current");
		    	 	});
	    	   	  }
	    	   	
	    	   }
	    	  }
	    });
	
	
}

//取消
$(".Select_btn_cancel").click(function(){
	 
	 $(".Additems_alert_people").hide();
	 $(".Additems_alert_friend").hide();
	 $(".Additems_alert_friendtwo").hide();
});


//取消添加事项
$("#btn1").click(function(){
	localStorage.removeItem("team_fgId");
	var type=localStorage.getItem("team_type");
	if (type==0) {
			localStorage.removeItem("team_type");
			window.location.href="eventdetailsone.html";
	}else if(type==1){
			localStorage.removeItem("team_type");
			window.location.href="svc_red_eventdetails.html";
	}
});

$("#btnone").click(function(){
	localStorage.removeItem("team_fgId");
	var type=localStorage.getItem("team_type");
	localStorage.removeItem("team_fgId");
	if (type==0) {
			localStorage.removeItem("team_type");
			window.location.href="eventdetailsone.html";
	}else if(type==1){
			localStorage.removeItem("team_type");
			window.location.href="svc_red_eventdetails.html";
	}
});
//返回首页
$(".return_index").click(function(){
	window.location.href="index.html";
});

//确认添加事项
$(".Serve_release").click(function(){
	var period=$(".Serve_con_btn i").find("i").html();
	if (period==1) {
		//周期
		check_revolution();
	}else{
		//单次
		check_info();
	}
});

//校验添加信息
function check_info(){
	var userId=localStorage.getItem("team_userId");
	via=true;
	var  depict=$(".time_affair").val();
	depict=_trim(depict);
	var finishTime=$("#testD").val();// 完成时间
	var strStartTime=$("#testsD").val();// 开始时间
	var fgId=localStorage.getItem("team_fgId");
	if(isnull(finishTime) || finishTime<sTime){
		$(".time_tishi").html("完成时间应大于项目开始时间");
		$(".time_tishi").css("display","block");
		setTimeout(function(){
			$(".time_tishi").css("display","none");
		},2000)
		via=false;
		return false;
	}
	if(isnull(finishTime) || finishTime<TimeIt(new Date().getTime(),1)){
		$(".time_tishi").html("完成时间应大于当前时间");
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
	var  depict=$(".time_affair").val();
	depict=_trim(depict);
	var eId=localStorage.getItem("team_eId");
	var finishTime=$("#testD").val();//完成时间
	var strStartTime=$("#testsD").val();//开始时间
	var userId=localStorage.getItem("team_userId");
	var frequency=$(".Serve_current").find("i").html();
	var fgId=$(".Serve_bottom_btn").find("em").html();
	var isRange=0;
	if ($(".Serve_bottom_open").find("span").hasClass("Serve_bottom_open_current")) {
		isRange=1;
	}
	$(".event_summary").show();
	$(".event_summary1").show();
	var Json='"eId":"'
		+eId
		+'","strFinishTime":"'
		+finishTime
		+'","strStartTime":"'
		+strStartTime
		+'","eiDesc":"'
		+depict
		+'","isRange":"'
		+isRange
		+'","frequency":"'
		+frequency
		+'","createBy":"'
		+userId
		+'","dutyId":"'
		+fgId
		+'","isCycle":"'
		+0
		+'","state":"'
		+0
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	var data='{'+start+'}';
	$.ajax({
		  url:dataLink+"item/saveItem",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     contentType: "application/json; charset=utf-8",
	     success:function(result){
	   	  if(result.msg=="success"){
	   		localStorage.removeItem("team_fgId");
	   		window.location.href="taskDetails.html";
	   	  }
	     },  
	     timeout:3000  
	});
	
}
//校验周期
function check_revolution(){
	via=true;
	var str = $("#tests").val();
	var arr = [];
	arr = str.split(":");
	var hour = arr[0]*60*60*1000;
	var min = arr[1]*60*1000;
	var sed = arr[2]*1000;
	var Millised = hour + min;
	console.log(Millised);
	var str2 = $("#test").val();
	var arr2 = [];
	arr2 = str2.split(":");
	var hour2 = arr2[0]*60*60*1000;
	var min2 = arr2[1]*60*1000;
	var sed2 = arr2[2]*1000;
	var Millised2 = hour2 + min2;
	console.log(Millised2);
	if (cycle_freq==null) {
		$(".time_tishi").html("请选择周期重复");
		$(".time_tishi").css("display","block");
		setTimeout(function(){
			$(".time_tishi").css("display","none");
		},2000)
		via=false;
		return false;
	}
	
	if(isnull(str) || isnull(str2) || Millised>=Millised2){
		$(".time_tishi").html("完成时间应大于开始时间");
		$(".time_tishi").css("display","block");
		setTimeout(function(){
			$(".time_tishi").css("display","none");
		},2000)
		via=false;
		return false;
	}
	var  depict=$(".time_affair").val();
	depict=_trim(depict);
	if (!centen(depict,2,200)) {
		$(".time_tishi").html("描述内容1~100汉字");
		$(".time_tishi").show();
		setTimeout(function(){$(".time_tishi").hide();},2000);
		via=false;
		return false;
	}
	if (via) {
		save_cycle(Millised,Millised2);
	}

	
}


//保存周期
function save_cycle(Millised,Millised2){

	var  depict=$(".time_affair").val();
	depict=_trim(depict);
	var eId=localStorage.getItem("team_eId");
	var userId=localStorage.getItem("team_userId");
	var frequency=$(".Serve_current").find("i").html();
	var fgId=$(".Serve_bottom_btn").find("em").html();
	var isRange=0;
	if ($(".Serve_bottom_open").find("span").hasClass("Serve_bottom_open_current")) {
		isRange=1;
	}
	$(".event_summary").show();
	$(".event_summary1").show();
	var Json='"eId":"'
		+eId
		+'","circleStartTime":"'
		+Millised
		+'","circleFinishTime":"'
		+Millised2
		+'","eiDesc":"'
		+depict
		+'","frequency":"'
		+frequency
		+'","createBy":"'
		+userId
		+'","dutyId":"'
		+fgId
		+'","isRange":"'
		+isRange
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
		  url:dataLink+"item/saveCycItem",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     contentType: "application/json; charset=utf-8",
	     success:function(result){
	   	  if(result.msg=="success"){
	   		  if (result.succeed==0) {
	   			setTimeout(function(){$(".event_summary").hide();
				$(".event_summary1").hide();$(".tishi").show();},1000);
				setTimeout(function(){$(".tishi").hide();},2000);
			}else{
				window.location.href="taskDetails.html";
			}
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
	$('.form_datetimetwo').datetimepicker('remove');
});
//明天上午十点
$(".now_arvo").click(function(){
	$(".time_ts").val(TimeIt(TimeInfo(34),1));
	$(".summary_time").css("display","none");
	$(".summary_time1").css("display","none");
	$('.form_datetimetwo').datetimepicker('remove');
});

//明天下午六点
$(".morn_noon").click(function(){
	$(".time_ts").val(TimeIt(TimeInfo(42),1));
	$(".summary_time").css("display","none");
	$(".summary_time1").css("display","none");
	$('.form_datetimetwo').datetimepicker('remove');
});
//本周五下班
$(".morn_arvo").click(function(){
	$(".time_ts").val(TimeIt(next_week(-30),1));
	$(".summary_time").css("display","none");
	$(".summary_time1").css("display","none");
	$('.form_datetimetwo').datetimepicker('remove');
});
//下周一
$(".below_Monday").click(function(){
	$(".time_ts").val(TimeIt(next_week(34),1));
	$(".summary_time").css("display","none");
	$(".summary_time1").css("display","none");
	$('.form_datetimetwo').datetimepicker('remove');
});
$("body").click(function(){
	$(".gearDatetime").remove();
	$(".gearDate").remove();
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
	$('.form_datetime').datetimepicker('remove');
})
//今天中午 十二点
$(".now_arvo2").click(function(){
	$(".time_ts_one").val(TimeIt(TimeInfo(12),1));
	console.log(TimeIt(TimeInfo(12),1))
	$(".summary_time").css("display","none");
	$(".summary_time2").css("display","none");
	$('.form_datetime').datetimepicker('remove');
});
//明天早上八点
$(".morn_noon2").click(function(){
	$(".time_ts_one").val(TimeIt(TimeInfo(32),1));
	$(".summary_time").css("display","none");
	$(".summary_time2").css("display","none");
	$('.form_datetime').datetimepicker('remove');
});
//后天早上八点
$(".morn_arvo2").click(function(){
	$(".time_ts_one").val(TimeIt(TimeInfo(56),1));
	$(".summary_time").css("display","none");
	$(".summary_time2").css("display","none");
	$('.form_datetime').datetimepicker('remove');
});
//下周一早上八点
$(".below_Monday2").click(function(){
	$(".time_ts_one").val(TimeIt(next_week(32),1));
	$(".summary_time").css("display","none");
	$(".summary_time2").css("display","none");
	$('.form_datetime').datetimepicker('remove');
});


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

//周六日
$(".weekend_day").click(function(){
	cycle_freq=1;
	 week="SA,SU";
	//var name= $(".working_day").find("p").html();
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
	 //$(".ul1 li").removeClass("active2");
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
		var Times = Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes())+":"+Substr(remindTime.getSeconds());;
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
function showtime(){
   var myDate = new Date();
   var S = myDate.getHours();       //获取当前小时数(0-23)
	var M =myDate.getMinutes();     //获取当前分钟数(0-59)
	M = M.length>1?M : 0+M;
   var str = S+":"+M;
   return str;
}
function showtimetwo(){
    var newTime = new Date();
	var year = newTime.getFullYear(); 
	var mour = newTime.getMonth()+1;  
	var day = newTime.getDate(); 
	var hour = newTime.getHours();
	var min = newTime.getMinutes(); 
	mour<10?mour = "0"+mour:mour;
	min<10?min = "0"+min:min;
	hour<10?hour = "0"+hour:hour;
   var str = year+"-"+mour+"-"+day+"  "+hour+":"+min;
   return str;
  }
//时间插件
$(function () {
	showTimeone();
	showTimetwo();
	$("#testsD").attr("placeholder",showtimetwo());
	$("#testsD").val(showtimetwo());
	$("#tests").attr("placeholder",showtime());
	$("#tests").val(showtime());
	
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
//		showNow: true,
//		nowText: "今天",
		stepMinute: 10
//      startYear: curr - 10, //开始年份
//      endYear: curr + 50 //结束年份
//      mintime:$(".tests").val()
	};
   $('.settings').bind('change', function() {
        var demo = 'datetime';
        if (!demo.match(/select/i)) {
            $('.demo-test-' + demo).val('');
        }
        $("#tests").scroller('destroy').scroller($.extend(opt['time'], opt['default']));
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
//		showNow: true
//		nowText: "今天",
		stepMinute: 10
//      startYear: curr - 10, //开始年份
//      endYear: curr + 50, //结束年份
//      minDate:current
	};
   $('.settings').bind('change', function() {
        var demo = 'datetime';
        if (!demo.match(/select/i)) {
            $('.demo-test-' + demo).val('');
        }
        $("#test").scroller('destroy').scroller($.extend(opt['time'], opt['default']));
        $('.demo').hide();
        $('.demo-' + demo).show();
    });
    $('#demo').trigger('change');
}

//edit by zhanghaitao 2017/11/02  单次时间插件
var opt={};
$(function () {
    var curr = new Date().getFullYear();
    
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
        endYear: curr + 50 //结束年份
	};

});
$(".form_datetime").click(timerOne);
function timerOne(){
	$(this).focus();
	var d = new Date();
	var hour = d.getHours(); //获取系统时
	var min = d.getMinutes(); //分
	hour<10?m = "0"+hour:hour;
	min<10?min = "0"+min:min;
	$(".now_time").html(hour+":"+min);
	$(".summary_time").css("display","block");
	$(".summary_time2").css("display","block");
};
//	edit by lixiaobin 2017/08/11 选择单次截至时间
$(".form_datetimetwo").click(timerTwo);
function timerTwo(){
	$(this).focus();
	var d = new Date();
	var hour = d.getHours(); //获取系统时
	var min = d.getMinutes(); //分
	hour<10?m = "0"+hour:hour;
	min<10?min = "0"+min:min;
	$(".now_time").html(hour+":"+min);
	$(".summary_time").css("display","block");
	$(".summary_time1").css("display","block");
};
$(function(){
	$(".custom").click(function(event){
		event.stopPropagation();
		$(".summary_time").css("display","none");
		$(".summary_time1").css("display","none");
		$('.settings').unbind().bind('change', function() {
			var demo = 'datetime';
			if (!demo.match(/select/i)) {
			    $("#testD").val('');
			}
			$("#testD").scroller('destroy').scroller($.extend(opt['datetime'], opt['default']));
			$('.demo').hide();
			$('.demo-' + demo).show();
		});
		$('#demo').trigger('change');
		$("#testD").unbind('click',timerTwo).trigger('click')
		$("#testD").unbind().click(timerTwo);
	
	});
	$(".custom2").click(function(event){
		event.stopPropagation();
		$(".summary_time").css("display","none");
		$(".summary_time2").css("display","none");
		$('.settings').unbind().bind('change', function() {
			var demo = 'datetime';
			if (!demo.match(/select/i)) {
			    $("#testsD").val('');
			}
			$("#testsD").scroller('destroy').scroller($.extend(opt['datetime'], opt['default']));
			$('.demo').hide();
			$('.demo-' + demo).show();
		});
		$('#demo').trigger('change');
		$("#testsD").unbind('click',timerOne).trigger('click')
		$("#testsD").unbind().click(timerOne);
	});
	
});







//项目参与人
function list_partake() {
	var eId = localStorage.getItem("team_eId");
	var createBy=localStorage.getItem("team_userId");
	var data = {"eId":eId}
	$.ajax({
		type : "get",
		url : dataLink+"join/listEventPartake",
		data : data,
		dataType : "json",
		success : function(json) {
			if (json.msg = "success") {
				$(".take_part_in").html("");
				var list = json.list;
				if (list.length>0) {
					for (var i = 0; i < list.length; i++) {
						var item=list[i];
						var	headUrl=isnull(item.headUrl)?"img/img03.png":imageHeadSrc+item.headUrl;
						var html ='<li><em><i style="display:none;">'+item.userId+'</i>';
							html +='<i style="display:none;">'+item.userName+'</i>';
							html +='</em><img src="'+headUrl+'" alt="" />';
							html +='<div class="Additems_list">'+item.userName+'</div></li>';
						$(".take_part_in").append(html);
					}
				}
				$(".Additems_alert_people").show();
				
				//选择参与人
				 $(".Additems_global").on("click","li",function(){
		    	   	 	$(".Additems_global em").removeClass("Additems_global_current");
		    	   		$(this).find("em").addClass("Additems_global_current");
		    	 });
			}
		}
	});
}   


//确认参与人
$(".Additems_btn_confirm").click(function(){
	var userId=localStorage.getItem("team_userId");
	var fgId=$(".Additems_global_current").find("i").eq(0).html();
	var fg_Name=$(".Additems_global_current").find("i").eq(1).html();
	if (!isnull(fgId)) {
		localStorage.setItem("team_fgId",fgId);
		$(".Serve_bottom_btn").html('<span>行动负责人</span><em style="display:none;">'+fgId+'</em><i>'+fg_Name+'</i>');
	}
	$(".Additems_alert_people").hide();
});


//分组下好友列表
function getFriendGroup(){
	var userId=localStorage.getItem("team_userId");
	var data = {"userId":userId,"state":1}
	 $.ajax({
		   type : "get",
	       url:dataLink+"friend/getFriendGroupById", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	$(".hail_fellow").html("");
	    	   	  if(json.groupFriend.length>0){
	    	   	  	for(i=0;i<json.groupFriend.length;i++){
	    	   	      	var item = json.groupFriend[i];
	    	   	      	var headUrl = isnull(item.headUrl)? "img/img03.png" : (imageHeadSrc+item.headUrl);
	    	   	      	var html = '<li><em><i style="display:none;">'+item.userId+'</i>';
	    	   	      		html +='<i style="display:none;">'+item.userName+'</i>';
	    	   	      		html +='</em><img src="'+headUrl+'" alt="" /><div class="Select_list">';
	    	   	      		html += '<span>'+item.userName+'</span></div></li>';
	    	   	      	$(".hail_fellow").append(html);
	    	   	  	}
	    	   	 $(".Additems_alert_friend").show();
	    	   	  }
	    	   }
	    	  }
	    });
	
	
}


//确认好友
$(".my_Friends").click(function(){
	var userId=localStorage.getItem("team_userId");
	var fgId=$(".Select_global_current").find("i").eq(0).html();
	var fg_Name=$(".Select_global_current").find("i").eq(1).html();
	if (!isnull(fgId)) {
		localStorage.setItem("team_fgId",fgId);
		$(".Serve_bottom_btn").html('<span>行动负责人</span><em style="display:none;">'+fgId+'</em><i>'+fg_Name+'</i>');
	}
	 $(".Additems_alert_people").hide();
	 $(".Additems_alert_friend").hide();
	 $(".Additems_alert_friendtwo").hide();
});


//轻企成员
function ligth_all(){
 	var userId = localStorage.getItem("team_userId");
 	var leId=localStorage.getItem("team_leId");
 	var data={"userId":userId,"leId":leId}
 	$.ajax({
 		  type : "GET",
 	      url:dataLink+"direct/listManage",  
 	      dataType:'json',  
 	      data:data, 
 	      contentType: "application/json; charset=utf-8",
 	      success:function(json){
 	    	  if(json.msg=="success"){
 	    		  // 轻企成员
 	    		 var memberList=json.memberList;
 	    		 if (memberList.length>0) {
 	    			 $(".my_company").html("");
 						for (var i = 0; i < memberList.length; i++) {
 							var member=memberList[i];
 							var headUrl= isnull(member.headUrl)?"img/img03.png":imageHeadSrc+member.headUrl;
 	 	    	   	      	if (userId !=member.userId) {
 	 	    	   	      			var html ='<li><em><i style="display:none;">'+member.userId+'</i>';
 	 	    	   	      		html +='<i style="display:none;">'+member.userName+'</i>';
 	 	    	   	      			html +='</em><img src="'+headUrl+'" alt="" />';
 	 	    	   	      			html +='<div class="Select_list"><span>'+member.userName+'</span>';
 	 	    	   	      			html +='</div></li>'; 
							}
 							
 							$(".my_company").append(html);
 						}
 					}
 	    		$(".Additems_alert_friendtwo").show(); 
 	    	  }
 	    		
 	      },  
 	      timeout:3000  
 	 }); 
 }

//确认轻企成员
$(".enterprise").click(function(){
	var userId=localStorage.getItem("team_userId");
	var fgId=$(".Select_global_current").find("i").eq(0).html();
	var fg_Name=$(".Select_global_current").find("i").eq(1).html();
	if (!isnull(fgId)) {
		localStorage.setItem("team_fgId",fgId);
		$(".Serve_bottom_btn").html('<span>行动负责人</span><em style="display:none;">'+fgId+'</em><i>'+fg_Name+'</i>');
	}
	 $(".Additems_alert_people").hide();
	 $(".Additems_alert_friend").hide();
	 $(".Additems_alert_friendtwo").hide();
});
