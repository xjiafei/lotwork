

//五星直选复式玩法实现类
(function(host, GameMethod, undefined){
	var defConfig = {
		//名称
		name:'biaozhuntouzhu.biaozhun.fushi',
		//玩法提示
		tips:'五星直选复式玩法提示',
		//选号实例
		exampleTip: '五星直选复式范例'
	},
	//记录胆码上次选球号码
	//超出限制则去掉上次选择
	redLastBallNum = 0,
	blueLastBallNum = 0,
	Games = host.Games,
	GameMessage = Games.getCurrentGameMessage(),
	SSQ = Games.SSQ.getInstance();
	
	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;
			
			// 默认加载执行当前遗漏号码
			// me.getHotCold(me.getGameMethodName(), 'currentFre', 'lost');
			//初始化冷热号事件
			me.initHotColdEvent();

			//胆拖玩法
			//手动选择球去重
			me.addEvent('afterSetBallData', function(e, x, y, value){
				
				me.ensureSoleBall(x, y, value);
			});

			//初始化下拉列表控件
			new phoenix.Select({realDom: '#J-red-balls-num-choose',isInput:true,expands:{inputEvent:function(){
													var me = this;
													me.getInput().keyup(function(e){
														var v = this.value;
														this.value = this.value.replace(/[^\d]/g, '');
														v = Number(this.value);
														if(v > 12){
															this.value = 12;
														}
														me.setValue(this.value);
														
													});
												}}});
			new phoenix.Select({realDom: '#J-blue-balls-num-choose',isInput:true,expands:{inputEvent:function(){
													var me = this;
													me.getInput().keyup(function(e){
														var v = this.value;
														this.value = this.value.replace(/[^\d]/g, '');
														v = Number(this.value);
														if(v > 8){
															this.value = 8;
														}
														me.setValue(this.value);
														
													});
												}}});
		},

		//胆拖玩法
		//手动选择球去重
		ensureSoleBall: function(x, y, value){
			var me = this,
				numArray = 0,
				balls = me.getBallData();

			if(Games.getCurrentGame().getCurrentGameMethod().getGameMethodName() == me.getGameMethodName()){
				var redBallLength = me.countBallsNumInLine(0);
				var blueBallLength = me.countBallsNumInLine(1);

				if(value > 0){
					
					if(x == 0){						

						if(redBallLength > 12){
							me.setBallData(0, redLastBallNum, -1);
							GameMessage.show({
								type : 'normal',
								msg : '您选择的红球数超过上限，红球数量范围为 6-12 个',
								mask : 'true',
								time : 3,
								data : {
									tplData:{
										msg : '您选择的红球数超过上限，红球数量范围为 6-12 个'
									}
								}
							});
						}
						//记录上一次点击的球坐标
						//超出限制则取消最后一次的点击球
						redLastBallNum = y;
					}

					if(x == 1){						

						if(blueBallLength > 8){
							me.setBallData(1, blueLastBallNum, -1);
							GameMessage.show({
								type : 'normal',
								msg : '您选择的蓝球数超过上限，蓝球数量范围为 1-8 个',
								mask : 'true',
								time : 3,
								data : {
									tplData:{
										msg : '您选择的蓝球数超过上限，蓝球数量范围为 1-8 个'
									}
								}
							});
						}
						//记录上一次点击的球坐标
						//超出限制则取消最后一次的点击球
						blueLastBallNum = y;
					}
				}
			}
		},

		//时时彩复式结构为5行10列
		//复位选球数据
		rebuildData:function(){
			var me = this;
			me.balls = [
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
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
				ball = me.getBallData(),
				//红球数量
				redBallNum = ball[0].length,
				//蓝球数量
				blueBallNum = ball[1].length,
				//蓝球
				blueBallLength = 0,
				//红球
				redBallLength = 0;

			//计算红球数量
			for (var i = 0; i < redBallNum; i++) {
				if(ball[0][i] > 0){
					redBallLength++;
				}
			}

			//计算蓝球数量
			for (var i = 0; i < blueBallNum; i++) {
				if(ball[1][i] > 0){
					blueBallLength++;
				}
			}

			//选求完成条件：
			//红球大于6位
			//蓝球大于1位
			if(redBallLength >= 6 && blueBallLength >= 1){
				
				return me.isBallsComplete = true;
			}

			//选球不完整
			return me.isBallsComplete = false;
		},

		//获取组合结果
		getLottery: function(){
			var me = this,
				ball = me.getBallData(),
				//红球的数量
				redBallLength = ball[0].length,
				//蓝球的数量
				blueBallLength = ball[1].length,
				//红球的排列结果
				redBallResultArray = [],
				//红球的排列结果
				buleBallResultArray = [];
			
			//校验当前的面板
			//获取选中数字
			if (me.checkBallIsComplete()) {
					
				//计算红球选中个数
				for(var i = 0;i < redBallLength; i++){
					if (ball[0][i] > 0) {
						redBallResultArray.push(i);
					}
				}

				//计算蓝球选中个数
				for(var i = 0;i < blueBallLength; i++){
					if (ball[1][i] > 0) {
						buleBallResultArray.push(i);
					}
				}
				
				//红球二维数组组合
				redBallResultArray = me.combine(redBallResultArray, 6);
				
				//蓝球二维数组组合
				buleBallResultArray = me.combine(buleBallResultArray, 1);

				//计算红蓝球二维数组
				//排列组合结果
				//返回组合结果
				return me.combination([redBallResultArray, buleBallResultArray]);	
			}

			//没有有效的投注
			return [];
		},

		//格式化数字
		//小于10前面加0
		formatNum: function(num){
			var num = Number(num);

			if(num < 10) {
				return '0' + num; 
			}

			return num;
		},

		//生成后端数据交互格式
		makePostParameter: function(original){
			var me = this,
				result = [],
				len,
				arr1 = [],
				arr2 = [],
				i = 0;

			//红球格式
			for(len = original[0].length;i < len;i++){
				arr1.push(me.formatNum(original[0][i]));
			}
			//蓝球格式
			for(i = 0,len = original[1].length;i < len;i++){
				arr2.push(me.formatNum(original[1][i]));
			}

			return arr1.join(',') + '+' + arr2.join(',');
		},

		//生成单注随机数
		createRandomNum: function(){
			var me =this,
				current = [[],[]],
				arr = [],
				blueArr = [],
				ballsArray = me.getBallData(), 
				len = ballsArray.length,
				redBallLength = ballsArray[0].length,
				blueBallLength = ballsArray[1].length,
				rowLen = ballsArray[0].length;

			//建立红球索引数组
			for (var i = redBallLength - 1; i >= 0; i--) {
				if(i > 0){
					arr.push(i);
				}
			};	

			// 建立蓝球索引数组
			for (var i = blueBallLength - 1; i >= 0; i--) {
				if(i > 0){
					blueArr.push(i);
				}
			};	

			//随机生成红球组合
			for(var k=0;k < 6; k++){
				var ranDomNum = Math.floor(Math.random() * arr.length);
				current[0].push(arr[ranDomNum]);
				arr.splice(ranDomNum, 1);
			};

			//红球排序
			current[0].sort(function(a, b){
				return a - b;
			});

			//随机生成蓝球
			current[1].push(blueArr[Math.floor(Math.random() * blueArr.length)]);

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

			//注单信息
			order = {
				'type':  Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
				'original': original,
				'lotterys': lotterys,
				'moneyUnit': Games.getCurrentGameStatistics().getMoneyUnit(),
				'multiple': Games.getCurrentGameStatistics().getMultip(),
				'onePrice': Games.getCurrentGameStatistics().getOnePrice(),
				'num': lotterys.length
			};
			//注单金额
			order['amountText'] = Games.getCurrentGameStatistics().formatMoney(order['num'] * order['moneyUnit'] * order['multiple'] * order['onePrice']);
			
			//返回单注
			return order;		
		}

		// 随机红球
		, randomRedBall: function( num ){
			var me = this,
				ballsArray = me.getBallData(),
				redBallsLength = ballsArray[0].length,
				tempArr = [],
				redBalls = [];

			// 双色球红球个数在6~16之间
			if( num < 6 ) num = 6;
			if( num > 16 ) num = 16;

			// 建立红球索引数组
			for (var i = redBallsLength - 1; i >= 0; i--) {
				if(i > 0){
					tempArr.push(i);
				}
			}

			// 生成随机数
			for( i=0; i<num; i++ ){
				var random = Math.floor(Math.random() * tempArr.length);
				redBalls.push(tempArr[random]);
				tempArr.splice(random, 1);
			}
			redBalls.sort(function(a, b){
				return a - b;
			});
			return redBalls;
		}

		// 随机蓝球
		, randomBlueBall: function( num ){
			var me = this,
				ballsArray = me.getBallData(),
				blueBallsLength = ballsArray[1].length,
				tempArr = [],
				blueBalls = [];

			// 双色球蓝球个数在1~16之间
			if( num < 1 ) num = 1;
			if( num > 16 ) num = 16;

			// 建立蓝球索引数组
			for (var i = blueBallsLength - 1; i >= 0; i--) {
				if(i > 0){
					tempArr.push(i);
				}
			}	

			// 生成随机数
			for( i=0; i<num; i++ ){
				var random = Math.floor(Math.random() * tempArr.length);
				blueBalls.push(tempArr[random]);
				tempArr.splice(random, 1);
			}
			blueBalls.sort(function(a, b){
				return a - b;
			});
			return blueBalls;
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

		//红球
	var html_row_red = [];
		html_row_red.push('<div class="red-ball-section ball-section">');
			html_row_red.push('<div class="ball-title">');
				html_row_red.push('<strong><#=title#></strong>');
			html_row_red.push('</div>');
			html_row_red.push('<div class="ball-content">');
				// i初始值, l最大值, a每行个数, s字符串拼接临时变量
				for(var i=0, l=33, a=11, s=''; i<l; i++){
					var j = i+1, b = j % a;
					if( b == 1 ){
						s += '<ul class="ball-row">'
					}
					if( i == 0 ){
						s += '<li style="display:none;"><a href="javascript:void(0);" data-param="action=ball&value=0&row=<#=row#>" class="ball-number">0</a><span class="ball-aid-hot">0</span></li>';
					}
					if( j < 10 ){
						j = '0' + j;
					}
					s += '<li><a href="javascript:void(0);" data-num="' + j + '" data-param="action=ball&value=' + j +'&row=<#=row#>" class="ball-number">' + j +'</a><span class="ball-aid-hot">' + j + '</span></li>';
					if( b == 0 || parseInt(j) == l ){
						s += '</ul>'
					}
				}
				html_row_red.push(s);
			html_row_red.push('</div>');
			html_row_red.push('<div class="ball-control">');
				html_row_red.push('<select id="J-red-balls-num-choose" class="J-ui-select J-balls-num-choose">');
					for(var i=6, l=12; i<=l; i++){
						html_row_red.push('<option value="' + i+ '"' + ( i == 6 ? ' selected="selected"' : '' )+ '>' + i+ '</option>');
					}
				html_row_red.push('</select>');
				html_row_red.push('<a href="javascript:void(0);" class="random-red-ball random-color-red">随机红球</a>');
				html_row_red.push('<a href="javascript:void(0);" class="reset-ball reset-red-ball">清空</a>');
			html_row_red.push('</div>');
		html_row_red.push('</div>');

		//蓝球
	var html_row_blue = [];
			html_row_blue.push('<div class="blue-ball-section ball-section">');
			html_row_blue.push('<div class="ball-title">');
			html_row_blue.push('<strong><#=title#></strong>');
			html_row_blue.push('</div>');
			html_row_blue.push('<div class="ball-content">');
				// i初始值, l最大值, a每行个数, s字符串拼接临时变量
				for(var i=0, l=16, a=6, s=''; i<l; i++){
					var j = i+1, b = j % a;
					if( b == 1 ){
						s += '<ul class="ball-row">'
					}
					if( i == 0 ){
						s += '<li style="display:none;"><a href="javascript:void(0);" data-param="action=ball&value=0&row=<#=row#>" class="ball-number">0</a><span class="ball-aid-hot">0</span></li>';
					}
					if( j < 10 ){
						j = '0' + j;
					}
					s += '<li><a href="javascript:void(0);" data-num="' + j + '" data-param="action=ball&value=' + j +'&row=<#=row#>" class="ball-number">' + j +'</a><span class="ball-aid-hot">' + j + '</span></li>';
					if( b == 0 || parseInt(j) == l ){
						s += '</ul>'
					}
				}
				html_row_blue.push(s);
			html_row_blue.push('</div>');
			html_row_blue.push('<div class="ball-control">');
				html_row_blue.push('<select id="J-blue-balls-num-choose" class="J-ui-select J-balls-num-choose">');
					for(var i=1, l=8; i<=l; i++){
						html_row_blue.push('<option value="' + i+ '"' + ( i == 1 ? ' selected="selected"' : '' )+ '>' + i+ '</option>');
					}
				html_row_blue.push('</select>');
				html_row_blue.push('<a href="javascript:void(0);" class="random-blue-ball random-color-blue">随机蓝球</a>');
				html_row_blue.push('<a href="javascript:void(0);" class="reset-ball reset-blue-ball">清空</a>');
			html_row_blue.push('</div>');
		html_row_blue.push('</div>');

	var html_bottom = [];
		html_bottom.push('</div>');
		//拼接所有
	var html_all = [],redRowStr = html_row_red.join(''),blueRowStr = html_row_blue.join('');
		html_all.push(html_head.join(''));
		$.each(['红球区','蓝球区'], function(i){

			if(i == 0) {
				html_all.push(redRowStr.replace(/<#=title#>/g, this).replace(/<#=row#>/g, i));
			}else{
				html_all.push(blueRowStr.replace(/<#=title#>/g, this).replace(/<#=row#>/g, i));
			}
			
		});
		html_all.push(html_bottom.join(''));
		
	
	
	//继承GameMethod
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器实例上
	SSQ[defConfig.name] = new Main();
	
})(phoenix, phoenix.GameMethod);

