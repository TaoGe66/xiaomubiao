$(function(){
//	edit by zhengjunting 2017/06/13 事项提醒
	join_info();
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
});




//修改事项推送时间
function save_item(type){
	var eId=localStorage.getItem("team_eId");
	var userId=localStorage.getItem("team_userId");
	var frequency=$(".Reminder_current").find("i").html();
	var Json='"eId":"'
		+eId
		+'","userId":"'
		+userId
		+'","frequency":"'
		+frequency
		+'","type":"'
		+2
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	var data='{'+start+'}';
	$.ajax({
		  url:dataLink+"item/updateJoin",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     contentType: "application/json; charset=utf-8",
	     success:function(result){
	   	  if(result.msg=="success"){
	   		window.location.href="eventdetailsone.html";
	   	  }
	     },  
	     timeout:3000  
	});
	
}

//提醒内容展示
function join_info(){
	var eId=localStorage.getItem("team_eId");
	var userId=localStorage.getItem("team_userId");
		var data={"eId":eId,"userId":userId}
		$.ajax({
			type : "GET",
			url:dataLink+"join/itemJoinInfo",  
			dataType:'json',  
			data:data, 
			contentType: "application/json; charset=utf-8",
			success:function(json){
				if(json.msg=="success"){
					var item=json.join;
					localStorage.setItem("team_joinId",item.id);
					if (item.frequency==-30) {
						var html ='<li class="Reminder_current"><i style="display:none;">-30</i>30分钟前</li>';
					}else{
						var html ='<li><i style="display:none;">-30</i>30分钟前</li>';
					}
					if (item.frequency==-60) {
						 html +='<li class="Reminder_current"><i style="display:none;">-60</i>1小时前</li>';
					}else{
						 html +='<li><i style="display:none;">-60</i>1小时前</li>';
					}
					if (item.frequency==-240) {
						 html +='<li class="Reminder_current"><i style="display:none;">-240</i>4小时前</li>';
					}else{
						 html +='<li><i style="display:none;">-240</i>4小时前</li>';
					}
					$(".warn_list").html(html);
					
					
					//选择事项提醒
					$(".Reminder_footer li").click(function(){
						$(this).addClass("Reminder_current").siblings().removeClass("Reminder_current");
					});
					//保存事项提醒
					$(".Reminder_save").click(function(){
						save_item();
					});
				}
			},  
			timeout:3000  
		}); 
}