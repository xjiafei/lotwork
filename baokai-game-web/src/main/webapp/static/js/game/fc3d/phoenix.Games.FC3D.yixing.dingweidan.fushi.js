

//一星定位胆万位玩法实现类
(function(host, GameMethod, undefined){
	var defConfig = {
		name:'yixing.dingweidan.fushi',
		//玩法提示
		tips:'一星定位胆复式玩法提示',
		//选号实例
		exampleTip: '一星定位胆玩复式范例'
	},
	Games = host.Games,
	FC3D = Games.FC3D.getInstance();
	
	
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
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
						];
		},
		buildUI:function(){
			var me = this;
			me.container.html(html_all.join(''));
		},
		//获取总注数/获取组合结果
		//isGetNum=true 只获取数量，返回为数字
		//isGetNum=false 获取组合结果，返回结果为单注数组
		getLottery:function(isGetNum){
			var me = this,data = me.getBallData(),
				i = 0,len = data.length,row,
				_tempRow = [],
				j = 0,len2 = 0,
				result = [],
				result2 = [],
				//总注数
				total = 1,
				rowNum = 0;
			
			//检测球是否完整
			for(;i < len;i++){
				result[i] = [];
				row = data[i];
				len2 = row.length;
				isEmptySelect = true;
				rowNum = 0;
				for(j = 0;j < len2;j++){
					if(row[j] > 0){
						me.isBallsComplete = true;
						//需要计算组合则推入结果
						if(!isGetNum){
							result[i].push(j);
						}
						rowNum++;
					}
				}
				//计算注数
				total *= rowNum;
			}
			
			//返回注数
			if(isGetNum){
				return total;
			}
			
			if(me.isBallsComplete){
				//组合结果
				for(i = 0,len = result.length;i < len;i++){
					for(j = 0,len2 = result[i].length;j < len2;j++){
						result2.push([result[i][j]]);
					}
				}
				return result2;
			}else{
				return [];
			}	
		},
		makePostParameter: function(original){
			var me = this,
				i = 0,
				len = original.length,
				result = [];
			for(;i < len;i++){
				if(original[i].length < 1){
					result.push('-');
				}else{
					result.push(original[i].join(''));
				}
			}
			return result.join(',');
		},
		//生成单注随机数
		createRandomNum: function(){
			var me = this,
				i = 0,
				current = [],
				len = me.getBallData().length,
				rowLen = me.getBallData()[0].length,
				numRow = 0;
				me.numCell = 0;

			numRow = Math.floor(Math.random() * len);
			
			for(;i < len;i++){
				if(i == numRow){
					numCell = Math.floor(Math.random() * rowLen);
					current.push([numCell]);
				}else{
					current.push([]);
				}
			}
			return current;
		},
		//限制随机投注重复
		checkRandomBets: function(hash,times){
			var me = this,
				allowTag = typeof hash == 'undefined' ? true : false,
				hash = hash || {},
				current = [],
				times = times || 0,
				len = me.getBallData().length,
				rowLen = me.getBallData()[0].length,
				order = Games.getCurrentGameOrder().getTotal()['orders'];

			//生成单数随机数
			current = me.createRandomNum(); 
			//如果大于限制数量
			//则直接输出
			if(Number(times) > Number(me.getRandomBetsNum())){
				return current;
			}
			//建立索引
			if(allowTag){
				for (var i = 0; i < order.length; i++) {
					if(order[i]['type'] == me.defConfig.name){
						var name = order[i]['original'].join(',');
						hash[name] = name;
					}
				};
			}
			//对比结果
			if(hash[current.join(',')]){
				times++;
				return arguments.callee.call(me, hash, times);
			}
			return current;
		},
		//生成一个当前玩法的随机投注号码
		//该处实现复式，子类中实现其他个性化玩法
		//返回值： 按照当前玩法生成一注标准的随机投注单(order)
		randomNum:function(){
			var me = this,
				i = 0, 
				current = [],
				order = null,
				len = me.getBallData().length,
				rowLen = me.getBallData()[0].length,
				lotterys = [],
				original = [];

			current = me.checkRandomBets();
			original = current;
			lotterys = [[me.numCell]];
			
			order = {
				'type':  Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
				'original':original,
				'lotterys':lotterys,
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
			html_row.push('<div class="ball-title">');
			html_row.push('<strong><#=title#>位</strong>');			
			html_row.push('</div>');
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
		$.each(['百','十','个'], function(i){
			html_all.push(rowStr.replace(/<#=title#>/g, this).replace(/<#=row#>/g, i));
		});
		html_all.push(html_bottom.join(''));
		
	
	
	//继承GameMethod
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器实例上
	FC3D[defConfig.name] = new Main();
	
})(phoenix, phoenix.GameMethod);

