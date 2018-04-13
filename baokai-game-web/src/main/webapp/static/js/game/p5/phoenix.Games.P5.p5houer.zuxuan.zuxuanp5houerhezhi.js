

//前二直选复式玩法实现类
(function(host, GameMethod, undefined){
	var defConfig = {
		name:'p5houer.zuxuan.zuxuanp5houerhezhi',
		//玩法提示
		tips:'后二组选和值玩法提示',
		//选号实例
		exampleTip: '后二组选和值范例'
	},
	Games = host.Games,
	P5 = Games.P5.getInstance();
	
	
	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;
			
			//默认加载执行30期遗漏号码
			me.getHotCold(me.getGameMethodName(), 'currentFre', 'lost');
			//初始化冷热号事件
			me.initHotColdEvent();
		},
		//时时彩复式结构为5行10列
		//复位选球数据
		rebuildData:function(){
			var me = this;
			me.balls = [
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
						];
		},
		buildUI:function(){
			var me = this;
			me.container.html(html_all.join(''));
		},
		makePostParameter: function(original){
			var me = this,
				i = 0,
				len = original.length,
				result = [];
			
			for(;i < len;i++){
				result.push(original[i].join());
			}
			return result.join(',');
		},
		//计算各种结果
		mathResult: function(sum, nBegin, nEnd){
			var me = this,
				arr = [],
				checkArray = [],
				_arr = [],
				x,y,
				has = {},
				key = '',
				fn = function(a, b){
					return a - b;
				};
				
			for (x=nBegin;x<=nEnd ;x++ ){
				for (y=nBegin;y<=nEnd ;y++ ){
					if(x+y == sum){
						_arr = [x,y];
						key = _arr.sort(fn).join(',');
						if(!has[key] && x != y){
							arr.push([x,y]);
							has[key] = true;
						}
					}
				}
			}
			return arr;
		},
		//获取总注数/获取组合结果
		//isGetNum=true 只获取数量，返回为数字
		//isGetNum=false 获取组合结果，返回结果为单注数组
		getLottery:function(isGetNum){
			var me = this,
				data = me.getBallData()[0],
				i = 0,
				len = data.length,
				j = 0,
				len2,
				
				isEmptySelect = true,
				numArr = [],
				result = [];
				
			
			for(i = 0;i < len;i++){
				if(data[i] > 0){
					isEmptySelect = false;
					numArr.push(i);
				}
			}
			if(isEmptySelect){
				me.isBallsComplete = false;
				return [];
			}
			me.isBallsComplete = true;
			
			
			for(i = 0,len = numArr.length;i < len;i++){
				result = result.concat(me.mathResult(numArr[i], 0, 9));
			}
			
			
			return result;
			
		},
		//生成单注随机数
		createRandomNum: function(){
			var me = this,
				i = 0,
				current = [],
				hasArr = [],
				len = me.getBallData().length,
				rowLen = me.getBallData()[0].length;
			
			current.push(Math.ceil(Math.random() * (rowLen-1)));
			return current;
		},
		//获取随机数
		randomNum:function(){
			var me = this,
				i = 0, 
				current = [], 
				currentNum, 
				ranNum,
				lotterys = [],
				order = null,
				dataNum = me.getBallData(),
				len = me.getBallData()[0].length,
				name = me.defConfig.name;				

			current = me.checkRandomBets();
			lotterys = me.mathResult(current[0], 0, 9);
			order = {
				'type':  Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
				'original':[current],
				'lotterys': lotterys,
				'moneyUnit': Games.getCurrentGameStatistics().getMoneyUnit(),
				'multiple': Games.getCurrentGameStatistics().getMultip(),
				'onePrice': Games.getCurrentGameStatistics().getOnePrice(),
				'num': lotterys.length
			};
			order['amountText'] = Games.getCurrentGameStatistics().formatMoney(order['num'] * order['moneyUnit'] * order['multiple'] * order['onePrice']);
			return order;
		}
		
		
	};
	



	//html模板
	var html_head = [];
		
		html_head.push('<div class="number-select-content">');
		html_head.push('<ul class="ball-section">');
		//每行
	var html_row = [];
			html_row.push('<li>');
			html_row.push('<ul class="ball-content">');
			$.each([0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17], function(i){
				if(i == 0){
					html_row.push('<li style="display:none;"><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a></li>');
				}else{
					html_row.push('<li><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a></li>');
				}
				
			});
			html_row.push('</ul>');
		html_row.push('</li>');
	var html_bottom = [];
		html_bottom.push('</ul>');
		html_bottom.push('</div>');
		//拼接所有
	var html_all = [],rowStr = html_row.join('');
		html_all.push(html_head.join(''));
		$.each([''], function(i){
			html_all.push(rowStr.replace(/<#=title#>/g, this).replace(/<#=row#>/g, i));
		});
		html_all.push(html_bottom.join(''));
		
	
	
	//继承GameMethod
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器实例上
	P5[defConfig.name] = new Main();
	
})(phoenix, phoenix.GameMethod);

