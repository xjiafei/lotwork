$(document).ready(function(){

	
		//游戏公共访问对象
		var Games = phoenix.Games;
			//游戏实例
			phoenix.Games.SSC.getInstance();
			//游戏玩法切换
			phoenix.GameTypes.getInstance();
			//统计实例
			phoenix.GameMMCStatistics.getInstance();
			//号码篮实例
			phoenix.GameMMCOrder.getInstance();
			//追号实例
			phoenix.GameTrace.getInstance();
			//提交
			phoenix.GameSubmit.getInstance();
			//消息类
			phoenix.Games.SSC.Message.getInstance();
			
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
	//服务器端输出的游戏配置
	//var serverConfig = config;
	//-------------------------------------------- url彩票投注接口开始 -------------------
	//页面URL彩票数据接口
	Games.getCurrentGame().parseBallData = function(gametype, ballData){
		var me = this,
			order = {},
			ballArray = [],
			current = [],
			gameOrder = Games.getCurrentGameOrder();

		ballArray = ballData.indexOf('_') == -1 ? [ballData] : ballData.split('_');

		for (var i = 0; i < ballArray.length; i++) {
			current = [];
			singel = ballArray[i].split('-');

			for (var k = 0; k < singel.length; k++) {
				current.push(singel[k].split(',')); 
			};

			if(checkBallIntact(gametype, current)) {

				order = {
					'type':  gametype,
					'original': current,
					'lotterys': current,
					'moneyUnit': Games.getCurrentGameStatistics().getMoneyUnit(),
					'multiple': Games.getCurrentGameStatistics().getMultip(),
					'onePrice': Games.getCurrentGameStatistics().getOnePrice(),
					'num': current.length
				};
				order['amountText'] = Games.getCurrentGameStatistics().formatMoney(order['num'] * order['moneyUnit'] * order['multiple'] * order['onePrice']);

				//返回投注信息
				gameOrder.add(order);	
			};
		};			
	};
	
	checkBallIntact= function(gameType, current){
		var gametype = $.trim(gameType),
			gameBallLength = 0;

		switch(gameType) {
			case 'wuxing.zhixuan.fushi':
				gameBallLength = 5;
			break;
			case 'sixing.zhixuan.fushi':
				gameBallLength = 4;
			break;
			case 'housan.zhixuan.fushi':
				gameBallLength = 3;
			break;
			case 'qiansan.zhixuan.fushi':
				gameBallLength = 3;
			break;
			case 'houer.zhixuan.fushi':
				gameBallLength = 2;
			break;
			case 'qianer.zhixuan.fushi':
				gameBallLength = 2;
			break;
			case 'yixing.dingweidan.fushi':
				gameBallLength = 1;
			break;
			default:
			return false; 
		}

		if(current.length != gameBallLength) {
			return false;
		} else {
			for (var i = current.length - 1; i >= 0; i--) {
				if(current[i].join('') == '') {
					return false;
				};
			};
			return true;
		}
	};
	//-------------------------------------------- url彩票投注接口结束 -----------------------------

	//设置配置
	//Games.getCurrentGame().setDynamicConfig(serverConfig);
	//当最新的配置信息和新的开奖号码出现后，进行界面更新
	/*Games.getCurrentGame().addEvent('changeDynamicConfig', function(e, cfg){
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
		
	});*/

	//顶部用户中心
	new phoenix.Hover({
		triggers:'#J-user-center',
		panels:'#J-user-center .menu-nav',
		hoverDelayOut:300
	});
	//顶部用户消息
	new phoenix.Hover({
		triggers:'#J-user-message',
		panels:'#J-user-message .msg-box',
		hoverDelayOut:300
	});			
	
	
	//我的方案切换tab
	new phoenix.Tab({
		par : '.program-chase',
		triggers : '.program-chase-title > li',
		panels : '.program-chase-content > li',
		eventType : 'click',
		currPanelClass: 'current'
	});
	
	
	//默认加载五星直选复式
	Games.getCurrentGameTypes().addEvent('endShow', function() {
		var a = function(){
		
			//格式化URL参数
			Games.getCurrentGame().parseDataFormUrl();
			Games.getCurrentGame().removeEvent('afterSwitchGameMethod', a);
		};
		Games.getCurrentGame().addEvent('afterSwitchGameMethod', a);
		//this.changeMode('housan.zhixuan.fushi');
		this.changeMode(Games.getCurrentGame().getGameConfig().getInstance().getDefaultMethod());
	});


	
	//玩法说明和示例
	$('.prompt').on('mouseenter', '.example-button', function(){
		$('.example-tip').css({
			top : $(this).position().top - 5,
			left : $(this).position().left + $(this).width() + 5,
			'z-index':10000
		}).show();			 	
	}).on('mouseleave', '.example-button', function(){
		$('.example-tip').hide();	
	});
	
	/*
	//走势图相关
	(function(){
		var dom = $('#J-game-chart'),
			gametype = phoenix.Games.getCurrentGameTypes();

		var loadBallChart = function(modeName){
			var modeName = $.trim(modeName);
			//var url = '/gameBet/historyballs?type=' + modeName;
			var url = 'simulatedata/historyballs.php?type=' + modeName;
					

			if(typeof Games.cacheData['charts'] == 'undefined'){
				Games.cacheData['charts'] = {};
			}
			
			//缓存设置
			if(typeof Games.cacheData['charts'][modeName] != 'undefined'){
				
				dom.html(Games.cacheData['charts'][modeName]);
			}else{
				//获取游戏相关数据
				$.ajax({
					url: url,
					dataType: 'json',
					success:function(result){
						if(result['isSuccess'] == 1){
							//添加走势图&&缓存
							dom.html(result['data']['historyBalls']);
							Games.cacheData['charts'][modeName] = result['data']['historyBalls'];
						}else{
							try{
								console.log('服务器异常');
							}catch(e){

							}
						}
					}
				});
			}
		}

		gametype.addEvent('beforeChange', function(e, container, modeName){
			if(!dom.is(':hidden')){
				loadBallChart(modeName);
			}
		});

		//走势图展开操作
		$('.chart-switch').bind('click', function(){
			var modeName = phoenix.Games.getCurrentGame().getCurrentGameMethod().getGameMethodName();

			if(!dom.is(':hidden')){
				dom.hide();
			}else{
				loadBallChart(modeName);
				dom.show();
			}
		});

	})();*/
	
	
	try
	{
		//头部模块数据处理
		var SscLotteryLogoPath = staticFileContextPath + Games.getCurrentGame().getGameConfig().getInstance().getLotteryLogoPath(),
			frcid = Games.getCurrentGame().getGameConfig().getInstance().defConfig["userName"];
		
		$('.logo a').css("background",'url('+SscLotteryLogoPath+')  no-repeat scroll 0 0 rgba(0, 0, 0, 0)');	
		$('#userName').html(frcid);
		
		
		Games.getCurrentGameSubmit().balanceInquiry('spanBall');
	}catch(err)
	{
	  alert("网络异常，读取信息失败");
	}
	
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
		Games.getCurrentGameOrder().reSet().cancelSelectOrder();
		Games.getCurrentGame().getCurrentGameMethod().reSet();
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
		//Games.getCurrentGameSubmit().submitData();				
		Games.MMCBellMethod.startBeting();
	});

	//收集钻石活动初始化
        (!Games.collectDiamonds) && (Games.collectDiamonds = $('body').collectDiamonds().init());

});