// add by xiehuilin 2017/06/27/ 参数校验
var param={
	fEvent:function  (param2,css2) { //完成事件参数校验
		 
      if(param2.length>100){
		      	$("."+css2).show();
		      	setTimeout(function(){
					$("."+css2).hide();
				  },2000);
	      	return false;
      }
      return true;
	}
}