$(function(){
//点击公开私有是否选中
$(".Serve_bottom_open span").click(function(){
	$(this).toggleClass("Serve_bottom_open_current");
});
});

var isCycle=0;
var ctId=null;

echo();
// 回显
function  echo(){
	var eiId=localStorage.getItem("team_eiId");
	var userId=localStorage.getItem("team_userId");
	var data={"eiId":eiId}
	$.ajax({
		  type : "GET",
	      url:dataLink+"item/getActionItem",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  var item = json.item;
	    		  isCycle=item.isCycle;
	    		  $(".time_affair").val(item.eiDesc);
	    		  if (item.ctId!=null) {
	    			  ctId=item.ctId;
				}
	    		  if (isCycle==1) {
	    			  $(".startDate").html(TimeIt(item.startTime));
		    		  $(".endDate").html(TimeIt(item.finishTime));
					}else{
						$(".startDate").html(TimeIt(item.startTime,1));
			    		$(".endDate").html(TimeIt(item.finishTime,1));
					}
	    		  $(".userName").html(item.userName);
	    		  if (item.isRange==0) {
	    			  $(".Serve_bottom_open span").removeClass("Serve_bottom_open_current");
				}
	    		 if (isCycle==0) {
					$(".is_echo").html("不重复");
				}else if (isCycle==1 && item.cycleFreq==0) {
					$(".is_echo").html("每天");
				}else if (isCycle==1 && item.cycleFreq==1 && item.cycle=="MO,TU,WE,TH,FR" ){
					$(".is_echo").html("周一至周五");
				}else if (isCycle==1 && item.cycleFreq==1 && item.cycle=="SA,SU" ) {
					$(".is_echo").html("周六日");
				}else{
					$(".is_echo").html("自定义");
				}
	    		 
	    		 if (item.frequency==1) {
	    			 var html ='<li class="Serve_current"><i style="display:none">1</i>不提醒</li>';
				}else {
					var html ='<li><i style="display:none">1</i>不提醒</li>';
				}
	    		 if (item.frequency==0) {
	    			 html +='<li class="Serve_current"><i style="display:none">0</i>正点</li>';
				}else{
					html +='<li ><i style="display:none">0</i>正点</li>';
				}
	    		 if (item.frequency==-5) {
	    			 html +='<li class="Serve_current"><i style="display:none">-5</i>5分钟前</li>';
				}else{
					html +='<li><i style="display:none">-5</i>5分钟前</li>';
				}
	    		 if (item.frequency==-15) {
	    			 html +='<li class="Serve_current"><i style="display:none">-15</i>15分钟前</li>';
				}else{
					html +='<li><i style="display:none">-15</i>15分钟前</li>';
				}
	    		 if (item.frequency==-30) {
	    			 html +='<li class="Serve_current"><i style="display:none">-30</i>30分钟前</li>';
				}else{
					 html +='<li><i style="display:none">-30</i>30分钟前</li>';
				}	 
	    		 if (item.frequency==-60) {
	    			 html +='<li class="Serve_current"><i style="display:none">-60</i>1小时前</li>';
				}else{
					 html +='<li><i style="display:none">-60</i>1小时前</li>';
				}	 
	    		 if (item.frequency==-1440) {
	    			 html +='<li class="Serve_current"><i style="display:none">-1440</i>1天前</li>';
				}else{
					 html +='<li><i style="display:none">-1440</i>1天前</li>';
				}
	    		 $(".warn").html(html);	 
	    		 checkFollow();	
	    			//点击提醒时间
	    		 $(".warn li").click(function(){
	    		 		$(this).addClass("Serve_current").siblings().removeClass("Serve_current");
	    		 	});
	    	  }
	      },  
	      timeout:3000  
	 }); 
}

//校验内容是否可以编辑
function  checkFollow(){
	var eiId=localStorage.getItem("team_eiId");
	var userId=localStorage.getItem("team_userId");
	var data={"eiId":eiId,"ctId":ctId}
	$.ajax({
		  type : "GET",
	      url:dataLink+"track/checkFollow",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  var item = json.count;
	    		  if (item==1) {
	    			  $(".time_affair").attr("readOnly",false); 
				}
	    	  }
	      },  
	      timeout:3000  
	 }); 
}



$(".Serve_release").click(function(){
	if (isCycle==0) {
		save_item();
	}else{
		save_cyc();
	}
});





//保存事项
function save_item(){
	var  depict=$(".time_affair").val();
	depict=_trim(depict);
	var eId=localStorage.getItem("team_eId");
	var userId=localStorage.getItem("team_userId");
	var eiId=localStorage.getItem("team_eiId");
	var frequency=$(".Serve_current").find("i").html();
	var isRange=0;
	if ($(".Serve_bottom_open").find("span").hasClass("Serve_bottom_open_current")) {
		isRange=1;
	}
	$(".event_summary").show();
	$(".event_summary1").show();
	var Json='"eId":"'
		+eId
		+'","eiId":"'
		+eiId
		+'","frequency":"'
		+frequency
		+'","eiDesc":"'
		+depict
		+'","isRange":"'
		+isRange
		+'","createBy":"'
		+userId
		+'","state":"'
		+0
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	var data='{'+start+'}';
	$.ajax({
		url : dataLink + "item/updateItem",
		type : "POST",
		data : data,
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		success : function(result) {
			if (result.msg == "success") {
				window.location.href="eventdetailsone.html";
				}
		},
		timeout : 3000
	});
	
}




//保存事项
function save_cyc(){
	var  depict=$(".time_affair").val();
	depict=_trim(depict);
	var eId=localStorage.getItem("team_eId");
	var userId=localStorage.getItem("team_userId");
	var eiId=localStorage.getItem("team_eiId");
	var frequency=$(".Serve_current").find("i").html();
	var isRange=0;
	if ($(".Serve_bottom_open").find("span").hasClass("Serve_bottom_open_current")) {
		isRange=1;
	}
	$(".event_summary").show();
	$(".event_summary1").show();
	var Json='"eId":"'
		+eId
		+'","eiId":"'
		+eiId
		+'","eiDesc":"'
		+depict
		+'","createBy":"'
		+userId
		+'","frequency":"'
		+frequency
		+'","isRange":"'
		+isRange
		+'","ctId":"'
		+ctId
		+'","isCycle":"'
		+1
		+'","cycleState":"'
		+0
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	var data='{'+start+'}';
	$.ajax({
		  url:dataLink+"item/updateCycItem",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     contentType: "application/json; charset=utf-8",
	     success:function(result){
	   	  if(result.msg=="success"){
	   		  window.location.href="eventdetailsone.html";
	   	  }
	     },  
	     timeout:3000  
	});
	
}





//时间格式
function TimeIt(long,siem){
	var remindTime = new Date(long);
	if(siem){
	var Times = remindTime.getFullYear()+"-"+Substr(remindTime.getMonth()+1)+"-"+Substr(remindTime.getDate());
		Times=Times+" "+Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes());
	}else{
		var Times = Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes());
	}
	return Times;
};