var finishTime="";
var dutyId="";
var eiId="";
var userId="";
var type="";
//add  by xiehuilin 2017/07/10 V0.0.02 分页参数
var page=1;
var verdict=true;
$(function(){
//	点击弹窗灰色区域弹窗消失	
	$(".Event_alert_bg").click(function(){
		$(".Event_alert_bg").hide();
		$(".Event_alerttwo").hide();
		$(".Event_alert").hide();
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
	//add by lixiaobin 2017/08/09  点击截止时间 隐藏 跟踪完成内容
	$(".jzsj").attr("flage","false");
	$(".jzsj").click(function(){
//		console.log($(this).attr("flage"));
		if($(this).attr("flage") == "false"){
			$(this).prev().find(".Event_centtimebox").css("display","none");
			$(this).prev().find(".Event_icon").css("display","none");
			$(this).css("background-image","url(img/yincang.png)");
			$(this).attr("flage","true")
		}else if($(this).attr("flage") == "true"){
			$(this).prev().find(".Event_centtimebox").css("display","block");
			$(this).prev().find(".Event_icon").css("display","block");
			$(this).css("background-image","url(img/xianshi.png)");
			$(this).attr("flage","false")
		}
	})
	 //add by lixiaobin 2017/08/01 点击阴影区影藏弹窗
	$(".Event_alert_bg").click(function(){
		$(".Event_alert_bg").css("display","none");
		$(".Event_alerttwo").css("display","none");
		$(".Event_alert").css("display","none");
	})
	$(".cancel").click(function(){
		$(".event_summary").css("display","none");
		$(".event_summary1").css("display","none");
	})
	//	edit by zhengjunting 2017/06/14 点击头像
	$(".Event_tab li").click(function(){
		$(".Event_tab li").find("p").removeClass("Event_tab_current");
		$(this).find("p").addClass("Event_tab_current");
	});
//	//	edit by lixiaobin 2017/07/11  点击星星按钮评星
//	$(".Event_start").click(function(){
//		event.stopPropagation();
//		var imgs = $(this).find("img");
//		for(var i = 0;i<imgs.length;i++){
//			imgs[i].flag = false;
//	        imgs[i].index = i;
//	         imgs[i].onclick = function(){
//	         	for(var j = 0;j<imgs.length;j++){
//	         		imgs[j].src = "img/start02.png";
//	         		imgs[j].flag = false;
//	         	}
//	         	for(var j = 0;j<=this.index;j++){
//	         		imgs[j].src = "img/start01.png";
//	                imgs[j].flag = true;
//	         	}
//	         }
//		}	
//	})
	//	edit by lixiaobin 2017/07/11  点击其他区域隐藏
	$("body").click(function(){
		$(".wancheng_icon").hide();
		$('.wancheng').attr("flag","false");
		$(".Event_start").hide();
		$(".tijiao").hide();
		$(".pinjia").show();
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
		$(".Event_footer_btnthree").click(function(){
			_g("comment.html");
		});
//edit by zhengjunting 2017/06/14   时间插件
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

var page=1;
var verdict=true;
//add by xiehuilin 事项是否全部完成 0 否 1 是
var fItem=0;
// add by xiehuilin 2017/06/16 初始化事件详情
var isEventDuty="";
// add by xiehuilin 2017/07/11 是否有奖惩规则
var isRule="";
localStorage.setItem("team_userId",2);
localStorage.setItem("team_eId",1);
var createBy= localStorage.getItem("team_userId");
var init={
	data:function(){
		var eId=localStorage.getItem("team_eId");
		var param={"userId":userId,"eId":eId,"createBy":createBy,"pageNum":page};
    	var url=dataLink+"event/timeManag";
    	_asyn(url,param,"get",init.Ok,type);
	},
	Ok:function(json, code,type){
		if(json.msg=='success'){
			console.info(json);
			isEventDuty=json.isEventDuty;
			isRule=json.isRule;
			isEventDuty==0?localStorage.setItem("team_roleType",1):localStorage.setItem("team_roleType","");
			if(type==1){
				e.show(json);
				e.dShow(json.users)
			}
			e.iShow(json.item);
		}else{
			alert("参数异常");
		}
	}
}
// add by xiehuilin 2017/06/16 事项列表	
var e={
	 show:function(json){
	 		var et=json.event;
	 		//add by xiehuilin 2017/06/19 调用页面元素开关方法
		     e.hasShow(json);
		     eId=et.eId;
		     aaa=et.startTime;
		     localStorage.setItem("team_sTime",Time(et.startTime,11));
		     localStorage.setItem("team_saTime",et.startTime);
		     localStorage.setItem("team_eTime",Time(et.endTime,11));
			$(".event_name").html(et.name);
			$(".Event_head_font").html("目标："+et.target);
			
			$(".Event_right_num i").html(et.compRate);
			$(".Event_head_time").html(et.sTime+"-"+et.eTime);
			$(".start_hour").html(dateHour(et.startTime,2));  
			$(".start_date").html(et.sTime);  
			$(".end_hour").html(dateHour(et.endTime,2));  
			$(".end_date").html(et.eTime); 
			  
	 },
	 /* add by xiehuilin 2017/06/19 事项内容显示  
	  *	@param items  事项
	  * @param isEventDuty  0 创建人 1责任人  2 (创建人、责任人)
	 */
	 iShow:function(items){
	 	 verdict=items.length>0?true:false;
	 	for(var x=0;x<items.length;x++){
	 		var item=items[x];
	 		$(".Event_listketis").append(e.itemData(item));
	 		
	 	   fItem=item.state==3||item.state==4?1:0;
	 	}
	 	//add  by xiehuilin 2017/07/11 调用标记完成或异常终止方法
	 	finish_stop();
	 	//add  by xiehuilin 2017/07/11  调用评价方法
	 	assess();
	 	//add  by xiehuilin 2017/07/11  调用打星方法
	 	mark();
	 	//add  by xiehuilin 2017/07/11  调用确认打分方法
	 	confirmMake();
	 	fItem==1?$(".start_inner").removeClass("start_innertwo"):$(".start_inner").addClass("start_innertwo");
	 	oper.irespond();
		//edit by lixiaobin 2017/07/11   标记完成
			$(".bjwc").click(function(){
				 finishTime= $(this).find("span").html();
				 dutyId=$(this).find("em").html();
				 eiId=$(this).find("i").html();
				$(".Event_alert_bg").css("display","block");
				$(".Event_alerttwo").css("display","block");
			})
//			edit by lixiaobin 2017/07/11	取消返回
			$(".Event_btn_no").click(function(){
				$(".Event_alert_bg").css("display","none");
				$(".Event_alerttwo").css("display","none");
				$(".Event_alert").css("display","none");
			})
			//edit by lixiaobin 2017/07/11	标记异常
			$(".yczz").click(function(){
				 eiId=$(this).find("i").html();
				$(".Event_alert_bg").css("display","block");
				$(".Event_alert").css("display","block");
			})
			//  edit by zyting 2017/06/14 点击异常取消
			$(document).click(function(){
			    $(".Event_btnul").hide();
			});
		 show_time();
		 
	 },
	  // add by xiehuilin 2017/06/19 事项责任人显示
	 dShow:function(users){
	 	for(var x=0;x<users.length;x++){
	 		var user=users[x];
	 		var pice=user.headUrl==null?"img/img03.png":(imageHeadSrc+user.headUrl);
	 		var dhtml='<li><p class="userId" style="display: none;">'+user.userId+'</p>';
	 			dhtml+='<img src="'+pice+'" alt="">';
	 			dhtml+='<p>'+user.userName+'</p></li>';
	 			$(".Event_tab").append(dhtml);
	 			oper.cHead();
	 	}
	 },
	/* add by xiehuilin 2017/06/19 事项内容数据封装  
	  *	@param items  事项
	  * @param isEventDuty  0 创建人 1责任人  2 (创建人、责任人)
	 */
	 itemData:function(item){
	 	// add by xiehuilin 2017/07/11 年月
	 	var str=item.fTime;
	 	var maxleng=str.length>7?6:2
	 	var yearM=str.substring(0,maxleng);
	 	// add by xiehuilin 2017/07/11 时间后时分
	 	 var hourM=str.substring(str.length,maxleng);
	 	 var temp_userId=localStorage.getItem("team_temp_userId");
	 	 var dutyId=item.dutyId;
	 	 var remark=item.remark==null?"":item.remark;
	 	 var  span=temp_userId!=dutyId?'<span class="Event_duty">负责人：</span><span class="Event_duty1">'+item.userName+'</span>':"";
	 	 var   divHtml='<div class="Event_icon">';
	 	 	   divHtml+='<i class="eiId" style="display: none;">'+item.eiId+'</i>';
	 	 	   if(item.rank!=null){
			 	    divHtml+='<div class="zhuguanpj" style="display: block;">';
			 		divHtml+='<span>主管评价：</span>';
			 		//add by xiehuilin 2017/07/12 会显打分值
			 		for(var x=0;x<item.rank;x++){
			 			divHtml+='<img src="img/start01.png" alt="">';
			 		}
			 		for(var y=0;y<5-item.rank;y++){
			 			divHtml+='<img src="img/start02.png" alt="">';
			 		}
			 		iHtml=+'</div>';
			 	}else{
			 	   divHtml+='<div class="zhuguanpj">';
				   divHtml+='<span>主管评价：</span>';
				   divHtml+='</div>';	
				   divHtml+='<button class="tijiao" style="display: none;">';
				   divHtml+='<i></i>提交';
				   divHtml+='<div class="Event_start" style="display: none;">';
				   divHtml+='<img src="img/start01.png" alt=""><img src="img/start01.png" alt="">';
				   divHtml+='<img src="img/start01.png" alt=""><img src="img/start02.png" alt=""><img src="img/start02.png" alt="">';
				   divHtml+='</div>';
				   divHtml+='</button>';
				   divHtml+='<button class="pinjia"><i></i>评价</button>';
			    }
			   divHtml+='</div>'
			var div=isEventDuty==0||isEventDuty==2?divHtml:"";
	 	    var iHtml="";
	 	switch(item.state)
			{
			case 3:
			   iHtml+='<li class="li2">';
			   iHtml+='<div class="Event_time">';
			   iHtml+='<i class="sanjiao1"></i>';
			   iHtml+='<span>'+yearM+'</span><br>';
			   iHtml+='<span>'+hourM+'</span>';
			   iHtml+='</div>';
			   iHtml+='<div class="Event_borderleft Event_borderleftis"></div>';
			   iHtml+='<div class="Event_borderleftsen"></div>';
			   iHtml+='<div class="Event_listcentenbox clearfix ">';
			   iHtml+='<div class="Event_listcenten">';
			   iHtml+='<i class="sanjiao"></i>';
			   iHtml+='<div class="Event_miaoshu">';
			   iHtml+='<img src="img/miaoshu_icon.png" alt="">';
			   iHtml+='<span class="span1">'+item.eiDesc+'</span>';
			   iHtml+=span;
			   iHtml+='</div>';
			   iHtml+='<div class="Event_centtimebox">';
			   iHtml+='<img class="biazhi" src="img/img24.png" alt="">';
			   iHtml+='<div class="Event_centtime">';
			   iHtml+='<img src="img/duihao_icon.png" alt=""><span>'+item.uTime+'</span><i>由<b>'+item.userName+'</b>完成</i>';
			   iHtml+='<p>'+remark+'</p>';
			   iHtml+='</div>';
			   iHtml+='</div>';
			   iHtml+=div;
			   iHtml+='</div></div></li>';
			  break;
			  case 4:
			  iHtml+='<li class="li1">';
			  iHtml+='<div class="Event_time">';
			  iHtml+='<i class="sanjiao1"></i>';
			  iHtml+='<span>'+yearM+'</span><br>';
			  iHtml+='<span>'+hourM+'</span>';
			  iHtml+='</div>';
			  iHtml+='<div class="Event_borderleft Event_borderleftis"></div>';
			  iHtml+='<div class="Event_borderleftsen"></div>';
			  iHtml+='<div class="Event_listcentenbox clearfix ">';
			  iHtml+='<div class="Event_listcenten">';
			  iHtml+='<i class="sanjiao"></i>';
			  iHtml+='<div class="Event_miaoshu">';
			  iHtml+='<img src="img/miaoshu_icon.png" alt="">';
			  iHtml+='<span class="span1">'+item.eiDesc+'</span>';
			  iHtml+=span;
			  iHtml+='</div>';
			  iHtml+='<div class="Event_centtimebox">';
			  iHtml+='<img class="biazhi" src="img/img23.png" alt="">';
			  iHtml+='<div class="Event_centtime">';
			  iHtml+='<img src="img/duihao_icon.png" alt=""><span>07-20</span><span>'+item.uTime+'</span><i>由<b>'+item.userName+'</b>完成</i>';
			  iHtml+='<p>'+remark+'</p>';
			  iHtml+='</div>';
			  iHtml+='</div>';
			  iHtml+=div;
			  iHtml+='</div>';
			  iHtml+='</div>';
			  iHtml+='</li>';
			  break;
			  case 5:
			   iHtml+='<li class="li5">';
			   iHtml+='<div class="Event_time">';
			   iHtml+='<i class="sanjiao1"></i>';
			   iHtml+='<span>'+yearM+'</span><br>';
			   iHtml+='<span>'+hourM+'</span>';
			   iHtml+='</div>';
			   iHtml+='<div class="Event_borderleft Event_borderleftis"></div>';
			   iHtml+='<div class="Event_borderleftsen"></div>';
			   iHtml+='<div class="Event_listcentenbox clearfix ">';
			   iHtml+='<div class="Event_listcenten">';
			   iHtml+='<i class="sanjiao"></i>';
			   iHtml+='<div class="Event_miaoshu">';
			   iHtml+='<img src="img/miaoshu_icon.png" alt="">';
			   iHtml+='<span class="span1">'+item.eiDesc+'</span>';
			   iHtml+=span;
			   iHtml+='</div>';
			   iHtml+='<div class="Event_centtimebox">';
			   iHtml+='<div class="Event_centtime">';
			   iHtml+='<img src="img/yichang_icon.png" alt="">';
			   iHtml+='<p class="yichang">'+remark+'</p>';
			   iHtml+='</div>';
			   iHtml+='</div>';
			   iHtml+='</div></div></li>';
			   iHtml+='';
			  break;
			default:
			   iHtml+='<li class="li4">';
			   iHtml+='<div class="Event_time">';
			   iHtml+='<i class="sanjiao1"></i>';
			   iHtml+='<span>'+yearM+'</span><br>';
			   iHtml+='<span>'+hourM+'</span>';
			   iHtml+='</div>';
			   iHtml+='<div class="Event_borderleft Event_borderleftis"></div>';
			   iHtml+='<div class="Event_borderleftsen"></div>';
			   iHtml+='<div class="Event_listcentenbox clearfix ">';
			   iHtml+='<div class="Event_listcenten">';
			   iHtml+='<i class="sanjiao"></i>';
			   iHtml+='<div class="Event_miaoshu">';
			   iHtml+='<img src="img/miaoshu_icon.png" alt="" />';
			   iHtml+='<span class="span1">'+item.eiDesc+'</span>';
			   iHtml+=span;
			   iHtml+='</div>';
			   if(item.isEventItemDuty==2){
			   	   iHtml+='<div class="Event_icon">';
				   iHtml+='<button class="wancheng"><i></i>· · ·';
				   iHtml+='<div class="wancheng_icon">';
				   iHtml+='<span class="bjwc"><img src="img/btnulisok.png" alt="" />';
				   iHtml+='<span style="display:none;">'+item.finishTime+'</span>';
				   iHtml+='<em style="display:none;">'+item.dutyId+'</em>';
				   iHtml+='<i style="display:none;">'+item.eiId+'</i> ';
				   iHtml+='<b>标记完成</b></span>';
				   iHtml+='<span class="yczz"><img src="img/btnulremove.png" alt="" />';
				    iHtml+='<i style="display:none;">'+item.eiId+'</i> ';
				   iHtml+='<b>异常终止</b></span></div>';
				   iHtml+='</button>';
				   iHtml+='</div></div></div></li> ';
			   }else if(item.isEventItemDuty==1){
				   iHtml+='<div class="Event_icon">';
				   iHtml+='<div class="wancheng_icon">';
				   iHtml+='<span  class="bjwc"><img src="img/btnulisok.png" alt="" />';
				   iHtml+='<span style="display:none;">'+item.finishTime+'</span>';
				   iHtml+='<em style="display:none;">'+item.dutyId+'</em>';
				   iHtml+='<i style="display:none;">'+item.eiId+'</i> ';
				   iHtml+='<i>标记完成</i></span>';
				   iHtml+='</div></div></li> ';
			   }else if(item.isEventItemDuty==0){
			   	   iHtml+='<div class="Event_icon">';
				   iHtml+='<div class="wancheng_icon">';
				   iHtml+='<span class="yczz"><img src="img/btnulremove.png" alt="" />';
				    iHtml+='<i style="display:none;">'+item.eiId+'</i> ';
				   iHtml+='<i>异常终止</i></span></div>';
				   iHtml+='</div></div></li> ';
			   }
			  
			}
	
			return iHtml;
	
	 },
	 

	  // add by xiehuilin 2017/06/19 事件页面元素控制开关
	 hasShow:function(json){
	 	//$(".Event_tab_con").show();
		// add by xiehuilin 2017/06/19 事件是否结束,开启查看事件报告\评论开关
		if(json.comments==null){
			$(".Event_footer_btnone").css("display","block");
			$(".Event_footer_btntwo").css("display","block");
			$(".Event_footer_btnthree").css("display","none");
			$(".Event_footer_btnfour").css("display","none");
		}else{
			$(".Event_footer_btnone").css("display","none");
			$(".Event_footer_btntwo").css("display","none");
			$(".Event_footer_btnthree").css("display","block");
			$(".Event_footer_btnfour").css("display","block");
			$(".Event_footer_btnfour").html('('+json.comments+')');
			
		}
		switch(json.isEventDuty){
			case 0:
				if(json.users.length>0){
					$(".Event_tab_con").show();
				 }
			    $(".Event_footer_btntwo").show();
			    if(json.isend==1){
				    $(".Event_footer").show();
				    $(".Event_footer_btntwo").hide();
				   
				}
			break;
			case 1:
				$(".Event_tab_con").hide();
				$(".Event_footer").hide();
				$(".Eventtwo_click").show();
				$(".Event_head_inner").addClass("Event_head_inner_two");
		     	$(".start_inner").css("padding-top","1rem");
		     	$(".Event_footer_btntwo").hide();
		     	if(json.isend==1){
				   $(".Event_footer_btntwo").hide();
				   $(".Event_footer").show();
				}
			break;
			default:
		}
	 }
}


var oper={
		//	edit by zhengjunting 2017/06/14 点击头像
	 cHead:function(){
		$(".Event_tab li").click(function(){
			$(".Event_tab li").find("p").removeClass("Event_tab_current");
			$(this).find("p").addClass("Event_tab_current");
			 userId=$(this).find(".userId ").html();
			 type=0;
		    userId=isnull(userId)?localStorage.getItem("team_userId"):userId;
			  $(".Event_listketis").html("");
			  localStorage.setItem("team_temp_userId",userId);
			  init.data(userId,type);
		});
	 },
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
	localStorage.setItem("team_type",0);
	window.location.href="add_items.html";
});



//add by xiehuilin 2017/06/19  打分 
var ranks={
	update:function(rank,eiId){
		var eId=localStorage.getItem("team_eId");
		var param={"eiId":eiId,"rank":rank,"eId":eId};
    	var url=dataLink+"item/updateEventItem";
    	 _asyn(url,param,"POST",ranks.Ok,rank);
	},
	Ok:function(json,code,count){
		console.info(json);
		if(json.msg=="success"){
			$(".tijiao").parent().find(".zhuguanpj").css("display","block");
			for(var i=0;i<count;i++){
				$(".tijiao").parent().find(".zhuguanpj").append('<img src="img/start01.png" alt="" />');
			}
			for(var i=0;i<5-count;i++){
				$(".tijiao").parent().find(".zhuguanpj").append('<img src="img/start02.png" alt="" />');
			 }
		}	
	}
}

//事项完成
$(".Event_btn_ok").click(function(){
	var via=true;
	var remark=$(".Event_textarea").val();
	if (!centen(remark,0,200)) {
		$(".Event_alerttwo").find("p").show();
		setTimeout(function(){$(".Event_alerttwo").find("p").hide();},2000);
		via=false;
	}
	if (via) {
		complete();
	}
});
function  complete(){
	var eId=localStorage.getItem("team_eId");
	var userId=localStorage.getItem("team_userId");
	var userTime=$("#appDateTime").val();//用户完成时间
	var remark=$(".Event_textarea").val();
	var state=3;
	//根据完成时间和当前时间进行判断是否是延期完成
	if (finishTime<new Date().getTime()) {
		state=4;
	}
	
	var data={"createBy":userId,"state":state,"eiId":eiId,"dutyId":dutyId,"eId":eId,"remark":remark}
	$.ajax({
		  type : "GET",
	      url:dataLink+"item/updateItemState",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  window.location.href="eventdetailsone.html";
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
	//var eiId=localStorage.getItem("team_eiId");
	var state=5;
	var remark=$(".Event_alert_textarea").val();
	var data={"createBy":userId,"state":state,"eiId":eiId,"dutyId":userId,"eId":eId,"remark":remark}
	$.ajax({
		  type : "GET",
	      url:dataLink+"item/updateItemState",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  window.location.href="eventdetailsone.html";
	    	  }
	      },  
	      timeout:3000  
	 });
}


//跳转修改事项提醒时间
$(".Eventtwo_click").click(function(){
	window.location.href="reminder.html";
	return false;
});

// add by xiehuilin 2017/07/11 完成异常终止操作
function  finish_stop(){
	//	edit by lixiaobin 2017/07/11  点击三个小点按钮弹出完成按钮
	$('.wancheng').attr("flag","false");
	var wancheng = $(".wancheng");
	for(var i = 0;i<wancheng.length;i++){
		wancheng[i].index = i;
        wancheng[i].onclick = function(event){
        	$(this).siblings().find(".wancheng_icon").css("background","red");
        	var flag = $(this).attr("flag");
         	event.stopPropagation();
         	$('.wancheng').find(".wancheng_icon").css("display","none");
         	$('.wancheng').attr("flag","false");
	     	if(flag =="true"){
	    	   $(this).find(".wancheng_icon").css("display","none");
	    	   this.setAttribute("flag","false");
		    }else{
		    	$(this).find(".wancheng_icon").css("display","block");
	    	    this.setAttribute("flag","true");
		    }
       }
	}
}


//add by xiehuilin 2017/07/11 分页
$(window).scroll(function () {
    var bot =($(".Event_listketis li").height())*3; 
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

//add by xiehuilin 2017/07/11 评价
function assess(){
	$(".pinjia").bind("click",function(){
		event.stopPropagation();
		$(".tijiao").css("display","none");
		$(".pinjia").css("display","block");
	    $(this).css("display","none");
		$(this).prev().css("display","block");
		$(this).prev().find(".Event_start").css("display","block");
		var imgs = $(this).prev().find(".Event_start img");
//	edit by lixiaobin 2017/07/11  点击星星按钮评星
		for(var i = 0;i<imgs.length;i++){
			imgs[i].flag = false;
	        imgs[i].index = i;
	         imgs[i].onclick = function(){
	         	event.stopPropagation();
	         	for(var j = 0;j<imgs.length;j++){
	         		imgs[j].src = "img/start02.png";
	         		imgs[j].flag = false;
	         	}
	         	for(var j = 0;j<=this.index;j++){
	         		imgs[j].src = "img/start01.png";
	                imgs[j].flag = true;
	         	}
	         }
		}	
	})
}
//add by xiehuilin 2017/07/11 打分
//function mark(){
//
//	//	edit by lixiaobin 2017/07/11  点击星星按钮评星
//	$(".Event_start").click(function(){
//		event.stopPropagation();
//		var imgs = $(this).find("img");
//		for(var i = 0;i<imgs.length;i++){
//			imgs[i].flag = false;
//	        imgs[i].index = i;
//	         imgs[i].onclick = function(){
//	         	for(var j = 0;j<imgs.length;j++){
//	         		imgs[j].src = "img/start02.png";
//	         		imgs[j].flag = false;
//	         	}
//	         	for(var j = 0;j<=this.index;j++){
//	         		imgs[j].src = "img/start01.png";
//	                imgs[j].flag = true;
//	         	}
//	         }
//		}	
//	})
//}

// add by xiehuilin 2017/07/12 确认打分
function confirmMake(){
	//	edit by lixiaobin 2017/07/11  点击提交按钮 提交星级
	$(".tijiao").on("click",function(){
		var count=0;
		$(this).find(".Event_start").find("img").each(function(){
			var arr = [];
			arr.push($(this).attr("src"));
			for(var i=0;i<arr.length;i++){
				if(arr[i] == "img/start01.png"){
					count++;
				}
			}
			
		})
		var eiId=$(this).parent().find(".eiId").html();
		ranks.update(count,eiId);
		
		/*$(this).parent().find(".zhuguanpj").css("display","block");
		for(var i=0;i<count;i++){
			$(this).parent().find(".zhuguanpj").append('<img src="img/start01.png" alt="" />');
		}
		for(var i=0;i<5-count;i++){
			$(this).parent().find(".zhuguanpj").append('<img src="img/start02.png" alt="" />');
		}*/
		//$(this).parent().find(".tijiao").css("display","none");
		//$(this).parent().find(".pinjia").css("display","none");
		
	})
}


