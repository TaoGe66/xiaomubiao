$(function(){
	//拒绝
	$(".Newfriend_btn_no").click(function(){
		$(".modalShow").show();
	});
	//同意
	$(".Newfriend_btn_ok").click(function(){
		$(".memberPublicPopups").fadeIn().delay(3000).fadeOut();
	});
	//拒绝模态框取消
	$(".mem_false").click(function(){
		$(".modalShow").hide();
	});
	//拒绝模态框确认
	$(".mem_yes").click(function(){
		$(".modalShow").hide();
	});
});