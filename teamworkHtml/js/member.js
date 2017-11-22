//add by xiehuilin 20170918 渲染数据
 (function () {
   var userId =localStorage.getItem("team_memId");
	var data={"userId":userId}
	$.ajax({
		  type : "GET",
	      url:dataLink+"userwx/getUserInfoById",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    	    var Mem = "";
	    	  if(json.user.sex==0){
	    	  		Mem = "女";
	    	  	}else if(json.user.sex==1){
	    	  		Mem = "男";
	    	  	}else if(json.user.sex==2){
	    	  		Mem = "保密"
	    	  	}
	    		 $(".Member_list_one i").html(json.user.userName);
	    		 $(".Member_list_two i").html(Mem);
	    		 if(json.user.birthday!=null){
	    		 	$(".Member_list_three i").html(Time(json.user.birthday));
	    		 }
    		  	 $(".Member_list_four i").html(json.user.address);
    		  	 $(".Member_list_five i").html(json.user.note);
	    	}
	      },  
	      timeout:3000  
	      
	 }); 
    }());