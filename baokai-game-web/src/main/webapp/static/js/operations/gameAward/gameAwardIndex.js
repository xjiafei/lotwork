(function() {
	
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#awardGroupMenu').attr("class","current");
	
	//弹窗
	var minWindow,mask,initFn,isture=true,
	minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});	  
	

	//---------------------------------发布通过操作效果--------------------------------
	$('a[id=J-Publish]').each(function(e){
		$(this).click(function(e){
			
			var tt = $(this).attr('name');
			var ids = tt.split("_");
			e.preventDefault();		
			minWindow.setTitle('温馨提示');
			minWindow.setContent($('#DivPublish').val());
			minWindow.show();
			
			$('#closeDs').click(function(e){
				minWindow.hide();
			});
			//提交发布确定操作
			$('#J-Submit-But1').click(function(e){			
				minWindow.hide();	
			
				var jsonStr = "";
				jsonStr += '{"lotteryId":';
				jsonStr += ids[0];
				jsonStr += ',"awardId":';
				jsonStr += ids[1];
				jsonStr += ',"publishResult":1';
				jsonStr += '}';
				
				var url = baseUrl + "/gameoa/publishGameAwardGroup";
				var data = "publishGameStr=" + jsonStr;
				
				sendUrl(url, data);
			});	

		});
	});
	
	//---------------------------------发布不通过操作效果--------------------------------
	$('a[id=J-Publish2]').each(function(e){
		$(this).click(function(e){
			
			var tt = $(this).attr('name');
			var ids = tt.split("_");
			e.preventDefault();		
			minWindow.setTitle('温馨提示');
			minWindow.setContent($('#DivPublish2').val());
			minWindow.show();
			
			$('#closeDs2').click(function(e){
				minWindow.hide();
			});
			//提交发布不通过确定操作
			$('#J-Submit-But2').click(function(e){			
				minWindow.hide();	
			
				var jsonStr = "";
				jsonStr += '{"lotteryId":';
				jsonStr += ids[0];
				jsonStr += ',"awardId":';
				jsonStr += ids[1];
				jsonStr += ',"publishResult":2';
				jsonStr += '}';
				
				var url = baseUrl + "/gameoa/publishGameAwardGroup";
				var data = "publishGameStr=" + jsonStr;
				
				sendUrl(url, data);
			});	

		});
	});
	
	
	//------------------------------------------删除操作---------------------------------
	$('a[id=J-Delete1]').each(function(e){
		$(this).click(function(e){
			var tt = $(this).attr('name');
			var ids = tt.split("_");
			e.preventDefault();		
			minWindow.setTitle('温馨提示');
			minWindow.setContent($('#DeleteGroup').val());
			minWindow.show();
			
			$('#closeDd').click(function(e){
				minWindow.hide();
			});
			//提交发布确定操作
			$('#J-Delete-Butt1').click(function(e){			
				minWindow.hide();	
			
				var jsonStr = "";
				jsonStr += '{"lotteryId":';
				jsonStr += ids[0];
				jsonStr += ',"awardId":';
				jsonStr += ids[1];
				jsonStr += '}';
				
				var url = baseUrl + "/gameoa/delGameAwardGroup";
				var data = "delStr=" + jsonStr;
				
				sendUrl(url, data);
			});	
		});
	});
	
//	
//	initFn2 = function(lotteryid, awardId){	
//		//关闭弹窗
//		$('#closeDd').click(function(e){
//			minWindow.hide();
//		});
//		//提交删除操作，
//		$('#J-Delete-Butt1').click(function(e){			
//			minWindow.hide();	
//			//成功展示,自动隐藏			
//			if(true){
//				fnDiv('DivSuccessful');
//				setTimeout(function (){$("#DivSuccessful").css("display","none");},1500);
//			}else{//失败提示
//				fnDiv('DivFailed');
//				$(".close,.btn").click(function(e){
//					$("#DivFailed").css("display","none");
//				});		
//			}
//		});		
//	};
//	
	
	
	//操作后提示	 
	function fnDiv(obj){		
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	} 	
	
	function sendUrl(url,data){
		jQuery.ajax({
			type:  "post",
			url: url,
			data: data,
			success:function(data){
				if(data.status==1){
					operationSuccess();
				}else{
					operationFailure();
				}
			},
			error: function(){
				operationFailure();
			}
		});
	}
	
	function operationSuccess(){
		fnDiv('DivSuccessful');
		setTimeout(function (){$("#DivSuccessful").css("display","none");},1500);
		location.reload();
	}
	
	function operationFailure(){
		fnDiv('DivFailed');
		$(".close,.btn").click(function(e){
			$("#DivFailed").css("display","none");
		});	
	}
})();
		
	
