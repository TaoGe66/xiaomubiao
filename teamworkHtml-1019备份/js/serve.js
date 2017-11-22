$(function(){
	$(".Myqr_img img").height($(".Myqr_img img").width());
	go_shore();
})

var _shore={
		_vip_member:function(shoreImg,shoreContent){
			var shoreLink = skipHttp+"serve.html";
			_shore._show(shoreImg,shoreLink,shoreContent);
		},
		_show:function(shoreImg,shoreLink,shoreContent){
			var shoreTitle ="公众号的名片";
			_wx._shore(shoreTitle,shoreContent,shoreLink,shoreImg);
		}
	}



	function go_shore(){
		var pageName = "serve";
		var urlDate = window.location.search; 
		_wx._getWxConfig(pageName,urlDate);
		var shoreContent ="醒客汇的二维码";
		var shoreImg = skipHttp+"img/icon18.png";
		_shore._vip_member(shoreImg,shoreContent);
		
	}


//返回首页
$(".return_index").click(function(){
	window.location.href="index.html";
});