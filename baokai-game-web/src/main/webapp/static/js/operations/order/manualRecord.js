(function($){
	
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(3)').attr("class","current");	
	
	var topTimer = {}; 

	smartInput = function(config) {
		var defConfig = {
			controlPanel: '#J-control-area',
			controlNodetype: 'a',
			dataSource: '',
			container: '#J-input-area',
			nodeName: 'input',
			submit: '#J-submit-ball'
		}
		this['inputRules'] = {
			//字数限制
			textLimit: 1,
			//字符格式化
			formartNum: false,
			//号码位数
			ballLength: 5,
			//
			isPureNum: true,
			//
			minNumber: 0,
			//
			maxNumber: 9,
			//分隔符
			separated: ''
		}
		this.init($.extend(defConfig, config));
	};
	$.extend(smartInput['prototype'], {
		init: function(cfg) {
			var me = this;
			me.cfg = cfg;
			me.getInputRules();
			me.bindEvent();
		},
		getInputRules: function() {
			var me = this;
			for (var name in me['inputRules']) {
				me['inputRules'][name] = $(me['cfg']['container']).attr('data-' + name);
			}
		},
		bindEvent: function() {
			var me = this;
			$(me['cfg']['controlPanel']).on('click', me['cfg']['controlNodetype'], function(event) {
				me.fillInputArea(this);
			});

			//绑定按钮
			$(me['cfg']['container']).on('input', me['cfg']['nodeName'], function(event) {
				event.preventDefault();
				me.handleInput(this);
			});

			//绑定按钮
			$(me['cfg']['container']).on('click', me['cfg']['nodeName'], function(event) {
				var innerText = this.value;
				$(this).val('').focus().val(innerText).focus();
			});

			//绑定按钮
			$(me['cfg']['container']).on('blur', me['cfg']['nodeName'], function(event) {
				event.preventDefault();
				me.formatNum(this);
			});

			//绑定按钮
			$(me['cfg']['container']).on('keydown', me['cfg']['nodeName'], function(event) {
				var value = this.value,
					prevDom = $(this).prev(me['cfg']['nodeName']),
					nextDom = $(this).next(me['cfg']['nodeName']);

				//left
				if (event.keyCode == 37) {
					if (prevDom.size() > 0) {
						prevDom.focus();
					}
					event.preventDefault();
				}

				//right
				if (event.keyCode == 39) {
					if (nextDom.size() > 0) {
						nextDom.focus();
					}
					event.preventDefault();
				}

				//add
				if (event.keyCode == 38) {
					
					if($(this).attr('data-maxNumber')){
						if($.trim(this.value) !='' && Number(this.value) < $(this).attr('data-maxNumber')){
							$(this).val(Number(value) + 1);
						}
					} else {
						if($.trim(this.value) !='' && Number(this.value) < Number(me['inputRules']['maxNumber'])){
							$(this).val(Number(value) + 1);
						}
					}

					event.preventDefault();
				}

				//reduce
				if (event.keyCode == 40) {

					if($.trim(this.value)!='' && Number(this.value) > Number(me['inputRules']['minNumber'])){
						$(this).val(Number(value) - 1);
					}
					
					event.preventDefault();
				}

				if (event.keyCode == 8) {
					if (prevDom.size() > 0 && $.trim(this.value) == '') {
						prevDom.focus();
						event.preventDefault();
					}
					
				}
				
			});
			
			$('#J-control-area').find('a').each(function(){	
				
					lotteryId = $('#lotteryId').val() == 'undefined' ? '0' : $('#lotteryId').val(),							
					_value = $.trim($(this).attr('value')),
					_textVa = $.trim($(this).text());
					
				    $(this).removeClass('current');
					if(_value == lotteryId ){							
						$(this).addClass('current');
						$('#J-game-name,#titleManual').html(_textVa);
						me.fillInputArea(this);
					}
			}); 	
			
		},

		lessTenFn: function(num){
			var me = this,
				value = num;

			if (Number(num) < 10) {
				value = '0' + Number(num);
			}

			return value;
		},

		formatNum: function(dom) {
			var me = this,
				inputRules = me['inputRules'],
				$dom = $(dom),
				value = $.trim($dom.val());

			//过滤格式
			if (value != '' && !!inputRules['formartNum']) {
				value = me.lessTenFn(value);
			}

			//最大值
			if($dom.attr('data-maxNumber')){
				if(value != '' && Number(value) > Number($dom.attr('data-maxNumber'))) {
					value = me.lessTenFn($dom.attr('data-maxNumber'));
				}
			} else {
				if(value != '' && Number(value) > Number(inputRules['maxNumber'])) {
					value = me.lessTenFn(inputRules['maxNumber']);
				}
			}

			//最小值
			if(value != '' && Number(value) < Number(inputRules['minNumber'])) {
				value = me.lessTenFn(inputRules['minNumber']);
			}

			$dom.val(value);
		},

		handleInput: function(dom) {
			var me = this,
				inputRules = me['inputRules'],
				$dom = $(dom),
				index = $dom.index(),
				value = $.trim($dom.val()),
				valueList,
				nextDom = $dom.next(me['cfg']['nodeName']),
				limitNum = Number(inputRules['textLimit']),
				valLength = value.length;

				eval("valueList =  inputRules['separated'] ? value" +'.split(' + inputRules['separated'] +') : "'+value+'".split("")');

			//过滤格式
			if (!!inputRules['isPureNum']) {
				value = $.trim(value.replace(/\D/gi, ''));
				$dom.val(value);
			}

			//大于字数限制
			//少于Dom位数
			if (valLength >= limitNum) {
				var limitText = value.substr(value.length - limitNum, limitNum);
				$dom.val(limitText);
				if (index < inputRules['ballLength'] && $.trim($dom.val()) != '') {
					nextDom.val(nextDom.val()).focus();
				}
			}

			//多位号码
			if (valueList.length > 2) {
				var surplusNum = inputRules['ballLength'] - index;
				$dom.val(valueList[0]);
				if (surplusNum > 0) {
					for (var i = 0; i < valueList.length; i++) {
						var current = $(me['cfg']['container']).find(me['cfg']['nodeName']).eq(index + i);

						current.val(valueList[i]).trigger('input').trigger('blur');
					};
				}
			}
		},
		fillInputArea: function(dom) {
			var me = this,
				$dom = $(dom),
				gameType = $('#J-game-type'),
				childLength = $dom.attr('data-ballLength'),
				lottoryId = $dom.attr('value');;
			
			$(me['cfg']['controlPanel']).children().removeClass('current');
			$dom.addClass('current');
			//$('#J-game-name').text($.trim($dom.text()));
			//gameType.val($.trim($(this).text()));
			$(me['cfg']['container']).html(function() {
				var html = '';
				for (var i = childLength - 1; i >= 0; i--) {
					if(childLength == 7 && i == 0 && lottoryId!=99701){//六合彩第7格不用 + 號
						html += '<span class="ssq-token">+</span><input type="text" name="ball-num" data-maxNumber="16" class="input w-1">';
					}else{
						html += '<input type="text" name="ball-num" class="input w-1">';
					}
					
				};
				return html;
			});
			
			$(me['cfg']['container'])
				.attr('data-ballLength', $dom.attr('data-ballLength'))
				.attr('data-textLimit', $dom.attr('data-textLimit'))
				.attr('data-formartNum', $dom.attr('data-formartNum'))
				.attr('data-minNumber', $dom.attr('data-minNumber'))
				.attr('data-maxNumber', $dom.attr('data-maxNumber'))
				.attr('data-isPureNum', $dom.attr('data-isPureNum'))
				.attr('data-separated', $dom.attr('data-separated'));

			me.getInputRules();

			//topTimer.stopCount();
			//getGameInfo(function(){
			//	startCountDown();
			//});
		},

		checkNoEmpty: function(){
			var me = this,
				result = true,
				$childDom = $(me['cfg']['container']).find(me['cfg']['nodeName']);

			$.each($childDom, function(index, val) {
				
				if($.trim($(this).val())=='') {
					result =  false;
				}
			});

			return result;
		},

		disableChild: function(){
			var me = this,
				$childDom = $(me['cfg']['container']).find(me['cfg']['nodeName']);

			$childDom.attr('disabled', 'disabled').val('');
			$(me['cfg']['submit']).attr('disabled', 'disabled').removeClass('btn-important').addClass('btn-disable');
		},

		allowChild: function(){
			var me = this,
				$childDom = $(me['cfg']['container']).find(me['cfg']['nodeName']);
			
			$childDom.removeAttr('disabled');
			$(me['cfg']['submit']).removeAttr('disabled').removeClass('btn-disable').addClass('btn-important');
		}
	});
	var SMART = new smartInput();

	var periods = '',
		currentTime = '',
		stopTime ='';

	var submitBallData = function(){
		var lotteryId = $('#lotteryId').val(),
		    dataSave = [],
			form = $('#J-ball-data'),
			ballInput = $('#result'),
			data;

		if(SMART.checkNoEmpty()) {

			//把选球号添加到隐藏域
			$('#J-input-area').find('input[name="ball-num"]').each(function(index){
				var mark = $('#J-input-area').attr('data-maxnumber');

				//双色球
				if(mark == 33){
					if(index == 5){
						dataSave.push($(this).val() + '+' + $('#J-input-area').find('input[name="ball-num"]').eq(6).val());
						return false;
					}else{
						dataSave.push($(this).val());
					}
				}else{
					dataSave.push($(this).val());
				}
			});

			ballInput.val(dataSave.join(','));

			//重新录入隐藏域数据
			data = form.serialize();

			$.ajax({
				url: '/gameoa/encodingGameManualRecord',
				type: 'POST',
				dataType: 'json',
				data: data
			})
			.done(function(r) {
				
				if(Number(r['status']) == 1) {
					alert('录号成功');
					window.location.reload();
				}else{
					alert('录号失败');

				}
			})
			.fail(function() {
				alert('服务器错误，请稍后再试');
			})
			.always(function() {
				//console.log("complete");
			});
		} else {
			alert('请填写全部选球号码');
		}
	}

	var getGameInfo = function(callback){
		//currentTime = $.trim($('#lastSaleStartTime').val());
		currentTime = $.trim($('#systemTime').val());
		stopTime = $.trim($('#lastSaleEndTime').val());
		periods = $.trim($('#J-draw-area').text());
		
		callback && callback();
	};
	
	var startCountDown = function(){

		//顶部倒计时
		topTimer = new phoenix.CountDown({
			//结束时间
			'endTime' : stopTime,
			//开始时间
			'startTime': currentTime,
			//是否需要循环计时
			'isLoop' : false,
			//需要渲染的DOM结构
			'showDom' : '.deadline-number',
			expands:{
				//覆盖showTime渲染方法
				_showTime:function(time){
					var me = this,
						h = me.checkNum(time.h) + '',
						m = me.checkNum(time.m) + '',
						s = me.checkNum(time.s) + '',
						pointerAngle = (60 - parseInt(s)) * 6;

					var num = new Date(currentTime).getTime()/ 1000 + 1;
						currentTime = num * 1000;
					var date = new Date(currentTime);

					var surplusNum = (new Date(stopTime).getTime() - date) / 1000;

					if(surplusNum < 0) {
						SMART.allowChild();
					}

					$('#J-current-time').text(me.checkNum(date.getHours())+':'+me.checkNum(date.getMinutes())+':'+me.checkNum(date.getSeconds()));

					me.fireEvent('afterShowTime', time, me);
				}
			}
		});

		/*topTimer.addEvent('afterTimeFinish', function(){
			getGameInfo(function(){
				topTimer.continueCount();
			});
			
			window.location.reload();
		});*/
	}

	getGameInfo(function(){
		startCountDown();
	});

	//初始化禁止录号
	SMART.disableChild();

	$('#J-submit-ball').on('click', function(e){
		submitBallData();

		e.preventDefault();
	});

	$('body').on('click', '.unlock-btn', function(event) {
		event.preventDefault();

		var lotteryId = $('#lotteryId').val(),
		    DrawNum = $(this).attr('data-draw'),
			actiontype =  $.trim($(this).attr('actiontype')),
			msg = new phoenix.Message(),
			divContent = '', //弹层内容
			gameType = $.trim($('#J-game-name').html()), 
			LotteryNumber = $.trim($(this).parent().parent().find('td:eq(0)').html()),
			//_issueCode = $.trim($('#issueCode').val());
			_issueCode = $(this).parent().parent().attr('id');
			_1stEncoding = $.trim($(this).parent().parent().find('td:eq(4)').html()),
			_1stSources = $.trim($(this).parent().parent().find('td:eq(5)').html()),
			_2stEncoding = $.trim($(this).parent().parent().find('td:eq(6)').html()),
			_2stSources = $.trim($(this).parent().parent().find('td:eq(7)').html());
		
		if(actiontype != '2'){
			var SalesCloseTime = $.trim($(this).parent().parent().find('td:eq(3)').html());
			divContent ='<div class="dialog-content-unlock"><div>'+gameType+' <span style="color:red">'+LotteryNumber+'</span>&nbsp;Draw Result</div><div>SlLES Close:'+SalesCloseTime+'</div><div><input type="text" class="code-area input"></div></div>';
			divContent += '<ul>	<li style="text-align:left;color:#999;">开奖号码标准格式如：</br><span id="J-standard-number1">【时时彩/P5】: 12345</br>【11选5】:01,02,03,04,05</br>【北京快乐8】:01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20</br>【双色球】：01,02,03,04,05,06+07</br>【3D】:123</br>【快3/骰宝】:123</br>【六合彩】:01,02,03,04,05,06,45</span></li></ul>';
			console.log('延迟直接录号');
			msg.hide();				
			msg.show({
				show : true,
				mask : true,				
				title : 'delay encode rusult',
				content : divContent,
				cancelText : 'cancel',
				cancelIsShow : true,
				cancelFun : function(){
					this.hide();
				},
				confirmText:'submit',
				callback :function(){
					$('.j-ui-miniwindow').removeClass().addClass('j-ui-miniwindow pop w-11');
				},
				confirmIsShow: true,
				confirmFun: function(){
					var me = this,
						$dom = me.getContentDom().find('.code-area');

					if($.trim($dom.val()) == ''){
						alert('请填写数值');
						return false;	
					}else{
						//* :直接录号，不用对比入号接口。
						$.ajax({
							url: '/gameoa/encodingGameManualRecord',
							type: 'post',
							dataType: 'json',
							data: {'issueCode': _issueCode, 'result': $.trim($dom.val()),'lotteryId':lotteryId},
						})
						.done(function(r) {
							
							if(Number(r['status']) == 1) {
								alert(r['message']);
								window.location.reload();
							}else{
								alert(r['message']);
							}
						})
						.fail(function(r) {
							alert(r['message']);
						})
						.always(function() {
							//console.log("complete");
						});
					}
					
					

				}
			})
			
		//UNLOCK：	解锁弹窗
		//1：当2个录号员录入不同的号码时会提示自动锁定况。
		//2：锁定后，第三人输入正确的号码点击Unlock提交。
		//请控制好此处按扭状态.Unlock
		}else{
			divContent ='<div class="dialog-content-unlock"><div>'+gameType+' <span style="color:red">'+LotteryNumber+'</span></div>';
			if(_1stEncoding != '' && _1stSources != ''){
				divContent += '<div>source 1 encoded '+_1stEncoding+' at '+_1stSources+'</div>';
			}
			if(_2stEncoding != '' && _2stSources != ''){
				divContent += '<div>source 1 encoded '+_2stEncoding+' at '+_2stSources+'</div>';
			}
			divContent += '<div>PLS.encode Draw Result</div><div><input type="text" class="code-area input"></div></div>';	
			divContent += '<ul>	<li style="text-align:left;color:#999;">开奖号码标准格式如：</br><span id="J-standard-number1">【时时彩/P5】: 12345</br>【11选5】:01,02,03,04,05</br>【北京快乐8】:01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20</br>【双色球】：01,02,03,04,05,06+07</br>【3D】:123</br>【快3/骰宝】:123</br>【六合彩】:01,02,03,04,05,06,45</span></li></ul>';			
			console.log('两人对比入号');	
			msg.hide();				
			msg.show({
				title : 'unlock',
				mask : true,				
				content : divContent,
				cancelText : 'cancel',
				cancelIsShow : true,
				cancelFun : function(){
					this.hide();
				},
				confirmText:'unlock',
				confirmIsShow: true,
				callback :function(){
					$('.j-ui-miniwindow').removeClass().addClass('j-ui-miniwindow pop w-11');
				},
				confirmFun: function(){
					var me = this,
						$dom = me.getContentDom().find('.code-area');

					if($.trim($dom.val()) == ''){
						alert('请填写数值');
						return false;						
					}else{
						$.ajax({
							url: '/gameoa/encodingGameManualRecord',
							type: 'post',
							dataType: 'json',
							data: {'issueCode': _issueCode, 'result': $.trim($dom.val()),'lotteryId':lotteryId},
						})
						.done(function(r) {
							
							if(Number(r['status']) == 1) {
								alert(r['message']);
								window.location.reload();
							}else{
								alert(r['message']);
							}
						})
						.fail(function(r) {
							alert(r['message']);
						})
						.always(function() {
							//console.log("complete");
						});
					}
					
					

				}
			})
		
		}

		
		
		
	});

})(jQuery);