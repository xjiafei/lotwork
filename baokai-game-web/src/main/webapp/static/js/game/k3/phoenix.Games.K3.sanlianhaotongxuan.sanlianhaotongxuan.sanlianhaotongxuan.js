

//后二直选复式玩法实现类
(function(host, GameMethod, undefined){
	var defConfig = {
		name:'sanlianhaotongxuan.sanlianhaotongxuan.sanlianhaotongxuan',
		//玩法提示
		tips:'快三三连号通选玩法',
		//选号实例
		exampleTip: '快三三连号通选玩法范例'
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
			var me = this;
			me.balls = [
				[0,0]
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
				result.push('123 234 345 456');
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
			html_row.push('<ul class="ball-content sanlianhaotongxuan">');
			$.each([0,1], function(i){
				if(i==0){
					html_row.push('<li style="display:none"><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">三连号通选</a><span class="ball-aid-hot">0</span></li>');
				}else{
					html_row.push('<li><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">三连号通选</a><span class="ball-aid-hot">0</span></li>');			
				}
			});
			html_row.push('</ul>');
			html_row.push('<div class="ball-control" style="<#=style#>">');
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
			html_all.push(rowStr.replace(/<#=title#>/g, this).replace(/<#=row#>/g, i).replace(/<#=style#>/g, "display:none"));
		});
		html_all.push(html_bottom.join(''));
		
	
	
	//继承GameMethod
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器实例上
	k3[defConfig.name] = new Main();
	
})(phoenix, phoenix.GameMethod);
