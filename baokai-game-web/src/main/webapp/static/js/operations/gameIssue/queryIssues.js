(function() {	
	var minWindow,mask,initFn;	
	minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	var msg = phoenix.Message.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});	
	
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#issueRuleMenu').attr("class","current");
	//----------------------------------时间加载开始-----------------------------------------------------------
	var inputTimeStart = $("#timesStart"),inputTimeEnd=$('#timesEnd'),form = $('#J-form');
	inputTimeStart.click(function(){
		var Dt = new phoenix.DatePicker({input:this, isShowTime:true});
			Dt.show();
	});	
	inputTimeEnd.click(function(){
		var Dt = new phoenix.DatePicker({input:this, isShowTime:true});
			Dt.show();
	});	
	
	//------------------------时间加载结束----------------------------------------------
	
	if($('#message').val() != ''){
		msg.show({
			mask:true,
			content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+$('#message').val()+'</h4></div>',
			closeIsShow: true,
			closeFun : function(){
				this.hide();
			} 
		})
	}
	
	//回显
	var queryType = $('#queryType').val();
	if(queryType == 1){
		$('#queryTypeId').attr("checked",true);
		if($('#checkAudit').val()!=null && $('#checkAudit').val()!=''&& $('#checkAudit').val()==1){
			$('#queryTypeId_1').attr("checked",true);
		}
	}else if(queryType == 3){
		$('#queryTypeId_1').attr("checked",true);
	}
/*	var $radios = $('input:radio[name=radios]');
	if($radios.is(':checked') === false) {
		$radios.filter("[value="+queryType+"]").prop('checked', true);
    }*/
	
	
	if($('#showStartTime').val() != ''){
		$('#timesStart').val(formatDate(new Date(Number($('#showStartTime').val()))));
	}
	if($('#showStartTime').val() != ''){
		
		$('#timesEnd').val(formatDate(new Date(Number($('#showEndTime').val()))));
	}
	
	
	//表单提交校验
	$('#J-Submit').click(function(){
		var _timeStart = ($.trim($('#timesStart').val())),
			_timeEnd =  ($.trim($('#timesEnd').val()));
		if( _timeStart > _timeEnd ){
			
			msg.show({
				mask:true,
				content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">过去奖期查询结束时间不得晚于当前起始时间</h4></div>',
				closeIsShow: true,
				closeFun : function(){
					this.hide();
				} 
			})
			return false;
		}
		form.submit();	
	});	
	
	form.submit(function(e){
		if($('#timesStart').val() =='' || $('#timesEnd').val()==''){ 
			e.preventDefault(); 
			alert('时间段不能为空');
			return false;
		}else{
			$('#showStartTime').attr('value',convertDate2UnixTimestamp($('#timesStart').val()));
			$('#showEndTime').attr('value', convertDate2UnixTimestamp($('#timesEnd').val()));
			var queryType=0;
			if($('#queryTypeId').is(':checked')){
				queryType=1;
				if($('#queryTypeId_1').is(':checked')){
					$('#checkAudit').val(1);
				}else{
					$('#checkAudit').val(0);
				}
			}else{
				$('#checkAudit').val(0);
				if($('#queryTypeId_1').is(':checked')){
					queryType=3;
				}else{
					queryType=2;
				}
			}
			
			$('#queryType').attr('value',queryType);
			return true;
		}
	});
	
	
	
	//日期控件跟随最外层容器滚动条滚动位置
	$('.col-main').scroll(function(){
		var dom = $(this),datepicker = $('.j-ui-datepicker '),oldTop = Number(datepicker.attr('oldTop'));
		if(isNaN(oldTop)){
			datepicker.attr('oldTop', oldTop = parseInt(datepicker.css('top')));
		}
		datepicker.css('top', oldTop - dom.scrollTop());
	});
	
			
	
	GetDateStr = function(AddDayCount,time){
		var dd = new Date();
		dd.setDate(dd.getDate()+AddDayCount);
		var y = dd.getFullYear(),
			m = dd.getMonth()+1 <= 9 ? "0" + (dd.getMonth() + 1) : dd.getMonth(),
			d = dd.getDate() <= 9 ? "0" + (dd.getDate()) : dd.getDate();
		return y + "-" + m + "-" + d + time;
	} 
	
	
	showChangeAwardTime = function(Id){
		
		$("#AwardTimeBtn"+Id).hide();
		
		$("#AwardTimediv"+Id).css('display','inline').show();
		$("#openAwardTime"+Id).click(function(){
			var Dt = new phoenix.DatePicker({input:this, isShowTime:true});
				Dt.show();
		});	

	}
	
	extend = function(issueCode){
		
		
		
				$.ajax({
		            url:'/gameoa/getNextOpenAwardTime',
		            method: "post",
		            data:{'lotteryId':$("#lotteryId").val(),'issueCode':issueCode},
		            type:'json',
		            
		            success:function(data){
		            	var message='';
		            	if(data.responseStatus==1){
		            		$("#id").val(data.id);
		            		$("#nextId").val(data.nextId);
		            		var msg = "将"+data.webIssueCode+"期的开奖日期从"+data.currentOpenDrawTime+
		            				", 延至"+data.nextOpenDrawTime+"。" +data.nextWebIssueCode+"期之后的所有奖期将依次顺延 , 是否确定做此操作?";
			        		
		            		$("#extendCheckMsg .pop-text").html(msg);
		            		fnDiv('extendCheckMsg','');
		            	
		            	}else{
		            		$("#extendCheckMsg .pop-text").html(data.message);
		            		fnDiv('DivMessage','');
		            	}
	            		           		
		            	
		            },
		           
		            error: function () {
		            	$("#DivMessage .pop-text").html("取得下期开奖时间失败");
	            		fnDiv('DivMessage','');
		            }
		        });
		
	}
	
	changeAwardTime = function(Id,webIssueCode,issueCode,issueId){
		
		var openAwardTime =  $("#openAwardTime"+Id).val();
		
		if(openAwardTime!=''){
		
			$.ajax({
	            url:'/gameoa/updateOpenAwardTime',
	            method: "post",
	            data:{'webIssueCode':webIssueCode,'openAwardTime':openAwardTime,'lotteryId':$("#lotteryId").val(),'issueCode':issueCode,'id':issueId},
	            type:'json',
	            
	            success:function(data){
	            	var message='';
	            	if(data.responseStatus==1){
	            		$("#AwardTimeBtn"+Id).show();
		        		$("#AwardTimediv"+Id).hide();
		        		$("#openAwardTimeTd"+Id).html(openAwardTime);
		        		$("#saleEndTimeTd"+Id).html(data.newSaleEndTime);
		        		if($("#saleStartTimeTd"+(Id+1))!=null)
		        		$("#saleStartTimeTd"+(Id+1)).html(data.newNextSaleStartTime);
		        		
	            	}
	            	$("#DivMessage .pop-text").html(data.message);
            		fnDiv('DivMessage',Id);
            		           		
	            	
	            },
	           
	            error: function () {
	            	$("#DivMessage .pop-text").html("开奖时间调整失败");
            		fnDiv('DivMessage',Id);
	            }
	        });
		}else{
			$("#AwardTimeBtn"+Id).show();
    		$("#AwardTimediv"+Id).hide();
		}
		
		
	}
	
	$("#doExtend").click(function(e){
		$.ajax({
            url:'/gameoa/doExtendOpenAwardTime',
            method: "post",
            data:{'id':$("#id").val(),'nextId':$("#nextId").val(),'lotteryId':$("#lotteryId").val()},
            type:'json',
            
            success:function(data){
            	var message='';
            	if(data.responseStatus==1){
            		form.submit();
            	}else{
            		$("#extendCheckMsg").hide();
                	$("#DivMessage .pop-text").html("展延开奖时间调整失败");
            		fnDiv('DivMessage','');
            	}
            },
           
            error: function () {
            	$("#extendCheckMsg").hide();
            	$("#DivMessage .pop-text").html("展延开奖时间调整失败");
        		fnDiv('DivMessage','');
            }
        });
		
		//location.reload(); 
	
	});	
	
	//操作后提示	 
	function fnDiv(obj,Id){		
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";		
		
		//Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		//Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";

		Idivdok.style.left = 300+"px";
		Idivdok.style.top = 200+"px"; 
	} 	
	
	$("#close,#close2").click(function(e){
		$("#DivMessage").hide();
		$("#extendCheckMsg").hide();
		

	});	
	$(".close").click(function(e){
		$("#DivMessage").hide();
		$("#extendCheckMsg").hide();
	});	

	
})();