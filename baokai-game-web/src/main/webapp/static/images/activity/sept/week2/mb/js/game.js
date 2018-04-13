
(function($) {
    function F(t, o) {
        this.opts = $.extend({
            url: "/vipmb/lightdata",
            submit: "/vipmb/activityaward"
        }, o);
        this.$t = $(t);
        this.config = {};
        this.isFinished = false;
        this.prizeData = null;
        this.init();
    }

    F.prototype = {
        init: function() {
            var _this = this;
            this.pop = $('.pop');
            this.floor = this.$t.find('.floor');
            this.tip = this.$t.find('.tip');
            this.getData();
        },
        getData: function(){
            var _this = this;
            var data= "token=" +  tokenInfo;
            $.ajax({
                dataType: 'json',
                cache: false,
                url: _this.opts.url,
                data: data,
                success: function(data){
                    if(data){
                        _this.config = data;
                        if(data.hasRight){
                            _this.bindEvent();
                        }else{
							_this.tip.hide();
						}
                    }
                }
            });
        },
        bindEvent: function(){
            var _this = this;
            var length = this.floor.length;
            var moneyMap = ['999','5199','3699','1699','1099','519','169','19'];
			var vipMoneyMap = ['999','5999','3999','1999','1199','599','199','29'];
			if(_this.config.isVip){
				moneyMap=vipMoneyMap;
			}
			if(Number(_this.config.level) <= 7 && Number(_this.config.level)>-1) {
                this.nowLevel = _this.$t.find('.floor7');
                this.showTips();
            }	
			if(Number(_this.config.level) == 8 ) {
          
				this.nowLevel = _this.$t.find('.floor7');
                this.showTips2();
            }	
			
            this.floor.on('click',function(){
                var lightingNum = Number(length) - Number($(this).index()) -1;
                var level = $(this).index();
                var index = Number($(this).index());				
                if($(this).prevAll().hasClass('light') || $(this).hasClass('light') ) return;
                if(level < Number(_this.config.level)) return;

                var $this = $(this);
                if($(this).nextAll('.light').length == lightingNum){
                    _this.nowLevel = _this.$t.find('.floor'+(index-1));



                    if(level < Number(_this.config.level)){
                        _this.showTips2();
                        return;
                    }else if(level == 1){
                        if(_this.config.isTop){
                            _this.showTips();
                        }
                        _this.submitClick('1',function(){
                            if(_this.config.nowLevel != level) {
                                _this.pop.fadeIn().find('.money').html(moneyMap[level]);
                            }
                            _this.tip.hide()
                        });

                        $(this).removeClass('flash').addClass('light');
                    }else if(level == Number(_this.config.level)){

                        if(Number(level) == 0){
                            if(_this.config.isTop){

                            }else if(Number(_this.config.nowLevel)== 0){
                                _this.floor.removeClass('flash').addClass('light');
								_this.tip.hide();
								return;
                            }else{
								return;
							}
                            _this.submitClick('0',function(){
                                if(_this.config.isTop){
                                    _this.floor.removeClass('flash');
									if(!_this.isFinished){
										_this.pop.fadeIn().find('.money').html(moneyMap[level]);
									}                                   
                                    _this.tip.hide();
                                }
                            })

                        }else {
                            _this.submitClick(level,function(){
								
								//if(!_this.isFinished){
								//	_this.showPrize(moneyMap[level]);
								//}
								if(_this.config.nowLevel != level){
									_this.showPrize(moneyMap[level]);
								}
                                _this.showTips2();
                            })

                        }
                        $(this).removeClass('flash').addClass('light');
                    }else{
                        _this.submitClick(level,function(){
                            if(_this.config.nowLevel != level) {
                                _this.showPrize(moneyMap[level]);
                            }
                            _this.showTips();
                            $this.addClass('light');
                        })
                    };



                };
            });

            this.pop.find('.close').on('click',function(){
                $(this).parent().hide();
            });
            this.pop.find('.confirm').on('click',function(){
                $(this).parents('.pop').hide();
            });
            if(Number(_this.config.nowLevel) < 8){
                for(var i=7; i>=0;i--){
                    if(i > Number(_this.config.nowLevel)){
                        _this.floor.eq(i).removeClass('flash').addClass('light')
                    }else{
                        _this.floor.eq(_this.config.nowLevel).trigger('click');
                        return;
                    };
                }

            }
						
        },
        showPrize: function(money){
            this.pop.fadeIn().find('.money').html(money);
        },
        showTips: function(){
            var top = this.nowLevel.offset().top - this.$t.offset().top;
            this.tip.show().removeClass('tips2').css({'top':top}).find('span').html('');
            this.floor.removeClass('flash');
            this.nowLevel.addClass('flash');
        },
        showTips2: function(){
            var top =  this.nowLevel.offset().top - this.$t.offset().top;
            this.tip.show().addClass('tips2').css({'top':top}).find('span').html(this.config['leftMoney']);
        },
        showResult: function(){
            var length = this.config['level'],
                i = this.floor.length;
            for(;i>=length && i>=0;i--){
                this.floor.eq(i-1).addClass('light');
            }
            this.nowLevel = this.$t.find('.floor'+(length-1));
            this.showTips2();
        },
        submitClick: function(level,callback){
            var _this = this;
            var data= "token=" +  tokenInfo+'&level='+level;
            $.ajax({
                dataType: 'json',
                cache: false,
                url: _this.opts.submit,
                data: data,
                success: function(data){
                    if(data['isSuccess']){
                        if($.isFunction(callback)){
							_this.isFinished=data.data.isFinished;
                            callback();
                        }
                    }else{
                        alert(data['msg']);
                    }
                }
            });
        }

    }

    $.fn.tower = function(o) {
        var instance;
        this.each(function() {
            instance = $.data(this, 'tower');
            if (instance) {
                // instance.init();
            } else {
                instance = $.data(this, 'tower', new F(this, o));
            }
        });
        return instance;
    }
})(jQuery);

