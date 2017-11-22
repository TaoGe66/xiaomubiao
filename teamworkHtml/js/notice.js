var userId = localStorage.getItem("team_userId");
var leId = localStorage.getItem("team_leId");
$(".rdo").attr("flag","false");
$(function(){
	//复选切换$
	$(".rdo").click(function(){
		var flag = $(this).attr("flag");
			if(flag =="true"){
				$(this).addClass("yuan1").removeClass("yuan2");
				$(this).attr("flag","false");
				$(this).addClass("activetwo").removeClass("activeone");
			}else{
				$(this).addClass("yuan2").removeClass("yuan1");
				$(this).attr("flag","true");
				$(this).addClass("activeone").removeClass("activetwo");
			}
	});
	
	$("#nomore").click(function(){
        window.location.href="lightdetails.html";
        return false;
	});
	$(".notice_yes").click(function(){
		saveNoticeInfo();
	});
});


function saveNoticeInfo(){
	
	var title = $(".noticeTitle input").val();
	var content = $(".noticeContent textarea").val();
	if(centen(title,2,40)){
		if(centen(content,2,800)){
			var isurgent =0;
			if($(".rdo").hasClass("activeone")){
				isurgent =1;
			}
			 var data = {"title":title,"leId":leId,"content":content,"isUrgent":isurgent,"createBy":userId};
			 $.ajax({
				   type : "post",
			       url:dataLink+"notice/saveNoticeInfo", 
			       data:data, 
			       dataType : "json",
			       success : function(json){
			       	  if(json.msg="success"){
			       	  	    $(".noticePopups p").html("发布成功");
							$(".noticePopups").show();
							setTimeout(function(){
								$(".noticePopups").hide();
								window.location.href="dynamic.html";
							},2000);
			       	  }
			       }
			    });
		}else{
			$(".noticePopups p").html("内容最多400个汉字");
			$(".noticePopups").show();
			setTimeout(function(){
				$(".noticePopups").hide();
			},2000);
		}
	}else{
		$(".noticePopups p").html("标题最多20个汉字");
		$(".noticePopups").show();
		setTimeout(function(){
			$(".noticePopups").hide();
		},2000);
	}
	
}
