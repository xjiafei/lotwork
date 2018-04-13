//五星直选复式玩法实现类
(function(host, GameMethod, undefined){
	var defConfig = {
		name:'biaozhuntouzhu.biaozhun.dantuo'
	},
	danLastBallNum = 0,
	tuoLastBallNum = 0,
	blueLastBallNum = 0,
	Games = host.Games,
	GameMessage = Games.getCurrentGameMessage(),
	SSQ = Games.SSQ.getInstance();

	var danBallsResult = [],
		tuoBallsResult = [],
		blueBallsResult = [];

	var isRandomType = false;

	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;

			// 默认加载执行当前遗漏号码
			// me.getHotCold(me.getGameMethodName(), 'currentFre', 'lost');
			// 初始化冷热号事件
			me.initHotColdEvent();

			/*me.addEvent('beforeSelect', function(){
				if(Games.getCurrentGame().getCurrentGameMethod().getGameMethodName() == me.getGameMethodName()){
					me.reSet();
				}
			});*/
			me.addEvent('afterSetBallData', function(e, x, y, value){
				me.ensureSoleBall(x, y, value);
			});

			//初始化下拉列表控件
			new phoenix.Select({realDom: '#J-tuo-balls-num-choose',isInput:true,expands:{inputEvent:function(){
													var me = this;
													me.getInput().keyup(function(e){
														var v = this.value;
														this.value = this.value.replace(/[^\d]/g, '');
														v = Number(this.value);
														if(v > 11){
															this.value = 11;
														}
														me.setValue(this.value);
													});
												}}});
			new phoenix.Select({realDom: '#J-dantuoblue-balls-num-choose',isInput:true,expands:{inputEvent:function(){
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
				balls = me.getBallData(),
				danBallLength = me.countBallsNumInLine(0),
				tuoBallLength = me.countBallsNumInLine(1),
				blueBallLength = me.countBallsNumInLine(2);

			if(value > 0){
					
				if(x == 0){

					if(tuoBallLength + danBallLength > 12) {
						me.setBallData(0, danLastBallNum, -1);
						GameMessage.show({
							type : 'normal',
							msg : '红球胆码和拖码总个数不能超过12个，且最少需要选择1个胆码',
							mask : 'true',
							time : 3,
							data : {
								tplData:{
									msg : '红球胆码和拖码总个数不能超过12个，且最少需要选择1个胆码'
								}
							}
						});
					}

					if(danBallLength > 5){
						me.setBallData(0, danLastBallNum, -1);
						GameMessage.show({
							type : 'normal',
							msg : '红球胆码不能超过5个',
							mask : 'true',
							time : 3,
							data : {
								tplData:{
									msg : '红球胆码不能超过5个'
								}
							}
						});
					}

					me.setBallData(1, y, -1);
					//记录上一次点击的球坐标
					//超出限制则取消最后一次的点击球
					danLastBallNum = y;
				}

				if(x == 1){						

					if(tuoBallLength > 11 || (tuoBallLength + danBallLength > 12)){
						me.setBallData(1, tuoLastBallNum, -1);
						GameMessage.show({
							type : 'normal',
							msg : '红球胆码和拖码总个数不能超过12个，且最少需要选择1个胆码',
							mask : 'true',
							time : 3,
							data : {
								tplData:{
									msg : '红球胆码和拖码总个数不能超过12个，且最少需要选择1个胆码'
								}
							}
						});
					}
					me.setBallData(0, y, -1);
					//记录上一次点击的球坐标
					//超出限制则取消最后一次的点击球
					tuoLastBallNum = y;
				}

				if(x == 2){						
					if(blueBallLength > 8){
						me.setBallData(2, blueLastBallNum, -1);
						GameMessage.show({
							type : 'normal',
							msg : '蓝球个数不能超过8个',
							mask : 'true',
							time : 3,
							data : {
								tplData:{
									msg : '蓝球个数不能超过8个'
								}
							}
						});
					}
					//记录上一次点击的球坐标
					//超出限制则取消最后一次的点击球
					blueLastBallNum = y;
				}
			}
		},
		// 双色球胆拖玩法为3行，红球33列，蓝球16列
		// 胆拖选球数据
		rebuildData: function(row){
			var me = this;
			var confs = [33,33,16],
				balls = [];
			// 仅复位单行选号
			if( row && confs[row] ){
				me.balls[row] = me.createBallRowData(confs[row]);
				return;
			}
				
			for(i=0,l=confs.length; i<l; i++){
				var k = confs[i];
				balls.push( me.createBallRowData(k) );
			}
			// 生成结果
			// [
			// 	[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
			// 	[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
			// 	[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
			// ]
			me.balls = balls;
		},
		createBallRowData: function(num){
			var i = 0, _balls = [];
			while( i <= num ) {
				// _balls.push(i);
				_balls.push(-1);
				i++;
			}
			return _balls;
		}, 
		buildUI: function(){
			var me = this;
			me.container.html(html_all.join(''));
		},
		
		// 检测选球是否完整，是否能形成有效的投注
		// 并设置 isBallsComplete 
		checkBallIsComplete: function(){
			var me = this,
				balls = me.getBallData(),
				danBalls = balls[0],
				tuoBalls = balls[1],
				blueBalls = balls[2],
				danBallLen = tuoBallLen = blueBallLen = 0;

			// 计算红球胆码数量
			for (var i=0, l=danBalls.length; i<l; i++) {
				if( danBalls[i] > 0 ){
					danBallLen++;
				}
			}
			// 计算红球拖码数量
			for (var i=0, l=tuoBalls.length; i<l; i++) {
				if( tuoBalls[i] > 0 ){
					tuoBallLen++;
				}
			}
			// 计算蓝球数量
			for (var i=0, l=blueBalls.length; i<l; i++) {
				if( blueBalls[i] > 0 ){
					blueBallLen++;
				}
			}
			/*
			**选择1-5个你认为必出的红球（胆码）、
			**至少选择1个拖码，胆码加拖码最少6个，最多12个，
			**至少1个蓝球（最多8个蓝球）
			*/
			// console.log(danBallLen, tuoBallLen, blueBallLen);
			if( danBallLen >= 1 && danBallLen <= 5 &&
				tuoBallLen >= 1 && 
				( danBallLen + tuoBallLen ) >= 6 && ( danBallLen + tuoBallLen ) <= 12 &&
				blueBallLen >= 1 && blueBallLen <= 8
			){
				return me.isBallsComplete = true;
			}
			return me.isBallsComplete = false;
		},

		// 获取组合结果
		getLottery: function(){
			var saveArray = [],
				me = this,
				balls = me.getBallData(),
				danBalls = balls[0],
				tuoBalls = balls[1],
				blueBalls = balls[2];

			danBallsResult = [],
			tuoBallsResult = [],
			blueBallsResult = [];

			if( me.checkBallIsComplete() ){
				// 获取红球胆码
				for (var i=0, l=danBalls.length; i<l; i++) {
					if( danBalls[i] > 0 ){
						danBallsResult.push(i);
					}
				}
				// 获取红球拖码
				for (var i=0, l=tuoBalls.length; i<l; i++) {
					if( tuoBalls[i] > 0 ){
						tuoBallsResult.push(i);
					}
				}
				// 获取蓝球
				for (var i=0, l=blueBalls.length; i<l; i++) {
					if( blueBalls[i] > 0 ){
						blueBallsResult.push(i);
					}
				}
				//红球二维数组组合
				redBallResultArray = me.combine( danBallsResult.concat(tuoBallsResult), 6 );
				//检查是否存在胆码
				for (var i = redBallResultArray.length - 1; i >= 0; i--) {
					var pushMark = false,
						existNum = 0,
						currentArray = redBallResultArray[i];
					for (var i1 = danBallsResult.length - 1; i1 >= 0; i1--) {
						if($.inArray(danBallsResult[i1], currentArray) >= 0){
							existNum++;
						};
						pushMark = existNum == danBallsResult.length ? true : false;
					};
					pushMark && saveArray.push(currentArray);
				}
				//蓝球二维数组组合
				buleBallResultArray = me.combine(blueBallsResult, 1);
				//计算红蓝球二维数组
				//排列组合结果
				//返回组合结果
				return me.combination([saveArray, buleBallResultArray]);
			}
			return [];
		},
		//检测结果重复
		checkResult: function(data, array){
			//检查重复
			for (var i = array.length - 1; i >= 0; i--) {
				if(array[i].join('') == data){
					return false;
				}
			};
			return true;
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
		//计算各种结果
		mathResult: function(sum, nBegin, nEnd){
			var me = this,
				arr = [],
				checkArray = [],
				x,y,z;
				
			for (x=nBegin;x<=nEnd ;x++ ){
				for (y=nBegin;y<=nEnd ;y++ ){
					for (z=nBegin;z<=nEnd ;z++ ){
						if(x == sum && me.arrIndexOf(x, [x,y,z]) != 3
						|| y == sum && me.arrIndexOf(x, [x,y,z]) != 3
						|| z == sum && me.arrIndexOf(x, [x,y,z]) != 3){
						 	var postArray = [x,y,z].sort(function(a, b){
								return a-b;
							});
							if(me.checkResult(postArray.join(''), checkArray)){
								checkArray.push(postArray)
								arr.push([x,y,z]);
							}
						}
					}
				}
			}
			return arr;
		},
		makePostParameter: function(original){
			var me = this;

			// 如果是随机下注（比如机选一注）
			if(isRandomType){	
				var result = [],
					len,
					arr1 = [],
					arr2 = [],
					arr3 = [],
					i = 0;

				//胆码格式
				for(len = original[0].length;i < len;i++){
					arr1.push(me.formatNum(original[0][i]));
				}
				//拖码格式
				for(i = 0,len = original[1].length;i < len;i++){
					arr2.push(me.formatNum(original[1][i]));
				}
				//蓝球格式
				for(i = 0,len = original[2].length;i < len;i++){
					arr3.push(me.formatNum(original[2][i]));
				}

				isRandomType = false;

				return 'D:' + arr1.join(',') + '_T:' + arr2.join(',') + '+' + arr3.join(',');
			} else {
				var result = [],
					len,
					arr1 = [],
					arr2 = [],
					arr3 = [],
					i = 0,
					j = 0,
					k = 0;

				//胆码格式
				for(len = danBallsResult.length;i < len;i++){
					arr1.push(me.formatNum(danBallsResult[i]));
				}
				//拖码格式
				for(len = tuoBallsResult.length;j < len;j++){
					arr2.push(me.formatNum(tuoBallsResult[j]));
				}
				//蓝球格式
				for(len = blueBallsResult.length;k < len;k++){
					arr3.push(me.formatNum(blueBallsResult[k]));
				}	

				return 'D:' + arr1.join(',') + '_T:' + arr2.join(',') + '+' + arr3.join(',');	
			}
		},
		// 生成单注随机数
		createRandomNum: function(){
			var me =this,
				current = [[],[],[]],
				arr = [],
				blueArr = [],
				ballsArray = me.getBallData(), 
				len = ballsArray.length,
				redBallLength = ballsArray[0].length,
				blueBallLength = ballsArray[2].length,
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
		
			for(var k=0;k < 6; k++){
				var ranDomNum = Math.floor(Math.random() * arr.length);
				if(k == 0){
					//随机生成胆码
					current[0].push(arr[ranDomNum]);
				}else{
					//随机生成拖码
					current[1].push(arr[ranDomNum]);
				}
				arr.splice(ranDomNum, 1);
			};

			//红球排序
			current[1].sort(function(a, b){
				return a - b;
			});

			//随机生成蓝球
			current[2].push(blueArr[Math.floor(Math.random() * blueArr.length)]);

			return current;
		},
		// 获取随机数
		randomNum: function(){
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

			isRandomType = true;

			//注单信息
			order = {
				'type':  'biaozhuntouzhu.biaozhun.dantuo',
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
		// 随机红球拖码
		, randomTuoBall: function( num ){
			var me = this,
				ballsArray = me.getBallData(),
				redBallsLength = ballsArray[1].length,
				tempArr = [],
				redBalls = [];

			// 双色球胆拖玩法的红球个数在1~11之间
			if( num < 1 ) num = 1;
			if( num > 11 ) num = 11;

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
				blueBallsLength = ballsArray[2].length,
				tempArr = [],
				blueBalls = [];

			// 双色球胆拖玩法的蓝球个数在1~8之间
			if( num < 1 ) num = 1;
			if( num > 8 ) num = 8;

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
		// 头部
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
		html_head.push('<div class="number-select-content dantuo-number-select-content">');
		//  胆码区
	var html_row_dan = [];
		html_row_dan.push('<div class="dan-ball-section ball-section" style="z-index:3">');
			html_row_dan.push('<div class="ball-title">');
				html_row_dan.push('<strong><#=title#></strong>');
				html_row_dan.push('<span>&nbsp;&nbsp;—&nbsp;&nbsp;&nbsp;请选择1-5个胆码</span>');
			html_row_dan.push('</div>');
			html_row_dan.push('<div class="ball-content">');
				// i初始值, l最大值, a每行个数, s字符串拼接临时变量
				for(var i=0, l=33, a=17, s=''; i<l; i++){
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
				html_row_dan.push(s);
			html_row_dan.push('</div>');
			html_row_dan.push('<div class="ball-control">');
				html_row_dan.push('<a href="javascript:void(0);" class="reset-ball reset-red-ball">清空</a>');
			html_row_dan.push('</div>');
		html_row_dan.push('</div>');

		//  拖码区
	var html_row_tuo = [];
		html_row_tuo.push('<div class="tuo-ball-section ball-section" style="z-index:2">');
			html_row_tuo.push('<div class="ball-title">');
				html_row_tuo.push('<strong><#=title#></strong>');
				html_row_tuo.push('<span>&nbsp;&nbsp;—&nbsp;&nbsp;&nbsp;至少选择1个拖码，胆码加拖码最少6个，最多12个</span>');
			html_row_tuo.push('</div>');
			html_row_tuo.push('<div class="ball-content">');
				// i初始值, l最大值, a每行个数, s字符串拼接临时变量
				for(var i=0, l=33, a=17, s=''; i<l; i++){
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
				html_row_tuo.push(s);
			html_row_tuo.push('</div>');
			html_row_tuo.push('<div class="ball-control">');
				html_row_tuo.push('<select id="J-tuo-balls-num-choose" class="J-ui-select J-balls-num-choose">');
					for(var i=5, l=11; i<=l; i++){
						html_row_tuo.push('<option value="' + i+ '"' + ( i == 5 ? ' selected="selected"' : '' )+ '>' + i+ '</option>');
					}
				html_row_tuo.push('</select>');
				html_row_tuo.push('<a href="javascript:void(0);" class="random-tuo-ball random-color-red">随机拖码</a>');
				html_row_tuo.push('<a href="javascript:void(0);" class="reset-ball reset-tuo-ball">清空</a>');
			html_row_tuo.push('</div>');
		html_row_tuo.push('</div>');

		//蓝球
	var html_row_blue = [];
			html_row_blue.push('<div class="tuodanblue-ball-section ball-section" style="z-index:1">');
			html_row_blue.push('<div class="ball-title">');
			html_row_blue.push('<strong><#=title#></strong>');
			html_row_blue.push('<span>&nbsp;&nbsp;—&nbsp;&nbsp;&nbsp;请选择1-8个蓝球号码</span>');
			html_row_blue.push('</div>');
			html_row_blue.push('<div class="ball-content">');
				// i初始值, l最大值, a每行个数, s字符串拼接临时变量
				for(var i=0, l=16, a=17, s=''; i<l; i++){
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
				html_row_blue.push('<select id="J-dantuoblue-balls-num-choose" class="J-ui-select J-balls-num-choose">');
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

		// 拼接所有
	var html_all = [],
		danRowStr = html_row_dan.join(''),
		tuoRowStr = html_row_tuo.join(''),
		blueRowStr = html_row_blue.join('');
		html_all.push(html_head.join(''));
		$.each(['红球胆码区','红球拖码区','蓝球区'], function(i){

			if( i == 0 ){
				html_all.push(danRowStr.replace(/<#=title#>/g, this).replace(/<#=row#>/g, i));
			}else if( i == 1 ){
				html_all.push(tuoRowStr.replace(/<#=title#>/g, this).replace(/<#=row#>/g, i));
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

