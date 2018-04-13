;
(function($) {
    function F(t, o) {
        this.opts = $.extend({
            ballsize: 6, // 彩球个数
            initball: '0,0,0,0,0,0', // 初始化彩球数据
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
            this.$handles.eq(1).animate({
                opacity: 'show'
            }, 300, function() {
                $(this).animate({
                    opacity: 'hide'
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
                    slide.stop().css('marginTop', -(4 + ball_num) * opts.offset[1]);
                });
                me.doCallback(me.callback);
            } else {
                me.play();
                $.each(me.slides, function(idx, slide) {
                    var ball_num = Number(balls[idx]),
                        timeout = opts.timeout + opts.delay * idx,
                        // 一圈是10个数，循环opts.loop圈后，在移动ball_num单位个高度(opts.offset[1])
                        step = (opts.loop * 4 + ball_num) * opts.offset[1];
                    slide.stop().animate({
                        marginTop: '+=' + (opts.offset[1] * .6)
                    }).stop().animate({
                        marginTop: -step
                    }, timeout, function() {
                        $(this).css('marginTop', -(5 + ball_num) * opts.offset[1]);                        
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