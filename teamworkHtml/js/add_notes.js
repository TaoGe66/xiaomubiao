$(function(){
	//返回首页
	$(".notes_left").click(function(){
		window.location.href="index.html";
	});
	//返回上一级
	$(".notes_right").click(function(){
		var content=$(".memo_content").val();
		content=_trim(content);
		if (!isnull(content)) {
			save_memo();
		}else{
			window.location.href="notes.html";
		}
	});
});
var memoId=localStorage.getItem("team_memoId");
if (!isnull(memoId)) {
	memo_info();
}

//保存随手记
function save_memo(){
	var userId = localStorage.getItem("team_userId");
	var content=$(".memo_content").val();
	var data={"userId":userId,"content":content,"id":memoId}
	$.ajax({
		  url:dataLink+"memo/saveMemoList",  
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



//随手记列表
function memo_info() {
	var data = {"id":memoId}
	$.ajax({
		type : "get",
		url : dataLink + "memo/infoMemoList",
		data : data,
		dataType : "json",
		success : function(json) {
			if (json.msg = "success") {
				var item = json.item;
				$(".memo_content").val(item.content);
			}
		}
	});
}