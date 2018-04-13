/*
 * scroll-bar 模拟滚动条插件
 *
 */
(function (factory) {
    if ( typeof define === 'function' && define.amd ) {
        // AMD. Register as an anonymous module.
        define(['jquery'], factory);
    } else if (typeof exports === 'object') {
        // Node/CommonJS style for Browserify
        module.exports = factory;
    } else {
        // Browser globals
        factory(jQuery);
    }
}(function ($) {

    var toFix  = ['wheel', 'mousewheel', 'DOMMouseScroll', 'MozMousePixelScroll'],
        toBind = ( 'onwheel' in document || document.documentMode >= 9 ) ?
                    ['wheel'] : ['mousewheel', 'DomMouseScroll', 'MozMousePixelScroll'],
        slice  = Array.prototype.slice,
        nullLowestDeltaTimeout, lowestDelta;

    if ( $.event.fixHooks ) {
        for ( var i = toFix.length; i; ) {
            $.event.fixHooks[ toFix[--i] ] = $.event.mouseHooks;
        }
    }

    var special = $.event.special.mousewheel = {
        version: '3.1.11',

        setup: function() {
            if ( this.addEventListener ) {
                for ( var i = toBind.length; i; ) {
                    this.addEventListener( toBind[--i], handler, false );
                }
            } else {
                this.onmousewheel = handler;
            }
            // Store the line height and page height for this particular element
            $.data(this, 'mousewheel-line-height', special.getLineHeight(this));
            $.data(this, 'mousewheel-page-height', special.getPageHeight(this));
        },

        teardown: function() {
            if ( this.removeEventListener ) {
                for ( var i = toBind.length; i; ) {
                    this.removeEventListener( toBind[--i], handler, false );
                }
            } else {
                this.onmousewheel = null;
            }
            // Clean up the data we added to the element
            $.removeData(this, 'mousewheel-line-height');
            $.removeData(this, 'mousewheel-page-height');
        },

        getLineHeight: function(elem) {
            var $parent = $(elem)['offsetParent' in $.fn ? 'offsetParent' : 'parent']();
            if (!$parent.length) {
                $parent = $('body');
            }
            return parseInt($parent.css('fontSize'), 10);
        },

        getPageHeight: function(elem) {
            return $(elem).height();
        },

        settings: {
            adjustOldDeltas: true, // see shouldAdjustOldDeltas() below
            normalizeOffset: true  // calls getBoundingClientRect for each event
        }
    };

    $.fn.extend({
        mousewheel: function(fn) {
            return fn ? this.bind('mousewheel', fn) : this.trigger('mousewheel');
        },

        unmousewheel: function(fn) {
            return this.unbind('mousewheel', fn);
        }
    });


    function handler(event) {
        var orgEvent   = event || window.event,
            args       = slice.call(arguments, 1),
            delta      = 0,
            deltaX     = 0,
            deltaY     = 0,
            absDelta   = 0,
            offsetX    = 0,
            offsetY    = 0;
        event = $.event.fix(orgEvent);
        event.type = 'mousewheel';

        // Old school scrollwheel delta
        if ( 'detail'      in orgEvent ) { deltaY = orgEvent.detail * -1;      }
        if ( 'wheelDelta'  in orgEvent ) { deltaY = orgEvent.wheelDelta;       }
        if ( 'wheelDeltaY' in orgEvent ) { deltaY = orgEvent.wheelDeltaY;      }
        if ( 'wheelDeltaX' in orgEvent ) { deltaX = orgEvent.wheelDeltaX * -1; }

        // Firefox < 17 horizontal scrolling related to DOMMouseScroll event
        if ( 'axis' in orgEvent && orgEvent.axis === orgEvent.HORIZONTAL_AXIS ) {
            deltaX = deltaY * -1;
            deltaY = 0;
        }

        // Set delta to be deltaY or deltaX if deltaY is 0 for backwards compatabilitiy
        delta = deltaY === 0 ? deltaX : deltaY;

        // New school wheel delta (wheel event)
        if ( 'deltaY' in orgEvent ) {
            deltaY = orgEvent.deltaY * -1;
            delta  = deltaY;
        }
        if ( 'deltaX' in orgEvent ) {
            deltaX = orgEvent.deltaX;
            if ( deltaY === 0 ) { delta  = deltaX * -1; }
        }

        // No change actually happened, no reason to go any further
        if ( deltaY === 0 && deltaX === 0 ) { return; }

        // Need to convert lines and pages to pixels if we aren't already in pixels
        // There are three delta modes:
        //   * deltaMode 0 is by pixels, nothing to do
        //   * deltaMode 1 is by lines
        //   * deltaMode 2 is by pages
        if ( orgEvent.deltaMode === 1 ) {
            var lineHeight = $.data(this, 'mousewheel-line-height');
            delta  *= lineHeight;
            deltaY *= lineHeight;
            deltaX *= lineHeight;
        } else if ( orgEvent.deltaMode === 2 ) {
            var pageHeight = $.data(this, 'mousewheel-page-height');
            delta  *= pageHeight;
            deltaY *= pageHeight;
            deltaX *= pageHeight;
        }

        // Store lowest absolute delta to normalize the delta values
        absDelta = Math.max( Math.abs(deltaY), Math.abs(deltaX) );

        if ( !lowestDelta || absDelta < lowestDelta ) {
            lowestDelta = absDelta;

            // Adjust older deltas if necessary
            if ( shouldAdjustOldDeltas(orgEvent, absDelta) ) {
                lowestDelta /= 40;
            }
        }

        // Adjust older deltas if necessary
        if ( shouldAdjustOldDeltas(orgEvent, absDelta) ) {
            // Divide all the things by 40!
            delta  /= 40;
            deltaX /= 40;
            deltaY /= 40;
        }

        // Get a whole, normalized value for the deltas
        delta  = Math[ delta  >= 1 ? 'floor' : 'ceil' ](delta  / lowestDelta);
        deltaX = Math[ deltaX >= 1 ? 'floor' : 'ceil' ](deltaX / lowestDelta);
        deltaY = Math[ deltaY >= 1 ? 'floor' : 'ceil' ](deltaY / lowestDelta);

        // Normalise offsetX and offsetY properties
        if ( special.settings.normalizeOffset && this.getBoundingClientRect ) {
            var boundingRect = this.getBoundingClientRect();
            offsetX = event.clientX - boundingRect.left;
            offsetY = event.clientY - boundingRect.top;
        }

        // Add information to the event object
        event.deltaX = deltaX;
        event.deltaY = deltaY;
        event.deltaFactor = lowestDelta;
        event.offsetX = offsetX;
        event.offsetY = offsetY;
        // Go ahead and set deltaMode to 0 since we converted to pixels
        // Although this is a little odd since we overwrite the deltaX/Y
        // properties with normalized deltas.
        event.deltaMode = 0;

        // Add event and delta to the front of the arguments
        args.unshift(event, delta, deltaX, deltaY);

        // Clearout lowestDelta after sometime to better
        // handle multiple device types that give different
        // a different lowestDelta
        // Ex: trackpad = 3 and mouse wheel = 120
        if (nullLowestDeltaTimeout) { clearTimeout(nullLowestDeltaTimeout); }
        nullLowestDeltaTimeout = setTimeout(nullLowestDelta, 200);

        return ($.event.dispatch || $.event.handle).apply(this, args);
    }

    function nullLowestDelta() {
        lowestDelta = null;
    }

    function shouldAdjustOldDeltas(orgEvent, absDelta) {
        // If this is an older event and the delta is divisable by 120,
        // then we are assuming that the browser is treating this as an
        // older mouse wheel event and that we should divide the deltas
        // by 40 to try and get a more usable deltaFactor.
        // Side note, this actually impacts the reported scroll distance
        // in older browsers and can cause scrolling to be slower than native.
        // Turn this off by setting $.event.special.mousewheel.settings.adjustOldDeltas to false.
        return special.settings.adjustOldDeltas && orgEvent.type === 'mousewheel' && absDelta % 120 === 0;
    }

}));


(function(host, name, Event, $, undefined) {


	var defConfig = {
		
	};

	var pros = {

		/*
		 *初始化插件
		 */
		init: function(setting) {
			//滚动条设置
			var _setting = setting || {};
			var _name = (_setting.name) ? _setting.name : '#J-balls-order-wrap';

			//滚动条对象
			this.scrollBar = {

				//插件支持外部传递DOM
				//默认的支持DOM均为class=scrollbar的DOM
				eDom: $(_name),

				//滚动条的方向[默认为垂直]
				//@param [apeak:垂直 | level:水平]
				direction: _setting.direction || 'apeak',

				//滚动条的宽度&&高度
				//[横向滚动时为高度] || [垂直滚动时为宽度]
				size: _setting.size || '5px'
			};

			//鼠标移动标记
			this.allowMove = false;

			//鼠标滚动标记
			this.allowWheel = true;

			//处理DOM
			this._disposalDom();
		},

		//正数
		toPositive: function(n) {
			return n < 0 ? -n : n;
		},

		//取整
		toInt: function(n) {
			return isNaN(parseInt(n)) ? 0 : parseInt(n);
		},

		/*
		 *绑定事件
		 */
		_addEvent: function() {
			var dom = this.scrollBar.eDom;
			var that = this;
			var scrollBar = dom.find('.scroll_Bar');

			//绑定滚动条事件
			dom.find('.scroll_Bar')
				.bind('mousedown', function(event) {
					var e = event || window.event;
					var t = this;

					//取消默认事件并阻止传播
					that.halt(e);

					//记录初始坐标
					if (that.scrollBar.direction == 'apeak') {
						that.noncePosition = e.clientY;
						that.scrollPosition = parseInt(scrollBar.css('top'));
					} else {
						that.noncePosition = e.clientX;
						that.scrollPosition = parseInt(scrollBar.css('left'));
					}

					$(t).addClass('scroll_Bar_hover');

					//处理鼠标点击
					that._scrollBarClick(e);

					$(document).bind('mousemove', function(e) {

						//取消默认事件并阻止传播
						that.halt(e);

						//处理鼠标移动
						that._scrollBarMove(e);
					}).one('mouseup', function(e) {
						//解绑document的mousemove
						//限制事件的存活周期
						$(document).unbind('mousemove');
						//恢复颜色
						$(t).removeClass('scroll_Bar_hover');

						//更改触发移动标记
						that.allowMove = null;

						//清空坐标信息
						that.noncePosition = null;

						//触发当前TOP记录
						that.scrollPosition = null
					})
				})

			//绑定鼠标滚轮
			// dom.bind('mousewheel', function(event) {
			// 	var e = event || window.event;

			// 	that._mouseWheel(e);
			// })

			// dom.bind('DOMMouseScroll', function(event) {
			// 	var e = event || window.event;

			// 	that._mouseWheel(e);
			// })

			dom.mousewheel(function(e){

				that._mouseWheel(e);
			});

			//绑定用户点击滚动条区域事件
			dom.find('.scroll_area').bind('click', function(event) {
				var e = event || window.event;
				var position = that.mouseCoords(e);

				that.controlBar(position, this);
			});

		},

		/**
		 * [mouseCoords description]
		 * @param  {[event]} ev [window.event对象]
		 */
		mouseCoords: function(ev) {
			if (ev.pageX || ev.pageY) {
				return {
					x: ev.pageX,
					y: ev.pageY
				};
			};
			return {
				x: ev.clientX + document.body.scrollLeft - document.body.clientLeft,
				y: ev.clientY + document.body.scrollTop - document.body.clientTop
			};
		},

		/*
		 *阻止event对象的浏览器默认时间
		 *并且阻止传播
		 */
		halt: function(e) {
			e.preventDefault();
			e.stopPropagation();
		},

		/*
		 *处理模拟滚动条的容器DOM
		 */
		_disposalDom: function() {
			var dom = this.scrollBar.eDom;
			var domInner = dom.html();
			var warpInner;

			//属性检查
			if (dom.css('overflow') != 'hidden') {
				dom.css({
					'overflow': 'hidden'
				});
			};
			if (dom.css('position') != 'relative') {
				dom.css({
					'position': 'relative'
				});
			}

			//向容器中添加定位层
			//将原对象的内容进行包裹处理
			dom.wrapInner('<div class="scroll_warp"></div>');

			warpInner = dom.find('.scroll_warp');
			warpInner.css('position', 'absolute');

			//处理滚动条
			this._disposalBar();

		},

		/**
		 *点击定位滚动条位置功能
		 * @param  {[obj]} e [当前鼠标位置 x:垂直 y:水平]
		 * @param  {[dom]} dom [需要进行定位的DOM]
		 */
		controlBar: function(e, dom) {
			var direction = (this.scrollBar.direction == 'apeak') ? 'top' : 'left',
				position = (this.scrollBar.direction == 'apeak') ? e.y : e.x,
				parentDom = (this.scrollBar.direction == 'apeak') ? $(dom).offset().top : $(dom).offset().left,
				domHeight = $(dom).height() / 100,
				num = parseInt((position - parentDom) / domHeight);

			this.scorllPosition(num + '%'); //点击定位滚动条位置
		},

		//隐藏滚动条
		scrollhide: function() {
			var dom = this.scrollBar.eDom;
			var scroll = dom.find('.scroll_warps');

			if (scroll.css('visibility') == 'visible') {
				scroll.css('visibility', 'hidden');
				this.allowWheel = false;
			}
		},

		//显示滚动条
		scrollshow: function() {
			var dom = this.scrollBar.eDom;
			var scroll = dom.find('.scroll_warps');
			var scrollBar = dom.find('.scroll_Bar');
			var direction = (this.scrollBar.direction == 'apeak') ? 'top' : 'left';

			if (scroll.css('visibility') == 'hidden') {
				scroll.css('visibility', 'visible');
				this.allowWheel = true;
				scrollBar.css(direction, '0px');
			}
		},

		/*
		 *处理模拟滚动条的容器DOM
		 */
		_checkHeight: function() {
			var dom = this.scrollBar.eDom;
			var warps = dom.find('.scroll_warp');
			var scroll = dom.find('.scroll_warps');
			var scrollBar = dom.find('.scroll_Bar');

			//如果内容层底部超过底部限制
			if ((warps.outerHeight() - dom.outerHeight()) < this.toPositive(parseInt(warps.css('top')))) {
				warps.css('top', -(warps.outerHeight() - dom.outerHeight()));
				scrollBar.css('top', dom.outerHeight() - scrollBar.outerHeight());
			}

			if (this.scrollBar.direction == 'apeak' && warps.outerHeight() <= dom.outerHeight()) { //如果内容高度小于
				this.scrollhide();
				warps.css('top', '0px');
				return true;
			} else if (this.scrollBar.direction == 'level' && warps.outerWidth() <= dom.outerWidth()) { //如果宽度高度小于	
				this.scrollhide();
				warps.css('left', '0px');
				return true;
			}

			this.scrollshow();
			return false;
		},

		/*
		 *处理滚动条相关
		 */
		_disposalBar: function() {
			var dom = this.scrollBar.eDom;
			var size = this.scrollBar.size;
			var scrollBar = $('<div class="scroll_warps"><div class="scroll_area"><div class="scroll_Bar"></div></div></div>');
			var warps, area, bar;

			//给容器添加滚动条
			dom.append(scrollBar);
			warps = dom.find('.scroll_warps');
			area = dom.find('.scroll_area');
			bar = dom.find('.scroll_Bar');

			//根据方向给滚动条附加相应样式
			if (this.scrollBar.direction == 'apeak') {
				warps.css({
					'position': 'absolute',
					'width': size,
					'height': dom.outerHeight(),
					'right': '5px',
					'top': '0px',
					'visibility': 'visible'
				});
				area.css({
					'position': 'relative',
					'width': '100%',
					'height': dom.outerHeight()
				});
				bar.css({
					'position': 'absolute',
					'width': size,
					'min-height': '20px',
					'top': '0px',
					'cursor': 'pointer'
				});
			} else {
				warps.css({
					'position': 'absolute',
					'width': dom.outerWidth(),
					'height': size,
					'bottom': '5px',
					'left': '0px',
					'visibility': 'visible'
				});
				area.css({
					'position': 'relative',
					'height': '100%',
					'width': dom.outerHeight()
				});
				bar.css({
					'position': 'absolute',
					'height': size,
					'min-width': '20px',
					'left': '0px',
					'cursor': 'pointer'
				});
			};

			//处理滚动条[高度][宽度]
			this.resizeHeight();

			//绑定滚动条触发事件
			this._addEvent();
		},

		/*
		 *处理滚动条相关
		 */
		scorllPosition: function(setting) {
			var dom = this.scrollBar.eDom;
			var eScrollSize = dom.find('.scroll_Bar');
			var eScrollwrap = dom.find('.scroll_warps');
			var allowSize = eScrollwrap.height() - eScrollSize.height();
			var scorllNum;

			//如果是数值
			if (typeof(setting) == 'number') {
				scorllNum = setting;
				//百分比形式
			} else if (typeof(setting) == 'string') {
				//如果是百分比形式
				//或者为纯数字的STRING类型
				if (setting.indexOf('%') != -1 || !! setting.match(/^\d+$/g)) {
					scorllNum = setting;
					//如果是单独查找DOM形式
				} else {
					//如果当前dom有多个
					if ($(setting).size() > 1) {
						scorllNum = $($(setting)[0]).position().top;
					} else {
						scorllNum = $(setting).position().top;
					};
				};
				//dom查找形式
			} else if (typeof(setting) == 'object' && setting instanceof Array) {
				var domName = dom.find(setting[0]),
					domSub = setting[1];

				//如果不符合条件
				if (domName.size() == 0 || typeof(domSub) != 'number') {
					return
				}
				scorllNum = $(domName[domSub]).position().top;
			};
			//判断参数
			if (typeof(scorllNum) == 'number') {
				scorllNum = scorllNum / dom.outerHeight() * eScrollwrap.outerHeight();
			} else {
				scorllNum = parseInt(scorllNum) / 100 * eScrollwrap.outerHeight() - eScrollSize.outerHeight() / 2;
			};
			//如果超出总高度则为置底
			if (scorllNum > allowSize) {
				scorllNum = allowSize;
			} else if (scorllNum < 0) {
				scorllNum = 0;
			}
			//调整滚动条高度
			this._processScroll(eScrollSize, scorllNum);
		},

		/**
		 * [resizeHeight description]
		 * @param  {[obj]} setting.scorllNum [需要初始化滚动条的位置]
		 */
		resizeHeight: function() {
			var dom = this.scrollBar.eDom;
			var eWarpSize = dom.find('.scroll_warp');
			var scrollBar = dom.find('.scroll_Bar');
			var scrollArea = dom.find('.scroll_warps');
			var domSize;


			if (this._checkHeight()) {
				return;
			};

			//判断是否显示滚动条
			if (this.scrollBar.direction == 'apeak') { //计算出滚动条的[高度]
				domSize = dom.height();
				scrollBar.css('height', domSize / eWarpSize.outerHeight() * 100 + '%');
				scrollArea.css('height', dom.outerHeight())
			} else { //计算出滚动条的[宽度]
				domSize = dom.width();
				scrollBar.css('width', domSize / eWarpSize.outerWidth() * 100 + '%');
				scrollArea.css('width', dom.outerHeight())
			};
			this._innerPosition(); //判断内容层DOM定位
		},

		/*
		 *高度变化内容层的定位
		 *解决内容层底部高于容器底部
		 */
		_innerPosition: function() {
			var dom = this.scrollBar.eDom;
			var eWarpSize = dom.find('.scroll_warp');
			var eScrollSize = dom.find('.scroll_Bar');

			if (this.scrollBar.direction == 'apeak') {
				if (eWarpSize.outerHeight() - dom.outerHeight() <= this.toPositive(parseInt(eWarpSize.css('top')))) {
					eWarpSize.css('top', -(eWarpSize.outerHeight() - dom.outerHeight()));
					eScrollSize.css('top', dom.outerHeight() - eScrollSize.outerHeight());
				};
			} else {
				if (eWarpSize.outerWidth() - dom.outerWidth() <= this.toPositive(parseInt(eWarpSize.css('top')))) {
					eWarpSize.css('left', -(eWarpSize.outerWidth() - dom.outerWidth()));
					eScrollSize.css('left', dom.outerWidth() - eScrollSize.outerWidth());
				};
			};
		},

		/*
		 *处理鼠标点击
		 */
		_scrollBarClick: function(e) {
			this.allowMove = true;
		},

		/*
		 *设定滚动条位置
		 */
		_processScroll: function(dom, num) {
			var direction = (this.scrollBar.direction == 'apeak') ? 'top' : 'left';

			dom.css(direction, num);
			this._eWarpMove(); //同步内容层的移动
		},

		/*
		 *处理鼠标移动
		 */
		_scrollBarMove: function(e, num) {
			var dom = this.scrollBar.eDom;
			var eWarpSize = dom.find('.scroll_warp');
			var eScrollSize = dom.find('.scroll_Bar');
			var warpSize, scrollSize, size, scrollSize;

			if (!this.allowMove) return; //判断是否点击触发
			if (this._confine(e)) return; //判断是否超出边线
			scrollSize = (this.scrollBar.direction == 'apeak') ? e.clientY - this.noncePosition : e.clientX - this.noncePosition; //计算当前鼠标的偏移量
			this._processScroll(eScrollSize, this.scrollPosition + scrollSize); //设置滚动条位置
		},

		/*
		 *判断是否超出边界
		 */
		_confine: function(e, type) {
			var dom = this.scrollBar.eDom;
			var eWarpSize = dom.find('.scroll_warp');
			var eScrollSize = dom.find('.scroll_Bar');
			var scrollSize = (this.scrollBar.direction == 'apeak') ? e.clientY - this.noncePosition : e.clientX - this.noncePosition;
			var direction = (this.scrollBar.direction == 'apeak') ? 'top' : 'left';

			if (type == 'mousewheel') {
				scrollSize = parseInt(eScrollSize.css(direction));
			};

			if (this.scrollBar.direction == 'apeak') {
				//顶部超出
				if (this.scrollPosition + scrollSize <= 0) {
					eWarpSize.css('top', '0px');
					eScrollSize.css('top', '0px');
					return true;
				};
				//底部超出
				if (this.scrollPosition + scrollSize > dom.outerHeight() - eScrollSize.outerHeight()) {
					eWarpSize.css('top', -(eWarpSize.outerHeight() - dom.outerHeight()));
					eScrollSize.css('top', dom.outerHeight() - eScrollSize.outerHeight());
					return true;
				};
			} else {
				//左部超出
				if (this.scrollPosition + scrollSize <= 0) {
					eWarpSize.css('left', '0px');
					eScrollSize.css('left', '0px');
					return true;
				};
				//右部超出
				if (this.scrollPosition + scrollSize > dom.outerWidth() - eScrollSize.outerWidth()) {
					eWarpSize.css('left', -(eWarpSize.outerWidth() - dom.outerWidth()));
					eScrollSize.css('left', dom.outerWidth() - eScrollSize.outerWidth());
					return true;
				};
			};
			return false;
		},

		/*
		 *处理鼠标移动
		 */
		_eWarpMove: function() {
			var dom = this.scrollBar.eDom;
			var eWarpSize = dom.find('.scroll_warp');
			var eScrollSize = dom.find('.scroll_Bar');
			var size = (this.scrollBar.direction == 'apeak') ? (eWarpSize.outerHeight() - dom.outerHeight()) / (dom.outerHeight() - eScrollSize.outerHeight()) : (eWarpSize.outerWidth() - dom.outerWidth()) / (dom.outerWidth() - eScrollSize.outerWidth());
			var direction = (this.scrollBar.direction == 'apeak') ? 'top' : 'left';
			var num = this.toInt(eScrollSize.css(direction));

			eWarpSize.css(direction, -(size * num)); //移动内容层
		},

		/*
		 *处理鼠标滚轮滚动
		 */
		_mouseWheel: function(e) {
			var delta = (this.scrollBar.direction == 'apeak') ? e.deltaY * 10 : e.deltaX * 10; //e.detail ? e.detail / -3 * 10 : e.wheelDelta / 120 * 10;
			var dom = this.scrollBar.eDom;
			var eScrollSize = dom.find('.scroll_Bar');
			var direction = (this.scrollBar.direction == 'apeak') ? 'top' : 'left';

			//判断是否点击触发
			if (!this.allowWheel) {
				return;
			} else {
				//阻止默认事件
				this.halt(e);
			};
			this.scrollPosition = -delta;
			//判断是否超出边线
			if (this._confine(e, 'mousewheel')) return;
			//滚动条移动
			eScrollSize.css(direction, parseInt(eScrollSize.css(direction)) + (-delta));
			//同步内容层的移动
			this._eWarpMove();
		}
	};

	var Main = host.Class(pros, Event);
	Main.defConfig = defConfig;
	host[name] = Main;

})(phoenix, "SimScroll", phoenix.Event, jQuery);