
 //add by xiehuilin 2017/07/18 页面输入参数 start
 var worst="";
 var hysteresis="";
 var best="";
 //add by xiehuilin 2017/07/18 页面输入参数 end
$(function(){
	$(".reward_btn").click(function(){
		worst=$(".penalty_top").find("input").val();
		 hysteresis=$(".penalty_bottom").find("input").val();
		 best=$(".reward_top").find("input").val();
		if(worst>5000||hysteresis>5000||best>5000){
			$(".ts_money").show();
			 setTimeout(function(){$(".ts_money").hide()},2000) 
			return ;
		}
		if(isnull(worst)){
			$(".penalty_top").find("input").addClass("red");
			return;
		}else{
			$(".penalty_top").find("input").removeClass("red");
		}
		if(isnull(hysteresis)){
			$(".penalty_bottom").find("input").addClass("red");
			return;
		}else{
			$(".penalty_bottom").find("input").removeClass("red");
		}
		if(isnull(best)){
			$(".reward_top").find("input").addClass("red");
			return;
		}else{
			$(".reward_top").find("input").removeClass("red");
		}

		$(".reward_summary").css("display","block");
		$(".reward_summary1").css("display","block");
		$(".bomb_box").css("display","block");
	})
	$(".cancel").click(function(){
		$(".reward_summary").css("display","none");
		$(".reward_summary1").css("display","none");

	})
	$(".determine").click(function(){
		var arr= new Array();
		var str1='{"rpMoney":"'+worst+'","rpCategory":"'+1+'","rpType":"'+0+'"}';
		var str2='{"rpMoney":"'+hysteresis+'","rpCategory":"'+0+'","rpType":"'+0+'"}';
		var str3='{"rpMoney":"'+best+'","rpCategory":"'+2+'","rpType":"'+1+'"}';
		arr.push(str1);
		arr.push(str2);
		arr.push(str3);
		save.data(arr);
		
	});

	$(".return_index").click(function(){
		_g("index.html");
	});
})

//返回首页
$(".return_index").click(function(){
	window.location.href="index.html";
});

/*add by xiehuilin 2017/07/10 保存奖惩规则*/
var save={
	data:function(ruleList){
		var userId=localStorage.getItem("team_userId");
		var eId=localStorage.getItem("team_eId");
		var param='{"createBy":'+userId+',"eId":'+eId+',"ruleList":'+'['+ruleList+']}';
		console.info(param);
		var url=dataLink+"ap/save";
    	_new_asyn(url,param,"POST",save.ok);
	},
	ok:function(json,code){
		if(json.msg=="success"){
			_g("rewardTab.html");
		}else{
			$(".bomb_box").hide();
			$(".reward_summary").hide();
			$(".ts_money").html("");
			$(".ts_money").html(json.errorCode);
			$(".ts_money").show();
			 setTimeout(function(){$(".ts_money").hide()},2000) ;
		}
	}
}


