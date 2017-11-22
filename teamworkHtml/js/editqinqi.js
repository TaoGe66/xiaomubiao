var leId = localStorage.getItem("team_leId");
$(function(){
	enterpriseInfo();
	
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
	
	$(".active_blue").click(function(){
	 	if(centen($(".new_name_short input").val(),2,12)){
	 		if(centen($(".new_name_long input").val(),2,60)){
	 			if(centen($("textarea").val(),2,600)){
	 				updateEnterprise();
	 			}else{
	 				$(".memberUndonePopups p").html("轻企概况最多300个汉字");
			 		$(".memberUndonePopups").show();
			 		setTimeout(function(){
						$(".memberUndonePopups").hide();
					},2000);
	 			}
	 		}else{
	 			$(".memberUndonePopups p").html("轻企全称最多30个汉字");
		 		$(".memberUndonePopups").show();
		 		setTimeout(function(){
					$(".memberUndonePopups").hide();
				},2000);
	 		}
	 	}else{
	 		$(".memberUndonePopups p").html("轻企简称最多6个汉字");
	 		$(".memberUndonePopups").show();
	 		setTimeout(function(){
				$(".memberUndonePopups").hide();
			},2000);
	 	}
	 	
	});
	
	
});


function enterpriseInfo(){
	var data = {"leId":leId};
	 $.ajax({
		   type : "get",
	       url:dataLink+"enterprise/enterpriseInfo", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   		var item = json.enterpriseInfo;
	    	   	    $(".new_name_short input").val(item.name);
	    	   	    $(".new_name_long input").val(item.fullName);
	    	   	    $("textarea").val(item.leDetails);
	    	   	    $(".New_img").find("p").html(item.leLogo);
	    	   	    $(".New_img").find(".img_one").attr("src",imageHeadSrc+item.leLogo);
	    	   }
	    	  }
	   });
}

function updateEnterprise(){
	var leLogo =$(".img_hide").html();
	var name =$(".new_name_short input").val();
	var fullName = $(".new_name_long input").val();
	var leDetails = $("textarea").val();
	var data = {"leId":leId,"leLogo":leLogo,"name":name,"fullName":fullName,"leDetails":leDetails}
	 $.ajax({
		   type : "post",
	       url:dataLink+"enterprise/updateEnterprise", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   	 window.location.href="dynamic.html";
	    	}
	    });
}


function selectEnterprise(){
	var name =$(".new_name_short input").val();
	var fullName = $(".new_name_long input").val();
	
	var data = {"leId":leId,"name":name,"fullName":fullName}
	 $.ajax({
		   type : "post",
	       url:dataLink+"enterprise/selectEnterprise", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	if(json.isSave==1){
	    	   		updateEnterprise();
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
