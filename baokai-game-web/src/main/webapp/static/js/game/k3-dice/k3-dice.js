;(function($){
	function F(t, o){
		this.opts = $.extend({
			sheetElement: '#J-dice-sheet',
			barElement: '#J-dice-bar',
			chipsAnimationDelay: 600, // 筹码动画时间
			chipsAnimationDelay2: 100, // 筹码抽离时间
			chipSize: [50, 46], // width/height
			chipStep: 2, // 筹码重叠间距
			chipZindex: 2,
			actionTipsTxt: '该注单超过限额',
			actionTipsDelay: 600,
			/*config: {
				chips: [1,2,5,10,20,50,100,500,1000,5000],
				chipsSelected: [10,20,50,100,500]
			},*/
			gameConfig: function(){},
			message: function(){},
			gameHistory: function(){},
			initContinus: 1, // 默认连投数
			initMultiple: 1, // 默认倍投数
			debugs: true // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
		}, o);
		this.slides = [];
		this.$t = $(t);
		// 投注金额
		this.betAmount = 0;
		// 投注总金额
		this.betCountAmount = 0;
		// 连投次数
		this.continusTimes = 1;
		// 倍投数
		this.multipleTimes = 1;

		this.gamelimit = {};
		this.debugs = this.opts.debugs;
		// this.isIE6 = $.browser.msie && parseFloat($.browser.version) < 7;
		this.init();
	}
     

	F.prototype = {
		init: function(){
			var opts = this.opts;

			this.gameConfig = opts.gameConfig;
			this.message = opts.message;
			this.gameHistory = opts.gameHistory;

			var config = this.gameConfig.getConfig()
			this.setConfig(config);

			// 操作记录
			this.actionLogs = [];
			// 投注信息
			this.betData = {};

			this.$sheetWrap = $(opts.sheetElement);
			this.$bar = $(opts.barElement);
			// 桌布玩法面板
			this.createDiceSheet();
			// 投注余额区
			this.createBalance();
			// 筹码区块
			this.createDiceChip();
			// 连投
			this.$continus = this.counterHtml();
			this.continusCounter = this.$continus.counter();
			// 倍投
			this.$multiple = this.counterHtml();
			this.multipleCounter = this.$multiple.counter();
			// 按钮操作区
			this.createActionButtons();
			// 投注历史toggle按钮
			this.createHistoryToggle();
			// Game Tips
			this.createGameTips();
			// Action Tips
			this.createActionTips();
			this.setActionTipsTxt(opts.actionTipsTxt);

			// 筹码金额数组，主要用于rebuildChip计算用
			this.chips = config.chips.reverse();
			// 当前筹码金额
			this.setChipValue( config.chipsSelected[0] );
			// 投注金额
			this.setBetAmount(0);
			// 连投次数
			this.setContinusTimes(opts.initContinus);
			// 倍投数
			this.setMultipleTimes(opts.initMultiple);

			// 余额
			// this.balance = this.getBalance();
			this.getBalance();

			/*状态值*/
			// 是否还在执行动画，因为动画较多，动画执行过程中是不允许下单等操作的进行
			this.setAnimationStatus(false);

			// event
			this.checkActionsStatus();
			this.bindEvent();
		},
		// 生成玩法区
		createDiceSheet: function(){
			var html = '';
			// 从0开始
			$.each(this.gameConfig.getBallLists(), function(i, n){
				html += '<div class="dice-sheet dice-sheet-' + i + '" data-name="' + n['name'] + '">';
					html += '<div class="dice-sheet-bg"></div>';
				html += '</div>';
			});
			this.$sheetWrap.append(html);
			return this.$sheets = this.$sheetWrap.find('.dice-sheet');
		},
		// 生成余额区
		createBalance: function(){
			var html =
				'<div class="dice-balance">' +
					'<div class="bet-amount"><label>下注额:</label>￥<span data-count-amount>0.00</span></div>' +
					'<div class="bet-balance"><label>余额:</label>￥<span data-user-balance>21,500,000.00</span>' +
						'<i class="ui-icon bet-balance-refresh" data-action="refresh">刷新</i>' +
						'<a class="ui-icon bet-balance-recharge" data-action="recharge" href="javascript:void(0)">充值</a>' +
					'</div>' +
				'</div>';			
			this.$bar.append(html);
			this.$betCountAmount = this.$bar.find('[data-count-amount]');
			this.$userBalance = this.$bar.find('[data-user-balance]');
			this.$balanceRefresh = this.$bar.find('[data-action="refresh"]');
			this.$balancerecharge = this.$bar.find('[data-action="recharge"]');
		},
		// 生成筹码区
		createDiceChip: function(){
			var me = this,
				config = me.getConfig(),
				chips = config.chips,
				chipsSelected = config.chipsSelected,
				html = '<div class="dice-chip-wrap">';
				html1 = '<div class="dice-chip-choose">',
				html2 = '<div class="dice-chip-custom">';
			// me.debug(chips)
			$.each(chips, function(idx, val){
				var cl = $.inArray(val, chipsSelected) < 0 ? ' dice-chip-hide' : '';
				html1 += '<div data-value="' + val+ '" class="dice-chip dice-chip-' + val + cl + '">' + val + '</div>';
				html2 += '<div data-value="' + val+ '" class="dice-chip-mirror dice-chip-' + val + cl +  '">' + val + '</div>';
			});
			html += html1 + '<div class="dice-chip-shadow"></div></div>';
			html += '<div class="dice-chip-button">自定义</div>';
			html += html2 + '</div>';
			me.$bar.append(html);
			
			this.$chipsWrap = this.$bar.find('.dice-chip-choose');
			this.$chipsButton = this.$bar.find('.dice-chip-button');
			this.$chipsCustom = this.$bar.find('.dice-chip-custom');
			this.$chipsMirror = this.$bar.find('.dice-chip-mirror');
			this.$chips = this.$bar.find('.dice-chip');
			var arr = [];
			this.$chipsMirror.each(function(){
				if( !$(this).hasClass('dice-chip-hide') ){
					arr.push($(this));
				}
			});
			this.setChipsMirror(arr);
		},
		// 生成事件按钮区
		createActionButtons: function(){
			var html ='<div class="action-buttons">';
			// 下注
			html += '<button class="ui-button ui-button-primary button-bet" data-action="bet">下注</button>';
			// 撤销
			html += '<button class="ui-button button-revocation" data-action="revocation">撤销</button>';
			// 清空
			html += '<button class="ui-button button-clear" data-action="clear">清空</button>';

			html += '</div>';
			this.$bar.append(html);
			return this.$actions = this.$bar.find('button[data-action]');
		},
		// 生成投注历史开关按钮
		createHistoryToggle: function(){
			var html = 
				'<div class="history-toggle"> \
					<span>投注记录</span> \
					<i></i> \
				</div>';
			this.$bar.append(html);
		},
		// 生成游戏玩法tips
		createGameTips: function(){
			var config = this.getConfig(),
				gameMethods = config['gameMethods'],
				html = '';
			$.each(gameMethods, function(i, n){
				html += '<div class="ui-icon game-tips game-tips-' +n.gameType+ '" data-tips-title="' +n.gameTips+ '"></div>';
			});
			this.$sheetWrap.append(html);
			return this.$tips = this.$sheetWrap.find('.game-tips');
		},
		// 生成筹码操作提示tips
		createActionTips: function(){
			this.$actionTips = $('<div class="ui-tips ui-tips-alert ui-tips-bottom"><span class="ui-tips-text"></span><i class="ui-tips-arrow"></i></div>');
			this.$actionTips.css({
				position: 'absolute',
				zIndex: 100
			}).appendTo('body').animate({opacity: 'hide'}, 0);
		},
		// 生成计数器html
		counterHtml: function(){
			var html = 
				'<div class="ui-simulation-wrap">' +
					'<div class="ui-simulation-title" data-counter-title></div>' +
					'<div class="ui-icon game-tips"></div>' +
					'<div class="ui-tips ui-tips-right"><span class="ui-tips-text"></span><i class="ui-tips-arrow"></i></div>' +
					'<div class="ui-simulation-counter">' +
						'<span class="ui-icon counter-action counter-decrease" data-counter-action="decrease">－</span>' +
						'<input type="text" value="1" class="J_counter">' +
						'<span class="ui-icon counter-action counter-increase" data-counter-action="increase">＋</span>' +
					'</div>' +
				'</div>';
			return $(html).appendTo(this.$bar);
		},
		/* 获取dom相关接口 */
		// 获取当前容器
		getTargetElements: function(){
			return this.$t;
		},
		// 获取筹码（可投放筹码）
		getChipsElements: function(){
			return this.$chips;
		},
		// 自定义按钮
		getChipsButtonElements: function(){
			return this.$chipsButton;
		},
		// 自定义筹码区（外围容器）
		getChipsCustomElements: function(){
			return this.$chipsCustom;
		},
		// 自定义筹码
		getChipsMirrorElements: function(){
			return this.$chipsMirror;
		},
		// 获取所有玩法元素
		getSheetsElements: function(){
			return this.$sheets;
		},
		// 获取操作按钮（下注、撤销、清空）
		getActionsElements: function(){
			return this.$actions;
		},
		// 获取余额刷新按钮
		getBalanceRefreshElements: function(){
			return this.$balanceRefresh;
		},
		// 获取充值按钮
		getBalanceRechargeElements: function(){
			return this.$balancerecharge;
		},
		// 获取游戏玩法元素
		getTipsElements: function(){
			return this.$tips;
		},
		// 设置当前选中的筹码元素
		setSelectedChipElements: function($selected){
			this.$selectedChip = $selected;
		},
		// 获取当前选中的筹码元素
		getSelectedChipElements: function(){
			return this.$selectedChip;
		},
		// 获取操作tips元素
		getActionTipsElements: function(){
			return this.$actionTips;
		},
		/* 获取参数相关 */
		// 获取连投组件，为jQuery对象
		getContinusCounter: function(){
			return this.continusCounter;
		},
		// 获取倍投组件，为jQuery对象
		getMultipleCounter: function(){
			return this.multipleCounter;
		},
		// 设置筹码动画状态
		setAnimationStatus: function(animation){
			this.isAnimation = animation;
		},
		// 获取筹码动画状态
		getAnimationStatus: function(){
			return this.isAnimation;
		},
		// 设置操作tips内容
		setActionTipsTxt: function(txt){
			this.actionTipsTxt = txt;
		},
		// 获取操作tips内容
		getActionTipsTxt: function(){
			return this.actionTipsTxt;
		},
		// 设置选中的自定义筹码数组
		setChipsMirror: function(arr){
			this.chipsMirror = arr;
		},
		// 获取选中的自定义筹码[数组]
		getChipsMirror: function(){
			return this.chipsMirror;
		},
		// 设置当前选择筹码的金额值
		setChipValue: function(value){
			this.chipValue = value;
		},
		// 获取当前选中筹码的金额数
		getChipValue: function(value){
			return this.chipValue;
		},
		// 设置玩法限额列表(全局)
		setGameLimit: function(limit){
			if( limit ){
				return this.gamelimit = limit;
			}
			var me = this,
				gamelimit = me.getDynamicConfig()['gamelimit'][0],
				factor = me.getDynamicConfig()['multipleToCnyFactor'];
			$.each(gamelimit, function(i,n){
				me.gamelimit[i] = n['maxmultiple'] * factor;
			});
			// me.debug(me.gamelimit);
		},
		// 获取玩法限额列表
		getGameLimit: function(){
			return this.gamelimit;
		},
		// 设置玩法投注金额(val为变化后数值)
		setSheetData: function($t, val){
			var v = this.getSheetData($t);
			val = parseFloat(val) || 0;
			if( val != 0 ){
				// this.debug(v, val);
				// 缓存当前数据
				$t.data('value', v + val );
				// 重构当前节点下筹码
				this.rebuildChip($t);
				// 设置投注总金额
				this.setBetAmount(val);
				// 重构当前投注信息
				var key = this.getSheetsElements().index( $t.eq(0) );
				this.setBetData(key, val);
				if( $t.tips() ){
					$t.tips().setText(v+val);
				}

				this.checkActionsStatus();
			}			
		},
		// 获取玩法投注金额
		getSheetData: function($t){
			return parseFloat( $t.data('value') ) || 0;
		},
		// 设置投注总金额
		setBetCountAmount: function(){
			var amount = this.getBetAmount(),
				multiple = this.getMultipleTimes(),
				continus = this.getContinusTimes(),
				betCountAmount = amount * multiple * continus;
			this.$betCountAmount.text(this.currency(betCountAmount));
			return this.betCountAmount = betCountAmount;
		},
		// 获取投注总金额
		getBetCountAmount: function(){
			return this.betCountAmount;
		},
		// 设置投注金额（单倍、单次）
		setBetAmount: function(val){
			val = parseFloat(val) || 0;
			this.betAmount += val;
			this.setBetCountAmount();
		},
		// 获取投注金额（单倍、单次）
		getBetAmount: function(){
			return this.betAmount;
		},
		// 设置连投次数
		setContinusTimes: function(times){
			times = parseFloat(times) || 0;
			// this.debug(times);
			this.continusTimes = times;
			this.setBetCountAmount();
		},
		// 获取连投次数
		getContinusTimes: function(){
			return this.continusTimes || 1;
		},
		// 设置倍投次数
		setMultipleTimes: function(times){
			var me = this,
				times = parseFloat(times) || 1;
			// me.debug(times);
			me.multipleTimes = times;
			me.setBetCountAmount();
			$.each(me.getBetData(), function(key, value){
				me.setBetDataMultiple(key, times);
			});
		},
		// 获取倍投次数
		getMultipleTimes: function(){
			return this.multipleTimes;
		},
		// 设置投注信息（val为金额变化值，如果变化后为0，删除该条信息）
		setBetData: function(key, val){
			var me = this,
				betData = me.getBetData()[key] || {};

			if( $.isEmptyObject(betData) ){
				me.getBetData()[key] = betData;
			}

			value = betData.amount ? betData.amount + val : val;
			if( !value ){
				delete me.betData[key];
			}else{
				var factor = me.getDynamicConfig()['multipleToCnyFactor'],
					order = me.gameConfig.getBallOrder(key),
					type = me.gameConfig.getBallType(key);
				betData = {
					ball: order,
					id: key, // 从0开始
					moneyunit: 1,
					multiple: me.getMultipleTimes(),
					amount: value,
					num: value / factor,
					type: type + '.' + type
				};
				return me.betData[key] = betData;
			}		
			// me.debug(me.betData);
		},
		// 获取投注信息
		getBetData: function(){
			return this.betData;
		},
		// 设置投注信息倍投数
		setBetDataMultiple: function(key, times){
			var me = this,
				betData = me.getBetData()[key] || {};

			if( !$.isEmptyObject(betData) ){
				betData['multiple'] = times;
			}
		},
		// 设置当前余额
		setBalance: function(balance){
			balance = this.currency( balance || 0 );
			this.$userBalance.text( balance );
			$('#spanBall').text( balance );
			return this.balance = balance;
		},
		// 获取账户余额
		getBalance: function($t){
			var me = this;
			$.ajax({
				dataType: 'json',
				cache: false,
				url: me.config.queryUserBalUrl,
				data: '',
				success:function(data){
					var balance = data == '' ? 0 : data;
					balance = balance / 10000;
					me.setBalance(balance);	
					if( $t && $t.length ){
						$t.removeClass('onhandled');
					}
				}
			});
		},
		/* 配置相关 */
		// 设置初始化配置信息及方法
		setConfig: function(config){
			this.config = config;
			// this.debug(config);
			this.gameMethods = config.gameMethods;
		},
		// 获取初始化配置信息及方法
		getConfig: function(){
			return this.config;
		},
		// 设置动态配置信息
		setDynamicConfig: function(config){
			this.dynamicConfig = config;
			// this.debug(config);
			this.setGameLimit();
		},
		// 获取当前动态配置
		getDynamicConfig: function(){
			return this.dynamicConfig;
		},
		// 重新获取服务器端动态配置
		getServerDynamicConfig: function(callback){
			var me = this;
			$.ajax({
				url: me.gameConfig.getDynamicConfigUrl(),
				dataType: 'json',
			    cache: false,
				success:function(data){
					if(Number(data['isSuccess']) == 1){
						me.setDynamicConfig(data['data']);
						if($.isFunction(callback)){
							callback.call(me, data['data']);
						}
						me.multipleCounter.setMaxValue(data['data']['multiplemaxtimes'] || 1);
						me.continusCounter.setMaxValue(data['data']['tracemaxtimes'] || 1);
					}
				}
			});
		},
		// 初始化绑定事件
		bindEvent: function(){
			var me = this;

			me.getServerDynamicConfig(function(){
				$('body').trigger('dynamicConfigChange');
				$('body').trigger('dynamicConfigOnReady');
			});

			// 连投
			me.getContinusCounter().setOnSetValue(function(value){
				me.setContinusTimes(value);
			});
			// 倍投
			me.getMultipleCounter().setOnSetValue(function(value){
				me.setMultipleTimes(value);
			});

			me.$t.on('animate.start', function(){
				return me.setAnimationStatus(true);
			}).on('animate.end', function(){
				return me.setAnimationStatus(false);
			});
		},
		// 检查投注是否已经超过该玩法的限额（超过返回ture）
		chipIsOverLimit: function($t, val){
			var me = this,
				data = $t && $t.data() || {},
				gamelimit = me.getGameLimit(),
				multiple=me.multipleCounter.$input.val(),
				key=this.getSheetsElements().index( $t.eq(0) ),
				order = me.gameConfig.getBallOrder(key),
				array=[["3",1111],["4",3333],["5",6667],["6",11111],["7",16667],["8",23529],["9",28571],["10",30769],["11",30769],["12",28571],["13",23529],["14",16667],["15",11111],["16",6667],["17",3333],["18",1111]],
				val = parseFloat(val) || 0;
			    var limit = gamelimit[data['name'] || ''] || 0,
				value = parseFloat(data['value']) || 0;
				if(data['name']=='hezhi')
				{
					var arrorder=new Array(order);
					for(var i=0;i<array.length;i++)
					{
						if($.inArray(array[i][0], arrorder)> -1)
						{
						   gamelimit.hezhi=array[i][1];
						   limit=array[i][1]*2;
						}
					}
				}
			 console.log(gamelimit, data, value, val, limit);
			 
			
			if(limit>=0 && value + val * parseFloat(multiple) > limit  ){
				
				return true;
			}else{
				return false;
			}
		},
		//坚持当前玩法时候停售
		checkisPlaystoped:function($t){
			var me = this,
				data = $t && $t.data() || {},
				gamelimit = me.getGameLimit();
				return (data['name'] in gamelimit);
		},
		// 显示操作提示tips
		showActionTips: function($t, msg){
			var me = this,
				$tips = me.getActionTipsElements(),
				offset = $t.offset(),
				fix = 10;
			if( me.actionTipsTimer ){
				clearTimeout(me.actionTipsTimer);
				me.actionTipsTimer = null;
			}
			$tips.find('.ui-tips-text').html(msg || me.getActionTipsTxt());
			$tips.css({
				left: offset.left + ( $t.outerWidth() - $tips.outerWidth() ) / 2,
				top: offset.top + $t.outerHeight() + fix
			}).animate({
				opacity: 'show'
			}, function(){
				me.actionTipsTimer = setTimeout(function(){
					$tips.animate({
						opacity: 'hide'
					});
				}, me.opts.actionTipsDelay);
			});
		},
		// 点击玩法下筹码操作动画
		moveChip: function($t){
			if( !$t || !$t.length ) return;

			var me = this,
				$selectedChip = me.getSelectedChipElements(),
				$chip = $selectedChip.clone(),
				value = $chip.data('value'),
				offset1 = $t.offset(),
				offset2 = $selectedChip.offset();
			// $chip.removeClass().addClass('dice-chip dice-chip-' + value).appendTo(me.$t);
			/*$chip.removeClass (function (index, css) {
				return (css.match (/(^|\s)color-\S+/g) || []).join(' ');
			}).appendTo(me.$t);*/
			me.$t.trigger('animate.start');
			$chip.appendTo(me.$t);
			var css = {
					position: 'absolute',
					top: offset2.top,
					left: offset2.left,
					marginTop: 0,
					zIndex: me.opts.chipZindex
				},
				css1 = {
					top: offset2.top - 20
				},
				css2 = {
					top: offset1.top + ( $t.outerHeight() - $chip.outerHeight() ) / 2,
					left: offset1.left + ( $t.outerWidth() - $chip.outerWidth() ) / 2
				};
			//判断是否停售
			if( !me.checkisPlaystoped($t)){
				$chip
					.css(css)
					.animate(css1, me.opts.chipsAnimationDelay2)
					.animate(css2, me.opts.chipsAnimationDelay)
					.animate(offset2, me.opts.chipsAnimationDelay, function(){
						$(this).remove();
						me.$t.trigger('animate.end');
					});
				me.showActionTips($t,'该玩法暂时停售');
				return;
			}

			if( me.chipIsOverLimit($t, value) ){
				$chip
					.css(css)
					.animate(css1, me.opts.chipsAnimationDelay2)
					.animate(css2, me.opts.chipsAnimationDelay)
					.animate(offset2, me.opts.chipsAnimationDelay, function(){
						$(this).remove();
						me.$t.trigger('animate.end');
					});
				me.showActionTips($t);
			}else{
				$chip
					.css(css)
					.animate(css1, me.opts.chipsAnimationDelay2)
					.animate(css2, me.opts.chipsAnimationDelay, function(){
						me.addActionLog({
							from   : me.getSelectedChipElements(),
							to     : $t,
							amount : value,
							type   : 'bet'
						});
						me.setSheetData($t, value);
						$(this).remove();
						me.$t.trigger('animate.end');
					});
			}
				
		},
		// 重建已投筹码DOM
		rebuildChip: function($t, value){
			if( !$t.length ) return;
			var me = this,
				value = value || me.getSheetData($t),
				_chips = me.getChipCombination(value),
				html = '',
				mtop = -me.opts.chipSize[1] / 2,
				step = me.opts.chipStep;
			
			// me.debug(_chips);
			// me.debug(me.getBetAmount());
			// me.debug(me.actionLogs);

			me.removeAllChips($t);
			// 根据新的数组来生成DOM
			$.each(_chips, function(idx, val){
				var $chip = $( '<div data-value="' + val+ '" class="dice-chip dice-chip-' + val + '">' + val + '</div>' );
				$chip.css({
					marginTop: mtop - idx * step
				}).appendTo($t);
			});
		},
		// 根据金额获取筹码组合
		getChipCombination: function(value){
			value = parseFloat(value) || 0;
			var chips = this.chips,
				_chips = [];

			$.each(chips, function(idx, chip){
				// me.debug(value)
				var k = Math.floor( value / chip );
				// _chips[idx] = k;
				if( k >= 1 ){
					value = value % chip;
					for(; k>0; k--){
						_chips.push(chip);
					}
				}
			});
			return _chips;
		},
		// 从桌上移除所有的筹码
		removeAllChips: function($t){
			$t.find('.dice-chip').remove();
		},
		// 禁止某dom元素
		disabledDom: function(dom){
			var $t = $(dom);
			if( $t.length ){
				$t.prop('disable', true).addClass('disabled');
			}
		},
		// enable某dom元素
		enabledDom: function(dom){
			var $t = $(dom);
			if( $t.length ){
				$t.prop('disable', false).removeClass('disabled');
			}
		},
		// 检查操作按钮状态
		checkActionsStatus: function(){
			var me = this;
			// 检查下注
			var bet = !$.isEmptyObject(me.getBetData());
			// 检查撤销
			var revocation = me.actionLogs.length ? true : false;
			// 检查清空
			var clear = bet;

			$.each({
				bet: bet,
				revocation: revocation,
				clear: clear
			}, function(key, value){
				var $t = me.$actions.filter('[data-action="' + key + '"]');
				if( value ){
					$t.prop('disable', false).removeClass('disabled');
				}else{
					$t.prop('disable', true).addClass('disabled');
				}
			});
		},
		// 添加log至数组
		// {from,to,amount,type}
		addActionLog: function(log){
			this.actionLogs.push(log);
		},
		// 从数组中移除log并返回
		removeActionLog: function(){
			// var poped = ;
			// if( !this.checkActionsStatus().revocation ){
			// 	this.disabledDom('[data-action="revocation"]');
			// }
			return this.actionLogs.pop();
		},
		// 移动所有的筹码至指定offset（下注和清空时需要用到）
		moveAllChips: function(offset, callback){
			var me = this,
				$chips = me.$sheets.find('.dice-chip'),
				len = $chips.length,
				offset = offset || {top:280,left:'50%'};
			$chips.each(function(i, n){
				var $t = $(this),
					_offset = $t.offset(),
					$chip = $t.clone();

				$chip
					.css({
						position: 'absolute',
						left: _offset.left,
						top: _offset.top,
						zIndex: me.opts.chipZindex
					})
					.appendTo('body')
					.animate({
						top: offset.top,
						left: offset.left,
						opacity: 0
					}, me.opts.chipsAnimationDelay, function(){
						$(this).remove();
					});

				$t.remove();
				if( i >= len - 1 ){
					me.resetData();
					if( callback && $.isFunction(callback) ){
						setTimeout(callback, me.opts.chipsAnimationDelay+200);
					}
				}
			});
		},
		// 撤销投注操作
		actionRevocation: function(){
			var me = this,
				log = me.removeActionLog(),
				type = log['type'] || '',
				amount = log['amount'] || 0,
				$from = log['from'] || $(null),
				$to = log['to'] || $(null);

			me.$t.trigger('animate.start');

			if( type != 'recycle' ){
				me.setSheetData($to, -amount);
			}

			if( type == 'bet' ){
				var $chip = $from.clone(),
					offset1 = $to.offset(),
					offset2 = $from.offset();
				if( $from.hasClass('dice-chip-hide') ){
					offset2 = $from.parent().offset();
				}
				$chip.appendTo(me.$t);
				var css1 = {
						top: offset1.top + $to.outerHeight()
					},
					css2 = {
						top: offset2.top,
						left: offset2.left,
						opacity: 0
					};
				// me.debug(css1, css2);
				$chip
					.css({
						position: 'absolute',
						top: offset1.top + ( $to.outerHeight() - $chip.outerHeight() ) / 2,
						left: offset1.left + ( $to.outerWidth() - $chip.outerWidth() ) / 2,
						zIndex: me.opts.chipZindex
					})
					// .animate(css1, me.opts.chipsAnimationDelay2)
					.animate(css2, me.opts.chipsAnimationDelay, function(){
						$(this).remove();
						me.$t.trigger('animate.end');
					});
			}else if( type == 'move' || type == 'recycle' ){
				var _chips = me.getChipCombination(amount),
					offset1 = $to.offset(),
					offset2 = $from.offset();
				$.each(_chips, function(idx, val){
					var $chip = $( '<div data-value="' + val+ '" class="dice-chip dice-chip-' + val + '">' + val + '</div>' ).appendTo(me.$t),
						w = $chip.outerWidth(),
						h = $chip.outerHeight();

					$chip.css({
						position: 'absolute',
						top: offset1.top + ( $to.outerHeight() - h ) / 2,
						left: offset1.left + ( $to.outerWidth() - w ) / 2,
						zIndex: me.opts.chipZindex
					}).animate({
						top: offset2.top + ( $from.outerHeight() - h ) / 2,
						left: offset2.left + ( $from.outerWidth() - w ) / 2
					}, me.opts.chipsAnimationDelay, function(){
						me.setSheetData($from, val);
						$(this).remove();
						me.$t.trigger('animate.end');
					});
				});
			}
			me.checkActionsStatus();
		},
		// 清空操作
		actionClear: function(){
			var $t = this.$chipsWrap,
				_offset = $t.offset(),
				offset = {
					top: _offset.top + $t.outerHeight() / 2,
					left: _offset.left + $t.outerWidth() / 2
				};
			this.moveAllChips(offset);
		},
		// 下注操作
		actionBet: function(){
			var me = this,
				$t = this.$sheetWrap,
				_offset = $t.offset(),
				offset = {
					top: _offset.top - 50,
					left: _offset.left + $t.outerWidth() / 2
				},
				data = me.getSubmitData();
			
			me.debug(data);

			// 提示至少选择一注
			if( $.isEmptyObject(data) || $.isEmptyObject(data.balls) ){
				me.message.show({
					type : 'mustChoose',
					msg : '请至少选择一注投注号码！',
					data : {
						tplData : {
							msg : '请至少选择一注投注号码！'
						}
					}
				});
				return;
			}

			var isTure = true;
			me.message.show({
				type: 'checkLotters',
				msg: '请核对您的投注信息！',
				confirmIsShow: true,
				confirmFun: function () {
					//判断加锁
					/*if (me.postLock) {
						return;
					} else {
						//加锁
						me.doPostLock();
						me.fireEvent('beforeSubmit');
					}*/
					data.ordersNumber = undefined; //去除ordersNumber，后台不接收
					data.onlyAmount = undefined;
					// data.amount = phoenix.util.formatFloat(data.amount);

					$.ajax({
						url: me.config.sumbitUrl,
						data: JSON.stringify(data),
						dataType: 'json',
						method: 'POST',
						cache: false,
						contentType: 'application/json; charset=utf-8',
						beforeSend: function(){
							if (isTure) {
								$('.btn-important').html('提交中...');
								$('.ui-js-btn-cancel').css('display', 'none').attr("disabled", "true");
								isTure = false;
							} else {
								return false;
							}
						},
						success: function(r){
							if (Number(r['isSuccess']) == 1) {
								// 推荐查询地址
								var dataTrace = Number(data['isTrace']) > 0 ? 1 : 0;
								if(dataTrace > 0){
									r.data.tplData["url"]="/gameUserCenter/queryPlans?time=7";
									r.data.tplData["gameType"]= '追号记录';
								}else{
									r.data.tplData["url"]="/gameUserCenter/queryOrdersEnter?time=7";
									r.data.tplData["gameType"]= '投注记录';
								}
								me.message.hide();
								me.moveAllChips(offset, function(){
									setTimeout(function(){
										me.message.show(r);
										me.resetData();
									}, me.chipsAnimationDelay);
									me.afterSubmitSuccess(r);
								});
							} else {
								me.message.show(r);
							}
						},
						complete: function () {
							// me.fireEvent('afterSubmit');
							isTure = true;
							$('.ui-js-btn-cancel').css('display', 'display').removeAttr("disabled");
						},
						error: function (xhr) {
							if(xhr.status == '404' || xhr.status == '0' || xhr.status == '401' ){
								me.message.show({
									mask: true,
									title: '温馨提示',
									content: "<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>登录超时 请重新登入！</h4></div>",
									cancelIsShow: true,
									cancelFun: function () {
										window.location.reload();                              
									}
								});
							}else{
								me.message.show({
									mask: true,
									title: '温馨提示',
									content: "<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>方案提交失败,<br />请检查网络并重新提交！</h4></div>",
									cancelIsShow: true,
									cancelFun: function () {
										me.message.hide();
									}
								});
							}
							
						}
					});
				},
				cancelIsShow: true,
				cancelFun: function () {
					//请求解锁
					// me.cancelPostLock();
					this.hide();
				},
				normalCloseFun: function () {
					//请求解锁
					// me.cancelPostLock();
				},
				callback: function () {					
					var backOutStartFee = Number(me.getConfig()['backOutStartFee']) / 10000, // 返手续费起始金额
						backOutRadio = Number(me.getConfig()['backOutRadio']) / 10000, // 返金额率	
						backAmount = me.moneyForamtToFloat(data['amount']);
						
					if(backAmount > backOutStartFee){
						$('#showLotteryCharge').show();
						me.message.getContentDom().find('.handlingCharge').html(me.currency(backAmount * backOutRadio, 3));
					}
				},
				data: {
					tplData: {
						//当期彩票详情
						lotteryDate: data['ordersNumber'],
						//彩种名称
						lotteryName: me.gameConfig.getGameTypeCn(),
						//投注详情
						lotteryInfo: function () {
							var html = '',
								balls = data['balls'];
							for (var i = 0; i < balls.length; i++) {
								var current = balls[i];
								html += '<div style="line-height:25px;">' + me.gameConfig.getTitleByName(current['type']) + ' ' + current.ball + '</div>';
							};
							return html;
						},
						// 彩种金额
						lotteryamount: data['amount'],
						// 付款帐号
						lotteryAcc: me.getDynamicConfig()['username'],
						
						lotteryOnlyAmount : data['onlyAmount'],
						
						lotteryTraceInfo : function (){
							var html = '';
							if(data['isTrace'] > 0){
								html = data['orders'];
							}
							return html;
						}						
					}
				}
			});
			
			// console.log( typeof JSON.stringify(data) );
			// me.moveAllChips(offset);
			// me.debug(params);
		},
		// 重置所有数据
		resetData: function(){
			this.removeAllChips(this.$sheetWrap);
			this.betData = {};
			this.actionLogs = [];
			this.checkActionsStatus();
			this.betAmount = 0;
			// 连投
			this.continusCounter.setValue(0);
			this.getContinusCounter().setValue(0);
			// 倍投
			this.multipleCounter.setValue(1);			
			this.getMultipleCounter().setValue(1);
			this.setBetCountAmount();
			this.$sheets.data('value', 0);
			this.getBalance();
		},
		// 获取投注信息
		getSubmitData: function(){
			var me = this,
				orders = [],
				traceTimes = me.getContinusTimes(),
				balls = [],
				betData = me.getBetData(),
				result = {};

			if( $.isEmptyObject(betData) ){
				return {};
			}

			$.each(betData, function(i,n){
				balls.push(n);
			});
			
			var gamenumbers = me.getDynamicConfig().gamenumbers;
			$.each(gamenumbers, function(i,n){
				if( i >= traceTimes ){
					return;
				}
				orders.push({
					number: n['number'],
					issueCode: n['issueCode'],
					multiple: 1
				});
			});

			result = {
				gameType: me.gameConfig.getGameType(),
				isTrace: me.getContinusTimes() > 1 ? 1 : 0,
				multiple: me.getMultipleTimes(), // 倍投数
				trace: traceTimes, // 追号期数
				// traceWinStrop: 0,
				// traceStopValue: -1,
				amount: me.currency(me.getBetCountAmount()),
				balls: balls,
				orders: orders
			}

			// 追号
			if( traceTimes > 1 ){
				result['ordersNumber'] = "第 " + orders[0]['number'] + "  至  " + orders[traceTimes - 1]['number'] + "   共  <strong class='color-red'>" + traceTimes + "</strong></span>  期";
				// 单期金额
				result['onlyAmount'] = betData.betAmount;
			}else{
				result['ordersNumber'] = '第 ' + orders[0]['number'] + ' 期'; //
				result['onlyAmount'] = '';
			}
			return result;
		},
		// 根据下单数据生成投注
		/*{
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
		}*/
		renderSheetFromData: function(data){
			var me = this,
				balls = data['balls'] || {},
				factor = me.getDynamicConfig()['multipleToCnyFactor'];
			if( $.isEmptyObject(balls) ) return;
			// 重置数据
			me.resetData();
			me.setContinusTimes(data['trace']);
			me.setMultipleTimes(data['multiple']);
			$.each(balls, function(i, n){
				var $t = me.getSheetsElements().filter('.dice-sheet-' + n['id']);
				// me.rebuildChip( $t, n['num'] * factor );
				me.setSheetData( $t, n['num'] * factor );
			});
			// console.log(me.getSubmitData());
		},
		// 注单成功后，更新我的方案，我的追号及余额		
		afterSubmitSuccess: function (r) {
			// 更新余额
			this.getBalance();
			// 插入投注记录
			this.gameHistory.append(r.data);
		},
		letterCapitalize: function(string){
			return string.charAt(0).toUpperCase() + string.slice(1);
		},
		/**
		 * currency(num, n, x) * 
		 * @param integer n: length of decimal
		 * @param integer x: length of sections
		 */
		currency: function(num, n, x){
			n = n || 2;
			x = x || 3;
			if( isNaN(num) || num <= 0 ){
				return '0.00';
			}else{
				var re = '\\d(?=(\\d{' + (x || 3) + '})+' + (n > 0 ? '\\.' : '$') + ')';
				return num.toFixed(Math.max(0, ~~n)).replace(new RegExp(re, 'g'), '$&,');
			}
		},
		// 将被格式的金额返回成float型
		moneyForamtToFloat: function(num){		
			return parseFloat( num.replace(/[^\d\.-]/g, '') );
		},
		// debug
		debug: function(){
			// this.debugs && window.console && console.log && console.log( '[k3dice] ' + Array.prototype.join.call(arguments, ' ') );
			// this.debugs && window.console && console.log && console.log( '[k3dice]', arguments );
			this.debugs && window.console && console.log && console.log( arguments );
		}
	}

	$.fn.k3dice = function(o) {
		var instance;
		this.each(function() {
			instance = $.data( this, 'k3dice' );
			// instance = $(this).data( 'k3dice' );
			if ( instance ) {
				// instance.init();
			} else {
				instance = $.data( this, 'k3dice', new F(this, o) );
			}
		});
		return instance;
	}
})(jQuery);