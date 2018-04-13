

(function(host, name, Event, undefined){
	var defConfig = {
		//当前彩种名称
		gameType : 'LLN115',
		gameTypeCn : '11选5'
	},
		instance;
		
		//选一
	var xuanyi,
		//选二
		xuaner,
		//选三
		xuansan,
		//选四
		xuansi,
		//选五
		xuanwu,
		//选六
		xuanliu,
		//选七
		xuanqi,
		//选八
		xuanba,
		//趣味
		quwei;
	
	
	//选一玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//任选一中一
			//复式 
		var renxuanfushi = {title:'复式',name:'fushi', parent:'renxuanyizhongyi', mode:'xuanyi'},
			//单式
			renxuandanshi = {title:'单式',name:'danshi', parent:'renxuanyizhongyi', mode:'xuanyi'},
		//定位胆
			//复式
			dingweidanfushi = {title:'复式',name:'fushi', parent:'dingweidan', mode:'xuanyi'},
		//前三不定位
			//复式
			qiansanfushi = {title:'复式',name:'fushi', parent:'qiansanyimabudingwei', mode:'xuanyi'};
		
		
		
		//玩法组===========================================
		var renxuanyizhongyi = {title:'任选一中一',name:'renxuanyizhongyi', parent:'xuanyi', childs:[renxuanfushi, renxuandanshi]},
			dingweidan = {title:'定位胆',name:'dingweidan', parent:'xuanyi', childs:[dingweidanfushi]},
			qiansanyimabudingwei = {title:'前三一码不定位',name:'qiansanyimabudingwei', parent:'xuanyi', childs:[qiansanfushi]};
		
		//玩法分类
		//选一
		xuanyi = {title:'选一', name:'xuanyi', childs:[renxuanyizhongyi, dingweidan, qiansanyimabudingwei]};
	})();

	//选二玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//任选二中二
			//复式 
		var renxuanfushi = {title:'复式',name:'renxuanfushi', parent:'renxuanerzhonger', mode:'xuaner'},
			//单式
			renxuandanshi = {title:'单式',name:'renxuandanshi', parent:'renxuanerzhonger', mode:'xuaner'},
			//胆拖
			renxuandantuo = {title:'胆拖',name:'renxuandantuo', parent:'renxuanerzhonger', mode:'xuaner'},
		//定位胆
			//复式
			zhixuanfushi = {title:'复式',name:'zhixuanfushi', parent:'qianerzhixuan', mode:'xuaner'},
			zhixuandanshi = {title:'单式',name:'zhixuandanshi', parent:'qianerzhixuan', mode:'xuaner'},
		//前三不定位
			//复式
			zuxuanfushi = {title:'复式',name:'zuxuanfushi', parent:'qianerzuxuan', mode:'xuaner'},
			zuxuandanshi = {title:'单式',name:'zuxuandanshi', parent:'qianerzuxuan', mode:'xuaner'},
			zuxuandantuo = {title:'胆拖',name:'zuxuandantuo', parent:'qianerzuxuan', mode:'xuaner'};
		
		
		
		//玩法组===========================================
		var renxuanerzhonger = {title:'任选二中二',name:'renxuanerzhonger', parent:'xuaner', childs:[renxuanfushi, renxuandanshi, renxuandantuo]},
			qianerzhixuan = {title:'前二直选',name:'qianerzhixuan', parent:'xuaner', childs:[zhixuanfushi, zhixuandanshi]},
			qianerzuxuan = {title:'前二组选',name:'qianerzuxuan', parent:'xuaner', childs:[zuxuanfushi, zuxuandanshi, zuxuandantuo]};
		
		//玩法分类
		//选一
		xuaner = {title:'选二', name:'xuaner', childs:[renxuanerzhonger, qianerzhixuan, qianerzuxuan]};
	})();

	//选三玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//任选二中二
			//复式 
		var renxuanfushi = {title:'复式',name:'renxuanfushi', parent:'renxuansanzhongsan', mode:'xuansan'},
			//单式
			renxuandanshi = {title:'单式',name:'renxuandanshi', parent:'renxuansanzhongsan', mode:'xuansan'},
			//胆拖
			renxuandantuo = {title:'胆拖',name:'renxuandantuo', parent:'renxuansanzhongsan', mode:'xuansan'},
		//定位胆
			//复式
			zhixuanfushi = {title:'复式',name:'zhixuanfushi', parent:'qiansanzhixuan', mode:'xuansan'},
			zhixuandanshi = {title:'单式',name:'zhixuandanshi', parent:'qiansanzhixuan', mode:'xuansan'},
		//前三不定位
			//复式
			zuxuanfushi = {title:'复式',name:'zuxuanfushi', parent:'qiansanzuxuan', mode:'xuansan'},
			zuxuandanshi = {title:'单式',name:'zuxuandanshi', parent:'qiansanzuxuan', mode:'xuansan'},
			zuxuandantuo = {title:'胆拖',name:'zuxuandantuo', parent:'qiansanzuxuan', mode:'xuansan'};
		
		
		
		//玩法组===========================================
		var renxuansanzhongsan = {title:'任选三中三',name:'renxuansanzhongsan', parent:'xuansan', childs:[renxuanfushi, renxuandanshi, renxuandantuo]},
			qiansanzhixuan = {title:'前三直选',name:'qiansanzhixuan', parent:'xuansan', childs:[zhixuanfushi, zhixuandanshi]},
			qiansanzuxuan = {title:'前三组选',name:'qiansanzuxuan', parent:'xuansan', childs:[zuxuanfushi, zuxuandanshi, zuxuandantuo]};
		
		//玩法分类
		//选三
		xuansan = {title:'选三', name:'xuansan', childs:[renxuansanzhongsan, qiansanzhixuan, qiansanzuxuan]};
	})();

	//选四玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//任选四中四
			//复式 
		var fushi = {title:'复式',name:'fushi', parent:'renxuansizhongsi', mode:'xuansi'},
			//单式
			danshi = {title:'单式',name:'danshi', parent:'renxuansizhongsi', mode:'xuansi'},
			//胆拖
			dantuo = {title:'胆拖',name:'dantuo', parent:'renxuansizhongsi', mode:'xuansi'};
		
		//玩法组===========================================
		var renxuansizhongsi = {title:'任选四中四',name:'renxuansizhongsi', parent:'xuansi', childs:[fushi, danshi, dantuo]};
		
		//玩法分类
		//选四
		xuansi = {title:'选四', name:'xuansi', childs:[renxuansizhongsi]}
	})();

	//选五玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//任选五中五
			//复式 
		var fushi = {title:'复式',name:'fushi', parent:'renxuanwuzhongwu', mode:'xuanwu'},
			//单式
			danshi = {title:'单式',name:'danshi', parent:'renxuanwuzhongwu', mode:'xuanwu'},
			//胆拖
			dantuo = {title:'胆拖',name:'dantuo', parent:'renxuanwuzhongwu', mode:'xuanwu'};
		
		//玩法组===========================================
		var renxuanwuzhongwu = {title:'任选五中五',name:'renxuanwuzhongwu', parent:'xuanwu', childs:[fushi, danshi, dantuo]};
		
		//玩法分类
		//选五
		xuanwu = {title:'选五', name:'xuanwu', childs:[renxuanwuzhongwu]}
	})();

	//选六玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//任选六中五
			//复式 
		var fushi = {title:'复式',name:'fushi', parent:'renxuanliuzhongwu', mode:'xuanliu'},
			//单式
			danshi = {title:'单式',name:'danshi', parent:'renxuanliuzhongwu', mode:'xuanliu'},
			//胆拖
			dantuo = {title:'胆拖',name:'dantuo', parent:'renxuanliuzhongwu', mode:'xuanliu'};
		
		//玩法组===========================================
		var renxuanliuzhongwu = {title:'任选六中五',name:'renxuanliuzhongwu', parent:'xuanliu', childs:[fushi, danshi, dantuo]};
		
		//玩法分类
		//选六
		xuanliu = {title:'选六', name:'xuanliu', childs:[renxuanliuzhongwu]}
	})();

	//选七玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//任选七中五
			//复式 
		var fushi = {title:'复式',name:'fushi', parent:'renxuanqizhongwu', mode:'xuanqi'},
			//单式
			danshi = {title:'单式',name:'danshi', parent:'renxuanqizhongwu', mode:'xuanqi'},
			//胆拖
			dantuo = {title:'胆拖',name:'dantuo', parent:'renxuanqizhongwu', mode:'xuanqi'};
		
		//玩法组===========================================
		var renxuanqizhongwu = {title:'任选七中五',name:'renxuanqizhongwu', parent:'xuanqi', childs:[fushi, danshi, dantuo]};
		
		//玩法分类
		//选七
		xuanqi = {title:'选七', name:'xuanqi', childs:[renxuanqizhongwu]}
	})();

	//选八玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//任选八中五
			//复式 
		var fushi = {title:'复式',name:'fushi', parent:'renxuanbazhongwu', mode:'xuanba'},
			//单式
			danshi = {title:'单式',name:'danshi', parent:'renxuanbazhongwu', mode:'xuanba'},
			//胆拖
			dantuo = {title:'胆拖',name:'dantuo', parent:'renxuanbazhongwu', mode:'xuanba'};
		
		//玩法组===========================================
		var renxuanbazhongwu = {title:'任选八中五',name:'renxuanbazhongwu', parent:'xuanba', childs:[fushi, danshi, dantuo]};
		
		//玩法分类
		//选八
		xuanba = {title:'选八', name:'xuanba', childs:[renxuanbazhongwu]}
	})();

	//趣味玩法配置
	(function(){
		//title 标题
		//name 英文名称
		//isColse 是否关闭
			  
		//具体玩法类型===========================================
		//任选八中五
			//复式 
		var dingdanshuang = {title:'定单双',name:'dingdanshuang', parent:'normal', mode:'quwei'},
			//单式
			caizhongwei = {title:'猜中位',name:'caizhongwei', parent:'normal', mode:'quwei'};
		
		//玩法组===========================================
		var normal = {title:'趣味',name:'normal', parent:'quwei', childs:[dingdanshuang, caizhongwei]};
		
		//玩法分类
		//趣味
		quwei = {title:'趣味', name:'quwei', childs:[normal]}
	})();

	var pros = {
		init:function(){
			var me = this;
			me.types = [xuanyi, xuaner, xuansan, xuansi, xuanwu, xuanliu, xuanqi, xuanba, quwei];
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
					//result.push(types[i]['title'].replace(/&nbsp;/g,''));
					if(nameLen > 1 && types[i]['childs'].length > 0){
						tempArr = types[i]['childs'];
						len2 = tempArr.length;
						//循环二级
						for(i2 = 0;i2 < len2;i2++){
							//console.log(tempArr[i2]['name']);
							if(tempArr[i2]['name'] == nameArr[1]){
								result.push(tempArr[i2]['title'].replace(/&nbsp;/g,''));
								if(nameLen > 2 && tempArr[i2]['childs'].length > 0){
									tempArr = tempArr[i2]['childs'];
									len3 = tempArr.length;
									//循环三级
									for(i3 = 0;i3 < len3;i3++){
										if(tempArr[i3]['name'] == nameArr[2]){
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
	
	host.Games.LLN115[name] = Main;
	
})(phoenix, "Config", phoenix.Event);









