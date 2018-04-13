
//游戏选球统计，如注数、当前操作金额等
(function(host, name, Event, undefined){
	var defConfig = {
		//主面板dom
		mainPanel:'#J-balls-statistics-panel',
		//倍数限制dom
		multipleLimitDom:'#J-balls-statistics-multipleLimit',
		//注数dom
		lotteryNumDom:'#J-balls-statistics-lotteryNum',
		//倍数
		multipleDom:'#J-balls-statistics-multiple',
		//总金额
		amountDom:'#J-balls-statistics-amount',
		moneyUnitDom:'#J-balls-statistics-moneyUnit',
		//元/角模式比例  1为元模式 0.1为角模式 0.01为分模式
		moneyUnit:1,
		//游戏整体倍数
		allMultiple:1,
		//元角模式对应的中文
		moneyUnitData:{'0.01':'分','0.1':'角','1':'元'},
		//单注价格
		onePrice:2,
		//倍数
		multiple:1,
		//倍数最大限制
		multipleLimit:88
	},
	instance,
	Games = host.Games,
	statistics =  host.GameStatistics;
	
	var pros = {
		initControl: function(cfg){
			var me = this,
				$singelDom = $('#J-counter-single-choose'),
				$allMultipleDom = $('#J-counter-multiple-choose'),
				$switcherDom = $('#J-balls-switcher-moneyUnit');


			Games.setCurrentGameStatistics(me);
			
			me.panel = $(cfg.mainPanel);
			me.moneyUnit = cfg.moneyUnit;
			me.onePrice = cfg.onePrice;
			me.multiple = cfg.multiple;
			me.allMultiple = cfg.allMultiple;

			//倍数最大限制
			me.multipleLimit = cfg.multipleLimit;
			me.setMultipleLimit(cfg.multipleLimit);
			//玩法名称
			me.gameMethodName = '';
			//已组合好的选球数据
			me.lotteryData = [];

			//倍数选择模拟下拉框
			//再未设置change事件前
			//之前设定倍数初始值
			me.setMultiple(me.multiple);
			me.multipleDom = $singelDom.counter({init: me.multiple});
			me.multipleDom.setBindEvent(function(value){
				var num = Number(value),
					moneyUnitDom = Games.getCurrentGameStatistics().getMoneyUnit(),//元角选中
					multipleLimit = Games.getCurrentGameStatistics().getMultipleLimit();//当前玩法倍数限制			

				//元模式时
				if(Number(moneyUnitDom)== 1){
					if(num > multipleLimit){
						num = multipleLimit;
						me.multipleDom.setValue(multipleLimit);
					}
				}else if(Number(moneyUnitDom)== 3){
					if(num > multipleLimit*100){
						num = multipleLimit*100;
						me.multipleDom.setValue(multipleLimit*100);
					} 
				}else{
					if(num > multipleLimit*10){
						num = multipleLimit*10;
						me.multipleDom.setValue(multipleLimit*10);
					}
				}	

				me.setMultiple(num);
				me.updateData({'lotterys':Games.getCurrentGame().getCurrentGameMethod().getLottery(),'original':Games.getCurrentGame().getCurrentGameMethod().getOriginal()}, Games.getCurrentGame().getCurrentGameMethod().getGameMethodName());
			});
			
			//元角模式模拟下拉框
			me.setMoneyUnit(me.moneyUnit);
			me.moneyUnitDom = $switcherDom.switcher({initNum: me.moneyUnit});

			$switcherDom.bind('switcher.change', function(e, value){
				var gameMoneyUnit = Number(value),
					multipleNum = me.multipleDom.getValue(),
					gamelimit =  Games.getCurrentGameStatistics().getMultipleLimit();//当前玩法倍数限制

				me.setMoneyUnit(Number(value));
				me.updateData({'lotterys':Games.getCurrentGame().getCurrentGameMethod().getLottery(),'original':Games.getCurrentGame().getCurrentGameMethod().getOriginal()}, Games.getCurrentGame().getCurrentGameMethod().getGameMethodName());
				if(gameMoneyUnit == 1){
					me.setMultipleLimit(gamelimit);
				}else if(gameMoneyUnit == 3){
					me.setMultipleLimit(gamelimit,'fms');
				}else{
					me.setMultipleLimit(gamelimit,'jms');
				}

				me.multipleDom.setValue(1);
			});

			//整体倍数操作
			me.setAllMultiple(me.allMultiple);
			me.fullMultipleDom = $allMultipleDom.counter({init: me.allMultiple,min: 1});
			me.fullMultipleDom.setBindEvent(function(value){
				var num = Number(value),
					saveCountNum = 0,
					saveModifyArr = [],
					msg = Games.getCurrentGameMessage(),
					maxnum = Games.getCurrentGameStatistics().getMultipleLimit(),
					currentOrderModel = Games.getCurrentGameOrder(),
					overNumArr = [],
					orderData = currentOrderModel.getTotal()['orders'];
					me.setAllMultiple(Number(value));

					if(orderData.length <= 0){
						//初始化到单倍
						me.fullMultipleDom.reset();
						me.setAllMultiple(1);
						
						msg.show({
							type: 'normal',
							msg: '请至少选择一注投注号码！',
							data: {
								tplData: {
									msg: '请至少选择一注投注号码！'
								}
							},
							confirmIsShow: true,
							confirmFun: function(){
								var me = this;
								me.hide();
							},
							cancelIsShow: false,
							closeIsShow: false
						});

						return;
					}

					for (var i = orderData.length - 1; i >= 0; i--) {
						currentOrderModel.editMultiple(orderData[i]['oldMultiple'] * num, i);
					}
					 
					me.updateData({'lotterys':Games.getCurrentGame().getCurrentGameMethod().getLottery(),'original':Games.getCurrentGame().getCurrentGameMethod().getOriginal()}, Games.getCurrentGame().getCurrentGameMethod().getGameMethodName());
				});

			//初始化相关界面，使得界面和配置统一
			me.updateData({'lotterys':[],'original':[]});
			me.initRebate();
		},
		//初始化返点内容
		initRebate:function(){
			var me = this;
			//返点下拉菜单
			var awardRetStatus = phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["awardRetStatus"],
			awardGroupRetStatus = phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["awardGroupRetStatus"],
			rebateSelect = phoenix.Games.getCurrentGameStatistics().rebateSelect;
			if(awardRetStatus && awardGroupRetStatus && !rebateSelect){
				me.rebateSelect = new host.Select({
					realDom:'#J-select-rebate',
					cls:'choose-model-large ui-simulation-select'
				});

			}
		},
		//全部倍数
		setAllMultiple:function(val){
			var me = this;

			me.allMultiple = val;
		},
		//全部倍数
		getAllMultiple:function(){
			return this.allMultiple;
		},
		reSet:function(){
			var me = this,cfg = me.defConfig;
			me.multipleDom.setValue(cfg.multiple);
			//me.moneyUnitDom.setValue(cfg.moneyUnit);
		}
		
	};
	
	var Main = host.Class(pros, statistics);
		Main.defConfig = defConfig;
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host[name] = Main;
	
})(phoenix, "GameMMCStatistics", phoenix.Event);










