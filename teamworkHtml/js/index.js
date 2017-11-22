var userId = localStorage.getItem("team_userId");
var topaa ='';
var pageOne= '';
var verdictOne=true;//是否继续刷新
$(window).scroll(function () {
   if(topaa==1){
        var bot =($(".find-event").height())*3;
        if (verdictOne) {
            if ((bot + $(window).scrollTop()) >= ($("body").height() - $(window).height())) {
                verdictOne=false;
                pageOne+=1;
                tobeEventList(pageOne);
            }
        }
    }
 });

$(function(){
//	edit by zhengjunting 2017/07/16  头部点击导航的时候切换
	$(".Index_nav li").click(function(){
		$(".Index_nav span").removeClass("Index_nav_current");
        $(this).find("span").addClass("Index_nav_current");
		$(".Index_con_current").hide().eq($(this).index()).show();
   });
	$(".hot_spot_default").hide(); 
	$(".list_default").hide();
	$(".todayEvents_default").hide();
	show_time();

    $(".cancel").click(function(){
    	$(".event_summary").hide();
	    $(".event_summary1").hide();
    });
	
//	点击出现分享引导页
	$(".share_page").click(function(){
		$(".share_page").css("display","none");
    	$(".share_page1").css("display","none");
	})

//	跳转个人中心
	$(".box1").click(function(){
		if(verificationUserLogin()){
			window.location.href="personal.html";
		}
	});
	$(".box2").click(function(){
		if(verificationUserLogin()){
		     window.location.href="my_event.html";
		}
	});
	$(".complete p").click(function(){
		 window.location.href="completed.html";
	});

	//点击选择类型跳转不同的页面
	$("#li1").click(function(){
		localStorage.removeItem("team_eId");
		localStorage.setItem("team_physics",1);
		alert(111111);
		window.location.href="serverelease.html";
	})
	$("#li2").click(function(){
		alert(22222);
		localStorage.removeItem("team_eId");
		window.location.href="servereleasethree.html";
	})
	$("#li3").click(function(){
		alert(333333333);
		localStorage.removeItem("team_eId");
		window.location.href="servereleasetwo.html";
	})
	//去发现
	$(".tohfind").click(function(){
		localStorage.setItem("team_top",3);
		$(".navigation ul .li3-1").addClass("li3");
		$(".navigation ul .li2-1").removeClass("li2");
		$(".navigation ul .li1-1").removeClass("li1");
		$(".todayEvents").hide();
		$(".to-do-list").hide();
		$(".hot-spot").show();
		findEvent(1);
	});
})


function show_time(){
	var currYear = (new Date()).getFullYear();	
	var opt={};
	opt.date = {preset : 'date'};
	opt.datetime = {preset : 'datetime'};
	opt.time = {preset : 'time'};
	opt.default = {
		theme: 'ios', //皮肤样式
	    display: 'modal', //显示方式 
	    mode: 'scroller', //日期选择模式
		lang:'zh',
	   	Date: new Date()

	};
	$.mobiscroll.i18n.zh = $.extend($.mobiscroll.i18n.zh, {
	    dateFormat: 'yyyy-mm-dd',
	    dateOrder: 'yymmdd',
	    dayNames: ['周日', '周一;', '周二;', '周三', '周四', '周五', '周六'],
		dayNamesShort: ['日', '一', '二', '三', '四', '五', '六'],
	    dayText: '日',
	    hourText: '时',
	    minuteText: '分',
	    monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月','十二月'],
	    monthNamesShort: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
	    monthText: '月',
	    secText: '秒',
	    timeFormat: 'HH:ii',
	    timeWheels: 'HHii',
	    yearText: '年',
	    setText: '确定',
  		cancelText: '取消'
	});
	var optDateTime = $.extend(opt['datetime'], opt['default']);
	$("#appDateTime").mobiscroll(optDateTime).datetime(optDateTime);
}


//待办事项
function tobeEventList(page){

	topaa =localStorage.getItem("team_top")
	pageOne=page;
	if(page==1){
		verdictOne=true;
	}
	var data = {"userId":userId,"pageNum":pageOne}
	 $.ajax({
		   type : "get",
	       url:dataLink+"homepage/tobeEventList", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	       	console.info(json);
	    	   if(json.msg="success"){
	    	   
	    	   	$(".hot_spot_default").hide(); 
	            $(".list_default").hide();
	            $(".todayEvents_default").hide();
	    	   	if(page==1){
	    	   		$(".typeone").html("");
    	   			// add by xiehuilin 2017/06/22 调用事件完成统计方法
    	   			todoFnish(json.user,json.totalNum,json.todayNum);
    	   			
	    	   	}
	    	   	if(!isnull(json.pageInfo)&&json.pageInfo.length>0){
	    	   		if(page==1){
	    	   	       	if (json.totalNum>0) {
		    	   			$(".list_icon").show();
		    	   			$(".list_icon").html(json.tobeNumber);
						}
	    	   		}
	    	   		for(i=0;i<json.pageInfo.length;i++){
	    	   			var item = json.pageInfo[i];
	    	   			
	    	   			var html  ='';
	    	   			var buttonStr ='';
	    	   			//事项内容
	    	   			var eiDesc ='';
	    	   			var timehtml ='<span class="bgtime">'+Timeday(item.startTime)+'</span><span>—</span><span class="endtime">'+Timeday(item.endTime)+'</span>';
	    	   			
	    	   			//时间管理
	    	   			if(item.type==0){
	    	   				var logo = '';
	    	   				//事件
	    	   				if(item.tSubjType==0){
	    	   					html  +='<div class="shutdown bigshdow time_management">';
	    	   					//待计划
	    	   					if(item.tState==0){
	    	   					    logo = "img/pending.png";
	    	   					    buttonStr ='<button class="toplan" id="'+item.eId+'">去计划</button>';
	    	   					//待关闭
	    	   				    }else if(item.tState==3){
	    	   				    	logo = "img/shutdown.png";
	    	   				    	buttonStr ='<button class="todo_closeevent" id="'+item.eId+'">关闭</button>';
	    	   				    //待评论
	    	   				    }else if(item.tState==6){
	    	   				    	logo = "img/pending-comment.png";
	    	   				    	buttonStr ='<button class="comment_to" id="'+item.id+'">评论</button>';
	    	   				    }
	    	   				//事项
	    	   				}else if(item.tSubjType==1){
	    	   					 timehtml ='<span class="bgtime">'+item.eiStartTime+'</span>';
	    	   					//待完成
	    	   					if(item.tState==0){
	    	   						html  +='<div class="shutdown bigshdow time_management">';
	    	   						logo = "img/pending-completion.png";
	    	   						buttonStr ='<button class="todo_close" id="'+item.id+'">完成</button>';
	    	   					//待接受
	    	   					}else if(item.tState==1){
	    	   						html  +='<div class="accepted bigshdow time_management">';
	    	   						logo = "img/to-be-accepted.png";
	    	   						buttonStr ='<button class="btn1 acceptNo" id="'+item.eiId+'">拒绝</button><button class="btn2 acceptYes" id="'+item.eiId+'">接受</button>';
	    	   					    eiDesc ='<p class="plan">'+item.eiDesc+'</p>';
	    	   					//未接受
	    	   					}else if(item.tState==2){
	    	   						html  +='<div class="not-accepted bigshdow time_management">';
	    	   						logo = "img/not-accepted.png";
	    	   						buttonStr ='<button class="btn1 closeEventItem" id="'+item.id+'">终止事项</button><button class="btn2 acceptYes" id="'+item.eiId+'">接受</button>';
	    	   					}
	    	   				}
	    	   				
	    	   				html +='<div class="left left5" style="background: url('+logo+')  no-repeat center center;background-size: 85%;"></div>';
	    	   			    html +='<div class="right right5"><p class="name" id="'+item.cuserId+'">'+item.name+'</p>';
	    	   			    html +='<p class="plan planStr" id="'+item.type+'">'+item.tExplained+'</p>'+eiDesc;
	    	   			    html +='<img class="time" src="img/time.png" alt="" />';
	    	   			    html +=timehtml+'<br/>';
	    	   			    html +=buttonStr+'</div></div>';
	    	   			//1 服务请求、2 组织活动	
	    	   			}else{
	    	   				//事件
	    	   				if(item.tSubjType==0){
	    	   					//待计划
	    	   					if(item.tState==0){
	    	   						buttonStr ='<button class="btn-event toplan" id="'+item.eId+'">去计划</button>';
	    	   					//待关闭
	    	   				    }else if(item.tState==3){
	    	   				    	buttonStr ='<button class="btn-event todo_closeevent" id="'+item.eId+'">关闭</button>';
	    	   				    //待评论
	    	   				    }else if(item.tState==6){
	    	   				    	buttonStr ='<button class="btn-event comment_to" id="'+item.id+'">评论</button>';
	    	   				    //签到提醒	
	    	   				    }else if(item.tState==4){
	    	   				    	buttonStr ='<button class="btn-event sign" id="'+item.eId+'">签到</button>';
	    	   				    }
	    	   				//事项
	    	   				}else if(item.tSubjType==1){
	    	   					timehtml ='<span class="bgtime">'+item.eiStartTime+'</span>';
	    	   					//待完成
	    	   					if(item.tState==0){
	    	   						buttonStr ='<button class="btn-event todo_close" id="'+item.id+'">完成</button>';
	    	   					//待接受
	    	   					}else if(item.tState==1){
	    	   						buttonStr ='<button class="btn1 acceptNo" id="'+item.eiId+'">拒绝</button><button class="btn2 acceptYes" id="'+item.eiId+'">接受</button>';
	    	   					//未接受
	    	   					}else if(item.tState==2){
	    	   						buttonStr ='<button class="btn1 closeEventItem" id="'+item.id+'">终止事项</button><button class="btn2 acceptYes" id="'+item.eiId+'">接受</button>';
	    	   					}
	    	   				}
	    	   				
	    	   				html +='<div class="find-event bigshdow"><div class="left left6">';
	    	   				html +='<img class="img1" src="'+imageHeadSrc+item.logo+'" alt="" />';
	    	   				html +='</div><div class="right right6"><p class="name" id="'+item.cuserId+'">'+item.name+'</p>';
	    	   				html +='<h3>'+item.tName+'</h3>';
	    	   				html +='<p class="plan2 planStr" id="'+item.type+'">'+item.target+'</p>';
	    	   				html +='<img class="time" src="img/time.png" alt="" />';
	    	   				html +=timehtml+'<br />'+buttonStr+'</div></div>';
	    	   			}
	    	   			    $(".typeone").append(html);
	    	   			    $(".left5").height($(".right5").height());
	    	   			    $(".left6").height($(".right6").height());
	    	   			}
	    	   		    //完成事项
	    	   			$(".todo_close").click(function(){
	    	   				var id = $(this).attr("id");
	    	   				todoClose(id);
	    	   			});
	    	   			//关闭事件
	    	   			$(".todo_closeevent").click(function(){
	    	   				var eId = $(this).attr("id");
	    	   				$(".event_summary").show();
	                        $(".event_summary1").show();
	                        $(".determine").click(function(){
	                        	var finishTime = $("#appDateTime").val();
	                        	var summary = $(".summary").val();
	                        	if(!isnull(finishTime)){
	                        		if(summary.length>4){
	                        			if(summary.length<101){
	                        				todoCloseEvent(eId);
	                        			}else{
		                        			$(".tishi2").html("内容不能超过100字");
		                        			$(".tishi2").show();
	                        		   }
	                        		}else{
	                        			$(".tishi2").html("内容不能小于4个字");
	                        			$(".tishi2").show();
	                        		}
	                        	}else{
	                        		$(".tishi1").show();
	                        	}
	                        });
	    	   			});
	    	   			//去计划
	    	   			$(".toplan").click(function(){
	    	   				//事件id
	    	   				var eId = $(this).attr("id");
	    	   				//创建人
	    	   				var cuserId = $(this).parent().find(".name").attr("id");
	    	   				//状态 0 时间管理 、1 服务请求、2 组织活动',
	    	   				var type = $(this).parent().find(".planStr").attr("id");
	    	   				localStorage.setItem("team_eId",eId);
		    	   			if(userId==cuserId){
		    	   				if(type==0){
		    	   					window.location.href="eventdetailsone.html";
		    	   				}else if(type==1){
		    	   					window.location.href="svc_red_eventdetails.html";
		    	   				}else if(type==2){
		    	   					window.location.href="event_description.html";
		    	   				}
		    	   			}else{
		    	   				if(type==0){
		    	   					window.location.href="eventdetailsone.html";
		    	   				}else if(type==1){
		    	   					window.location.href="svc_red_eventdetails.html";
		    	   				}else if(type==2){
		    	   					window.location.href="sign_in.html";
		    	   				}
		    	   			}
	    	   			});
	    	   		    //评论 
	    	   		    $(".comment_to").click(function(){
	    	   		    	var id = $(this).attr("id");
	    	   		    	commentTo(id);
	    	   		    	
	    	   		    });
	    	   		    //去签到
	    	   		    $(".sign").click(function(){
	    	   		    	var eId = $(this).attr("id");
	    	   		    	sign(eId);
	    	   		    });
	    	   		    //拒绝
	    	   		    $(".acceptNo").click(function(){
	    	   		    	var eiId = $(this).attr("id");
	    	   		    	acceptOrRefuse(eiId,0);
	    	   		    });
	    	   		    //接受
	    	   		    $(".acceptYes").click(function(){
	    	   		    	var eiId = $(this).attr("id");
	    	   		    	acceptOrRefuse(eiId,1);
	    	   		    });
	    	   		    //终止事项
	    	   		    $(".closeEventItem").click(function(){
	    	   		    	var id = $(this).attr("id");
	    	   		    	closeEventItem(id);
	    	   		    });
	    	   		}else{
	    	   			verdictOne=false;
		    	   		if(page==1){
		    	   			$(".to-do-list").hide();
		    	   		    $(".list_default").show();
		    	     	}
	    	   	}
	    	   	
	    	   }
	    	  }
	    });
	$(".tasktodo").show();
	    
}
//完成事项
function todoClose(id){
	var data = {"id":id}
	 $.ajax({
		   type : "post",
	       url:dataLink+"homepage/todoClose", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	window.location.href="index.html";
	    	}
	    }
	});
}
//关闭事件
function todoCloseEvent(eId){
	var finishTime = $("#appDateTime").val();
	var summary = $(".summary").val();
	var data = {"eId":eId,"finishTimeStr":finishTime,"summary":summary,"userId":userId,"tState":"2"};
	 $.ajax({
		   type : "post",
	       url:dataLink+"homepage/todoCloseEvent", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	window.location.href="index.html";
	    	}
	    }
	});
	
}

//add by xiehuilin 2017/06/22 统计待办任务数量和完成数量
function todoFnish(user,totalNum,todayNum){
	$(".tasktodo").html("");
	var img=user.headUrl==null?"img/head-portrait.png":imageHeadSrc+user.headUrl;
	var html  =' <div class="task bigshdow">';
		html +='<ul><li class="picture"><img src="'+img+'" alt="" /></li>';
        html +='<li class="nickname"><span>hi,'+user.userName+'</span>';
        html+='<p>您今天一共有'+totalNum+'个任务</p></li>';  
        html +='<li class="complete"><span>'+todayNum+'</span><p>已完成</p></li></ul></div>';
		$(".tasktodo").append(html);	
		target();

								
}

//去签到
function sign(eId){
	localStorage.setItem("team_eId",eId)
	window.location.href="sign_in.html";
}

// 接受、拒绝
function acceptOrRefuse(eiId,isAccept){
	var data = {"userId":userId,"eiId":eiId,"isAccept":isAccept};
	 $.ajax({
		   type : "post",
	       url:dataLink+"homepage/acceptOrRefuse", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   		window.location.href="index.html";
	    	}
	    }
	});
}

//终止事项
function closeEventItem(id){
	var data = {"userId":userId,"id":id};
	 $.ajax({
		   type : "post",
	       url:dataLink+"homepage/closeEventItem", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   		window.location.href="index.html";
	    	}
	    }
	});
}



//个人中心消息
function getNews(){
	
	var data = {"sendId":userId,"nType":1,"isRed":0}
	 $.ajax({
		   type : "get",
	       url:dataLink+"news/getNewsByUserid", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
		    	   if(!isnull(json.newsList)&&json.newsList.length>0){
		    	      $(".icon1_show").append('<i class="icon1">'+json.newsList.length+'</i>');
		    	   }
	    	   }
	       }
	  });
}

// add by xiehuilin 2017/06/26 跳转已完成列表
function target(){
	$(".complete").click(function(){
		_g("completed.html");
	});
}


//去评论
function commentTo(id){
	var data = {"id":id}
	 $.ajax({
		   type : "get",
	       url:dataLink+"homepage/commentTo", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	   		    	localStorage.setItem("team_eId",json.retinfo.eId);
	   		    	localStorage.setItem("team_roleType",json.retinfo.roleType);
	   		    	window.location.href="comment.html";
	    	   }
	       }
	  });
}















var pageTwo = '';
var verdictTwo=true;//是否继续刷新
var userId = localStorage.getItem("team_userId");
var topbb ='';

$(window).scroll(function () {
	if(topbb==2){
		var bot =($(".bigshdow").height())*3; 
        if (verdictTwo) {
            if ((bot + $(window).scrollTop()) >= ($("body").height() - $(window).height())) {
                verdictTwo=false;
                pageTwo+=1;
                tadayEvent(pageTwo);
            }
        }
	}
   
});



//今日事件
function tadayEvent(page){
	topbb =localStorage.getItem("team_top")
	pageTwo=page;
	$(".tasktodo").hide();
	if(page==1){
		verdictTwo=true;
	}
	var data = {"userId":userId,"pageNum":pageTwo}
	 $.ajax({
		   type : "get",
	       url:dataLink+"homepage/tadayEvent", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
		    	   	$(".hot_spot_default").hide(); 
					$(".list_default").hide();
					$(".todayEvents_default").hide();
	    	   	if(page==1){
	    	   		$(".todayEvents").html("");  
	    	   	}
	    	   	if(!isnull(json.pageInfo)&&json.pageInfo.length>0){
	    	   		for(i=0;i<json.pageInfo.length;i++){
	    	   			var item = json.pageInfo[i];
	    	   			
	    	   			var percent =isnull(item.percent) ? "0": Number(Number(item.percent)*Number(100)).toFixed(0);
	    	   			var address = item.type==2 ? '</br><img class="address-img" src="img/address.png" alt="" /><span class="address">'+item.address+'</span>' :"";
	    	   		    var styclass = item.type==2 ? 'activity':'event';
	    	   		    var html  ='<div class="bigshdow '+styclass+'" id="'+item.eId+'"><p class="name" id="'+item.cuserId+'">'+item.name+'</p>';
	    	   		        html +='<img class="time" src="img/time.png" alt="" /><span class="bgtime">'+Timeday(item.startTime)+'</span><span>—</span><span class="endtime">'+Timeday(item.endTime)+'</span>';
	    	   		        html +=address;
	    	   		        html +='<div class="completion-degree" id="'+item.type+'"><span>完成度：</span><b>'+percent+'%</b></div></div>';
	    	   		        
	    	   		    $(".todayEvents").append(html);    
	    	   		} 
	    	   		$(".bigshdow").click(function(){
	    	   			var eId = $(this).attr("id");
	    	   			var type = $(this).find(".completion-degree").attr("id");
	    	   			var cuserId = $(this).find("p").attr("id");
	    	   			localStorage.setItem("team_eId",eId);
	    	   			if(userId==cuserId){
	    	   				if(type==0){
	    	   					window.location.href="eventdetailsone.html";
	    	   				}else if(type==1){
	    	   					window.location.href="servicedetailsone.html";
	    	   				}else if(type==2){
	    	   					window.location.href="event_description.html";
	    	   				}
	    	   			}else{
	    	   				if(type==0){
	    	   					window.location.href="eventdetailsone.html";
	    	   				}else if(type==1){
	    	   					window.location.href="eventdetailsone.html";
	    	   				}else if(type==2){
	    	   					window.location.href="sign_in.html";
	    	   				}
	    	   			}
	    	   		});
	    	   		
	    	   	 }else{
	    	   	 	verdictTwo=false;
	    	   	 	if(page==1){
	    	   		   $(".todayEvents").hide();  
	    	   		   $(".todayEvents_default").show();
	    	      	}else{
	    	      	   $(".todayEvents_default").hide();
	    	      	}
	    	   	 }
	    	   }
	    	}
	});
	
}





















var pageThree = '';
var verdictThree=true;//是否继续刷新
var userId = localStorage.getItem("team_userId");
//add by xiehuilin 2017/06/16 屏蔽分享菜单
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
   WeixinJSBridge.call('hideOptionMenu');
});

var topcc ="";

$(window).scroll(function () {
   if(topcc==3){
        var bot =($(".find-event").height())*3;
        if (verdictThree) {
            if ((bot + $(window).scrollTop()) >= ($("body").height() - $(window).height())) {
                verdictThree=false;
                pageThree+=1;
                findEvent(pageThree);
            }
        }
    }
 });


//热点发现
function findEvent(page){
	topcc =localStorage.getItem("team_top");
	$(".tasktodo").hide();
	pageThree = page;
	if(page==1){
		verdictThree=true;
	}
	var data = {"userId":userId,"pageNum":pageThree}
	 $.ajax({
		   type : "get",
	       url:dataLink+"homepage/findEvent", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	$(".hot_spot_default").hide(); 
				$(".list_default").hide();
				$(".todayEvents_default").hide();
		    	   	if(page==1){
		    	   		 $(".hot-spot").html(""); 
		    	   	}
	    	   	if(json.pageInfo.length>0){
	    	   		for(i=0;i<json.pageInfo.length;i++){
	    	   			var item = json.pageInfo[i];
	    	   			var html  ='';
	    	   			var addressHtml ='';
	    	   			//组织活动
	    	   			if(item.type==2){
	    	   			    html +='<div class="find-event bigshdow">';
	    	   			    addressHtml ='<img class="address-img" src="img/address.png" alt="" />';
	    	   			    addressHtml +='<span class="address">'+item.address+'</span>';
	    	   			    addressHtml +='<div style="clear: both;"></div>';
	    	   			//0 时间管理 、1 服务请求、
	    	   			}else{
	    	   				html +='<div class="find-event bigshdow">';
	    	   			}
	    	   			    html +='<div class="left"><img src="'+imageHeadSrc+item.logo+'" alt="" /></div>';
	    	   			    html +='<div class="right"><p class="name">'+item.name+'</p><i class="share">';
	    	   			    html +='<em style="display:none;">'+item.eId+'</em><em style="display:none;">'+item.type+'</em>';
	    	   			    html +='<em style="display:none;">'+item.name+'</em><em style="display:none;">'+item.target+'</em>';
	    	   			    html +='<em style="display:none;">'+item.address+'</em><em style="display:none;">'+item.logo+'</em>';
	    	   			    html +='</i>';
	    	   			    html +='<p class="plan1">'+item.target+'</p>';
	    	   			    html +='<img class="time" src="img/time.png" alt="" />';
	    	   			    html +='<span class="bgtime">'+Timeday(item.startTime)+'</span><span>—</span><span class="endtime">'+Timeday(item.endTime)+'</span><br />';
	    	   			    html +=addressHtml+'<button class="togoto">去参选</button></div></div>';
	    	   			$(".hot-spot").append(html);
                    	//$(".left").height($(".right").height());
	               }
	    	   		$(".togoto").click(function(){
	    	   			var eId = $(this).parent().children(".share").find("em").eq(0).html();
	    	   			var type = $(this).parent().children(".share").find("em").eq(1).html();
	    	   			localStorage.setItem("team_eId",eId);
	    	   				if(type==0){
	    	   					window.location.href="eventdetailsone.html";
	    	   				}else if(type==1){
	    	   					window.location.href="servicedetailsone.html";
	    	   				}else if(type==2){
	    	   					window.location.href="sign_in.html";
	    	   				}
	    	   		});
	    	   		
//	    	   		点击出现分享引导页
	    	   		$(".share").click(function(){
	    	   			$(".share_page").css("display","block");
	    	   	    	$(".share_page1").css("display","block");
	    	   	    	WeixinJSBridge.call('showOptionMenu');
	    		 		var eId = $(this).find("em").eq(0).html();
	    		 		var type =$(this).find("em").eq(1).html();
	    		 		var name = $(this).find("em").eq(2).html();
	    		 		var target =$(this).find("em").eq(3).html();
	    		 		var address = $(this).find("em").eq(4).html();
	    		 		var logo =$(this).find("em").eq(5).html();
	    		 		if (!isnull(eId) && !isnull(type)) {
	    		 			if (type==2) {
	    		 				go_sign(eId,target,(imageHeadSrc+logo),name);
							}else if (type==1) {
								go_shore(eId,target,(imageHeadSrc+logo),name);
							}
	    		 			
	    				}
	    		  });
	    	   		
	    	   	}else{
	    	   		verdictThree=false;
	    	   		if(page==1){
		    	   		 $(".hot-spot").hide(); 
		    	   		 $(".hot_spot_default").show(); 
		    	   	}else{
		    	   		 $(".hot_spot_default").hide(); 
		    	   	}
	    	   	}
	    	   	
	    	 }
	     }
	});
}


function go_sign(eId,target,logo,name){
	var urlDate = window.location.search; 
	var pageName ="index";
	_wx._getWxConfig(pageName,urlDate);
	var shoreContent ="【组织活动】"+ target;
	var shoreImg = logo;
	_sign._vip_member(shoreImg,eId,shoreContent,name);
	
}


var _sign={
		_vip_member:function(shoreImg,eId,shoreContent,name){
			var shoreLink = skipHttp+"sign_in.html?eId="+eId+"&soure="+1;
			_sign._show(shoreImg,shoreLink,shoreContent,name);
		},
		_show:function(shoreImg,shoreLink,shoreContent,name){
			var shoreTitle =name
			_wx._shore(shoreTitle,shoreContent,shoreLink,shoreImg);
		}
	}



//add by xiehuilin 20170621 去分享

function go_shore(eId,target,logo,name){
	var urlDate = window.location.search; 
	var pageName ="index";
	_wx._getWxConfig(pageName,urlDate);
	var shoreContent ="【服务请求】"+target;
	var shoreImg = logo;
	_shore._vip_member(shoreImg,eId,shoreContent,name);
	
}
var _shore={
	_vip_member:function(shoreImg,eId,shoreContent,name){
		var shoreLink = skipHttp+"servicedetailsone.html?eId="+eId+"&soure="+1;
		_shore._show(shoreImg,shoreLink,shoreContent,name);
	},
	_show:function(shoreImg,shoreLink,shoreContent,name){
		var shoreTitle =name
		_wx._shore(shoreTitle,shoreContent,shoreLink,shoreImg);
	}
}



































