
var share_eId=$.getUrlParam("eId");
var userId=localStorage.getItem("team_userId");
var eId=isnull(share_eId)?localStorage.getItem("team_eId"):share_eId;
localStorage.setItem("team_eId",eId)
var soure=$.getUrlParam("soure");
var _shareTitle="";// 分享标题
var _shareCoutent = ""; //分享内容
var _shareLogo = ""; //分享logo
var signTime="";
var reslutLng="";
var reslutlat="";
var clng="";
var clat="";
var id="";

$(function(){
	 //add by xiehuilin 2017/06/21 跳转参与列表
	$(".view_participation button").click(function(){
		if(verificationUserLogin()){
			$(".sure_popups").show();
			$(".sure_popups1").show();
		}
	});
	//add by xiehuilin 2017/06/23 跳转事件描述页
	$(".list").click(function(){
		_g("event_description.html");
	});
	$(".sign button").click(function(){
	  if(verificationUserLogin()){
			getLocation();
   	 }
	})
	//add  by xiehuilin 2017/06/26 跳转评论页
	$(".comment").click(function(){
		_g("comment.html");
	});
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
    init.data();
    var leId=localStorage.getItem("team_leId");
if(!isnull(leId)){
	$(".global img").hide();
	$(".global").addClass("Global_current_now");
}
//  点击分享图标弹出引导页
    $(".share1").click(function(event){
    	go_shore();
    	$(".share_page").css("display","block");
    	$(".share_page1").css("display","block");
    	event.stopPropagation();

    })
    $(".share_page").click(function(){
		$(".share_page").css("display","none");
    	$(".share_page1").css("display","none");
	})

	// add by xiehuilin 2017/06/27 跳转查看参与列表
    $(".view_participation1 button").click(function(){
    	_g("participants.html");
    })
    //add by xiehuilin 2017/07/18 参与报名弹框取消操作
    $(".btn_qx").click(function(){
    	$(".sure_popups").hide();
		$(".sure_popups1").hide();
    });
    //add by xiehuilin 2017/07/18 参与报名弹框确定操作
    $(".btn_qd").click(function(){
    	if(verificationUserLogin()){
			 update.join();
		}
    });
//  var cont=$(".return_index");	
//	var contW=$(".return_index").width();
//	var contH=$(".return_index").height();			
//	var startX,startY,sX,sY,moveX,moveY;		
//	var winW=$(window).width();	
//	var winH=$(window).height();
//	var body=$("body");
//	cont.on({//绑定事件
//		touchstart:function(e){				
//			startX = e.originalEvent.targetTouches[0].pageX;	//获取点击点的X坐标	
//			startY = e.originalEvent.targetTouches[0].pageY;    //获取点击点的Y坐标
//			//console.log("startX="+startX+"************startY="+startY);
//			sX=$(this).offset().left;//相对于当前窗口X轴的偏移量
//			sY=$(this).offset().top;//相对于当前窗口Y轴的偏移量
//			//console.log("sX="+sX+"***************sY="+sY);
//			leftX=startX-sX;//鼠标所能移动的最左端是当前鼠标距div左边距的位置
//			rightX=winW-contW+leftX;//鼠标所能移动的最右端是当前窗口距离减去鼠标距div最右端位置
//			topY=startY-sY;//鼠标所能移动最上端是当前鼠标距div上边距的位置
//			bottomY=winH-contH+topY;//鼠标所能移动最下端是当前窗口距离减去鼠标距div最下端位置				
//			},
//		touchmove:function(e){				
//			e.preventDefault();
//			moveX=e.originalEvent.targetTouches[0].pageX;//移动过程中X轴的坐标
//			moveY=e.originalEvent.targetTouches[0].pageY;//移动过程中Y轴的坐标
//			//console.log("moveX="+moveX+"************moveY="+moveY);
//			if(moveX<leftX){moveX=leftX;}								
//			if(moveX>rightX){moveX=rightX;}
//			if(moveY<topY){moveY=topY;}
//			if(moveY>bottomY){moveY=bottomY;}
//			$(this).css({
//				"left":moveX+sX-startX,
//				"top":moveY+sY-startY,					
//			})
//		},
//	})
});

// add by xiehuilin 2017/06/16 初始化事件详情
var init={
	data:function(){
		var param={"userId":userId,"eId":eId};
    	var url=dataLink+"event/svcPartyEventInfo";
    	_asyn(url,param,"get",init.Ok);
	},
	Ok:function(json, code){
		
		console.info(json);
		if(json.msg=='success'){
			if(json.cflag==true&&json.isend==0&&json.roleType!=1){
				$(".view_participation2").show();
				$(".view_participation").hide();
				$(".view_participation1").hide()
			}
			json.roleType==1?$(".view_participation1").show():$(".view_participation").show();

			signTime=json.signTime;
			reslutLng=json.event.lng;
			reslutLat=json.event.lat;
			json.isSelected==0?$(".Service_footer").show():$(".Service_footer").hide();
			json.roleType==0?$(".running").show():$(".jeCount").show();
			var eve=json.event;
			var lessCount=eve.lessCount==null?0:eve.lessCount;
			var maxCount=eve.maxCount==null?0:eve.maxCount;
			_shareTitle=eve.name;
			_shareCoutent=eve.target;
			_shareLogo=imageHeadSrc+eve.logo;
			$(".list").find("p").html(eve.name);
			$(".target_content").html(eve.target);
			$(".bgtime").html(eve.sTime);
			$(".endtime").html(eve.eTime);
			$(".bgnumber").html(lessCount);
			$(".endnumber").html(maxCount);
			$(".Sign_people").html("创建人：&nbsp;"+eve.userName);
			if (isnull(eve.content)) {
				$(".item").hide();
			}else{
				$(".item").html(eve.content);
			}
			$(".dizhi").html(eve.address);
		  localStorage.setItem("team_roleType",json.roleType);
		  localStorage.setItem("team_isSelected",json.isSelected);
		 if(json.roleType!=1&&json.joinEvents.length>0){

		  		for(var x=0;x<json.joinEvents.length;x++){
		  			var je=json.joinEvents[x];
		  				id=je.id;
		  			//已签到
					if(je.isSign==1){
						$(".signed_in").css("display","block");
			  			$(".view_participation").css("display","none");
			  			$(".comment").css("display","none");
			  			$(".view_participation1").css("display","none");
			  			$(".sign").hide();
		  			}else{
		  				//已参与,签到倒计时
		  				$(".view_participation").hide();
		  				$(".view_participation").hide();
			  			$(".comment").hide();
			  			$(".view_participation1").hide();
			  			var mydate = new Date().getTime();
			  			if(mydate<signTime){
		  				setTimeout(function(){
							//$(".count_down").css("display","block");
							Count_down(".Count_down",".count_down",".sign",signTime);
						 },0)
		  			}else{
		  				$(".sign").show();
		  			}
		  		 }	
		  		}
		  	 }
		  	 //add by xiehuilin 2017/07/20 报名人数是否已满
		  	 if(json.roleType!=1&&eve.lessCount>=eve.maxCount){
		  	 	$(".view_participation").hide();
		  	 	$(".view_participation2").hide();
		  	 	$(".view_participation1").hide();
		  	 	$(".count_down").hide();
		  	 	$(".sure_popups1").hide();
		  	 	$(".sure_popups").hide();
		  	 	$(".sign").hide();
		  	 	$(".signed_in").hide();
		  	 	$(".gray").show();
		  	 }
		  	 element.onOff(json);
		  	 go_shore();
		  }
		}
}


// add by xiehuilin 20170621 去分享
var _shore={
	_vip_member:function(shoreImg,eId,shoreContent){
		var shoreLink = skipHttp+"sign_in.html?eId="+eId+"&soure="+1;
		_shore._show(shoreImg,shoreLink,shoreContent);
	},
	_show:function(shoreImg,shoreLink,shoreContent){
		var shoreTitle =_shareTitle
		_wx._shore(shoreTitle,shoreContent,shoreLink,shoreImg);
	}
}



function go_shore(){
	var pageName = "sign_in";
	var urlDate = window.location.search; 
	_wx._getWxConfig(pageName,urlDate);
	var shoreContent ="【活动】"+ _shareCoutent;
	var shoreImg = _shareLogo;
	_shore._vip_member(shoreImg,eId,shoreContent);
	
}

// add by xiehuilin 2017/06/23  控制页面元素开关  onOff
var element={
	onOff:function(json){
		switch(json.isend)
		{
		case 0:
		   //$(".view_participation").show();
		   //$(".comment").hide();
		  break;
		case 1:
		  if(json.roleType==1){
		  		$(".share").hide();
		  }else if(json.joinEvents.length>0){
		  	
		  	$(".comment").show();
		  }else{
		  	$(".share").show();
		  }
		  $(".signed_in").hide();
		  $(".sign").hide();
		  $(".view_participation").hide();
		  break;
		default:
		}
	}
}

			 	
// add by xiehuilin 2017/06/23  参与
var update={
	join:function(){
		var param={"userId":userId,"eId":eId};
    	var url=dataLink+"join/save";
    	_asyn(url,param,"POST",update.Ok);
	},
	Ok:function(json,code){
		if(json.msg=="success"){
			_g("sign_in.html");
			$(".Serviceone_election").hide();
			$(".Serviceone_selected").show();
		 setTimeout(function(){
			$(".have_participated").css("display","none");
			$(".view_participation").css("display","none");
			$(".count_down").css("display","block");
			Count_down(".Count_down",".count_down",".sign",signTime);
		},2000)
		}else{
			 setTimeout(function(){$(".have_participated").hide()},2000) 
			 $(".have_participated").html(json.errorCode);
		  	 $(".have_participated").show();
		  	 $(".sure_popups1").hide();
		  	 $(".sure_popups").hide();
		}
		
	},
	//add by xiehuilin 2017/06/24 签到
	sign:function(){
		var param={"userId":userId,"eId":eId,"id":id};
    	var url=dataLink+"join/sign";
    	_asyn(url,param,"POST",update.signOk);
	},
	signOk:function(json,code){
		if(json.msg=="success"){
			$(".signin_ts").hide();
			$(".signed_in").show();
		}else{
			$(".sign_in_failed").html(json.errorCode);
			$(".sign_in_failed").show();
		}
	}
}	


//获取当前位置经纬度的方法
	function getLocation(){
		$(".signin_ts").show();
        if (navigator.geolocation){
            navigator.geolocation.getCurrentPosition(showPosition);//HTML5获取GPS设备地理位置信息
        }else{
            document.getElementById("allmap").innerHTML="Geolocation is not supported by this browser.";
        }
    }
	
    function showPosition(position){
    	var x=position.coords.latitude;//获取纬度
    	console.info(x);
        var y=position.coords.longitude;//获取经度
        console.info(y);
        //调用腾讯地图坐标的方法
        getGPS(x,y); 
    }
    //转为腾讯地图坐标的方法
    function getGPS(lat,lng){
        var data = {"locations":lat+","+lng,"key":tencent_key,"type":1}
        data.output="jsonp"	;
        $.ajax({
		    type : "get",
		    dataType:'jsonp',
	        url:"https://apis.map.qq.com/ws/coord/v1/translate?locations="+lat+","+lng+"&key="+tencent_key+"&type=1",
	        data:data,
	        jsonp:"callback",
            jsonpCallback:"QQmap",
	        success:function(json){
	        	//纬度
            	clng = json.locations[0].lng;
            	clat = json.locations[0].lat;
            	//经度
            	console.log("转换后纬度"+clat);
            	console.log("转换后经度"+clng);
            	//调用的计算距离方法
            	Calculation(reslutLat,reslutLng,clat,clng);
            	//add by xiehuilin 2017/07/11 校验是否在签到范围内
            	var distance = localStorage.getItem("distance");
//          	console.log(distance);
            	if(distance<=500){
            		update.sign();
            		$(".sign_in_failed").hide();
            	}else{
            		$(".sign_in_failed").html("不在签到范围内");
            		setTimeout(function(){$(".sign_in_failed").hide()},2000) 
            		$(".sign_in_failed").show();
            	}   
            },
            error : function(err){alert("服务端错误，请刷新浏览器后重试")}
		});
   	}
    //计算两点之间距离的方法
    function Calculation(lat1,lng1,lat2,lng2){//纬度1 经度1  纬度2 经度2 
	    var data = {"from":lat1+","+lng1,"to":lat2+","+lng2,"key":tencent_key}
	    data.output="jsonp";
	    $.ajax({
			type : "get",
			dataType:'jsonp',
		    url:"https://apis.map.qq.com/ws/distance/v1/?from="+lat1+","+lng1+"&to="+lat2+","+lng2+"&key="+tencent_key,
	        data:data,
	        jsonp:"callback",
            jsonpCallback:"QQmap",
	        success:function(json){
	        	//距离
//          	console.log(json.result.elements[0].distance);
            	var distance = json.result.elements[0].distance;
            	localStorage.setItem("distance", distance);
            },
            error : function(err){alert("服务端错误，请刷新浏览器后重试")}
		});
//		var distance = localStorage.getItem("distance");
//      console.log(distance);
    }
     //HTML5获取地理位置信息错误处理
    function showError(error){
  		switch(error.code){
    		case error.PERMISSION_DENIED:
    			document.getElementById("allmap").innerHTML="User denied the request for Geolocation."
      			break;
    		case error.POSITION_UNAVAILABLE:
      			document.getElementById("allmap").innerHTML="Location information is unavailable."
      			break;
    		case error.TIMEOUT:
      			document.getElementById("allmap").innerHTML="The request to get user location timed out."
      			break;
    		case error.UNKNOWN_ERROR:
     		 	document.getElementById("allmap").innerHTML="An unknown error occurred."
      			break;
    	}
  	}