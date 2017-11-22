var userId = localStorage.getItem("team_userId");
$(function(){
	eventa.data();
	eventEnd.data();
});

var eventa={
	data:function(){
		var param={"userId":userId};
    	var url=dataLink+"homepage/tadayEvent";
		_asyn(url,param,"get",eventa.Ok);
	},
	Ok:function(json,code){
		if(json.msg="success"){
			$(".Index_list_con_three").html("");
		if(!isnull(json.pageInfo)&&json.pageInfo.length>0){
			$(".Index_project_title_one").html("进行中（"+json.pageInfo.length+"）");
	   		for(i=0;i<json.pageInfo.length;i++){
	   			var item = json.pageInfo[i];
					var redspan = isnull(item.isRecord) ? '<p class="Index_project_list_title">':'<p class="Index_project_list_title Index_project_list_titleone" id="'+item.isRecord+'">';
					 var html  ='<div class="Index_project_list" id="'+item.eId+'">';
					     html +=redspan+'<span></span>'+item.name+'</p>';
					     html +='<p class="Index_project_list_name">负责人：'+item.userName+'</p>';
					     html +='<div class="Index_project_list_btm"><div class="Index_project_list_from">';
						if(!isnull(item.endTime)){
							 html +=sameDate(item.startTime,item.endTime)+'</div>';
							 html +='<div class="Index_project_list_btn">剩余时长：<i>10天</i></div>';
						}else{
							 html +=Timeday(item.startTime)+'- <i class="to_timeend">未设置</i></div>';
						}
						 html +='</div></div>';
						$(".Index_list_con_three").append(html);
		     }
	   		   $(".Index_project_list").click(function(){
	   		      var eId =$(this).attr("id");
	   		      var isrecord_id = $(this).find(".Index_project_list_titleone").attr("id");
	   		      localStorage.setItem("team_eId",eId);
		    	  localStorage.setItem("isrecord_id",isrecord_id);
		    	  window.location.href="eventdetailsone.html";
	   		   });
	   		   $(".to_timeend").click(function(){
	   		   	$(".event_summary").show();
	   		   	  $(".Index_overtime").show();
	   		   	  return false;
	   		   });
	     }else{
	     	$(".Index_project_title_one").html("进行中（0）");
	     }
		}
	}
}


var eventEnd={
	data:function(){
		var param={"userId":userId,"state":2};
    	var url=dataLink+"event/listJoinTwo";
		_asyn(url,param,"get",eventEnd.Ok);
	},
	Ok:function(json,code){
		if(json.msg="success"){
			$(".Index_list_btmcon").html("");
			if(!isnull(json.list)&&json.list.length>0){
				$(".Index_project_title_two").html("已完成（"+json.list.length+"）");
		    	for(i=0;i<json.list.length;i++){
		    	   var item = json.list[i];
		    	   var html  ='<div class="Index_project_list" id="'+item.eId+'">';
				       html +='<p class="Index_project_list_title"><span></span>'+item.name+'</p>';
				       html +='<p class="Index_project_list_name">负责人：'+item.userName+'</p>';
				       html +='<div class="Index_project_list_btm"><div class="Index_project_list_from">';
					if(!isnull(item.endTime)){
						 html +=sameDate(item.startTime,item.endTime)+'</div>';
					}else{
						 html +=Timeday(item.startTime)+'-';
					}
					 html +='</div></div>';
					$(".Index_list_btmcon").append(html);
			}
		    	
		      $(".Index_project_list").click(function(){
	   		      var eId =$(this).attr("id");
	   		      localStorage.setItem("team_eId",eId);
		    	  window.location.href="eventdetailsone.html";
	   		   });
		  }else{
		  	$(".Index_project_title_two").html("已完成（0）");
		  }
	   }
	}
}