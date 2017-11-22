var finishTime="";
var dutyId="";
var eiId="";
var userId="";
var type="";
//add  by xiehuilin 2017/07/10 V0.0.02 分页参数
var page=1;
var verdict=true;
//add by xiehuilin 20170922 项目类型 0 目标 1 需求
var event_type=1;
//add by xiehuilin 20170922 是否是轻企 0 否 1是
var isLight=0;
//add by xiehuilin 20171009 行动责任为自己不显示公开/私有操作
var isShowRange=0;
$(function(){

//切换头像
	$(".event_tab>li").click(function(){
	$(this).addClass("select").siblings().removeClass("select");
	});
	
//确认冻结
	$(".freeze_icon").click(function(){
		$(".Event_alertfrozen2").show();
	});
	
//确认冻结
	$(".freeze_icon").click(function(){
		$(".Event_alertfrozen2").show();
	});

	$(".open_cancel").click(function(){
		$(".action_open").hide();
	});

	$(".private_cancel").click(function(){
		$(".action_private").hide();
	});
	$(".open_determine").click(function(){
		$(".private_determine").hide();
	});

//	点击弹窗灰色区域弹窗消失	
	$(".Event_alert_bg").click(function(){
		$(".Event_alert_bg").hide();
		$(".Event_alerttwo").hide();
		$(".Event_alert").hide();
		$(".Event_alertfrozen").hide();
		$(".Event_alertfrozen2").hide();
		$(".Index_reward").hide();
		$(".Index_rewardtwo").hide();
	});
//	总结弹框
	$(".int1").on("click",function(){
		$(this).attr("src","img/radion_click.png");
		$(".int2").attr("src","img/radio.png");
		$(this).addClass("getState");
		$(".int2").removeClass("getState");
	})
	$(".int2").on("click",function(){
		$(this).attr("src","img/radion_click.png");
		$(".int1").attr("src","img/radio.png");
		$(this).addClass("getState");
		$(".int1").removeClass("getState");
	})
	$(".complete").click(function(){
		$(".event_summary").css("display","block");
		$(".event_summary1").css("display","block");
		$(".determine").click(function(){
			var finishTime = $("#appDateTime").val();
        	var summary = $(".summary").val();
        	//add by xiehuilin 校验输入参数
        	if(param.fEvent(finishTime,summary,"tishi1","prompt")){
        		todoCloseEvent();
        	}
		});
		
	})
	$(".edit_icon").attr("flage","false");
	$(".Event_track_no").on("click",function(){
		$(".Event_alertthree").css("display","none");
		$(".Event_alert_bg").css("display","none");
	})
	
	//add by lixiaobin 2017/08/09  点击返回首页三角
	var flage = false;
	$(".return_sanjiao").click(function(){
		if(flage == false){
			$(this).animate({"right":"3rem"},300);
			$(".return_index").animate({"right":"0rem"},300);
			flage = true;
		}else{
			$(this).animate({"right":"-1rem"},300);
			$(".return_index").animate({"right":"-4rem"},300);
			flage = false;
		}
	})
	 //add by lixiaobin 2017/08/01 点击阴影区影藏弹窗
	$(".Event_alert_bg").click(function(){
		$(".Event_alert_bg").css("display","none");
		$(".Event_alerttwo").css("display","none");
		$(".Event_alert").css("display","none");
		$(".Event_alertthree").css("display","none");
		$(".Event_alert_check").css("display","none");
	});
	$(".memberPopupsBg").click(function(){
		$(".action_open").css("display","none");
	});
	

	$(".cancel").click(function(){
		$(".event_summary").css("display","none");
		$(".event_summary1").css("display","none");
	})
	//	edit by lixiaobin 2017/07/11  点击其他区域隐藏
	$(".content").click(function(){
		$(".wancheng_icon").hide();
		$('.wancheng').attr("flag","false");
		$(".event_detailedPiece_edit_popups").hide();
		$(".event_detailedPiece_edit_popupstwo").hide();
//		$(".Event_start").hide();
		$(".start_box").hide();
		$(".event_detailedPiece_edit_popups_end").hide();
		$(".tijiao").hide();
		$(".pinjia").show();
		$(".yanshou").show();
		$(".edit_box").hide();
		$(".zpsb_box").hide();
		$(".zpsb").css("background","url(img/zpsb_top.png) no-repeat right center");
	  $(".zpsb").css("background-size","20%");
	  $('.zpsb').attr("flag","false");
	})	
	//edit by lixiaobin 2017/07/11 返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});

$(".Event_btnul").click(function(event){
    event.stopPropagation();
});
		//点击异常终止页面字体置灰
		$(".Event_btnulremove").click(function(){ 
			$(this).parent().parent().parent().find("p").css("color","#999999");
			
		})
         userId= localStorage.getItem("team_userId");
         localStorage.setItem("team_temp_userId",userId);
          type=1;
          init.data(userId,type);
	})
	var ali = $(".Event_tab li");
	var iW=0;
   	for(var i=0;i<ali.length;i++){
        	iW+=ali.outerWidth();
        }
        $(".Event_tab").css("width",iW+"px");
        $(".Event_head_inner").click(function(){
        	_g("event_description_founder.html")
        });
          // add by xiehuilin 2017/06/24 跳转评论列表
		$(".Event_footer1").click(function(){
			_g("comment.html");
		});


//add by xiehuilin 事项是否全部完成 0 否 1 是
var fItem=0;
// add by xiehuilin 2017/06/16 初始化事件详情
var isEventDuty="";
// add by xiehuilin 2017/07/11 是否有奖惩规则
var isRule="";
var createBy= localStorage.getItem("team_userId");
var eventState=0;
var source=$.getUrlParam("source");
var isend=0;
var init={
	data:function(userId,type){
		var leId=localStorage.getItem("team_leId");
		var eId=localStorage.getItem("team_eId");
		var param={"userId":userId,"eId":eId,"createBy":createBy,"pageNum":page,"reqType":type};
    	var url=dataLink+"newEvent/svcManag";
    	_asyn(url,param,"get",init.Ok,type);
	},
	Ok:function(json, code,type){
		console.info(json);
		if(json.msg=='success'){
			isEventDuty=json.isEventDuty;
			isRule=json.isRule;
			dutyId=json.dutyId;
			isend=json.isend;
			if(type==1&&page==1){
				isShowRange=json.users.length>1?1:0;
				localStorage.setItem("team_isShowRange",isShowRange);
			}
			isShowRange=localStorage.getItem("team_isShowRange");
			isEventDuty==0?localStorage.setItem("team_roleType",1):localStorage.setItem("team_roleType","");
			if(type==1){
				e.show(json);
			}
			e.dShow(json.users,type)
			e.iShow(json.item);
			//评星小弹窗
	
		}else{
			alert("参数异常");
		}

		//add  by xiehuilin 2017/07/12 没有规则的场景下 屏蔽奖惩管理入口
		if(isEventDuty==0&&isRule==0&&type==1){
			$(".Event_reward").show();
			}else if(type==1&&isEventDuty==1){
				isRule==0?$(".Event_reward").hide():$(".Event_reward").show();
			}
		if(type==1&&isRule==0&&json.isend==1){
			$(".Event_reward").hide();
		}
		if(isRule==1&&isEventDuty!==3){
			$(".Event_reward").show();
		}
	}
}

// add by xiehuilin 2017/06/16 事项列表	
var e={
	 show:function(json){
	 		var et=json.event;
	 		//add by xiehuilin 2017/06/19 调用页面元素开关方法
	 		 eventState=et.state;
		     e.hasShow(json);
		     eId=et.eId;
		     aaa=et.startTime;
		     isLight=et.leId!=null?1:0;
		     localStorage.setItem("team_sTime",TimeMin(et.startTime));
		     localStorage.setItem("team_leId",et.leId);
		     localStorage.setItem("team_saTime",et.startTime);
		     localStorage.setItem("team_eTime",Time(et.endTime,11));
		     if(et.leId==null){
			 	$(".event_name").html(et.name);
			 }else{
			 	$(".event_title").html("["+et.leName+"]");
			 	 $(".event_name").find("i").html(et.name);
			 }
			 $(".Event_head_font").html("描述："+et.target);
			 $(".Sign_people").html("创建人：&nbsp;"+et.userName);
			 $(".Event_right_num i").html(et.compRate);
			if(json.isThisYear==0){
				$(".Event_head_time span").html(sameDate(et.startTime,et.endTime));
			}else{
				$(".Event_head_time span").html(getMyDate(et.startTime)+'-'+getMyDate(et.endTime));
			}
			var wiDth = $(".Event_head_time span").width();
			$(".start_hour").html(dateHour(et.startTime,2));  
			$(".end_hour").html(dateHour(et.endTime,2));  
			$(".start_date").html(et.sTime);  
			$(".end_date").html(et.eTime); 
			var peo = isnull(et.userNameTwo) ?'创建人：&nbsp;'+et.userName:'责任人：&nbsp;'+et.userNameTwo;
			$(".Sign_people").html(peo);
			  
	 },
	 /* add by xiehuilin 2017/06/19 事项内容显示  
	  *	@param items  事项
	  * @param isEventDuty  0 创建人 1责任人  2 (创建人、责任人)
	 */
	 iShow:function(items){
	 	 verdict=items.length>0?true:false;
	 	  page==1&&items.length<=0?$(".my_event_default").show():$(".my_event_default").hide();
	 	 page==1&&items.length<=0?$(".event_category ").hide():$(".event_category ").show();
	 	for(var x=0;x<items.length;x++){
	 		var item=items[x];
	 		$(".item_list").append(e.itemData(item));
//	 		edit(0);
	 	   fItem=item.state==3||item.state==4?1:0;
	 	}
	
	 	$(".jzsj").attr("flag","false");
	 	fItem==1?$(".start_inner").removeClass("start_innertwo"):$(".start_inner").addClass("start_innertwo");
	 	oper.irespond();
//			edit by lixiaobin 2017/07/11	取消返回
			$(".Event_btn_no").click(function(){
				$(".Event_alert_bg").css("display","none");
				$(".Event_alerttwo").css("display","none");
				$(".Event_alert").css("display","none");
			})
			//  edit by zyting 2017/06/14 点击异常取消
			$(document).click(function(){
			    $(".Event_btnul").hide();
			});
	 },
	  // add by xiehuilin 2017/06/19 事项责任人显示
	 dShow:function(users,type){
	 		for(var x=0;x<users.length;x++){
	 		var user=users[x];
	 		var pice=user.headUrl==null?"img/img03.png":(imageHeadSrc+user.headUrl);
	 		var dhtml='<li><p class="userId" style="display: none;">'+user.userId+'</p>';
	 			dhtml+='<img src="'+pice+'" alt="">';
	 			if(user.newPushRecord==0&&isEventDuty==0||isEventDuty==3){
	 				dhtml+='<i class="touxiang_icon" id='+user.userId+'></i>';
	 				
	 			}
	 			dhtml+='<p>'+user.userName+'</p></li>';
	 			if(type==1){
	 				$(".Event_tab").append(dhtml);
	 				user.newPushRecord==0?$(".touxiang_icon").css("display","block"):$(".touxiang_icon").css("display","none");
	 			}
		 	}
	 },
	 		
	/* add by xiehuilin 2017/06/19 事项内容数据封装  
	  *	@param items  事项
	  * @param isEventDuty  0 创建人 1责任人  2 (创建人、责任人)
	 */
	 itemData:function(item){
	 	 var temp_userId=localStorage.getItem("team_temp_userId");
	 	 var dutyId=item.dutyId;
	 	 var remark=item.remark==null?"":item.remark;
	 	 var cuserId=localStorage.getItem("team_userId");
	 	 var   dutyhtml='<p>执行人：<span>'+item.userName+'</span><img src="img/img15.png" alt="" class="event_detailedPiece_content_sanjiao_hide"></p>';
	 	 //add by xiehuilin 20170922 星级回显
	 	 var  rankHtml="";
	 	 //add by xeihuilin 20170926 评价操作
	 	 var  assessHtml="";
		 	 	   if(item.rank!=null){
				 	    rankHtml+='<div class="event_detailedPiece_start BorderTop">';
				 		rankHtml+='<span>验收评价：</span>';
				 		rankHtml+='<div class="event_detailedPiece_start_quantity">';
				 		//add by xiehuilin 2017/07/12 会显打分值
				 		for(var x=0;x<item.rank;x++){
				 			rankHtml+='<img src="img/start01.png" alt="">';
				 		}
				 		for(var y=0;y<5-item.rank;y++){
				 			rankHtml+='<img src="img/start02.png" alt="">';
				 		}
				 		rankHtml+='</div></div>';
				 	}
				 	 if(isEventDuty==0&&item.rank==null&&item.isReward==0&&isend==0){
				 	     assessHtml+='<button class="yanshou">验收</button>';
						 assessHtml+='<button class="tijiao">提交';
						 assessHtml+='<div class="start_box">';
						 assessHtml+='<ul class="Event_start">';
						 assessHtml+='<li class="start_img start01"></li><li class="start_img start01"></li><li class="start_img start01"></li><li class="start_img start02"></li><li class="start_img start02"></li>';
						 assessHtml+=' </ul>';
						 assessHtml+='<div class="event_start_sanjiao"></div>';
						 assessHtml+='</div>'
						 assessHtml+='</button>';
				    }
	 	    var iHtml="";
	 	    var falg=eventState!==2&&eventState!==6&&eventState==!7&&eventState!==8;
	 	    //add by xie huilin 20170920是奖惩行动
	 	    if(item.isReward==1){
	 	    	iHtml+='<li class="event_detailedPiece">';
				iHtml+='<div class="event_detailed_public">'
				if(item.state==3){
					iHtml+='<img src="img/qian_icon_03.png" alt="" class="event_schedule_icon">';
				}else if(item.state!==3&&item.showType==0||item.showType==1){
					iHtml+='<img src="img/all36.png" alt="" class="event_schedule_icon">';
				}else if(item.state!==3&&item.showType==2){
					iHtml+='<img src="img/all35.png" alt="" class="event_schedule_icon">';
				}
				iHtml+='<i class="event_schedule_line"></i>';	
				iHtml+='</div>'	;
				iHtml+='<div class="event_detailedPiece_right">';
				iHtml+='<div class="event_detailedPiece_flex">';
				iHtml+='<i class="event_sanjiaoxu"></i>';
				iHtml+='<p class="event_detailedPiece_time BorderBottom">';
				iHtml+='<span><span>'+item.startStrTime+'</span>开始</span>';
				iHtml+='</p>'
				iHtml+='<p class="event_detailedPiece_time event_detailedPiece_time_right BorderBottom">';
				iHtml+='<span><span>'+item.fTimeStr+'</span>结束</span>';
				iHtml+='</p>';
				iHtml+='</div>';
				iHtml+='<div class="event_detailedPiece_content">';
				iHtml+='<div class="event_detailedPiece_jiange">';
				iHtml+='<img src="img/icon08.png" alt="" class="event_detailedPiece_content_img"><span class="event_detailedPiece_content_txt">'+item.eiDesc+'</span>';
				iHtml+='</div>';
				iHtml+='</div>';
				iHtml+='<div class="jzsj" flag="false">';
				iHtml+=dutyhtml
				iHtml+='</div>';
				  if(item.state==0&&(item.dutyId==createBy||isEventDuty==0)){
					iHtml+='<div class="Event_listhidebox BorderTop">';
					iHtml+='<div class="event_detailedPiece_btn">';
					iHtml+='<button><i  style="display:none;">'+item.eiId+'</i><i style="display:none;">'+item.rpCategory+'</i><i  style="display:none;">'+item.dutyId+'</i>执行</button>';
					iHtml+='</div>';
					iHtml+='</div>';
					iHtml+='</div>';
				}
				if(item.state==3||item.state==4||item.state==6||item.state==7){
					iHtml+='<div class="Event_listhidebox" style="display: block;">';
					iHtml+='<div class="event_detailedPiece_jiangli BorderTop">';
					iHtml+='<img src="img/icon09.png" alt="" class="event_detailedPiece_content_img">'
					iHtml+='<span class="event_detailedPiece_content_txt">'+item.uTime+'由<span>'+item.userName+'</span>完成</span>';
					iHtml+='</div>';
					iHtml+='<div class="event_detailedPiece_statusIcon" style="display: block;">';
					iHtml+='<img src="img/normal.png" alt=""></div>';
					iHtml+='</div>';
					iHtml+='<div class="event_detailedPiece_statusIcon2"><img src="img/normal.png" alt=""></div>';
					iHtml+='</div>';
				}
				iHtml+='</li>';
	 	    }
	 	    //add by xie huilin 20170920非奖惩行动
	 	    if(item.isReward!==1){
	 	    	 var falg=eventState!==2&&eventState!==6&&eventState==!7&&eventState!==8;
	 	    	switch(item.state){
	 	    		 case 3:
	 	    		iHtml+='<li class="event_detailedPiece">';
					 iHtml+='<!--通用进展条样式-->';
					 iHtml+='<div class="event_detailed_public">';
					 iHtml+='<img src="img/all34.png" alt="" class="event_schedule_icon">';
					 iHtml+='<i class="event_schedule_line"></i>';
					 iHtml+='</div>';
					 iHtml+='<!--右边详细内容-->';
					 iHtml+='<div class="event_detailedPiece_right">';
					 iHtml+='<div class="event_detailedPiece_flex">';
					 iHtml+='<i class="event_sanjiaoxu"></i>';
					 iHtml+='<p class="event_detailedPiece_time BorderBottom">';
					 iHtml+='<span><span>'+item.startStrTime+'</span>开始</span>';
					 iHtml+='</p>';
					 iHtml+='<p class="event_detailedPiece_time event_detailedPiece_time_right BorderBottom">';
					 iHtml+='<span><span>'+item.fTimeStr+'</span>结束</span>';
					 iHtml+='</p>';
					 iHtml+='</div>';
					 iHtml+='<div class="event_detailedPiece_content">';
					 iHtml+='<div class="event_detailedPiece_jiange">';
					 iHtml+='<img src="img/icon08.png" alt="" class="event_detailedPiece_content_img"><span class="event_detailedPiece_content_txt">'+item.eiDesc+'</span>';
					 iHtml+='</div>';
					 iHtml+='</div>';
					 iHtml+='<div class="jzsj" flag="false">';
					 iHtml+=dutyhtml;
					 iHtml+='</div>';
					 iHtml+='<div class="Event_listhidebox">';
					 iHtml+='<div class="event_detailedPiece_track_block BorderTop">';
					 iHtml+=trackShow(item)
					 iHtml+='<div class="event_detailedPiece_track">';
					 iHtml+='<img src="img/icon09.png" alt="" class="event_detailedPiece_content_img">';
					 iHtml+='<span class="event_detailedPiece_content_txt">'+item.uTime+'由<span>'+item.userName+'</span>完成</span>';
					 iHtml+='</div>';
					 iHtml+='<div class="event_statusIcon"><img src="img/img24.png" alt=""></div>';
					 iHtml+='</div>';
					 iHtml+='<!--验收评论-->';
					 iHtml+='<i class="showRank"></i>'
					 if(item.rank!=null){
					   iHtml+=rankHtml;
					 }
					
					 iHtml+='<!--评价星级-->';
					 iHtml+='<div class="Event_icon BorderTop"><i class="dutyId" style="display: none;">'+item.dutyId+'</i>';
					 iHtml+='<i class="ctId" style="display:none;">'+item.ctId+'</i><i class="isCycle" style="display:none;">'+item.isCycle+'</i><i class="eiId" style="display:none;">'+item.eiId+'</i><i class="isRange" style="display:none;">'+item.isRange+'</i>';
					var editFlag=falg&&item.dutyId==createBy||isEventDuty==0;
					  //add by xiehuilin 20171009 公开私有操作 start
				 	if((editFlag||isEventDuty==0)&&isShowRange==1){
						if(item.isRange==0){
		 	    			 iHtml+='<img src="img/img112_01_03.png" alt="" class="event_detailedPiece_publicSuo event_privateSuo">';
		 	    		 }else{
		 	    		 	 iHtml+='<img src="img/publicSuo_09.png" alt="" class="event_detailedPiece_publicSuo event_detailedPiece_public_edit">';  
		 	    		 }
					 }

					 if(editFlag&&item.isCycle==1&&item.cycleState==0){
					 	iHtml+='<img src="img/img112_01_05.png" alt="" class="event_detailedPiece_publicSuo event_Icon2">';
					 }
					 if(editFlag&&item.isCycle==1&&item.cycleState==1){
					 	 iHtml+='<img src="img/icon100_13.png" alt="" class="event_detailedPiece_publicSuo event_detailedPiece_Icon2 frozen_icon2">'
					 }
					  if(item.rank==null){
						iHtml+=assessHtml;
					 }
					 iHtml+='<!--指派编辑小弹窗-->';
					 iHtml+='<div class="event_detailedPiece_edit_popups">';
					 iHtml+='<div class="event_detailedPiece_btn_flex btn_bianji"><img src="img/btnulremove.png" class="event_detailedPiece_btn_popups_icon "><span>编辑</span></div>';
					 iHtml+='<div class="event_detailedPiece_btn_flex btn_zhipai"><img src="img/btnulisok.png" class="event_detailedPiece_btn_popups_icon"><span>指派</span></div>';
					 iHtml+='<div class="event_detailedPiece_edit_popups_edit_sanjiao"></div>';
					 iHtml+='</div>';
					 iHtml+='</div>';
					 iHtml+='</div>';
					 iHtml+='<div class="event_statusIcon2"><img src="img/img24.png" alt=""></div>';
					 iHtml+='</div>';
					 iHtml+='</li>';
	 	    		 break;
	 	    		case 4:
	 	    		 iHtml+='<li class="event_detailedPiece">';
					 iHtml+='<!--通用进展条样式-->';
					 iHtml+='<div class="event_detailed_public">';
					 iHtml+='<img src="img/all34.png" alt="" class="event_schedule_icon">';
					 iHtml+='<i class="event_schedule_line"></i>';
					 iHtml+='</div>';
					 iHtml+='<!--右边详细内容-->';
					 iHtml+='<div class="event_detailedPiece_right">';
					 iHtml+='<div class="event_detailedPiece_flex">';
					 iHtml+='<i class="event_sanjiaoxu"></i>';
					 iHtml+='<p class="event_detailedPiece_time BorderBottom">';
					 iHtml+='<span><span>'+item.startStrTime+'</span>开始</span>';
					 iHtml+='</p>';
					 iHtml+='<p class="event_detailedPiece_time event_detailedPiece_time_right BorderBottom">';
					 iHtml+='<span><span>'+item.fTimeStr+'</span>结束</span>';
					 iHtml+='</p>';
					 iHtml+='</div>';
					 iHtml+='<div class="event_detailedPiece_content">';
					 iHtml+='<div class="event_detailedPiece_jiange">';
					 iHtml+='<img src="img/icon08.png" alt="" class="event_detailedPiece_content_img"><span class="event_detailedPiece_content_txt">'+item.eiDesc+'</span>';
					 iHtml+='</div>';
					 iHtml+='</div>';
					 iHtml+='<div class="jzsj" flag="false">';
					 iHtml+=dutyhtml;
					 iHtml+='</div>';
					 iHtml+='<div class="Event_listhidebox">';
					 iHtml+='<div class="event_detailedPiece_track_block BorderTop">';
					 iHtml+=trackShow(item)
					 iHtml+='<div class="event_detailedPiece_track">';
					 iHtml+='<img src="img/icon09.png" alt="" class="event_detailedPiece_content_img">';
					 iHtml+='<span class="event_detailedPiece_content_txt">'+item.uTime+'由<span>'+item.userName+'</span>完成</span>';
					 iHtml+='</div>';
					 iHtml+='<div class="event_statusIcon"><img src="img/img23.png" alt=""></div>';
					 iHtml+='</div>';
					 iHtml+='<!--验收评论-->';
					 iHtml+='<i class="showRank"></i>'
					 if(item.rank!=null){
					   iHtml+=rankHtml;
					 }
					
					 iHtml+='<!--评价星级-->';
					 iHtml+='<div class="Event_icon BorderTop"><i class="dutyId" style="display: none;">'+item.dutyId+'</i>';
					 iHtml+='<i class="ctId" style="display:none;">'+item.ctId+'</i><i class="isCycle" style="display:none;">'+item.isCycle+'</i><i class="eiId" style="display:none;">'+item.eiId+'</i><i class="isRange" style="display:none;">'+item.isRange+'</i>';
					var editFlag=falg&&item.dutyId==createBy||isEventDuty==0;
					  //add by xiehuilin 20171009 公开私有操作 start
				 	if(editFlag&&isShowRange==1){
						if(item.isRange==0){
		 	    			 iHtml+='<img src="img/img112_01_03.png" alt="" class="event_detailedPiece_publicSuo event_privateSuo">';
		 	    		 }else{
		 	    		 	 iHtml+='<img src="img/publicSuo_09.png" alt="" class="event_detailedPiece_publicSuo event_detailedPiece_public_edit">';  
		 	    		 }
					 }

					// iHtml+='<img src="img/edit_03.png" alt="" class="event_detailedPiece_publicSuo event_detailedPiece_Icon2 event_edit_icon">';
					 
					 if(editFlag&&isEventDuty==0&&item.isCycle==1&&item.cycleState==0){
					 	iHtml+='<img src="img/img112_01_05.png" alt="" class="event_detailedPiece_publicSuo event_Icon2">';
					 }
					 if(editFlag&&isEventDuty==0&&item.isCycle==1&&item.cycleState==1){
					 	 iHtml+='<img src="img/icon100_13.png" alt="" class="event_detailedPiece_publicSuo event_detailedPiece_Icon2 frozen_icon2">'
					 }
					  if(item.rank==null){
						iHtml+=assessHtml;
					 }
					 iHtml+='<!--指派编辑小弹窗-->';
					 iHtml+='<div class="event_detailedPiece_edit_popups">';
					 iHtml+='<div class="event_detailedPiece_btn_flex btn_bianji"><img src="img/btnulremove.png" class="event_detailedPiece_btn_popups_icon "><span>编辑</span></div>';
					 iHtml+='<div class="event_detailedPiece_btn_flex btn_zhipai"><img src="img/btnulisok.png" class="event_detailedPiece_btn_popups_icon"><span>指派</span></div>';
					 iHtml+='<div class="event_detailedPiece_edit_popups_edit_sanjiao"></div>';
					 iHtml+='</div>';
					 iHtml+='</div>';
					 iHtml+='</div>';
					 iHtml+='<div class="event_statusIcon2"><img src="img/img23.png" alt=""></div>';
					 iHtml+='</div>';
					 iHtml+='</li>';
	 	    		 break;
	 	    		 case 5:
 	    			 iHtml+='<li class="event_detailedPiece">';
					 iHtml+='<!--通用进展条样式-->';
					 iHtml+='<div class="event_detailed_public">';
					 iHtml+='<img src="img/all34.png" alt="" class="event_schedule_icon">';
					 iHtml+='<i class="event_schedule_line"></i>';
					 iHtml+='</div>';
					 iHtml+='<!--右边详细内容-->';
					 iHtml+='<div class="event_detailedPiece_right">';
					 iHtml+='<div class="event_detailedPiece_flex">';
					 iHtml+='<i class="event_sanjiaoxu"></i>';
					 iHtml+='<p class="event_detailedPiece_time BorderBottom color999">';
					 iHtml+='<span><span>'+item.startStrTime+'</span>开始</span>';
					 iHtml+='</p>';
					 iHtml+='<p class="event_detailedPiece_time event_detailedPiece_time_right BorderBottom color999">';
					 iHtml+='<span><span>'+item.fTimeStr+'</span>结束</span>';
					 iHtml+='</p>';
					 iHtml+='</div>';
					 iHtml+='<div class="event_detailedPiece_content">';
					 iHtml+='<div class="event_detailedPiece_jiange">';
					 iHtml+='<img src="img/icon08.png" alt="" class="event_detailedPiece_content_img">';
					 iHtml+='<span class="event_detailedPiece_content_txt">'+item.eiDesc+'</span>';
					 iHtml+='</div>';
					 iHtml+='</div>';
					 iHtml+='<div class="jzsj" flag="false">';
					 iHtml+='<p>执行人：<span>'+item.userName+'</span>';
					if(item.isAccept==2){
						iHtml+='<span class="event_detailedPiece_assignedfailure">(待接受)</span>';
					 }
					 if(item.state==2){
					 	iHtml+='<span class="event_detailedPiece_assignedfailure">指派失败</span>';
					 }
					 iHtml+='<img src="img/img15.png" alt="" class="event_detailedPiece_content_sanjiao_hide"></p>';
					 iHtml+='</div>';
					 iHtml+='<div class="Event_listhidebox">';
					 iHtml+='<div class="event_detailedPiece_jiangli BorderTop">';
					 iHtml+='<img src="img/yichang_icon.png" alt="" class="event_detailedPiece_content_img">';
					 iHtml+='<span class="event_detailedPiece_content_txt color999">'+item.uTime+'</span><span class="event_detailedPiece_jiangli_stop color999">终止行动</span>';
					 iHtml+='<p class="color999">'+remark+'</p>	';
					 iHtml+=trackShow(item);
					 iHtml+='<div class="event_statusIcon"><img src="img/abnormal.png" alt=""></div>';
					 iHtml+='</div>';
					 iHtml+='</div>';
					 iHtml+='<div class="event_statusIcon2"><img src="img/abnormal.png" alt=""></div>';
					 iHtml+='</div>';
					 iHtml+='</li>';
	 	    		 break;
	 	    		 case 7:
	 	    		 iHtml+='<li class="event_detailedPiece">';
					 iHtml+='<!--通用进展条样式-->';
					 iHtml+='<div class="event_detailed_public">';
					 iHtml+='<img src="img/all34.png" alt="" class="event_schedule_icon">';
					 iHtml+='<i class="event_schedule_line"></i>';
					 iHtml+='</div>';
					 iHtml+='<!--右边详细内容-->';
					 iHtml+='<div class="event_detailedPiece_right">';
					 iHtml+='<div class="event_detailedPiece_flex">';
					 iHtml+='<i class="event_sanjiaoxu"></i>';
					 iHtml+='<p class="event_detailedPiece_time BorderBottom">';
					 iHtml+='<span><span>'+item.startStrTime+'</span>开始</span>';
					 iHtml+='</p>';
					 iHtml+='<p class="event_detailedPiece_time event_detailedPiece_time_right BorderBottom">';
					 iHtml+='<span><span>'+item.fTimeStr+'</span>结束</span>';
					 iHtml+='</p>';
					 iHtml+='</div>';
					 iHtml+='<div class="event_detailedPiece_content">';
					 iHtml+='<div class="event_detailedPiece_jiange">';
					 iHtml+='<img src="img/icon08.png" alt="" class="event_detailedPiece_content_img"><span class="event_detailedPiece_content_txt">'+item.eiDesc+'</span>';
					 iHtml+='</div>';
					 iHtml+='</div>';
					 iHtml+='<div class="jzsj" flag="false">';
					 iHtml+=dutyhtml;
					 iHtml+='</div>';
					 iHtml+='<div class="Event_listhidebox">';
					 iHtml+='<div class="event_detailedPiece_track_block BorderTop">';
					 iHtml+=trackShow(item)
					 iHtml+='<div class="event_detailedPiece_track">';
					 iHtml+='<img src="img/icon09.png" alt="" class="event_detailedPiece_content_img">';
					 iHtml+='<span class="event_detailedPiece_content_txt">'+item.uTime+'由<span>'+item.userName+'</span>完成</span>';
					 iHtml+='</div>';
					 iHtml+='<div class="event_statusIcon"><img src="img/advance.png" alt=""></div>';
					 iHtml+='</div>';
					 iHtml+='<!--验收评论-->';
					 iHtml+='<i class="showRank"></i>'
					 if(item.rank!=null){
					   iHtml+=rankHtml;
					 }
					
					 iHtml+='<!--评价星级-->';
					 iHtml+='<div class="Event_icon BorderTop"><i class="dutyId" style="display: none;">'+item.dutyId+'</i>';
					 iHtml+='<i class="ctId" style="display:none;">'+item.ctId+'</i><i class="isCycle" style="display:none;">'+item.isCycle+'</i><i class="eiId" style="display:none;">'+item.eiId+'</i><i class="isRange" style="display:none;">'+item.isRange+'</i>';
					var editFlag=falg&&item.dutyId==createBy||isEventDuty==0;
					  //add by xiehuilin 20171009 公开私有操作 start
				 	if(editFlag&&isShowRange==1){
						if(item.isRange==0){
		 	    			 iHtml+='<img src="img/img112_01_03.png" alt="" class="event_detailedPiece_publicSuo event_privateSuo">';
		 	    		 }else{
		 	    		 	 iHtml+='<img src="img/publicSuo_09.png" alt="" class="event_detailedPiece_publicSuo event_detailedPiece_public_edit">';  
		 	    		 }
					 }

					 if(editFlag&&item.isCycle==1&&item.cycleState==0){
					 	iHtml+='<img src="img/img112_01_05.png" alt="" class="event_detailedPiece_publicSuo event_Icon2">';
					 }
					 if(editFlag&&item.isCycle==1&&item.cycleState==1){
					 	 iHtml+='<img src="img/icon100_13.png" alt="" class="event_detailedPiece_publicSuo event_detailedPiece_Icon2 frozen_icon2">'
					 }
					  if(item.rank==null){
						iHtml+=assessHtml;
					 }
					 iHtml+='<!--指派编辑小弹窗-->';
					 if(isShowRange==1){
					    iHtml+='<div class="event_detailedPiece_edit_popups">';
					  }else{
					    iHtml+='<div class="event_detailedPiece_edit_popupstwo">';	
					}
					 iHtml+='<div class="event_detailedPiece_btn_flex btn_bianji"><img src="img/btnulremove.png" class="event_detailedPiece_btn_popups_icon "><span>编辑</span></div>';
					 iHtml+='<div class="event_detailedPiece_btn_flex btn_zhipai"><img src="img/btnulisok.png" class="event_detailedPiece_btn_popups_icon"><span>指派</span></div>';
					 iHtml+='<div class="event_detailedPiece_edit_popups_edit_sanjiao"></div>';
					 iHtml+='</div>';
					 iHtml+='</div>';
					 iHtml+='</div>';
					 iHtml+='<div class="event_statusIcon2"><img src="img/advance.png" alt=""></div>';
					 iHtml+='</div>';
					 iHtml+='</li>';
	 	    		 break;
	 	    		 default:
	 	    		 var startTime=getFormatDateByLong(Number(item.startTime), "yyyy-MM-dd");
							startTime = startTime.replace(new RegExp("-","gm"),"/");
				    var starttimeHaoMiao = (new Date(startTime)).getTime(); //得到毫秒数
					var cTime=getFormatDateByLong(Number(new Date().getTime()), "yyyy-MM-dd");
							cTime = cTime.replace(new RegExp("-","gm"),"/");
							cTime= (new Date(cTime)).getTime(); //得到毫秒数
	 	    		 	iHtml+='<li class="event_detailedPiece">';
						iHtml+='<!--通用进展条样式-->';
						iHtml+='<div class="event_detailed_public">';
						if(item.showType==0||item.showType==1){
							iHtml+='<img src="img/zhouqi_icon_06.png" alt="" class="event_schedule_icon">';
						}
						if(item.showType==2){
						    iHtml+='<img src="img/zhouqi_bule_08.png" alt="" class="event_schedule_icon">';
						}
						iHtml+='<i class="event_schedule_line"></i>';
						iHtml+='</div>';
						iHtml+='<!--右边详细内容-->';
						iHtml+='<div class="event_detailedPiece_right">';
						iHtml+='<div class="event_detailedPiece_flex">';
						iHtml+='<i class="event_sanjiaoxu"></i>';
						iHtml+='<p class="event_detailedPiece_time BorderBottom">';
						iHtml+='<span><span>'+item.startStrTime+'</span>开始</span>';
						iHtml+='</p>';
						iHtml+='<p class="event_detailedPiece_time event_detailedPiece_time_right BorderBottom">';
						iHtml+='<span><span>'+item.fTimeStr+'</span>结束</span>';
						iHtml+='</p>';
						iHtml+='</div>';
						iHtml+='<div class="event_detailedPiece_content">';
						iHtml+='<div class="event_detailedPiece_jiange">';
						iHtml+='<img src="img/icon08.png" alt="" class="event_detailedPiece_content_img">';
						iHtml+='<span class="event_detailedPiece_content_txt">'+item.eiDesc+'</span>';
						iHtml+='</div>';
						iHtml+='</div>';
						iHtml+='<div class="jzsj" flag="false">';
						iHtml+='<p>执行人：<span>'+item.userName+'</span>';
						if(item.isAccept==2){
							iHtml+='<span class="event_detailedPiece_assignedfailure">(待接受)</span>';
						 }
						 if(item.state==2){

						 	iHtml+='<span class="event_detailedPiece_assignedfailure">指派失败</span>';
						 }
						iHtml+='<img src="img/img15.png" alt="" class="event_detailedPiece_content_sanjiao_hide"></p>';
						iHtml+='</div>';
						iHtml+='<div class="Event_listhidebox">';
						if(item.recordList.length>0){
						iHtml+='<div class="event_detailedPiece_track_block BorderTop">';
					 	iHtml+=trackShow(item)
					 	iHtml+='<div class="event_detailedPiece_track">';
					 	 }
						iHtml+='<!--评价星级-->';
						if(createBy==item.dutyId||isEventDuty==0){
								iHtml+='<div class="Event_icon BorderTop">';
							 	var editFlag=falg&&item.dutyId==createBy&&item.recordList.length<=0;
							 	var opFlag=item.dutyId==createBy||isEventDuty==0;
								  //add by xiehuilin 20171009 公开私有操作 start
							 	if(opFlag&&isShowRange==1){
									if(item.isRange==0){
					 	    			 iHtml+='<img src="img/img112_01_03.png" alt="" class="event_detailedPiece_publicSuo event_privateSuo">';
					 	    		 }else{
					 	    		 	 iHtml+='<img src="img/publicSuo_09.png" alt="" class="event_detailedPiece_publicSuo event_detailedPiece_public_edit">';  
					 	    		 }
								 }
								 //add by xiehuilin 20170925 校验是否有编辑权限
							 	if(editFlag&&isEventDuty==0&&(isToday(startTime)||cTime<starttimeHaoMiao)){
									iHtml+='<img src="img/edit_03.png" alt="" class="event_detailedPiece_publicSuo event_detailedPiece_Icon2 event_edit_icon">';
								  }
								 //add by xiehuilin 20171009 公开私有操作 end
								 //add by xiehuilin 20171009 周期显示 start
								 if(opFlag&&item.isCycle==1&&item.cycleState==0){
								 	    iHtml+='<img src="img/img112_01_05.png" alt="" class="event_detailedPiece_publicSuo event_Icon2">';
								 	}
								 if(opFlag&&item.isCycle==1&&item.cycleState==1){
								 	    iHtml+='<img src="img/icon100_13.png" alt="" class="event_detailedPiece_publicSuo event_detailedPiece_Icon2 frozen_icon2">'
							 	}
							 	//add by xiehuilin 20171009 周期显示 end
							 	iHtml+='<img src="img/img112_01_05.png" alt="" class="event_detailedPiece_publicSuo event_Icon2 frozen_icon">';
							 	iHtml+='<i class="eiId" style="display:none;">'+item.eiId+'</i>';
								iHtml+='<i class="ctId" style="display:none;">'+item.ctId+'</i><i class="isCycle" style="display:none;">'+item.isCycle+'</i><i class="dutyId" style="display:none;">'+item.dutyId+'</i>';
								if(createBy==item.dutyId&&falg&&isEventDuty==0){
									iHtml+='<button class="wancheng" flag="false">';
									iHtml+='<span>···</span>';
									iHtml+='</button>';
									iHtml+='<!--未完成编辑小弹窗-->';
									iHtml+='<div class="event_detailedPiece_edit_popups_end" style="display: none;"><i class="eiId" style="display:none;">'+item.eiId+'</i>';
									iHtml+='<i class="ctId" style="display:none;">'+item.ctId+'</i><i class="isCycle" style="display:none;">'+item.isCycle+'</i><i class="dutyId" style="display:none;">'+item.dutyId+'</i><i class="finishTime" style="display:none;">'+item.finishTime+'</i>';
								    iHtml+='<div class="event_btn_flex btn_zhongzhi"><img src="img/btnulremove.png" class="event_detailedPiece_btn_popups_icon"><span>终止</span></div>';
								    iHtml+='<div class="event_btn_flex btn_zuowan"><img src="img/img28.png" class="event_detailedPiece_btn_popups_icon"><span>做完了</span></div>';
								    iHtml+='<div class="event_btn_flex btn_jilu"><img src="img/track_icon2.png" class="event_detailedPiece_btn_popups_icon"><span>记录</span></div>';
								    iHtml+='<div class="event_detailedPiece_btn_popups_sanjiao"></div>';
								}else if(createBy==item.dutyId&&falg&&isEventDuty!==0){
									iHtml+='<button class="wancheng" flag="false">';
									iHtml+='<span>···</span>';
									iHtml+='</button>';
									iHtml+='<!--未完成编辑小弹窗-->';
									iHtml+='<div class="event_detailedPiece_edit_popups_end" style="display: none;"><i class="ctId" style="display:none;">'+item.ctId+'</i><i class="eiId" style="display:none;">'+item.eiId+'</i>';
									iHtml+='<i class="dutyId" style="display:none;">'+item.dutyId+'</i><i class="finishTime" style="display:none;">'+item.finishTime+'</i>';
								    iHtml+='<div class="event_btn_flex btn_zuowan"><img src="img/img28.png" class="event_detailedPiece_btn_popups_icon"><span>做完了</span></div>';
								    iHtml+='<div class="event_btn_flex btn_jilu"><img src="img/track_icon2.png" class="event_detailedPiece_btn_popups_icon"><span>记录</span></div>';
								    iHtml+='<div class="event_detailedPiece_btn_popups_sanjiao"></div>';
							}else if(isEventDuty==0&&falg&&createBy!==item.dutyId){
									if(item.isAccept!==2){
									iHtml+='<button class="wancheng" flag="false">';
									iHtml+='<span>···</span>';
									iHtml+='</button>';
									iHtml+='<!--未完成编辑小弹窗-->';
									iHtml+='<div class="event_detailedPiece_edit_popups_end" style="display: none;"><i class="eiId" style="display:none;">'+item.eiId+'</i>';
									iHtml+='<i class="ctId" style="display:none;">'+item.ctId+'</i><i class="isCycle" style="display:none;">'+item.isCycle+'</i><i class="dutyId" style="display:none;">'+item.dutyId+'</i><i class="finishTime" style="display:none;">'+item.finishTime+'</i>';
								    iHtml+='<div class="event_btn_flex btn_zhongzhi"><img src="img/btnulremove.png" class="event_detailedPiece_btn_popups_icon"><span>终止</span></div>';
								    iHtml+='<div class="event_detailedPiece_btn_popups_sanjiao"></div>';
								  }
								}
								iHtml+='</div>';
								iHtml+='<!--指派编辑小弹窗-->';
								if(isShowRange==1){
								    iHtml+='<div class="event_detailedPiece_edit_popups">';
								  }else{
								    iHtml+='<div class="event_detailedPiece_edit_popupstwo">';	
								}
								iHtml+='<div class="event_detailedPiece_btn_flex btn_bianji"><img src="img/btnulremove.png" class="event_detailedPiece_btn_popups_icon"><span>编辑</span></div>';
								iHtml+='<div class="event_detailedPiece_btn_flex btn_zhipai"><img src="img/btnulisok.png" class="event_detailedPiece_btn_popups_icon"><span>指派</span></div>';
								iHtml+='<div class="event_detailedPiece_edit_popups_edit_sanjiao"></div>';
								iHtml+='</div>';
								iHtml+='</div>';
								iHtml+='</div>';
							}	
						iHtml+='</div>';
						iHtml+='</li>';
	 	    		 break;
	 	    	} 
	 	    } 
		
			return iHtml
	
	 },
	 

	  // add by xiehuilin 2017/06/19 事件页面元素控制开关
	 hasShow:function(json){
	 	//$(".Event_tab_con").show();
		// add by xiehuilin 2017/06/19 事件是否结束,开启查看事件报告\评论开关
		if(json.comments==null){
			$(".Event_footer_btnone").css("display","block");
			$(".Event_footer_btntwo").css("display","block");
			$(".Event_footer1").css("display","none");
			$(".Event_footer_btnfour").css("display","none");
		}else{
			$(".Event_footer_btnone").css("display","none");
			$(".Event_footer_btntwo").css("display","none");
			$(".Event_footer1").css("display","block");
			$(".Event_footer_btnfour").css("display","block");
			$(".Event_footer1").find("span").find("i").html('('+json.comments+')');
			
		}
		switch(json.isEventDuty){
			case 0:
				if(json.users.length>0){
					$(".Event_tab_con").show();
				 }
			    $(".Event_footer_btntwo").show();
			    if(json.isend==1){
				    $(".Event_footer").hide();
				    $(".Event_footer1").show();
				   
				}else{
					$(".Event_footer").show();
				}
			break;
			case 1:
				$(".Event_tab_con").hide();
				$(".Event_footer").hide();
				if(source!=1){
					$(".Eventtwo_click").show();
					$(".Event_head_inner").addClass("Event_head_inner_two");
			     	$(".start_inner").css("padding-top","1rem");
		     	}
		     	$(".Event_footer_btntwo").hide();
		     	if(json.isend==1){
				    $(".Event_footer").hide();
				    $(".Event_footer1").show();
				}
			break;
			case 2:

				if(json.users.length>0){
					$(".Event_tab_con").show();
				 }
		     	if(json.isend==1){
				    $(".Event_footer").hide();
				    $(".Event_footer1").show();
				}
			break;
			case 3:
				if(json.users.length>0){
					$(".Event_tab_con").show();
				 }
		     	if(json.isend==1){
				    $(".Event_footer").hide();
				    $(".Event_footer1").show();
				}else{
					$(".Event_footer").show();
				}
			break;
			default:
		}
	 }
}

//	edit by zhengjunting 2017/06/14 点击头像
$(".Event_tab ").on("click","li",function(){
		$(".Event_tab li").find("p").removeClass("Event_tab_current");
		$(this).find("p").addClass("Event_tab_current");
			page=1;
			$(".Event_tab li").find("p").removeClass("Event_tab_current");
			$(this).find("p").addClass("Event_tab_current");
			 userId=$(this).find(".userId ").html();
			var tc=$(this).find("p").html();
			 type=tc=="全部"?2:0;
			
		    userId=isnull(userId)?localStorage.getItem("team_userId"):userId;

			  $(".item_list").html("");
			  localStorage.setItem("team_temp_userId",userId);
			  $(".Event_tab").find("li").each(function(index){
			  	var fuserId=$(".Event_tab").find("li").eq(index).find(".userId").html();
			  	if(userId==fuserId){
			  		$("#"+fuserId).css("display","none");
			  	}
			  });


			  init.data(userId,type);
});

var oper={
	 // add by xiehuilin 2017/06/19  事项响应操作
	 irespond:function(){
	$(".Event_btnokbox .Event_btnsenket").click(function(){
		 finishTime="";
		 dutyId="";
		 eiId="";
	   	if($(this).parent(".Event_btnokbox").find(".Event_btnul").hasClass("animatbtnul")){
	   		$(this).parent(".Event_btnokbox").find(".Event_btnul").removeClass("animatbtnul");
	   		$(this).parent(".Event_btnokbox").find(".Event_btnul").css({"display":"block","right":"-12rem"});	    		   		
	   	}else{
	   		$(this).parent(".Event_btnokbox").find(".Event_btnul").addClass("animatbtnul");
	   		$(this).parent(".Event_btnokbox").find(".Event_btnul").css({"display":"block","right":"1.8rem"});
	   	};
	   	return false;
   	});
	},
}

//跳转添加事项页面
$(".Event_footer").click(function(){
	localStorage.setItem("team_type",1);
	isLight==0?_g("add_items.html"):_g("add_items_qq.html");
});



//add by xiehuilin 2017/06/19  打分 
var ranks={
	
	update:function(rank,eiId,dutyId,rankThis){
		var eId=localStorage.getItem("team_eId");
		var createBy= localStorage.getItem("team_userId");
		var param={"eiId":eiId,"rank":rank,"eId":eId,"createBy":createBy,"dutyId":dutyId};
    	var url=dataLink+"item/updateEventItem";
    	 _asyn(url,param,"POST",ranks.Ok,rank,rankThis);
	},
	Ok:function(json,code,count,rankThis){
		if(json.msg=="success"){
			if(count==5||count==1){
				window.location.reload();//刷新当前页面	
			}else{
			$(".start_box").hide();
			$(".Event_alert_check").hide();
			$(".Event_alert_bg").hide();
			$(".event_detailedPiece_start").show();
			$(rankThis).parent().find(".yanshou").remove();
			$(rankThis).parent().find(".tijiao").hide();
			$(rankThis).parent().find(".start_box").hide();
			var rshtml='<div class="event_detailedPiece_start BorderTop"><span>验收评价：</span>';
			    rshtml+='<div class="event_detailedPiece_start_quantity">';
			for(var i=0;i<count;i++){
				rshtml+='<img src="img/start01.png" alt="">';
			}
			for(var i=0;i<5-count;i++){
				rshtml+='<img src="img/start02.png" alt="">';
			}
			 rshtml+='</div></div>';
			 $(rankThis).parent().parent().find(".showRank").html(rshtml);
			}	
		}
	}
}

//跳转修改事项提醒时间
$(".Eventtwo_click").click(function(){
	window.location.href="reminder.html";
	return false;
});
//add by xiehuilin 2017/07/11 分页
$(window).scroll(function () {
    var bot =($(".item_list li").height())*3; 
        if (verdict) {
            if ((bot + $(window).scrollTop()) >= ($("body").height() - $(window).height())) {
                verdict=false;
                page+=1;
               init.data(userId,type);
            }
        }
});

// add by xiehuilin 2017/07/11 跳转奖惩规则页
$(".Event_reward").click(function(){
	isEventDuty==0&&isRule==0?_g("rewardPenalty.html"):_g("rewardTab.html");
});