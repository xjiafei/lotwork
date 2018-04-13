//走势图相关JS

//画线辅助工具

(function(host, name, Event, undefined) {
	var defConfig = {

	};

	var pros = {
		init: function() {
			var me = this;

			me.lineWidth = 2;
			me.lineColor = 'red';
		},

		getParentContainer: function() {
			var me = this;

			return me.parentContainer || document.body;
		},

		setOption: function(option) {
			var me = this;

			me.parentContainer = option['parentContainer'] || me.parentContainer;
			me.lineWidth = option['lineWidth'] || me.lineWidth;
			me.lineColor = option['lineColor'] || me.lineColor;


		},

		//绘制画布
		//绘制 单列数据
		//M moveTo  起始点
		//L lineTo
		line: function(Mx, My, Lx, Ly, custom) {

			var me = this,
				position = '',
				canvas = null,
				width = 0,
				height = 0,
				maxNum = 0,
				minNum = 0,
				saveNum = [],
				lastTop = Mx,
				lastLeft = My,
				top = Lx,
				left = Ly;
			setTimeout(function() {
				maxNum = Math.max.apply(Math, [left, lastLeft]);
				minNum = Math.min.apply(Math, [left, lastLeft]);

				if (maxNum == minNum) {
					width = me.lineWidth;
				} else {
					width = maxNum - minNum;
				}

				height = top - lastTop;

				canvas = document.createElement('canvas');
				canvas.style.position = 'absolute';
				canvas.style.left = minNum + 'px';
				canvas.style.top = lastTop + 'px';

				if (lastLeft == left) {
					canvas.style.left = minNum - width / 2 + 'px';
				}

				canvas.setAttribute('width', width);
				canvas.setAttribute('height', height);


				//me.getParentContainer().appendChild(canvas);
				$('#J-chart-area')[0].appendChild(canvas);
				if (!$.support.leadingWhitespace) {
					window.G_vmlCanvasManager_.initElement(canvas);
				}

				ctx = canvas.getContext("2d");
				//开始一个新的绘制路径
				ctx.beginPath();

				if (lastLeft > left) {
					saveNum = me.mathNum(width, 0, 0, height, custom / 2);
				} else {
					saveNum = me.mathNum(0, 0, width, height, custom / 2);
				}

				//定义直线的起点坐标为(10,10)
				ctx.moveTo(saveNum[0], saveNum[1]);
				//定义直线的终点坐标为(50,10)
				ctx.lineTo(saveNum[2], saveNum[3]);
				//线的
				ctx.lineWidth = me.lineWidth;
				//线条颜色
				ctx.strokeStyle = me.lineColor;
				//沿着坐标点顺序的路径绘制直线
				ctx.stroke();
				//关闭当前的绘制路径
				ctx.closePath();

			}, 25);

		},

		//计算圆心半径坐标
		mathNum: function(x1, y1, x2, y2, r) {
			var a = x1 - x2,
				b = y1 - y2,
				c = Math.round(Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2))),
				_a = Math.round((a * r) / c),
				_b = Math.round((b * r) / c);

			return [x2 + _a, y2 + _b, x1 - _a, y1 - _b];
		}

	};

	var Main = host.Class(pros, Event);
	Main.defConfig = defConfig;
	host[name] = new Main;

})(phoenix, "DrawLine", phoenix.Event);

(function(host, name, Event, undefined) {

	var defConfig = {
			//获取数据路径
			//getDataUrl: '../js/json/chartresult.json',
			getDataUrl: '../js/json/chartdata.php',
			//单次渲染数据期数
			renderLength: 20,
			//需要进行分隔的数据数量
			separateNum: 5,
			//是否进行分屏加载
			isDelayLoading: false,
			//不使用分屏加载时
			//单次输出HTML间隔时间[单位:秒]
			renderIntervalTime: 0,
			//走势图父级DOM
			chartWarpDom: '#J-chart-content',
			//当前期数类型 [期数 | 天数 | 时间戳] ：默认期数
			dateType: 'periods',
			//当前期数具体时间 [期 | 天 | 分钟] : 默认30期
			dateNum: 30,
			//当前游戏类型 : 默认重庆时时彩
			currentGameType: 'cqssc',
			//当前游戏类型 : 默认重庆时时彩[五星玩法]
			currentGameMethod: 'Wuxing',
			lotteryCode: 'cqssc', //处理时时彩完全重用时时彩走势图DOM
			chartTableDom: '#J-chart-table'

		},
		//走势图画布缓存
		chartTrendPosition = {},
		//数据请求
		serverDataRequest = null,
		//当前渲染进程
		currentRenderProcess = null,
		//执行渲染标记
		queueMark = '',
		//走势图渲染计数器
		//用于监控DOM
		chartRenderCount = 1;


	var pros = {

		init: function(cfg) {
			var me = this;

			//走势图数据缓存
			//防止存在多实例时冲突
			//所以用实例属性保存
			me['chartCache'] = [];
			me['statisticsCache'] = [];

			//
			me.draw = host.DrawLine;

			me.addEvent('afterRenderChartHtml', function() {
				var data = me.getStatisticsData();


				// if (typeof me[me.getCurrentGameType() + me.getCurrentGameMethod() +  'RenderStatistics'] == 'function') {

				// 	me[me.getCurrentGameType() + me.getCurrentGameMethod() +  'RenderStatistics'](data);
				// }

			});

		},
		//获取选球区域容器
		getSelectContent: function() {
			return $('#J-select-content');
		},
		//获取主体容器
		getContainer: function() {
			return document.getElementById('J-chart-content');
		},
		//获取统计部分容器
		getBallContainer: function() {
			return document.getElementById('J-ball-content');
		},

		//获取单次插入DOM树的结果数量
		getRenderLength: function() {
			var me = this;

			return me.defConfig['renderLength'];
		},

		//获取插入
		setRenderLength: function(num) {
			var me = this;

			me.defConfig['renderLength'] = num;
		},

		//获取单次插入DOM树的结果数量
		getSeparateNum: function() {
			var me = this;

			return me.defConfig['separateNum'];
		},

		//获取插入
		setSeparateNum: function(num) {
			var me = this;

			me.defConfig['separateNum'] = num;
		},

		//获取走势图数据
		getChartData: function() {
			var me = this;

			return me['chartCache'];
		},

		//更新走势图数据
		setChartData: function(data) {
			var me = this;
			me['chartCache'] = data;
		},
		getStatisticsData: function() {
			return this['statisticsCache'];
		},
		setStatisticsData: function(data) {
			this['statisticsCache'] = data;
		},
		//获取游戏类型
		setCurrentLotteryCode: function(typeText) {
			var me = this;

			me.defConfig.lotteryCode = typeText;
		},
		//获取当前游戏类型
		getCurrentLotteryCode: function() {
			var me = this,
				lotteryCode = me.defConfig['lotteryCode'];

			return lotteryCode ? lotteryCode : '';
		},
		//修改当前游戏类型
		setCurrentGameType: function(typeText) {
			var me = this;

			me.defConfig[currentGameType] = typeText;
		},

		//获取当前游戏类型
		getCurrentGameType: function() {
			var me = this,
				gameType = me.defConfig['currentGameType'];

			return gameType ? gameType : '';
		},

		//修改当前游戏类型
		setCurrentGameMethod: function(methodName) {
			var me = this;

			me.defConfig[currentGameMethod] = methodName;
		},

		//获取当前游戏类型
		getCurrentGameMethod: function() {
			var me = this,
				gameType = me.defConfig['currentGameMethod'];

			return gameType ? gameType : '';
		},


		//修改当前期数类型
		setDateType: function(typeText) {
			var me = this;

			me.defConfig['dateType'] = typeText;
		},

		//获取当前期数类型
		getDateType: function() {
			var me = this;

			return me.defConfig['dateType'];
		},

		//修改当前期数具体时间
		setDateNum: function(numText) {
			var me = this;

			me.defConfig['dateNum'] = numText;
		},

		//获取当前期数具体时间
		getDateNum: function() {
			var me = this;

			return me.defConfig['dateNum'];
		},

		//修改当前队列渲染标记
		//用于记录队列渲染状态
		setQueueMark: function(markText) {
			var me = this;

			queueMark = markText;
		},

		//获取当前队列渲染标记
		getQueueMark: function() {
			var me = this;

			return queueMark;
		},

		//获取当前游戏的HTML结构
		getCurrentHtml: function() {
			var me = this,
				gameType = me.getCurrentGameType();

			return gameType ? gameType : '';
		},

		//获取服务器端图表数据
		//callback 回调事件[一般是渲染函数]
		getServerChartData: function(callback) {

			var me = this,
				//当前游戏类型
				currentGameType = me.getCurrentGameType(),
				//期数具体时间
				currentGameMethod = me.getCurrentGameMethod(),
				//期数类型
				periodsType = me.getDateType(),
				//期数具体时间
				periodsNum = me.getDateNum(),
				//服务器路径
				url = me.defConfig['getDataUrl'];

			//如果存在请求则终端
			if (serverDataRequest) {
				serverDataRequest.abort();
			}

			//当前数据请求
			serverDataRequest = $.ajax({
				type: 'GET',
				url: url,
				cache: true,
				data: {
					'periodsType': periodsType,
					'gameType': currentGameType,
					'gameMethod': currentGameMethod,
					'periodsNum': periodsNum
				},
				dataType: 'json',
				success: function(result) {
					//**************  test data ******************

 					/*var result = {
 	 						"isSuccess": 1,
 	 						"zoneComment": null,
 	 						"lotteryCode": "jsk3",
 	 						"data": [
 	 ["2015054", "355",
 	 								号码走势
 	 								[
 	 									1, 4, 0, 7, 0, 6
 	 								],
 	 								和值走势  子数组的第一位表示遗漏值，第二位表示遗漏条0表示没有遗漏条，1表示有
 	 								[[32,0], [9,0], [1,0], [1,0], [1,0], [32,0], [9,0], [1,0], [1,0], [1,1], [0,0], [9,0], [1,0], [1,0], [1,0], [1,0]],
 	 								和值组合形态
 	 								[2, 0, 3, 4],
 	 								号码形态
 	 								[0, 1, 1, 4, 5, 6]
 	 							],
 	 							["2015054", "355",
 	 								号码走势
 	 								[
 	 									1, 4, 0, 7, 0, 6
 	 								],
 	 								和值走势  子数组的第一位表示遗漏值，第二位表示遗漏条0表示没有遗漏条，1表示有
 	 								[[0,0],[32,0], [9,0], [1,0], [1,0], [1,0], [32,0], [9,0], [1,0], [1,0], [1,1],  [9,0], [1,0], [1,0], [1,0], [1,0]],
 	 								和值组合形态
 	 								[2, 0, 3, 4],
 	 								号码形态
 	 								[0, 1, 1, 4, 5, 6]
 	 							],
 	 							["2015054", "355",
 	 								号码走势
 	 								[
 	 									1, 4, 0, 7, 0, 6
 	 								],
 	 								和值走势  子数组的第一位表示遗漏值，第二位表示遗漏条0表示没有遗漏条，1表示有
 	 								[[32,0], [9,0], [1,0], [1,0], [1,0], [32,0], [9,0], [1,0], [1,0], [1,1], [0,0], [9,0], [1,0], [1,0], [1,0], [1,0]],
 	 								和值组合形态
 	 								[2, 0, 3, 4],
 	 								号码形态
 	 								[0, 1, 1, 4, 5, 6]
 	 							],
 	 							["2015054", "355",
 	 								号码走势
 	 								[
 	 									1, 4, 0, 7, 0, 6
 	 								],
 	 								和值走势  子数组的第一位表示遗漏值，第二位表示遗漏条0表示没有遗漏条，1表示有
 	 								[[32,0], [9,0], [1,0], [1,0], [1,0], [32,0], [9,0], [1,0], [1,0], [1,1], [0,0], [9,0], [1,0], [1,0], [1,0], [1,0]],
 	 								和值组合形态
 	 								[2, 0, 3, 4],
 	 								号码形态
 	 								[0, 1, 1, 4, 5, 6]
 	 							],
 	 							["2015054", "355",
 	 								号码走势
 	 								[
 	 									1, 4, 0, 7, 0, 6
 	 								],
 	 								和值走势  子数组的第一位表示遗漏值，第二位表示遗漏条0表示没有遗漏条，1表示有
 	 								[[32,0], [9,0], [1,0], [1,0], [1,0], [32,0], [9,0], [1,0], [1,0], [1,1], [0,0], [9,0], [1,0], [1,0], [1,0], [1,0]],
 	 								和值组合形态
 	 								[2, 0, 3, 4],
 	 								号码形态
 	 								[0, 1, 1, 4, 5, 6]
 	 							],
 	 							["2015054", "355",
 	 								号码走势
 	 								[
 	 									1, 4, 0, 7, 0, 6
 	 								],
 	 								和值走势  子数组的第一位表示遗漏值，第二位表示遗漏条0表示没有遗漏条，1表示有
 	 								[[32,0], [9,0], [1,0], [1,0], [1,0], [32,0], [9,0], [1,0], [1,0], [1,1], [0,0], [9,0], [1,0], [1,0], [1,0], [1,0]],
 	 								和值组合形态
 	 								[2, 0, 3, 4],
 	 								号码形态
 	 								[0, 1, 1, 4, 5, 6]
 	 							],
 	 							["2015054", "355",
 	 								号码走势
 	 								[
 	 									1, 4, 0, 7, 0, 6
 	 								],
 	 								和值走势  子数组的第一位表示遗漏值，第二位表示遗漏条0表示没有遗漏条，1表示有
 	 								[[32,0], [9,0], [1,0], [1,0], [1,0], [32,0], [9,0], [1,0], [1,0], [1,1], [0,0], [9,0], [1,0], [1,0], [1,0], [1,0]],
 	 								和值组合形态
 	 								[2, 0, 3, 4],
 	 								号码形态
 	 								[0, 1, 1, 4, 5, 6]
 	 							]


 	 						],
 	 						"statistics": null
 	 					}*/

					//**************  test data ******************

					if (result['isSuccess']) {
						//更新走势图数据
						me.setChartData(result['data']);
						//设置统计数据
						me.setStatisticsData(result['statistics']);
						me.setCurrentLotteryCode(result['lotteryCode']);
						//触发完成自定义事件
						me.fireEvent('afterGetServerData', result);

						if (callback) {
							callback();
						}
					}
				},
				complete: function() {

					serverDataRequest = null;
				}
			});
		},

		//渲染html
		//走势图数据 chartData 
		//queueMark 队列标记
		//blockDataSave 分段数据缓存
		renderChartHtml: function(currentRank, htmlFragment, queueMark, blockDataSave) {
			var me = this,
				//当前渲染标记
				currentRank = currentRank || 0,
				//获取走势图数据
				chartData = me.getChartData(),
				//渲染计数
				renderCount = 0,
				//渲染队列标记
				queueMark = queueMark || me.getQueueMark(),
				//渲染允许数量
				allowCount = me.getRenderLength() + 1,
				//当前数据缓存
				currentData = {},
				//分段数据缓存
				blockDataSave = blockDataSave || [],
				//渲染分段数据时
				//的等待时间
				renderTime = me.defConfig['renderIntervalTime'],
				//文档模版碎片
				htmlFragment = htmlFragment || document.createDocumentFragment();

			//查看是否执行当前渲染队列
			//如果已经创建新队列杂停止当前队列
			if (queueMark != me.getQueueMark()) {
				return;
			}


			//如果渲染数据为空
			//输出已经渲染的DOM结构
			if (currentRank == chartData.length) {

				//输出HTML片段单元
				me.appendHtmlDom(htmlFragment, blockDataSave);
				//复位渲染计数器
				chartRenderCount = 1;

				//更改首次就执行,或者造成dom自适应撑开，绘图无法定位正确
				//me.fireEvent('afterRenderChartHtml', chartData);

				return;
			}

			//当前渲染数据
			currentData = chartData[currentRank];

			//将dom添加分为若干小块进行
			currentRenderProcess = setTimeout(function() {
				//如果没有到达渲染数量
				//则添加到模版碎片
				if (chartRenderCount < allowCount) {

					//检测是否存在当前彩种的渲染方法
					if (typeof me[me.getCurrentGameType() + me.getCurrentGameMethod() + 'Render'] == 'function') {


						//添加DOM进入HTML碎片
						htmlFragment.appendChild(me[me.getCurrentGameType() + me.getCurrentGameMethod() + 'Render'](currentData, chartRenderCount));
						//存储分段数据
						blockDataSave.push(currentData);
						//当前渲染标记
						currentRank++;
						//渲染计数器
						chartRenderCount++;

						//如果已经达到可渲染数量
						//则输出一个HTML碎片
						if (chartRenderCount == allowCount) {

							//输出HTML片段单元
							me.appendHtmlDom(htmlFragment, blockDataSave);
							//复位渲染计数器
							chartRenderCount = 1;

							//分段渲染下一个文档片段
							//加入定时器减轻浏览器渲染压力
							setTimeout(function() {
								me.renderChartHtml(currentRank);
							}, renderTime * 1000);

							return;
						}

						//渲染输出下一段数据
						me.renderChartHtml(currentRank, htmlFragment, queueMark, blockDataSave);
					} else {
						try {
							console.log('缺少当前玩法:[' + me.getCurrentGameType() + me.getCurrentGameMethod() + ']渲染方法');
						} catch (e) {

						}
					}
				}

				//浏览器最小响应时间间隔为25s
				//如果时间过长则会影响用户的EVENT事件
				//和页面的GUI渲染事件造成浏览器假死
			}, 25);
		},

		//渲染统计区域
		cqsscWuxingRenderStatistics: function(data) {
			var me = this,
				index = 0,
				i = 0,
				len = 0,
				j = 0,
				len2 = 0,
				n = 0,
				tdstr = '<td class="ball-none border-right"></td><td class="ball-none"></td>',
				frame1 = [],
				frame2 = [],
				frame3 = [],
				frame4 = [];
			frame1.push('<tr class="auxiliary-area"><td class="ball-none"></td><td class="border-bottom border-top">出现总次数</td><td class="ball-none border-right border-bottom"></td><td class="ball-none  border-bottom"></td><td class="border-bottom"></td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');
			frame2.push('<tr class="auxiliary-area"><td class="ball-none border-bottom"></td><td class="border-bottom">平均遗漏值</td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td><td class="border-bottom"></td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');
			frame3.push('<tr class="auxiliary-area"><td class="ball-none border-bottom"></td><td class="border-bottom">最大遗漏值</td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td><td class="border-bottom"></td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');
			frame4.push('<tr class="auxiliary-area"><td class="ball-none border-bottom"></td><td class="border-bottom">最大连出值</td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td><td class="border-bottom"></td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');


			for (i = 0, len = 60; i < len; i++) {
				tdstr = ((i + 1) % 10 == 0) ? '<td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>' : '';
				tdstr = (i == (len - 1)) ? '<td class="ball-none border-bottom"></td>' : tdstr;

				frame1.push('<td class="border-bottom"><i class="ball-noraml">' + data[0][i] + '</i></td>' + tdstr);
				frame2.push('<td class="border-bottom"><i class="ball-noraml">' + data[1][i] + '</i></td>' + tdstr);
				frame3.push('<td class="border-bottom"><i class="ball-noraml">' + data[2][i] + '</i></td>' + tdstr);
				frame4.push('<td class="border-bottom"><i class="ball-noraml">' + data[3][i] + '</i></td>' + tdstr);
			}

			frame1.push('</tr>');
			frame2.push('</tr>');
			frame3.push('</tr>');
			frame4.push('</tr>');
			$(me.getBallContainer()).append($(frame1.join(''))).append($(frame2.join(''))).append($(frame3.join(''))).append($(frame4.join('')));

			if (me.getCurrentLotteryCode() == 'slmmc') {

				if ($(me.defConfig['chartTableDom']).find('tr:eq(0)>th').size() > 7) {
					$(me.defConfig['chartTableDom']).find('tr:eq(0)>th:eq(1)').attr('colspan', '6');
					$(me.defConfig['chartTableDom']).find('tr:eq(0)>th:eq(0)').remove();
				}
				$(me.defConfig['chartTableDom']).find('tr:eq(1)>th:eq(1)').attr('colspan', '4');
			}

		},

		//渲染走势图区域画布CANVAS方法
		//该方法为分发函数
		//具体实现由各彩种名称 + TrendCanvas实现
		renderTrendArea: function(html, data) {
			var me = this;

			//分发到具体彩种
			if (typeof me[me.getCurrentGameType() + me.getCurrentGameMethod() + 'TrendCanvas'] == 'function') {
				me[me.getCurrentGameType() + me.getCurrentGameMethod() + 'TrendCanvas'](html, data);
			}
		},

		//输出一个html片段单元
		//各玩法统一输出接口
		//htmlframent需要添加进DOM树的片段
		//blockData 分段渲染基础数据
		appendHtmlDom: function(htmlFragment, blockData) {
			var me = this,
				parentDom = document.getElementById('J-chart-content');
				//parentDom = document.getElementById('big-k3-chart');

			//空数据
			if (!blockData) {
				return;
			}


			parentDom.appendChild(htmlFragment);
			//将渲染输出任务划分为
			//若干小块定时器执行
			setTimeout(function() {
				//parentDom.appendChild(htmlFragment);

				//渲染走势图区域
				me.renderTrendArea(parentDom, blockData);

				//浏览器最小响应时间间隔为25s
				//如果时间过长则会影响用户的EVENT事件
				//和页面的GUI渲染事件造成浏览器假死
			}, 25);
		},

		//销毁渲染队列
		destroyRenderQueue: function() {
			var me = this;

			//重新添加队列渲染标记
			me.setQueueMark(new Date().getTime());
			//销毁缓存数据
			me.setChartData([]);
			//复位渲染计数器
			chartRenderCount = 1;
			//清除当前渲染线程
			if (currentRenderProcess) {
				clearTimeout(currentRenderProcess);
				currentRenderProcess = null;
			}
		},

		//销毁走势图实例
		destroyTrendQueue: function() {
			var me = this;

			//销毁当前页面的svg画布
			$('canvas').remove();
			//重置走势图坐标存储变量
			chartTrendPosition = [];
		},

		//集成方法接口
		//读取服务器数据
		//写入数据
		//渲染数据到输出
		show: function() {
			var me = this,
				chartData = me.getChartData();

			//读取服务器数据
			//写入数据到缓存
			me.getServerChartData(function() {
				me.fireEvent('afterRenderChartHtml', chartData);
				//渲染输出html结构
				me.renderChartHtml();
			});

			//加入读取数据完成事件
			me.addEvent('afterGetServerData', function() {

			});
		},

		//插件内部缓存复位
		reset: function() {
			var me = this,
				parentDom = $(me.defConfig['chartWarpDom']);

			//清空当前内容列表
			parentDom.html('<tr></tr>');

			//清空统计区域内容
			$(me.getBallContainer()).html('');

			//销毁其他渲染队列
			me.destroyRenderQueue();

			//销毁走势图实例
			me.destroyTrendQueue();
		},

		//切换期数限制
		//统一切换接口方法
		//data 期数切换数据
		changePeriodsShow: function(data) {
			var me = this;

			//复位内部缓存
			me.reset();

			//期数类型
			if (typeof data['dateType'] != 'undefined') {
				me.setDateType(data['dateType']);
			}

			//具体时间
			if (typeof data['dateNum'] != 'undefined') {
				me.setDateNum(data['dateNum']);
			}

			//当前游戏类型
			if (typeof data['gameType'] != 'undefined') {
				me.setCurrentGameType(data['dateNum']);
			}

			//修改期数数据完成
			me.fireEvent('afterChangePeriods', me.getDateType(), me.getDateNum(), me.getCurrentGameType());

			//渲染输出
			me.show();
		},

		//获取基础单元格大小
		//从而计算整个画布尺寸以及走势图距离
		getUnitSize: function(dom) {
			var me = this,
				width = 0,
				height = 0,
				dom = $(dom),
				topNum = dom.position().top,
				leftNum = dom.position().left,
				widthNum = dom[0].offsetWidth,
				heightNum = dom[0].offsetHeight;

			return {
				topNum: topNum,
				leftNum: leftNum,
				widthNum: widthNum,
				heightNum: heightNum
			}
		},



		//获取选求的坐标位置
		findBallsPosition: function() {
			var me = this;

		},

		//彩种单号码渲染
		//号码类型判断逻辑统一
		//[0,1,2,3] 0 号码数字 1
		//所有彩种统一方法
		singleLotteryBall: function(ballsData, styleName) {
			var td1, td2, td3,
				me = this,
				htmlData = document.createDocumentFragment(),
				borderStyleText = styleName,
				lostBallstyle = '',
				ballStyleText = '';

			//左边界
			td1 = document.createElement('td');
			td1.className = 'ball-none' + borderStyleText;
			htmlData.appendChild(td1);

			for (var i = 0, current; i < ballsData.length; i++) {
				current = ballsData[i];
				lostBallstyle = 'l-' + current[3];
				ballStyleText = 'c-' + current[0] + '-' + current[2];

				td2 = document.createElement('td');
				td2.className = borderStyleText + ' ' + lostBallstyle;
				td2.innerHTML = '<i data-info="' + current.join(',') + '" class="ball-noraml ' + ballStyleText + '">' + (current[0] == 0 ? current[1] : current[0]) + '</i>'
				htmlData.appendChild(td2);
			};

			td3 = document.createElement('td');
			td3.className = 'ball-none border-right' + borderStyleText;
			htmlData.appendChild(td3);

			return htmlData;
		},

		//彩种号码分布渲染
		//所有彩种统一方法
		layoutLotteryBall: function(ballsData, styleName) {
			var td1, td2, td3,
				me = this,
				htmlData = document.createDocumentFragment(),
				borderStyleText = styleName,
				ballStyleText = '';

			//左边界
			td1 = document.createElement('td');
			td1.className = 'ball-none' + borderStyleText;
			htmlData.appendChild(td1);

			for (var i = 0, current; i < ballsData.length; i++) {
				current = ballsData[i];

				//判断是否是单双号
				ballStyleText = 'f-' + current[2];

				td2 = document.createElement('td');
				td2.className = borderStyleText;
				td2.innerHTML = '<i data-info="' + current.join(',') + '" class="ball-noraml ' + ballStyleText + '">' + (current[0] == 0 ? current[1] : current[0]) + '</i>'
				htmlData.appendChild(td2);
			};

			//td3 = document.createElement('td');
			//td3.className = 'ball-none' + borderStyleText;
			//htmlData.appendChild(td3);

			return htmlData;
		},
		//在预选区增加一行
		addSelectRow: function() {
			var me = this,
				dom = me.getSelectContent(),
				rows = dom.find('.select-area'),
				newDom = $('<tr class="select-area">' + $('#J-tr-select').html() + '</tr>');
			newDom.find('.ball-noraml').removeClass('ball-orange');
			newDom.insertAfter(rows.eq(rows.size() - 1)).find('.ico-add').removeClass('.ico-add').addClass('ico-del').attr('data-action', 'delSelectRow');
		},
		//在预选区删除行
		delSelectRow: function(tr) {
			tr.remove();
		},

		//重庆时时彩
		//data当前渲染数据
		//CurrentNum当前渲染计数
		cqsscWuxingRender: function(data, currentNum) {
			var td1, td2, td3, td4, td5, td6,
				current,
				me = this,
				styleName = currentNum % me.getSeparateNum() == 0 ? ' border-bottom' : '',
				htmlTextArr = new Array(),
				allowCount = me.getRenderLength(),
				parentDom = document.createElement('tr');

			if (me.getCurrentLotteryCode() != 'slmmc') {
				td1 = document.createElement('td');
				td1.className = "ball-none " + styleName;
				parentDom.appendChild(td1);

				//期号号码
				td2 = document.createElement('td');
				td2.className = "issue-numbers " + styleName;
				td2.innerHTML = data[0];
				parentDom.appendChild(td2);

				td3 = document.createElement('td');
				td3.className = "ball-none border-right" + styleName;
				parentDom.appendChild(td3);

				td4 = document.createElement('td');
				td4.className = "ball-none " + styleName;
				parentDom.appendChild(td4);


				//期号号码
				td5 = document.createElement('td');
				td5.className = styleName;
				td5.innerHTML = '<span class="lottery-numbers">' + data[1] + '</span>';
				parentDom.appendChild(td5);
			} else {

				td4 = document.createElement('td');
				td4.className = "ball-none " + styleName;
				parentDom.appendChild(td4);


				//期号号码
				td5 = document.createElement('td');
				td5.className = styleName;
				td5.setAttribute("colspan", '4');
				td5.innerHTML = '<span class="lottery-numbers">' + data[1] + '</span>';
				parentDom.appendChild(td5);
			}



			td6 = document.createElement('td');
			td6.className = 'ball-none border-right' + styleName;
			parentDom.appendChild(td6);

			//万位
			parentDom.appendChild(me.singleLotteryBall(data[2], styleName));

			//千位
			parentDom.appendChild(me.singleLotteryBall(data[3], styleName));
			//百位
			parentDom.appendChild(me.singleLotteryBall(data[4], styleName));
			//十位
			parentDom.appendChild(me.singleLotteryBall(data[5], styleName));
			//个位
			parentDom.appendChild(me.singleLotteryBall(data[6], styleName));

			//号码分布
			parentDom.appendChild(me.layoutLotteryBall(data[7], styleName));

			td1 = document.createElement('td');
			td1.className = "ball-none " + styleName;
			parentDom.appendChild(td1);


			//返回完整的单行TR结构
			return parentDom;
		},
		getChartTrendPosition: function() {
			return chartTrendPosition;
		},
		//重庆时时彩
		//渲染走势图画布
		//dom渲染第一行后的内容
		cqsscWuxingTrendCanvas: function(dom, data) {
			var positionCount = 0,
				me = this,
				currentBallLeft = 0,
				currentBallTop = 0,
				chartTrendPosition = me.getChartTrendPosition();


			//遍历分段渲染数据
			for (var i = 0, current; i < data.length; i++) {
				current = data[i];

				for (var k = 0; k < current.length; k++) {
					//选球区域

					if (k > 1 && k < 7) {
						for (var j = 0; j < current[k].length; j++) {

							if (j == 0) {
								var currentDom = dom.getElementsByTagName('i')[positionCount].parentNode,
									unitSize = me.getUnitSize(currentDom),
									top = unitSize.topNum,
									left = unitSize.leftNum,
									width = unitSize.widthNum,
									height = unitSize.heightNum;
							}

							//当前位置球
							positionCount++;

							//当前号码
							if (current[k][j][0] == 0) {
								//第一排渲染
								if (typeof chartTrendPosition[k] == 'undefined') {

									//当前球的坐标
									currentBallLeft = left + (j + 1) * width - width / 2;
									currentBallTop = top + height / 2;

									chartTrendPosition[k] = {};
									chartTrendPosition[k]['top'] = currentBallTop;
									chartTrendPosition[k]['left'] = currentBallLeft;
								} else {

									//当前球的坐标
									currentBallLeft = left + (j + 1) * width - width / 2;
									currentBallTop = chartTrendPosition[k]['top'] + height;

									//绘制画布
									//绘制走势图线
									me.draw.setOption({
										//old  
										parentContainer: $('#J-chart-area')[0]
											//parentContainer: $('#new-k3-chart')[0]										
									});
									me.draw.line(chartTrendPosition[k]['top'], chartTrendPosition[k]['left'], currentBallTop, currentBallLeft);

									chartTrendPosition[k]['top'] = currentBallTop;
									chartTrendPosition[k]['left'] = currentBallLeft;
								}
							}
						};
					}
				};

				positionCount = 0;
			}



		}
	}

	var Main = host.Class(pros, Event);
	Main.defConfig = defConfig;
	host[name] = Main;

})(phoenix, "GameCharts", phoenix.Event);