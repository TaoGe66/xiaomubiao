var dynamicId=localStorage.getItem("team_ledId");
var userId = localStorage.getItem("team_userId");
var interactId=null;//互动id
//解决第三方软键盘唤起时底部input输入框被遮挡问题
var bfscrolltop = document.body.scrollTop;//获取软键盘唤起前浏览器滚动部分的高度
$("input#Dynam_input").focus(function(){//在这里‘input.inputframe’是我的底部输入栏的输入框，当它获取焦点时触发事件
    interval = setInterval(function(){//设置一个计时器，时间设置与软键盘弹出所需时间相近
    document.body.scrollTop = document.body.scrollHeight;//获取焦点后将浏览器内所有内容高度赋给浏览器滚动部分高度
    },500)
}).blur(function(){//设定输入框失去焦点时的事件
    clearInterval(interval);//清除计时器
//  document.body.scrollTop = bfscrolltop;//将软键盘唤起前的浏览器滚动部分高度重新赋给改变后的高度
});

$(function(){
//	点击点赞按钮
	$(".Dynam_list_btntwo").click(function(){
		$(this).toggleClass("Dynam_list_btn_current");
		
		if ($(".Dynam_list_btntwo").hasClass("Dynam_list_btn_current")) {
			//点赞
			if (isnull(interactId)) {
				save_interact(1,interactId);
			}else{
				update_interact(1,interactId);
			}
		}else{
			//取消点赞
			update_interact(0,interactId);
		}
	});
//		点击评论框以外的区域评论框隐藏
			$('body').click(function(e) {
			    var target = $(e.target);
			    // 如果#overlay或者#btn下面还有子元素，可使用
			    // !target.is('#btn *') && !target.is('#overlay *')
			    if(!target.is('.Dynamic_global')) {
			       if ( $('.Dynamic_input').is(':visible') ) {  
			            $('.Dynamic_input').hide();                                                    
			       }
			    }
			});
	$(".Dynam_list_btnone").click(function(){
		$(".Dynam_input").css("display","block");
		$("#Dynam_input").focus();
	});//点击确定发送
	/*$(".Dynamic_btn_ok").click(function(){
		var content=$("#Dynam_input").val();
		if(content == ""){
		}else{
			
		}
	});*/
	//监控input事件
	$("#Dynam_input").bind("input propertychange",function(){
		if($(this).val() != ""){
			$(".Dynamic_btn_ok").css("background","#fe6161");
		}else{
			$(".Dynamic_btn_ok").css("background","#cdcdcd");
		}
	});
	
})


$(".Dynamic_btn_ok").click(function(){
	var judge=$("#Dynam_input").val();
	judge=_trim(judge);
	if (!isnull(judge)) {
		save_interact(0,interactId);
	}
});

dynamicInfo();
/**
 * add by wuchao  20170914 动态详情
 */
function dynamicInfo(){
	var data = {"id" : dynamicId,"userId":userId};
	$.ajax({
		type : "get",
		url : dataLink + "dynamic/dynamicInfo",
		data : data,
		dataType : "json",
		success : function(json) {
			if (json.msg == "success") {//成功的时候
				$(".Dynam_list_comment").html("");
				var item = json.item;
				if ( !isnull(item.dotLaud)) {
					interactId=item.dotLaud;
				}
				if (item.isLaud==1) {
					$(".Dynam_list_btntwo").addClass("Dynam_list_btn_current");
				}
				var event=item.event;
				var interactList=item.interactList;
				var html ='<p class="Dynam_list_fontone">'+event.name+'</p>';
				if (item.state ==1) {
					var itemInfo=item.item;
					html +='<div class="Dynam_list_fontfive"><span>行动描述:&nbsp;</span>';
					html +='<i>'+itemInfo.eiDesc+'</i></div>';
					html +='<p class="Dynam_list_fonttwo">计划完成时间：'+itemInfo.strFinishTime+'</p>';
					html +='<p class="Dynam_list_fontthree">实际完成时间：'+itemInfo.strFtimestamp+'</p>';
					if (!isnull(itemInfo.remark)) {
						html +='<div class="Dynam_list_fontsix"><span>标记描述:&nbsp;</span>';
						html +='<i>'+itemInfo.remark+'</i></div>';
					}
				}else if (item.state ==2) {
					var itemInfo=item.item;
					html +='<div class="Dynam_list_fontfive"><span>行动描述:&nbsp;</span>';
					html +='<i>'+itemInfo.eiDesc+'</i></div>';
					html +='<p class="Dynam_list_seven">指派时间：'+item.delegateTimeStr+'</p>';
					html +='<p class="Dynam_list_eight">接受时间：'+item.receTimeStr+'</p>';
				}else if (item.state ==3) {
					var itemInfo=item.item;
					html +='<div class="Dynam_list_fontfive"><span>行动描述:&nbsp;</span>';
					html +='<i>'+itemInfo.eiDesc+'</i></div>';
					html +='<div class="Dynam_list_fontnine"><span>跟踪内容:&nbsp;</span>';
					html +='<i>'+item.content+'</i></div>';
				}else if (item.state ==4) {
					html +='<p class="Dynam_list_fonttwo">计划完成时间：'+event.strEndTime+'</p>';
					html +='<p class="Dynam_list_fontthree">实际完成时间：'+event.strFinishTime+'</p>';
					html +='<div class="Dynam_list_fontfour"><span>项目总结:&nbsp;</span>';
					html +='<i>'+event.summary+'</i></div>';
				}else if (item.state ==5) {
					var itemInfo=item.item;
					html +='<div class="Dynam_list_fontfive"><span>行动描述:&nbsp;</span>';
					html +='<i>'+itemInfo.eiDesc+'</i></div>';
					html +='<p class="Dynam_list_seven">指派时间：'+item.delegateTimeStr+'</p>';
					html +='<p class="Dynam_list_eight">拒绝时间：'+item.rejecTimeStr+'</p>';
				}
				$(".Dynam_list_solid").html(html);
				
				var headUrl = isnull(item.headUrl)?'img/all06.png':imageHeadSrc+item.headUrl;
				 $(".Attest_photo_logo").attr("src",headUrl);//头像
				$(".Dynam_list_head_name").html(item.userName);
				$(".Dynam_list_head_time").html(item.createTimeStr);
				$(".Dynam_list_title").html(item.content);
				if (!isnull(item.laudName)) {
					$(".Dynam_fabulous").html("赞 :&nbsp;"+item.laudName);
				}
				if (item.laudCount !=0) {
					$(".identify_with").show();
					$(".identify_with").html(item.laudCount+"人认同他的工作");
				}else{
					$(".identify_with").hide();
				}
				if (interactList.length>0) {
					for (var i = 0; i < interactList.length; i++) {
						var interact= interactList[i];
						$(".Dynam_list_comment").append("<li><i>"+interact.userName+"</i>:&nbsp;"+interact.judge+"</li>");
					}
				}
			}
		},
	});		
}

/**
 * add by wuchao 20170915 保存互动信息
 */
function save_interact(type,interactId){
	var judge=$("#Dynam_input").val();
	judge=_trim(judge);
	judge=judge.replace(/[\r\n]/g,"<br>");//替换回车换行
	var data = {"createBy":userId,"ledId":dynamicId,"type":type,"id":interactId,"judge":judge};
	$.ajax({
		url : dataLink + "interact/saveInteract",
		type : "POST",
		data : data,
		dataType : 'json',
		success : function(result) {
			if (result.msg == "success") {
				window.location.reload();
			}
		},
		timeout : 3000
	});
}

/**
 * add by wuchao 20170915 修改
 */
function update_interact(isValid,interactId){
	var data = {"isValid":isValid,"id":interactId};
	$.ajax({
		url : dataLink + "interact/updateInteract",
		type : "POST",
		data : data,
		dataType : 'json',
		success : function(result) {
			if (result.msg == "success") {
				window.location.reload();
			}
		},
		timeout : 3000
	});
}