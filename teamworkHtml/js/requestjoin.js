var leId = localStorage.getItem("team_leId");
$(function(){
	$(".mem_false").click(function(){
		$(".modalShow").hide();
	});
	memberListAll();
});
var five =false;
var six =false;
function memberListAll(){
	 var data = {"leId":leId,"roleTypeStr":"1,2"};
	 $.ajax({
		   type : "get",
	       url:dataLink+"member/memberListAll", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	       	  if(json.msg="success"){
	       	  	if(!isnull(json.memberList) && json.memberList.length>0){
	       	  		for(i=0;i<json.memberList.length;i++){
	       	  			var item = json.memberList[i];
	       	  			var headUrl = isnull(item.headUrl) ? 'img/touxiang_14.png' :  imageHeadSrc+item.headUrl;
	       	  			var html  ='<li><img src="'+headUrl+'" alt="" />';
	       	  			    html +='<div class="Newfriend_con"><span>'+item.userName+'</span>';
	       	  			    html +='<i>请求加入</i></div>';
	       	  			    html +='<div class="Newfriend_btn" id="'+item.userId+'">';
	       	  			if(item.lemState==2){
	       	  				html +='<span class="Newfriend_btn_no" id="'+item.id+'">拒绝</span>';
	       	  				html +='<span class="Newfriend_btn_ok" id="'+item.id+'">同意</span>';
	       	  			}else if(item.lemState==1){
	       	  				html +='<p class="Newfriend_btn_font">已同意</p>';
	       	  			}else if(item.lemState==0){
	       	  			    html +='<p class="Newfriend_btn_font">已拒绝</p>';
	       	  			}
	       	  			    html +='</div></li>';
	       	  		   $(".Newfriend_list").append(html);
	       	  		}
	       	  		//拒绝
	       	  		$(".Newfriend_btn_no").click(function(){
	       	  			var userName =$(this).parents("li").find(".Newfriend_con span").html();
	       	  			var userId =$(this).parents(".Newfriend_btn").attr("id");
	       	  			var id = $(this).attr("id");
	       	  			$(".memberManagementBorderBottom").html("是否拒绝"+userName+"的加入");
	       	  			$(".modalShow").show();
	       	  			$(".mem_yes").unbind("click");
	       	  			$(".mem_yes").click(function(){
	       	  				updateMember(id,userId,0);
	       	  			});
	       	  		});
	       	  		//接受
	       	  		$(".Newfriend_btn_ok").click(function(){
	       	  			var userId =$(this).parents(".Newfriend_btn").attr("id");
	       	  			var id = $(this).attr("id");
	       	  			enterpriseInfo(id,userId);
	       	  			/*if(five){
	       	  				enList(id,userId);
	       	  				if(six){
	       	  					updateMember(id,userId,1);
	       	  				}
	       	  			}*/
	       	  		});
	       	  	}else{
	       	  		$(".qsy").show();
	       	  	}
	       	  }
	       	 }
	    });
	
}
function enList(id,userId){
	var data = {"userId":userId};
	 $.ajax({
		   type : "get",
	       url:dataLink+"enterprise/enterpriseMyList", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	  if(!isnull(json.enList) && json.enList.length>5){
	    	   	  	   $(".memberUndonePopups p").html("用户最多可加入或创建6个轻企");
	    	   	   	   $(".memberUndonePopups").show();
				 		setTimeout(function(){
							$(".memberUndonePopups").hide();
						},2000);
	    	   	  }else{
	    	   	  	updateMember(id,userId,1);
	    	   	  }
	    	   	 }
	    	  }
	   });
}
function enterpriseInfo(id,userId){
	
	var data = {"leId":leId};
	 $.ajax({
		   type : "get",
	       url:dataLink+"enterprise/enterpriseInfo", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	     var item = json.enterpriseInfo;
	    	   	   if(item.memberCount>4 && isnull(item.type)){
		    	   	   	if(item.type!=1){
		    	   	   		$(".memberUndonePopups p").html("您还未认证，未认证轻企最多可加入5人");
		    	   	   	   $(".memberUndonePopups").show();
					 		setTimeout(function(){
								$(".memberUndonePopups").hide();
							},2000);
		    	   	   	}else{
		    	   	   	enList(id,userId);
		    	   	   }
	    	   	   }else{
	    	   	   	enList(id,userId);
	    	   	   }
	    	   }
	    	  }
	     });
}
	    	   	
function updateMember(id,userId,lemState){
	 var data = {"leId":leId,"id":id,"userId":userId,"lemState":lemState};
	 $.ajax({
		   type : "get",
	       url:dataLink+"member/updateMember", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	       	  if(json.msg="success"){
	       	  	window.location.href="requestjoin.html";
	       	  }
	       	 }
	      });
}
