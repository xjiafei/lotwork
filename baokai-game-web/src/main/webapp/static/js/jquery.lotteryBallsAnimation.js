;(function($){
    function F(t, o){
        this.opts = $.extend({
            delay: 30,
            step: 100,
            maxNum: 9, // 最大球号
            debugs: true // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
        }, o);  
        this.slides = [];
        this.$t = $(t);
        this.debugs = this.opts.debugs;
        // this.isIE6 = $.browser.msie && parseFloat($.browser.version) < 7;
        this.init();
    }

    F.prototype = {
        init: function(){
            var me = this, opts = me.opts;
            me.elems = me.$t.children();
            me.elemLen = me.elems.length;
            me.timer = null;
        },
        randomBalls: function(){
            var me = this, balls = [];
            for( var i=0; i<me.elemLen; i++ ){
                balls.push( Math.ceil( Math.random() * (me.opts.maxNum - 1) + 1 ) );
            }
            return balls;
        },
        changeBallNumber: function(param){
            var me = this, balls = [];
            if( param && Object.prototype.toString.call(param) === '[object Array]' ){
                balls = param;
            }else {
                balls = me.randomBalls();
            }
            me.elems.each(function(i) {
                this.innerHTML = balls[i];
            });
        },
        animation: function(realBalls){
            var me = this, count = 0, opts = me.opts;
            (function(){
                me.changeBallNumber();
                if( count >= opts.step ){
                    clearTimeout(me.timer);
                    me.changeBallNumber(realBalls);
                    return me.timer = null;
                }
                me.debug(count, opts.delay);
                count++;
                me.timer = setTimeout(arguments.callee, opts.delay);
            })();
        },
        // debug
        debug: function(){
            this.debugs && window.console && console.log && console.log( '[lotteryBallsAnimation]', arguments );
        }
    }

    $.fn.lotteryBallsAnimation = function(o) {    
        var instance;
        this.each(function() {              
            instance = $.data( this, 'lotteryBallsAnimation' );
            // instance = $(this).data( 'lotteryBallsAnimation' );
            if ( instance ) {
                // instance.init();         
            } else {
                instance = $.data( this, 'lotteryBallsAnimation', new F(this, o) );          
            }
        });
        return instance;
    }
})(jQuery);

// 用法
// var lotteryBallsAnimation = $('#J-lottery-info-balls').lotteryBallsAnimation();
// 
// lotteryBallsAnimation.animation([1,2,3,4,5]);
