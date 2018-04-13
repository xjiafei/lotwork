(function(host, undefined) {
	var gameConfigData = {
		"gameType":"jldice",
		"gameTypeCn":"吉利骰宝",
		"defaultMethod":null,
		"lotteryId":99601,
		"userLvl":2,
		"userId":1198908,
		"userName":"luck888",
		"backOutStartFee":20010000,
		"backOutRadio":0,
		"gameMethods":
			[{"title":null,"name":null,"childs":null,"gameType":"hezhi","gameTypeCn":"和值","gameTips":"至少选择1个和值（3个号码之和）进行投注，所选和值与开奖的3个号码的和值相同即中奖\n"},
				{"title":null,"name":null,"childs":null,"gameType":"santonghaotongxuan","gameTypeCn":"三同号通选","gameTips":"对所有相同的三个号码（111、222、333、444、555、666）进行投注，任意号码开出即中奖"},
				{"title":null,"name":null,"childs":null,"gameType":"santonghaodanxuan","gameTypeCn":"三同号单选","gameTips":" 对相同的三个号码（111、222、333、444、555、666）中的任意一个进行投注，所选号码开出即中奖"},
				{"title":null,"name":null,"childs":null,"gameType":"ertonghaofuxuan","gameTypeCn":"二同号复选","gameTips":"从11～66中至少选择一对号码，开奖号码出现所选号码即中奖，顺序不限"},
				{"title":null,"name":null,"childs":null,"gameType":"erbutonghao","gameTypeCn":"二不同号","gameTips":"从15种组合中至少选择一组号码，开奖号码内包含任意一组即中奖"},
				{"title":null,"name":null,"childs":null,"gameType":"yibutonghao","gameTypeCn":"猜一个号","gameTips":"从1-6中至少选择1个号码，所选号码在开奖号码中出现1次，即中赔率为单骰的奖金；所选号码在开奖号码中出现2次，即中赔率为双骰的奖金；所选号码在开奖号码中出现3次，即中赔率为全骰的奖金"},
				{"title":null,"name":null,"childs":null,"gameType":"daxiao","gameTypeCn":"特殊","gameTips":"大：开奖号码三个号和值为11-17即中奖\n小：开奖号码三个号和值为4-10即中奖\n遇全骰（豹子）则不中奖"},
				{"title":null,"name":null,"childs":null,"gameType":"danshuang","gameTypeCn":"特殊","gameTips":"单：开奖号码三个号和值为奇数即中奖\n双：开奖号码三个号和值为偶数即中奖\n 遇全骰（豹子）则不中奖"}],
		"awardGroups":[{"gid":189,
				"awardGroupId":15311098,
				"awardName":"混合奖金组",
				"betType":1,
				"directRet":400,
				"threeoneRet":0,
				"createTime":1434402264000,
				"updateTimte":1434402264000
			}],

		"headImg": "3",
		"userNickName": "324242",
		"uploadUserInfo":"../js/game/jl-dice/data/uploadUserInfo.json",


		// "dynamicConfigUrl": "/gameBet/cqssc/dynamicConfig",
		"dynamicConfigUrl": "../js/game/jl-dice/data/dynamicConfig.json",
		"uploadPath":"/gameBet/jsdice/betFile",
		// "queryUserBalUrl": "/gameBet/queryUserBal",
		"queryUserBalUrl": "../js/game/jl-dice/data/getBalance.php",
		"getUserOrdersUrl":"/gameBet/jsdice/getUserOrders",
		"getUserPlansUrl":"/gameBet/jsdice/getUserPlans",
		"getHandingChargeUrl":"/gameBet/jsdice/handlingCharge?amount=",
		"getBetAwardUrl":"/gameBet/jsdice/getBetAward",
		"getHotColdUrl":"/gameBet/jsdice/frequency",
		"trendChartUrl":"/gameBet/jsdice/trendChart?type=",
		"getLotteryLogoPath":"/static/images/game/logos/logo-jsdice.png",
		"queryGameUserAwardGroupByLotteryIdUrl":"/gameBet/jsdice/queryGameUserAwardGroupByLotteryId",
		"saveProxyBetGameAwardGroupUrl":"/gameBet/jsdice/saveBetAward",
		// "sumbitUrl": "/gameBet/cqssc/submit",
		"sumbitUrl": "../js/game/jl-dice/data/submit.php",
		"indexInit":"/gameBet/jsdice/indexInit",
		"poolBouns":null,
		"isLotteryStopSale":false,
		// "lastNumberUrl": "/gameBet/cqssc/lastNumber",
		"lastNumberUrl": "../js/game/jl-dice/data/lastNumber.php",
		"sourceList":[],
		"helpLink":"/help/queryLotteryDetail?helpId=1143",

		"playerBetUrl": "../js/game/jl-dice/data/playerBet.json",
		"winningListUrl": "../js/game/jl-dice/data/winninglist.json",

		"chips":[100,200,500,1000,2000,5000,6000,8000,10000,20000],
		"chipsSelected":[100,200,500,1000,2000],
		"ballLists":[{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"daxiao","order":"大"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"danshuang","order":"单"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"ertonghaofuxuan","order":"66*"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"ertonghaofuxuan","order":"55*"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"ertonghaofuxuan","order":"44*"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"santonghaodanxuan","order":"666"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"santonghaodanxuan","order":"555"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"santonghaodanxuan","order":"444"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"santonghaodanxuan","order":"333"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"santonghaodanxuan","order":"222"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"santonghaodanxuan","order":"111"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"santonghaotongxuan","order":"111 222 333 444 555 666"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"ertonghaofuxuan","order":"33*"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"ertonghaofuxuan","order":"22*"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"ertonghaofuxuan","order":"11*"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"daxiao","order":"小"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"danshuang","order":"双"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"hezhi","order":"17"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"hezhi","order":"16"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"hezhi","order":"15"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"hezhi","order":"14"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"hezhi","order":"13"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"hezhi","order":"12"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"hezhi","order":"11"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"hezhi","order":"10"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"hezhi","order":"9"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"hezhi","order":"8"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"hezhi","order":"7"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"hezhi","order":"6"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"hezhi","order":"5"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"hezhi","order":"4"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"5,6"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"4,6"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"4,5"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"3,6"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"3,5"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"3,4"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"2,6"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"2,5"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"2,4"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"2,3"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"1,6"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"1,5"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"1,4"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"1,3"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"erbutonghao","order":"1,2"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"yibutonghao","order":"6"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"yibutonghao","order":"5"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"yibutonghao","order":"4"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"yibutonghao","order":"3"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"yibutonghao","order":"2"},
			{"lotteryId":null,"issueCode":null,"webIssueCode":null,"type":null,"number":null,"drawTime":null,"name":"yibutonghao","order":"1"}]};
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
		//获取其他玩家投注信息
		getPlayerBetUrl: function() {
			return gameConfigData['playerBetUrl'];
		},
		getWinningListUrl: function() {
			return gameConfigData['winningListUrl'];
		},
		//获取其他玩家投注信息
		uploadUserInfo: function() {
			return gameConfigData['uploadUserInfo'];
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
		}

	};

	function GameConfig(){
		this.init();
	};
	GameConfig.prototype = pros;

	return host.GameConfig = GameConfig;

})(window);