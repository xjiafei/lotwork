
(function(host, Danshi, undefined){
	var defConfig = {
		name:'xuanliu.renxuanliuzhongwu.danshi',
		//玩法提示
		tips: '',
		//选号实例
		exampleTip: '',
		exampleText: '03 04 05 06 08 11<br />01 02 06 09 10 11 <br />01 04 05 07 10 11 <br />01 06 08 09 10 11  <br />01 03 06 09 10 11 '
	},
	Games = host.Games,
	LLN115 = Games.LLN115.getInstance();
	
	
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
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
						];
		},
		getOriginal:function(){
			var me = this,balls = me.getBallData(),
				len = balls.length,
				len2 = balls[0].length;
				i = 0,
				j = 0,
				row = [],
				tData = me.getTdata(),
				data = me.getHtml(),
				result = [];

			for(;i < len;i++){
				row = [];
				for(j = 0;j < len2;j++){
					if(balls[i][j] > 0){
						row.push(j);
					}
				}
				result.push(row);
			};
			if(tData.length > 0){
				result[0][0] = me.getTdata().join(',');
			}
			return result;
		},
		//生成单注随机数
		createRandomNum: function(){
			var me =this,
				arr = [],
				current = [],
				saveArray = [],
				lotteryText = '',
				len = me.getBallData().length,
				rowLen = me.getBallData()[0].length;

			//建立索引数组
			for (var i = rowLen - 1; i >= 0; i--) {
				if(i > 0){
					arr.push(i);
				}
			};	
			//随机数
			for(var k=0;k < len; k++){
				var ranDomNum = Math.floor(Math.random() * arr.length);
				saveArray.push(Number(arr[ranDomNum]));
				arr.splice(ranDomNum, 1);
			};
			saveArray.sort(function(a, b){
				return a-b;
			});
			for (var j = 0; j < saveArray.length; j++) {
				var c = saveArray[j];
				if(c < 10){
					lotteryText += ' 0' + c;
				}else{
					lotteryText += ' ' + c;
				}
			};
			current.push([$.trim(lotteryText)]);
			return current;
		},
		//生成一个当前玩法的随机投注号码
		//该处实现复式，子类中实现其他个性化玩法
		//返回值： 按照当前玩法生成一注标准的随机投注单(order)
		randomNum:function(){
			var me = this,
				i = 0, 
				current = [], 
				currentNum, 
				ranNum,
				order = null,
				dataNum = me.getBallData(),
				name = me.defConfig.name,
				lotterys = [],
				original = [];
			
			//增加机选标记
			me.addRanNumTag();

			current  = me.checkRandomBets();
			original = [[current.join('')],[],[],[],[],[]];
			lotterys = current;
				
			//生成投注格式
			order = {
				'type':  Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
				'original':original,
				'lotterys':lotterys,
				'moneyUnit': Games.getCurrentGameStatistics().getMoneyUnit(),
				'multiple': Games.getCurrentGameStatistics().getMultip(),
				'onePrice': Games.getCurrentGameStatistics().getOnePrice(),
				'num': lotterys.length
			};
			order['amountText'] = Games.getCurrentGameStatistics().formatMoney(order['num'] * order['moneyUnit'] * order['multiple'] * order['onePrice']);
			return order;		
		},
		//检测选球是否完整，是否能形成有效的投注
		//并设置 isBallsComplete 
		checkBallIsComplete:function(data){
			var me = this,
				i = 0,
				current = 999,
				saveArray = [],
				result = [];

				me.aData = [];
				me.vData = [];
				me.sameData = [];
				me.errorData = [];
				me.tData = [];

			//按规则进行拆分结果
			result = me.iterator(me.filterLotters(data), me.defConfig.filtration) || [];
			//判断结果
			for(i = 0;i < result.length;i++){
				//当前结果
				current = $.trim(result[i]);
				saveArray = current.match(/\d{2}/g);
				//判断单注规则
				if(/^\d{2}[\s]\d{2}[\s]\d{2}[\s]\d{2}[\s]\d{2}[\s]\d{2}$/.test(current)
					&& !(me.checkBallGroup(saveArray))
					&& !(me.checkArrayInnerSame(saveArray))){
					current = [saveArray[0], saveArray[1], saveArray[2], saveArray[3], saveArray[4], saveArray[5]].sort(function(a, b){
						return a-b;
					})
					current = current.join(' ');
					if(me.checkResult(current, me.tData)){
						//正确结果[已去重]
						me.tData.push(current);
					}else{
						if(me.checkResult(current, me.sameData)){
							//重复结果
							me.sameData.push(current);
						}
					}
					//正确结果[不去重]
					me.vData.push(current);
				}else{
					if(me.checkResult(current, me.errorData)){
						//错误结果[已去重]
						me.errorData.push(current);
					}else{
						//错误重复结果
						me.sameData.push(current);
					}
				}
				//所有结果[已去重]
				if(me.checkResult(current, me.aData)){
					me.aData.push(current);
				}
			}
			//校验
			if(me.tData.length > 0){
				me.isBallsComplete = true;
				if(me.isFirstAdd){
					return me.vData;
				}else{
					return me.tData;	
				}
			}else{
				me.isBallsComplete = false;
				return [];
			}
		}
	};
	
	//继承Danshi
	var Main = host.Class(pros, Danshi);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器上
	LLN115[defConfig.name] = new Main();
	
	
	
})(phoenix, phoenix.Games.LLN115.Danshi);


