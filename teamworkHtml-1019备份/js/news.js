var sendId = localStorage.getItem("team_userId");
var page=1;
var verdict=true;
$(window).scroll(function() {
	if (verdict) {
		verdict = false;
		page += 1;
		var cut = localStorage.getItem("team_isCut");
		if (cut == 1) {
			wait(page);
		} else {
			getNews(page);
		}
	}
});
//切换
$(function(){
	$(".li1").on("click",function(){
		$(".table ul .li1 a").attr("class","a1");
		$(".table ul .li2 a").attr("class","a2");
		$(".table ul .li1 a").css("color","#FF0000");
		$(".table ul .li2 a").css("color","#333");
		$(".News_system").css("display","block");
		$(".News_remind").css("display","none");
		$(".my_event_default").hide();
		localStorage.setItem("team_isCut",null);
		$(".News_system").html("");
		page=1;
		verdict=true;
		getNews(page);
	});
	$(".li2").on("click",function(){
		$(".table ul .li1 a").attr("class","a1_click");
		$(".table ul .li2 a").attr("class","a2_click");
		$(".table ul .li2 a").css("color","#FF0000");
		$(".table ul .li1 a").css("color","#333");
		$(".News_system").css("display","none");
		$(".News_remind").css("display","block");
		$(".my_event_default").hide();
		localStorage.setItem("team_isCut",1);
		$(".News_remind").html("");
		page=1;
		verdict=true;
		wait(page);
	});
});

	
/**
 * 系统消息
 * @author zhengjunting
 * @date 2017年6月19日
 */
getNews(page);
function getNews(page){
	$(".qsy2").hide();
	var data = {"sendId":sendId,"nType":2,"pageNum":page}
	 $.ajax({
		   type : "get",
	       url:dataLink+"news/getNewsByUserid", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
		    	   	if(page==1){
		    	   		$(".News_system").html("");
		    	   	}
	                if(!isnull(json.newsList)&&json.newsList.length>0){
	                	for(i=0;i<json.newsList.length;i++){
	                		var item = json.newsList[i];
		                	var	html = '<li class="smallshdow">';
		                		html +='<p class="News_name">'+item.nTitle+'</p>';
		                		html +='<p class="News_font">'+item.nContent+'</p>';
		                		html +='<p class="News_time">'+item.nTimeStr+'</p>';
								html +='</li>';
	                        $(".News_system").append(html);
	                	}
	                	
				}else{
					verdict=false;
		    	  	if(page==1){
						$(".qsy1").show(); 
					}else{
						$(".qsy1").hide();
					}
	    	  }
	                updateNew(2);
	                
			}
	     }
	});
}


/**
 * 待办消息
 * @param page
 */
function wait(page){
	$(".qsy1").hide();
	var data = {"sendId":sendId,"nType":1,"pageNum":page}
	 $.ajax({
		   type : "get",
	       url:dataLink+"news/getNewsByUserid", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
		    	   	if(page==1){
		    	   		$(".News_remind").html("");
		    	   	}
	                if(!isnull(json.newsList)&&json.newsList.length>0){
	                	for(i=0;i<json.newsList.length;i++){
	                		var item = json.newsList[i];
		                	var	html = '<li class="smallshdow">';
		                		html +='<div class="remind_name"><div></div>';
		                	
		                		html +='<div class="remind_name_right">'+item.nTitle+'</div></div>';
		                		html +='<div class="remind_font"><p class="remind_status">'+item.nContent+'</p></div>';
		                		html +='<p class="News_time">'+item.nTimeStr+'</p>';
								html +='</li>';
	                        $(".News_remind").append(html);
	                	}
	                	
				}else{
					verdict=false;
		    	  	if(page==1){
						$(".qsy2").show(); 
					}else{
						$(".qsy2").hide();
					}
	    	  }
	                updateNew(1);
	                
			}
	     }
	    });
}

function updateNew(nType){
	var data = {"sendId":sendId,"nType":nType,"isRed":1}
	 $.ajax({
		   type : "post",
	       url:dataLink+"news/updateNew", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    		   getNewsCount();
	      }
	    }
	});
}
$(function(){
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
})

