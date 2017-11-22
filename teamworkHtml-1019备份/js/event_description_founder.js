$(function(){
	show_time();
	$(".complete").click(function(){
		//判断项目是否有行动未完成		
		var eId=localStorage.getItem("team_eId");
		var data={"eId":eId};
		 $.ajax({
			   type : "get",
		       url:dataLink+"homepage/getUndoneItem", 
		       data:data, 
		       dataType : "json",
		       success : function(json){
		       	if(json.msg=="success"){
		       		if(json.itemSize==0){
						$(".event_summary").show();
					    $(".event_summary1").show();
					    $(".determine").click(function(){
				        	var summary = $(".summary").val();
				        	//add by xiehuilin 校验输入参数
				        	//if(param.fEvent(summary,"prompt")){
				        		if(!isnull(summary)){
				        			$(".signin_issue").show();
				        			todoCloseEvent();
				        		}else{
				        			$(".tishi2").show();
				        			setTimeout( function(){$(".tishi2").hide()}, 3000);
				        		}
				        	//}
					    });
					}else{
						$(".event_summary2").show();
					    $(".event_summary3").show();
					    $(".determine2").click(function(){
					    	$(".event_summary2").hide();
					        $(".event_summary3").hide();
					    	$(".event_summary").show();
					        $(".event_summary1").show();
					        $(".determine").click(function(){
					        	
					        	var summary = $(".summary").val();
					        	//add by xiehuilin 校验输入参数
					        //	if(param.fEvent(summary,"prompt")){
					        		if(!isnull(summary)){
					        			$(".signin_issue").show();
				        			    todoCloseEvent();
				        		     }else{
				        		     	$(".tishi2").show();
				        		     	setTimeout( function(){$(".tishi2").hide()}, 3000);
				        		     }
					        	//}
					    });
					    });
					}
		       	}
		       }
		});
	})
	 //add by lixiaobin 2017/08/01 点击阴影区影藏弹窗
	$(".event_summary").click(function(){
		$(".event_summary").css("display","none");
		$(".event_summary1").css("display","none");
	})
	$(".cancel").click(function(){
		$(".event_summary").css("display","none");
		$(".event_summary1").css("display","none");
	})
	
	 //add by lixiaobin 2017/08/08 点击阴影区影藏弹窗
	$(".event_summary2").click(function(){
		$(".event_summary2").css("display","none");
		$(".event_summary3").css("display","none");
	})
	$(".cancel2").click(function(){
		$(".event_summary2").css("display","none");
		$(".event_summary3").css("display","none");
	})
	
 //add by xiehuilin 2017/06/21 跳转目标页
	$(".edit").click(function(){
	 	_g("servereleasetwo.html");
	});
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
 	 $(".event_xmbg").click(function(){
		_g("event_report.html");
	});
})

//关闭事件
function todoCloseEvent(){
	var finishTime = $("#appDateTime").val();
	var summary = $(".summary").val();
	var eId=localStorage.getItem("team_eId");
	var userId = localStorage.getItem("team_userId");
	var state = $(".getState").attr("id");
	var data = {"eId":eId,"summary":summary,"userId":userId,"tState":state};
	 $.ajax({
		   type : "post",
	       url:dataLink+"homepage/closeEvent", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	window.location.href="index.html";
	    	}
	    }
	});
	
}

 $(function (){ 
 	var roleType=localStorage.getItem("team_roleType");
 	var eId=localStorage.getItem("team_eId");
 	 var userId = localStorage.getItem("team_userId");
 	var param={"userId":userId,"eId":eId};
 	 $.ajax({
		   type : "get",
	       url:dataLink+"event/getEventDes", 
	       data:param, 
	       dataType : "json",
	       success : function(json){
	       	console.info(json);
	       	var info=json.envetInfo;
	       	var user=json.user;
	    	   if(json.msg="success"){
	    	   	var maxCount=info.maxCount==null?0:info.maxCount;
	    	   		$(".event_name").html(info.name);
	    	   		$(".Event_desc_two").find("i").html(info.target);
	    	   		$(".Event_desc_three").find("i").html(sameDate(info.startTime,info.endTime));
	    	   		$(".Event_desc_five").find("i").html(frequency.svcManageTime(info.frequency));
	    	   		$(".Event_desc_four").find("i").html(frequency.pubRange(info.pubRange));
	    	   		var headUrl=isnull(user.headUrl)?"img/img03.png":imageHeadSrc+user.headUrl;
	    	   		if(info.type==0){
	    	   			$(".Event_desc_two").find("span").html("目标描述：");
	    	   		}else{
	    	   			$(".Event_desc_two").find("span").html("需求描述：");
	    	   			var $body = $('body');
							document.title = '需求描述';
							var $iframe = $('<iframe src="/favicon.ico"></iframe>');
							$iframe.on('load',function() {
							  setTimeout(function() {
							      $iframe.off('load').remove();
							  }, 0);
							}).appendTo($body);
	    	   		}
	    	   		var html='<li>'
		    	   		html+=json.isSelected==0?'<span>创建人：</span>':'<span>责任人：</span>';
		    	   		html+='<div class="cjr_box">'
	    	   			html+='<img src="'+headUrl+'" alt="" /><p>'+user.userName+'</p></div></li>';
	    	   		$(".edit_area ul").append(html);
	    	   		if(json.isEnd==1||json.roleType==2||json.roleType==1){
	    	   			$(".complete").hide();
	    	   		}else{
	    	   			$(".complete").show();
	    	   		}
	    	   		json.isEnd==1?$(".event_xmbg").css("display","block"):$(".event_xmbg").css("display","none");
	    	   
	    	   
	    	   }
	    	}
	    	  	
	    });
 }());
function show_time(){
	//获取当前时间前4个小时，一天为86400000毫秒
	var yesterdsay = new Date(new Date().getTime() - 14400000);
	var currYear = (new Date()).getFullYear();	
	var opt={};
	opt.date = {preset : 'date'};
	opt.datetime = {preset : 'datetime'};
	opt.time = {preset : 'time'};
	opt.default = {
		theme: 'ios', //皮肤样式
	    display: 'modal', //显示方式 
	    mode: 'scroller', //日期选择模式
		lang:'zh',
		startYear:currYear - 50, //开始年份,如 currYear - 10
	    endYear:currYear + 50, //结束年份
	    maxDate:new Date(),
	    minDate:new Date(yesterdsay) 
	};
	
	$.mobiscroll.i18n.zh = $.extend($.mobiscroll.i18n.zh, {
	    dateFormat: 'yyyy-mm-dd',
	    dateOrder: 'yymmdd',
	    dayNames: ['周日', '周一;', '周二;', '周三', '周四', '周五', '周六'],
		dayNamesShort: ['日', '一', '二', '三', '四', '五', '六'],
	    dayText: '日',
	    hourText: '时',
	    minuteText: '分',
	    monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月','十二月'],
	    monthNamesShort: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
	    monthText: '月',
	    secText: '秒',
	    timeFormat: 'HH:ii',
	    timeWheels: 'HHii',
	    yearText: '年',
	    setText: '确定',
  		cancelText: '取消'
		});

		var optDateTime = $.extend(opt['datetime'], opt['default']);
		$("#appDateTime").mobiscroll(optDateTime).datetime(optDateTime);
}
/*function checkLen(obj)
	{
		var maxChars = 100;//最多字符数
		if (obj.value.length > maxChars)
		obj.value = obj.value.substring(0,maxChars);
		var curr = maxChars - obj.value.length;
		document.getElementById("count").innerHTML = curr.toString();
}*/
