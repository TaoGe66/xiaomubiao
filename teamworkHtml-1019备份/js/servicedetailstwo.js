$(function(){
	$(".Servicetwo_election").click(function(){
		$(".Servicetwo_head").css("display","none");
		$(".Servicetwo_con").css("display","none");
		$(".Servicetwo_footer").css("display","none");
		$(".Explain_con").css("display","block");
	})
	$(".Explain_con footer").click(function(){
		$(".Servicetwo_head").css("display","block");
		$(".Servicetwo_con").css("display","block");
		$(".Servicetwo_footer").css("display","block");
		$(".Explain_con").css("display","none");
	})
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
})
