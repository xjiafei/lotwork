

(function(host, name, Event, undefined){
	var defConfig = {
		//当前彩种名称
		gameType : 'BJKL8',
		gameTypeCn : 'BJKL8'
	},
		instance;
		
		//任选
	var renxuan,
		//趣味
		quwei;
	
	//趣味玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//直选
			//任选1 
		var quweib = {title:'趣味型', name:'quweib', parent:'panmian', mode:'quwei', headline:['趣味型'] };
		
		//玩法组===========================================
		var panmian = {title:'趣味型',name:'panmian', parent:'quwei', childs:[quweib]};
		
		//玩法分类
		//趣味
		quwei = {title:'趣味型', name:'quwei', childs:[panmian]};
	})();
	
	//任选玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//直选
			//任选1 
		var renxuan1 = {title:'任选1', name:'renxuan1', parent:'putongwanfa', mode:'renxuan'},
			//任选2
			renxuan2 = {title:'任选2', name:'renxuan2', parent:'putongwanfa', mode:'renxuan'},
			//任选3
			renxuan3 = {title:'任选3', name:'renxuan3', parent:'putongwanfa', mode:'renxuan'},
			//任选4
			renxuan4 = {title:'任选4', name:'renxuan4', parent:'putongwanfa', mode:'renxuan'},
			//任选5
			renxuan5 = {title:'任选5', name:'renxuan5', parent:'putongwanfa', mode:'renxuan'},
			//任选6
			renxuan6 = {title:'任选6', name:'renxuan6', parent:'putongwanfa', mode:'renxuan'},
			//任选7
			renxuan7 = {title:'任选7', name:'renxuan7', parent:'putongwanfa', mode:'renxuan'};
		
		//玩法组===========================================
		var putongwanfa = {title:'普通玩法',name:'putongwanfa', parent:'renxuan', childs:[renxuan1, renxuan2, renxuan3, renxuan4, renxuan5, renxuan6, renxuan7]};
		
		//玩法分类
		//任选
		renxuan = {title:'任选型', name:'renxuan', childs:[putongwanfa]};
	})();

	var pros = {
		init:function(){
			var me = this;
			me.types = [quwei, renxuan];
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
	
	host.Games.BJKL8[name] = Main;
	
})(phoenix, "Config", phoenix.Event);









