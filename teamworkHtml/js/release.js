var physics=localStorage.getItem("team_physics");
//	if (physics !=1) {
//		window.location.href="index.html";
//	}	
$(function(){
	var Height =$(window).height()-$(".Relese_head").height()-$(".Relese_head_null").height();
	$("#editor").height(Height);
})
var leId = localStorage.getItem("team_leId");
if(!isnull(leId)){
	$("title").html("轻企活动详情");
}
		
var _partyLogo="";
	var _richText={
		_upload:function(obj){
			$("#post_party_content_upload").attr("action",dataLink+"post_image");	//赋予form表单上传地址
			var len = obj.files.length
			var text="";
		    for (var i =0 ; i < len ; i++){
		            text +=obj.files[i].size/1024/1024;
		    }
		    var _file_size=decimal(text,2);
		  
			//校验图片是否符合格式
			if(_upload._limit(document.getElementById("post_party_content_upload_file").value)){//得到本地图片路径
				alert("只能选择图片文件");
				return;
			}
			  if(_file_size<=4){
				  $("#post_party_content_upload").submit();
			  }else{
				  $(".fm_ts1").show();
					setTimeout(function(){$(".fm_ts1").hide();},2000);
			  }
		},
_uploadOk:function(state,errorCode,saveUrl){
	
	_upload._clearFile("post_party_content_upload_file");""
	 if(state==1){
		    _partyLogo=saveUrl;
	 		$("#editor").append('<img src="'+imageHeadSrc+saveUrl+'" alt="" /><div><br></div>');
	   }
	 }
};
//校验图片上传类型
var _upload={
	_limit:function(path){
		return path.match(/\.(gif|jpg|jpeg|png)$/i)==null;
		_upload._clearFile("post_party_content_upload_file");
		return;
	},
	_clearFile: function(o) {
    var _id = $(o).attr("id");
    var _type = $("#" + _id + "_type").val();
    $(o).html("");
    //<input id="post_party_content_upload_file" type="file" name="file" onchange="_richText._upload()">
    $(o).html("<input  id=\"" + _id + "_file\" type=\"file\" onchange=\"_richText._upload()\">");
  },
	_ok:null
};	  




$(".release").click(function(){
	var content=$("#editor").html();
	content=_trim(content);
	content=(content).replace(/"/g, '\'')//编译器的内容
	content = content.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
	if (!isnull(content)) {
		save_content();
	}
});


//保存内容
function save_content(){
	$(".event_summary").show();
	$(".event_summary1").show();
	var eId=localStorage.getItem("team_eId");
	var createBy=localStorage.getItem("team_userId");
	var content=$("#editor").html();
	content=_trim(content);
	content=(content).replace(/"/g, '\'')//编译器的内容
	
	content = content.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
	var Json='"eId":"'
		+eId
		+'","userId":"'
		+createBy
		+'","content":"'
		+content
		+'","createBy":"'
		+createBy
		+'","isValid":"'
		+1
		+'","state":"'
		+0
		+'","type":"'
		+2
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	var data='{'+start+'}';
	$.ajax({
		  url:dataLink+"event/updateEnvet",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     contentType: "application/json; charset=utf-8",
	     success:function(result){
	   	  if(result.msg=="success"){
	   		localStorage.removeItem("team_physics");
	   		window.location.href="sign_in.html";
	   	  }
	     },  
	     timeout:3000  
	});
	

}


 