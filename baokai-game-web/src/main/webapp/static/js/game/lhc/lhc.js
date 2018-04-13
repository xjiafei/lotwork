$(document).ready(function(){

		//游戏公共访问对象
		var Games = phoenix.Games;
		//游戏实例
		phoenix.Games.LHC.getInstance();
		//游戏玩法切换
		phoenix.GameTypes.getInstance();
		//号码篮实例
		phoenix.GameOrder.getInstance();
		//提交
		phoenix.GameSubmit.getInstance();
		//消息类
		phoenix.Games.LHC.Message.getInstance();
		//统计实例
		phoenix.GameStatistics.getInstance();
		//alert(Games.getCurrentGame().getDynamicConfig());
		//遮罩,是否设有此彩种奖金组
		var mask = phoenix.Mask.getInstance(),
			Msg = Games.getCurrentGameMessage();

		Games.getCurrentGame().bonusGroupProce();
		Games.getCurrentGame().isLotteryStopSale();
		
		if(1!=Games.getCurrentGame().getGameConfig().getInstance().getLhcStatus()){
			Msg.show({
				mask:true,
				title:'温馨提示',
				content:'<div class="bd text-center"><div class="pop-title"><i class="ico-error"></i><h4 class="pop-text">没有六合彩权限，请联系上一级或客服人员</h4></div></div>',
				closeIsShow:true,
				hideClose: true,
				closeFun:function(){
					window.location.href = indexServer+"/index";
				}
			});
		}
		
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

		//当最新的配置信息和新的开奖号码出现后，进行界面更新
		Games.getCurrentGame().addEvent('changeDynamicConfig', function(e, cfg){

			//重新启动/更新新一轮定时器
			topTimer.setStartTime(new Date(cfg['nowtime']));
			topTimer.setEndTime(new Date(cfg['nowstoptime']));
			topTimer.continueCount();
		});


		//顶部倒计时
		var topTimer = new phoenix.CountDown({
			//结束时间
			'endTime': Games.getCurrentGame().getDynamicConfig()['nowstoptime'],
			//开始时间
			'startTime': Games.getCurrentGame().getDynamicConfig()['nowtime'],
			//是否需要循环计时
			'isLoop': false,
			//是否开启计时矫正
			'isRedress' :true,
			'redressTime' : 10,
			//需要渲染的DOM结构
			'showDom': '.deadline-number',
			expands: {
				//覆盖showTime渲染方法
				_showTime: function (time) {
					var me = this,
							dom = $(me.defConfig.showDom),
							d = '' + me.checkNum(time.d),
							h = '' + me.checkNum(time.h),
							m = '' + me.checkNum(time.m),
							s = '' + me.checkNum(time.s);
					if(d > 0){
						var hour = time.h + time.d * 24;
						h = '' + me.checkNum(hour > 99? 99: hour);
					}
					//渲染时间输出
					dom.find('.hour-left').text(h.substring(0, 1));
					dom.find('.hour-right').text(h.substring(1, 2));
					dom.find('.min-left').text(m.substring(0, 1));
					dom.find('.min-right').text(m.substring(1, 2));
					dom.find('.sec-left').text(s.substring(0, 1));
					dom.find('.sec-right').text(s.substring(1, 2));

					me.fireEvent('afterShowTime', time, me);
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
										me.setEndTime(new Date(data['nowstoptime']).getTime() + (new Date().getTime() - timeMath));
										//恢复计时
										me.continueCount();
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
		topTimer.addEvent('afterTimeFinish', function () {
			//定时器结束，当前期结束
			//请求下一期时间
			var Msg = Games.getCurrentGameMessage(),
				oldIssueCode = Games.currentGame.dynamicConfig.number,
				newIssueCode = '';

			Games.getCurrentGame().getServerDynamicConfig(function(){
				newIssueCode=Games.currentGame.dynamicConfig.number;
				Msg.show({
					mask:true,
					title:'温馨提示',
					content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+oldIssueCode+'期已截止，<br>当前期为<strong class="color-red" id="J-lottery-info-newNumbe">' +newIssueCode+ '</strong>期。<br>投注时请注意期号！</h4></div></div>',
					closeIsShow:true,
					closeFun:function(){
						Msg.hide();
					}
				});
			});
			//Games.getCurrentGame().isLotteryStopSale();

		});
		//默认加载五星直选复式
		Games.getCurrentGameTypes().addEvent('endShow', function() {
			//this.changeMode('housan.zhixuan.fushi');
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
				console.log('打开遮罩')
			}, 500);
		});
		Games.getCurrentGame().addEvent('afterSwitchGameMethod', function(){
			clearTimeout(this._loadingtimer);
			$('#J-bet-loading-panel').hide();
			mask.hide();
		});
		//投注按钮操作
		$('body').on('click', '#J-submit-order', function(){
			Games.getCurrentGameSubmit().submitData();
		});

		//中奖滚动
		(function(){
			var ul = $('#J-winningList'),
					li = ul.find('li'),
					height = li.height(),
					timer = null;
			timer = setInterval(fnTimer,3000);
			function fnTimer(){
				ul.find('li').eq(0).animate({
					'margin-top': - height
				},500,function(){
					$(this).clone().removeAttr('style').appendTo(ul);
					$(this).remove();
				});
			};
			//鼠标移入 停止滚动
			ul.hover(function(){
				clearInterval(timer);
			},function(){
				timer = setInterval(fnTimer,3000);
			})
		})()
		
		
		var showWinners = function() {
		$.ajax({
			url: '/gameBet/queryWinsOrder?lotteryid=99701',
			dataType: 'JSON',
			cache: false,
			success: function(data) {
				if (data['status'] == "1") {
					var result = data['data'];
					if (result) { //六合彩
						pushDateMoth(result, '#J-winningList', 'leftfour');
					}
				}
			},
			error: function(data){

			}
		});
		}
		showWinners();
		setInterval(showWinners, 10000);
		//setInterval(showWinners, 5000); 
		var pushDateMoth = function(arrObj,  divHtml, leftLocation) {
			var htmls = '';
			$(divHtml).html('');
			if (!arrObj) {
				return;
			}
			for (var i = 0; i < arrObj.length; i++) {
				htmls += "<li>[" + arrObj[i].userName +"]"+arrObj[i].currentIssue+"<span>&nbsp;&nbsp;贏得" + arrObj[i].amount+ "</span>元</li>";
			}
			$(divHtml).html(htmls);
		}
	});