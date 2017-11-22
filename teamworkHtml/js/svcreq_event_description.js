 (function (){ 
 	var eId=localStorage.getItem("team_eId");
 	 var userId = localStorage.getItem("team_userId");
 	var param={"userId":userId,"eId":eId};
 	 $.ajax({
		   type : "get",
	       url:dataLink+"event/getEventDes", 
	       data:param, 
	       dataType : "json",
	       success : function(json){
	       	console.info(json);
	       	var info=json.envetInfo;
	    	   if(json.msg="success"){
	    	   		$(".event_name").html(info.name);
	    	   		$(".target").html(info.target);
	    	   		$(".Event_desc_two i").html(info.promise);
	    	   		$(".time").html(info.sTime+'-'+info.eTime);
	    	   		$(".frequency").html(frequency.svcManageTime(info.frequency));
	    	   		if(json.roleType==0){
			 	 	 $(".item").html(info.content);
			 	 }
	    	   }
	    	}
	    	  	
	    });
 }());

 //add by xiehuilin 2017/06/21 跳转目标页
$(".edit").click(function(){
 	_g("servereleasethree.html");
});
 //add by lixiaobin 2017/08/01 跳转目标页
$(".event_summary").click(function(){
 	$(".event_summary").css("display","none");
 	$(".event_summary1").css("display","none");
});
//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	}); 	 
