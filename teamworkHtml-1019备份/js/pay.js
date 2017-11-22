$(".content").click(function(){
	var order_no=parseInt(1000000*Math.random());
	var openId="o9nRWvyn8XU3JUHBFQbZEk64f3cI";
	go_pay._wx_pay(order_no,openId);
});

var appId = "";
var timeStamp = "";
var nonceStr = "";
var pk = "";
var signType ="";
var paySign = "";
var signature = "";
var go_pay={
	_wx_pay:function(order_no,openId){
		var this_url = "pay.html";
		var data={"openID":openId,"order_id":order_no,"thisUrl":this_url};
		$.ajax({//得到协助者信息
	        type : "get",
	        url:dataLink+"wxutil/order_submit",
	        data:data,
	        dataType : "json",
	        success : function(json){
	            if(json.msg=="success"){//成功的时候
	    			appId = json.appId;
	    			timeStamp = json.timeStamp;
	    			nonceStr = json.nonceStr;
	    			pk = json.packageValue;
	    			signType =json.signType;
	    			paySign = json.paySign;
	    			signature = json.signature;
	    			wxPay();
	            }else{
	            	alert("系统错误");
		        }
	        }
	    });	
    },
/*	_ali_pay:function(order_no){
		if(!isnull(order_no)){
			sessionStorage.setItem("club_cache_back","1");
			localStorage.setItem("club_cart_ids","");
			var jsonStr="{\"orderNo\":\""+order_no+"\"}";
			var href=dataLink+"alitool/submit?jsonStr="+jsonStr;
			window.location.href=href;
		}
	}*/
}
function wxPay(){
	wx.config({
        debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: appId, // 必填，公众号的唯一标识
        timestamp:timeStamp , // 必填，生成签名的时间戳
        nonceStr: nonceStr, // 必填，生成签名的随机串
        signature: signature,// 必填，签名，见附录1
        jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    }); 
    wx.ready(function(){
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
        wx.chooseWXPay({
            timestamp: timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
            nonceStr: nonceStr, // 支付签名随机串，不长于 32 位
            package: pk, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
            signType: signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
            paySign: paySign, // 支付签名
            complete: function (res) {
            	if(res.errMsg=="chooseWXPay:ok"){
            		alert("!!!!!!!!!!!!");
				}
            }
        });
    });
}