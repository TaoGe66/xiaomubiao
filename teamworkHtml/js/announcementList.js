var page=1;
var verdict=true;
$(window).scroll(function() {
	if (verdict) {
		verdict = false;
		page += 1;
		announList(page);
	}
});
$(function(){
	announList(page);
});
//通知
function  announList(page){
	var leId=localStorage.getItem("team_leId");
//	var data={"userId":createBy,"state":1,"pageNum":page}
	var data={"leId":leId,"pageNum":page}
	$.ajax({
		  type : "GET",
	      url:dataLink+"notice/listGetNotice",  
	      dataType:'json',  
	      data:data, 
	      contentType: "application/json; charset=utf-8",
	      success:function(json){
	      	console.info(json);
	    	  if(json.msg=="success"){
	    		  var list=json.noticeList;
	    		  (list.length==0 && page==1) ?$(".Dynamic_nocontent").show():$(".Dynamic_nocontent").hide();
	    		  if (list.length>0) {
	    			  for (var i = 0; i < list.length; i++) {
	    			  	var item = list[i];
	    			  	var imge_class="";
						if (item.isUrgent==1) {
							img_class="Announ_list Announ_list_two";
							
						}else{
							img_class="Announ_list";
						}
						var html = '<li class="'+img_class+'">';
							html+='<em style="display:none;">'+item.id+'</em>';
							html+='<p class="Announ_list_title">'+item.title+'</p>'
							html+='<p class="Announ_list_time">'+item.pushTime+'</p>'
							html+='</li>'
						
						$(".Announ_con").append(html);
					}
	    			  verdict=true;
				}else{
					verdict=false;
				}
	    		//跳转事件详情
	    		  $(".Announ_list").click(function(){
	    			var isrecord_id = $(this).find("em").html();
	    			localStorage.setItem("team_notifyId",isrecord_id);
	    			_g("notificationdetails.html");
	    			
	    		  });
	    	  }
	      },  
	      timeout:3000  
	 }); 
}