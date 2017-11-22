$(function(){
$(".time_jian").hide();
$(".time_jiantwo").show();
//	$(".time_jia").hide();
});
            var currentFirstDate;
            var formatDate = function(date){             
                var year = date.getFullYear()+'年';
                var month = (date.getMonth()+1)+'月';
                var day = date.getDate()+'日';
                var week = '('+['星期天','星期一','星期二','星期三','星期四','星期五','星期六'][date.getDay()]+')';  
                return month+day+' ';
//              return year+month+day+' '+week;
            };
            var addDate= function(date,n){       
                date.setDate(date.getDate()+n);        
                return date;
            };
            var setDate = function(date){             
                var week = date.getDay()-1;
                date = addDate(date,week*-1);
                currentFirstDate = new Date(date);
                   var now = new Date(); 
					var nowTime = now.getTime() ; 
					var day = now.getDay();
					var oneDayLong = 24*60*60*1000 ; 
                 //  var weekone =addDate((n.getDay()-1)*-1);
                 ////本周 周一    
                  var MondayTime = nowTime - (day-1)*oneDayLong  ; 
                  var Monday = new Date(MondayTime);
                      Monday.setMilliseconds("");
                      Monday.setSeconds("");
                      currentFirstDate.setMilliseconds("");
	                  currentFirstDate.setSeconds("");
//                var weeklydate = "1506844463000";
               //一天的毫秒数   
	            var millisecond = 1000 * 60 * 60 * 24;  
	            var weeklydate = localStorage.getItem("team_qinqiCTime");
	            console.info(getMyDate(weeklydate));
	            //本周 周日 
				var sunday = new Date(Monday.getTime() + (6 * millisecond));  	

		         if(currentFirstDate.getTime()<Monday.getTime() || currentFirstDate.getTime()==Monday.getTime()){
//			            		 cells[0].innerHTML = formatDate(date)+"~";
//		                         cells[1].innerHTML = formatDate(addDate(date,4));
								$(".Week_time").html(formatDate(date)+"~"+formatDate(addDate(date,6)));
								localStorage.setItem("team_weeklyTime",Date.parse(addDate(date,0)));
//								console.info(Date.parse(addDate(date,0)));
								$(".time_jian").css("display","inline-block");
								$(".time_jiantwo").hide();
								is_weekly();
                  }
//		       	  本周周一>传值||本周周一等于传值
		          if(currentFirstDate.getTime()<=weeklydate&&weeklydate<=sunday.getTime()){
//			            		 cells[0].innerHTML = formatDate(date)+"~";
//		                         cells[1].innerHTML = formatDate(addDate(date,4));
//								$(".Week_time").html(formatDate(date)+"~"+formatDate(addDate(date,6)));
//								localStorage.setItem("team_weeklyTime",Date.parse(addDate(date,0)));
								//$(".time_jia").css("display","inline-block");
								$(".time_jia").hide();
								$(".time_jiatwo").show();
//								is_weekly();
                 }	
              
            };    
            
            
			
            document.getElementById('last-week').onclick = function(){
            	  setDate(addDate(currentFirstDate,-7));
//          	  localStorage.setItem("team_weeklyTime",Date.parse(addDate(date,6)));
                   console.log(currentFirstDate+"___________________");
//                 		本周周一<=传值 &&传值<=周日

            };             
            document.getElementById('next-week').onclick = function(){
//          	  localStorage.setItem("team_weeklyTime",Date.parse(addDate(date,6)));
                setDate(addDate(currentFirstDate,7));
                 var now = new Date(); 
            	 var millisecond = 1000 * 60 * 60 * 24; 
            	 var weeklydate = localStorage.getItem("team_qinqiCTime");
            	 var sunday = new Date(currentFirstDate.getTime() + (6 * millisecond)); 
//          	 判断本周周一<当前时间&&当前时间<本周周日的时间
				if(currentFirstDate.getTime()<now.getTime()&&now.getTime()<sunday.getTime()){
                  	$(".time_jian").hide();
                  	$(".time_jiantwo").show();
                  }
                if(currentFirstDate.getTime()>weeklydate){
                  	$(".time_jia").show();
                  	$(".time_jiatwo").hide();
                  }
            };     
            setDate(new Date());
//        console.log(currentFirstDate+"000000000000000000");
            