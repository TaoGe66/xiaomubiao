//轻企二维码详情
var leName="";
var qrcodeUrl="";
var userId= $.getUrlParam('userId');
if (isnull(userId)) {
	 userId =localStorage.getItem("team_userId");
}
var leId= $.getUrlParam('leId');
if (isnull(leId)) {
	leId=localStorage.getItem("team_leId");
}
info();
function info() {
	var data={"userId":userId,"leId":leId}
	$.ajax({
		  type : "GET",
	      url:dataLink+"member/shareLigth",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  var item= json.member;
	    		  leName=item.leName;
	    		  qrcodeUrl=imageSrc+item.qrcodeUrl;
	    		  $(".Myqr_title").html("扫一扫</br>加入"+item.leName);
	    		  $(".Myqr_img").html(' <img src="'+imageSrc+item.qrcodeUrl+'" alt="" />');
	    		  go_shore();
	    	  }
	      },  
	      timeout:3000  
	      
	 }); 
    }




var _shore={
		_vip_member:function(shoreImg,shoreContent){
			var shoreLink = skipHttp+"qrcode.html?userId="+userId+"&leId="+leId;
			_shore._show(shoreImg,shoreLink,shoreContent);
		},
		_show:function(shoreImg,shoreLink,shoreContent){
			var shoreTitle =leName+"名片";
			_wx._shore(shoreTitle,shoreContent,shoreLink,shoreImg);
		}
	}



function go_shore(){
	var pageName = "qrcode";
	var urlDate = window.location.search; 
	_wx._getWxConfig(pageName,urlDate);
	var shoreContent ="邀请你加入"+leName;
	var shoreImg = qrcodeUrl;
	_shore._vip_member(shoreImg,shoreContent);
	
}
