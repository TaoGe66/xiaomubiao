$(function(){
//	点击弹窗灰色区域弹窗消失	
	$(".Perdata_alert_bg").click(function(){
		$(".Perdata_alert").hide();
	});	
//	edit by zhengjunting 2017/06/15 点击选中按钮
	$(".Perdata_ok").click(function(){
		$(".Perdata_alert").show();
	});
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});

//	edit by zhengjunting 2017/06/15 点击弹窗取消按钮
	$(".Perdata_alert_btnno").click(function(){
		$(".Perdata_alert").hide();
		$(".Perdata_ok").show();
		$(".Perdata_call").hide();
	});
	init.data();
	update.send();
});

var init={
	 data:function(){
	 	var eId=localStorage.getItem("team_eId");
	 	var userId=$.getUrlParam("userId");
		var param={"userId":userId,"eId":eId};
    	var url=dataLink+"join/getStandData";
    	_asyn(url,param,"get",init.Ok);
		
	},
	Ok:function(json, code){
		if(json.msg=="success"){
			console.info(json);
			var user=json.standData;
			var sex="";
			if(user.sex!=null){
			 sex=user.sex==0?"女":"男";
			}
			//昵称
			var pthis=$(".Perdata_list").find("li");
			$(pthis).eq(0).find("i").html(user.userName);
			$(pthis).eq(1).find("i").html(sex);
			$(pthis).eq(2).find("i").html(user.birthday);
			$(pthis).eq(3).find("i").html(user.address);
			$(pthis).eq(4).find("i").html(user.note);
			$(".name").html(user.userName);
			$(".Perdata_last").html(user.advantage);
			$(".id").html(user.id);
			user.state==0?$(".Perdata_ok ").show():$(".Perdata_ok ").hide();
		}
	
	}
}

var update={
	send:function(){
	//	edit by zhengjunting 2017/06/15 点击弹窗确定按钮
	$(".Perdata_alert_btnok").click(function(){
		var userId=$.getUrlParam("userId");
		var eId=localStorage.getItem("team_eId");
		var id=$(this).parent().find(".id").html();
		var param={"eId":eId,"userId":userId,"id":id};
    	var url=dataLink+"join/screenDuty";
    	_asyn(url,param,"post",update.sendOk);
		$(".Perdata_alert").hide();
		$(".Perdata_ok").hide();
		
	 });
	},
	sendOk:function(json,code){
		console.info(json);
	}
}
