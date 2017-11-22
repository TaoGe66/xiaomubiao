<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript" charset="UTF-8" src="https://back.qingzhihui.cn/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
var dataLink = "https://back.qingzhihui.cn/team/";

function onloadMessage(){
	var userTel = $('#user_tel').val();
	var userId = $('#user_id').val();
	var userNice = $('#user_nice').val();
	var userHeadUrl = $('#user_headUrl').val();
	//1:已注册跳转首页  2:未注册跳转关联手机号
    var login_state = $('#login_state').val();
	var wxOid = $('#wx_oid').val();
	//1:pc端扫码登录 跳转页面  2 移动端登录
	var login_page = $('#login_page').val();
	if(login_page=="1"){
		window.location.href= dataLink+"task.html";
	}else {
		localStorage.setItem("team_userId",userId);
		localStorage.setItem("team_is_login",1);
		localStorage.setItem("team_userTel",userTel);
		localStorage.setItem("team_userName",userNice);
		localStorage.setItem("team_wxOid",wxOid);   
		localStorage.setItem("team_loginState",login_state);
		localStorage.setItem("team_userHeadUrl",userHeadUrl);
		if(userTel==""&&login_state=="2"){
			window.location.href= dataLink+"relationmobile.html";
		}else{
			var now_href = localStorage.getItem("team_href");
			if(now_href!=null&&now_href!="null"&&now_href!=undefined&&now_href!="undefined"&&now_href!=""){
				localStorage.setItem("team_href","");
				window.location.href= dataLink+now_href;
			}else{
				window.location.href=dataLink+"index.html";
			}
			
			
		}
	}
}
</script>
</head>
<body>

<body onload="onloadMessage()">
    <input id="user_tel" type="hidden" value="${tel}" />
    <input id="user_id" type="hidden" value="${userId}" />
    <input id="user_nice" type="hidden" value="${nice}" />
    <input id="user_headUrl" type="hidden" value="${headUrl}" />
    <input id="wx_oid" type="hidden" value="${oId}" />
    <input id="login_state" type="hidden" value="${login_state}" />
    <input id="login_page" type="hidden" value="${login_page}" />
</body>
</html>