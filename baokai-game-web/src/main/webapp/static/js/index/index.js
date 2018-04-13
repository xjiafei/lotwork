/**
 * countdown
 * @date    2013-07-18 10:32:57
 * #用法实例：
 * # new phoenix.countdown({
 * 		startTime : 2013/8/10,00:00:00,
 * 		endTime : 2013/8/10,00:00:00,
 * 		finishFun : function() {
 * 			this.endTime;
 * 		},
 * 		serverTimeurl : 'http://www.***.com/find',
 * 		ruleTime : 60,
 * 		shwoDom : '#timeshow'
 * })
 */
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
			//alert(me.defConfig.endTime);
			//alert(me.endTime);
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
				time.allSecond = leftsecond;
				time.w = parseInt(leftsecond/3600/24/7);
				time.d = parseInt(leftsecond/3600/24);
				time.h = parseInt((leftsecond/3600)%24);
				time.m = parseInt((leftsecond/60)%60);
				time.s = thisTime == 0 ? thisTime : parseInt(leftsecond%60);

				//执行定时队列事件
				that.doFixedEvents(countNum);
				
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
					fixedEvents.splice(i, 1);
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
	
})(phoenix, "CountDown", phoenix.Event);

/*!
* jQuery Cycle2; version: 2.1.5 build: 20140415
* http://jquery.malsup.com/cycle2/
* Copyright (c) 2014 M. Alsup; Dual licensed: MIT/GPL
*/
!function(a){"use strict";function b(a){return(a||"").toLowerCase()}var c="2.1.5";a.fn.cycle=function(c){var d;return 0!==this.length||a.isReady?this.each(function(){var d,e,f,g,h=a(this),i=a.fn.cycle.log;if(!h.data("cycle.opts")){(h.data("cycle-log")===!1||c&&c.log===!1||e&&e.log===!1)&&(i=a.noop),i("--c2 init--"),d=h.data();for(var j in d)d.hasOwnProperty(j)&&/^cycle[A-Z]+/.test(j)&&(g=d[j],f=j.match(/^cycle(.*)/)[1].replace(/^[A-Z]/,b),i(f+":",g,"("+typeof g+")"),d[f]=g);e=a.extend({},a.fn.cycle.defaults,d,c||{}),e.timeoutId=0,e.paused=e.paused||!1,e.container=h,e._maxZ=e.maxZ,e.API=a.extend({_container:h},a.fn.cycle.API),e.API.log=i,e.API.trigger=function(a,b){return e.container.trigger(a,b),e.API},h.data("cycle.opts",e),h.data("cycle.API",e.API),e.API.trigger("cycle-bootstrap",[e,e.API]),e.API.addInitialSlides(),e.API.preInitSlideshow(),e.slides.length&&e.API.initSlideshow()}}):(d={s:this.selector,c:this.context},a.fn.cycle.log("requeuing slideshow (dom not ready)"),a(function(){a(d.s,d.c).cycle(c)}),this)},a.fn.cycle.API={opts:function(){return this._container.data("cycle.opts")},addInitialSlides:function(){var b=this.opts(),c=b.slides;b.slideCount=0,b.slides=a(),c=c.jquery?c:b.container.find(c),b.random&&c.sort(function(){return Math.random()-.5}),b.API.add(c)},preInitSlideshow:function(){var b=this.opts();b.API.trigger("cycle-pre-initialize",[b]);var c=a.fn.cycle.transitions[b.fx];c&&a.isFunction(c.preInit)&&c.preInit(b),b._preInitialized=!0},postInitSlideshow:function(){var b=this.opts();b.API.trigger("cycle-post-initialize",[b]);var c=a.fn.cycle.transitions[b.fx];c&&a.isFunction(c.postInit)&&c.postInit(b)},initSlideshow:function(){var b,c=this.opts(),d=c.container;c.API.calcFirstSlide(),"static"==c.container.css("position")&&c.container.css("position","relative"),a(c.slides[c.currSlide]).css({opacity:1,display:"block",visibility:"visible"}),c.API.stackSlides(c.slides[c.currSlide],c.slides[c.nextSlide],!c.reverse),c.pauseOnHover&&(c.pauseOnHover!==!0&&(d=a(c.pauseOnHover)),d.hover(function(){c.API.pause(!0)},function(){c.API.resume(!0)})),c.timeout&&(b=c.API.getSlideOpts(c.currSlide),c.API.queueTransition(b,b.timeout+c.delay)),c._initialized=!0,c.API.updateView(!0),c.API.trigger("cycle-initialized",[c]),c.API.postInitSlideshow()},pause:function(b){var c=this.opts(),d=c.API.getSlideOpts(),e=c.hoverPaused||c.paused;b?c.hoverPaused=!0:c.paused=!0,e||(c.container.addClass("cycle-paused"),c.API.trigger("cycle-paused",[c]).log("cycle-paused"),d.timeout&&(clearTimeout(c.timeoutId),c.timeoutId=0,c._remainingTimeout-=a.now()-c._lastQueue,(c._remainingTimeout<0||isNaN(c._remainingTimeout))&&(c._remainingTimeout=void 0)))},resume:function(a){var b=this.opts(),c=!b.hoverPaused&&!b.paused;a?b.hoverPaused=!1:b.paused=!1,c||(b.container.removeClass("cycle-paused"),0===b.slides.filter(":animated").length&&b.API.queueTransition(b.API.getSlideOpts(),b._remainingTimeout),b.API.trigger("cycle-resumed",[b,b._remainingTimeout]).log("cycle-resumed"))},add:function(b,c){var d,e=this.opts(),f=e.slideCount,g=!1;"string"==a.type(b)&&(b=a.trim(b)),a(b).each(function(){var b,d=a(this);c?e.container.prepend(d):e.container.append(d),e.slideCount++,b=e.API.buildSlideOpts(d),e.slides=c?a(d).add(e.slides):e.slides.add(d),e.API.initSlide(b,d,--e._maxZ),d.data("cycle.opts",b),e.API.trigger("cycle-slide-added",[e,b,d])}),e.API.updateView(!0),g=e._preInitialized&&2>f&&e.slideCount>=1,g&&(e._initialized?e.timeout&&(d=e.slides.length,e.nextSlide=e.reverse?d-1:1,e.timeoutId||e.API.queueTransition(e)):e.API.initSlideshow())},calcFirstSlide:function(){var a,b=this.opts();a=parseInt(b.startingSlide||0,10),(a>=b.slides.length||0>a)&&(a=0),b.currSlide=a,b.reverse?(b.nextSlide=a-1,b.nextSlide<0&&(b.nextSlide=b.slides.length-1)):(b.nextSlide=a+1,b.nextSlide==b.slides.length&&(b.nextSlide=0))},calcNextSlide:function(){var a,b=this.opts();b.reverse?(a=b.nextSlide-1<0,b.nextSlide=a?b.slideCount-1:b.nextSlide-1,b.currSlide=a?0:b.nextSlide+1):(a=b.nextSlide+1==b.slides.length,b.nextSlide=a?0:b.nextSlide+1,b.currSlide=a?b.slides.length-1:b.nextSlide-1)},calcTx:function(b,c){var d,e=b;return e._tempFx?d=a.fn.cycle.transitions[e._tempFx]:c&&e.manualFx&&(d=a.fn.cycle.transitions[e.manualFx]),d||(d=a.fn.cycle.transitions[e.fx]),e._tempFx=null,this.opts()._tempFx=null,d||(d=a.fn.cycle.transitions.fade,e.API.log('Transition "'+e.fx+'" not found.  Using fade.')),d},prepareTx:function(a,b){var c,d,e,f,g,h=this.opts();return h.slideCount<2?void(h.timeoutId=0):(!a||h.busy&&!h.manualTrump||(h.API.stopTransition(),h.busy=!1,clearTimeout(h.timeoutId),h.timeoutId=0),void(h.busy||(0!==h.timeoutId||a)&&(d=h.slides[h.currSlide],e=h.slides[h.nextSlide],f=h.API.getSlideOpts(h.nextSlide),g=h.API.calcTx(f,a),h._tx=g,a&&void 0!==f.manualSpeed&&(f.speed=f.manualSpeed),h.nextSlide!=h.currSlide&&(a||!h.paused&&!h.hoverPaused&&h.timeout)?(h.API.trigger("cycle-before",[f,d,e,b]),g.before&&g.before(f,d,e,b),c=function(){h.busy=!1,h.container.data("cycle.opts")&&(g.after&&g.after(f,d,e,b),h.API.trigger("cycle-after",[f,d,e,b]),h.API.queueTransition(f),h.API.updateView(!0))},h.busy=!0,g.transition?g.transition(f,d,e,b,c):h.API.doTransition(f,d,e,b,c),h.API.calcNextSlide(),h.API.updateView()):h.API.queueTransition(f))))},doTransition:function(b,c,d,e,f){var g=b,h=a(c),i=a(d),j=function(){i.animate(g.animIn||{opacity:1},g.speed,g.easeIn||g.easing,f)};i.css(g.cssBefore||{}),h.animate(g.animOut||{},g.speed,g.easeOut||g.easing,function(){h.css(g.cssAfter||{}),g.sync||j()}),g.sync&&j()},queueTransition:function(b,c){var d=this.opts(),e=void 0!==c?c:b.timeout;return 0===d.nextSlide&&0===--d.loop?(d.API.log("terminating; loop=0"),d.timeout=0,e?setTimeout(function(){d.API.trigger("cycle-finished",[d])},e):d.API.trigger("cycle-finished",[d]),void(d.nextSlide=d.currSlide)):void 0!==d.continueAuto&&(d.continueAuto===!1||a.isFunction(d.continueAuto)&&d.continueAuto()===!1)?(d.API.log("terminating automatic transitions"),d.timeout=0,void(d.timeoutId&&clearTimeout(d.timeoutId))):void(e&&(d._lastQueue=a.now(),void 0===c&&(d._remainingTimeout=b.timeout),d.paused||d.hoverPaused||(d.timeoutId=setTimeout(function(){d.API.prepareTx(!1,!d.reverse)},e))))},stopTransition:function(){var a=this.opts();a.slides.filter(":animated").length&&(a.slides.stop(!1,!0),a.API.trigger("cycle-transition-stopped",[a])),a._tx&&a._tx.stopTransition&&a._tx.stopTransition(a)},advanceSlide:function(a){var b=this.opts();return clearTimeout(b.timeoutId),b.timeoutId=0,b.nextSlide=b.currSlide+a,b.nextSlide<0?b.nextSlide=b.slides.length-1:b.nextSlide>=b.slides.length&&(b.nextSlide=0),b.API.prepareTx(!0,a>=0),!1},buildSlideOpts:function(c){var d,e,f=this.opts(),g=c.data()||{};for(var h in g)g.hasOwnProperty(h)&&/^cycle[A-Z]+/.test(h)&&(d=g[h],e=h.match(/^cycle(.*)/)[1].replace(/^[A-Z]/,b),f.API.log("["+(f.slideCount-1)+"]",e+":",d,"("+typeof d+")"),g[e]=d);g=a.extend({},a.fn.cycle.defaults,f,g),g.slideNum=f.slideCount;try{delete g.API,delete g.slideCount,delete g.currSlide,delete g.nextSlide,delete g.slides}catch(i){}return g},getSlideOpts:function(b){var c=this.opts();void 0===b&&(b=c.currSlide);var d=c.slides[b],e=a(d).data("cycle.opts");return a.extend({},c,e)},initSlide:function(b,c,d){var e=this.opts();c.css(b.slideCss||{}),d>0&&c.css("zIndex",d),isNaN(b.speed)&&(b.speed=a.fx.speeds[b.speed]||a.fx.speeds._default),b.sync||(b.speed=b.speed/2),c.addClass(e.slideClass)},updateView:function(a,b){var c=this.opts();if(c._initialized){var d=c.API.getSlideOpts(),e=c.slides[c.currSlide];!a&&b!==!0&&(c.API.trigger("cycle-update-view-before",[c,d,e]),c.updateView<0)||(c.slideActiveClass&&c.slides.removeClass(c.slideActiveClass).eq(c.currSlide).addClass(c.slideActiveClass),a&&c.hideNonActive&&c.slides.filter(":not(."+c.slideActiveClass+")").css("visibility","hidden"),0===c.updateView&&setTimeout(function(){c.API.trigger("cycle-update-view",[c,d,e,a])},d.speed/(c.sync?2:1)),0!==c.updateView&&c.API.trigger("cycle-update-view",[c,d,e,a]),a&&c.API.trigger("cycle-update-view-after",[c,d,e]))}},getComponent:function(b){var c=this.opts(),d=c[b];return"string"==typeof d?/^\s*[\>|\+|~]/.test(d)?c.container.find(d):a(d):d.jquery?d:a(d)},stackSlides:function(b,c,d){var e=this.opts();b||(b=e.slides[e.currSlide],c=e.slides[e.nextSlide],d=!e.reverse),a(b).css("zIndex",e.maxZ);var f,g=e.maxZ-2,h=e.slideCount;if(d){for(f=e.currSlide+1;h>f;f++)a(e.slides[f]).css("zIndex",g--);for(f=0;f<e.currSlide;f++)a(e.slides[f]).css("zIndex",g--)}else{for(f=e.currSlide-1;f>=0;f--)a(e.slides[f]).css("zIndex",g--);for(f=h-1;f>e.currSlide;f--)a(e.slides[f]).css("zIndex",g--)}a(c).css("zIndex",e.maxZ-1)},getSlideIndex:function(a){return this.opts().slides.index(a)}},a.fn.cycle.log=function(){window.console&&console.log&&console.log("[cycle2] "+Array.prototype.join.call(arguments," "))},a.fn.cycle.version=function(){return"Cycle2: "+c},a.fn.cycle.transitions={custom:{},none:{before:function(a,b,c,d){a.API.stackSlides(c,b,d),a.cssBefore={opacity:1,visibility:"visible",display:"block"}}},fade:{before:function(b,c,d,e){var f=b.API.getSlideOpts(b.nextSlide).slideCss||{};b.API.stackSlides(c,d,e),b.cssBefore=a.extend(f,{opacity:0,visibility:"visible",display:"block"}),b.animIn={opacity:1},b.animOut={opacity:0}}},fadeout:{before:function(b,c,d,e){var f=b.API.getSlideOpts(b.nextSlide).slideCss||{};b.API.stackSlides(c,d,e),b.cssBefore=a.extend(f,{opacity:1,visibility:"visible",display:"block"}),b.animOut={opacity:0}}},scrollHorz:{before:function(a,b,c,d){a.API.stackSlides(b,c,d);var e=a.container.css("overflow","hidden").width();a.cssBefore={left:d?e:-e,top:0,opacity:1,visibility:"visible",display:"block"},a.cssAfter={zIndex:a._maxZ-2,left:0},a.animIn={left:0},a.animOut={left:d?-e:e}}}},a.fn.cycle.defaults={allowWrap:!0,autoSelector:".cycle-slideshow[data-cycle-auto-init!=false]",delay:0,easing:null,fx:"fade",hideNonActive:!0,loop:0,manualFx:void 0,manualSpeed:void 0,manualTrump:!0,maxZ:100,pauseOnHover:!1,reverse:!1,slideActiveClass:"cycle-slide-active",slideClass:"cycle-slide",slideCss:{position:"absolute",top:0,left:0},slides:"> img",speed:500,startingSlide:0,sync:!0,timeout:4e3,updateView:0},a(document).ready(function(){a(a.fn.cycle.defaults.autoSelector).cycle()})}(jQuery),/*! Cycle2 autoheight plugin; Copyright (c) M.Alsup, 2012; version: 20130913 */
function(a){"use strict";function b(b,d){var e,f,g,h=d.autoHeight;if("container"==h)f=a(d.slides[d.currSlide]).outerHeight(),d.container.height(f);else if(d._autoHeightRatio)d.container.height(d.container.width()/d._autoHeightRatio);else if("calc"===h||"number"==a.type(h)&&h>=0){if(g="calc"===h?c(b,d):h>=d.slides.length?0:h,g==d._sentinelIndex)return;d._sentinelIndex=g,d._sentinel&&d._sentinel.remove(),e=a(d.slides[g].cloneNode(!0)),e.removeAttr("id name rel").find("[id],[name],[rel]").removeAttr("id name rel"),e.css({position:"static",visibility:"hidden",display:"block"}).prependTo(d.container).addClass("cycle-sentinel cycle-slide").removeClass("cycle-slide-active"),e.find("*").css("visibility","hidden"),d._sentinel=e}}function c(b,c){var d=0,e=-1;return c.slides.each(function(b){var c=a(this).height();c>e&&(e=c,d=b)}),d}function d(b,c,d,e){var f=a(e).outerHeight();c.container.animate({height:f},c.autoHeightSpeed,c.autoHeightEasing)}function e(c,f){f._autoHeightOnResize&&(a(window).off("resize orientationchange",f._autoHeightOnResize),f._autoHeightOnResize=null),f.container.off("cycle-slide-added cycle-slide-removed",b),f.container.off("cycle-destroyed",e),f.container.off("cycle-before",d),f._sentinel&&(f._sentinel.remove(),f._sentinel=null)}a.extend(a.fn.cycle.defaults,{autoHeight:0,autoHeightSpeed:250,autoHeightEasing:null}),a(document).on("cycle-initialized",function(c,f){function g(){b(c,f)}var h,i=f.autoHeight,j=a.type(i),k=null;("string"===j||"number"===j)&&(f.container.on("cycle-slide-added cycle-slide-removed",b),f.container.on("cycle-destroyed",e),"container"==i?f.container.on("cycle-before",d):"string"===j&&/\d+\:\d+/.test(i)&&(h=i.match(/(\d+)\:(\d+)/),h=h[1]/h[2],f._autoHeightRatio=h),"number"!==j&&(f._autoHeightOnResize=function(){clearTimeout(k),k=setTimeout(g,50)},a(window).on("resize orientationchange",f._autoHeightOnResize)),setTimeout(g,30))})}(jQuery),/*! caption plugin for Cycle2;  version: 20130306 */
function(a){"use strict";a.extend(a.fn.cycle.defaults,{caption:"> .cycle-caption",captionTemplate:"{{slideNum}} / {{slideCount}}",overlay:"> .cycle-overlay",overlayTemplate:"<div>{{title}}</div><div>{{desc}}</div>",captionModule:"caption"}),a(document).on("cycle-update-view",function(b,c,d,e){if("caption"===c.captionModule){a.each(["caption","overlay"],function(){var a=this,b=d[a+"Template"],f=c.API.getComponent(a);f.length&&b?(f.html(c.API.tmpl(b,d,c,e)),f.show()):f.hide()})}}),a(document).on("cycle-destroyed",function(b,c){var d;a.each(["caption","overlay"],function(){var a=this,b=c[a+"Template"];c[a]&&b&&(d=c.API.getComponent("caption"),d.empty())})})}(jQuery),/*! command plugin for Cycle2;  version: 20140415 */
function(a){"use strict";var b=a.fn.cycle;a.fn.cycle=function(c){var d,e,f,g=a.makeArray(arguments);return"number"==a.type(c)?this.cycle("goto",c):"string"==a.type(c)?this.each(function(){var h;return d=c,f=a(this).data("cycle.opts"),void 0===f?void b.log('slideshow must be initialized before sending commands; "'+d+'" ignored'):(d="goto"==d?"jump":d,e=f.API[d],a.isFunction(e)?(h=a.makeArray(g),h.shift(),e.apply(f.API,h)):void b.log("unknown command: ",d))}):b.apply(this,arguments)},a.extend(a.fn.cycle,b),a.extend(b.API,{next:function(){var a=this.opts();if(!a.busy||a.manualTrump){var b=a.reverse?-1:1;a.allowWrap===!1&&a.currSlide+b>=a.slideCount||(a.API.advanceSlide(b),a.API.trigger("cycle-next",[a]).log("cycle-next"))}},prev:function(){var a=this.opts();if(!a.busy||a.manualTrump){var b=a.reverse?1:-1;a.allowWrap===!1&&a.currSlide+b<0||(a.API.advanceSlide(b),a.API.trigger("cycle-prev",[a]).log("cycle-prev"))}},destroy:function(){this.stop();var b=this.opts(),c=a.isFunction(a._data)?a._data:a.noop;clearTimeout(b.timeoutId),b.timeoutId=0,b.API.stop(),b.API.trigger("cycle-destroyed",[b]).log("cycle-destroyed"),b.container.removeData(),c(b.container[0],"parsedAttrs",!1),b.retainStylesOnDestroy||(b.container.removeAttr("style"),b.slides.removeAttr("style"),b.slides.removeClass(b.slideActiveClass)),b.slides.each(function(){a(this).removeData(),c(this,"parsedAttrs",!1)})},jump:function(a,b){var c,d=this.opts();if(!d.busy||d.manualTrump){var e=parseInt(a,10);if(isNaN(e)||0>e||e>=d.slides.length)return void d.API.log("goto: invalid slide index: "+e);if(e==d.currSlide)return void d.API.log("goto: skipping, already on slide",e);d.nextSlide=e,clearTimeout(d.timeoutId),d.timeoutId=0,d.API.log("goto: ",e," (zero-index)"),c=d.currSlide<d.nextSlide,d._tempFx=b,d.API.prepareTx(!0,c)}},stop:function(){var b=this.opts(),c=b.container;clearTimeout(b.timeoutId),b.timeoutId=0,b.API.stopTransition(),b.pauseOnHover&&(b.pauseOnHover!==!0&&(c=a(b.pauseOnHover)),c.off("mouseenter mouseleave")),b.API.trigger("cycle-stopped",[b]).log("cycle-stopped")},reinit:function(){var a=this.opts();a.API.destroy(),a.container.cycle()},remove:function(b){for(var c,d,e=this.opts(),f=[],g=1,h=0;h<e.slides.length;h++)c=e.slides[h],h==b?d=c:(f.push(c),a(c).data("cycle.opts").slideNum=g,g++);d&&(e.slides=a(f),e.slideCount--,a(d).remove(),b==e.currSlide?e.API.advanceSlide(1):b<e.currSlide?e.currSlide--:e.currSlide++,e.API.trigger("cycle-slide-removed",[e,b,d]).log("cycle-slide-removed"),e.API.updateView())}}),a(document).on("click.cycle","[data-cycle-cmd]",function(b){b.preventDefault();var c=a(this),d=c.data("cycle-cmd"),e=c.data("cycle-context")||".cycle-slideshow";a(e).cycle(d,c.data("cycle-arg"))})}(jQuery),/*! hash plugin for Cycle2;  version: 20130905 */
function(a){"use strict";function b(b,c){var d;return b._hashFence?void(b._hashFence=!1):(d=window.location.hash.substring(1),void b.slides.each(function(e){if(a(this).data("cycle-hash")==d){if(c===!0)b.startingSlide=e;else{var f=b.currSlide<e;b.nextSlide=e,b.API.prepareTx(!0,f)}return!1}}))}a(document).on("cycle-pre-initialize",function(c,d){b(d,!0),d._onHashChange=function(){b(d,!1)},a(window).on("hashchange",d._onHashChange)}),a(document).on("cycle-update-view",function(a,b,c){c.hash&&"#"+c.hash!=window.location.hash&&(b._hashFence=!0,window.location.hash=c.hash)}),a(document).on("cycle-destroyed",function(b,c){c._onHashChange&&a(window).off("hashchange",c._onHashChange)})}(jQuery),/*! loader plugin for Cycle2;  version: 20131121 */
function(a){"use strict";a.extend(a.fn.cycle.defaults,{loader:!1}),a(document).on("cycle-bootstrap",function(b,c){function d(b,d){function f(b){var f;"wait"==c.loader?(h.push(b),0===j&&(h.sort(g),e.apply(c.API,[h,d]),c.container.removeClass("cycle-loading"))):(f=a(c.slides[c.currSlide]),e.apply(c.API,[b,d]),f.show(),c.container.removeClass("cycle-loading"))}function g(a,b){return a.data("index")-b.data("index")}var h=[];if("string"==a.type(b))b=a.trim(b);else if("array"===a.type(b))for(var i=0;i<b.length;i++)b[i]=a(b[i])[0];b=a(b);var j=b.length;j&&(b.css("visibility","hidden").appendTo("body").each(function(b){function g(){0===--i&&(--j,f(k))}var i=0,k=a(this),l=k.is("img")?k:k.find("img");return k.data("index",b),l=l.filter(":not(.cycle-loader-ignore)").filter(':not([src=""])'),l.length?(i=l.length,void l.each(function(){this.complete?g():a(this).load(function(){g()}).on("error",function(){0===--i&&(c.API.log("slide skipped; img not loaded:",this.src),0===--j&&"wait"==c.loader&&e.apply(c.API,[h,d]))})})):(--j,void h.push(k))}),j&&c.container.addClass("cycle-loading"))}var e;c.loader&&(e=c.API.add,c.API.add=d)})}(jQuery),/*! pager plugin for Cycle2;  version: 20140415 */
function(a){"use strict";function b(b,c,d){var e,f=b.API.getComponent("pager");f.each(function(){var f=a(this);if(c.pagerTemplate){var g=b.API.tmpl(c.pagerTemplate,c,b,d[0]);e=a(g).appendTo(f)}else e=f.children().eq(b.slideCount-1);e.on(b.pagerEvent,function(a){b.pagerEventBubble||a.preventDefault(),b.API.page(f,a.currentTarget)})})}function c(a,b){var c=this.opts();if(!c.busy||c.manualTrump){var d=a.children().index(b),e=d,f=c.currSlide<e;c.currSlide!=e&&(c.nextSlide=e,c._tempFx=c.pagerFx,c.API.prepareTx(!0,f),c.API.trigger("cycle-pager-activated",[c,a,b]))}}a.extend(a.fn.cycle.defaults,{pager:"> .cycle-pager",pagerActiveClass:"cycle-pager-active",pagerEvent:"click.cycle",pagerEventBubble:void 0,pagerTemplate:"<span>&bull;</span>"}),a(document).on("cycle-bootstrap",function(a,c,d){d.buildPagerLink=b}),a(document).on("cycle-slide-added",function(a,b,d,e){b.pager&&(b.API.buildPagerLink(b,d,e),b.API.page=c)}),a(document).on("cycle-slide-removed",function(b,c,d){if(c.pager){var e=c.API.getComponent("pager");e.each(function(){var b=a(this);a(b.children()[d]).remove()})}}),a(document).on("cycle-update-view",function(b,c){var d;c.pager&&(d=c.API.getComponent("pager"),d.each(function(){a(this).children().removeClass(c.pagerActiveClass).eq(c.currSlide).addClass(c.pagerActiveClass)}))}),a(document).on("cycle-destroyed",function(a,b){var c=b.API.getComponent("pager");c&&(c.children().off(b.pagerEvent),b.pagerTemplate&&c.empty())})}(jQuery),/*! prevnext plugin for Cycle2;  version: 20140408 */
function(a){"use strict";a.extend(a.fn.cycle.defaults,{next:"> .cycle-next",nextEvent:"click.cycle",disabledClass:"disabled",prev:"> .cycle-prev",prevEvent:"click.cycle",swipe:!1}),a(document).on("cycle-initialized",function(a,b){if(b.API.getComponent("next").on(b.nextEvent,function(a){a.preventDefault(),b.API.next()}),b.API.getComponent("prev").on(b.prevEvent,function(a){a.preventDefault(),b.API.prev()}),b.swipe){var c=b.swipeVert?"swipeUp.cycle":"swipeLeft.cycle swipeleft.cycle",d=b.swipeVert?"swipeDown.cycle":"swipeRight.cycle swiperight.cycle";b.container.on(c,function(){b._tempFx=b.swipeFx,b.API.next()}),b.container.on(d,function(){b._tempFx=b.swipeFx,b.API.prev()})}}),a(document).on("cycle-update-view",function(a,b){if(!b.allowWrap){var c=b.disabledClass,d=b.API.getComponent("next"),e=b.API.getComponent("prev"),f=b._prevBoundry||0,g=void 0!==b._nextBoundry?b._nextBoundry:b.slideCount-1;b.currSlide==g?d.addClass(c).prop("disabled",!0):d.removeClass(c).prop("disabled",!1),b.currSlide===f?e.addClass(c).prop("disabled",!0):e.removeClass(c).prop("disabled",!1)}}),a(document).on("cycle-destroyed",function(a,b){b.API.getComponent("prev").off(b.nextEvent),b.API.getComponent("next").off(b.prevEvent),b.container.off("swipeleft.cycle swiperight.cycle swipeLeft.cycle swipeRight.cycle swipeUp.cycle swipeDown.cycle")})}(jQuery),/*! progressive loader plugin for Cycle2;  version: 20130315 */
function(a){"use strict";a.extend(a.fn.cycle.defaults,{progressive:!1}),a(document).on("cycle-pre-initialize",function(b,c){if(c.progressive){var d,e,f=c.API,g=f.next,h=f.prev,i=f.prepareTx,j=a.type(c.progressive);if("array"==j)d=c.progressive;else if(a.isFunction(c.progressive))d=c.progressive(c);else if("string"==j){if(e=a(c.progressive),d=a.trim(e.html()),!d)return;if(/^(\[)/.test(d))try{d=a.parseJSON(d)}catch(k){return void f.log("error parsing progressive slides",k)}else d=d.split(new RegExp(e.data("cycle-split")||"\n")),d[d.length-1]||d.pop()}i&&(f.prepareTx=function(a,b){var e,f;return a||0===d.length?void i.apply(c.API,[a,b]):void(b&&c.currSlide==c.slideCount-1?(f=d[0],d=d.slice(1),c.container.one("cycle-slide-added",function(a,b){setTimeout(function(){b.API.advanceSlide(1)},50)}),c.API.add(f)):b||0!==c.currSlide?i.apply(c.API,[a,b]):(e=d.length-1,f=d[e],d=d.slice(0,e),c.container.one("cycle-slide-added",function(a,b){setTimeout(function(){b.currSlide=1,b.API.advanceSlide(-1)},50)}),c.API.add(f,!0)))}),g&&(f.next=function(){var a=this.opts();if(d.length&&a.currSlide==a.slideCount-1){var b=d[0];d=d.slice(1),a.container.one("cycle-slide-added",function(a,b){g.apply(b.API),b.container.removeClass("cycle-loading")}),a.container.addClass("cycle-loading"),a.API.add(b)}else g.apply(a.API)}),h&&(f.prev=function(){var a=this.opts();if(d.length&&0===a.currSlide){var b=d.length-1,c=d[b];d=d.slice(0,b),a.container.one("cycle-slide-added",function(a,b){b.currSlide=1,b.API.advanceSlide(-1),b.container.removeClass("cycle-loading")}),a.container.addClass("cycle-loading"),a.API.add(c,!0)}else h.apply(a.API)})}})}(jQuery),/*! tmpl plugin for Cycle2;  version: 20121227 */
function(a){"use strict";a.extend(a.fn.cycle.defaults,{tmplRegex:"{{((.)?.*?)}}"}),a.extend(a.fn.cycle.API,{tmpl:function(b,c){var d=new RegExp(c.tmplRegex||a.fn.cycle.defaults.tmplRegex,"g"),e=a.makeArray(arguments);return e.shift(),b.replace(d,function(b,c){var d,f,g,h,i=c.split(".");for(d=0;d<e.length;d++)if(g=e[d]){if(i.length>1)for(h=g,f=0;f<i.length;f++)g=h,h=h[i[f]]||c;else h=g[c];if(a.isFunction(h))return h.apply(g,e);if(void 0!==h&&null!==h&&h!=c)return h}return c})}})}(jQuery);
// 顶部导航需要的脚本
(function($) {
	//$('[name="Page_firefrog_index"]').length > 0 ? $('.back-top-home').hide() : $('.back-top-home').show();
	// 顶部用户信息
	/*new phoenix.Hover({
		triggers: '#J-top-userinfo',
		panels: '#J-top-userinfo .menu-nav',
		hoverDelayOut: 300
	});*/
	
	new phoenix.Hover({
		triggers      :'#J-user-center',
		panels        :'#J-user-center .menu-nav',
		hoverDelayOut :300
	});
	


	// 顶部彩种菜单
	/*new phoenix.Hover({
		triggers: '#J-top-game-menu',
		panels: '#J-top-game-menu .game-menu-panel',
		hoverDelayOut: 300,
		currClass: 'game-menu-current'
	});*/
	
	
		// 彩种链接
	new phoenix.Hover({
		triggers      :'#J-btn-lottery',
		panels        :'#J-btn-lottery .lottery-link',
		hoverDelayOut :300
	});
	new phoenix.Hover({
		triggers: '#J-btn-elegame',
		panels: '#J-btn-elegame .ele-game',
		hoverDelayOut: 300
	});
	// 彩种链接
	new phoenix.Hover({
		triggers      :'#J-btn-download',
		panels        :'#J-btn-download .app-download',
		hoverDelayOut :300
	});
	
	
	// 游戏记录tab切换
	new phoenix.Tab({
		par           : '#news-tab',
		triggers      : '.news-list-hd li',
		panels        : '.news-tab-con .news-list-bd',
		currClass     : 'active',
		currPanelClass: 'active'
	});
	// 游戏记录tab切换
	new phoenix.Tab({
		par           : '#menu-main',
		triggers      : '.menu-title li',
		panels        : '.menu-con .menu-type',
		currClass     : 'active',
		currPanelClass: 'active'
	});
	// 顶部站内消息
	new phoenix.Hover({
		triggers: '#J-top-user-message',
		panels: '#J-top-user-message .msg-box',
		hoverDelayOut: 300
	});
	//在线客服
	$('#J-client-service').click(function(){
		$(this).find('.service-box').fadeIn().delay(3000).fadeOut();
	});
	//显示余额
	$('#showAllBall').click(function() {
		$.cookie("showAllBall", "1", {
			expires: 7,
			path: '/'
		});
		$('#hiddBall').css("display", "inline");
		$('#hiddenBall').css("display", "none");
	});
	//隐藏余额
	$('#hiddBall').click(function() {
		$.cookie("showAllBall", "0", {
			expires: 7,
			path: '/'
		});
		$('#hiddBall').css("display", "none");
		$('#hiddenBall').css("display", "inline");
	});
	try {
		//自动查询此用户未读信件
		$.ajax({
			type: 'post',
			dataType: 'json',
			cache: false,
			url: '/service2/queryunreadmessage',
			data: '',
			success: function(json) {
				if (json.unreadCnt != 0) {
					var html = "";
					$.each(json.receives,
					function(i) {
						if(json.receives[i].parentAccount == json.receives[i].sendAccount){
							json.receives[i].sendAccount = "上級代理";
						}else if(json.receives[i].parentAccount == null && json.receives[i].sendAccount != "系统管理员" ){
							json.receives[i].sendAccount = "下級玩家";
						}
						html += "<a href=\"/Service2/sysmessages?id=" + json.receives[i].id + "&msgTpye=uMsg&unread=1&pro=" + json.unreadCnt + "\">" +json.receives[i].sendAccount+"(未读消息"+json.unreadCnt+"笔)"+ "</a>";
					});
					$("#readmsg").html(html);
					$("#msgcount,#msg-title,#noreadmsg,#msgcount2,#noreadmsg2").html(parseInt(json.unreadCnt));

				} else {
					$("#readmsg").html("暂未收到新消息");
					$('#msgcount,#msg-title,#noreadmsg,#msgcount2,#noreadmsg2').html("0");
					$('#readmsg').attr("style", "text-align:center; color:black;");
				}
			}
		});
	} catch(err) {

}

	//金额刷新
	$('.refreshBall').click(function(event) {
		var spanBalls = $('#spanBall');
		try {
			//用户余额接口
			$.ajax({
				type: 'post',
				dataType: 'json',
				cache: false,
				url: '/index/getuserbal',
				data: '',
				beforeSend: function() {
					spanBalls.css('font-size', '11px').html('查询中...');
					$('.refreshBall').hide();
				},
				success: function(data) {
					if (data['status'] == "ok") {
						spanBalls.removeAttr('style').text(data["data"]);
						$('.refreshBall').show();
					}
				},
				complete: function() {
					$('.refreshBall').show();
				}
			});
		} catch(err) {
			console.log("网络异常，读取信件信息失败");
		}
		event.stopPropagation();
	});
        $('.refreshBall').click();
        
	$('.user-balance-amount').click(function() {
		var spanBalls = $('[name="spanBall"]'),
		userBalanceTitle = $('.uuser-balance-amount');
		try {
			//用户余额接口
			$.ajax({
				type: 'post',
				dataType: 'json',
				cache: false,
				url: '/index/getuserbal',
				data: '',
				beforeSend: function() {
					userBalanceTitle.addClass('user-balance-refresh');
					$('[name=spanBall]').css('text-align', 'center').html('查询中...');
				},
				success: function(data) {
					if (data['status'] == "ok") {
						setTimeout(function() {
							spanBalls.text(data["data"]);
						},
						500);

					}
				},
				complete: function() {
					setTimeout(function() {
						userBalanceTitle.removeClass('user-balance-refresh');
					},
					500);
				}
			});
		} catch(err) {

              }
	});
        $('.user-balance-amount').click();
	//读取，修改余额可见状态值
	var cookieAllball = $.cookie("showAllBall");
	if (cookieAllball == "1") {
		$('#hiddBall').css("display", "inline");
		$('#hiddenBall').css("display", "none");
	} else {
		$('#hiddBall').css("display", "none");
		$('#hiddenBall').css("display", "inline");
	}
})(jQuery);

$(function() {
	var saveTime = {};
	var topTimer = new phoenix.CountDown({
		//结束时间
		'endTime': sEndTime,
		//开始时间
		'startTime': sNowTime,
		//是否需要循环计时
		'isLoop': false,
		//需要渲染的DOM结构
		'showDom': '.deadline-number',
		expands: {
			//覆盖showTime渲染方法
			_showTime: function(time) {
				
				var me = this,
				dom = $(me.defConfig.showDom),
				d = '' + me.checkNum(time.d),
               
				m = me.checkNum(time.m) + '',
				s = me.checkNum(time.s) + '';
				//渲染时间输出
				if(d > 0)
				{
                    h = '' + me.checkNum(time.h + time.d * 24);
                }
				else
				{
					 h = me.checkNum(time.h) + '';
				}

				if(h>99)
				{
					dom.find('.hour-left').text('9');
					dom.find('.hour-right').text('9');
				}
			    else
				{
					dom.find('.hour-left').text(h.substring(0, 1));
				    dom.find('.hour-right').text(h.substring(1, 2));
				}
				
				dom.find('.hour-center').text(h.substring(2, 3));
				dom.find('.min-left').text(m.substring(0, 1));
				dom.find('.min-right').text(m.substring(1, 2));
				dom.find('.sec-left').text(s.substring(0, 1));
				dom.find('.sec-right').text(s.substring(1, 2));
				//console.log(time);
				me.fireEvent('afterShowTime', time, me);
				
			}
		}
	});

	topTimer.addEvent('afterTimeFinish',
	function() {
		//定时器结束，当前期结束
		//定时器结束，请把逻辑写在这里 定时器完成时自动执行
		$.ajax({
			url: '/index/getgametimedown',
			dataType: 'JSON',
			cache: false,
			success: function(data) {
				if (data['status'] == "ok") {
					var result = data['data']['result'];

					var nowstoptime = JSON.stringify(result['saleEndTime']),
					nowtime = JSON.stringify(data['data']['nowTime']),
					nowNumber = JSON.stringify(result['currentIssue']);
					$('#nowNumber').html(nowNumber);

					topTimer.setStartTime(nowtime);
					topTimer.setEndTime(nowstoptime);
					topTimer.continueCount();
				}

			}
		});

	});

	$('.header_tools .user_action').hover(function() {
		$(this).addClass('user_action_active').find('.action_menus, .action_shadow').slideDown(200).end().find('.caret').addClass('active_caret');
	},
	function() {
		$(this).removeClass('user_action_active').find('.action_menus, .action_shadow').slideUp(200).end().find('.caret').removeClass('active_caret');
	});
	// 游戏记录tab切换
	new phoenix.Tab({
		par: '#game_log',
		triggers: '.log_tabs li',
		panels: '.log_panels .panel',
		eventType: 'click',
		currClass: 'active',
		currPanelClass: 'active'
	});

	var showWinners = function() {
		$.ajax({
			url: '/index/getgameprizelist',
			dataType: 'JSON',
			cache: false,
			success: function(data) {
				if (data['status'] == "ok") {
					var result = data['data'];

					if (result['99112']) { //秒秒彩							
						pushDateMoth(result['99112']['wins'], '99112', '.mmc-info', 'leftone');
					}
					if (result['99106']) { //乐利彩								
						pushDateMoth(result['99106']['wins'], '99106', '.lelicai-animate', 'lefttwo');
					}
					if (result['99305']) { //乐利115
						pushDateMoth(result['99305']['wins'], '99305', '.n115-animate', 'leftthree');
					}
					if (result['99111']) { //吉利分分彩
						pushDateMoth(result['99111']['wins'], '99111', '.jili-animate', 'leftfour');
					}

				}

			}
		});
	}
	setInterval(showWinners, 120000);
	//setInterval(showWinners, 5000); 
	var pushDateMoth = function(arrObj, lottryid, divHtml, leftLocation) {
		var htmls = '';
		$(divHtml).html('');
		if (!arrObj) {
			return;
		}
		for (var i = 0; i < arrObj.length; i++) {

			htmls += "<li>" + arrObj[i].username + "&nbsp;&nbsp;中奖" + arrObj[i].prize + "元</li>";
		}
		$(divHtml).html(htmls);
		scrollAnimate(divHtml, 'li', leftLocation, 'top');
	}

	var scrollAnimate = function(dom, cycleDom, name, animateType, minNum) {
		var $parentDom = $(dom),
		$child = $parentDom.find(cycleDom),
		height = $child.eq(0).outerHeight(),
		minNum = minNum || 1,
		time = 3;

		if ($child.size() > minNum) {

			if (typeof saveTime[name] == 'undefined') {
				saveTime[name] = {};
			} else {
				clearInterval(saveTime[name]);
			}
			//循环滚动显示
			saveTime[name] = setInterval(function() {
				dom = $parentDom.find(cycleDom);

				if (animateType == 'top') {

					dom.eq(0).animate({
						marginTop: -height
					},
					1000,
					function() {

						$parentDom.append(dom.eq(0));
						dom.eq(0).css('marginTop', 0);
						dom.removeClass('current');
						dom.eq(2).addClass('current');
					})
				} else if (animateType == 'bottom') {
					dom = dom.eq(dom.length - 1);

					$parentDom.prepend(dom);

					dom.css('marginTop', -height);
					dom.eq(0).animate({
						marginTop: 0
					},
					1000)
				}
			},
			time * 1000)
		}
	}

	//左侧滚动一
	scrollAnimate('.mmc-info', 'li', 'leftone', 'top', 3);
	//左侧滚动一
	scrollAnimate('.lelicai-animate', 'li', 'lefttwo', 'top');
	//左侧滚动一
	scrollAnimate('.n115-animate', 'li', 'leftthree', 'top');
	//左侧滚动一
	scrollAnimate('.jili-animate', 'li', 'leftfour', 'top');

	var $dialog = $('#J-dialog-qq');
	var $button = $('#J-button-qq');
	var $dialogClose = $('#J-dialogqq-close');
	var $sliderBarclose = $('#sliderBar_close');

	/*		$(".close").click(function(){
			$(".dialog-qqlist").hide();
			$(".slider-bar").hide();
		});*/

	$button.click(function() {

		if ($dialog.is(':hidden')) {
			$dialog.show();
		} else {
			$dialog.hide();
		}
	});

	$sliderBarclose.click(function() {
		$('#sliderBar').hide();
	});

	$dialogClose.click(function() {
		$dialog.hide();
	});

	//焦点图绑定更新事件
	$(document).on('cycle-update-view', '#focus',
	function(event, opts) {
		var $dom = $(this);

		if (Number(opts['slideCount']) <= 1) {
			$dom.find('.prev,.next,.cycle-pager-wrap').hide();
		} else {
			$dom.find('.prev,.next,.cycle-pager-wrap').show();
		}
	});


	//焦点图加载完毕绑定点击ga监控
	$(document).on('cycle-initialized', '#focus',function(event, opts) {
		(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
			(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
			m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

		ga('create', 'UA-71901456-1', 'auto',{'name': 'pdd'});
		ga('pdd.send', 'pageview');

		//google analytics 点击统计
		$('#J-globalad-index_banner_right').on('click',function(){
			ga('pdd.send', 'event', '右侧小banner', '点击');
		});
		//banner 统计
		var slider = $('#focus .cycle-slide');
		$.each(slider,function(num,val){
			$(val).find('a').on('click',function(){
				ga('pdd.send', 'event', '大banner位置'+ (num+1) , '点击');
			});
		});
	});



	//用JS方法初始化滚动图
	var pressSlideshow = $('#focus').cycle();

	//处理广告数据
	var reBuildAd = function(tpl, ad, w, h) {
		var me = tpl;
		ad['link'] = reBuildLink(ad['id'], ad['link']);
		ad['src'] = ad['src'];
		ad['width'] = w;
		ad['height'] = h;
		return ad;
	}
	var reBuildLink = function(id, link) {
		if (link != null && link.indexOf("{phoenixURL}") != -1) {
			link = link.replace('{phoenixURL}', '');
			link = window.location.host.replace('www', 'http://em') + link;
		}
		return link == null ? "": link;
	}

	$.ajax({
		url: '/api/jsonp/getAdverts?k=index_pos_center_new,index_beginner_guide,index_down_app,index_banner_left,index_banner_center,index_banner_right&r=' + Math.random(),
		cache: false,
		dataType: 'jsonp',
		jsonp: "callBack",
		success: function(data) {
			
			if (Number(data['isSuccess']) == 1) {
				var me = data,
				list = me.data,
				len = list.length,
				listLen, dom, width, height, tplSingle, html = '';
				if (len > 0) {
					
					for (var i = 0; i < len; i++) {
					
						if (list[i]['name'] == 'index_pos_center_new') {
							
							html = '';
							width = 900;
							height = 360;
							dom = $('#focus');
							tplSingle = '<div class="item"><a href="<#=link#>" target="_blank"><img src="<#=src#>" title="<#=title#>" alt="<#=title#>"></a></div>'
							if (dom.size() > 0) {
								
								listLen = list[i]['list'].length;
								if (listLen > 0) {
									dom.css({
										width: width
									});
									for (var j = 0; j < listLen; j++) {
									
										ad = reBuildAd(tplSingle, list[i]['list'][j], width, height);
										
										ad['index'] = (j + 1);
										html = phoenix.util.template(tplSingle, ad);
										dom.cycle('add', html);
									}

								} else {
									getbanner_post_center();
								}
							}
						} else if (list[i]['name'] == 'index_beginner_guide') {
							html = '';
							width = 220;
							height = 190;
							tplSingle = '<a href="<#=link#>" target="_blank" ><img src="<#=src#>" title="<#=title#>" alt="<#=title#>"></a>';
							dom = $('#J-globalad-' + list[i]['name']);
							if (dom.size() > 0) {
								listLen = list[i]['list'].length;
								if (listLen > 0) {
									dom.css({
										width: width
									});
									for (var j = 0; j < listLen; j++) {
										ad = reBuildAd(tplSingle, list[i]['list'][j], width, height);
										ad['index'] = (j + 1);
										html += phoenix.util.template(tplSingle, ad);
									}
									dom.append(html);
								} else {
									dom.append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/common/bg-beginner-guide.jpg" alt=""></a>');
								}
							}
						} else if (list[i]['name'] == 'index_down_app') {
							
							html = '';
							width = 220;
							height = 270;
							tplSingle = '<a href="<#=link#>" target="_blank" ><img src="<#=src#>" title="<#=title#>" alt="<#=title#>"></a>';
							dom = $('#J-globalad-' + list[i]['name']);
							if (dom.size() > 0) {
								listLen = list[i]['list'].length;
								if (listLen > 0) {
									dom.css({
										width: width
									});
									for (var j = 0; j < listLen; j++) {
										ad = reBuildAd(tplSingle, list[i]['list'][j], width, height);
										ad['index'] = (j + 1);
										html += phoenix.util.template(tplSingle, ad);
									}
									dom.append(html);
								} else {
									dom.append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/common/bg-download-app.jpg" alt=""></a>');
								}
							}
						} else if (list[i]['name'] == 'index_banner_left') {
							
							html = '';
							width = 280;
							height = 230;
							tplSingle = '<a href="<#=link#>" target="_blank" ><img src="<#=src#>" title="<#=title#>" alt="<#=title#>"></a>';
							dom = $('#J-globalad-' + list[i]['name']);
							if (dom.size() > 0) {
								listLen = list[i]['list'].length;
								if (listLen > 0) {
									dom.css({
										width: width
									});
									//for (var j = 0; j < listLen; j++) {
									 for (var j = 0; j < 1; j++) {
										
										ad = reBuildAd(tplSingle, list[i]['list'][j], width, height);
										ad['index'] = (j + 1);
										html += phoenix.util.template(tplSingle, ad);
									}
									dom.append(html);
									
								} else {
									
									getbanner_left();
									
									//console.log(banner.length);
								
									//dom.append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/common/ico-loading.gif" alt=""></a>');
									
								}
								
							}
							
						} else if (list[i]['name'] == 'index_banner_center') {
							html = '';
							width = 280;
							height = 230;
							tplSingle = '<a href="<#=link#>" target="_blank" ><img src="<#=src#>" title="<#=title#>" alt="<#=title#>"></a>';
							dom = $('#J-globalad-' + list[i]['name']);
							if (dom.size() > 0) {
								listLen = list[i]['list'].length;
								if (listLen > 0) {
									dom.css({
										width: width
									});
									//for (var j = 0; j < listLen; j++) {
									for (var j = 0; j < 1; j++) {
										ad = reBuildAd(tplSingle, list[i]['list'][j], width, height);
										ad['index'] = (j + 1);
										html += phoenix.util.template(tplSingle, ad);
									}
									dom.append(html);
								} else {
									getbanner_center();
									//dom.append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/common/ico-loading.gif" alt=""></a>');
								}
							}
						} else if (list[i]['name'] == 'index_banner_right') {
							html = '';
							width = 280;
							height = 230;
							tplSingle = '<a href="<#=link#>" target="_blank" ><img src="<#=src#>" title="<#=title#>" alt="<#=title#>"></a>';
							dom = $('#J-globalad-' + list[i]['name']);
							if (dom.size() > 0) {
								listLen = list[i]['list'].length;
								if (listLen > 0) {
									dom.css({
										width: width
									});
									//for (var j = 0; j < listLen; j++) {
									 for (var j = 0; j < 1; j++) {
										ad = reBuildAd(tplSingle, list[i]['list'][j], width, height);
										ad['index'] = (j + 1);
										html += phoenix.util.template(tplSingle, ad);
									}
									dom.append(html);
								} else {
									
									
									
									getbanner_right();
									//dom.append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/common/ico-loading.gif" alt=""></a>');
								}
							}
						} 
					}
				}
			} else {
				$('#focus').cycle('add', '<div class="item"><a href="/index" target="_blank" ><img src="' + global_path_url + '/images/common/slideshow1.jpg" alt=""></a></div>');
				$('#J-globalad-index_beginner_guide').append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/index/bg-beginner-guide.jpg" alt=""></a>');
				$('#J-globalad-index_down_app').append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/index/bg-download-app.jpg" alt=""></a>');
			}
		},
		error: function(xhr, type) {
			$('#focus').cycle('add', '<div class="item"><a href="/index" target="_blank" ><img src="' + global_path_url + '/images/common/slideshow1.jpg" alt=""></a></div>');
			$('#J-globalad-index_beginner_guide').append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/index/bg-beginner-guide.jpg" alt=""></a>');
			$('#J-globalad-index_down_app').append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/index/bg-download-app.jpg" alt=""></a>');
		}
	});
	
	function getbanner_left(data,callback)
	{
		
											
	   $.ajax( {
                url: '/index/getbanner',// 跳转到 action
                data:'',
                type:'post',
                dataType:'json',
                
				success:function(data) {
					
					bannerdata = data['data']['body']['result'];
					
					
					for (var i=0;i<bannerdata.length;i++)
				    
					{
						if(bannerdata[i]['name']=='index_banner_left')
						{
							
							$('#J-globalad-index_banner_left').append('<a href="/index" target="_blank" ><img src="'+ dynamicroot +'/'+ bannerdata[i]['dftImg'] +'" alt=""></a>');
							
						}
						
						
					 }
				}
                
            });
     }
	 
	function getbanner_right(data,callback)
	{
			
											
	   $.ajax( {
                url: '/index/getbanner',// 跳转到 action
                data:'',
                type:'post',
                dataType:'json',
                
				success:function(data) {
					
					bannerdata = data['data']['body']['result'];
					
					
					for (var i=0;i<bannerdata.length;i++)
				    
					{
						if(bannerdata[i]['name']=='index_banner_right')
						{
							//console.log(bannerdata[i]['dftImgs']);	
							$('#J-globalad-index_banner_right').append('<a href="/index" target="_blank" ><img src="'+ dynamicroot +'/'+bannerdata[i]['dftImg'] +'" alt=""></a>');
						}
						
					 }
				}
                
            });
     }
	 
	function getbanner_center(data,callback)
	{
		
	   $.ajax( {
                url: '/index/getbanner',// 跳转到 action
                data:'',
                type:'post',
                dataType:'json',
                
				success:function(data) {
					
					bannerdata = data['data']['body']['result'];
					
					
					for (var i=0;i<bannerdata.length;i++)
				    
					{
					
						if(bannerdata[i]['name']=='index_banner_center')
						{
							//console.log(bannerdata[i]['dftImgs']);	
							$('#J-globalad-index_banner_center').append('<a href="/index" target="_blank" ><img src="'+ dynamicroot +'/'+bannerdata[i]['dftImg'] +'" alt=""></a>');
						}
					
						
					 }
				}
                
            });
     }
	 
	 function getbanner_post_center(data,callback)
	{
		
	   $.ajax( {
                url: '/index/getbanner',// 跳转到 action
                data:'',
                type:'post',
                dataType:'json',
                
				success:function(data) {
					
					bannerdata = data['data']['body']['result'];
					
					
					for (var i=0;i<bannerdata.length;i++)
				    
					{
					
						if(bannerdata[i]['name']=='index_pos_center_new')
						{
							//console.log(bannerdata[i]['dftImgs']);	
							$('#focus').cycle('add', '<div class="item"><a href="/index" target="_blank" ><img src="'+ dynamicroot +'/'+bannerdata[i]['dftImg'] +'" alt=""></a></div>');
							
						}
					
						
					 }

				}
                
            });
     }
									

	var win = $(window),
	float = $('#sliderBar'),
	top = parseInt(float.css('top')),
	sTop = win.scrollTop(),
	_sTop = sTop;
	setInterval(function() {
		_sTop = win.scrollTop();
		if (sTop != _sTop) {
			float.stop().animate(0, 250);
			sTop = _sTop;
		}
	},
	50);

	// monopoly - start
	$(function(){
		function getMainDomain(hostName)
		{
			return hostName.substring(hostName.lastIndexOf(".", hostName.lastIndexOf(".") - 1) + 1);
		}
		var main_domain = getMainDomain(window.location.host);
		//console.log(main_domain);
		
		//玩法链接地址
		var arrLink = [
			['http://pt.' + main_domain + '/pt/index', ' PT老虎机上线了', 'PT老虎机'],
			['http://em.' + main_domain + '/gameBet/jsdice', '江苏骰宝，玩的就是刺激', '江苏骰宝'],
			['http://em.' + main_domain + '/gameBet/llssc', '乐利时时彩,美女视频陪你投注哦', '乐利时时彩'],
			['http://em.' + main_domain + '/gameBet/ssq', '您的双色球幸运号码：' + redBall() + blueBall(), '幸运双色球'],
			['http://em.' + main_domain + '/gameBet/jlffc', '吉利分分彩,一分钟一开奖', '吉利分分彩']
		];
		//每个格子数据
		var database = {
			0:arrLink[0],
			1:null,
			2:arrLink[1],
			3:null,
			4:arrLink[2],
			5:null,
			6:arrLink[3],
			7:arrLink[4],
			8:null
		}
		function resetDatabase(){
			database[1] = createRandomData('奖金翻倍');
			database[3] = createRandomData('哇哦，命运大奖');
			database[5] = createRandomData('奖金翻倍');
			database[8] = createRandomData('哇哦，机会大奖');
		}
		//奖金翻倍，命运大奖，机会大奖数据随机
		function createRandomData(title){
			var arr = arrLink[parseInt(Math.random()*arrLink.length)].slice();
			arr[2]=title;
		 	return arr;
	    }
		resetDatabase();//生成数据
	    //随机生成双色球号码
	    function createRandomNumber(num ,from ,to ){
			var arr=new Array();
			for(var i=from;i<=to;i++){
				arr.push(i);
			}
			arr.sort(function(){
				return 0.5-Math.random();
			});
			arr.length = num;
			arr.sort(function(a,b){
				return a - b;
			});
			return arr;
		}
		function redBall(){
			var arr = createRandomNumber(6,1,33);
			$('#text').append('<span id="redBall"><span class="redBall"></span></span>');
			for(var i=0;i<arr.length;i++){
				$('.redBall').append('<em>'+arr[i]+'</em>');
			}
			return $('#redBall').html();
		}
		function blueBall(){
			var arr = createRandomNumber(1,1,16);
			$('#text').append('<span id="blueBall"><span class="blueBall"></span></span>');
			for(var i=0;i<arr.length;i++){
				$('.blueBall').append('<em>'+arr[i]+'</em>');
			}
			return $('#blueBall').html();
		}
		//骰子动画
		function diceroll(dice,num){
			dice.attr("class","dice");//清除上次动画后的点数
			var maxNum = 6;
			var time = setInterval(function(){
				maxNum > 1 ? maxNum-- : maxNum = 6;
				dice.attr('class', 'dice dice_' + maxNum);
			},100);
			setTimeout(function(){
				clearInterval(time);
				dice.attr('class', 'dice dice_' + num);
			},1000);
		}
		//点数移动路径
		function roll(i,step){
			var time = setInterval(function(){
				if(i>9){
					var t = 19-i;//点数大于9，往回走
					$("#d_"+t).addClass('current').find('.monopoly-step-hover').fadeIn(200);
					$("#d_"+t).siblings().removeClass('current').find('.monopoly-step-hover').fadeOut(200);
				}
				$("#d_"+i).addClass('current').find('.monopoly-step-hover').fadeIn(200)
				$("#d_"+i).siblings().removeClass('current').find('.monopoly-step-hover').fadeOut(200);
				if(i==step){
					clearInterval(time); //如果到达指定位置则停止
					if(i>9){
						i = 19-i;
					}
					setTimeout(function(){
						$("#d_"+(i-1)).addClass('end').find('.monopoly-light').fadeIn(200);
						$('.human').removeClass('human-run');
					},1000)
				}
				i++;//继续前进
				move();
			},500);
		}
		//人物移动
		function move(){
			var human = $('.human');
			var destination = $('#prize .current');
			human.addClass('human-run');
			human.animate({
				top:destination.position().top - human.height() + destination.height()/2,
				left:destination.position().left - human.width()/2 + destination.width()/2
			},500,'linear');
		}
		var mask = phoenix.Mask.getInstance();//加蒙版
		$("#monopolyPop").append("<div id='diceMask'></div>");//加遮罩
		
		//按下大富翁骰寶按鈕執行此函數
		var monopoly_play = function(){
			$(this).unbind('click');
			$.ajax({
				url:'/Activity/monopoly',
				dataType:'json',
				cache: false,
				success:function(res){
					if(Number(res['status']) == 2)
					{
						alert("您今天已经参加过游戏，欢迎明天再来!");
						//恢復監聽點擊
						$("#monopolyBar").click(monopoly_fun);
						return false;
					}
					else if(Number(res['isSuccess']) != 1)
					{
						alert("请求失败，请稍后再试");
						//恢復監聽點擊
						$("#monopolyBar").click(monopoly_fun);
						return false;
					}
					else
					{
						$('#diceMask').show();
						$("#prize li").attr("class","");
						$("#prize li").eq(0).addClass('current');
						var dice1 = $("#dice1");
						var dice2 = $("#dice2");
						var dice3 = $("#dice3");
						var num1 = res['points'][0];
						var num2 = res['points'][1];
						var num3 = res['points'][2];
						diceroll(dice1,num1);//掷色子1动画
						diceroll(dice2,num2);//掷色子2动画
						diceroll(dice3,num3);//掷色子2动画
						var num = parseInt(num1)+parseInt(num2)+parseInt(num3);
						$("#dicePoint").text(num).delay(1000).fadeIn(500).delay(num*500+1000).fadeOut(500);
						roll(0, num);
						var newNum = num;
						if(num > 9){
							newNum = 19-num;
						}
						if(newNum == 2 || newNum == 6){
							res.prize= res.prize/2+' x 2';
						}
						$("#msg").html(res.prize);
						$("#lotteryPop .btn").attr("href",database[newNum-1][0]);
						$("#text").html(database[newNum-1][1]);
						$("#title").html(database[newNum-1][2]);
						setTimeout(function(){
							$('#lotteryPop').fadeIn(500);
						},num*500+2000);	
						$(this).unbind('click');
					}
				},
				error:function(){
					alert('请求失败，重新刷新');//请求失败，重新刷新
				}
			});
		};
		var timer = [];
		function clearInter(timeArrary){
			 do{
				clearInterval(timeArrary.shift())
			 }while (timeArrary.length);
		}
		//按下大富翁圖示執行此函數
		var monopoly_fun = function(){
			$(this).unbind('click');
			$.ajax({
				//url:'dice.php',
				url:'/Activity/monopolycheck',
				dataType:'json',
				cache: false,
				success:function(res){
					
					//获奖信息滚动
					(function(){
						//alert("获奖信息滚动");
						var oListUl = $(".lottery-notice ul");
						var dailyBigPrizeAry = res['dailyBigPrizeAry'];
						oListUl.empty();
						for(i = 0; i < dailyBigPrizeAry.length; i++){
							oListUl.append('<li>恭喜 ' + dailyBigPrizeAry[i] + ' 中的神秘大奖</li>');
						}
						
						
						oListUl.html(oListUl.html() + oListUl.html());
						var oListLi = $(".lottery-notice li");
						
						var nReplace = nTopHeight = 30;
						function roll(){
							if(nTopHeight > oListUl.outerHeight()/2){
								oListUl.css("top",0);
								nTopHeight = nReplace;
							}
							oListUl.animate({
								top:-nTopHeight
							},800);

							console.log("nTopHeight",nTopHeight);

							nTopHeight += nReplace;
						}
						timer.push(setInterval(roll,3000));

						oListUl.mouseover(function(){
							clearInter(timer);
						});
						oListUl.mouseout(function(){
							timer.push(setInterval(roll,3000));							
						});
					})();
					
					if(Number(res['status']) == 2)
					{
						alert("您今天已经参加过游戏，欢迎明天再来!");
						//恢復監聽點擊
						$("#monopolyBar").click(monopoly_fun);
						return false;
					}
					else if(Number(res['status']) == 0)	//如果已经参加过今日活动,显示之前中奖信息
					{
						var historyPrize = res['historyPrize']
						var historyPoints =  res['historyPoints'];
						if(historyPoints > 9){
							historyPoints = 19-historyPoints;
						}
						if(historyPoints == 2 || historyPoints == 6){
							historyPrize = historyPrize/2 + ' x 2';
						}
						//console.log(database);
						$('#diceMask').show();
						$('#monopolyPop').fadeIn(500);
						$('#lotteryPop').fadeIn(500);
						$("#msg").html(historyPrize);//历史中奖金额
						$("#lotteryPop .btn").attr("href",database[historyPoints-1][0]);//历史中奖信息，按钮链接地址
						$("#text").html(database[historyPoints-1][1]);//历史中奖信息，彩种文字提示
						$("#title").html(database[historyPoints-1][2]);//历史中奖信息，弹窗title
						return false;
					}
					else if(Number(res['isSuccess']) != 1)
					{
						alert("请求失败，请稍后再试");
						//恢復監聽點擊
						$("#monopolyBar").click(monopoly_fun);
						return false;
					}
					else	//玩游戏
					{
						mask.show();
						$('#monopolyPop').fadeIn(500);
						$("#monopolyStar").unbind('click');
						$("#monopolyStar").click(monopoly_play);
					}
				},
				error:function(){
					alert('请求失败，重新刷新');//请求失败，重新刷新
				}
			});
		};
		
		//大富翁弹窗
		$("#monopolyBar").click(monopoly_fun);
		$("#monopolyStar").click(monopoly_play);
		$("#monopolyPopClose").click(function(){
			clearInter(timer);
			$('#monopolyPop').fadeOut(500);
			mask.hide();
			//恢復監聽點擊
			$("#monopolyBar").click(monopoly_fun);
		});
		//获奖信息弹窗
		$("#lotteryPopClose").click(function(){
			clearInter(timer);
			$('#monopolyPop').fadeOut(500);
			mask.hide();
			//恢復監聽點擊
			$("#monopolyBar").click(monopoly_fun);
		});
		//活動連結
		$("#lotteryPopBtn").click(function(){
			$('#lotteryPop').fadeOut(500);
			$('#monopolyPop').fadeOut(500);
			mask.hide();
			//恢復監聽點擊
			$("#monopolyBar").click(monopoly_fun);
		});
		//游戏规则弹窗
		$("#monopolyRules").click(function(){
			$("#diceMask").show();//加遮罩
			$('#rulesPop').fadeIn(500);
		});
		$("#rulesPopClose").click(function(){
			$('#rulesPop').fadeOut(500);
			mask.hide();
		});
		$("#rulesPopBtn").click(function(){
			$('#rulesPop').fadeOut(500);
			$("#diceMask").hide();//移除遮罩
		});
		$('#monopolyPop .close').click(function(){
			$("#diceMask").hide();//移除遮罩
		});
		
		

	});
	
	(function(){
		var $dom = $('#winningScroll');
		var height = $dom.height();
		var allheight = $dom.find('ul').height();
		var now = 0;
		
		setInterval(function(){
			
			now++;
			
			$dom.find('ul').animate({'marginTop': -height*now},1000);
			
			if(now >= allheight/height){
				$dom.find('ul').css({'marginTop': 0}).stop();
				now = 0;
			}
		},8000);
	})()

	//monopoly - end
});
var prostyle = $('.progress-inner').attr('style');
$('.progress-inner').attr('style', 'width:100%');
setTimeout(function() {
	$('.progress-inner').attr('style', prostyle);
},
2000);

var path = global_path_url + '/images/index/';
var param = {
	imgPath: path,
	ajaxUrl: '/index/endguide'
};
$(function(){
    	var lotteryLogo = $('#lotteryList a');
	var l = lotteryLogo.length;
	var index = 0;
	
	function autoRun(){
		lotteryLogo.eq(index).addClass('current').siblings().removeClass('current');
		index++;
		if(index == l){
			index = 0;
		}
		
	};
	
	var mask = $('<div class="mask"></div>').appendTo('body');
	var superBar = $('#superBar');
                       $.ajax({
		url:'/index/super2000',
		dataType:'json',
		cache: false,
		success:function(res){
			//用户每日首次登陆后，会出现活动弹窗
			//用户领取红包,投注过超级2000的玩法
			if(Number(res['state']) == 1 || Number(res['state']) == 2){
				mask.show();
				superBar.show();

			}
			$('#superBarBtn a').click(function(){
				$('#superBarBtn').hide();
				$('#superBarSecond').fadeIn();
				setInterval(autoRun,800);
				//用户当日领取了红包并且确实投注过超级2000的玩法，投注金额≥8元
				if(Number(res['state']) == 2){
					$('#winningInfo').html('恭喜您第二次获得<strong id="winningNum">' + res['prize'] + '</strong>元');
					$('#winningNum').text(res['prizeSecond']);
				}else{
					$('#winningInfo').html('恭喜您第一次获得<strong id="winningNum">' + res['prize'] + '</strong>元');
					$('#winningNum').text(res['prizeFirst']);
				}
                                                                                           //write prize & activity log
                                                                                           $.ajax({
                                                                                               url:'/index/award?prize',
                                                                                               method:"post",
                                                                                               data:{prize:res['prize']},
                                                                                               success:function(){
                                                                                               },
                                                                                               error:function(){
                                                                                               }
                                                                                     });                
			})
		}
	});
	$('#sliderBarClose').click(function(){
                                             mask.hide();
		superBar.hide();
	})
});

//密碼修改
if($('#showPasswdInfo').val() == 1){
	$('#DivshowPasswdInfo').show();
}
$('#DivshowPasswdInfoclose').click(function() {
	$('#DivshowPasswdInfo').hide();
});	
