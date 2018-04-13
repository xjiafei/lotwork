

//后二直选复式玩法实现类
(function(host, GameMethod, undefined){
	var defConfig = {
		name:'hezhi.hezhi.hezhi',
		//玩法提示
		tips:'快三和值玩法',
		//选号实例
		exampleTip: '快三和值玩法范例'
	},
	Games = host.Games,
	GameTypes = Games.getCurrentGameTypes(),
	k3 = Games.k3.getInstance();
	
	
	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;
			
            GameTypes.hiddenTitleDom();
			
			GameTypes.addEvent('beforeChange', function(e, containerDom, modeName){
				var that = this;

				if(modeName == me.defConfig.name){
					that.hiddenTitleDom();
				}
			});

			//默认加载执行30期遗漏号码
			me.getHotCold(me.getGameMethodName(), 'currentFre', 'lost');
			//初始化冷热号事件
			me.initHotColdEvent();
		},
		//时时彩复式结构为5行10列
		//复位选球数据
		rebuildData:function(){
			var i = 18,
				me = this,
				dataStructure = [];
			//组装数据
			while (i >= 0){
				i--;
				dataStructure.push(-1);
			};
			me.balls = [
				dataStructure
			];
		},
		buildUI:function(){
			var me = this;
			me.container.html(html_all.join(''));
		},
		//生成单注随机数
		createRandomNum: function(){
			var me =this,
				current = [],
				arr = [],
				len = me.getBallData().length,
				rowLen = me.getBallData()[0].length;

			//随机数
			for(var k=0;k < len; k++){
				current[k] = [parseInt(Math.random()*(18-3+1)+3)];
			};

			return current;
		},
		getLottery:function(isGetNum){
			var me = this,data = me.getBallData(),
                meStatistics=phoenix.GameStatistics.getInstance(),
				array=[[3,1111],[4,3333],[5,6667],[6,11111],[7,16667],[8,23529],[9,28571],[10,30769],[11,30769],[12,28571],[13,23529],[14,16667],[15,11111],[16,6667],[17,3333],[18,1111]],
				minArray=[],
				minNum=0,
				multipleLimit=0,
				i = 0,len = data.length,row,isEmptySelect = true,
				_tempRow = [],
				j = 0,len2 = 0,
				result = [],
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
						isEmptySelect = false;
						//需要计算组合则推入结果
						if(!isGetNum){
							result[i].push(j);
						}
						rowNum++;
					}
				}
				if(isEmptySelect){
					//alert('第' + i + '行选球不完整');
					me.isBallsComplete = false;
					meStatistics.multipleLimit = 30769;
			        meStatistics.setMultipleLimit(30769);
					meStatistics.getMultipleLimitDom().html(30769);
					return [];
				}
				//计算注数
				total *= rowNum;
			}
			for(var i=0;i<array.length;i++)
			{
				if($.inArray(array[i][0], result[0])> -1)
				{
				   minArray.push(array[i][1]);
				}
			}
            multipleLimit=Math.min.apply(Math,minArray);
			//倍数最大限制
			if(meStatistics.multipleDom.getInput().val()>multipleLimit)
			{
			   meStatistics.setMultiple(multipleLimit);
			   meStatistics.multipleDom.getInput().val(multipleLimit);
			}
			meStatistics.multipleLimit = multipleLimit;
			meStatistics.setMultipleLimit(multipleLimit);
			meStatistics.getMultipleLimitDom().html(multipleLimit);
			me.isBallsComplete = true;
			//返回注数
			if(isGetNum){
				return total;
			}
			
			if(me.isBallsComplete){
				//组合结果
				return me.combination(result);
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
				result.push(original[i].join(','));
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
			html_row.push('<strong><#=title#></strong>');
			html_row.push('<span>当前遗漏</span>');
			html_row.push('</div>');
			html_row.push('<ul class="ball-content hezhi">');
			$.each([0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18], function(i){
				if(i >= 0 && i < 3){
					html_row.push('<li style="display:none"><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a><span class="ball-aid-hot">0</span></li>');
				}else{
					html_row.push('<li><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a><span class="ball-aid-hot">0</span></li>');
				}
			});
			html_row.push('</ul>');
			html_row.push('<div class="ball-control">');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=small&start=3" href="javascript:void(0);">小</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=odd&start=3" href="javascript:void(0);">单</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=even&start=3" href="javascript:void(0);">双</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=big&start=3" href="javascript:void(0);">大</a>');
			html_row.push('</div>');
		html_row.push('</li>');
	var html_bottom = [];
		html_bottom.push('</ul>');
		html_bottom.push('</div>');
		//拼接所有
	var html_all = [],rowStr = html_row.join('');
		html_all.push(html_head.join(''));
		$.each(['选号区'], function(i){
			html_all.push(rowStr.replace(/<#=title#>/g, this).replace(/<#=row#>/g, i));
		});
		html_all.push(html_bottom.join(''));
		
	
	
	//继承GameMethod
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器实例上
	k3[defConfig.name] = new Main();
	
})(phoenix, phoenix.GameMethod);

