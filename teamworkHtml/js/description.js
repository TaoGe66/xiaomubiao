$(function(){
	show_time();
	$(".int1").on("click",function(){
		$(this).attr("src","img/radion_click.png");
		$(".int2").attr("src","img/radio.png");
		$(this).addClass("getState");
		$(".int2").removeClass("getState");
	})
	$(".int2").on("click",function(){
		$(this).attr("src","img/radion_click.png");
		$(".int1").attr("src","img/radio.png");
		$(this).addClass("getState");
		$(".int1").removeClass("getState");
	})
	$(".complete1").click(function(){
		$(".event_summary").css("display","block");
		$(".event_summary1").css("display","block");
	})
	$(".cancel").click(function(){
		$(".event_summary").css("display","none");
		$(".event_summary1").css("display","none");
	})
	//返回首页
	$(".return_index").click(function(){
		window.location.href="index.html";
	});
	 //add by lixiaobin 2017/08/01 点击阴影区影藏弹窗
	$(".event_summary").click(function(){
		$(".event_summary").css("display","none");
		$(".event_summary1").css("display","none");
	})
})
function show_time(){
	//获取当前时间前4个小时，一天为86400000毫秒
	var yesterdsay = new Date(new Date().getTime() - 14400000);
	var currYear = (new Date()).getFullYear();	
	var opt={};
	opt.date = {preset : 'date'};
	opt.datetime = {preset : 'datetime'};
	opt.time = {preset : 'time'};
	opt.default = {
		theme: 'ios', //皮肤样式
	    display: 'modal', //显示方式 
	    mode: 'scroller', //日期选择模式
		lang:'zh',
		startYear:currYear - 50, //开始年份,如 currYear - 10
	    endYear:currYear + 50, //结束年份
	    maxDate:new Date(),
	    minDate:new Date(yesterdsay) 
	};
	
	$.mobiscroll.i18n.zh = $.extend($.mobiscroll.i18n.zh, {
	    dateFormat: 'yyyy-mm-dd',
	    dateOrder: 'yymmdd',
	    dayNames: ['周日', '周一;', '周二;', '周三', '周四', '周五', '周六'],
		dayNamesShort: ['日', '一', '二', '三', '四', '五', '六'],
	    dayText: '日',
	    hourText: '时',
	    minuteText: '分',
	    monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月','十二月'],
	    monthNamesShort: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
	    monthText: '月',
	    secText: '秒',
	    timeFormat: 'HH:ii',
	    timeWheels: 'HHii',
	    yearText: '年',
	    setText: '确定',
  		cancelText: '取消'
		});

		var optDateTime = $.extend(opt['datetime'], opt['default']);
		$("#appDateTime").mobiscroll(optDateTime).datetime(optDateTime);
}
