$(function(){
//	edit by zhengjunting 2017/06/12 点击新好友跳转
	$(".Select_new").click(function(){
		window.location.href="newfriend.html";
	});
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
	$(".Select_global li").click(function(){
		$(".Select_ul em").removeClass("Select_global_current");
		$(this).find("em").addClass("Select_global_current");
	});
//	edit by zhengjunting 2017/06/12  点击分组
	$(".Select_global p").click(function(){
		$(this).toggleClass(".Select_current");
		if($(this).hasClass("Select_current")){
			$(this).next().hide();
			$(this).removeClass("Select_current").css({"border-bottom":"1px solid #e5e5e5"});
			$(this).parent().css({"height":"3.167rem"});
		}else{
			$(this).next().show();
			$(this).addClass("Select_current").css({"border":"0 none"});
			$(this).parent().css({"height":"auto"});
		}
	})
});
