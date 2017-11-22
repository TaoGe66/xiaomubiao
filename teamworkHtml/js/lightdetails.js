var leId = localStorage.getItem("team_leId");
var userId = localStorage.getItem("team_userId");
$(function(){
//	点击详情按钮
	$(".Light_btn").click(function(){
		if($(".Light_btn").hasClass("Light_btn_current")){
			$(".Light_hide").hide();
			$(".Light_btn").removeClass("Light_btn_current");
			
		}else{
			$(".Light_hide").show();
			$(".Light_btn").addClass("Light_btn_current");
		}

	});
	
//	点击弹窗取消按钮
	$(".Index_overlook_btnone").on("click",function(){
		$(".event_summary").hide();
		$(".Index_overlook").hide();
	});
	$(".Index_disbandlook_btnone").on("click",function(){
		$(".event_summary").hide();
		$(".Index_disbandlook").hide();
	});
	//add  by zhengjunting  20170918 轻企二维码
	$(".Light_icon").on("click",".Light_icon_one",function(){
	   	window.location.href="qrcode.html";
	});
	//add  by zhengjunting  20170918 认证
	$(".Light_icon").on("click",".Light_icon_two",function(){
	   	window.location.href="authentication.html";
	});
	//add  by zhengjunting  20170918 写公告
	$(".Light_icon").on("click",".Light_icon_three",function(){
	   	window.location.href="notice.html";
	});
	//add  by zhengjunting  20170918 成员管理
	$(".Light_icon").on("click",".Light_icon_four",function(){
	   	window.location.href="memberManagement.html";
	});
	//add  by zhengjunting  20170918 管理员管理
	$(".Light_icon").on("click",".Light_icon_five",function(){
	   	window.location.href="admin.html";
	});
	//点击退出轻企
	$(".Index_overlook_btntwo").click(function(){
		isMemberDel();
	});
	enterpriseInfo();
	
});

function isMemberDel(){
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
	       	  		delMember();
	       	  	}else{
	       	  		$(".event_summary").hide();
		            $(".Index_overlook").hide();
	       	  		$(".Light_alert").show();
	       	  		setTimeout(function(){
					     $(".Light_alert").hide();
				    },2000);
	       	  	}
	       	  }
	      }
	 });
}


function delMember(){
	 var data = {"leId":leId,"userId":userId};
	 $.ajax({
		   type : "get",
	       url:dataLink+"member/delMember", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	       	  if(json.msg="success"){
	       	  	 var dataa ={"userId":userId,"leId":0};
	       	  	 updateUserInfo(dataa);
	       	  }
	       	 }
	    });
}

function updateUserInfo(data){
	 $.ajax({
		   type : "post",
	       url:dataLink+"userwx/updateUserInfo", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	window.location.href="index.html";
	    	   }
	       }
	 });
}

function enterpriseInfo(){
	
	var data = {"userId":userId,"leId":leId};
	 $.ajax({
		   type : "get",
	       url:dataLink+"enterprise/enterpriseInfo", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	var item = json.enterpriseInfo;
	    	   	$(".Light_head_img").html('<img src="'+imageHeadSrc+item.leLogo+'" alt="" />');
	    	   	if(item.type==1){
	    	   	     $(".Light_head i").html(item.name+'<em class="Dynamic_car"></em>');
	    	   	}else{
	    	   		 $(".Light_head i").html(item.name);
	    	   	}
	    	    $(".Light_content_one i").html(item.fullName);
	    	   	$(".Light_content_two i").html(item.usercName);
	    	   	$(".Light_content_three i").html(item.memberCount);
	    	   	var html = '';
	    	   	if(item.roleType==0){
	    	   	  $(".Light_hide").html('<li class="Light_content_four"><span>企业概况 :&nbsp;</span><i>'+item.leDetails+'</i></li>');
	    	   		html += '<li class="Light_icon_one">轻企二维码</li>';
	    	   	    if(item.type!=1){
	    	   	    	html += '<li class="Light_icon_two">认证</li>';
	    	   	    	//$(".light_jiesan").show();
	    	   	    }
	    	   		html += '<li class="Light_icon_three">写公告</li>';
	    	   		html += '<li class="Light_icon_four">成员管理 <em></em></li>';
	    	   		html += '<li class="Light_icon_five">管理员管理<em></em></li>';
	    	   		
	    	   	}else if(item.roleType==1){
	    	   	  $(".Light_hide").html('<li class="Light_content_four"><span>企业概况 :&nbsp;</span><i>'+item.leDetails+'</i></li>');
	    	   		html += '<li class="Light_icon_one">轻企二维码</li>';
	    	   		html += '<li class="Light_icon_three">写公告</li>';
	    	   		html += '<li class="Light_icon_four">成员管理 <em></em></li>';
	    	   	}else if(item.roleType==2){
	    	   	  $(".Light_hide").html('<li class="Light_content_four"><span>企业概况 :&nbsp;</span><i>'+item.leDetails+'</i></li>');
//	    	   	  $(".Light_hide").append('<li class="Light_content_five"><em>退出轻企</em></li>');	
                  $(".light_tuichu").show();
	    	   		html += '<li class="Light_icon_one">轻企二维码</li>';
	    	   	}
	    	   	//add  by zhengjunting  20170918 退出轻企
				$(".light_tuichu").on("click","em",function(){
					$(".event_summary").show();
					$(".Index_overlook").show();
			
				});
				//add by zhanghaitao 2017/10/25 解散轻企
				$(".light_jiesan").on("click","em",function(){
					$(".event_summary").show();
					$(".Index_disbandlook").show();
			
				});
				if(item.isTerm==1){
	    	   		$(".Light_eorry").html("有效期至："+Time(item.term)+"&nbsp;&nbsp;去年审");
	    	   	 }
				
				if(item.type!=1 && item.roleType==0){
					$(".Light_head_img").append("<span style='display:block;'></span>");
				}
				
	    	   	$(".Light_icon").html(html);
	    	   	if(item.roleType==0 || item.roleType==1){
	    	   		memberRed();
	    	   	}
	    	   	$(".Light_head_img span").click(function(){
	    	   		window.location.href="editqinqi.html";
	    	   	});
	    	   	
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
	       	  		$(".Light_icon_four em").show();
	       	  	}
	       	  }
	       	 }
	    });
}