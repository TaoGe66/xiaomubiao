
$(function(){
	$(".int1").on("click",function(){
		$(this).attr("src","img/radion_click.png");
		$(".int2").attr("src","img/radio.png");
	})
	$(".int2").on("click",function(){
		$(this).attr("src","img/radion_click.png");
		$(".int1").attr("src","img/radio.png");
	})
	$(".complete").click(function(){
		$(".event_summary").css("display","block");
		$(".event_summary1").css("display","block");
	})
	$(".cancel").click(function(){
		$(".event_summary").css("display","none");
		$(".event_summary1").css("display","none");
	})
	
 //add by xiehuilin 2017/06/21 跳转目标页
	$(".edit").click(function(){
	 	_g("servereleasetwo.html");
	});
 	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	}); 
})


 $(function (){ 
 	var roleType=localStorage.getItem("team_roleType");
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
	    	   	var maxCount=info.maxCount==null?0:info.maxCount;
	    	   	var pubRange=info.pubRange==0?"好友可见":"所有人可见";
	    	   		$("#Event_desc").find("p").html(info.name);
	    	   		$(".list").find("li").eq(0).find("i").html(info.target);
	    	   		$(".list").find("li").eq(1).find("i").html(info.sTime+'-'+info.eTime);
	    	   		$(".list").find("li").eq(2).find("i").html(pubRange);
	    	   		$(".list").find("li").eq(3).find("i").html(maxCount);
	    	   		$(".list").find("li").eq(4).find("i").html(frequency.svcManageTime(info.frequency));
	    	   		var head=isnull(json.user.headUrl)?"mg/img03.png":imageHeadSrc+json.user.headUrl;
	    	   		var html='<img src="'+head+'" alt="" /><p>'+json.user.userName+'</p>';
	    	   		$(".list").find("li").eq(5).find("div").html(html);
	    	   		//json.roleType==1?$(".edit").show():$(".edit").hide();
	    	   }
	    	}
	    	  	
	    });
 }());

