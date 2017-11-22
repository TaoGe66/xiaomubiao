var userId = localStorage.getItem("team_userId");
var friendId =  localStorage.getItem("team_friendId");
$(function(){
//	点击弹窗灰色区域弹窗消失	
	$(".Userdata_alert_bg").click(function(){
		$(".Userdata_alert").hide();
		$(".Userdata_remarks").hide();
		$(".Userdata_alert_two").hide();
	});
//	edit by zhengjunting 2017/06/12 点击分组弹出弹窗
	$(".Userdata_list em").click(function(){
		$(".Userdata_alert").show();
	});
//	edit by zhengjunting 2017/06/12 点击取消分组弹窗隐藏
	$(".Userdata_alert_btnno").click(function(){
		$(".group_list").html("");
		$(".Userdata_alert").hide();
		$(".Userdata_remarks").hide();
		$(".Userdata_alert_two").hide();
	});
	//备注
	$(".user_remarks").click(function(){
		$(".Userdata_remarks").show();
	});
//	edit by zhengjunting 2017/06/12 点击确定分组弹窗隐藏
/*	$(".Userdata_alert_btnok").click(function(){
		if(!$(".Userdata_alert_button").val()==""){
			$(".Userdata_list em").html($(".Userdata_alert_button").val());
		}else{
			$(".Userdata_list em").html($(".Userdata_alert_current").html())
		}
		$(".Userdata_alert").hide();
	});*/
//	edit by zhengjunting 2017/06/12 点击新增按钮
	$(".Userdata_alert_newadd").click(function(){
		$(".Userdata_alert_newadd").hide();
		$(".Userdata_alert_button").show();
	})
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
	getUserInfo();
	getDelStatus();
	$(".Userdata_delete").click(function(){
		$(".Userdata_alert_two").show();
	});
	
	$(".Userdata_alert_ok").click(function(){
		delFriends();
	});
});

function delFriends(){
	
	var data = {"userId":userId,"fId":friendId}
	 $.ajax({
		   type : "post",
	       url:dataLink+"friend/delFriends", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	     	window.location.href="friends.html";
	    	   }
	    	  }
	    });
	
}

function getUserInfo(){
	
		
	var data = {"userId":userId,"friendId":friendId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"userwx/getFriendInfoById", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	var userInfo = json.user;
	    	   	$(".userName").html(userInfo.userName);
	    	   	$(".note").html(userInfo.note);
	    	   	var sex ="保密";
	    	   	if(userInfo.sex==0){
	    	   		sex ="女";
	    	   	}else if(userInfo.sex==1){
	    	   		sex ="男";
	    	   	}
	    	   	$(".sex").html(sex);
	    	   	$(".birthday").html(userInfo.birthday);
	    	   	$(".address").html(userInfo.address);
	    	   	if(isnull(userInfo.groupName)){
	    	   		$(".groupName").html("无");
	    	   	}else{
	    	   	    $(".groupName").html(userInfo.groupName);	
	    	   	}
	    	   }
	    	  }
	    });
}


//校验组名称是否存在
function check_name(){
	var createBy=localStorage.getItem("team_userId");
	var name=$("#block_sort").val();//组名称
	if (centen(name,4,16)) {
		var data={"createBy":createBy,"name":name}
		$.ajax({
			type : "GET",
			url:dataLink+"group/checkGroupInfo",  
			dataType:'json',  
			data:data, 
			contentType: "application/json; charset=utf-8",
			success:function(json){
				if(json.msg=="success"){
					var count= json.count;
					if (count==1) {
						$(".Userdata_error").html("组名称不可以重复");
						$(".Userdata_error").show();
						setTimeout(function(){$(".Userdata_error").hide();},2000);
					}else{
						saveGroup();
					}
					
				}
			},  
			timeout:3000  
		}); 
	}else{
		$(".Userdata_error").html("名称为2~8个字");
		$(".Userdata_error").show();
		setTimeout(function(){$(".Userdata_error").hide();},2000);
	}
}


//保存组名称
function saveGroup(){
	var createBy=localStorage.getItem("team_userId");
	var name=$("#block_sort").val();//组名称
	var Json='"name":"'
		+name
		+'","createBy":"'
		+createBy
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	var data='{'+start+'}';
	$.ajax({
		  url:dataLink+"group/saveGroup",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     contentType: "application/json; charset=utf-8",
	     success:function(result){
	   	  if(result.msg=="success"){
	   		var item=result.group;
	   		localStorage.setItem("team_gId",item.id);
	   		//保存组和好友关系
	   		updateFriend();
	   	  }
	     },  
	     timeout:3000  
	});
	
}


//修改好友和组关系
function updateFriend(){
	var friendId =  localStorage.getItem("team_friendId");
	var gId=localStorage.getItem("team_gId");
	var userId=localStorage.getItem("team_userId");
	var Json='"fId":"'
		+friendId
		+'","userId":"'
		+userId
		+'","gId":"'
		+gId
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	var data='{'+start+'}';
	$.ajax({
		  url:dataLink+"group/updateFriend",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     contentType: "application/json; charset=utf-8",
	     success:function(result){
	   	  if(result.msg=="success"){
	   		window.location.href="userdata.html";
	   	  }
	     },  
	     timeout:3000  
	});
	
}


//组列表
function group_list(){
	var userId=localStorage.getItem("team_userId");
		var data={"userId":userId}
		$.ajax({
			type : "GET",
			url:dataLink+"friend/getGroupByUserId",  
			dataType:'json',  
			data:data, 
			contentType: "application/json; charset=utf-8",
			success:function(json){
				if(json.msg=="success"){
					var list= json.group;
					if (list.length>0) {
						for (var i = 0; i < list.length; i++) {
							var item=list[i];
							var gId=localStorage.getItem("team_gId");
							if (gId==item.gId) {
								var html='<li class="Userdata_alert_current"><i style="display:none;">'+item.gId+'</i>'+item.name+'</li>';
							}else{
								var html='<li><i style="display:none;">'+item.gId+'</i>'+item.name+'</li>';
							}
							$(".group_list").append(html);
						}
					}
//					edit by zhengjunting 2017/06/12  分组弹窗选项切换
					$(".Userdata_alert_con li").click(function(){
						$(this).addClass("Userdata_alert_current").siblings().removeClass("Userdata_alert_current")
					});
				}
			},  
			timeout:3000  
		}); 
}

//修改组确定
$(".Userdata_alert_btnok").click(function(){
	var name=$("#block_sort").val();//组名称
	if (isnull(name)) {
		var team_gId= $(".Userdata_alert_current").find("i").html();
		localStorage.setItem("team_gId",team_gId);
		updateFriend();
	}else{
		check_name();
	}
});

//修改备注
$(".").click(function(){
	
	
});
//组列表
$(".user_groupName").click(function(){
	window.location.href = "add_group.html"
});
/*$(".groupName").click(function(){
	group_list();
});*/



function getDelStatus(){
	var data = {"userId":userId,"fId":friendId};
	$.ajax({
			type : "GET",
			url:dataLink+"friend/getDelStatus",  
			dataType:'json',  
			data:data, 
			contentType: "application/json; charset=utf-8",
			success:function(json){
				if(json.msg=="sizebest"){
					$(".Userdata_footer").show();
				}else{
					$(".Userdata_footer").hide();
				}
			}
	});
}


