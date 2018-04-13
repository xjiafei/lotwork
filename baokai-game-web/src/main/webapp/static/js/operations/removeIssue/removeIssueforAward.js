$(document).ready(function(){
	jQuery.ajax({
			type: "GET",
			url: '/gameRemove/queryIssueRemovedTime',
			dataType :"json",
			async: false,
			success: function(data){
				$.each(data,function(key,val){
						checkRemoveDate(val.lotteryId,val.awardTime);		
					});
			},
			complete:function(){
				
			},
			error: function(xhr,status,errMsg){
				return false;
			}
		});
		
	function checkRemoveDate(lotteryId,awardTime){
		var lotteryName = {99101:"cqssc",99102:"jxssc",99103:"xjssc",99104:"tjssc",99105:"hljssc",99106:"llssc",99107:"shssl",99111:"jlffc",
							99112:"slmmc",99301:"sd115",99302:"jx115",99303:"gd115",99304:"cq115",99305:"ll115",99306:"sl115",99201:"bjkl8",
							99501:"jsk3",99502:"ahk3",99601:"jsdice",99602:"jldice1",99603:"jldice2",99108:"fc3d",99109:"p5",99401:"ssq"};
		var toDate = new Date();
		var gmt = new Date().getTimezoneOffset();
		toDate.setSeconds(gmt*60);
		var timeZone = toDate.getTime();
		
		if(awardTime != null && awardTime != "close"){
			var strTime = awardTime.split("T");
			var ymd = strTime[0].split("-");
			var mis = strTime[1].split(":");
			var removeDate = new Date(ymd[0],ymd[1]-1,ymd[2],mis[0],mis[1],mis[2]).getTime();
		
			if(timeZone >= removeDate){
				$("a[href$="+lotteryName[lotteryId]+"]").hide();
			}
		}
	}	
	});