check_time();
//校验时间
function check_time(){
	var eId=localStorage.getItem("team_eId");
	var data = {"eId":eId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"event/sumUp", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    		  var item = json.even;
	    		  
	    		  var logo='img/img24.png';
	    		  var state="正常完成";
	    		  if (item.state==8) {
	    			  state="滞后完成";
	    			  logo='img/lagging.png';
	    		  }else if (item.state==6) {
	    			  state="异常终止";
	    			  logo="img/abnormal.png";
	    		  }
	    		  
	    		  var summary="";
	    		  if (!isnull(item.summary)) {
	    			  summary=item.summary;
				}
	    		  
//	    		  if(item.type==0){
//	    		  	$(".Eve_title").html("任务报告");
//	    		  	$(".Eve_one").html("任务名称：");
//	    		  	$(".Eve_time").html("任务时间：");
//	    		  }else if(item.type==1){
//	    		  	$(".Eve_title").html("需求报告");
//	    		  	$(".Eve_one").html("需求名称：");
//	    		  	$(".Eve_time").html("需求时间：");
//	    		  }
	    		  var compRate=item.compRate=="NaN"? 0 :item.compRate;
	    		  $(".is_state_name").html(item.name);
	    		  $(".is_state").html(state);
	    		  $(".bgtime").html(Time(item.startTime));
	    		  $(".endtime").html(Time(item.endTime));
	    		  $(".time").html(timeFinish(item.finishTime,1));
	    		  $(".summary").html(summary);
	    		  $(".rate").html((compRate*100).toFixed(0)+"%");
	    		  $(".state_logo").html('<img class="complete" src="'+logo+'" alt="" />');
	    		  $(".number").html(json.stateFive);
	    		 var oneRhtml = '暂无';
	    		  if(!isnull(json.oneRank) && json.oneRank.length>0){
	    		  	for(p=0;p<json.oneRank.length;p++){
	    		  		if(p==0){
	    		  		    oneRhtml = json.oneRank[p].userName;
	    		  		}else{
	    		  		    oneRhtml += '、'+json.oneRank[p].userName;
	    		  		}
	    		  	}
	    		  }
	    		  $(".Event_people_one div").html(oneRhtml);
	    		  var fiveRhtml = '暂无';
	    		  if(!isnull(json.fiveRank) && json.fiveRank.length>0){
	    		  	for(p=0;p<json.fiveRank.length;p++){
	    		  		if(p==0){
	    		  			fiveRhtml = json.fiveRank[p].userName;
	    		  		}else{
	    		  			fiveRhtml += '、'+json.fiveRank[p].userName;
	    		  		}
	    		  		
	    		  	}
	    		  }
	    		  $(".Event_people_two div").html(fiveRhtml);
	    		  var fourShtml = '暂无';
	    		  if(!isnull(json.statefour) && json.statefour.length>0){
	    		  	for(p=0;p<json.statefour.length;p++){
	    		  		if(p==0){
	    		  		     fourShtml = json.statefour[p].userName;
	    		  		}else{
	    		  			 fourShtml += '、'+json.statefour[p].userName;
	    		  		}
	    		  	}
	    		  	
	    		  }
	    		  $(".Event_people div").html(fourShtml);
	    	   }
	    	  }
	    });
	
}


function timeFinish(long,siem){
	var remindTime = new Date(long);
	var Times = remindTime.getFullYear()+"-"+Substr(remindTime.getMonth()+1)+"-"+Substr(remindTime.getDate());
	if(siem){
		Times=Times+" "+Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes());
	};
	return Times;
};
$(function(){
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
})
