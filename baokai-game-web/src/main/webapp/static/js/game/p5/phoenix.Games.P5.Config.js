

(function(host, name, Event, undefined){
	var defConfig = {
		//当前彩种名称
		gameType : 'P5',
		gameTypeCn : '排列5'
	},
		instance;
		
		//p3三星
	var p3sanxing,
		//p3前二
		p3qianer,
		//p3后二
		p3houer,
		//p5后二
		p5houer,
		//p5一星
		p5yixing;
	
	
	//三星玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//直选
			//复式 
		var p3fushi = {title:'复式',name:'p3fushi', parent:'zhixuan', mode:'p3sanxing', headline:['p3三星','复式']},
			//单式
			p3danshi = {title:'单式',name:'p3danshi', parent:'zhixuan', mode:'p3sanxing', headline:['p3三星','单式']},
			//和值
			p3hezhi = {title:'和值',name:'p3hezhi', parent:'zhixuan', mode:'p3sanxing', headline:['p3三星','直选和值']},
			//跨度
			p3kuadu = {title:'跨度',name:'p3kuadu', parent:'zhixuan', mode:'p3sanxing', headline:['p3三星','直选跨度']},
		//组选
			//组三
			p3zusan = {title:'组三',name:'p3zusan', parent:'zuxuan', mode:'p3sanxing', headline:['p3三星','组三']},
			p3zuliu = {title:'组六',name:'p3zuliu', parent:'zuxuan', mode:'p3sanxing', headline:['p3三星','组六']},
			p3zusandanshi = {title:'组三单式',name:'p3zusandanshi', parent:'zuxuan', mode:'p3sanxing', headline:['p3三星','组三单式']},
			p3zuliudanshi = {title:'组六单式',name:'p3zuliudanshi', parent:'zuxuan', mode:'p3sanxing', headline:['p3三星','组六单式']},
			p3hunhezuxuan = {title:'混合组选',name:'p3hunhezuxuan', parent:'zuxuan', mode:'p3sanxing', headline:['p3三星','混合组选']},
			p3zuxuanhezhi = {title:'组选和值',name:'p3zuxuanhezhi', parent:'zuxuan', mode:'p3sanxing', headline:['p3三星','组选和值']},
			p3zuxuanbaodan = {title:'组选包胆',name:'p3zuxuanbaodan', parent:'zuxuan', mode:'p3sanxing', headline:['p3三星','组选包胆']},
		//不定位
			//一码不定位
			yimabudingwei = {title:'一码不定位',name:'yimabudingwei', parent:'budingwei', mode:'p3sanxing', headline:['p3三星','一码不定位']},
			//二码不定位
			ermabudingwei = {title:'二码不定位',name:'ermabudingwei', parent:'budingwei', mode:'p3sanxing', headline:['p3三星','二码不定位']};
		
		//玩法组===========================================
		var zhixuan = {title:'直&nbsp;&nbsp;&nbsp;选',name:'zhixuan', parent:'p3sanxing', childs:[p3fushi, p3danshi, p3hezhi, p3kuadu]},
			zuxuan = {title:'组&nbsp;&nbsp;&nbsp;选',name:'zuxuan', parent:'p3sanxing', childs:[p3zusan, p3zuliu, p3zusandanshi, p3zuliudanshi, p3hunhezuxuan, p3zuxuanhezhi, p3zuxuanbaodan]},
			budingwei = {title:'不定位',name:'budingwei', parent:'p3sanxing', childs:[yimabudingwei, ermabudingwei]};
		
		//玩法分类
		//三星
		p3sanxing = {title:'三星', name:'p3sanxing', childs:[zhixuan, zuxuan, budingwei]};
	})();

	//p3后二玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//直选
			//p3后二复式 
		var zhixuanp3houerfushi = {title:'复式',name:'zhixuanp3houerfushi', parent:'zhixuan', mode:'p3houer', headline:['p3后二','复式']},
			//p3后二单式
			zhixuanp3houerdanshi = {title:'单式',name:'zhixuanp3houerdanshi', parent:'zhixuan', mode:'p3houer', headline:['p3后二','单式']},
			//p3后二和值
			zhixuanp3houerhezhi = {title:'和值',name:'zhixuanp3houerhezhi', parent:'zhixuan', mode:'p3houer', headline:['p3后二','直选和值']},
			//p3后二跨度
			zhixuanp3houerkuadu = {title:'跨度',name:'zhixuanp3houerkuadu', parent:'zhixuan', mode:'p3houer', headline:['p3后二','直选跨度']},
		//组选
			//p3后二复式 
			zuxuanp3houerfushi = {title:'复式',name:'zuxuanp3houerfushi', parent:'zuxuan', mode:'p3houer', headline:['p3后二','组选']},
			//p3后二单式
			zuxuanp3houerdanshi = {title:'单式',name:'zuxuanp3houerdanshi', parent:'zuxuan', mode:'p3houer', headline:['p3后二','组选单式']},
			//p3后二和值
			zuxuanp3houerhezhi = {title:'和值',name:'zuxuanp3houerhezhi', parent:'zuxuan', mode:'p3houer', headline:['p3后二','组选和值']},
			//p3后二包胆
			zuxuanp3houerbaodan = {title:'包胆',name:'zuxuanp3houerbaodan', parent:'zuxuan', mode:'p3houer', headline:['p3后二','组选包胆']};
		
		//玩法组===========================================
		var zhixuan = {title:'直&nbsp;&nbsp;&nbsp;选',name:'zhixuan', parent:'p3houer', childs:[zhixuanp3houerfushi, zhixuanp3houerdanshi, zhixuanp3houerhezhi, zhixuanp3houerkuadu]},
			zuxuan = {title:'组&nbsp;&nbsp;&nbsp;选',name:'zuxuan', parent:'p3houer', childs:[zuxuanp3houerfushi, zuxuanp3houerdanshi, zuxuanp3houerhezhi, zuxuanp3houerbaodan]};
		
		//玩法分类
		//p3后二
		p3houer = {title:'后二', name:'p3houer', childs:[zhixuan, zuxuan]};
	})();

	//p3前二玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//直选
			//p3前二复式 
		var zhixuanp3qianerfushi = {title:'复式',name:'zhixuanp3qianerfushi', parent:'zhixuan', mode:'p3qianer', headline:['p3前二','复式']},
			//p3前二单式
			zhixuanp3qianerdanshi = {title:'单式',name:'zhixuanp3qianerdanshi', parent:'zhixuan', mode:'p3qianer', headline:['p3前二','单式']},
			//p3前二和值
			zhixuanp3qianerhezhi = {title:'和值',name:'zhixuanp3qianerhezhi', parent:'zhixuan', mode:'p3qianer', headline:['p3前二','直选和值']},
			//p3前二跨度
			zhixuanp3qianerkuadu = {title:'跨度',name:'zhixuanp3qianerkuadu', parent:'zhixuan', mode:'p3qianer', headline:['p3前二','直选跨度']},
		//组选
			//p3前二复式 
			zuxuanp3qianerfushi = {title:'复式',name:'zuxuanp3qianerfushi', parent:'zuxuan', mode:'p3qianer', headline:['p3前二','组选']},
			//p3前二单式
			zuxuanp3qianerdanshi = {title:'单式',name:'zuxuanp3qianerdanshi', parent:'zuxuan', mode:'p3qianer', headline:['p3前二','组选单式']},
			//p3前二和值
			zuxuanp3qianerhezhi = {title:'和值',name:'zuxuanp3qianerhezhi', parent:'zuxuan', mode:'p3qianer', headline:['p3前二','组选和值']},
			//p3前二包胆
			zuxuanp3qianerbaodan = {title:'包胆',name:'zuxuanp3qianerbaodan', parent:'zuxuan', mode:'p3qianer', headline:['p3前二','组选包胆']};
		
		//玩法组===========================================
		var zhixuan = {title:'直&nbsp;&nbsp;&nbsp;选',name:'zhixuan', parent:'p3qianer', childs:[zhixuanp3qianerfushi, zhixuanp3qianerdanshi, zhixuanp3qianerhezhi, zhixuanp3qianerkuadu]},
			zuxuan = {title:'组&nbsp;&nbsp;&nbsp;选',name:'zuxuan', parent:'p3qianer', childs:[zuxuanp3qianerfushi, zuxuanp3qianerdanshi, zuxuanp3qianerhezhi, zuxuanp3qianerbaodan]};
		
		//玩法分类
		//p3前二
		p3qianer = {title:'前二', name:'p3qianer', childs:[zhixuan, zuxuan]};
	})();

	//p5后二玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//直选
			//p3后二复式 
		var zhixuanp5houerfushi = {title:'复式',name:'zhixuanp5houerfushi', parent:'zhixuan', mode:'p5houer', headline:['p5后二','复式']},
			//p3后二单式
			zhixuanp5houerdanshi = {title:'单式',name:'zhixuanp5houerdanshi', parent:'zhixuan', mode:'p5houer', headline:['p5后二','单式']},
			//p3后二和值
			zhixuanp5houerhezhi = {title:'和值',name:'zhixuanp5houerhezhi', parent:'zhixuan', mode:'p5houer', headline:['p5后二','直选和值']},
			//p3后二跨度
			zhixuanp5houerkuadu = {title:'跨度',name:'zhixuanp5houerkuadu', parent:'zhixuan', mode:'p5houer', headline:['p5后二','直选跨度']},
		//组选
			//p3后二复式 
			zuxuanp5houerfushi = {title:'复式',name:'zuxuanp5houerfushi', parent:'zuxuan', mode:'p5houer', headline:['p5后二','组选']},
			//p3后二单式
			zuxuanp5houerdanshi = {title:'单式',name:'zuxuanp5houerdanshi', parent:'zuxuan', mode:'p5houer', headline:['p5后二','组选单式']},
			//p3后二和值
			zuxuanp5houerhezhi = {title:'和值',name:'zuxuanp5houerhezhi', parent:'zuxuan', mode:'p5houer', headline:['p5后二','组选和值']},
			//p3后二包胆
			zuxuanp5houerbaodan = {title:'包胆',name:'zuxuanp5houerbaodan', parent:'zuxuan', mode:'p5houer', headline:['p5后二','组选包胆']};
		
		//玩法组===========================================
		var zhixuan = {title:'直&nbsp;&nbsp;&nbsp;选',name:'zhixuan', parent:'p5houer', childs:[zhixuanp5houerfushi, zhixuanp5houerdanshi, zhixuanp5houerhezhi, zhixuanp5houerkuadu]},
			zuxuan = {title:'组&nbsp;&nbsp;&nbsp;选',name:'zuxuan', parent:'p5houer', childs:[zuxuanp5houerfushi, zuxuanp5houerdanshi, zuxuanp5houerhezhi, zuxuanp5houerbaodan]};
		
		//玩法分类
		//p5后二
		p5houer = {title:'后二', name:'p5houer', childs:[zhixuan, zuxuan]};
	})();

	//p5一星玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//定位胆
			//万位 
		var fushi = {title:'复式',name:'fushi', parent:'dingweidan', mode:'p5yixing', headline:['p5一星','复式']};
		
		//玩法组===========================================
		var dingweidan = {title:'定位胆',name:'dingweidan', parent:'p5yixing', childs:[fushi]};
		
		//玩法分类
		//p5一星
		p5yixing = {title:'一星', name:'p5yixing', childs:[dingweidan]};
	})();

	// //靓号玩法配置
	// (function(){
	// 	//title 标题
	// 	//name 英文名称
	// 	//isColse 是否关闭
			  
	// 	//具体玩法类型===========================================
	// 	//特殊直选
	// 		//靓号
	// 	var xuanlianghao = {title:'靓号',name:'xuanlianghao', parent:'teshuzhixuan', mode:'lianghao'};
		
	// 	//玩法组===========================================
	// 	var teshuzhixuan = {title:'特殊直选',name:'teshuzhixuan', parent:'lianghao', childs:[xuanlianghao]};
		
	// 	//玩法分类
	// 	//靓号
	// 	lianghao = {title:'靓号', name:'lianghao', childs:[teshuzhixuan]};
	// })();

	var pros = {
		init:function(){
			var me = this;
			me.types = [p3sanxing, p3qianer, p3houer, p5houer, p5yixing];
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
							if(tempArr[i2]['name'] == nameArr[1]){
								result.push(tempArr[i2]['title'].replace(/&nbsp;/g,''));
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
	
	host.Games.P5[name] = Main;
	
})(phoenix, "Config", phoenix.Event);









