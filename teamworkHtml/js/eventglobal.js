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
	    	   		$("ul").find("li").eq(2).find("i").html(frequency.pubRange(info.pubRange));
	    	   		var headUrl=isnull(info.headUrl)?"img/img03.png":imageHeadSrc+info.headUrl;
	    	   		var html='<img src="'+headUrl+'" alt="" /><p>'+info.userName+'</p>';
	    	   		$(".cjr_box").append(html);
	    	   		if(json.roleType==0&&json.isEnd==0){
	 	 				$(".complete1").show();
			 	    }else if(json.isSelected==1){
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
//返回首页
$(".return_index").click(function(){
	window.location.href="index.html";
});
 //add by xiehuilin 2017/06/27 完成时间
$(".complete1 ").click(function(){
		$(".event_summary").css("display","block");
		$(".event_summary1").css("display","block");
		$(".determine").click(function(){
			var finishTime = $("#appDateTime").val();
        	var summary = $(".summary").val();
        	//add by xiehuilin 校验输入参数
        	if(param.fEvent(finishTime,summary,"tishi1","tishi2")){
        		todoCloseEvent();
        	}
		});
		
	});

//关闭事件
function todoCloseEvent(eId){
	var finishTime = $("#appDateTime").val();
	var summary = $(".summary").val();
	var eId=localStorage.getItem("team_eId");
	var userId = localStorage.getItem("team_userId");
	var state = $(".getState").attr("id");
	var data = {"eId":eId,"finishTimeStr":finishTime,"summary":summary,"userId":userId,"tState":state};
	 $.ajax({
		   type : "post",
	       url:dataLink+"homepage/closeEvent", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	window.location.href="index.html";
	    	}
	    }
	});
	
}