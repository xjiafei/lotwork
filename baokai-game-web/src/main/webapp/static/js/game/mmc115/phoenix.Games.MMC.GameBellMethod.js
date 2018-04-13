;
(function() {
	function Lottery() {
		this.ON_PREPARED = 'prepared'; // 准备开奖 
		this.ON_DRAWING = 'drawing'; // 开奖中
		this.ON_DRAWED = 'drawed'; // 已开奖
		this.dialogMarkup = {
			// loading加载
			'loading': '<div class="dialog" id="dl_loading"></div>',

			// 连续开奖中奖提示
			// data = {'num': '中奖注数', 'amount': '中奖金额'}
			'win': '<div class="dialog" id="win_prize"> \
							<span class="prize_icon"></span> \
							<p>恭喜您，中奖<span class="highlight" data-amount></span>元</p> \
						</div>',

			// 连续开奖次数提示
			// data = {'times': '3'}
			'times': '<div class="dialog" id="draw_times"> \
							<p>第<span class="highlight" data-times></span>次开奖</p> \
						</div>',

			// 开奖最终结果[未中奖]
			'noprize': '<div class="dialog" id="no_prize"> \
							<div class="result_bg"></div> \
						</div>',

			// 开奖最终结果[有中奖]
			// data = {'result': '中奖金额'}
			'result': '<div class="dialog" id="prize_result"> \
							<div class="result_bg"></div> \
							<div class="result_cont"><span style="font-size:35px;font-family:Arial;color:#511708;font-weight: bold;">共</span><span class="highlight" data-result>23,456,789.00</span><span style="font-size:35px;font-family:Arial;color:#511708;font-weight: bold;">元</span></div> \
						</div>',

			// 普通消息提示[普通]
			// data = {'content': '提示内容'}
			'normal': '<div class="dialog" id="dl_normal"> \
							<p data-content>开奖完成</p> \
						</div>'
		};
		this.dialogs = {};
		this.$dialogs = $();
		this.init();
	}
	Lottery.prototype = {
		init: function() {
			this.flipball();
			this.records();
			this.handlePanelDom();
			this.createDialog();
			this.bindEvent();
		},
		bindEvent: function() {
			var me = this;
			// 滚动条
			me.scrollPane();
			// 投注记录展开、收起
			$('.lottery-footer .expand-btn').on('click', function() {
				$(this).toggleClass('active');
				$('.history-content ul').slideToggle();
				$('.ht-icon').toggle();
			});
			me.$panelCtrl.on('click', function() {
				if ($(this).hasClass('disabled')) return false;
				me.panel('show');
				//清空信息面板
				$('#J-result-info').hide();
			});
		},
		scrollPane: function(){
			return $('.scroll-pane').jScrollPane({
				autoReinitialise: true
			});
		},
		flipball: function() {
			return $('#flipball').flipball({
				ballsize: 5, // 彩球个数
				initball: '0,0,0,0,0', // 初始化彩球数据
				loop: 5, // 彩球滚动循环次数（必须为整数）
				timeout: 5000, // 彩球滚动动画执行时间基数
				delay: 150, // 每个彩球动画执行延迟时间基数
				offset: [80, 110] // 球的宽高
			});
		},
		records: function() {
			return $('[data-simulation="records"]').records();
		},
		gameHistory: function(){
			return $('[data-simulation="gameHistory"]').gameHistory({
				showNum: 10,
				ballurl: '/gameUserCenter/queryOrderDetail'
			});
		},
		handlePanelDom: function() {
			this.$panel = $('.top_section');
			this.$panelCtrl = $('.section_handle', this.$panel);
			this.$panelCont = this.$panel.children().not(this.$panelCtrl);
			this.$drawbtn = $('.bet-btn input'); // 开奖按钮
			this.$drawbarbtn = $('.handle_hand'); // 拉杆按钮
			this.$history = $('.lottery-history'); // 游戏记录
			this.$header = $('.header');
			this.$gameBtns = $('.result_button_group');

			this.$panelCtrl.animate({
				opacity: 'hide'
			});
		},
		panel: function(status) {
			var me = this;
			if (status == 'show') {
				// 显示控制面板
				me.$panelCtrl.animate({
					opacity: 'hide'
				},function(){
					me.$header.removeAttr('style');
				});
				me.$panelCont.slideDown();
			} else if (status == 'hide') {
				$('html, body').animate({
					scrollTop: 0
				}, function(){
					// 隐藏控制面板
					me.$header.css('margin-bottom','-20px');
					me.$panelCont.slideUp();
					me.$panelCtrl.animate({
						opacity: 'show'
					});
				});				
			}
		},
		createDialog: function() {
			var me = this,
				$body = $('body');
			for (m in me.dialogMarkup) {
				// console.log(m)
				var $m = $(me.dialogMarkup[m]).hide();
				$body.append($m);
				me.dialogs[m] = $m;
			}
		},
		gamebuttons: function( method )	{
			if( method == 'show' ){
				this.$gameBtns.fadeIn();
			}else if( method == 'hide' ){
				this.$gameBtns.fadeOut();
			}
		},
		// method: 'show', 'hide'
		// type: 'loading', 'win', 'times', 'noprize', 'result', 'normal'
		// data
		// timeout: if(timeout > 0)： autohide
		dialog: function(method, type, data, timeout) {
			var me = this,
				$t = me.dialogs[type] || $();
			if (method == 'hide') {
				// 隐藏其他的
				for (t in me.dialogs) {
					if( t != type ){
						me.dialogs[t].fadeOut();
					}
				}
				me.gamebuttons('hide');
			} else if (method == 'show') {
				if (!$t.length) return false;
				if (data) {
					for (d in data) {
						if (data[d]) {
							$t.find('[data-' + d + ']').html(data[d]);
						}
					}
				}
				// 隐藏其他的
				for (t in me.dialogs ) {
					if( t != type ){
						me.dialogs[t].fadeOut();
					}
				}
				// 显示当前的
				var css = me.dialogStyle(type, $t);
				$t.css(css).fadeIn();
				if( type == 'result' || type == 'noprize' ){
					me.$gameBtns.fadeIn();
				}
				if (timeout && Number(timeout) > 0) {
					setTimeout(function() {
						$t.fadeOut();
						if( type == 'result' || type == 'noprize' ){
							me.$gameBtns.fadeOut();
						}
					}, Number(timeout));
				}
			}
		},
		dialogStyle: function(type, $t) {
			var me = this,
				css = {
					marginLeft: -$t.outerWidth() / 2
				},
				$target = $();
			if (type == 'win' || type == 'times' || type == 'normal') {
				$target = me.$panel;
				css.top = $target.offset().top - $t.outerHeight() / 2;
			} else if (type == 'loading') {
				$target = me.$drawbtn;
				css.top = $target.offset().top - $t.outerHeight() - 20;
			}else if (type == 'noprize') {
				$target = me.$header;
				css.top = $target.offset().top - 10;
				css.marginLeft = -504;
				/*css = {
					css: me.$history.offset().top + 84 // 游戏记录的offset().top + 其上竖线的高度
				}*/
			}else if (type == 'result') {
				$target = me.$header;
				css.top = $target.offset().top - 10;
				css.marginLeft = -513;
				/*css = {
					css: me.$history.offset().top + 84 // 游戏记录的offset().top + 其上竖线的高度
				}*/
			}
			return css;
		}
	};

	var lottery = new Lottery();

	//根据模板和数据对象生成模板内容字符串
	//模板格式 <#=xxx#>
	var template = function(tpl, data) {
		var me = this,
			o = data,
			p, reg;
		for (p in o) {
			if (o.hasOwnProperty(p)) {
				reg = RegExp('<#=' + p + '#>', 'g');
				tpl = tpl.replace(reg, (p=='totalPrice'||p=='winMoney'?(isNaN(o[p])?o[p]:phoenix.util.formatMoney(o[p]/10000)):o[p]));
			}
		}
		return tpl;
	};

	//秒秒彩玩法逻辑
	//将游戏玩法特例挂靠到game当前游戏上面
	//不影响原有游戏逻辑
	;
	(function(host, Event, name, undefined) {
		var defConfig = {
				$submitButton: $('#J-submit-order'),
				$selectDom: $('#J-select-panel'),
				$betInfoDom: $('#J-result-info'),
				handleHand: '.handle_hand',
				replayButton: '.restart_game',
				reSelectButton: '.rechoose_ball',
				$lotteryNum: '#J-balls-lotterys-num',
				$gameMenu: '#J-top-game-menu,#J-user-panel,#J-msg-panel',
				//等待开奖html模版
				waitBeting: ['<li>',
					'<div class="result">',
					'<span class="moneyUnitText"><#=modes#></span>',
					'<span class="bet"><#=num#>注</span>',
					'<span class="multiple"><#=multiple#>倍</span>',
					'<span class="opening-text">开奖中</span>',
					'</div>',
					'<span>[<#=methodName#>]</span>',
					'<span><#=code#></span>',
					'</li>'
				].join(''),
				//单次开奖结果
				singleResult: ['<li>',
					'<div class="result">',
					'<span class="moneyUnitText"><#=modes#></span>',
					'<span class="bet"><#=num#>注</span>',
					'<span class="multiple"><#=multiple#>倍</span>',
					'<span class="opening-text"><#=winMoney#></span>',
					'</div>',
					'<span>[<#=methodName#>]</span>',
					'<span><#=code#></span>',
					'</li>'
				].join(''),
				//多次开奖结果
				multipleResult: ['<li>',
					'<span class="multiple-lottery-num">第<#=openNums#>次开奖</span>',
					'<span class="multiple-lottery"><#=lotteryNum#></span>',
					'<span class="multiple-lottery-result"><#=winMoney#></span>',
					'</li>'
				].join('')
			},
			instance,
			//
			Games = host.Games,
			//当前的游戏submit对象
			SUBMIT = host.GameSubmit.getInstance(),
			//投注按钮
			submitButton = $('#J-submit-order');

		//外部重写数据组建方法
		SUBMIT.getSubmitData = function(e){
			
			var me = this,result = {},
				ballsData = Games.getCurrentGameOrder()['orderData'],
				i = 0,
				len = ballsData.length,
				traceInfo = Games.getCurrentGameTrace().getResultData(),
				j = 0;
				//len2 = traceInfo['traceData'].length;
			
			//游戏类型
			//result['gameType'] = Games.getCurrentGame().getName();
			result['gameType'] =Games.getCurrentGame().getGameConfig().defConfig.gameType;
			result['isTrace'] = traceInfo['traceData'].length > 0 ? 1 : 0;
			result['traceWinStop'] = Games.getCurrentGameTrace().getIsWinStop();
			result['traceStopValue'] = Games.getCurrentGameTrace().getTraceWinStopValue();
			//游戏选球数据
			result['balls'] = [];
			for(;i < len;i++){
				result['balls'].push({
									 	'id':ballsData[i]['id'],
									 	'ball':ballsData[i]['postParameter'],
										'type':ballsData[i]['type'],
										//'onePrice':ballsData[i]['onePrice'],
										'moneyunit':ballsData[i]['moneyUnit'],
										'multiple':ballsData[i]['multiple'],
										'num': ballsData[i]['num']//传递投注数
									});
			}
			result['orders'] = [];
			//非追号
			//获得当前期号
			result['orders'].push({'number':Games.getCurrentGame().getCurrentNumber(),'issueCode':1,multiple:1});
			//总金额
			result['amount'] = Games.getCurrentGameOrder().getTotal()['amount'];

			return result;
			e.preventDefault();
		},

		//由于内部逻辑耦合弹窗方法
		//所以必须篡改提交方法
		SUBMIT.submitData = function(e) {
			
			var me = this,
				data = me.getSubmitData(),
				message = Games.getCurrentGameMessage(),
				mmcBetKey = '';
			//判断加锁
			if (me.postLock) {
				return;
			} else {
				//加锁
				me.doPostLock();
				me.fireEvent('beforeSubmit');
			}

			//提示至少选择一注
			if (data.balls.length <= 0) {
				message.show({
					type: 'mustChoose',
					msg: '请至少选择一注投注号码！',
					data: {
						tplData: {
							msg: '请至少选择一注投注号码！'
						}
					}
				});
				//请求解锁
				me.cancelPostLock();
				me.fireEvent('afterSubmitCancel');
				//恢复按钮状态
				submitButton.removeClass('disabled');
				return;
			}

			me.fireEvent('beforeSubmitSuccess');
			mmcBetKey = $.trim($('#mmcBetKey').val());				
			$.cookie('mmcBetCookie99306',mmcBetKey,{expires:1,path:'/',domain:window.location.hostname.substring(window.location.hostname.indexOf('.'))});
            data.onlyAmount = undefined;
			data.ordersNumber = undefined;
			$.ajax({
				url:  Games.getCurrentGame().getGameConfig().getInstance().submitUrl(),
				data:  JSON.stringify(Games.getCurrentGame().editSubmitData(data)),				
				dataType: 'json',
				method: 'POST',
				cache: false,
                contentType: "application/json; charset=utf-8",
				success: function(r) {
					if (Number(r['isSuccess']) == 1) {
						me.fireEvent('afterSubmitSuccess', r);
					} else {
						me.fireEvent('afterSubmitError', r);
					}

					//请求解锁
					me.cancelPostLock();
				},
				//完成
				complete: function() {
					me.fireEvent('afterSubmit');
				},
				//错误
				error: function(xhr) {
					me.fireEvent('afterSubmitError', xhr);
					if(xhr.status == '404' || xhr.status == '0'){
						message.show({
							mask: true,
							title: '温馨提示',
							content: "<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>登录超时 请重新登入！</h4></div>",
							cancelIsShow: true,
							cancelFun: function () {
								window.location.reload();                              
							}
						});
					}else{
						message.show({
							mask: true,
							title: '温馨提示',
							content: "<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>方案提交失败,<br />请检查网络并重新提交！</h4></div>",
							cancelIsShow: true,
							cancelFun: function () {
								message.hide();
							}
						});
					}
				}
			});
			// e.preventDefault();
		};

		//投注相关数据
		var betData = {

			//连续开奖期数
			continuousPeriod: 1,
			//游戏投注记录
			//用于多期投注汇总
			betDataHistory: [],
			//游戏已经投注次数记录
			saveCompletePeriod: 0,
			//缓存投注信息
			saveBetsInfo: [],
			//缓存多次中奖信息
			saveMultipleBetInfo: []
		};

		//定义方法
		var pros = {
			init: function(cfg) {
				var me = this,
					that = me;

				//中奖&非中奖提示状态
				me.isWaringStatus = false;
				//绑定按钮事件
				me.bindEvent();
				//快速开奖标记
				me.quickStopMark = false;
				

				me.lotteryInput = new phoenix.Select({
					realDom: cfg.$lotteryNum,
					isInput: true,
					expands:{inputEvent:function(){
							var me = this;
							me.getInput().keyup(function(e){
								var v = this.value;//当前玩法倍数限制
								if(v > 50){
									this.value = 50;
								}
								me.setValue(this.value);
							});
							me.getInput().blur(function(e) {
								var v = this.value;
								that.setContinuousPeriod(this.value);
							})
						}}
				});
				me.lotteryInput.addEvent('change', function(e, value, text) {

					//设置连续开奖次数
					me.setContinuousPeriod(value);
				});

				//获取submit的失败后的执行
				SUBMIT.addEvent('afterSubmitError', function(e, r) {
					me.controlError(r);
				});

				//验证完成数据以后
				//发送请求之前
				SUBMIT.addEvent('beforeSubmitSuccess', function(e, r) {

				});

				//获取submit的成功后的参数执行下一步
				SUBMIT.addEvent('afterSubmitCancel', function(e, r) {

					me.SubmitCancel(r);
				});

				//获取submit的完成
				SUBMIT.addEvent('afterSubmit', function(e, r) {

					//请求结束后对INPUT执行 失焦处理
					//否则会重复进行请求
					me.defConfig.$submitButton.blur();
				});

				//获取submit的取消后的执行
				SUBMIT.addEvent('afterSubmitSuccess', function(e, r) {

					//如果为第一次开奖
					//不分多次还是单次开奖
					//关闭开奖面板
					if (me.getSaveCompletePeriod() == 1) {
						//选求面板隐藏
						lottery.panel('hide');
						//禁止按钮下拉
						cfg.$selectDom.addClass('disabled');
						//多次开奖马上开奖按钮
						//变为停止按钮
						if(betData['continuousPeriod'] > 1) {
							me.defConfig.$submitButton.addClass('stop');
						}
					}

					//如果连续开奖期数为1
					//则为单次开奖
					if (betData['continuousPeriod'] <= 1) {
						//单次开奖
						me.singlefinish(r);
					}

					//如果为多期开奖
					if (betData['continuousPeriod'] > 1) {

						//执行连续投注
						me.continuousBets(r);
					}
				});
			},

			//绑定用户交互事件
			bindEvent: function() {
				var me = this,
					cfg = this.defConfig;
				// replayButton: '.restart_game',
				// reSelectButton: '.rechoose_ball',
				//再玩一次
				$('body').on('click', cfg.replayButton, function() {

					//隐藏弹窗
					lottery.dialog('hide');
					//隐藏投注信息
					me.hideInfoPanel();
					//关闭提示信息状态
					me.closeWaringStatus();
				});

				//重新选号
				$('body').on('click', cfg.reSelectButton, function() {
					
					//隐藏弹窗
					lottery.dialog('hide');
					//打开选号面板
					lottery.panel('show');
					//隐藏投注信息
					me.hideInfoPanel();
					//关闭提示信息状态
					me.closeWaringStatus();
					//清空数据
					me.dataReset();
				});
				//更改层级关系，下拉不被弹层挡信			
				$('body').on('mouseenter', cfg.$gameMenu, function() {					
					$('.bg-body >.dialog').css('z-index',99);				
				});
				
				//拉杆选号
				$('body').on('click', cfg.handleHand, function() {
					var button = me.defConfig.$submitButton;
					
					if(button.hasClass('stop') || me.isWaringStatus) {
						return;
					}

					$('.handle_tips').fadeOut();
					
					button.trigger('click');					
				});
				
			},

			//打开提示状态
			openWaringStatus: function(){
				var me = this;

				me.isWaringStatus = true;
			},

			//关闭提示状态
			closeWaringStatus: function(){
				var me = this;

				me.isWaringStatus = false;
			},			

			//填充信息区域
			fillInfoPanel: function(html){
				var me = this;

				me.defConfig.$betInfoDom.find('ul').html(html);
			},

			//展开信息区域
			showInfoPanel: function(){
				var me = this;

				me.defConfig.$betInfoDom.show();
			},

			//隐藏信息区域
			hideInfoPanel: function(){
				var me = this;

				me.defConfig.$betInfoDom.hide();
			},

			//禁止提交按钮
			disabledSubmitButton: function(){
				var me = this;

				//去除按钮禁止状态
				me.defConfig.$submitButton.addClass('disabled');
			},

			//取消禁止状态
			unDisabledSubmitButton: function(){
				var me = this;

				//去除按钮禁止状态
				me.defConfig.$submitButton.removeClass('disabled');
			},

			//是否多次开奖
			isContinuous: function() {
				var me = this;

				return (betData['continuousPeriod'] > 0) ? true : false;
			},

			//设置期数
			setContinuousPeriod: function(data) {
				var me = this;

				betData['continuousPeriod'] = data;
			},

			//获取期数
			getContinuousPeriod: function() {
				return betData['continuousPeriod'];
			},

			//获取当前开奖次数
			getSaveCompletePeriod: function() {
				return betData['saveCompletePeriod'];
			},

			//设置本次的投注内容
			setCurrentBetData: function(data) {
				var me = this;

				betData['saveBetsInfo'] = data;
			},

			//获取本次的投注内容
			getCurrentBetData: function() {
				var me = this,
					//当前的游戏submit对象
					ORDER = host.GameOrder.getInstance();

				return ORDER.getTotal()['orders'];
			},

			//获取单次开机开奖结果
			//组装HTML数据
			getSingleOpeningHtml: function(data) {
				var me = this,
					html = '',
					tpl = me.defConfig.waitBeting;

				for (var i = 0; i < data.length; i++) {
					html += template(tpl, data[i]);
				};

				return html;
			},

			//获取单次开机开奖结果
			//组装HTML数据
			getSingleResultHtml: function(data) {
				var me = this,
					html = '',
					tpl = me.defConfig.singleResult;

				for (var i = 0; i < data.length; i++) {
					data[i]['winMoney'] = data[i]['winMoney'] > 0 ? data[i]['winMoney'] : '未中奖';
					html += template(tpl, data[i]);
				};

				return html;
			},

			//获取多次开奖完成结果
			//组装HTML数据
			getMultipleResultHtml: function(data) {
				var me = this,
					html = '',
					tpl = me.defConfig.multipleResult;

				for (var i = 0; i < data.length; i++) {
					data[i]['winMoney'] = data[i]['status'] > 0 ? data[i]['status'] : '未中奖';
					html += template(tpl, data[i]);
				};

				return html;
			},

			//单次开奖完成
			singlefinish: function(r) {
				var me = this,
					data = r['data'];

				
				//加入历史记录
				lottery.gameHistory().append(r['data']);
				//显示选号信息
				//等待开奖中状态
				me.fillInfoPanel(me.getSingleOpeningHtml(data['list']));
				me.showInfoPanel();

				//单次开奖动画
				lottery.flipball().flip(data['result'], true, function() {

					//判断中奖与否
					//中奖金额以及中奖注数
					if (Number(data['winMoney']) > 0 && Number(data['winNum']) > 0) {

						lottery.dialog('show', 'result', {
							'result': SUBMIT.formatMoney(data['winMoney']/10000)
						});
					} else {

						lottery.dialog('show', 'noprize');
					}

					//打开提示状态
					me.openWaringStatus();
					//等待开奖后显示中奖状态信息
					me.fillInfoPanel(me.getSingleResultHtml(data['list']));
					//下拉按钮恢复
					me.defConfig.$selectDom.removeClass('disabled');
					//加入开奖记录
					lottery.records().append(data['result'].split(','));
					//取消按钮禁止状态
					me.unDisabledSubmitButton();
					//更新历史记录
					lottery.gameHistory().update();
					//更新余额
					$('.refreshBall').click();
				});

				//重置数据
				me.reset();
			},

			//从多次开奖记录中
			//组装开奖面板数据
			makeMultpleData: function(data) {
				var me = this,
					saveDataArr = [];

				for (var i = 0; i < data.length; i++) {
					var current = data[i];

					saveDataArr.push({
						openNums: i + 1,
						lotteryNum: current['result'],
						status: current['winMoney']
					})
				};

				return saveDataArr;
			},

			//获取总中奖金额
			getTotalAmount: function(data){
				var me = this,	
					amount = 0;

				for (var i = 0; i < data.length; i++) {
					var current = data[i];

					amount += current['status'];
				};

				return amount;
			},

			//多次开奖完成
			continuousfinish: function() {
				var me = this,
					tpl = me.defConfig.multipleResult,
					//缓存多次开奖信息
					saveDataObject = betData['saveMultipleBetInfo'],
					//组装开奖信息
					completeData = me.makeMultpleData(saveDataObject),
					//总中奖金额
					totalAmount = me.getTotalAmount(completeData);

				//下拉按钮恢复
				me.defConfig.$selectDom.removeClass('disabled');
				//显示多次中奖信息数据
				me.fillInfoPanel(me.getMultipleResultHtml(completeData));
				//恢复投注按钮
				me.unDisabledSubmitButton();
				//恢复投注
				me.defConfig.$submitButton.removeClass('stop');
				//打开提示状态
				me.openWaringStatus();
				
				//提示多次开奖完成
				if(totalAmount > 0){

					lottery.dialog('show', 'result', {
						'result': SUBMIT.formatMoney(totalAmount/10000)
					});
				} else {

					lottery.dialog('show', 'noprize');
				}
				//重置数据
				me.reset();
			},

			SubmitCancel: function() {
				var me = this;

				me.reset();
			},

			errorReset: function(){
				var me = this;

				//打开提示状态
				me.openWaringStatus();
				//恢复投注
				me.defConfig.$submitButton.removeClass('stop');
				//恢复投注按钮
				me.unDisabledSubmitButton();
			},

			//错误处理
			controlError: function(r) {
				var me = this,
					that = me,
					message = Games.getCurrentGameMessage(),
					completeNums = me.getSaveCompletePeriod();

				//如果为多期开奖
				if (me.isContinuous()) {

					//多次开奖第一期即错误
					if (Number(completeNums) === 1) {

						//恢复操作区域
						me.errorReset();
						//错误提示
						message.show({
							type: 'normal',
							msg: r['msg'] || '服务器错误，请稍后再试。',
							data: {
								tplData: {
									msg: r['msg'] || '服务器错误，请稍后再试。'
								}
							}
						});
						//清空操作
						me.reset();

					} else {

						//错误提示
						message.show({
							type: 'normal',
							msg: r['msg'] || '服务器错误，请稍后再试。',
							data: {
								tplData: {
									msg: r['msg'] || '服务器错误，请稍后再试。'
								}
							},
							closeIsShow: true,
							closeFun: function(){
								var me = this;

								me.hide();
								//开奖过程中错误
								that.continuousfinish();
							}
						});
					}

				//单次开奖错误
				}else{

					//恢复操作区域
					me.errorReset();
					//错误提示
					message.show({
						type: 'normal',
						msg: r['msg'] || '服务器错误，请稍后再试。',
						data: {
							tplData: {
								msg: r['msg'] || '服务器错误，请稍后再试。'
							}
						}
					});

					//清空操作
					me.reset();
				}
			},

			//数据投注成功处理策略
			controlSuccess: function(r) {
				var me = this;


			},

			//ORDER数据清除
			dataReset: function() {
				var order = Games.getCurrentGameOrder();

				//清空订单
				order.reSet();
				//添加取消编辑
				order.cancelSelectOrder();
				//清空
				Games.getCurrentGame().getCurrentGameMethod().reSet();
			},

			reset: function() {
				var me = this;

				//重置相关数据
				betData = {
					//连续开奖期数
					continuousPeriod: 1,
					//游戏投注记录
					//用于多期投注汇总
					betDataHistory: [],
					//游戏已经投注次数记录
					saveCompletePeriod: 0,
					//缓存投注信息
					saveBetsInfo: [],
					//缓存多次中奖信息
					saveMultipleBetInfo: []
				}

				//连续开奖为默认1次
				me.lotteryInput.setValue(1);
			},

			//停止多期投注
			stopMultipleBet: function(){
				var me = this;

				//停止按钮禁止状态
				me.defConfig.$submitButton.addClass('disabled');
				//设置当前的连续开奖期数
				//已达到快速开奖
				me['quickStopMark'] = true;
				//快速终止动画
				lottery.flipball().quickflip();
			},

			//投注接口
			startBeting: function() {
				var me = this,
					button = me.defConfig.$submitButton;

				//是否有禁止标识
				if(button.hasClass('disabled')){
					return;
				}

				//是否有停止标识
				//进行停止多期操作
				if(button.hasClass('stop')){
					me.stopMultipleBet();
					return;
				}

				//开奖接口
				me.singleBets();
			},

			//单独单独投注
			singleBets: function() {
				var me = this;

				//加入按钮禁止状态
				me.defConfig.$submitButton.addClass('disabled');
				//投注记录递增
				betData['saveCompletePeriod']++;
				//调取投注接口
				SUBMIT.submitData();
			},

			//多次开奖过程
			//其中单次开奖
			continuousBets: function(r) {
				var me = this,
					data = r['data'];

				//加入历史记录
				lottery.gameHistory().append(r['data']);
				//显示选号信息
				//等待开奖中状态
				me.fillInfoPanel(me.getSingleOpeningHtml(data['list']));
				me.showInfoPanel();

				//去除按钮禁止状态
				me.unDisabledSubmitButton();

				lottery.flipball().flip(data['result'], true, function() {

					//判断中奖
					//中奖金额以及中奖注数
					if (Number(data['winMoney']) > 0 && Number(data['winNum']) > 0) {

						lottery.dialog('show', 'win', {
							'amount': SUBMIT.formatMoney(data['winMoney']/10000),
							'num': data['winNum']
						});
					} else {

					}

					//等待开奖后显示中奖状态信息
					me.fillInfoPanel(me.getSingleResultHtml(data['list']));
					//加入开奖记录
					lottery.records().append(data['result'].split(','));
					//缓存多次开奖的中奖信息
					betData['saveMultipleBetInfo'].push(data);
					//更新历史记录
					lottery.gameHistory().update();
					//更新余额
					$('.refreshBall').click();

					//多次开奖结束
					if (Number(betData['saveCompletePeriod']) == Number(betData['continuousPeriod'])) {
						//结束
						me.continuousfinish(r);
					} else {
						//停止按钮禁止状态
						me.defConfig.$submitButton.addClass('disabled');
						//如果为快速开奖
						//则中断连续操作
						if (me['quickStopMark']) {
							me.continuousfinish();
							me['quickStopMark'] = false;
						} else {
							//加入多次摇奖间隔时间
							setTimeout(function() {
								//投注记录递增
								betData['saveCompletePeriod']++;
								//秒秒彩投注不进行用户验证
								SUBMIT.submitData();
							}, 2000);
						}
					}
				});

				//------------------调试
				// $('#msg-area').html('多次开奖，第' + betData['saveCompletePeriod'] + '次');
				lottery.dialog('show', 'times', {
					'times': betData['saveCompletePeriod']
				});
			},

			//提交
			postBetsData: function() {

			}

		};

		//继承GameMethod
		var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
		//将实例挂在游戏管理器实例上
		Games[name] = new Main();

	})(phoenix, phoenix.Event, 'MMCBellMethod');

})();