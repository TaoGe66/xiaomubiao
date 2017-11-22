$(function(){
	//add by zhanghaitao 2017/09/21 点击左边-->右边箭头切换图片
//	$(".time_jia").click(function(){
//		$(".time_jian").attr("src","img/img60_07.png");
//	});
//	add by zhanghaitao 2017/09/21 点击右边-->左边箭头切换图片
//	$(".time_jian").click(function(){
//		$(".time_jia").attr("src","img/img59_04.png");
//	});
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
});



var userId = localStorage.getItem("team_userId");
var leId = localStorage.getItem("team_leId");
var team_userUrl = localStorage.getItem("team_userUrl");
team_fgLogo=isnull(team_userUrl)?"img/img03.png":imageHeadSrc+team_userUrl;

enterpriseInfoByFrist();

follow_list();
//轻企我关注的列表 		wuchao
function  follow_list(){
	var data={"createBy":userId,"leId":leId}
	$.ajax({
		  type : "GET",
	      url:dataLink+"enterprise/listFollowUser",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  var list=json.follows;
	    		  
	    		  if (list.length>0) {
	    			  var html ='<li class="event_category_all select">';
	    			  	  html+='<em style="display:none;">'+userId+'</em>';
	    			  	  html +='<img src="'+team_fgLogo+'" alt="" /><p>我的</p></li>';
	    			  for (var i = 0; i < list.length; i++) {
	    				  var item=list[i];
	    				  headUrl=isnull(item.headUrl)?"img/img03.png":imageHeadSrc+item.headUrl;
	    				  html +='<li class="event_category_alone">';
	    				  html+='<em style="display:none;">'+item.userId+'</em>';
	    			  	  html +='<img src="'+headUrl+'" alt="" /><p>'+item.userName+'</p></li>';
	    			  }
	    			  $(".event_tab").append(html);
				}else{
					var html ='<li class="event_category_all select">';
						html+='<em style="display:none;">'+userId+'</em>';
						html +='<img src="'+team_fgLogo+'" alt="" /><p>我的</p></li>';
					 $(".event_tab").append(html);
				}
	    	  }
	    	//add by zhanghaitao 2017/09/22 切换用户头像
	    		$(".event_tab>li").click(function(){
	    			$(this).addClass("select").siblings().removeClass("select");
	    			var f_userId=$(".select").find("em").html();
	    			if (f_userId!=userId) {
	    				localStorage.setItem("team_f_userId",f_userId);
	    				is_weekly();
					}else{
						localStorage.removeItem("team_f_userId");
						is_weekly();
					}
	    		});	
	      },  
	      timeout:3000  
	 }); 
}

//是否生成周报 		wuchao
function  is_weekly(){
	var weeklyDate=localStorage.getItem("team_weeklyTime");
	var f_userId=localStorage.getItem("team_f_userId");
	if (isnull(f_userId)) {
		f_userId=userId;
	}
	var data={"userId":f_userId,"leId":leId,"strTime":weeklyDate}
	$.ajax({
		  type : "GET",
	      url:dataLink+"census/isMakeWeekly",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		 var item= json.item;
	    		 var f_uId=localStorage.getItem("team_f_userId");
	    		 if (item !=null) {
						$(".shuju").hide();
						$(".shengcheng").hide();
						$(".meiyou").hide();
						$(".zanwu").hide();
	    			 $(".btn_border").hide();
	    			 localStorage.setItem("team_isWeekly",1);
	    			 if (isnull(f_uId)) {
	    				 $(".btn_border_new").show();	
					}else{
						$(".btn_border_new").hide();
					}
	    			 $(".weekly_contentBox").show();
	    			 weekly_info();
				}else{
					$(".weekly_contentBox").hide();
					$(".btn_border_new").hide();
					localStorage.setItem("team_isWeekly",0);
					if (isnull(f_uId)) {
						$(".zanwu").hide();
					
						if (weeklyDate>=next_week(-144)&& new Date().getTime()<next_week(-31)) {
							$(".btn_border").hide();	
							$(".shengcheng").hide();
							$(".meiyou").hide();
							$(".shuju").show();
						}else if (weeklyDate>=next_week(-144)&& new Date().getTime()>next_week(-31)) {
							$(".btn_border").hide();
							$(".shuju").hide();
							$(".meiyou").hide();
							$(".shengcheng").show();
						}else if (weeklyDate<next_week(-144)) {
							$(".btn_border").show();
							$(".shuju").hide();
							$(".shengcheng").hide();
							$(".meiyou").show();
						}
						/*$(".btn_border").hide();
						$(".shuju").hide();
						$(".meiyou").hide();
						$(".shengcheng").show();*/
					}else{
						$(".btn_border").hide();
						$(".shuju").hide();
						$(".shengcheng").hide();
						$(".meiyou").hide();
						$(".zanwu").show();
						
					}
				}
	    	  }
	      },  
	      timeout:3000  
	 }); 
}


//周报详情 		wuchao
function  weekly_info(){
	var f_userId=localStorage.getItem("team_f_userId");
	if (isnull(f_userId)) {
		f_userId=userId;
	}
	var startTime=localStorage.getItem("team_weeklyTime");
	var data={"userId":f_userId,"leId":leId,"strTime":startTime}
	$.ajax({
		  type : "GET",
	      url:dataLink+"census/infoWeekly",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		 var items= json.item;
	    		 if (items.totalItem>0) {
					chart1(items.advanceItem,items.normalItem,items.lagItem,items.unusualItem);
					chart2(items.advanceAction,items.normalAction,items.lagAction,items.unusualAction);
					chart3(items.totalAssess,items.bestNumber,items.worstNumber);
					$(".chart1").show();
					$(".chart2").show();
					$(".chart3").show();
				}else{
					$(".chart1").hide();
					$(".chart2").hide();
					$(".chart3").hide();
				}
	    		 $(".item_tswk").html(items.totalItem);//本周项目
	    		 $(".fulfil_item_tswk").html(items.finishItem);//已完成项目
	    		 $(".act_tswk").html(items.totalAction);//本周行动
	    		 $(".fulfil_act_tswk").html(items.finishAction);//已完成行动
	    		 //项目完成状况
	    		 var html ='<li>项目</li><li>'+items.advanceItem+'</li><li>'+items.normalItem+'</li>';
	    		 	 html +='<li>'+items.lagItem+'</li><li>'+items.unusualItem+'</li>';
	    		 $(".complete_xiangmu").html(html);
	    		  //行动完成状况
	    		 var htmls='<li>行动</li><li>'+items.advanceAction+'</li><li>'+items.normalAction+'</li>';
	    		 	htmls+='<li>'+items.lagAction+'</li><li>'+items.unusualAction+'</li>';
	    		 $(".complete_xingdong").html(htmls);//已完成行动
	    		 $(".all_assess").html(items.totalAssess);//全部评价
	    		 $(".five_assess").html(items.bestNumber);//五星好评
	    		 $(".one_assess").html(items.worstNumber);//一星差评
	    		 //清单
	    		 $(".weekly_job").html("");
	    		 var other_eId=null;
	    		 if (!isnull(items.listWeeklyList) &&items.listWeeklyList.length>0) {
	    			 $(".weekly_job").html('<p class="public_title">工作清单</p>');
	    			 var act=1;//行动序号
	    			 var tail_after=1;//跟踪序号
	    			 var item_number=0;//项目序号
	    			 for (var i = 0; i <= items.listWeeklyList.length; i++) {
							var item=items.listWeeklyList[i];

							if (isnull(item) || (!isnull(other_eId) && item.eId !=other_eId)) {
								html +='</ul></li>';
								$(".weekly_job").append(jHtml);
							}
							if (!isnull(item)) {
								if (i ==0 || (item.eId !=other_eId) ) {
									var jHtml ='<div class="job_content">';
									item_number++;
								}	
								
								if (item.type==0) {
									act=1;
									jHtml +='<p>'+Arabia_To_SimplifiedChinese(item_number)+'：'+item.eName+'</p><ul>';
								}else if (item.type==1){
									jHtml +='<li class="job_action">'+act+'：【行动】'+item.eiDesc+'</li>';
									act++;
									tail_after=1;
								}else if (item.type==2){
									jHtml +='<li class="job_text">'+tail_after+'：【计划】'+item.trContent+'</li>';
									tail_after++;
								}else if (item.type==4){
									jHtml +='<li class="job_text">'+tail_after+'：【完成】'+item.eiDesc+'</li>';
									tail_after++;
								}else if (item.type==3){
									jHtml +='<li class="job_summary">项目总结：'+item.summary+'</li>';
								}
								if (!isnull(other_eId)) {
									if (item.eId !=other_eId || i== items.listWeeklyList.length-1) {
										
									}
								}
								
								other_eId=item.eId;
							}
						}
	    			 
				}
	    	  }
	      },  
	      timeout:3000  
	 }); 
}




//跳转清单页面
$(".btn_border").click(function(){
	_g("generate.html");
});
$(".btn_border_new").click(function(){
	_g("generate.html");
});
$(".shengcheng_href").click(function(){
	_g("generate.html");
});

//console.info(new Date().getTime());//当前周0点00
//console.info(next_week(-144));//当前周0点00
//console.info(next_week(-29));//周五下午五点
function next_week(long){
	var now = new Date(); 
	var nowTime = now.getTime() ; 
	var day = now.getDay();
	var oneDayLong = 24*60*60*1000 ; 
	var SundayTime =  nowTime + (7-day)*oneDayLong ; 
	var sunday = new Date(SundayTime);
	sunday.setHours(long, 00,00, 0);
	//console.log(TimeIt(sunday.getTime(),1)) ; 
	return sunday.getTime();

}




function TimeIt(long,siem){
	var remindTime = new Date(long);
	if(siem){
	var Times = remindTime.getFullYear()+"-"+Substr(remindTime.getMonth()+1)+"-"+Substr(remindTime.getDate());
		Times=Times+" "+Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes());
	}else{
		var Times = Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes())+":"+Substr(remindTime.getSeconds());;
	}
	return Times;
};
