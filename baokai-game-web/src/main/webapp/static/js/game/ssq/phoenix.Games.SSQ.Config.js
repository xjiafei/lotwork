

(function(host, name, Event, undefined){
	var defConfig = {
		//当前彩种名称
		gameType : 'SSQ',
		gameTypeCn : 'SSQ'
	},
		instance;
		
		//标准投注
	var biaozhuntouzhu;
	
	
	//标准投注玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//直选
			//复式
		var fushi = {title:'复式', name:'fushi', parent:'biaozhun', mode:'biaozhuntouzhu'},
			//单式
			danshi = {title:'单式', name:'danshi', parent:'biaozhun', mode:'biaozhuntouzhu'},
			//和值
			dantuo = {title:'胆拖', name:'dantuo', parent:'biaozhun', mode:'biaozhuntouzhu'};
		
		//玩法组===========================================
		var biaozhun = {title:'标准',name:'biaozhun', parent:'biaozhuntouzhu', childs:[fushi, danshi, dantuo]};
		
		//玩法分类
		//标准投注
		biaozhuntouzhu = {title:'标准投注', name:'biaozhuntouzhu', childs:[biaozhun]};
	})();

	var pros = {
		init:function(){
			var me = this;
			me.types = [biaozhuntouzhu];
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
	
	host.Games.SSQ[name] = Main;
	
})(phoenix, "Config", phoenix.Event);









