$(document).ready(function(){
	
	jQuery.ajax({
			type: "GET",
			url: '/gameRemove/queryIssueRemovedTime',
			dataType :"json",
			async: false,
			success: function(data){
				$.each(data,function(key,val){
						checkRemoveDate(val.lotteryId,val.orderTime);		
					});
			},
			complete:function(){
				
			},
			error: function(xhr,status,errMsg){
				return false;
			}
		});
		
	function checkRemoveDate(lotteryId,orderTime){
		var toDate = new Date();
		var gmt = new Date().getTimezoneOffset();
		toDate.setSeconds(gmt*60);
		var timeZone = toDate.getTime();
		
		if(orderTime != null){
			var strTime = orderTime.split("T");
			var ymd = strTime[0].split("-");
			var mis = strTime[1].split(":");
			var removeDate = new Date(ymd[0],ymd[1]-1,ymd[2],mis[0],mis[1],mis[2]).getTime();
			
			if(timeZone >= removeDate && orderTime != "close" ){
				$("option[value="+lotteryId+"]").hide();
			}
		}
	}	
	});