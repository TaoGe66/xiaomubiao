//选择好友
var arr=[];
 $(".Select_global").on("click","li",function(){
	$(this).find("em").toggleClass("Select_global_current");	
});

$(function(){
	init.data();
});

var eId=localStorage.getItem("team_eId");
var userId=localStorage.getItem("team_userId");
var leId=localStorage.getItem("team_leId");
//add  by xiehuilin 2017/07/10 V0.0.02 分页参数
var page=1;
var verdict=true;

//add by xiehuilin 20171101 初始化数据渲染
var init={
	data:function(){
		var url = dataLink + "join/getEventJoinUser";
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            data: {"eId": eId,"userId":userId,"pageNum":page},
            success: function (json) {
            	console.info(json);
            	if(json.msg=="success"){
	            	var friendsHtml=[];
	            	var friends="";
	            	if(isnull(leId)){
	            		friends=json.friendGroups;
	            	}else{
	            		friends=json.members;
	            	}
	            	 verdict=friends.length>0?true:false;
	            	 if(page==1&&friends.length<=0){
	            	 	//add by xiehuilin 2017/11/01 隐藏好友或轻企成员列表
	            	 	$(".Select_con").hide();
	            	 	//add by xiehuilin 2017/11/01 显示缺省页
	            	 	$(".Additems_alert_null").show();
	            	 	//add by xiehuilin 2017/11/01 隐藏可操作按钮
	            	 	$(".Select_btn").hide();
	            	 }else{
	            	 	//add by xiehuilin 2017/11/01 显示好友或轻企成员列表
	            	 	$(".Select_con").show();
	            	 	//add by xiehuilin 2017/11/01 隐藏缺省页
	            	 	$(".Additems_alert_null").hide();
	            	 	//add by xiehuilin 2017/11/01 显示可操作按钮
	            	 	$(".Select_btn").show();
	            	 }
	            	$(".Select_global").find("b").html('('+json.count+')');
	            	for(var x=0;x<friends.length;x++){
	            		var headUrl=friends[x].headUrl!=null?(imageHeadSrc+friends[x].headUrl):"img/img03.png";
	            		var cls=friends[x].isJoinUser==1?"Select_noclick":"";
	            		var joinUserId=isnull(leId)?friends[x].fId:friends[x].userId;
	            		friendsHtml.push(
	            		'<li>'
						+'<em class='+cls+'><i class="fId" style="display: none;">'+joinUserId+'</i></em>'
						+'<img src="'+headUrl+'" alt="">'
						+'<div class="Select_list">'
						+'<span>'+friends[x].userName+'</span>'
						+'</div>'
						+'</li>'
				       );
	            	}
	            $(".Select_global").find("ul").append(friendsHtml.join(""));
              }
            },
            error: function(XMLHttpRequest) {
                
            }
        });
	},
}
var post_flag = false; 
//add by xiehuilin  2017/11/01 确定添加参与人
$(".Select_btn").on("click",".Select_btn_confirm",function(){
	//add by xiehulin 2017/11/01 获取选择参与id
	$(".Select_global ul").find("li").each(function(){
		if($(this).find("em").hasClass("Select_global_current")){
			var joinUserId=$(this).find(".Select_global_current").find(".fId").html();
			arr.push(joinUserId);
		}
	});
	add.joinUser();
});


//add by xiehuilin 2017/11/01 取消添加任务参与人
$(".Select_btn").on("click",".Select_btn_cancel",function(){
	_g("event_description_founder.html");
});

//add by xiehuilin 2017/11/01 保存任务参与人记录
var add={
	 joinUser:function(){
	 	if(post_flag) return; //如果正在提交则直接返回，停止执行
		post_flag = true;//标记当前状态为正在提交状态
	 	var eId=localStorage.getItem("team_eId");
		var createBy= localStorage.getItem("team_userId");
		var userName=localStorage.getItem("team_userName");
		var joinUserIds=arr.join(",")
		var param={"eId":eId,"createBy":createBy,"joinUserIds":joinUserIds,"userName":userName};
    	var url=dataLink+"join/addEventJoinUser";
    	console.info(joinUserIds);
    	_asyn(url,param,"POST",add.ok);
	 },
	 ok:function(json){
	 	post_flag =false; //在提交成功之后将标志标记为可提交状态
	 	if(json.msg=="success"){
	 		_g("event_description_founder.html");
	 	}
   }
}



//add by xiehuilin 2017/11/01 分页
$(window).scroll(function () {
    var bot =($(".Select_global ul").find("li").height())*3; 
        if (verdict) {
            if ((bot + $(window).scrollTop()) >= ($("body").height() - $(window).height())) {
                verdict=false;
                page+=1;
               init.data();
            }
        }
});

