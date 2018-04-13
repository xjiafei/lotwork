
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
		moneyUnit:$.cookie("moneyUnitDom-record") || 1,
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
	Games = host.Games;

	
	var pros = {
		init:function(cfg){
			var me = this;
			Games.setCurrentGameStatistics(me);
			
			me.panel = $(cfg.mainPanel);
			me.moneyUnit = cfg.moneyUnit;
			me.onePrice = cfg.onePrice;
			me.multiple = cfg.multiple;
			//倍数最大限制
			me.multipleLimit = cfg.multipleLimit;
			me.setMultipleLimit(cfg.multipleLimit);
			//玩法名称
			me.gameMethodName = '';
			//已组合好的选球数据
			me.lotteryData = [];
			
			
			//倍数选择模拟下拉框
			me.multipleDom = new phoenix.Select({realDom:cfg.multipleDom,isInput:true,expands:{inputEvent:function(){
				var me = this;
				me.getInput().keyup(function(e){
					var v = this.value,
						moneyUnitDom = Games.getCurrentGameStatistics().getMoneyUnitDom().getValue(),//元角选中
						multipleLimit = Games.getCurrentGameStatistics().getMultipleLimit();//当前玩法倍数限制
	
					this.value = this.value.replace(/[^\d]/g, '');
					v = Number(this.value);
					if(v < 1){
						this.value = 1;
					}else {
						//元模式时
						if(Number(moneyUnitDom)== 1){
							this.value = v > multipleLimit ? multipleLimit : v;
						}else if(Number(moneyUnitDom)== 3){
							this.value = v > multipleLimit*100 ? multipleLimit*10 : v;
						}else{
							this.value = v > multipleLimit*10 ? multipleLimit*10 : v;
						}						
						
					}
					me.setValue(this.value);
					
				});
			}}});
			me.multipleDom.setValue(me.multiple);
			me.multipleDom.addEvent('change', function(e, value, text){
				var num = Number(value),
					moneyUnitDom = Games.getCurrentGameStatistics().getMoneyUnitDom().getValue(),//元角选中
					maxnum = Games.getCurrentGameStatistics().getMultipleLimit();//当前玩法倍数限制
				if(Number(moneyUnitDom)== 1){//元模式时
					if(num > maxnum){
						num = maxnum;
						this.setValue(num);
					}
				}else if(Number(moneyUnitDom)== 3){//分模式时倍数*100处理
					if(num > maxnum * 100){
						num = maxnum * 100;
						this.setValue(num);
					}
				}else{//角模式时倍数*10处理
					if(num > maxnum * 10){
						num = maxnum * 10;
						this.setValue(num);
					}
				}			
				
				me.setMultiple(num);
				me.updateData({'lotterys':Games.getCurrentGame().getCurrentGameMethod().getLottery(),'original':Games.getCurrentGame().getCurrentGameMethod().getOriginal()}, Games.getCurrentGame().getCurrentGameMethod().getGameMethodName());
				
			});
			
			
			//元角模式模拟下拉框

			me.moneyUnitDom = new host.Select({realDom:cfg.moneyUnitDom});
			//richardgong 2015/08/04
			$(cfg.moneyUnitDom).data('moneyUnitDom-record','true');		
			//在未添加change事件之前设置初始值
			me.moneyUnitDom.setValue(me.moneyUnit);
			me.moneyUnitDom.addEvent('change', function(e, value, text){
				var gamelimit =  Games.getCurrentGame().getDynamicConfig()['gamelimit'][me.gameMethodName]['maxmultiple'],
					gameMoneyUnit = Number(value);
				
				me.setMoneyUnit(Number(value));
				me.updateData({'lotterys':Games.getCurrentGame().getCurrentGameMethod().getLottery(),'original':Games.getCurrentGame().getCurrentGameMethod().getOriginal()}, Games.getCurrentGame().getCurrentGameMethod().getGameMethodName());
				if(gameMoneyUnit == 1){
					me.setMultipleLimit(gamelimit);
				}else if(gameMoneyUnit == 3){
					me.setMultipleLimit(gamelimit,'fms');
				}else{
					me.setMultipleLimit(gamelimit,'jms');
				}
				
				me.multipleDom.setValue(1);//添加change事件之时选择倍数下拉框设置初始值为1
			});
			
			//初始化相关界面，使得界面和配置统一
			me.updateData({'lotterys':[],'original':[]});
			
			
		},
		getMultipleDom:function(){
			return this.multipleDom;
		},
		getMultipleTextDom:function(){
			return $('#J-balls-statistics-multiple-text');
		},
		setMultipleLimit:function(num,obj){
			var me = this,text = '无限制';
			num = Number(num);
			if(isNaN(num)){
				//num = 99999;
				num = (obj == undefined ? 99999 : 999999);
			}
			this.multipleLimit = num;
			if(num < 0){
				this.multipleLimit = (obj == undefined ? 99999 : 999999);
				text = '无限制';
			}else{
				if(obj == 'jms'){
					if(num < 999999){
						text = '' + num*10 + ' 倍';
					}
				}else if(obj == 'fms'){
					if(num < 9999999){
						text = '' + num*100 + ' 倍';
					}
				}else{
					if(num < 99999){
						text = '' + num + ' 倍';
					}
				}
				
			}

			me.getMultipleLimitDom().html(text);			
		},
		getMultipleLimit:function(){
			return this.multipleLimit;
		},
		getMoneyUnitText:function(moneyUnit){
			return this.defConfig.moneyUnitData[''+moneyUnit];
		},
		//更新各种数据
		updateData:function(data, name){
			var me = this,
				cfg = me.defConfig,
				count = data['lotterys'] == undefined ? 0 : data['lotterys'].length,
				price = me.onePrice,
				multiple = me.multiple,
				moneyUnit = me.moneyUnit;
			
			//设置投注内容
			me.setLotteryData(data);
			//设置玩法类型
			me.setGameMethodName(name);
			//设置倍数
			//由于设置会引发updateData的死循环，因此在init里手动设置一次，之后通过change事件触发updateData
			//me.setMultipleDom(multiple);
			//更新元角模式(切换换游戏时调用)
			//me.setMoneyUnitDom(moneyUnit);
			//更新注数
			me.setLotteryNumDom(count);
			//更新总金额
			me.setAmountDom(me.formatMoney(count * moneyUnit * multiple * price));
			
		},
		//获取当前数据
		getResultData:function(){
			var me = this,
				lotterys = me.getLotteryData();
			if(lotterys['lotterys'].length < 1){
				return {};
			}
			return {
					type:me.getGameMethodName(),
					original:lotterys['original'],
					lotterys:lotterys['lotterys'],
					moneyUnit:me.moneyUnit,
					num:lotterys['lotterys'].length,
					multiple:me.multiple,
					//单价
					onePrice:me.onePrice,
					//总金额
					amount:me.formatMoney(lotterys['lotterys'].length * me.moneyUnit * me.multiple * me.onePrice),
					//格式化后的总金额
					amountText:me.formatMoney(lotterys['lotterys'].length * me.moneyUnit * me.multiple * me.onePrice)
				};
		},
		//设置玩法类型
		setGameMethodName:function(name){
			this.gameMethodName = name;
		},
		//设置玩法类型
		getGameMethodName:function(name){
			return this.gameMethodName;
		},
		//设置元角模式
		setMoneyUnit:function(num){
			var me = this;
			me.moneyUnit = num;
		},
		getMoneyUnit:function(){
			return this.moneyUnit;
		},
		getLotteryData:function(){
			return this.lotteryData;
		},
		setLotteryData:function(data){
			var me = this;
			me.lotteryData = data;
		},
		//将数字保留两位小数并且千位使用逗号分隔
		formatMoney:function(num){
			var num = Number(num),
				re = /(-?\d+)(\d{3})/;
				
			if(Number.prototype.toFixed){
				num = (num).toFixed(2);
			}else{
				num = Math.round(num*100)/100
			}
			num  =  '' + num;
			while(re.test(num)){
				num = num.replace(re,"$1,$2");
			}
			return num;  
		},
		//将被格式的金额返回成float型
		ToForamtmoney : function(num){		
			return parseFloat(num.replace(/[^\d\.-]/g, ""));
		},
		//倍数限制dom
		getMultipleLimitDom:function(){
			var me = this,cfg = me.defConfig;
			return me.multipleLimitDom || (me.multipleLimitDom = $(cfg.multipleLimitDom));
		},
		//注数
		getLotteryNumDom:function(){
			var me = this,cfg = me.defConfig;
			return me.lotteryNumDom || (me.lotteryNumDom = $(cfg.lotteryNumDom));
		},
		setLotteryNumDom:function(v){
			var me = this;
			me.getLotteryNumDom().html(v);
		},
		//倍数
		getMultipleDom:function(){
			return this.multipleDom;
		},
		getMultip: function() {
			var me = this;
			return me.multiple;
		},
		setMultipleDom:function(v){
			var me = this;
			me.getMultipleDom().setValue(v);
		},
		setMultiple:function(num){
			this.multiple = num;
		},
		//元角模式
		getMoneyUnitDom:function(){
			return this.moneyUnitDom;
		},
		setMoneyUnitDom:function(v){
			var me = this;
			me.getMoneyUnitDom().setValue(v);
		},
        hidesetMoneyUnitDom: function(){
            this.moneyUnitDom.hide();
        },
		//获取单注金额
		getOnePrice: function() {
			var me = this;
			return me.onePrice;
		},
		//总金额
		getAmountDom:function(){
			var me = this,cfg = me.defConfig;
			return me.amountDom || (me.amountDom = $(cfg.amountDom));
		},
		setAmountDom:function(v){
			var me = this;
			me.getAmountDom().html(v);
		},
		reSet:function(){
			var me = this,cfg = me.defConfig;
			//me.multipleDom.setValue(cfg.multiple);
			//me.moneyUnitDom.setValue(cfg.moneyUnit);
			//复位操作成功
			me.fireEvent('afterStatisReset');
		}
		
		
	};
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host[name] = Main;
	
})(phoenix, "GameStatistics", phoenix.Event);










