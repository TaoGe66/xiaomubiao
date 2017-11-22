var userId = localStorage.getItem("team_userId");
$(function(){
	 $(".Friends_null").hide();	
//	edit by zhengjunting 2017/06/12 点击新好友跳转
	$(".Friends_new").click(function(){
		window.location.href="newfriend.html";
	})
//	edit by zhengjunting 2017/06/12  点击分组
	getGroup();
	getHailNews();//是否有new
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
});

function getGroup(){
	
	var data = {"userId":userId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"friend/getGroupByUserId", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	                if(!isnull(json.group)&&json.group.length>0){
	                	var html = '<li class="Friends_global" id=""><p>最近联系</p><ul class="globalTen"></ul></li>';
	                	    html += '<li class="Friends_global" id=""><p>全部好友</p><ul class="globalAll"></ul></li>';
	                	   
	                	for(i=0;i<json.group.length;i++){
	                		var item = json.group[i];
	                		    html += '<li class="Friends_global" id="'+item.gId+'"><p>'+item.name+'</p><ul class="global'+i+'"></ul></li>';
	                	}
	                	$(".Friends_ul").append(html);
	                
	                	$(".Friends_global p").click(function(){
	                		
							$(this).toggleClass(".Friends_current");
							if($(this).hasClass("Friends_current")){
								$(this).next().hide();
								$(this).removeClass("Friends_current").css({"border-bottom":"1px solid #e5e5e5"});
								$(this).parent().css({"height":"3.167rem"});
							}else{
								$(this).next().show();
								$(this).addClass("Friends_current").css({"border":"0 none"});
								$(this).parent().css({"height":"auto"});
								var clana = "."+$(this).parents(".Friends_global").find("ul").attr("class");
		                		var id = $(this).parents(".Friends_global").attr("id");
		                		if(clana==".globalTen"){
		                			getLatelyFriendList();
		                		}else{
		                			getFriendGroup(clana,id);
		                		}
		                		
							}
	                    });
	                	
	                } else{
	                	var html = '<li class="Friends_global" id=""><p>最近联系</p><ul class="globalTen"></ul></li>';
	                	    html += '<li class="Friends_global" id=""><p>全部好友</p><ul class="globalAll"></ul></li>';
	                	   
	                	$(".Friends_ul").append(html);
	                	$(".Friends_global p").click(function(){
							$(this).toggleClass(".Friends_current");
							if($(this).hasClass("Friends_current")){
								$(this).next().hide();
								$(this).removeClass("Friends_current").css({"border-bottom":"1px solid #e5e5e5"});
								$(this).parent().css({"height":"3.167rem"});
								$(".Friends_null").hide();
							}else{
								$(this).next().show();
								$(this).addClass("Friends_current").css({"border":"0 none"});
								$(this).parent().css({"height":"auto"});
								var id = $(this).parents(".Friends_global").attr("id");
								var clana = "."+$(this).parents(".Friends_global").find("ul").attr("class");
		                		if(clana==".globalTen"){
		                			getLatelyFriendList();
		                		}else{
		                			getFriendGroup(clana,id);
		                		}
							}
						
	                    });
	                }
	
	             }
	    	  }
	    });
}


function getFriendGroup(clana,gId){
	
	var data = {"userId":userId,"state":1,"gId":gId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"friend/getFriendGroupById", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	$(clana).html("");
	    	   	  if(!isnull(json.groupFriend)&&json.groupFriend.length>0){
	    	   	  	for(i=0;i<json.groupFriend.length;i++){
	    	   	      	var item = json.groupFriend[i];
	    	   	      	var headUrl = isnull(item.headUrl)? "img/img03.png" : (imageHeadSrc+item.headUrl);
	    	   	      	var note = isnull(item.note)?"":item.note;
	    	   	      	var html = '<li class="getFriend" id="'+item.fId+'"><img src="'+headUrl+'" alt="" /><div class="Friends_list">';
	    	   	      	    html+= '<span>'+item.userName+'</span><i>'+note+'</i></div></li>';
	    	   	      	$(clana).append(html);
	    	   	  	}
	    	   	  	$(".getFriend").click(function(){
	    	   	  		var friendId = $(this).attr("id");
	    	   	  		localStorage.setItem("team_friendId",friendId);
	    	   	  		window.location.href="userdata.html";
	    	   	  	});
	    	   	  }else{
	    	   	  	if(isnull(gId)){
	    	   	  		 $(".Friends_null").show();	
	    	   	  	}
	    	   	    
	    	   	  }
	    	   	
	    	   }
	    	  }
	    });
	
	
}


//好友消息
function getHailNews(){
	
	var data = {"sendId":userId,"nType":0,"isRed":0}
	 $.ajax({
		   type : "get",
	       url:dataLink+"news/getNewsByUserid", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
		    	   if(!isnull(json.newsList)&&json.newsList.length>0){
		    			$(".Friends_new_inner").show();
		    	   }
	    	   }
	       }
	  });
}


function getLatelyFriendList(){
	
	var data = {"userId":userId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"friend/getLatelyFriendList", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	$(".globalTen").html("");
	    	   	  if(!isnull(json.friendsList)&&json.friendsList.length>0){
	    	   	  	for(i=0;i<json.friendsList.length;i++){
	    	   	      	var item = json.friendsList[i];
	    	   	      	var headUrl = isnull(item.headUrl)? "img/img03.png" : (imageHeadSrc+item.headUrl);
	    	   	      	var note = isnull(item.note)?"":item.note;
	    	   	      	var html = '<li class="getFriend" id="'+item.fId+'"><img src="'+headUrl+'" alt="" /><div class="Friends_list">';
	    	   	      	    html+= '<span>'+item.userName+'</span><i>'+note+'</i></div></li>';
	    	   	      	$(".globalTen").append(html);
	    	   	  	}
	    	   	  	$(".getFriend").click(function(){
	    	   	  		var friendId = $(this).attr("id");
	    	   	  		localStorage.setItem("team_friendId",friendId);
	    	   	  		window.location.href="userdata.html";
	    	   	  	});
	    	   	  }/*else{
	    	   	  	if(isnull(gId)){
	    	   	  		 $(".Friends_null").show();	
	    	   	  	}
	    	   	    
	    	   	  }*/
	    	   	
	    	   }
	    	  }
	    });
	
	
}