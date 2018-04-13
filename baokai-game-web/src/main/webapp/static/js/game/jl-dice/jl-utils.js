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
			animationTimes: 10, // 动画执行次数
			resultDelay: 2000,
			resultTip: '#J-result-tips',
			debugs: false // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
		}, o);
		this.slides = [];
		this.$t = $(t);
		this.debugs = this.opts.debugs;
		this.init();
	}

	F.prototype = {
		init: function(){
			var me = this;
			this.$dices = this.$t.children();
			this.posArr = [[157,162],[197,144],[170,198],[202,184],[244,189],[219,217]];
			this.setBallsNum(this.opts.balls);
			this.randomPosition(true);
			//骰子循环动画
			this.groupDiceAnimate = null;

			this.tipDom = $(me.opts.resultTip);
		},
		setBallsNum: function(balls){
			var balls = balls || this.balls || this.opts.balls;
			var position = this.randomPosition();
			this.$dices.each(function(i,n){
				var ball = balls[i];
				$(this).attr('class', 'dice-ball-' + ball ).text(ball);
				$(this).css({
					left: position[i][0],
					top: position[i][1]
				})
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

			if(init){
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
		doDice: function(){
			var me = this;
			me.tipDom.html('即将开骰，祝您好运!').show();
			// 执行骰子动画
			me.showCup(function(){
				me.diceAnimation('start');
			})
		},
		showResult: function(balls, callback){
			var me = this;
			// 定时结束
			setTimeout(function(){
				// 开奖结束后的操作
				me.diceAnimation('stop', balls, callback);
				me.tipDom.html("开奖号码："+balls);
			}, (me.opts.animationTimes + 1) * me.opts.animationDelay);
		},
		showResult2: function(balls){
			var me = this;
			me.tipDom.html("开奖号码："+balls).show();
		},
		animateProcess: function(){
			var position,
				dom = this.$t,
				currentPosition = dom.position().top,
				me = this,
				animationDelay = me.opts.animationDelay;
			if( me.groupDiceAnimate ){
				me.stopAllAnimate();
			}
			//变动位置
			//me.groupDiceAnimate = setInterval(function(){
			//	position = me.randomPosition();
			//	// 跳起
			//	dom.animate({
			//		'top': currentPosition - 50
			//	}, animationDelay * 0.28, function() {
			//		// 落下
			//		dom.animate({
			//			'top': currentPosition
			//		}, animationDelay * 0.3).removeClass('blur');
			//	}).addClass('blur');
            //
			//}, animationDelay);
			function rotateRec(dom,rand,dir) {
				var rand = rand;
				var fn = arguments.callee;
				var dir = dir;
				$(dom).addClass('blur').rotate({
					duration: 100,
					animateTo: dir,
					callback: function(){
						rand--;
						if(rand<=0) {
							$(dom).removeClass('blur').rotate({
								duration: 1,
								animateTo: 0
							});
						}else {
							fn(dom,rand,-dir);
						}
					}
				});
			}
			rotateRec(dom,10,10);
		},
		stopAllAnimate: function(){
			var me = this;
			clearInterval(me.groupDiceAnimate);
			me.groupDiceAnimate = null;
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
					me.$t.addClass('dice-cup-open');
					me.setBallsNum(balls);
					setTimeout(function(){
						me.$t.fadeOut(function(){
							me.$t.removeClass('dice-cup-open');
							if( callback && Object.prototype.toString.call(callback) === '[object Function]' ){
								callback();
							}
						})

					},me.opts.resultDelay)


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
			//getArray(4,27,0);
			// 4是生成4个随机数,27和0是指随机生成数是从0到27的数
			function getArray(count, maxs, mins){
				while(numArray.length < count){
					var temp = getRandom(maxs,mins);
					if(!search(numArray,temp)){
						numArray.push(temp);
					}
				}
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
		showCup: function(callback){
			this.$t.fadeIn(callback);
		},
		hideCup: function(callback){
			this.$t.fadeOut(callback);
		},
		// debug
		debug: function(){
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
			maxLi: 30,
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
			if(this.$t.children().length > this.opts.maxLi) {
				this.$t.children().last().remove();
			}
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
					'<div class="cell1" data-history-project="<#=orderCode#>"><#=orderCode#></div>' +
					'<div class="cell2"><#=webIssueCode#></div>' +
					'<div class="cell3"><#=formatSaleTime#></div>' +
					'<div class="cell4"><dfn>¥</dfn><#=totamount#></div>' +
					'<div class="cell5" data-history-balls>-,-,-</div>' +
					'<div class="cell6" data-history-result><#=statusName#></div>' +
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
					markup = me.template( markup, p, ( p == 'totamount' || p == 'winMoney' ? me.currency(data[p]/10000) : data[p]) );
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
			$.each(data,function(n,v){
				me.$render = $(me.render(v)).appendTo(me.$t);
				var ballhtml = '',
					balls =  !!v['numberRecordList'].length && v['numberRecordList'];
				if( balls ){
					$.each(balls, function(i, n){
						var markup = me.opts.ballMarkup
						ballhtml += me.template( markup, 'ball', n) + '\n';
					});
					me.$render.find('[data-history-balls]').html(ballhtml);
				}

				if( me.getLength() > me.opts.showNum ){
					me.$items.filter(function(idx){
						return idx >= me.opts.showNum;
					}).remove();
				}
			});

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
				winlists = data['winlists'] || [],
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
		cleanHtml: function(){
			this.$t.html('');
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



/* user head setup */
;(function($) {
	function F(t, o) {
		this.opts = $.extend({
			callback: function(){},
			contentDelay: 100,
			inputLength: 8,
			debugs: false
		}, o);
		this.$t = $(t);
		this.debugs = this.opts.debugs;
		this.init();
	}
	F.prototype = {
		init: function() {
			var opts = this.opts;
			var _this = this;
			this.win = new phoenix.util.MiniWindow({
				cls:'pop pop-head-setup'
			});
			this.mask = new phoenix.util.Mask.getInstance();
			this.win.setContent(_this._html(),_this.opts.contentDelay);
			this.win.getContainerDom().find('.hd').remove();
			this.win.hideCloseButton();
			this.win.hideCancelButton();
			//select head event
			this.selectEvent();
			this.inputEvent();

		},
		show: function(){
			this.mask.show();
			this.win.show();
		},
		hide: function(){
			var _this = this;
			this.mask.hide();
			this.win.hide();
			this.win.addEvent('afterHide', function(){
				_this._reSet();
			})
		},
		selectEvent: function(){
			var $li = this.win.getContentDom().find('.setup-select li');
			$li.on('click',function(){
				$li.removeClass('active');
				$(this).addClass('active');
			});
		},
		inputEvent: function(){

			var _this = this;
			var $input = this.win.getContentDom().find('input');

			function stringBytes(c){
				var n=c.length,s;
				var len=0;
				for(var i=0; i <n;i++){
					s=c.charCodeAt(i);
					while( s > 0 ){
						len++;
						s = s >> 12;
					}
				}
				return len;
			}

			$input.on('keyup',function(){

				var nickname = $input.val(),
					len = stringBytes(nickname);
				if(len < 4){
					if(nickname==''){
						_this.showWrong('请输入昵称');
					}
					else{
						_this.showWrong('最少输入4个字元');
					}
				}else if( len > 12 ){
					_this.showWrong('最多输入12个字元');
				}else if(!/^[\u4e00-\u9fa5a-zA-Z0-9]+$/.test(nickname))
				{
					_this.showWrong('只支持英文、数字、汉字');
				}else if(len >= 4 && len <= 12) {
					_this.win.getContentDom().find('.wrong-tips').html('');
				}
			})
		},
		checkData: function(){
			var isEmpty = this.win.getContentDom().find('input').val() == ''? true: false;
			if(isEmpty) {
				this.win.getContentDom().find('.wrong-tips').html('请输入用户昵称');
				return this.isWrong();
			}else {
				return this.isWrong();
			}
		},
		isWrong: function(){
			return this.win.getContentDom().find('.wrong-tips').html() != '' ? false : true;
		},
		showWrong: function(msg){
			this.win.getContentDom().find('.wrong-tips').html(msg);
		},
		hideWrong: function(){
			this.win.getContentDom().find('.wrong-tips').html('');
		},
		setfnConfirm: function(fn){
			this.win.doConfirm = fn;
		},
		uploadUser: function(param,callback,fail){
			$.ajax({
				dataType: 'json',
				cache: false,
				url: param['url'],
				data: {'nickname': param['nickname'],'headImg': param['headImg']},
				success:function(data){

					if(Number(data['isSuccess']) == 1){
						$.isFunction(callback) && callback();
					}else{
						$.isFunction(fail) && fail(data);
					}
				}
			});
		},
		_html: function(){
			var html = 	'<div class="setup-p">'+
				'<div class="p-left p-left-first">选择头像</div>'+
			'<div class="p-right">'+
			'<ul class="setup-select">'+
			'<li class="active"><span class="marvel1"></span></li>'+
			'<li><span class="marvel2"></span></li>'+
			'<li><span class="marvel3"></span></li>'+
			'<li><span class="marvel4"></span></li>'+
			'<li><span class="marvel5"></span></li>'+
			'<li><span class="marvel6"></span></li>'+
			'<li><span class="marvel7"></span></li>'+
			'<li><span class="marvel8"></span></li>'+
			'<li><span class="marvel9"></span></li>'+
			'<li><span class="marvel10"></span></li>'+
			'</ul>'+
			'</div>'+
			'</div>'+
			'<div class="setup-p">'+
			'<div class="p-left">设置昵称</div>'+
			'<div class="p-right">'+
			'<input type="text" placeholder="支持英文、数字、汉字不超过12个字元">'+
			'<p class="wrong-tips"></p>'+
			'</div>'+
			'</div>'+
			'<div class="setup-p">'+
			'</div>';
			return html;
		},
		getUsername: function(){
			return this.win.getContentDom().find('input').val();
		},
		getHeadNumber: function(){
			var $li = this.win.getContentDom().find('.setup-select li'),
				result = 1;
			$.each($li,function(i,v){
				if($(v).hasClass('active')){
					result =  Number($(v).index())+1;
					return false;
				}
			});
			return result;
		},
		// debug
		debug: function() {
			this.debugs && window.console && console.log && console.log('[gameHistory] ' + Array.prototype.join.call(arguments, ' '));
		}
	}

	$.fn.setupPop = function(o) {
		var instance;
		this.each(function() {
			instance = $.data(this, 'setupPop');
			if (instance) {

			} else {
				instance = $.data(this, 'setupPop', new F(this, o));
			}
		});
		return instance;
	}

})(jQuery);


/* game players */
;(function($,phoenix) {
	function F(t, o) {
		this.opts = $.extend({
			userNum: 6,
			popTipsDelay: 3000,
			popBetDelay: 2000,
			diceSheet: "#J-dice-sheet .dice-sheet",
			debugs: false
		}, o);
		this.$t = $(t);
		this.debugs = this.opts.debugs;
		this.otherPlayer = null;
		this.userOrderId = [];
		this.init();
	}
	F.prototype = {
		init: function() {
			var opts = this.opts;
			var _this = this;

		},
		userHtml: function(num) {
			return '<div class="user-chair '+ 'chair'+ num +'">'+
				'<div class="user-head">'+
				'<div class="head-pic"></div>'+
				'<span class="head-name"></span>'+
				'</div>'+
				'<div class="user-winning"></div>'+
				'</div>';
		},
		getData: function(url,showTips){
			var _this = this;
			$.ajax({
				dataType: 'json',
				cache: false,
				url: url,
				data: '',
				success:function(data){
					if(Number(data['isSuccess']) == 1){
						var aUserinfo = [],
							aBet = [];
						if(data['data']){
							_this.clearDom();
							_this.bulidDom(data['data'].length);
							_this.otherPlayer = _this.$t.find('.user-chair');
							_this.userOrderId = [];
							$.each(data['data'],function(i,n){
								var obj = {'name': n.userNickName,'head': n.headImg};
								_this.userOrderId.push(n.orderId);
								aUserinfo.push(obj);
								aBet.push(n.slipsStruc);
							});

							_this.setUserInfo(aUserinfo);
							if(!showTips){
								_this.showPlayerBet(aBet);
							}
						}
					}
				}
			});
		},
		setUserInfo: function(data){
			var _this = this;
			if(!this.otherPlayer) return;
			$.each(data,function(i,n){
				var name = n.name;
				_this.otherPlayer.eq(i).find('.head-name').html(name);
				_this.otherPlayer.eq(i).find('.head-pic').addClass('marvel'+ n.head);
			});
		},
		showPlayerBet: function(data){
			var _this = this;
			if(!data) return;

			$.each(data,function(i,n){
				setTimeout(function(){
					$.each(n,function(j,m){
						var betAmount = m['totamount']/10000;
						var id = m["slipViewId"];
						_this.popBet(id,betAmount);
					});
				}, i*2000);

			})
		},
		showWinningList : function(data,callback,dom){
			var _this = this,
				callbackFn = $.isFunction(callback) && callback,
				dom = dom || $('#dice-user .user-chair');
			if(!data) return;
			var arrWinning = [];
			$.each(data,function(i,n){
				arrWinning.push(n);
			});
			$.each(arrWinning,function(i,n){
				if(n != '0') {
					_this.popTips(dom.eq(i),n,callbackFn);
				}
			});
			if(Number(data['$self']) > 0 ){
				$('#dice-user').gamePlayer().popTips($('#dice-user .main-chair'), data['$self']+'', function () {
				}, 96);
			}

		},
		popTips: function(dom,tips,callback,dis){
			var _this = this;
			var timer = null;
			var dis = dis || 66 ;
			tips = '+' + phoenix.util.formatMoney(tips/10000)+'元';
			if(dom){
				var tips = tips || '';
				var $tip = $(dom).find('.user-winning');
				$tip.html(tips).animate({
					opacity: 100,
					top: '-'+ dis +'px'
				},"fast","swing");
				if(timer) return;
				timer = setTimeout(function(){
					$tip.animate({
						opacity: 0,
						top: '-10px'
					},"fast","swing",function(){
						$(this).html('');

						$.isFunction(callback) && callback();
					});
				},_this.opts.popTipsDelay);
			}
		},
		popBet: function(id,str){
			var _this = this,
				timer = null,
				$sheets = $(this.opts.diceSheet),
				$dom = $sheets.eq(id);
			str = '+'+ phoenix.util.formatMoney(str);
			if(typeof id == 'undefined') return;
			if($dom.find('.dice-winning').length == 0){
				var html = $('<div class="dice-winning"></div>');
				$dom.append(html);
			}
			var $show = $dom.find('.dice-winning');
			$show.html(str).show().stop().animate({
				'top': "-60px",
				'opacity': '100'
			},"fast","swing");
			setTimeout(function(){
				$show.html(str).stop().animate({
					'top': "-34px",
					'opacity': '0'
				},"fast","swing",function(){
					$(this).hide();
				});
			},_this.opts.popBetDelay);
		},
		bulidDom: function(num){
			var html = '';
			for(var i=0; i<num; i++){
				html+=this.userHtml(i+1);
			}
			return this.$t.append(html);
		},
		clearDom: function(){
			this.$t.find('.user-chair').remove();
		},
		getOrderId: function(meorder){
			if(meorder) {
				this.userOrderId.push(meorder);
			}
			return this.userOrderId;
		},
		// debug
		debug: function() {
			this.debugs && window.console && console.log && console.log('[gamePlayer] ' + Array.prototype.join.call(arguments, ' '));
		}
	}

	$.fn.gamePlayer = function(o) {
		var instance;
		this.each(function() {
			instance = $.data(this, 'gamePlayer');
			if (instance) {

			} else {
				instance = $.data(this, 'gamePlayer', new F(this, o));
			}
		});
		return instance;
	}

})(jQuery,phoenix);


//重定义计时器
(function(host, name, Event, undefined){

	/*
	 * 默认配置
	 * 时间格式 : 2013/8/10,00:00:00
	 * 结束动作 : function
	 */
	var defConfig = {
			//开始时间
			'startTime' : '2013-1-1,00:00:00',
			//结束时间
			'endTime' : '2014-1-1,00:00:00',
			//定时器频率
			'frequency':1000,
			//结束提示
			'finishFun' : null,
			//当前启动计时矫正
			'isRedress': false,
			//矫正周期时间单位[秒]
			'redressTime': 10,
			//配置时间接口
			'redressUrl': './simulatedata/getnowtime.php',
			//是否
			'isLoop' : false,
			//输入时间的dom
			'showDom' : '#time'
		},
	//内部计时标记
		countNum = 0,
	//要加入定时处理的事件
		fixedEvents = [];

	var pros = {
			//初始化
			init : function() {
				var me = this;
				me.startTime = new Date(me.defConfig.startTime);
				me.endTime = new Date(me.defConfig.endTime);
				me.frequency = me.defConfig.frequency;

				//缓存时间读取请求
				me.timeload = null;

				//启动定时器
				me.continueCount();
			},
			getStartTime:function(){
				return this.startTime;
			},
			setStartTime:function(time){
				this.startTime = time;
			},
			getEndTime:function(){
				return this.endTime;
			},
			setEndTime:function(time){
				this.endTime = time;
			},
			getFrequency:function(){
				return this.frequency;
			},
			setFrequency:function(frequency){
				this.frequency = frequency;
			},
			getRedressUrl:function(){
				return this['defConfig']['redressUrl'];
			},
			setRedressUrl:function(url){
				this['defConfig']['redressUrl'] = url;
			},
			getRedressTime:function(){
				return this['defConfig']['redressTime'];
			},
			setRedressTime:function(time){
				this['defConfig']['redressTime'] = time;
			},
			//加入预处理文件
			//时间单位： s秒
			joinEvents : function(time, fn){
				fixedEvents.push([time, fn]);
			},
			//倒计时控制
			_countFun : function(now) {
				var that = this, time = {}, count = 0, thisTime = null, leftsecond,
					nowtime = that.getStartTime(),
					endTime = that.getEndTime(),
					ruleTime = this.defConfig.ruleTime;


				//时间计算定时器
				this.timeFun = setInterval(function() {

					//计算剩余时间
					leftsecond = thisTime || parseInt((endTime - nowtime)/1000);
					//缓存当前时间
					(thisTime == null) && (thisTime = leftsecond);
					//计时器累加
					countNum++;

					//格式化时间
					time.allSecond = thisTime;
					time.w = parseInt(leftsecond/3600/24/7);
					time.d = parseInt(leftsecond/3600/24);
					time.h = parseInt((leftsecond/3600)%24);
					time.m = parseInt((leftsecond/60)%60);
					time.s = thisTime == 0 ? thisTime : parseInt(leftsecond%60);

					//执行定时队列事件
					that.doFixedEvents(thisTime);
					//输出时间
					that._showTime(time);

					//是否需要矫正计时
					that['defConfig']['isRedress'] && (countNum%that.getRedressTime() == 0) && thisTime > that.getRedressTime() && that.redRessTime();

					//时间结束
					if(thisTime == 0) {
						//如果已经存在请求
						//还没有返回则中断
						that.timeload && that.timeload.abort();

						that._endCount();
						that.fireEvent('afterTimeFinish');
						return;
					};

					//缓存时间
					thisTime = thisTime != null ? thisTime - 1 : leftsecond - 1;

				}, that.getFrequency());
			},
			//时间矫正函数
			redRessTime: function(){
				var me = this,
					timeMath = new Date().getTime();

				//如果已经存在请求
				//还没有返回则中断
				me.timeload  && me.timeload.abort();

				me.timeload = $.ajax({
					url: me.getRedressUrl(),
					type: 'GET',
					dataType: 'json'
				})
					.done(function(data) {

						if(Number(data['isSuccess']) == 1){
							//停止计时
							me.stopCount();
							//更新时间
							me.setStartTime(new Date(data['nowTime']).getTime() + (new Date().getTime() - timeMath));
							//恢复计时
							me.continueCount();
						}

					})
					.fail(function() {

					})
					.always(function() {

						me.timeload = null;
					});

			},
			//检查队列中需要执行的
			doFixedEvents : function(time){
				var me = this,i = 0;
				if(fixedEvents.length == 0){
					return;
				}
				//执行定时处理
				for(;i<fixedEvents.length;i++){
					if(time == fixedEvents[i][0]){
						fixedEvents[i][1].call(me);
					}
				}
			},
			//时间显示
			_showTime : function(time) {
				var timeArea = $(this.defConfig.showDom),
					w = timeArea.find('.week'),
					d = timeArea.find('.day'),
					h = timeArea.find('.hour'),
					m = timeArea.find('.min'),
					s = timeArea.find('.sec');

				//渲染DOM
				w.text(time.w), d.text(time.d), h.text(time.h), m.text(time.m), s.text(time.s);
			},
			//缺位补零
			checkNum : function(num) {
				if(num < 10){
					return '0' + num;
				}
				return num;
			},
			//计时结束
			_endCount : function() {
				//停止
				this.stopCount();
				//清空内部计时
				countNum = 0;
				//执行回调
				this.defConfig.finishFun && this.defConfig.finishFun.call(this);
			},
			//停止计时
			stopCount : function() {
				clearInterval(this.timeFun);
				this.timeFun = null;
			},
			//恢复计时
			continueCount : function() {
				if(this.defConfig.serverTimeURl){
					this._serverTime();
				}else {
					this._countFun();
				}
			}
		},

		Main = host.Class(pros, Event);
	Main.defConfig = defConfig;

	host[name] = Main;

})(phoenix, "CountDown2", phoenix.Event);


//背景音效
(function(host, name, Event, undefined){


	var defConfig = {
			//开始时间
			'bmSrcDraw' : staticFileContextPath+'/static/js/game/jl-dice/music/draw.mp3',
			'bmSrcBet'	: staticFileContextPath+'/static/js/game/jl-dice/music/bet.mp3',
			'bmSrcStop'	: staticFileContextPath+'/static/js/game/jl-dice/music/stop.mp3'
		},
		isClose = false,
		isLowIe = false;


	var pros = {
			//初始化
			init : function() {
				var me = this;
				if (!$.support.leadingWhitespace) {
					this.isLowIe = true;
				}

				this.bmBet = new Audio5js({
					swf_path:'../static/js/game/jl-dice/swf/audio5js.swf',
					throw_errors: true,
					ready: function () {
						var player = this;
						player.load(defConfig.bmSrcBet);
						if(player.playing){
							player.pause();
						}
					}
				});
				this.bmStop = new Audio5js({
					swf_path:'../static/js/game/jl-dice/swf/audio5js.swf',
					throw_errors: true,
					ready: function () {
						var player = this;
						player.load(defConfig.bmSrcStop);
						if(player.playing){
							player.pause();
						}
					}
				});
				this.bmDraw = new Audio5js({
					swf_path:'../static/js/game/jl-dice/swf/audio5js.swf',
					throw_errors: true,
					ready: function () {
						var player = this;
						player.load(defConfig.bmSrcDraw);
						if(player.playing){
							player.pause();
						}
					}
				});
			},
			playBet : function() {
				if(this.bmBet && !isClose && !this.isLowIe){
					if(this.bmBet.playing) return;
					this.bmBet.play();
				}
			},
			playStop : function() {
				if(this.bmStop && !isClose && !this.isLowIe){
					if(this.bmStop.playing) return;
					this.bmStop.play();
				}
			},
			playDraw : function(){
				if(this.bmDraw && !isClose && !this.isLowIe){
					if(this.bmDraw.playing) return;
					this.bmDraw.play();
				}
			},
			closeMusic: function(){
				isClose = true;
			},
			openMusic: function(){
				isClose = false;
			}

		},

		Main = host.Class(pros, Event);
	Main.defConfig = defConfig;

	host[name] = Main;

})(phoenix, "backgroundMusic", phoenix.Event);





var c=""
function stringBytes(c){
	var n=c.length,s;
	var len=0;
	for(var i=0; i <n;i++){
		s=c.charCodeAt(i);
		while( s > 0 ){
			len++;
			s = s >> 12;
		}
	}
	return len;
}

