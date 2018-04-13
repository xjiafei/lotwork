(function() {
	var lotteryId = $('#lotteryId').val();
	var msg = phoenix.Message.getInstance(),
	editConfirm = new phoenix.EditConfirm();
	var tomoDay = $('#tomoDay').val();
	$('#issueRuleMenu').attr("class","current");
	
	//----------------------------------时间加载开始-----------------------------------------------------------
	$("[name='times']").click(function(){
		var Dt = new phoenix.DatePicker({input:this, isShowTime:false});
			Dt.show();
	});
	
	
	$('#j_clean_button_a').click(function(){
		location.href = baseUrl + '/gameoa/queryGameIssues?lotteryId='+lotteryId
	});
	
	
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
		var jsonStr = "";
		jsonStr += '{"lotteryId":';
		jsonStr += $('#lotteryId').val();
		jsonStr += ',"start":"';
		jsonStr += startTimeStr;
		jsonStr += '","end":"';
		jsonStr += endTimeStr;
		jsonStr += '","type":';
		jsonStr+=1;
		jsonStr+='}';
		
		var url = baseUrl + '/gameoa/deleteGameIssues';
		var data = "deleteGameIssuesStr=" + jsonStr;

		var title = '请确认删除'+startTimeStr+'至'+endTimeStr+'的奖期';
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