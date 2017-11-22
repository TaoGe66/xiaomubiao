
//add by xiehuilin 2017/11/01 行动详情数据渲染
var eiId=isnull($.getUrlParam("eiId"))?localStorage.getItem("team_eiId"):$.getUrlParam("eiId");
 var userId=localStorage.getItem("team_userId");
 var itemState=0;
 var trId,dutyId,eId,finishTime,ctId;
$(function(){
	$(".Event_option_four").hide();
	//add by zhanghaitao 2017/11/02  冻结与解冻弹窗
	$(".Event_option_three").on("click",function(){
		//冻结
		freeze();
		//$(".dongjie_open").show();
	});
	$(".dongjie_open").on("click",".open_determine",function(){
		$(".dongjie_open").hide();
		$(".Event_option_four").show();
		$(".Event_option_three").hide();
	});
	//解冻
	$(".Event_option_four").on("click",function(){
		activateCyc();
		//$(".jiedong_open").show();
	});
	$(".jiedong_open").on("click",".open_determine",function(){
		$(".jiedong_open").hide();
		$(".Event_option_four").hide();
		$(".Event_option_three").show();
	});
	
	//add by zhanghaitao 2017/11/02  删除行动
	$(".Event_option_one").on("click",function(){
		$(".event_summary").hide();
		$(".Event_option_alert").hide();
		$(".delete_stop").show();
	});
	$(".delete_stop").on("click",".stop_determine",function(){
		$(".delete_stop").hide();
		$(".delete_success").fadeIn(0).delay(2000).fadeOut(0);
	});
	//add by zhanghaitao 2017/11/02  发布进度弹窗
	$(".cycle_fabu").click(function(){
		$(".Event_alertthree").show();
	});
	//add by zhanghaitao 2017/11/02  行动完成弹窗
	$(".cycle_biaoji").click(function(){
		$(".event_summary").show();
		$(".Event_alerttwo").show();
	});
});

(function (){
        var url = dataLink + "item/clyeEventItemDetails";
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            data: {"eiId": eiId,"userId":userId},
            success: function (json) {
              console.info(json);
              if(getAjaxJson(json)){
              	$(".Event_areatwo").find("p").eq(0).html(json.itemInfo.eiDesc);
              	$(".Event_chuangjian").find("i").eq(0).html(json.itemInfo.dUserName);
              	$(".Event_starttime1").find("i").eq(0).html(json.itemInfo.strStartTime);
              	$(".Event_endtime2").find("i").eq(0).html(json.itemInfo.strFinishTime);
              	showItem(json);
              }
            },
            error: function(XMLHttpRequest) {
                
            }
        });
}());


//激活
function activateCyc(){
	var data = {"ctId":ctId,"cycleState":0};
	$.ajax({
		   type : "post",
	       url:dataLink+"item/updateActivateCyc", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg=="success"){
	    		   window.location.href="cycleDetails.html";
	    	   }
	       }
	});
 }		



//冻结
function freeze(){
	var data = {"ctId":ctId,"cycleState":1};
	$.ajax({
		   type : "post",
	       url:dataLink+"item/updateCycleState", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg=="success"){
	    		   window.location.href="cycleDetails.html";
	    	   }
	       }
	});
 }		

function showItem(json){

	  var items=json.eventItems;
	  var itemsHtml=[];
	  for(var x=0;x<items.length;x++){
	  	var html=
	  		'<div class="section_content">'
			html+='<div class="cycle_titleshow">'
			html+='<!-- <p class="cycle_data">点击加载更多</p> -->'
			html+='<p class="cycle_day"><span class="cycle_time">'+items[x].startStrTime+'</span><span class="cycle_biaoji">标记完成</span></p>'
			html+='</div>'
			html+='<div class="cycle_content">'
			if(items[x].recordList.length<=0){
				html+='<div class="actionsection_block cycle_block">'
				html+='<div class="actionsection_left">'
				html+='<img src="img/imgredyuan_03.png"><i class="cycle_time_line"></i>'
				html+='</div>'
				html+='<div class="qs_right">'
				html+='<p class="qs_title">截至到当前，还没有进度内容！</p>'
				html+='<p class="cycle_text">走错的路也是路</p>'
				html+='<p class="cycle_fabu">发布进度</p>'
				html+='</div>'
				html+='</div>'
			}else{
				html+='<div class="actionsection_block">'
				html+='<div class="actionsection_left">'
				html+='<img src="img/touxiang_14.png"><span class="cycle_time_line"></span>'
				html+='</div>'
				html+='<div class="actionsection_right">'
				html+='<p class="actionright_name">'
				html+='<span>张三</span><em>昨天 18:00</em>'
				html+='</p>'
				html+='<div class="actionright_content">'
				html+='<span class="aaa"><i class="wancheng_status">【正常完成】</i>对于小目标APP的框架逻辑111111111111111111111111</span>'
				html+='</div>'
				html+='<div class="actionright_interaction">'
				html+='<span class="actionright_revoked">撤销</span>'
				html+='</div>'
				html+='</div>'
				html+='</div>'
			}
			html+='</div>'
			html+='</div>'
	  	itemsHtml.push(html);
	  }
	  $(".section_block").append(itemsHtml.join(""));

}


