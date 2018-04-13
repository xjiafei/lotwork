
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
		//元/角模式比例  1为元模式 0.1为角模式 0.01为分模式 // richardgong 2015-06-26 设置初始值 
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

			me.initControl(cfg);
		},
		initControl: function(cfg){
			var me = this;
			Games.setCurrentGameStatistics(me);
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
		
	};
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host[name] = Main;
	
})(phoenix, "GameStatistics", phoenix.Event);










