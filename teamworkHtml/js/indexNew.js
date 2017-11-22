tab(".tabs li","default",".Index_swiper_all_inner section")
var userId = localStorage.getItem("team_userId");

$(function(){
	if(verificationUserLogin()){
		init.data();
		
	}
//	点击弹窗取消
	$(".Index_overtime_btnone").click(function(){
		$(".event_summary").hide();
		$(".Index_overtime").hide();
	});
//	点击背景的时候
	$(".event_summary").click(function(){
		$(".event_summary").hide();
		$(".Index_overtime").hide();
	});
	//时间插件
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
        $("#test").scroller('destroy').scroller($.extend(opt['date'], opt['default']));
        $('.demo').hide();
        $('.demo-' + demo).show();
    });
    $('#demo').trigger('change');
	
	$(".to_Item_html").on("click",function(){
	
	});
	$(".Index_list_con_from").on("click",function(){
	   var eId = $(this).attr("id");
	    localStorage.setItem("team_eId",eId);
	    window.location.href="eventdetailsone.html";
		return false;
	});
	$(".to_Event_html").on("click",function(){
	   var eId = $(this).attr("id");
	    localStorage.setItem("team_eId",eId);
	    window.location.href="eventdetailsone.html";
		return false;
	});
	 //点击选择类型跳转不同的页面
	$("#li1").click(function(){
		localStorage.removeItem("team_eId");
		localStorage.setItem("team_physics",1);
		localStorage.removeItem("team_eventPartake");
   		localStorage.removeItem("team_eventName");
   		localStorage.removeItem("team_startTime");
   		localStorage.removeItem("team_endTime");

		window.location.href="serverelease.html";
	});
	$("#li2").click(function(){
		localStorage.removeItem("team_eId");
		window.location.href="notes.html";
	});
	
});
   
   
var init={
	data:function(){
		var param={"userId":userId};
    	var url=dataLink+"homepage/tobeEventList";
		_asyn(url,param,"get",init.Ok);
	},
	Ok:function(json,code){
		if(json.msg="success"){
		   if(isnull(json.pageInfo)&&isnull(json.dayNumberList)){
		   	   $(".list_default").show();
		   	   $(".Index_list_one_inner").hide();
		   }else{
		   	    //今日待办
			    $(".Index_list_con_one").html("");
			    parem.today(json.pageInfo);
			    //任务
			    $(".Index_list_con_two").html("");
			    parem.task(json.dayNumberList);
		   }
		}
	}
};

var parem={
	today:function(jsonInfo){
		if(!isnull(jsonInfo)&&jsonInfo.length>0){
			$(".Index_list_one_title_one").html("待完成（"+jsonInfo.length+"）");
			for(i=0;i<jsonInfo.length;i++){
				var item = jsonInfo[i];
				
				var topImag ='<p class="Index_list_con_title">';
				if(!isnull(item.ctId)){
					topImag ='<p class="Index_list_con_titleone">';
				}
				var html ='';
				//行动
				if(item.tSubjType==1){
				   var doing = '';
					if(item.tState==0 && isnull(item.ctId)){
					   doing = '<span class="Index_list_con_position">处理中</span>';
				      }
					var strip ='';
					if(!isnull(item.ctId)&& item.mCount>1){
					   strip ='<i>['+item.mCount+'条]</i>';
					}
				   html +='<div class="Index_list_con_list to_Item_html" id="'+item.eiId+'">'+doing;
			       html +=topImag+strip+item.eiDesc+'</p>';
		    	   html +='<p class="Index_list_con_time">截止：'+item.finishTimeStr+'</p>';
		    	   html +='<div class="Index_list_con_btm"><div class="Index_list_con_from" id="'+item.eId+'">';
		    	   html +='<span>来自</span><i>'+item.name+'</i></div></div></div>';
				//任务
				}else if(item.tSubjType==0){
				  
				   html +='<div class="Index_list_con_list to_Event_html"  id="'+item.eId+'">';
			       html +=topImag+'任务到期，关闭任务</p>';
		    	   html +='<p class="Index_list_con_time">截止：'+item.endTimeStr+'</p>';
		    	   html +='<div class="Index_list_con_btm"><div class="Index_list_con_from" id="'+item.eId+'">';
		    	   html +='<span>来自</span><i>'+item.name+'</i></div></div></div>';
				}
				
				$(".Index_list_con_one").append(html);
			}
		}else{
			$(".Index_list_one_title_one").html("待完成（0）");
		}
		
	},
	task:function(jsonInfo){
		if(!isnull(jsonInfo)&&jsonInfo.length>0){
			$(".Index_list_one_title_two").html("已完成（"+jsonInfo.length+"）");
			
			for(i=0;i<jsonInfo.length;i++){
				var item = jsonInfo[i];
				
				var topImag ='<p class="Index_list_con_title">';
				if(!isnull(item.ctId)){
					topImag ='<p class="Index_list_con_titleone">';
				}
				var html ='';
				//行动
				if(item.tSubjType==1){
				   html +='<div class="Index_list_con_list to_Item_html" id="'+item.eiId+'">'+doing;
			       html +=topImag+item.eiDesc+'</p>';
		    	   html +='<p class="Index_list_con_time">截止：'+item.finishTimeStr+'</p>';
		    	   html +='<div class="Index_list_con_btm"><div class="Index_list_con_from" id="'+item.eId+'">';
		    	   html +='<span>来自</span><i>'+item.name+'</i></div></div></div>';
				//任务
				}else if(item.tSubjType==0){
				  
				   html +='<div class="Index_list_con_list to_Event_html"  id="'+item.eId+'">';
			       html +=topImag+'任务到期，关闭任务</p>';
		    	   html +='<p class="Index_list_con_time">截止：'+item.endTimeStr+'</p>';
		    	   html +='<div class="Index_list_con_btm"><div class="Index_list_con_from" id="'+item.eId+'">';
		    	   html +='<span>来自</span><i>'+item.name+'</i></div></div></div>';
				}
				
				$(".Index_list_con_two").append(html);
			}
		}else{
			$(".Index_list_one_title_two").html("已完成（0）");
		}
		
	}
};
