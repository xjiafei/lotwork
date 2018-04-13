(function() {
	var minWindow,mask,initFn;	
	minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	var msg = phoenix.Message.getInstance(),
		editConfirm = new phoenix.EditConfirm();
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
	//Tab
	new phoenix.Tab({triggers:'.ui-tab-title2 li',panels:'.ui-tab-content',eventType:'click',currPanelClass:'ui-tab-content-current',index:0});
	
	var lotteryId = $('#lotteryId').val();
	
	//----------------------------------时间加载开始-----------------------------------------------------------
	$("[name='times']").click(function(){
		var Dt = new phoenix.DatePicker({input:this, isShowTime:true});
			Dt.show();
	});
	
	//------------------------时间加载结束----------------------------------------------
	
	
	//删除分段奖期时间示例效果()
	$('body').on('click', '.delete-close', function(e){
		e.preventDefault();
		var dom = $(this).parents('.break-time').eq(0);
		minWindow.setTitle('温馨提示');
		minWindow.setContent($('#DeleteSegmented').val());
		minWindow.show();
		initFn(dom);
		
	});
	initFn = function(dom){	
		//关闭弹窗
		$('#closeDs').click(function(e){
			minWindow.hide();
		});
		//提交删除操作，
		$('#J-submit-safePassword').click(function(e){			
			minWindow.hide();	
			dom.remove();			
			
			//成功展示,自动隐藏
			fnDiv('DivSuccessful');
			setTimeout(function (){$("#DivSuccessful").css("display","none");},1500);	
		});		
	};
	
	//修改分段奖期时间(系统检测到您已经设置过一条常规规则了)
	// $('#J_Submit_Button').click(function(e){
			
	// 	e.preventDefault();		
	// 	minWindow.setTitle('温馨提示');
	// 	minWindow.setContent($('#UpdateSegmented').val());
	// 	minWindow.show();	
	// 	initFns();	
	// });
	// initFns = function(){	
	// 	//关闭弹窗
	// 	$('#closeDd').click(function(e){
	// 		minWindow.hide();
	// 	});
	// };
	
	function operationSuccess(){
		fnDiv('DivSuccessful');
		setTimeout(function (){$("#DivSuccessful").css("display","none");},1500);
//		location.reload();
		location.href = baseUrl +'/gameoa/gameIssueIndex?lotteryId='+ lotteryId +'&ruleId=';
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
	 
	//数字校验，自动矫正不符合数学规范的数学(除了特例奖期规则名称，休市/维护时间名称)
	var inputs = $('.break-time').find('input:not([name="names"])'),checkFn;				
	checkFn = function(){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/^\.$/g, '');			
		index = v.indexOf('.');
		if(index >= 0){
			me.value = v = '0';				
		}
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0');		
							
	};	
	inputs.keyup(checkFn);
	inputs.blur(checkFn);
	
	//日期控件跟随最外层容器滚动条滚动位置
	$('.col-main').scroll(function(){
		var dom = $(this),datepicker = $('.j-ui-datepicker '),oldTop = Number(datepicker.attr('oldTop'));
		if(isNaN(oldTop)){
			datepicker.attr('oldTop', oldTop = parseInt(datepicker.css('top')));
		}
		datepicker.css('top', oldTop - dom.scrollTop());
	});

	$('#AddRule, #Addrule_a').click(function(){
		var html = $('#addbreaktimeHtml').val(),
			parent = $(this).parents('ul').eq(0);

			parent.find('.ui-btn').before(html)		
	});
	
	$('#j_clean_button_a,#j_clean_button_b').click(function(){
		location.href = baseUrl + '/gameoa/gameIssueIndex?lotteryId='+lotteryId+'&ruleId='
	});
	
//	$('#ruleTypeUlId li').click(function(e){
//		var v = $(this).attr('value');
//		$('#currentRuleTypeId').attr('value', v);
//	});
	
	$("input[name=checkbox1]").each(function(i,v){
		$(this).click(function(e){
			$("input[name='checkbox1'][value="+i+"]").attr('checked','checked');
		});
	});
	
	//转换Unix timestamp(h,m,s)
	convert2Sec = function(h,m,s){
		var da=new Date();
		var str =da.getFullYear()+"/"+(da.getMonth()+1)+"/"+da.getDate()+" "+h+":"+m+":"+s; 
		var getDd =  new Date(str);
		return  Math.round(getDd.getTime());		
		
	}	

		
	convert2Arr = function(v){
		var _v = '';
		var j = 2;
		var k = 0;
		for(var i = 0; i< v.toString().length; i++){
			if(i%2==0){
				_v += v.toString().substring(k,j);
				_v += ',';
				j+=2;
				k+=2;
			}
		}
		_v = _v.toString().substring(0, _v.toString().length -1);
		return _v.split(',');
		
	}
	
	$('#J_Submit_Button_b').click(function(){
		//休市/维护
		var issueName = $('#X_IssueName').val();
		
		if(issueName == ''){
			msg.show({
				mask:true,
				content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">休市/维护时间名称 未填写请检查！</h4></div>',
				closeIsShow: true,
				closeFun : function(){
					this.hide();
				} 
			})
			return false;
		}
		
		var _maintenanceTimeStarts = $('#maintenanceTimeStarts').val();
		var _maintenanceTimeEnd = $('#maintenanceTimeEnd').val();
		var start=new Date(_maintenanceTimeStarts.replace("-", "/").replace("-", "/")),
			end=new Date(_maintenanceTimeEnd.replace("-", "/").replace("-", "/"));
		if(_maintenanceTimeStarts == '' || _maintenanceTimeEnd ==''){
			
			msg.show({
				mask:true,
				content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">休市/维护时间段 未填写请检查！</h4></div>',
				closeIsShow: true,
				closeFun : function(){
					this.hide();
				} 
			})
			
			return false;
		}	
		if(end < start)
		{
			
			msg.show({
				mask:true,
				content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">结束执行时间早于开始执行时间！</h4></div>',
				closeIsShow: true,
				closeFun : function(){
					this.hide();
				} 
			})
			
			return false;
		}
		var period3 = '';
		var k = 1;
		$("input[name=checkbox3]").each(function(i,v){
			if($('input[name=checkbox3][value='+k+']').is(':checked')==true){
				period3 += $('input[name=checkbox3][value='+k+']').val();
				period3 += ',';
			}
			k = k+1;
		});
		
		
		//官方第一期开奖时间
		//时
		var _firstH = $("select[name=firstStopH2]").find("option:selected").text();
		if(_firstH == ''){
			_firstH = '00';
		}
		_firstH = convert2Arr(_firstH);
		//分
		var _firstM = $("select[name=firstStopM2]").find("option:selected").text();
		if(_firstM == ''){
			_firstM = '00';
		}
		_firstM = convert2Arr(_firstM);
		//秒
		var _firstS = $("select[name=firstStopS2]").find("option:selected").text();
		if(_firstS == ''){
			_firstS = '00';
		}
		_firstS = convert2Arr(_firstS);
		//官方最后一期开奖时间
		var _lastH = $("select[name=lastStopH2]").find("option:selected").text();
		if(_lastH == ''){
			_lastH = '00';
		}
		_lastH = convert2Arr(_lastH);
		var _lastM = $("select[name=lastStopM2]").find("option:selected").text();
		if(_lastM == ''){
			_lastM = '00';
		}
		_lastM = convert2Arr(_lastM);
		var _lastS = $("select[name=lastStopS2]").find("option:selected").text();
		if(_lastS == ''){
			_lastS = '00';
		}
		_lastS = convert2Arr(_lastS);
		/*if((_firstH[0] == '00' && _firstM[0] == '00' && _firstS[0] == '00') || (_lastH[0] == '00' && _lastM[0] == '00' && _lastS[0] == '00')){
			msg.show({
				mask:true,
				content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">休市开始结束时间（时-分-秒）不能全为0！</h4></div>',
				closeIsShow: true,
				closeFun : function(){
					this.hide();
				} 
			})
			return false;
		}*/
		var stopStartTime=convert2Sec(_firstH[0],_firstM[0],_firstS[0]);
		var stopEndTime=convert2Sec(_lastH[0],_lastM[0],_lastS[0]);
		var seriesIssueCode=1;
		if($('#seriesIssueCode').is(':checked')==false){
			seriesIssueCode=0;
		}
		
		var jsonStr = "";
		jsonStr += '{"lotteryId":';
		jsonStr += $('#lotteryId').val();
		jsonStr += ',"seriesIssueCode":';
		jsonStr += seriesIssueCode;
		jsonStr += ',"issueRulesName":"';
		jsonStr += issueName;
		jsonStr += '","ruleStartTime":';
		jsonStr += convertDate2UnixTimestamp(_maintenanceTimeStarts);
		jsonStr += ',"ruleEndTime":';
		jsonStr += convertDate2UnixTimestamp(_maintenanceTimeEnd);
		jsonStr +=',"stopStartTime":';
		jsonStr += stopStartTime;
		jsonStr +=',"stopEndTime":';
		jsonStr += stopEndTime;
		jsonStr += ',"openAwardPeriod":"';
		jsonStr +=	period3;
		jsonStr += '","operationType":"1"}';
		
		url = baseUrl + '/gameoa/addOrUdapteStopGameIssueRule';
		data = "reqData=" + jsonStr;
		
		sendUrl(url, data);
	});

	$('#J_Submit_Button').click(function(){
		var parent = $(this).parents('ul').eq(0);
			saleWaitTime = parent.find('.sale-wait-time'),
			saleTime = parent.find('.sale-time');
			saleWaitTime.each(function(i){
				if($(this).val() == ''){
					msg.show({
						mask:true,
						content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">等待开奖时间 未填写请检查！</h4></div>',
						closeIsShow: true,
						closeFun : function(){
							this.hide();
						} 
					})
				}
				
			})

			if(lotteryId!=99108 && lotteryId!=99109 && lotteryId!=99401 && lotteryId!=99701){
				saleTime.each(function(i){
					if($(this).val() == ''){
						msg.show({
							mask:true,
							content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">销售周期时间 未填写请检查！</h4></div>',
							closeIsShow: true,
							closeFun : function(){
								this.hide();
							}
						})
					}
				})
			}
			
			//发送数据
			var jsonStr ="";
			var url = '';
			var data = '';
			
			var ruleType = $('#currentRuleTypeId').val();
			
			//常规规则
			var period = '';
			var u = 1;
			$("input[name=checkbox1]").each(function(i,v){
				if($('input[name=checkbox1][value='+u+']').is(':checked')==true){
					$('input[name=checkbox1][value='+u+']').val();
					period += ',';
				}
				u=u+1;
			});
			
			jsonStr += '{"lotteryId":';
			jsonStr += lotteryId;
			jsonStr += ',"ruleStartTime":';
			jsonStr += convertDate2UnixTimestamp($('#J-date-start').val());
			jsonStr += ',"openAwardPeriod":"';
			jsonStr += period+'"';
			
			//操作类型
			jsonStr += ',"ruleType":1,"operationType":1';//1新增，2 修改
			
			var arr = [];
			/*//取销售开始时间
			//时
			var _saleH = $("select[name=saleH]").find("option:selected").text();
			
			if(_saleH==''){
				_saleH = '00';
			}
			_saleH = convert2Arr(_saleH);
			//分
			var _saleM = $("select[name=saleM]").find("option:selected").text();
			if(_saleM == ''){
				_saleM = '00';
			}
			_saleM = convert2Arr(_saleM);
			//秒
			var _saleS = $("select[name=saleS]").find("option:selected").text();
			if(_saleS == ''){
				_saleS = '00';
			}
			_saleS = convert2Arr(_saleS);*/
			
			//销售周期
			var _saleCyc = '';
				if(lotteryId!=99108 && lotteryId!=99109 && lotteryId!=99401 && lotteryId!=99701){
				$('input[name=saleWeek]').each(function(e){
					_saleCyc += $(this).val();
					_saleCyc +=',';
				});
				
				_saleCyc = _saleCyc.split(",");
			}
			//等待开奖时间
			var _waitTime = '';
			$('input[name=saleWait]').each(function(e){
				_waitTime += $(this).val();
				_waitTime += ',';
			});
			_waitTime = _waitTime.split(",");
			//官方第一期开奖时间
			//时
			var _firstH = $("select[name=firstH]").find("option:selected").text();
			if(_firstH == ''){
				_firstH = '00';
			}
			_firstH = convert2Arr(_firstH);
			//分
			var _firstM = $("select[name=firstM]").find("option:selected").text();
			if(_firstM == ''){
				_firstM = '00';
			}
			_firstM = convert2Arr(_firstM);
			//秒
			var _firstS = $("select[name=firstS]").find("option:selected").text();
			if(_firstS == ''){
				_firstS = '00';
			}
			_firstS = convert2Arr(_firstS);
			//官方最后一期开奖时间
			var _lastH = $("select[name=lastH]").find("option:selected").text();
			if(_lastH == ''){
				_lastH = '00';
			}
			_lastH = convert2Arr(_lastH);
			var _lastM = $("select[name=lastM]").find("option:selected").text();
			if(_lastM == ''){
				_lastM = '00';
			}
			_lastM = convert2Arr(_lastM);
			var _lastS = $("select[name=lastS]").find("option:selected").text();
			if(_lastS == ''){
				_lastS = '00';
			}
			_lastS = convert2Arr(_lastS);
			
			jsonStr += ',"salesIssueStrucs":[';
			
			for(var i = 0 ;i<_firstH.length; i++){
				
				jsonStr += '{"saleStartTime":';
				jsonStr +=0;
				jsonStr += ',"salePeriodTime":';
				if(lotteryId!=99108 && lotteryId!=99109 && lotteryId!=99401 && lotteryId!=99701){
				jsonStr += _saleCyc[i];}
				else{
					jsonStr += 0;
				}
				jsonStr += ',"scheduleStopTime":';
				jsonStr += _waitTime[i];
				jsonStr += ',"firstAwardTime":';
				jsonStr += convert2Sec(_firstH[i],_firstM[i],_firstS[i]);
				jsonStr += ',"lastAwardTime":';
				jsonStr += convert2Sec(_lastH[i],_lastM[i],_lastS[i]);
				jsonStr += "}";
				if(i != (_firstH.length - 1)){
					
					jsonStr += ','
				}
			}
			jsonStr += ']}';
			
			
			url = baseUrl + '/gameoa/addOrUdapteCommonOrSpecialGameIssueRule';
			data = "reqData=" + jsonStr;
			
			sendUrl(url, data);
			
			
	});
	
	$('#J_Submit_Button_a').click(function(){
		var parent = $(this).parents('ul').eq(0);
			saleWaitTime = parent.find('.sale-wait-time'),
			saleTime = parent.find('.sale-time'),
			checkboxlist=parent.find('[name="checkbox2"]'),
			isChecked= true,
			isTureOk = true,
			dataTimeOk = true;	//时间格式是否符合规则(时，分，秒不能为全0)
			var lotteryId = $('#lotteryId').val();

			if(lotteryId!=99108 && lotteryId!=99109 && lotteryId!=99401 && lotteryId!=99701){
			saleTime.each(function(i){
					if($(this).val() == ''){
						msg.show({
							mask:true,
							content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">销售周期时间 未填写请检查！</h4></div>',
							closeIsShow: true,
							closeFun : function(){
								this.hide();
							}
						});
						isTureOk = false;				
						return false;
					}
				});
			}
			
			saleWaitTime.each(function(i){
				if($(this).val() == ''){
					msg.show({
						mask:true,
						content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">等待开奖时间 未填写请检查！</h4></div>',
						closeIsShow: true,
						closeFun : function(){
							this.hide();
						} 
					});
					
					isTureOk = false;					
					return false;
					
				}
				
			});			
			
			//发送数据
			var jsonStr ="";
			var url = '';
			var data = '';
			
			var ruleType = $('#currentRuleTypeId').val();
			
			//常规规则
			
			var period2 = '';
			var k = 1;
			$("input[name=checkbox2]").each(function(i,v){
				if($('input[name=checkbox2][value='+k+']').is(':checked')==true){
					period2 += $('input[name=checkbox2][value='+k+']').val();
					period2 += ',';
				}
				k = k+1;
			});
			
			//特例规则
			jsonStr += '{"lotteryId":';
			jsonStr += lotteryId;
			jsonStr += ',"issueRulesName":"';
			jsonStr += $('#issueRulesNameId').val();
			jsonStr += '","ruleStartTime":';
			jsonStr += convertDate2UnixTimestamp($('#J-date-exception-start').val());
			jsonStr += ',"ruleEndTime":';
			jsonStr += convertDate2UnixTimestamp($('#J-date-exception-end').val());
			jsonStr += ',"openAwardPeriod":"';
			jsonStr += period2+'"';
			
			//操作类型
			jsonStr += ',"ruleType":2,"operationType":1';//1新增，2 修改
			
			//取销售开始时间
			//时
			/*var _saleH = $("select[name=saleH2]").find("option:selected").text();
			
			if(_saleH==''){
				_saleH = '00';
			}
			_saleH = convert2Arr(_saleH);
			//分
			var _saleM = $("select[name=saleM2]").find("option:selected").text();
			if(_saleM == ''){
				_saleM = '00';
			}
			_saleM = convert2Arr(_saleM);
			//秒
			var _saleS = $("select[name=saleS2]").find("option:selected").text();
			if(_saleS == ''){
				_saleS = '00';
			}
			_saleS = convert2Arr(_saleS);*/
			
			//销售周期
			var _saleCyc = '';
			if(lotteryId!=99108 && lotteryId!=99109 && lotteryId!=99401 && lotteryId!=99701){
				$('input[name=saleWeek2]').each(function(e){
					_saleCyc += $(this).val();
					_saleCyc +=',';
				});
			
			
			_saleCyc = _saleCyc.split(",");
			}
			//等待开奖时间
			var _waitTime = '';
			$('input[name=saleWait2]').each(function(e){
				_waitTime += $(this).val();
				_waitTime += ',';
			});
			_waitTime = _waitTime.split(",");
			//官方第一期开奖时间
			//时
			var _firstH = $("select[name=firstH2]").find("option:selected").text();
			if(_firstH == ''){
				_firstH = '00';
			}
			_firstH = convert2Arr(_firstH);
			//分
			var _firstM = $("select[name=firstM2]").find("option:selected").text();
			if(_firstM == ''){
				_firstM = '00';
			}
			_firstM = convert2Arr(_firstM);
			//秒
			var _firstS = $("select[name=firstS2]").find("option:selected").text();
			if(_firstS == ''){
				_firstS = '00';
			}
			_firstS = convert2Arr(_firstS);
			//官方最后一期开奖时间
			var _lastH = $("select[name=lastH2]").find("option:selected").text();
			if(_lastH == ''){
				_lastH = '00';
			}
			_lastH = convert2Arr(_lastH);
			var _lastM = $("select[name=lastM2]").find("option:selected").text();
			if(_lastM == ''){
				_lastM = '00';
			}
			_lastM = convert2Arr(_lastM);
			var _lastS = $("select[name=lastS2]").find("option:selected").text();
			if(_lastS == ''){
				_lastS = '00';
			}
			_lastS = convert2Arr(_lastS);
			
			jsonStr += ',"salesIssueStrucs":[';
			
			for(var i = 0 ;i<_firstH.length; i++){
				//日期全为0时不合规则
				/*if((_firstH[i] == '00' && _firstM[i] == '00' && _firstS[i] == '00') || (_lastH[i] == '00' && _lastM[i] == '00' && _lastS[i] == '00')){
					isTureOk = false;
					dataTimeOk = false;
					break;
				}*/
				jsonStr += '{"saleStartTime":';
				jsonStr += 0;
				jsonStr += ',"salePeriodTime":';
				if(lotteryId!=99108 && lotteryId!=99109 && lotteryId!=99401 && lotteryId!=99701){
				jsonStr += _saleCyc[i];
				}else{
					jsonStr += 0;
				}
				jsonStr += ',"scheduleStopTime":';
				jsonStr += _waitTime[i];
				jsonStr += ',"firstAwardTime":';
				jsonStr += convert2Sec(_firstH[i],_firstM[i],_firstS[i]);
				jsonStr += ',"lastAwardTime":';
				jsonStr += convert2Sec(_lastH[i],_lastM[i],_lastS[i]);
				jsonStr += "}";
				if(i != (_firstH.length - 1)){
					
					jsonStr += ','
				}
				
			}
			jsonStr += ']}';
			
			//判断是否合规则输入
			/*if(!isTureOk){
				if(!dataTimeOk){
					msg.show({
						mask:true,
						content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">（时-分-秒）不能全为0</h4></div>',
						closeIsShow: true,
						closeFun : function(){
							this.hide();
						} 
					});
				}
				return false;
			}*/

			url = baseUrl + '/gameoa/addOrUdapteCommonOrSpecialGameIssueRule';
			data = "reqData=" + jsonStr;
			
			var msgError = '';
			if($.trim($('#issueRulesNameId').val()) == ''){
				msgError = '特例奖期规则名称不能为空';					
			}else if($.trim($('#J-date-exception-start').val()) == ''){
				msgError = '特例时间段不能空';			
				
			}
			/*else if($("input[name='checkbox2']:checkbox").attr('checked')==undefined){
				msgError = '开奖周期不能为空';			
			}*/
			checkboxlist.each(function(i) {
                if(this.checked)
				{
					isChecked=false;
				}
            });
			if(isChecked)
			{
				msgError = '开奖周期不能为空';
			}
			
			if(msgError != ''){
				msg.show({
					mask:true,
					content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+msgError+'</h4></div>',
					closeIsShow: true,
					closeFun : function(){
						this.hide();
					} 
				});
			}else{
				sendUrl(url, data);
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
					operationSuccess();
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
	
	$("li[class='close  delete-close']").each(function(e){
		$(this).click(function(e){
			e.preventDefault();
			var dom = $(this).parents('.break-time').eq(0);
			var t = dom.find('input:radio:checked').val();
			if(t != undefined){
				msg.show({
					mask:true,
					content : '<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">此默认分段奖期时间不能删除，最少一个分段奖期时间</h4></div>',
					closeIsShow: true,
					closeFun : function(){
						this.hide();
					} 
				})
				return false ;
			}
			
		});
	});

	
})();
