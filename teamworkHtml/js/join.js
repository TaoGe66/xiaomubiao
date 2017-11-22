var leId = $.getUrlParam('leId');
var friendId = $.getUrlParam('friendId');
var userId = localStorage.getItem("team_userId");
$(function(){
	if(verificationUserLogin()){
		getMeber();
		enterpriseInfo();
	}
});

function getMeber(){
	var data = {"leId":leId,"userId":userId,"lemStateStr":"1,2"};
	 $.ajax({
		   type : "get",
	       url:dataLink+"member/getMeber", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	  if(!isnull(json.getMeber)){
	    	   	  	  if(json.getMeber[0].lemState==2){
	    	   	  	  	$(".Join_footer").addClass("Join_btn_none");
	    	   	  	  	$(".Join_footer").html("等待管理员审核");
	    	   	  	  }else if(json.getMeber[0].lemState==1){
	    	   	  	  	$(".Join_footer").addClass("Join_btn_none");
	    	   	  	  	$(".Join_footer").html("您已是成员");
	    	   	  	  }
	    	   	  }else{
	    	   	  	    $(".Join_footer").addClass("Join_btn");
	    	   	  	  	$(".Join_footer").html("申请加入");
	    	   	  }
	    	   	  $(".Join_btn").unbind("click");
	    	   	  $(".Join_btn").click(function(){
						getMeberTwo();
					});
	    	   }
	    	}
	});
}

function enterpriseInfo(){
	
	var data = {"leId":leId};
	 $.ajax({
		   type : "get",
	       url:dataLink+"enterprise/enterpriseInfo", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	var item = json.enterpriseInfo;
	    	   	$(".Join_head img").attr("src",imageHeadSrc+item.leLogo);
	    	   	$(".Join_title").html(item.name);
	    	   	$(".Join_list_one i").html(item.fullName);
	    	   	$(".Join_list_two i").html(item.usercName);
	    	   	$(".Join_list_three i").html(item.memberCount);
	    	   }
	    	  }
	    });
}

function getMeberTwo(){
	var data = {"leId":leId,"userId":userId,"lemStateStr":"1,2"};
	 $.ajax({
		   type : "get",
	       url:dataLink+"member/getMeber", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	  if(!isnull(json.getMeber)){
	    	   	  	  if(json.getMeber[0].lemState==2){
	    	   	  	  	$(".Join_footer").addClass("Join_btn_none");
	    	   	  	  	$(".Join_footer").html("等待管理员审核");
	    	   	  	  }else if(json.getMeber[0].lemState==1){
	    	   	  	  	$(".Join_footer").addClass("Join_btn_none");
	    	   	  	  	$(".Join_footer").html("您已是成员");
	    	   	  	  }
	    	   	  }else{
	    	   	  	   insertMem(); 
	    	   	  }
	    	   	
	    	   }
	    	}
	});
}


function insertMem(){
	var data = {"leId":leId,"inviteId":friendId,"userId":userId,"lemState":2,"roleType":2};
	 $.ajax({
		   type : "post",
	       url:dataLink+"member/insertMem", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	     	$(".Join_alert").show();
	    	     	$(".Join_footer").html("等待管理员审核");
				    $(".Join_footer").removeClass("Join_btn").addClass("Join_btn_none");
	       	  		setTimeout(function(){
					     $(".Join_alert").hide();
				    },2000);
	    	   }
	    	  }
	    });
	
}
