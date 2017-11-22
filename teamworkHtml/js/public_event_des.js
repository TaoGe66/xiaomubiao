
//	点击奖励/惩罚弹窗的取消按钮
	$(".Index_reward_btn").on("click",".Index_accept_btnone",function(){
     	$(".Event_alert_bg").hide();
     	$(".Index_rewardtwo").hide();
     	$(".Index_reward").hide();
   });



//add by xiehuilin 20170923 跟踪弹框
	$(".item_list").on("click",".btn_jilu",function(event){
		$(".Event_alertthree").css("display","block");
		$(".Event_alert_bg").css("display","block");
		$(".Event_alertthree_textarea").val("");
		eiId=$(this).parent().find(".eiId").html();
	})

//add by wuchao 20170821 取消跟踪记录
$(".Event_track_no").on("click",function(){
	$(".Event_alertthree").css("display","none");
	$(".Event_alert_bg").css("display","none");
});

// add by xiehulin 20170816 校验跟踪记录
	$(".Event_track_ok").on("click",function(){
		var conent=$(this).parents().parents().find(".Event_alertthree_textarea").val();
		if(isnull(conent)){
			$(".Event_alertthree p").show();
			setTimeout(function(){$(".Event_alertthree p").hide();},2000);
			return false;
		}
		tracks.save(conent);
	});

//add  by xiehuilin 20170816 提交跟踪记录
var post_flag = false; //设置一个对象来控制是否进入AJAX过程
var tracks={
	save:function(conent){
		if(post_flag) return; //如果正在提交则直接返回，停止执行
		post_flag = true;//标记当前状态为正在提交状态
	    var eId=localStorage.getItem("team_eId");
		var createBy= localStorage.getItem("team_userId");
		var param={"eiId":eiId,"content":conent,"eId":eId,"createBy":createBy};
    	var url=dataLink+"track/save";
    	alert(post_flag);
    	 _asyn(url,param,"POST",tracks.ok);
	},
	ok:function(json,code){
		if(getAjaxJson(json)){
			post_flag =false; //在提交成功之后将标志标记为可提交状态
			window.location.reload();
		}else{
		   post_flag = false;
		}
	}
}


//修改周期模板状态 (冻结、 激活)
function cycle_state(eiId,cycleState){
	$(".event_summary").show();
	$(".event_summary1").show();
	var Json='"eiId":"'
		+eiId
		+'","cycleState":"'
		+cycleState
		+'"';
	var data='{'+Json+'}';
	$.ajax({
		url : dataLink + "item/updateCycleState",
		type : "POST",
		data : data,
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		success : function(result) {
			if (getAjaxJson(result)) {
				var type = localStorage.getItem("team_type");
				localStorage.removeItem("team_eiId");
				if (event_type == 0) {
					localStorage.removeItem("team_type");
					window.location.href = "eventdetailsone.html";
				} else if (event_type == 1) {
					localStorage.removeItem("team_type");
					window.location.href = "svc_red_eventdetails.html";
				}
			}
		},
		timeout : 3000
	});
	
}


// add by xiehuilin 20170815 行动跟踪记录
function trackShow(item){
	var records=item.recordList;
	var iHtml='';
	for(var x=0;x<records.length;x++){
		var record=records[x];
		 var startTime=timeToMonthDayhm(record.createTime);
		    iHtml+='<div class="event_detailedPiece_track">';
		    iHtml+='<img src="img/track_icon11_03.png" alt="" class="event_detailedPiece_content_img">';
		    iHtml+='<span class="event_detailedPiece_content_txt">'+startTime+'记录跟踪</span>';
		    iHtml+='<p>'+record.content+'</p> ';
		    iHtml+='</div>';
		   
	}
	return iHtml;
}

//事项完成
$(".Event_btn_ok").click(function(){
	var via=true;
	var remark=$(".Event_textarea").val();
	if (!centen(remark,0,80)) {
		$(".Event_alerttwo").find(".Event_alerttwo_error").html("40个汉字以内");
		setTimeout(function(){$(".Event_alerttwo").find(".Event_alerttwo_error").html("");},2000);
		via=false;
	}
	if (via) {
		complete();
	}
});
function  complete(){
	var eId=localStorage.getItem("team_eId");
	var userId=localStorage.getItem("team_userId");
	var userTime=$(".appDateTime").val();//用户完成时间
	var remark=$(".Event_textarea").val();
	var state=3;
	var reslutTime=getFormatDateByLong(Number(finishTime), "yyyy-MM-dd"); 	
	var fTime = userTime.replace(new RegExp("-","gm"),"/");
		fTime= (new Date(fTime)).getTime(); //得到毫秒数

	//根据完成时间和当前时间进行判断是否是延期完成
	if (!isToday(reslutTime)&&finishTime<fTime) {
		state=4;
	}
	//根据完成时间和当前时间进行判断是否是提前完成
	if(!isToday(reslutTime)&&finishTime>fTime){
		state=7;
	}
	if(isToday(reslutTime)){
		state=finishTime<fTime?4:3;
	}

	var data={"createBy":userId,"state":state,"eiId":eiId,"dutyId":dutyId,"eId":eId,"remark":remark,"strfTime":fTime}
	$.ajax({
		  type : "POST",
	      url:dataLink+"item/updateItemState",  
	      dataType:'json',  
	      data:data, 
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  window.location.reload();//刷新当前页面.
	    	  }
	      },  
	      timeout:3000  
	 });
}



//事项终止
$(".Event_ok").click(function(){
	var via=true;
	var remark=$(".Event_alert_textarea").val();
	if (!centen(remark,0,200)) {
		$(".Event_alert").find("p").show();
		setTimeout(function(){$(".Event_alert").find("p").hide();},2000);
		via=false;
	}
	if (via) {
		item_cutout();
	}
});

function  item_cutout(){
	var eId=localStorage.getItem("team_eId");
	var userId=localStorage.getItem("team_userId");
	var state=5;
	var remark=$(".Event_alert_textarea").val();
	var data={"createBy":userId,"state":state,"eiId":eiId,"eId":eId,"remark":remark,"strfTime":new Date().getTime()}
	$.ajax({
		  type : "POST",
	      url:dataLink+"item/updateItemState",  
	      dataType:'json',  
	      data:data, 
	     
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  window.location.reload();//刷新当前页面.
	    	  }
	      },  
	      timeout:3000  
	 });
}

  //add by xiehuilin 20170919 奖惩执行弹框
 var reward_eiId="";
 var rpCategory="";
 var beneficiary="";
$(".item_list").on("click",".event_detailedPiece_btn",function(){
		reward_eiId=$(this).find("i").eq(0).html();
		rpCategory=$(this).find("i").eq(1).html();
		beneficiary=$(this).find("i").eq(2).html();
		var dutyName=$(this).parent().parent().find(".jzsj").find("span").eq(0).html();
		var money=$(this).parent().parent().find(".event_detailedPiece_content_txt").html();
			money=money.split(",")[1].substring(2,8);
		//var money=
		if(rpCategory==2){
			$(".Index_reward_people").find("i").html(dutyName);
			$(".Index_reward_money").find("i").html(money);
			$(".Event_alert_bg").show();
			//奖励弹窗
			$(".Index_reward").show();
		}else{
			$(".Index_rewardtwo_people").find("i").html(dutyName);
			$(".Index_rewardtwo_money").find("i").html(money);
			beneficiary=userId;
			$(".Event_alert_bg").show();
			//惩罚弹窗
			$(".Index_rewardtwo").show();
		}
		

});

//奖惩支付
//add by xiehuilin 20170919 奖执行 确定
$(".Index_reward").on("click",".Index_reward_btntwo",function(){
  	createPayOrder(event_type);

});

//add by xiehuilin 20170919 罚执行 确定
$(".Index_rewardtwo").on("click",".Index_rewardtwo_btntwo",function(){
		createPayOrder(event_type);
});


//收起展开
$(".item_list").on("click",".jzsj",function(){
	var flag = $(this).attr("flag");
	if(flag =="true"){
		$(this).siblings(".Event_listhidebox").show();
		$(this).siblings(".event_detailedPiece_start").show();
		$(this).siblings(".event_detailedPiece_gongkai").show();
		$(this).siblings(".event_detailedPiece_track_block").show();
		$(this).children("p").children(".event_detailedPiece_content_sanjiao_hide").css("transform","rotate(0deg)");
		$(this).parent(".event_detailedPiece_right").children(".event_detailedPiece_statusIcon2").children("img").css("display","none");
		$(this).parent().children(".event_statusIcon2").children("img").css("display","none");
		$(this).attr("flag","false");
	}else{
		$(this).siblings(".Event_listhidebox").hide();
		$(this).siblings(".event_detailedPiece_start").hide();
		$(this).siblings(".event_detailedPiece_gongkai").hide();
		$(this).siblings(".event_detailedPiece_track_block").hide();
		$(this).children("p").children(".event_detailedPiece_content_sanjiao_hide").css("transform","rotate(180deg)");
		$(this).parent().children(".event_detailedPiece_statusIcon2").children("img").css("display","block");
		$(this).parent().children(".event_statusIcon2").children("img").css("display","block");
		$(this).attr("flag","true");
	}
});

//add  by xiehuilin 20170921 公开范围弹框
var eiId="";
var rangThis="";
var ctId=null;
$(".item_list").on("click",".event_detailedPiece_public_edit",function(){
		$(".action_open").show();
		$(this).parents().find(".event_detailedPiece_edit_popups_end").css("display","none");
		$(this).parents().find(".start_box").css("display","none");
		eiId=$(this).parent().find(".eiId").html();
		ctId=$(this).parent().find(".ctId").html();
		rangThis=$(this);
		if(isShowRange==1){
			$(this).parents().find(".event_detailedPiece_edit_popups").css("display","none");
		}else{
			$(this).parents().find(".event_detailedPiece_edit_popupstwo").css("display","none");
		}

});
//add by xiehuilin 20170921 私有弹框
$(".item_list").on("click",".event_privateSuo",function(){

		$(".action_private").show();
		$(".private_determine").show();
		eiId=$(this).parent().find(".eiId").html();
		ctId=$(this).parent().find(".ctId").html();
		rangThis=$(this);

});
//add by xiehuilin  20170921 公开范围--私有 确定更新
$(".memberModal").on("click",".private_determine",function(){
	updateRang(1);
});
//add by xiehuilin  20170921 公开范围--私有 确定更新
$(".memberModal").on("click",".open_determine",function(){
	updateRang(0);
});
//add by xiehuilin 20170921 更新公开范围
function updateRang(isRange){
	ctId=isnull(ctId)?0:ctId;
	var data={"eiId":eiId,"isRange":isRange,"ctId":ctId};
		$.ajax({//得到协助者信息
	        type : "POST",
	        url:dataLink+"item/update",
	        data:data,
	        dataType : "json",
	        success : function(json){
	            if(json.msg=="success"){//成功的时候
	            	if(ctId==0){
		            	if(isRange==0){
			    			$(rangThis).parent().find(".event_detailedPiece_publicSuo ").eq(0).removeClass("event_detailedPiece_public_edit");
							$(rangThis).parent().find(".event_detailedPiece_publicSuo ").eq(0).attr("src","img/img112_01_03.png");
							$(rangThis).parent().find(".event_detailedPiece_publicSuo ").eq(0).addClass("event_privateSuo");
							
							$(".action_open").hide();
						}
						if(isRange==1){
							$(rangThis).parent().find(".event_detailedPiece_publicSuo ").eq(0).removeClass("event_privateSuo");
							$(rangThis).parent().find(".event_detailedPiece_publicSuo ").eq(0).attr("src","img/publicSuo_09.png");
							$(rangThis).parent().find(".event_detailedPiece_publicSuo ").eq(0).addClass("event_detailedPiece_public_edit");
							$(".action_private").hide();
						}
				}else{
					window.location.reload();//刷新当前页面.
				}	
	            }else{
	            	alert("系统错误");
		        }
	        }
	  });
}


//add by xiehuilin 20170922 编辑跳转编辑页
var isCycle=0; //是否是周期行动
$(".item_list").on("click",".event_edit_icon",function(event){
		eiId=$(this).parent().find(".eiId").html();
		isCycle=$(this).parent().find(".isCycle").html();
		event.stopPropagation();
//		$(this).parent(".Event_icon").parent(".event_detailedPiece_gongkai").find(".event_detailedPiece_edit_popups").toggle();
//		$(this).siblings(".event_detailedPiece_edit_popups").toggle();
//		$(this).parents().find(".event_detailedPiece_edit_popups_end").css("display","none");
		if(isShowRange==1){
			$(this).siblings(".event_detailedPiece_edit_popups").toggle();
			$(this).parents(".event_detailedPiece").find(".start_box").hide();
			$(this).parents(".event_detailedPiece").find(".event_detailedPiece_edit_popups_end").hide();
			$(this).parents(".event_detailedPiece").siblings(".event_detailedPiece").find(".event_detailedPiece_edit_popups").hide();
			$(this).parents(".event_detailedPiece").siblings(".event_detailedPiece").find(".event_detailedPiece_edit_popups_end").hide();
			$(this).parents(".event_detailedPiece").siblings(".event_detailedPiece").find(".start_box").hide();
		}else{
			$(this).siblings(".event_detailedPiece_edit_popupstwo").toggle();
			$(this).parents(".event_detailedPiece").find(".start_box").hide();
			$(this).parents(".event_detailedPiece").find(".event_detailedPiece_edit_popups_end").hide();
			$(this).parents(".event_detailedPiece").siblings(".event_detailedPiece").find(".event_detailedPiece_edit_popupstwo").hide();
			$(this).parents(".event_detailedPiece").siblings(".event_detailedPiece").find(".event_detailedPiece_edit_popups_end").hide();
			$(this).parents(".event_detailedPiece").siblings(".event_detailedPiece").find(".start_box").hide();
		}
});
//add by xiehuilin 20170922 编辑跳转
$(".item_list").on("click",".btn_bianji",function(){
	localStorage.setItem("team_eiId",eiId);
	localStorage.setItem("team_type",event_type);
	isCycle==0?_g("edit.html"):_g("edittwo.html");
});

//add by xiehuilin 20170922 指派跳转
$(".item_list").on("click",".btn_zhipai",function(){
	localStorage.setItem("team_isCyc",isCycle);
	localStorage.setItem("team_eiId",eiId);
	localStorage.setItem("team_type",event_type);
	isLight==0?_g("select.html"):_g("lightSelect.html");
});
//add by xiehuilin 20170922  点击冻结事件
$(".item_list").on("click",".frozen_icon2",function(){
	$(".Event_alertfrozen").css("display","block");
	$(".Event_alert_bg").css("display","block");
	eiId=$(this).parent().find(".eiId").html();

});
//add by xiehuilin 20170922  点击取消冻结
$(".Event_alertfrozen2").on("click",".Event_frozen_no2",function(){
		$(".start_box").hide();
		$(".tijiao").hide();
		$(".Event_alert_check").hide();
		$(".Event_alert_bg").hide();
		$(".Event_alertfrozen").hide();
});

//add by xiehuilin 20170922 冻结--更新为解冻
$(".Event_alertfrozen").on("click",".Event_frozen_ok",function(){
		cycle_state(eiId,0);
});
$(".Event_alertfrozen").on("click",".Event_frozen_no",function(){
		$(".Event_alert_bg").hide();
		$(".Event_alertfrozen").hide();
});

//add by lixiaobin 2017/08/14  点击解冻事件
$(".item_list").on("click",".event_Icon2",function(){

	$(".Event_alertfrozen2").css("display","block");
	$(".Event_alert_bg").css("display","block");
	eiId=$(this).parent().find(".eiId").html();
});
//add by lixiaobin 2017/08/14  点击取消解冻
$(".Event_alertfrozen2").on("click",".Event_frozen_no2",function(){
	$(".Event_alertfrozen2").css("display","none");
	$(".Event_alert_bg").css("display","none");
});

//add by xiehuilin 20170922 解冻--更新为冻结
$(".Event_alertfrozen2").on("click",".Event_frozen_ok2",function(){
		cycle_state(eiId,1);
});

//add by xiehuilin 20170922  验收按钮
$(".item_list").on("click",".yanshou",function(event){
	event.stopPropagation();
	$(this).css("display","none");
	$(this).siblings(".tijiao").css("display","block");
	$(this).siblings(".tijiao").find(".start_box").show();
});

//add by xiehuilin 20170922 打分
$(".item_list").on("click",".Event_start>li",function(event){
	event.stopPropagation();
	$(".Event_start>li").removeClass("start02").addClass("start01");
	$(this).removeClass("start02").addClass("start01");
	$(this).nextAll().addClass("start02");

});
//add by xiehuilin 20170923 验收提交提示框
var rank=3;
var dutyId="";
var rankThis="";
var finishTime="";
$(".item_list").on("click",".tijiao",function(event){
	event.stopPropagation();
	rank=5-$(this).parent().find(".Event_start").find(".start02").length;
	eiId=$(this).parent().find(".eiId").html();
	dutyId=$(this).parent().find(".dutyId").html();
	rankThis=$(this);
	$(".Event_alert_check").find("i").html(rank);
	$(".Event_alert_check").show();
	$(".Event_alert_bg").show();
	
});

//add by xiehuilin 20170923 验收提交 取消
$(".Event_alert_check").on("click",".Event_frozen_no",function(){
	$(".Event_alert_bg").hide();
	$(".Event_alert_check").hide();
});



//add by xiehuilin 20170923 评星提交后弹窗
$(".Event_alert_check").on("click",".Event_check_ok",function(event){
		event.stopPropagation();
		ranks.update(rank,eiId,dutyId,rankThis);
		
	});
//add  by xiehuilinn 20170923 未完成编辑小弹窗
$(".item_list").on("click",".wancheng",function(event){
	event.stopPropagation();
	//edit by zhanghaitao  2017/09/28  未完成编辑小弹窗 start
//	$(this).siblings(".event_detailedPiece_edit_popups_end").toggle();
//	$(this).parents().find(".event_detailedPiece_edit_popups").css("display","none");
	$(this).siblings(".event_detailedPiece_edit_popups_end").toggle();
	$(this).parents(".event_detailedPiece").find(".start_box").hide();
	$(this).parents(".event_detailedPiece").siblings(".event_detailedPiece").find(".event_detailedPiece_edit_popups_end").hide();
	$(this).parents(".event_detailedPiece").siblings(".event_detailedPiece").find(".start_box").hide();
	if(isShowRange==1){
		$(this).parents(".event_detailedPiece").find(".event_detailedPiece_edit_popups").hide();
		$(this).parents(".event_detailedPiece").siblings(".event_detailedPiece").find(".event_detailedPiece_edit_popups").hide();
	}else{
		$(this).parents(".event_detailedPiece").find(".event_detailedPiece_edit_popupstwo").hide();
		$(this).parents(".event_detailedPiece").siblings(".event_detailedPiece").find(".event_detailedPiece_edit_popupstwo").hide();
	}
	//edit by zhanghaitao  2017/09/28  未完成编辑小弹窗 end 
});

//add  by  zhanghaitao  2017/09/28  终止编辑小弹窗
$(".item_list").on("click",".zhongzhi",function(event){
	event.stopPropagation();
	$(this).siblings(".event_detailedPiece_edit_popups_end").toggle();
	$(this).parents(".event_detailedPiece").find(".start_box").hide();
	$(this).parents(".event_detailedPiece").siblings(".event_detailedPiece").find(".event_detailedPiece_edit_popups_end").hide();
	$(this).parents(".event_detailedPiece").siblings(".event_detailedPiece").find(".start_box").hide();
	if(isShowRange==1){
		$(this).parents(".event_detailedPiece").find(".event_detailedPiece_edit_popups").hide();
		$(this).parents(".event_detailedPiece").siblings(".event_detailedPiece").find(".event_detailedPiece_edit_popups").hide();
	}else{
		$(this).parents(".event_detailedPiece").find(".event_detailedPiece_edit_popupstwo").hide();
		$(this).parents(".event_detailedPiece").siblings(".event_detailedPiece").find(".event_detailedPiece_edit_popupstwo").hide();
	}
});

//edit by lixiaobin 2017/07/11	标记异常
$(".item_list").on("click",".btn_zhongzhi",function(){
	 eiId=$(this).parent().find(".eiId").html();
	 $(".Event_alert_textarea").val("")
	$(".Event_alert_bg").css("display","block");
	$(".Event_alert").css("display","block");
});

//add by xiehuilin 20170923 做完了 弹框
$(".item_list").on("click",".btn_zuowan",function(){
	 finishTime= $(this).parent().find(".finishTime").html();
	 dutyId=$(this).parent().find(".dutyId").html();
	 eiId=$(this).parent().find(".eiId").html();
	 $(".Event_textarea").val("");
	$(".Event_alert_bg").css("display","block");
	$(".Event_alerttwo").css("display","block");
	function p(s) {
	    return s < 10 ? '0' + s: s;
	}
	var myDate = new Date();
	//获取当前年
	var year=myDate.getFullYear();
	//获取当前月
	var month=myDate.getMonth()+1;
	//获取当前日
	var date=myDate.getDate(); 
	var h=myDate.getHours();       //获取当前小时数(0-23)
	var m=myDate.getMinutes();     //获取当前分钟数(0-59)
	var s=myDate.getSeconds();  
	
	var now=year+'-'+p(month)+"-"+p(date)+" "+p(h)+':'+p(m)+":"+p(s);
	$(".Event_alerttwo input").val(now);
})