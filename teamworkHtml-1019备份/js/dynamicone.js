var userId = localStorage.getItem("team_userId");
var leId = localStorage.getItem("team_leId");
var enCount =0;
var userName =localStorage.getItem("team_userName");
//解决第三方软键盘唤起时底部input输入框被遮挡问题
var bfscrolltop = document.body.scrollTop;//获取软键盘唤起前浏览器滚动部分的高度
$("input.Dynamic_global").focus(function(){//在这里‘input.inputframe’是我的底部输入栏的输入框，当它获取焦点时触发事件
    interval = setInterval(function(){//设置一个计时器，时间设置与软键盘弹出所需时间相近
    document.body.scrollTop = document.body.scrollHeight;//获取焦点后将浏览器内所有内容高度赋给浏览器滚动部分高度
    },500);
}).blur(function(){//设定输入框失去焦点时的事件
    clearInterval(interval);//清除计时器
//  document.body.scrollTop = bfscrolltop;//将软键盘唤起前的浏览器滚动部分高度重新赋给改变后的高度
//	$(".Dynamic_bg").hide();
//	$(".Dynamic_input").hide();
});
$(function(){
	$(".Dynamic_bg").click(function(){
		$(".Dynamic_bg").hide();
		$(".Dynamic_input").hide();
		$(".Index_footer_button").show();
	});
	//监控input事件
	$("#Dynamic_input").bind("input propertychange",function(){
		if($(this).val() != ""){
			$(".Dynamic_btn_ok").css("background","#fe6161");
		}else{
			$(".Dynamic_btn_ok").css("background","#cdcdcd");
		}
	});
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
	dynamicList(1);
	enterpriseInfoByFrist();
	$(".Dynamic_newmessage_inner").click(function(){
		window.location.href="newsinfo.html";
	});
});
var page=1;
var verdict=true;
$(window).scroll(function () {
	if(verdict){
        verdict=false;
        page+=1;
        dynamicList(page);
      }
 });
 
 

 
 
 
 
 
function dynamicList(pageOne){
	var data = {"leId":leId,"userId":userId,"pageNum":pageOne}
	 $.ajax({
		   type : "get",
	       url:dataLink+"dynamic/dynamicList", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
		    	   if(pageOne==1){
		    	   	    //通知
			    	   	if(!isnull(json.noticeList) && json.noticeList.length>0){
			    	   		for(j=0;j<json.noticeList.length;j++){
			    	   			var itemNotice = json.noticeList[j];
			    	   			var isUrgent= '';
			    	   			if(itemNotice.isUrgent==1){
			    	   				isUrgent= 'Dynamic_notice_worry';
			    	   			}
			    	   			var html='<p class="'+isUrgent+'">'+itemNotice.title+'</p>';
			    	   			$(".Dynamic_notice_list").append(html);
			    	   		}
			    	   	  $(".Dynamic_notice").show();
			    	   	}
			    	   	//消息
			    	   	if(json.lighCount>0){
			    	   		$(".Dynamic_newmessage_inner").html(json.lighCount+"条新消息");
			    	   		$(".Dynamic_newmessage").show();
			    	   	}
		    	   }
	    	 
	    	   	//动态
	    	   	if(!isnull(json.dynamicList)  && json.dynamicList.length>0){
	    	   		for(i=0;i<json.dynamicList.length;i++){
	    	   			var itemDynamic = json.dynamicList[i];
	    	   			var headUrl = isnull(itemDynamic.headUrl) ? 'img/touxiang_14.png' :  imageHeadSrc+itemDynamic.headUrl;
	    	   			var html  = '<div class="Dynamic_list" id="'+itemDynamic.id+'"><div class="Dynamic_list_head">';
	    	   			    html += '<img src="'+headUrl+'" alt="" />';
	    	   			    html += '<div class="Dynamic_list_head_r">';
	    	   			    html += '<p class="Dynamic_list_head_name">'+itemDynamic.userName+'</p>';
	    	   			    html += '<p class="Dynamic_list_head_time">'+itemDynamic.createTimeStr+'</p>';
	    	   			    html += '</div></div>';
	    	   			    html += '<div class="Dynamic_list_title">'+itemDynamic.title+'</div>';
	    	   			    html += '<div class="Dynamic_list_solid">';
	    	   			    html += '<p class="Dynamic_list_fontone">'+itemDynamic.eventName+'</p>';
    	   			    if (itemDynamic.state ==1) {
							html +='<div class="Dynam_list_fontfive"><span>行动描述:&nbsp;</span>';
							html +='<i>'+itemDynamic.eiDesc+'</i></div>';
							html +='<p class="Dynam_list_fonttwo">计划完成时间：'+itemDynamic.finishTimeStr+'</p>';
							html +='<p class="Dynam_list_fontthree">实际完成时间：'+itemDynamic.fTimeStr+'</p>';
							if(!isnull(itemDynamic.remark)){
								html +='<div class="Dynam_list_fontsix"><span>标记描述:&nbsp;</span>';
								html +='<i>'+itemDynamic.remark+'</i></div>';
							}
						}else if (itemDynamic.state ==2) {
							html +='<div class="Dynam_list_fontfive"><span>行动描述:&nbsp;</span>';
							html +='<i>'+itemDynamic.eiDesc+'</i></div>';
							html +='<p class="Dynam_list_seven">指派时间：'+itemDynamic.delegateTimeStr+'</p>';
							html +='<p class="Dynam_list_eight">接受时间：'+itemDynamic.receTimeStr+'</p>';
						}else if (itemDynamic.state ==3) {
							html +='<div class="Dynam_list_fontfive"><span>行动描述:&nbsp;</span>';
							html +='<i>'+itemDynamic.eiDesc+'</i></div>';
							html +='<div class="Dynam_list_fontnine"><span>跟踪内容:&nbsp;</span>';
							html +='<i>'+itemDynamic.content+'</i></div>';
						}else if (itemDynamic.state ==4) {
							html +='<p class="Dynam_list_fonttwo">计划完成时间：'+itemDynamic.enEndTimeStr+'</p>';
							html +='<p class="Dynam_list_fontthree">实际完成时间：'+itemDynamic.enFinishTimeStr+'</p>';
							html +='<div class="Dynam_list_fontfour"><span>项目总结:&nbsp;</span>';
							html +='<i>'+itemDynamic.summary+'</i></div>';
						}else if (itemDynamic.state ==5) {
							html +='<div class="Dynam_list_fontfive"><span>行动描述:&nbsp;</span>';
							html +='<i>'+itemDynamic.eiDesc+'</i></div>';
							html +='<p class="Dynam_list_seven">指派时间：'+itemDynamic.delegateTimeStr+'</p>';
							html +='<p class="Dynam_list_eight">拒绝时间：'+itemDynamic.rejecTimeStr+'</p>';
						}
	    	   			    html +='</div>';
	    	   			    html +='<div class="Dynamic_list_btn">';
	    	   			if(!isnull(itemDynamic.laudCount) && itemDynamic.laudCount>0){
	    	   				html +='<span class="Dynamic_count"><i>'+itemDynamic.laudCount+'</i>人认同他的工作</span>';
	    	   			}else{
	    	   				html +='<span class="Dynamic_count" style="display:none;"><i>0</i>人认同他的工作</span>';
	    	   			}
	    	   			    html +='<div class="Dynamic_list_btn_r">';
	    	   			    html +='<span class="Dynamic_list_btnone Dynamic_global"></span>';
	    	   			if(itemDynamic.isLaud==1){
	    	   				html +='<span class="Dynamic_list_btntwo Dynamic_list_btn_current" id="Dynamic_btn_'+itemDynamic.id+'"></span>';
	    	   			}else{
	    	   				html +='<span class="Dynamic_list_btntwo" id="Dynamic_btn_'+itemDynamic.id+'"></span>';
	    	   			}
	    	   			    html +='</div></div>';
    	   			    if(!isnull(itemDynamic.interactList) && itemDynamic.interactList.length>0){
	   			    	    html +='<ul class="Dynamic_list_comment" id="Dynamic_comment_'+itemDynamic.id+'">';
		    	   			for(p=0;p<itemDynamic.interactList.length;p++){
		    	   				var inter = itemDynamic.interactList[p];
		    	   				html +='<li><i>'+inter.userName+'</i>:&nbsp;'+inter.judge+'</li>';
		    	   			}
		    	   			html +='</ul>';
    	   			    }else{
    	   			    	html +='<ul class="Dynamic_list_comment" id="Dynamic_comment_'+itemDynamic.id+'" style="display:none;" ></ul>';
    	   			    }
	    	   			    html +='</div>';
	    	   			$(".Dynamic_list_all").append(html);    
	    	   		}
	    	   		
////					点击评论框以外的区域评论框隐藏
//					$('body').click(function(e) {
//					    var target = $(e.target);
//					    // 如果#overlay或者#btn下面还有子元素，可使用
//					    // !target.is('#btn *') && !target.is('#overlay *')
//					    if(!target.is('.Dynamic_global')) {
//					       if ( $('.Dynamic_input').is(':visible') ) {  
//					            $('.Dynamic_input').hide();                                                    
//					       }
//					    }
//					});
	    	   		
	    	   		verdict=true;
	    	   	}else{
	    	   		if(pageOne==1){
	    	   			$(".Dynamic_list_all").hide();
	    	   			$(".Dynamic_nocontent").show();
	    	   		}
	    	   	}
	    	   	
	    	   }
	    	 }
	    });
}
/*
 * type  '类型:0 评论  、1 点赞',
 * 
 * */
function saveInteract(ledId,type,isValid,addr,content){
	$(".spinner").show();
	$(".spinner_bg").show();
	var data = {"ledId":ledId,"createBy":userId,"type":type,"isValid":isValid,"judge":content};
	 $.ajax({
		   type : "post",
	       url:dataLink+"interact/saveInteract", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	    //点赞
	    	   	    if(type==1){
	    	   	    	if(isValid==0){
	    	   	    		var oldcount = $(addr).parents(".Dynamic_list_btn").find("i").html();
	    	   	    		var newcount = parseInt(oldcount) -1;
	    	   	    		if(newcount>0){
	    	   	    			$(addr).parents(".Dynamic_list_btn").find("i").html(newcount);
	    	   	    		}else{
	    	   	    			$(addr).parents(".Dynamic_list_btn").find("i").html(newcount);
	    	   	    			$(addr).parents(".Dynamic_list_btn").find(".Dynamic_count").hide();
	    	   	    		}
	    	   	    		$(addr).removeClass("Dynamic_list_btn_current");
	    	   	    	}else{
	    	   	    		var oldcount = $(addr).parents(".Dynamic_list_btn").find("i").html();
	    	   	    		var newcount = parseInt(oldcount) +1; ;
	    	   	    		$(addr).parents(".Dynamic_list_btn").find("i").html(newcount);
	    	   	    		$(addr).parents(".Dynamic_list_btn").find(".Dynamic_count").show();
	    	   	    		$(addr).addClass("Dynamic_list_btn_current");
	    	   	    	}
	    	   	    }else if(type==0){
	    	   	    	/*$(addr).show();
	    	   	    	$(addr).append('<li><i>'+userName+'</i>:&nbsp;'+content+'</li>');
	    	   	    	$(".Dynamic_input").hide();
	    	   	    	$(".Dynamic_bg").hide();
	    	   	    	$(".Index_footer_button").show();*/
	    	   	    	window.location.href="dynamic.html";
	    	   	    }
	    	   	$(".spinner").hide();
	            $(".spinner_bg").hide();
	            return false;
	    	   }
	    	}
	    });
	
}


/*function useraaa() {
	var data={"userId":userId}
	$.ajax({
		  type : "GET",
	      url:dataLink+"userwx/getUserInfoById",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    	    userName = json.user.userName;
	    	}
	      },  
	      timeout:3000  
	      
	 }); 
 }
*/
 //点击点赞按钮
$(".Dynamic_list_all").on("click",".Dynamic_list_btntwo",function(){
	
	var ledId= $(this).parents(".Dynamic_list").attr("id");
	var addr = "#"+$(this).attr("id");
	var pan = $(this).hasClass("Dynamic_list_btn_current");
	if(pan){
		saveInteract(ledId,1,0,addr);
		return false;
	}else{
		saveInteract(ledId,1,1,addr);
		return false;
	}
	return false;
});

//点击评论按钮
$(".Dynamic_list_all").on("click",".Dynamic_list_btnone",function(){
	$("#Dynamic_input").val("");
	var ledId= $(this).parents(".Dynamic_list").attr("id");
	var addr = "#"+$(this).parents(".Dynamic_list").find(".Dynamic_list_comment").attr("id");
	$(".Dynamic_input").css("display","block");
	$(".Index_footer_button").hide();
	$(".Dynamic_bg").show();
	$("#Dynamic_input").focus();
	$(".Dynamic_btn_ok").unbind("click");
	$(".Dynamic_btn_ok").click(function(){
		$(".Index_footer_button").show();
		var content=$("#Dynamic_input").val();
		if(!isnull(content)){
			saveInteract(ledId,0,1,addr,content);
			return false;
		}
	});
});	