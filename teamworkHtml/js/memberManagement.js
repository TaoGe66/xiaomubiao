var leId = localStorage.getItem("team_leId");
$(function(){
	$(".memberJoin").click(function(){
		window.location.href="requestjoin.html";
	});
	$(".del_hide").click(function(){
		$(".modalShow").hide();
	});
	memberList();
	memberRed();
});

function memberList(){
	
	 var data = {"leId":leId,"roleTypeStr":2};
	 $.ajax({
		   type : "get",
	       url:dataLink+"member/memberList", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	       	  if(json.msg="success"){
	       	  	if(!isnull(json.memberList) && json.memberList.length>0){
	       	  		$(".memberQuantity_count").html(json.memberList.length);
	       	  		for(i=0;i<json.memberList.length;i++){
	       	  			var item = json.memberList[i];
	       	  			var headUrl = isnull(item.headUrl) ? 'img/touxiang_14.png' :  imageHeadSrc+item.headUrl;
	       	  			var html  ='<li>';
	       	  			    html +='<div class="memberList memberManagementBorderBottom memberPublic" id="'+item.userId+'">';
	       	  			    html +='<img src="'+headUrl+'"/><span>'+item.userName+'</span><img class="del_member" src="img/delete_15.png"/>';
	       	  		        html +='</div></li>';
	       	  		  $(".member_listall").append(html);     
	       	  		}
	       	  		$(".memberList").click(function(){
	       	  			var memId = $(this).attr("id");
	       	  			localStorage.setItem("team_memId",memId);
	       	  			window.location.href="member.html";
	       	  			return false;
	       	  		});
	       	  		$(".del_member").click(function(){
	       	  			var userId = $(this).parents(".memberList").attr("id");
	       	  			var userName = $(this).parents(".memberList").find("span").html();
	       	  			isMemberDel(userId,userName);
	       	  			return false;
	       	  		});
	       	  		
	       	  	}else{
	       	  		$(".memberQuantity_count").html("0");
	       	  		$(".qsy").show();
	       	  	}
	       	  }
	       	 }
	    });
}
function isMemberDel(userId,userName){
	 var data = {"leId":leId,"userId":userId};
	 $.ajax({
		   type : "get",
	       url:dataLink+"member/isMemberDel", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	       	  if(json.msg="success"){
	       	  	//能删除
	       	  	if(json.isDel==1){
	       	  		$(".del_name").html("是否删除"+userName);
	       	  		$(".del_yes").click(function(){
	       	  			delMember(userId);
	       	  		});
	       	  		$(".modalShow").show();
	       	  	}else{
	       	  		$(".memberUndonePopups").show();
	       	  		setTimeout(function(){
					     $(".memberUndonePopups").hide();
				    },2000);
	       	  	}
	       	  	
	       	  }
	       	 }
	    });
}

function delMember(userId){
	 var data = {"leId":leId,"userId":userId};
	 $.ajax({
		   type : "get",
	       url:dataLink+"member/delMember", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	       	  if(json.msg="success"){
	       	  	window.location.href="memberManagement.html";
	       	  }
	       	 }
	    });
}



function memberRed(){
	 var data = {"leId":leId,"lemState":2,"roleTypeStr":2};
	 $.ajax({
		   type : "get",
	       url:dataLink+"member/memberListAll", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	       	  if(json.msg="success"){
	       	  	if(!isnull(json.memberList) && json.memberList.length>0){
	       	  		$(".red_mem").addClass("menberJoinDots");
	       	  	}
	       	  }
	       	 }
	    });
}
