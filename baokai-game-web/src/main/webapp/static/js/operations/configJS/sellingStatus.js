(function() {
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#soldMenu').attr("class","current");
	var inputStart = $('#J-time-start');
	
	
	inputStart.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
		d.addEvent('afterSetValue', function(){
			//setSelect(0);
			//selectTime.val(0);
		});
	});
	var table = $('table.table-info'),
		message = phoenix.Message.getInstance(),
		gameType = $('#J-game-closebutton'),
		ACTION = {};

	table.bind('click',function(e){
		doAction(e);
	});
	
	var lotteryid = $('#lotteryid').val();
	var status = $('#status').val();
	var checkStatus = $('#checkStatus').val();
	
	function getJsonStr(groupId, setId, methodId, methodType){
		var count1 = 0;
		var count2 = 0;
		$("input:hidden", document.forms[0]).each(function(i,val){
			if(this.name=='modifyParamsHidden'){
				var codeArr = this.value.split("_");
				if(methodType == 1){
					if(groupId == codeArr[0]){
						count1++;
					}
				}else if(methodType == 2){
					if(groupId == codeArr[0] && setId == codeArr[1]){
						count2++;
					}
				}
			}
		});
		
		var jsonStr = "";
		jsonStr += '{"lotteryid":';
		jsonStr += lotteryid;
		jsonStr += ',"status":';
		jsonStr += status;
		jsonStr += ',"checkStatus":';
		jsonStr += checkStatus;
		jsonStr += ',"betMethodStatusStruc":[';
		var arr = [];
		//var aInputArray = $('input:hidden', document.forms[0]);
		var count11 = 0;
		var count22 = 0;
		
		$("input:hidden", document.forms[0]).each(function(i,val){
			if(this.name=='modifyParamsHidden'){
				arr = this.value.split("_");
				if(methodType == 1){
					if(groupId == arr[0]){
						count11++;
						
						jsonStr += '{"gameGroupCode":';
						jsonStr += arr[0];
						jsonStr += ',';
						jsonStr += '"gameSetCode":';
						jsonStr += arr[1];
						jsonStr += ',';
						jsonStr += '"betMethodCode":';
						jsonStr += arr[2];
						jsonStr += ',';
						jsonStr += '"status":';
						jsonStr += arr[5] == 1 ? 0 : 1;
						jsonStr += '}';
						
						if(count11 != count1){
							jsonStr += ',';
						}
					}
				}else if(methodType == 2){
					if(groupId == arr[0] && setId == arr[1]){
						count22++;
						
						jsonStr += '{"gameGroupCode":';
						jsonStr += arr[0];
						jsonStr += ',';
						jsonStr += '"gameSetCode":';
						jsonStr += arr[1];
						jsonStr += ',';
						jsonStr += '"betMethodCode":';
						jsonStr += arr[2];
						jsonStr += ',';
						jsonStr += '"status":';
						jsonStr += arr[4] == 1 ? 0 : 1;
						jsonStr += '}';
						
						if(count22 != count2){
							jsonStr += ',';
						}
					}
				}else if(methodType == 3){
					if(groupId == arr[0] && setId == arr[1] && methodId == arr[2]){
						jsonStr += '{"gameGroupCode":';
						jsonStr += arr[0];
						jsonStr += ',';
						jsonStr += '"gameSetCode":';
						jsonStr += arr[1];
						jsonStr += ',';
						jsonStr += '"betMethodCode":';
						jsonStr += arr[2];
						jsonStr += ',';
						jsonStr += '"status":';
						jsonStr += arr[3] == 0 ? 1 : 0;
						jsonStr += '}';
					}
				}
				
			}
		});
		jsonStr += ']}';
		
		return jsonStr;
	}
	
	function getStatus(groupId, setId, methodId, methodType){
		var status = 0;
		var arr = [];
		$("input:hidden", document.forms[0]).each(function(i,val){
			if(this.name=='modifyParamsHidden'){
				arr = this.value.split("_");
				if(methodType == 1){
					if(groupId == arr[0]){
						status = arr[5] == 1 ? 1 : 0;
					}
				}else if(methodType == 2){
					if(groupId == arr[0] && setId == arr[1]){
						status = arr[4] == 1 ? 1 : 0;
					}
				}else if(methodType == 3){
					if(groupId == arr[0] && setId == arr[1] && methodId == arr[2]){
						status = arr[3] == 1 ? 1 : 0;
					}
				}
				
			}
		});
		
		return status;
	}

	ACTION.doActionqun = function(el){
		var codeArr = codes.split("_");
		
		var content = '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">';
		content += getStatus(codeArr[0], 0, 0, 1) == 0 ? '销售' : '停售';
		content += '玩法群，该玩法群下的玩法组、玩法将同时';
		content += getStatus(codeArr[0], 0, 0, 1) == 0 ? '销售' : '停售';
		content += '。您确定要';
		content += getStatus(codeArr[0], 0, 0, 1) == 0 ? '销售' : '停售';
		content += '所选玩法群吗？</h4></div>';
		
		message.show({
			title:'温馨提示',
			mask : true,
			content: content,
			confirmText: '确认',
			confirmIsShow: true,
			confirmFun: function(){
				var resultStr = getJsonStr(codeArr[0], 0, 0, 1);
				var url = currentContextPath + "/gameoa/modifySellingStatus";
				var data = resultStr;
				var toUrl = currentContextPath + "/gameoa/toAuditSellingStatus?lotteryid="+lotteryid;
				sendUrl(url, data, toUrl);
			},
			closeText: '关闭',
			closeIsShow: true,
			closeFun: function(){
				//var name = el.html();
				var me =this;
				me.hide();
				
				
				
			}
		})
	}

	ACTION.doActionzu = function(el){
		var codeArr = codes.split("_");
		
		var content = '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">';
		content += getStatus(codeArr[0], codeArr[1], 0, 2) == 0 ? '销售' : '停售';
		content += '玩法组，该玩法组下的玩法将同时';
		content += getStatus(codeArr[0], codeArr[1], 0, 2) == 0 ? '销售' : '停售';
		content += '。您确定要';
		content += getStatus(codeArr[0], codeArr[1], 0, 2) == 0 ? '销售' : '停售';
		content += '所选玩法组吗？</h4></div>';
		
		message.show({
			title:'温馨提示',
			mask : true,
			content: content,
			confirmText: '确认',
			confirmIsShow: true,
			confirmFun: function(){
				
				
				var resultStr = getJsonStr(codeArr[0], codeArr[1], 0, 2);
				var url = currentContextPath + "/gameoa/modifySellingStatus";
				var data = resultStr;
				var toUrl = currentContextPath + "/gameoa/toAuditSellingStatus?lotteryid="+lotteryid;
				sendUrl(url, data, toUrl);
			},
			closeText: '关闭',
			closeIsShow: true,
			closeFun: function(){
				
				var me =this;
				me.hide();
				
			}
		})
	}

	ACTION.doActiondan = function(el){
		var codeArr = codes.split("_");
		
		var content = '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确定';
		content += getStatus(codeArr[0], codeArr[1], codeArr[2], 3) == 0 ? '销售' : '停售';
		content += '该彩种吗？</h4></div>';
		
		message.show({
			title:'温馨提示',
			mask : true,
			content: content,
			confirmText: '确认',
			confirmIsShow: true,
			confirmFun: function(){
				var resultStr = getJsonStr(codeArr[0], codeArr[1], codeArr[2], 3);
				var url = currentContextPath + "/gameoa/modifySellingStatus";
				var data = resultStr;
				var toUrl = currentContextPath + "/gameoa/toAuditSellingStatus?lotteryid="+lotteryid;
				sendUrl(url, data, toUrl);
			},
			closeText: '关闭',
			closeIsShow: true,
			closeFun: function(){
				//var name = el.html();
				var me =this;
				me.hide();
			}
		})
	}
	
	var codes = 0;

	var doAction = function(e){
		var el = $(e.target);
		
		codes = el.attr('code');
		
		if(el.attr('data-param') == 'undefined'){return};
		var actionNameText = $(e.target).attr('data-param'),
			actionFunText = 'doAction' + actionNameText;  
		if(ACTION[actionFunText] && $.isFunction(ACTION[actionFunText])){
			ACTION[actionFunText](el);
		}
	}


	gameType.bind('click', function(){
		var content = '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确定';
		content += status==0 ? '销售' : '停售';
		content += '该彩种吗？</h4></div>';
		
		message.show({
			title:'温馨提示',
			mask : true,
			content: content,
			confirmText: '确认',
			confirmIsShow: true,
			confirmFun: function(){
				var paramStr = getParamStr();
				var url = currentContextPath + "/gameoa/modifySellingStatus";
				var data = paramStr;
				var toUrl = currentContextPath + "/gameoa/toAuditSellingStatus?lotteryid="+lotteryid;
				sendUrl(url, data, toUrl);
			},
			closeText: '关闭',
			closeIsShow: true,
			closeFun: function(){
				var me =this;
				me.hide();
			}
		})
	}) 
	
	
	function getParamStr(){
		var jsonStr = "";
		jsonStr += '{"lotteryid":';
		jsonStr += lotteryid;
		jsonStr += ',"status":';
		jsonStr += status==0 ? 1 : 0;
		jsonStr += ',"checkStatus":';
		jsonStr += checkStatus;
		jsonStr += ',"takeOffTime":';
		jsonStr += '"'+$('#J-time-start').val()+'"'; //彩種下架時間
		jsonStr += ',"betMethodStatusStruc":[';
		var arr = [];
		var aInputArray = $('input:hidden', document.forms[0]);
		
		$("input:hidden", document.forms[0]).each(function(i,val){
			
			if(this.name=='modifyParamsHidden'){
				arr = this.value.split("_");
				
				jsonStr += '{"gameGroupCode":';
				jsonStr += arr[0];
				jsonStr += ',';
				jsonStr += '"gameSetCode":';
				jsonStr += arr[1];
				jsonStr += ',';
				jsonStr += '"betMethodCode":';
				jsonStr += arr[2];
				jsonStr += ',';
				jsonStr += '"status":';
				jsonStr += arr[3];
				jsonStr += '}';
				
				if(i != (aInputArray.size()-1)){
					jsonStr += ',';
				}
			}
			
		});
		
		jsonStr += ']}';
		
		return jsonStr;
	}
	
	var inputTip = new phoenix.Tip.getInstance();
	$('body').on('mouseover', '.input-mark', function(){
		var dom = $(this),text = dom.attr('data-showtip');
		if(text){
			inputTip.setText(dom.attr('data-showtip'));
			inputTip.show(dom.outerWidth() + 12, dom.outerHeight()*-1 + 4, this);
		}
	}).on('mouseout', '.input-mark', function(){
		var text = this.getAttribute('data-showtip');
		if(text){
			inputTip.hide();
		}
	});
	
	$('#J-Audit-Butt1').click(function(e){		
		message.show({
			title:'温馨提示',
			mask : true,
			content:'<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确认审核通过当前销售状态吗？</h4></div>',
			confirmText: '确认',
			confirmIsShow: true,
			confirmFun: function(){
			
				
				var url = currentContextPath + "/gameoa/auditSellingStatus";
				var jsonStr = "";
				jsonStr += '{"lotteryid":';
				jsonStr += lotteryid;
				jsonStr += ',"auditType":1';
				jsonStr += '}';
				var data = jsonStr;
				var toUrl = currentContextPath + "/gameoa/toSellingStatus?lotteryid="+lotteryid;
				sendUrl(url, data, toUrl);
			},
			closeText: '关闭',
			closeIsShow: true,
			closeFun: function(){
				var me =this;
				me.hide();
			}
		})
	});		
	
	$('#J-Audit-Butt2').click(function(e){		
		message.show({
			title:'温馨提示',
			mask : true,
			content:'<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确认审核不通过当前销售状态吗？</h4></div>',
			confirmText: '确认',
			confirmIsShow: true,
			confirmFun: function(){
				
				var url = currentContextPath + "/gameoa/auditSellingStatus";
				var jsonStr = "";
				jsonStr += '{"lotteryid":';
				jsonStr += lotteryid;
				jsonStr += ',"auditType":2';
				jsonStr += '}';
				var data = jsonStr;
				var toUrl = currentContextPath + "/gameoa/toSellingStatus?lotteryid="+lotteryid;
				sendUrl(url, data, toUrl);
			},
			closeText: '关闭',
			closeIsShow: true,
			closeFun: function(){
				var me =this;
				me.hide();
			}
		})
	});		
	
	$('#J-button-publish').click(function(e){		
		message.show({
			title:'温馨提示',
			mask : true,
			content:'<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确认发布当前销售状态吗？</h4></div>',
			confirmText: '确认',
			confirmIsShow: true,
			confirmFun: function(){
				
				
				var url = currentContextPath + "/gameoa/publishSellingStatus";
				var jsonStr = "";
				jsonStr += '{"lotteryid":';
				jsonStr += lotteryid;
				jsonStr += ',"publishType":1';
				jsonStr += '}';
				var data = jsonStr;
				var toUrl = currentContextPath + "/gameoa/toSellingStatus?lotteryid="+lotteryid;
				sendUrl(url, data, toUrl);
			},
			closeText: '关闭',
			closeIsShow: true,
			closeFun: function(){
				var me =this;
				me.hide();
			}
		})
	});		
	
	$('#J-button-publish2').click(function(e){		
		message.show({
			title:'温馨提示',
			mask : true,
			content:'<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确认发布不通过当前销售状态吗？</h4></div>',
			confirmText: '确认',
			confirmIsShow: true,
			confirmFun: function(){
				
				
				var url = currentContextPath + "/gameoa/publishSellingStatus";
				var jsonStr = "";
				jsonStr += '{"lotteryid":';
				jsonStr += lotteryid;
				jsonStr += ',"publishType":2';
				jsonStr += '}';
				var data = jsonStr;
				var toUrl = currentContextPath + "/gameoa/toSellingStatus?lotteryid="+lotteryid;
				sendUrl(url, data, toUrl);
			},
			closeText: '关闭',
			closeIsShow: true,
			closeFun: function(){
				var me =this;
				me.hide();
			}
		})
	});		
	
	function sendUrl(url,data,toUrl){
		jQuery.ajax({
			type:  "post",
			url: url,
			dataType:'json', 
			contentType: "application/json; charset=utf-8",
			data: data,
			cache: false,
			success:function(data){
				if(data.status==1){
					//提交成功刷新页面
					//window.location.reload();
					location.href = toUrl;
				}else{
					
				}
			},
			error: function(){
				
			}
		});
	}
	
})();
