
(function(host, Danshi, undefined){
	var defConfig = {
		name:'housan.zuxuan.hunhezuxuan',
		//父容器
		UIContainer:'#J-balls-main-panel',
		exampleText: '113 264<br />223 184<br />334 270<br />122 629<br />118 235'
	},
	Games = host.Games,
	FFC = Games.FFC.getInstance();
	
	
	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;
			//建立编辑器DOM
			//防止绑定事件失败加入定时器
			setTimeout(function(){
				me.initFrame();
			},25);
		},
		rebuildData:function(){
			var me = this;
			me.balls = [
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
						];
		},
		checkNumSameIndex: function(num, index){
			var me = this,
				result,
				arr = num.length > 0 ? num : [];
			
			for (var i = 0; i < arr.length; i++) {
				if(me.arrIndexOf(arr[i], arr) == index){
					result = true;
					break;
				}
			};
			
			return result || false;
		},
		//检测选球是否完整，是否能形成有效的投注
		//并设置 isBallsComplete 
		checkBallIsComplete:function(data){
			//去除中文 && 全角符号 && 英文字符
			var me = this,
				i = 0,
				result = [];

			if(me['isFirstAdd']){
					me.aData = [];
					me.sameData = [];
					me.errorData = [];
					me.tData = [];
					me.vData = [];

					me.vDataBack = {};
					me.aDataBack = {};
					me.tDataBack = {};
					me.sameDataBack = {};
					me.errorDataBack = {};
					
				//按规则进行拆分结果
				result = me.iterator(me.filterLotters(data)) || [];
				
				//判断结果
				for(;i<result.length;i++){
					var splitData,
						original,
						current;

					original = result[i].split('');
					splitData = result[i].split('').sort(function(a, b){
						return a-b;
					});
					current = splitData.join('');

					if(me.defConfig.checkNum.test(current) 
						&& current.length == me.balls.length
						&& !me.checkNumSameIndex(current, 3)){
						
						//1为正确重复结果 2正确结果[去重]
						(me['vDataBack'][current]) ? me['sameData'].push(original) : me['tData'].push(splitData);
						//create hash
						me['vDataBack'][current] = original;
						//正确结果[不去重]
						me['vData'].push(original);
					}else{

						//1为错误重复结果 2为错误结果[去重]
						(me['errorDataBack'][current]) ? me['sameData'].push(original) : me['errorData'].push(original);
						//错误结果[不去重]
						me['errorDataBack'][current] = original;
					}

					//全部结果
					(!me['aDataBack'][current]) ? me['aData'].push(original) : '';
					//所有结果[不去重]
					me['aDataBack'][current] = original;
				}
			}

			//校验
			if(me.vData.length > 0){
				me.isBallsComplete = true;
				if(me.isFirstAdd){
					return me.vData;
				}else{
					if(me.tData.length > 0){
						return me.tData;
					}else{
						return [];
					}	
				}
			}else{
				me.isBallsComplete = false;
				return [];
			}
		},
		randomNum:function(){
			var me = this,
				i = 0, 
				current = [], 
				currentNum, 
				ranNum,
				lotterys = [],
				order = null,
				original = [],
				dataNum = me.getBallData(),
				len = me.getBallData().length,
				rowLen = me.getBallData()[0].length,
				name = me.defConfig.name;		

			//增加机选标记
			me.addRanNumTag();		

			//生成随机数
			for(;i < len; i++){
				if(i == 2){
					current[i] = me.removeSameNum(current)
				}else{
					current[i] = Math.floor(Math.random() * rowLen);
				}
			}
			current.sort(function(a, b){
				return a > b ? 1 : -1;
			});

			original = [[current.join(',')],[],[]];
			lotterys.push(current);

			//生成投注格式
			order = {
				'type':  Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
				'original':original,
				'lotterys': lotterys,
				'moneyUnit': Games.getCurrentGameStatistics().getMoneyUnit(),
				'multiple': Games.getCurrentGameStatistics().getMultip(),
				'onePrice': Games.getCurrentGameStatistics().getOnePrice(),
				'num': lotterys.length
			};
			order['amountText'] = Games.getCurrentGameStatistics().formatMoney(order['num'] * order['moneyUnit'] * order['multiple'] * order['onePrice']);
			return order;
		}
	};
	
	
	//继承Danshi
	var Main = host.Class(pros, Danshi);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器上
	FFC[defConfig.name] = new Main();
	
	
	
})(phoenix, phoenix.Games.FFC.Danshi);
