var userId = localStorage.getItem("team_userId");
var enCount =0;
var isOpen = 0;
$(function(){
	enList();
	//	点击轻企首页头部跳转详情页面
	$(".Dynamic_header").click(function(){
		window.location.href="lightdetails.html";
	});
	//点击弹出切换轻企弹窗
	//add by zhanghaitao 2017/09/28 切换轻企居中弹窗
	$(".Dynamic_switch").click(function(){
		$(".popups_bg").fadeIn(500);  
        center($(".popups_content")); 
		return false;
	});
	$(".popups_bg").click(function(){
		$(this).hide();
		$(".popups_content").hide();
//		$("body").css("overflow","auto");
		isOpen = 0; 
	});
	//新建轻企跳转
	$(".add_list").click(function(){
		if(enCount<6){
			window.location.href="newqinqi.html";
		}else{
//			$(".tab_popups").hide();
			$(".popups_bg").hide(); 
			$(".popups_content").hide(); 
		    $("body").css("overflow","auto");
	 		$(".Dynamic_alert").show();
	 		setTimeout(function(){
				$(".Dynamic_alert").hide();
			},2000);
			isOpen = 0;
		}
		
	})
});

//add by zhanghaitao 2017/09/28 切换轻企居中弹窗
function center(obj) {  
    var screenWidth = $(window).width();
    var screenHeigth = $(window).height();
    var scollTop = $(document).scrollTop();
    var objLeft = (screenWidth - obj.width()) / 2;  
    var objTop = (screenHeigth - obj.height()) / 2;  
    obj.css({  
        "left":objLeft + "px",  
        "top":objTop + "px"  
    });  
    obj.fadeIn(500); 
//  $("body").css("overflow","hidden");
    isOpen = 1; 
    //当滚动条发生改变的时候  
    $(window).scroll(function() {  
//  	alert(1);
        if (isOpen == 1) {  
            //重新获取数据  
            var screenWidth = $(window).width();  
            var screenHeigth = $(window).height();  
            var scollTop = $(document).scrollTop();  
            var objLeft = (screenWidth - obj.width()) / 2;  
            var objTop = (screenHeigth - obj.height()) / 2;  
            obj.css({  
                "left":objLeft + "px",  
        		"top":objTop + "px"   
            });  
            obj.fadeIn(500);  
//          $("body").css("overflow","hidden");
        }  
    });  
}   

function enList(){
	$(".popups_content ul").html("");
	var data = {"userId":userId};
	 $.ajax({
		   type : "get",
	       url:dataLink+"enterprise/enterpriseMyList", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	  if(!isnull(json.enList) && json.enList.length>0){
	    	   	  	enCount =json.enList.length;
	    	   	  	for(i=0;i<json.enList.length;i++){
	    	   	  		var item = json.enList[i];
	    	   	  		var html  ='<li class="popups_list" id="'+item.leId+'">';
	    	   	  		    html +='<img src="'+imageHeadSrc+item.leLogo+'" alt="" class="tab_popups_avatar"/>';
	    	   	  		    html +='<span>'+item.name+'</span>';
	    	   	  		    if(item.type==1){
	    	   	  		    	html +='<img src="img/firm01_06.png" alt="" class="tab_pass_icon"/>';
	    	   	  		    }
	    	   	  		    html +='</li>';
	    	   	  		$(".popups_content ul").append(html);
	    	   	  	}
	    	   	  	$(".popups_list").click(function(){
	    	   	  		var leId =$(this).attr("id");
	    	   	  		var dataa ={"userId":userId,"leId":leId};
	    	   	  		localStorage.setItem("team_leId",leId);
	    	   	  		updateUserInfo(dataa);
	    	   	  	});
	    	   	  }
	    	   $(".tab_popups").show();
//		       $("body").css("overflow","hidden");
	    	   }
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