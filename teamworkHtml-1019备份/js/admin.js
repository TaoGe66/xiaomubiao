var leId = localStorage.getItem("team_leId");
$(function(){
	//跳转添加管理员页面
	$(".memberJoin").click(function(){
		window.location.href="addadmin.html";
	});
	
	//取消管理员职务
	$(".admin_cancel").click(function(){
		$(".modalShow").show();
	});
	$(".admin_yes").click(function(){
		$(".modalShow").hide();
		$(".memberDeletePopups").fadeIn(0).delay(3000).fadeOut(0);
	});
	$(".admin_no").click(function(){
		$(".modalShow").hide();
	});
	memberList();
});



function memberList(){
	
	 var data = {"leId":leId,"roleTypeStr":1};
	 $.ajax({
		   type : "get",
	       url:dataLink+"member/memberList", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	       	  if(json.msg="success"){
	       	  	if(!isnull(json.memberList) && json.memberList.length>0){
	       	  		$(".memberQuantity span").html(json.memberList.length);
	       	  		for(i=0;i<json.memberList.length;i++){
	       	  			var item = json.memberList[i];
	       	  			var headUrl = isnull(item.headUrl) ? 'img/touxiang_14.png' :  imageHeadSrc+item.headUrl;
	       	  			var html  ='<li class="admin_list" id="'+item.id+'">';
	       	  			    html +='<div class="memberList memberPublic">';
	       	  			    html +='<img src="'+headUrl+'"/>';
	       	  			    html +='<span class="admin_name">'+item.userName+'</span>';
	       	  			    html +='<span class="admin_cancel" id="'+item.userId+'" >取消职务</span>';
	       	  			    html +='</div></li>';
	       	  			
	       	  		  $(".member_listall").append(html);     
	       	  		}
	       	  		$(".admin_cancel").click(function(){
	       	  			var id =$(this).parents(".admin_list").attr("id");
	       	  			var name =$(this).parents(".admin_list").find(".admin_name").html();
	       	  			var userIds =$(this).attr("id");
	       	  			$(".admin_yes").unbind("click");
	       	  			$(".admin_yes").click(function(){
	       	  				updateMemberOne(id,name,userIds);
	       	  			});
	       	  			$(".modalShow").show();
	       	  		});
	       	  		
	       	  	}else{
	       	  		$(".memberQuantity span").html("0");
	       	  		$(".qsy").show();
	       	  	}
	       	  }
	       	 }
	    });
}
function updateMemberOne(id,name,userIds){
	 var data = {"id":id,"roleType":2,"leId":leId,"userId":userIds};
	 $.ajax({
		   type : "get",
	       url:dataLink+"member/updateMemberOne", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	       	  if(json.msg="success"){
	       	  	   $(".modalShow").hide();
	       	  	   $(".memberDeletePopups p").html(name+"已不是管理员");
	       	  	   $(".memberDeletePopups").show();
	       	  		setTimeout(function(){
					     window.location.href="admin.html";
				    },2000);
	       	    }
	       	  }
	    });
}
