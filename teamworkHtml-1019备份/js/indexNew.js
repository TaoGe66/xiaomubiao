var userId = localStorage.getItem("team_userId");
var topaa ='';
var pageOne= '';
var verdictOne=true;//是否继续刷新
var reslutLng="";
var reslutlat="";
var clng="";
var clat="";
var id="";
var eIdMap="";
//add by xiehuilin  奖惩行动id
var reward_eiId="";
//add by xiehuilin  奖惩类别
var rpCategory="";
//add by xiehuilin  奖惩收益人
var beneficiary="";
   $(window).scroll(function () {
   if(topaa==1){
        var bot =($(".Index_con_list").height())*3;
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
//	show_time();
	 
//	点击弹窗灰色区域弹窗消失
	$(".event_summary").click(function(){
		$(".event_summary").hide();
		$(".event_summary1").hide();
		$(".Index_accept").hide();
		$(".Index_accept_two").hide();
		$(".Index_sign").hide();
		$(".Index_alert_content_inner").hide();
		$(".Index_overlook").hide();
		$(".Event_alertthree").hide();
		$(".Index_reward").hide();
		$(".Index_rewardtwo").hide();
	});
	$(".Index_title").hide();
	$(".Index_content_all li").click(function(){
		$(".Index_content_all li").removeClass("default");
        $(this).addClass("default");
		$(".Index_con_current").hide().eq($(this).index()).show();
		if($(".Index_nav_one").hasClass("default")){
			if(verificationUserLogin()){
			   $(".Index_title").show();
			   $(".Index_con_one").show(); 
	    	   $(".Index_con_two").hide(); 
	    	   $(".Index_con_three").hide(); 
	    	   localStorage.setItem("team_top",1);
			   tobeEventList(1);
			}
		}else if($(".Index_nav_two").hasClass("default")){
			if(verificationUserLogin()){
				$(".Index_title").hide();
			    $(".Index_con_one").hide(); 
	    	//    $(".Index_con_two").show(); 
	    	    $(".Index_con_three").hide(); 
	    	  /*  localStorage.setItem("team_top",2);
				tadayEvent(1);*/
				tadayEvent();
				complete();
			}
		}else if($(".Index_nav_three").hasClass("default")){
			$(".Index_title").hide();
			$(".Index_con_one").hide(); 
    	    $(".Index_con_two").hide(); 
    	    $(".Index_con_three").show(); 
			localStorage.setItem("team_top",3);
			findEvent(1);
		}
   });

   $(".cancel").click(function(){
    	$(".event_summary").hide();
	    $(".event_summary1").hide();
   });
   $(".Index_sign_btnone").click(function(){
    	$(".event_summary").hide();
	    $(".Index_sign").hide();
   });
   $(".Index_accept_btnone").click(function(){
     	$(".event_summary").hide();
     	$(".Index_accept_two").hide();
     	$(".Index_accept").hide();
     	$(".Index_rewardtwo").hide();
     	$(".Index_reward").hide();
   });
   $(".Index_overlook_btnone").click(function(){
    	$(".event_summary").hide();
    	$(".Index_overlook").hide();
   });
   $(".Event_track_no").click(function(){
   	    $(".event_summary").hide();
   	    $(".Event_alertthree").hide();
   	
   });
   //首页跳转
    $(".Index_footer_btntwo").click(function(){
    	if(verificationUserLogin()){
	    	$(".Index_nav span").removeClass("Index_nav_current");
	    	$(".Index_nav_one").addClass("Index_nav_current");
	    	$(".Index_con_one").show(); 
	    	$(".Index_con_two").hide(); 
	    	$(".Index_con_three").hide(); 
	    	localStorage.setItem("team_top",1);
		    tobeEventList(1);
	    }
   });
   //个人中心跳转
    $(".Index_footer_btnone").click(function(){
       if(verificationUserLogin()){
			window.location.href="personal.html";
		}
   });
   
   if(isnull(userId)){
   	    $(".tabs li ").removeClass("default");
    	$(".Index_nav_three").addClass("default");
   	    $(".Index_con_one").hide(); 
	    $(".Index_con_two").hide(); 
	    $(".Index_con_three").show(); 
	    $(".Index_title").hide();
		localStorage.setItem("team_top",3);
		findEvent(1);
   }else{
		if(verificationUserLogin()){
			$(".tabs li").removeClass("default");
			$(".Index_nav_one").addClass("default");
			$(".Index_con_one").show(); 
			$(".Index_con_two").hide(); 
			$(".Index_con_three").hide(); 
			$(".Index_title").show();
			localStorage.setItem("team_top",1);
		    tobeEventList(1);
		    getNews();
		}
     	
   }

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
	//完成跳转
	$(".Index_title_button").click(function(){
		window.location.href="completed.html";
	});
	$(".tohfind").click(function(){
		$(".Index_nav span").removeClass("Index_nav_current");
    	$(".Index_nav_three").addClass("Index_nav_current");
   	    $(".Index_con_one").hide(); 
	    $(".Index_con_two").hide(); 
	    $(".Index_con_three").show(); 
		localStorage.setItem("team_top",3);
		findEvent(1);
	});
});
var twelve_flag = false; //开始处理
var eleven_flag = false;//周委托查看
var eleven_flag_one = false;//周委托查看接受
var five_flag = false; //查看
function tobeEventList(page){
	topaa =localStorage.getItem("team_top")
	pageOne=page;
	var data = {"userId":userId,"pageNum":pageOne,"numPerPage":30}
	 $.ajax({
		   type : "get",
	       url:dataLink+"homepage/tobeEventList", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	    	$(".list_default").hide();
	    	    	$(".Index_con_inner").show();
	    	    	if(page==1){
	    	    	  verdictOne=true;
	    	   		  $(".Index_con_inner").html("");
    	   		  	  todoFnish(json.user,json.totalNum,json.todayNum);
	    	   	    }
    	    		if(!isnull(json.pageInfo)&&json.pageInfo.length>0){
	    	    		for(i=0;i<json.pageInfo.length;i++){
		    	   			var item = json.pageInfo[i];
		    	   			var buttonStr ='';
		    	   			var imgtop ='';
		    	   			var logo = imageHeadSrc+item.logo;
		    	   			var conStr ='';
		    	   			var topcon='<div class="Index_con_list Index_con_listTo  smallshdow" id="'+item.eId+'">';
		    	   			var leftImg ='<div class="Index_list_left Index_list_left_two">';
		    	   			var rightFount ='<div class="Index_list_right" id="'+item.type+'">';
		    	   			var Yspan = '';
		    	   			var qingqi = '';
		    	   			//未完成
		    	   			if(item.isFinish!=1){
	    	   				   if(!isnull(item.leId)){
	    	   						qingqi = '<span class="Index_con_title_red">['+item.ligName+']</span>';
	    	   					}
		    	   				//目标
		    	   				if(item.type==0){
		    	   					
		    	   				 //事件
	    	   				        if(item.tSubjType==0){
			    	   					//新增计划
		    	   					    if(item.tState==0){
		    	   					    	logo ='img/icon01.png';
			    	   					    buttonStr ='<span class="Index_list_btnone" id="'+item.eId+'">新增计划</span>';
			    	   					    conStr ='<p class="Index_list_right_one Index_font_weight">'+item.tExplained+'</p><p class="Index_list_right_two">'+sameDate(item.startTime,item.endTime)+'</p>';
		    	   					    //关闭目标
		    	   					    }else if(item.tState==3){
		    	   					    	logo ='img/icon03.png';
		    	   					    	buttonStr ='<span class="Index_list_btnthree" id="'+item.eId+'"> <input type="hidden" id="'+item.id+'"/>关闭目标</span>';
		    	   					    	conStr ='<p class="Index_list_right_one Index_font_weight">'+item.tExplained+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
		    	   					    }
		    	   					//事项
		    	   					}else if(item.tSubjType==1){
		    	   						//开始处理
		    	   						if(item.tState==16){
		    	   							logo ='img/icon02.png';
			    	   					    buttonStr ='<span class="Index_list_btntwelve" id="'+item.id+'"><i id="'+item.eiId+'"/><input type="hidden" id="'+item.startTime+'"/>开始处理</span>';
			    	   					    conStr ='<p class="Index_list_right_three Index_font_weight">'+item.eiDesc+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
		    	   						//做完了	
		    	   						}else if(item.tState==0){
		    	   					    	Yspan = '<span class="Index_span"></span>';
		    	   					    	logo ='img/icon02.png';
			    	   					    buttonStr ='<span class="Index_list_btntwo" id="'+item.id+'"><i id="'+item.eiId+'"/><input type="hidden" id="'+item.startTime+'"/>做完了</span>';
			    	   					    conStr ='<p class="Index_list_right_three Index_font_weight">'+item.eiDesc+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
		    	   					    //委托进度
		    	   					    }else if(item.tState==1){
		    	   					    	topcon='<div class="Index_con_list smallshdow" id="'+item.eId+'">';
		    	   					    	logo ='img/icon04.png';
		    	   					    	buttonStr ='<span class="Index_list_btnfour" id="'+item.eiId+'"><input type="hidden" id="'+item.id+'"/>接受</span><span class="Index_list_btnfour_two" id="'+item.eiId+'"><input type="hidden" id="'+item.id+'"/>拒绝</span>';
		    	   					        conStr ='<p class="Index_list_right_one Index_font_weight">'+item.tExplained+'</p><p class="Index_list_right_three">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
		    	   					   //未接受
		    	   					    }else if(item.tState==2){
		    	   					    //	topcon='<div class="Index_con_list smallshdow" id="'+item.eId+'">';
		    	   					    	logo ='img/icon05.png';
		    	   					    	buttonStr ='<span class="Index_list_btnfive" id="'+item.eId+'"><i id="'+item.tState+'"></i><input type="hidden" id="'+item.id+'"/>查看</span>';
		    	   					        conStr ='<p class="Index_list_right_one Index_font_weight">'+item.tExplained+'</p><p class="Index_list_right_three">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
		    	   					    //周委托进度
		    	   					    }else if(item.tState==11){
		    	   					    	topcon='<div class="Index_con_list smallshdow" id="'+item.eId+'">';
		    	   					    	logo ='img/icon05_07.png';
		    	   					        buttonStr ='<span class="Index_list_eleven" id="'+item.eiId+'"><input type="hidden" id="'+item.id+'"/>查看</span>';
		    	   					        conStr ='<p class="Index_list_right_one Index_font_weight">'+item.tExplained+'</p><p class="Index_list_right_three">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
		    	   					   //周未接受
		    	   					    }else if(item.tState==12){
		    	   					    	logo ='img/icon05_08.png';
		    	   					    	buttonStr ='<span class="Index_list_btnfive" id="'+item.eId+'"><i id="'+item.tState+'"></i><input type="hidden" id="'+item.id+'"/>查看</span>';
		    	   					        conStr ='<p class="Index_list_right_one Index_font_weight">'+item.tExplained+'</p><p class="Index_list_right_three">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
		    	   					    //行动跟踪
		    	   					    }else if(item.tState==10){
		    	   					    	logo ='img/icon05_06.png';
		    	   					    	buttonStr ='<span class="Index_listt_btnsix" id="'+item.id+'">记录一下</span><span class="Index_listt_btnsix_two" id="'+item.id+'">忽略</span>';
		    	   					        conStr ='<p class="Index_list_right_three Index_font_weight">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
		    	   					    //执行奖惩
		    	   					    }else if(item.tState==17){
		    	   					    	logo ='img/icon02.png';
			    	   					    buttonStr ='<span class="Index_list_btnthirteen" id="'+item.id+'"><i class="thirteen_one" id="'+item.eiId+'"/>';
			    	   					    buttonStr +='<i class="thirteen_two" id="'+item.rpCategory+'"/><i class="thirteen_three" id="'+item.beneficiary+'"/><input type="hidden" id="'+item.startTime+'"/>执行</span>';
			    	   					    conStr ='<p class="Index_list_right_three Index_font_weight">'+item.eiDesc+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
		    	   					    }
		    	   					}
	    	   				        
		    	   					leftImg ='<div class="Index_list_left">';
		    	   					
		    	   					imgtop ='<p class="Index_con_title" style="background-image: url(img/icon11.png)";>';
		    	   			    //需求
		    	   				}else if(item.type==1){
		    	   					
		    	   				 //事件
	    	   				        if(item.tSubjType==0){
			    	   					//新增计划
		    	   					    if(item.tState==0){
			    	   					    buttonStr ='<span class="Index_list_btnone" id="'+item.eId+'">新增计划</span>';
			    	   					    conStr ='<p class="Index_list_right_one Index_font_weight">'+item.tExplained+'</p><p class="Index_list_right_two">'+sameDate(item.startTime,item.endTime)+'</p>';
		    	   					    //关闭目标
		    	   					    }else if(item.tState==3){
		    	   					    	buttonStr ='<span class="Index_list_btnthree" id="'+item.eId+'"> <input type="hidden" id="'+item.id+'"/>关闭需求</span>';
		    	   					    	conStr ='<p class="Index_list_right_one Index_font_weight">'+item.tExplained+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
		    	   					    }
		    	   					//事项
		    	   					}else if(item.tSubjType==1){
		    	   						//开始处理
		    	   						if(item.tState==16){
		    	   							buttonStr ='<span class="Index_list_btntwelve" id="'+item.id+'"><i id="'+item.eiId+'"/><input type="hidden" id="'+item.startTime+'"/>开始处理</span>';
			    	   					    conStr ='<p class="Index_list_right_three Index_font_weight">'+item.eiDesc+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
		    	   						//做完了	
		    	   						}else if(item.tState==0){
		    	   					    	Yspan = '<span class="Index_span"></span>';
			    	   					    buttonStr ='<span class="Index_list_btntwo" id="'+item.id+'"><i id="'+item.eiId+'"/><input type="hidden" id="'+item.startTime+'"/>做完了</span>';
			    	   					    conStr ='<p class="Index_list_right_three Index_font_weight">'+item.eiDesc+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
		    	   					    //委托进度
		    	   					    }else if(item.tState==1){
		    	   					    	topcon='<div class="Index_con_list smallshdow" id="'+item.eId+'">';
		    	   					    	buttonStr ='<span class="Index_list_btnfour" id="'+item.eiId+'"><input type="hidden" id="'+item.id+'"/>接受</span><span class="Index_list_btnfour_two" id="'+item.eiId+'"><input type="hidden" id="'+item.id+'"/>拒绝</span>';
		    	   					        conStr ='<p class="Index_list_right_one Index_font_weight">'+item.tExplained+'</p><p class="Index_list_right_three">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
		    	   					   //未接受
		    	   					    }else if(item.tState==2){
		    	   					    //	topcon='<div class="Index_con_list smallshdow" id="'+item.eId+'">';
		    	   					    	buttonStr ='<span class="Index_list_btnfive" id="'+item.eId+'"><i id="'+item.tState+'"></i><input type="hidden" id="'+item.id+'"/>查看</span>';
		    	   					        conStr ='<p class="Index_list_right_one Index_font_weight">'+item.tExplained+'</p><p class="Index_list_right_three">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
		    	   					    //周委托进度
		    	   					    }else if(item.tState==11){
		    	   					    	topcon='<div class="Index_con_list smallshdow" id="'+item.eId+'">';
		    	   					        buttonStr ='<span class="Index_list_eleven" id="'+item.eiId+'"><input type="hidden" id="'+item.id+'"/>查看</span>';
		    	   					        conStr ='<p class="Index_list_right_one Index_font_weight">'+item.tExplained+'</p><p class="Index_list_right_three">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
		    	   					   //周未接受
		    	   					    }else if(item.tState==12){
		    	   					    //	topcon='<div class="Index_con_list smallshdow" id="'+item.eId+'">';
		    	   					    	buttonStr ='<span class="Index_list_btnfive" id="'+item.eId+'"><i id="'+item.tState+'"></i><input type="hidden" id="'+item.id+'"/>查看</span>';
		    	   					        conStr ='<p class="Index_list_right_one Index_font_weight">'+item.tExplained+'</p><p class="Index_list_right_three">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
		    	   					    //行动跟踪
		    	   					    }else if(item.tState==10){
		    	   					    	buttonStr ='<span class="Index_listt_btnsix" id="'+item.id+'">记录一下</span><span class="Index_listt_btnsix_two" id="'+item.id+'">忽略</span>';
		    	   					        conStr ='<p class="Index_list_right_three Index_font_weight">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
		    	   					    //执行奖惩
		    	   					    }else if(item.tState==17){
			    	   					    buttonStr ='<span class="Index_list_btnthirteen" id="'+item.id+'"><i class="thirteen_one" id="'+item.eiId+'"/>';
			    	   					    buttonStr +='<i class="thirteen_two" id="'+item.rpCategory+'"/><i class="thirteen_three" id="'+item.beneficiary+'"/><input type="hidden" id="'+item.startTime+'"/>执行</span>';
			    	   					    conStr ='<p class="Index_list_right_three Index_font_weight">'+item.eiDesc+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
		    	   					    }
		    	   					}
		    	   					
		    	   				   logo = imageHeadSrc+item.logo;
		    	   				   imgtop ='<p class="Index_con_title Index_con_titletwo" style="background-image: url(img/icon12.png)";>';
		    	   				   
		    	   				   
		    	   				//活动
		    	   				}else if(item.type==2){
		    	   					logo = imageHeadSrc+item.logo;
		    	   					imgtop ='<p class="Index_con_title Index_con_titlethree" style="background-image: url(img/icon17.png)";>';
		    	   					if(item.tState==4){
		    	   						if(item.overdue==1){
		    	   							rightFount ='<div class="Index_list_right Index_list_righttwo" id="'+item.type+'">';
		    	   							imgtop ='<p class="Index_con_title Index_con_titlefive" >';
		    	   							buttonStr ='<span class="Index_list_btnsix_two" id="'+item.eId+'">签到</span>';
		    	   							conStr ='<p class="Index_list_right_one Index_font_weight">已过签到时间，无法签到</p><p class="Index_list_right_four">'+TimeChina(item.remindTime,22)+'</p>';
		    	   						}else{
		    	   							buttonStr ='<span class="Index_list_btnsix" id="'+item.eId+'">签到</span>';
		    	   						    conStr ='<p class="Index_list_right_one Index_font_weight">'+item.tExplained+'</p><p class="Index_list_right_four">'+TimeChina(item.remindTime,22)+'</p>';
		    	   						}
		    	   						
		    	   					}else if(item.tState==3){
		    	   						conStr ='<p class="Index_list_right_one Index_font_weight">'+item.tExplained+'</p>';
		    	   						buttonStr ='<span class="Index_list_btnseven" id="'+item.eId+'"> <input type="hidden" id="'+item.id+'"/>关闭活动</span>';
		    	   					}
		    	   				}
		    	   			}
		    	   			var html  =topcon+imgtop+qingqi+item.name+Yspan+'</p>';
		    	   			    html +='<div class="Index_con_list_bottom">'+leftImg+'<img src="'+logo+'" alt="" /></div>';
		    	   			    html +=rightFount;
		    	   			    html +=conStr+'<div class="Index_list_right_btn"  id="'+item.cuserId+'">'+buttonStr+'</div></div></div></div>';
		    	   			$(".Index_con_inner").append(html);
		    	   		}
	    	    		
	    	    		$(".Index_con_listTo").click(function(){
	    	    			var eId = $(this).attr("id");
	    	    			var type = $(this).find(".Index_list_right").attr("id");
	    	    			var cuserId = $(this).find(".Index_list_right_btn").attr("id");
	    	    			localStorage.setItem("team_eId",eId);
	    	    		   if(userId==cuserId){
		    	   				if(type==0){
		    	   					window.location.href="eventdetailsone.html";
		    	   					return false;
		    	   				}else if(type==1){
		    	   					window.location.href="svc_red_eventdetails.html";
		    	   					return false;
		    	   				}else if(type==2){
		    	   					window.location.href="view_participation.html";
		    	   					return false;
		    	   				}
		    	   			}else{
		    	   				if(type==0){
		    	   					window.location.href="eventdetailsone.html";
		    	   					return false;
		    	   				}else if(type==1){
		    	   					window.location.href="svc_red_eventdetails.html";
		    	   					return false;
		    	   				}else if(type==2){
		    	   					window.location.href="sign_in.html";
		    	   					return false;
		    	   				}
		    	   			}
	    	    		});
	    	    		//开始执行
	    	    		$(".Index_list_btnthirteen").click(function(){
	    	    			 reward_eiId = $(this).find(".thirteen_one").attr("id");
	    	    		     rpCategory = $(this).find(".thirteen_two").attr("id");
	    	    		    var money = $(this).parents(".Index_list_right").find(".Index_list_right_three").html();
	    	    		        money = money.split(",")[1].substring(2,8);
	    	    		    var userName = localStorage.getItem("team_userName");
	    	    		     beneficiary = $(this).find(".thirteen_").attr("id");
	    	    		    $(".Index_reward_btntwo").unbind("click");
	    	    		    $(".Index_reward_btntwo").click(function(){
	    	    		    	
	    	    		    });
	    	    		    if(rpCategory==2){
	    	    		    	$(".Index_reward_money i").html(money);
	    	    		    	$(".Index_reward_people i").html(userName);
	    	    		    	$(".Index_reward").show();
	    	    		    	$(".event_summary").show();
	    	    		    }else{
	    	    		    	$(".Index_rewardtwo_money i").html(money);
	    	    		    	$(".Index_rewardtwo_people i").html(userName);
	    	    		    	$(".Index_rewardtwo").show();
	    	    		    	$(".event_summary").show();
	    	    		    }
	    	    		    return false; 
	    	    		});
	    	    		//开始处理
	    	    		$(".Index_list_btntwelve").click(function(){
	    	    			var id = $(this).attr("id");
	    	    	    	var eiId = $(this).find("i").attr("id");
	    	    	    	if(twelve_flag) return;
	    	    	    	twelve_flag = true;
	    	    			updateSixteen(id,eiId);
	    	    			return false; 
	    	    		});
	    	    		//周委托查看
	    	    		$(".Index_list_eleven").click(function(){
	    	    			var eiId = $(this).attr("id");
	    	    			var eId =$(this).parents(".Index_con_list").attr("id");
	    	    			var id = $(this).find("input").attr("id");
	    	    			if(eleven_flag) return;
	    	    	    	eleven_flag = true;
	    	    			getTemWinInfo(eiId,eId,id);
	    	    			return false; 
	    	    		});
	    	    		//记录
	    	    		$(".Index_listt_btnsix").click(function(){
	    	    			var flag_six =false;
	    	    			var id = $(this).attr("id");
	    	    			$(".Index_sign_text").val("");
	    	    			$(".event_summary").show();
	    	    			$(".Event_alertthree").show();
	    	    			$(".Event_alertthree p").html("");
	    	    			$(".Event_track_ok").unbind("click");
	    	    			$(".Event_track_ok").click(function(){
	    	    				//var desc = $(".Index_sign_text").val();
	    	    				if(flag_six) return;
	    	    				var desc = $(".Event_alertthree_textarea").val();
	    	    				if(!isnull(desc)){
	    	    					flag_six =true;
	    	    					todoTrack(id,0);
	    	    				}else{
	    	    				    $(".Event_alertthree p").html("跟踪记录不能为空");
	    	    				    $(".Event_alertthree p").show();
	    	    				}
	    	    			    
	    	    			});
	    	    			return false;
	    	    		});
	    	    		//忽略
	    	    		$(".Index_listt_btnsix_two").click(function(){
	    	    			var flag_six =false;
	    	    			var id = $(this).attr("id");
	    	    			$(".event_summary").show();
	    	    			$(".Index_overlook").show();
	    	    			$(".Index_overlook_btntwo").unbind("click");
	    	    			$(".Index_overlook_btntwo").click(function(){
	    	    			   if(flag_six) return;
	    	    			   flag_six =true;
	    	    			   todoTrack(id,1);
	    	    			});
	    	    			return false;
	    	    		});
	    	    		//关闭目标
	    	    		$(".Index_list_btnthree").click(function(){
	    	    			var flag_three =false;
	    	    			var eId = $(this).attr("id");
	    	    			var id = $(this).find("input").attr("id");
	    	    			$(".tishi2").hide();
	    	    			$(".event_summary").show();
	    	    			$(".event_summary1").show();
	    	    			$(".summary").val("");
	    	    			$(".determine").unbind("click");
	    	    			$(".determine").click(function(){
	    	    				
	                        	var summary = $(".summary").val();
	                        	summary=_trim(summary);
	                        		if(!isnull(summary)){
	                        			$(".signin_issue").show();
	                        			if(flag_three) return;
	    	    			            flag_three =true;
	                        			todoCloseEvent(eId,id);
	                        		}else{
//	                        			$(".tishi2").html("项目总结不能低于20字");
	                        			$(".tishi2").show();
	                        			setTimeout( function(){$(".tishi2").hide()}, 3000);
	                        		}
	                        });
	    	    			return false;
	    	    		});
	    	    		//关闭活动
	    	    		$(".Index_list_btnseven").click(function(){
	    	    			var flag_seven =false;
	    	    			var eId = $(this).attr("id");
	    	    			var id = $(this).find("input").attr("id");
	    	    			$(".tishi2").hide();
	    	    			$(".event_summary").show();
	    	    			$(".event_summary1").show();
							$(".summary").val("");	
							$(".determine").unbind("click");
	    	    			$(".determine").click(function(){
	                        	var summary = $(".summary").val();
	                        	summary=_trim(summary);
	                        		if(!isnull(summary)){
	                        			$(".signin_issue").show();
	                        			if(flag_seven) return;
	    	    			            flag_seven =true;
	                        			todoCloseEvent(eId,id);
	                        		}else{
	                        			$(".tishi2").show();
										 setTimeout( function(){$(".tishi2").hide()}, 3000);
	                        		}
	                        });
	    	    			return false;
	    	    		});
	    	    		//新增计划
	    	    		$(".Index_list_btnone").click(function(){
	    	    		   //事件id
	    	    		   var eId = $(this).attr("id");
	    	    		   //创建人
	    	    		   var cuserId = $(this).parent().attr("id");
	    	    		   //状态 0 时间管理 、1 服务请求、2 组织活动',
	    	    		   var type = $(this).parents(".Index_list_right").attr("id");
	    	    		   localStorage.setItem("team_eId",eId);
	    	    		   if(userId==cuserId){
		    	   				if(type==0){
		    	   					window.location.href="eventdetailsone.html";
		    	   				}else if(type==1){
		    	   					window.location.href="svc_red_eventdetails.html";
		    	   				}else if(type==2){
		    	   					window.location.href="view_participation.html";
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
	    	    		   return false;
	    	    		});
	    	    	    //标记完成（事项完成）做完了
	    	    	    $(".Index_list_btntwo").click(function(){
	    	    	    	$(".Index_sign_error").html("");
	    	    	    	var id = $(this).attr("id");
	    	    	    	var timeq = $(this).find("input").attr("id");
	    	    	    	var eiId = $(this).find("i").attr("id");
	    	    	    	check_item_valid(id,eiId);
	    	    	    
	    	    	    	return false;
	    	    	    });
	    	    	    //拒绝
	    	   		    $(".Index_list_btnfour_two").click(function(){
	    	   		    	var id = $(this).find("input").attr("id");
	    	   		    	var eiId = $(this).attr("id");
	    	   		    	var eId = $(this).parents(".Index_con_list").attr("id");
	    	   		   //校验委托任务是否有效
	    	   		    	check_todo_valid(eiId,0,id,eId);
	    	   		    	return false;
	    	   		    });
	    	    	    //接受
	    	   		    $(".Index_list_btnfour").click(function(){
	    	   		    	var id = $(this).find("input").attr("id");
	    	   		    	var eiId = $(this).attr("id");
	    	   		    	var eId = $(this).parents(".Index_con_list").attr("id");
	    	   		   //校验委托任务是否有效
	    	   		    	check_todo_valid(eiId,1,id,eId);
	    	   		    	return false;
	    	   		    });
	    	   		    //查看
	    	   		    $(".Index_list_btnfive").click(function(){
	    	   		       //事件id
	    	    		   var eId = $(this).attr("id");
	    	    		   //创建人
	    	    		   var cuserId = $(this).parent().attr("id");
	    	    		   //状态 0 时间管理 、1 服务请求、2 组织活动',
	    	    		   var type = $(this).parents(".Index_list_right").attr("id");
	    	    		   var id = $(this).find("input").attr("id");
	    	    		   var sta = $(this).find("i").attr("id");
	    	    		   var state = sta==2 ? "8" : "15" ;
	    	    		   if(five_flag) return;
	    	    		   five_flag =true;
	    	    		   todoDelAddHis(id,eId,cuserId,type,state);
	    	    		   return false;
	    	    	    });
	    	    	   //签到
	    	    	   $(".Index_list_btnsix").click(function(){
	    	    	   	   eIdMap = $(this).attr("id");
	    	    	      var param={"userId":userId,"eId":eIdMap};
	    	    	   	  init.data(param);
	    	    	      //getLocation();
	    	    	      return false;
	    	    	   	});
	    	    	   	verdictOne=true;
    	    		}else{
    	    			verdictOne=false;
		    	   		if(page==1){
		    	   			$(".Index_con_inner").hide();
		    	   		    $(".list_default").show();
		    	     	}
    	    		}
	    	   }
	    	  }
	  });
}

function todoFnish(user,totalNum,todayNum){
	var img=user.headUrl==null?"img/head-portrait.png":imageHeadSrc+user.headUrl;
	$(".Index_title_img img").attr("src",img);
	$(".Index_title_name").html("hi,"+user.userName);
	$(".Index_title_font").html("今天一共有"+totalNum+"个待办，已完成<i> "+todayNum+" </i>个");
//	$(".Index_title_num").html(todayNum);
	$(".Index_title_font i").click(function(){
		_g("completed.html");
	});
}

function TimeChina(long,siem){
		var remindTime = new Date(long);
		var Times = Substr(remindTime.getMonth()+1)+"月"+Substr(remindTime.getDate())+"日";
		if(siem){
			Times=Times+" "+Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes());
		}
		return Times;
};

//关闭事件
function todoCloseEvent(eId,id){
	var summary = $(".summary").val();
	summary=_trim(summary);
	var data = {"eId":eId,"summary":summary,"userId":userId,"tState":"2","id":id};
	 $.ajax({
			   type : "post",
		       url:dataLink+"homepage/todoCloseEvent", 
		       data:data, 
		       dataType : "json",
		       success : function(json){
		    	   if(json.msg=="success"){
		    	   	window.location.href="index.html?v="+Math.random();
			    	}else if(json.msg=="wrong"){
			    		$(".event_summary").hide();
	    	    		$(".event_summary1").hide();
	    	    		$(".Index_alert_con").html("抱歉，项目已手动完成！");
			    		$(".Index_alert").show();
			    		setTimeout(function(){ 
			    			$(".Index_alert").hide();
			    			$(".signin_issue").hide();
			    		    window.location.href="index.html?v="+Math.random();
			    		},2000);
			    	}
		    }
		});
}

//完成事项
function todoClose(id,ei_desc,eiId){
	var finishTimeStr = $(".appDateTime").val();
	var data = {"id":id,"eiDesc":ei_desc,"finishTimeStr":finishTimeStr,"eiId":eiId}
	 $.ajax({
		   type : "post",
	       url:dataLink+"homepage/todoClose", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg=="success"){
	    	   	window.location.href="index.html?v="+Math.random();
	    	    }else if(json.msg=="wrong"){
	    	    	$(".event_summary").hide();
	    	    	$(".Index_sign").hide();
	    	    	$(".Index_alert_con").html("抱歉，项目已手动完成！");
		    		$(".Index_alert").show();
		    		setTimeout(function(){ 
		    			$(".Index_alert").hide();
		    		    window.location.href="index.html?v="+Math.random();
		    		},2000);
		    	}
	    }
	});
}

// 接受、拒绝
function acceptOrRefuse(eiId,isAccept,id,eId){
	var data = {"userId":userId,"eiId":eiId,"isAccept":isAccept,"id":id,"eId":eId};
	 $.ajax({
		   type : "post",
	       url:dataLink+"homepage/acceptOrRefuse", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg=="success"){
	    	   		window.location.href="index.html?v="+Math.random();
	    	   		return false;
	    	   }else if(json.msg=="wrong"){
	    	   		    $(".event_summary").hide();
	    	   		    $(".Index_accept_two").hide();
	    	   		    $(".Index_accept").hide();
	    	   		    $(".Index_alert_con").html("抱歉，项目已手动完成！");
			    		$(".Index_alert").show();
			    		setTimeout(function(){ 
			    			$(".Index_alert").hide();
			    		    window.location.href="index.html?v="+Math.random();
			    		},2000);
			    		
			    	}
	    }
	});
}



// add by xiehuilin 2017/06/16 初始化事件详情
var init={
	data:function(param){
    	var url=dataLink+"event/svcPartyEventInfo";
    	_asyn(url,param,"get",init.Ok);
	},
	Ok:function(json, code){
		if(json.msg=='success'){
			console.log(json);
			reslutLng=json.event.lng;
			reslutlat=json.event.lat;
			getLocation();
			if(json.joinEvents.length>0){
		  		for(var x=0;x<json.joinEvents.length;x++){
		  			var je=json.joinEvents[x];
		  				id=je.id;
			     }
		  	   }
			}
	},
	//add by xiehuilin 2017/06/24 签到
	sign:function(){
		
		var param={"userId":userId,"eId":eIdMap,"id":id};
    	var url=dataLink+"join/sign";
    	_asyn(url,param,"POST",init.signOk);
	},
	signOk:function(json,code){
		if(json.msg=="success"){
			$(".signin_ts").hide();
			if(json.isClose==1){
				$(".Index_signin_con").html("活动已结束");
//				$(".Index_signin").show();
				setTimeout(function(){
					$(".Index_signin").hide();
				    window.location.href="index.html?v="+Math.random();
				},2000);
			}else{
				$(".Index_signin_con").html("签到成功");
				$(".Index_signin").show();
				setTimeout(function(){
//					$(".Index_signin").hide();
				    window.location.href="index.html?v="+Math.random();
				},2000);
			}
			
		}else{
			$(".signin_ts").hide();
			$(".Index_signin_con").html(json.errorCode);
			$(".Index_signin").show();
			setTimeout(function(){
				$(".Index_signin").hide();
				 window.location.href="index.html?v="+Math.random();
				},2000) 
		}
	}
}


//获取当前位置经纬度的方法
	function getLocation(){
	$(".signin_ts").show();
        if (navigator.geolocation){
            navigator.geolocation.getCurrentPosition(showPosition);//HTML5获取GPS设备地理位置信息
        }else{
            document.getElementById("allmap").innerHTML="Geolocation is not supported by this browser.";
        }
    }
	
    function showPosition(position){
    	var x=position.coords.latitude;//获取纬度
        var y=position.coords.longitude;//获取经度
        //调用腾讯地图坐标的方法
        getGPS(x,y); 
    }
    //转为腾讯地图坐标的方法
    function getGPS(lat,lng){
        var data = {"locations":lat+","+lng,"key":tencent_key,"type":1}
        data.output="jsonp";
        $.ajax({
		    type : "get",
		    dataType:'jsonp',
	        url:"https://apis.map.qq.com/ws/coord/v1/translate?locations="+lat+","+lng+"&key="+tencent_key+"&type=1",
	        data:data,
	        jsonp:"callback",
            jsonpCallback:"QQmap",
	        success:function(json){
	        	//纬度
            	clng = json.locations[0].lng;
            	clat = json.locations[0].lat;
            	//经度
            	//调用的计算距离方法
            	Calculation(reslutlat,reslutLng,clat,clng);
            	var distance = localStorage.getItem("distance");
            	//add by xiehuilin 2017/07/11 校验是否在签到范围内
            	if(distance<=500){
            		init.sign();
            		$(".Index_signin").hide();
            	}else{
            		$(".Index_signin_con").html("不在签到范围内");
            		setTimeout(function(){$(".Index_signin").hide()},2000) 
            		$(".Index_signin").show();
            	}   
            },
            error : function(err){alert("服务端错误，请刷新浏览器后重试")}
		});
   	}
    //计算两点之间距离的方法
    function Calculation(lat1,lng1,lat2,lng2){//纬度1 经度1  纬度2 经度2 
	    var data = {"from":lat1+","+lng1,"to":lat2+","+lng2,"key":tencent_key}
	    data.output="jsonp";
	    $.ajax({
			type : "get",
			dataType:'jsonp',
		    url:"https://apis.map.qq.com/ws/distance/v1/?from="+lat1+","+lng1+"&to="+lat2+","+lng2+"&key="+tencent_key,
	        data:data,
	        jsonp:"callback",
            jsonpCallback:"QQmap",
	        success:function(json){
	        	//距离
            	var distance = json.result.elements[0].distance;
            	localStorage.setItem("distance", distance);
            },
            error : function(err){alert("服务端错误，请刷新浏览器后重试")}
		});
    }
     //HTML5获取地理位置信息错误处理
    function showError(error){
  		switch(error.code){
    		case error.PERMISSION_DENIED:
    			document.getElementById("allmap").innerHTML="User denied the request for Geolocation."
      			break;
    		case error.POSITION_UNAVAILABLE:
      			document.getElementById("allmap").innerHTML="Location information is unavailable."
      			break;
    		case error.TIMEOUT:
      			document.getElementById("allmap").innerHTML="The request to get user location timed out."
      			break;
    		case error.UNKNOWN_ERROR:
     		 	document.getElementById("allmap").innerHTML="An unknown error occurred."
      			break;
    	}
  	}

function todoDelAddHis(id,eId,cuserId,type,state){
	var data = {"id":id,"tState":state}
	 $.ajax({
		   type : "post",
	       url:dataLink+"homepage/todoDelAddHis", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
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
	       }
	    }
	});
}
//add by wuchao 2017/7/18 个人中心消息未读数

function getNews(){
	var data = {"sendId":userId,"isRed":0,"strType":"0"}
	 $.ajax({
		   type : "get",
	       url:dataLink+"news/getPromptCount", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
		    	   if(json.promptCount>0){
		    		   $(".Index_footer_btnone").find("span").html(json.promptCount);
		    		   $(".Index_footer_btnone").find("span").show();
		    	   }
	    	   }
	       }
	  });
}
//校验委托任务是否有效  周委托 zhou 1  
function check_todo_valid(eiId,isAccept,id,eId,zhou){
	var data = {"id":id}
	 $.ajax({
		   type : "get",
	       url:dataLink+"homepage/checkTodo", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
		    	   if(json.hint==0){
		    	   	if(zhou==1){
		    	   		if(eleven_flag_one) return;
	    	    	    eleven_flag_one = true;
		    	   		acceptOrRefuse(eiId,isAccept,id,eId);
		    	   	}else{
		    	   		 if (isAccept==0) {
		    			   $(".event_summary").show();
		    			   $(".Index_accept_two").show();
		    			   $(".Index_accepttwo_btntwo").unbind("click");
		    			   $(".Index_accepttwo_btntwo").click(function(){
		    			   	   if(eleven_flag_one) return;
	    	    	           eleven_flag_one = true;
		    				   acceptOrRefuse(eiId,0,id,eId);
		    			   });
		    		   }else{
		    			   $(".event_summary").show();
		    			   $(".Index_accept").show();
		    			   $(".Index_accept_btntwo").unbind("click");
		    			   $(".Index_accept_btntwo").click(function(){
		    			   	  if(eleven_flag_one) return;
	    	    	           eleven_flag_one = true;
		    				   acceptOrRefuse(eiId,1,id,eId);
		    			   });
		    		   }
		    	   	}
		    	   }else{
		    		   $(".entrust").html("此项委托已失效");
		    		   $(".entrust").show();
		    		   setTimeout(function(){$(".entrust").hide();_g("index.html?v="+Math.random());},2000);
		    		   
		    	   }
	    	   }
	       }
	  });
}

var flag_btntwo =false;
//校验标记待完成是否有效
function check_item_valid(id,eiId){
	var data = {"id":id}
	 $.ajax({
		   type : "get",
	       url:dataLink+"homepage/checkTodo", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
		    	   if(json.hint==0){
		    			$(".event_summary").show();
    	    	    	$(".Index_sign").show();
    	    	    	function p(s) {
						    return s < 10 ? '0' + s: s;
						}
    	    	    	var myDate = new Date();
						//获取当前年
						var year=myDate.getFullYear();
						//获取当前月
						var month=myDate.getMonth()+1;
						//获取当前日
						var date=myDate.getDate(); 
						var h=myDate.getHours();       //获取当前小时数(0-23)
						var m=myDate.getMinutes();     //获取当前分钟数(0-59)
						var s=myDate.getSeconds();  
						
						var now=year+'-'+p(month)+"-"+p(date)+" "+p(h)+':'+p(m)+":"+p(s);
    	    	    	$(".Index_sign_time input").val(now);
    	    	    	$(".Index_sign_text").val("");
    	    	    	$('.Index_sign_btntwo').unbind("click");
    	    	    	$(".Index_sign_btntwo").click(function(){
    	    	    		var ei_desc =$(".Index_sign_text").val();
    	    	    		ei_desc=_trim(ei_desc);
    	    	    		if(centen(ei_desc,0,80)){
    	    	    			if(flag_btntwo) return;
	    	    			    flag_btntwo =true;
    	    	    			todoClose(id,ei_desc,eiId);
    	    	    		}else{
    	    	    			$(".Index_sign_error").html("描述文字为40个字以内");
    	    	    		}
    	    	        });
		    	   }else{
		    		   $(".entrust").html("抱歉，项目已手动完成！");
		    		   $(".entrust").show();
		    		   setTimeout(function(){$(".entrust").hide();_g("index.html?v="+Math.random());},2000);
		    		   
		    	   }
	    	   }
	       }
	  });
}
//zyting 20170814 行动跟踪卡片
function todoTrack(id,isIgnore){
	//var d =$(".Index_sign_text").val();
	var d = $(".Event_alertthree_textarea").val();
	var data = {"id":id,"isIgnore":isIgnore,"eiDesc":d}
	 $.ajax({
		   type : "post",
	       url:dataLink+"homepage/todoTrack", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	 window.location.href="index.html?v="+Math.random();
	    	   }
	       }
	});
}

function getTemWinInfo(eiId,eId,id){
	var data = {"eiId":eiId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"homepage/getTemWinInfo", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	var info = json.itemInfo;
	    	   	$(".Index_alert_cont").html(info.eiDesc);
	    	   	if(isnull(info.cycle)){
	    	   		$(".Index_alert_conttwo").html("每天");
	    	   	}else{
	    	   		var week_name="";
	    	   		var  arr=info.cycle.split(",");
	    			for (var i = 0; i < arr.length; i++) {
	    				var str= arr[i];
	    				if (!isnull(week_name)) {
							week_name=week_name+","+frequency.week(str);
						}else{
							week_name=frequency.week(str);
						}
					}
	    	   		$(".Index_alert_conttwo").html(week_name);
	    	   	}
	    	   	$(".Index_alert_contthree").html(TimeChina(info.startTime)+"—"+TimeChina(info.endTime));
	    	   //拒绝
	    	   $(".Index_alert_content_btnone").unbind("click");
	    	   $(".Index_alert_content_btnone").click(function(){
	    	   		check_todo_valid(eiId,0,id,eId,1);
	    	   	});
	    	   	//接受
	    	   $(".Index_alert_content_btntwo").unbind("click");
	    	   $(".Index_alert_content_btntwo").click(function(){
	    	   		check_todo_valid(eiId,1,id,eId,1);
	    	   	});
	    	   	$(".event_summary").show();
	    	    $(".Index_alert_content_inner").show();
	    	    eleven_flag = false;
	    	   }
	       }
	});
}
//edit by zhengjunting 2017/06/13    时间插件
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
	    startYear:currYear, //开始年份,如 currYear - 10
	    endYear:currYear + 10, //结束年份
	    minDate: new Date()

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
//		$("#appDateTime").mobiscroll(optDateTime).datetime(optDateTime);
		$("#appDateTimetwo").mobiscroll(optDateTime).datetime(optDateTime);
//		$("#appDateTimethree").mobiscroll(optDateTime).datetime(optDateTime);
	}


function updateSixteen(id,eiId){
	var data = {"eiId":eiId,"id":id}
	 $.ajax({
		   type : "get",
	       url:dataLink+"homepage/updateSixteen", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	              window.location.href="index.html?v="+Math.random();
	        }
	     }
	 });
}


//add by xiehuilin 20170927 奖惩执行
$(".Index_reward_btn").on("click",".Index_reward_btntwo",function(){
	createPayOrder(2);
});