$(function(){
/*//	点击分类弹出列表
	$(".Project_ul .Project_title").click(function(){
		if($(this).parent().find(".Project_list").css("display")=="block"){
			$(this).parent().find(".Project_list").hide();
			$(this).removeClass("Project_global_current");
		}else{
			$(this).parent().find(".Project_list").show();
			$(this).addClass("Project_global_current");
		}
			
	});*/
	//点击分类弹出列表
	$(".Project_ul").on("click",".Project_title",function(){
		if($(this).parent().find(".Project_list").css("display")=="inline"){
			$(this).parent().find(".Project_list").hide();
			$(this).removeClass("Project_global_current");
		}else{
			var  followUserId=$(this).parent().find("em").eq(0).html();
			 var clana = "#"+$(this).parent().find(".Project_list").attr("id");
			  if (followUserId==userId) {
				  item_list(followUserId,null,clana);
			  }else {
			  item_list(followUserId,1,clana);
			  }
			$(this).parent().find(".Project_list").css("display","inline");
			$(this).addClass("Project_global_current");
		}
			
	});
		//点击选择类型跳转不同的页面
	$("#li1").click(function(){
		localStorage.removeItem("team_eId");
		localStorage.setItem("team_physics",1);
		window.location.href="serverelease.html";
	});
	$("#li2").click(function(){
		localStorage.removeItem("team_eId");
		window.location.href="servereleasethree.html";
	});
	$("#li3").click(function(){
		localStorage.removeItem("team_eId");
		window.location.href="servereleasetwo.html";
	});

});

var userId = localStorage.getItem("team_userId");
var leId = localStorage.getItem("team_leId");
follow_list();
enterpriseInfoByFrist();
//进行中 		wuchao
function  follow_list(page){
	var data={"createBy":userId,"leId":leId}
	$.ajax({
		  type : "GET",
	      url:dataLink+"enterprise/listFollowUser",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  var list=json.follows;
	    		  if (list.length>0) {
	    			  var html ='<li class="Project_global"><p class="Project_title">我的轻企项目<i></i></p>';
	    			  	  html+='<em style="display:none;">'+userId+'</em>';
	    			  	  html +='<ul class="Project_list" id="item"></ul></li>';
	    			  for (var i = 0; i < list.length; i++) {
	    				  var item=list[i];
	    				  html +='<li class="Project_global"><p class="Project_title">'+item.userName+'轻企项目<i></i></p>';
	    				  html+='<em style="display:none;">'+item.userId+'</em>';
	    			  	  html +='<ul class="Project_list" id="item'+i+'"></ul></li>';
	    			  }
	    			  $(".Project_ul").append(html);
				}else{
				var html ='<li class="Project_global"><p class="Project_title">我的轻企项目<i></i></p>';
   			  	  html+='<em style="display:none;">'+userId+'</em>';
   			  	  html +='<ul class="Project_list" id="item"></ul></li>';
   			  	$(".Project_ul").append(html);
				}
	    		  //打开列表
	    		  

	    	  }
	      },  
	      timeout:3000  
	 }); 
}


function  item_list(followUserId,type,clana){
	var data={"userId":followUserId,"leId":leId,"type":type}
	$.ajax({
		  type : "GET",
	      url:dataLink+"enterprise/listProject",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  var list=json.list;
	    		  if (list.length>0) {
	    			  $(clana).html("");
	    			  for (var i = 0; i < list.length; i++) {
						var item=list[i];
						var imge_class="";
						if (item.eventType ==0) {
							imge_class="";
						}else if(item.eventType ==1){
							imge_class="Project_list_two";
						}else if(item.eventType ==2){
							imge_class="Project_list_three";
						}
						var	dutyName="";
						if (item.eventType ==0 || item.eventType ==2 || isnull(item.dutyName)) {
							dutyName=item.userName;
						}else{
							dutyName=item.dutyName;
						}
						var html ='<li class="Project_list_inner"><div class="Project_kong">';
						html +='<em style="display:none;">'+item.eventType+'</em>';
						html +='<em style="display:none;">'+item.eId+'</em>';
						html +='<p class="Project_list_title '+imge_class+'">'+item.name+'</p>';
						html +='<p class="Project_list_name">负责人:'+dutyName+'</p>';
						html +='<p class="Project_list_con"><span>'+sameDate(item.startTime,item.endTime)+'</span>';
						if (item.eventType !=2) {
							html +='<i>健康度: <em>'+(item.percent*100).toFixed(0)+'%</em></i>';
						}
						html +='</p></div></li>';
						$(clana).append(html);
					}
	    		  }
	    			//跳转事件详情
	    		  $(".Project_list_inner").click(function(){
	    			var  eventType=$(this).find("em").eq(0).html();
	    			var  eId= $(this).find("em").eq(1).html();
	    			localStorage.setItem("team_eId",eId);
	    			if (eventType==0) {
	    				window.location.href="eventdetailsone.html";
					}else if(eventType==1){
						get.joinInfo();
						
					}else if(eventType==2){
						get.eventInfo();
					}
	    			
	    		  });
	    	  }
	      },  
	      timeout:3000  
	 }); 
	 
}


var get={
		joinInfo:function(){
		var eId = localStorage.getItem("team_eId");
		var userId = localStorage.getItem("team_userId");
			var param={"eId":eId,"userId":userId};
	    	var url=dataLink+"event/joinEventIsSelect";
	    	_asyn(url,param,"get",get.jOk);
		},
		jOk:function(json,code){
			console.info(json);
			if(json.msg=="success"){
				var eId = localStorage.getItem("team_eId");
				if(json.isSelected==0&&json.roleType==0){
					_g("servicedetailsone.html?eId="+eId);
				}else if(json.isSelected==1){
					_g("svc_red_eventdetails.html?eId="+eId);
				}
			}
		},
		eventInfo:function(){
		var eId = localStorage.getItem("team_eId");
		var userId = localStorage.getItem("team_userId");
			var param={"eId":eId,"userId":createBy};
	    	var url=dataLink+"event/eventInfo";
	    	_asyn(url,param,"get",get.eOk);
		},
		eOk:function(json,code){
			if(json.msg=="success"){
				if(json.event.type==2){
					var eId = localStorage.getItem("team_eId");
					if(json.roleType==1){
						window.location.href="view_participation.html?eId="+eId;
					}else if(json.roleType==0){
						window.location.href="sign_in.html?eId="+eId;
					}
				}
			}
		}
	}