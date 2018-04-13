/*
Base on jQuery-1.4.2.min.js
*/
$.BetMgr = function(selLotteryId, selMethodId, selModeId, divPlatesId, txtNumId, txtTimesId, txtMoneyId, divListId, selIssuesId, selTraceId, txtTraceTimesId, divBetDataId, txtTokenId){
	this.selLotteryId = selLotteryId;
	this.selMethodId = selMethodId;
	this.selModeId = selModeId;
	this.divPlatesId = divPlatesId;
	this.txtNumId = txtNumId;
	this.txtTimesId = txtTimesId;
	this.txtMoneyId = txtMoneyId;
	this.divListId = divListId;
	this.selIssuesId = selIssuesId;
	this.selTraceId = selTraceId;
	this.txtTraceTimesId = txtTraceTimesId;
	this.divBetDataId = divBetDataId;
	this.txtTokenId = txtTokenId;
	this.PLATFORM = null;
	this.LOTTERYS = null;
	this.METHODS = null;
	this.MODE = null;
	this.BET_DATA = null;
	this.BETS = [];
	
	this.PLATFORMS = {"PLATFORM_3":1
			, "PLATFORM_ADMIN":2
			, "PLATFORM_4":3
			, "PLATFORM_BM":4
			, "PLATFORM_BM_AGENT":5};
	
	this.BET_DATA_4 = {"BET_4":'{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOTTERY_ID#","issue":"#ISSUE#","list":#LIST#,"money":"#MONEY#"}'
			,"TRACE_4":'{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOTTERY_ID#","issue":"#ISSUE#","traceIstrace":1,"traceStop":0,"traceIssues":"#TRACE_ISSUES#","traceTimes":"#TRACE_TIMES#","list":#LIST#,"money":"#MONEY#"}'
			};
			
	this.BET_DATA_BM = {"BET_4":'{"jsessionid":"#TOKEN#","lottery_id":"#LOTTERY_ID#","isTrace":0,"traceWinStop":0,"amount":#MONEY#,"balls":#LIST#,"orders":#ORDERS#}'
			,"TRACE_4":'{"jsessionid":"#TOKEN#","lottery_id":"#LOTTERY_ID#","isTrace":1,"traceWinStop":0,"amount":#MONEY#,"balls":#LIST#,"orders":#ORDERS#}'
			};
	
	this.LOTTERYS_4 = {"CQSSC":{"name":"重庆时时采", "chan_id":"4", "lottery_id":"1"},
			"SD115":{"name":"山东11选5", "chan_id":"4", "lottery_id":"5"},
			"FC3D":{"name":"福彩3D", "chan_id":"1", "lottery_id":"1"},
			"P5":{"name":"P5", "chan_id":"1", "lottery_id":"2"},
			"SSQ":{"name":"双色求", "chan_id":"1", "lottery_id":"3"},
			"JSK3":{"name":"江苏快三", "chan_id":"4", "lottery_id":"16"}};			//////////////
	this.LOTTERYS_BM = {"CQSSC":{"name":"重庆时时采", "chan_id":"4", "lottery_id":"1"},
			"SD115":{"name":"山东11选5", "chan_id":"4", "lottery_id":"2"}};
	
	this.METHODS_4 = {"CQSSC":{"WuXingZhiXuan":{"name":"五星直选", methodid:"429", "plate":["divWan", "divQian", "divBai", "divShi", "divGe"]},
			"WuXingZuXuan120":{"name":"五星组选120", methodid:"435", "plate":["divXuan"]},
			"WuXingZuXuan60":{"name":"五星组选60", methodid:"436", "plate":["divErZhongHao", "divDanHao"]},
			"WuXingZuXuan30":{"name":"五星组选30", methodid:"427", "plate":["divErZhongHao", "divDanHao"]},
			"WuXingZuXuan20":{"name":"五星组选20", methodid:"438", "plate":["divSanZhongHao", "divDanHao"]},
			"WuXingZuXuan10":{"name":"五星组选10", methodid:"439", "plate":["divSanZhongHao", "divErZhongHao"]},
			"WuXingZuXuan5":{"name":"五星组选5", methodid:"440", "plate":["divSiZhongHao", "divDanHao"]},
			//"WuXingYiMaBuDingWei":{"name":"五星一码不定位", methodid:"0", "plate":["divXuan"]},
			"WuXingErMaBuDingWei":{"name":"五星二码不定位", methodid:"485", "plate":["divXuan"]},
			"WuXingSanMaBuDingWei":{"name":"五星三码不定位", methodid:"487", "plate":["divXuan"]},
			"WuXingYiFanFengShun":{"name":"五星一帆风顺", methodid:"508", "plate":["divXuan"]},
			"WuXingHaoShiChengShuang":{"name":"五星好事成双", methodid:"509", "plate":["divXuan"]},
			"WuXingSanXingBaoXi":{"name":"五星三星报喜", methodid:"510", "plate":["divXuan"]},
			"WuXingSiJiFaCai":{"name":"五星四季发财", methodid:"511", "plate":["divXuan"]},
			
			"SiXingZhiXuan":{"name":"四星直选", methodid:"442", "plate":["divQian", "divBai", "divShi", "divGe"]},
			"SiXingZuXuan24":{"name":"四星组选24", methodid:"448", "plate":["divXuan"]},
			"SiXingZuXuan12":{"name":"四星组选12", methodid:"449", "plate":["divErZhongHao", "divDanHao"]},
			"SiXingZuXuan6":{"name":"四星组选6", methodid:"450", "plate":["divErZhongHao"]},
			//"SiXingZuXuan4":{"name":"四星组选4", methodid:"451", "plate":["divSanZhongHao", "divDanHao"]},
			"SiXingYiMaBuDingWei":{"name":"四星一码不定位", methodid:"481", "plate":["divXuan"]},
			"SiXingErMaBuDingWei":{"name":"四星二码不定位", methodid:"483", "plate":["divXuan"]},
	
			"QianSanZhiXuan":{"name":"前三直选", methodid:"2", "plate":["divWan", "divQian", "divBai"]},
			"QianSanZhiXuanHeZhi":{"name":"前三直选和值", methodid:"3", "plate":["divXuan0to27"]},
			"QianSanZhiXuanKuaDu":{"name":"前三直选跨度", methodid:"452", "plate":["divXuan"]},
			"QianSanZuXuanHeZhi":{"name":"前三组选和值", methodid:"11", "plate":["divXuan1to26"]},
			"QianSanZuSan":{"name":"前三组三", methodid:"8", "plate":["divXuan"]},
			"QianSanZuLiu":{"name":"前三组六", methodid:"9", "plate":["divXuan"]},
			"QianSanZuXuanBaoDan":{"name":"前三组选包胆", methodid:"453", "plate":["divXuan"]},
			"QianSanYiMaBuDingWei":{"name":"前三一码不定位", methodid:"512", "plate":["divXuan"]},
			"QianSanErMaBuDingWei":{"name":"前三二码不定位", methodid:"513", "plate":["divXuan"]},
			
			"HouSanZhiXuan":{"name":"后三直选", methodid:"5", "plate":["divBai", "divShi", "divGe"]},
			"HouSanZhiXuanHeZhi":{"name":"后三直选和值", methodid:"6", "plate":["divXuan0to27"]},
			"HouSanZhiXuanKuaDu":{"name":"后三直选跨度", methodid:"462", "plate":["divXuan"]},
			"HouSanZuXuanHeZhi":{"name":"后三组选和值", methodid:"16", "plate":["divXuan1to26"]},
			"HouSanZuSan":{"name":"后三组三", methodid:"13", "plate":["divXuan"]},
			"HouSanZuLiu":{"name":"后三组六", methodid:"14", "plate":["divXuan"]},
			"HouSanZuXuanBaoDan":{"name":"后三组选包胆", methodid:"463", "plate":["divXuan"]},
			"HouSanYiMaBuDingWei":{"name":"后三一码不定位", methodid:"18", "plate":["divXuan"]},
			"HouSanErMaBuDingWei":{"name":"后三二码不定位", methodid:"20", "plate":["divXuan"]},
			
			"QianErZhiXuan":{"name":"前二直选", methodid:"22", "plate":["divWan", "divQian"]},
			"QianErZhiXuanHeZhi":{"name":"前二直选和值", methodid:"472", "plate":["divXuan0to18"]},
			"QianErZhiXuanKuaDu":{"name":"前二直选跨度", methodid:"473", "plate":["divXuan"]},
			"QianErZuXuan":{"name":"前二组选", methodid:"26", "plate":["divXuan"]},
			"QianErZuXuanHeZhi":{"name":"前二组选和值", methodid:"476", "plate":["divXuan1to17"]},
			"QianErZuXuanBaoDan":{"name":"前二组选包胆", methodid:"477", "plate":["divXuan"]},
			
			"HouErZhiXuan":{"name":"后二直选", methodid:"24", "plate":["divShi", "divGe"]},
			"HouErZhiXuanHeZhi":{"name":"后二直选和值", methodid:"474", "plate":["divXuan0to18"]},
			"HouErZhiXuanKuaDu":{"name":"后二直选跨度", methodid:"475", "plate":["divXuan"]},
			"HouErZuXuan":{"name":"后二组选", methodid:"28", "plate":["divXuan"]},
			"HouErZuXuanHeZhi":{"name":"后二组选和值", methodid:"478", "plate":["divXuan1to17"]},
			"HouErZuXuanBaoDan":{"name":"后二组选包胆", methodid:"479", "plate":["divXuan"]},
	
			"YiXingDingWeiDan":{"name":"一星定位胆", methodid:"30", "plate":["divWan", "divQian", "divBai", "divShi", "divGe"]},
			},
		"SD115":{"QianSanYiMaBuDingWei":{"name":"前三一码不定位", methodid:"228", "plate":["div11Xuan5"]},
			"DingWeiDan":{"name":"定位胆", methodid:"230", "plate":["divDiYiWei", "divDiErWei", "divDiSanWei"]},
			"RenXuanYiZhongYi":{"name":"任选一中一", methodid:"238", "plate":["div11Xuan5"]},
			//"QianErZhiXuan":{"name":"前二直选", methodid:"1", "plate":["divDiYiWei", "divDiErWei"]},
			//"QianErZuXuan":{"name":"前二组选", methodid:"1", "plate":["div11Xuan5"]},
			//"QianErZuXuanDanTuo":{"name":"前二组选胆拖", methodid:"1", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			"RenXuanErZhongEr":{"name":"任选二中二", methodid:"240", "plate":["div11Xuan5"]},
			//"RenXuanErZhongErDanTuo":{"name":"任选二中二胆拖", methodid:"1", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			"QianSanZhiXuan11Xuan5":{"name":"前三直选", methodid:"220", "plate":["divDiYiWei", "divDiErWei", "divDiSanWei"]},
			"QianSanZuXuan11Xuan5":{"name":"前三组选", methodid:"222", "plate":["div11Xuan5"]},
			//"QianSanZuXuanDanTuo":{"name":"前三组选胆拖", methodid:"1", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			"RenXuanSanZhongSan":{"name":"任选三中三", methodid:"243", "plate":["div11Xuan5"]},
			//"RenXuanSanZhongSanDanTuo":{"name":"任选三中三胆拖", methodid:"1", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			"RenXuanSiZhongSi":{"name":"任选四中四", methodid:"246", "plate":["div11Xuan5"]},
			//"RenXuanSiZhongSiDanTuo":{"name":"任选四中四胆拖", methodid:"1", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			"RenXuanWuZhongWu":{"name":"任选五中五", methodid:"249", "plate":["div11Xuan5"]},
			"RenXuanWuZhongWuDanTuo":{"name":"任选五中五胆拖", methodid:"519", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			//"RenXuanLiuZhongWu":{"name":"任选六中五", methodid:"1", "plate":["div11Xuan5"]},
			//"RenXuanLiuZhongWuDanTuo":{"name":"任选六中五胆拖", methodid:"1", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			//"RenXuanQiZhongWu":{"name":"任选七中五", methodid:"1", "plate":["div11Xuan5"]},
			//"RenXuanQiZhongWuDanTuo":{"name":"任选七中五胆拖", methodid:"1", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			//"RenXuanBaZhongWu":{"name":"任选八中五", methodid:"1", "plate":["div11Xuan5"]},
			//"RenXuanBaZhongWuDanTuo":{"name":"任选八中五胆拖", methodid:"1", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			
			},
		"FC3D":{"SanXingZhiXuan":{"name":"三星直选", methodid:"9", "plate":["divBai", "divShi", "divGe"]},
			"SanXingZhiXuanHeZhi":{"name":"三星直选和值", methodid:"10", "plate":["divXuan0to27"]},
			//"SanXingZhiXuanKuaDu":{"name":"三星直选跨度", methodid:"1", "plate":["divXuan"]},
			"SanXingZuXuanHeZhi":{"name":"三星组选和值", methodid:"15", "plate":["divXuan1to26"]},
			"SanXingZuSan":{"name":"三星组三", methodid:"12", "plate":["divXuan"]},
			"SanXingZuLiu":{"name":"三星组六", methodid:"13", "plate":["divXuan"]},
			//"SanXingZuXuanBaoDan":{"name":"三星组选包胆", methodid:"1", "plate":["divXuan"]},
			"SanXingYiMaBuDingWei":{"name":"三星一码不定位", methodid:"16", "plate":["divXuan"]},
			"SanXingErMaBuDingWei":{"name":"三星二码不定位", methodid:"17", "plate":["divXuan"]},
			
			"QianErZhiXuan":{"name":"前二直选", methodid:"18", "plate":["divWan", "divQian"]},
			//"QianErZhiXuanHeZhi":{"name":"前二直选和值", methodid:"1", "plate":["divXuan0to18"]},
			//"QianErZhiXuanKuaDu":{"name":"前二直选跨度", methodid:"1", "plate":["divXuan"]},
			"QianErZuXuan":{"name":"前二组选", methodid:"20", "plate":["divXuan"]},
			//"QianErZuXuanHeZhi":{"name":"前二组选和值", methodid:"1", "plate":["divXuan1to17"]},
			//"QianErZuXuanBaoDan":{"name":"前二组选包胆", methodid:"1", "plate":["divXuan"]},
			
			"HouErZhiXuan":{"name":"后二直选", methodid:"19", "plate":["divShi", "divGe"]},
			//"HouErZhiXuanHeZhi":{"name":"后二直选和值", methodid:"1", "plate":["divXuan0to18"]},
			//"HouErZhiXuanKuaDu":{"name":"后二直选跨度", methodid:"1", "plate":["divXuan"]},
			"HouErZuXuan":{"name":"后二组选", methodid:"21", "plate":["divXuan"]},
			//"HouErZuXuanHeZhi":{"name":"后二组选和值", methodid:"1", "plate":["divXuan1to17"]},
			//"HouErZuXuanBaoDan":{"name":"后二组选包胆", methodid:"1", "plate":["divXuan"]},
			
			"YiXingDingWeiDan":{"name":"一星定位胆", methodid:"22", "plate":["divBai", "divShi", "divGe"]},
			},
		"P5":{"P5HouErZhiXuan":{"name":"P5后二直选", methodid:"1", "plate":["divShi", "divGe"]},
			"P5HouErZhiXuanHeZhi":{"name":"P5后二直选和值", methodid:"3", "plate":["divXuan0to18"]},
			"P5HouErZhiXuanKuaDu":{"name":"P5后二直选跨度", methodid:"4", "plate":["divXuan"]},
			"P5HouErZuXuan":{"name":"P5后二组选", methodid:"5", "plate":["divXuan"]},
			"P5HouErZuXuanHeZhi":{"name":"P5后二组选和值", methodid:"7", "plate":["divXuan1to17"]},
			"P5HouErZuXuanBaoDan":{"name":"P5后二组选包胆", methodid:"8", "plate":["divXuan"]},
			"YiXingDingWeiDan":{"name":"P5一星定位胆", methodid:"9", "plate":["divWan", "divQian", "divBai", "divShi", "divGe"]},
			"SanXingZhiXuan":{"name":"P3三星直选", methodid:"10", "plate":["divWan", "divQian", "divBai"]},
			"SanXingZhiXuanHeZhi":{"name":"P3三星直选和值", methodid:"12", "plate":["divXuan0to27"]},
			"SanXingZhiXuanKuaDu":{"name":"P3三星直选跨度", methodid:"13", "plate":["divXuan"]},
			"SanXingZuXuanHeZhi":{"name":"P3三星组选和值", methodid:"14", "plate":["divXuan1to26"]},
			"SanXingZuSan":{"name":"P3三星组三", methodid:"15", "plate":["divXuan"]},
			"SanXingZuLiu":{"name":"P3三星组六", methodid:"16", "plate":["divXuan"]},
			"SanXingZuXuanBaoDan":{"name":"P3三星组选包胆", methodid:"18", "plate":["divXuan"]},
			"SanXingYiMaBuDingWei":{"name":"P3三星一码不定位", methodid:"21", "plate":["divXuan"]},
			"SanXingErMaBuDingWei":{"name":"P3三星二码不定位", methodid:"22", "plate":["divXuan"]},
			"QianErZhiXuan":{"name":"P3前二直选", methodid:"23", "plate":["divWan", "divQian"]},
			"QianErZhiXuanHeZhi":{"name":"P3前二直选和值", methodid:"25", "plate":["divXuan0to18"]},
			"QianErZhiXuanKuaDu":{"name":"P3前二直选跨度", methodid:"26", "plate":["divXuan"]},
			"QianErZuXuan":{"name":"P3前二组选", methodid:"27", "plate":["divXuan"]},
			"QianErZuXuanHeZhi":{"name":"P3前二组选和值", methodid:"29", "plate":["divXuan1to17"]},
			"QianErZuXuanBaoDan":{"name":"P3前二组选包胆", methodid:"30", "plate":["divXuan"]},
			"P3HouErZhiXuan":{"name":"P3后二直选", methodid:"31", "plate":["divShi", "divGe"]},
			"P3HouErZhiXuanHeZhi":{"name":"P3后二直选和值", methodid:"33", "plate":["divXuan0to18"]},
			"P3HouErZhiXuanKuaDu":{"name":"P3后二直选跨度", methodid:"34", "plate":["divXuan"]},
			"P3HouErZuXuan":{"name":"P3后二组选", methodid:"35", "plate":["divXuan"]},
			"P3HouErZuXuanHeZhi":{"name":"P3后二组选和值", methodid:"37", "plate":["divXuan1to17"]},
			"P3HouErZuXuanBaoDan":{"name":"P3后二组选包胆", methodid:"38", "plate":["divXuan"]},
			},
		"SSQ":{"SSQFuShi":{"name":"复式", methodid:"61", "plate":["divRedBall", "divBlueBall"]},
			"SSQDanTuo":{"name":"胆拖", methodid:"62", "plate":["divRedBallDanMa", "divRedBallTuoMa", "divBlueBall"]},
			},
		
		"JSK3":{"JSK3HeZhi":{"name":"和值", methodid:"1", "plate":["divXuan3to18"]},
			"JSK3SanTongHaoTongXuan":{"name":"三同号通选", methodid:"2", "plate":["divSanTongHaoTongXuan"]},
			"JSK3SanTongHaoDanXuan":{"name":"三同号单选", methodid:"3", "plate":["divSanTongHaoDanXuan"]},
			"JSK3SanBuTongHaoBiaoZhun":{"name":"三不同号标准", methodid:"4", "plate":["divXuan1to6"]},
			"JSK3SanBuTongHaoDanTuo":{"name":"三不同号胆拖", methodid:"5", "plate":["divXuan1to6DanMa","divXuan1to6TuoMa"]},
			"JSK3SanLianHaoTongXuan":{"name":"三连号通选", methodid:"6", "plate":["divSanLianHaoTongXuan"]},
			"JSK3ErTongHaoFuXuan":{"name":"二同号复选", methodid:"7", "plate":["divErTongHao"]},
			"JSK3ErTongHaoDanXuan":{"name":"二同号单选", methodid:"8", "plate":["divErTongHao2", "divXuan1to6"]},
			"JSK3ErBuTongHaoBiaoZhun":{"name":"二不同号标准", methodid:"9", "plate":["divXuan1to6"]},
			"JSK3ErBuTongHaoDanTuo":{"name":"二不同号胆拖", methodid:"10", "plate":["divXuan1to6DanMa","divXuan1to6TuoMa"]},
			"JSK3CaiYiHao":{"name":"猜一号", methodid:"11", "plate":["divXuan1to6"]},

			},
	};
	
	this.METHODS_BM = {"CQSSC":{"WuXingZhiXuan":{"name":"五星直选", methodid:"68", "type":"wuxing.zhixuan.fushi", "plate":["divWan", "divQian", "divBai", "divShi", "divGe"]},
			"WuXingZuXuan120":{"name":"五星组选120", methodid:"32", "type":"wuxing.zuxuan.zuxuan120", "plate":["divXuan"]},
			"WuXingZuXuan60":{"name":"五星组选60", methodid:"31", "type":"wuxing.zuxuan.zuxuan60", "plate":["divErZhongHao", "divDanHao"]},
			"WuXingZuXuan30":{"name":"五星组选30", methodid:"30", "type":"wuxing.zuxuan.zuxuan30", "plate":["divErZhongHao", "divDanHao"]},
			"WuXingZuXuan20":{"name":"五星组选20", methodid:"29", "type":"wuxing.zuxuan.zuxuan20", "plate":["divSanZhongHao", "divDanHao"]},
			"WuXingZuXuan10":{"name":"五星组选10", methodid:"28", "type":"wuxing.zuxuan.zuxuan10", "plate":["divSanZhongHao", "divErZhongHao"]},
			"WuXingZuXuan5":{"name":"五星组选5", methodid:"27", "type":"wuxing.zuxuan.zuxuan5", "plate":["divSiZhongHao", "divDanHao"]},
			//"WuXingYiMaBuDingWei":{"name":"五星一码不定位", methodid:"0", "type":"", "plate":["divXuan"]},
			"WuXingErMaBuDingWei":{"name":"五星二码不定位", methodid:"36", "type":"budingwei.wuxingbudingwei.wuxingermabudingwei", "plate":["divXuan"]},
			"WuXingSanMaBuDingWei":{"name":"五星三码不定位", methodid:"37", "type":"budingwei.wuxingbudingwei.wuxingsanmabudingwei", "plate":["divXuan"]},
			"WuXingYiFanFengShun":{"name":"五星一帆风顺", methodid:"44", "type":"quwei.teshu.yifanfengshun", "plate":["divXuan"]},
			"WuXingHaoShiChengShuang":{"name":"五星好事成双", methodid:"45", "type":"quwei.teshu.haoshichengshuang", "plate":["divXuan"]},
			"WuXingSanXingBaoXi":{"name":"五星三星报喜", methodid:"46", "type":"quwei.teshu.sanxingbaoxi", "plate":["divXuan"]},
			"WuXingSiJiFaCai":{"name":"五星四季发财", methodid:"47", "type":"quwei.teshu.sijifacai", "plate":["divXuan"]},
			
			"SiXingZhiXuan":{"name":"四星直选", methodid:"67", "type":"sixing.zhixuan.fushi", "plate":["divQian", "divBai", "divShi", "divGe"]},
			"SiXingZuXuan24":{"name":"四星组选24", methodid:"26", "type":"sixing.zuxuan.zuxuan24", "plate":["divXuan"]},
			"SiXingZuXuan12":{"name":"四星组选12", methodid:"25", "type":"sixing.zuxuan.zuxuan12", "plate":["divErZhongHao", "divDanHao"]},
			"SiXingZuXuan6":{"name":"四星组选6", methodid:"24", "type":"sixing.zuxuan.zuxuan6", "plate":["divErZhongHao"]},
			//"SiXingZuXuan4":{"name":"四星组选4", methodid:"451", "type":"sixing.zuxuan.zuxuan4", "plate":["divSanZhongHao", "divDanHao"]},
			"SiXingYiMaBuDingWei":{"name":"四星一码不定位", methodid:"34", "type":"budingwei.sixingbudingwei.sixingyimabudingwei", "plate":["divXuan"]},
			"SiXingErMaBuDingWei":{"name":"四星二码不定位", methodid:"35", "type":"budingwei.sixingbudingwei.sixingermabudingwei", "plate":["divXuan"]},
	
			"QianSanZhiXuan":{"name":"前三直选", methodid:"65", "type":"qiansan.zhixuan.fushi", "plate":["divWan", "divQian", "divBai"]},
			"QianSanZhiXuanHeZhi":{"name":"前三直选和值", methodid:"71", "type":"qiansan.zhixuan.hezhi", "plate":["divXuan0to27"]},
			"QianSanZhiXuanKuaDu":{"name":"前三直选跨度", methodid:"60", "type":"qiansan.zhixuan.kuadu", "plate":["divXuan"]},
			"QianSanZuXuanHeZhi":{"name":"前三组选和值", methodid:"75", "type":"qiansan.zuxuan.hezhi", "plate":["divXuan1to26"]},
			"QianSanZuSan":{"name":"前三组三", methodid:"16", "type":"qiansan.zuxuan.zusan", "plate":["divXuan"]},
			"QianSanZuLiu":{"name":"前三组六", methodid:"17", "type":"qiansan.zuxuan.zuliu", "plate":["divXuan"]},
			"QianSanZuXuanBaoDan":{"name":"前三组选包胆", methodid:"64", "type":"qiansan.zuxuan.baodan", "plate":["divXuan"]},
			"QianSanYiMaBuDingWei":{"name":"前三一码不定位", methodid:"18", "type":"budingwei.sanxingbudingwei.qiansanyimabudingwei", "plate":["divXuan"]},
			"QianSanErMaBuDingWei":{"name":"前三二码不定位", methodid:"21", "type":"budingwei.sanxingbudingwei.qiansanermabudingwei", "plate":["divXuan"]},
			
			"HouSanZhiXuan":{"name":"后三直选", methodid:"69", "type":"housan.zhixuan.fushi", "plate":["divBai", "divShi", "divGe"]},
			"HouSanZhiXuanHeZhi":{"name":"后三直选和值", methodid:"73", "type":"housan.zhixuan.hezhi", "plate":["divXuan0to27"]},
			"HouSanZhiXuanKuaDu":{"name":"后三直选跨度", methodid:"62", "type":"housan.zhixuan.kuadu", "plate":["divXuan"]},
			"HouSanZuXuanHeZhi":{"name":"后三组选和值", methodid:"80", "type":"housan.zuxuan.hezhi", "plate":["divXuan1to26"]},
			"HouSanZuSan":{"name":"后三组三", methodid:"49", "type":"housan.zuxuan.zusan", "plate":["divXuan"]},
			"HouSanZuLiu":{"name":"后三组六", methodid:"50", "type":"housan.zuxuan.zuliu", "plate":["divXuan"]},
			"HouSanZuXuanBaoDan":{"name":"后三组选包胆", methodid:"83", "type":"housan.zuxuan.baodan", "plate":["divXuan"]},
			"HouSanYiMaBuDingWei":{"name":"后三一码不定位", methodid:"51", "type":"budingwei.sanxingbudingwei.housanyimabudingwei", "plate":["divXuan"]},
			"HouSanErMaBuDingWei":{"name":"后三二码不定位", methodid:"52", "type":"budingwei.sanxingbudingwei.housanermabudingwei", "plate":["divXuan"]},
			
			"QianErZhiXuan":{"name":"前二直选", methodid:"66", "type":"erxing.zhixuan.qianerfushi", "plate":["divWan", "divQian"]},
			"QianErZhiXuanHeZhi":{"name":"前二直选和值", methodid:"72", "type":"erxing.zhixuan.qianerhezhi", "plate":["divXuan0to18"]},
			"QianErZhiXuanKuaDu":{"name":"前二直选跨度", methodid:"61", "type":"erxing.zhixuan.qianerkuadu", "plate":["divXuan"]},
			"QianErZuXuan":{"name":"前二组选", methodid:"20", "type":"erxing.zuxuan.qianerfushi", "plate":["divXuan"]},
			"QianErZuXuanHeZhi":{"name":"前二组选和值", methodid:"76", "type":"erxing.zuxuan.qianerhezhi", "plate":["divXuan1to17"]},
			"QianErZuXuanBaoDan":{"name":"前二组选包胆", methodid:"84", "type":"erxing.zuxuan.qianerbaodan", "plate":["divXuan"]},
			
			"HouErZhiXuan":{"name":"后二直选", methodid:"70", "type":"erxing.zhixuan.houerfushi", "plate":["divShi", "divGe"]},
			"HouErZhiXuanHeZhi":{"name":"后二直选和值", methodid:"74", "type":"erxing.zhixuan.houerhezhi", "plate":["divXuan0to18"]},
			"HouErZhiXuanKuaDu":{"name":"后二直选跨度", methodid:"63", "type":"erxing.zhixuan.houerkuadu", "plate":["divXuan"]},
			"HouErZuXuan":{"name":"后二组选", methodid:"59", "type":"erxing.zuxuan.houerfushi", "plate":["divXuan"]},
			"HouErZuXuanHeZhi":{"name":"后二组选和值", methodid:"77", "type":"erxing.zuxuan.houerhezhi", "plate":["divXuan1to17"]},
			"HouErZuXuanBaoDan":{"name":"后二组选包胆", methodid:"85", "type":"erxing.zuxuan.houerbaodan", "plate":["divXuan"]},
	
			"YiXingDingWeiDan":{"name":"一星定位胆", methodid:"78", "type":"yixing.dingweidan.fushi", "plate":["divWan", "divQian", "divBai", "divShi", "divGe"]},
			},
		"SD115":{
			//"QianSanYiMaBuDingWei":{"name":"前三一码不定位", methodid:"228", "type":"", "plate":["div11Xuan5"]},
			"DingWeiDan":{"name":"定位胆", methodid:"106", "type":"dingweidan.dingweidan.dingweidan", "plate":["divDiYiWei", "divDiErWei", "divDiSanWei"]},
			"RenXuanYiZhongYi":{"name":"任选一中一", methodid:"98", "type":"renxuanfushi.renxuanfushi.renxuanyi", "plate":["div11Xuan5"]},
			//"QianErZhiXuan":{"name":"前二直选", methodid:"1", "type":"", "plate":["divDiYiWei", "divDiErWei"]},
			//"QianErZuXuan":{"name":"前二组选", methodid:"1", "type":"", "plate":["div11Xuan5"]},
			//"QianErZuXuanDanTuo":{"name":"前二组选胆拖", methodid:"1", "type":"", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			"RenXuanErZhongEr":{"name":"任选二中二", methodid:"99", "type":"renxuanfushi.renxuanfushi.renxuaner", "plate":["div11Xuan5"]},
			//"RenXuanErZhongErDanTuo":{"name":"任选二中二胆拖", methodid:"1", "type":"", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			"QianSanZhiXuan11Xuan5":{"name":"前三直选", methodid:"112", "type":"sanma.zhixuan.fushi", "plate":["divDiYiWei", "divDiErWei", "divDiSanWei"]},
			"QianSanZuXuan11Xuan5":{"name":"前三组选", methodid:"108", "type":"sanma.zuxuan.fushi", "plate":["div11Xuan5"]},
			//"QianSanZuXuanDanTuo":{"name":"前三组选胆拖", methodid:"1", "type":"", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			"RenXuanSanZhongSan":{"name":"任选三中三", methodid:"100", "type":"renxuanfushi.renxuanfushi.renxuansan", "plate":["div11Xuan5"]},
			//"RenXuanSanZhongSanDanTuo":{"name":"任选三中三胆拖", methodid:"1", "type":"", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			"RenXuanSiZhongSi":{"name":"任选四中四", methodid:"101", "type":"renxuanfushi.renxuanfushi.renxuansi", "plate":["div11Xuan5"]},
			//"RenXuanSiZhongSiDanTuo":{"name":"任选四中四胆拖", methodid:"1", "type":"", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			"RenXuanWuZhongWu":{"name":"任选五中五", methodid:"102", "type":"renxuanfushi.renxuanfushi.renxuanwu", "plate":["div11Xuan5"]},
			"RenXuanWuZhongWuDanTuo":{"name":"任选五中五胆拖", methodid:"116", "type":"renxuandantuo.renxuandantuo.renxuanwu", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			//"RenXuanLiuZhongWu":{"name":"任选六中五", methodid:"1", "type":"", "plate":["div11Xuan5"]},
			//"RenXuanLiuZhongWuDanTuo":{"name":"任选六中五胆拖", methodid:"1", "type":"", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			//"RenXuanQiZhongWu":{"name":"任选七中五", methodid:"1", "type":"", "plate":["div11Xuan5"]},
			//"RenXuanQiZhongWuDanTuo":{"name":"任选七中五胆拖", methodid:"1", "type":"", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			//"RenXuanBaZhongWu":{"name":"任选八中五", methodid:"1", "type":"", "plate":["div11Xuan5"]},
			//"RenXuanBaZhongWuDanTuo":{"name":"任选八中五胆拖", methodid:"1", "type":"", "plate":["div11Xuan5DanMa", "div11Xuan5TuoMa"]},
			
			}};
	
	this.PLATES = [{"div":"divWan", "name":"万", "min": 0, "max": 9, "quick":1, "digits":0},
			{"div":"divQian", "name":"千", "min": 0, "max": 9, "quick":1, "digits":0},
			{"div":"divBai", "name":"百", "min": 0, "max": 9, "quick":1, "digits":0},
			{"div":"divShi", "name":"十", "min": 0, "max": 9, "quick":1, "digits":0},
			{"div":"divGe", "name":"个", "min": 0, "max": 9, "quick":1, "digits":0},
			{"div":"divSiZhongHao", "name":"四重号", "min": 0, "max": 9, "quick":1, "digits":0},
			{"div":"divSanZhongHao", "name":"三重号", "min": 0, "max": 9, "quick":1, "digits":0},
			{"div":"divErZhongHao", "name":"二重号", "min": 0, "max": 9, "quick":1, "digits":0},
			{"div":"divDanHao", "name":"单号", "min": 0, "max": 9, "quick":1, "digits":0},
			{"div":"divXuan", "name":"选", "min": 0, "max": 9, "quick":1, "digits":0},
			{"div":"divXuan0to27", "name":"选", "min": 0, "max": 27, "quick":0, "digits":0},
			{"div":"divXuan1to26", "name":"选", "min": 1, "max": 26, "quick":0, "digits":0},
			{"div":"divXuan0to18", "name":"选", "min": 0, "max": 18, "quick":0, "digits":0},
			{"div":"divXuan1to17", "name":"选", "min": 1, "max": 17, "quick":0, "digits":0},
			
			
			{"div":"divXuan3to18", "name":"选", "min": 3, "max": 18, "quick":0, "digits":0},
			{"div":"divSanTongHaoTongXuan", "name":"选", ary:[{"三同号通选":"111 222 333 444 555 666"}], "quick":0, "digits":0},
			{"div":"divSanTongHaoDanXuan", "name":"选", ary:[{"111":"111","222":"222","333":"333","444":"444","555":"555","666":"666"}], "quick":0, "digits":0},
			{"div":"divErTongHao", "name":"选", ary:[{"1｜1":"11*"},{"2｜2":"22*"},{"3｜3":"33*"},{"4｜4":"44*"},{"5｜5":"55*"},{"6｜6":"66*"},], "quick":0, "digits":0},
			{"div":"divErTongHao2", "name":"选", ary:[{"1｜1":"11"},{"2｜2":"22"},{"3｜3":"33"},{"4｜4":"44"},{"5｜5":"55"},{"6｜6":"66"},], "quick":0, "digits":0},
			{"div":"divXuan1to6", "name":"选", "min": 1, "max": 6, "quick":0, "digits":0},
			{"div":"divSanLianHaoTongXuan", "name":"选", ary:[{"三连号通选":"123 234 345 456"}], "quick":0, "digits":0},
			
			{"div":"divXuan1to6DanMa", "name":"胆码", "min": 1, "max": 6, "quick":0, "digits":0},
			{"div":"divXuan1to6TuoMa", "name":"拖码", "min": 1, "max": 6, "quick":0, "digits":0},
			
			
			
			
			
			{"div":"div11Xuan5", "name":"选", "min": 1, "max": 11, "quick":1, "digits":1},
			{"div":"divDiYiWei", "name":"第一位", "min": 1, "max": 11, "quick":1, "digits":1},
			{"div":"divDiErWei", "name":"第二位", "min": 1, "max": 11, "quick":1, "digits":1},
			{"div":"divDiSanWei", "name":"第三位", "min": 1, "max": 11, "quick":1, "digits":1},
			{"div":"div11Xuan5DanMa", "name":"胆码", "min": 1, "max": 11, "quick":0, "digits":1},
			{"div":"div11Xuan5TuoMa", "name":"拖码", "min": 1, "max": 11, "quick":1, "digits":1},
			
			{"div":"divRedBall", "name":"红球区", "min": 1, "max": 33, "quick":0, "digits":1},
			{"div":"divRedBallDanMa", "name":"红球胆码区", "min": 1, "max": 33, "quick":0, "digits":1},
			{"div":"divRedBallTuoMa", "name":"红球拖码区", "min": 1, "max": 33, "quick":0, "digits":1},
			{"div":"divBlueBall", "name":"蓝球区", "min": 1, "max": 16, "quick":0, "digits":1},
			];
	/*
	this.MODE_4 = {"元":{"value":1, "unit":2},
			"角":{"value":2, "unit":0.2}};
				
	this.MODE_BM = {"元":{"value":1, "unit":2},
			"角":{"value":0.1, "unit":0.2},
			"分":{"value":0.2, "unit":0.02}};
	*/
	
	this.MODE_4 = [{"name":"元","mode":1,"unit":2}
			,{"name":"角","mode":2,"unit":0.2
				,{"name":"分","mode":3,"unit":0.02}}];
				
	this.MODE_BM = [{"name":"元","mode":1,"unit":2}
			,{"name":"角","mode":0.1,"unit":0.2}
			,{"name":"分","mode":0.2,"unit":0.02}];
	this.Init();
};
$.BetMgr.prototype = {
	Init: function(){
		var betMgr = this;
		$("#selLotteryId").change(function(){
			betMgr.SelectLottery();
		});
		$("#" + this.selModeId).change(function(){
			betMgr.RefreshData();
		});
		$("#" + this.selMethodId).change(function(){
			betMgr.SelectMethod();
			betMgr.RefreshData();
		});
		$("#" + this.selIssuesId).change(function(){
			var total = $("#" + this.selIssuesId + " option").length;
			var iCount = 0;
			$("#" + this.selIssuesId + " option").each(function(){
					if($(this).is(':selected'))
					{
						return false;	
					}
					iCount++;
				});
			$('#' + this.selTraceId)
				.find('option')
				.remove()
				.end();
			for(var i = 1;i <= total - iCount;i++)
			{
				$("#" + this.selTraceId).append("<option value='" + i + "'>" + i + "</option>");
			}
			betMgr.CreateBetData();
		});
		$("#" + this.selTraceId).change(function(){
			betMgr.CreateBetData();
			});
		$("#" + this.txtTraceTimesId).change(function(){
			betMgr.CreateBetData();
			});	
			
		this.CreatePlates();
		this.HidePlates();
		this.SelectMethod();
	},
	SetPlatform: function(platform){
		this.PLATFORM = platform;
		if(this.PLATFORM == this.PLATFORMS["PLATFORM_4"]){
			this.LOTTERYS = this.LOTTERYS_4;
			this.METHODS = this.METHODS_4;
			this.MODE = this.MODE_4;
			this.BET_DATA = this.BET_DATA_4;
		}else if(this.PLATFORM == this.PLATFORMS["PLATFORM_BM"]){
			this.LOTTERYS = this.LOTTERYS_BM;
			this.METHODS = this.METHODS_BM;
			this.MODE = this.MODE_BM;
			this.BET_DATA = this.BET_DATA_BM;
		}else{
			this.LOTTERYS = null;
			this.METHODS = null;
			this.MODE = null;
			this.BET_DATA = null;	
		}
		// lottery
		$('#' + this.selLotteryId)
				.find('option')
				.remove()
				.end();
		for(var key in this.LOTTERYS)
		{
			$("#" + this.selLotteryId).append("<option value='" + key + "'>" + this.LOTTERYS[key]["name"] + "</option>");
		}
		// method
		this.SelectLottery();
		// mode
		$('#' + this.selModeId)
				.find('option')
				.remove()
				.end();
		for(var key in this.MODE)
		{
			$("#" + this.selModeId).append("<option value='" + this.MODE[key]["unit"] + "'>" + this.MODE[key]["name"] + "</option>");
		}
	},
	SelectLottery: function(){
		$('#' + this.selMethodId)
			.find('option')
			.remove()
			.end();
		if($("#" + this.selLotteryId).val() == null)
		{
			return;	
		}
		var selLottery = this.METHODS[$("#" + this.selLotteryId).val()];
		for(var key in selLottery)
		{
			$("#" + this.selMethodId).append("<option value='" + $("#" + this.selLotteryId).val() + "_" + key + "'>" + selLottery[key]["name"] + "</option>");
		}
		this.SelectMethod();
		this.RefreshData();
		this.ClearBets();
	},
	SelectMethod: function(){
		if($("#" + this.selMethodId).val() == null)
		{
			return;	
		}
		this.HidePlates();
		var lottery = $("#" + this.selMethodId).val().split("_")[0];
		var method = $("#" + this.selMethodId).val().split("_")[1];
		
		var plates = this.METHODS[lottery][method]["plate"];
		
		for(var key in plates)
		{
			$("#" + plates[key]).show();
		}
		
		switch(method){
			case "QianSanZuXuanBaoDan":
			case "HouSanZuXuanBaoDan":
			case "QianErZuXuanBaoDan":
			case "HouErZuXuanBaoDan":
			case "P5HouErZuXuanBaoDan":
			case "P3HouErZuXuanBaoDan":
			case "SanXingZuXuanBaoDan":
				$("#divXuan div.funcs").hide();
				break;
			default:
				$("#divXuan div.funcs").show();
		}	
	},
	HidePlates: function(){
		$("#" + this.divPlatesId + " div.plate").each(function(){
					$(this).hide();
				});
		this.ClearBalls();	
	},
	ClearBalls: function(){
		$("div[class=ball_enable]").each(function(){
			$(this).attr("class", "ball");
				});
	},
	CreatePlates: function(){
		var betMgr = this;
		var r = $("#" + this.divPlatesId);
		for(i = 0;i < this.PLATES.length;i++)
		{
			var did = this.PLATES[i]["div"];
			var min = this.PLATES[i]["min"];
			var max = this.PLATES[i]["max"];
			var ary = this.PLATES[i]["ary"];
			var quick = this.PLATES[i]["quick"];
			var digits = this.PLATES[i]["digits"];
			r.append('<div id="' + did + '" class="plate"></div><div style="clear: both;"></div>');
			
			var d = new $.DivMgr($("#" + did));
			d.AddDiv().Attr("class", "position").InnerHtml(this.PLATES[i]["name"]).ToParent();
			d.AddDiv().Attr("class", "balls");
			if(ary != null)
			{
				for(var key in ary)
				{
					for(var key2 in ary[key])
					{
						d.AddDiv().ID(did + "_ball_" + ary[key][key2] + "_" + digits).Attr("class", "ball").InnerHtml(key2);
						$(d.focusNode).data("codes",ary[key][key2]);
						$(d.focusNode).click(function(){
								betMgr.PreExe();
								if($(this).attr("class") == "ball")
								{
									$(this).attr("class", "ball_enable");
								}
								else
								{
									$(this).attr("class", "ball");
								}
								betMgr.RefreshData();
							});
						d.ToParent();
					}
				}
			}	
			else
			{
				for(j = min;j <= max;j++)
				{
					var num = j;
					if(digits == 1)
					{
						num = num.toFill(2, "0");	
					}
					
					d.AddDiv().ID(did + "_ball_" + num + "_" + digits).Attr("class", "ball").InnerHtml(num);
					$(d.focusNode).data("codes", num);
					$(d.focusNode).click(function(){
							betMgr.PreExe();
							if($(this).attr("class") == "ball")
							{
								$(this).attr("class", "ball_enable");
							}
							else
							{
								$(this).attr("class", "ball");
							}
							betMgr.RefreshData();
						});
					d.ToParent();
				}
			}
			d.ToParent();
			if(quick == 0)
			{
				continue;	
			}
			
			d.AddDiv().Attr("class", "funcs");
			
			d.AddDiv().ID(did + "_all").Attr("class", "func").InnerHtml("全");
			$(d.focusNode).click(function(){
					betMgr.QuickSelect($(this).attr("id"));
					betMgr.RefreshData();
					});
			d.ToParent();
			d.AddDiv().ID(did + "_big").Attr("class", "func").InnerHtml("大");
			$(d.focusNode).click(function(){
					betMgr.QuickSelect($(this).attr("id"));
					betMgr.RefreshData();
					});
			d.ToParent();
			d.AddDiv().ID(did + "_small").Attr("class", "func").InnerHtml("小");
			$(d.focusNode).click(function(){
					betMgr.QuickSelect($(this).attr("id"));
					betMgr.RefreshData();
					});
			d.ToParent();
			d.AddDiv().ID(did + "_odd").Attr("class", "func").InnerHtml("单");
			$(d.focusNode).click(function(){
					betMgr.QuickSelect($(this).attr("id"));
					betMgr.RefreshData();
					});
			d.ToParent();
			d.AddDiv().ID(did + "_even").Attr("class", "func").InnerHtml("双");
			$(d.focusNode).click(function(){
					betMgr.QuickSelect($(this).attr("id"));
					betMgr.RefreshData();
					});
			d.ToParent();
			d.AddDiv().ID(did + "_clear").Attr("class", "func").InnerHtml("清");
			$(d.focusNode).click(function(){
					betMgr.QuickSelect($(this).attr("id"));
					betMgr.RefreshData();
					});
			d.ToParent();
			d.ToParent();
		}
	},
	AddBet: function(){
		var nums = $("#" + this.txtNumId).html();
		if(nums > 0)
		{
			$("#" + this.divListId).html("");
			var lottery = $("#" + this.selMethodId).val().split("_")[0];
			var method = $("#" + this.selMethodId).val().split("_")[1];
			
			var unit = $("#" + this.selModeId).val();
			var times = $("#" + this.txtTimesId).val();
			var money = $("#" + this.txtMoneyId).html();
		
			var bet = {};
			bet["lottery"] = lottery;
			bet["method"] = method;
			bet["nums"] = nums;
			bet["mode"] = this.GetMode(unit);
			bet["times"] = times;
			bet["money"] = money;
			bet["methodid"] = this.METHODS[lottery][method]["methodid"];
			bet["name"] = this.METHODS[lottery][method]["name"];
			bet["type"] = this.METHODS[lottery][method]["type"];
			bet["codes"] = this.SelectedBalls();
			
			this.BETS.push(bet);
			
			var d = new $.DivMgr($("#divList"));
			for(var i = 0;i < this.BETS.length;i++)
			{
				d.AddDiv().Attr("class", "data_row");
				d.AddDiv().Attr("class", "data_nums").InnerHtml(this.BETS[i]["nums"] + "注").ToParent();
				d.AddDiv().Attr("class", "data_mode").InnerHtml(this.GetModeText(this.BETS[i]["mode"])).ToParent();
				d.AddDiv().Attr("class", "data_times").InnerHtml(this.BETS[i]["times"] + "倍").ToParent();
				d.AddDiv().Attr("class", "data_name").InnerHtml(this.BETS[i]["name"] + "(" + this.BETS[i]["methodid"] + ")").ToParent();
				d.AddDiv().Attr("class", "data_money").InnerHtml(this.BETS[i]["money"] + "元").ToParent();
				d.AddDiv().Attr("class", "data_codes").InnerHtml(this.CreateCodes(this.BETS[i])).ToParent();
				d.ToParent();
			}
			this.ClearBalls();
			this.RefreshData();
			
			
		}
		else
		{
			alert("请选择号码");
		}	
	},
	GetMode: function(unit){
		var mode;
		for(var key in this.MODE)
		{
			if(this.MODE[key]["unit"] == unit)
			{
				mode = this.MODE[key];
			}
		}
		return mode["mode"];
	},
	GetModeText: function(mode){
		var mode;
		for(var key in this.MODE)
		{
			if(this.MODE[key]["mode"] == mode)
			{
				mode = this.MODE[key];
			}
		}
		return mode["name"];
	},
	GetModeUnit: function(mode){
		var mode;
		for(var key in this.MODE)
		{
			if(this.MODE[key]["mode"] == mode)
			{
				mode = this.MODE[key];
			}
		}
		return mode["unit"];
	},
	PreExe: function(){
		var lottery = $("#" + this.selMethodId).val().split("_")[0];
		var method = $("#" + this.selMethodId).val().split("_")[1];
		
		switch(method){
			case "QianSanZuXuanBaoDan":
			case "HouSanZuXuanBaoDan":
			case "QianErZuXuanBaoDan":
			case "HouErZuXuanBaoDan":
			case "P5HouErZuXuanBaoDan":
			case "P3HouErZuXuanBaoDan":
			case "SanXingZuXuanBaoDan":
				$("div[id^=divXuan_ball]").each(function(){
					$(this).attr("class", "ball");
					});
				break;
			default:
		}
	},
	CreateCodes: function(bet){
		var balls = bet["codes"];
		var codes = "";
		var iCount = 0;
		var iPlateLen = Object.keys(balls).length;
		
		if(this.PLATFORM == this.PLATFORMS["PLATFORM_BM"]){
			if(bet["lottery"] == "SD115")
			{
				for(var key in balls) {
					if(iPlateLen == 1){
						codes = balls[key].join(" ");
					}else{
						if(iCount != 0)
						{
							codes += "|";
						}
						if(balls[key].length == 0){
							codes += "";
						}else{
							codes += balls[key].join(" ");
						}
					}
					iCount++;
				}
			}else if(bet["lottery"] == "CQSSC"){
				for(var key in balls) {
					if(iPlateLen == 1){
						codes = balls[key].join(" ");
					}else{
						if(iCount != 0)
						{
							codes += "|";
						}
						
						if(balls[key].length == 0){
							codes += "";
						}else{
							for(var i = 0;i < balls[key].length;i++)
							{
								codes += balls[key][i];
							}
						}
						iCount++;
					}
				}
			}
		}else{
			if(bet["lottery"] == "SD115")
			{
				for(var key in balls) {
					if(bet["method"] == "RenXuanWuZhongWuDanTuo"){
						if(iCount == 0)
						{
							codes += "[胆";
							codes += balls[key].join(",");
							codes += "] ";
						}else if(iCount == 1){
							codes += balls[key].join(",");
						}
					}else{
						if(iPlateLen == 1){
							codes = balls[key].join(",");
						}else{
							if(iCount != 0)
							{
								codes += ",";
							}
							
							if(balls[key].length == 0){
								codes += "-";
							}else{
								codes += balls[key].join(" ");
							}
						}
					}
					iCount++;
				}
				
				if(bet["method"] == "DingWeiDan"){
					codes = codes + ",-,-";	
				}else if(bet["method"] == "QianSanZhiXuan11Xuan5"){
					codes = codes + ",-,-";
				}
			}else if(bet["lottery"] == "FC3D"){
				for(var key in balls) {
					if(iPlateLen == 1){
						codes = balls[key].join(",");
					}else{
						if(iCount != 0)
						{
							codes += ",";
						}
						
						if(balls[key].length == 0){
							codes += "-";
						}else{
							for(var i = 0;i < balls[key].length;i++)
							{
								codes += balls[key][i];
							}
						}
						iCount++;
					}
				}
				if(bet["method"] == "QianErZhiXuan"){
					codes = codes + ",-";
				}else if(bet["method"] == "HouErZhiXuan"){	
					codes = "-," + codes;
				}
			}else if(bet["lottery"] == "CQSSC"){
				for(var key in balls) {
					if(iPlateLen == 1){
						codes = balls[key].join(",");
					}else{
						if(iCount != 0)
						{
							codes += ",";
						}
						
						if(balls[key].length == 0){
							codes += "-";
						}else{
							for(var i = 0;i < balls[key].length;i++)
							{
								codes += balls[key][i];
							}
						}
						iCount++;
					}
				}
				if(bet["method"] == "SiXingZhiXuan"){
					codes = "-," + codes;
				}else if(bet["method"] == "QianSanZhiXuan"){
					codes = codes + ",-,-";	
				}else if(bet["method"] == "HouSanZhiXuan"){	
					codes = "-,-," + codes;
				}else if(bet["method"] == "QianErZhiXuan"){	
					codes = codes + ",-,-,-";
				}else if(bet["method"] == "HouErZhiXuan"){	
					codes = "-,-,-," + codes;
				}
			}else if(bet["lottery"] == "P5"){
				for(var key in balls) {
					if(iPlateLen == 1){
						codes = balls[key].join(",");
					}else{
						if(iCount != 0)
						{
							codes += ",";
						}
						
						if(balls[key].length == 0){
							codes += "-";
						}else{
							for(var i = 0;i < balls[key].length;i++)
							{
								codes += balls[key][i];
							}
						}
						iCount++;
					}
				}
				if(bet["method"] == "SiXingZhiXuan"){
					codes = "-," + codes;
				}else if(bet["method"] == "P5HouErZhiXuan"){	
					codes = "-,-,-," + codes;
				}else if(bet["method"] == "P3HouErZhiXuan"){	
					codes = "-," + codes;
				}else if(bet["method"] == "QianErZhiXuan"){	
					codes = codes + ",-";
				}
			}else if(bet["lottery"] == "SSQ"){
				if(bet["method"] == "SSQFuShi"){
					for(var key in balls) {
						if(iCount == 0)
						{
							codes += balls[key].join(",");
						}else if(iCount == 1){
							codes += "+" + balls[key].join(",");	
						}
						iCount++;
					}
				}else if(bet["method"] == "SSQDanTuo"){
					for(var key in balls) {
						if(iCount == 0)
						{
							codes += "D:" + balls[key].join(",");
						}else if(iCount == 1){
							codes += "_T:" + balls[key].join(",");	
						}else if(iCount == 2){
							codes += "+" + balls[key].join(",");
						}
						iCount++;
					}
				}
			}else if(bet["lottery"] == "JSK3"){
				if(bet["method"] == "JSK3HeZhi"
					|| bet["method"] == "JSK3SanBuTongHaoBiaoZhun"
					|| bet["method"] == "JSK3ErTongHaoFuXuan"
					|| bet["method"] == "JSK3ErBuTongHaoBiaoZhun"
					|| bet["method"] == "JSK3CaiYiHao"){
					for(var key in balls) {
						codes = balls[key].join(",");
					}
				}else if(bet["method"] == "JSK3ErTongHaoDanXuan"){
					for(var key in balls) {
						codes += balls[key].join(" ");
						if(iCount == 0)
						{
							codes += "#";
						}
						iCount++;
					}
				}else if(bet["method"] == "JSK3SanBuTongHaoDanTuo"
					|| bet["method"] == "JSK3ErBuTongHaoDanTuo"){
					for(var key in balls) {
						if(iCount == 0)
						{
							codes += "D:";
							codes += balls[key].join(",");
							codes += "_";
						}else if(iCount == 1){
							codes += "T:";
							codes += balls[key].join(",");
						}
						iCount++;
					}
				}else{
					for(var key in balls) {
						if(iPlateLen == 1){
							codes = balls[key].join(" ");
						}else{
							if(iCount != 0)
							{
								codes += ",";
							}
							
							if(balls[key].length == 0){
								codes += "-";
							}else{
								for(var i = 0;i < balls[key].length;i++)
								{
									codes += balls[key][i];
								}
							}
							iCount++;
						}
					}
				}
			}
		}
		
		return codes;
	},
	SelectedBalls: function(){
		var aryBalls = {};
		for(var i = 0;i < this.PLATES.length;i++)
		{
			if(!$("#" + this.PLATES[i]["div"]).is(":visible"))
			{
				continue;	
			}
			var balls = [];
			$("div[id^=" + this.PLATES[i]["div"] + "_ball]").each(function(){
							//$(this).attr("class", "ball_enable");
							if($(this).attr("class") == "ball_enable")
							{
								//balls.push($(this).html());
								balls.push($(this).data("codes"));
								//alert($(this).data("codes"));
							}
						});
			aryBalls[this.PLATES[i]["div"]] = balls;
		}
		return aryBalls;
	},
	RefreshData: function(){
		var balls = this.SelectedBalls();
		var nums = 0;
		var mode = $("#" + this.selModeId).val();
		var times = $("#" + this.txtTimesId).val();
		
		var lottery = $("#" + this.selMethodId).val().split("_")[0];
		var method = $("#" + this.selMethodId).val().split("_")[1];
		
		switch(method){
			case "WuXingZuXuan120":
				nums = this.Combine(balls["divXuan"].length, 5);
				break;
			case "WuXingZuXuan60":
				var iErZhong = balls["divErZhongHao"].length;
				var iDan = balls["divDanHao"].length;
				var iSame = this.CheckSame(balls["divErZhongHao"], balls["divDanHao"]);
				if(iErZhong == iDan && iDan == iSame){
					nums = this.Combine(iSame, 1) * this.Combine(iDan - 1, 3);
				}else if(iSame == 0){
					nums = this.Combine(iErZhong, 1) * this.Combine(iDan, 3);
				}else if(iSame != 0){
					nums = this.Combine(iSame, 1) * this.Combine(iDan - 1, 3) + this.Combine(iErZhong - iSame, 1) * this.Combine(iDan, 3);
				}
				break;
			case "WuXingZuXuan30":
				var iErZhong = balls["divErZhongHao"].length;
				var iDan = balls["divDanHao"].length;
				var iSame = this.CheckSame(balls["divErZhongHao"], balls["divDanHao"]);
				if(iErZhong == iDan && iDan == iSame){
					nums = this.Combine(iSame, 1) * this.Combine(iErZhong - 1, 2);
				}else if(iSame == 0){
					nums = this.Combine(iDan, 1) * this.Combine(iErZhong, 2);
				}else if(iSame != 0){
					nums = this.Combine(iSame, 1) * this.Combine(iErZhong - 1, 2) + this.Combine(iDan - iSame, 1) * this.Combine(iErZhong, 2);
				}
				break;
			case "WuXingZuXuan20":
				var iSanZhong = balls["divSanZhongHao"].length;
				var iDan = balls["divDanHao"].length;
				var iSame = this.CheckSame(balls["divSanZhongHao"], balls["divDanHao"]);
				var iDiff = iSanZhong - iSame;
				if(iSanZhong ==  iDan && iDan == iSame){
					nums = this.Combine(iSanZhong, 1) * this.Combine(iDan - 1, 2);
				}else if(iSame == 0){
					nums = this.Combine(iDiff, 1) * this.Combine(iDan, 2);
				}else if(iSame != 0){
					nums = this.Combine(iSame, 1) * this.Combine(iDiff, 0) * this.Combine(iDan - 1, 2) + this.Combine(iSame, 0) * this.Combine(iDiff, 1) * this.Combine(iDan - 0, 2);
				}
				break;
			case "WuXingZuXuan10":
				var iSanZhong = balls["divSanZhongHao"].length;
				var iErZhong = balls["divErZhongHao"].length;
				var iSame = this.CheckSame(balls["divSanZhongHao"], balls["divErZhongHao"]);
				var iDiff = iSanZhong - iSame;
				if(iSanZhong ==  iErZhong && iErZhong == iSame){
					nums = this.Combine(iSame, 1) * this.Combine(iErZhong - 1, 1);
				}else if(iSame == 0){
					nums = this.Combine(iSanZhong, 1) * this.Combine(iErZhong, 1);
				}else if(iSame != 0){
					nums = this.Combine(iSame, 1) * this.Combine(iDiff, 0) * this.Combine(iErZhong - 1, 1) + this.Combine(iSame, 0) * this.Combine(iDiff, 1) * this.Combine(iErZhong - 0, 1);
				}
				break;
			case "WuXingZuXuan5":
				var iSiZhong = balls["divSiZhongHao"].length;
				var iDan = balls["divDanHao"].length;
				var iSame = this.CheckSame(balls["divSiZhongHao"], balls["divDanHao"]);
				var iDiff = iSiZhong - iSame;
				if(iSiZhong ==  iDan && iDan == iSame){
					nums = this.Combine(iSame, 1) * this.Combine(iDan - 1, 1);
				}else if(iSame == 0){
					nums = this.Combine(iSiZhong, 1) * this.Combine(iDan, 1);
				}else if(iSame != 0){
					nums = this.Combine(iSame, 1) * this.Combine(iDiff, 0) * this.Combine(iDan - 1, 1) + this.Combine(iSame, 0) * this.Combine(iDiff, 1) * this.Combine(iDan - 0, 1);
				}
				break;
			case "WuXingErMaBuDingWei":
				nums = this.Combine(balls["divXuan"].length, 2);
				break;
			case "WuXingSanMaBuDingWei":
				nums = this.Combine(balls["divXuan"].length, 3);
				break;
			case "SiXingZuXuan24":
				nums = this.Combine(balls["divXuan"].length, 4);
				break;
			case "SiXingZuXuan12":
				var iErZhong = balls["divErZhongHao"].length;
				var iDan = balls["divDanHao"].length;
				var iSame = this.CheckSame(balls["divErZhongHao"], balls["divDanHao"]);
				var iDiff = iErZhong - iSame;
				if(iErZhong ==  iDan && iDan == iSame){
					nums = this.Combine(iSame, 1) * this.Combine(iDan - 1, 2);
				}else if(iSame == 0){
					nums = this.Combine(iErZhong, 1) * this.Combine(iDan, 2);
				}else if(iSame != 0){
					nums = this.Combine(iSame, 1) * this.Combine(iDiff, 0) * this.Combine(iDan - 1, 2) + this.Combine(iSame, 0) * this.Combine(iDiff, 1) * this.Combine(iDan - 0, 2);
				}
				break;
			case "SiXingZuXuan6":
				var iErZhong = balls["divErZhongHao"].length;
				nums = this.Combine(iErZhong, 2);
				break;
			case "SiXingErMaBuDingWei":
			case "QianSanErMaBuDingWei":
			case "HouSanErMaBuDingWei":
			case "QianErZuXuan":
			case "HouErZuXuan":
			case "P5HouErZuXuan":
			case "P3HouErZuXuan":
			case "SanXingErMaBuDingWei":
				nums = this.Combine(balls["divXuan"].length, 2);
				break;
			case "QianSanZhiXuanHeZhi":
			case "HouSanZhiXuanHeZhi":
			case "SanXingZhiXuanHeZhi":
				for(var i = 0;i < balls["divXuan0to27"].length;i++)
				{
					var no = balls["divXuan0to27"][i];
					if(no == "0" || no == "27"){
						nums += 1;
					}else if(no == "1" || no == "26"){
						nums += 3;
					}else if(no == "2" || no == "25"){
						nums += 6;
					}else if(no == "3" || no == "24"){
						nums += 10;
					}else if(no == "4" || no == "23"){
						nums += 15;
					}else if(no == "5" || no == "22"){
						nums += 21;
					}else if(no == "6" || no == "21"){
						nums += 28;
					}else if(no == "7" || no == "20"){
						nums += 36;
					}else if(no == "8" || no == "19"){
						nums += 45;
					}else if(no == "9" || no == "18"){
						nums += 55;
					}else if(no == "10" || no == "17"){
						nums += 63;
					}else if(no == "11" || no == "16"){
						nums += 69;
					}else if(no == "12" || no == "15"){
						nums += 73;
					}else if(no == "13" || no == "14"){
						nums += 75;
					}
				}
				break;
			case "QianSanZhiXuanKuaDu":
			case "HouSanZhiXuanKuaDu":
			case "SanXingZhiXuanKuaDu":
				for(var i = 0;i < balls["divXuan"].length;i++)
				{
					var no = balls["divXuan"][i];
					if(no == "0"){
						nums += 10;
					}else if(no == "1" || no == "9"){
						nums += 54;
					}else if(no == "2" || no == "8"){
						nums += 96;
					}else if(no == "3" || no == "7"){
						nums += 126;
					}else if(no == "4" || no == "6"){
						nums += 144;
					}else if(no == "5"){
						nums += 150;
					}
				}
				break;
			case "QianSanZuXuanHeZhi":
			case "HouSanZuXuanHeZhi":
			case "SanXingZuXuanHeZhi":
				for(var i = 0;i < balls["divXuan1to26"].length;i++)
				{
					var no = balls["divXuan1to26"][i];
					if(no == "1" || no == "26"){
						nums += 1;
					}else if(no == "2" || no == "3" || no == "24" || no == "25"){
						nums += 2;
					}else if(no == "4" || no == "23"){
						nums += 4;
					}else if(no == "5" || no == "22"){
						nums += 5;
					}else if(no == "6" || no == "21"){
						nums += 6;
					}else if(no == "7" || no == "20"){
						nums += 8;
					}else if(no == "8" || no == "19"){
						nums += 10;
					}else if(no == "9" || no == "18"){
						nums += 11;
					}else if(no == "10" || no == "17"){
						nums += 13;
					}else if(no == "11" || no == "12" || no == "15" || no == "16"){
						nums += 14;
					}else if(no == "13" || no == "14"){
						nums += 15;
					}
				}
				break;
			case "QianSanZuSan":
			case "HouSanZuSan":
			case "SanXingZuSan":
				nums = 2 * this.Combine(balls["divXuan"].length, 2);
				break;
			case "QianSanZuLiu":
			case "HouSanZuLiu":
			case "SanXingZuLiu":
				nums = this.Combine(balls["divXuan"].length, 3);
				break;
			case "QianSanZuXuanBaoDan":
			case "HouSanZuXuanBaoDan":
			case "SanXingZuXuanBaoDan":
				if(balls["divXuan"].length > 0){
					nums = 54;
				}
				break;
			case "QianErZhiXuanHeZhi":
			case "HouErZhiXuanHeZhi":
			case "P5HouErZhiXuanHeZhi":
			case "P3HouErZhiXuanHeZhi":
				for(var i = 0;i < balls["divXuan0to18"].length;i++)
				{
					var no = balls["divXuan0to18"][i];
					if(no == "0" || no == "18"){
						nums += 1;
					}else if(no == "1" || no == "17"){
						nums += 2;
					}else if(no == "2" || no == "16"){
						nums += 3;
					}else if(no == "3" || no == "15"){
						nums += 4;
					}else if(no == "4" || no == "14"){
						nums += 5;
					}else if(no == "5" || no == "13"){
						nums += 6;
					}else if(no == "6" || no == "12"){
						nums += 7;
					}else if(no == "7" || no == "11"){
						nums += 8;
					}else if(no == "8" || no == "10"){
						nums += 9;
					}else if(no == "9"){
						nums += 10;
					}
				}
				break;
			case "QianErZhiXuanKuaDu":
			case "HouErZhiXuanKuaDu":
			case "P5HouErZhiXuanKuaDu":
			case "P3HouErZhiXuanKuaDu":
				for(var i = 0;i < balls["divXuan"].length;i++)
				{
					var no = balls["divXuan"][i];
					if(no == "0"){
						nums += 10;
					}else if(no == "1"){
						nums += 18;
					}else if(no == "2"){
						nums += 16;
					}else if(no == "3"){
						nums += 14;
					}else if(no == "4"){
						nums += 12;
					}else if(no == "5"){
						nums += 10;
					}else if(no == "6"){
						nums += 8;
					}else if(no == "7"){
						nums += 6;
					}else if(no == "8"){
						nums += 4;
					}else if(no == "9"){
						nums += 2;
					}
				}
				break;
			case "QianErZuXuanHeZhi":
			case "HouErZuXuanHeZhi":
			case "P5HouErZuXuanHeZhi":
			case "P3HouErZuXuanHeZhi":
				for(var i = 0;i < balls["divXuan1to17"].length;i++)
				{
					var no = balls["divXuan1to17"][i];
					if(no == "1" || no == "2" || no == "16" || no == "17"){
						nums += 1;
					}else if(no == "3" || no == "4" || no == "14" || no == "15"){
						nums += 2;
					}else if(no == "5" || no == "6" || no == "12" || no == "13"){
						nums += 3;
					}else if(no == "7" || no == "8" || no == "10" || no == "11"){
						nums += 4;
					}else if(no == "9"){
						nums += 5;
					}
				}
				break;
			case "QianErZuXuanBaoDan":
			case "HouErZuXuanBaoDan":
			case "P5HouErZuXuanBaoDan":
			case "P3HouErZuXuanBaoDan":
				if(balls["divXuan"].length > 0){
					nums = 9;
				}
				break;
			case "YiXingDingWeiDan":
			case "DingWeiDan":
			case "RenXuanYiZhongYi":
				for(var key in balls) {
					nums += balls[key].length;
				}
				break;
			case "RenXuanErZhongEr":
				nums = this.Combine(balls["div11Xuan5"].length, 2);
				break;
			case "QianSanZhiXuan11Xuan5":
				var b1 = balls["divDiYiWei"];
				var b2 = balls["divDiErWei"];
				var b3 = balls["divDiSanWei"];
				var a = b1.length;
				var b = b2.length;
				var c = b3.length;
				
				var iSame12 = this.Compare2(b1, b2, b3);
				var iSame13 = this.Compare2(b1, b3, b2);
				var iSame23 = this.Compare2(b2, b3, b1);
				var iSame123 = this.Compare(b1, b2, b3);
				nums = a * b * c-(iSame12 * c + iSame13 * b + iSame23 * a) - iSame123 * ((a-1)+(b-1)+(c-1)+1);
				break;
			case "QianSanZuXuan11Xuan5":
			case "RenXuanSanZhongSan":
				nums = this.Combine(balls["div11Xuan5"].length, 3);
				break;
			case "RenXuanSiZhongSi":
				nums = this.Combine(balls["div11Xuan5"].length, 4);
				break;
			case "RenXuanWuZhongWu":
				nums = this.Combine(balls["div11Xuan5"].length, 5);
				break;
			case "RenXuanWuZhongWuDanTuo":
				var iDan = balls["div11Xuan5DanMa"].length;
				var iTuo = balls["div11Xuan5TuoMa"].length;
				nums = this.Combine(iTuo, 5 - iDan);
				break;
			case "SSQFuShi":
				var iRed = balls["divRedBall"].length;
				var iBlue = balls["divBlueBall"].length;
				nums = this.Combine(iRed, 6) * this.Combine(iBlue, 1);
				break;
			case "SSQDanTuo":
				var iRedDan = balls["divRedBallDanMa"].length;
				var iRedTuo = balls["divRedBallTuoMa"].length;
				var iBlue = balls["divBlueBall"].length;
				nums = this.Combine(iRedTuo, 6 - iRedDan) * this.Combine(iBlue, 1);
				break;
			case "JSK3SanTongHaoTongXuan":
				if(balls["divSanTongHaoTongXuan"].length > 0){
					nums = 1;
				}
				break;
			case "JSK3SanBuTongHaoBiaoZhun":
				nums = this.Combine(balls["divXuan1to6"].length, 3);
				break;
			case "JSK3SanBuTongHaoDanTuo":
				var iDan = balls["divXuan1to6DanMa"].length;
				var iTuo = balls["divXuan1to6TuoMa"].length;
				nums = this.Combine(iTuo, 3 - iDan);
				break;
			case "JSK3ErTongHaoDanXuan":
				var iErTongHao = balls["divErTongHao2"].length;
				var iXuan1to6 = balls["divXuan1to6"].length;
				nums = iErTongHao * iXuan1to6;
				break;
			case "JSK3ErBuTongHaoBiaoZhun":
				nums = this.Combine(balls["divXuan1to6"].length, 2);
				break;
			case "JSK3ErBuTongHaoDanTuo":
				var iDan = balls["divXuan1to6DanMa"].length;
				var iTuo = balls["divXuan1to6TuoMa"].length;
				nums = iDan * iTuo;
				break;
			default:
				var tmp = 1;
				for(var key in balls) {
					tmp *= balls[key].length;
				}
				nums = tmp;
		}
		
		$("#txtNum").html(nums);
		/*
		if($("#selLotteryId").val() == "CQSSC")
		{
			if($("#selMethodId").val().split("_")[1] == "WuXingZhiXuan")
			{
				//alert(balls["divWan"].length);
				var nums = balls["divWan"].length * balls["divQian"].length * balls["divBai"].length * balls["divShi"].length * balls["divGe"].length;
				
				
			}
		}
		*/
		var money = nums * 1 * mode * 1 * times;
		var div = Math.pow(10,2);
       		money = Math.round(money * div) / div;
		$("#" + this.txtMoneyId).html(money);
		this.CreateBetData();
	},
	CreateBetData: function(){
		var data = null;
		var traceNum = $("#" + this.selTraceId).val();
		var traceTimes = $("#" + this.txtTraceTimesId).val();
		if(traceNum == null || traceNum == 1){
			data = this.BET_DATA["BET_4"];
		}else{
			data = this.BET_DATA["TRACE_4"];
		}
		
		
		if(this.PLATFORM == this.PLATFORMS["PLATFORM_4"]){
			var aryBet = [];
			var total = 0;
			for(var i = 0;i < this.BETS.length;i++)
			{
				var bet = {};
				bet["methodid"] = this.BETS[i]["methodid"];
				bet["mode"] = this.BETS[i]["mode"];
				bet["money"] = this.BETS[i]["money"];
				bet["nums"] = this.BETS[i]["nums"];
				bet["times"] = this.BETS[i]["times"];
				bet["codes"] = this.CreateCodes(this.BETS[i]);
				total += this.BETS[i]["money"] * 1;
				aryBet.push(bet);
			}
			
			data = data.replace("#TOKEN#", $("#" + this.txtTokenId).val())
					.replace("#CHAN_ID#", this.LOTTERYS[$("#" +this.selLotteryId).val()]["chan_id"])
					.replace("#LOTTERY_ID#", this.LOTTERYS[$("#" +this.selLotteryId).val()]["lottery_id"])
					.replace("#ISSUE#", $("#" + this.selIssuesId).val())
					.replace("#LIST#", JSON.stringify(aryBet));
					
			
			if(traceNum > 1)
			{
				var times = new Array(traceNum);
				for(var i = 0;i < traceNum;i++)
				{
					times[i] = traceTimes;
				}
				data = data.replace("#TRACE_TIMES#", times.join(","));
				
				var traces = new Array(traceNum);
				var start = false;
				var iCount = 0;
				$("#" + this.selIssuesId + " option").each(function(){
						if(iCount == traceNum)
						{
							return false;
						}
						if($(this).is(':selected'))
						{
							start = true;
						}
						if(start)
						{
							traces[iCount] = $(this).val();
							iCount++;
						}
					});
				data = data.replace("#TRACE_ISSUES#", traces.join(","));
				var money = 0;
				for(var i = 0;i < this.BETS.length;i++)
				{
					var unit = this.GetModeUnit(this.BETS[i]["mode"]);
					money += unit * this.BETS[i]["nums"] * traceTimes;
				}
				money *= traceNum;
				var div = Math.pow(10,2);
		       		money = Math.round(money * div) / div;
				data = data.replace("#MONEY#", money);
			}else{
				data = data.replace("#MONEY#", total);
			}	
		}else if(this.PLATFORM == this.PLATFORMS["PLATFORM_BM"]){
			var aryBet = [];
			var total = 0;
			for(var i = 0;i < this.BETS.length;i++)
			{
				var bet = {};
				bet["wayId"] = this.BETS[i]["methodid"];
				bet["ball"] = this.CreateCodes(this.BETS[i]);
				bet["viewBalls"] = bet["ball"];
				bet["num"] = this.BETS[i]["nums"];
				bet["type"] = this.BETS[i]["type"];
				bet["moneyunit"] = this.BETS[i]["mode"];
				bet["multiple"] = this.BETS[i]["times"];
				total += this.BETS[i]["money"] * 1;
				aryBet.push(bet);
			}
			data = data.replace("#TOKEN#", $("#" + this.txtTokenId).val())
					.replace("#CHAN_ID#", this.LOTTERYS[$("#" +this.selLotteryId).val()]["chan_id"])
					.replace("#LOTTERY_ID#", this.LOTTERYS[$("#" +this.selLotteryId).val()]["lottery_id"])
					.replace("#LIST#", JSON.stringify(aryBet));
					
			if(traceNum > 1)
			{
				// traceTimes
				var orders = {};
				var traces = new Array(traceNum);
				var start = false;
				var iCount = 0;
				$("#" + this.selIssuesId + " option").each(function(){
						if(iCount == traceNum)
						{
							return false;
						}
						if($(this).is(':selected'))
						{
							start = true;
						}
						if(start)
						{
							traces[iCount] = $(this).val();
							orders[$(this).val()] = traceTimes;
							iCount++;
						}
					});
				data = data.replace("#ORDERS#", JSON.stringify(orders));
				var money = 0;
				for(var i = 0;i < this.BETS.length;i++)
				{
					var unit = this.GetModeUnit(this.BETS[i]["mode"]);
					money += unit * this.BETS[i]["nums"] * traceTimes;
				}
				money *= traceNum;
				var div = Math.pow(10,2);
		       		money = Math.round(money * div) / div;
				data = data.replace("#MONEY#", money);
			}else{
				var orders = {};
				orders[$("#" + this.selIssuesId + " option").val()] = traceTimes;
				data = data.replace("#ORDERS#", JSON.stringify(orders));
				data = data.replace("#MONEY#", total);
			}
		}
		$("#" + this.divBetDataId).html(data);
	},
	QuickSelect: function(str){
		var id = str.split("_")[0];
		var way = str.split("_")[1];
		
		$("div[id^=" + id + "_ball]").each(function(){
				$(this).attr("class", "ball");
					});
		
		if(way == "all"){
			$("div[id^=" + id + "_ball]").each(function(){
					$(this).attr("class", "ball_enable");
						});
		}else if(way == "big"){
			$("div[id^=" + id + "_ball]").each(function(){
							var no = $(this).attr("id").split("_")[2];
							var digits = $(this).attr("id").split("_")[3];
							var max = digits==1?5:4;
							if(no > max)
							{
								$(this).attr("class", "ball_enable");
							}
						});
		}else if(way == "small"){
			$("div[id^=" + id + "_ball]").each(function(){
							var no = $(this).attr("id").split("_")[2];
							var digits = $(this).attr("id").split("_")[3];
							var max = digits==1?5:4;
							if(no <= max)
							{
								$(this).attr("class", "ball_enable");
							}
						});
		}else if(way == "odd"){
			$("div[id^=" + id + "_ball]").each(function(){
							var no = $(this).attr("id").split("_")[2];
							if(no % 2 == 1)
							{
								$(this).attr("class", "ball_enable");
							}
						});
		}else if(way == "even"){
			$("div[id^=" + id + "_ball]").each(function(){
							var no = $(this).attr("id").split("_")[2];
							if(no % 2 == 0)
							{
								$(this).attr("class", "ball_enable");
							}
						});
		}else if(way == "clear"){
			
		}
	},
	ClearBets: function(){
		this.BETS = [];
		$("#" + this.divListId).html("");
		this.CreateBetData();
	},
	CheckSame: function(ary1, ary2){
		var iSame = 0;
		for(var i = 0;i < ary1.length;i++)
		{
			for(var j = 0;j < ary2.length;j++)
			{
				if(ary1[i] == ary2[j])
				{
					iSame++;
					continue;	
				}
			}
		}
		return iSame;
	},
	Combine: function(n, k){
		if(n >= k)
		{
			return this.Factorial(n) / (this.Factorial(k) * this.Factorial(n-k));
		}
		else
		{
			return 0;
		}
	},
	Factorial: function(n){
		var iValue = 1;
		for(var i = 1;i <= n;i++)
		{
			iValue *= i;
		}
		return iValue;
	},
	Compare: function(a, b, c){
		/*
		a = b = c
		*/
		var iSame = 0;
		for(var i = 0;i < a.length;i++)
		{
			for(var j = 0;j < b.length;j++)
			{
				if(a[i] == b[j])
				{
					for(var k = 0;k < c.length;k++)
					{
						if(b[j] == c[k])
						{
							iSame++;
							break;
						}
					}
				}
			}		
		}
		return iSame;
	},
	Compare2: function(a, b, c){
		/*
		a = b != c
		*/
		var iSame = 0;
		var bln;
		for(var i = 0;i < a.length;i++)
		{
			for(var j = 0;j < b.length;j++)
			{
				if(a[i] == b[j])
				{
					bln = true;
					for(var k = 0;k < c.length;k++)
					{
						if(b[j] == c[k])
						{
							bln = false;
							break;
						}
					}
					if(bln)
					{
						iSame++;	
					}
				}
			}		
		}
		return iSame;
	}
};
