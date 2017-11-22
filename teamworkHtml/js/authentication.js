var front_logo="";//正面身份证照片
var con_logo="";//反面身份证照片
var leId=localStorage.getItem("team_leId");
var userId=localStorage.getItem("team_userId");
//离焦事件
//window.onload=function(){
//	var sMobile=document.getElementById('tel');
//		sMobile.onblur=function(){
//		
//		};
//};
function yanzheng(obj,name1,name2){
	 if(!mobile(obj.value)){ 
	 	obj.value="";
        if(obj.placeholder==name1){
        	obj.placeholder=name2;
        }else{
	        obj.value="";
        };
	}; 
};	
//	实时监控事件
	 $(".Auth_con").bind('DOMNodeInserted', function(e){
 		 if(isnull($("#tel").val())||isnull($(".Auth_stratenumber").val())||isnull($(".Attest_photo_logonull").html())||isnull($(".Attest_photo_null").html())){
 		 	$(".Auth_footer_null").show();
 		 	$(".Auth_footer").hide();
 		 }else{
 		 	$(".Auth_footer_null").hide();
 		 	$(".Auth_footer").show();
 		 }
	});

echo();
// 回显
function  echo(){
	var data={"leId":leId}
	$.ajax({
		  type : "GET",
	      url:dataLink+"auth/infoAuth",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  localStorage.removeItem("team_authId");
	    		  var item = json.item;
	    		  if (!isnull(item.id)) {
	    			  $(".Auth_footer_null").hide();
	    	 		 	$(".Auth_footer").show();
	    			  $("#tel").val(item.phone);
	    			  $(".Auth_stratenumber").val(item.identity);
	    			   front_logo=item.identityLogoFront;//正面身份证照片
	    			   con_logo=item.identityLogoBack;//反面身份证照片
	    			  $(".Attest_photo_logo").attr("src",front_logo);//正面
	    			  $(".Attest_url_logo").attr("src",con_logo);//反面
	    			  if (item.isPay==0) {
	    				  localStorage.setItem("team_authId",item.id);
	    			  }
					if (item.type==0 && item.isPay==0) {
						localStorage.setItem("team_authType",0);
						//认证 未支付
						$("title").html("认证");
					}else{
						//年审
						$("title").html("年审");
						 localStorage.setItem("team_authType",1);
						$("#tel").attr("readOnly",true);
						$(".Auth_stratenumber").attr("readOnly",true);
					}
				}else{
					//认证
					localStorage.setItem("team_authType",0);
				}
	    	  }
	      },  
	      timeout:3000  
	 }); 
}

/**
 * 下一步
 */
$(".Auth_footer").click(function() {
	var isNextStep=true;
	var id = localStorage.getItem("team_authId");
	var phone = $("#tel").val();
	var identity = $(".Auth_stratenumber").val();
	if (isnull(phone)) {
		isNextStep=false;
		$(".Auth_alert").show();
		setTimeout(function() {
			$(".Auth_alert").hide();
		}, 3000);
		return false;
	}
	if (!isnull(phone) && !Regexs.allphpne.test(phone)) {
		isNextStep=false;
		$(".Auth_alert").show();
		setTimeout(function() {
			$(".Auth_alert").hide();
		}, 3000);
		return false;
	}
	if (isnull(identity)) {
		isNextStep=false;
		$(".Auth_alert_two").show();
		setTimeout(function() {
			$(".Auth_alert_two").hide();
		}, 3000);
		return false;
	}
	if (!isnull(identity) && !Regexs.idCard.test(identity)) {
		isNextStep=false;
		$(".Auth_alert_two").show();
		setTimeout(function() {
			$(".Auth_alert_two").hide();
		}, 3000);
		return false;
	}
	if (isNextStep) {
		if (isnull(id)) {
			save_auth();
		} else {
			update_auth();
		}
	}
});

/**
 * add by wuchao 20170913 添加认证信息
 */
function save_auth(){
	var phone=$("#tel").val();
	var identity=$(".Auth_stratenumber").val();
	var authType=localStorage.getItem("team_authType");
	$(".event_summary").show();
	$(".event_summary1").show();
	
	var Json='"leId":"'
		+leId
		+'","phone":"'
		+phone
		+'","identity":"'
		+identity
		+'","identityLogoFront":"'
		+front_logo
		+'","identityLogoBack":"'
		+con_logo
		+'","createBy":"'
		+userId
		+'","type":"'
		+authType
		+'","isPay":"'
		+0
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	var data='{'+start+'}';
	$.ajax({
		  url:dataLink+"auth/saveAuth",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     contentType: "application/json; charset=utf-8",
	     success:function(result){
	   	  if(result.msg=="success"){
	   		localStorage.removeItem("team_authType");
	   		localStorage.setItem("team_authId",result.item.id);
	   		window.location.href="payment.html";
	   	  }
	     },  
	     timeout:3000  
	});
	
}


/**
 * add by wuchao 20170913 修改认证信息
 */
function update_auth(){
	var phone=$("#tel").val();
	var identity=$(".Auth_stratenumber").val();
	var authType=localStorage.getItem("team_authType");
	var id=localStorage.getItem("team_authId");
	$(".event_summary").show();
	$(".event_summary1").show();

	var Json='"leId":"'
		+leId
		+'","phone":"'
		+phone
		+'","identity":"'
		+identity
		+'","identityLogoFront":"'
		+front_logo
		+'","identityLogoBack":"'
		+con_logo
		+'","id":"'
		+id
		+'","type":"'
		+authType
		+'","isPay":"'
		+0
		+'"';
	 Json = Json.replace(/null/gm,'')
	 Json = Json.replace(/"null"/gm,"")
	 start = Json.replace(/undefined/gm,"")
	 start = start.replace(/"undefined"/gm,"")
	 
	var data='{'+start+'}';
	$.ajax({
		  url:dataLink+"auth/updateAuth",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     contentType: "application/json; charset=utf-8",
	     success:function(result){
	   	  if(result.msg=="success"){
	   		localStorage.removeItem("team_authType");
	   		window.location.href="payment.html";
	   	  }
	     },  
	     timeout:3000  
	});
	
}



//图片上传
var objThis="";
var _richText={
	_upload:function(obj){
		objThis=obj;
		var _form=$(obj).parent().parent().parent().find("form").attr("id");;
		$("#"+_form).attr("action",dataLink+"watermar_image");	//赋予form表单上传地址
		var len = obj.files.length
		var text="";
	    for (var i =0 ; i < len ; i++){
	            text +=obj.files[i].size/1024/1024;
	    }
	    var _file_size=decimal(text,2);
	    var _val=$(obj).parent().find(".post_party_content_upload_file").val();
		//校验图片是否符合格式
		if(_upload._limit(_val)){//得到本地图片路径
			alert("只能选择图片文件");
			return;
		}
		  if(_file_size<=4){
			  $("#"+_form).submit();
		  }else{
			  alert("图片大小超过了4M,不能上传");
		  }
	},
	_uploadOk:function(state,errorCode,saveUrl){
		_upload._clearFile(objThis);
	    if(state==1){
	    	//front_logo=saveUrl;
	    	$(objThis).parent().parent().parent().find("img").attr("src",imageHeadSrc+saveUrl);
	    	front_logo=$(".Attest_photo_logo").attr("src");
	    	con_logo=$(".Attest_url_logo").attr("src");
	    	if (con_logo!="img/all09.png") {
	    		$(".Attest_photo_null").html(con_logo);
			}
	    	if (front_logo !="img/all08.png") {
	    		$(".Attest_photo_logonull").html(front_logo);
			}
	    	
	    }
	 }
};
//校验图片上传类型
var _upload={
	_limit:function(path){
		return path.match(/\.(gif|jpg|jpeg|png)$/i)==null;
		_upload._clearFile(objThis);
		return;
	},
	_clearFile: function(o) {
	var _id = $(o).attr("id");
	var _type = $("#" + _id + "_type").val();
	$(o).html("");
	$(o).html("<input  id=\"" + _id + "_file\" type=\"file\" onchange=\"_richText._upload()\">");
	},
	_ok:null
};	




