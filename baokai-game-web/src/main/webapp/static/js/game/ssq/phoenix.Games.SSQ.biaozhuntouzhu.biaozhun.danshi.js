(function(host, Danshi, undefined) {
	var defConfig = {
			name: 'biaozhuntouzhu.biaozhun.danshi',
			//父容器
			UIContainer: '#J-balls-main-panel'
		},
		Games = host.Games,
		SSQ = Games.SSQ.getInstance();


	//定义方法
	var pros = {
		init: function(cfg) {
			var me = this;
			//建立编辑器DOM
			//防止绑定事件失败加入定时器
			setTimeout(function() {
				me.initFrame();
			}, 25);
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
		checkNumSameIndex: function(num, index) {
			var me = this,
				result,
				sameNum = 0,
				arr = num.length > 0 ? num : [];

			for (var i = 0; i < arr.length; i++) {
				sameNum = me.arrIndexOf(arr[i], arr);

				if (sameNum >= index) {
					result = sameNum;
					break;
				}
			}

			return result || -1;
		},
		//检测红球蓝球的合法性
		//红球是否在1-33的序列
		//蓝球是否在1-16的序列
		checkBalls: function(redBall, blueBall){
			var result = true;

			if(redBall && blueBall) {
				//红球
				for (var i = redBall.length - 1; i >= 0; i--) {
					var num = Number(redBall[i]);

					if(num < 1 || num > 33) {
						result = false;
						break;
					}
				};
				//蓝球
				if(Number(blueBall[0]) < 1 || Number(blueBall[0]) > 16) {
					result = false;
				}
			}
			
			return result;
		},
		//检测选球是否完整，是否能形成有效的投注
		//并设置 isBallsComplete 
		checkBallIsComplete: function(data) {
			//去除中文 && 全角符号 && 英文字符
			var me = this,
				i = 0,
				current = [],
				//号码STRING状态
				//用来做选号对比
				currentText = '',
				redBallNum = '',
				blueBallNum = '',
				result = [];

			me.aData = [];
			me.sameData = [];
			me.errorData = [];
			me.tData = [];
			me.vData = [];

			//按规则进行拆分结果
			result = me.iterator(me.filterLotters(data)) || [];

			//判断结果
			for (; i < result.length; i++) {

				//红球
				redBallNum = result[i].replace(/[+;|；：:]+.*$/, '');
				//蓝球
				blueBallNum = [result[i].replace(/^.*[+;|；：:]+/, '')];
				//红球排序
				redBallNum = redBallNum.split(/[.]+|[,]+|[。]+|[，]+/).sort(function(a, b) {
					return a - b;
				});

				//校验红篮球
				//是否符合单式规则
				//判断是否为数字
				//判断红篮球的长度
				//判断红球是否有重复号码
				if (me['defConfig']['checkNum'].test(redBallNum.join('')) 
					&& me['defConfig']['checkNum'].test(blueBallNum.join('')) 
					&& redBallNum.length == 6 
					&& blueBallNum.length == 1 
					&& me.checkNumSameIndex(redBallNum, 2) == -1
					&& me.checkBalls(redBallNum, blueBallNum)) {

					//双色球单注数组
					current = [redBallNum, blueBallNum];
					//当前选求的string格式化
					currentText = current.join('');

					if (me.checkResult(currentText, me.tData)) {
						//正确结果
						//加入结果集
						me.tData.push(current);
					} else {

						if (me.checkResult(currentText, me.sameData)) {
							//正确结果[已去重]
							me.sameData.push(current);
						}
					}

					//正确结果[不去重]
					me.vData.push(current);
				} else {

					//错误代码过滤空格字符
					if($.trim(result[i]) != '') {

						if (me.checkSingleResult(result[i], me.errorData)) {
							//错误结果
							//加入错误列表
							me.errorData.push(result[i]);
						} else {
							//错误结果[已去重]
							me.sameData.push(result[i]);
						}
					}					
				}

				//所有结果
				//去除重复结果
				if (me.checkSingleResult(result[i], me.aData)) {
					me.aData.push(result[i]);
				}
			}

			//返回结果集列表数据
			if (me.tData.length > 0) {

				//添加正确标识
				me.isBallsComplete = true;
				//是否是第一次提交
				//此处用于计算全部注数
				//如果非初次提交则只计算正确注数结果
				if (me.isFirstAdd) {
					//返回全部结果
					return me.vData;
				} else {
					//返回正确结果
					return me.tData;
				}
			} else {

				//添加错误标识
				me.isBallsComplete = false;
				return [];
			}
		},
		//检测结果重复
		checkSingleResult: function(data, array){
			//检查重复
			for (var i = array.length - 1; i >= 0; i--) {
				if(array[i] == data){
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
		//生成后端参数格式
		makePostParameter: function(data, order){
			var me = this,
				result = [],
				data = order['lotterys'],
				i = 0;

			for (; i < data.length; i++) {
				for(var j = 0;j < data[i][0].length; j++) {	
					data[i][0][j] = me.formatNum(data[i][0][j]);
				}
				data[i][1][0] = me.formatNum(data[i][1][0]);
			
				result = result.concat(data[i][0].join(',') + '+' +  data[i][1].join(''));
			}
			
			return result.join(';');
		},
		//生成单注随机数
		createRandomNum: function() {
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
		randomNum: function() {
			var me = this,
				i = 0, 
				current = [], 
				currentNum, 
				ranNum,
				order = null,
				dataNum = me.getBallData(),
				name = me.defConfig.name,
				lotterys = [],
				original = [],
				originalBall = [[],[]];
			
			//增加机选标记
			me.addRanNumTag();
			current  = me.checkRandomBets();
			original = current;
			lotterys = [original];


			//过滤单式原始求格式
			for (var i = original[0].length - 1; i >= 0; i--) {
				originalBall[0][i] = Number(original[0][i]);
			}
			originalBall[1][0] = Number(original[1][0]);

			//注单信息
			order = {
				'type':  'biaozhuntouzhu.biaozhun.danshi',
				'original': originalBall,
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
	};


	//继承Danshi
	var Main = host.Class(pros, Danshi);
	Main.defConfig = defConfig;
	//将实例挂在游戏管理器上
	SSQ[defConfig.name] = new Main();



})(phoenix, phoenix.Games.SSQ.Danshi);