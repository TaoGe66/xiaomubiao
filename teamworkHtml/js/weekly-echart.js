// 基于准备好的dom，初始化echarts实例
//$(function(){
	// 基于准备好的dom，初始化echarts实例
//  function Chart(ec){
//  	chart1(ec);
//  	chart2(ec);
//  	chart3(ec);
//  }
    function chart1(advance, normal, stagnation, abnormal){
    	var myChart = echarts.init(document.getElementById('chart_app'));
		option = {
		    color:['#f25a4d','#3878c2','#2aaa91','#6c07a6'],
		    legend: {
		        orient: 'horizontal',
		        x: 'center',
		        y:'170',
		        selectedMode:false,
		        itemGap: 5,
		        itemWidth: 13,             // 图例图形宽度
        		itemHeight: 13,
		        data:['提前完成','正常完成','滞后完成','异常终止']
		    },
		    series: [
		        {
		        	center : ['50%', '40%'],
		            name:'chart_app',
		            type:'pie',
		            clickable:false,
		            radius: ['30%', '70%'],
		            avoidLabelOverlap: false,
		            stillShowZeroSum: true,
		            label: {
		                normal: {
		                	color:'#666666',
		                    show: false,
		                    position:'center',
		                    formatter: '{c}'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '20',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false,
		                    length:0
		                }
		            },
		            data:[
		                {value:advance, name:'提前完成'},
		                {value:normal, name:'正常完成'},
		                {value:stagnation, name:'滞后完成'},
		                {value:abnormal, name:'异常终止'}
		            ]
		        }
		    ]
		};
		myChart.setOption(option);
    };
    function chart2(advance, normal, stagnation, abnormal){
    	var myChart = echarts.init(document.getElementById('chart_app2'));
		option = {
		    color:['#f25a4d','#3878c2','#2aaa91','#6c07a6'],
		    legend: {
		        orient: 'horizontal',
		        x: 'center',
		        y:'170',
		        selectedMode:false,
		        itemGap: 5,
		        itemWidth: 13,             // 图例图形宽度
        		itemHeight: 13,
		        data:['提前完成','正常完成','滞后完成','异常终止']
		    },
		    orient: 'horizontal',
		    series: [
		        {
		        	center : ['50%', '40%'],
		            name:'chart_app',
		            type:'pie',
		            clickable:false,
		            radius: ['30%', '70%'],
		            avoidLabelOverlap: false,
		            stillShowZeroSum: true,
		            label: {
		                normal: {
		                	color:'#666666',
		                    show: false,
		                    position:'center',
		                    formatter: '{c}'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '20',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false,
		                    length:0
		                }
		            },
		            data:[
		                {value:advance, name:'提前完成'},
		                {value:normal, name:'正常完成'},
		                {value:stagnation, name:'滞后完成'},
		                {value:abnormal, name:'异常终止'}
		            ]
		        }
		    ]
		};
		myChart.setOption(option);
    };
    function chart3(all, five, one){
    	var myChart = echarts.init(document.getElementById('chart_app3'));
		option = {
		    color:['#f25a4d','#3878c2','#2aaa91','#6c07a6'],
		    legend: {
		        orient: 'horizontal',
		        x: 'center',
		        y:'170',
		        selectedMode:false,
		        itemGap: 10,
		        itemWidth: 13,             // 图例图形宽度
        		itemHeight: 13,
		        data:['全部评价','五星好评','一星差评']
		    },
		    orient: 'horizontal',
		    series: [
		        {
		        	center : ['50%', '40%'],
		            name:'chart_app',
		            type:'pie',
		            clickable:false,
		            radius: ['30%', '70%'],
		            avoidLabelOverlap: false,
		            stillShowZeroSum: true,
		            label: {
		                normal: {
		                	color:'#666666',
		                    show: false,
		                    position:'center',
		                    formatter: '{c}'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '20',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false,
		                    length:0
		                }
		            },
		            data:[
		                {value:all, name:'全部评价'},
		                {value:five, name:'五星好评'},
		                {value:one, name:'一星差评'}
		            ]
		        }
		    ]
		};
   		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
    };
//  Chart();
//});