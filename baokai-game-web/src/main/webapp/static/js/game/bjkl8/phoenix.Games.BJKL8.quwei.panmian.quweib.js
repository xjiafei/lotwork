

//选一任选二中二复式
(function(host, GameMethod, undefined){
	var defConfig = {
		//名称
		name:'quwei.panmian.quweib',
		//玩法提示
		tips:'从01-11共11个号码中选择3个不重复的号码组成一注，所选号码与当期顺序摇出的5个号码中的前3个号码相同，且顺序一致，即为中奖。即中1782元。',
		//选号实例
		exampleTip: '选一任选一中一复式'
	}
	Games = host.Games,
	GameMessage = Games.getCurrentGameMessage(),
	GameTypes = Games.getCurrentGameTypes(),
	BJKL8 = host.Games.BJKL8.getInstance();

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

			//展示右侧小走势图
			me.addEvent('afterUpdataGamesInfo', function(){
				if(typeof me.showResultCharts == 'function'){
					me.showResultCharts();
				}
			});
		},
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
		showResultCharts: function(){
			var me = this,
				container = me.container,
				contentDom = $('#J-charts-area'),
				Charts = Games.BJKL8.Charts.getInstance();

			Charts.loadData({'count':30, 'type': me.defConfig['name']}, function(){
				contentDom.html(Charts.getHtml());
			})
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
						name = '上';
					}
					if(num == 1){
						name = '中';
					}
					if(num == 2){
						name = '下';
					}
					if(num == 3){
						name = '奇';
					}
					if(num == 4){
						name = '和';
					}
					if(num == 5){
						name = '偶';
					}
					if(num == 6){
						name = '大单';
					}
					if(num == 7){
						name = '大双';
					}
					if(num == 8){
						name = '小单';
					}
					if(num == 9){
						name = '小双';
					}
						
					result.push(name);
					
				};
			};
			return result.join('|');
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

			if(num > 0 && num < 9){
				return me.isBallsComplete = true;
			}

			return me.isBallsComplete = false;
		}
	};

	//html模板
	var html_all = [];
			html_all.push('<div class="number-select-content">');
				html_all.push('<ul class="ball-section">');	
					html_all.push('<li class="clearfix">');
						html_all.push('<div class="ball-title-k8">上下盘</div>');
						html_all.push('<a class="ball-number ball-number-k8 up" data-param="action=ball&value=0&row=0">');
						html_all.push('<strong>上</strong>');
						html_all.push('<span>（01-40）<br />（上&gt;下）</span>');
						html_all.push('</a>');
						html_all.push('<a class="ball-number ball-number-k8 updown" data-param="action=ball&value=1&row=0">');
						html_all.push('<strong>上</strong>');
						html_all.push('<span>（上=下）</span>');
						html_all.push('</a>');
						html_all.push('<a class="ball-number ball-number-k8 down" data-param="action=ball&value=2&row=0">');
						html_all.push('<strong>上</strong>');
						html_all.push('<span>（41-80）<br />（上&lt;下）</span>');
						html_all.push('</a>');
					html_all.push('</li>');	
					html_all.push('<li class="clearfix">');
						html_all.push('<div class="ball-title-k8">奇偶盘</div>');
						html_all.push('<a class="ball-number ball-number-k8 ji" data-param="action=ball&value=3&row=0">');
						html_all.push('<strong>奇</strong>');
						html_all.push('<span>（1,3,5...79）<br />（奇&gt;偶）</span>');
						html_all.push('</a>');
						html_all.push('<a class="ball-number ball-number-k8 huo" data-param="action=ball&value=4&row=0">');
						html_all.push('<strong>中</strong>');
						html_all.push('<span>（奇=偶）</span>');
						html_all.push('</a>');
						html_all.push('<a class="ball-number ball-number-k8 ou" data-param="action=ball&value=5&row=0">');
						html_all.push('<strong>下</strong>');
						html_all.push('<span>（2,4,6...80）<br />（奇&lt;偶）</span>');
						html_all.push('</a>');
					html_all.push('</li>');
					html_all.push('<li class="clearfix">');
						html_all.push('<div class="ball-title-k8">和值大小单双</div>');
						html_all.push('<a class="ball-number ball-number-k8 dadan" data-param="action=ball&value=6&row=0">');
						html_all.push('<strong>大·单</strong>');
						html_all.push('</a>');
						html_all.push('<a class="ball-number ball-number-k8 dashuang" data-param="action=ball&value=7&row=0">');
						html_all.push('<strong>大·双</strong>');
						html_all.push('</a>');
						html_all.push('<a class="ball-number ball-number-k8 xiaodan" data-param="action=ball&value=8&row=0">');
						html_all.push('<strong>小·单</strong>');
						html_all.push('</a>');
						html_all.push('<a class="ball-number ball-number-k8 xiaoshuang" data-param="action=ball&value=9&row=0">');
						html_all.push('<strong>小·双</strong>');
						html_all.push('</a>');
					html_all.push('</li>');	
				html_all.push('</ul>');	
			html_all.push('</div>');
		
	//继承GameMethod
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器实例上
	BJKL8[defConfig.name] = new Main();
		
})(phoenix, phoenix.GameMethod);

