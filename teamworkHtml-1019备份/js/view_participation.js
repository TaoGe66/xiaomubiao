$(function(){
	init.data();
	
	 //add by xiehuilin 2017/06/21 跳转参与列表
	$(".view_participation button").click(function(){
		_g("participants.html");
	});
	//add by xiehuilin 2017/06/23 跳转事件描述页
	$(".list").click(function(){
		_g("event_description.html");
	});
	// add by xiehuilin 2017/06/23 跳转评论页
	$(".comment button").click(function(){
		_g("comment.html");
	});
	//  点击分享图标弹出引导页
    $(".share1").click(function(){
    	go_shore();
    	$(".share_page").css("display","block");
    	$(".share_page1").css("display","block");
    })
    $(".share_page").click(function(){

		$(".share_page").css("display","none");
    	$(".share_page1").css("display","none");
	})
    //返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
});
// add by xiehuilin 2017/06/16 初始化事件详情
var _shareTitle="";// 分享标题
var _shareCoutent = ""; //分享内容
var _shareLogo = ""; //分享logo
var eId=localStorage.getItem("team_eId");
var userId=localStorage.getItem("team_userId");
console.info("eId="+eId+"userId="+userId);
var init={
	data:function(){
		var param={"userId":userId,"eId":eId};
    	var url=dataLink+"event/svcPartyEventInfo";
    	_asyn(url,param,"get",init.Ok);
		
	},
	Ok:function(json, code){
		 console.info(json);
		if(json.msg=='success'){
			json.isSelected==0?$(".Service_footer").show():$(".Service_footer").hide();
			json.roleType==0?$(".running").show():$(".jeCount").show();
			var eve=json.event;
			var lessCount=eve.lessCount==null?0:eve.lessCount;
			var maxCount=eve.maxCount==null?0:eve.maxCount;
			eId=eve.encryptEventId;
			_shareTitle=eve.name;
			_shareCoutent=eve.target;
			_shareLogo=imageHeadSrc+eve.logo;
			$(".list").find("p").html(eve.name);
			$(".target_content").html(eve.target);
			$(".bgtime").html(eve.sTime);
			$(".endtime").html(eve.eTime);
			$(".bgnumber").html(lessCount);
			$(".endnumber").html(maxCount);
			$(".dizhi").html(eve.address);
			$(".Sign_people").html("创建人：&nbsp;"+eve.userName);
			if (isnull(eve.content)) {
				$(".item").hide();
			}else{
				$(".item").html(eve.content);
			}
		  localStorage.setItem("team_roleType",json.roleType);
		  localStorage.setItem("team_isSelected",json.isSelected);
		  element.onOff(json.isend);
		}
	}
}


// add by xiehuilin 20170621 去分享
var _shore={
	_vip_member:function(shoreImg,eId,shoreContent){
		var shoreLink = skipHttp+"sign_in.html?eId="+eId+"&soure="+1;
		_shore._show(shoreImg,shoreLink,shoreContent);
	},
	_show:function(shoreImg,shoreLink,shoreContent){
		var shoreTitle =_shareTitle
		_wx._shore(shoreTitle,shoreContent,shoreLink,shoreImg);
	}
}



function go_shore(){
	var pageName = "view_participation";
	var urlDate = window.location.search; 
	_wx._getWxConfig(pageName,urlDate);
	var shoreContent ="【组织活动】"+ _shareCoutent;
	var shoreImg = _shareLogo;
	_shore._vip_member(shoreImg,eId,shoreContent);
	
}
// add by xiehuilin 2017/06/23  控制页面元素开关  onOff
var element={
	onOff:function(isEnd){
		switch(isEnd)
		{
		case 0:
		  $(".view_participation").show();
		  break;
		case 1:
		  $(".comment").show();
		  break;
		default:
		}
	}
}

			 	
