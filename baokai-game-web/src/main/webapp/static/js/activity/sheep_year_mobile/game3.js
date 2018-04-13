;(function($){
	
	var userid=getParam("userid");
	var userdevice=getParam("device");
    function F(t, o){
        this.opts = $.extend({
        	ajaxurl: '/sheepYear/rotary',
        	debugs: false // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
        }, o);  
        this.slides = [];
        this.$t = $(t);
        this.debugs = this.opts.debugs;
        // this.isIE6 = $.browser.msie && parseFloat($.browser.version) < 7;
        this.init();
    }

    F.prototype = {
        init: function(){
        	var me = this;
        	me.$times   = $('[data-amount="rotary"]');
        	me.$history = $('#rh-cycle');
        	me.$button  = $('.rotary-desktop .rotary-button');
        	me.$dialog 	= $('#rotary-modal');
        	me.$shadow 	= $('#overlay-shadow');
        	me.$rotary  = $('#rotary');

        	me.rotation('init');

        	// 中奖对应的角度，分别为10元, 20元, 100元, 200元, 500元, 10,000元
        	me.angles = [240, 260, 160, 280, 180, 40];

        	me.bindEvent();
       	},
       	rotation: function(flag, angle, callback){
       		var me = this;
       		if( me.rotaryTimer ){
   				clearInterval(me.rotaryTimer);
   			}
       		if( flag == 'init' ){       			
				var now_angle = 0;
	   			me.rotaryTimer = setInterval(function(){
	   				now_angle += 2;
	   				me.$rotary.rotate(now_angle);
		   		}, 150);
	   		}else if( flag == 'prepare' ){
	   			var now_angle = me.$rotary.getRotateAngle()[0];
	   			me.rotaryTimer = setInterval(function(){
	   				now_angle += 10;
	   				me.$rotary.rotate(now_angle);
		   		}, 80);
	   		}else if( flag == 'draw' ){
	   			var now_angle = me.$rotary.getRotateAngle()[0],
	   				target_angle = now_angle + (360 - now_angle % 360) + angle;
				// me.rotaryTimer = setInterval(function(){
				// 	now_angle += 10;
				// 	if( now_angle >= target_angle ){
				// 		now_angle = target_angle;
				// 		clearInterval(me.rotaryTimer);
				// 	}
				// 	me.$rotary.rotate(now_angle);
				// }, 100);
				me.$rotary.rotate({
					angle: now_angle,
					animateTo: now_angle + (360 - now_angle % 360) + angle,
					duration: 10000,
					// easing: function (x,t,b,c,d){
					// 	return c*(t/d)+b;
					// },
					callback: callback
				});
	   		}else if( flag == 'stop' ){
	   			me.$rotary.rotate()
	   		}
       	},
       	bindEvent: function(){
       		var me = this;
       		me.$button.on('click', function(){
       			if( $(this).hasClass('disabled') ) return false;
       			me.$button.addClass('disabled');
       			me.goLottery();
       		});
       	},
        createHistory: function(data){        	
        	var html = '<div class="rh-list">' +
							'<span class="rh-name">' +data.username+ '</span>' +
					    	'<span class="rh-desc">' +data.desc+ '</span>' +
					    	'<span class="rh-date">' +data.date+ '</span>' +
						'</div>';
			this.$history.cycle('destroy').prepend(html).cycle();
        },
        goLottery: function(diceType){
        	var me = this;
        	// 执行骰子动画
        	me.rotation('prepare');
        	// 记录当前时间
        	var startTime = new Date();
        	// 请求数据
        	$.post(me.opts.ajaxurl, {diceType: diceType,'userId':userid,'device':userdevice}).done(function(resp){
//        		var resp = $.parseJSON(resp);                
        		if( resp.status == 'ok' ){
        			var endTime = new Date(),
        				time = endTime - startTime,
        				needTime = 4000;
        			if( time - needTime > 0 ){
        				time = 0;
        			}else{
        				time = needTime - time;
        			}
        			setTimeout(function(){
        				// 开奖结束后的操作
        				// 此处luckyidx是以0开始计数，注意与后端返回一致
        				var angle = me.angles[resp.luckyidx];
        				me.rotation('draw', angle, function(){
        					me.handleData(resp);
        				});        				
        			}, time);        			
        		}else{
                    alert(resp.desc);
					me.rotation('stop');
                }
        	}).fail(function(xhr, textStatus, errorThrown){
        		alert('服务器返回错误："' + xhr.responseText + '". 失败，请重试！');
        	});
        },
        handleData: function(resp){
        	var me = this;
			me.$button.removeClass('disabled');
        	me.updateGame(resp);
        	me.dialog(resp.desc);
        },
        updateGame: function(data){
        	var me = this;
        	me.$times.text(data.times);
        	me.createHistory(data);
        	if( data.times < 1 ){
        		me.$button.addClass('disabled');
        	}else{
        		me.$button.removeClass('disabled');
        	}
        },
        dialog: function(desc){
        	var me = this;
        	me.$dialog.find('.dm-desc').html(desc);

        	// 全局方法，显示弹窗
        	showModal( me.$shadow );
       		showModal( me.$dialog );
        },
        // debug
        debug: function(){      
            this.debugs && window.console && console.log && console.log( '[rotaryGame] ' + Array.prototype.join.call(arguments, ' ') );
        }
    }

    $.fn.rotaryGame = function(o) {    
        var instance;
        this.each(function() {              
            instance = $.data( this, 'rotaryGame' );
            // instance = $(this).data( 'rotaryGame' );
            if ( instance ) {
                // instance.init();         
            } else {
                instance = $.data( this, 'rotaryGame', new F(this, o) );          
            }
        });
        return instance;
    }
})(jQuery);


$(function(){
	$('#game3').rotaryGame();
});