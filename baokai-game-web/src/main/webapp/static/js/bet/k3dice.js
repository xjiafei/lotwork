$(document).ready(function(){
	var gameMessage = new GameMessage(),
	gameConfig = new GameConfig(),
	// 游戏记录
	k3History = $('[data-simulation="gameHistory"]').gameHistory({
		ballurl: 'gameUserCenter/queryOrderDetail' // 投注内容详情页url
	}),
	// 骰宝
	k3dice = $('body').k3dice({
		gameConfig: gameConfig,
		message: gameMessage,
		gameHistory: k3History
	}),
	// 开奖动画
	diceAnimation = $('#J-lottery-info-balls').diceAnimation(),
	// 开奖记录
	k3Records = $('#J-lottery-records').k3Records();

		
		isLotteryStopSale();
	 function isLotteryStopSale(){				
			var isture = (new GameConfig()).getConfig().isLotteryStopSale;	
			
			if(isture){
				setTimeout(function(){
					(new GameMessage).show({
					   type : 'normal',
					   hideClose: true,
					   data : {
						   tplData:{
								//开始购买时间
								msg : '您好，当前彩种已停售!'//,
								//提示彩票种类
//								lotteryType : ' <dd><span class="pic"><img src="'+staticFileContextPath+'/static/images/game/tancenglogo/llssc.jpg"/></span><a class="btn" href="/gameBet/llssc">去投注</a></dd><dd><span class="pic"><img src="'+staticFileContextPath+'/static/images/game/tancenglogo/ll115.jpg"/></span><a class="btn" href="/gameBet/ll115">去投注</a></dd>'
						   }
					   }
					});
				}, 3000);
				 
				
			}
			

		}
// 根据投注信息生成筹码
/*k3dice.renderSheetFromData({
	amount: "3,810.00",
	balls: [
		{num: 110, id: 0, moneyunit: 1, type: "hezhi.hezhi", ball: ""},
		{num: 100, id: 15, moneyunit: 1, type: "ertonghaofuxuan", ball: ""},
		{num: 500, id: 16, moneyunit: 1, type: "daxiao", ball: ""},
		{num: 500, id: 20, moneyunit: 1, type: "hezhi", ball: ""},
		{num: 100, id: 24, moneyunit: 1, type: "hezhi", ball: ""},
		{num: 500, id: 29, moneyunit: 1, type: "hezhi", ball: ""},
		{num: 500, id: 33, moneyunit: 1, type: "erbutonghao", ball: ""},
		{num: 500, id: 47, moneyunit: 1, type: "yibutonghao", ball: ""},
		{num: 500, id: 49, moneyunit: 1, type: "yibutonghao", ball: ""},
		{num: 500, id: 5, moneyunit: 1, type: "yibutonghao", ball: ""}
	],
	gameType: "k3",
	isTrace: 1,
	multiple: 2,
	orders: [
		{number: "20150226-056", issueCode: 20150226101056, multiple: 1},
		{number: "20150226-057", issueCode: 20150226101057, multiple: 1}
	],
	trace: 2
});*/

$('body').on('dynamicConfigOnReady', function(){
	// 筹码相关事件
	var $chips = k3dice.getChipsElements();
	$chips.draggable({
			revert: 'invalid',
			delay: 600,
			//refreshPositions: true,
			opacity: 0.5,
			helper: 'clone',
			zIndex: 100
		})
		.on('longclick', function(){
			var $this = $(this);
			k3dice.$chips.removeClass('dice-chip-draggable');
			$this.addClass('dice-chip-draggable');
			$this.trigger('chip.selected');
		})
		.on('click', function(){
			$(this).trigger('chip.selected');
		})
		.on('chip.selected', function(){
			var $this = $(this);
			k3dice.$chips.removeClass('dice-chip-selected');
			$this.addClass('dice-chip-selected');
			k3dice.setChipValue($this.data('value'));
			k3dice.setSelectedChipElements($this);
		})
		.on('chip.bet', function(event, offset){

		})
		.filter('[data-value="' + k3dice.getChipValue() + '"]').trigger('chip.selected');

	// 自定义筹码按钮
	var $chipsButton = k3dice.getChipsButtonElements(),
		$chipsCustom = k3dice.getChipsCustomElements();
	$chipsButton.addClass('active').on('click', function(){
		if( $(this).hasClass('active') ){
			$chipsCustom.animate({
				top: '+=20px',
				opacity: 'hide'
			});
			$(this).removeClass('active');
		}else{
			$chipsCustom.animate({
				top: '-=20px',
				opacity: 'show'
			});
			$(this).addClass('active');
		}
	}).trigger('click');

	// 筹码自定义
	var $chipsMirror = k3dice.getChipsMirrorElements();
	$chipsMirror.on('click', function(){
		if( $(this).hasClass('dice-chip-hide') ){
			var chipsMirror = k3dice.getChipsMirror(),
				$shifted = chipsMirror.shift();
			$shifted.addClass('dice-chip-hide');
			chipsMirror.push($(this).removeClass('dice-chip-hide'));
			k3dice.setChipsMirror(chipsMirror);
			var value1 = $shifted.data('value'),
				value2 = $(this).data('value'),
				$chips = k3dice.getChipsElements();
			$chips.filter('[data-value="' + value1 + '"]').addClass('dice-chip-hide');
			$chips.filter('[data-value="' + value2 + '"]').removeClass('dice-chip-hide');
			var $selected = $chips.filter('.dice-chip-selected')
			if( $selected.hasClass('dice-chip-hide') ){
				$selected.removeClass('dice-chip-selected');
				var $_selected = $chips.filter(':not(.dice-chip-hide)').eq(0),
					_value = $_selected.data('value');
				$_selected.addClass('dice-chip-selected');
				k3dice.setChipValue(_value);
				k3dice.setSelectedChipElements($_selected);
			}
		}
		return false;
	});

	// 回收站
	var $recycle = $('.dice-recycle');
	$recycle.droppable({
		accept: '.dice-chip, .dice-sheet',
		hoverClass: 'dice-recycle-hover',
		tolerance: 'pointer',
		drop: function( event, ui ) {
			var $ui = $(ui.draggable),
				value = k3dice.getSheetData($ui),
				$this = $(this);

			$ui.removeClass('dice-sheet-draggable');
			k3dice.setSheetData($ui, -value);

			k3dice.addActionLog({
				from   : $ui,
				to     : $this,
				amount : value,
				type   : 'recycle'
			});
		}
	});
	// 玩法盘相关事件
	var $sheets = k3dice.getSheetsElements();
	$sheets.droppable({
			accept: '.dice-chip, .dice-sheet',
			hoverClass: 'dice-sheet-hover',
			tolerance: 'pointer',
			drop: function( event, ui ) {
				var $ui = $(ui.draggable),
					value = k3dice.getSheetData($ui),
					$this = $(this),
					type = 'bet';

				if( k3dice.chipIsOverLimit($this, value) ){
					var $helper = $(ui.helper).clone().css({
						position: 'absolute',
						display: 'block',
						zIndex: 1,
						left: ui.offset.left,
						top: ui.offset.top
					}).appendTo('body');
					$helper.find('.dice-sheet-bg').css({opacity: 0});
					$helper.animate( $ui.offset(), function(){
						$helper.remove();
					});
					k3dice.showActionTips($this, '该注单超过限额了');
					return false;
				}

				if( $ui.hasClass('dice-sheet') ){
					// $this.append( $ui.removeClass('dice-sheet-draggable').find('.dice-chip') );				
					$ui.removeClass('dice-sheet-draggable');
					k3dice.setSheetData($ui, -value);
					type = 'move';
				}else{
					// $this.append( $ui.clone() );
					type = 'bet';
				}
				k3dice.addActionLog({
					from   : $ui,
					to     : $this,
					amount : value,
					type   : type
				});
				k3dice.setSheetData($this, value);
				// k3dice.rebuildChip($this);
			}
		})
		.draggable({
			revert: 'invalid',
			delay: 500,
			//refreshPositions: true,
			opacity: 0.5,
			helper: 'clone'
		})
		.on('longclick', function(){
			if( !$(this).find('.dice-chip').length ){
				return false;
			}
			$sheets.removeClass('dice-sheet-draggable');
			$(this).addClass('dice-sheet-draggable');
		})
		.on('click', function(){
			if( k3dice.getAnimationStatus() ) return false;
			var chipValue = k3dice.getChipValue(),
				$this = $(this);
			// if( !k3dice.chipIsOverLimit($this, chipValue) ){
				k3dice.moveChip($this);
			// }
		})
		.tips({
			attr: 'value',
			autoinitialize: true,
			showCase: function(me){
				return me.getText() != '￥0';
			},
			beforeSetText: function(text){
				if( !text ){
					text = 0;
				}
				return text = '￥' + text;
			}
		});

	// 操作按钮相关
	k3dice.getActionsElements()
		.on('click', function(){
			if( $(this).hasClass('disabled') || $(this).prop('disable') || k3dice.getAnimationStatus() ) return false;
			var action = $(this).data('action');
			if( action ){
				actionString = 'action' + k3dice.letterCapitalize(action);
				// k3dice.debug(actionString)
				if( k3dice[actionString] ){
					k3dice[actionString]();
				}
			}
		});

	    // 连投
		var continusCounter = k3dice.getContinusCounter();
		continusCounter.setTitle('连  投');
		continusCounter.addClass('continus-counter');
		continusCounter.$t.find(".ui-tips-text").text('连投可以帮助您多期连续投注相同的号码');
		continusCounter.$t.find(".game-tips").hover(function(){
			$(this).next().stop(false,true).fadeIn();
		},function(){
			$(this).next().hide();
		});
		// 倍投
		var multipleCounter = k3dice.getMultipleCounter();
		multipleCounter.setTitle('倍  投');
		multipleCounter.addClass('multiple-counter');
		multipleCounter.$t.find(".ui-tips-text").text('倍投可以让您每次下注的筹码翻倍');
		multipleCounter.$t.find(".game-tips").hover(function(){
			$(this).next().stop(false,true).fadeIn();
		},function(){
			$(this).next().hide();
		});

	// refresh balance
	k3dice.getBalanceRefreshElements()
		.on('click', function(){
			if( !$(this).hasClass('onhandled') ){
				k3dice.getBalance();
			}
			return false;
		});
	// 设置充值链接
	k3dice.getBalanceRechargeElements().attr({
		href: _logOut+'/fund',
		target: '_blank'
	});

	// 游戏玩法tips
	k3dice.getTipsElements().tips({
		direction: 'right'
	});
});

$('body').on('dynamicConfigChange', function(){

	var dynamicConfig = k3dice.getDynamicConfig(),
        redressTime=Math.round(new Date(dynamicConfig['nowstoptime']).getTime()/1000)-Math.round(new Date(dynamicConfig['nowtime']).getTime()/1000);
		$('#userName').html(dynamicConfig.username);
	//顶部倒计时
	if( dynamicConfig.isstop ){
		$('.lottery-status').find('.soldout').show();
		$('.lottery-status').find('.lottery-countdown').hide();
	}else{	        	
		$('.lottery-status').find('.soldout').hide();
		$('.lottery-status').find('.lottery-countdown').show();
		var topTimer = new phoenix.CountDown({
			//结束时间
			'endTime': dynamicConfig['nowstoptime'],
			//开始时间
			'startTime': dynamicConfig['nowtime'],
			//是否需要循环计时
			'isLoop': false,
			//是否开启计时矫正
			'isRedress': true,
			'redressTime': 15,
			//配置时间接口
			//'redressUrl': Games.getCurrentGame().getGameConfig().getInstance().lastNumberUrl(),
			//需要渲染的DOM结构
			'showDom': '.lottery-countdown',
			expands: {
				//覆盖showTime渲染方法
				_showTime: function(time) {
					var me = this,
						dom = $(me.defConfig.showDom),
						m = me.checkNum(time.m) + '',
						s = me.checkNum(time.s) + '';
					// 还剩10s时
					if (time.allSecond <= 10) {
						dom.addClass('caution-countdown');
					}else if(time.allSecond > 3600){
						dom.find("em,span").css("display","none");
						dom.find("strong").css("display","inline");
					}else {
						dom.removeClass('caution-countdown');							
					}
					//渲染时间输出 
					var m1 = m.substring(0, 1),
						m2 = m.substring(1, 2),
						s1 = s.substring(0, 1),
						s2 = s.substring(1, 2);
					dom.find('[data-time="m1"]').text(m1).attr('class', 'time-'+m1);
					dom.find('[data-time="m2"]').text(m2).attr('class', 'time-'+m2);
					dom.find('[data-time="s1"]').text(s1).attr('class', 'time-'+s1);
					dom.find('[data-time="s2"]').text(s2).attr('class', 'time-'+s2);
					//console.log(time);
					me.fireEvent('afterShowTime', time, me);
				},
				redRessTime: function() {
					var me = this,
						timeload = null,
						timeMath = new Date().getTime(),
						lastNumbers = [],
						lastballs = [],
						lastnumberold = '';

					//如果已经存在请求
					//还没有返回则中断
					me.timeload && me.timeload.abort();

					me.timeload = $.ajax({
						url: k3dice.gameConfig.lastNumberUrl(),
						type: 'GET',
						cache: false,
						dataType: 'json'
					})
						.done(function(data) {
							if (data) {
								//更新时间
								if (data['nowtime']) {
									//停止计时
									me.stopCount();
									me.setStartTime(new Date(data['nowtime']).getTime() + (new Date().getTime() - timeMath));
									//恢复计时
									me.continueCount();
								}
                                if (data['lastballs']) {
									var lastnumber = data['lastnumber'],
										number = data['number'],
										lastballs = data['lastballs'].split(','),
										lasthostnumber=$('#J-lottery-records li').eq(0).find('.rec5').text().replace('期',''),
										lastnumberold = $.trim($('#J-lottery-info-number').text());

									lastballs.sort(function(a, b){
										return a - b;
									});
									// 奖期变化
									if (lastnumber !=null && lastnumberold != lastnumber && lastnumber.substr(lastnumber.length-3,3) != lasthostnumber) {
										// 开奖动画
							           diceAnimation.doDice(lastballs, function(){
											$('#J-lottery-info-number').html(number);
											// 开奖记录
											var records = {}, num = 0;
											$.each(lastballs, function(i,n){
												var key = 'ball' + (i+1);
												records[key] = n;
												num += parseInt(n);
											});
											var size = '小',
												oddEven = '双';
											if( num >= 11 ){
												size = '大';
											}
											if( num % 2 ){
												oddEven = '单';
											}
											records['num'] = num;
											records['number'] = lastnumber.substr(lastnumber.length-3,3) + '期';
											records['size'] = size;
											records['oddEven'] = oddEven;
											k3Records.prepend(records);
											var _data = {},
												winlists = data['winlists'];
											_data['balls'] = lastballs;
											_data['winlists'] = winlists;
											// console.log(_data);
											k3History.update(_data);
										});
									}else{
										diceAnimation.setBallsNum(lastballs);
									}
						     	}
							}

						})
						.fail(function() {

						})
						.always(function() {
							me.timeload = null;
						});
				}
			}
		});

		/*k3dice.message.show({
			type : 'normal',
				msg : '请至少选择一注投注号码！',
				data : {
					tplData : {
						msg : '请至少选择一注投注号码！'
					}
				}
		});*/

		topTimer.addEvent('afterTimeFinish', function() {
			//定时器结束，当前期结束
			//请求下一期时间 
			var Msg = k3dice.message,
				oldIssueCode = k3dice.getDynamicConfig().number,
				timeMath = new Date().getTime(),
				me=this,
				data='',
				newIssueCode = ''; //上一期，当前期 

				k3dice.getServerDynamicConfig(function() {
					data=k3dice.getDynamicConfig();
					newIssueCode = k3dice.getDynamicConfig().number;
					if (data) {
					//更新时间
					if (data['nowtime']) {
						//停止计时
						me.stopCount();
						me.setStartTime(new Date(data['nowtime']).getTime() + (new Date().getTime() - timeMath));
						me.setEndTime(new Date(data['nowstoptime']).getTime());
						//恢复计时
						me.continueCount();
					}
					if (data['lastballs']) {
						var lastnumber = data['lastnumber'],
							number = data['number'],
							lastballs = data['lastballs'].split(','),
							lastNumberText=$('#J-lottery-records .rec5').eq(0).text().replace('期',''),
							lastnumberold = $.trim($('#J-lottery-info-number').text());

							lastballs.sort(function(a, b){
								return a - b;
							});
							// 奖期变化
							$('#J-lottery-info-number').html(number);
						
					}else{
						diceAnimation.setBallsNum(lastballs);
					}
				}

				Msg.show({
					mask: true,
					cancelIsShow: false,
					title: '温馨提示',
					content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">' + oldIssueCode + '期已截止，<br>当前期为<strong class="color-red" id="J-lottery-info-newNumbe">' + newIssueCode + '</strong>期。<br>投注时请注意期号！</h4></div></div>',
					closeIsShow: true,
					closeFun: function() {
						Msg.hide();
					}
				});
			});
		});
	}

	// 奖期
	$('#J-lottery-info-number').html($.trim(dynamicConfig.number));
});	


// 顶部站内消息
new phoenix.Hover({
	triggers:'#J-top-user-message',
	panels:'#J-top-user-message .msg-box',
	hoverDelayOut:300
});


// 开奖记录滚动条
$('.scroll-pane').jScrollPane({
	autoReinitialise: true,
	showArrows: true,
	arrowScrollOnHover: true,
	autoReinitialiseDelay: 5000
});

// 游戏公告关闭
$('.notice-close').on('click', function(){
	// $(this).parent().slideUp();
	$(this).parent().animate({
		marginTop: '-=30px'
	});
	return false;
});

// 游戏记录开关
var $history = $('.game-history-wrap').slideUp(0);
$('.dice-bar').on('click', '.history-toggle', function(){
	$(this).toggleClass('history-toggle-active');
	$history.slideToggle();
	return false;
});
$history.on('click', '.history-close', function(){
	$('.history-toggle').trigger('click');
	return false;
});

// 筹码区位置
(function(){
	var isIE6 = $.browser.msie && (parseFloat($.browser.version) < 7),
		minHeight = 700,
		maxHeight = 1098,
		$wrap = $('.dice-wrap'),
		$ctrl = $('.dice-ctrl'),
		$footer = $('.footer'),
		f_h = $footer.outerHeight();
		
	var extraTop = $ctrl.outerHeight() + f_h + $('.toolbar').outerHeight();

	function renderPosition(){
		var height = $(window).height(),
			width = $(window).width();

		// 在某些浏览器下窗口宽度为奇数时会出现一些奇怪的样式bug
		// 需要修正为偶数像素宽度
		if( width % 2 ){
			$('body').width(width-1);
		}

		if( height < extraTop + minHeight ){
			$wrap.height(minHeight);
			$ctrl.css({
				position: 'relative',
				bottom: 'auto',
				left: 0
			});
			$footer.css({
				position: 'relative',
				bottom: 'auto',
				left: 0
			});
		}else if( height > extraTop + minHeight ){
			if( !isIE6 ){
				$ctrl.css({
					position: 'fixed',
					bottom: f_h,
					left: 0
				});
				$footer.css({
					position: 'fixed',
					bottom: 0,
					left: 0
				});
			}
			if( height > extraTop + maxHeight ){
				$wrap.height(maxHeight);
			}else{
				$wrap.height(height - extraTop);
			}
		}
	}
	var resizeTimer = null;
	$(window).on( 'resize', function () {
		// renderPosition();
		if ( resizeTimer ) {
			clearTimeout(resizeTimer);
		}
		resizeTimer = setTimeout(function(){
			renderPosition();
		}, 100);
	}).trigger('resize');

})();

});