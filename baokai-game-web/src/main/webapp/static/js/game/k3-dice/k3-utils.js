/* counter */
;(function($) {
	function F(t, o) {
		this.opts = $.extend({
			max: 1,
			min: 1,
			step: 1,
			init: 1,
			debugs: true // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
		}, o);
		// console.log(this.opts)
		this.slides = [];
		this.$t = $(t);
		this.beforeSetValue = function(){};
		this.onSetValue = function(){};
		this.debugs = this.opts.debugs;
		this.init();
	}

	F.prototype = {
		init: function() {
			var me = this;
			me.$ctrl = me.$t.find('[data-counter-action]');
			me.$title = me.$t.find('[data-counter-title]');
			me.$input = me.$t.find('input.J_counter');
			//me.$valueInput = me.$t.siblings('[data-valueInput]');
			if (!me.$input.length) return me.debug('No input element!');
			me.setMaxValue(me.opts.max);
			me.setMinValue(me.opts.min);
			me.checkCtrl();
			me.bindEvent();

			var init = me.opts.init;
			if( init > me.opts.max ){
				init = me.opts.max
			}else if( init < me.opts.min ){
				init = me.opts.min
			}
			me.setInitValue(init);
			me.setValue(init);
		},
		lowerCase: function(s) {
			return (s || '').toLowerCase();
		},
		setTitle: function(title){
			this.$title.text(title);
		},
		addClass: function(classname){
			this.$t.addClass(classname);
		},
		setOnSetValue: function(fn){
			var me = this;
			me.onSetValue = fn;
		},
		setBeforeSetValue: function(fn){
			var me = this;
			me.beforeSetValue = fn;
		},
		setMinValue: function( num ){
			this.minValue = num;
			this.checkCtrl();
		},
		getMinValue: function(){
			return this.minValue;
		},
		setMaxValue: function( num ){
			this.maxValue = num;
			this.checkCtrl();
		},
		getMaxValue: function(){
			return this.maxValue;
		},
		setInitValue: function( num ){
			this.initValue = num;
		},
		getInitValue: function(){
			return this.initValue;
		},
		setValue: function(val) {
			var me = this,
				dom = me.$input,
				data = {val: me.checkCtrl(val)};
				
			me.beforeSetValue(data);
			dom.val(data['val']);
			me.onSetValue(data['val']);
		},
		getValue: function() {
			return this.$input.val();
		},
		setButtonStatus: function(button, status){
			var $ctrl = this.$ctrl.filter('[data-counter-action="' + button + '"]');
			if( status == 'disabled' ){
				$ctrl.addClass('disabled');
			}else{
				$ctrl.removeClass('disabled');
			}
		},
		checkCtrl: function(val) {
			var me = this,
				opts = me.opts;
			if( val == 'undefined' ){
				val = me.getInitValue();
			}
			// me.debug(val);
			if (val <= me.getMinValue()) {
				me.setButtonStatus('decrease', 'disabled');
				val = me.getMinValue();
			} else {				
				me.setButtonStatus('decrease');
			}
			if (val >= me.getMaxValue()) {
				me.setButtonStatus('increase', 'disabled');
				val = me.getMaxValue();
			} else {
				me.setButtonStatus('increase');
			}
			return val;
		},
		reset: function(){
			var me = this;
			me.setValue(me.getInitValue());
			me.$input.val(me.getInitValue());
			me.checkCtrl();
		},
		bindEvent: function() {
			var me = this,
				opts = me.opts;
			me.$ctrl.on('click', function(e) {
				if ($(this).hasClass('disabled')) return false;
				var val = parseInt(me.$input.val()),
					action = $(this).data('counter-action');
				if (action == 'increase') val += opts.step;
				else if (action == 'decrease') val -= opts.step;
				// me.debug(val)
				me.setValue(val);
			});
			me.$input.on('change', function() {
				var val = parseInt(this.value);
				me.setValue(val);
			});
		},
		// debug
		debug: function() {
			this.debugs && window.console && console.log && console.log('[counter] ' + Array.prototype.join.call(arguments, ' '));
		}
	}

	$.fn.counter = function(o) {
		var instance;
		this.each(function() {
			instance = $.data(this, 'counter');
			// instance = $(this).data( 'counter' );
			if (instance) {
				// instance.init();         
			} else {
				instance = $.data(this, 'counter', new F(this, o));
			}
		});
		return instance;
	}
})(jQuery);

/* tips */
/*
$('body').tips({
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
*/
;(function($){
	function F(t, o){
		this.opts = $.extend({
			attr: 'tips-title', // data('tips-title')
			direction: 'top', //[top,right,bottom,left]
			target: 'body',
			event: 'mouseover', // [mouseover, click]
			offsetFix: 10, // 位置修正量
			zIndex: 500,
			animationDelay: 300,
			autoinitialize: false,
			maxLetter: 20,
			beforeSetText: function(text){
				return text;
			},
			showCase: function(){
				return true;
			},
			debugs: true // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
		}, o);
		this.slides = [];
		this.$t = $(t);
		this.text = '';
		this.debugs = this.opts.debugs;
		// this.isIE6 = $.browser.msie && parseFloat($.browser.version) < 7;
		this.init();
	}

	F.prototype = {
		init: function(){
			var opts = this.opts;

			this.$tips = $('<div class="ui-tips ui-tips-' +opts.direction+ '"><span class="ui-tips-text"></span><i class="ui-tips-arrow"></i></div>');
			this.$tips.css({
				position: 'absolute',
				zIndex: opts.zIndex
			}).appendTo(opts.target).animate({opacity: 'hide'}, 0);

			this.beforeSetText = opts.beforeSetText;
			this.timer = null;
			
			this.$text = this.$tips.find('.ui-tips-text');
			this.fontSize = parseFloat(this.$text.css('fontSize'));
			this.setText(this.$t.data(opts.attr));

			this.bindEvent();
		},
		setText: function(text){
			text = this.beforeSetText(text);
			if( !text ){
				text = '';
			}
			if( this.text != text ){
				this.text = text;
				this.$text.text(text);
			}
		},
		getText: function(){
			return this.text;
		},
		setStyle: function(){
			var me = this,
				offset = me.$t.offset(),
				fix = me.opts.offsetFix,
				maxLetter = me.opts.maxLetter,
				left = 0,
				top = 0,
				width = me.getText().length > maxLetter ? maxLetter * me.fontSize : 'auto';
			switch( me.opts.direction ){
				case 'right':
					left = offset.left + me.$t.outerWidth() + fix;
					top = offset.top + ( me.$t.outerHeight() - me.$tips.outerHeight() ) / 2;
					break;
				case 'bottom':
					left = offset.left + ( me.$t.outerWidth() - me.$tips.outerWidth() ) / 2;
					top = offset.top + me.$t.outerHeight() + fix;
					break;
				case 'left':
					left = offset.left - me.$tips.outerWidth() - fix;
					top = offset.top + ( me.$t.outerHeight() - me.$tips.outerHeight() ) / 2;
					break;
				case 'top':
				default:
					left = offset.left + ( me.$t.outerWidth() - me.$tips.outerWidth() ) / 2;
					top = offset.top - me.$tips.outerHeight() - fix;
					break;
			}
			me.$tips.css({
				left: left,
				top: top,
				width: width
			});
		},
		show: function(){
			var me = this;			
			if( me.opts.autoinitialize ){
				me.setText(me.$t.data(me.opts.attr));
			}
			if( !me.opts.showCase(me) ) return false;
			me.setStyle();
			me.timer = setTimeout(function(){
				$(me.$tips).animate({
					opacity: 'show'
				});
			}, me.opts.animationDelay);				
		},
		hide: function(){
			var me = this;
			if( me.timer ){
				clearTimeout(me.timer);
				me.timer = null;
			}
			me.$tips.animate({
				opacity: 'hide'
			});
		},
		bindEvent: function(){
			var me = this,
				event = me.opts.event;
			if( event === 'mouseover' ){
				me.$t.on({
					mouseover: function(){
						me.show();
					},
					mouseout: function(){
						me.hide();
					}
				});
			}else{
				me.$t.toggle(function(){					
					me.show();
				}, function(){
					me.hide();
				});
			}
			return false;
		},
		// debug
		debug: function(){
			// this.debugs && window.console && console.log && console.log( '[tips] ' + Array.prototype.join.call(arguments, ' ') );
			this.debugs && window.console && console.log && console.log( '[tips]', arguments );
		}
	}

	$.fn.tips = function(o) {
		var instance;
		this.each(function() {
			instance = $.data( this, 'tips' );
			// instance = $(this).data( 'tips' );
			if ( instance ) {
				// instance.init();
			} else {
				instance = $.data( this, 'tips', new F(this, o) );
			}
		});
		return instance;
	}
})(jQuery);

/* diceAnimation */
;(function($){
	function F(t, o){
		this.opts = $.extend({
			balls: [2,4,5],
			animationDelay: 300,
			animationTimes: 5, // 动画执行次数
			debugs: false // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
		}, o);
		this.slides = [];
		this.$t = $(t);
		this.debugs = this.opts.debugs;
		// this.isIE6 = $.browser.msie && parseFloat($.browser.version) < 7;
		this.init();
	}

	F.prototype = {
		init: function(){
			this.$dices = this.$t.children();
			this.posArr = [[33,92],[55,53],[84,86],[13,53]];
			this.setBallsNum(this.opts.balls);
			this.randomPosition(true);
			//单骰子动画
			this.singleDiceAnimate = null;
			//骰子循环动画
			this.groupDiceAnimate = null;
		},
		setBallsNum: function(balls){
			balls = balls || this.balls || this.opts.balls;
			this.$dices.each(function(i,n){
				var ball = balls[i];
				$(this).attr('class', 'dice-ball-' + ball ).text(ball);
			});
			this.balls = balls;
		},
		getCurrentBalls: function(){
			return this.balls;
		},
		randomPosition: function(init){
			var me = this,
				posArr = me.posArr,
				position = [];

			if( init ){
				// 取前三个作为position
				me.$dices.each(function(i){
					var pos = posArr[i];
					$(this).css({
						left: pos[0],
						top : pos[1]
					});
					position.push([pos[0], pos[1]]);
				});
			}else{
				var rands = me.randomBelle(3, posArr.length-1, 0);
				for(i=0; i<rands.length; i++){
					var pos = posArr[rands[i]];
					position.push([pos[0], pos[1]]);
				}
				return position;
			}
		},
		doDice: function(balls, callback){
			var me = this;
			// 执行骰子动画
			me.diceAnimation('start');
			// 定时结束
			setTimeout(function(){
				// 开奖结束后的操作
				me.diceAnimation('stop', balls, callback);
			}, (me.opts.animationTimes + 1) * me.opts.animationDelay);
		},
		singleAnimateProcess: function(){
			var me = this,
				maxNum = 6;
			if(me.singleDiceAnimate){
				return;
			}
			//单球转动
			me.singleDiceAnimate = setInterval(function(){
				var balls = me.getRandomBalls();
				me.$dices.each(function(i, n){
					var ball = balls[i];
					$(this).attr('class', 'dice-ball-' + ball).text(ball);	
				})
			}, 100);   
		},
		animateProcess: function(){
			var position, dom, left, currentPosition, rands,
				me = this,
				animationDelay = me.opts.animationDelay;
			if( me.groupDiceAnimate ){
				return;
			}
			//变动位置
			me.groupDiceAnimate = setInterval(function(){
				position = me.randomPosition(),
				// 开启骰子转动
				me.singleAnimateProcess();
				me.$dices.each(function(i){
					(function(s){
						dom = me.$dices.eq(s);
						currentPosition = position[s];
						left = Number(currentPosition[0] - dom.position().left);
						// 跳起
						dom.animate({
								'top': currentPosition[1] - 50,
								'left': dom.position().left + left * 0.8
							}, animationDelay * 0.28, function() {
								dom = me.$dices.eq(s);
								currentPosition = position[s];
								left = Number(currentPosition[0]-dom.position().left);
								// 落下
								dom.animate({
									'top': currentPosition[1],
									'left': dom.position().left + left
								}, animationDelay * 0.3);
						});
					})(i);
				});
			}, animationDelay);   
		},
		stopAllAnimate: function(){
			var me = this;

			clearInterval(me.singleDiceAnimate);
			clearInterval(me.groupDiceAnimate);
			me.singleDiceAnimate = null;
			me.groupDiceAnimate = null;
			// me.randomPosition(true);
		},
		diceAnimation: function(type, balls, callback){
			var me = this;
			if( type == 'start' ){
				//开始循环动画
				me.animateProcess();
			}else if( type == 'stop' ){
				if( balls 
					&& Object.prototype.toString.call(balls) === '[object Array]' 
					&& balls.length == me.$dices.length
				){
					me.stopAllAnimate();
					me.setBallsNum(balls);
					if( callback && Object.prototype.toString.call(callback) === '[object Function]' ){
						callback();
					}
				}else{
					alert('开奖号码错误');
				}
			}
		},
		getRandomBalls: function(){
			var me = this;
			return me.randomBelle(me.$dices.length, 6, 1);
		},
		randomBelle :function(count, maxs, mins){
			var numArray = new Array();
			//getArray(4,27,0); //4是生成4个随机数,27和0是指随机生成数是从0到27的数
			function getArray(count, maxs, mins){
				while(numArray.length < count){
					var temp = getRandom(maxs,mins);
					if(!search(numArray,temp)){
						numArray.push(temp);
					}
				}
				//alert("生成的数组为:"+numArray);
				return numArray;
			}
			function getRandom(maxs, mins){  //随机生成maxs到mins之间的数
				return Math.round(Math.random()*(maxs-mins))+mins;
			}
			function search(numArray, num){   //array是否重复的数
				for(var i=0; i<numArray.length; i++){
					if(numArray[i] == num){
						return true;
					}
				}
				return false;
			}
			return getArray(count, maxs, mins);
		},
		// debug
		debug: function(){
			// this.debugs && window.console && console.log && console.log( '[diceAnimation] ' + Array.prototype.join.call(arguments, ' ') );
			this.debugs && window.console && console.log && console.log( '[diceAnimation]', arguments );
		}
	}

	$.fn.diceAnimation = function(o) {
		var instance;
		this.each(function() {
			instance = $.data( this, 'diceAnimation' );
			// instance = $(this).data( 'diceAnimation' );
			if ( instance ) {
				// instance.init();
			} else {
				instance = $.data( this, 'diceAnimation', new F(this, o) );
			}
		});
		return instance;
	}
})(jQuery);

/* k3Record */
;(function($){
	function F(t, o){
		this.opts = $.extend({
			markup: '',
			callback: function(){},
			debugs: false // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
		}, o);
		this.slides = [];
		this.$t = $(t);
		this.markup = 
			'<li>' +
				'<div class="rec1">' +
					'<span class="dice-number dice-number-<#=ball1#>"><#=ball1#></span>' +
					'<span class="dice-number dice-number-<#=ball2#>"><#=ball2#></span>' +
					'<span class="dice-number dice-number-<#=ball3#>"><#=ball3#></span>' +
				'</div>' +
				'<div class="rec2"><#=num#></div>' +
				'<div class="rec3"><#=size#></div>' +
				'<div class="rec4"><#=oddEven#></div>'  +
				'<div class="rec5"><#=number#></div>' +
			'</li>';
		this.debugs = this.opts.debugs;
		// this.isIE6 = $.browser.msie && parseFloat($.browser.version) < 7;
		this.init();
	}

	F.prototype = {
		init: function(){
			var markup = this.opts.markup;
			if( markup ){
				this.setMarkup(markup);
			}
		},
		setMarkup: function(markup){
			this.markup = markup;
		},
		getMarkup: function(){
			return this.markup;
		},
		getHTML: function(data){
			var html = this.getMarkup();
			for(p in data){
				if(data.hasOwnProperty(p)){
					reg = RegExp('<#=' + p + '#>', 'g');
					html = html.replace(reg, data[p]);
				}
			}
			return html;
		},
		append: function(data){
			var html = this.getHTML(data);
			this.$t.append(html);
			this.afterInsert();
		},
		prepend: function(data){
			var html = this.getHTML(data);
			this.$t.prepend(html);
			this.afterInsert();
		},
		afterInsert: function(){
			var callback = this.opts.callback;
			if( callback && Object.prototype.toString.call(callback) === '[object Function]' ){
				callback();
			}
		},
		// debug
		debug: function(){
			// this.debugs && window.console && console.log && console.log( '[k3Records] ' + Array.prototype.join.call(arguments, ' ') );
			this.debugs && window.console && console.log && console.log( '[k3Records]', arguments );
		}
	}

	$.fn.k3Records = function(o) {
		var instance;
		this.each(function() {
			instance = $.data( this, 'k3Records' );
			// instance = $(this).data( 'k3Records' );
			if ( instance ) {
				// instance.init();
			} else {
				instance = $.data( this, 'k3Records', new F(this, o) );
			}
		});
		return instance;
	}
})(jQuery);

/* gamehistory */
;(function($) {
	function F(t, o) {
		this.opts = $.extend({
			showNum: 5, // 记录显示条数
			ballurl: 'javascript:void(0);', // 号码详情链接
			markup: 
				'<li>' +
					'<div class="cell1" data-history-project="<#=projectId#>"><#=projectId#></div>' +
					'<div class="cell2"><#=number#></div>' +
					'<div class="cell3"><#=writeTime#></div>' +
					'<div class="cell4"><dfn>¥</dfn><#=totalprice#></div>' +
					'<div class="cell5" data-history-balls>-,-,-</div>' +
					'<div class="cell6" data-history-result>等待开奖</div>' +
					'<div class="cell7">' +
						'<a href="<#=ballurl#>?orderId=<#=orderId#>" target="_blank">投注详情</a>' +
					'</div>' +
				'</li>',
			ballMarkup: '<span class="dice-number dice-number-<#=ball#>"><#=ball#></span>',
			debugs: false // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
		}, o);
		this.slides = [];
		this.$t = $(t);
		this.debugs = this.opts.debugs;
		this.init();
	}

	F.prototype = {
		init: function() {
			var opts = this.opts;
		},
		getLength: function(){
			this.$items = this.$t.children();
			return this.$items.length;
		},
		render: function(data){
			var me = this,
				opts = me.opts,
				markup = opts.markup;
			data['ballurl'] = baseUrl+"/"+opts.ballurl;
			for (p in data) {
				if (data.hasOwnProperty(p)) {
					markup = me.template( markup, p, ( p == 'totalprice' || p == 'winMoney' ? me.currency(data[p]/10000) : data[p]) );
				}
			}
			return markup;
		},
		template: function(markup, key, value, reg){
			reg = reg || RegExp('<#=' + key + '#>', 'g');
			return markup.replace(reg, value);
		},
		append: function(data) {
			var me = this;
			// console.log(data);
			// 是否判断data的合法性？			
			me.$render = $(me.render(data)).prependTo(me.$t);
			if( me.getLength() > me.opts.showNum ){
				me.$items.filter(function(idx){
					return idx >= me.opts.showNum;
				}).remove();
			}
		},
		/*
		data = {
			balls: [1, 3, 6],
			winlists: [
				{
					projectId: "DCDSGSGDFHGDFHFDF",
					winMoney: 95194573
				},{
					projectId: "DCDSGSGDFHGDFHFDK",
					winMoney: 15663161
				}
			]
		}
		*/
		update: function(data){
			var me = this,
				winlists = data['winlists'],
				balls = data['balls'] || [],
				ballhtml = '';

			if( balls.length ){
				$.each(balls, function(i, n){
					var markup = me.opts.ballMarkup
					ballhtml += me.template( markup, 'ball', n) + '\n';
				});			
			}
			$.each(winlists, function(i, n){
				var projectId = n['projectId'],
					winMoney = n['winMoney'] || 0;
				me.$t.find('[data-history-project="' + projectId + '"]').each(function(){
					var _win = '未中奖';
					if( winMoney > 0 ){
						_win = '<span class="price"><dfn>¥</dfn>' +  me.currency(winMoney/10000) + '</span>';
					}
					$(this).siblings('[data-history-result]').html(_win);
					$(this).siblings('[data-history-balls]').html(ballhtml);
				});
			});
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
		// debug
		debug: function() {
			this.debugs && window.console && console.log && console.log('[gameHistory] ' + Array.prototype.join.call(arguments, ' '));
		}
	}

	$.fn.gameHistory = function(o) {
		var instance;
		this.each(function() {
			instance = $.data(this, 'gameHistory');
			// instance = $(this).data( 'gameHistory' );
			if (instance) {
				// instance.init();         
			} else {
				instance = $.data(this, 'gameHistory', new F(this, o));
			}
		});
		return instance;
	}
	// $('[data-simulation="gameHistory"]').gameHistory();
})(jQuery);