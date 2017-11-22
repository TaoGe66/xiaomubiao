//var pageTwo = '';
//var verdictTwo=true;//是否继续刷新
var userId = localStorage.getItem("team_userId");
//var topbb ='';
//点击分类弹出列表
$(".Index_con_two").on("click",".Index_project_title",function(){
	//
	if($(this).parent().find(".Index_con_two_info").css("display")=="inline"){
		$(this).parent().find(".Index_con_two_info").hide();
		$(this).parent().removeClass("Project_global_current");
	//
	}else{
		$(this).parent().find(".Index_con_two_info").css("display","inline");
		$(this).parent().addClass("Project_global_current");
	}
	//
//	if($(this).parent().find(".Index_all").css("display")=="block"){
//		$(this).parent().find(".Index_all").hide();
//		$(this).removeClass("Project_global_current");
//	//
//	}else{
//		$(this).parent().find(".Index_all").css("display","block");
//		$(this).addClass("Project_global_current");
//	}
});
//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
//$(window).scroll(function () {
//	if(topbb==2){
//		var bot =($(".smallshdow").height())*3; 
//      if (verdictTwo) {
//          if ((bot + $(window).scrollTop()) >= ($("body").height() - $(window).height())) {
//              verdictTwo=false;
//              pageTwo+=1;
//              tadayEvent(pageTwo);
//          }
//      }
//	}
// 
//});

//项目进行中
function tadayEvent(){
	//topbb =localStorage.getItem("team_top")
	//pageTwo=page;
	var data = {"userId":userId};//,"pageNum":pageTwo,"numPerPage":20
	 $.ajax({
		   type : "get",
	       url:dataLink+"homepage/tadayEvent", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	     	$(".one_info").html(""); 
	    	   	  /* $(".Index_con_two_info").show();
	    	   	   $(".todayEvents_default").hide();
		    	  	if(page==1){
		    	   		$(".Index_con_two_info").html("");  
		    	   		verdictTwo=true;
		    	  	}*/
	    	   
	    	   		if(!isnull(json.pageInfo)&&json.pageInfo.length>0){
		    	   		for(i=0;i<json.pageInfo.length;i++){
		    	   			var item = json.pageInfo[i];
		    	   			
		    	   			var imgtop ='';
		    	   			var nameType ='创建人：';
		    	   			var redspan = isnull(item.isRecord) ? '':'<span class="red_span" id="'+item.isRecord+'"></span>';
		    	   			var perHtml= '';
		    	   			var percent =isnull(item.percent) ? "0": Number(Number(item.percent)*Number(100)).toFixed(0);
		    	   			var timePer ='';
		    	   			var qingqi = '';
		    	   			if(!isnull(item.leId)){
	    	   						qingqi = '<span class="Index_con_title_red">['+item.ligName+']</span>';
	    	   					}
		    	   			if(item.type==0){
		    	   				timePer +='<div class="progress-out Index_div1 Index_divcss_'+i+'" id="div1"><div class="percent-show">';
		    	   			    timePer +='<span></span></div><div class="progress-in"></div></div>';
		    	   				perHtml='<p class="Index_two_font">健康度：<i>'+percent+'%</i></p>';
		    	   				imgtop ='<p class="Index_two_title" id="'+item.type+'" style="background-image: url(img/icon11.png)"; >'+qingqi+item.name+redspan+'</p>';
		    	   			}else if(item.type==1){
		    	   				timePer +='<div class="progress-out Index_div1 Index_divcss_'+i+'" id="div1"><div class="percent-show">';
		    	   			    timePer +='<span></span></div><div class="progress-in"></div></div>';
		    	   				perHtml='<p class="Index_two_font">健康度：<i>'+percent+'%</i></p>';
		    	   				if(item.isTypeTwo ==1){
		    	   					nameType ='责任人：';
		    	   				}
		    	   				imgtop ='<p class="Index_two_title Index_two_titletwo" id="'+item.type+'" style="background-image: url(img/icon12.png)"; >'+qingqi+item.name+redspan+'</p>';
		    	   			}else if(item.type==2){
		    	   				imgtop ='<p class="Index_two_title Index_two_titlethree" id="'+item.type+'" style="background-image: url(img/icon17.png)"; >'+qingqi+item.name+'</p>';
		    	   			}
		    	   			var icon = '';
		    	   			if(item.tState==2 ||item.tState==6 ||item.tState==7 ||item.tState==8){
		    	   				icon ='<span class="Index_two_icon">待评价</span>';
		    	   			}else if(item.tState==0 ||item.tState==1 ||item.tState==3 ||item.tState==4 ||item.tState==5){
		    	   				icon ='<span class="Index_two_icon" style="display:none">待评价</span>';
		    	   			}
		    	   			
		    	   			//创建人 ： 张三
		    	   			var html  ='<li class="Index_today_list" id="'+item.eId+'"><div class="index_kong">';
		    	   			    html +=imgtop;
		    	   			    html +='<p class="Index_two_people">'+nameType+item.userName+'</p><div class="Index_two_bottom" id="'+item.cuserId+'">';
		    	   			    html +='<p class="Index_two_time"><span>'+sameDate(item.startTime,item.endTime)+'</span></p>';
		    	   			    html +=perHtml+'</div>';
		    	   			    html +=timePer;
								html +=icon+'</div></li>';
							
							$(".one_info").append(html);
							
						//	var cssdiv ='.Index_divcss_'+i;
						//	var wiDth = $(".Index_two_time span").width();
						//	$(cssdiv).myProgress({speed: 1000, percent: betweenDate(item.startTime,item.endTime), width: wiDth, height: "0.8rem"});
		    	   		}
		    	   		
		    	   		$(".Index_two_icon").click(function(){
		    	   			var eId =$(this).parent().attr("id");
		    	   			localStorage.setItem("team_eId",eId);
		    	   		    window.location.href="comment.html";
		    	   		    return false;
		    	   		});
		    	   		$(".Index_today_list").click(function(){
		    	   			var eId =$(this).attr("id");
		    	   			var type = $(this).find(".Index_two_title").attr("id");
	    	    			var cuserId = $(this).find(".Index_two_bottom").attr("id");
	    	    			var isrecord_id = $(this).find(".red_span").attr("id");
		    	   			localStorage.setItem("team_eId",eId);
		    	   			localStorage.setItem("isrecord_id",isrecord_id);
		    	   				if(type==0){
		    	   					window.location.href="eventdetailsone.html";
		    	   				}else if(type==1){
		    	   					get.joinInfo();
		    	   				}else if(type==2){
		    	   					get.eventInfo();
		    	   				}
		    	   		});
		    	   		$(".Index_project:first-child").addClass("Project_global_current");
		    	   		$(".Index_project:first-child ul").css("display","inline");
		    	   		$(".project_title_one i").html("("+json.pageInfo.length+")");
		    	   		//verdictTwo=true;
	    	   		}else{
	    	   			$(".project_title_one i").html("(0)");
		    	   		//verdictTwo=false;
		    	   	 	//if(page==1){
		    	   		 //  $(".Index_con_two_info").hide();  
		    	   		//   $(".todayEvents_default").show();
		    	      //	}
	    	   		}
	    	   }
	    }
	});
}

function  complete(){
	var data={"userId":userId,"state":2}
	$.ajax({
		  type : "GET",
	      url:dataLink+"event/listJoinTwo",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    	  	$(".two_info").html("");
                   if(!isnull(json.list)&&json.list.length>0){
		    	   		for(i=0;i<json.list.length;i++){
		    	   			var item = json.list[i];
		    	   			
		    	   			var imgtop ='';
		    	   		//	var nameType ='创建人：';
		    	   			var redspan = isnull(item.isRecord) ? '':'<span class="red_span" id="'+item.isRecord+'"></span>';
		    	   			var perHtml= '';
		    	   			var percent =isnull(item.percent) ? "0": Number(Number(item.percent)*Number(100)).toFixed(0);
		    	   			var timePer ='';
		    	   			var qingqi = '';
		    	   			if(!isnull(item.leId)){
	    	   						qingqi = '<span class="Index_con_title_red">['+item.leName+']</span>';
	    	   					}
		    	   			if(item.eventType==0){
		    	   				timePer +='<div class="progress-out Index_div1 Index_divcss_'+i+'" id="div1"><div class="percent-show">';
		    	   			    timePer +='<span></span></div><div class="progress-in"></div></div>';
		    	   				perHtml='<p class="Index_two_font">健康度：<i>'+percent+'%</i></p>';
		    	   				imgtop ='<p class="Index_two_title" id="'+item.eventType+'" style="background-image: url(img/icon11.png)"; >'+qingqi+item.name+redspan+'</p>';
		    	   			}else if(item.eventType==1){
		    	   				timePer +='<div class="progress-out Index_div1 Index_divcss_'+i+'" id="div1"><div class="percent-show">';
		    	   			    timePer +='<span></span></div><div class="progress-in"></div></div>';
		    	   				perHtml='<p class="Index_two_font">健康度：<i>'+percent+'%</i></p>';
		    	   				/*if(item.isTypeTwo ==1){
		    	   					nameType ='责任人：';
		    	   				}*/
		    	   				imgtop ='<p class="Index_two_title Index_two_titletwo" id="'+item.eventType+'" style="background-image: url(img/icon12.png)"; >'+qingqi+item.name+redspan+'</p>';
		    	   			}else if(item.eventType==2){
		    	   				imgtop ='<p class="Index_two_title Index_two_titlethree" id="'+item.eventType+'" style="background-image: url(img/icon17.png)"; >'+qingqi+item.name+'</p>';
		    	   			}
		    	   			var nameT ='';
		    	   			if (item.eventType ==0 || item.eventType ==2 || isnull(item.dutyName)) {
								nameT='创建人：'+item.userName;
							}else{
								nameT='责任人：'+item.dutyName;
							}
		    	   			//创建人 ： 张三
		    	   			var html  ='<li class="Index_today_list" id="'+item.eId+'"><div class="index_kong">';
		    	   			    html +=imgtop;
		    	   			    html +='<p class="Index_two_people">'+nameT+'</p><div class="Index_two_bottom" id="'+item.cuserId+'">';
		    	   			    html +='<p class="Index_two_time"><span>'+sameDate(item.startTime,item.endTime)+'</span></p>';
		    	   			    html +=perHtml+'</div>';
		    	   			    html +=timePer+'</div></li>';
							
							$(".two_info").append(html);
							
						//	var cssdiv ='.Index_divcss_'+i;
						//	var wiDth = $(".Index_two_time span").width();
						//	$(cssdiv).myProgress({speed: 1000, percent: betweenDate(item.startTime,item.endTime), width: wiDth, height: "0.8rem"});
		    	   		}
		    	   		
		    	   		$(".Index_two_icon").click(function(){
		    	   			var eId =$(this).parent().attr("id");
		    	   			localStorage.setItem("team_eId",eId);
		    	   		    window.location.href="comment.html";
		    	   		    return false;
		    	   		});
		    	   		$(".Index_today_list").click(function(){
		    	   			var eId =$(this).attr("id");
		    	   			var type = $(this).find(".Index_two_title").attr("id");
	    	    			var cuserId = $(this).find(".Index_two_bottom").attr("id");
	    	    			var isrecord_id = $(this).find(".red_span").attr("id");
		    	   			localStorage.setItem("team_eId",eId);
		    	   			localStorage.setItem("isrecord_id",isrecord_id);
		    	   				if(type==0){
		    	   					window.location.href="eventdetailsone.html";
		    	   				}else if(type==1){
		    	   					get.joinInfo();
		    	   				}else if(type==2){
		    	   					get.eventInfo();
		    	   				}
		    	   		});
		    	   		$(".project_title_two i").html("("+json.list.length+")");
	    	   		}else{
	    	   			$(".project_title_two i").html("(0)");
	    	   		}

                }
	    	 }
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
		var param={"eId":eId,"userId":userId};
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