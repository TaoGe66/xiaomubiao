$(function(){
	//复选切换
	$(".rdo").click(function(){
		var flag = $(this).attr("flag");
			if(flag =="true"){
//				$(this).css("background","url(img/radio.png)");
//				$(this).css("background-size","100%");
//				$(this).attr('checked', 'checked').parent("li").children("input").removeAttr("checked","checked");
//				$(this).addClass('aclive2').parent("li").children("input").removeClass('aclive2');
				$(this).attr("flag","false");
			}else{
//				$(this).css("background","url(img/img_ok_03.png)");
//				$(this).css("background-size","100%");
//				$(this).attr('checked', 'checked').parent("li").children("input").removeAttr("checked","checked");
//				$(this).addClass('aclive').parent("li").children("input").removeClass('aclive');
				$(this).attr("flag","true");
			}
	});

/*var list = $("input");
	for(var i = 0;i<list.length;i++){
		console.log(list.length);
		list[i].onclick = function(event){
			$(this).addClass('aclive').removeClass('aclive2');
			$(this).parent("li").siblings("li").children("input").addClass('aclive2').removeClass('aclive');
			$(this).parent("li").parent("ul").parent(".qinqi_rdo").siblings(".qinqi_rdo").children("ul").children("li").children("input").addClass('aclive2').removeClass('aclive');
		}
	};*/
	
	
	//add by wuchao 20170919 取消
	$(".btn_no").click(function(){
		window.location.href="admin.html";
	});
	
	//关注我的和已关注收起展开
	var list = $(".show_list");
	for(var i = 0;i<list.length;i++){
		list[i].onclick = function(event){
			var flag = $(this).attr("flag");
			if(flag =="true"){
				$(this).parent(".list").children(".Newfriend_list").hide();
				$(this).parent(".list").children(".show_list").children("img").css("transform","rotate(0deg)");
				$(this).attr("flag","false");
			}else{
				$(this).parent(".list").children(".Newfriend_list").show();
				$(this).parent(".list").children(".show_list").children("img").css("transform","rotate(90deg)");
				$(this).attr("flag","true");
			}
		}
	};
	
	//默认展开的轻企成员
	var list = $(".qinqi_show_list");
	for(var i = 0;i<list.length;i++){
		list[i].onclick = function(event){
			var flag = $(this).attr("flag");
			if(flag =="true"){
				$(this).parent(".qinqi_list").children(".Newfriend_list").show();
				$(this).parent(".qinqi_list").children(".qinqi_show_list").children("img").css("transform","rotate(90deg)");
				$(this).attr("flag","false");
			}else{
				$(this).parent(".qinqi_list").children(".Newfriend_list").hide();
				$(this).parent(".qinqi_list").children(".qinqi_show_list").children("img").css("transform","rotate(0deg)");
				$(this).attr("flag","true");
			}
		}
	};
});

var userId=localStorage.getItem("team_userId");
var leId=localStorage.getItem("team_leId");
/**
 * 选择成员
 */
ligth_all()
function ligth_all(){

	var data={"userId":userId,"leId":leId,"roleType":2}
	$.ajax({
		  type : "GET",
	      url:dataLink+"direct/listManage",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  //关注我的
	    		  if (json.listToMyFollow.length>0) {
	    			  $(".follow_me").show();
	    			  var listToMyFollow= json.listToMyFollow;
	    			  for (var i = 0; i < listToMyFollow.length; i++) {
							var myFollow=listToMyFollow[i];
							if (myFollow.roleType==2) {
								var headUrl= isnull(myFollow.headUrl)?"img/img03.png":imageHeadSrc+myFollow.headUrl;
								var html ='<li><input type="checkbox" id="check" class="rdo aclive2"/>';
									html+='<em style="display:none;">'+myFollow.memberId+'</em>';
									html+='<i style="display:none;">'+myFollow.userId+'</i>';
									html +='<div class="list_right">';
									html +='<img src="'+headUrl+'" alt="" />';
									html +='<span>'+myFollow.userName+'</span>';
									html +='</div></li>';
								$(".follow_me_list").append(html);
							}
							
						}
				}
	    		  //我关注的
	    		  if (json.listMyToFollow.length>0) {
	    			  $(".already").show();
	    			  var listMyToFollow=json.listMyToFollow;
	    			  for (var i = 0; i < listMyToFollow.length; i++) {
							var toFollow=listMyToFollow[i];
							if (toFollow.roleType==2) {
								var headUrl= isnull(toFollow.headUrl)?"img/img03.png":imageHeadSrc+toFollow.headUrl;
								var html ='<li><input type="checkbox" id="check" class="rdo aclive2"/>';
									html+='<em style="display:none;">'+toFollow.memberId+'</em>';
									html+='<i style="display:none;">'+toFollow.userId+'</i>';
									html +='<div class="list_right">';
									html +='<img src="'+headUrl+'" alt="" />';
									html +='<span>'+toFollow.userName+'</span>';
									html +='</div></li>';
								$(".already_list").append(html);
							}
							
						}
					}
	    		  
	    		  //轻企成员
	    		 var memberList=json.memberList;
	    		 if (memberList.length>0) {
					for (var i = 0; i < memberList.length; i++) {
						var member=memberList[i];
						if (member.roleType==2) {
							var headUrl= isnull(member.headUrl)?"img/img03.png":imageHeadSrc+member.headUrl;
							var html ='<li><input type="checkbox" id="check" class="rdo aclive2"/>';
								html+='<em style="display:none;">'+member.id+'</em>';
								html+='<i style="display:none;">'+member.userId+'</i>';
								html +='<div class="list_right">';
								html +='<img src="'+headUrl+'" alt="" />';
								html +='<span>'+member.userName+'</span>';
								html +='</div></li>';
							$(".member_list").append(html);
						}
						
					}
				}

	    		 var list = $("input");
	    			for(var i = 0;i<list.length;i++){
	    				console.log(list.length);
	    				list[i].onclick = function(event){
	    					$(this).addClass('aclive').removeClass('aclive2');
	    					$(this).parent("li").siblings("li").children("input").addClass('aclive2').removeClass('aclive');
	    					$(this).parent("li").parent("ul").parent(".qinqi_rdo").siblings(".qinqi_rdo").children("ul").children("li").children("input").addClass('aclive2').removeClass('aclive');
	    				}
	    			};
	    			
	    	  }
	      },  
	      timeout:3000  
	 }); 
}


$(".btn_yes").click(function(){
	if ($("input").hasClass("aclive")) {
		updateAuth();
	}else{
		window.location.href="admin.html";
	}
});
//add by wuchao 20170919 添加管理员
function updateAuth(){
	var id=$(".aclive").parent().find("em").html();
	var useId=$(".aclive").parent().find("i").html();
	var data={"id":id,"roleType":1,"leId":leId,"userId":useId}
	$.ajax({
		  url:dataLink+"enterprise/updateLightRole",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     success:function(result){
	   	  if(result.msg=="success"){
	   		$(".memberUndonePopups").fadeIn(0).delay(3000).fadeOut(0);
			setTimeout(function(){
				window.location.href="admin.html";
			},3000);
	   	  }
	     },  
	     timeout:3000  
	});
}