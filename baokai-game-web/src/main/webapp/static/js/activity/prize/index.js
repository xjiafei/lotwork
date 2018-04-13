;(function($){
    function F(t, o){
        this.opts = $.extend({
        	visible: 3,
        	initDay: 1,
        	after: function(){},
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
        	var me = this, opts = me.opts;
        	me.$box = me.$t.find('[data-cycle-box]');
        	// if( !me.$box.length ){
        	// 	return alert('Please config the cycle box element!');
        	// }
        	me.$slides = me.$box.children();
        	me.slideLen = me.$slides.length;
        	me.step = me.$slides.eq(0).outerWidth();
        	me.$box.css('width', me.step * me.slideLen);

        	me.$button = me.$t.find('[data-cycle-button]');
        	me.$prev = me.$button.find('.prev');
        	me.$next = me.$button.find('.next');
        	me.idx = -1;

        	me.initDay = me.$t.data('thisday') || opts.initDay;
        	
        	// 视觉下中间元素左右两边的元素个数
        	// 视觉显示3个元素，则左右两边各1个元素
        	me.flagNum = Math.floor(opts.visible / 2);

        	me.slide(me.initDay - 1);

        	me.bindEvent();

        },
        slide: function(idx){
        	var me = this;
        	me.onhandled = true;
        	// idx此处为索引值：即从0开始计数
        	if( idx >= me.flagNum && idx <= me.slideLen - me.flagNum - 1 ){
        		me.$box.animate({
	        		marginLeft: -me.step * (idx-1)
	        	}, 1000);
        	}else if( idx < 0 ){
        		idx = 0;
        	}else if( idx >= me.slideLen ){
				idx = me.slideLen - 1;
			}
	        	
        	me.idx = idx;
    		me.$slides.eq(idx).addClass('active').find('.prize').removeClass('normal-prize')
    			.end()
    			.siblings('.active').removeClass('active').find('.prize').addClass('normal-prize');
    		me.checkStatus();

    		setTimeout(function(){
    			me.onhandled = false;
    		}, 1000);
        },
        checkStatus: function(){
        	var me = this, idx = me.idx;
        	if( idx < 1 ){
        		me.$prev.addClass('disabled');
        	}else{
        		me.$prev.removeClass('disabled');
        	}

        	if( idx >= me.slideLen - me.flagNum ){
        		me.$next.addClass('disabled');
        	}else{
        		me.$next.removeClass('disabled');
        	}
        },
        bindEvent: function(){
        	var me = this;
        	me.$button.children().on('click', function(){
        		if( $(this).hasClass('disabled') || me.onhandled ) return false;
        		var idx = me.idx;
        		if( $(this).hasClass('prev') ){
        			idx -= 1;
        		}else{
        			idx += 1;
        		}
        		me.slide(idx);
        	});
        	// me.$slides.on('click', function(){
        	// 	if( $(this).hasClass('active') || me.onhandled ) return false;
        	// 	var idx = $(this).index();
        	// 	me.slide( idx );
        	// }).eq(me.initDay - 1).trigger('click');
        },
        // debug
        debug: function(){      
            this.debugs && window.console && console.log && console.log( '[slide] ' + Array.prototype.join.call(arguments, ' ') );
        }
    };

    $.fn.slide = function(o) {    
        var instance;
        this.each(function() {              
            instance = $.data( this, 'slide' );
            // instance = $(this).data( 'slide' );
            if ( instance ) {
                // instance.init();         
            } else {
                instance = $.data( this, 'slide', new F(this, o) );          
            }
        });
        return instance;
    };
})(jQuery);

;(function($){
    function F(t, o){
        this.opts = $.extend({
        	ajaxurl: '/gamePrize/getLucky',
            activeClass: 'ltry-active',
            shadowLayer: '#overlay_shadow',
            prizeLayer : '#myprize_layer',
            winnerLayer: '#winner_layer',
            historyTable: '.result-table tbody',
            prizeImgs   : '.prizes img',
            prizeTimes  : '.lottery-board h3 strong',
            time1       : 300, // 300毫秒
            time2       : 50,
            time3       : 800,
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
        	var me = this, opts = me.opts;
        	me.$button = me.$t.find('.lottery-button');
            me.$ltrys = me.$t.find('.lotterys').children();
            me.ltryLen = me.$ltrys.length;

            me.$shadowLayer  = $(opts.shadowLayer).fadeOut(0);
            me.$prizeLayer   = $(opts.prizeLayer).fadeOut(0);
            me.$winnerLayer  = $(opts.winnerLayer).fadeOut(0);
            me.$historyTable = me.$prizeLayer.find(opts.historyTable);
            me.$prizeImgs    = me.$winnerLayer.find(opts.prizeImgs);

            me.$allLayers = me.$prizeLayer.add(me.$winnerLayer);

            me.$prizeTimes = $(opts.prizeTimes);

            me.time1 = opts.time1;
            me.time2 = opts.time2;
            me.time3 = opts.time3;
            me.timeCount = 0;

            me.timer = null;
            me.idx = 0;

            me.bindEvent();
            
        },
        bindEvent: function(){
            var me = this;
            me.$button.on('click', function(){
                if($(this).hasClass('onhandled')) return false;
                $(this).addClass('onhandled');
                me.draw();
                return false;
            });
            me.$allLayers.find('[data-action]').on('click', function(){
                var action = $(this).data('action');
                if( action == 'close' ){
                    me.hideDialog($(this).parents('.overlay_layer').eq(0));
                }
                return false;
            });
            if( me.getTimes() < 1 ){
                me.$button.addClass('onhandled');
            }
        },
        draw: function(){
        	var me = this;
            me.time = me.time1;
        	me.timeCount = 0;

            var time1 = me.time1 * 6,
                time2 = time1 + me.time2 * 32;

            me.getLuckyIdx();

            (function(){
                me.move(me.idx);
                me.idx += 1;
                me.timeCount += me.time;
                if( me.timeCount >= time1 ){
                    me.time = me.time2;
                }
                if( me.timeCount >= time2 ){
                    // 抽奖成功
                    if( me.luckyidx >= 0 ){
                        me.time = me.time3;
                        if( me.idx == me.luckyidx + 1 ){
                            me.$historyTable.prepend('<tr> \
                                    <td>' +me.lucky.date+ '</td> \
                                    <td>' +me.lucky.desc+ '</td> \
                                </tr>');
                            setTimeout(function(){
                                me.showDialog('winner', {idx: me.luckyidx});
                                var times = me.getTimes() - 1;
                                me.$prizeTimes.text(times);
                                if( times >= 1 ){
                                    me.$button.removeClass('onhandled');
                                }
                                $('body').trigger('getPrize');                       
                            }, 1000);
                            return clearTimeout(me.timer);
                        }
                    }
                    // 抽奖失败
                    else{
                        me.$prizeTimes.text(0);
                        me.$ltrys.removeClass(me.opts.activeClass);
                        me.$button.addClass('onhandled');
                        alert('您已经没有抽奖机会啦~感谢参与！');
                        return clearTimeout(me.timer); 
                    }
                }
                if( me.idx >= me.ltryLen ){
                    me.idx = 0;
                }
                // console.log(me.idx, me.timeCount, me.time);
                me.timer = setTimeout(arguments.callee, me.time);
            })();
        },
        move: function(idx){
            var me = this;
            me.$ltrys.removeClass(me.opts.activeClass)
                .eq(idx).addClass(me.opts.activeClass);
            return me.idx = idx;
        },
        getLuckyIdx: function(){
        	var me = this;
        	$.post(me.opts.ajaxurl).done(function(resp){
        		//var resp = $.parseJSON(resp);
                console.log(resp.luckyidx);
        		if( resp.status == 'ok' ){
        			me.luckyidx = resp.luckyidx - 1;
                    me.lucky = resp;
        		}else{
                    me.luckyidx = -1;
                }
                return me.luckyidx;
        	}).fail(function(xhr, textStatus, errorThrown){
        		me.luckyidx = -1;
        		alert('服务器返回错误："' + xhr.responseText + '". 失败，请重试！');
        	});
        },
        getTimes: function() {
            var me = this,
                times = parseInt(me.$prizeTimes.text()) || 0;
            return times;
        },
        showDialog: function(type, data){
            var me = this;
            me.shadowLayer('show');
            me.$allLayers.filter(':visible').fadeOut(0);
            if( type == 'history' ){
                $target = me.$prizeLayer;
            }else if( type == 'winner' ){
                var idx = data.idx || 0;
                me.$prizeImgs.hide(0).eq(idx).show();
                $target = me.$winnerLayer;
            }
            $target.fadeIn();
        },
        hideDialog: function($t){
            var me = this, $target = ($t && $t.length) ? $t : me.$allLayers;
            me.shadowLayer('hide');
            $target.fadeOut(0);
        },
        shadowLayer: function(action){
            var me = this;
            if( action == 'show' ){
                me.$shadowLayer.stop().show();
            }else if( action == 'hide' ){
                me.$shadowLayer.stop().hide();
            }
        },
        // debug
        debug: function(){      
            this.debugs && window.console && console.log && console.log( '[lottery] ' + Array.prototype.join.call(arguments, ' ') );
        }
    }

    $.fn.lottery = function(o) {    
        var instance;
        this.each(function() {              
            instance = $.data( this, 'lottery' );
            // instance = $(this).data( 'lottery' );
            if ( instance ) {
                // instance.init();         
            } else {
                instance = $.data( this, 'lottery', new F(this, o) );          
            }
        });
        return instance;
    }
})(jQuery);

;(function($){
    function F(t, o){
        this.opts = $.extend({
            showNum: 6,
            wrapClass: 'list-wrap',
            ajaxurl: '/gamePrize/getLuckyList',
            ajaxTimeout: 60 * 1000, // 60s
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
            var me = this, opts = me.opts;
            me.$children = me.$t.children();
            me.len = me.$children.length;
            me.step = me.$children.eq(0).outerHeight();
            var wrapStyle = ' style="height: ' + opts.showNum * me.step + 'px;position: relative;overflow:hidden;"';
            me.$t.wrap('<div class="' + opts.wrapClass + '"' + wrapStyle +'></div>');
            me.$t.height(me.len * me.step);
            me.animate();
            me.bindEvent();
        },
        bindEvent: function(){
            var me = this;
            me.play();
            me.$t.on({
                mouseover: function(){
                    me.stop();
                },
                mouseout : function(){
                    me.play();
                }
            });
            if( me.opts.ajaxurl && me.opts.ajaxTimeout ){
                setInterval(function(){
                    me.updateList();
                }, me.opts.ajaxTimeout);
            }
            $('body').on('getPrize', function(){
                me.updateList();
            });         
        },
        play: function(){
            var me = this;
            me.timer = setInterval(function(){
                me.animate();   
            }, 2000);
        },
        stop: function(){
            var me = this;
            clearInterval(me.timer);
            me.timer = null;
        },
        animate: function(){
            var me = this;
            // console.log(me.$t)
            me.$t.animate({
                marginTop: -me.step
            }, 600, function(){
                $(this).css('margin-top', 0);
                me.$t.children().eq(0).appendTo(me.$t);
            });
        },
        updateList: function(){
            var me = this;
            $.get(me.opts.ajaxurl, function(resp){
               // var resp = $.parseJSON(resp);
                if( resp.status == 'ok' ){
                    var lists = resp.list,
                        html = '';
                    $.each(lists, function(i, list){
                        html += '<li><span class="name">' + list.username + '</span><span>' + list.desc + '</span></li>'
                    });
                    me.$t.html(html);
                    me.reset();
                }
            });
        },
        reset: function(){
            var me = this;
            me.$children = me.$t.children();
            me.len = me.$children.length;
            me.$t.css({
                marginTop: 0,
                height: me.len * me.step
            });
        },
        // debug
        debug: function(){      
            this.debugs && window.console && console.log && console.log( '[record] ' + Array.prototype.join.call(arguments, ' ') );
        }
    }

    $.fn.record = function(o) {    
        var instance;
        this.each(function() {              
            instance = $.data( this, 'record' );
            // instance = $(this).data( 'record' );
            if ( instance ) {
                // instance.init();         
            } else {
                instance = $.data( this, 'record', new F(this, o) );          
            }
        });
        return instance;
    }
})(jQuery);

$(function(){
	
    $('.cycle').slide();

    var lottery = $('.lottery-poll').lottery();
    $('.check-result').on('click', function(){
        lottery.showDialog('history');
        return false;
    });

    var record = $('.winner-list ul').record({
        // ajaxTimeout: 20 * 1000
    });
});

//全局未读信件读取(三个模块取)
$(document).ready(function(){
	

	var isDraw = Number($('.lottery-board').find('h3').find('strong').text());
    if(isDraw == 0)
	{
		$('.lottery-button').addClass('onhandled');
		$('.lottery-button').unbind("click");
	}else
	{
		$('.lottery-button').removeClass('onhandled');
	}
	
	//显示余额
	$('#showAllBall').click(function(){
		$('#hiddBall').css("display","inline"); 
		$('#hiddenBall').css("display","none");
	});
	//隐藏余额
	$('#spanBall').click(function(){
		$('#hiddBall').css("display","none");	
		$('#hiddenBall').css("display","inline");
	});		
	
	var noreadmsg2 = "0";	

	try {
		//自动查询此用户未读信件(四处)
		$.ajax({
			type:'post',
			dataType:'json',
			cache:false,
			url:'/service2/queryunreadmessage',				
			data:'',				
			success:function(json){				
				if(json.unreadCnt !=0){																
					var html = "";
					$.each(json.receives, function (i){
                                            html += "<a href=\""+_logOut+"/Service2/sysmessages?id="+ json.receives[i].id +"&msgTpye=uMsg&unread=1&pro=" + json.unreadCnt + "\">"+json.receives[i].sendAccount+"(未读消息"+json.unreadCnt+"笔)"+"</a>";								
					});
					$("#readmsg").html(html);	
					$("#msgcount,#msg-title,#noreadmsg,#msgcount2,#noreadmsg2").html(parseInt(json.unreadCnt));												
					$('#radiuscount').show();
				}
				else {					
					$("#readmsg").html("暂未收到新消息");
					$('#radiuscount').hide();//没有信件事，左菜单小图标隐藏
					$('#msgcount,#msg-title,#noreadmsg,#msgcount2,#noreadmsg2').html("0");	
					$('#readmsg').attr("style","text-align:center; color:black;");			
				}
			},
			error:function(xhr, type){
				
			},
			complete:function(){   }
	   });
	   
	   /* toolbar 整合
	   //游戏说明链接
	   if (typeof phoenix.Games != "undefined" && $('.lottery-link').length > 0){
			var helpLink = _logOut + phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["helpLink"];
				$('.lottery-link').find('.info').attr('href',helpLink);
		}
		*/
	   } catch (err) {		
				console.log("网络异常，读取信件信息失败");
	}
	/*toolbar 整合
	//将数字保留两位小数并且千位使用逗号分隔
	function formatMoney(num){
		var num = Number(num),
			re = /(-?\d+)(\d{3})/;
			
		if(Number.prototype.toFixed){
			num = (num).toFixed(2);
		}else{
			num = Math.round(num*100)/100;
		}
		num  =  '' + num;
		while(re.test(num)){
			num = num.replace(re,"$1,$2");
		}
		return num;  
	};
	
	//金额刷新
	$('.refreshBall').click(function(event){
		var spanBalls = $('#spanBall');
			
		try {
			//用户余额接口
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/gameBet/queryUserBal',			
				data:'',
				beforeSend:function(){						
					 spanBalls.css('font-size','11px').html('查询中...');
					 $('.refreshBall').hide();
				},
				success:function(data){	
					if(data){							
						spanBalls.removeAttr('style').text( formatMoney(Number(data)/10000));
						$('.refreshBall').show();
					}
				},
				complete:function(){
					$('.refreshBall').show();					
				}
			});
		} catch (err) {		
			console.log("网络异常，读取信件信息失败");
		}
		 event.stopPropagation();
	});
        $('.refreshBall').click();
	*/
$('.enabled').click(function(){
	  var me=this,par=$(me).parent(),params;
	  var date=par.find('.date').text();
	  var desc=par.find('.desc').text();
	  params={'date':date,'desc':desc};
	  $.ajax({
			url:'/gamePrize/getDailyBetPrize',
			dataType:'json',
			method:'post',
			cahce:false,
			data:params,
			success:function(data){
				if(data['status'] == 'ok'){
					alert('领取成功');
					$(me).unbind("click");
					$(me).removeClass('enabled').addClass('accepted');
				}else if(data['status'] == 'fail')
				{
					alert('红包已经领取');
					$(me).unbind("click");
					$(me).removeClass('enabled').addClass('accepted');
					
				}else
				{
					alert('领取失败');
				}
			},
			complete:function(){
			}
		});
  });

	
});