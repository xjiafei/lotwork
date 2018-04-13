

//选一任选二中二复式
(function(host, GameMethod, undefined){
	var defConfig = {
		//名称
		name:'renxuan.putongwanfa.renxuan1',
		//玩法提示
		tips:'从01-11共11个号码中选择3个不重复的号码组成一注，所选号码与当期顺序摇出的5个号码中的前3个号码相同，且顺序一致，即为中奖。即中1782元。',
		//选号实例
		exampleTip: '选一任选一中一复式'
	}
	Games = host.Games,
	GameMessage = Games.getCurrentGameMessage(),
	BJKL8 = host.Games.BJKL8.getInstance();

	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;
			//console.log(me.combine([31,32,39,40],3));

			//添加统计中奖结果图表
			me.addEvent('afterSetBallData', function(e, x, y, v){
				var me = this;
				
		
				//展示选择结果图表
				me.showSelectResult();
			})
		},
		
		//复位选球数据
		rebuildData:function(){
			var me = this;
			me.balls = [
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
						];
		},
		//输出中奖结果[图表]
		showSelectResult: function(){
			var me = this,
				html = '',
				dom = me.container.find('.J-select-result'),
				currentSelectNum = me.countBallsNum();

				//超出限制范围
				if(currentSelectNum < 1 || currentSelectNum > 80){
					if($.trim(me.defConfig['name']) != 'renxuan.putongwanfa.renxuan1'){
						me.resetChartsResult();
						return;
					}
					return;					
				}

				var html = me.ballsResultCharts({
					'selectNum': currentSelectNum,
					'mode': '任选1',
					'maxNum': currentSelectNum,
					'money': {'1' : 6.4}
				});

				//渲染输出图表
				dom.html(html);
		},
		//清空中奖结果列表
		resetChartsResult: function(){
			var me = this,
				html = '',
				dom = me.container.find('.J-select-result');

				html = '<div class="ball-table-title">您选择的号码可能中奖结果如下：</div><table cellspacing="1" width="100%"><tbody><tr><th colspan="5">玩法：任选1　　　投注号码数：0</th></tr><tr><td>每注奖金</td><td>6.40</td><td></td><td></td><td></td></tr><tr><td>中奖号码数</td><td>选1中1</td><td>未中奖</td><td>总注数</td><td>总奖金</td></tr><tr><td>中0个号码</td><td>0</td><td>0</td><td>0</td><td style="text-align:center">0</td></tr></tbody></table>';

			dom.html(html);
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
			for(var k=0;k < 1; k++){
				current[k] = [Math.ceil(Math.random() * (rowLen - 1))];
				current[k].sort(function(a, b){
					return a > b ? 1 : -1;
				});
			};	
			return current;
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
		//检测选球是否完整，是否能形成有效的投注
		//并设置 isBallsComplete 
		checkBallIsComplete: function(){
			var me = this,
				ball = me.getBallData()[0],
				i = 0,
				len = ball.length,
				num = 0;
			for(;i < len;i++){
				if(ball[i] > 0){
					num++;
				}
			}

			if(num > 0 ){
				return me.isBallsComplete = true;
			}

			return me.isBallsComplete = false;
		}
	};

	//html模板
	var html_all = [];
			html_all.push('<div class="number-select-content">');
				html_all.push('<div class="ball-section">');
					html_all.push('<div class="clearfix">');
						html_all.push('<div class="ball-number-section">');
						html_all.push('<div class="ball-number-up">');
							html_all.push('<div class="ball-title"><strong>上</strong></div>');
							html_all.push('<div class="ball-content">');
								$.each([0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40], function(i){
									if(i == 0){
										html_all.push('<a style="display:none" href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a>');	
									}else{
										if(i < 10){
											html_all.push('<a href="javascript:void(0);" data-param="action=ball&value=' + '0' + this +'&row=<#=row#>" class="ball-number">' + '0' +this+'</a>');
										}else{
											html_all.push('<a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a>');
										}
									}
								});
								html_all.push('</div>');
							html_all.push('</div>');
								html_all.push('<div class="ball-number-down">');
									html_all.push('<div class="ball-title"><strong>下</strong></div>');
									html_all.push('<div class="ball-content">');
										$.each([41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80], function(i){
											html_all.push('<a href="javascript:void(0);" data-param="action=ball&value='+ this +'&row=<#=row#>" class="ball-number">'+this+'</a>');
										});
									html_all.push('</div>');
								html_all.push('</div>');
							html_all.push('</div>');
									html_all.push('<div class="J-select-result ball-table">');
										
									html_all.push('</div>');
								html_all.push('</div>');
									html_all.push('<div class="ball-control">');
										html_all.push('<span class="ball-control-title">趣味机选：</span>');
										html_all.push('<a href="javascript:void(0);" data-param="action=batchSetBall&row=<#=row#>&bound=random&ranNum=8&start=1">随机</a>');
										html_all.push('<a href="javascript:void(0);" data-param="action=batchSetBall&row=<#=row#>&bound=up&ranNum=40&start=1">上</a>');
										html_all.push('<a href="javascript:void(0);" data-param="action=batchSetBall&row=<#=row#>&bound=down&ranNum=40">下</a>');
										html_all.push('<a href="javascript:void(0);" data-param="action=batchSetBall&row=<#=row#>&bound=odd&ranNum=40">奇</a>');
										html_all.push('<a href="javascript:void(0);" data-param="action=batchSetBall&row=<#=row#>&bound=even&ranNum=40&start=1">偶</a>');
										html_all.push('<a href="javascript:void(0);" data-param="action=batchSetBall&row=<#=row#>&bound=upOdd&ranNum=20">上·单</a>');
										html_all.push('<a href="javascript:void(0);" data-param="action=batchSetBall&row=<#=row#>&bound=upEven&ranNum=20&start=1">上·双</a>');
										html_all.push('<a href="javascript:void(0);" data-param="action=batchSetBall&row=<#=row#>&bound=downOdd&ranNum=20">下·单</a>');
										html_all.push('<a href="javascript:void(0);" data-param="action=batchSetBall&row=<#=row#>&bound=downEven&ranNum=20">下·双</a>');
										html_all.push('<a href="javascript:void(0);" data-param="action=batchSetBall&row=<#=row#>&bound=none">清</a>');
										html_all.push('<span class="ball-control-text">(每组选取8个号码)</span>');
									html_all.push('</div>');
							html_all.push('</div>');
						html_all.push('</div>');
		
	//继承GameMethod
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器实例上
	BJKL8[defConfig.name] = new Main();
	
})(phoenix, phoenix.Games.BJKL8.Renxuan);

