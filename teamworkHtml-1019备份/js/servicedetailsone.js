$(function(){
	init.data();
	$(".Service_head_inner").click(function(){
		_g("event_description_service.html");
	});
	 //add by xiehuilin 2017/06/21 跳转参与列表
	$(".jeCount").click(function(){
		_g("numberofpeople.html");
	});
	$(".Serviceone_election").click(function(){
		 if(verificationUserLogin()){
			$(".Service_head").css("display","none");
			$(".Service_con").css("display","none");
			$(".Service_footer").css("display","none");
			$(".Explain_con").css("display","block");
		}
	});
	$(".Explain_con").find("footer").click(function(){
		if(verificationUserLogin()){
		var advantage=$(".Explain_con").find("textarea").val();
		update.join(advantage);
	 }
	});
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
	//  点击分享图标弹出引导页
    $(".share").click(function(){
    	go_shore();
    	$(".share_page").css("display","block");
    	$(".share_page1").css("display","block");
    })
    $(".share_page").click(function(){
		$(".share_page").css("display","none");
    	$(".share_page1").css("display","none");
	})
    //add by lixiaobin 2017/07/28 点击立即跳转页面跳转
    $(".Service_popup1 p i").click(function(){
    	_g("svc_red_eventdetails.html");
    })
     //add by lixiaobin 2017/07/28 点击立即跳转页面跳转
    $(".Service_popup3 p i").click(function(){
    	_g("svc_red_eventdetails.html");
    })
//  调用责任人立即跳转弹窗方法
//  var  time = 5;
//  Count_down(time);
});
var leId=localStorage.getItem("team_leId");
if(!isnull(leId)){
	$(".share").hide();
	$(".Service_footer").addClass("Service_footer_two");
}
// add by xiehuilin 2017/06/16 初始化事件详情
var _shareTitle="";// 分享标题
var _shareCoutent = ""; //分享内容
var _shareLogo = ""; //分享logo 
var share_eId=$.getUrlParam("eId");
var eId=isnull(share_eId)?localStorage.getItem("team_eId"):share_eId;
localStorage.setItem("team_eId",eId);
var userId=localStorage.getItem("team_userId");
var soure=$.getUrlParam("soure");
var init={
	data:function(){
		var param={"userId":userId,"eId":eId};
    	var url=dataLink+"event/svcPartyEventInfo";
    	_asyn(url,param,"get",init.Ok);
		
	},
	Ok:function(json, code){
		console.info(json);
		if(json.msg=='success'){
			if(json.isDuty==1&&json.isSelected==1){
					Count_down(".Service_popup1 p span",5,0);			
			}else if(json.roleType==1&&json.isSelected==1){
				Count_down(".Service_popup3 p span",5,1);
			}
			json.isSelected==0?$(".Service_footer").show():$(".Service_footer").hide();
			json.roleType==0?$(".running").show():$(".jeCount").show();
			if(json.isSelected==1&&json.roleType==1){
				$(".Service_footer").show();
			}
			var eve=json.event;
			//eId=eve.encryptEventId;
			_shareTitle=eve.name;
			_shareCoutent=eve.target;
			_shareLogo=imageHeadSrc+eve.logo;
			if(json.roleType!=1&&json.joinEvents.length>0){
				$(".Serviceone_selected").html("已参选");
				$(".Serviceone_election").hide();
				$(".Serviceone_selected").show(); 
			}else if (json.roleType!=1&&json.joinEvents.length<=0) {
				$(".Serviceone_election").html("投标参选");
				$(".Serviceone_election").show();
				$(".Serviceone_selected").hide();
			}
			$(".event_name").html(eve.name);
			$(".Service_head_font").html("描述："+eve.target);
			$(".Service_head_time").html(eve.sTime+"&nbsp;-&nbsp;"+eve.eTime);
			$(".Service_head_name").html(eve.userName);
			if (!isnull(eve.content)) {
				$(".Service_con_inner").show();
				$(".Service_con_inner").html(eve.content);
			}
			var jCount=json.joinEvents.length;
			$(".jeCount").html("参看参选人（"+jCount+"）");
		  localStorage.setItem("team_roleType",json.roleType);
		  localStorage.setItem("team_isSelected",json.isSelected);
		  if(json.isend==1&&soure==1){
		  	$(".share1").show();
		  }
		  if(json.isSelected==1&&soure==1&&json.roleType!=1&&json.isDuty!=1){
		  	$(".share1").show();
		  	$(".tishi").show();
		  	setTimeout(function(){
					$(".tishi").hide();
			},2000)
		  }
		  go_shore();
		}
	}
}



// add by xiehuilin 2017/06/23 
var update={
	join:function(advantage){
		var param={"userId":userId,"eId":eId,"advantage":advantage};
		console.info(param);
    	var url=dataLink+"join/save";
    	_asyn(url,param,"POST",update.Ok);
	},
	Ok:function(json,code){
		if(json.msg=="success"){
			$(".Serviceone_election").hide();
			$(".Serviceone_selected").show();
			$(".Service_head").css("display","block");
			$(".Service_con").css("display","block");
			$(".Service_footer").css("display","block");
			$(".Explain_con").css("display","none");
		}
	}
}	


			 	


// add by xiehuilin 20170621 去分享
var _shore={
	_vip_member:function(shoreImg,eId,shoreContent){
		var shoreLink = skipHttp+"servicedetailsone.html?eId="+eId+"&soure="+1;
		_shore._show(shoreImg,shoreLink,shoreContent);
	},
	_show:function(shoreImg,shoreLink,shoreContent){
		var shoreTitle =_shareTitle
		_wx._shore(shoreTitle,shoreContent,shoreLink,shoreImg);
	}
}



function go_shore(){
	var pageName = "servicedetailsone";
	var urlDate = window.location.search; 
	_wx._getWxConfig(pageName,urlDate);
	var shoreContent ="【需求】"+_shareCoutent;
	var shoreImg = _shareLogo;
	_shore._vip_member(shoreImg,eId,shoreContent);
	
}
//eidt by xiehuilin 20170728 
function Count_down(region,time,type){
	$(region).html(time);
	setInterval(function(){
		if(time <= 0){
				_g("svc_red_eventdetails.html");
		}else{
			time--;
			if(type==0){
				$(".Service_popup").show();
				$(".Service_popup1").show();
			}
			if(type==1){
				$(".Service_popup2").show();
				$(".Service_popup3").show();
			}
			$(region).html(time);
		}
	},1000)
}
