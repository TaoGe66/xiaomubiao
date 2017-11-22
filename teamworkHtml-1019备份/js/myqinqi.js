var userId=localStorage.getItem("team_userId");


personalLight();
//个人中心我的轻企列表 		wuchao
function  personalLight(){
	localStorage.setItem("team_isCut",null);
	var data={"userId":userId}
	$.ajax({
		  type : "GET",
	      url:dataLink+"enterprise/personalLight",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  if (json.list.length>0) {
	    			  var list = json.list;
					for (var i = 0; i < list.length; i++) {
						var item =list[i];
						var light_logo=imageHeadSrc+item.leLogo;
						var state_logo="";
						if (item.lemState==2) {
							state_logo="img/firm02_11.png";
						}else if(item.lemState!=2 && item.authState==1){
							state_logo="img/firm01_06.png";
						}
						var html ='<li class="firm_list">';
							html+='<em style="display:none;">'+item.roleType+'</em>';
							html+='<em style="display:none;">'+item.is_enter+'</em>';
							html+='<em style="display:none;">'+item.leId+'</em>';
							html +='<div class="firm_logo"><img src="'+light_logo+'" alt="" /></div>';
							html +='<div class="firm_title">';
							if (!isnull(state_logo)) {
								html +='<p class="firm_name">'+item.leName+'<img src="'+state_logo+'" alt="" class="firm_pass_icon"/></p>';
							}else{
								html +='<p class="firm_name">'+item.leName+'</p>';
							}
							if (item.lemState!=2) {
								html +='<p class="firm_founder">创始人：<span>'+item.userName+'</span></p>';
							}else{
								html +='<p class="firm_founder"><span>等待管理员审核</span></p>';
							}
							html +='</div></li>';
							$(".firm_ul").append(html);	
					}
				}else{
					$(".qsy").show();
				}
	    		  
	    		  $(".firm_list").click(function(){
	      			var  roleType=$(this).find("em").eq(0).html();
	      			var  is_enter= $(this).find("em").eq(1).html();
	      			var  leId= $(this).find("em").eq(2).html();
	      			if (is_enter==1) {
	      				localStorage.setItem("team_leId",leId);
	      				window.location.href="dynamic.html";
	  				}else if(is_enter==0 && roleType==0){
	  					localStorage.setItem("team_leId",leId);
	  					window.location.href="authentication.html";
	  				}else{
	  					$(".noticePopups").show();
	  					setTimeout(function(){$(".noticePopups").hide();},2000);
	  				}
	      			
	      		  });
	    	  }
	      },  
	      timeout:3000  
	 }); 
}