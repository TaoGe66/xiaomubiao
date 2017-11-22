var validate_code = "";
var overtime =true;
//openId
var oId = localStorage.getItem("team_wxOid");
$(function(){
	$(".Login_alert").hide();
	if(navigator.userAgent.indexOf("MicroMessenger")!==-1){
		var is_exit = sessionStorage.getItem("team_is_exit");
		if(isnull(oId)){
			if(isnull(is_exit)){
				window.location.href=skipHttp+"wxlogin.html";
			}
		}
	}
	
	//点击确认按钮
	$(".Login_three").click(function(){
		var tel=$("#tel").val();
		if(verificationPhone()){
		    loginNext(tel);
		}
	});
	
	//离焦事件
	window.onload=function(){
		var sMobile=document.getElementById('tel');
			sMobile.onblur=function(){
			yanzheng(sMobile,"手机号码","请输入手机号")
			};
	};
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
	
});

//点击确认按钮
function loginNext(tel){
	var isCode = false;
	if(verificationPhone()){
		isCode =  verificationCode();
	}
	if(isCode){
	var data={"tel":tel,"oId":oId};
		$.ajax({
			url:dataLink+"userwx/webLogin", 
			type : "get",
			dataType: 'json',
			data:data, 
			success : function(date){
				if(date.msg=="success"){
					localStorage.setItem("team_userId",date.user.userId);
					localStorage.setItem("team_userTel",date.user.tel);
					localStorage.setItem("team_userName",date.user.userName);
					localStorage.setItem("team_userUrl",date.user.headUrl);
					localStorage.setItem("team_note",date.user.note);
					localStorage.setItem("team_is_login","1");
					if(!isnull(date.user.leId) && date.user.leId !=0){
						localStorage.setItem("team_leId",date.user.leId);
					}
					var now_href = localStorage.getItem("team_href");
					if(now_href!=null&&now_href!="null"&&now_href!=undefined&&now_href!="undefined"&&now_href!=""&&now_href!="/team/"){
						localStorage.setItem("team_href","");
						window.location.href= skipHttp+now_href;
					}else{
						window.location.href=skipHttp+"index.html";
					}
				}else{
					localStorage.setItem("team_is_login","0");
					alert("注册失败!");
				};
			},
		}); 
    }
}


//获取验证码
function getValidated(){
	var tel=$("#tel").val();
	if (verificationPhone()) {
		//取消计时器
		clearInterval(times);
		var i;
		var times=null;
		startMove();
		function startMove(){
			clearInterval(times);
			var i=60;
			times=setInterval(function(){
				$(".Relev_obtain").show();
				$(".Relev_obtaintwo").css("background","#AEAFB1")
				$("#codeget").hide();
				if(i<1){
					$(".Relev_obtain").hide();
					$("#codeget").show();
					$("#codeget").html("验证码")
					clearInterval(times);
				}else{
					$(".Relev_obtain").html(i+"S后重试")
				};
				i-=1;
			},1000)
		};
		var data={"tel":tel};
		$.ajax({
	       url:dataLink+"userwx/getValidCode", 
	       type : "get",
	       dataType: 'json',
	       data:data, 
	       success : function(json){
	  	    if(json.msg=="success"){
	       		validate_code=json.phoneCode;
	       	}else{
	       		startMove();
	       	};
	       },
	 	}); 
	}
}


function verificationPhone(){
	var tel=$("#tel").val();
	tel = trimStr(tel);
	if (isnull(tel)) {
		$(".Login_alert_font").html("手机号不能为空");
		$(".Login_alert").show();
   		setTimeout(function(){
   			$(".Login_alert").hide();
   		},2000);
		return false;
	}else if(!mobile(tel)){
		$(".Login_alert_font").html("请输入正确手机号");
		$(".Login_alert").show();
   		setTimeout(function(){
   			$(".Login_alert").hide();
   		},2000);
		$("#tel").val("");
		return false;
	}
	$(".Login_alert").hide();
	return true;
}

function verificationCode(){
	var code=$("#code").val();
	code = trimStr(code);
	if(isnull(code)){
		$(".Login_alert_font").html("请输入验证码");
   		$(".Login_alert").show();
   		setTimeout(function(){
   			$(".Login_alert").hide();
   		},2000);
		return false;
	}else{
		vaCode();
		if(overtime){
			if(validate_code!=code){
			$(".Login_alert_font").html("输入验证码错误");
	   		$(".Login_alert").show();
	   		setTimeout(function(){
	   			$(".Login_alert").hide();
	   		},2000);
				return false;
			}
		}else{
			$(".Login_alert_font").html("超时！请重新输入");
		   	$(".Login_alert").show();
	   		setTimeout(function(){
	   			$(".Login_alert").hide();
	   		},2000);
	     	return false;
		}
		
	}
	$(".Login_alert").hide();
	return true;
}

function vaCode(){
	var tel=$("#tel").val();
	var data={"tel":tel};
	$.ajax({
       url:dataLink+"userwx/getCode", 
       type : "get",
       async: false,
       dataType: 'json',
       data:data, 
       success : function(json){
  	    if(json.msg=="success"){
  	    	overtime =true;
  	    	validate_code=json.code.validated;
       	}else if(json.msg=="overtime"){
       		overtime =false;
       	}
       },
 	}); 
}

