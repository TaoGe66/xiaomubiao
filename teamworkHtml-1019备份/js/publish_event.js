$(function(){
	$("body").height($(window).height());
});
//发布事件组织活动
$(".Next_step").click(function(){
	check_info();
});

//校验信息
function check_info(){
	var via=true;
	//上传封面校验
	var _partyLogo=localStorage.getItem("team_partyLogo");
	if (isnull(_partyLogo)) {
		$(".fm_ts").show();
		setTimeout(function(){$(".fm_ts").hide();},2000);
		via=false;
		return false;
	}
	
	if (via) {
		save_content();
	}
	
}
var leId = localStorage.getItem("team_leId");
if(!isnull(leId)){
	$("title").html("轻企活动详情");
}
//保存内容
function save_content(){
	var eId=localStorage.getItem("team_eId");
	var createBy=localStorage.getItem("team_userId");
	var _partyLogo=localStorage.getItem("team_partyLogo");
	var Json='"eId":"'
		+eId
		+'","userId":"'
		+createBy
		+'","createBy":"'
		+createBy
		+'","isValid":"'
		+0
		+'","state":"'
		+0
		+'","logo":"'
		+_partyLogo
		+'","type":"'
		+2
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	var data='{'+start+'}';
	$.ajax({
		  url:dataLink+"event/updateEnvet",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     contentType: "application/json; charset=utf-8",
	     success:function(result){
	   	  if(result.msg=="success"){
	   		localStorage.setItem("team_physics",1);
	   		localStorage.removeItem("team_partyLogo");
	   		window.location.href="release.html";
	   	  }
	     },  
	     timeout:3000  
	});
}


