var pageThree = '';
var verdictThree=true;//是否继续刷新
var userId = localStorage.getItem("team_userId");
//add by xiehuilin 2017/06/16 屏蔽分享菜单
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
   WeixinJSBridge.call('hideOptionMenu');
});

var topcc ="";

//$(".swiper-slide").scroll(function () {
// if(topcc==3){
//      var bot =($(".Index_con_listtwo").height())*3;
//      if (verdictThree) {
//          if ((bot + $(".swiper-slide").scrollTop()) >= ($(".swiper-slide").height() - $(window).height())) {
//              verdictThree=false;
//              pageThree+=1;
//              findEvent(pageThree);
//          }
//      }
//  }
// });
$(window).scroll(function () {
   if(topcc==3){
        var bot =($(".Index_con_listtwo").height())*3;
        if (verdictThree) {
            if ((bot + $(window).scrollTop()) >= ($("body").height() - $(window).height())) {
                verdictThree=false;
                pageThree+=1;
                findEvent(pageThree);
            }
        }
    }
 });


//热点发现
function findEvent(page){
	topcc =localStorage.getItem("team_top");
	pageThree = page;
	var data = {"userId":userId,"pageNum":pageThree}
	 $.ajax({
		   type : "get",
	       url:dataLink+"homepage/findEvent", 
	       data:data, 
	       dataType : "json",
	       success : function(json){
	    	   if(json.msg="success"){
	    	   	  $(".Index_find_list").show(); 
	    	   	  $(".hot_spot_default").hide();
	    	   		if(page==1){
	    	   			 verdictThree=true;
		    	   		 $(".Index_find_list").html(""); 
		    	   	}
	    	   		if(!isnull(json.pageInfo)&&json.pageInfo.length>0){
	    	   			for(i=0;i<json.pageInfo.length;i++){
	    	   				var item = json.pageInfo[i];
	    	   				
	    	   				var imgtop = item.type==1 ? '<p class="Index_con_title Index_con_titletwo" id="'+item.type+'" style="background-image: url(img/icon12.png)"; >' 
	    	   				                          : '<p class="Index_con_title Index_con_titlethree" id="'+item.type+'" style="background-image: url(img/icon17.png)"; >';
	    	   				var buttonStr = item.type==1 ? '<span class="Index_list_btnnine">投标参选</span>' :'<span class="Index_list_btnten">报名参加</span>';
	    	   			    var qingqi = '';
		    	   			if(!isnull(item.leId)){
	    	   						qingqi = '<span class="Index_con_title_red">['+item.leName+']</span>';
	    	   					}
	    	   			   var html  ='<div class="Index_con_listtwo smallshdow" id="'+item.eId+'">';
	    	   			        html +=imgtop+qingqi+item.name+'</p>';
	    	   			        html +='<div class="Index_con_list_bottom" id="'+item.cuserId+'">';
	    	   			        html +='<div class="Index_list_left Index_list_left_two">';
	    	   			        html +='<img src="'+imageHeadSrc+item.logo+'" alt="" /></div>';
	    	   			        html +='<div class="Index_list_right">';
	    	   			        html +='<p class="Index_list_right_one">'+item.target+'</p>';
	    	   			        html +='<p class="Index_list_right_two">'+sameDate(item.startTime,item.endTime)+'</p>';
	    	   			        html +='<div class="Index_list_right_btn">'+buttonStr;
	    	   			        html +='</div></div></div></div>';
		    	   			$(".Index_find_list").append(html);
	    	   			}
	    	   			$(".Index_con_listtwo").click(function(){
	    	   				var eId = $(this).attr("id");
	    	   				var type = $(this).find(".Index_con_title").attr("id");
	    	   				var cuserId = $(this).find(".Index_con_list_bottom").attr("id");
	    	    			localStorage.setItem("team_eId",eId);
	    	   			    if(type==1){
	    	   					window.location.href="servicedetailsone.html";
	    	   				}else if(type==2){
	    	   				   if(userId==cuserId){
	    	   					    window.location.href="view_participation.html";
	    	   					}else{
	    	   						window.location.href="sign_in.html";
	    	   					}
	    	   				}
	    	   				return false;
	    	   			});
	    	   			verdictThree=true;
	    	   		}else{
	    	   			verdictThree=false;
	    	   			if(page==1){
		    	   		   $(".Index_find_list").hide(); 
	    	   	           $(".hot_spot_default").show();
		    	      	}
	    	   		}
	    	   }
	     }
	});
}
