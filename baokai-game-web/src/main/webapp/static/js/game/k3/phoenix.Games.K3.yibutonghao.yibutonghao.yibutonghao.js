

//后二直选复式玩法实现类
(function(host, GameMethod, undefined){
	var defConfig = {
		name:'yibutonghao.yibutonghao.yibutonghao',
		//玩法提示
		tips:'猜一个号就中奖',
		//选号实例
		exampleTip: '猜一个号就中奖玩法范例'
	},
	Games = host.Games,
	GameTypes = Games.getCurrentGameTypes(),
	k3 = Games.k3.getInstance(),
	lastBallNum = 0;

	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;

			GameTypes.hiddenTitleDom();
			
			GameTypes.addEvent('beforeChange', function(e, containerDom, modeName){
				var that = this;

				if(modeName == me.defConfig.name){
					that.hiddenTitleDom();
				}
			});
			
			//默认加载执行30期遗漏号码
			me.getHotCold(me.getGameMethodName(), 'currentFre', 'lost');
			//初始化冷热号事件
			me.initHotColdEvent();

			//只选一个球
			//手动选择球去重
			//me.addEvent('afterSetBallData', function(e, x, y, value){
				//me.ensureSoleBall(x, y, value);
			//});

			//面板复位时执行批量选求状态清空
			me.addEvent('afterReset', function(){
				lastBallNum = 0;
			});
		},
		//单行单个选球
		//手动选择球去重
		ensureSoleBall: function(x, y, value){
			var me = this,
				numArray = 0,
				balls = me.getBallData();

			if(Games.getCurrentGame().getCurrentGameMethod().getGameMethodName() == me.getGameMethodName()){
				var checkedNum = me.countBallsNumInLine(0);

				if(value > 0){
					me.setBallData(0, lastBallNum, -1);
					//记录上一次点击的球坐标
					//超出限制则取消最后一次的点击球
					lastBallNum = y;
				}
			}
		},
		//时时彩复式结构为5行10列
		//复位选球数据
		rebuildData:function(){
			var i = 18,
				me = this;

			me.balls = [
					[-1,-1,-1,-1,-1,-1,-1]
				];
		},
		buildUI:function(){
			var me = this;
			me.container.html(html_all.join(''));
		},
		//获取组合结果
		getLottery: function(){
			var me = this,
				ball = me.getBallData(),
				len = ball[0].length,
				i = 0,
				result = [];
				arr = [],
				danMaArr = [],
				allResult = [],
				current = 0,
				nrSave = [],
				nr = [];
			
			//校验当前的面板
			//获取选中数字
			if(me.checkBallIsComplete()){
				for(;i < len;i++){
					if(ball[0][i] > 0){
						current = i;
						nr.push(current);
					}
				}

				/*allResult = me.combination([[1,2,3,4,5,6],[1,2,3,4,5,6],[1,2,3,4,5,6]]);

				for (var i = allResult.length - 1; i >= 0; i--) {
					allResult[i].sort(function(a,b){
						return a-b;
					});
					if(allResult[i].join(',').indexOf(current) != -1){
						if(me.arrIndexOf( allResult[i].join(','), nrSave) == -1){
							nr.push(allResult[i]);
							nrSave.push(allResult[i].join(','));	
						}
					}
				};*/

				return nr;		
			}
			return [];
		},
		//生成单注随机数
		createRandomNum: function(){
			var me =this,
				current = [],
				arr = [],
				len = me.getBallData().length,
				rowLen = me.getBallData()[0].length;

			//随机数
			current[0] = [parseInt(Math.random()*(6-1+1)+1)];
			return current;
		},
		makePostParameter: function(original){
			var me = this,
				i = 0,
				len = original.length,
				result = [];
				
			for(;i < len;i++){
				result.push(original[i].join(','));
			}

			return result.join(',');
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
				nrSave = [],
				nr = [],
				original = [];

			current  = me.checkRandomBets();
            nr.push(current);
			original = current;

			/*allResult = me.combination([[1,2,3,4,5,6],[1,2,3,4,5,6],[1,2,3,4,5,6]]);
			for (var i = allResult.length - 1; i >= 0; i--) {
				allResult[i].sort(function(a,b){
					return a-b;
				});
				if(allResult[i].join(',').indexOf(current) != -1){
					if(me.arrIndexOf( allResult[i].join(','), nrSave) == -1){
						nr.push(allResult[i]);
						nrSave.push(allResult[i].join(','));	
					}
				}
			};*/
			lotterys = nr;

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
			html_row.push('<ul class="ball-content caiyihao">');
			$.each([0,1,2,3,4,5,6], function(i){
				var numCurrent = this;
				if(i==0){
					html_row.push('<li style="display:none"><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">' + numCurrent + '</a><span class="ball-aid-hot">0</span></li>');
				}else{
					html_row.push('<li><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">' + numCurrent + '</a><span class="ball-aid-hot">0</span></li>');			
				}
			});
			html_row.push('</ul>');
			html_row.push('<div class="ball-control" style="<#=style#>">');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=small" href="javascript:void(0);">小</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=odd" href="javascript:void(0);">单</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=even" href="javascript:void(0);">双</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=big" href="javascript:void(0);">大</a>');
			html_row.push('</div>');
		html_row.push('</li>');
	var html_bottom = [];
		html_bottom.push('</ul>');
		html_bottom.push('</div>');
		//拼接所有
	var html_all = [],rowStr = html_row.join('');
		html_all.push(html_head.join(''));
		$.each(['选号区'], function(i){
			html_all.push(rowStr.replace(/<#=title#>/g, this).replace(/<#=row#>/g, i).replace(/<#=style#>/g, "display:none"));
		});
		html_all.push(html_bottom.join(''));
		
	
	
	//继承GameMethod
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器实例上
	k3[defConfig.name] = new Main();
	
})(phoenix, phoenix.GameMethod);

