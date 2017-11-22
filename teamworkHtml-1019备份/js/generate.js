$(function(){
//	点击全选的时候
	$(".Generate_select").on("click",function(){
		$(this).toggleClass("Generate_select_current");
		if($(".Generate_select").hasClass("Generate_select_current")){
			$(".Generate_list li").addClass("Generate_list_current");
		}else{
			$(".Generate_list li").removeClass("Generate_list_current");
		}
	});

//	点击取消的时候
	$(".Generate_btn_no").on("click",function(){
		window.location.href="weekly.html";
	});
	
});
var userId = localStorage.getItem("team_userId");
var leId = localStorage.getItem("team_leId");
var startTime=localStorage.getItem("team_weeklyTime");

var other_eId=null;
billList();
function  billList(){
	var data={"userId":userId,"leId":leId,"strTime":startTime}
	$.ajax({
		  type : "GET",
	      url:dataLink+"census/listWeekly",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	    	  if(json.msg=="success"){
	    		  var list=json.list;
	    		  if (list.length>0) {
	    			  $(".Generate_select").show();
	    			  var act=1;//行动序号
	    			  var tail_after=1;//跟踪序号
	    			  var item_number=0;//项目序号
					for (var i = 0; i <= list.length; i++) {
						var item=list[i];
						if (isnull(item) || (!isnull(other_eId) && item.eId !=other_eId) ) {
							html +='</ul></li>';
							$(".Generate_content").append(html);
						}
						if (!isnull(item)) {
							var isRecord=item.isRecord==1?"Generate_list_current":"";
							
							if (item.eId !=other_eId ) {
								var html ='<li class="Generate_list"><ul>';
								item_number++;
							}
							if (item.type==0) {
								act=1;
								html +='<li class="Generate_global  '+isRecord+'">';
								html +='<em style="display:none;">'+item.id+'</em>';
								html +='<span><em></em><b>'+Arabia_To_SimplifiedChinese(item_number)+' :</b></span>';
								html +='<i>'+item.eName+'</i></li>';
								
							}else if (item.type==1) {
								html +='<li class="Generate_global Generate_action  '+isRecord+'">';
								html+='<em style="display:none;">'+item.id+'</em>';
								html +='<span><em></em><b>'+act+' :</b></span>';
								html +='<i>【行动】'+item.eiDesc+'</i></li>';
								act++;
								tail_after=1;
							}else if (item.type==2) {
								html +='<li class="Generate_global Generate_track  '+isRecord+'">';
								html+='<em style="display:none;">'+item.id+'</em>';
								html +='<span><em></em><b>('+tail_after+') :</b></span>';
								html +='<i>【计划】'+item.trContent+'</i></li>';
								tail_after++;
							}else if (item.type==4) {
								html +='<li class="Generate_global Generate_track  '+isRecord+'">';
								html+='<em style="display:none;">'+item.id+'</em>';
								html +='<span><em></em><b>('+tail_after+') :</b></span>';
								html +='<i>【完成】'+item.eiDesc+'</i></li>';
								tail_after++;
							}else if (item.type==3){
								html +='<li class="Generate_global Generate_track_two  '+isRecord+'">';
								html +='<em style="display:none;">'+item.id+'</em>';
								html +='<span><em></em><b>项目总结 :</b></span>';
								html +='<i>'+item.summary+'</i></li>';
							}
							/*	if (!isnull(other_eId)) {
							if (item.eId !=other_eId || i== list.length-1) {
								
							}
						}*/
							
							other_eId=item.eId;
						}
					}
					if($(".Generate_content .Generate_global").length ==$(".Generate_content .Generate_list_current").length){
    					$(".Generate_select").addClass("Generate_select_current");
    				}
				}else{
					$(".Dynamic_nocontent").show()
				}
//	    			点击每个li的时候
	    			$(".Generate_content .Generate_global").on("click",function(){
	    				$(this).toggleClass("Generate_list_current");
	    				if($(".Generate_content .Generate_global").hasClass("Generate_list_current")){
	    					$(".Generate_select").removeClass("Generate_select_current");
	    				};
	    				if($(".Generate_content .Generate_global").length ==$(".Generate_content .Generate_list_current").length){
	    					$(".Generate_select").addClass("Generate_select_current");
	    				}
	    			});
	    		  
	    	  }
	      },  
	      timeout:3000  
	 }); 
}

var is_yes=false;
//确定
$(".Generate_btn_ok").click(function(){
	 var billAll="";
	 var bill="";
	 // 所有
		$(".Generate_global").each(function(){
			var  id=$(this).find("em").eq(0).html();
			if (!isnull(billAll)) {
				billAll=billAll+","+id;
			}else{
				billAll=id;
			}
		});
		// 选中
		$(".Generate_list_current").each(function(){
			var  id=$(this).find("em").eq(0).html();
			if (!isnull(bill)) {
				bill=bill+","+id;
			}else{
				bill=id;
			}
		});
		if (is_yes) return;
		is_yes=true;
		 var data={"allBill":billAll,"bill":bill};
			$.ajax({
				  url:dataLink+"census/updateBill",  
				  type : "POST",
			     data:data,
			     dataType:'json',  
			     success:function(json){
				  	    if(json.msg=="success"){
				  	    	var isWeekly=localStorage.getItem("team_isWeekly");
				  	    	if (isWeekly==0) {
				  	    		save_bill();
							}else{
								is_yes=false;
								_g("weekly.html");
							}
				  	    }else{
				  	    	is_yes=false;
				  	    }
			       },  
			     timeout:3000  
			});	
});

//保存添加周报信息
function save_bill(){
	var leId = localStorage.getItem("team_leId");
	var data={"leId":leId,"isWeeklyPsuh":1,"strTime":startTime,"userId":userId};
	$.ajax({
		  url:dataLink+"census/saveWeekly",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     success:function(json){
		  	    if(json.msg=="success"){
		  	    	is_yes=false;
		  	    	_g("weekly.html");
		  	    }else{
		  	    	is_yes=false;
		  	    }
	       },  
	     timeout:3000  
	});
}