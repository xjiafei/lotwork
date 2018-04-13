/**
 * Created by user on 15/8/12.
 */
(function($) {
    function F(t, o) {
        this.opts = $.extend({
            title: '.tab_title',
            content: '.tab_content'
        }, o);
        this.$t = t;
        this.oLi = {};
        this.oDiv = {};
        this.now = 0;
        this.init();
    }
    F.prototype = {
        init: function() {
            var me = this;
            var box = this.$t;
            var oLi = $(box).find(me.opts.title + ' li'),
                oDiv = $(box).find(me.opts.content + ' .content');
            this.oLi = oLi;
            this.oDiv = oDiv;
            oLi.on('click',function(){
                var n = $(this).index();
                me.tab(n);
            });
            if('changepage' in this.opts){
                this.play();
            }
        },
        play: function(){
            var box = this.$t;
            var page = $(box).find(this.opts.changepage);
            var left = page.find('.left'),
                right =  page.find('.right');
            var me = this;

            console.log(this.now);
            left.on('click',function(){
                me.now--;
                me.check();
                me.tab(me.now);
            })
            right.on('click',function(){
                me.now++;
                me.check();
                me.tab(me.now);
            })
        },
        tab: function(index){
            this.oLi.removeClass('current');
            this.oLi.eq(index).addClass('current');
            this.oDiv.hide();
            this.oDiv.eq(index).show();
            this.now = index;
        },
        check: function(){
            var l = this.oLi.length;
            if(this.now >= l){
                this.now = 0;
            }else if(this.now < 0){
                this.now = l-1;
            }
        }
    }
    $.fn.tab = function(o) {
        var instance;
        this.each(function() {
            instance = $.data(this, 'tab');
            if (instance) {
            } else {
                instance = $.data(this, 'tab', new F(this, o));
            }
        });
        return instance;
    }
})(jQuery);