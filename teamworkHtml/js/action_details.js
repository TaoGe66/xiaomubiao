//add by xiehuilin 2017/11/01 行动详情数据渲染
var eiId=isnull($.getUrlParam("eiId"))?localStorage.getItem("team_eiId"):$.getUrlParam("eiId");
 var userId=localStorage.getItem("team_userId");
 var itemState=0;
 var trId,dutyId,eId,finishTime,ctId;
 //add  by xiehuilin 2017/07/10 V0.0.02 分页参数
var page=1;
var verdict=true;
$(function(){
	//add by zhanghaitao 2017/11/01  返回首页
	$(".notes_left").click(function(){
		window.location.href="taskDetails.html";
	});
	//add by zhanghaitao 2017/11/01  点击出现输入框留言
	$("section").on("click",".actionright_message",function(){
		trId=$(this).parent().find("i").html();
		$(".Dynamic_input").show();
		$("#Dynamic_input").focus();
		$("#Dynamic_input").val("");
		$(".Dynamic_bg").show();
	});
	//add by zhanghaitao 2017/11/01  监控input事件
	$("#Dynamic_input").bind("input propertychange",function(){
		if($(this).val() != ""){
			$(".Dynamic_btn_ok").css("background","#fe6161");
			_trim();
		}else{
			$(".Dynamic_btn_ok").css("background","#cdcdcd");
		}
		
	});
	
	//add by zhanghaitao 2017/11/01  点击验收
	$(".action_yanshou").click(function(){
		$(".start_model").show();
	});
	//add by zhanghaitao 2017/11/01  评星星数
	$(".start_model").on("click","li",function(){
		$("li").removeClass("start02").addClass("start01");
		$(this).removeClass("start02").addClass("start01");
		$(this).nextAll().addClass("start02");
	});
		
});

  // add      
	(function (){
	        var url = dataLink + "item/sinEventItemDetails";
	        $.ajax({
	            type: "GET",
	            url: url,
	            dataType: "json",
	            data: {"eiId": eiId,"userId":userId},
	            success: function (json) {
	              console.info(json);
	              if(getAjaxJson(json)){
	              	trackRecordList();
	              	var itemInfo=json.itemInfo;
	              	ctId=itemInfo.ctId;
	              	dutyId=itemInfo.dutyId;
	              	eId=itemInfo.eId;
	              	finishTime=itemInfo.finishTime;
	              	itemState=itemInfo.state;
	              	$(".Event_starttime").html('开始：'+getMyDate(itemInfo.startTime));
	              	$(".Event_endtime").html('结束：'+getMyDate(itemInfo.finishTime));
	              	$(".Event_name").find("i").eq(0).html(itemInfo.cUserName);
	              	$(".Event_name").find("i").eq(1).html(itemInfo.dUserName);
	              	$(".Event_areatwo").find("p").html(itemInfo.eiDesc);
	              	itemInfo.isRange==0?$(".Event_public").show():$(".Event_publictwo").show();
	              	//不是委派行动只显示责任人
	              	if(itemInfo.dutyId==itemInfo.createBy){
	              		$(".Event_chuangjian").hide();
	              	}
	              	if(itemInfo.state==3){
	              		$(".moren").addClass("Event_icon");
	              		off.headHide();
	              		off.footHide();
	              	}else if(itemInfo.state==5){
	              		$(".moren").addClass("Event_icontwo");
	              		off.headHide();
	              		off.footHide();
	              	}else if(itemInfo.state==4){
	              		$(".moren").addClass("Event_iconthree");
	              		off.headHide();
	              		off.footHide();
	              	}
	              	if(json.joinEvents.length!=0){
	              		$(".Event_canyu").show();
	              		$(".Event_canyu").find("i").eq(0).html(json.joinEvents.length);
	              	}else{
	              		$(".Event_canyu").hide();
	              	}
	              	//评价回显
	              	if(itemInfo.rank!=null){
	              		for(var i=0;i<itemInfo.rank;i++){
							$(".event_detailedPiece_start_quantity").find("img").eq(i).attr("src","img/startbai1.png");
							$(".start_model").hide();
							$(".action_yanshou").hide();
							$(".event_detailedPiece_start").show();
						}
	              	}
	              }
	            },
	            error: function(XMLHttpRequest) {
	                
	            }
	        });
    }());

	//add by xiehuilin 2017/11/02 跟踪记录
	function trackRecordList(){
		  var url = dataLink + "track/trackRecordList";
		 $.ajax({
	            type: "GET",
	            url: url,
	            dataType: "json",
	            async: false,
	            data: {"eiId": eiId,"userId":userId,"pageNum":page},
	            success: function (json) {
	              console.info(json);
	              if(getAjaxJson(json)){
	              	var trHtml=[];
	              	var trackRecords=json.trackRecords;
	              	 verdict=trackRecords.length>0?true:false;
	              	for(var x=0;x<trackRecords.length;x++){
	              		var headUrl=isnull(trackRecords[x].headUrl)?"img/touxiang_14.png":(imageHeadSrc+trackRecords[x].headUrl);
	              		var html='<div class="actionsection_block">'
							html+='<div class="actionsection_left">'
							html+='<img src="'+headUrl+'"><i class="time_line"></i>'
							html+='</div>'
							html+='<div class="actionsection_right">'
							html+='<p class="actionright_name">'
							html+='<span>'+trackRecords[x].userName+'</span><em>'+trackRecords[x].strCreateTime+'</em>'
							html+='</p>'
							html+='<div class="actionright_content">'
							html+='<span class="aaa">';
							if(trackRecords[x].trType!=null&&trackRecords[x].trType!==0){
								html+='<i class="wancheng_status">'+isNndefined(itemStateVlue[itemState])+'</i>'
							}
							html+=trackRecords[x].content+'</span>'
							html+='</div>'
							html+='<div class="actionright_interaction"><i  style="display:none;">'+trackRecords[x].id+'</i>'
							html+='<span class="actionright_message">留言</span>';
							if(trackRecords[x].trType==null||trackRecords[x].trType==0){
								html+='<span class="actionright_revoked">撤销</span>'
							}
							html+='</div>'
							if(trackRecords[x].replyList.length>0){
								var replyList=trackRecords[x].replyList;
								html+='<div class="messsage_content">'
								for(var y=0;y<replyList.length;y++){
									html+='<div class="messsage_list">'
									html+='<p class="messsage_list_content"><span class="messsage_list_name">'+replyList[y].userName+'：</span>'+replyList[y].content+'</p>'
									html+='<p class="messsage_time">'+replyList[y].strCreteTime+'</p>'
									html+='</div>'
								}
								html+='</div>'
							}
							html+='</div>'
							html+='</div>'
	              		trHtml.push(html);
	              	}
	              	$("section").append(trHtml.join(""));
	              }
	              
	            },
	            error: function(XMLHttpRequest) {
	                
	            }
	        });
	}
	//add by xiehuilin 2017/11/02 保存留言内容
	$(".Dynamic_btn_ok").on("click",function(){
		var url = dataLink + "reply/save";
		var messsage_content=$("#Dynamic_input").val();
		if($("#Dynamic_input").val() != ""){
			_trim();
			$.ajax({
	            type: "POST",
	            url: url,
	            dataType: "json",
	            data: {"eiId": eiId,"userId":userId,"content":messsage_content,"trId":trId},
	            success: function (json) {
	              console.info(json);
	              if(getAjaxJson(json)){
	              	myRefresh ();
	              }
	            },
	            error: function(XMLHttpRequest) {
	            }
	        });
		}
	});
	//add by xiehuilin 2017/11/02 撤销跟踪 弹框
	$("section").on("click",".actionright_revoked",function(){
		trId=$(this).parent().find("i").html();
		$(".action_revoked").show();
	});
	//add by xiehuilin 2017/11/02 撤销跟踪 确认
	$(".revoked_determine").click(function(){
		 var url = dataLink + "track/revokeTrackRecord";
		$.ajax({
	            type: "POST",
	            url: url,
	            dataType: "json",
	            data: {"eiId": eiId,"userId":userId,"id":trId},
	            success: function (json) {
	              console.info(json);
	              if(getAjaxJson(json)){
	              	myRefresh ();
	              }
	            },
	            error: function(XMLHttpRequest) {
	            }
	        });
			
	});	
	

	//add by xiehuilin 2017/11/02 打分确定
	$(".start_determine").click(function(){
		var rank=5-$(".start_box").find(".start02").length;
		for(var i=0;i<ranks;i++){
			$(".event_detailedPiece_start_quantity").find("img").eq(i).attr("src","img/startbai1.png");
		}
		ranks.update(rank);
		
	 });
	//add by xiehuilin 2017/06/19  打分 
	var ranks={
		update:function(rank){
			var param={"eiId":eiId,"rank":rank,"createBy":userId,"eId":eId,"dutyId":dutyId};
	    	var url=dataLink+"item/updateEventItem";
	    	 _asyn(url,param,"POST",ranks.Ok,rank);
		},
		Ok:function(json,code,count){
			if(getAjaxJson(json)){
			$(".start_model").hide();
			$(".action_yanshou").hide();
			$(".event_detailedPiece_start").show();
			}
		}
  }

//add by xiehilin 2017/11/03 控制页面元素开关
var off={
	 // 关闭头部编辑、终止操作
		headHide:function(){
		 $(".Event_option_three").hide();
		 $(".Event_option_two").hide();	
	 },
	 //关闭底部可操作
	 footHide:function(obj){
	 	$(".action_two").hide();
	 }
}

var on={
	 show:function(){
	 }

}



//add by xiehuilin 2017/11/02 分页
	$(window).scroll(function () {
	    var bot =($("section").find(".actionsection_block").height())*3; 
	        if (verdict) {
	            if ((bot + $(window).scrollTop()) >= ($("body").height() - $(window).height())) {
	                verdict=false;
	                page+=1;
	              trackRecordList();
	            }
	        }
	});
	

