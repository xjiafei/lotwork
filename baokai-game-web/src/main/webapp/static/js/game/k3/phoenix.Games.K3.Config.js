

(function(host, name, Event, undefined){
	var defConfig = {
		//当前彩种名称
		gameType : 'ssc',
		gameTypeCn : '时时彩'
	},
		instance;
		
		//五星
	var wuxing,
		//四星
		sixing,
		//后三
		housan,
		//前三
		qiansan,
		//后二
		houer,
		//前二
		qianer,
		//一星
		yixing;
	
	
	//五星玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//直选
			//复式 
		var fushi = {title:'复式',name:'fushi', parent:'zhixuan', mode:'wuxing'},
			//单式
			danshi = {title:'单式',name:'danshi', parent:'zhixuan', mode:'wuxing'},
		//组选
			//组选120(杂牌)
			zuxuan120 = {title:'组120',name:'zuxuan120', parent:'zuxuan', mode:'wuxing'},
			//组选60(对子)
			zuxuan60 = {title:'组60',name:'zuxuan60', parent:'zuxuan', mode:'wuxing'},
			//组选30(两对)
			zuxuan30 = {title:'组30',name:'zuxuan30', parent:'zuxuan', mode:'wuxing'},
			//组选20(三条)
			zuxuan20 = {title:'组20',name:'zuxuan20', parent:'zuxuan', mode:'wuxing'},
			//组选10(葫芦)
			zuxuan10 = {title:'组10',name:'zuxuan10', parent:'zuxuan', mode:'wuxing'},
			//组选5(四条)
			zuxuan5 = {title:'组5',name:'zuxuan5', parent:'zuxuan', mode:'wuxing'},
		//不定位
			//一码不定位
			yimabudingwei = {title:'一码不定位',name:'yimabudingwei', parent:'budingwei', mode:'wuxing'},
			//二码不定位
			ermabudingwei = {title:'二码不定位',name:'ermabudingwei', parent:'budingwei', mode:'wuxing'},
			//三码不定位
			sanmabudingwei = {title:'三码不定位',name:'sanmabudingwei', parent:'budingwei', mode:'wuxing'},
		//趣味
			//一帆风顺
			yifanfengshun = {title:'一帆风顺',name:'yifanfengshun', parent:'quwei', mode:'wuxing'},
			//好事成双
			haoshichengshuang = {title:'好事成双',name:'haoshichengshuang', parent:'quwei', mode:'wuxing'},
			//三星报喜
			sanxingbaoxi = {title:'三星报喜',name:'sanxingbaoxi', parent:'quwei', mode:'wuxing'},
			//三星报喜
			sijifacai = {title:'四季发财',name:'sijifacai', parent:'quwei', mode:'wuxing'};
		
		
		
		//玩法组===========================================
		//直选
		var zhixuan = {title:'直&nbsp;&nbsp;&nbsp;选',name:'zhixuan', parent:'wuxing', childs:[fushi, danshi]},
			zuxuan = {title:'组&nbsp;&nbsp;&nbsp;选',name:'zuxuan', parent:'wuxing', childs:[zuxuan120, zuxuan60, zuxuan30, zuxuan20, zuxuan10, zuxuan5]},
			budingwei = {title:'不定位',name:'budingwei', parent:'wuxing', childs:[yimabudingwei, ermabudingwei, sanmabudingwei]},
			quwei = {title:'趣&nbsp;&nbsp;&nbsp;味',name:'quwei', parent:'wuxing', childs:[yifanfengshun, haoshichengshuang, sanxingbaoxi, sijifacai]};
		
		
		//玩法分类
		//五星
		wuxing = {title:'五星', name:'wuxing', childs:[zhixuan, zuxuan, budingwei, quwei]};
	})();

	//四星玩法配置
	(function(){
		/**
		title 标题
		name 英文名称
		isColse 是否关闭
		**/
			  
		//具体玩法类型===========================================
		//直选
			//复式 
		var fushi = {title:'复式',name:'fushi', parent:'zhixuan', mode:'sixing'},
			//单式
			danshi = {title:'单式',name:'danshi', parent:'zhixuan', mode:'sixing'},
		//组选
			//组选120(杂牌)
			zuxuan24 = {title:'组选24',name:'zuxuan24', parent:'zuxuan', mode:'sixing'},
			//组选60(对子)
			zuxuan12 = {title:'组选12',name:'zuxuan12', parent:'zuxuan', mode:'sixing'},
			//组选30(两对)
			zuxuan6 = {title:'组选6',name:'zuxuan6', parent:'zuxuan', mode:'sixing'},
			//组选20(三条)
			zuxuan4 = {title:'组选4',name:'zuxuan4', parent:'zuxuan', mode:'sixing'},
		//不定位
			//一码不定位
			yimabudingwei = {title:'一码不定位',name:'yimabudingwei', parent:'budingwei', mode:'sixing'},
			//二码不定位
			ermabudingwei = {title:'二码不定位',name:'ermabudingwei', parent:'budingwei', mode:'sixing'};
		
		
		
		//玩法组===========================================
		//直选
		var zhixuan = {title:'直&nbsp;&nbsp;&nbsp;选', name:'zhixuan', parent:'sixing', childs:[fushi, danshi]},
			zuxuan = {title:'组&nbsp;&nbsp;&nbsp;选', name:'zuxuan', parent:'sixing', childs:[zuxuan24, zuxuan12, zuxuan6, zuxuan4]},
			budingwei = {title:'不定位', name:'budingwei', parent:'sixing', childs:[yimabudingwei, ermabudingwei]};
		
		
		//玩法分类
		//五星
		sixing = {title:'四星',name:'sixing',childs:[zhixuan, zuxuan, budingwei]};
	})();

	//后三玩法配置
	(function(){
		/**
		title 标题
		name 英文名称
		isColse 是否关闭
		**/
			  
		//具体玩法类型===========================================
		//直选
			//复式 
		var fushi = {title:'复式',name:'fushi', parent:'zhixuan', mode:'housan'},
			//单式
			danshi = {title:'单式',name:'danshi', parent:'zhixuan', mode:'housan'},
			//和值
			hezhi = {title: '和值', name: 'hezhi', parent:'zhixuan', mode:'housan', headline:['后二','直选和值']},
			//跨度
			kuadu = {title: '跨度', name: 'kuadu', parent:'zhixuan', mode:'housan', headline:['后二','直选跨度']},
		//组选
			//组三
			zusan = {title: '组三', name:'zusan', parent:'zuxuan', mode:'housan'},
			//组六
			zuliu = {title: '组六', name:'zuliu', parent:'zuxuan', mode:'housan'},
			//组三单式
			zusandanshi = {title: '组三单式',name:'zusandanshi', parent:'zuxuan', mode:'housan'},
			//组六单式
			zuliudanshi = {title:'组六单式',name:'zuliudanshi', parent:'zuxuan', mode:'housan'},
			//混合组选
			hunse = {title:'混合组选',name:'hunhezuxuan', parent:'zuxuan', mode:'housan'},
			//组选和值
			zuxuanhezhi = {title:'组选和值',name:'zuxuanhezhi', parent:'zuxuan', mode:'housan'},
			//组选包胆
			zuxuanbaodan = {title:'组选包胆',name:'zuxuanbaodan', parent:'zuxuan', mode:'housan'},
		//不定位
			//一码不定位
			yimabudingwei = {title:'一码不定位',name:'yimabudingwei', parent:'budingwei', mode:'housan'},
			//二码不定位
			ermabudingwei = {title:'二码不定位',name:'ermabudingwei', parent:'budingwei', mode:'housan'};
		
		
		//玩法组===========================================
		//直选
		var zhixuan = {title:'直&nbsp;&nbsp;&nbsp;选',name:'zhixuan', parent:'housan',childs:[fushi, danshi, hezhi, kuadu]},
			zuxuan = {title:'组&nbsp;&nbsp;&nbsp;选',name:'zuxuan', parent:'housan',childs:[zusan, zuliu, zusandanshi, zuliudanshi, hunse, zuxuanhezhi, zuxuanbaodan]},
			budingwei = {title:'不定位',name:'budingwei', parent:'housan',childs:[yimabudingwei, ermabudingwei]};
		
		
		//玩法分类
		//五星
		housan = {title:'后三',name:'housan',childs:[zhixuan, zuxuan, budingwei]};
	})();

	//前三玩法配置
	(function(){
		/**
		title 标题
		name 英文名称
		isColse 是否关闭
		**/
			  
		//具体玩法类型===========================================
		//直选
			//复式 
		var fushi = {title:'复式',name:'fushi', parent:'zhixuan', mode:'qiansan',isClose:true},
			//单式
			danshi = {title:'单式',name:'danshi', parent:'zhixuan', mode:'qiansan'},
			//和值
			hezhi = {title: '和值', name: 'hezhi', parent:'zhixuan', mode:'qiansan', headline:['后二','直选和值']},
			//跨度
			kuadu = {title: '跨度', name: 'kuadu', parent:'zhixuan', mode:'qiansan', headline:['后二','直选和值']},
		//组选
			//组三
			zusan = {title: '组三', name:'zusan', parent:'zuxuan', mode:'qiansan'},
			//组六
			zuliu = {title: '组六', name:'zuliu', parent:'zuxuan', mode:'qiansan'},
			//组三单式
			zusandanshi = {title: '组三单式',name:'zusandanshi', parent:'zuxuan', mode:'qiansan'},
			//组六单式
			zuliudanshi = {title:'组六单式',name:'zuliudanshi', parent:'zuxuan', mode:'qiansan'},
			//混合组选
			hunse = {title:'混合组选',name:'hunhezuxuan', parent:'zuxuan', mode:'qiansan'},
			//组选和值
			zuxuanhezhi = {title:'组选和值',name:'zuxuanhezhi', parent:'zuxuan', mode:'qiansan'},
			//组选包胆
			baodan = {title:'组选包胆',name:'zuxuanbaodan', parent:'zuxuan', mode:'qiansan'},
		//不定位
			//一码不定位
			yimabudingwei = {title:'一码不定位',name:'yimabudingwei', parent:'budingwei', mode:'qiansan'},
			//二码不定位
			ermabudingwei = {title:'二码不定位',name:'ermabudingwei', parent:'budingwei', mode:'qiansan'};
		
		
		//玩法组===========================================
		//直选
		var zhixuan = {title:'直&nbsp;&nbsp;&nbsp;选',name:'zhixuan', parent:'qiansan',childs:[fushi, danshi, hezhi, kuadu]},
			zuxuan = {title:'组&nbsp;&nbsp;&nbsp;选',name:'zuxuan', parent:'qiansan',childs:[zusan, zuliu, zusandanshi, zuliudanshi, hunse, zuxuanhezhi, baodan]},
			budingwei = {title:'不定位',name:'budingwei', parent:'qiansan',childs:[yimabudingwei, ermabudingwei]};
		
		
		//玩法分类
		//五星
		qiansan = {title: '前三', name: 'qiansan', childs:[zhixuan, zuxuan, budingwei]};
	})();

	//后二玩法配置
	(function(){
		/**
		title 标题
		name 英文名称
		isColse 是否关闭
		**/
			  
		//具体玩法类型===========================================
		//直选
			//复式 
		var fushi = {title:'复式',name:'fushi', parent:'zhixuan', mode:'houer'},
			//单式
			danshi = {title:'单式',name:'danshi', parent:'zhixuan', mode:'houer'},
			//和值
			hezhi = {title: '和值', name: 'hezhi', parent:'zhixuan', mode:'houer', headline:['后二','直选和值']},
			//跨度
			kuadu = {title: '跨度', name: 'kuadu', parent:'zhixuan', mode:'houer', headline:['后二','直选跨度']},
		//组选
			//组三
			fushizu = {title: '复式', name:'fushi', parent:'zuxuan', mode:'houer', headline:['后二','组选']},
			//组六
			danshizu = {title: '单式', name:'danshi', parent:'zuxuan', mode:'houer', headline:['后二','组选单式']},
			//组三单式
			hezhizu = {title: '和值',name:'hezhi', parent:'zuxuan', mode:'houer', headline:['后二','组选和值']},
			//组六单式
			baodanzu = {title:'包胆',name:'baodan', parent:'zuxuan', mode:'houer', headline:['后二','组选包胆']};
		
		//玩法组===========================================
		//直选
		var zhixuan = {title:'直&nbsp;&nbsp;&nbsp;选', name:'zhixuan', parent:'houer', childs:[fushi, danshi, hezhi, kuadu]},
			zuxuan = {title:'组&nbsp;&nbsp;&nbsp;选', name:'zuxuan', parent:'houer', childs:[fushizu, danshizu, hezhizu, baodanzu]};
		
		//玩法分类
		//后二
		houer = {title: '后二',name: 'houer', childs:[zhixuan, zuxuan]};
	})();

	//前二玩法配置
	(function(){
		/**
		title 标题
		name 英文名称
		isColse 是否关闭
		**/
			  
		//具体玩法类型===========================================
		//直选
			//复式 
		var fushi = {title:'复式',name:'fushi', parent:'zhixuan', mode:'qianer',isClose:true},
			//单式
			danshi = {title:'单式',name:'danshi', parent:'zhixuan', mode:'qianer'},
			//和值
			hezhi = {title: '和值', name: 'hezhi', parent:'zhixuan', mode:'qianer', headline:['前二','直选和值']},
			//跨度
			kuadu = {title: '跨度', name: 'kuadu', parent:'zhixuan', mode:'qianer', headline:['前二','直选跨度']},
		//组选
			//组三
			fushizu = {title: '复式', name:'fushi', parent:'zuxuan', mode:'qianer', headline:['前二','组选']},
			//组六
			danshizu = {title: '单式', name:'danshi', parent:'zuxuan', mode:'qianer', headline:['前二','组选单式']},
			//组三单式
			hezhizu = {title: '和值',name:'hezhi', parent:'zuxuan', mode:'qianer', headline:['前二','组选和值']},
			//组六单式
			baodanzu = {title:'包胆',name:'baodan', parent:'zuxuan', mode:'qianer', headline:['前二','组选包胆']};
		
		
		
		//玩法组===========================================
		//直选
		var zhixuan = {title:'直&nbsp;&nbsp;&nbsp;选', name:'zhixuan', parent:'qianer', childs:[fushi, danshi, hezhi, kuadu]},
			zuxuan = {title:'组&nbsp;&nbsp;&nbsp;选', name:'zuxuan', parent:'qianer', childs:[fushizu, danshizu, hezhizu, baodanzu]};
		
		
		//玩法分类
		//前二
		qianer = {title:'前二', name:'qianer', childs:[zhixuan, zuxuan]};
		
	})();

	//一星玩法配置
	(function(){
		/**
		title 标题
		name 英文名称
		isColse 是否关闭
		**/
			  
		//具体玩法类型===========================================
		//不定位
		var fushi = {title:'复式', name:'fushi', parent:'dingweidan', mode:'yixing'};

		//玩法组===========================================
		//直选
		var dingweidan = {title:'定位胆', name:'dingweidan', parent:'yixing', childs:[fushi]};
		//玩法分类
		//一星
		yixing = {title:'一星', name:'yixing', childs: [dingweidan]};
	})();

	var pros = {
		init:function(){
			var me = this;
			me.types = [wuxing, sixing, housan, qiansan, houer, qianer, yixing];
			console.log(me.types);
		},
		//获取玩法类型
		getTypes:function(isFilterClose){
			return this.types;
		},
		getGameTypeCn:function(){
			return this.defConfig.gameTypeCn;
		},
		//name  wuxing.zhixuan.fushi
		getTitleByName:function(name){
			var me = this,
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
			for(;i < len;i++){
				if(types[i]['name'] == nameArr[0]){
					result.push(types[i]['title'].replace(/&nbsp;/g,''));
					if(nameLen > 1 && types[i]['childs'].length > 0){
						tempArr = types[i]['childs'];
						len2 = tempArr.length;
						//循环二级
						for(i2 = 0;i2 < len2;i2++){
							//console.log(tempArr[i2]['name']);
							if(tempArr[i2]['name'] == nameArr[1]){
								//result.push(tempArr[i2]['title'].replace(/&nbsp;/g,''));
								if(nameLen > 2 && tempArr[i2]['childs'].length > 0){
									tempArr = tempArr[i2]['childs'];
									len3 = tempArr.length;
									//循环三级
									for(i3 = 0;i3 < len3;i3++){
										if(tempArr[i3]['name'] == nameArr[2]){
											if(tempArr[i3]['headline']){
												return tempArr[i3]['headline'];
											}
											result.push(tempArr[i3]['title'].replace(/&nbsp;/g,''));
											return result;
										}
									}
								}else{
									return result;
								}
							}
						}
					}else{
						return result;
					}
				}
			}
			return '';
		}
		
	};
	
	var Main = host.Class(pros, Event);
	Main.defConfig = defConfig;
	Main.getInstance = function(cfg){
		return instance || (instance = new Main(cfg));
	};
	
	host.Games.SSC[name] = Main;
	
})(phoenix, "Config", phoenix.Event);









