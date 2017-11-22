
//add  by xiehuilin 2017/07/10 V0.0.02 分页参数
var page=1;
var verdict=true;
var rpType=1;
var dutyName="";
//add by xiehuilin 20170923 
var eiId="";
$(function(){
	//	edit by zhengjunting 2017/9/19 惩罚
//$(".content").on("click",".reward",function(){
//	console.info($(this).html());
//	eiId=$(this).find(".eiId").html();
//	var rplMoney=$(this).find(".rplMoney").html();
//	$(".Index_reward_money").find("i").html(rplMoney);
//	$(".Index_reward_people").find("i").html(dutyName);
//	$(".Event_alert_bg").show();
////		奖励弹窗
//		$(".Index_reward").show();
//});
//	edit by zhengjunting 2017/9/19 惩罚
//$(".content").on("click",".punish",function(){
//	$(".Event_alert_bg").show();
////		惩罚弹窗
//		$(".Index_rewardtwo").show();
//});
//	//	点击奖励/惩罚弹窗的取消按钮
//	$(".Index_reward_btn").on("click",".Index_accept_btnone",function(){
//   	$(".Event_alert_bg").hide();
//   	$(".Index_rewardtwo").hide();
//   	$(".Index_reward").hide();
// });
	$(".fa").click(function(){
		page=1;
		rpType=0;
		rewardPunishment.list(rpType);
		$(".chufa").css("display","block");
		$(".jiangli").css("display","none");
		$(this).addClass("click");
		$(".jiang ").removeClass("click");
		 $(".relust_chufa").html("");

	});
	$(".jiang ").click(function(){
		page=1;
		rpType=1;
		rewardPunishment.list(rpType);
		$(".chufa").css("display","none");
		$(".jiangli").css("display","block");
		$(this).addClass("click");
		$(".fa ").removeClass("click");
		 $(".relust_jiangli").html("");

	});
	
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
	
	//add by xiehuilin 2017/07/10 调用奖惩规则方法
	rewardPunishment.rule();
	rewardPunishment.list(1);

	//add  by xiehuilin 20170925 奖励确定
//$(".Index_reward").on("click",".Index_reward_btntwo",function(){
//	alert(222);
// });
})

var rewardPunishment={
	//add  by xiehuilin 2017/07/10 奖惩规则封装数据
	 rule:function(){
		var eId=localStorage.getItem("team_eId");
		var param={"eId":eId};
		var url=dataLink+"ap/getRuleList";
    	_asyn(url,param,"GET",rewardPunishment.ok);
	 },
	 ok:function(json,code){
	 	
	 	if(json.msg=="success"){
	 		 dutyName=json.dutyName;
	 		var items=json.ruleList;
	 		for(var x=0;x<items.length;x++){
	 			var item=items[x];
	 			rewardPunishment.show(item);
	 		}
	 	}
	 },
	 //add  by xiehuilin 2017/07/10 奖惩规则数据渲染
	 show:function(item){
	 	var html='';
	 	switch (item.rpType) {
	    case 0:

	     	html+=item.rpCategory==1?' <p>主管评价一星，罚<span>'+item.rpMoney+'</span>元</p>':'<p>进度滞后完成，罚<span>'+item.rpMoney+'</span>元</p>';
	     	$(".list_penalty").find(".right").append(html);
	      break;
	    case 1:
	        html+='<p>主管评价五星，奖<span>'+item.rpMoney+'</span>元</p>';
	        $(".list_reward").find(".right").append(html);
	      break;
	    default:
	       
		}
	 },
	 //add  by xiehuilin 2017/07/10 奖惩名单封装数据
	 list:function(rpType){
	 	var eId=localStorage.getItem("team_eId");
		var param={"eId":eId,"rpType":rpType,"pageNum":page};
		var url=dataLink+"ap/getList";
    	_asyn(url,param,"GET",rewardPunishment.list_ok,rpType);
	 },
	 list_ok:function(json,code,rpType){
	 	console.info(json);
	 	if(json.msg=="success"){
	 		var penaltyList=json.penaltyList;
	 		 verdict=penaltyList.length>0?true:false;
	 		for(var i=0;i<penaltyList.length;i++){
	 			var penalty=penaltyList[i];
	 			var index=i+1;
	 			rewardPunishment.list_show(penalty,rpType,index);
	 		}
	 	}
	 },
	  //add  by xiehuilin 2017/07/10 奖惩名单数据渲染
	 list_show:function(penalty,rpType,index){
	 	var recordNumber=(page-1)*10+index;
	 	var list_html='';
	 	switch(rpType)
			{
			case 0:
			 list_html+='<div class="reward_list">';
			 list_html+='<div class="icon"><span>'+recordNumber+'</span></div>';
			 list_html+='<div class="top">';
			 list_html+='<ul class="ul1">';
			 list_html+='<li>'+penalty.cTime+'</li>';
			 list_html+='<li id="zhong">'+penalty.userName+'</li>';
			 list_html+='<li>罚<i>'+penalty.rplMoney+'</i>元</li>';
			 list_html+='</ul>';
			 list_html+='</div>';
			 list_html+='<div style="clear: both;"></div>';
			 list_html+='<div class="bottom">';
			 list_html+='<div class="left">';
			 list_html+='<p class="first">进度日期：<span>'+penalty.tTime+'</span></p>';
			 list_html+='<p>奖惩原因：<span>'+penalty.rplReason+'</span></p>';
			 list_html+='</div>';
			 list_html+='<div class="right punish"><a href="#">';
			 list_html+='<i class="eiId" style="display:none;">'+penalty.eiId+'</i>';
			 var str= penalty.isFinish==0?"未交":"已交";
			 list_html+='<i class="rplMoney" style="display:none;">'+penalty.rplMoney+'</i>'+str+'</a></div>';
			 list_html+='</div></div>';
			 $(".relust_chufa").append(list_html);
			  break;
			case 1:
			  list_html+='<div class="reward_list">';
			 list_html+='<div class="icon"><span>'+recordNumber+'</span></div>';
			 list_html+='<div class="top">';
			 list_html+='<ul class="ul1">';
			 list_html+='<li>'+penalty.cTime+'</li>';
			 list_html+='<li id="zhong">'+penalty.userName+'</li>';
			 list_html+='<li>奖<i>'+penalty.rplMoney+'</i>元</li>';
			 list_html+='</ul>';
			 list_html+='</div>';
			 list_html+='<div style="clear: both;"></div>';
			 list_html+='<div class="bottom">';
			 list_html+='<div class="left">';
			 list_html+='<p class="first">进度日期：<span>'+penalty.tTime+'</span></p>';
			 list_html+='<p>奖惩原因：<span>'+penalty.rplReason+'</span></p>';
			 list_html+='</div>';
			 list_html+='<div class="right reward"><a href="#">';
			 list_html+='<i class="eiId" style="display:none;">'+penalty.eiId+'</i>';
			var str= penalty.isFinish==0?"未操作":"已操作";
			 list_html+='<i class="rplMoney" style="display:none;">'+penalty.rplMoney+'</i>'+str+'</a></div>';
			 list_html+='</div></div>';
			 $(".relust_jiangli").append(list_html);
			  break;
			default:
			  
			}
	 }
}

// 罚款分页
$(window).scroll(function () {
    var bot =($(".Tab_list .reward_list").height())*3; 
        if (verdict) {
            if ((bot + $(window).scrollTop()) >= ($("body").height() - $(window).height())) {
                verdict=false;
                page+=1;
                rewardPunishment.list(rpType);
            }
        }
});

