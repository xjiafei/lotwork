;(function($){
	var userid=getParam("userid");
	var userdevice=getParam("device");
    function F(t, o){
        this.opts = $.extend({
        	ajaxurl: '/sheepYear/dice',
        	animationImg: '',
            frequency: 300, //骰子跳动频率
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
        	me.$diceAmount   = $('[data-amount="dice"]');
        	me.$diceContinus = $('#dice-win-continus');
        	me.$history      = $('#dice-history tbody');
        	me.$buttons      = $('.dice-desktop .dice-button');
        	me.$dices    	 = $('#dice-table .dice-ball');
        	me.$dialog 		 = $('#dice-modal');
        	me.$shadow 		 = $('#overlay-shadow');
            //单骰子动画
            me.singleDiceAnimate = null;
            //骰子循环动画
            me.groupDiceAnimate = null;

        	me.winDesc = ['哇塞！猜中了', '人品真好，猜中啦', '赌神附体 猜中了！', '幸运女神到你家了 猜中！'];
        	me.loseDesc = ['幸运女神在隔壁', '革命尚未成功 同志仍需努力', '别灰心，好运就在下一次', '韬光养晦 下次就中'];

        	me.randomPosition(false);
        	me.bindEvent();
       	},
       	bindEvent: function(){
       		var me = this;
       		me.$buttons.on('click', function(){
       			if( $(this).hasClass('disabled') ) return false;
       			me.$buttons.addClass('disabled');
       			var diceType = '';
       			if( $(this).hasClass('dice-button-big') ){
       				diceType = 'big';
       			}else{
       				diceType = 'small';
       			}
                $(this).parent().addClass('process-'+diceType);
       			me.goDice(diceType);
       		});
            me.$dialog.on('click.www', '[data-modal="close"]', function(event) {
                me.$buttons.eq(0).parent().removeClass('process-big process-small');
            });    
       	},
       	randomPosition: function(init, param){
       		var me = this;
       		if( init ){
       			var posArr = [[160,145],[130,190],[190,190]],
       				rands = [0,1,2];
       		}else{
       			var posArr = [[60,60],[110,60],[160,60],[110,110],[60,130],[110,130],[160,130]],
       				rands = me.randomBelle(3, posArr.length-1, 0);
       		}

            if(param){
                return posArr;
            }else{
                // console.log(rands)
                me.$dices.each(function(i){
                    var pos = posArr[rands[i]];
                    $(this).css({
                        left: pos[0],
                        top : pos[1]
                    });
                });
            }	
       	},
        loadAnimationImg: function(){
        	$('<img/>').load(function(i){  
				// ++l;
				// handleLoading(l/c);
				// if(l == c) o.onComplete();
			}).attr('src',$i.attr('src'));
        },
        createHistory: function(data){ 
		   if(data.status=="ok")
		   {       	
				var html = '<tr>' +
					'<td class="col-white">' +data.date+ '</td>' +
					'<td>' +
						'<div class="dice-result-icon">' +
							'<span class="dice-' +data.result[0]+ '">' +data.result[0]+ '</span>' +
							'<span class="dice-' +data.result[1]+ '">' +data.result[1]+ '</span>' +
							'<span class="dice-' +data.result[2]+ '">' +data.result[2]+ '</span>' +
						'</div>' +
					'</td>' +
					'<td>' + (data.type == 'big' ? '大' : '小') + '</td>' +
					'<td>￥' +data.winMoney+ '</td>' +
				'</tr>';
				var $h = this.$history;
				$h.find("tr td[colspan='4']").parent().remove();
				if( $h.find('tr').length > 4 ){
					$h.find('tr:gt(3)').remove();
				}
				$h.prepend(html);
			}
        },
        goDice: function(diceType){
        	var me = this;
        	// 执行骰子动画
        	me.diceAnimation('start');
        	// 记录当前时间
        	var startTime = new Date();
        	// 请求数据
        	$.post(me.opts.ajaxurl, {diceType: diceType,'userId':userid,'device':userdevice}).done(function(resp){
//        		var resp = $.parseJSON(resp);                
        		if( resp.status == 'ok' ){
        			var endTime = new Date(),
        				time = endTime - startTime,
        				needTime = 1000;
        			if( time - needTime > 0 ){
        				time = 0;
        			}else{
        				time = needTime - time;
        			}
        			setTimeout(function(){
        				// 开奖结束后的操作
        				me.diceAnimation('stop', resp);
        				me.updateGame(resp);
        			}, time);
        		}else if( resp.status == 'timeout' ){
					me.$dialog.find('.dm-result').html('<li class="dice-show-2">2</li><li class="dice-show-6">6</li><li class="dice-show-4">4</li>');
                    me.$dialog.find('.dm-title').html('欢迎您下次再来！').end().find('.dm-desc').html('活动已经结束');
					// 全局方法，显示弹窗
					showModal( me.$shadow );
					showModal( me.$dialog );
					me.stopAllAnimate();
					setTimeout(function(){
					    me.$buttons.each(function(){
                        var current = $(this);
                        if(current.attr('data-over') == 'false'){
                            $(this).removeClass('disabled');
                        }
                        });
        		    }, 600);
                }else if( resp.status == 'noAward' ){
					me.$dialog.find('.dm-result').html('<li class="dice-show-2">2</li><li class="dice-show-6">6</li><li class="dice-show-4">4</li>');
                    me.$dialog.find('.dm-title').html('感谢您的支持！').end().find('.dm-desc').html('奖品已经抽完啦');
					// 全局方法，显示弹窗
					showModal( me.$shadow );
					showModal( me.$dialog );
					me.stopAllAnimate();
					setTimeout(function(){
					    me.$buttons.each(function(){
                        var current = $(this);
                        if(current.attr('data-over') == 'false'){
                            $(this).removeClass('disabled');
                        }
                        });
        		    }, 600);
                }else if( resp.status == 'fail' ){
					me.$dialog.find('.dm-result').html('<li class="dice-show-2">2</li><li class="dice-show-6">6</li><li class="dice-show-4">4</li>');
                    me.$dialog.find('.dm-title').html('请您再玩一次！').end().find('.dm-desc').html('正在处理中...');
					// 全局方法，显示弹窗
					showModal( me.$shadow );
					showModal( me.$dialog );
					me.stopAllAnimate();
					setTimeout(function(){
					    me.$buttons.each(function(){
                        var current = $(this);
                        if(current.attr('data-over') == 'false'){
                            $(this).removeClass('disabled');
                        }
                        });
        		    }, 600);
                }
        	}).fail(function(xhr, textStatus, errorThrown){
        		me.$dialog.find('.dm-result').html('<li class="dice-show-2">2</li><li class="dice-show-6">6</li><li class="dice-show-4">4</li>');
                    me.$dialog.find('.dm-title').html('请您再玩一次！').end().find('.dm-desc').html('正在处理中...');
					// 全局方法，显示弹窗
					showModal( me.$shadow );
					showModal( me.$dialog );
					me.stopAllAnimate();
					setTimeout(function(){
					    me.$buttons.each(function(){
                        var current = $(this);
                        if(current.attr('data-over') == 'false'){
                            $(this).removeClass('disabled');
                        }
                        });
        		    }, 600);
        	});
        },
        singleAnimateProcess: function(){
            var me = this,
                maxNum = 6;
            if(me.singleDiceAnimate){
                return;
            }
            //单球转动
            me.singleDiceAnimate = setInterval(function(){
                maxNum > 1 ? maxNum-- : maxNum = 6;
                me.$dices.attr('class', 'dice-ball dice-num-' + maxNum);
            }, 100);   
        },
        animateProcess: function(){
            var position, dom, left, currentPosition, rands,
                me = this,
                frequency = me['opts']['frequency'];
            if(me.groupDiceAnimate){
                return;
            }
            //变动位置
            me.groupDiceAnimate = setInterval(function(){
                position = me.randomPosition('', 'param'),
                rands = me.randomBelle(3, position.length-1, 0),
                 //开启骰子转动
                me.singleAnimateProcess();
                me.$dices.each(function(i){
                    (function(s){
                        dom = me.$dices.eq(s);
                        currentPosition = position[rands[s]];
                        left = Number(currentPosition[0]-dom.position().left);
                        //跳起
                        dom.animate({
                                'top': currentPosition[1] - 50,
                                'left': dom.position().left + left * 0.8
                            }, frequency * 0.28, function() {
                                dom = me.$dices.eq(s);
                                currentPosition = position[rands[s]];
                                left = Number(currentPosition[0]-dom.position().left);
                                //落下
                                dom.animate({
                                    'top': currentPosition[1],
                                    'left': dom.position().left + left
                                }, frequency * 0.3);
                        });
                    })(i);
                });
            }, frequency);   
        },
        stopAllAnimate: function(){
            var me = this;

            clearInterval(me.singleDiceAnimate);
            clearInterval(me.groupDiceAnimate);
            me.singleDiceAnimate = null;
            me.groupDiceAnimate = null;
        },
        diceAnimation: function(type, resp){
        	var me = this;
        	if( type == 'start' ){
                //开始循环动画
        		me.animateProcess();
        	}else if( type == 'stop' ){
        		var balls = resp.result;
        		if( balls ){
                    me.stopAllAnimate();
        			me.$dices.each(function(i){
        				$(this).attr('class', 'dice-ball dice-num-' + balls[i]);
        			});
        		}        		
        		setTimeout(function(){
        			me.dialog(resp);
					me.$buttons.each(function(){
                        var current = $(this);
                        if(current.attr('data-over') == 'false'){
                            $(this).removeClass('disabled');
                        }
                    });
        		}, 600);
        	}
        },
        updateGame: function(data){
        	var me = this;
        	me.$diceAmount.text(data.diceAmount);
        	me.$diceContinus.text(data.diceContinus >= 8 ? 0 : data.diceContinus);
        	me.createHistory(data);
        	if( data.diceAmount < 1 ){
        		me.$buttons.attr('data-over', true).addClass('disabled');
        	}else{
        		me.$buttons.attr('data-over', false).removeClass('disabled');
        	}
        },
        dialog: function(resp){
        	var me = this, st = '', title = '', desc = '';
        	// 连赢8次
        	if( resp.diceContinus >= 8 && resp.diceStatus == 'win'){
        		st = 'win';
        		title = '连胜8次,赌神非你莫属！';
        		desc = '获得赌神奖励￥' +resp.winMoney+ '奖金';
        	}else if( resp.diceStatus == 'win' ){
        		st = 'win';
        		var idx = parseInt( Math.floor( Math.random() * me.winDesc.length ) );
        		title = me.winDesc[idx];
        		desc = '获得奖励￥' +resp.winMoney+ '奖金';
        	}else{
        		st = 'lose';
        		var idx = parseInt( Math.floor( Math.random() * me.winDesc.length ) );
        		title = me.loseDesc[idx];
        		desc = '获得奖励￥' +resp.winMoney+ '奖金';
        	}
        	if( st == 'win' ){
        		me.$dialog.addClass('dice-win');
        	}else{
        		me.$dialog.removeClass('dice-win');
        	}
        	me.$dialog.find('.dm-result').html(me.showResult(resp));
            me.$dialog.find('.dm-title').html(title).end()
        		.find('.dm-desc').html(desc);
        	// 全局方法，显示弹窗
        	showModal( me.$shadow );
       		showModal( me.$dialog );
        },
        //输出中奖结果
        showResult: function(resp){
            var me = this,
                result = resp['result'],
                html = '';
            for (var i = 0; i < result.length; i++) {
               html += '<li class="dice-show-'+result[i]+'">' + result[i] + '</li>';
            };
            return html;
        },
        randomBelle :function(count, maxs, mins){
			var numArray = new Array();
			//getArray(4,27,0); //4是生成4个随机数,27和0是指随机生成数是从0到27的数
			function getArray(count, maxs, mins){
			  while(numArray.length < count){
			       var temp = getRandom(maxs,mins);
			       if(!search(numArray,temp)){
			            numArray.push(temp);
			       }
			  }
			  //alert("生成的数组为:"+numArray);
			  return numArray;
			}  
			function getRandom(maxs, mins){  //随机生成maxs到mins之间的数
			 return Math.round(Math.random()*(maxs-mins))+mins;
			}
			function search(numArray, num){   //array是否重复的数
			  for(var i=0; i<numArray.length; i++){
			       if(numArray[i] == num){
			            return true;
			       }    
			  }
			  return false;
			}
			return getArray(count, maxs, mins);
		},
        // debug
        debug: function(){      
            this.debugs && window.console && console.log && console.log( '[diceGame] ' + Array.prototype.join.call(arguments, ' ') );
        }
    }

    $.fn.diceGame = function(o) {    
        var instance;
        this.each(function() {              
            instance = $.data( this, 'diceGame' );
            // instance = $(this).data( 'diceGame' );
            if ( instance ) {
                // instance.init();         
            } else {
                instance = $.data( this, 'diceGame', new F(this, o) );          
            }
        });
        return instance;
    }
})(jQuery);


$(function(){
	$('#game2').diceGame();
});