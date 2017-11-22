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
	    		  //关注我的
	    		  var listToMyFollow= json.listToMyFollow;
	    		  if (listToMyFollow.length>0) {
	    			  $(".follow_me").html("");
	    			  $(".Select_global_listone").show();
					for (var i = 0; i < listToMyFollow.length; i++) {
						var toMyFollow=listToMyFollow[i];
						var headUrl= isnull(toMyFollow.headUrl)?"img/img03.png":imageHeadSrc+toMyFollow.headUrl;
						var note=isnull(toMyFollow.note)?"":toMyFollow.note;
						var html ='<li><em><i style="display:none;">'+toMyFollow.userId+'</i>';
							html +='<i style="display:none;">'+toMyFollow.userName+'</i>';
							html +='<i style="display:none;">'+headUrl+'</i>';
							html +='</em> <img src="'+headUrl+'" alt="" />';
							html +='<div class="Select_list"><span>'+toMyFollow.userName+'</span>';
							html +='<i>'+note+'</i></div></li>';
							$(".follow_me").append(html);
					}
				}
	    		  //我关注的
	    		  var listMyToFollow=json.listMyToFollow;
	    		  if (listMyToFollow.length>0) {
	    			  $(".me_follow").html("");
	    			  $(".Select_global_listtwo").show();
					for (var i = 0; i < listMyToFollow.length; i++) {
						var myToFollow=listMyToFollow[i];
						var headUrl= isnull(myToFollow.headUrl)?"img/img03.png":imageHeadSrc+myToFollow.headUrl;
						var note=isnull(myToFollow.note)?"":myToFollow.note;
						var html ='<li><em><i style="display:none;">'+myToFollow.userId+'</i>';
							html +='<i style="display:none;">'+myToFollow.userName+'</i>';
							html +='<i style="display:none;">'+headUrl+'</i>';
							html +='</em><img src="'+headUrl+'" alt="" />';
							html +='<div class="Select_list"><span>'+myToFollow.userName+'</span>';
							html +='<i>'+note+'</i></div></li>';
							$(".me_follow").append(html);
					}
				}
	    		  //轻企成员
	    		 var memberList=json.memberList;
	    		 if (memberList.length>0) {
	    			 $(".light_all").html("");
						for (var i = 0; i < memberList.length; i++) {
							var member=memberList[i];
							var headUrl= isnull(member.headUrl)?"img/img03.png":imageHeadSrc+member.headUrl;
							var note=isnull(member.note)?"":member.note;
							if (userId !=member.userId) {
								var html ='<li><em><i style="display:none;">'+member.userId+'</i>';
								html +='<i style="display:none;">'+member.userName+'</i>';
								html +='<i style="display:none;">'+headUrl+'</i>';
								html +='</em><img src="'+headUrl+'" alt="" />';
								html +='<div class="Select_list"><span>'+member.userName+'</span>';
								html +='<i>'+note+'</i></div></li>';
								$(".light_all").append(html);
							}
						}
					}
	    		 
	    	  }
	      },  
	      timeout:3000  
	 }); 
}
//点击分组出现下拉列表
$(".Select_global").on("click","p",function(){
	   $(this).toggleClass(".Select_current");
	   if($(this).hasClass("Select_current")){
		   $(this).next().hide();
		   $(this).removeClass("Select_current").css({"border-bottom":"1px solid #e5e5e5"});
		   $(this).parent().css({"height":"3.167rem"});
	   }else{
		   $(this).next().show();
		   $(this).addClass("Select_current").css({"border":"0 none"});
		   $(this).parent().css({"height":"auto"});
	   }
	   
   });
//选择好友
$(".Select_global").on("click","li",function(){
		$(".Select_ul em").removeClass("Select_global_current");
		$(this).find("em").addClass("Select_global_current");
});

//确认选择好友
$(".Select_btn_confirm").click(function(){
	var userId=localStorage.getItem("team_userId");
	var fgId=$(".Select_global_current").find("i").eq(0).html();
	var fg_Name=$(".Select_global_current").find("i").eq(1).html();
	var fg_Logo=$(".Select_global_current").find("i").eq(2).html();
	if (!isnull(fgId)) {
		localStorage.setItem("team_fgId",fgId);
		var isCyc=localStorage.getItem("team_isCyc");
		//team_isCyc为0是单次，若为1 是周期
		if (isCyc==0) {
			save_item(fgId);
		}else if(isCyc==1){
			save_Cycitem(fgId);
		}
		
	}
});
//取消
$(".Select_btn_cancel").click(function(){
	var type = localStorage.getItem("team_type");
	if (type == 0) {
		localStorage.removeItem("team_type");
		window.location.href = "eventdetailsone.html";
	} else if (type == 1) {
		localStorage.removeItem("team_type");
		window.location.href = "svc_red_eventdetails.html";
	}
});

//保存单次事项
function save_item(fgId){
	var eId=localStorage.getItem("team_eId");
	var userId=localStorage.getItem("team_userId");
	var eiId=localStorage.getItem("team_eiId");
	$(".event_summary").show();
	$(".event_summary1").show();
	var Json='"eId":"'
		+eId
		+'","eiId":"'
		+eiId
		+'","isCycle":"'
		+0
		+'","createBy":"'
		+userId
		+'","dutyId":"'
		+fgId
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	var data='{'+start+'}';
	$.ajax({
		url : dataLink + "item/updateClient",
		type : "POST",
		data : data,
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		success : function(result) {
			if (result.msg == "success") {
				localStorage.removeItem("team_fgId");
				localStorage.removeItem("team_isCyc");
				var type = localStorage.getItem("team_type");
				if (type == 0) {
					localStorage.removeItem("team_type");
					window.location.href = "eventdetailsone.html";
				} else if (type == 1) {
					localStorage.removeItem("team_type");
					window.location.href = "svc_red_eventdetails.html";
				}
			}
		},
		timeout : 3000
	});
	
}


//保存周期事项
function save_Cycitem(fgId){
	var eId=localStorage.getItem("team_eId");
	var userId=localStorage.getItem("team_userId");
	var eiId=localStorage.getItem("team_eiId");
	$(".event_summary").show();
	$(".event_summary1").show();
	var Json='"eId":"'
		+eId
		+'","eiId":"'
		+eiId
		+'","isCycle":"'
		+1
		+'","createBy":"'
		+userId
		+'","dutyId":"'
		+fgId
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	var data='{'+start+'}';
	$.ajax({
		url : dataLink + "item/updateCycClient",
		type : "POST",
		data : data,
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		success : function(result) {
			if (result.msg == "success") {
				localStorage.removeItem("team_fgId");
				localStorage.removeItem("team_isCyc");
				var type = localStorage.getItem("team_type");
				if (type == 0) {
					localStorage.removeItem("team_type");
					window.location.href = "eventdetailsone.html";
				} else if (type == 1) {
					localStorage.removeItem("team_type");
					window.location.href = "svc_red_eventdetails.html";
				}
			}
		},
		timeout : 3000
	});
	
}