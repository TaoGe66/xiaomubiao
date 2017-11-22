function srcfun(src){
	window.location=src;
}
$(function(){
	$("#file0").change(function(){
	  var objUrl = getObjectURL(this.files[0]) ;
	  console.log("objUrl = "+objUrl) ;
	  if (objUrl) {
	    $("#img0").attr("src", objUrl) ;
	    $('.cropper-canvas img').attr('src',objUrl);
	    $('.cropper-view-box img').attr('src',objUrl);
	  }
	  var File=$('#img0').attr('src');
	  if(File!=''||File==undefined){
	    $('.img-container').show();
	  }
	}) ;


	//建立一個可存取到該file的url
	function getObjectURL(file) {
	  var url = null ; 
	  if (window.createObjectURL!=undefined) { // basic
	    url = window.createObjectURL(file) ;
	  } else if (window.URL!=undefined) { // mozilla(firefox)
	    url = window.URL.createObjectURL(file) ;
	  } else if (window.webkitURL!=undefined) { // webkit or chrome
	    url = window.webkitURL.createObjectURL(file) ;
	  }
	  return url ;
	}


	$('.close').click(function(){
	  $('.img-container').hide();
	   window.location.reload();//刷新当前页面.
	})
	
	
function convertToData(url, canvasdata, cropdata, callback) {
	  var cropw = cropdata.width; // 剪切的宽
	  var croph = cropdata.height; // 剪切的宽
	  var imgw = canvasdata.width; // 图片缩放或则放大后的高
	  var imgh = canvasdata.height; // 图片缩放或则放大后的高
	
	
	  var poleft = canvasdata.left - cropdata.left; // canvas定位图片的左边位置
	  var potop = canvasdata.top - cropdata.top; // canvas定位图片的上边位置
	
	
	  var canvas = document.createElement("canvas");
	  var ctx = canvas.getContext('2d');
	
	
	  canvas.width = cropw;
	  canvas.height = croph;
	
	
	  var img = new Image();
	  img.src = url;
	
	
	 img.onload = function() {
	    this.width = imgw;
	    this.height = imgh;
	    // 这里主要是懂得canvas与图片的裁剪之间的关系位置
	    ctx.drawImage(this, poleft, potop, this.width, this.height);
	    var base64 = canvas.toDataURL('image/jpg', 1);  // 这里的“1”是指的是处理图片的清晰度（0-1）之间，当然越小图片越模糊，处理后的图片大小也就越小
	    callback && callback(base64)      // 回调base64字符串
	}
}


	
$(function(){

	var $image = $('.img-container > img');

	$image.on("load", function() {        // 等待图片加载成功后，才进行图片的裁剪功能
	    $image.cropper({  
	        aspectRatio: 1 / 0.967  　　// 1：1的比例进行裁剪，可以是任意比例，自己调整  
	    });
	})
	  // 点击保存
	  $(".saveBtn").on("click", function() {
	      var src = $image.eq(0).attr("src");  
	      var canvasdata = $image.cropper("getCanvasData");  
	      var cropBoxData = $image.cropper('getCropBoxData');  
	      convertToData(src, canvasdata, cropBoxData, function(basechar) {
	          // 回调后的函数处理  
	          $(".newImg").attr("src", basechar);
	          //alert(basechar);
	          decipher(basechar);
	          deciphertwo(basechar);
	    });
	})
})

function ajaxpubilc(url,data,posttype,callback){
	var loginrel= $ajaxurl+'/'+'login.html';
	
	
	if(data==undefined){
		data = {};
	}
	data["access_token"]=tooken;
	$.ajax({
		url:$ajaxurl+url,
		type:posttype, /*$ajaxPOST*/
		dataType:'json',
		data:data,
		success: function(msg){
		callback(msg);
		},
		error:function(msg){
			var data = jQuery.parseJSON(msg.responseText);
			if(data.error==undefined){
				alert(msg);
			}else{
			    if(msg.status==401){
					$.removeCookie("access_token");
			        window.location='loginnew.html'
			    }c
			}
		}
	})
}
})
function  decipher(code){
	 var str= new Array(); 
	 str=code.split(","); 
	 var _reslut=str[1];
	var _data={"imgStr":_reslut};
		$.ajax({
			url:dataLink+"image/generateImage",
			data:_data,
			type:"post",
			dataType:"json",
			success:function(result){
				if(result.msg=="success"){
					$(".img-container").hide();
					var url = result.imgUrl;
					$(".New_img").find(".img_one").attr("src",imageHeadSrc+url);
					$(".New_img").find("p").html(url);
				}			
			}
		});
}
function  deciphertwo(code){
	 var str= new Array(); 
	 str=code.split(","); 
	 var _reslut=str[1];
	var _data={"imgStr":_reslut};
		$.ajax({
			url:dataLink+"image/generateImage",
			data:_data,
			type:"post",
			dataType:"json",
			success:function(result){
				if(result.msg=="success"){
					$(".img-container").hide();
					var url = result.imgUrl;
					localStorage.setItem("team_partyLogo",url);
					$(".upload").find("img").attr("src",imageHeadSrc+url);
				}			
			}
		});
}