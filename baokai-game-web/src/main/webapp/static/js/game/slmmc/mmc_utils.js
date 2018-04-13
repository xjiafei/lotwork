;
(function($) {
    function F(t, o) {
        this.opts = $.extend({
            ballsize: 5, // 彩球个数
            initball: '0,0,0,0,0', // 初始化彩球数据
            loop: 5, // 彩球滚动循环次数（必须为整数）
            timeout: 5000, // 彩球滚动动画执行时间基数
            delay: 150, // 每个彩球动画执行延迟时间基数
            offset: [80, 110], // 球的宽高
            handbar: '.handle_hand', // 拉杆元素
            lamp: '.lamp', // 跑马灯元素
            debugs: true // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
        }, o);
        this.slides = [];
        this.size = this.opts.ballsize;
        this.$t = $(t);
        this.balls = [];
        // CALLBACK
        this.callback = function(){'sss'};
        this.errors = {
            'invalidBallFormat': '彩球数据格式错误'
        };
        this.debugs = this.opts.debugs;
        this.init();
    }

    F.prototype = {
        init: function() {
            // alert('fdadffd');
            var me = this,
                opts = me.opts;
            if (me.checkballs(opts.initball) != me.size) {
                alert(me.errors['invalidBallFormat']);
            }
            me.$handles = $(opts.handbar).children();
            me.createdom();
            // me.flip(me.balls, false);
            me.preloadLightImg();
        },
        checkballs: function(balls) {
            var me = this,
                k = 0;
            if( balls && typeof balls === 'string' ){
                balls = balls.split(',');
            }
            // balls存在、为数组，且其长度为size
            if (balls &&
                Object.prototype.toString.call(balls) === '[object Array]' &&
                balls.length == me.size
            ) {
                me.balls = balls;
                for (var i = 0; i < balls.length; i++) {
                    var ball = Number(balls[i]);
                    if (ball < 0 || ball > 11) {
                        break;
                    }
                    k++;
                }
            }
            // me.debug(k);
            return k;
        },
        createdom: function() {
            var me = this,
                opts = me.opts,
                balls = me.balls;
            for (var i = 0; i < me.size; i++) {
                var _style = 'position:absolute;top:0;left:' + i*me.opts.offset[0] + 'px;float:none;';
                    /* ball_number*ball_height*(ball_max_loop+3) */
                    _style += 'height:' + 13 * opts.offset[1] * (opts.loop + 3) + 'px';
                me.slides.push(
                    $('<div>', {
                        'class': 'flipball flipball_' + (i + 1),
                        'style' : _style,
                        text: balls[i]
                    }).appendTo(me.$t)
                );
            }
        },
        preloadLightImg: function() {
            var me = this,
                $img = $('img', this.opts.lamp),
                src = $img.data('imgholder');
            $('<img/>').load(function() {
                me.$lampimg = $img;
                me.originsrc = $img.attr('src');
                me.lampsrc = src;
            }).attr('src', src);
        },
        // 跑马灯效果
        marquee: function(status) {
            if (this.lampsrc && this.$lampimg.length) {
                if (status == 'on') {
                    this.$lampimg.attr('src', this.lampsrc);
                } else if (status == 'off') {
                    this.$lampimg.attr('src', this.originsrc);
                }
            }
        },
        // 拉杆动画效果
        drawbar: function(callback) {
            this.$handles.eq(0).animate({
                opacity: 'hide'
            }, 300, function() {
                $(this).animate({
                    opacity: 'show'
                }, 300, function(){
                    callback && callback();
                });
            });
        },
        play: function() {
            this.marquee('on');
            this.drawbar();
        },
        stop: function() {
            this.marquee('off');
        },        
        // 数字球滚动效果
        flip: function(balls, anim, callback) {
            var me = this,
                opts = me.opts,
                balls = balls || me.balls,
                callback = callback || me.callback;
            if (me.checkballs(balls) != me.size) {
                return alert(me.errors['invalidBallFormat']);
            }
            if( !me.$t.hasClass('.hasball') ) me.$t.addClass('hasball');
            balls = me.balls;
            me.callback = callback;
            if (anim === false || anim === 'undefined') {
                me.stop();
                $.each(me.slides, function(idx, slide) {
                    var ball_num = Number(balls[idx]);
                    slide.stop().css('marginTop', -(13 + ball_num) * opts.offset[1]);
                });
                me.doCallback(me.callback);
            } else {
                me.play();
                $.each(me.slides, function(idx, slide) {
                    var ball_num = Number(balls[idx]),
                        timeout = opts.timeout + opts.delay * idx,
                        // 一圈是12个数，循环opts.loop圈后，在移动ball_num单位个高度(opts.offset[1])
                        step = (opts.loop * 12 + ball_num) * opts.offset[1];
                    slide.stop().animate({
                        marginTop: '+=' + (opts.offset[1] * .6)
                    }).stop().animate({
                        marginTop: -step
                    }, timeout, function() {
                        $(this).css('marginTop', -((12 + ball_num) * opts.offset[1]+80));                        
                        if( idx == me.size - 1 ){
                            me.stop();
                            me.doCallback(me.callback);
                        }
                    });
                });
            }
        },
        quickflip: function(callback1){
            var me = this,
                opts = me.opts,
                balls = balls || me.balls,
                callback = callback || me.callback;

            me.callback = function(){};

            if (me.checkballs(balls) != me.size) {
                return alert(me.errors['invalidBallFormat']);
            }
            $.each(me.slides, function(idx, slide) {
                var ball_num = Number(balls[idx]);
                slide.stop();
                slide.css({
                    marginTop: -(12 + ball_num) * opts.offset[1]
                }).animate({
                    marginTop: -(12 + ball_num + 12) * opts.offset[1]
                }, 1000, function(){
                    
                    if( idx == me.size - 1 ){
                        me.doCallback(callback);          
                        callback1 && callback1();
                    }
                });
            });


            
        },
        doCallback: function( callback ){
            if( callback && Object.prototype.toString.call(callback) === '[object Function]' ){
                callback();
            }
        },
        // debug
        debug: function() {
            this.debugs && window.console && console.log && console.log('[flipball] ' + Array.prototype.join.call(arguments, ' '));
        }
    }

    $.fn.flipball = function(o) {
        var instance;
        this.each(function() {
            instance = $.data(this, 'flipball');
            // instance = $(this).data( 'flipball' );
            if (instance) {
                // instance.init();
            } else {
                instance = $.data(this, 'flipball', new F(this, o));
            }
        });
        return instance;
    }
})(jQuery);

;
(function($) {
    function F(t, o) {
        var data = $(t).data();
        for (p in data) {
            if (data.hasOwnProperty(p) && /^counter[A-Z]+/.test(p)) {
                var val = data[p];
                var shortName = p.match(/^counter(.*)/)[1].replace(/^[A-Z]/, this.lowerCase);
                // console.log(shortName + ':', val, '(' + typeof val + ')');
                data[shortName] = val;
            }
        }
        this.opts = $.extend({
            max: 12,
            min: 1,
            step: 1,
            init: 1,
            debugs: true // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
        }, data, o);
        // console.log(this.opts)
        this.slides = [];
        this.$t = $(t);
        this.eventSave = function(){};
        this.beforeSetValue = function(){};
        this.debugs = this.opts.debugs;
        this.init();
    }

    F.prototype = {
        init: function() {
            var me = this;
            me.$ctrl = me.$t.find('[data-counter-action]');
            me.$input = me.$t.find('input.J_counter');
            //me.$valueInput = me.$t.siblings('[data-valueInput]');
            if (!me.$input.length) return me.debug('No input element!');
            me.setMaxValue(me.opts.max);
            me.setMinValue(me.opts.min);
            me.checkCtrl();
            me.bindEvent();
            me.setValue(me.opts.init);
        },
        lowerCase: function(s) {
            return (s || '').toLowerCase();
        },
        setBindEvent: function(fn){
            var me = this;
            me.eventSave = fn;
        },
        setBeforeSetValue: function(fn){
            var me = this;
            me.beforeSetValue = fn;
        },
        setMinValue: function( num ){
            this.minValue = num;
        },
        getMinValue: function(){
            return this.minValue;
        },
        setMaxValue: function( num ){
            this.maxValue = num;
        },
        getMaxValue: function(){
            return this.maxValue;
        },
        setValue: function(val) {
            var me = this,
                dom = me.$input,
                data = {val: me.checkCtrl(val)};
                
            me.beforeSetValue(data);
            dom.val(data['val']);
            me.eventSave(data['val']);
        },
        getValue: function() {
            return this.$input.val();
        },
        setMaxBtnStatus: function(status){
            if( status == 'disabled' ){
                this.$ctrl.filter('[data-counter-action="increase"]').addClass('disabled');
            }else if( status == 'enabled' ){
                this.$ctrl.filter('[data-counter-action="increase"]').removeClass('disabled');
            }
        },
        checkCtrl: function(val) {
            var me = this,
                opts = me.opts;
            val = val || opts.init;
            // me.debug(val);
           /* if (val <= me.getMinValue()) {
                me.$ctrl.filter('[data-counter-action="decrease"]').addClass('disabled');
                val = me.getMinValue();
            } else {
                me.$ctrl.filter('[data-counter-action="decrease"]').removeClass('disabled');
            }
            if (val >= me.getMaxValue()) {
                me.$ctrl.filter('[data-counter-action="increase"]').addClass('disabled');
                val = me.getMaxValue();
            } else {
                me.$ctrl.filter('[data-counter-action="increase"]').removeClass('disabled');
            }*/
            return val;
        },
        reset: function(){
            var me = this;
            me.$input.val(me.opts.init);
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
        }
        // debug
        ,
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

;
(function($) {
    function F(t, o) {
        this.opts = $.extend({
            initNum: 1,
            debugs: true // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
        }, o);
        this.slides = [];

        this.$t = $(t);
        this.debugs = this.opts.debugs;
        // this.isIE6 = $.browser.msie && parseFloat($.browser.version) < 7;
        this.init();
    }

    F.prototype = {
        init: function() {
            var $options = this.$t.find('option');
            // console.log($options)
            if (!$options.length) return this.debug('Not a select element!');
            // console.log($options)
            this.markup($options);
            this.setValue(this.opts.initNum);
        },
        markup: function($options) {
            var me = this,
                $switchers = $(''),
                $html = $('<div class="choose-model ui-simulation-switcher"/>');
            $options.each(function(idx) {
                $switchers = $switchers.add(
                    '<span class="switcher-' + (idx + 1) + '"' +
                    'data-value="' + this.value + '" data-name="' + $(this).text() + '"' +
                    '>' + $(this).text() + '</span>');
            });
            me.$t.before($html.append($switchers)).hide(0);
            me.$switchers = $switchers;
            $switchers.on('click', function() {
                var $this = $(this);
                if( $this.hasClass('onhandled') ) return false;
                $switchers.addClass('onhandled');
                me.setValue(me.$switchers.not(this).data('value'), function(){
                    $switchers.removeClass('onhandled');
                });
            });
        },
        setValue: function(val, callback) {
            var me = this,
                $s = me.$switchers.filter('[data-value="' + val + '"]');

            me.$switchers.not($s).filter(':visible').animate({
                opacity: 'hide'
            });
            $s.filter(':hidden').animate({
                opacity: 'show'
            }, function(){
                if( callback && Object.prototype.toString.call(callback) === '[object Function]' ){
                    callback();
                }
            });

            me.$t.val(val).trigger('switcher.change', val);            
        },
        getValue: function() {
            return this.$t.val();
        }
        // debug
        ,
        debug: function() {
            this.debugs && window.console && console.log && console.log('[switcher] ' + Array.prototype.join.call(arguments, ' '));
        }
    }

    $.fn.switcher = function(o) {
        var instance;

        this.each(function() {
            instance = $.data(this, 'switcher');
            // instance = $(this).data( 'switcher' );
            if (instance) {
                // instance.init();         
            } else {
                instance = $.data(this, 'switcher', new F(this, o));
            }
        });

        return instance;
    }

    //$('[data-simulation="switcher"]').switcher();

})(jQuery);

;(function($) {
    function F(t, o) {
        this.opts = $.extend({
            showNum: 5,
            maxNum: 15,
            wrap: '[data-records="wrap"]',
            noneString: '-', // 没有数据占位符（IE6下使用）
            debugs: false // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
        }, o);
        this.slides = [];
        this.idx = 0;
        this.$t = $(t);
        this.debugs = this.opts.debugs;
        // this.isIE6 = $.browser.msie && parseFloat($.browser.version) < 7;
        this.init();
    }

    F.prototype = {
        init: function() {
            var opts = this.opts;
            this.$wrap = $(opts.wrap, this.$t);
            this.$prev = $('.prev', this.$t);
            this.$next = $('.next', this.$t);
            this.update();
            this.initData();
            this.checkCtrl();
            this.bindEvent();
        },
        initData: function() {
            var me = this,
                opts = me.opts,
                k = opts.showNum - me.len;
            if (k > 0) {
                // console.log(k)
                for (var i = 0; i < k; i++) {
                    // console.log(i)
                    me.append([-1, -1, -1, -1, -1], true, true);
                }
            }
            // me.isAppend = false;
        },
        setAppendStatus: function(flag){
            return this.isAppend = flag;
        },
        update: function(){            
            this.$items = this.$wrap.children();
            this.len = this.$items.not('.blankRecord').length;
            this.step = this.$items.eq(0).outerHeight();
            this.slideMaxNum = this.len - this.opts.showNum;
            this.$wrap.height(this.len * this.step);
        },
        checkCtrl: function() {
            var me = this,
                idx = me.idx,
                opts = me.opts;
            if (me.len > opts.maxNum) {
                me.len = opts.maxNum;
            }
            if (me.len <= opts.showNum) {
                me.$prev.add(me.$next).addClass('disabled');
            } else {
                if (idx < 1) {
                    me.$prev.addClass('disabled');
                } else {
                    me.$prev.removeClass('disabled');
                }

                if (idx >= me.slideMaxNum) {
                    me.$next.addClass('disabled');
                } else {
                    me.$next.removeClass('disabled');
                }
            }
        },
        bindEvent: function() {
            var me = this;
            me.$prev.on('click', function() {
                if ($(this).hasClass('disabled'))
                    return false;
                me.slide('prev');
            });
            me.$next.on('click', function() {
                if ($(this).hasClass('disabled'))
                    return false;
                me.slide('next');
            });
            me.$wrap.on('slide.append', function(event, html) {
                // console.log(html);
                // console.log(me.isAppend)
                if( me.isAppend ){
                    $(this).append(html);
                    // me.setAppendStatus(false);
                }else{
                    $(this).prepend(html);
                }                
                me.update();
                me.$items.filter(function(idx) {
                    return idx >= me.opts.maxNum;
                }).remove();
                me.checkCtrl();
            });
        },
        slide: function(dir) {
            var me = this,
                idx = me.idx;
            if (dir == 'prev') idx--;
            else idx++;
            me.goto(idx);
        },
        goto: function(idx, html) {
            var me = this;
            if (!idx || idx < 0) idx = 0;
            else if (idx > me.slideMaxNum) idx = me.slideMaxNum;
            me.$wrap.animate({
                marginTop: -me.step * idx
            }, function() {
                me.idx = idx;
                me.checkCtrl();
                if (html) {
                    $(this).trigger('slide.append', html);
                }
            });
        },
        // blankRecord: true表示是占位用的空数据
        append: function(balls, blankRecord, isAppend) {
            var me = this;
            me.isAppend = isAppend || false;
            if (!balls || Object.prototype.toString.call(balls) != '[object Array]') {
                return alert('开奖记录数据格式有误');
            }
            me.goto(0, me.getNewRecord(balls, blankRecord));
        },
        getNewRecord: function(balls, blankRecord) {
            var me = this,
                html = '',
                defClass = blankRecord ? ' class="blankRecord"' : '';
            $.each(balls, function(i, ball) {
                if (ball < 0) {
                    html += '<span class="num_none">' + me.opts.noneString + '</span>';
                } else {
                    html += '<span class="num_' + ball + '">' + ball + '</span>';
                }
            });
            return '<li' + defClass + '>' + html + '</li>';
        }
        // debug
        ,
        debug: function() {
            this.debugs && window.console && console.log && console.log('[records] ' + Array.prototype.join.call(arguments, ' '));
        }
    }

    $.fn.records = function(o) {
        var instance;
        this.each(function() {
            instance = $.data(this, 'records');
            // instance = $(this).data( 'records' );
            if (instance) {
                // instance.init();         
            } else {
                instance = $.data(this, 'records', new F(this, o));
            }
        });
        return instance;
    }
    // $('[data-simulation="records"]').records();
})(jQuery);

;(function($) {
    function F(t, o) {
        this.opts = $.extend({
            showNum: 10, // 记录显示条数
            ballurl: 'javascript:void(0);', // 号码详情链接
            markup: '<li> \
                        <div class="cell1">{{projectId}}</div> \
                        <div class="cell2">{{writeTime}}</div> \
                        <div class="cell3"> \
                            <a href="javascript:void(0);" data-link>投注详情</a> \
                        </div> \
                        <div class="cell4"> \
                            <span class="price"><dfn>¥</dfn>{{totalprice}}</span> \
                        </div> \
                        <div class="cell5" data-ballNum>-,-,-,-,-</div> \
                        <div class="cell6" data-result>开奖中...</div> \
                    </li>',
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
            var me = this, opts = me.opts, markup = opts.markup;
            for (p in data) {
                if (data.hasOwnProperty(p)) {
                    markup = me.template(markup, p, (p=='totalprice'||p=='winMoney'?phoenix.util.formatMoney(data[p]/10000):data[p]));
                }
            }
            return markup;
        },
        template: function(markup, key, value, reg){
            reg = reg || RegExp('{{' + key + '}}', 'g');
            return markup.replace(reg, value);
        },
        append: function(data) {
            var me = this;
            // 是否判断data的合法性？
            me.data = data;
            me.$render = $(me.render(data)).prependTo(me.$t);
            if( me.getLength() > me.opts.showNum ){
                me.$items.filter(function(idx){
                    return idx >= me.opts.showNum;
                }).remove();
            }
        },
        update: function(){
            var me = this, data = me.data;
            if( data['result'] ){
                me.$render.find('[data-ballNum]').text(data['result']);
            }
            if( data['winNum'] != 'undefined' && data['winMoney'] != 'undefined' && Number(data['winNum']) > 0 ){
                me.$render.find('[data-result]').html('<span class="price"><dfn>¥</dfn>' + phoenix.util.formatMoney(data['winMoney']/10000) + '</span>');
            }else{
                me.$render.find('[data-result]').text('未中奖');
            }
            if( me.opts.ballurl && data['projectId'] ){
                me.$render.find('[data-link]')
                    .attr('href', me.opts.ballurl + '?orderCode=' + data['projectId'])
                    .attr('target', '_blank');
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