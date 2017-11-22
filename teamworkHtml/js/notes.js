$(function(){
	memo_list();
	//返回首页
	$(".notes_left").click(function(){
		window.location.href="index.html";
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
	//点击新建随手记
	$(".note_edit").click(function(){
		localStorage.removeItem("team_memoId");
		window.location.href="add_notes.html";
	});
	
	
	//点击删除
	$(".btn_yes").click(function(){
		if($(this).hasClass("notes_btn_ok")){
			$(".event_summary").show();
			$(".notes_overlook").show();
		}
		if(!$(this).hasClass("notes_btn_ok")){
			$(".event_summary").hide();
			$(".notes_overlook").hide();
		}
	});
})



//随手记列表
function memo_list() {
	var userId = localStorage.getItem("team_userId");
	var data = {"userId":userId}
	$.ajax({
		type : "get",
		url : dataLink + "memo/listMemoList",
		data : data,
		dataType : "json",
		success : function(json) {
			if (json.msg = "success") {
				var list = json.list;
				if (list.length>0) {
					$(".notes_delete").show();
					for (var i = 0; i < list.length; i++) {
						var item=list[i];
						var html ='<li class="notes_list notes_jump">';
							html +='<em style="display:none;">'+item.id+'</em>';
							html +='<img src="img/radio.png" class="notes_select"/>';
							html +='<div class="notes_list_content">';
							html +='<p class="notes_title">'+item.content+'</p>';
							html +='<div class="notes_time">'+item.strTime+'</div>';
							html +='</div></li>';
							$(".notes_content").append(html);					
					}
				}else{
					$(".notes_delete").hide();
					alert("缺省页");
				}
				
				//点击多选
				$(".notes_select").attr("flage","true");
				$(".notes_content").on("click",".notes_select",function(){
					if($(this).attr("flage") == "true"){
						$(this).attr("flage","false");
						$(this).attr("src","img/img_ok_03.png");
						$(this).addClass("bbb");
						if($(".notes_select").hasClass('bbb')){
							$(".btn_yes").css("background","#6eabfe").addClass("notes_btn_ok");
						}
					}else{
						$(this).attr("src","img/radio.png");
						$(this).removeClass("bbb");
						$(this).attr("flage","true");
						if(!$(".notes_select").hasClass('bbb')){
							$(".btn_yes").css("background","#cecece").removeClass("notes_btn_ok");
						}
					}
					return false;
				});
				
				//点击编辑
				$(".notes_delete").click(function(){
					$(this).hide();
					$(".note_edit").hide();
					$(".notes_btn").show();
					$(".notes_select").show();
					$('.notes_jump').unbind("click");
					$("li").removeClass("notes_jump");
					
				});
				//点击取消
				$(".btn_no").click(function(){
					$(".notes_btn").hide();
					$(".notes_select").hide();
					$(".notes_delete").show();
					$(".note_edit").show();
					$("li").addClass("notes_jump");
				});
				//编辑内容
				$(".notes_jump").click(function(){
					var  memo_id=$(this).find("em").html();
					localStorage.setItem("team_memoId",memo_id);
					window.location.href="add_notes.html";
					
				});
			}
		}
	});
}

//确定删除随手记
$(".notes_overlook_btntwo").click(function(){
	update_memo();
});


//删除随手记
function update_memo(){
	var memoId="";
	$(".bbb").each(function(){
		var  cycle=$(this).parent().find("em").html();
		if (!isnull(memoId)) {
			memoId=memoId+","+cycle;
		}else{
			memoId=cycle;
		}
	});
	var data={"isValid":0,"memoIdStr":memoId}
	$.ajax({
		  url:dataLink+"memo/updateMemoList",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     success:function(result){
	   	  if(result.msg=="success"){
	   		window.location.href="notes.html";
	   	  }
	     },  
	     timeout:3000  
	});
}
