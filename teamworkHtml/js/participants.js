var page=1;//页码
var verdict=true;//是否继续刷新
$(window).scroll(function () {
    var bot =($(".Number_con").height())*3; 
        if (verdict) {
            if ((bot + $(window).scrollTop()) >= ($("body").height() - $(window).height())) {
                verdict=false;
                page+=1;
                getNumber(page);
            }
        }
});
getNumber();
/**
 * 消息列表
 * @author zhengjunting
 * @date 2017年6月19日
 */
$(function(){
	//edit by lixiaobin 2017/07/11 返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
})

function getNumber(){
	var eId=localStorage.getItem("team_eId");
	var data = {"eId":eId,"pageNum":page}
	 $.ajax({
		   type : "get",
	       url:dataLink+"event/joinEventUserList", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	       	 if(json.msg=="success"){
	       	 	var joinEvents=json.joinEvents;
	       	 	page==1&&joinEvents.length<=0?$(".todayEvents_default").show():$(".todayEvents_default").hide();
	       	 	for(var x=0;x<joinEvents.length;x++){
	       	 		var item=joinEvents[x];
	       	 		var tempHtml=item.headUrl==null?'<img src="img/img03.png" alt="" />':'<img src="'+(imageHeadSrc+item.headUrl)+'" alt="" />';
	       	 		var html='<li><p class="userId" style="display: none;">'+item.userId+'</p>';
	       	 			html+=tempHtml;
	       	 			html+='<p>'+item.userName+'</p>';
	       	 			html+='</li>';
	       	 			$(".Part_con ul").append(html);

	       	 	}
	       	 	target.go();
	       	 }
	    }
	});
}

var target={
	go:function(){
		$(".Part_con li").click(function(){
			var userId=$(this).find(".userId").html();
			_g("personaldatatwo.html?userId="+userId);
		});
	}
}