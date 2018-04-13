/**
 * Created by user on 16/7/15.
 */
(function($) {
    function F(t, o) {
        this.opts = $.extend({
			url: "/vipmb/qianghongbaodata",
            getPrizeUrl: "/vipmb/qianghongbaoprize",
            debugs: true // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
        }, o);
        this.$t = $(t);
        this.config = {};
        this.debugs = this.opts.debugs;
        this.isFinished = false;
        this.init();
    }

    F.prototype = {
        init: function() {
            var _this = this;

            this.status = this.$t.find('.gameStatus');
            this.gameTips = this.$t.find('.gameTips');
            this.card = this.$t.find('.cardList');
            this.gameLi = this.$t.find('.cardList li');
            this.funLeft = this.$t.find('.funLeft');
            this.funRight = this.$t.find('.funRight');

            this.popTip = $('.popTip');
            this.noneRight = $('.noneRight');
            this.noneCard = $('.noneCard');
            this.winning = $('.winning');

            this.bindEvent();
            _this.getData();

            if(!this.getCookie('isfirst')){
                this.popTip.show();
                this.setCookie('isfirst',true);
            }

        },
        getData: function(){
            var _this = this;
			var data= "token=" +  tokenInfo;
            $.ajax({
				type: "POST",
                dataType: 'json',
                cache: false,
                url: _this.opts.url,
                data: data,
                success:function(data){
                        if(data){
                            _this.config = data;

                            _this.isFinished = data['finished']['isFinished'];
                            var status = data['status'];

                            _this.setFinished();
                            switch(status){
                                case "0":
                                    _this.beforeGame();
                                    break;
                                case "1":
                                    _this._countFun(true);
                                    _this.startGame();
                                    break;
                                case "2":
                                    _this.preview();
                            };

                        }
                }
            });
        },
        updateData: function(callback){
            var _this = this;
            $.ajax({
                dataType: 'json',
                cache: false,
                url: _this.opts.url,
                data: '',
                success:function(data){
                    if(data){

                        _this.config = data;
                        _this.isFinished = data['finished']['isFinished'];
                        $.isFunction(callback) && callback();
                    }
                }
            });
        },
        setPrize: function(data){
            var arr = data.split('');
            $.each(this.gameLi,function(n,v){
                $(v).find('.front').addClass('cardNum'+arr[n]);
            });
        },

        preview: function(){
            this.status.removeClass('active');
            this.card.addClass('disable');
            this.funRight.html("活动开始日期 : <span>8月23日</span>");
        },
        beforeGame: function(){
            var _this = this;
            this.status.removeClass('active');
            this.card.addClass('disable');
            if(!this.config.todayEnd){
                _this._countFun(false);
            }else {
                _this._stopTime();
            };
        },
        startGame: function(){
            this.status.addClass('active');
            if(this.isFinished){

            }else {
                this.card.removeClass('disable');
            }
        },
        bindEvent: function(){
            var _this = this;
            var gameLi = this.gameLi;
            gameLi.on("click", function() {
                var $this = $(this);

                if($this.parent().hasClass('disable')) return;

                if(!_this.config.qualification) {
                    _this.noneRight.show();
                    return;
                }


                _this.getPrize(function(data){
                    var eleBack = $this.find('a').eq(0), eleFront = $this.find('a').eq(1);
                    eleFront.addClass("out").removeClass("in");
                    setTimeout(function() {
                        eleBack.addClass("in").removeClass("out");
                        //如果翻出4张牌
                        if(gameLi.find(".in").length == 4) {
                            _this.showPrize(data);
                            _this.isFinished = true;
                            _this.funLeft.html("您仍需投注 : <span>" + data.leftAmount + "</span>").show();
                            _this.card.addClass('disable');
                        }
                    }, 225);

                });
                return false;
            });
            this.popTip.find('.confirm').click(function(){
                $(this).parent().hide();
            });

            this.noneCard.find('.confirm').click(function(){
                $(this).parent().hide();
                _this.card.addClass('disable');
            });

            this.noneRight.find('.confirm').click(function(){
                $(this).parent().hide();
            });
        },
        setFinished : function(){
            var _this = this;
            var data = this.config;
            if(_this.isFinished){
                _this.card.addClass('disable');
				_this.setPrize(_this.config['finished']['prize']);
                _this.funLeft.html("您仍需投注 : <span>" + _this.config['finished']['leftAmount'] + "</span>").show();
                _this.gameTips.html("恭喜您抽中了"+ Number(data['finished']['prize']) +"元，您需要在24:00前有效投注满"+ data['finished']['amount'] +"元。");
                _this.card.find('.front').removeClass('out').addClass('in');
                _this.card.find('.back').addClass('out');
            };
        },
        getPrize: function(callback){
            var _this = this;
			var data= "token=" +  tokenInfo;
            $.ajax({
				type: "POST",
                dataType: 'json',
                cache: false,
                url: _this.opts.getPrizeUrl,
                data: data,
                success:function(data){
                    if(Number(data['isSuccess']) == 1){
                        if(data['data']){
                            _this.setPrize(data['data']['prize'])
                            $.isFunction(callback) && callback(data['data']);
                        }
                    }else{
                        _this.noneCard.show();
                    }
                }
            });
        },
        showPrize: function(data){
            var _this =this;
            this.winning.find('.money').html(Number(data.prize));
            this.winning.find('.amount').html(data.amount);
            this.winning.show();
            this.winning.find('.confirm').click(function(){
                _this.gameTips.html(_this.winning.find('p').html());
                _this.winning.hide();
            });
        },
        _countDay : function(){
            var _this = this,thisTime = null;
            var nowTime = new Date(_this.config['nowTime']),
                time = {},
                stopTime = new Date(nowTime.valueOf() + 1*24*60*60*1000);
            stopTime.setHours(0,0,0),


            this.timeFun2 = setInterval(function() {
                //计算剩余时间
                leftsecond = thisTime || parseInt((stopTime - nowTime)/1000);
                //缓存当前时间
                (thisTime == null) && (thisTime = leftsecond);
                //格式化时间
                time.h = parseInt((leftsecond/3600)%24);
                time.m = parseInt((leftsecond/60)%60) + 1;

                _this.funRight.html('剩余时间' + '<span>' + time.h +'时' + time.m + '分');
                //时间结束
                if(thisTime == 0) {
                    _this.timeFun2 = null;
                    return;
                };
                //缓存时间
                thisTime = thisTime != null ? thisTime - 1 : leftsecond - 1;
            }, 1000);
        },
        _countFun : function(isStart) {
            if(this.isFinished) {
                this._countDay();
                return;
            }

            var _this = this, time = {},
                thisTime = null,
                leftsecond,
                nowTime = new Date(_this.config['nowTime']),
                stopTime = new Date(_this.config['stopTime']),
                startTime = new Date(_this.config['startTime']),
                ssTime = isStart ? stopTime : startTime;

            //时间计算定时器
            this.timeFun = setInterval(function() {
                //计算剩余时间
                leftsecond = thisTime || parseInt((ssTime - nowTime)/1000);
                //缓存当前时间
                (thisTime == null) && (thisTime = leftsecond);
                //格式化时间
                time.d = parseInt(leftsecond/3600/24);
                time.h = parseInt((leftsecond/3600)%24);
                time.m = parseInt((leftsecond/60)%60) + 1;
                time.isStart = isStart || false;

                _this._showTime(time);

                //时间结束
                if(thisTime == 0) {
                    //游戏倒计时结束
                    if(isStart){
                        var time2 = {};
                        time2.h = 0;
                        time2.m = 0;
                        time2.isStart = isStart || false;
                        _this._showTime(time2);
                        clearInterval(_this.timeFun);
                        _this.timeFun = null;
                        if(!_this.isFinished){
                            _this.gameTips.html("请您确认是否有翻卡抽奖资格。");
                        }
                        _this.card.addClass('disable');
                        _this.status.removeClass('active');
                        return;
                    //开始倒计时结束
                    }else {
                        _this.updateData(function(){
                            //var status = _this.config['status'];
                            var status = "1";
                            var isStart2 = status == "0" ? false: true;
                            if(!_this.isFinished) {
                                _this.card.removeClass('disable');
                            }
                            _this.status.addClass('active');
                            _this.gameTips.html("翻牌开奖，祝您好运");
                            _this.funLeft.html("抽奖名额剩余 : <span>" + _this.config.surplusPrize + "</span>").show();
                            _this._countFun(isStart2);
                        });
                        clearInterval(_this.timeFun);
                        _this.timeFun = null;
                    }

                };
                //缓存时间
                thisTime = thisTime != null ? thisTime - 1 : leftsecond - 1;
            }, 1000);
        },
        _stopTime: function(){
            var _this = this;
            var time = {};
            time.h = 0;
            time.m = 0;
            time.isStart = true;
            this._showTime(time);
        },
        _showTime: function(time){
            var tips = time.isStart ? "距离抢红包结束还有":"距离今天翻卡开奖还有";
            this.funRight.html(tips + '<span>' + time.h +'时' + time.m + '分');
        },

        setCookie: function(name,value){
            var Days = 1;
            var exp = new Date();
            exp.setTime(exp.getTime() + Days*24*60*60*1000);
            document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
        },
        getCookie: function(name){
            var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
            if(arr=document.cookie.match(reg))
                return unescape(arr[2]);
            else
                return null;
        },

        // debug
        debug: function() {
            this.debugs && window.console && console.log && console.log('[counter] ' + Array.prototype.join.call(arguments, ' '));
        }
    }

    $.fn.card = function(o) {
        var instance;
        this.each(function() {
            instance = $.data(this, 'card');
            if (instance) {
                // instance.init();
            } else {
                instance = $.data(this, 'card', new F(this, o));
            }
        });
        return instance;
    }
})(jQuery);
