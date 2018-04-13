

(function(host, name, Event, undefined){
	var defConfig = {
		//当前彩种名称
		gameType : 'FC3D',
		gameTypeCn : 'FC3D'
	},
		instance;
		
		//三星
	var sanxing,
		//前二
		qianer,
		//后二
		houer,
		//一星
		yixing;
	
	
	//三星玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//直选
			//复式 
		var fushi = {title:'复式',name:'fushi', parent:'zhixuan', mode:'sanxing'},
			//单式
			danshi = {title:'单式',name:'danshi', parent:'zhixuan', mode:'sanxing'},
			//和值
			hezhi = {title:'和值',name:'hezhi', parent:'zhixuan', mode:'sanxing'},
			//跨度
			kuadu = {title:'跨度',name:'kuadu', parent:'zhixuan', mode:'sanxing'},
		//组选
			//组三
			zusan = {title:'组三',name:'zusan', parent:'zuxuan', mode:'sanxing'},
			zuliu = {title:'组六',name:'zuliu', parent:'zuxuan', mode:'sanxing'},
			zusandanshi = {title:'组三单式',name:'zusandanshi', parent:'zuxuan', mode:'sanxing'},
			zuliudanshi = {title:'组六单式',name:'zuliudanshi', parent:'zuxuan', mode:'sanxing'},
			hunhezuxuan = {title:'混合组选',name:'hunhezuxuan', parent:'zuxuan', mode:'sanxing'},
			zuxuanhezhi = {title:'组选和值',name:'zuxuanhezhi', parent:'zuxuan', mode:'sanxing'},
			zuxuanbaodan = {title:'组选包胆',name:'zuxuanbaodan', parent:'zuxuan', mode:'sanxing'},
		//不定位
			//一码不定位
			yimabudingwei = {title:'一码不定位',name:'yimabudingwei', parent:'budingwei', mode:'sanxing'},
			//二码不定位
			ermabudingwei = {title:'二码不定位',name:'ermabudingwei', parent:'budingwei', mode:'sanxing'};
		
		//玩法组===========================================
		var zhixuan = {title:'直&nbsp;&nbsp;&nbsp;选',name:'zhixuan', parent:'sanxing', childs:[fushi, danshi, hezhi, kuadu]},
			zuxuan = {title:'组&nbsp;&nbsp;&nbsp;选',name:'zuxuan', parent:'sanxing', childs:[zusan, zuliu, zusandanshi, zuliudanshi, hunhezuxuan, zuxuanhezhi, zuxuanbaodan]},
			budingwei = {title:'不定位',name:'budingwei', parent:'sanxing', childs:[yimabudingwei, ermabudingwei]};
		
		//玩法分类
		//三星
		sanxing = {title:'三星', name:'sanxing', childs:[zhixuan, zuxuan, budingwei]};
	})();

	//p3后二玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//直选
			//后二复式 
		var zhixuanfushi = {title:'复式',name:'zhixuanfushi', parent:'zhixuan', mode:'houer'},
			//后二单式
			zhixuandanshi = {title:'单式',name:'zhixuandanshi', parent:'zhixuan', mode:'houer'},
			//后二和值
			zhixuanhezhi = {title:'和值',name:'zhixuanhezhi', parent:'zhixuan', mode:'houer', headline:['后二','直选和值']},
			//后二跨度
			zhixuankuadu = {title:'跨度',name:'zhixuankuadu', parent:'zhixuan', mode:'houer', headline:['后二','直选跨度']},
		//组选
			//后二复式 
			zuxuanfushi = {title:'复式',name:'zuxuanfushi', parent:'zuxuan', mode:'houer', headline:['后二','组选']},
			//后二单式
			zuxuandanshi = {title:'单式',name:'zuxuandanshi', parent:'zuxuan', mode:'houer', headline:['后二','组选单式']},
			//后二和值
			zuxuanhezhi = {title:'和值',name:'zuxuanhezhi', parent:'zuxuan', mode:'houer', headline:['后二','组选和值']},
			//后二包胆
			zuxuanbaodan = {title:'包胆',name:'zuxuanbaodan', parent:'zuxuan', mode:'houer', headline:['后二','组选包胆']};
		
		//玩法组===========================================
		var zhixuan = {title:'直&nbsp;&nbsp;&nbsp;选',name:'zhixuan', parent:'houer', childs:[zhixuanfushi, zhixuandanshi, zhixuanhezhi, zhixuankuadu]},
			zuxuan = {title:'组&nbsp;&nbsp;&nbsp;选',name:'zuxuan', parent:'houer', childs:[zuxuanfushi, zuxuandanshi, zuxuanhezhi, zuxuanbaodan]};
		
		//玩法分类
		//p3后二
		houer = {title:'后二', name:'houer', childs:[zhixuan, zuxuan]};
	})();

	//p3前二玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//直选
			//p3前二复式 
		var zhixuanfushi = {title:'复式',name:'zhixuanfushi', parent:'zhixuan', mode:'qianer'},
			//p3前二单式
			zhixuandanshi = {title:'单式',name:'zhixuandanshi', parent:'zhixuan', mode:'qianer'},
			//p3前二和值
			zhixuanhezhi = {title:'和值',name:'zhixuanhezhi', parent:'zhixuan', mode:'qianer', headline:['前二','直选和值']},
			//p3前二跨度
			zhixuankuadu = {title:'跨度',name:'zhixuankuadu', parent:'zhixuan', mode:'qianer', headline:['前二','直选跨度']},
		//组选
			//p3前二复式 
			zuxuanfushi = {title:'复式',name:'zuxuanfushi', parent:'zuxuan', mode:'qianer', headline:['前二','组选']},
			//p3前二单式
			zuxuandanshi = {title:'单式',name:'zuxuandanshi', parent:'zuxuan', mode:'qianer', headline:['前二','组选单式']},
			//p3前二和值
			zuxuanhezhi = {title:'和值',name:'zuxuanhezhi', parent:'zuxuan', mode:'qianer', headline:['前二','组选和值']},
			//p3前二包胆
			zuxuanbaodan = {title:'包胆',name:'zuxuanbaodan', parent:'zuxuan', mode:'qianer', headline:['前二','组选包胆']};
		
		//玩法组===========================================
		var zhixuan = {title:'直&nbsp;&nbsp;&nbsp;选',name:'zhixuan', parent:'qianer', childs:[zhixuanfushi, zhixuandanshi, zhixuanhezhi, zhixuankuadu]},
			zuxuan = {title:'组&nbsp;&nbsp;&nbsp;选',name:'zuxuan', parent:'qianer', childs:[zuxuanfushi, zuxuandanshi, zuxuanhezhi, zuxuanbaodan]};
		
		//玩法分类
		//前二
		qianer = {title:'前二', name:'qianer', childs:[zhixuan, zuxuan]};
	})();

	//一星玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//定位胆
			//万位 
		var fushi = {title:'复式',name:'fushi', parent:'dingweidan', mode:'yixing'};
		
		//玩法组===========================================
		var dingweidan = {title:'定位胆',name:'dingweidan', parent:'yixing', childs:[fushi]};
		
		//玩法分类
		//一星
		yixing = {title:'一星', name:'yixing', childs:[dingweidan]};
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
			me.types = [sanxing, qianer, houer, yixing];
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
	
	host.Games.FC3D[name] = Main;
	
})(phoenix, "Config", phoenix.Event);









