
(function($) {
    function F(t, o) {
        this.opts = $.extend({
            getConfig: 'data.json',
            getResult: 'result.json',
            count: 3,
            configData: {},
            resultData: {}
        }, o);
        this.$t = $(t);
    }
    F.prototype = {
        init: function(data) {
            var _this = this;

            this.$flag = this.$t.find('.flag');
            this.$jp = this.$t.find('.japanese');
            this.$j1 = this.$t.find('.j1');
            this.$j2 = this.$t.find('.j2');
            this.$j3 = this.$t.find('.j3');
            this.$flag = this.$t.find('.flag');
            this.$mask = this.$t.find('.mask');
            this.$pop = this.$t.find('.pop');
            this.$result = this.$t.find('.result');
            this.$line = this.$result.find('.line');
            this.$tips = this.$result.find('.tips');
            this.$amount = this.$result.find('.amount');
            this.$scale = this.$result.find('.scale');
            this.$multiple = this.$result.find('.multiple');
            this.$bonus = this.$result.find('.bonus');
            _this.missionInit();

            return this;
        },
        // 初始化
        missionInit: function(){
            var _this = this;
            var decorateData = {};
            this.getData(function(data){
                if(data['isSuccess']){
                    _this.configData = data['data'];
                    if(data['data']['isGetPrize']){
                        _this.showResult();
                    }else{
                        _this.eventInit();
                    };
                };
            });
        },
        eventInit: function(){
            var _this = this;
            this.$t.addClass('start');

            this.$jp.show();
            this.stopMove();
            this.jpMove();
            this.$pop.find('.cancel').click(function(){
                $(this).parents('.pop').hide();
                _this.$mask.hide();
                _this.gameReset();
                _this.$jp.off('click').on('click',function(){
                    $(this).addClass('dead');
                    _this.stopMove();
                    setTimeout(function(){
                        _this.showMessage();
                    },1000);
                    _this.$jp.off('click');
                });
            });
            this.$jp.off('click').on('click',function(){
                $(this).addClass('dead');
                _this.stopMove();
                setTimeout(function(){
                    _this.showMessage();
                },1000);
                _this.$jp.off('click');
            });
            this.showResult(true);
        },
        jpMove: function(){
            var _this = this;
            var left = 600;
            this.moveTimer = setInterval(function(){
                if(left < 0){
                    left = 600;
                }else{
                    left -= 20;
                }
                _this.$j3.css({"left":left});
            },400);
        },
        stopMove: function(){
            var _this = this;
            clearInterval(_this.moveTimer);
            this.moveTimer = null;
        },
        gameReset: function(){
            this.$jp.removeClass('dead');
            this.eventInit();
        },
        showResult: function(flag){
            var _this = this;
            this.$amount.html(this.configData['betAmount']);
            this.$scale.html(this.configData['betScale']);
            if(!flag){
                this.$flag.fadeIn();
                this.$multiple.html(this.configData['betMutile']);
                this.$bonus.html(this.configData['prize']);
            }
            this.showLine();
        },
        showLine: function(){
            var lineMap = [2000,50000,100000,200000],
                widthMap = [0,150,183,231,228],
                leftMap = [0,150,336,567,800],
                amount = Number(this.configData['betAmount']),
                num = 0,
                left = 0;
            var _this = this;

            $.each(lineMap,function(n,v){
                num = n;
                if(amount>v){
                }else{
                    return false;
                }
            });
            if(num == 0) {
                left = amount/2000 * 150;
            }else if( num == 3 && amount >= lineMap[3]){
                left = 800;
            } else {
                left = leftMap[num] + (amount-lineMap[num-1])/(lineMap[num]-lineMap[num-1])* widthMap[num+1];
            }

            if(amount >= lineMap[3]){
                this.$tips.hide();
            }else {
                this.$tips.show().html(_this.configData['leftAmount']).css({'left':left});
            }

            console.log(num)
            console.log(left)
            this.$line.css({'width':left});

        },

        getData: function(callback){
            var _this = this;
            $.ajax({
                url: '/vip/octinfo',
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
        getResult: function(callback){
            var _this = this;
            $.ajax({
                url: '/vip/shotkill',
                dataType: 'json',
                cache: false,
                contentType: "application/json; charset=utf-8",
                success:function(data) {
                    _this.configData = data['data'];
                    callback(data);
                },error: function(msg) {
                    alert(msg);
                }
            });
        },
        submit: function(){
            var _this = this;
            var multipleMap = {
                "1.0": "1",
                "1.5": "2",
                "2.0": "3",
                "10.0": "4"
            }
            this.getResult(function(data){
                if(data['isSuccess']){
                    _this.$pop.find('.popConfirm').hide();
                    var con =  _this.$pop.find('.popSuccess');
                    con.find('.img_multiple').addClass('m'+multipleMap[data.data.betMutile+'']);
                    con.find('.result_bonus').html(data.data.prize);
                    con.fadeIn();
                    var num = _this.opts.count;
                    _this.timer = setInterval(function(){
                        con.find('.count').html(num+'秒后关闭');
                        num--;
                        if(num < 0){
                            clearInterval(_this.timer);
                            _this.timer = null;
                            _this.$pop.hide();
                            _this.$mask.hide();
                            _this.afterSubmit();
                        }
                    },1000);


                }else{
                    _this.$pop.find('.popConfirm').hide();
                    var con =  _this.$pop.find('.popFail');
                    con.fadeIn();
                    var num = _this.opts.count;
                    _this.timer = setInterval(function(){
                        con.find('.count').html(num+'秒后关闭');
                        num--;
                        if(num < 0){
                            clearInterval(_this.timer);
                            _this.timer = null;
                            _this.$pop.hide();
                            _this.$mask.hide();
                            _this.gameReset();
                        }
                    },1000);

                    _this.$pop.find('.confirm').removeClass('disable');
                }
            });
        },
        afterSubmit: function(){
            this.$jp.hide();
            this.$flag.fadeIn();
            this.$amount.html(this.configData['betAmount']);
            this.$scale.html(this.configData['betScale']);
            this.$multiple.html(this.configData['betMutile']);
            this.$bonus.html(this.configData['prize']);
            this.showLine();
            setTimeout(function(){
                $('body').animate({'scrollTop': '1000px'})
            },2000)
        },
        showMessage: function(){
            var _this = this;
            this.$mask.show();
            this.$pop.show();
            this.$pop.find('.popConfirm').show();
            this.$pop.find('.popSuccess').hide();
            this.$pop.find('.popFail').hide();

            this.$pop.find('.confirm').off('click').on('click',function(){
                if($(this).hasClass('disable')) return;
                    _this.submit();
                $(this).addClass('disable');

            });

        }
    }

    $.fn.fight = function(o) {
        var instance;
        this.each(function() {
            instance = $.data(this, 'fight');
            if (instance) {

            } else {
                instance = $.data(this, 'fight', new F(this, o));
            }
        });
        return instance;
    }
})(jQuery);
