
$(function(){
	 $(".Select_global li").click(function(){
 		$(".Select_ul em").removeClass("Select_global_current");
 		$(this).find("em").addClass("Select_global_current");
 	});
 	//取消
	$(".Select_btn_cancel").click(function(){
		 $(".Select_ul").html("");
		 $(".Additems_alert").hide();
	});
	 $(".Select_global p").click(function(){
	   $(this).toggleClass(".Select_current");
	   if($(this).hasClass("Select_current")){
		   $(this).next().hide();
		   $(this).removeClass("Select_current").css({"border-bottom":"1px solid #e5e5e5"});
		   $(this).parent().css({"height":"3.167rem"});
	   }else{
		   $(this).next().show();
		   $(this).addClass("Select_current").css({"border":"0 none"});
		   $(this).parent().css({"height":"auto"});
	   }
	})	  
})
getGroup();
//好友分组
function getGroup(){
	var userId=localStorage.getItem("team_userId");
	var data = {"userId":userId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"friend/getGroupByUserId", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    		   $(".Select_ul").html("");
	    		   $(".Additems_alert_null").hide();
	    		   if(!isnull(json.group)&&json.group.length>0){
	    			   var list=json.group;
	    			   if(list.length>0){
	    				   var html ='<li class=" Select_global" id=""><p>最近联系</p><ul class="globalTen"></ul></li>';
	    				    html +='<li class=" Select_global" id=""><p>全部好友</p><ul class="globalAll"></ul></li>';
	    				   for (var i = 0; i < list.length; i++) {
	    					   var item=list[i];
	    					   html +='<li class=" Select_global" id="'+item.gId+'"><p>'+item.name+'</p><ul class="global'+i+'"></ul></li>';
	    				   }
	    				   $(".Select_ul").append(html);
	    			   }
	    			   $(".Select_global p").click(function(){
	    				   $(this).toggleClass(".Select_current");
	    				   if($(this).hasClass("Select_current")){
	    					   $(this).next().hide();
	    					   $(this).removeClass("Select_current").css({"border-bottom":"1px solid #e5e5e5"});
	    					   $(this).parent().css({"height":"3.167rem"});
	    				   }else{
	    					   $(this).next().show();
	    					   $(this).addClass("Select_current").css({"border":"0 none"});
	    					   $(this).parent().css({"height":"auto"});
	    				   }
	    				   var clana = "."+$(this).parents(".Select_global").find("ul").attr("class");
	    				   var id = $(this).parents(".Select_global").attr("id");
	    				   if(clana==".globalTen"){
	                			getLatelyFriendList();
	                		}else{
	                			getFriendGroup(clana,id);
	                		}
	    			   });
	    		   }else{
					var html = '<li class="Select_global" id=""><p>最近联系</p><ul class="globalTen"></ul></li>';
						html +='<li class=" Select_global" id=""><p>全部好友</p><ul class="globalAll"></ul></li>';
	    			   $(".Select_ul").append(html);
	    			   $(".Select_global p").click(function(){
	    				   $(this).toggleClass(".Select_current");
	    				   if($(this).hasClass("Select_current")){
	    					   $(this).next().hide();
	    					   $(this).removeClass("Select_current").css({"border-bottom":"1px solid #e5e5e5"});
	    					   $(this).parent().css({"height":"3.167rem"});
	    				   }else{
	    					   $(this).next().show();
	    					   $(this).addClass("Select_current").css({"border":"0 none"});
	    					   $(this).parent().css({"height":"auto"});
	    				   }
	    				   var id = $(this).parents(".Select_global").attr("id");
	    				   var clana = "."+$(this).parents(".Select_global").find("ul").attr("class");
	    				   if(clana==".globalTen"){
	                			getLatelyFriendList();
	                		}else{
	                			getFriendGroup(clana,id);
	                		}
	    			   });
	    		   }
	    		   
	    		 //选择好友
		    	   	 $(".Select_global li").click(function(){
		    	 		$(".Select_ul em").removeClass("Select_global_current");
		    	 		$(this).find("em").addClass("Select_global_current");
		    	 	});
	             }
	    	  }
	    });
}


//分组下好友列表
function getFriendGroup(clana,gId){
	var userId=localStorage.getItem("team_userId");
	var data = {"userId":userId,"state":1,"gId":gId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"friend/getFriendGroupById", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	$(clana).html("");
	    	   	  if(json.groupFriend.length>0){
	    	   	  	for(i=0;i<json.groupFriend.length;i++){
	    	   	      	var item = json.groupFriend[i];
	    	   	      	var headUrl = isnull(item.headUrl)? "img/img03.png" : (imageHeadSrc+item.headUrl);
	    	   	      	var note = isnull(item.note)?"":item.note;
	    	   	      	var html = '<li><em><i style="display:none;">'+item.fId+'</i>';
	    	   	      		html +='<i style="display:none;">'+item.userName+'</i>';
	    	   	      		html +='<i style="display:none;">'+headUrl+'</i>';
	    	   	      		html +='</em><img src="'+headUrl+'" alt="" /><div class="Select_list">';
	    	   	      		html += '<span>'+item.userName+'</span><i>'+note+'</i></div></li>';
	    	   	      	$(clana).append(html);
	    	   	  	}
	    	   	  	//选择好友
		    	   	 $(".Select_global li").click(function(){
		    	 		$(".Select_ul em").removeClass("Select_global_current");
		    	 		$(this).find("em").addClass("Select_global_current");
		    	 	});
	    	   	  }
	    	   	
	    	   }
	    	  }
	    });
	
	
}


function getLatelyFriendList(){
	var userId=localStorage.getItem("team_userId");
	var data = {"userId":userId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"friend/getLatelyFriendList", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	$(".globalTen").html("");
	    	   	  if(!isnull(json.friendsList)&&json.friendsList.length>0){
	    	   	  	for(i=0;i<json.friendsList.length;i++){
	    	   	      	var item = json.friendsList[i];
	    	   	      	var headUrl = isnull(item.headUrl)? "img/img03.png" : (imageHeadSrc+item.headUrl);
	    	   	      	var note = isnull(item.note)?"":item.note;
	    	   	      	
	    	   	     var html = '<li><em><i style="display:none;">'+item.fId+'</i>';
	 	   	      		html +='<i style="display:none;">'+item.userName+'</i>';
	 	   	      		html +='<i style="display:none;">'+headUrl+'</i>';
	 	   	      		html +='</em><img src="'+headUrl+'" alt="" /><div class="Select_list">';
	 	   	      		html += '<span>'+item.userName+'</span><i>'+note+'</i></div></li>';
	 	   	      	$(".globalTen").append(html);
	    	   	  	}
	    	   	//选择好友
		    	   	 $(".Select_global li").click(function(){
		    	 		$(".Select_ul em").removeClass("Select_global_current");
		    	 		$(this).find("em").addClass("Select_global_current");
		    	 	});
	    	   	  }
	    	   	
	    	   }
	    	  }
	    });
	
	
}
//确认选择好友
$(".Select_btn_confirm").click(function(){
	var userId=localStorage.getItem("team_userId");
	var fgId=$(".Select_global_current").find("i").eq(0).html();
	var fg_Name=$(".Select_global_current").find("i").eq(1).html();
	var fg_Logo=$(".Select_global_current").find("i").eq(2).html();
	if (!isnull(fgId)) {
		localStorage.setItem("team_fgId",fgId);
		var isCyc=localStorage.getItem("team_isCyc");
		//team_isCyc为0是单次，若为1 是周期
		if (isCyc==0) {
			save_item(fgId);
		}else if(isCyc==1){
			save_Cycitem(fgId);
		}
		
	}
});
//取消
$(".Select_btn_cancel").click(function(){
	var type = localStorage.getItem("team_type");
	if (type == 0) {
		localStorage.removeItem("team_type");
		window.location.href = "eventdetailsone.html";
	} else if (type == 1) {
		localStorage.removeItem("team_type");
		window.location.href = "svc_red_eventdetails.html";
	}
});

//保存单次事项
function save_item(fgId){
	var eId=localStorage.getItem("team_eId");
	var userId=localStorage.getItem("team_userId");
	var eiId=localStorage.getItem("team_eiId");
	$(".event_summary").show();
	$(".event_summary1").show();
	var Json='"eId":"'
		+eId
		+'","eiId":"'
		+eiId
		+'","isCycle":"'
		+0
		+'","createBy":"'
		+userId
		+'","dutyId":"'
		+fgId
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	var data='{'+start+'}';
	$.ajax({
		url : dataLink + "item/updateClient",
		type : "POST",
		data : data,
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		success : function(result) {
			if (result.msg == "success") {
				localStorage.removeItem("team_fgId");
				localStorage.removeItem("team_isCyc");
				var type = localStorage.getItem("team_type");
				if (type == 0) {
					localStorage.removeItem("team_type");
					window.location.href = "eventdetailsone.html";
				} else if (type == 1) {
					localStorage.removeItem("team_type");
					window.location.href = "svc_red_eventdetails.html";
				}
			}
		},
		timeout : 3000
	});
	
}


//保存周期事项
function save_Cycitem(fgId){
	var eId=localStorage.getItem("team_eId");
	var userId=localStorage.getItem("team_userId");
	var eiId=localStorage.getItem("team_eiId");
	$(".event_summary").show();
	$(".event_summary1").show();
	var Json='"eId":"'
		+eId
		+'","eiId":"'
		+eiId
		+'","isCycle":"'
		+1
		+'","createBy":"'
		+userId
		+'","dutyId":"'
		+fgId
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	var data='{'+start+'}';
	$.ajax({
		url : dataLink + "item/updateCycClient",
		type : "POST",
		data : data,
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		success : function(result) {
			if (result.msg == "success") {
				localStorage.removeItem("team_fgId");
				localStorage.removeItem("team_isCyc");
				var type = localStorage.getItem("team_type");
				if (type == 0) {
					localStorage.removeItem("team_type");
					window.location.href = "eventdetailsone.html";
				} else if (type == 1) {
					localStorage.removeItem("team_type");
					window.location.href = "svc_red_eventdetails.html";
				}
			}
		},
		timeout : 3000
	});
	
}