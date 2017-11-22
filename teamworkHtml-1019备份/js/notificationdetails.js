//add by xiehuilin 20170918 渲染数据
 (function () {
    var id=localStorage.getItem("team_notifyId");
    var leId=localStorage.getItem("team_leId");
	var data={"leId":1,"id":id}
	$.ajax({
		  type : "GET",
	      url:dataLink+"notice/getNoticeInfo",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		 $(".Not_title").html(json.noticet.title);
	    		 $(".Not_time").html(json.noticet.pushTime);
	    		 $(".Not_con").html(json.noticet.content);
	    	}
	      },  
	      timeout:3000  
	      
	 }); 
    }());