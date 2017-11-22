var userId = localStorage.getItem("team_userId");
var verdict=true;//是否继续刷新
var page =1;
$(window).scroll(function () {
        var bot =($(".find-event").height())*3;
        if (verdict) {
            if ((bot + $(window).scrollTop()) >= ($("body").height() - $(window).height())) {
                verdict=false;
                page+=1;
              toDayHisList(page);
            }
        }
 });

$(function(){
	toDayHisList(1);
	$(".Comp_content_two").hide();
	$(".Comp_head li").click(function(){
		$(".Comp_head span").removeClass("Comp_head_current");
        $(this).find("span").addClass("Comp_head_current");
		$(".content").hide().eq($(this).index()).show();
		if($(".Comp_head_one").hasClass("Comp_head_current")){
			$(".Comp_content_two").hide(); 
		   	$(".Comp_content_one").show();
			
		}else if($(".Comp_head_two").hasClass("Comp_head_current")){
    	    $(".Comp_content_one").hide();
    	    $(".Comp_content_two").show();
    	    isInvalidTodo();
		}
   });
});
function toDayHisList(page){
	var data = {"userId":userId,"pageNum":page}
	 $.ajax({
		   type : "get",
	       url:dataLink+"homepage/toDayHisList", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	    	$(".Index_con_inner").show();
	    	    	$(".my_event_default").hide();
		    	    if(page==1){
		    	   		$(".Index_con_inner").html("");
		    	   	}
	    	   	if(!isnull(json.pageInfo)&&json.pageInfo.length>0){
	    	   		for(i=0;i<json.pageInfo.length;i++){
	    	   			var item = json.pageInfo[i];
	    	   			var buttonStr ='';
		    	   		var imgtop ='';
		    	   		var logo = imageHeadSrc+item.logo;
		    	   		var conStr ='';
		    	   		var leftImg ='<div class="Index_list_left Index_list_left_two">';
	    	   			var tophtml ='<div class="Index_con_list Index_con_listTo smallshdow" id="'+item.eId+'">';
	    	   			//事件
	   				   
    	   				//时间管理
    	   				if(item.type==0){
    	   					
    	   					 if(item.tSubjType==0){
	    	   					//新增计划
    	   					    if(item.tState==0){
    	   					    	logo ='img/icon01.png';
	    	   					    buttonStr ='<span class="Index_list_btneight">已新增计划</span>';
	    	   					    conStr ='<p class="Index_list_right_two Index_font_weight Index_font_weight_color">'+sameDate(item.startTime,item.endTime)+'</p>';
    	   					    //关闭目标
    	   					    }else if(item.tState==3){
    	   					    	logo ='img/icon03.png';
    	   					    	buttonStr ='<span class="Index_list_btneight">已关闭目标</span>';
    	   					    	conStr ='<p class="Index_list_right_four Index_font_weight Index_font_weight_color">截止时间：'+item.pushTimeStr+'</p>';
    	   					    }
	    	   					//事项
	    	   				}else if(item.tSubjType==1){
	    	   						//标记完成
	    	   					    if(item.tState==0){ 
	    	   					    	logo ='img/icon02.png';
		    	   					    buttonStr ='<span class="Index_list_btneight">已做完</span>';
		    	   					    conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
	    	   					    //委托进度(单次)
	    	   					    }else if(item.tState==7 ){
	    	   					    	logo ='img/icon04.png';
	    	   					    	buttonStr ='<span class="Index_list_btneight">已接受</span>';
	    	   					        conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
	    	   					    }else if(item.tState==14){
		    	   					    logo ='img/icon05_07.png';
		    	   					    buttonStr ='<span class="Index_list_btneight">已接受</span>';
		    	   					      conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
	    	   					        //委托进度
	    	   					    }else if(item.tState==6){
	    	   					    	tophtml ='<div class="Index_con_list smallshdow">';
	    	   					    	logo ='img/icon05.png';
	    	   					    	buttonStr ='<span class="Index_list_btneight">已拒绝</span>';
	    	   					        conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
	    	   					   //未接受
	    	   					    }else if(item.tState==13){
	    	   					    	tophtml ='<div class="Index_con_list smallshdow">';
	    	   					    	logo ='img/icon05_08.png';
	    	   					    	buttonStr ='<span class="Index_list_btneight">已拒绝</span>';
	    	   					        conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
	    	   					   //未接受
	    	   					    }else if(item.tState==8){
	    	   					    	logo ='img/icon05.png';
	    	   					    	buttonStr ='<span class="Index_list_btneight">已查看</span>';
	    	   					        conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
	    	   					    //未接受(周)
	    	   					    }else if(item.tState==15){
	    	   					    	logo ='img/icon05_08.png';
	    	   					    	buttonStr ='<span class="Index_list_btneight">已查看</span>';
	    	   					        conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
	    	   					    //已记录
	    	   					    }else if(item.tState==10){
	    	   					    	if(item.isIgnore==0 ||isnull(item.isIgnore)){
	    	   					    		logo ='img/icon05_06.png';
	    	   					    	    buttonStr ='<span class="Index_list_btneight">已记录</span>';
		    	   					        conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
	    	   					    	//已忽略
	    	   					    	}else if(item.isIgnore==1){
	    	   					    		logo ='img/icon05_06.png';
		    	   					        buttonStr ='<span class="Index_list_btneight">已忽略</span>';
		    	   					        conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
	    	   					    	}
	    	   					    //
	    	   					    }else if(item.tState==17){
	    	   					    	logo ='img/icon02.png';
			    	   					buttonStr ='<span class="Index_list_btneight" id="'+item.id+'">已执行</span>';
			    	   					conStr ='<p class="Index_list_right_three Index_font_weight">'+item.eiDesc+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
	    	   					    }
	    	   					    
	    	   				}
	    	   					
    	   					leftImg ='<div class="Index_list_left">';
    	   					imgtop ='<p class="Index_con_title" style="background-image: url(img/icon11_1.png)"; >';
    	   			    //服务请求
    	   				}else if(item.type==1){
    	   					
    	   					
    	   					 if(item.tSubjType==0){
	    	   					//新增计划
    	   					    if(item.tState==0){
	    	   					    buttonStr ='<span class="Index_list_btneight">已新增计划</span>';
	    	   					    conStr ='<p class="Index_list_right_two Index_font_weight Index_font_weight_color">'+sameDate(item.startTime,item.endTime)+'</p>';
    	   					    //关闭目标
    	   					    }else if(item.tState==3){
    	   					    	buttonStr ='<span class="Index_list_btneight">已关闭需求</span>';
    	   					    	conStr ='<p class="Index_list_right_four Index_font_weight Index_font_weight_color">截止时间：'+item.pushTimeStr+'</p>';
    	   					    }
	    	   					//事项
	    	   				}else if(item.tSubjType==1){
	    	   						//标记完成
	    	   					    if(item.tState==0){
		    	   					    buttonStr ='<span class="Index_list_btneight">已做完</span>';
		    	   					    conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
	    	   					    //委托进度
	    	   					    }else if(item.tState==7 ||item.tState==14){
	    	   					    	buttonStr ='<span class="Index_list_btneight">已接受</span>';
	    	   					        conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
	    	   					   //委托进度
	    	   					    }else if(item.tState==6 ||item.tState==13){
	    	   					    	tophtml ='<div class="Index_con_list smallshdow">';
	    	   					    	buttonStr ='<span class="Index_list_btneight">已拒绝</span>';
	    	   					        conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
	    	   					   //未接受
	    	   					    }else if(item.tState==8){
	    	   					    	buttonStr ='<span class="Index_list_btneight">已查看</span>';
	    	   					        conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
	    	   					    //未接受(周)
	    	   					    }else if(item.tState==15){
	    	   					    	buttonStr ='<span class="Index_list_btneight">已查看</span>';
	    	   					        conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
	    	   					    //已记录
	    	   					    }else if(item.tState==10){
	    	   					    	if(item.isIgnore==0 ||isnull(item.isIgnore)){
	    	   					    		 buttonStr ='<span class="Index_list_btneight">已记录</span>';
		    	   					         conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
	    	   					    	}else if(item.isIgnore==1){
	    	   					    		 buttonStr ='<span class="Index_list_btneight">已忽略</span>';
		    	   					         conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+item.eiDesc+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
	    	   					    	}
	    	   					    }else if(item.tState==17){
			    	   					buttonStr ='<span class="Index_list_btneight" id="'+item.id+'">已执行</span>';
			    	   					conStr ='<p class="Index_list_right_three Index_font_weight">'+item.eiDesc+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
	    	   					    }
	    	   				}
    	   					
    	   					
    	   					logo = imageHeadSrc+item.logo;
    	   				    imgtop ='<p class="Index_con_title" style="background-image: url(img/icon12_1.png)"; >';
    	   				//组织活动
    	   				}else if(item.type==2){
    	   					logo = imageHeadSrc+item.logo;
    	   					imgtop ='<p class="Index_con_title" style="background-image: url(img/icon17_1.png)"; >';
    	   					if(item.tState==4){
    	   						buttonStr ='<span class="Index_list_btneight">已签到</span>';
    	   						conStr ='<p class="Index_list_right_four Index_font_weight Index_font_weight_color">'+TimeChina(item.remindTime)+'</p>';
    	   					}else if(item.tState==3){
    	   						buttonStr ='<span class="Index_list_btneight">已关闭活动</span>';
    	   					}
    	   				}
	    	   			var html  =tophtml+imgtop+item.name+'</p>';
	    	   			    html +='<div class="Index_con_list_bottom">'+leftImg+'<img src="'+logo+'" alt="" /></div>';
	    	   			    html +='<div class="Index_list_right" id="'+item.type+'">';
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
	    	   		
	    	   		   verdict=true;
	    	   		}else{
	    	   		   verdict=false;
	    	   		   if(page==1){
		    	   		  $(".Index_con_inner").hide();
	    	    	      $(".my_event_default").show();
		    	      	}
	    	   		   
	    	      	}
	    	   }
	    	  }
	    });

}


$(function(){
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
})


function isInvalidTodo(){
	
	var data = {"userId":userId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"homepage/isInvalidTodo", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	    $(".Index_con_inner_two").show();
	    	    	$(".my_event_default_two").hide();
		    	    $(".Index_con_inner_two").html("");
	    	   	if(!isnull(json.pageInfo)&&json.pageInfo.length>0){
	    	   		for(i=0;i<json.pageInfo.length;i++){
	    	   			var item = json.pageInfo[i];
	    	   			
		    	   		var imgtop ='';
		    	   		var logo = imageHeadSrc+item.logo;
		    	   		var conStr ='';
		    	   		var leftImg ='<div class="Index_list_left Index_list_left_two">';
		    	   		var descei =isnull(item.tContent) ? item.eiDesc : item.tContent;
	    	   			//事件
                       
                         if(item.type==0){
                         	if(item.tSubjType==0){
    	   					    if(item.tState==0){
    	   					    	logo ='img/icon01.png';
	    	   					    conStr ='<p class="Index_list_right_two Index_font_weight Index_font_weight_color">'+sameDate(item.startTime,item.endTime)+'</p>';
    	   					    }
                         	}else if(item.tSubjType==1){
	    	   					if(item.tState==0){
	    	   						logo ='img/icon02.png';
	    	   						conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+descei+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
    	   						}else if(item.tState==1 || item.tState==11){
    	   							logo ='img/icon04.png';
    	   							conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+descei+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
    	   						}else if(item.tState==17){
    	   							logo ='img/icon02.png';
			    	   			    conStr ='<p class="Index_list_right_three Index_font_weight">'+item.eiDesc+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
    	   						}else if(item.tState==16){
    	   							logo ='img/icon02.png';
			    	   				conStr ='<p class="Index_list_right_three Index_font_weight">'+item.eiDesc+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
    	   						}
	    	   			   }
	    	   			     imgtop ='<p class="Index_con_title" style="background-image: url(img/icon11_1.png)"; >';
	    	   		         leftImg ='<div class="Index_list_left">';  
                         }else if(item.type==1){
                         	if(item.tSubjType==0){
    	   					    if(item.tState==0){
	    	   					    conStr ='<p class="Index_list_right_two Index_font_weight Index_font_weight_color">'+sameDate(item.startTime,item.endTime)+'</p>';
    	   					    }
                         	}else if(item.tSubjType==1){
	    	   		         	if(item.tState==0){
	    	   						conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+descei+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
    	   						}else if(item.tState==1 || item.tState==11){
    	   							conStr ='<p class="Index_list_right_three Index_font_weight Index_font_weight_color">'+descei+'</p><p class="Index_list_right_four">'+TimeChina(item.pushTime,123)+'</p>';
    	   						}else if(item.tState==17){
			    	   			    conStr ='<p class="Index_list_right_three Index_font_weight">'+item.eiDesc+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
    	   						}else if(item.tState==16){
			    	   				conStr ='<p class="Index_list_right_three Index_font_weight">'+item.eiDesc+'</p><p class="Index_list_right_four">截止时间：'+item.pushTimeStr+'</p>';
    	   						}
    	   					}
	    	   		       	    imgtop ='<p class="Index_con_title" style="background-image: url(img/icon12_1.png)"; >';
	    	   		       }else if(item.type==2){
	    	   		         	imgtop ='<p class="Index_con_title" style="background-image: url(img/icon17_1.png)"; >';
	    	   		       }


	    	   			var html  ='<div class="Index_con_list smallshdow" id="'+item.eId+'">'+imgtop+item.name+'</p>';
	    	   			    html +='<div class="Index_con_list_bottom">'+leftImg+'<img src="'+logo+'" alt="" /></div>';
	    	   			    html +='<div class="Index_list_right">';
	    	   			    html +=conStr+'<div class="Index_list_right_btn" >';
	    	   			    html +='<span class="Index_list_lapse">已失效</span></div></div></div></div>';
	    	   			
	    	   			
	    	   			$(".Index_con_inner_two").append(html);
	    	   	}
	    	}else{
    	   		  $(".Index_con_inner_two").hide();
	    	      $(".my_event_default_two").show();
	    	}
	    }
	  }
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