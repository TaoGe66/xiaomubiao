var eId = localStorage.getItem("team_eId");
var roleType=localStorage.getItem("team_roleType");
var userId=localStorage.getItem("team_userId");
$(function(){
	var btn = document.getElementsByClassName("write_reviews")[0];
	var Text_box=document.getElementById("Text_box");
	btn.onclick = function(){
		
		$(".input_box").css("display","block");
		$("#Text_box").focus();
	}
	$("#confirm").click(function(){
		var content=$("#Text_box").val();
		if(content == ""){
		}else{
			save.send(content);
		}
	});
	$("#Text_box").bind("input propertychange",function(){
		if($(this).val() != ""){
			$("#confirm").css("background","#fe6161");
		}else{
			$("#confirm").css("background","#cdcdcd");
		}
	})
	//add  by xiehuilin 2017/06/27  返回上一页
	$(".return").click(function(){
		history.back();
	});
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
	getComment();
})
var page=1;//页码
var verdict=true;//是否继续刷新
$(window).scroll(function () {
        if (verdict) {
                verdict=false;
                page+=1;
                getComment();
        }
});



/**
 * 评论消息列表
 * @author zhengjunting
 * @date 2017年6月19日
 */
function getComment(){
	
	var data = {"eId":eId,"pageNum":page}
	 $.ajax({
		   type : "get",
	       url:dataLink+"com/getCommntList", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	  $(".comment_list").html("");
	    	   	var comments=json.comments;
	    	   	comments.length<=0&&page==1?$(".comment_default").show():$(".comment_default").hide();
	    	   	  if(comments.length>0){
	    	   	  	for(var x=0;x<comments.length;x++){
	    	   	  			var item =comments[x];
	    	   	  			var ctimes=item.ctime;
	    	   	  			ctimes=ctimes==null||ctimes==""?"1秒前":ctimes;
	    	   	  			var logo=item.headUrl==null?"img/img03.png":(imageHeadSrc+item.headUrl);
	    	   	  			var html='<div class="list_content bigshdow">';
					 		html+='<div class="top">';
					 		html+='<div class="left">';
					 		html+='<img src="'+logo+'" alt="" />';
					 		html+='	</div>';
					 		html+='<div class="right">';
					 		html+='<span class="name">'+item.userName+'</span>';
					 		/*if(item.identity==1){
					 		  html+='<i class="founder"></i>';
					 		}*/
							html+='<p class="data">'+ctimes+'</p>';
							html+='</div>';
							html+='<div class="bottom">';
							html+=item.content;
							html+='</div>';
							html+='</div>';
							$(".comment_list").append(html);
	    	   	  			
	    	   	  	}
					verdict=true;
	    	   	  }else{
	    	   	  	verdict=false;
	    	   	  }

	    	   		
	      }
	  }
	});
}

// add  by xiehuilin 2017/06/23 保存评论内容
var save={
	send:function(content){
		var param={"userId":userId,"eId":eId,"identity":roleType,"content":content};
    	var url=dataLink+"com/save";
    	_asyn(url,param,"POST",save.Ok);
	},
	Ok:function(json,code){
		if(json.msg=="success"){
			_g("comment.html");
			
		}
	}
}

