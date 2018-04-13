(function() {	
	var minWindow,mask,initFn,Wd = phoenix.Message.getInstance();	
	minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});		
	
	var lotteryId = $('#lotteryId').val();
	var url = $('#url').val();
	$("#J-select-lotteryid").find("option[value='"+lotteryId+"']").attr("selected",true);
	$("#J-select-lotteryid").change(function(){
		location.href = url + $("#J-select-lotteryid").val();
	});
	
	
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#issueRuleMenu').attr("class","current");
	
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
	
	$('#checkBoxId').click(function(){
		
		if($(this).is(':checked') == true){
			$('[id="invalidDiv"]').hide();
			$('[id="stopDiv"]').hide();
		}else{
			$('[id="invalidDiv"]').show();
			$('[id="stopDiv"]').show();
		}
	});
	
	//---------------------删除分段奖期时间示例效------------------------------------)
	$('a[id=DeleteSubmit1]').each(function(e){
		$(this).click(function(e){
		
			var tt = $(this).attr('name');
			var ids = tt.split("_");
			e.preventDefault();		
			minWindow.setTitle('温馨提示');
			minWindow.setContent($('#DeleteTip').val());
			minWindow.show();
	//		initFn();
			initFn_del(ids[0],ids[1]);
		});
		
	});
	/*initFn = function(){			
		$('#closeDs').click(function(e){
			minWindow.hide();
		});
		//提交删除操作，
		$('#J-submit-safePassword').click(function(e){			
			minWindow.hide();				
			//如果成功展示,自动隐藏
			if(!true){
				fnDiv('DivSuccessful');
				setTimeout(function (){$("#DivSuccessful").css("display","none");},1500);
			}else{//失败提示
				fnDiv('DivFailed');
				$(".close,.btn").click(function(e){
					$("#DivFailed").css("display","none");
				});		
			}
				
		});		
	};	*/
	
	function initFn_del(lotteryId, ruleId){
		$('#closeDs').click(function(e){
			minWindow.hide();
		});
		//提交删除操作，
		$('#J-submit-safePassword').click(function(e){			
			minWindow.hide();				
			
			//成功展示,自动隐藏	
			var jsonStr="";
			jsonStr += '{"lotteryId":';
			jsonStr += lotteryId;
			jsonStr += ',"ruleId":';
			jsonStr += ruleId;
			jsonStr += '}';
			
			var url = baseUrl + '/gameoa/delGameIssueRule';
			var data = "delGameIssue=" + jsonStr;
			sendUrl(url, data);
				
		});		
	}
	//---------------------审核通过------------------------------------
//	$('#AuditSubmit1').click(function(e){
	$('a[id=AuditSubmit1]').each(function(e){
		$(this).click(function(e){
			
			var tt = $(this).attr('name');
			var ids = tt.split("_");
			e.preventDefault();		
			minWindow.setTitle('温馨提示');
			minWindow.setContent($('#AuditTip').val());
			minWindow.show();
	//		initFn2();
			initFn_audit(ids[0],ids[2],ids[1]);
		});
	});
	
	
	function initFn_audit(lotteryId, ruleId, ruleType){	
		
		$('#closeDd').click(function(e){
			minWindow.hide();
		});
		//提交审核操作，
		$('#J-submit-Audit1').click(function(e){			
			minWindow.hide();				
			//成功展示,自动隐藏	
			var jsonStr="";
			jsonStr += '{"lotteryId":';
			jsonStr += lotteryId;
			jsonStr += ',"ruleId":';
			jsonStr += ruleId;
			jsonStr += ',"checkType":';
			jsonStr += ruleType;
			jsonStr += ',"checkResult":1';
			jsonStr += '}';
			
			var url = baseUrl + '/gameoa/auditGameIssueRule';
			var data = "auditGameIssue=" + jsonStr;
			sendUrl(url, data);
		});		
	}
	
	//---------------------审核不通过------------------------------------
//	$('#AuditSubmit1').click(function(e){
	$('a[id=AuditSubmit2]').each(function(e){
		$(this).click(function(e){
			
			var tt = $(this).attr('name');
			var ids = tt.split("_");
			e.preventDefault();		
			minWindow.setTitle('温馨提示');
			minWindow.setContent($('#AuditTip2').val());
			minWindow.show();
	//		initFn2();
			initFn_audit2(ids[0],ids[2],ids[1]);
		});
	});
	
	
	function initFn_audit2(lotteryId, ruleId, ruleType){	
		
		$('#closeDd').click(function(e){
			minWindow.hide();
		});
		//提交审核操作，
		$('#J-submit-Audit1').click(function(e){			
			minWindow.hide();				
			//成功展示,自动隐藏	
			var jsonStr="";
			jsonStr += '{"lotteryId":';
			jsonStr += lotteryId;
			jsonStr += ',"ruleId":';
			jsonStr += ruleId;
			jsonStr += ',"checkType":';
			jsonStr += ruleType;
			jsonStr += ',"checkResult":2';
			jsonStr += '}';
			
			var url = baseUrl + '/gameoa/auditGameIssueRule';
			var data = "auditGameIssue=" + jsonStr;
			sendUrl(url, data);
		});		
	}
	
	//操作后提示	 
	function fnDiv(obj){		
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	} 		
	//-----------------停止--------------------------
	
	$('a[id=cancalButton]').each(function(e){
		$(this).click(function(e){
			
			var tt = $(this).attr('name');
			var ids = tt.split("_");
			e.preventDefault();		
			minWindow.setTitle('温馨提示');
			minWindow.setContent($('#calcanTip').val());
			minWindow.show();
	//		initFn2();
			init_Fn_calcan(ids[0],ids[1]);
		});
	});
	
	function init_Fn_calcan(lotteryId, ruleId){
		
		$('#closeDd').click(function(e){
			minWindow.hide();
		});
		//提交审核操作，
		$('#J-submit-calcan1').click(function(e){			
			minWindow.hide();				
			
			var jsonStr = "";
			jsonStr += '{"lotteryId":';
			jsonStr += lotteryId;
			jsonStr += ',"ruleId":';
			jsonStr += ruleId;
			jsonStr += '}';
			
			var url = baseUrl + '/gameoa/cancelGameIssueRule';
			var data = "calGameIssue=" + jsonStr;
			sendUrl(url, data);
		});		
	}
	
	//----------------END------------------------- 
	
//	//============常规（特例）奖期修改===========
//	
//	//休市
//	$('#editCommonSpecialGame1').click(function(e){
//		e.preventDefault();		
//		var jsonStr = "";
//		jsonStr += '{"lotteryId":';
//		jsonStr += lotteryId;
//		jsonStr += ',"ruleId":';
//		jsonStr += $('#editRuleId3').val();
//		jsonStr += ',"status":';
//		jsonStr += $('#editRuleStatus3').val();
//		jsonStr += ',"ruleType":';
//		jsonStr += $('#eidtRuleType3').val();
//		jsonStr += "}";
//		var url = baseUrl + '/gameoa/preEditCommonGameIssue';
//		var data = "commonGameIssue=" + jsonStr;
//		sendUrl(url, data);
//	});
	
	
	//ajax
	function sendUrl(url,data){
		jQuery.ajax({
			type:  "post",
			url: url,
			data: data,
			success:function(data){
				if(data.status==1){
					operationSuccess();
				}else{
					//operationFailure();
					Wd.show({
						mask:true,
						content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data['message'] +'</h4></div></div>',
						cancelIsShow:true,
						cancelText:'关闭',
						cancelFun:function(){
							Wd.hide();
						}
					});
				}
			},
			error: function(){
				operationFailure();
			}
		});
	}
})();
