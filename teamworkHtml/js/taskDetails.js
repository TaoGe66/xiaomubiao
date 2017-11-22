var userId,type;
$(function(){
	userId= localStorage.getItem("team_userId");
 	localStorage.setItem("team_temp_userId",userId);
  	type=1;
  	init.data(userId,type);
});
$(function () {
            var curr = new Date().getFullYear();
            var  curr2=new Date();
            var opt={};
			opt.date = {preset : 'date'};
			opt.datetime = {preset : 'datetime'};
			opt.time = {preset : 'time'};

          opt.default = {
				theme: 'android-holo light', //皮肤样式
		        display: 'modal', //显示方式 
		        mode: 'scroller', //日期选择模式
				dateFormat: 'yyyy-mm-dd',//yyyy-mm-dd
				lang: 'zh',
				showNow: true,
				nowText: "今天",
				stepMinute: 10,
		        startYear: curr - 10, //开始年份
		        endYear: curr + 50 //结束年份
			};
           $('.settings').bind('change', function() {
                var demo = 'datetime';
                if (!demo.match(/select/i)) {
                    $('.demo-test-' + demo).val('');
                }
                $("#tests").scroller('destroy').scroller($.extend(opt['datetime'], opt['default']));
                $('.demo').hide();
                $('.demo-' + demo).show();
            });
            $('#demo').trigger('change');
        });
	



var isEventDuty="";
var createBy= localStorage.getItem("team_userId");
var eventState=0;
var isend=0;
// add by xiehuilin 2017/11/03 初始化任务详情
var init={
	data:function(userId,type){
		var leId=localStorage.getItem("team_leId");
		var eId=localStorage.getItem("team_eId");
		var param={"userId":userId,"eId":eId,"createBy":createBy,"reqType":type};
    	var url=dataLink+"newEvent/timeManag";
    	_asyn(url,param,"get",init.Ok,type);
	},
	Ok:function(json, code,type){
		if(getAjaxJson(json)){
		 	var  et=json.event;
		 	if(!isnull(et.leName)){
		 		$(".event_title").html("["+et.leName+"]");	
		 	}
		 	$(".event_name").find("i").eq(0).html(et.name);
		 	$(".Sign_people").html("负责人："+et.userName);
		 	if(json.isThisYear==0){
				$(".Event_head_time span").html(sameDate(et.startTime,et.endTime));
			}else{
				$(".Event_head_time span").html(getMyDate(et.startTime)+'-'+getMyDate(et.endTime));
			}
			if(et.isDelay==0&&et.duration<=10){
				$(".event_right_left").show();
				$(".Event_right_font").html("剩余时长");
				$(".Event_right_num").html(et.duration);
			}
			//行动责任人列表
			var users=json.users;
			var usersHtmls=[];
			if(type==1){
				for(var x=0;x<users.length;x++){
					var userName=userId==users[x].userId?"我":users[x].userName;
					usersHtmls.push(
							'<li class="event_category_alone"><p class="userId" style="display: none;">'+users[x].userId+'</p>'
							+'<img src="img/touxiang_14.png" alt="">'
							+'<p>'+userName+'</p>'
							+'</li>'
						);
				}
				$(".event_tab").append(usersHtmls.join(""));
		}
			//行动列表
			var items=json.item;
			
			for(var x=0;x<items.length;x++){
				itemShow(items[x]);		
			}
		}
	}
}

//行动列表展示
function itemShow(item){
	var itemsHtml=[];
	var rhtml="";
	   if(item.recordList.length>0){
	    	rhtml+='<em class="task_reddian"></em><i>【'+item.rCount+'】'+item.recordList[0].userName+':'+item.recordList[0].content+'</i>';
	    }
	switch(item.state){
		case 3:
		itemsHtml.push(
			'<div class="task_block"><i class="eiId" style="display: none;">'+item.eiId+'</i><i class="isCycle" style="display: none;">'+item.isCycle+'</i>'
			+'<span class="task_icon"></span>'
			+'<div class="task_left">'
			+'<span class="task_leftone task_lefttwo"></span><i class="time_line"></i>'
			+'</div>'
			+'<div class="task_right">'
			+'<p class="task_title">'+item.eiDesc+'</p>'
			+'<p class="task_start">开始：<i>'+item.startStrTime+'</i></p>'
			+'<p class="task_end">结束：<i>'+item.fTimeStr+'</i></p>'
			+'<p class="task_name">负责人：<i>'+item.userName+'</i></p>'
			+'<p class="task_message">'+rhtml+'</p>'
			+'</div>'
			+'</div>'
			);
		 break;
		case 4:
		itemsHtml.push(
			'<div class="task_block"><i class="eiId" style="display: none;">'+item.eiId+'</i><i class="isCycle" style="display: none;">'+item.isCycle+'</i>'
			+'<span class="task_iconthree"></span>'
			+'<div class="task_left">'
			+'<span class="task_leftone task_lefttwo"></span><i class="time_line"></i>'
			+'</div>'
			+'<div class="task_right">'
			+'<p class="task_title">'+item.eiDesc+'</p>'
			+'<p class="task_start">开始：<i>'+item.startStrTime+'</i></p>'
			+'<p class="task_end">结束：<i>'+item.fTimeStr+'</i></p>'
			+'<p class="task_name">负责人：<i>'+item.userName+'</i></p>'
			+'<p class="task_message">'+rhtml+'</p>'
			+'</div>'
			+'</div>'
			);
		 break;
		case 5:
		itemsHtml.push(
			'<div class="task_block"><i class="eiId" style="display: none;">'+item.eiId+'</i><i class="isCycle" style="display: none;">'+item.isCycle+'</i>'
			+'<span class="task_icontwo"></span>'
			+'<div class="task_left">'
			+'<span class="task_leftone task_lefttwo"></span><i class="time_line"></i>'
			+'</div>'
			+'<div class="task_right">'
			+'<p class="task_title">'+item.eiDesc+'</p>'
			+'<p class="task_start">开始：<i>'+item.startStrTime+'</i></p>'
			+'<p class="task_end">结束：<i>'+item.fTimeStr+'</i></p>'
			+'<p class="task_name">负责人：<i>'+item.userName+'</i></p>'
			+'<p class="task_message">'+rhtml+'</p>'
			+'</div>'
			+'</div>'
			);
		 break;
		default:
		var cssHtml=item.isCycle==0?"task_lefttwo":"task_leftthree";
		itemsHtml.push(
			'<div class="task_block"><i class="eiId" style="display: none;">'+item.eiId+'</i><i class="isCycle" style="display: none;">'+item.isCycle+'</i>'
			+'<div class="task_left">'
			+'<span class="'+cssHtml+'"></span><i class="time_line"></i>'
			+'</div>'
			+'<div class="task_right">'
			+'<p class="task_title">'+item.eiDesc+'</p>'
			+'<p class="task_start">开始：<i>'+item.startStrTime+'</i></p>'
			+'<p class="task_end">结束：<i>'+item.fTimeStr+'</i></p>'
			+'<p class="task_name">负责人：<i>'+item.userName+'</i></p>'
			+'<p class="task_message">'+rhtml+'</p>'
			+'</div>'
			+'</div>'
			);

	}
	$(".task_content").append(itemsHtml.join(""));
}


	//add by wuchao 20171107 跳转添加行动
	$(".task_footerone").click(function(){
		window.location.href="add_items.html";
	});
	//跳转目标详情
	$(".Event_head_inner").on("click",".event_right_img",function(){
		_g("event_description_founder.html");
	});
	//跳转行动详情
	$(".task_content").on("click",".task_block",function(){
		var isCycle=$(this).find(".isCycle").html();
		var eiId=$(this).find(".eiId").html();
		localStorage.setItem("team_eiId",eiId);
		isCycle==0?_g("action_details.html"):_g("cycleDetails.html");
	});
	//add by zhanghaitao 2017/11/01  切换头像
	$(".event_tab ").on("click","li",function(){
		$(this).addClass("select").siblings().removeClass("select");
		 userId=$(this).find(".userId ").html();
		var tc=$(this).find("p").html();
		type=tc=="全部"?2:0;
		userId=isnull(userId)?localStorage.getItem("team_userId"):userId;
		 $(".task_content").html("");
		 localStorage.setItem("team_temp_userId",userId);
		init.data(userId,type);
	});