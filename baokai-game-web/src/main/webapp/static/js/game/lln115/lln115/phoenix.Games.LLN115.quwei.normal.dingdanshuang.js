

//选一任选二中二复式
(function(host, GameMethod, undefined){
	var defConfig = {
		//名称
		name:'quwei.normal.dingdanshuang',
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
			
			//初始化冷热号事件
			me.initHotColdEvent();
		},
		//复位选球数据
		rebuildData:function(){
			var me = this;
			me.balls = [
						[-1,-1,-1,-1,-1,-1]
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
					var num = Number(current[k]),
						name = '';

					if(num == 0){
						name = '5单0双';
					}
					if(num == 1){
						name = '4单1双';
					}
					if(num == 2){
						name = '3单2双';
					}
					if(num == 3){
						name = '2单3双';
					}
					if(num == 4){
						name = '1单4双';
					}
					if(num == 5){
						name = '0单5双';
					}
						
					result.push(name);
					
				};
			};
			return result.join('|');
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
				current[k] = [Math.floor(Math.random() * rowLen)];
				current[k].sort(function(a, b){
					return a > b ? 1 : -1;
				});
			};	
			
			return current;
		}
	};
	
	//html模板
	var html_head = [];
		html_head.push('<div class="number-select-content">');
		html_head.push('<ul class="ball-section">');
		//每行
	var html_row = [];
			html_row.push('<li>');
			html_row.push('<ul class="ball-content dingdanshuang">');
			$.each(['5单0双','4单1双','3单2双','2单3双','1单4双','0单5双'], function(i){
				html_row.push('<li><a href="javascript:void(0);" data-param="action=ball&value=' + i +'&row=<#=row#>" class="ball-number">' +this+'</a></li>');	
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
	LLN115[defConfig.name] = new Main();
	
})(phoenix, phoenix.GameMethod);

