//选择
var arr=[];
$(function(){
	//取消返回上一级
	$(".Select_btn_cancel").click(function(){
		window.location.href="serverelease.html";
	});
	//点击灰色区域
	$(".event_summary").click(function(){
		$(".event_summary").hide();
		$(".notes_overlook").hide();
	});
	//点击确定显示弹窗
	$(".Select_btn_confirm").click(function(){
		if($(this).hasClass("notes_btn_ok")){
			$(".event_summary").show();
			$(".notes_overlook").show();
		}
		if(!$(this).hasClass("notes_btn_ok")){
			$(".event_summary").hide();
			$(".notes_overlook").hide();
		}
	});
	//	点击弹窗取消按钮
	$(".notes_overlook_btnone").on("click",function(){
		$(".event_summary").hide();
		$(".notes_overlook").hide();
	});
	//多选
	$("li>em").attr("flage","true");
	$(".deleteparson_ul").on("click","li>em",function(){
		if($(this).attr("flage") == "true"){
			$(this).attr("flage","false");
			$(this).addClass("deleteparson_current");
			if($("em").hasClass('deleteparson_current')){
				$(".Select_btn_confirm").css("background","#6eabfe").addClass("notes_btn_ok");
			}
		}else{
			$(this).removeClass("deleteparson_current");
			$(this).attr("flage","true");
			if(!$("em").hasClass('deleteparson_current')){
				$(".Select_btn_confirm").css("background","#cecece").removeClass("notes_btn_ok");
			}
		}
		//add by xiehulin 2017/11/01 获取选择参与id
		$(".deleteparson_ul").find("li").each(function(){
			if($(this).find("em").hasClass("deleteparson_current")){
				var joinUserId=$(this).find(".deleteparson_current").find(".userId").html();
				arr.push(joinUserId);
			}
		});
	});
	init.data();
});

var eId=localStorage.getItem("team_eId");
var userId=localStorage.getItem("team_userId");
//add  by xiehuilin 2017/07/10 V0.0.02 分页参数
var page=1;
var verdict=true;
var init={
	data:function(){
		var url = dataLink + "join/listEventJoinUser";
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            data: {"eId": eId,"userId":userId,"pageNum":page},
            success: function (json) {
            	console.info(json);
            	var joinUserHtml=[];
            	var joinEvents=json.joinEvents;
            	for(var x=0;x<joinEvents.length;x++){
            		var headUrl=joinEvents[x].headUrl!=null?(imageHeadSrc+joinEvents[x].headUrl):"img/img03.png";
            		var html='<li flage="true">';
								html+='<em class=""><i class="userId" style="display: none;">'+joinEvents[x].userId+'</i></em>';
								html+='<div class="deleteparson_list">';
								html+='<img src="'+headUrl+'" alt="">';
								html+='<span>'+joinEvents[x].userName+'</span>';
								if(joinEvents[x].duytItemCount!=0){
								 html+='<span class="tips_xingdong"><i>2</i>个行动</span>'
								}
								html+='</div>';
								html+='</li>';
            		joinUserHtml.push(html);
            	}
            	$(".deleteparson_ul").append(joinUserHtml.join(""));
            	
            },
            error: function(XMLHttpRequest) {
                
            }
        });
	},
}


//add by xiehuilin 2017/11/01 确定删除任务参与人
$(".notes_overlook").on("click",".notes_overlook_btntwo",function(){
	del.joinUser();
});

var post_flag = false; 
//add by xiehuilin 2017/11/01 保存任务参与人记录
var del={
	 joinUser:function(){
	 	if(post_flag) return; 	//如果正在提交则直接返回，停止执行
		post_flag = true;		//标记当前状态为正在提交状态
		var createBy= localStorage.getItem("team_userId");
		var joinUserIds=arr.join(",")
		var param={"eId":eId,"createBy":createBy,"joinUserIds":joinUserIds};
    	var url=dataLink+"join/delEventJoinUser";
    	_asyn(url,param,"POST",del.ok);
	 },
	 ok:function(json){
	 	post_flag =false; //在提交成功之后将标志标记为可提交状态
	 	if(json.msg=="success"){
	 		//_g("event_description_founder.html");
	 	}
   }
}

//add by xiehuilin 2017/11/01 分页
$(window).scroll(function () {
    var bot =($(".deleteparson_ul li").height())*3; 
        if (verdict) {
            if ((bot + $(window).scrollTop()) >= ($("body").height() - $(window).height())) {
                verdict=false;
                page+=1;
               init.data();
            }
        }
});