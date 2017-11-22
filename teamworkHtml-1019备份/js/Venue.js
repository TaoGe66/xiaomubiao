$(function(){
	var Height = window.innerHeight;
	$("body").height(Height-48);
	window.addEventListener('message', function(event) {
        // 接收位置信息，用户选择确认位置点后选点组件会触发该事件，回传用户的位置信息
        var loc = event.data;
        if (loc && loc.module == 'locationPicker') {//防止其他应用也会向该页面post信息，需判断module是否为'locationPicker'
       		//console.log(loc);
          	//console.log(loc.cityname);
          	//console.log(loc.poiname);
          	//console.log(loc.poiaddress);
          	//console.log(loc.latlng.lat);  
          	//console.log(loc.latlng.lng); 
          	
        }
        getAddress(loc.latlng.lat,loc.latlng.lng);                                
    }, false);
    //getAddress(loc.latlng.lat,loc.latlng.lng);
    function getAddress(lat,lng){
        var data = {"location":lat+","+lng,"key":tencent_key}
        data.output="jsonp"; 
        $.ajax({
		   type : "get",
	       url:"https://apis.map.qq.com/ws/geocoder/v1/?",
	       data:data,
	       dataType:'jsonp',
           data:data,
           jsonp:"callback",
           jsonpCallback:"QQmap",
	       success:function(json){
	    	// add by wuchao 2017/7/11 选中后的地图信息放入缓存
	    	console.log(json);
            localStorage.setItem("team_lng",json.result.location.lng);//经度
//          console.log(json.result.location.lng);
   	   		localStorage.setItem("team_lat",json.result.location.lat);//纬度
// 	   		console.log(json.result.location.lat);
   	   		localStorage.setItem("team_province",json.result.address_component.province);//省
// 	   		console.log(json.result);
// 	   		console.log(json.result.address_component.province);
   	   		localStorage.setItem("team_city",json.result.address_component.city);//市
// 	   		console.log(json.result.address_component.city);
   	   		localStorage.setItem("team_district",json.result.address_component.district);//区
// 	   		console.log(json.result.address_component.district);
   	   		localStorage.setItem("team_address",json.result.formatted_addresses.recommend);//详细地址
   	   		$(".activity_site").html(json.result.formatted_addresses.recommend);
// 	   		console.log(json.result.formatted_addresses.recommend);
           },
           error : function(err){alert("服务端错误，请刷新浏览器后重试")}
		});
    } 
    $(".search-bar").click(function(){
    	$(".btn").addClass("clear-input");
    })
    // add by wuchao 2017/7/11 跳转到上一个页面
    $(".btn").click(function(){
//  	window.location.href="servereleasetwo.html";
	$(".Servetwo_Venue").hide();
    	return false;
    })
    
})

