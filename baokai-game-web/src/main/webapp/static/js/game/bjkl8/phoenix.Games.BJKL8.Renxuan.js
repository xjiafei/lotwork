

//选一任选二中二复式
(function(host, name, GameMethod, undefined){
	var defConfig = {
		//名称
		name:'renxuan.putongwanfa.renxuan',
		//玩法提示
		tips:'从01-11共11个号码中选择3个不重复的号码组成一注，所选号码与当期顺序摇出的5个号码中的前3个号码相同，且顺序一致，即为中奖。即中1782元。',
		//选号实例
		exampleTip: '选一任选一中六复式'
	}
	Games = host.Games,
	GameMessage = Games.getCurrentGameMessage(),
	BJKL8 = host.Games.BJKL8;
	
	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;

			//复位选号
			me.addEvent('afterReset', function(){
				if(typeof me.resetChartsResult == 'function'){
					me.resetChartsResult();
				}
			})
		},
		//复位选球数据
		rebuildData:function(){
			var me = this;
			me.balls = [
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
						];
		},
		//输出中奖结果[图表]
		showSelectResult: function(){
			var me = this,
				html = '',
				dom = me.container.find('.J-select-result'),
				currentSelectNum = me.countBallsNum();

				//超出限制范围
				if(currentSelectNum < 7 || currentSelectNum > 8){
					dom.html('');
					return;
				}

				var html = me.ballsResultCharts({
					'selectNum': currentSelectNum,
					'mode': '任选7',
					'maxNum': currentSelectNum,
					'money': {'1' : 10000.00, '2': 200.00, '3': 38.00, '4': 6.00, '5': 3.00}
				});

				//渲染输出图表
				dom.html(html);
		},
		showResultCharts: function(){
			var me = this,
				container = me.container,
				contentDom = $('#J-charts-area'),
				Charts = Games.BJKL8.Charts.getInstance();

			Charts.loadData({'count':30, 'type': me.defConfig['name']}, function(){
				setTimeout(function(){
					contentDom.html(Charts.getHtml());
				},0);
			})
		},
		//清空中奖结果列表
		resetChartsResult: function(){
			var me = this,
				html = '',
				dom = me.container.find('.J-select-result');

			dom.empty();
		},
		buildUI:function(){
			var me = this;
			me.container.html(html_all.join(''));
		},
		/*
		* 中奖结果组合列表
		*/
		ballsResultCharts: function(ballData){
			var me = this,
				//html存储
				html = '',
				//投注号码
				lotteryNums = me.getLottery(true),
				//投注注数
				nums = typeof lotteryNums == 'number' ? lotteryNums : lotteryNums.length,
				//当前倍数
				times = Games.getCurrentGameStatistics().getMultip(),
				//游戏类型名称
				mname = ballData['mode'],
				//任选组合数字
				n = parseInt(mname.substring(mname.length - 1, 3), 10),
				//最小限制
				min_n = {1: 1,2: 2,3: 2,4: 2,5: 3,6: 3,7: 4},
				//当前选球数量
				ns = ballData['selectNum'],
				//奖金组
				moneyGroupArray = ballData['money'],
				//缓存
				prizelevels = 0;

			html += '<div class="ball-table-title">您选择的号码可能中奖结果如下：</div>';
				for (var level in moneyGroupArray) {
	                prizelevels++
	            }
	            html += '<table width="100%" cellspacing=1>';
	            html += "<tr><th colspan=" + (prizelevels + 4) + '>玩法：' + mname + "　　　投注号码数：" + ns + "</th></tr>";
	            ns = ns > 20 ? (n == 1 ? 20 : ns) : ns;
	            html += "<tr><td>每注奖金</td>";
	            for (var i = 1; i <= prizelevels; i++) {
	                html += "<td>" + (isNaN(moneyGroupArray[i]) ? "&nbsp;" : moneyGroupArray[i].toFixed(2)) + "</td>"
	            }
	            html += "<td></td><td></td><td></td></tr>";
	            html += "<tr><td>中奖号码数</td>";
	            for (var i = prizelevels, nn = n; i > 0; i--, nn--) {
	                if (n == 7 && i == 1) {
	                    html += "<td>选" + n + "中0</td>"
	                } else {
	                    html += "<td>选" + n + "中" + (i == prizelevels ? n : nn) + "</td>"
	                }
	            }
	            html += "<td>未中奖</td><td>总注数</td><td>总奖金</td></tr>";
	            var tmpcodecount = 0;
	            for (var i = ns; i >= min_n[n]; i--) {
	                var prizes = tickets = 0;
	                html += "<tr><td>中" + i + "个号码</td>";
	                for (var j = 1; j <= prizelevels; j++) {
	                    tickets += (ns == 8 && j == 5 && i == 4) ? 0 : me.GetCombinCount(i, n + 1 - j) * me.GetCombinCount(ns - i, n - (n + 1 - j));
	                    prizes  += (ns == 8 && j == 5 && i == 4) ? 0 : me.GetCombinCount(i, n + 1 - j) * me.GetCombinCount(ns - i, n - (n + 1 - j)) * moneyGroupArray[j];
	                    tmpcodecount = me.GetCombinCount(i, n + 1 - j) * me.GetCombinCount(ns - i, n - (n + 1 - j)) == 0 ? "&nbsp;" : me.GetCombinCount(i, n + 1 - j) * me.GetCombinCount(ns - i, n - (n + 1 - j));
	                    html += "<td class='color-red'>" + ((ns == 8 && j == 5 && i == 4) ? "&nbsp;" : tmpcodecount) + "</td>"
	                }
	                if (nums - tickets == 0) {
	                    html += "<td>&nbsp;</td><td>" + nums + '</td><td style="text-align:center">' + prizes.toFixed(2) + "</td></tr>"
	                } else {
	                    html += "<td>" + (nums - tickets) + "</td><td>" + nums + '</td><td style="text-align:center">' + prizes.toFixed(2) + "</td></tr>"
	                }
	            }
	            if (n == 7) {
	                var prizes = tickets = 0;
	                if (ns == 8) {
	                    html += "<tr><td>中1个号码</td>";
	                    for (var j = 1; j <= prizelevels; j++) {
	                        tickets += (j == prizelevels ? 1 : 0);
	                        prizes += (j == prizelevels ? 1 : 0) * 3;
	                        html += "<td class='color-red'>" + (j == prizelevels ? 1 : "&nbsp;") + "</td>"
	                    }
	                    if (nums - tickets == 0) {
	                        html += "<td>&nbsp;</td><td>" + nums + '</td><td style="text-align:center">' + prizes.toFixed(2) + "</td></tr>"
	                    } else {
	                        html += "<td>" + (nums - tickets) + "</td><td>" + nums + '</td><td style="text-align:center">' + prizes.toFixed(2) + "</td></tr>"
	                    }
	                    prizes = tickets = 0
	                }
	                html += "<tr><td>中0个号码</td>";
	                for (var j = 1; j <= prizelevels; j++) {
	                    tickets += (j == prizelevels ? (ns == 7 ? 1 : 8) : 0);
	                    prizes += (j == prizelevels ? (ns == 7 ? 1 : 8) : 0) * moneyGroupArray[5];
	                    html += "<td class='color-red'>" + (j == prizelevels ? (ns == 7 ? 1 : 8) : "&nbsp;") + "</td>"
	                }
	                if (nums - tickets == 0) {
	                    html += "<td>&nbsp;</td><td>" + nums + '</td><td style="text-align:center">' + prizes.toFixed(2) + "</td></tr>"
	                } else {
	                    html += "<td>" + (nums - tickets) + "</td><td>" + nums + '</td><td style="text-align:center">' + prizes.toFixed(2) + "</td></tr>"
	                }
	            }
	            html += "<table><tr><td class='msg-tips'>注：红色数字表示各奖级的<span class='color-red'>中奖注数</span></td></tr></table>";
	            html += "</table>";
			html += '</div>';

			return html;
		},
		/**
		* 计算排列组合的个数
		* @param integer $iBaseNumber   基数
		* @param integer $iSelectNumber 选择数
		*/
		GetCombinCount: function( iBaseNumber, iSelectNumber ){
		    if(iSelectNumber > iBaseNumber){
		        return 0;
		    }
		    if( iBaseNumber == iSelectNumber || iSelectNumber == 0 ){
		        return 1;//全选
		    }
		    if( iSelectNumber == 1 ){
		        return iBaseNumber;//选一个数
		    }
		    var iNumerator = 1;//分子
		    var iDenominator = 1;//分母
		    for(var i = 0; i < iSelectNumber; i++){
		        iNumerator *= iBaseNumber - i;//n*(n-1)...(n-m+1)
		        iDenominator *= iSelectNumber - i;//(n-m)....*2*1
		    }
		    return iNumerator / iDenominator;
		},
		//计算号码结果组合中
		//有几种当前数字组合
		mathResultGroup: function(arr, num){
			var me = this,
				saveNum = 0,
				r = 0,
				saveArray = [];

			for (var i = 0; i < arr.length; i++) {
				saveArray = saveArray.concat(me.combine(arr[i], num));
			};
			if(saveArray.length <= 0){
				return 0;
			}else{
				saveNum = saveArray[0].join();
				
				for(var s=0; s<saveArray.length; s++){
			        if(saveArray[s].join() == saveNum){
			            r += 1;
			        }
			    }
			    return r;
			}
		},
		//批量选球事件
		exeEvent_batchSetBall:function(param, target){
			var me = this,
				ballsData = me.balls,
				x = Number(param['row']),
				bound = param['bound'],
				row = ballsData[x],
				i = 0,
				len = row.length,
				makearr = [],
				start = (typeof param['start'] == 'undefined') ? 0 : Number(param['start']);
				ranNum = (typeof param['ranNum'] == 'undefined') ? 0 : Number(param['ranNum']);
				halfLen = Math.ceil((len - start)/2 + start),
				dom = $(target),
				i = start;
				
			//清空该行选球
			for(;i < len;i++){
				me.setBallData(x, i, -1);
			}
			
			switch(bound){
				case 'all':
					for(i = start;i < len;i++){
						me.setBallData(x, i, 1);
					}
					break;
				case 'big':
					for(i = halfLen;i < len;i++){
						me.setBallData(x, i, 1);
					}
					break;
				case 'small':
					for(i = start;i < halfLen;i++){
						me.setBallData(x, i, 1);
					}
					break;
				case 'odd':
					if(ranNum > 0){
						var saveArray = [];
						for(i = start;i < len;i++){
							if((i+1)%2 != 1){
								saveArray.push(i);
							}
						}

						for (var j = 0; j < ranNum; j++) {
							var currentNum = Math.floor(Math.random() * saveArray.length);
							me.setBallData(x, saveArray[currentNum], 1);
							saveArray.splice(currentNum, 1);
						};
					}else{
						for(i = start;i < len;i++){
							if((i+1)%2 != 1){
								me.setBallData(x, i, 1);
							}
						}
					}
					break;
				case 'even':
					if(ranNum > 0){
						var saveArray = [];
						for(i = start;i < len;i++){
							if((i+1)%2 == 1){
								saveArray.push(i);
							}
						}

						for (var j = 0; j < ranNum; j++) {
							var currentNum = Math.floor(Math.random() * saveArray.length);
							me.setBallData(x, saveArray[currentNum], 1);
							saveArray.splice(currentNum, 1);
						};
					}else{
						for(i = start;i < len;i++){
							if((i+1)%2 == 1){
								me.setBallData(x, i, 1);
							}
						}
					}
					break;
				case 'up':
					var saveArray = [];

					for(i = start;i < halfLen;i++){
						saveArray.push(i);
					}

					for (var j = 0; j < ranNum; j++) {
						var currentNum = Math.floor(Math.random() * saveArray.length);
						me.setBallData(x, saveArray[currentNum], 1);
						saveArray.splice(currentNum, 1);
					};
					break;
				case 'down':
					var saveArray = [];

					for(i = halfLen;i < len;i++){
						saveArray.push(i);
					}

					for (var j = 0; j < ranNum; j++) {
						var currentNum = Math.floor(Math.random() * saveArray.length);
						me.setBallData(x, saveArray[currentNum], 1);
						saveArray.splice(currentNum, 1);
					};
					break;
				case 'upEven':
					var saveArray = [];

					for(i = start;i < halfLen;i++){
						if((i+1)%2 == 1){
							saveArray.push(i);
						}
					}

					for (var j = 0; j < ranNum; j++) {
						var currentNum = Math.floor(Math.random() * saveArray.length);
						me.setBallData(x, saveArray[currentNum], 1);
						saveArray.splice(currentNum, 1);
					};
					break;
				case 'upOdd':
					var saveArray = [];

					for(i = start;i < halfLen;i++){
						if((i+1)%2 != 1){
							saveArray.push(i);
						}
					}

					for (var j = 0; j < ranNum; j++) {
						var currentNum = Math.floor(Math.random() * saveArray.length);
						me.setBallData(x, saveArray[currentNum], 1);
						saveArray.splice(currentNum, 1);
					};
					break;
				case 'downEven':
					var saveArray = [];

					for(i = halfLen;i < len;i++){
						if((i+1)%2 == 1){
							saveArray.push(i);
						}
					}

					for (var j = 0; j < ranNum; j++) {
						var currentNum = Math.floor(Math.random() * saveArray.length);
						me.setBallData(x, saveArray[currentNum], 1);
						saveArray.splice(currentNum, 1);
					};
					break;
				case 'downOdd':
					var saveArray = [];

					for(i = halfLen;i < len;i++){
						if((i+1)%2 != 1){
							saveArray.push(i);
						}
					}

					for (var j = 0; j < ranNum; j++) {
						var currentNum = Math.floor(Math.random() * saveArray.length);
						me.setBallData(x, saveArray[currentNum], 1);
						saveArray.splice(currentNum, 1);
					};
					break;
				case 'random':
					var saveArray = [];

					for(i = start;i < len;i++){
						saveArray.push(i);
					}

					for (var j = 0; j < ranNum; j++) {
						var currentNum = Math.floor(Math.random() * saveArray.length);
						me.setBallData(x, saveArray[currentNum], 1);
						saveArray.splice(currentNum, 1);
					};
					break;
				case 'none':
					
					break;
				default:
					break;
			}
			
			dom.addClass('current');
			me.updateData();
		},
		//输出中奖结果[图表][BJKL8]
		//该方法在子类中实现
		showSelectResult: function(){

		}
	};

	//继承GameMethod
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器实例上
	BJKL8[name] = Main;
	
})(phoenix, 'Renxuan', phoenix.GameMethod);

