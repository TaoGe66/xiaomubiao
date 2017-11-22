$(function(){
	//返回首页
	$(".notes_left").click(function(){
		window.location.href="index.html";
	});
//	点击添加
	$(".notes_add").click(function(){
		window.location.href="action.html";
	});
	//点击灰色区域
	$(".event_summary").click(function(){
		$(".event_summary").hide();
		$(".notes_overlook").hide();
	});
	//	点击弹窗取消按钮
	$(".notes_overlook_btnone").on("click",function(){
		$(".event_summary").hide();
		$(".notes_overlook").hide();
	});
//	点击弹窗确定
	$(".notes_overlook_btntwo").on("click",function(){
		$(".event_summary").hide();
		$(".notes_overlook").hide();
		
	});
	//点击删除
	$(".notes_delete").click(function(){
		$(this).hide();
		$(".notes_add").hide();
		$(".notes_btn").css("display"," -webkit-flex");
		$(".Part_listcon").addClass("Part_listcon_del");
		
	});
	
	//点击取消
	$(".btn_no").click(function(){
		$(".notes_btn").hide();
		$(".notes_delete").show();
		$(".notes_add").show();
		$(".note_edit").show();
		$(".Part_listcon").removeClass("Part_listcon_del");
	});
	//选择切换
	$(".Part_listcon em").click(function(){
		$(this).toggleClass("Part_listcon_del_current");
	});
	
	//点击确定删除
	$(".btn_yes").click(function(){
		if($(".Part_listcon em").hasClass("Part_listcon_del_current")){
			
			$(".event_summary").show();
			$(".notes_overlook").show();
		}else{
			$(".event_summary").hide();
			$(".notes_overlook").hide();
		}
		
	});
//	点击弹窗取消
	$(".notes_overlook_btnone").click(function(){
		$(".event_summary").hide();
		$(".notes_overlook").hide();
	});
	
				
})





//编辑内容
$(".notes_jump").click(function(){
	var  memo_id=$(this).find("em").html();
	localStorage.setItem("team_memoId",memo_id);
	window.location.href="add_notes.html";
	
});

//确定删除随手记
$(".notes_overlook_btntwo").click(function(){
	update_memo();
});


