$(function(){
	//add by zhanghaitao 2017/11/01  点击背景灰色区域
	$(".event_summary").click(function(){
		$(".event_summary").hide();
		$(".Event_option_alert").hide();
		$(".action_open").hide();
		$(".Event_alerttwo").hide();
		$(".Dynamic_input").hide();
		$(".dongjie_open").hide();
		$(".jiedong_open").hide();
		$(".delete_stop").hide();
	});
	$(".memberPopupsBg").click(function(){
		$(".action_open").hide();
		$(".action_private").hide();
		$(".action_stop").hide();
		$(".action_revoked").hide();
		$(".action_delete").hide();
		$(".Event_alertthree").hide();
		$(".start_model").hide();
		$(".dongjie_open").hide();
		$(".jiedong_open").hide();
		$(".delete_stop").hide();
	});
	//add by zhanghaitao 2017/11/01  所有弹窗取消按钮
	$(".open_cancel").click(function(){
		$(".action_open").hide();
		$(".dongjie_open").hide();
		$(".jiedong_open").hide();
	});
	$(".private_cancel").click(function(){
		$(".action_private").hide();
	});
	$(".stop_cancel").click(function(){
		$(".action_stop").hide();
		$(".delete_stop").hide();
	});
	$(".revoked_cancel").click(function(){
		$(".action_revoked").hide();
	});
	$(".delete_cancel").click(function(){
		$(".action_delete").hide();
	});
	$(".Event_track_no").click(function(){
		$(".Event_alertthree").hide();
	});
	$(".Event_btn_no").click(function(){
		$(".event_summary").hide();
		$(".Event_alerttwo").hide();
	});
	$(".start_cancel").click(function(){
		$(".start_model").hide();
	});
	//add by zhanghaitao 2017/11/01  所有弹窗确认按钮
	$(".open_determine").click(function(){
		$(".Event_publictwo").hide();
		$(".Event_public").show();
		$(".action_open").hide();
	});

	$(".Event_track_ok").click(function(){
		$(".Event_alertthree").hide();
		$(".action_two").hide();
	});
	//add by zhanghaitao 2017/11/01  点击三个点出现列表选项弹窗
	$(".action_right").click(function(){
		$(".event_summary").show();
		$(".Event_option_alert").show();
	});
	//add by zhanghaitao 2017/11/01  删除行动
	$(".Event_option_four").click(function(){
		$(".event_summary").hide();
		$(".Event_option_alert").hide();
		$(".action_delete").show();
	});
	//add by zhanghaitao 2017/11/01  发布进度弹窗
	$(".action_fabu").click(function(){
		$(".Event_alertthree").show();
	});
	//add by zhanghaitao 2017/11/01  行动完成弹窗
	$(".action_wancheng").click(function(){
		$(".event_summary").show();
		$(".Event_alerttwo").show();
	});
	//add by zhanghaitao 2017/11/01  行动公开、私有
	$(".Event_publictwo").click(function(){
		$(".action_open").show();
	});
	$(".Event_public").click(function(){
		$(".action_private").show();
	});
	
	//add by zhanghaitao 2017/11/01  点击三个点出现列表选项弹窗
	$(".action_right").click(function(){
		$(".event_summary").show();
		$(".Event_option_alert").show();
	});
	$(".Event_option_two").click(function(){
		window.location.href="editorialAction.html";
	});
	//add by zhanghaitao 2017/11/01  终止行动
	$(".Event_option_three").click(function(){
		$(".event_summary").hide();
		$(".Event_option_alert").hide();
		$(".action_stop").show();
	});
	
	
	$("#tests").on("click",function(){
		$(this).focus();
	})
});

$(function () {
            var curr = new Date().getFullYear();
            var  curr2=new Date();
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
		        startYear: curr - 10, //开始年份
		        endYear: curr + 50 //结束年份
			};
           $('.settings').bind('change', function() {
                var demo = 'datetime';
                if (!demo.match(/select/i)) {
                    $('.demo-test-' + demo).val('');
                }
                $("#tests").scroller('destroy').scroller($.extend(opt['datetime'], opt['default']));
                $('.demo').hide();
                $('.demo-' + demo).show();
            });
            $('#demo').trigger('change');
        });
