$(function(){
	//点击取消返回上一级
	$(".group_no").click(function(){
		window.location.href = "userdata.html";
	});
	//新建分组
	$("header").click(function(){
		$(".Userdata_remarks").show();
	});
	//点击灰色区域
	$(".Userdata_alert_bg").click(function(){
		$(".Userdata_remarks").hide();
	});
	//点击取消
	$(".Userdata_alert_btnno").click(function(){
		$(".Userdata_remarks").hide();
	});
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
	//add by zhanghaitao 2017/11/03      单选勾选
	$(".group_list").on("click",function(){
		$(this).children("img").show();
		$(this).siblings(".group_list").children("img").hide();
	});
});