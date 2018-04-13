(function(host, undefined) {
	var gameConfigData = {
		"gameType": "k3",
		"gameTypeCn": "江苏快三",
		"lotteryId": 99101,
		"userLvl": 1,
		"userId": 748221,
		"userName": "fd0011",
		"backOutStartFee": 100000,
		"backOutRadio": 100,
		"chips": [1,2,5,10,20,50,100,500,1000,5000],
		"chipsSelected": [10,20,50,100,500],
		"gameMethods": [
			{
				"gameType": "daxiao",
				"gameTypeCn": "大小",
				"gameTips": "这里是大小的tips哇哈哈哈"
			},
			{
				"gameType": "danshuang",
				"gameTypeCn": "单双",
				"gameTips": "这里是单双的tips哇哈哈哈"
			},
			{
				"gameType": "ertonghaofuxuan",
				"gameTypeCn": "二同号复选",
				"gameTips": "这里是二同号复选的tips哇哈哈哈"
			},
			{
				"gameType": "santonghaodanxuan",
				"gameTypeCn": "三同号单选",
				"gameTips": "这里是三同号单选的tips哇哈哈哈"
			},
			{
				"gameType": "santonghaotongxuan",
				"gameTypeCn": "三同号通选",
				"gameTips": "这里是三同号通选的tips哇哈哈哈"
			},
			{
				"gameType": "hezhi",
				"gameTypeCn": "和值",
				"gameTips": "这里是和值的tips哇哈哈哈"
			},
			{
				"gameType": "erbutonghao",
				"gameTypeCn": "二不同号",
				"gameTips": "这里是二不同号的tips哇哈哈哈这里是二不同号的tips哇哈哈哈这里是二不同号的tips哇哈哈哈"
			},
			{
				"gameType": "yibutonghao",
				"gameTypeCn": "一不同号",
				"gameTips": "这里是一不同号的tips哇哈哈哈"
			}
		],
		"ballLists":[
			{"name":"daxiao", "order":"大", },
			{"name":"danshuang", "order":"单"},
			{"name":"ertonghaofuxuan", "order":"66*"},
			{"name":"ertonghaofuxuan", "order":"55*"},
			{"name":"ertonghaofuxuan", "order":"44*"},
			{"name":"santonghaodanxuan", "order":"666"},
			{"name":"santonghaodanxuan", "order":"555"},
			{"name":"santonghaodanxuan", "order":"444"},
			{"name":"santonghaodanxuan", "order":"333"},
			{"name":"santonghaodanxuan", "order":"222"},
			{"name":"santonghaodanxuan", "order":"111"},
			{"name":"santonghaotongxuan", "order":"111 222 333 444 555 666"},
			{"name":"ertonghaofuxuan", "order":"33*"},
			{"name":"ertonghaofuxuan", "order":"22*"},
			{"name":"ertonghaofuxuan", "order":"11*"},
			{"name":"daxiao", "order":"小"},
			{"name":"danshuang", "order":"双"},
			{"name":"hezhi", "order":"17"},
			{"name":"hezhi", "order":"16"},
			{"name":"hezhi", "order":"15"},
			{"name":"hezhi", "order":"14"},
			{"name":"hezhi", "order":"13"},
			{"name":"hezhi", "order":"12"},
			{"name":"hezhi", "order":"11"},
			{"name":"hezhi", "order":"10"},
			{"name":"hezhi", "order":"9"},
			{"name":"hezhi", "order":"8"},
			{"name":"hezhi", "order":"7"},
			{"name":"hezhi", "order":"6"},
			{"name":"hezhi", "order":"5"},
			{"name":"hezhi", "order":"4"},
			{"name":"erbutonghao", "order":"5,6"},
			{"name":"erbutonghao", "order":"4,6"},
			{"name":"erbutonghao", "order":"4,5"},
			{"name":"erbutonghao", "order":"3,6"},
			{"name":"erbutonghao", "order":"3,5"},
			{"name":"erbutonghao", "order":"3,4"},
			{"name":"erbutonghao", "order":"2,6"},
			{"name":"erbutonghao", "order":"2,5"},
			{"name":"erbutonghao", "order":"2,4"},
			{"name":"erbutonghao", "order":"2,3"},
			{"name":"erbutonghao", "order":"1,6"},
			{"name":"erbutonghao", "order":"1,5"},
			{"name":"erbutonghao", "order":"1,4"},
			{"name":"erbutonghao", "order":"1,3"},
			{"name":"erbutonghao", "order":"1,2"},
			{"name":"yibutonghao", "order":"6"},
			{"name":"yibutonghao", "order":"5"},
			{"name":"yibutonghao", "order":"4"},
			{"name":"yibutonghao", "order":"3"},
			{"name":"yibutonghao", "order":"2"},
			{"name":"yibutonghao", "order":"1"}
		],
		"awardGroups": [{
			"awardGroupId": 2881949,
			"awardName": "奖金组1800",
			"betType": 1
		}],
		// "dynamicConfigUrl": "/gameBet/cqssc/dynamicConfig",
		"dynamicConfigUrl": "../js/game/k3-dice/data/dynamicConfig.json",
		"uploadPath": "/gameBet/cqssc/betFile",
		// "queryUserBalUrl": "/gameBet/queryUserBal",
		"queryUserBalUrl": "../js/game/k3-dice/data/getBalance.php",
		"getUserOrdersUrl": "/gameBet/cqssc/getUserOrders",
		"getUserPlansUrl": "/gameBet/cqssc/getUserPlans",
		"getHandingChargeUrl": "/gameBet/cqssc/handlingCharge?amount=",
		"getBetAwardUrl": "/gameBet/cqssc/getBetAward",
		"getHotColdUrl": "/gameBet/cqssc/frequency",
		"trendChartUrl": "/gameBet/cqssc/trendChart?type=",
		"getLotteryLogoPath": "/static/images/game/logos/logo-cqssc.png",
		"queryGameUserAwardGroupByLotteryIdUrl": "/gameBet/cqssc/queryGameUserAwardGroupByLotteryId",
		"saveProxyBetGameAwardGroupUrl": "/gameBet/cqssc/saveBetAward",
		// "sumbitUrl": "/gameBet/cqssc/submit",
		"sumbitUrl": "../js/game/k3-dice/data/submit.php",
		"indexInit": "/gameBet/cqssc/indexInit",
		"poolBouns": null,
		"isLotteryStopSale": false,
		// "lastNumberUrl": "/gameBet/cqssc/lastNumber",
		"lastNumberUrl": "../js/game/k3-dice/data/lastNumber.php",
		"sourceList": [],
		"rechargeUrl": '充值链接',
		"helpLink": "/help/queryLotteryDetail?helpId=869"
	};
	var defConfig = {
			//当前彩种名称
			gameType: gameConfigData['gameType'],
			gameTypeCn: gameConfigData['gameTypeCn'],
			lotteryId: gameConfigData['lotteryId'],
			awardGroups: gameConfigData['awardGroups'],
			userId: gameConfigData['userId'],
			userName: gameConfigData['userName'],
			userLvl: gameConfigData['userLvl'],
			backOutStartFee: gameConfigData['backOutStartFee'],
			backOutRadio: gameConfigData['backOutRadio'],
			isLotteryStopSale: gameConfigData['isLotteryStopSale'],
			helpLink: gameConfigData['helpLink'],
			sourceList: gameConfigData['sourceList']
		},
		instance;
	var pros = {
		init: function() {
			var me = this;
			me.types = gameConfigData['gameMethods'];
		},
		getConfig: function(){
			return this.config = gameConfigData;
		},
		//获取玩法类型
		getTypes: function(isFilterClose) {
			return this.types;
		},
		getBallLists: function() {
			return gameConfigData['ballLists'];
		},
		getBallType: function(idx) {
			var balls = this.getBallLists(),
				ball = balls[idx],
				name = '';
			if( ball && ball.name ){
				name = ball.name;
			}
			return name;
		},
		getBallOrder: function(idx) {
			var balls = this.getBallLists(),
				ball = balls[idx],
				order = '';
			if( ball && ball.order ){
				order = ball.order;
			}
			return order;
		},
		getGameType: function() {
			return defConfig['gameType'];
		},
		getGameTypeCn: function() {
			return defConfig['gameTypeCn'];
		},
		getDefaultMethod: function() {
			return gameConfigData['defaultMethod'];
		},
		//获取动态配置接口地址
		getDynamicConfigUrl: function() {
			return gameConfigData['dynamicConfigUrl'];
		},
		//获取单式上传接口地址
		getUploadPath: function() {
			return gameConfigData['uploadPath'];
		},
		//获取用户余额
		getUserBalUrl: function() {
			return gameConfigData['queryUserBalUrl'];
		},
		//获取投注页面显示订单接口地址
		getUserOrdersUrl: function() {
			return gameConfigData['getUserOrdersUrl'];
		},
		//获取单式上传接口地址
		getUserPlansUrl: function() {
			return gameConfigData['getUserPlansUrl'];
		},
		//获取撤销手续费接口地址
		getHandingChargeUrl: function() {
			return gameConfigData['getHandingChargeUrl'];
		},
		//获取彩种logo地址
		getLotteryLogoPath: function() {
			return gameConfigData['getLotteryLogoPath'];
		},
		//获取玩法走势图接口地址
		trendChartUrl: function() {
			return gameConfigData['trendChartUrl'];
		},
		//查询玩法描述和默认冷热球及用户投注方式奖金接口地址
		getBetAwardUrl: function() {
			return gameConfigData['getBetAwardUrl'];
		},
		//获取冷热遗漏接口地址
		getHotColdUrl: function() {
			return gameConfigData['getHotColdUrl'];
		},
		//查询奖金组
		getQueryGameUserAwardGroupByLotteryIdUrl: function() {
			return gameConfigData['queryGameUserAwardGroupByLotteryIdUrl'];
		},
		//保存代理投注奖金组
		getSaveProxyBetGameAwardGroupUrl: function() {
			return gameConfigData['saveProxyBetGameAwardGroupUrl'];
		},
		//获取投注提交接口地址
		submitUrl: function() {
			return gameConfigData['sumbitUrl'];
		},
		//获取首页接口
		indexInitUrl: function() {
			return gameConfigData['indexInit'];
		},
		//获取最新开奖号码
		lastNumberUrl: function() {
			return gameConfigData['lastNumberUrl'];
		},
		//name  wuxing.zhixuan.fushi
		getTitleByName: function(name) {
			var me = this,
				types = me.types,
				i = 0,
				len = types.length,
				nameArr = name.split('.'),
				nameLen = nameArr.length;
			for(; i<len; i++){
				if( types[i]['gameType'] == nameArr[nameLen-1] ){
					return types[i]['gameTypeCn'];
				}
			}
			return '';
			/*var me = this,
				nameArr = name.split('.'),
				nameLen = nameArr.length,
				types = me.types,
				i = 0,
				len = types.length,
				i2,
				len2,
				i3,
				len3,
				tempArr = [],
				result = [];
			//循环一级
			for (; i < len; i++) {
				if (types[i]['name'] == nameArr[0]) {
					if (gameConfigData['gameType'].indexOf('115') < 0) {
						result.push(types[i]['title'].replace(/&nbsp;/g, ''));
					}
					if (nameLen > 1 && types[i]['childs'].length > 0) {
						tempArr = types[i]['childs'];
						len2 = tempArr.length;
						//循环二级
						for (i2 = 0; i2 < len2; i2++) {
							//console.log(tempArr[i2]['name']);
							if (tempArr[i2]['name'] == nameArr[1]) {
								if (gameConfigData['gameType'].indexOf('115') > 0) {
									result.push(tempArr[i2]['title'].replace(/&nbsp;/g, ''));
								}
								if (nameLen > 2 && tempArr[i2]['childs'].length > 0) {
									tempArr = tempArr[i2]['childs'];
									len3 = tempArr.length;
									//循环三级
									for (i3 = 0; i3 < len3; i3++) {
										if (tempArr[i3]['name'] == nameArr[2]) {
											if (tempArr[i3]['headline']) {
												return tempArr[i3]['headline'];
											}
											result.push(tempArr[i3]['title'].replace(/&nbsp;/g, ''));
											return result;
										}
									}
								} else {
									return result;
								}
							}
						}
					} else {
						return result;
					}
				}
			}
			return '';*/
		}

	};

	function GameConfig(){
		this.init();
	};
	GameConfig.prototype = pros;

	return host.GameConfig = GameConfig;

})(window);
