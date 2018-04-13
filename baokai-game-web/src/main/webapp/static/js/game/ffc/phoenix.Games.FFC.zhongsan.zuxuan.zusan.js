//五星直选复式玩法实现类
(function(host, GameMethod, undefined){
	var defConfig = {
		name:'zhongsan.zuxuan.zusan'
		
	},
	Games = host.Games,
	//游戏类
	FFC = host.Games.FFC.getInstance();
	
	
	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;

			
		},
		//时时彩复式结构为5行10列
		//复位选球数据
		rebuildData: function(){
			var me = this;
			me.balls = [
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
						];
		},
		buildUI: function(){
			var me = this;
			me.container.html(html_all.join(''));
		},
		makePostParameter: function(original){
			var me = this,
				result = [],
				len = original.length,
				i = 0;
			for (; i < len; i++) {
				result = result.concat(original[i].join(','));
			}
			return result.join('');
		},
		//检测选球是否完整，是否能形成有效的投注
		//并设置 isBallsComplete 
		checkBallIsComplete: function(){
			var me = this,
				ball = me.getBallData(),
				i = 0,
				len = ball[0].length,
				num = 0, oNum = 0;

			for(;i < len;i++){
				if(ball[0][i] > 0){
					oNum++;
				}
			}
			//二重号大于1 && 单号大于3
			if(oNum >= 2){
				return me.isBallsComplete = true;
			}
			return me.isBallsComplete = false;
		},
		//获取组合结果
		getLottery: function(){
			var me = this,
				ball = me.getBallData(),
				i = 0,
				len = ball[0].length,
				saveNum = [],
				checkNum = [],
				result = [];
				arr = [], nr = new Array();
			
			//校验当前的面板
			//获取选中数字
			if(me.checkBallIsComplete()){
				for(;i < len;i++){
					if(ball[0][i] > 0){
						arr.push(i);
					}
				};
				for(var c=0;c<arr.length;c++){
					checkNum =[];
					saveNum = arr.concat();
					checkNum.push([[arr[c],arr[c]].join('')]);
					saveNum.splice(c, 1)
					checkNum.push(saveNum);
					result = result.concat(me.combination(checkNum));
				};

				for (var k = 0; k < result.length; k++) {
					result[k] = result[k].join('').split('');
				};

				return result;		
			}
			return [];
		},
		getLotteryOriginal: function(data){
				var arr = data,
					me = this,
					ball = me.getBallData(),
					i = 0,
					len = ball[0].length,
					saveNum = [],
					checkNum = [],
					result = [];

				for(var c=0;c<arr.length;c++){
					checkNum =[];
					saveNum = arr.concat();
					checkNum.push([[arr[c],arr[c]].join('')]);
					saveNum.splice(c, 1)
					checkNum.push(saveNum);
					result = result.concat(me.combination(checkNum));
				};
				for (var k = 0; k < result.length; k++) {
					result[k] = result[k].join('').split('');
				};

				return result;	
		},
		//单组去重处理
		removeSame: function(data) {
			var i = 0, result, me = this,
				numLen = this.getBallData()[0].length;
				len = data.length;
			result = Math.floor(Math.random() * numLen);
			for(;i<data.length;i++){
				if(result == data[i]){
					return arguments.callee.call(me, data);
				}
			}
			return result;
		},
		//获取随机数
		randomNum: function(){
			var me = this,
				i = 0, 
				current = [], 
				currentNum, 
				ranNum,
				lotterys = [],
				order = null,
				dataNum = me.getBallData(),
				len = me.getBallData()[0].length,
				name = me.defConfig.name;				

			for(;i<2;i++){
				current[i] = me.removeSame(current);
			}
			current.sort(function(a, b){
				return a > b ? 1 : -1;
			});
			lotterys = me.getLotteryOriginal(current);
			order = {
				'type':  Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
				'original':[current],
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
	
		//html模板
	var html_head = [];
		//头部
		html_head.push('<div class="number-select-content">');
		html_head.push('<ul class="ball-section">');
		//每行
	var html_row = [];
			html_row.push('<li>');
			html_row.push('<ul class="ball-content">');
			$.each([0,1,2,3,4,5,6,7,8,9], function(i){
				html_row.push('<li><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a></li>');
			});
			html_row.push('</ul>');
			html_row.push('<div class="ball-control">');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=all" href="javascript:void(0);">全</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=big" href="javascript:void(0);">大</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=small" href="javascript:void(0);">小</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=odd" href="javascript:void(0);">奇</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=even" href="javascript:void(0);">偶</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=none" href="javascript:void(0);">清</a>');
			html_row.push('</div>');
		html_row.push('</li>');
	var html_bottom = [];
		html_bottom.push('</ul>');
		html_bottom.push('</div>');
		//拼接所有
	var html_all = [],rowStr = html_row.join('');
		html_all.push(html_head.join(''));
		$.each([''], function(i){
			html_all.push(rowStr.replace(/<#=title#>/g, this).replace(/<#=row#>/g, i));
		});
		html_all.push(html_bottom.join(''));
		
		
	
	
	//继承GameMethod
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器实例上
	FFC[defConfig.name] = new Main();
	
})(phoenix, phoenix.GameMethod);