  var div=document.querySelector('div');
   var item;//接受配置信息，后台给的
   go_sign();
   function go_sign(){
		var urlDate = window.location.search; 
		var pageName ="browse";
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
    
    //
    
    function isWeiXin(){
    	var ua = window.navigator.userAgent.toLowerCase();
    	if(ua.match(/MicroMessenger/i) == 'micromessenger'){
    	return true;
    	}else{
    	return false;
    	}
    	} 