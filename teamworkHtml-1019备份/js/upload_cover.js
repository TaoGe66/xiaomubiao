$(function(){
	$("body").height($(window).height());
});
var leId = localStorage.getItem("team_leId");
if(!isnull(leId)){
	$("title").html("轻企需求详情");
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
				  alert("图片大小超过了4M,不能上传");
			  }
		},
_uploadOk:function(state,errorCode,saveUrl){
	
	_upload._clearFile("post_party_content_upload_file");""
	 if(state==1){
		    _partyLogo=saveUrl;
	 		$(".upload img").attr("src",imageHeadSrc+saveUrl);
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

	
	
	
	
	
	//保存
	$(".keep").click(function(){
		_partyLogo=localStorage.getItem("team_partyLogo");
		if (!isnull(_partyLogo)) {
			save_erve_event();
		}else{
			$(".fm_ts").show();
			setTimeout(function(){$(".fm_ts").hide();},2000);
			return false;
		}
	});	
	
	
	
	//保存服务请求事件
	function save_erve_event(){
		var eId=localStorage.getItem("team_eId");
		var createBy=localStorage.getItem("team_userId");
		var Json='"eId":"'
			+eId
			+'","userId":"'
			+createBy
			+'","createBy":"'
			+createBy
			+'","isValid":"'
			+0
			+'","state":"'
			+0
			+'","logo":"'
			+_partyLogo
			+'","type":"'
			+1
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
		   		localStorage.setItem("team_physics",1);
		   		localStorage.removeItem("team_partyLogo");
		 		window.location.href="release1.html";
		   	  }
		     },  
		     timeout:3000  
		});
		
	}