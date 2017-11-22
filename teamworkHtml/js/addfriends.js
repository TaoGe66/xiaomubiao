var userId=localStorage.getItem("team_userId");

//add by xiehuilin 2017/06/22 好友id
var fId=$.getUrlParam("team_friendId");
$(function(){
	if(verificationUserLogin()){
		getUserInfoById();
	}
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
});
function getUserInfoById(){ 
    var param={"typeUserId":fId};
     $.ajax({
           type : "get",
           url:dataLink+"userwx/getUserInfoById", 
           data:param, 
           dataType : "json",
           success : function(json){
            var user=json.user;
            var img=user.headUrl==null?"img/img03.png":(imageHeadSrc+user.headUrl);
            var note=user.note==null?"":user.note;
            var html='<section class="Addfriend_con smallshdow">';
                html+='<img src="'+img+'" alt="" />';
                html+='<div class="Addfriend_con_left">';
                html+='<p>'+user.userName+' <i>普通会员</i></p>';
                html+='<span>'+note+'</span>';
                html+='</div>';
                html+='</section>';
                html+='<footer class="Addfriend_footer">添加好友</footer>';
                $(".Addfriend_inner").append(html);
                $(".Addfriend_footer").click(function(){
                   update.friends();
                });
            }
                
        });
 };

var update={
    friends:function(){
      
        var param={"userId":userId,"friendType":fId};
        var url=dataLink+"friend/saveFriend";
        _asyn(url,param,"POST",update.Ok);
    },
    Ok:function(json,code){
        console.info(json);
        if(json.msg=="success"){
            _g("newfriend.html?v="+Math.random());
        }else{
            $(".tishi").html(json.errorCode);
            $(".tishi").show();
            setTimeout(function(){
            $(".tishi").css("display","none");
        },2000);
        }
    }
} 
