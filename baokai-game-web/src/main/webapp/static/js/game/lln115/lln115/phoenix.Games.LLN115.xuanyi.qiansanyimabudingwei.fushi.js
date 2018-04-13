

//选一任选二中二复式
(function(host, GameMethod, undefined){
	var defConfig = {
		//名称
		name:'xuanyi.qiansanyimabudingwei.fushi',
		//玩法提示
		tips:'从01-11共11个号码中选择3个不重复的号码组成一注，所选号码与当期顺序摇出的5个号码中的前3个号码相同，且顺序一致，即为中奖。即中1782元。',
		//选号实例
		exampleTip: '选一任选一中一复式'
	};
		//游戏类
	var LLN115 = host.Games.LLN115.getInstance();
	
	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;
            me.getHotCold(me.getGameMethodName(), 'currentFre', 'lost');
			//初始化冷热号事件
			me.initHotColdEvent();
		},
		//复位选球数据
		rebuildData:function(){
			var me = this;
			me.balls = [
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
						];
		},
		buildUI:function(){
			var me = this;
			me.container.html(html_all.join(''));
		},
		makePostParameter: function(original){
			var me = this,
				result = [],
				current = [],
				len = original.length,
				i = 0;
			for (; i < len; i++) {
				current = original[i];
				for (var k = 0; k < current.length; k++) {
					if(Number(current[k]) < 10){
						result.push('0' + current[k]);
					}else{
						result.push(current[k]);
					}
				};
			};
			return result.join(',');
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
				current[k] = [Math.ceil(Math.random() * (rowLen - 1))];
				current[k].sort(function(a, b){
					return a > b ? 1 : -1;
				});
			};	
			
			return current;
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
			html_row.push('<ul class="ball-content">');
			$.each([0,1,2,3,4,5,6,7,8,9,10,11], function(i){
				if(i == 0){
					html_row.push('<li style="display:none;"><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a><span class="ball-aid-hot">0</span></li>');	
				}else{
					if(i < 10){
						html_row.push('<li><a href="javascript:void(0);" data-param="action=ball&value=' + '0' + this +'&row=<#=row#>" class="ball-number">' + '0' +this+'</a><span class="ball-aid-hot">0</span></li>');
					}else{
						html_row.push('<li><a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a><span class="ball-aid-hot">0</span></li>');
					}
				}
			});
			html_row.push('</ul>');
			html_row.push('<div class="ball-control">');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=all&start=1" href="javascript:void(0);">全</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=big&start=0" href="javascript:void(0);">大</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=small&start=0" href="javascript:void(0);">小</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=odd" href="javascript:void(0);">奇</a>');
				html_row.push('<a data-param="action=batchSetBall&row=<#=row#>&bound=even&start=1" href="javascript:void(0);">偶</a>');
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
	LLN115[defConfig.name] = new Main();
	
})(phoenix, phoenix.GameMethod);

