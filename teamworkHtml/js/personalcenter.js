var userId = localStorage.getItem("team_userId");
$(function(){
//	点击弹窗灰色区域弹窗消失	
	$("#Personalc_autoWet").click(function(){
		$("#Personalc_autoWet").hide();
		$(".Personalc_photoone").hide();
		$(".Personalc_alert_global").hide();
		$(".Personalc_frim_list").hide();
	});	
	show_time();
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
//	$(".tishi_phone").hide();
//	edit by zhengjunting 2017/06/12 修改头像
	$(".Personalc_img").click(function(){
		//点击时显示选择图片按钮
		$("#Personalc_autoWet").show();
		$(".Personalc_photoone").show();
	  })
//  edit by zhengjunting 2017/06/12 取消头像上传
	$(".Personalc_cancel").click(function(){
		//edit by zhengjunting 2017/06/12  点击取消时把上传图片的按钮隐藏
		$("#Personalc_autoWet").hide();
		$(".Personalc_photoone").hide();
	});
//	edit by zhengjunting 2017/06/12 昵称弹窗
	$(".Personalc_two").click(function(){
		$("#Personalc_autoWet").show();
		$(".Personalc_name").show();
		var name =$(".Personalc_two i").html();
		$(".Personalc_name_val").val(name);
	});
//	edit by zhengjunting 2017/06/23 出生日期弹窗
	$(".Personalc_four").click(function(){
		var t =$(".Personalc_four i").html();
		$("#appDateTime").val(t);
		$("#Personalc_autoWet").show();
		$(".Personalc_time_alert").show();
	});
//	edit by zhengjunting 2017/06/12所在地弹窗
	$(".Personalc_five").click(function(){
		var addre =$(".Personalc_five i").html();
		$(".Personalc_site_val").val(addre);
		$("#Personalc_autoWet").show();
		$(".Personalc_site").show();
	});	
//	edit by zhengjunting 2017/06/12联系方式弹窗
/*	$(".Personalc_six").click(function(){
		var tel =$(".Personalc_six i").html();
		$(".Personalc_tel_val").val(tel);
		$("#Personalc_autoWet").show();
		$(".Personalc_tel").show();
	});	*/	
//  edit by zhengjunting 2017/06/12 取消 弹窗
	$(".Personalc_tel_no").click(function(){
		//edit by zhengjunting 2017/06/12  点击取消时把上传图片的按钮隐藏
		$("#Personalc_autoWet").hide();
		$(".Personalc_alert_global").hide();
	});	
//	edit by zhengjunting 2017/06/12 点击性别时
	$(".Personalc_three").click(function(){
	
    	   var sex = $(".Personalc_three i").html();
    	   $(".Personalc_frim_listul i").removeClass("Personalc_frim_current");
    	   	if(sex =="保密"){  
    	   		$("#2").addClass("Personalc_frim_current");
    	   	}else if(sex =="女"){
    	   		$("#0").addClass("Personalc_frim_current");
    	   	}else if(sex =="男"){
    	   	    $("#1").addClass("Personalc_frim_current");
    	   	}
		
		$("#Personalc_autoWet").show();
		$(".Personalc_frim_list").show();
	});
//	edit by zhengjunting 2017/06/12  选择性别
	$(".Personalc_frim_listul i").click(function(){
		$(".Personalc_frim_listul i").removeClass("Personalc_frim_current");
		$(this).addClass("Personalc_frim_current");
	});
//  edit by zhengjunting 2017/06/12  	点击选择性别取消时
	$(".Personalc_frim_btnno").click(function(){
		$("#Personalc_autoWet").hide();
		$(".Personalc_frim_list").hide();
	});
//	edit by zhengjunting 2017/06/12  个人说明跳转
	$(".Personalc_seven").click(function(){
		window.location.href="personalnote.html";
	});	
	$(".tishi").hide();
//	昵称确认修改
	$(".Personalc_name_ok").click(function(){
		var userName = $(".Personalc_name_val").val();
		if(centen(userName,4,12)){
			var data ={"userId":userId,"userName":userName};
		    updateUserInfo(data);
		}else{
			$(".tishi").show();
		}
	});	
//  性别确认修改
	$(".Personalc_frim_btnok").click(function(){
		var sex = $(".Personalc_frim_current").attr("id");
		if(!isnull(sex)){
			var data ={"userId":userId,"sex":sex};
		    updateUserInfo(data);
		}
	});
//  出生日期确认修改
	$(".Personalc_time_ok").click(function(){
		var birthday = $("#appDateTime").val();
		if(!isnull(birthday)){
			var data ={"userId":userId,"birthday":birthday};
		    updateUserInfo(data);
		}
	});
//  所在地确认修改
	$(".Personalc_site_ok").click(function(){
		var address = $(".Personalc_site_val").val();
		if(!isnull(address)){
			var data ={"userId":userId,"address":address};
		    updateUserInfo(data);
		}
	});
//  联系方式确认修改
	/*$(".Personalc_tel_ok").click(function(){
		var tel = $(".Personalc_tel_val").val();
		if(!isnull(tel)&& mobile(tel)){
			var data ={"userId":userId,"tel":tel};
		    updateUserInfo(data);
		}else{
			$(".tishi_phone").show();
		}
	});*/
	
	$(".Personalc_btn").click(function(){
		 localStorage.clear();
		 sessionStorage.clear();
		 sessionStorage.setItem("team_is_exit","1");
		 window.location.href="login.html";
	});
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
	
	getUserInfo();
});
//edit by zhengjunting 2017/06/12 图片上传
var _richText={
	_upload:function(){
		$("#post_party_content_upload").attr("action",dataLink+"post_image");	//赋予form表单上传地址
		//校验图片是否符合格式
		if(_upload._limit(document.getElementById("post_party_content_upload_file").value)){//得到本地图片路径
			$(".Personalc_alertPhoto").html("只能选择图片文件");
			$(".Personalc_alertPhoto").show();
			setTimeout(function(){
				$(".Personalc_alertPhoto").hide();
			},2000)
			return;
		}
		$("#post_party_content_upload").submit();
	},
	_uploadOk:function(state,errorCode,saveUrl){
	    if(state==1){
	    	
	    	$("#Personalc_autoWet").css("display","none");
	    	$(".Personalc_photoone").css("display","none");
	    	
	  	    $('#Pl_head').find('img').attr('src',imageHeadSrc+saveUrl);
//	  	    $('.Plcl_blur').css("background-images",imageHeadSrc+saveUrl);
	  	    localStorage.setItem("headUrl",saveUrl);
	  	    localStorage.setItem("team_userUrl",saveUrl);
	  	      	//将用户头像保存到数据库
			var data={"userId":userId,"headUrl":saveUrl}
		    $.ajax({ 
				type : "POST",
				async : false,
				url:dataLink+"userwx/updateUserInfo",    
				data:data,  
				dataType : "json",
				success:function(result){
					window.location.reload();//刷新当前页面.
				},  
				error:function(XMLHttpRequest, textStatus){
					starterror.errors(XMLHttpRequest, textStatus);
		       	}	
			});
	}else{
//		$(".Alert_con").css("display","block");
//     	$(".Alert_content_prompt").html(errorCode);
	}
		 
	}
};
//edit by zhengjunting 2017/06/12 校验图片上传类型
var _upload={
	_limit:function(path){
		return path.match(/\.(gif|jpg|jpeg|png)$/i)==null;
	},
	_ok:null
};

//edit by zhengjunting 2017/06/12    时间插件
function show_time(){
	var currYear = (new Date()).getFullYear();	
	var opt={};
	opt.date = {preset : 'date'};
	opt.datetime = {preset : 'datetime'};
	opt.time = {preset : 'time'};
	opt.default = {
		theme: 'ios', //皮肤样式
	    display: 'modal', //显示方式 
	    mode: 'scroller', //日期选择模式
		lang:'zh',
	    startYear:currYear - 50, //开始年份,如 currYear - 10
	    endYear:currYear + 10, //结束年份
	    maxDate: new Date()

	};

	$.mobiscroll.i18n.zh = $.extend($.mobiscroll.i18n.zh, {
	    dateFormat: 'yyyy-mm-dd',
	    dateOrder: 'yymmdd',
	    dayNames: ['周日', '周一;', '周二;', '周三', '周四', '周五', '周六'],
		dayNamesShort: ['日', '一', '二', '三', '四', '五', '六'],
	    dayText: '日',
	    hourText: '时',
	    minuteText: '分',
	    monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月','十二月'],
	    monthNamesShort: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
	    monthText: '月',
	    secText: '秒',
	    timeFormat: '',
	    timeWheels: '',
	    yearText: '年',
	    setText: '确定',
  		cancelText: '取消'
		});
	
		var optDateTime = $.extend(opt['datetime'], opt['default']);
		$("#appDateTime").mobiscroll(optDateTime).datetime(optDateTime);
		
		
}


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
	    	   	$(".Personalc_two i").html(userInfo.userName);
	    	   	var headUrl = isnull(userInfo.headUrl)? "img/img03.png" : imageHeadSrc+userInfo.headUrl;
	    	   	$(".Personalc_img img").attr("src",headUrl);
	    	   	var sex ="保密";
	    	   	if(userInfo.sex==0){
	    	   		sex ="女";
	    	   	}else if(userInfo.sex==1){
	    	   		sex ="男";
	    	   	}
	    	   	$(".Personalc_three i").html(sex);
	    	   	var birthday = isnull(userInfo.birthday)? "" : userInfo.birthday;
	    	   	$(".Personalc_four i").html(birthday);
	    	   	$(".Personalc_five i").html(userInfo.address);
	    	   	$(".Personalc_six i").html(userInfo.tel);
	    	   	if(!isnull(userInfo.note)){
	    	   			$(".Personalc_seven i").html(userInfo.note);
	    	   	}
	    	   	
	    	   }
	    	  }
	    });
}

function updateUserInfo(data){
	
	 $.ajax({
		   type : "post",
	       url:dataLink+"userwx/updateUserInfo", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    		   window.location.href="personalcenter.html";
	    	   }
	       }
	 });
	
}