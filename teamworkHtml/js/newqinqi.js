var userId = localStorage.getItem("team_userId");
$(function(){
//	实时监控事件
	 $(".new_ziliao").on('keyup', function(event){
 		 if(isnull($(".new_name input").val())|| isnull($("textarea").val())||isnull($(".img_hide").html())){
 		 	$(".new_btn").addClass("active_gray").removeClass("active_blue");
 		 }else{
 		 	$(".new_btn").addClass("active_blue").removeClass("active_gray");
 		 	
 		 	$(".active_blue").unbind("click");
 		 	$(".active_blue").click(function(){
			 	if(centen($(".new_name_short input").val(),4,12)){
			 		if(centen($(".new_name_long input").val(),6,60)){
			 			if(centen($("textarea").val(),2,600)){
			 				selectEnterprise();
			 			}else{
			 				$(".memberUndonePopups p").html("轻企概况最多300个汉字");
					 		$(".memberUndonePopups").show();
					 		setTimeout(function(){
								$(".memberUndonePopups").hide();
							},2000);
			 			}
			 		}else{
			 			$(".memberUndonePopups p").html("轻企全称3~30个汉字");
				 		$(".memberUndonePopups").show();
				 		setTimeout(function(){
							$(".memberUndonePopups").hide();
						},2000);
			 		}
			 	}else{
			 		$(".memberUndonePopups p").html("轻企简称2~6个汉字");
			 		$(".memberUndonePopups").show();
			 		setTimeout(function(){
						$(".memberUndonePopups").hide();
					},2000);
			 	}
			 	
			});
 		 }
	});
	
	
	$(".img_hide").bind('DOMNodeInserted', function(e) {  
 		 if(isnull($(".new_name input").val())|| isnull($("textarea").val())||isnull($(".img_hide").html())){
 		 	$(".new_btn").addClass("active_gray").removeClass("active_blue");
 		 }else{
 		 	$(".new_btn").addClass("active_blue").removeClass("active_gray");
 		 	
 		 	$(".active_blue").unbind("click");
 		 	$(".active_blue").click(function(){
			 	if(centen($(".new_name_short input").val(),4,12)){
			 		if(centen($(".new_name_long input").val(),6,60)){
			 			if(centen($("textarea").val(),2,600)){
			 				insert();
			 			}else{
			 				$(".memberUndonePopups p").html("轻企概况最多300个汉字");
					 		$(".memberUndonePopups").show();
					 		setTimeout(function(){
								$(".memberUndonePopups").hide();
							},2000);
			 			}
			 		}else{
			 			$(".memberUndonePopups p").html("轻企全称3~30个汉字");
				 		$(".memberUndonePopups").show();
				 		setTimeout(function(){
							$(".memberUndonePopups").hide();
						},2000);
			 		}
			 	}else{
			 		$(".memberUndonePopups p").html("轻企简称2~6个汉字");
			 		$(".memberUndonePopups").show();
			 		setTimeout(function(){
						$(".memberUndonePopups").hide();
					},2000);
			 	}
			 	
			});
 		 }
	});
	
	
});
function insert(){
	var leLogo =$(".img_hide").html();
	var name =$(".new_name_short input").val();
	var fullName = $(".new_name_long input").val();
	var leDetails = $("textarea").val();
	var data = {"createBy":userId,"leLogo":leLogo,"name":name,"fullName":fullName,"leDetails":leDetails};
	 $.ajax({
		   type : "post",
	       url:dataLink+"enterprise/insert", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	     localStorage.setItem("team_leId",json.id);
	    	     var dataa ={"userId":userId,"leId":json.id};
	    	     updateUserInfo(dataa);
	    	}
	    });
}

function updateUserInfo(data){
	 $.ajax({
		   type : "post",
	       url:dataLink+"userwx/updateUserInfo", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    		  window.location.href="dynamic.html";
	    	   }
	       }
	 });
}

function selectEnterprise(){
	var name =$(".new_name_short input").val();
	var fullName = $(".new_name_long input").val();
	
	var data = {"name":name,"fullName":fullName}
	 $.ajax({
		   type : "POST",
	       url:dataLink+"enterprise/selectEnterprise", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	if(json.isSave==1){
	    	   		insert();
	    	   	}else if(json.isSave==2){
	    	   		$(".memberUndonePopups p").html("轻企简称已有人占用");
			 		$(".memberUndonePopups").show();
			 		setTimeout(function(){
						$(".memberUndonePopups").hide();
					},2000);
	    	   	}else if(json.isSave==3){
	    	   		$(".memberUndonePopups p").html("轻企全称已有人占用");
			 		$(".memberUndonePopups").show();
			 		setTimeout(function(){
						$(".memberUndonePopups").hide();
					},2000);
	    	   	}
	    	  }
	       }
	 });
	
}


