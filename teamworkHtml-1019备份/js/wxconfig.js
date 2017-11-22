//微信分享朋友圈,发给朋友
var _wx = {
	_getWxConfig:function(pageName,urlDate){

		var data=JSON.stringify({"requestPage":pageName,"pageMessage":urlDate});
		$.ajax({
			url:dataLink+"wxtool/wx_parameter",  
	        type: 'POST',
	        dataType: 'json',
	        contentType: "application/json; charset=utf-8",
	        data: data
	    }).done(function(data, status, xhr) {
		   if(data.msg=="success"){
				var signature=data.signature;
				var timeStamp=data.timeStamp;
				var nonceStr=data.nonceStr;
				var appId=data.appId;
		    	_wx._config(appId,timeStamp,nonceStr,signature);
		   }
		}); 
	},
	_config:function(appId,timeStamp,nonceStr,signature){
		wx.config({
		    debug: false,appId: appId,timestamp:timeStamp ,nonceStr: nonceStr,signature: signature,
		    jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'] 
		}); 
	},
	_shore:function(shoreTitle,shoreContent,shoreLink,shoreImg){
		wx.ready(function(){
			//分享到朋友圈
			wx.onMenuShareTimeline({title: shoreContent,link: shoreLink,imgUrl: shoreImg,success: function () {},cancel: function () {}});
			//分享给朋友
			wx.onMenuShareAppMessage({title: shoreTitle,link: shoreLink,desc: shoreContent,imgUrl: shoreImg, type: 'link',dataUrl: '',success: function () {},cancel: function () {}});
		});
	}

	
}
