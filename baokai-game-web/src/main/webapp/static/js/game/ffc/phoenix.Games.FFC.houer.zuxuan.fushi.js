

//后二组选复式玩法实现类
(function(host, GameMethod, undefined){
	var defConfig = {
		name:'houer.zuxuan.fushi',
		//玩法提示
		tips:'后二组选复式玩法提示',
		//选号实例
		exampleTip: '后二组选复式范例'
	},
	Games = host.Games,
	FFC = Games.FFC.getInstance();
	
	
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
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
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
				result = [],
				
				times = 0;
			
			
			
			for(i = 0;i < len;i++){
				if(data[i] > 0){
					times++;
					numArr.push(i);
				}
			}
			if(times > 1){
				isEmptySelect = false;
			}
			if(isEmptySelect){
				me.isBallsComplete = false;
				return [];
			}
			me.isBallsComplete = true;
			
			result = me.combine(numArr, 2);
			
			return result;
			
		},
		//获取随机数
		randomNum:function(){
			var me = this,
				i = 0, 
				current = [], 
				lotterys = [],
				order = null,
				len = me.getBallData()[0].length,
				
				hasArr = [];

			current.push(me.removeSame(hasArr));
			hasArr.push(current);
			current.push(me.removeSame(hasArr));
			current.sort(function(a, b){
				return a - b;
			});
			lotterys = [[current[0], current[1]]];
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
		//头部
		html_head.push('<div class="number-select-content">');
		html_head.push('<ul class="ball-section">');
		//每行
	var html_row = [];
			html_row.push('<li>');
			html_row.push('<ul class="ball-content">');
			$.each([0,1,2,3,4,5,6,7,8,9], function(i){
				html_row.push('<li><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a></li>');
			});
			html_row.push('</ul>');
			html_row.push('<div class="ball-control">');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=all" href="javascript:void(0);">全</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=big" href="javascript:void(0);">大</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=small" href="javascript:void(0);">小</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=odd" href="javascript:void(0);">奇</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=even" href="javascript:void(0);">偶</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=none" href="javascript:void(0);">清</a>');
			html_row.push('</div>');
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
	FFC[defConfig.name] = new Main();
	
})(phoenix, phoenix.GameMethod);

