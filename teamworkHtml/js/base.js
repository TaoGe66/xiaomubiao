//跳转
var skipHttp="https://www.qingzhihui.cn/team/";
//数据传输
var dataLink="https://www.qingzhihui.cn/teamwork/";
var imageHeadSrc="https://www.qingzhihui.cn/imageFile/team_img_upload/";
var imageSrc="https://www.qingzhihui.cn/imageFile/team_img/";
var tencent_key = "MM5BZ-BAOR6-5SJSV-EA4QQ-CWLA5-4NF4Q";
$(function(){
	$("body").height($(window).height());
	//返回首页
	$(".return_index").click(function(){
		localStorage.removeItem("team_leId");
		window.location.href="index.html";
	});
//	点击轻企动态
	$(".Dynamic_title_one").click(function(){
		window.location.href="dynamic.html";
	});
	//	点击项目动态
	$(".Dynamic_title_two").click(function(){
		window.location.href="project.html";
	});
	//	点击周期
	$(".Dynamic_title_three").click(function(){
		window.location.href="weekly.html";
	});
	//	点击周期
	$(".Dynamic_title_four").click(function(){
		window.location.href="maillist.html";
	});
	//	点击首页
	$(".Index_footer_one").click(function(){
		localStorage.removeItem("team_leId");
		window.location.href="index.html";
	});
	//	点击轻企
	$(".Index_footer_two").click(function(){
		getEnterprise();
	});
	//	点击消息
	$(".Index_footer_three").click(function(){
		localStorage.removeItem("team_leId");
		window.location.href="news.html";
	});
	//	点击我的
	$(".Index_footer_four").click(function(){
		localStorage.removeItem("team_leId");
		window.location.href="personal.html";
	});
});
//add by wuchao 2017/7/18 消息未读数
getNewsCount();
function getNewsCount(){
	var userId=localStorage.getItem("team_userId");
	var data = {"sendId":userId,"isRed":0,"strType":"1,2"}
	 $.ajax({
		   type : "get",
	       url:dataLink+"news/getPromptCount", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
		    	   if(json.promptCount>0){
		    		   $(".Index_footer_three").find("span").html(json.promptCount);
		    		   $(".Index_footer_three").find("span").show();
		    	   }else{
		    		   $(".Index_footer_three").find("span").hide();
		    	   }
		    	   
	    	   }
	       }
	  });
}
//add by wuchao 2017/7/18 个人中心未读数
getNews();
function getNews(){
	var userId=localStorage.getItem("team_userId");
	var data = {"sendId":userId,"isRed":0,"strType":"0"}
	 $.ajax({
		   type : "get",
	       url:dataLink+"news/getPromptCount", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
		    	   if(json.promptCount>0){
		    		   $(".Index_footer_btnone").find("span").html(json.promptCount);
		    		   $(".Index_footer_btnone").find("span").show();
		    	   }else{
		    		   $(".Index_footer_btnone").find("span").hide();
		    	   }
	    	   }
	       }
	  });
}
//tab切换插件
function tab(sect,moer,reali){//tab切换插件
	$(sect).click(function(){
	        $(sect).eq($(this).index()).addClass(moer).siblings().removeClass(moer);
			$(reali).hide().eq($(this).index()).show();
	   });
};
function tabse(sect,indexs,reali,moer){//tab切换插件
        $(sect).eq(indexs).addClass(moer).siblings().removeClass(moer);
		$(reali).hide().eq(indexs).show();
};

//判断是否为空
function isnull(thiscoler){
	var thisnull=true;
	if(thiscoler==null ||thiscoler=="" || thiscoler==undefined ||thiscoler=="null" || thiscoler=="undefined"){
		thisnull=true;
	}else{
		thisnull=false;
	};
	return thisnull;
};
//验证主函数
function maneyNumber(thit,reg){
	var ismaney=true
	if(!isnull(thit)){
		if(!reg.test(thit)){
			ismaney=false;
		}else{
			ismaney=true;
		};
	}else{
		ismaney=false;
	};
	return ismaney;
};
//时为金额效验带后两位小数
function maneytest(thit){
	var reg =/^[0-9]*[1-9][0-9]*$/;
	return maneyNumber(thit,reg);
};
//正整数效验
function numberFant(thit){
	var reg =/^\+?[1-9][0-9]*$/;
	return maneyNumber(thit,reg);
};
//手机验证
function mobile(thit){
	if(!(/^1[34578]\d{9}$/.test(thit))){ 
		 return false; 
	}
	return true;
};
//验证短信验证码
function check_code(thit){
	var reg =/^\d{5}$/;
	return maneyNumber(thit,reg);
};
//去除空格
function trimStr(str){
	str.replace(/\s/g, "")
	return str;
}
/**
 * 前后去除空格
 */
function _trim(text){
	return text.replace(/(^\s*)|(\s*$)/g, "");
}
/**验证用户是否登录*/
function verificationUserLogin(){
	var userId=localStorage.getItem("team_userId");
	var userTel=localStorage.getItem("team_userTel");
	if(isnull(userId)||isnull(userTel)){
		localStorage.removeItem("team_userTel");
		localStorage.removeItem("team_userId");
		localStorage.removeItem("team_userName");
		localStorage.removeItem("team_userUrl");
		var url_name = window.location.pathname;
		url_name = url_name.replace("/team/",""); 
		var urlDate = window.location.search; 
		localStorage.setItem("team_href",url_name+urlDate);
		if(navigator.userAgent.indexOf("MicroMessenger")!==-1){
			//微信浏览器
			window.location.href=skipHttp+"wxlogin.html";
		}else{
			window.location.href=skipHttp+"login.html";
		}
		
		return false;
	}else{
		if(navigator.userAgent.indexOf("MicroMessenger")!==-1){
			getUserOP(userId);
		}
		return true;
	}
}

function getUserOP(userIda){
	var data = {"userId":userIda}
	 $.ajax({
		   type : "get",
	       url:dataLink+"userwx/getUserInfoById", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	var userInfo = json.user;
	    	   	if(isnull(userInfo.openId)){
	    	   		window.location.href=skipHttp+"wxlogin.html";
	    	   	}
	    	   }
	    	  }
	    });
}



/**add by xiehuilin 2017/06/16 V0.0.1 发送ajxa请求**/
var _asyn= function(url, param,method,fun,type,index){
    $.ajax({
        type: method,
        url: url,
        cache: false,
        data: param,
        dataType: "json",
        success: function(data) {
                var _msg = data.msg;
                //请求正常
                if (_msg == 'success') {
                   fun(data,200, type,index);
                }else{
                	fun(data,200, type);
                }
        },
        error: function() {

      		 fun(data,500, type);   
        }
    });
}


var _new_asyn= function(url, param,method,fun,type){
    $.ajax({
        type: method,
        url: url,
        cache: false,
        data: param,
        dataType: "json",
      contentType: "application/json; charset=utf-8",
        success: function(data) {
                var _msg = data.msg;
                //请求正常
                if (_msg == 'success') {
                   fun(data,200, type);
                }else{
                	fun(data,200, type);
                }
        },
        error: function() {

      		 fun(data,500, type);   
        }
    });
}
/** add by xiehuilin 2017/06/16 V0.0.1 跳转链接*/
var _g= function(url) {
    location.href = url;
};

function Substr(str){
	var bestime="00"+str
	return bestime.substr(bestime.length-2)
};

//转换时间格式
//		Time(1491080884000,15)//2017-04-02 05:08:04    //15可以为任意值，只要不为空
//		Time(1491080884000)//2017-04-02
function Time(long,siem){
		var remindTime = new Date(long);
		var Times = remindTime.getFullYear()+"-"+Substr(remindTime.getMonth()+1)+"-"+Substr(remindTime.getDate());
		if(siem){
			Times=Times+" "+Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes())+":"+Substr(remindTime.getSeconds());
		};
		return Times;
	};
//转换时间格式
//		Time(1491080884000,15)//2017-04-02 05:08
function TimeMin(long){
		var remindTime = new Date(long);
		var Times = remindTime.getFullYear()+"-"+Substr(remindTime.getMonth()+1)+"-"+Substr(remindTime.getDate());
			Times=Times+" "+Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes());
		return Times;
	};

//		Time(1491080884000)//04-02
function Timeday(long){
		var remindTime = new Date(long);
		var Times = Substr(remindTime.getMonth()+1)+"-"+Substr(remindTime.getDate());
		return Times;
	};
function timeToMonthDayhm(long){
	var remindTime = new Date(long);
	var Times = Substr(remindTime.getMonth()+1)+"-"+Substr(remindTime.getDate());
	Times=Times+" "+Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes());
	return Times;
};
//校验内容长度，islengthA为最短，islengthB为最长，centent要效验的内容,islengthB不传只判断最短
function centen(centent,islengthA,islengthB){
	var isarrart=true
	if(!isnull(islengthB)){
		if(centent.replace(/[^\x00-\xff]/g, "**").length<islengthA||centent.replace(/[^\x00-\xff]/g, "**").length>islengthB){
			isarrart=false;
		};
	}else if(centent.replace(/[^\x00-\xff]/g, "**").length<islengthA){
		isarrart=false;
	}
	return isarrart;
};


/**
 * ajax封装方法
 **/
function ajax(json){
    var oAjax=window.XMLHttpRequest?new XMLHttpRequest():new ActiveXObject('Microsoft.XMLHTTP');
    json.t=Math.random();
    var arr=[];
    for(var i in json.data)
    {
        arr.push(i+'='+encodeURIComponent(json.data[i]));
    }
    var str=arr.join('&');
    if(json.type=='get')
    {
        oAjax.open('get',json.url+'?'+str,true);
        oAjax.send();
    }
    else if(json.type=='post')
    {
        oAjax.open('post',json.url,true);
        oAjax.setRequestHeader('Accept','application/json, text/javascript, */*; q=0.01');
        oAjax.setRequestHeader('content-type','application/x-www-form-urlencoded');
        oAjax.send(str);       
    }
    oAjax.onreadystatechange=function()
    {
        if(oAjax.readyState==4)
        {
            if(oAjax.status>=200&&oAjax.status<=300||oAjax.status==304)
            {
                json.fnSucc&&json.fnSucc(JSON.parse(oAjax.responseText));
               
            }
        }
        else
        {
            json.fnFaild&&json.fnFaild();
           
        }
    }
}

function dateHour(long,siem){
	var remindTime = new Date(long);
	function Substr(str){
		var bestime="00"+str
		return bestime.substr(bestime.length-2)
	};
	var Times ="";
	if (siem==1) {
		Times=Substr(remindTime.getMonth()+1)+"月"+Substr(remindTime.getDate())+"日";
	}else if(siem==2){
		Times=Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes());
	};
	return Times;
};

/**
 * 正则校验
 */
var Regexs = {
        email: (/^[0-9a-z][0-9a-z\-\_\.]+@([0-9a-z][0-9a-z\-]*\.)+[a-z]{2,}$/i),//邮箱  
        phone: (/^0[0-9]{2,3}[2-9][0-9]{6,7}$/),//座机手机号码  
        ydphpne: (/^((13[4-9])|(15[012789])|147|182|187|188)[0-9]{8}$/),//移动手机号码  
        allphpne: (/^((13[0-9])|(15[0-9])|(18[0-9]))[0-9]{8}$/),//所有手机号码  
        ltphpne: (/^((13[0-2])|(15[56])|(186)|(145))[0-9]{8}$/),//联通手机号码  
        dxphpne: (/^((133)|(153)|(180)|(189))[0-9]{8}$/),//电信手机号码  
        url: (/^http:\/\/([0-9a-z][0-9a-z\-]*\.)+[a-z]{2,}(:\d+)?\/[0-9a-z%\-_\/\.]+/i),//网址  
        num: (/[^0-9]/),//数字  
        cnum: (/[^0-9a-zA-Z_.-]/),  
        photo: (/\.jpg$|\.jpeg$|\.gif$|\.png$|\.bmp$/i),//图片格式  
        row: (/\n/ig),
        invalidChar:(/^(([^\^\.<>%&',;=?$"':#@!~\]\[{}\\/`\|])*)$/),
        idCard:(/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/),//身份证号
        price:(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/)//金额
    };  

/** 根据key获取url上的vlue
 *add by xiehuilin 20170525 V1.0.3.2
 * name: 参数名
 */
(function ($) {
        $.getUrlParam = function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        }
})(jQuery);


//num表示要四舍五入的数,v表示要保留的小数位数。  
function decimal(num,v)  
{  
    var vv = Math.pow(10,v);  
    return Math.round(num*vv)/vv;  
} 

function Count_down (class1,class2,class3,endTime){//class1： 显示倒计时内容的class   class2：倒计时按钮框的class    class3：签到按钮框的class   例如".sign"
 	var timer = setInterval(function(){
 		var nowTime = new Date();
 		nowTime=nowTime.getTime();
 		var a = endTime;
//	 		console.log(a);
        var result = parseInt(a)-parseInt(nowTime);//将结束时间减去当前时间  得到时间差 单位为毫秒数
        if(result < 0){
        	clearInterval(timer); 
            $(class2).css("display","none");
            $(class3).css("display","block");
        }else{
//	        	console.log(result);
	        var day = Math.floor(result/24/60/60/1000)+"天";//总毫秒数除去一天的毫秒数 得到天数
//		        console.log(day);
	        var hours = Math.floor(result/1000/60/60%24);//总毫秒数模去天的毫秒数 得到小时的毫秒数 再转换为小时
//		        console.log(hours);
	        var minute = Math.floor(result/1000/60%60);//总毫秒数模去小时的毫秒数 得到剩余分钟的毫秒数 转换为分钟
	        var seconds = Math.floor(result/1000%60);//总毫秒数模去分钟的毫秒数得到剩余秒的毫秒数 转换为秒
	        seconds<10?seconds = "0"+seconds:seconds;
//		        console.log(seconds);
	        minute<10?minute = "0"+minute:minute;
//		        console.log(minute);
	        var str ="";
	        if(day == "0天"){
	        	str = str.concat("&nbsp;&nbsp;&nbsp;",hours,":",minute,":",seconds);	
	        }else{
	        	str = str.concat(day,"&nbsp;&nbsp;&nbsp;",hours,":",minute,":",seconds);
	        }
	        $(class1).html(str);
			$(class2).css("display","block");
	        if(result <= 1000){
	        	$(class1).html("00:00:00");
	            clearInterval(timer); 
	            $(class2).css("display","none");
	            $(class3).css("display","block");
	        }
        }
   		
    },0)
}
var str = "";
//  //	edit by lixiaobin 2017/08/16  详情页倒计时方法
function Count_down1 (endtime){//  endtime  截止时间
//	var timer = setInterval(function(){
		var nowTime = new Date();
 		nowTime=nowTime.getTime();
		var result = parseInt(endtime) - parseInt(nowTime);
		if(result > 0){
			var day = Math.floor(result/24/60/60/1000);//总毫秒数除去一天的毫秒数 得到天数
//		        console.log(day);
	        var hours = Math.floor(result/1000/60/60%24);//总毫秒数模去天的毫秒数 得到小时的毫秒数 再转换为小时
//		        console.log(hours);
	        var minute = Math.floor(result/1000/60%60);//总毫秒数模去小时的毫秒数 得到剩余分钟的毫秒数 转换为分钟
	        var seconds = Math.floor(result/1000%60);//总毫秒数模去分钟的毫秒数得到剩余秒的毫秒数 转换为秒
//	        seconds<10?seconds = "0"+seconds:seconds;
//		        console.log(seconds);
//	        minute<10?minute = "0"+minute:minute;
//		        console.log(minute);
			if(day > 0){
				day++;
				str = "<i class='day'>"+day+"</i><span class='day1'>天</span>";		
			}else{
				str = "<i class='xiaoshi'>"+hours+"</i><span class='xiaoshi1'>小时</span><i class='fenzhong'>"+minute+"</i><span class='fenzhong1'>分钟</span>";
			}
		}else{
			str = "<i class='xiaoshi'>0</i><span class='xiaoshi1'>小时</span><i class='fenzhong'>0</i><span class='fenzhong1'>分钟</span>";
		}
//		return str;
//	},1000)
	return str;
}


//判断是否是微信浏览器	true 是 	false 不是
function isWeiXin(){
	var ua = window.navigator.userAgent.toLowerCase();
	if(ua.match(/MicroMessenger/i) == 'micromessenger'){
		return true;
	}else{
		return false;
	}
} 

function sameDate(date1,date2){
	var remindTime1 = new Date(date1);
	var remindTime2 = new Date(date2);
	var Times = Substr(remindTime1.getMonth()+1)+"月"+Substr(remindTime1.getDate())+"日  - " +Substr(remindTime2.getMonth()+1)+"月"+Substr(remindTime2.getDate())+"日";
	if(remindTime1.getYear()==remindTime2.getYear()&&remindTime1.getMonth()==remindTime2.getMonth()&&remindTime1.getDate()==remindTime2.getDate()){
		Times  = Substr(remindTime1.getMonth()+1)+"月"+Substr(remindTime1.getDate())+"日  ";
		Times += Substr(remindTime1.getHours())+":"+Substr(remindTime1.getMinutes())+ " - ";
		Times += Substr(remindTime2.getHours())+":"+Substr(remindTime2.getMinutes())
	}
	return Times;
}
//	edit by lixiaobin 2017/06/19 毫秒转年月日
function getYear(index,time){
	var str = "";
	var this_time = index*60*60*1000;
	var times = time + this_time;
	var newTime = new Date(times); //就得到普通的时间了 
	var year = newTime.getFullYear();   //获取系统的年；
	var mour = newTime.getMonth()+1;   //获取系统月份，由于月份是从0开始计算，所以要加1
	var day = newTime.getDate(); // 获取系统日，
	var hour = newTime.getHours(); //获取系统时，
	var min = newTime.getMinutes(); //分
	mour<10?mour = "0"+mour:mour;
	min<10?min = "0"+min:min;
	hour<10?hour = "0"+hour:hour;
	str = year+"-"+mour+"-"+day+" "+hour+":"+min;
	return str;	
}
// add by xiehuilin 20170819 校验当前时间是否是今天
function isToday(str){
    var d = new Date(str.replace(/-/g,"/"));
    var todaysDate = new Date();
    if(d.setHours(0,0,0,0) == todaysDate.setHours(0,0,0,0)){
        return true;
    } else {
        return false;
    }
}


function betweenDate(begin,end){
	var day = new Date();
	var  m =end-begin;
	var  z =day-begin;
	var p =z/m*100;
	if(p>0 && p<100){
		p=p;
	}else if(p<0 ||p==0){
		p=0;
	}else if(p>100){
		p=100
	}else{
		p=0;
	}
	/*if(p<0){
		p=0;
	}else if(p>100){
		p=100
	}*/
	return p.toFixed(0);
}


//add by xiehuilin 20170918 将毫秒转换成年月日格式
function getMyDate(str){  
            var oDate = new Date(str),  
            oYear = oDate.getFullYear(),  
            oMonth = oDate.getMonth()+1,  
            oDay = oDate.getDate(),  
            oHour = oDate.getHours(),  
            oMin = oDate.getMinutes(),  
            oSen = oDate.getSeconds(),  
            oTime = oYear +'年'+ getzf(oMonth) +'月'+ getzf(oDay) +' ';
            return oTime;  
};  
//补0操作  
function getzf(num){  
    if(parseInt(num) < 10){  
        num = '0'+num;  
    }  
    return num;  
}  
//阿拉伯数字转换为简写汉字
function Arabia_To_SimplifiedChinese(Num) {
    for (i = Num.length - 1; i >= 0; i--) {
        Num = Num.replace(",", "")//替换Num中的“,”
        Num = Num.replace(" ", "")//替换Num中的空格
    }    
    if (isNaN(Num)) { //验证输入的字符是否为数字
        //alert("请检查小写金额是否正确");
        return;
    }
    //字符处理完毕后开始转换，采用前后两部分分别转换
    part = String(Num).split(".");
    newchar = "";
    //小数点前进行转化
    for (i = part[0].length - 1; i >= 0; i--) {
        if (part[0].length > 10) {
            //alert("位数过大，无法计算");
            return "";
        }//若数量超过拾亿单位，提示
        tmpnewchar = ""
        perchar = part[0].charAt(i);
        switch (perchar) {
            case "0":  tmpnewchar = "零" + tmpnewchar;break;
            case "1": tmpnewchar = "一" + tmpnewchar; break;
            case "2": tmpnewchar = "二" + tmpnewchar; break;
            case "3": tmpnewchar = "三" + tmpnewchar; break;
            case "4": tmpnewchar = "四" + tmpnewchar; break;
            case "5": tmpnewchar = "五" + tmpnewchar; break;
            case "6": tmpnewchar = "六" + tmpnewchar; break;
            case "7": tmpnewchar = "七" + tmpnewchar; break;
            case "8": tmpnewchar = "八" + tmpnewchar; break;
            case "9": tmpnewchar = "九" + tmpnewchar; break;
        }
        switch (part[0].length - i - 1) {
            case 0: tmpnewchar = tmpnewchar; break;
            case 1: if (perchar != 0) tmpnewchar = tmpnewchar + "十"; break;
            case 2: if (perchar != 0) tmpnewchar = tmpnewchar + "百"; break;
            case 3: if (perchar != 0) tmpnewchar = tmpnewchar + "千"; break;
            case 4: tmpnewchar = tmpnewchar + "万"; break;
            case 5: if (perchar != 0) tmpnewchar = tmpnewchar + "十"; break;
            case 6: if (perchar != 0) tmpnewchar = tmpnewchar + "百"; break;
            case 7: if (perchar != 0) tmpnewchar = tmpnewchar + "千"; break;
            case 8: tmpnewchar = tmpnewchar + "亿"; break;
            case 9: tmpnewchar = tmpnewchar + "十"; break;
        }
        newchar = tmpnewchar + newchar;
    }   
    //替换所有无用汉字，直到没有此类无用的数字为止
    while (newchar.search("零零") != -1 || newchar.search("零亿") != -1 || newchar.search("亿万") != -1 || newchar.search("零万") != -1) {
        newchar = newchar.replace("零亿", "亿");
        newchar = newchar.replace("亿万", "亿");
        newchar = newchar.replace("零万", "万");
        newchar = newchar.replace("零零", "零");      
    }
    //替换以“一十”开头的，为“十”
    if (newchar.indexOf("一十") == 0) {
        newchar = newchar.substr(1);
    }
    //替换以“零”结尾的，为“”
    if (newchar.lastIndexOf("零") == newchar.length - 1) {
        newchar = newchar.substr(0, newchar.length - 1);
    }
    return newchar;
}

//获取我的轻企
function getEnterprise(){
	if(verificationUserLogin()){
			var userid = localStorage.getItem("team_userId");
			
			var data = {"userId":userid}
			 $.ajax({
				   type : "get",
			       url:dataLink+"userwx/getUserInfoById", 
			       data:data, 
			       dataType : "json",
			       success : function(json){
			    	   if(json.msg="success"){
			    	   	var userInfo = json.user;
			    	    	if(!isnull(userInfo.leId)){
			    	    		localStorage.setItem("team_leId",userInfo.leId);
							    window.location.href="dynamic.html";
			    	    	}else{
			    	    		ente(userid);
			    	    	}
			    	   }
			    	  }
			    });
			
	}
}

function ente(userid){
	var data={"userId":userid};
			$.ajax({
				url:dataLink+"enterprise/enterpriseMyList", 
				type : "get",
				dataType: 'json',
				data:data, 
				success : function(date){
					if(date.msg=="success"){
						if(!isnull(date.enList)&& date.enList.length>0){
							localStorage.setItem("team_leId",date.enList[0].leId);
							window.location.href="dynamic.html";
						}else{
							window.location.href="suggest.html";
						}
					}
				}
			});
	
}

//轻企头部
function enterpriseInfoByFrist(){
	var userId = localStorage.getItem("team_userId");
    var leId = localStorage.getItem("team_leId");
	var data = {"userId":userId,"leId":leId};
	 $.ajax({
		   type : "get",
	       url:dataLink+"enterprise/enterpriseInfo", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	var item = json.enterpriseInfo;
	    	   	localStorage.setItem("team_qinqiCTime",item.createTime);
	    	   	if(item.type==1){
	    	     	$(".Dynamic_header_inner i").html(item.name+'<em class="Dynamic_car"></em>');
	    	   	}else{
	    	   		$(".Dynamic_header_inner i").html(item.name);
	    	   	}
	    	   	$(".Dynamic_header_inner img").attr('src',imageHeadSrc+item.leLogo);
	    	    if(item.roleType==0 || item.roleType==1){
	    	   		memberRedtop();
	    	   	}
	    	   }
	    	}
	    });
}



function memberRedtop(){
	 var data = {"leId":leId,"lemState":2,"roleTypeStr":2};
	 $.ajax({
		   type : "get",
	       url:dataLink+"member/memberListAll", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	       	  if(json.msg="success"){
	       	  	if(!isnull(json.memberList) && json.memberList.length>0){
                   $(".Dynamic_red").show();
	       	  	}
	       	  }
	       	 }
	    });
}

//add by zhanghaitao 2017/11/03 撤销定时器
function Count_down1 (endtime){//  endtime  发布时间
	setInterval(function(){
		var nowTime = new Date();
 		var nowTime=nowTime.getTime();
		var result = parseInt(nowTime) - parseInt(endtime);
		if(result > 0){
			var day = Math.floor(result/24/60/60/1000);//总毫秒数除去一天的毫秒数 得到天数
        	var hours = Math.floor(result/1000/60/60%24);//总毫秒数模去天的毫秒数 得到小时的毫秒数 再转换为小时
        	var minute = Math.floor(result/1000/60%60);//总毫秒数模去小时的毫秒数 得到剩余分钟的毫秒数 转换为分钟
        	var seconds = Math.floor(result/1000%60);//总毫秒数模去分钟的毫秒数得到剩余秒的毫秒数 转换为秒
			if(minute > 10){
				str = "";		
			}else{
				str = "<span class='actionright_revoked'>撤销</span>";
			}
		}else{
			str = "";
		}
//		return str;
	},1000)
	return str;
};