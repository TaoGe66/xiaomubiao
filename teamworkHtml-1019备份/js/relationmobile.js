$(function(){
	//頭像
	var user_url=localStorage.getItem("team_userHeadUrl");
	//用戶名
	var user_name = localStorage.getItem("team_userName");
	if(!isnull(user_url)){
		$("#user_img").attr("src",imageHeadSrc+user_url);
	}else{
		$("#user_img").attr("src","img/img03.png");
	}
	if(!isnull(user_name)){
		$("#user_name").html(user_name);
	}else{
		$("#user_name").html("");
	}
	$(".zq_relation_Get").click(function(){//点击按钮事件
		window.location.href="login.html"; 
	})
})
function allbackground(isOne,mouget){
	$(isOne).css("height",$(window).height()+"px");
	if(mouget){
		$(isOne).css("width",$(window).width()+"px");
	};
}