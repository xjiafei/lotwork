
//选二任选二中二复式
(function(host, GameMethod, undefined){
	var defConfig = {
		//名称
		name:'xuansi.renxuansizhongsi.dantuo',
		//玩法提示
		tips:'从01-11共11个号码中选择3个不重复的号码组成一注，所选号码与当期顺序摇出的5个号码中的前3个号码相同，且顺序一致，即为中奖。即中1782元。',
		//选号实例
		exampleTip: '选一任选一中一复式'
	},
	//记录胆码上次选球号码
	//超出限制则去掉上次选择
	lastBallNum = 0,
	Games = host.Games,
	N115 = host.Games.N115.getInstance();
	
	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;
			
			//初始化冷热号事件
			me.initHotColdEvent();

			//胆拖玩法
			//手动选择球去重
			me.addEvent('afterSetBallData', function(e, x, y, value){
				me.ensureSoleBall(x, y, value);
			});
		},
		//胆拖玩法
		//手动选择球去重
		ensureSoleBall: function(x, y, value){
			var me = this,
				numArray = 0,
				balls = me.getBallData();

			if(Games.getCurrentGame().getCurrentGameMethod().getGameMethodName() == me.getGameMethodName()){
				var checkedNum = me.countBallsNumInLine(0);

				if(value > 0){
					
					if(x == 0){						

						if(checkedNum > 3){
							me.setBallData(0, lastBallNum, -1);
						}

						me.setBallData(1, y, -1);
						//记录上一次点击的球坐标
						//超出限制则取消最后一次的点击球
						lastBallNum = y;
					}else{
					
						me.setBallData(0, y, -1);
					}
				}
			}
		},
		//复位选球数据
		rebuildData:function(row){
			var me = this;
			//仅复位单行选号
			if(typeof row != 'undefined'){
				me.balls[row] = [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1];
				return;
			}
			me.balls = [
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
						];
		},
		buildUI:function(){
			var me = this;
			me.container.html(html_all.join(''));
		},
		makePostParameter: function(original){
				var me = this,
				result = [],
				current = [],
				saveArray = [],
				danMaArr = [],
				len = original.length,
				i = 0;

			for (; i < len; i++) {
				saveArray = [];
				current = original[i];

				for (var k = 0; k < current.length; k++) {
					
					if(i == 0){
						if(Number(current[k]) < 10){
							danMaArr.push('0' + current[k]);
						}else{
							danMaArr.push(current[k]);
						}
					}else{
						if(Number(current[k]) < 10){
							saveArray.push('0' + current[k]);
						}else{
							saveArray.push(current[k]);
						}
					}
				};

				result.push(saveArray.join(','));
			};

			result.unshift('[胆' + danMaArr.join(',') + ']');

			return result.join(' ');
		},
		//检测选球是否完整，是否能形成有效的投注
		//并设置 isBallsComplete 
		checkBallIsComplete: function(){
			var me = this,
				ball = me.getBallData(),
				i = 0,
				len = ball[0].length,
				num = 0, oNum = 0;

			//总选球大于2
			if(me.countBallsNum() > 3){
				return me.isBallsComplete = true;
			}
			return me.isBallsComplete = false;
		},
		//获取组合结果
		getLottery: function(){
			var me = this,
				ball = me.getBallData(),
				i = 0,
				danMaLen = me.countBallsNumInLine(0),
				len = ball[1].length,
				result = [];
				arr = [],
				danMaArr = [],
				nr = new Array();
			
			//校验当前的面板
			//获取选中数字
			if(me.checkBallIsComplete()){
				for(;i < len;i++){
					if(ball[1][i] > 0){
						arr.push(i);
					}
				}

				//存储单号组合
				result = me.combine(arr, 4 - danMaLen);
				//二重号组合
				for(var i=0,current;i<ball[0].length;i++){
					if(ball[0][i] == 1){
						danMaArr.push(i);	
					}	
				};

				//加上单号各种组合	
				for(var s=0;s<result.length;s++){

					nr.push(result[s].concat(danMaArr));
				}

				return nr;		
			}
			return [];
		},
		//单组去重处理
		removeSame: function(data) {
			var i = 0, result, me = this,
				numLen = this.getBallData()[0].length,
				len = data.length;
			result = Math.ceil(Math.random() * (numLen - 1));
			for(;i<data.length;i++){
				if(result == data[i]){
					return arguments.callee.call(me, data);
				}
			}
			return result;
		},
		//生成单注随机数
		createRandomNum: function(){
			var me =this,
				arr = [],
				current = [],
				allArr = [],
				current2 = [],
				lotteryText = '',
				num = 0,
				len = me.getBallData().length,
				rowLen = me.getBallData()[0].length;

			for(var k=0;k < 4;k++){
				if(k < 1){
					num = me.removeSame(allArr);
					current = current.concat(num);
					allArr.push(num);
				}else{
					num = me.removeSame(allArr);
					current2 = current2.concat(num);
					allArr.push(num);
				}
			}
			current2.sort(function(a, b){
				return a - b;
			});
			current = [current, current2];

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
			
			
			current  = me.checkRandomBets();
			original = current;
			lotterys = [original];

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
		}
	};
	
	//html模板
	var html_head = [];
		//头部
		html_head.push('<div class="number-select-title balls-type-title clearfix">');
			html_head.push('<ul class="function-select-title game-frequency-type">');
				html_head.push('<li class="lost" data-type="lost">遗漏</li>');
				html_head.push('<li class="fre" data-type="fre">冷热</li>');
			html_head.push('</ul>');
			html_head.push('<ul class="function-select-content">');
				html_head.push('<li class="game-frequency-lost-length"><a href="javascript:void(0);" data-type="currentFre" class="periodcurrentFre">当前遗漏</a><a data-type="maxFre" href="javascript:void(0);" class="periodmaxFre">最大遗漏</a></li>');
				html_head.push('<li style="display:none" class="game-frequency-fre-length"><a href="javascript:void(0);" data-type="30" class="period30">30期</a><a href="javascript:void(0);" data-type="60" class="period60">60期</a><a href="javascript:void(0);" data-type="100" class="period100">100期</a></li>');
			html_head.push('</ul>');
		html_head.push('</div>');
		html_head.push('<div class="number-select-content">');
		html_head.push('<ul class="ball-section">');
		//每行
	var html_row = [];
			html_row.push('<li>');
			html_row.push('<div class="ball-title">');
			html_row.push('<strong><#=title#></strong>');
			html_row.push('<span>当前遗漏</span>');
			html_row.push('</div>');
			html_row.push('<ul class="ball-content">');
			$.each([0,1,2,3,4,5,6,7,8,9,10,11], function(i){
				if(i == 0){
					html_row.push('<li style="display:none;"><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a><span class="ball-aid-hot">0</span></li>');	
				}else{
					if(i < 10){
						html_row.push('<li class="arrange"><a href="javascript:void(0);" data-param="action=ball&value=' + '0' + this +'&row=<#=row#>" class="ball-number">' + '0' +this+'</a><span class="ball-aid-hot">0</span></li>');
					}else{
						html_row.push('<li class="arrange"><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a><span class="ball-aid-hot">0</span></li>');
					}
				}
			});
			html_row.push('</ul>');
			html_row.push('<div class="ball-control" style="<#=style#>">');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=all&start=1" href="javascript:void(0);">全</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=big&start=1" href="javascript:void(0);">大</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=small&start=1" href="javascript:void(0);">小</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=odd" href="javascript:void(0);">奇</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=even&start=1" href="javascript:void(0);">偶</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=none" href="javascript:void(0);">清</a>');
			html_row.push('</div>');
		html_row.push('</li>');
	var html_bottom = [];
		html_bottom.push('</ul>');
		html_bottom.push('</div>');
		//拼接所有
	var html_all = [],rowStr = html_row.join('');
		html_all.push(html_head.join(''));
		$.each(['胆码','拖码'], function(i){
			if(this == '胆码'){
				html_all.push(rowStr.replace(/<#=title#>/g, this).replace(/<#=row#>/g, i).replace(/<#=style#>/, 'display:none'));	
			}else{
				html_all.push(rowStr.replace(/<#=title#>/g, this).replace(/<#=row#>/g, i));
			}
			
		});
		html_all.push(html_bottom.join(''));
		
	
	
	//继承GameMethod
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器实例上
	N115[defConfig.name] = new Main();
	
})(phoenix, phoenix.GameMethod);

