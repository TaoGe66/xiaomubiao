var eId=localStorage.getItem("team_eId");
var userId = localStorage.getItem("team_userId");
$(function(){
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
//点击截止时间弹窗确定按钮
$(".Index_overtime_btntwo").click(function(){
	$(".event_summary").hide();
	$(".Index_overtime").hide();
	$(".Event_endtime").html('结束时间：'+ $("#test").val()) ;
});
//	点击三个点出现列表选项弹窗
$(".Event_area ").on("click","span",function(){
	$(".event_summary").show();
	$(".Event_option_alert").show();
});
//点击弹窗取消
$(".Event_delete_btnone").click(function(){
	$(".event_summary").hide();
	$(".Event_delete").hide();
	$(".Event_end").hide();
	$(".Event_secede").hide();
	$(".event_summary3").hide();
	$(".event_summary1").hide();
	$(".Event_alert").hide();
	$(".Index_overtime").hide();
});
//跳转任务延期
$(".Event_option_two").click(function(){
	window.location.href="task_delay.html";
});
//标记完成
$(".Event_option_one").click(function(){
	$(".Event_option_alert").hide();
	/*$(".event_summary").show();
	$(".event_summary3").show();*/
	checkUnfinishedItem();
});
$(".determine2").click(function(){
	$(".event_summary").show();
	$(".event_summary3").hide();
	$(".event_summary1").show();
});

//删除任务弹窗
$(".Event_option_four").click(function(){
	$(".Event_option_alert").hide();
	$(".event_summary").show();
	$(".Event_delete").show();
});
//确定终止任务弹窗
$(".Event_option_three").click(function(){
	$(".Event_option_alert").hide();
	$(".event_summary").show();
	$(".Event_end").show();
});
$(".Event_endtwo_btntwo").click(function(){
	$(".event_summary").show();
	$(".Event_end").hide();
	$(".Event_alert").show();
});
//确定退出任务弹窗
$(".Event_option_five").click(function(){
	$(".Event_option_alert").hide();
	$(".event_summary").show();
	$(".Event_secede").show();
});
	 //add by lixiaobin 2017/08/01 点击阴影区影藏弹窗
	$(".event_summary").click(function(){
		$(".event_summary").hide();
	$(".Event_delete").hide();
	$(".Event_end").hide();
	$(".Event_secede").hide();
	$(".event_summary3").hide();
	$(".event_summary1").hide();
	$(".Event_alert").hide();
		$(".Event_option_alert").hide();
	})
	$(".cancel").click(function(){
		$(".event_summary").css("display","none");
		$(".event_summary1").css("display","none");
	});
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
	//目标报告
	$(".Event_btn").click(function(){
		window.location.href="event_report.html";
	});
	//确定终止
	$(".Event_alertbtn").on("click",".Event_ok",function(){
 		stopEvent();
 	});
 	//删除任务
 	$(".Event_delete").on("click",".Event_deletetwo_btntwo",function(){
 		delEvent();
 	});

})




$(function (){ 


 	var param={"userId":userId,"eId":eId};
 	 $.ajax({
		   type : "get",
	       url:dataLink+"event/getEventInfoDes", 
	       data:param, 
	       dataType : "json",
	       success : function(json){
	         var info = json.envetInfo;
            var leName ='';
            if(!isnull(info.leId)){
            	leName ="["+info.leName+"]";
            }
            $(".Event_area").html("<i>"+leName+"</i>"+info.name+"<span></span>");
            $(".Event_starttime").html("开始时间："+Time(info.startTime));
            if(!isnull(info.endTime)){
            	$(".Event_endtime").html("结束时间："+Time(info.endTime));
            }else{
            	$(".Event_endtime").html("结束时间：<i>未设置</i>");
            }
            if(!isnull(info.delayCount) && info.delayCount>0){
           	   $(".Event_font").html("延期"+Arabia_To_SimplifiedChinese(info.delayCount)+"次，延期时长"+info.delayTime);
            }else{
            	$(".Event_font").hide();
            }
            $(".Event_content_name i").html(info.userName);
            //	点击未设置
			$(".Event_endtime").on("click","i",function(){
				$(".event_summary").show();
				$(".Index_overtime").show();
			});	
            //参加人员
            if(!isnull(json.joinPeople)&&json.joinPeople.length>0){
            	$(".Event_footer_people i").html("("+json.joinPeople.length+")");
            	for(var i=0;i<json.joinPeople.length;i++){
	            	  var headUrl=isnull(json.joinPeople.headUrl)?"img/img03.png":imageHeadSrc+json.joinPeople.headUrl;
	            	  var peoHtml  ='<li class="Event_footer_list">';
	            	      peoHtml +='<img src="'+headUrl+'" alt="" />';
	            	      peoHtml +='<p>'+json.joinPeople.userName+'</p></li>';
	                  $(".Event_content ul").append(peoHtml);
                 }
            }else{
            	$(".Event_footer_people i").html("(0)");
            }
            //任务是否完成
            if(json.isEnd==1){
            	//异常终止
            	if(info.state==6){
            		$(".Event_img").addClass("Event_icontwo");
            		//终止原因
            		if(!isnull(info.stopCauses)){
            			$(".Event_reason_font").html(info.stopCauses);
            			$(".Event_reason_info").show();
            		}
            	}else{
            		$(".Event_img").addClass("Event_icon");
            	}
            	$(".Event_btn").show();
            	$(".Event_option_four").show();
            }else{
            	$(".Event_complete_info").show();
            	$(".Event_complete_con span").eq(0).html(json.countAll);
            	$(".Event_complete_con span").eq(1).html(json.countSix);
            	$(".Event_complete_con span").eq(2).html(json.countThree);
            	$(".Event_complete_con span").eq(3).html(json.countFive);
            	$(".Event_complete_con span").eq(4).html(json.countFour);
            	
            	//操作弹框
            	$(".Event_option_one").show();
            	$(".Event_option_two").show();
            	$(".Event_option_three").show();
            	$(".Event_option_four").show();
            }
            
             //创建人
             if(json.roleType==0){
             	$(".Event_area").addClass("Event_areatwo");
             	 var addHtml  ='<li class="Serve_footer_list"><img src="img/list07.png" alt="" /><p></p></li>';
             	     addHtml +='<li class="Serve_footer_list"><img src="img/list08.png" alt="" /><p></p></li>';
             	$(".Event_content ul").append(addHtml);
             }

             }
	    });
});
//add by xiehuilin 2017/11/03  项目是否有行动未完成
function  checkUnfinishedItem(){ 
		var eId=localStorage.getItem("team_eId");
		var data={"eId":eId};
		 $.ajax({
			   type : "get",
		       url:dataLink+"homepage/getUndoneItem", 
		       data:data, 
		       dataType : "json",
		       success : function(json){
		       	if(getAjaxJson(json)){
		       		if(json.itemSize==0){
						$(".event_summary").show();
					    $(".event_summary1").show();
					    $(".determine").click(function(){
				        	var summary = $(".summary").val();
				        		if(!isnull(summary)){
				        			$(".signin_issue").show();
				        			todoCloseEvent();
				        		}else{
				        			 $(".tishi2").fadeIn(0).delay(2000).fadeOut(0);
				        		}
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
				        		if(!isnull(summary)){
				        			$(".signin_issue").show();
			        			    todoCloseEvent();
			        		     }else{
			        		     	 $(".tishi2").fadeIn(0).delay(2000).fadeOut(0);
			        		     }
					   		 });
					    });
					}
		       	}
		       }
		});
}

//关闭事件
function todoCloseEvent(){
	var summary = $(".summary").val();
	var eId=localStorage.getItem("team_eId");
	var userId = localStorage.getItem("team_userId");
	//var state = $(".getState").attr("id");
	var data = {"eId":eId,"summary":summary,"userId":userId,"tState":2};
	 $.ajax({
		   type : "post",
	       url:dataLink+"homepage/closeEvent", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(getAjaxJson(json)){
	    	   	window.location.href="index.html";
	    	}
	     }
		});
	}


 //add by xiehuilin 2017/11/03 终止任务
 function stopEvent(){
 	var eId=localStorage.getItem("team_eId");
	var userId = localStorage.getItem("team_userId");
	var stopCauses=$(".Event_alert_textarea").val();
	var data = {"eId":eId,"userId":userId,"stopCauses":stopCauses,"state":6};
	alert(stopCauses);
	$.ajax({
		   type : "post",
	       url:dataLink+"event/stopEvent", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	if(getAjaxJson(json)){
	    	   	myRefresh ();
	    	}
	      }
	});
 }	
	
 //add by xiehuilin 2017/11/03 终止任务
 function delEvent(){
 	var eId=localStorage.getItem("team_eId");
	var userId = localStorage.getItem("team_userId");
	var data = {"eId":eId,"userId":userId};
	$.ajax({
		   type : "post",
	       url:dataLink+"event/delEvent", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	if(getAjaxJson(json)){
	    	   	myRefresh ();
	    	}
	      }
	});
 }		

