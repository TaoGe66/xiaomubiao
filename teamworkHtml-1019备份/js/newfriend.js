$(function(){
	init.data();
	updateNew();
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
});
var page=1;//页码
var verdict=true;//是否继续刷新
$(window).scroll(function () {
    var bot =($(".Newfriend_list").height())*3; 
        if (verdict) {
            if ((bot + $(window).scrollTop()) >= ($("body").height() - $(window).height())) {
                verdict=false;
                page+=1;
                init.data();
            }
        }
});
//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
var userId=localStorage.getItem("team_userId");
var fId=$.getUrlParam("team_friendId");
// add by xiehuilin 2017/06/22 初始化好友消息列表
var init={
	data:function(){
		var param={"userId":userId,"pageNum":page};
    	var url=dataLink+"friend/getFriendsList";
    	_asyn(url,param,"get",init.Ok);
		
	},
	Ok:function(json, code){
		console.info(json);
		if(json.msg=='success'){
		 var items=json.friendsList;
		 page==1&&items.length<=0?$(".Newfriend_null").show():$(".Newfriend_null").hide();
		 for(var x=0;x<items.length;x++){
		 	var item=items[x];
		 	$(".Newfriend_list").append(init.show(item));
		 }
		 agree();
		 refuse();
		}
	},
	show:function(item){
		var rhtml="";
		var img=item.headUrl==null?"img/img03.png":(imageHeadSrc+item.headUrl);
			 rhtml+='<li>';
		     rhtml+='<img src="'+img+'" alt="" />';
		     rhtml+='<div class="Newfriend_con">';
		     rhtml+='<span>'+item.userName+'</span>';
		switch(item.state)
		{
		    case 0:
		     if(userId==item.fId){
		     	rhtml+='<i>请求加为好友</i></div>';
		     	rhtml+='<div class="Newfriend_btn">';
		     	rhtml+='<p class="Newfriend_btn_font">已拒绝</p>';
		     	rhtml+='</div>';
		     }else{
		     	rhtml+='<i>对方拒绝了您的好友请求</i>';
		     	rhtml+='</div>';
		     }

		    break;
		    case 1:
		    rhtml+='<i>请求加为好友</i></div>';
		    rhtml+='<div class="Newfriend_btn">';
		    rhtml+='<p class="Newfriend_btn_font">已添加</p>';
		    rhtml+='</div>';
		    break;
		    case 2:
		     if(userId==item.userId){
		     	rhtml+='<i></i></div>';
		     	rhtml+='<div class="Newfriend_btn">';
		     	rhtml+='<p class="Newfriend_btn_font">等待验证</p>';
		     	rhtml+='</div>';
		     }else{
			rhtml+='<i>请求加为好友</i></div>';
		    rhtml+='<div class="Newfriend_btn">';
		    rhtml+='<i class="fId" style="display: none;">'+item.userId+'</i>';
		    rhtml+='<i class="fgId" style="display: none;">'+item.fgId+'</i>';
		    rhtml+='<span class="Newfriend_btn_no">拒绝</span>';
		    rhtml+='<span class="Newfriend_btn_ok">同意</span>';
		    rhtml+='</div>';
		     }
		    break;
		    default:
		}
		rhtml+='</li>';
		return rhtml;
	}
}
//add by xiehuilin 2017/0622 同意
function agree(){
	$(".Newfriend_btn_ok").click(function(){
		var fId=$(this).parent().find(".fId").html();
		var fgId=$(this).parent().find(".fgId").html();
			oper.update(fId,fgId,1);
	});
}
//add by xiehuilin 2017/0622 拒绝
function refuse(){
	$(".Newfriend_btn_no").click(function(){
		var fId=$(this).parent().find(".fId").html();
		var fgId=$(this).parent().find(".fgId").html();
		oper.update(fId,fgId,0);
	});
}

var oper={
	update:function(fId,fgId,isAgree){
		var param={"userId":userId,"fId":fId,"isAgree":isAgree,"fgId":fgId};
    	var url=dataLink+"friend/updateFriend";
    	_asyn(url,param,"POST",oper.Ok);
	},
	Ok:function(json,code){
		console.info(json);
		if(json.msg=="success"){
			_g("newfriend.html");
		}else{
			$(".Newfriend_tishi").html(json.errorCode);
			$(".Newfriend_tishi").show();
			setTimeout(function(){
            		$(".Newfriend_tishi").hide();
            		_g(".newfriend.html");
        	},2000);
			
		}
	}
}

function updateNew(){
	var userId=localStorage.getItem("team_userId");
	var data = {"sendId":userId,"nType":0,"isRed":1}
	 $.ajax({
		   type : "post",
	       url:dataLink+"news/updateNew", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	
	      }
	    }
	});
}
