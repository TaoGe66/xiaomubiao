//verificationUserLogin();
var userId = localStorage.getItem("team_userId");
var personal=localStorage.getItem("team_personal");
if (!isnull(personal)) {
	localStorage.removeItem("team_personal");
	getNews();
	getHailNews();
}
if (isWeiXin()) {
	$(".Personal_list_six").show();
}
$(function(){
//	edit by zhengjunting 2017/06/09  点击头部跳转个人资料页面
	$(".Personal_header_photo").click(function(){
		window.location.href="personalcenter.html";
	});
	$(".Personal_title").click(function(){
		window.location.href="personalcenter.html";
	});
//	edit by zhengjunting 2017/06/09  点击二维码跳转二维码页面
	$(".Personal_header .Personal_qr").click(function(){
		window.location.href="myqr.html";
	});
//	edit by zhengjunting 2017/06/09  点击消息跳转页面
	$(".Personal_list_two").click(function(){
		localStorage.setItem("team_personal",1);
		window.location.href="news.html";
	});
//	edit by zhengjunting 2017/06/09  点击事件跳转页面
	$(".Personal_list_four").click(function(){
		window.location.href="my_event.html";
	});
//	edit by zhengjunting 2017/06/09  我的好友跳转页面
	$(".Personal_list_three").click(function(){
		localStorage.setItem("team_personal",1);
		window.location.href="friends.html";
	});	
	//	edit by zhengjunting 2017/09/21  我的好友跳转页面
	$(".Personal_list_seven").click(function(){
		window.location.href="myqinqi.html";
	});	
	//首页跳转页面
	$(".Index_footer_btntwo").click(function(){
		window.location.href="index.html";
	});
	$(".Personal_list_five").click(function(){
		window.location.href="serve.html"
	})
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
	//扫一扫
	$(".Personal_list_six").click(function(){
		go_sign();
	});
	
	getUserInfo();
	getNews();
	getHailNews();
	userbin();
});

function getUserInfo(){
	
		
	var data = {"userId":userId}
	 $.ajax({
		   type : "get",
	       url:dataLink+"userwx/getUserInfoById", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	var userInfo = json.user;
	    	   	$(".Personal_title_font").html(userInfo.userName);
	    	   	$(".Personal_title_content").html(userInfo.note);
	    	   	var headUrl = isnull(userInfo.headUrl)? "img/img03.png" : imageHeadSrc+userInfo.headUrl;
	    	   	$(".Personal_header_photo img").attr("src",headUrl);
	    	   	
	    	   }
	    	  }
	    });
}

//事件消息
function getNews(){
	
	var data = {"sendId":userId,"nType":1,"isRed":0}
	 $.ajax({
		   type : "get",
	       url:dataLink+"news/getNewsByUserid", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
		    	   if(!isnull(json.newsList)&&json.newsList.length>0){
		    		$(".Personal_list_icon").show();
		    	   }
	    	   }
	       }
	  });
}

//好友消息
function getHailNews(){
	
	var data = {"sendId":userId,"nType":0,"isRed":0}
	 $.ajax({
		   type : "get",
	       url:dataLink+"news/getNewsByUserid", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
		    	   if(!isnull(json.newsList)&&json.newsList.length>0){
		    			$(".Personal_list_icontwo").show();
		    	   }
	    	   }
	       }
	  });
}

function userbin(){
	var data = {"userId":userId};
	 $.ajax({
		   type : "get",
	       url:dataLink+"userwx/userbin", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
		    	   	if(json.scrsub==1){
		    	   		$(".Personal_five_inner span").hide();
		    	   	}else{
		    	   		$(".Personal_five_inner span").show();
		    	   	}
	    	   }
	       }
	  });
	
}

//扫一扫
function go_sign(){
	var urlDate = window.location.search; 
	var pageName ="personal";
	var data=JSON.stringify({"requestPage":pageName,"pageMessage":urlDate});
	$.ajax({
		url:dataLink+"wxtool/wx_parameter",  
        type: 'POST',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: data
    }).done(function(data, status, xhr) {
	   if(data.msg=="success"){
	    	scan(data);
	   }
	}); 
}

function  scan(data){
    wx.config({
        debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        //                                debug : true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId : data.appId, // 必填，公众号的唯一标识
        timestamp : data.timeStamp, // 必填，生成签名的时间戳
        nonceStr : data.nonceStr, // 必填，生成签名的随机串
        signature : data.signature,// 必填，签名，见附录1
        jsApiList : ['checkJsApi', 'scanQRCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });
    wx.ready(function() {
       wx.scanQRCode({   
            needResult:1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
            scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
            success: function (res) {
            var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
            _g(result);
            }
        });
    });
}