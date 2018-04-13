$(document).ready(function(){
		var gameMessage = new GameMessage(),
		gameConfig = new GameConfig(),
		// 游戏记录
		jlHistory = $('[data-simulation="gameHistory"]').gameHistory({
			ballurl: 'gameUserCenter/queryOrderDetail' // 投注内容详情页url
		}),
		// 骰宝
		jldice = $('body').k3dice({
			gameConfig: gameConfig,
			message: gameMessage,
			gameHistory: jlHistory
		}),
		// 开奖动画
		diceAnimation = $('#diceCup').diceAnimation(),
		// 开奖记录
		jlRecords = $('#J-lottery-records').k3Records(),
                jlRecordDatas = {},
		// 其他玩家
		gamePlayer = $('#dice-user').gamePlayer(),
		//背景音效
		gameMusic = new phoenix.backgroundMusic();

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

	$('#J-game-volume').click(function(){
		var $this = $(this);
		if($this.hasClass('game-volume-muted')){
			gameMusic.openMusic();
			$this.removeClass('game-volume-muted');
		}else{
			gameMusic.closeMusic();
			$this.addClass('game-volume-muted');
		}
	});

	$('body').on('dynamicConfigOnReady', function(){
		// 筹码相关事件
		var $chips = jldice.getChipsElements();
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
				jldice.$chips.removeClass('dice-chip-draggable');
				$this.addClass('dice-chip-draggable');
				$this.trigger('chip.selected');
			})
			.on('click', function(){
				$(this).trigger('chip.selected');
			})
			.on('chip.selected', function(){
				var $this = $(this);
				jldice.$chips.removeClass('dice-chip-selected');
				$this.addClass('dice-chip-selected');
				jldice.setChipValue($this.data('value'));
				jldice.setSelectedChipElements($this);
			})
			.on('chip.bet', function(event, offset){

			})
			.filter('[data-value="' + jldice.getChipValue() + '"]').trigger('chip.selected');

		// 自定义筹码按钮
		var $chipsButton = jldice.getChipsButtonElements(),
			$chipsCustom = jldice.getChipsCustomElements();
		$chipsButton.addClass('active').on('click', function(){
			if( $(this).hasClass('active') ){
				$chipsCustom.animate({
					top: '0px',
					opacity: 'hide'
				});
				$(this).removeClass('active');
			}else{
				$chipsCustom.animate({
					top: '-170px',
					opacity: 'show'
				});
				$(this).addClass('active');
			}
		}).trigger('click');

		// 筹码自定义
		var $chipsMirror = jldice.getChipsMirrorElements();
		$chipsMirror.on('click', function(){
			if( $(this).hasClass('dice-chip-hide') ){
				var chipsMirror = jldice.getChipsMirror(),
					$shifted = chipsMirror.shift();
				$shifted.addClass('dice-chip-hide');
				chipsMirror.push($(this).removeClass('dice-chip-hide'));
				jldice.setChipsMirror(chipsMirror);
				var value1 = $shifted.data('value'),
					value2 = $(this).data('value'),
					$chips = jldice.getChipsElements();
				$chips.filter('[data-value="' + value1 + '"]').addClass('dice-chip-hide');
				$chips.filter('[data-value="' + value2 + '"]').removeClass('dice-chip-hide');
				var $selected = $chips.filter('.dice-chip-selected')
				if( $selected.hasClass('dice-chip-hide') ){
					$selected.removeClass('dice-chip-selected');
					var $_selected = $chips.filter(':not(.dice-chip-hide)').eq(0),
						_value = $_selected.data('value');
					$_selected.addClass('dice-chip-selected');
					jldice.setChipValue(_value);
					jldice.setSelectedChipElements($_selected);
				}
			}
			return false;
		});
		// 回收站
		var $recycle = $('body');
		$recycle.droppable({
			accept: '.dice-chip, .dice-sheet',
			hoverClass: 'dice-recycle-hover',
			tolerance: 'pointer',
			drop: function( event, ui ) {
				if(removeDichAvailable){
					var $ui = $(ui.draggable),
						value = jldice.getSheetData($ui),
						$this = $(this);
					$ui.removeClass('dice-sheet-draggable');
					jldice.setSheetData($ui, -value);

					jldice.addActionLog({
						from   : $ui,
						to     : $this,
						amount : value,
						type   : 'recycle'
					});					
				}
			}
		});

		// 玩法盘相关事件
		var $sheets = jldice.getSheetsElements();
		var dice = $("#J-dice-sheet");
		var diceOffsets = dice.offset();
		var diceWidth = dice.width();
		var diceHeight = dice.height();
		var diceBorder = {
			right:diceOffsets.left + diceWidth,
			bottom:diceOffsets.top + diceHeight
		}
		var removeDichAvailable = false;
		var recycle = $("#dice-recycle-box .dice-recycle");

		$sheets.droppable({
				accept: '.dice-chip, .dice-sheet',
				hoverClass: 'dice-sheet-hover',
				tolerance: 'pointer',
				drop: function( event, ui ) {
					var $ui = $(ui.draggable),
						value = jldice.getSheetData($ui),
						$this = $(this),
						type = 'bet';
					if( jldice.chipIsOverLimit($this, value) ){
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
						jldice.showActionTips($this, '该注单超过限额了');
						return false;
					}

					if( $ui.hasClass('dice-sheet') ){
						// $this.append( $ui.removeClass('dice-sheet-draggable').find('.dice-chip') );				
						$ui.removeClass('dice-sheet-draggable');
						jldice.setSheetData($ui, -value);
						type = 'move';
					}else{
						// $this.append( $ui.clone() );
						type = 'bet';
					}
					jldice.addActionLog({
						from   : $ui,
						to     : $this,
						amount : value,
						type   : type
					});
					jldice.setSheetData($this, value);
					// jldice.rebuildChip($this);
				}
			})
			.draggable({
				revert: 'invalid',
				delay: 500,
				//refreshPositions: true,
				opacity: 0.5,
				helper: 'clone',
				drag:function (event,ui) {
					function isOverBorder(pos,pagePos){
						if(pos.left<0|| pos.top<0){
							return true;
						}
						if (pagePos.pageX>diceBorder.right  || pagePos.pageY > diceBorder.bottom) {
							return true;
						}						
						return false;
					}
					if(isOverBorder(ui.position,event) && $(this).find(".dice-chip").size()){
						removeDichAvailable = true;
						ui.helper.append(recycle);
					} else{
						removeDichAvailable =false;
						$("#dice-recycle-box").append(ui.helper.find(".dice-recycle"));
					}
				
									
				}
			})
			.on('longclick', function(){
				if( !$(this).find('.dice-chip').length ){
					return false;
				}
				$sheets.removeClass('dice-sheet-draggable');
				$(this).addClass('dice-sheet-draggable');
			})
			.on('click', function(){
				if( jldice.getAnimationStatus() ) return false;
				var chipValue = jldice.getChipValue(),
					$this = $(this);
				// if( !jldice.chipIsOverLimit($this, chipValue) ){
					jldice.moveChip($this);
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
		jldice.getActionsElements()
			.on('click', function(){
				if( $(this).hasClass('disabled') || $(this).prop('disable') || jldice.getAnimationStatus() ) return false;
				var action = $(this).data('action');
				if( action ){
					actionString = 'action' + jldice.letterCapitalize(action);
					// jldice.debug(actionString)
					if( jldice[actionString] ){
						jldice[actionString]();
					}
				}
			});

		// 连投
		var continusCounter = jldice.getContinusCounter();
		continusCounter.setTitle('连  投');
		continusCounter.addClass('continus-counter');
		continusCounter.$t.find(".ui-tips-text").text('连投可以帮助您多期连续投注相同的号码');
		continusCounter.$t.find(".game-tips").hover(function(){
			$(this).next().stop(false,true).fadeIn();
		},function(){
			$(this).next().hide();
		});


		// 倍投
		var multipleCounter = jldice.getMultipleCounter();
		multipleCounter.setTitle('倍  投');
		multipleCounter.addClass('multiple-counter');
		multipleCounter.$t.find(".ui-tips-text").text('倍投可以让您每次下注的筹码翻倍');
		multipleCounter.$t.find(".game-tips").hover(function(){
			$(this).next().stop(false,true).fadeIn();
		},function(){
			$(this).next().hide();
		});


		// refresh balance
		jldice.getBalanceRefreshElements()
			.on('click', function(){
				if( !$(this).hasClass('onhandled') ){
					jldice.getBalance();
				}
				return false;
			});
		// 设置充值链接
		jldice.getBalanceRechargeElements().attr({
			href: _logOut+'/fund',
			target: '_blank'
		});

		// 游戏玩法tips
		jldice.getTipsElements().tips({
			direction: 'right'
		});
	});
	
	$('body').on('dynamicConfigChange', function(){
		var dynamicConfig = jldice.getDynamicConfig();
		var stopmask = new phoenix.util.Mask.getInstance();
		var isShowLottery = true;
		stopmask.css({top:'40px'});
		//顶部倒计时
		if( dynamicConfig.isstop ){
			$('.lottery-status').find('.soldout').show();
			$('.lottery-status').find('.lottery-countdown').hide();
		}else{	        	
			$('.lottery-status').find('.soldout').hide();
			$('.lottery-status').find('.lottery-countdown').show();
                        //轮询奖期是否变化
                        var queryNumber = function(me) {

                                me.lastload && me.lastload.abort();
                                me.lastload = $.ajax({
                                        url: jldice.gameConfig.lastNumberUrl(),
                                        type: 'GET',
                                        cache: false,
                                        dataType: 'json'
                                }).done(function(data) {
                                        if (data) {
                                            var dataCopy = data;
                                            if (data['lastballs']) {
                                                var lastnumber = data['lastnumber'],
                                                        lastballs = data['lastballs'].split(','),
                                                        lastnumberold = $.trim($('#J-lottery-info-number').text());
                                                // 奖期变化
                                                if (lastnumberold == lastnumber) {
                                                        var aUser = gamePlayer.getOrderId(jldice.meOrderId),
                                                                orderIds = {};
                                                        if (typeof aUser != undefined) {
                                                                aUser = aUser.join(',');
                                                                orderIds = {"orderIds": aUser}
                                                        }
														me.querySuccess = true;
                                                        // 开奖动画
                                                        diceAnimation.showResult(lastballs, function () {
                                                                //播放音效
                                                                gameMusic.playDraw();
                                                                if(dataCopy['prizeBet'] && dataCopy['prizeBet'].length>0){
                                                                        jldice.showBetPrize(dataCopy['prizeBet']);
                                                                }
                                                                jldice.getWinningList(orderIds, function (data) {
                                                                        gamePlayer.showWinningList(data, function () {

                                                                        });
                                                                });
                                                                //插入开奖记录
                                                                if ( lastnumber !=null  && jlRecordDatas[lastnumber]==null) {
                                                                        // 开奖记录
                                                                        var records = {}, num = 0;
                                                                        $.each(lastballs, function(i,n){
                                                                                var key = 'ball' + (i+1);
                                                                                records[key] = n;
                                                                                num += parseInt(n);
                                                                        });
                                                                        records['num'] = num;
                                                                        records['number'] = lastnumber.substr(-4) + '期';
                                                                        records['size'] = num >= 11?'大':'小';
                                                                        records['oddEven'] = num % 2?'单':'双';
                                                                        jlRecordDatas[lastnumber] = num;
                                                                        jlRecords.prepend(records);

                                                                }else{
                                                                        diceAnimation.setBallsNum(lastballs);
                                                                }
                                                        });
                                                }
                                            }
                                        }
                                }).always(function() {
                                        me.lastload = null;
                                });
                        };
			var topTimer = new phoenix.CountDown2({
				//结束时间
				'endTime': dynamicConfig['nowstoptime'],
				//开始时间
				'startTime': dynamicConfig['nowtime'],
				//是否需要循环计时
				'isLoop': false,
				//是否开启计时矫正
				'isRedress': true,
				'redressTime': 10,
				//配置时间接口
				//'redressUrl': Games.getCurrentGame().getGameConfig().getInstance().lastNumberUrl(),
				//需要渲染的DOM结构
				'showDom': '.lottery-countdown',
				expands: {
					_showTime: function(time) {
						var me = this,
							dom = $(me.defConfig.showDom),
							m = me.checkNum(time.m) + '',
							s = me.checkNum(time.s) + '';
						// 还剩10s时
						if (time.allSecond <= 10) {
							dom.addClass('caution-countdown');
						} else {
							dom.removeClass('caution-countdown');
						}
						if(time.allSecond <= 15 && isShowLottery){
                                                        var Msg = jldice.message;
							//开奖动画播放开关
							isShowLottery = false;
							//开奖动画
							stopmask.show();
							diceAnimation.doDice();
							Msg.hide();
							gamePlayer.getData(jldice.gameConfig.getPlayerBetUrl(),true);
						}
						if(time.allSecond <= 15){
							if(!me.querySuccess){
								queryNumber(me);
							}
						}else{
							me.querySuccess = false;
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
						me.fireEvent('afterShowTime', time, me);

					},
					redRessTime: function() {
						var me = this,
							timeMath = new Date().getTime();
						//如果已经存在请求
						//还没有返回则中断
						me.timeload && me.timeload.abort();
						me.timeload = $.ajax({
							url: jldice.gameConfig.lastNumberUrl(),
							type: 'GET',
							cache: false,
							dataType: 'json'
						}).done(function(data) {
							if (data) {
								var lastnumber = data['lastnumber'],
									lastballs = data['lastballs'].split(','),
									lasthostnumber=$('#J-lottery-records li').eq(0).find('.rec5').text().replace('期','');
								//更新时间
								if (data['nowtime']) {
									//停止计时
									me.stopCount();
									me.setStartTime(new Date(data['nowtime']).getTime() + (new Date().getTime() - timeMath));
									//恢复计时
									me.continueCount();
								}
								if(lastnumber !=null  && jlRecordDatas[lastnumber]==null && lastnumber.substr(lastnumber.length-4)!=lasthostnumber){
									var records = {}, num = 0;
									$.each(lastballs, function(i,n){
											var key = 'ball' + (i+1);
											records[key] = n;
											num += parseInt(n);
									});
									records['num'] = num;
									records['number'] = lastnumber.substr(-4) + '期';
									records['size'] = num >= 11?'大':'小';
									records['oddEven'] = num % 2?'单':'双';
									jlRecordDatas[lastnumber] = num;
									jlRecords.prepend(records);
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
			//其他玩家投注信息
			topTimer.joinEvents('30',function(){
				gamePlayer.getData(jldice.gameConfig.getPlayerBetUrl());
			});

			topTimer.joinEvents('18',function(){
				//播放音效
				gameMusic.playStop();
			});

			topTimer.joinEvents('6',function(){
				var tips = $('#J-result-tips'),
						count = 5;
				var timer2 = setInterval(function(){
					if(count < 0) {
						tips.hide();
						clearInterval(timer2);
						stopmask.hide();
						//播放音效
						gameMusic.playBet();
						return;
					}
					tips.html(count-- +'秒后，进入新奖期').show();
				},1000);

			});

			topTimer.addEvent('afterTimeFinish', function() {
				var timeMath = new Date().getTime(),
					me = this;
				var tips = $('#J-result-tips');
				jldice.getServerDynamicConfig(function () {
					var data = jldice.getDynamicConfig();
					//更新时间
					if (data['nowtime']) {
						//停止计时
						me.stopCount();
						//倒计时修正
						if(parseInt(new Date(data['nowstoptime']).getTime() - new Date(data['nowtime']).getTime() + (new Date().getTime() - timeMath)) < 0){
							setTimeout(function(){
								topTimer.fireEvent('afterTimeFinish');
							},1000);
						};
						me.setStartTime(new Date(data['nowtime']).getTime() + (new Date().getTime() - timeMath));
						me.setEndTime(new Date(data['nowstoptime']).getTime());
						//恢复计时
						me.continueCount();
					}
					if (data['lastballs']) {
						var number = data['number'];
						// 奖期变化
						$('#J-lottery-info-number').html(number);
					}
					stopmask.hide();
					tips.hide();
				});
				jldice.meOrderId = '';
				isShowLottery = true;
				//更新投注历史状态
				jldice.afterSubmitSuccess();
				diceAnimation.hideCup();
			});
		}
		// 奖期
		$('#J-lottery-info-number').html($.trim(dynamicConfig.number));
	});

	// 开奖记录滚动条
	$('.scroll-pane').jScrollPane({
		autoReinitialise: true,
		showArrows: false,
		arrowScrollOnHover: true,
		autoReinitialiseDelay: 5000
	});

	// 游戏记录开关
	var $history = $('.game-history-wrap').slideUp(0);
	$('.dice-bar').on('click', '.history-toggle', function(){
		$(this).toggleClass('history-toggle-active');
		var  $this = $(this);
		$history.slideToggle(function(){
			if($this.hasClass("history-toggle-active")) {
				//$this.animate({
				//	'top':'-21px',
				//	'opacity': '0'
				//},'fast');
				jldice.afterSubmitSuccess();
			}//else {
			//	$this.animate({
			//		'top':'-51px',
			//		'opacity': '100'
			//	},'fast');
			//}
		});
		return false;
	});
	$history.on('click', '.history-close', function(){
		$('.history-toggle').trigger('click');
		return false;
	});

	// 筹码区位置
	(function(){
		var isIE6 = $.browser.msie && (parseFloat($.browser.version) < 7),
			minHeight = 840,
			maxHeight = 1098,
			$wrap = $('.dice-wrap'),
			$recycle = $('.dice-recycle'),
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
				//$recycle.height(80);
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
					$recycle.height(height - extraTop - 670);
				}
			}
		}
		var resizeTimer = null;
		$(window).on('resize', function () {
			// renderPosition();
			if ( resizeTimer ) {
				clearTimeout(resizeTimer);
			}
			resizeTimer = setTimeout(function(){
				renderPosition();
			}, 100);
		}).trigger('resize');
	})();

	var $setupPop = $('body').setupPop();

	if(!jldice.gameConfig.getConfig()['headImg']){
		$setupPop.setfnConfirm(function(){
			var _this = this;
			if($setupPop.checkData()){
				var param = {};
				param['url'] = jldice.gameConfig.uploadUserInfo();
				param['nickname'] = $setupPop.getUsername();
				param['headImg'] = $setupPop.getHeadNumber();
				$setupPop.uploadUser(param,function(){

					var $dom = $('#dice-user .main-chair'),
						config = jldice.gameConfig.getConfig();
					$dom.show();
					$dom.find('.head-pic').addClass('marvel'+param['headImg']);
					$dom.find('.head-name').html(param['nickname']);
					$setupPop.hide();
				},function(data){
					$setupPop.showWrong(data.msg)
				});
			}
		});
		$setupPop.show();
	}else{
		var $dom = $('#dice-user .main-chair'),
			config = jldice.gameConfig.getConfig();
		$dom.show();
		$dom.find('.head-pic').addClass('marvel'+config['headImg']);
		$dom.find('.head-name').html(config['userNickName']);
	}
});