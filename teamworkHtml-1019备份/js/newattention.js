//add  by xiehuilin 220170918 分页参数
var page=1;
var verdict=true;
var userId = localStorage.getItem("team_userId");
var leId=localStorage.getItem("team_leId");
var newFollow=$.getUrlParam("newFollow");

$(function(){
	init.data(page);
});
//add by xiehuilin 20170918 渲染数据
var init={
	data:function(){
	    var param={"userId":userId,"leId":leId,"pageNum":page,"newFollow":newFollow};
    	var url=dataLink+"follow/listGetFollow";
        _asyn(url,param,"get",init.ok);
	},
	ok:function(json,code){
        console.info(json);
		if(json.msg=="success"){
			var follows=json.follows;
            var team_one_load=localStorage.getItem("team_one_load");
 			verdict=follows.length>0?true:false;
            follows.length>0&&team_one_load!=1?$(".memberPublicPopups").show():$(".memberPublicPopups").hide();
            page==1&&follows.length<=0?$(".qsy").show():$(".qsy").hide();
            if(follows.length>0&&team_one_load!=1){
                localStorage.setItem("team_one_load",1);
                $(".memberDeletePopups").fadeIn(0).delay(3000).fadeOut(0);
            }
 			var followsHtml=[];
 			for(var x=0;x<follows.length;x++){
 				var follow=follows[x];
 				var html='<li>'
						html+='<img src="'+imageHeadSrc+(follow.headUrl)+'" alt="">'
						html+='<div class="Newfriend_con">'
						html+='<span class="userName">'+follow.userName+'</span>'
						html+='<i>请求关注你</i>'
						html+='</div>'
						html+='<div class="Newfriend_btn"><i class="id" style="display: none;">'+follow.id+'</i><i class="userId" style="display: none;">'+follow.userId+'</i>'
						if(follow.state==2){
							html+='<span class="Newfriend_btn_no">拒绝</span>'
							html+='<span class="Newfriend_btn_ok">同意</span>'
						}else if(follow.state==0){
							html+='<p class="Newfriend_btn_refuse">已拒绝</p>'
						}else{
							html+='<p class="Newfriend_btn_agree">已同意</p>'
						}
						html+='</div>'
						html+='</li>';
 				followsHtml.push(html);
 			}
 			$(".Newfriend_list").append(followsHtml.join(""));
		}	
	 }
}	 

//add by xiehuilin 20170918 拒绝 
var id="";
var followId="";
$(".Newfriend_list").on("click",".Newfriend_btn_no",function(){
	id=$(this).parent().parent().find(".id").html();
    followId=$(this).parent().parent().find(".userId").html();
	$.ajax({
            type:"POST",
            url:dataLink+"follow/update",
            data:{"id":id,"state":0,"leId":leId,"userId":followId,"createBy":userId},
            datatype: "json", 
            //成功返回之后调用的函数             
            success:function(json){
            localStorage.getItem("team_one_load",1);
              _g("newattention.html");
            },
            error: function(){
                //请求出错处理
            }         
     });
});

//add by xiehuilin 20170918 同意弹框
$(".Newfriend_list").on("click",".Newfriend_btn_ok",function(){
	var id=$(this).parent().find(".id").html();
    followId=$(this).parent().parent().find(".userId").html();
	$.ajax({
            type:"POST",
            url:dataLink+"follow/update",
            data:{"id":id,"state":1,"leId":leId,"userId":followId,"createBy":userId},
            datatype: "json", 
            //成功返回之后调用的函数             
            success:function(json){
                localStorage.getItem("team_one_load",1);
              _g("newattention.html");
            },
            error: function(){
                //请求出错处理
            }         
     });
});


//add by xiehuilin 20170918 分页
$(window).scroll(function () {
    var bot =($(".Newfriend_list li").height())*3; 
        if (verdict) {
            if ((bot + $(window).scrollTop()) >= ($("body").height() - $(window).height())) {
                verdict=false;
                page+=1;
               init.data(page);
            }
        }
});

