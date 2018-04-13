

//四星直选复式玩法实现类
(function(host, GameMethod, undefined){
	var defConfig = {
		name:'sixing.zhixuan.fushi',
		//玩法提示
		tips:'四星直选复式玩法提示',
		//选号实例
		exampleTip: '四星直选复式范本',
		//限制选求重复次数
		randomBetsNum:1000
	};
	//游戏类
	var SSC = host.Games.SSC.getInstance();
	
	
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
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
						];
		},
		buildUI:function(){
			var me = this;
			me.container.html(html_all.join(''));
		},
		
		//检测选球是否完整，是否能形成有效的投注
		//并设置 isBallsComplete 
		checkBallIsComplete:function(){
			var me = this,data = me.getBallData(),
				i = 0,len = data.length,row,isEmptySelect = true,
				j = 0,len2 = 0;
			
			//检测球是否完整
			for(;i < len;i++){
				row = data[i];
				len2 = row.length;
				isEmptySelect = true;
				for(j = 0;j < len2;j++){
					if(row[j] > 0){
						isEmptySelect = false;
					}
				}
				if(isEmptySelect){
					//alert('第' + i + '行选球不完整');
					me.isBallsComplete = false;
					return false;
				}
			}
			
			return me.isBallsComplete = true;
		},
		//获取组合结果
		getLottery:function(){
			var me = this,data = me.getBallData(),
				i = 0,len = data.length,row,
				j = 0,len2 = 0,
				result = [];
			
			//选球是否完整
			if(me.checkBallIsComplete()){
				//获取有效选球
				for(;i < len;i++){
					result[i] = [];
					row = data[i];
					len2 = row.length;
					for(j = 0;j < len2;j++){
						if(row[j] > 0){
							result[i].push(j);
						}
					}
				}
				//组合结果
				return me.combination(result);
			}
			return [];
		},
		makePostParameter: function(original){
			var me = this,
				result = ['-'],
				len = original.length,
				i = 0;
			for (; i < len; i++) {
				result = result.concat(original[i].join(''));
			}
			return result.join(',');
		}
		
		
	};
	



	//html模板
	var html_head = [];
		//头部
		html_head.push('<div class="number-select-title balls-type-title clearfix">');
			html_head.push('<ul class="function-select-title game-frequency-type">');
				html_head.push('<li class="lost" data-type="lost">遗漏</li>');
				html_head.push('<li class="fre" data-type="fre">冷热</li>');
			html_head.push('</ul>');
			html_head.push('<ul class="function-select-content">');
				html_head.push('<li class="game-frequency-lost-length"><a href="javascript:void(0);" data-type="currentFre" class="periodcurrentFre">当前遗漏</a><a data-type="maxFre" href="javascript:void(0);" class="periodmaxFre">最大遗漏</a></li>');
				html_head.push('<li style="display:none" class="game-frequency-fre-length"><a href="javascript:void(0);" data-type="30" class="period30">30期</a><a href="javascript:void(0);" data-type="60" class="period60">60期</a><a href="javascript:void(0);" data-type="100" class="period100">100期</a></li>');
			html_head.push('</ul>');
		html_head.push('</div>');
		html_head.push('<div class="number-select-content">');
		html_head.push('<ul class="ball-section">');
		//每行
	var html_row = [];
			html_row.push('<li>');
			html_row.push('<div class="ball-title">');
			html_row.push('<strong><#=title#>位</strong>');
			html_row.push('<span>当前遗漏</span>');
			html_row.push('</div>');
			html_row.push('<ul class="ball-content">');
			$.each([0,1,2,3,4,5,6,7,8,9], function(i){
				html_row.push('<li><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a><span class="ball-aid-hot">0</span></li>');
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
		$.each(['千','百','十','个'], function(i){
			html_all.push(rowStr.replace(/<#=title#>/g, this).replace(/<#=row#>/g, i));
		});
		html_all.push(html_bottom.join(''));
		
		
	
	
	//继承GameMethod
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器实例上
	SSC[defConfig.name] = new Main();
	
})(phoenix, phoenix.GameMethod);

