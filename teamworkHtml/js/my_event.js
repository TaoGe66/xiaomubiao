var page=1;
var verdict=true;
$(window).scroll(function() {
	if (verdict) {
		verdict = false;
		page += 1;
		var cut = localStorage.getItem("team_isCut");
		if (cut == 1) {
			complete(page);
		} else {
			under_way(page);
		}
	}
});

$(function(){
	$(".li1").on("click",function(){
		$(".table ul .li1 a").attr("class","a1");
		$(".table ul .li2 a").attr("class","a2");
		$(".table ul .li1 a").css("color","#FF0000");
		$(".table ul .li2 a").css("color","#333");
		$(".content").css("display","block");
		$(".content1").css("display","none");
		$(".my_event_default").hide();
		localStorage.setItem("team_isCut",null);
		$(".content").html("");
		page=1;
		under_way(page);
	});
	$(".li2").on("click",function(){
		$(".table ul .li1 a").attr("class","a1_click");
		$(".table ul .li2 a").attr("class","a2_click");
		$(".table ul .li2 a").css("color","#FF0000");
		$(".table ul .li1 a").css("color","#333");
		$(".content").css("display","none");
		$(".content1").css("display","block");
		$(".my_event_default").hide();
		localStorage.setItem("team_isCut",1);
		$(".content1").html("");
		page=1;
		complete(page);
	});
	
	//点击灰色区域
	$(".event_summary").click(function(){
		$(".event_summary").hide();
		$(".Index_accept").hide();
		$(".Index_overtime").hide();
	});
	$(".not_set").click(function(){
		$(".event_summary").show();
		$(".Index_overtime").show();
	});
	//点击弹窗取消按钮
	$(".Index_overtime_btnone").click(function(){
		$(".event_summary").hide();
		$(".Index_overtime").hide();
	});
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
})

var createBy=localStorage.getItem("team_userId");

	 


under_way(page);
//进行中 		wuchao
function  under_way(page){
	localStorage.setItem("team_isCut",null);
	var data={"userId":createBy,"state":1,"pageNum":page}
	$.ajax({
		  type : "GET",
	      url:dataLink+"event/listJoin",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  var list=json.list;
	    		  (list.length==0 && page==1) ?$(".my_event_default").show():$(".my_event_default").hide();
	    		  if (list.length>0) {
	    			  for (var i = 0; i < list.length; i++) {
						var item = list[i];
						var percent=item.percent=="NaN"? 0 :item.percent;
						var headUrl=item.headUrl==null? "img/img03.png" :imageHeadSrc+item.headUrl;
						var mold_logo="";
						var imge_class="";
						var redspan = isnull(item.isRecord) ? '':'<i class="list1_icon" id="'+item.isRecord+'"></i>';
						if (item.eventType ==0) {
							imge_class="list1";
							mold_logo="img/time_management2.png";
						}else if(item.eventType ==1){
							imge_class="list2";
							 mold_logo="img/service_request1.png";
						}else if(item.eventType ==2){
							imge_class="list3";
							 mold_logo="img/activity2.png";
						}
						var html ='<div class="'+imge_class+' jump">';
							html+='<em style="display:none;">'+item.eventType+'</em>';
							html+='<em style="display:none;">'+item.eId+'</em>';
							html+='<em style="display:none;">'+item.leId+'</em>';
							html+='<div class="left"><div class="top"><img src="'+mold_logo+'" alt="" />'+redspan;
							if (!isnull(item.leId)) {
							html+='<span class="Index_con_title_red">['+item.leName+']</span>';
							}
							html+='<p>'+item.name+'</p></div>';
							if (item.eventType ==0 || item.eventType ==2 || isnull(item.dutyName)) {
								html+='<div class="top1"><span class="sp1">负责人：</span><span class="sp2">'+item.userName+'</span></div>';
							}else{
								html+='<div class="top1"><span class="sp1">责任人：</span><span class="sp2">'+item.dutyName+'</span></div>';
							}
							html+='<div class="bottom"><img src="img/time.png" alt="" />';
							html+='<span>'+sameDate(item.startTime,item.endTime)+'</span>';
							if (item.eventType !=2) {
								html+='<div class="right"><span>健康度：</span><i>'+(percent*100).toFixed(0)+'%</i></div>';
							}
							html+='</div></div>';
							$(".content").append(html);
					}
	    			  verdict=true;
				}else{
					verdict=false;
				}
	    		//跳转事件详情
	    		  $(".jump").click(function(){
	    			var  eventType=$(this).find("em").eq(0).html();
	    			var  eId= $(this).find("em").eq(1).html();
	    			var  leId= $(this).find("em").eq(2).html();
	    			var isrecord_id = $(this).find(".list1_icon").attr("id");
	    			localStorage.setItem("team_eId",eId);
	    			localStorage.setItem("isrecord_id",isrecord_id);
	    			if (!isnull(leId)) {
	    				localStorage.setItem("team_leId",leId);
						}
	    			if (eventType==0) {
	    				window.location.href="eventdetailsone.html";
					}else if(eventType==1){
					    get.joinInfo();
					}else if(eventType==2){
						//window.location.href="sign_in.html";
						get.eventInfo();
					}
	    			
	    		  });
	    	  }
	      },  
	      timeout:3000  
	 }); 
}

//已完成		wuchao
function  complete(page){
	var data={"userId":createBy,"state":2,"pageNum":page}
	$.ajax({
		  type : "GET",
	      url:dataLink+"event/listJoin",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  var list=json.list;
	    		  (list.length==0 && page==1) ?$(".my_event_default").show():$(".my_event_default").hide();
	    		  if (list.length>0) {
	    			  for (var i = 0; i < list.length; i++) {
						var item = list[i];
						var percent=item.percent=="NaN"? 0 :item.percent;
						var headUrl=item.headUrl==null? "img/img03.png" :imageHeadSrc+item.headUrl;
						var mold_logo="";
						var imge_class="";
						if (item.eventType ==0) {
							var imge_class="list1";
							mold_logo="img/time_management3.png";
						}else if(item.eventType ==1){
							var imge_class="list2";
							 mold_logo="img/service_request2.png";
						}else if(item.eventType ==2){
							var imge_class="list3";
							 mold_logo="img/activity3.png";
						}
						var html ='<div class="'+imge_class+' jump">';
							html+='<em style="display:none;">'+item.eventType+'</em>';
							html+='<em style="display:none;">'+item.eId+'</em>';
							html+='<em style="display:none;">'+item.leId+'</em>';
							html+='<div class="left"><div class="top"><img src="'+mold_logo+'" alt="" />';
							if (!isnull(item.leId)) {
								html+='<span class="Index_con_title_red">['+item.leName+']</span>';
								}
							html+='<p>'+item.name+'</p></div>';
							if (item.eventType ==0 || item.eventType ==2 || isnull(item.dutyName)) {
								html+='<div class="top1"><span class="sp1">负责人：</span><span class="sp2">'+item.userName+'</span></div>';
							}else{
								html+='<div class="top1"><span class="sp1">责任人：</span><span class="sp2">'+item.dutyName+'</span></div>';
							}
							
							
							html+='<div class="bottom"><img src="img/time.png" alt="" />';
							html+='<span>'+sameDate(item.startTime,item.endTime)+'</span>';
							if (item.eventType !=2) {
								html+='<div class="right"><span>健康度：</span><i>'+(percent*100).toFixed(0)+'%</i></div>';
							}
							html+='</div></div>';
							$(".content1").append(html);
					}
	    			  verdict=true;
				}else{
					verdict=false;
				}
	    	  }
	    		//跳转事件详情
    		  $(".jump").click(function(){
    			var  eventType=$(this).find("em").eq(0).html();
    			var  eId= $(this).find("em").eq(1).html();
    			localStorage.setItem("team_eId",eId);
    			var  leId= $(this).find("em").eq(2).html();
    			if (!isnull(leId)) {
    				localStorage.setItem("team_leId",leId);
					}
    			if (eventType==0) {
    				window.location.href="eventdetailsone.html";
				}else if(eventType==1){
					get.joinInfo();
					
				}else if(eventType==2){
					get.eventInfo();
					//window.location.href="sign_in.html";
				}
    			
    		  });
	      },  
	      timeout:3000  
	 }); 
}


//var eId = localStorage.getItem("team_eId");
//add  by xiehuilin 2017/06/23 
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

//时间插件
$(function () {
            var curr = new Date().getFullYear();
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
//              $('.demo-test-' + demo).scroller('destroy').scroller($.extend(opt['time'], opt['default']));
                $("#test").scroller('destroy').scroller($.extend(opt['date'], opt['default']));
                $('.demo').hide();
                $('.demo-' + demo).show();
            });
            $('#demo').trigger('change');
        });




/*
ddd();

function ddd(){
	var data={"eId":11}
	$.ajax({
		  type : "GET",
	      url:dataLink+"event/updateCreate",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  
	    	  }
	      },  
	      timeout:3000  
	 }); 
}*/