//全局未读信件读取(三个模块取)
$(document).ready(function(){
	
	var noreadmsg2 = "0";	

	try {
		//自动查询此用户未读信件(四处)
		$.ajax({
			type:'post',
			dataType:'json',
			cache:false,
			url:'/service2/queryunreadmessage',				
			data:'',				
			success:function(json){				
				if(json.unreadCnt !=0){																
					var html = "";
					$.each(json.receives, function (i){
                                            html += "<a href=\""+_logOut+"/Service2/sysmessages?id="+ json.receives[i].id +"&msgTpye=uMsg&unread=1&pro=" + json.unreadCnt + "\">"+json.receives[i].sendAccount+"(未读消息"+json.unreadCnt+"笔)"+"</a>";
					});
					$("#readmsg").html(html);	
					$("#msgcount,#msg-title,#noreadmsg,#msgcount2,#noreadmsg2").html(parseInt(json.unreadCnt));												
					$('#radiuscount').show();
				}
				else {					
					$("#readmsg").html("暂未收到新消息");
					$('#radiuscount').hide();//没有信件事，左菜单小图标隐藏
					$('#msgcount,#msg-title,#noreadmsg,#msgcount2,#noreadmsg2').html("0");	
					$('#readmsg').attr("style","text-align:center; color:black;");			
				}
			},
			error:function(xhr, type){
				
			},
			complete:function(){   }
	   });
	   
	   //游戏说明链接
	   if (typeof phoenix.Games != "undefined" && $('.lottery-link').length > 0){
			var helpLink = _logOut + phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["helpLink"];
				$('.lottery-link').find('.info').attr('href',helpLink);
		}
	  
	   } catch (err) {		
				console.log("网络异常，读取信件信息失败");
	}
	
	//将数字保留两位小数并且千位使用逗号分隔
	function formatMoney(num){
		var num = Number(num),
			re = /(-?\d+)(\d{3})/;
			
		if(Number.prototype.toFixed){
			num = (num).toFixed(2);
		}else{
			num = Math.round(num*100)/100;
		}
		num  =  '' + num;
		while(re.test(num)){
			num = num.replace(re,"$1,$2");
		}
		return num;  
	};
	
	//金额刷新
	$('.refreshBall').click(function(event){
		var spanBalls = $('#spanBall');
			
		try {
			//用户余额接口
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/gameBet/queryUserBal',			
				data:'',
				beforeSend:function(){						
					 spanBalls.css('font-size','11px').html('查询中...');
					 $('.refreshBall').hide();
				},
				success:function(data){	
					if(data){							
						spanBalls.removeAttr('style').text( formatMoney(Number(data)/10000));
						$('.refreshBall').show();
					}
				},
				complete:function(){
					$('.refreshBall').show();					
				}
			});
		} catch (err) {		
			console.log("网络异常，读取信件信息失败");
		}
		 event.stopPropagation();
	});
        $('.refreshBall').click();
	
});