var userId = localStorage.getItem("team_userId");
$(function(){
	//  edit by zhengjunting 2017/06/12  	点击取消时
	$(".Personaln_footer_no").click(function(){
		window.location.href="personalcenter.html";
	});
	//  edit by zhengjunting 2017/06/12  	点击确定时
	$(".Personaln_footer_ok").click(function(){
		var note = $(".smallshdow").val();
		if(!isnull(note)){
			var data ={"userId":userId,"note":note};
		    updateUserInfo(data);
		}
	});
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
	getUserInfo();
})


function updateUserInfo(data){
	 $.ajax({
		   type : "post",
	       url:dataLink+"userwx/updateUserInfo", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    		   window.location.href="personalcenter.html";
	    	   }
	       }
	 });
	
}


function getUserInfo(){
	
	var data = {"userId":userId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"userwx/getUserInfoById", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	var userInfo = json.user;
	    	   	$(".smallshdow").val(userInfo.note);
	    	   }
	       }
	  });
}
