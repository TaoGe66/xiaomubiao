
//时间插件
$(function () {
	//取消返回上一级
	$(".Select_btn_cancel").click(function(){
		window.location.href="taskDetails.html";//跳转路径暂时还没修改
	});
	//确定返回上一级
	/*$(".Select_btn_confirm").click(function(){
		window.location.href="taskDetails.html";//跳转路径暂时还没修改
	});*/
//	$("#tests").attr("placeholder",showtime());
//	$("#tests").val(showtime());
//	showTimeone();
	showTimetwo();
//	var Time = $("#tests").attr("placeholder",show());
//	alert(Time)
});
function showtime(){
    var mydate = new Date();
    var str = "" + mydate.getFullYear() + "-";
    str += (mydate.getMonth()+1) + "-";
    str += mydate.getDate();
    return str;
};
function showTimetwo(){
	var curr = new Date().getFullYear();
	var current = new Date();
    var opt={};
	opt.date = {preset : 'date'};
	opt.datetime = {preset : 'datetime'};
	opt.time = {preset : 'time'};

	opt.default = {
		theme: 'android-holo light', //皮肤样式
        display: 'modal', //显示方式 
        mode: 'scroller', //日期选择模式
		dateFormat: 'yyyy-mm-dd',//yyyy-mm-dd
		lang: 'zh',
		showNow: true,
		nowText: "今天",
		stepMinute: 10,
//      startYear: curr - 10, //开始年份
        endYear: curr + 50, //结束年份
        minDate:current
	};
    $('.settings').bind('change', function() {
        var demo = 'datetime';
        if (!demo.match(/select/i)) {
            $('.demo-test-' + demo).val('');
        }
//      $('.demo-test-' + demo).scroller('destroy').scroller($.extend(opt['time'], opt['default']));
//      $("#tests").scroller('destroy').scroller($.extend(opt['date'], opt['default']));
        $("#test").scroller('destroy').scroller($.extend(opt['date'], opt['default']));
        $('.demo').hide();
        $('.demo-' + demo).show();
    });
    $('#demo').trigger('change');
}

var end_time="";

//任务详情
eventInfo();
function eventInfo() {
	var eId = localStorage.getItem("team_eId");
	var data = {"eId":eId}
	$.ajax({
		type : "get",
		url : dataLink + "event/getInfoEvent",
		data : data,
		dataType : "json",
		success : function(json) {
			if (json.msg = "success") {
				var item = json.event;
				end_time=item.endTime;
				$("#start_one").val(TimeIt(item.startTime));
				$("#start_two").val(TimeIt(item.endTime));
			}
		}
	});
}



//确定
$(".Select_btn_confirm").click(function(){
	var  endTime=$("#test").val();
	if (!isnull(endTime)&&(endTime>TimeIt(end_time))) {
		save_cycle();
	}
});



//保存延期
function save_cycle(){
	var  endTime=$("#test").val();
	var eId=localStorage.getItem("team_eId");
	var data= {"eId":eId,"strEndTime":endTime};
	$.ajax({
		  url:dataLink+"event/updateDelay",  
		  type : "POST",
	     data:data,
	     dataType:'json',  
	     success:function(result){
	   	  if(result.msg=="success"){
	   		window.location.href="taskDetails.html";
	   	  }
	     },  
	     timeout:3000  
	});
	

}


function TimeIt(long,siem){
	var remindTime = new Date(long);
	var Times = remindTime.getFullYear()+"-"+Substr(remindTime.getMonth()+1)+"-"+Substr(remindTime.getDate());
	if(siem){
		Times=Times+" "+Substr(remindTime.getHours())+":"+Substr(remindTime.getMinutes());
	};
	return Times;
};
