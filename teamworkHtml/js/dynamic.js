	/*弹出创建事件页面*/
//	var fleg=0;
//	var lis=$(".add ul li");
	$("#btn_add").on("click",function(e){
		if($(this).attr("class") == "disabled"){
			
		}
		else{
			if(verificationUserLogin()){
				$(".add").css("opacity",1);
				$(".add1").css("opacity",1);
				$(".add").css("display","block");
				$(".add1").css("display","block");
				$("#li1").css("display","block");
				$("#li2").css("display","block");
//				$("#li3").css("display","block");
				$("#li1").animate({"opacity":1,"top":"61%"},500);
//				$("#li2").animate({"opacity":1,"top":"61%"},500);
//				$("#li3").animate({"opacity":1,"top":"61%"},500,function(){
//					$("#btn-close").css("display","block");
//					$("#btn_add").addClass("disabled");
//				});
				$("#li2").animate({"opacity":1,"top":"61%"},500,function(){
					$("#btn-close").css("display","block");
					$("#btn_add").addClass("disabled");
				});
			}	
		}	
	});
	/*点击关闭回到首页*/
	$("#btn-close").on("click",function(e){
		$("#btn-close").css("display","none");
		$("#li1").animate({"top":"71%","opacity":0},300);
		$("#li2").animate({"top":"71%","opacity":0},300);
		$("#li3").animate({"top":"71%","opacity":0},300);
		$(".add").animate({"opacity":0},400);
		$(".add1").animate({"opacity":0},400,function(){
			$(".add").css("display","none");
			$(".add1").css("display","none");
			$("#btn_add").removeClass("disabled");
			$("#li1").css("display","none");
			$("#li2").css("display","none");
			$("#li3").css("display","none");
		});
	});

//add by xiehuilin 20170928 通知更多跳转
$(".Dynamic_notice").on("click",".Dynamic_notice_btntwo",function(){
	_g("announcementList.html");
});
//add by zhanghaitao 2017/10/26 通知跳转相应详情
$(".Dynamic_notice_list").on("click",".Dynamic_details",function(){
	window.location.href = "announcementList.html"
})
