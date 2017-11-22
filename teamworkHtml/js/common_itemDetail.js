//add by xiehuilin  20170921 公开范围--私有 确定更新
$(".memberModal").on("click",".private_determine",function(){
    updateRang(1);
});
//add by xiehuilin  20170921 公开范围--私有 确定更新
$(".memberModal").on("click",".open_determine",function(){
	updateRang(0);
});
//add by xiehuilin 20170921 更新公开范围
function updateRang(isRange){
	var data="";
	 if(isnull(ctId)){
	 	data={"eiId":eiId,"isRange":isRange,"ctId":ctId};
	 }else{
	 	data={"eiId":eiId,"isRange":isRange};
	 }
		$.ajax({//得到协助者信息
	        type : "POST",
	        url:dataLink+"item/updatePublicScope",
	        data:data,
	        dataType : "json",
	        success : function(json){
	            if(getAjaxJson(json)){//成功的时候
	            	if(isRange==1){
	            		$(".Event_public").hide();
						$(".Event_publictwo").show();
						$(".action_private").hide();
	            	}else{
	            		$(".event_summary").hide();
						$(".action_stop").hide();
						$(".Event_option_alert").hide();
	            	}	
	            }
	        }
	  });
}


//add by xiehuilin 2017/11/02 终止行动
$(".stop_determine").click(function(){
	var userId=localStorage.getItem("team_userId");
	var state=5;
	var data={"createBy":userId,"state":state,"eiId":eiId,"strfTime":new Date().getTime()}
	$.ajax({
		  type : "POST",
	      url:dataLink+"item/updateItemState",  
	      dataType:'json',  
	      data:data, 
	     
	      success:function(json){
	    	  if(getAjaxJson(json)){
	    		  window.location.reload();//刷新当前页面.
	    	  }
	      },  
	      timeout:3000  
	 });
});

//add  by xiehuilin 2017/11/02 删除行动
$(".deleteModal").on("click",".delete_determine",function(){
	var userId=localStorage.getItem("team_userId");
	var data={"createBy":userId,"eiId":eiId}
	$.ajax({
		  type : "POST",
	      url:dataLink+"item/delEventItem",  
	      dataType:'json',  
	      data:data, 
	      success:function(json){
	    	  if(getAjaxJson(json)){
	    		 $(".event_summary").hide();
				$(".action_delete").hide();
				$(".Event_option_alert").hide();
				$(".delete_success").fadeIn(0).delay(3000).fadeOut(0);
	    	  }
	      },  
	      timeout:3000  
	 });
});

//add  by xiehuilin 2017/11/02 发布跟踪进度
	$(".Event_track_ok").on("click",function(){
		var conent=$(this).parents().parents().find(".Event_alertthree_textarea").val();
		if(isnull(conent)){
			$(".Event_alertthree p").show();
			setTimeout(function(){$(".Event_alertthree p").hide();},2000);
			return false;
		}
		tracks.save(conent);
 });

//add  by xiehuilin 20170816 提交跟踪记录
var post_flag = false; //设置一个对象来控制是否进入AJAX过程
var tracks={
	save:function(conent){
		if(post_flag) return; //如果正在提交则直接返回，停止执行
		post_flag = true;//标记当前状态为正在提交状态
		var createBy= localStorage.getItem("team_userId");
		var param={"eiId":eiId,"content":conent,"createBy":createBy};
    	var url=dataLink+"track/save";
    	 _asyn(url,param,"POST",tracks.ok);
	},
	ok:function(json,code){
		if(getAjaxJson(json)){
			post_flag =false; //在提交成功之后将标志标记为可提交状态
			window.location.reload();
		}else{
		   post_flag = false;
		}
	}
}


var itemStateVlue = 
{
   3: "【正常完成】",
   4: "【滞后完成】",
   7:"【提前完成】"
 };

 //add by xiehuilin 2017/11/02 行动完成
$(".Event_alerttwo").on("click",".Event_btn_ok",function(){
	var remark=$(".Event_textarea").val();
	if (!centen(remark,1,80)) {
		  $(".Event_texttips").fadeIn(0).delay(2000).fadeOut(0);
		return false;
	}
		complete();
});

  function  complete(){
	var userId=localStorage.getItem("team_userId");
	var userTime=$(".appDateTime").val();//用户完成时间
	var remark=$(".Event_textarea").val();
	var state=3;
	var reslutTime=getFormatDateByLong(Number(finishTime), "yyyy-MM-dd"); 	
	var fTime = userTime.replace(new RegExp("-","gm"),"/");
		fTime= (new Date(fTime)).getTime(); //得到毫秒数
	//根据完成时间和当前时间进行判断是否是延期完成
	if (!isToday(reslutTime)&&finishTime<fTime) {
		state=4;
	}
	if(isToday(reslutTime)){
		state=finishTime<fTime?4:3;
	}
	var data={"createBy":userId,"state":state,"eiId":eiId,"dutyId":dutyId,"eId":eId,"remark":remark,"strfTime":fTime}
	$.ajax({
		  type : "POST",
	      url:dataLink+"item/updateItemState",  
	      dataType:'json',  
	      data:data, 
	      success:function(json){
	    	  if(getAjaxJson(json)){
	    		//刷新当前页面.
	    		myRefresh();
	    	  }
	      },  
	      timeout:3000  
	 });
}
