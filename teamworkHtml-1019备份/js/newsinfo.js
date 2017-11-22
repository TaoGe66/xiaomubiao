var userId = localStorage.getItem("team_userId");
var leId = localStorage.getItem("team_leId");
update_interact();
getNews();

function getNews() {
	var data = {"userId":userId,"leId":leId}
	$.ajax({
		type : "get",
		url : dataLink + "dynamic/interactList",
		data : data,
		dataType : "json",
		success : function(json) {
			if (json.msg = "success") {
				if (json.list.length>0) {
					var list=json.list;
					for (var i = 0; i < list.length; i++) {
						var item = list[i];
						var headUrl=isnull(item.headUrl)?"img/head-portrait.png":imageHeadSrc+item.headUrl;
						var html ='<li class="newsinfo_list">';
							html+='<em style="display:none;">'+item.ledId+'</em>';
							html +='<div class="newsinfo_logo">';
							html +='<img src="'+headUrl+'" alt="" /></div>';
							html +='<div class="newsinfo_title">';
							if (item.type==1) {
								html +='<p class="newsinfo_name">'+item.userName+'赞了你的评论</p>';
								html +='<p class="newsinfo_founder">'+item.content+'</p>';
							}else{
								html +='<p class="newsinfo_name">'+item.userName+'评论了你的动态</p>';
								html +='<p class="newsinfo_founder">'+item.judge+'</p>';
								html +='<p class="newsinfo_founder">'+item.content+'</p>';
							}
							html +='</div></li>';
						$(".newsinfo_content").append(html);
					}
				}
				
				//跳转事件详情
	    		  $(".newsinfo_list").click(function(){
	    			var  ledId=$(this).find("em").html();
	    			localStorage.setItem("team_ledId",ledId);
	    			window.location.href="dynamicdetails.html";
	    		  });
				
			}
		}
	});
}




/**
 * add by wuchao 20170915 修改
 */
function update_interact(){
	var data = {"leId":leId};
	$.ajax({
		url : dataLink + "interact/updateRead",
		type : "POST",
		data : data,
		dataType : 'json',
		success : function(result) {
			if (result.msg == "success") {
				
			}
		},
		timeout : 3000
	});
}