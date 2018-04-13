$(document).ready(function(){

	//游戏公共访问对象
	var Games = phoenix.Games;
		//游戏实例
		phoenix.Games.FFC.getInstance();
		//游戏玩法切换
		GamesTypesObject  = phoenix.GameFFCTypes.getInstance();
		//统计实例
		phoenix.GameStatistics.getInstance();
		//号码篮实例
		var	GamesOrderObject = phoenix.GameOrder.getInstance();
		//追号实例
		phoenix.GameTrace.getInstance();
		//提交
		var	GameSubmit = phoenix.GameSubmit.getInstance();
		//消息类
		phoenix.Games.FFC.Message.getInstance();
	

	//遮罩,是否设有此彩种奖金组
	var mask = phoenix.Mask.getInstance(),		
		Msg = Games.getCurrentGameMessage();
		
		//奖金组情况
		Games.getCurrentGame().bonusGroupProce();
		Games.getCurrentGame().isLotteryStopSale(); 	
		
		//游戏配置
		$.ajax({
			url:Games.getCurrentGame().getGameConfig().getInstance().getDynamicConfigUrl(),
			dataType:'json',
			async:false,
			cache:false,
			success:function(data){
				if(Number(data['isSuccess']) == 1){
	
					//由于后端只能返回ARRAY形式的DATA对象结构
					//所以前端进行数据结构更改将数组改为KEY VALUE形式存储
					data['data']['gamelimit'] = data['data']['gamelimit'][0];
					//设置配置
					Games.getCurrentGame().setDynamicConfig(data['data']);
				}
			}
		});
		try
		{
			//头部模块数据处理
			var SscLotteryLogoPath = staticFileContextPath + Games.getCurrentGame().getGameConfig().getInstance().getLotteryLogoPath(),
				frcid = Games.getCurrentGame().getGameConfig().getInstance().defConfig["userName"];

			$('#userName').html(frcid);
			
			Games.getCurrentGameSubmit().balanceInquiry('spanBall');
			
			setInterval(postDynamicConfig, 30000);				
			
		}catch(err)
		{
		  alert("网络异常，读取信息失败");
		}
		
		//游戏配置
		function postDynamicConfig(){	
			new phoenix.GameSubmit().afterSubmitSuccess();	
			Games.cacheData['frequency'] = {};
			Games.getCurrentGame().getCurrentGameMethod().updataGamesInfo2();
		}		
		
		
		//当最新的配置信息和新的开奖号码出现后，进行界面更新
		Games.getCurrentGame().addEvent('changeDynamicConfig', function(e, cfg){
			var lastballs = cfg['lastballs'].split(',');
			//当前期号
			$('#J-lotter-info-number').text(cfg['number']);
			//上期期号
			$('#J-lotter-info-lastnumber').text(cfg['lastnumber']);
			//上期开奖号码
			$('#J-lotter-info-balls').find('em').each(function(i){
				this.innerHTML = lastballs[i];
			});
			
			
			//重新启动/更新新一轮定时器
			topTimer.setStartTime(new Date(cfg['nowtime']));
			topTimer.setEndTime(new Date(cfg['nowstoptime']));
			topTimer.continueCount();
			
		});
		
		
		//顶部用户信息
		new phoenix.Hover({triggers:'#J-top-userinfo',panels:'#J-top-userinfo .menu-nav',hoverDelayOut:300});
		
			
		var topTimer = new phoenix.CountDown({
			//结束时间
			'endTime' : Games.getCurrentGame().getDynamicConfig()['nowstoptime'],
			//开始时间
			'startTime': Games.getCurrentGame().getDynamicConfig()['nowtime'],
			//是否需要循环计时
			'isLoop' : false,
			//是否开启计时矫正
			'isRedress' :true,
			'redressTime' : 15,
			//需要渲染的DOM结构
			'showDom' : '.deadline-number',
			expands:{
				//覆盖showTime渲染方法
				_showTime:function(time){
					var me = this,
						dom = $(me.defConfig.showDom),
						m = me.checkNum(time.m) + '',
						s = me.checkNum(time.s) + '',
						pointerAngle = (60 - parseInt(s)) * 6;
						
						//渲染时间输出
						dom.find('.min-left').text(m.substring(0,1));
						dom.find('.min-right').text(m.substring(1,2));
						dom.find('.sec-left').text(s.substring(0,1));
						dom.find('.sec-right').text(s.substring(1,2));

						me.fireEvent('afterShowTime', time, me);

						if(s <= 6 && s != 0) {
							lightFlash();
						}

						//钟表转动计时
						$('#second-hand').rotate({'angle':pointerAngle});
				},
				redRessTime : function (){
					
					var me = this,
						timeload = null,
						timeMath = new Date().getTime();

					//如果已经存在请求
					//还没有返回则中断
					me.timeload  && me.timeload.abort();

					me.timeload = $.ajax({
						url:  Games.getCurrentGame().getGameConfig().getInstance().lastNumberUrl(),
						type: 'GET',
						cache: false,
						dataType: 'json'
					})
					.done(function(data) {	
						if(data){		
							//更新时间
							if(data['nowtime']){
								//停止计时
								me.stopCount();
								me.setStartTime(new Date(data['nowtime']).getTime() + (new Date().getTime() - timeMath));
								//恢复计时
								me.continueCount();
							}
							if(data['lastballs']){												
								var lastballs = data['lastballs'].split(',');	
								$('#J-lottery-info-lastnumber').html(data['lastnumber']);	
								//上期开奖号码
								$('#J-lottery-info-balls').find('em').each(function(i){
									this.innerHTML = lastballs[i];
								});	
							}							
						}							
							
					})
					.fail(function() {
						
					})
					.always(function() {
						
						me.timeload = null;
					});
				}
			}
		});

		lightFlash = function() {
			var redLightDom = $('#J-red-light');

			setTimeout(function() {
				redLightDom.show();
				setTimeout(function() {
					redLightDom.hide();
				}, 500)
			}, 500);
		};

		//覆盖切换菜单
		//原有事件绑定方法
		//增加菜单hover事件显示子类玩法
		GamesTypesObject._bindTagSelect = function(){
			var tab,
				me = this,
				parentDom = $('#J-play-select'),
				panesParentDom = $('.play-select-content'),
				titleListDom = $('.play-select-title > li'),
				panesListDom = $('.play-select-content > li');

			tab = new phoenix.Tab({
				par : '#change',
				triggers : '.play-select-title > li',
				panels : '.play-select-content > li',
				eventType : 'click',
				currPanelClass: 'current'
			});

			me.bigTab = tab;
					
			tab.addEvent('afterSwitch', function(i, s) {
				var dom = this.getTrigger(s),
					name = $('#change .play-select-content .'+ dom.attr('class').replace(/\s.*/g, '') + ' dd:first');
					
				me._getMode(name);
			});

			$('#change .play-select-content').on('click', 'dd',function() {
				
				me._getMode($(this));
			});

			parentDom.mousemove(function(event) {
				var currentDom = event.target,
					indexNum = 0,
					panesDom,
					marginLeftNum,
					positionLeftNum;


				if($(currentDom).parent().hasClass('play-select-title') &&
					currentDom.nodeName.toLowerCase() == 'li' 
					){
					indexNum = $(currentDom).index(),
					panesDom = panesListDom.eq(indexNum),
					marginLeftNum = parseInt($(currentDom).css('marginLeft')),
					positionLeftNum = $(currentDom).position().left + marginLeftNum;

					titleListDom.removeClass('hover');
					panesListDom.hide();
					panesDom.show();
					panesParentDom.css('left', positionLeftNum).show();
				}else if($(currentDom).hasClass('play-select-title')
				 && currentDom.nodeName.toLowerCase() == 'ul'){
					titleListDom.removeClass('hover');
					panesParentDom.hide();
					panesListDom.hide();	
				}
			});

			parentDom.mouseleave(function(event) {
				
				titleListDom.removeClass('hover');
				panesParentDom.hide();
				panesListDom.hide();	
			});

			panesListDom.mouseenter(function(event) {
				var indexNum = $(this).index(),
					currentDom = titleListDom.eq(indexNum);

				currentDom.addClass('hover');
			});
		};
		
		//倒计时提示
		(function(){
			var countDownDom = $('.countdown'),
				arrowwidth = countDownDom.find('a').width(),
				headerLeft = $('.main').offset().left,
				headerHeight = $('.main').offset().top,
				timeoutSave = null;
				
			countDownDom.css('right', headerLeft - arrowwidth);

			$(window).scroll(function(event) {
				if(timeoutSave){
					clearTimeout(timeoutSave);
					timeoutSave = null;	
				}
				timeoutSave = setTimeout(function(){
					arrowwidth = countDownDom.find('a').width(),
					headerLeft = $('.main').offset().left,
					headerHeight = $('.main').offset().top;

					if($(window).scrollTop() > headerHeight){
						countDownDom.show();
					}else{
						countDownDom.hide();
						if(!countDownDom.hasClass('countdown-current')){
							countDownDom.addClass('countdown-current');
						}
					}	
				},30);
			});

			countDownDom.find('a').bind('click', function(){
				if(countDownDom.hasClass('countdown-current')){
					countDownDom.removeClass('countdown-current');
				}else{
					countDownDom.addClass('countdown-current');
				}
			});

			topTimer.addEvent('afterShowTime', function(e, time, me){
				var m = me.checkNum(time.m) + '',
					s = me.checkNum(time.s) + '';

				countDownDom.find('strong').html(m + ':' + s);
			});
		})();
		
		
		
		
		topTimer.addEvent('afterTimeFinish', function(){			
			//奖金组情况同步
			Games.getCurrentGame().bonusGroupProce();
			//更新我的方案及追号数据
			new phoenix.GameSubmit().afterSubmitSuccess();
			
			Msg.show({
				'mask':true,
				'content':'<div class="bd">' +
							  '<div class="pop-waring">' +
							    '<div style="width: 230px;margin:0px auto;">' +
							      '<i class="ico-waring <#=icon-class#>"></i>' +
							      '<div class="pop-text">' +
							        '<p>' + Games.getCurrentGame().getDynamicConfig()['number'] + '期 已截止，</p><p>投注时请注意期号！</p></div>' +
							      '</div>' +
							    '</div>' +
							  	'<div style="width: 150px;margin: 0px auto;height: 30px;line-height: 35px;overflow: hidden;text-align: center;">' +
							  '<strong class="time-count">3</strong> 秒之后自动关闭窗口' +
							  	'</div>' +
							  '</div>',
				'callback': function(){
					var me = this,
						timeCount = null,
						startTime = 3,
						contentDom = me.getContentDom();

					timeCount = setInterval(function() {
					
						contentDom.find('.time-count').text(startTime);

						if(startTime == 0) {	
							Games.getCurrentGame().getServerDynamicConfig(function(){
								
								Msg.hide();
								//奖金组情况同步
								Games.getCurrentGame().bonusGroupProce();
								Games.getCurrentGame().isLotteryStopSale();								
							});				
							
							

							//防止立即投注加锁
							//手动释放解锁功能
							GameSubmit.cancelPostLock();
							clearInterval(timeCount);
							timeCount = null;
						}

						startTime -= 1;
					}, 1000);
				}
			});
			
		});
		
		
		
		
		//我的方案切换tab
		new phoenix.Tab({
					par : '.program-chase',
					triggers : '.program-chase-title li',
					panels : '.program-chase-content li',
					eventType : 'click',
					currPanelClass: 'current'
				});
		
		//默认加载五星直选复式
		Games.getCurrentGameTypes().addEvent('endShow', function() {
			this.changeMode(Games.getCurrentGame().getGameConfig().getInstance().getDefaultMethod());
		});
		
		
		//监听游戏玩法切换前后事件
		//设置加载Loading
		Games.getCurrentGame().addEvent('beforeSwitchGameMethod', function(){
			var dom = $('#J-bet-loading-panel');
			this._loadingtimer = setTimeout(function(){
										phoenix.util.toViewCenter(dom);
										dom.show();
										mask.show();
								}, 500);
							
		});
		Games.getCurrentGame().addEvent('afterSwitchGameMethod', function(){
			clearTimeout(this._loadingtimer);
			$('#J-bet-loading-panel').hide();
			mask.hide();
		});

		
		//玩法说明和示例
		$('.prompt').on('mouseenter', '.example-button', function(){
			$('.example-tip').css({
				top : $(this).position().top + $(this).outerHeight(),
				left : $(this).position().left - $('.example-tip').outerWidth() + $(this).outerWidth()
			}).show();			 	
		}).on('mouseleave', '.example-button', function(){
			$('.example-tip').hide();	
		});
		
		//走势图相关
		(function(){
			var dom = $('#J-game-chart'),		
				gametype = phoenix.Games.getCurrentGameTypes();

			gametype.addEvent('beforeChange', function(e, container, modeName){
				var currentGameMethod = phoenix.Games.getCurrentGame().getCurrentGameMethod();

				if(!dom.is(':hidden')){
					currentGameMethod.getChart(modeName, function(chart){
						dom.html(chart);
					});
				}
			});

			//走势图展开操作
			$('.chart-switch').bind('click', function(){
				var currentGameMethod = phoenix.Games.getCurrentGame().getCurrentGameMethod(),
					modeName = currentGameMethod.getGameMethodName();

				if(!dom.is(':hidden')){
					dom.hide();
					$('#J-header-clock-img').show();
				}else{
					currentGameMethod.getChart(modeName, function(chart){
						dom.html(chart);
					});
					dom.show();
					$('#J-header-clock-img').hide();
				}
			});
		})();
			
		
			
		//将选球数据添加到号码篮
		$('#J-add-order').click(function(){
			Games.getCurrentGameOrder().add(Games.getCurrentGameStatistics().getResultData());
		});
		//机选一注
		$('#randomone').click(function(){
			Games.getCurrentGame().getCurrentGameMethod().randomLotterys(1);
		});
		//机选五注
		$('#randomfive').click(function(){
			Games.getCurrentGame().getCurrentGameMethod().randomLotterys(5);
		});
	
		//清空号码篮
		$('#restdata').click(function(){
			if(Games.getCurrentGameOrder().getTotal()['amount'] <= 0){
				return false;
			}
			Games.getCurrentGameMessage().show({				
				mask:true,				
				title:'温馨提示',				
				content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确认删除号码篮内全部内容吗?</h4></div></div>',				
				confirmIsShow:true,				
				cancelIsShow:true,	
				cancelFun:function(){                   
					Games.getCurrentGameMessage().hide();				
				},			
				confirmFun:function(){	
					Games.getCurrentGameOrder().reSet().cancelSelectOrder();
					//Games.getCurrentGame().getCurrentGameMethod().reSet(); 选球篮不复位
					Games.getCurrentGameMessage().hide();	
				}	
			});	
			
			
		});
		
		//单式上传的删除、去重、清除功能
		$('body').on('click', '.remove-error', function(){
			Games.getCurrentGame().getCurrentGameMethod().removeOrderError();
		}).on('click', '.remove-same', function(){	
			Games.getCurrentGame().getCurrentGameMethod().removeOrderSame();
		}).on('click', '.remove-all', function(){	
			Games.getCurrentGame().getCurrentGameMethod().removeOrderAll();
		});


		//投注按钮操作
		$('body').on('click', '#J-submit-order', function(){
			Games.getCurrentGameSubmit().submitData();				
		});
});