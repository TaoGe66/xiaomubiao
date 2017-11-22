var userId = isnull($.getUrlParam('friendkey')) ?  localStorage.getItem("team_userId") : $.getUrlParam('friendkey');
var titleNmae = isnull($.getUrlParam('friendkey')) ? '我的二维码' : '二维码名片';

$(function(){
	$("title").html(titleNmae); 
	getUserInfo();
//	$(".Myqr_img img").height($(".Myqr_img img").width());
	
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
})
var userName ='';
var qrcodeUrl ='';
function getUserInfo(){
	
		
	var data = {"userId":userId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"userwx/getUserInfoById", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	 var userInfo = json.user;
	    	   	 userName = userInfo.userName;
	    	   	 qrcodeUrl = imageSrc+userInfo.qrcodeUrl;
	    	     $(".Myqr_img img").attr("src",imageSrc+userInfo.qrcodeUrl);
	    	     go_shore();
	    	   }
	    	  }
	    });
}



var _shore={
		_vip_member:function(shoreImg,shoreContent){
			var shoreLink = skipHttp+"myqr.html?friendkey="+userId;
			_shore._show(shoreImg,shoreLink,shoreContent);
		},
		_show:function(shoreImg,shoreLink,shoreContent){
			var shoreTitle ="二维码名片";
			_wx._shore(shoreTitle,shoreContent,shoreLink,shoreImg);
		}
	}



function go_shore(){
	var pageName = "myqr";
	var urlDate = window.location.search; 
	_wx._getWxConfig(pageName,urlDate);
	var shoreContent =userName+"邀请你添加好友";
	var shoreImg = qrcodeUrl;
	_shore._vip_member(shoreImg,shoreContent);
	
}
