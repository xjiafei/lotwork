(function() {
	var lotteryId = $('#lotteryId').val();
	var msg = phoenix.Message.getInstance(),
	editConfirm = new phoenix.EditConfirm();
	var tomoDay = $('#tomoDay').val();
	var maxIssuesDay = $('#maxIssuesDay').val();
	var maxIssueCode = $('#maxIssueCode').val();
	$('#issueRuleMenu').attr("class","current");
	
	//----------------------------------时间加载开始-----------------------------------------------------------
	$("[name='times']").click(function(){
		var Dt = new phoenix.DatePicker({input:this, isShowTime:false});
			Dt.show();
	});
	
	
	$('#j_clean_button_a').click(function(){
		location.href = baseUrl + '/gameoa/queryGameIssues?lotteryId='+lotteryId
	});
	
	var getStartWebIssueCode = function(){
		var startTimeStr = $('#J-date-exception-start').val();
		if(startTimeStr==''){
			return;
		}else{
			var startTime = convertDate2UnixTimestamp($('#J-date-exception-start').val());
			var jsonStr = "";
			jsonStr += '{"lotteryId":';
			jsonStr += $('#lotteryId').val();
			jsonStr += ',"showStartTime":';
			jsonStr += startTime;
			jsonStr+='}';
			
			var url = baseUrl + '/gameoa/getStartWebIssueCode';
			var data = "startWebIssueCodeStr=" + jsonStr;
			editConfirm.isFlag = false;		
			jQuery.ajax({
				type:  "post",
				url: url,
				data: data,
				success:function(data){
					if(data.status == 1){
						$('#startIssueCode').val(data.maxIssueCode);
					}
				}
			});
		}
	};
	
	$('#J-date-exception-end').click(getStartWebIssueCode);
	$('#J-date-exception-start').click(getStartWebIssueCode);
	
	$('#J_Submit_Button_a').click(function(){

		var startTimeStr = $('#J-date-exception-start').val();
		var endTimeStr = $('#J-date-exception-end').val();
		var startTime = convertDate2UnixTimestamp($('#J-date-exception-start').val());
		var endTime = convertDate2UnixTimestamp($('#J-date-exception-end').val());
		var startIssueCode = 0;
		if(startTimeStr==''||endTimeStr==''){
			msg.show({
				mask:true,
				content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">开始结束时间不能为空！</h4></div>',
				closeIsShow: true,
				closeFun : function(){
					this.hide();
				} 
			})
			return false;
		}
		
		
		
		
		
		if(startTime>endTime){	
			msg.show({
				mask:true,
				content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">开始时间不能大于结束时间！</h4></div>',
				closeIsShow: true,
				closeFun : function(){
					this.hide();
				} 
			})
			return false;
		}
		
		
		if(startTimeStr<tomoDay){
			msg.show({
				mask:true,
				content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">开始时间必须大于当天！</h4></div>',
				closeIsShow: true,
				closeFun : function(){
					this.hide();
				} 
			})
			return false;
		}
		var startIsssueNumber =1;
		if(lotteryId != 99108 && lotteryId != 99109 && lotteryId!=99401){
			startIssueCode = $('#startIssueCode').val();
			if(startIssueCode==''){
				msg.show({
					mask:true,
					content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">起始期号不能为空！</h4></div>',
					closeIsShow: true,
					closeFun : function(){
						this.hide();
					} 
				})
				return false;
			}
			/*if(lotteryId != 99105 && lotteryId != 99201){
				if($('#startIsssueNumber').is(':checked')==false){
					startIsssueNumber=2;
				}else{
					startIsssueNumber=1;
				}
			}*/
			
			
		}
		
		var jsonStr = "";
		jsonStr += '{"lotteryId":';
		jsonStr += $('#lotteryId').val();
		jsonStr +=',"startIsssueNumber":';
		jsonStr +=startIsssueNumber;
		jsonStr += ',"showStartTime":';
		jsonStr += startTime;
		jsonStr += ',"showEndTime":';
		jsonStr += endTime;
		jsonStr += ',"startIsssueCode":"';
		jsonStr+=startIssueCode;
		jsonStr+='"}';
		
		var url = baseUrl + '/gameoa/manualGenerateIssues';
		var data = "manualGenerateStr=" + jsonStr;
		if(maxIssuesDay==null||maxIssuesDay==''||startTimeStr>maxIssuesDay){
			sendUrl(url,data);
		}else{
			var title = '';
			if(endTimeStr>=maxIssuesDay){
				title+=startTimeStr+' 到  '+endTimeStr+' 的奖期中'+'</br>';
				title+='起始期'+' 至 ' +maxIssueCode +'已经存在</br>';
				title+='是否需要用新规则覆盖?';
				
			}else{
				title+='手动生成 '+startTimeStr+' 到  '+endTimeStr+' 的奖期时'+'</br>';
				title+='起始期'+' 至 ' +maxIssueCode +'已经存在</br>';
				title+='是否需要用新规则覆盖，并删除'+endTimeStr+'之后的奖期?';
			}

			msg.show({
				mask:true,
				title:'温馨提示',
				content:'<span class="color-red">'+title+'</span>',
				confirmIsShow:true,
				cancelIsShow:true,
				confirmFun:function(){
					sendUrl(url,data);
					msg.hide();
				},
				cancelFun:function(){
					msg.hide();
				}
			});
		}
		
		
	});
	
	//ajax
	function sendUrl(url,data){
		
		editConfirm.isFlag = false;		
		jQuery.ajax({
			type:  "post",
			url: url,
			data: data,
			success:function(data){
				if(data.status == 1){
					msg.show({
						mask:true,
						content : '<div class="pop-title"><i class="ico-success"></i><h4 class="pop-text">'+data.message+'</h4></div>',
						closeIsShow: true,
						closeFun : function(){
							this.hide();
						} 
					});
					maxIssuesDay = data.maxIssuesDay;
					maxIssueCode = data.maxIssueCode;
				}else{	
					msg.show({
						mask:true,
						content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+data.message+'</h4></div>',
						closeIsShow: true,
						closeFun : function(){
							this.hide();
						} 
					});

				}
			},
			error: function(){
				operationFailure();
			}
		});
	}
	
	function operationFailure(){
		fnDiv('DivFailed');
		$(".close,.btn").click(function(e){
			$("#DivFailed").css("display","none");
		});	
	}
	//操作后提示	 
	function fnDiv(obj){		
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	 }
	

})();