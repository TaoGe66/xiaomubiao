$(".Select_btn").show(); 
 ligth_all();
 function ligth_all(){
 	var userId = localStorage.getItem("team_userId");
 	var leId=localStorage.getItem("team_leId");
 	var data={"userId":userId,"leId":leId}
 	$.ajax({
 		  type : "GET",
 	      url:dataLink+"direct/listManage",  
 	      dataType:'json',  
 	      data:data, 
 	      contentType: "application/json; charset=utf-8",
 	      success:function(json){
 	    	  if(json.msg=="success"){
 	    		  //轻企成员
 	    		 var memberList=json.memberList;
 	    		 if (memberList.length>0) {
 	    			 $(".light_all").html("");
 						for (var i = 0; i < memberList.length; i++) {
 							var member=memberList[i];
 							var headUrl= isnull(member.headUrl)?"img/img03.png":imageHeadSrc+member.headUrl;
 							var  partake=localStorage.getItem("team_eventPartake");
 	 	    	   	      	var is_show=0;
 	 	    	   	      	if (!isnull(partake)) {
 	 	    	   	      		var  arr= partake.split(",");
 	 	    	   	      		for (var j = 0; j < arr.length; j++) {
 	 	    	   	      			var str= arr[j];
 	 	    	   	      			if (member.userId==str) {
 	 	    	   	      				is_show=1;
 	 	    	   	      				//return true;
 	 	    	   	      			}
 	 	    	   	      		}
 							}
 	 	    	   	      	if (userId !=member.userId) {
								
 	 	    	   	      		if (is_show==0) {
 	 	    	   	      			var html ='<li><em><i style="display:none;">'+member.userId+'</i>';
 	 	    	   	      			html +='</em><img src="'+headUrl+'" alt="" />';
 	 	    	   	      			html +='<div class="Select_list"><span>'+member.userName+'</span>';
 	 	    	   	      			html +='</div></li>'; 
 	 	    	   	      		}else{
 	 	    	   	      			var html ='<li><em class="Select_global_current"><i style="display:none;">'+member.userId+'</i>';
 	 	    	   	      			html +='</em><img src="'+headUrl+'" alt="" />';
 	 	    	   	      			html +='<div class="Select_list"><span>'+member.userName+'</span>';
 	 	    	   	      			html +='</div></li>';
 	 	    	   	      		}
							}
 							
 								$(".my_company").append(html);
 						}
 					}
 	    		 
 	    	  }
 	    		//选择好友
	    	   	 $(".Select_global").on("click","li",function(){
	    	   		$(this).find("em").toggleClass("Select_global_current");
	    	   	});
 	      },  
 	      timeout:3000  
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

 