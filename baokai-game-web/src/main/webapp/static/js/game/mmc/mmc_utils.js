/**
 * Created by wanghao on 2017/10/13.
 */
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
                    if (ball < 0 || ball > 9) {
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
                _style += 'height:' + 10 * opts.offset[1] * (opts.loop + 3) + 'px';
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

            //richardgong 2016-3-17
            $.each(me.slides,function (idx, slide){
                slide.removeClass('isdiamondflipball');
            });
            var flipball =$('#flipball');
            flipball.removeClass('hasball2');

            var isHasSameWithFirst;

            var sameball = [];

            if(window.hasdiamondSubmit){
                $('.flipball_mask').addClass('flipball_mask2');
                $('#flipball .flipball').eq(0).addClass('isdiamondflipball');
                isHasSameWithFirst = balls.indexOf(balls[0]) != balls.lastIndexOf(balls[0]);
                isHasSameWithFirst && sameball.push(me.slides[0]);
                flipball.addClass('hasball2');
                $('.ball_number_area_border').show();
            } else {
                $('.flipball_mask').removeClass('flipball_mask2');
                $('.ball_number_area_border').hide();
            }

            if (anim === false || anim === 'undefined') {
                me.stop();
                $.each(me.slides, function(idx, slide) {
                    var ball_num = Number(balls[idx]);
                    slide.stop().css('marginTop', -(10 + ball_num) * opts.offset[1]);
                });
                me.doCallback(me.callback);
            } else {
                me.play();
                $.each(me.slides, function(idx, slide) {
                    var ball_num = Number(balls[idx]),
                        timeout = opts.timeout + opts.delay * idx,
                        // 一圈是10个数，循环opts.loop圈后，在移动ball_num单位个高度(opts.offset[1])
                        step = (opts.loop * 10 + ball_num) * opts.offset[1];

                    //richardgong 2016-3-17
                    if(isHasSameWithFirst){
                        balls[idx]==balls[0] && sameball.push(slide);
                    }

                    slide.stop().animate({
                        marginTop: '+=' + (opts.offset[1] * .6)
                    }).stop().animate({
                        marginTop: -step
                    }, timeout, function() {
                        $(this).css('marginTop', -(10 + ball_num) * opts.offset[1]);
                        if( idx == me.size - 1 ){
                            me.stop();

                            var cb = function (){

                                $.each(sameball,function (){
                                    $(this).addClass('isdiamondflipball');
                                });

                                setTimeout(function (){
                                    me.callback();
                                },1200);
                            };
                            me.doCallback(cb);

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
            //快速开奖后立即重置掉当前的CALLBACK缓存
            //防止正常开奖逻辑再次执行回调
            me.callback = null;
            if (me.checkballs(balls) != me.size) {
                return alert(me.errors['invalidBallFormat']);
            }
            $.each(me.slides, function(idx, slide) {
                var ball_num = Number(balls[idx]);
                slide.stop().css({
                    marginTop: -(10 + ball_num) * opts.offset[1]
                }).animate({
                    marginTop: -(10 + ball_num + 10) * opts.offset[1]
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
            max: 10,
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

                $html = $('<div class="choose-model ui-simulation-switcher" style="color: black;border-radius: 4px;background-color: rgba(254,241,158,1);line-height: 29px;height: 29px;text-align: center;"><span class="current-allen-model" style="text-indent: 0em;border-radius: 4px;box-sizing: border-box;text-align: left;padding-left: 10px;">元</span></div>');
            $options.each(function(idx) {
                var alenElm = null;
                if(idx == 0){
                    alenElm = '<span style=" line-height: 27px;text-align: center;top: 30px;background: none;color: black;text-indent: 0em;border-top-left-radius: 4px;border-top-right-radius: 4px;"  class="slider switcher-allen-' + (idx + 1) + '"' +
                        'data-value="' + this.value + '" data-name="' + $(this).text() + '"' +
                        '>' + $(this).text() + '</span>';
                }else if(idx == 1){
                    alenElm = '<span style="top: 57px;line-height: 27px;text-align: center;display: block;text-indent: 0em;color: black;border-top: 1px solid #ddcb73;"  class="slider switcher-allen-' + (idx + 1) + '"' +
                        'data-value="' + this.value + '" data-name="' + $(this).text() + '"' +
                        '>' + $(this).text() + '</span>';

                }else if(idx == 2){
                    alenElm = '<span style="line-height: 27px;text-indent: 0;text-align: center;top: 87px;color: black;border-top: 1px solid #ddcb73;border-bottom-left-radius: 4px;border-bottom-right-radius: 4px;"  class="slider switcher-allen-' + (idx + 1) + '"' +
                        'data-value="' + this.value + '" data-name="' + $(this).text() + '"' +
                        '>' + $(this).text() + '</span>';
                }

                $switchers = $switchers.add(alenElm
                    );
            });
            me.$t.before($html.append($switchers)).hide(0);
            me.$switchers = $switchers;
            $switchers.hide();
            $switchers.on('click', function() {


                $switchers.hide();
                var $this = $(this);
                if($this.hasClass("switcher-allen-1")){
                    $('.current-allen-model').html("元");
                }else if($this.hasClass("switcher-allen-2")){
                    $('.current-allen-model').html("角");
                }else if($this.hasClass("switcher-allen-3")){
                    $('.current-allen-model').html("分");
                }
             //   if( $this.hasClass('onhandled') ) return false;
                $switchers.addClass('onhandled');
                me.setValue($this.data('value'), function(){
                    $switchers.removeClass('onhandled');
                });
            });

            $('.current-allen-model').on('click',function (event) {

                $switchers.show();
                event.stopPropagation();
            });

            $(document).on('click',function () {
                $switchers.hide();
            });
        },
        setValue: function(val, callback) {
            var me = this,
                $s = me.$switchers.filter('[data-value="' + val + '"]');

            // me.$switchers.not($s).filter(':visible').animate({
            //     opacity: 'hide'
            // });
            // $s.filter(':hidden').animate({
            //     opacity: 'show'
            // }, function(){
            //     if( callback && Object.prototype.toString.call(callback) === '[object Function]' ){
            //         callback();
            //     }
            // });

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
            showNum: 0,
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

                if (idx >= me.slideMaxNum-5) {
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
                html = '';

            //richardgong 2016-3-17
            //test data.....
            //balls  = [1,1,3,2,2];

            var havediamond = balls.indexOf(balls[0]) != balls.lastIndexOf(balls[0]);

            var num = 0;
            if(havediamond){
                for (var i = 1; i < balls.length; i++) {
                    if(balls[0] == balls[i]) num++;
                }
            }

            html += '<span class="diamond-icon"></span><span class="diamond-grade'+num+'"></span>';
            $.each(balls, function(i, ball) {
                if (ball < 0) {
                    html += '<span class="num_none">' + me.opts.noneString + '</span>';
                } else {
                    var ck = '';
                    if(havediamond){
                        if(balls[0] == balls[i]) ck = " diamondnum";
                    }
                    html += '<span class="num_' + ball + ck+'">' + ball + '</span>';
                }
            });

            var ck = havediamond? " hassomediamond":"";
            var ck2 = havediamond?  ' class="hassomediamond"':' class="hassomediamond"';
            defClass = blankRecord ? ' class="blankRecord '+ck+'"' : ck2;

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


(function($) {
    function F(t, o) {
        this.opts = $.extend({
            infoLink: '/mmcRank/mmcRankingInfo',
            getConfig: '/mmcRank/queryRanking',
            getDay: '/mmcRank/queryHistory',
            updateTime: 300,
            boardData: {},
            //body html模板
            tpl_body:

            '<div class="diamondsBoard">'+
            '<div class="boardBar">'+
            '<a class="boardInstruction" target="_blank" href="{{link}}"></a>'+
            '<a class="boardOpen" href="javascript:;"></a>'+
            '</div>'+
            '<div class="boardDay">'+
            '<ul>'+
            '<li class="day1 {{dactive0}}">{{day0}}</li>'+
            '<li class="day2 {{dactive1}}">{{day1}}</li>'+
            '<li class="day3 {{dactive2}}">{{day2}}</li>'+
            '<li class="day4 {{dactive3}}">{{day3}}</li>'+
            '<li class="day5 {{dactive4}}">{{day4}}</li>'+
            '<li class="day6 {{dactive5}}">{{day5}}</li>'+
            '</ul>'+
            '</div>'+
            '<div class="boardMain">'+
            '<a class="boardClose" href="javascript:;"></a>'+
            '<div class="statusBox">'+
            '</div>'+
            '<a class="boardInstruction" target="_blank" href="{{link}}"></a>'+
            '</div>'+
            '</div>'
        }, o);
        this.$t = $(t);
        this.debugs = this.opts.debugs;
    }
    F.prototype = {
        init: function(data) {
            var _this = this;
            _this.missionInit();
            return this;
        },
        // 初始化
        missionInit: function(){
            var _this = this;
            var decorateData = {};



            this.getData(function(data){
                if(data['start']){
                    _this.getDay(function(dataDay){
                        if(_this.boardData['prize']['isWinning']){
                            _this.showMessage()
                        }
                        if(_this.boardData['myprize']['isShowing']){
                            _this.showMymessage()
                        }
                        decorateData['link'] = _this.opts.infoLink;
                        decorateData = _this.decorateDay(dataDay['day'],decorateData);
                        _this.$t.append(_this.render(decorateData,_this.opts.tpl_body));
                        _this.$box = _this.$t.find('.diamondsBoard');
                        _this.eventInit();
                        _this.setList(data);
                        _this.upDate();
                    });

                }
            });
        },
        decorateDay: function(data,parent){
            var result = parent;
            _this = this;
            $.each(data,function(n,v){
                var markName = v['name'].substr(0,1)+"***"+ v['name'].substr(v['name'].length-1,1)
                result['day'+n] = '<p class="b_day">'+ v['day']+'</p>' + '<p class="b_money">'+ _this.formatMoney(v['money'])+'</p>' + '<p class="b_name">'+ markName+'</p>';
                result['dactive'+n] = 'dactive';
            });
            return result;
        },
        eventInit: function(){
            var _this = this,
                $box = this.$box || $('.diamondsBoard');
            if($box){
                var $close = $box.find('.boardClose'),
                    $open = $box.find('.boardOpen');
                $close.click(function(){
                    $box.addClass('close');
                });
                $open.click(function(){
                    $box.removeClass('close');
                });
            }
        },
        getDay: function(callback){
            var _this = this;
            $.ajax({
                url: _this.opts.getDay,
                dataType: 'json',
                cache: false,
                contentType: "application/json; charset=utf-8",
                success:function(data) {
                    callback(data);
                },error: function(msg) {
                    alert(msg);
                }
            });
        },
        getData: function(callback){
            var _this = this;
            $.ajax({
                url: _this.opts.getConfig,
                dataType: 'json',
                cache: false,
                contentType: "application/json; charset=utf-8",
                success:function(data) {
                    _this.boardData = data['data'];
                    callback(data);
                },error: function(msg) {
                    alert(msg);
                }
            });
        },
        afterSubmit: function(){
            var _this = this;
            this.getData(function(data){
                _this.setList(data);
                if(data.data['prize']['isWinning']){
                    _this.showMessage()
                }
                if(data.data['myprize']['isShowing']){
                    _this.showMymessage()
                }
            });
        },
        upDate: function(){
            var _this = this;
            this.timer = setInterval(function(){
                _this.getData(function(data){
                    _this.setList(data);
                    if(data.data['prize']['isWinning']){
                        _this.showMessage();
                    }
                })
            },_this.opts.updateTime*1000);
        },
        setList: function(data){
            var result = '',
                _this = this;
            data = data['data'];
            if(data['list']){
                $.each(data['list'],function(n,v){
                    var strNum = '',
                        current = '';

                    if(v['isDiamond'] && Number(v['number'])<6) {
                        strNum = 'num1'
                    }else if(Number(v['number'])<6) {
                        strNum = 'num2'
                    }else {
                        strNum = '';
                    }
                    if(Number(v['number']) == Number(data['user'])){
                        current = 'current';
                    }
                    var markName = v['name'].substr(0,1)+"***"+ v['name'].substr(v['name'].length-1,1)
                    result+= '<div class="statusBar '+ strNum + ' ' + current +'">';
                    result+= '<span class="b_number">'+ v['number'] +'</span>';
                    result+= '<span class="b_name">'+ markName +'</span>';
                    result+= '<span class="b_amount">'+ _this.formatMoney(v['amount']) +'</span>';
                    result+= '</div>';
                    if(n == 6 && Number(data['user']) > 8) {
                        result += '<div class="bLine"></div>'
                    }
                });
            };
            this.$box.find(".statusBox").html(result)
        },
        render: function(data,tpl){
            var _this = this,
                markup = tpl;
            for(p in data) {
                markup = _this.template(markup, p, data[p]);
            }
            return markup.replace(/\{\{.*?\}\}/g,'');
        },
        template: function(markup, key, value, reg){
            reg = reg || RegExp('{{' + key + '}}', 'g');
            return markup.replace(reg, value);
        },
        showMessage: function(){
            var _this = this;
            if(!this.message){
                this.$t.append('<div class="boardMessage"><a class="messageClose" href="javascript:;"></a><p>'+ _this.boardData['prize']['message'] +'</p></div>').fadeIn();
                this.message = this.$t.find('.boardMessage');
                $('.messageClose').click(function(){
                    $(this).parent().hide();
                });
            }else {
                this.$t.find('.boardMessage').fadeIn().find('p').html(_this.boardData['prize']['message']);
            }
            setTimeout(function(){
                _this.$t.find('.boardMessage').fadeOut();
            },5000);
        },
        showMymessage: function(){
            var _this = this;
            if(!this.mymessage){
                this.$t.append('<div class="boardMymessage"><p>'+ _this.boardData['myprize']['message'] +'</p><a class="messageClose" href="javascript:;">确定</a></div>').fadeIn();
                this.mymessage = this.$t.find('.boardMymessage');
                $('.messageClose').click(function(){
                    $(this).parent().hide();
                });
            }else {
                this.$t.find('.boardMymessage').fadeIn().find('p').html(_this.boardData['myprize']['message']);
            }
        },
        formatMoney: function (s,n){
            n = n>0 && n<=20 ? n : 2;
            s = this.toFixed(parseFloat((s+"").replace(/[^\d\.-]/g,"")),n)+"";
            var l = s.split(".")[0].split("").reverse(),
                r = s.split(".")[1];
            t = "";
            for(i = 0;i<l.length;i++){
                t+=l[i]+((i+1)%3==0 && (i+1) != l.length ? "," : "");
            }
            return "￥"+t.split("").reverse().join("")+"."+r;
        },
        toFixed: function(num,d){
            var s=num+"";if(!d)d=2;
            if(s.indexOf(".")==-1){
                s+=".";
            }
            s+=new Array(d+1).join("0");
            if (new RegExp("^(-|\\+)?(\\d+(\\.\\d{0,"+ (d+1) +"})?)\\d*$").test(s)){
                var s="0"+ RegExp.$2, pm=RegExp.$1, a=RegExp.$3.length, b=true;
                if (a==d+2){
                    a=s.match(/\d/g);
                    /*if (parseInt(a[a.length-1])>4){
                     for(var i=a.length-2; i>=0; i--) {
                     a[i] = parseInt(a[i])+1;
                     if(a[i]==10){
                     a[i]=0; b=i!=1;
                     }else{
                     break;
                     }
                     }
                     }*/
                    s=a.join("").replace(new RegExp("(\\d+)(\\d{"+d+"})\\d$"),"$1.$2");
                }
                if(b){
                    s=s.substr(1);
                }
                return (pm+s).replace(/\.$/, "");
            }
            return num+"";
        }
    }

    $.fn.diamondsBoard = function(o) {
        var instance;
        this.each(function() {
            instance = $.data(this, 'diamondsBoard');
            if (instance) {

            } else {
                instance = $.data(this, 'diamondsBoard', new F(this, o));
            }
        });
        return instance;
    }
})(jQuery);
