$(".Select_btn").show(); 
getFriendGroup();
//分组下好友列表
 function getFriendGroup(){
 	var userId=localStorage.getItem("team_userId");
 	var data = {"userId":userId,"state":1}
 	 $.ajax({
 		   type : "get",
 	       url:dataLink+"friend/getFriendGroupById", 
 	       data:data, 
 	       dataType : "json",
 	       success : function(json){
 	    	   if(json.msg="success"){
 	    	   	$(".my_fellow").html("");
 	    	   	  if(json.groupFriend.length>0){
 	    	   	  	for(i=0;i<json.groupFriend.length;i++){
 	    	   	      	var item = json.groupFriend[i];
 	    	   	      	var headUrl = isnull(item.headUrl)? "img/img03.png" : (imageHeadSrc+item.headUrl);
 	    	   	      	var  partake=localStorage.getItem("team_eventPartake");
 	    	   	      	var is_show=0;
 	    	   	      	if (!isnull(partake)) {
 	    	   	      		var  arr= partake.split(",");
 	    	   	      		for (var j = 0; j < arr.length; j++) {
 	    	   	      			var str= arr[j];
 	    	   	      			if (item.fId==str) {
 	    	   	      				is_show=1;
 	    	   	      				//return true;
 	    	   	      			}
 	    	   	      		}
						}
 	    	   	      	if (is_show==0) {
 	    	   	      		var html = '<li><em><i style="display:none;">'+item.fId+'</i>';
 	    	   	      		html +='</em><img src="'+headUrl+'" alt="" /><div class="Select_list">';
 	    	   	      		html += '<span>'+item.userName+'</span></div></li>';
						}else{
							var html = '<li><em class="Select_global_current"><i style="display:none;">'+item.fId+'</i>';
 	    	   	      		html +='</em ><img src="'+headUrl+'" alt="" /><div class="Select_list">';
 	    	   	      		html += '<span>'+item.userName+'</span></div></li>';
						}
 	    	   	      	$(".my_fellow").append(html);
 	    	   	  	}
 	    	   	  	//选择好友
 	    	   	 $(".Select_global").on("click","li",function(){
 	    	   		$(this).find("em").toggleClass("Select_global_current");
 	    	   	});
 	    	   	  }
 	    	   	
 	    	   }
 	    	  }
 	    });
 	
 	
 }
//确定
 $(".Select_btn_confirm").click(function() {
	var eventPartake = "";
	$(".Select_global_current").each(function() {
		var cycle = $(this).find("i").html();
		if (!isnull(eventPartake)) {
			eventPartake = eventPartake + "," + cycle;
		} else {
			eventPartake = cycle;
		}
	});
	localStorage.setItem("team_eventPartake", eventPartake);
	window.location.href = "serverelease.html";
});
 
 //取消
 $(".Select_btn_cancel").click(function(){
		window.location.href="serverelease.html";
});

