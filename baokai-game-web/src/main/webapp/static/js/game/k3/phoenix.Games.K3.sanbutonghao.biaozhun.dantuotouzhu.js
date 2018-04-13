
//后二直选复式玩法实现类
(function(host, GameMethod, undefined){
	var defConfig = {
		name:'sanbutonghao.biaozhun.dantuotouzhu',
		//玩法提示
		tips:'快三三不同号胆拖玩法',
		//选号实例
		exampleTip: '快三三不同号胆拖玩法范例'
	},
	lastBallNum = 0,
	Games = host.Games,
	k3 = Games.k3.getInstance();
	
	
	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;
			
			//默认加载执行30期遗漏号码
			me.getHotCold(me.getGameMethodName(), 'currentFre', 'lost');
			//初始化冷热号事件
			me.initHotColdEvent();

			//胆拖玩法
			//手动选择球去重
			me.addEvent('afterSetBallData', function(e, x, y, value){
				me.ensureSoleBall(x, y, value);
			});

			//面板复位时执行批量选求状态清空
			me.addEvent('afterReset', function(){
				lastBallNum = 0;
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
						if(checkedNum > 2){
							//胆码只能单选投注结果		
							me.setBallData(0, lastBallNum, -1);
						}
						//重置同位置拖码球
						me.setBallData(1, y, -1);
						//记录上一次点击的球坐标
						//超出限制则取消最后一次的点击球
						lastBallNum = y;
					}else{
						if(lastBallNum == y){
							lastBallNum = 0;
						}
						me.setBallData(0, y, -1);
					}
				}
			}
		},
		//时时彩复式结构为5行10列
		//复位选球数据
		rebuildData:function(){
			var me = this;
			me.balls = [
				[-1,-1,-1,-1,-1,-1,-1],
				[-1,-1,-1,-1,-1,-1,-1]
			];
		},
		buildUI:function(){
			var me = this;
			me.container.html(html_all.join(''));
		},
		//检测选球是否完整，是否能形成有效的投注
		//并设置 isBallsComplete 
		checkBallIsComplete: function(){
			var me = this,
				danBall = me.getBallData()[0],
				tuoBall = me.getBallData()[1],
				i = 0,
				len = danBall.length,
				danBallLength = 0,
				tuoBallLength = 0;

			for(;i < len;i++){
				if(danBall[i] > 0){
					danBallLength++;
				}
				if(tuoBall[i] > 0){
					tuoBallLength++;
				}
			}

			if(danBallLength > 0 && tuoBallLength >= 1){
				return me.isBallsComplete = true;
			}
			return me.isBallsComplete = false;
		},
		//获取组合结果
		getLottery: function(){
			var me = this,
				danBall = me.getBallData()[0],
				tuoBall = me.getBallData()[1],
				i = 0,
				danBallNum = 0,
				len = danBall.length,
				danBallarr = [],
				tuoBallarr = [],
				arr = [],
				resultArr = [];

			for(;i < len;i++){
				if(danBall[i] > 0){
					danBallarr.push(i);
				}
			}
			
			for(i = 0;i < len;i++){
				if(tuoBall[i] > 0){
					arr.push(i);
				}
			}

			//两位胆码
			if(danBallarr.length > 1) {
				for(i = 0;i < arr.length;i++){
					resultArr.push(arr[i] + ',' + danBallarr.join(','));
				}
			//一位胆码	
			} else {
				arr = me.combine(arr, 2);
				for(i = 0;i < arr.length;i++){
					resultArr.push(arr[i].join(',') + ',' + danBallarr[0]);
				}
			}

			//校验当前的面板
			//获取选中数字
			if(me.checkBallIsComplete()){
				return arr;
			}
			
			return [];
		},
		makePostParameter: function(original){
			var me = this,
				i = 0,
				len = original.length,
				result = [];

			for(;i < len;i++){
				if(i == 0){
					result.push('D:'+original[i].join(',')+'_T:');
				}else{
					result.push(original[i].join(','));
				}
				/*for (var k = 0; k < current.length; k++) {
					if(i == 0){

					}
				};*/
			}
			
			return result.join('');
		},
		//生成单注随机数
		createRandomNum: function(){
			var me =this,
				current = [[],[]],
				arr = [],
				len = me.getBallData().length,
				rowLen = me.getBallData()[0].length;

			//建立索引数组
			for (var i = rowLen - 1; i >= 0; i--) {
				if(i > 0){
					arr.push(i);
				}
			};	
			//随机胆码
			for(var k=0;k < 1; k++){
				var ranDomNum = Math.floor(Math.random() * arr.length);
				current[0].push([arr[ranDomNum]]);
				arr.splice(ranDomNum, 1);
			};
			//随机拖码
			for(var k=0;k < 2; k++){
				var ranDomNum = Math.floor(Math.random() * arr.length);
				current[1].push([arr[ranDomNum]]);
				arr.splice(ranDomNum, 1);
			};
			current.sort(function(a, b){
				return a-b;
			})
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
			html_row.push('<ul class="ball-content sanbutonghao-dantuo">');
			$.each([0,1,2,3,4,5,6], function(i){
				if(i < 1 || i > 6){
					html_row.push('<li style="display:none"><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a><span class="ball-aid-hot">0</span></li>');
				}else{
					html_row.push('<li><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a><span class="ball-aid-hot">0</span></li>');
				}
			});
			html_row.push('</ul>');
			html_row.push('<div class="ball-control" style="<#=style#>">');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=small&start=1" href="javascript:void(0);">小</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=odd&start=1" href="javascript:void(0);">单</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=even&start=1" href="javascript:void(0);">双</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=big&start=1" href="javascript:void(0);">大</a>');
			html_row.push('</div>');
		html_row.push('</li>');
	var html_bottom = [];
		html_bottom.push('</ul>');
		html_bottom.push('</div>');
		//拼接所有
	var html_all = [],rowStr = html_row.join('');
		html_all.push(html_head.join(''));
		$.each(['胆码', '拖码'], function(i){
			html_all.push(rowStr.replace(/<#=title#>/g, this).replace(/<#=row#>/g, i).replace(/<#=style#>/, 'display:none'));
		});
		html_all.push(html_bottom.join(''));
		
	
	
	//继承GameMethod
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器实例上
	k3[defConfig.name] = new Main();
	
})(phoenix, phoenix.GameMethod);

