
//$(function(){
//	$(".alert").click(function(){
//		justify_location();
//		agree_obtain_location();
//		geoSuccess(event);
//		geoError(event);
//	});
//})
////判断浏览器是否支持地理位置接口
//
//  function justify_location(){
//      if(navigator.geolocation) {
//          // 支持
//          alert("支持地理位置接口");
//      } else {
//          // 不支持
//          console.log("不支持地理位置接口");
//      }
//  }
//  //获取用户的地理位置。使用它需要得到用户的授权
//
//  function agree_obtain_location(){
//      var option = {
//          enableHighAccuracy : true,
//          timeout : Infinity,
//          maximumAge : 0
//      };
//
//      navigator.geolocation.getCurrentPosition(geoSuccess,geoError,option);
//
//  }
//   //同意授权
//
//  function geoSuccess(event) {
//      alert(event.coords.latitude + ', ' + event.coords.longitude);
//
//  }
//  //拒绝授权
//
//  function geoError(event) {
//      console.log("Error code " + event.code + ". " + event.message);
//  } 


var x=document.getElementById("alert");
function getLocation()
{
	if (navigator.geolocation)
	{
		navigator.geolocation.getCurrentPosition(showPosition);
	}
	else
	{
		x.innerHTML="该浏览器不支持获取地理位置。";
	}
}

function showPosition(position)
{
  
/*alert("纬度: " + position.coords.latitude + 
	"<br>经度: " + position.coords.longitude);	*/
  return position;
}