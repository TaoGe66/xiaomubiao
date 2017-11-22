//选择
$(function(){
	//取消返回上一级
	$(".Select_btn_cancel").click(function(){
		window.location.href="serverelease.html";
	});
	//点击灰色区域
	$(".event_summary").click(function(){
		$(".event_summary").hide();
		$(".notes_overlook").hide();
	});
	//点击确定显示弹窗
	$(".Select_btn_confirm").click(function(){
		if($("em").hasClass('deleteparson_current')){
			$(".event_summary").show();
			$(".notes_overlook").show();
		}else{
			$(".event_summary").hide();
			$(".notes_overlook").hide();
		}
	});
	//	点击弹窗取消按钮
	$(".notes_overlook_btnone").on("click",function(){
		$(".event_summary").hide();
		$(".notes_overlook").hide();
	});
	
});

user_list();

//用户列表
function user_list() {
	var eventPartake=localStorage.getItem("team_eventPartake");
	var data = {"userIdStr":eventPartake}
	$.ajax({
		type : "get",
		url : dataLink + "userwx/partakeEvent",
		data : data,
		dataType : "json",
		success : function(json) {
			if (json.msg = "success") {
				var list = json.list;
				if (list.length>0) {
					for (var i = 0; i < list.length; i++) {
						var item=list[i];
						var	headUrl=isnull(item.headUrl)?"img/img03.png":imageHeadSrc+item.headUrl;
						var html ='<li><em class="deleteparson_currenthui">';
							html +='<i style="display:none;">'+item.userId+'</i>';	
							html +='</em><div class="deleteparson_list">';
							html +='<img src="'+headUrl+'" alt="" />';
							html +='<span>'+item.userName+'</span></div></li>';
						$(".deleteparson_ul").append(html);
					}
				}
			}
			//多选
			$("li>em").attr("flage","true");
			$(".deleteparson_ul").on("click","li>em",function(){
				if($(this).attr("flage") == "true"){
					$(this).attr("flage","false");
					$(this).addClass("deleteparson_current").removeClass("deleteparson_currenthui");
					if($("em").hasClass('deleteparson_current')){
						$(".Select_btn_confirm").css("background","#6eabfe").addClass("notes_btn_ok");
					}
				}else{
					$(this).addClass("deleteparson_currenthui").removeClass("deleteparson_current");
					$(this).attr("flage","true");
					if(!$("em").hasClass('deleteparson_current')){
						$(".Select_btn_confirm").css("background","#cecece").removeClass("notes_btn_ok");
					}
				}
			});
		}
	});
}

//确认删除
$(".notes_overlook_btntwo").click(function(){
	var eventPartake = "";
	$(".deleteparson_currenthui").each(function() {
		var cycle = $(this).find("i").html();
		if (!isnull(eventPartake)) {
			eventPartake = eventPartake + "," + cycle;
		} else {
			eventPartake = cycle;
		}
	});
	localStorage.setItem("team_eventPartake", eventPartake);
	window.location.href = "serverelease.html";
});