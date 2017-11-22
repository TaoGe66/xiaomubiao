var userId = localStorage.getItem("team_userId");
var leId=localStorage.getItem("team_leId");
//add  by  zhanghaitao  2017/09/25  跳转新的关注
var newFollow="";
$(function(){
	$(".Mail_newfriend").click(function(){
		localStorage.setItem("team_one_load",null);
		window.location.href="newattention.html?newFollow="+newFollow;
	});
		//点击选择类型跳转不同的页面
	$("#li1").click(function(){
		localStorage.removeItem("team_eId");
		localStorage.setItem("team_physics",1);
		localStorage.removeItem("team_eventPartake");
	   		localStorage.removeItem("team_eventName");
	   		localStorage.removeItem("team_startTime");
	   		localStorage.removeItem("team_endTime");

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
$(function(){
	//	点击分类弹出列表
	$(".Mail_list .Mail_global_title").click(function(){
		if($(this).parent().find(".Mail_list_ul").css("display")=="block"){
			$(this).removeClass("Mail_global_title_current");
			
			$(this).css("border-bottom","1px solid #e5e5e5");
			$(".Mail_list_global:last-child .Mail_global_title").css("border-bottom","0 none");
			
			$(this).parent().find(".Mail_list_ul").hide();
		}else{
			$(this).addClass("Mail_global_title_current");
			$(this).css("border-bottom","0 none");
			$(this).parent().find(".Mail_list_ul").show();
			myMail.list($(this).attr("flag"));
		}
	});
	enterpriseInfoByFrist();
	myMail.list(3);
	if($(".Mail_global_title_one .enterprise_member").html()==""){
		$(".Mail_global_title_one p").css("border-bottom","0 none");
	}
});
//add  by  xiehuilin 20170915 我的通讯训列表

var myMail={
	list:function(type){
	    var param={"userId":userId,"leId":leId,"type":type};
    	var url=dataLink+"direct/listGetDir";
        _asyn(url,param,"get",myMail.list_ok,type);
	},
	list_ok:function(json,code,type){
		console.info(json);
		if(json.msg=="success"){
			newFollow=json.newFollow;
			json.newFollow==1?$(".Mail_newfriend").find("span").show():$(".Mail_newfriend").find("span").hide();
		myMail.list_show(type,json);
		}	
	 },
	list_show:function(type,json){
		var myMailHtml=[];
		switch(parseInt(type)){
	 	case 0:
	 		var  toMyFollows=json.listToMyFollow;
	 		$(".be_concerned").find(".Mail_global_title").find("i").html("("+toMyFollows.length+")");
	 	 	for(var x=0;x<toMyFollows.length;x++){
	 	 		var headUrl=toMyFollows[x].headUrl!=null?(imageHeadSrc+toMyFollows[x].headUrl):"img/head-portrait.png"
	 	 		myMailHtml.push(
	 	 			'<li class="Mail_list_li"><i class="userId" style="display: none;">'+toMyFollows[x].userId+'</i>'
					+'<img src="'+headUrl+'" alt="">'
					+'<span class="Mail_list_name">'+toMyFollows[x].userName+'<em></span>'
					+'</li>'
	 	 			);

	 	 	}
	 	 	$(".be_concerned").find(".Mail_list_ul").html("");
	 	 	$(".be_concerned").find(".Mail_list_ul").append(myMailHtml.join(""));
	 		break;
	 	case 1:
	 		var  myToFollows=json.listMyToFollow;
	 		$(".concern").find(".Mail_global_title").find("i").html("("+myToFollows.length+")");
	 	 	for(var x=0;x<myToFollows.length;x++){
	 	 		var headUrl=myToFollows[x].headUrl!=null?(imageHeadSrc+myToFollows[x].headUrl):"img/head-portrait.png"
	 	 		myMailHtml.push(
	 	 			'<li class="Mail_list_li"><i class="userId" style="display: none;">'+myToFollows[x].userId+'</i>'
					+'<img src="'+headUrl+'" alt="">'
					+'<span class="Mail_list_name"><i>'+myToFollows[x].userName+'</i><em>'+roleType(myToFollows[x].roleType)+'</span>'
					+'<i class="Mail_list_cancel"><i class="fId" style="display: none;">'+myToFollows[x].id+'</i>取消关注</i>'
					+'</li>'
	 	 			);

	 	 	}
	 	 	$(".concern").find(".Mail_list_ul").html("");
	 	 	$(".concern").find(".Mail_list_ul").append(myMailHtml.join(""));
	 	break;
	 	default:
	 	$(".enterprise_member").html("");

			var members=json.memberList;
			json.listMyToFollow.length<=0?$(".concern").hide():$(".concern").show();
			json.listToMyFollow.length<=0?$(".be_concerned").hide():$(".be_concerned").show();
			if(type==3){
				members.length>0?$(".Mail_global_title_one p").addClass("Mail_global_title_current"):"";
			}
			$(".Mail_global_title_one").find(".Mail_global_title").find("i").html("("+members.length+")");
			$(".be_concerned").find(".Mail_global_title").find("i").html("("+json.listToMyFollow.length+")");
			$(".concern").find(".Mail_global_title").find("i").html("("+json.follows.length+")");
			for(var x=0;x<members.length;x++){
				var member=members[x];
				var html='<li class="Mail_list_li"><i class="userId" style="display: none;">'+member.userId+'</i>'
				var headUrl=member.headUrl!=null?(imageHeadSrc+member.headUrl):"img/head-portrait.png"
				html+='<img src="'+headUrl+'" alt="">'
				html+='<span class="Mail_list_name"><i>'+member.userName+'</i><em>'+roleType(member.roleType)+'</em></span>'
				if(member.state==2){
				html+='<i class="Mail_list_noclick">等待确认</i>'
				 }
				if (member.state==null){ 
		         html+='<i class="Mail_list_follow"><i class="userId" style="display: none;">'+member.userId+'</i>关注</i>'
				}
				html+='</li>'
				myMailHtml.push(html);
			}
			$(".enterprise_member").append(myMailHtml.join(""));
	 		break;
		}
	}
		
}
//add  by xiehuilin 20170915 取消关注 弹框
var fId="";
$(".Mail_list").on("click",".Mail_list_cancel",function(event){
    fId=$(this).parent().find(".fId").html();
    followId=$(this).parent().parent().find(".userId").html();
    var  followName=$(this).parents().find(".Mail_list_name i").html();
    $(".Mail_cancel").find(".Mail_cancel_font").html("取消对"+followName+'?');
    $(".Mail_alert_bg").show();
    $(".Mail_cancel").show();
    event.stopPropagation();
});


//add  by xiehuilin 20170915 取消关注 确定
$(".Mail_cancel").on("click",".Mail_cancel_btntwo",function(event){
   	 $.ajax({
            type:"POST",
            url:dataLink+"follow/update",
            data:{"id":fId,"state":3,"leId":leId,"userId":followId,"createBy":userId},
            datatype: "json", 
            //成功返回之后调用的函数             
            success:function(json){
              _g("maillist.html");
            },
            error: function(){
                //请求出错处理
            }         
         });
});


//add by xiehuilin 20170915 加关注  弹框
var followId="";
$(".Mail_list").on("click",".Mail_list_follow",function(event){
	event.stopPropagation();
	followId=$(this).parent().find(".userId").html();
    var  followName=$(this).parent().find(".Mail_list_name i").html();
    $(".Mail_overlook").find(".Mail_overlook_font").html("确定关注"+followName+'?');
	$(".Mail_alert_bg").show();
	$(".Mail_overlook").show();

});


//add by xiehuilin 20170915 加关注  确定
$(".Mail_overlook").on("click",".Mail_overlook_btntwo",function(){
	$.ajax({
            type:"POST",
            url:dataLink+"follow/insert",
            data:{"userId":followId,"state":2,"leId":leId,"createBy":userId,"type":1},
            datatype: "json", 
            //成功返回之后调用的函数             
            success:function(json){
              _g("maillist.html");
            },
            error: function(){
                //请求出错处理
            }         
         });
});

//add by xiehuilin 20170915	点击取消按钮
$(".Mail_cancel_btnone").click(function(){
		$(".Mail_alert_bg").hide();
		$(".Mail_cancel").hide();
		$(".Mail_overlook").hide();
});

function roleType(roleType){
	var  html='';
	 switch(roleType){
	 	case 0:
	 	html="(创始人)";
	 	break;
	 	case 1:
	 	html="(管理员)";
	 	break;
	 	default:
	 	return "";
	 	break;
	 }
	 return html;
}
//add by xiehuilin 20170927 跳转人员详情
$(".Mail_list").on("click",".Mail_list_li",function(event){
	event.stopPropagation();
	var team_memId=$(this).find(".userId").html();
	localStorage.setItem("team_memId",team_memId);
	_g("member.html");
	
});
