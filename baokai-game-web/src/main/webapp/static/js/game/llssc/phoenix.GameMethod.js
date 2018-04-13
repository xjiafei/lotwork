
//游戏方法类
//所有具体游戏实现应继承该类
(function(host, name, Event, undefined){
	var defConfig = {
		//玩法名称，必须是完整的名称
		//如：'wuxing.zhixuan.fushi'
		name:'',
		//父容器
		UIContainer: '#J-balls-main-panel',
		//球dom元素选择器
		ballsDom: '.ball-number',
		//球下方频率数dom选择器
		ballAidDom: '.ball-aid-hot',
		//选球高亮class
		ballCurrentCls:'ball-number-current',
		//玩法提示信息
		methodMassageDom:'.prompt .method-tip',
		//玩法提示信息
		methodExampleDom:'.prompt .example-tip',
		//冷热遗漏号获取地址
		hotColdUrl:'',
		//限制选求重复次数
		randomBetsNum: 500,
		//单式上传限号
		danshiLimitBall: 2000
	},
	Games = host.Games;
	
	var pros = {
		init:function(cfg){
			var me = this;
			//父容器
			me.UIContainer = $(cfg.UIContainer);
			//自身容器
			me.container = $('<div></div>').appendTo(me.UIContainer);
			me.buildUI();
	
			me.hide();
			
			//初始化数据结构
			me.balls = [];
			me.rebuildData();
			
			//所有选球dom
			me.ballsDom = me.getBallsDom();
			//选球下方辅助数字
			me.ballsAidDom = me.getBallsAidDom();
			//当前选球是否完整
			me.isBallsComplete = false;
			
			//由于玩法是异步延后加载并实例化，所以与其他组件的结合不能提取到外部
			//选球数据更改后触发动作
			me.addEvent('updateData', function(e, data){
				//更新统计
				var me = this,
					data = me.isBallsComplete ? data : {'lotterys':[],'original':[]};
				Games.getCurrentGameStatistics().updateData(data, me.getGameMethodName());
				//更新选球界面
				me.batchSetBallDom();
			});
			
			 //面板复位时执行批量选求状态清空
			me.addEvent('afterReset', function(){
				me.exeEvent_cancelCurrentButton();
			});
			//选球动作结束执行批量选求状态清空
			me.addEvent('afterSetBallData', function(){
				me.exeEvent_cancelCurrentButton();
			});
			
			
		},
		//获取选球dom元素，保存结构和选球数据(me.balls)一致
		getBallsDom:function(){
			var me = this,cfg = me.defConfig,dataMode = me.balls;
			if(dataMode.length < 1){
				return [];
			}
			return me.ballsDom || (function(){
				var balls = me.container.find(cfg.ballsDom),
					i = 0,
					len = balls.length,
					_row = [],
					result = [],
					cellnum = dataMode[0].length;
					for(;i < len;i++){
						_row.push(balls[i]);
						if((i+1)%cellnum == 0){
							result.push(_row);
							_row = [];
						}
					}
					return result;
			})();
		},
		//游戏类型切换后
		//游戏相关信息的更新方法
		updataGamesInfo: function(){
			var me = this,
				type = me.getGameMethodName(),
				currentGame = Games.getCurrentGame(),
				freCacheName = type + 'lostcurrentFre';				
				
			//url = ctx + '/gameBet/historyballs?type=' + type + '&extent=currentFre&line=5&lenth=30';
			var url = Games.getCurrentGame().getGameConfig().getInstance().getBetAwardUrl() + '?type=' + type + '&extent=currentFre&line=5&lenth=30';

			if(!Games.cacheData['gameBonus']){
				Games.cacheData['gameBonus'] = {};
			}
			if(!Games.cacheData['gameTips']){
				Games.cacheData['gameTips'] = {};
			}
			if(!Games.cacheData['frequency']){
				Games.cacheData['frequency'] = {};
			}
			if(!Games.cacheData['moreBouns']){
				Games.cacheData['moreBouns'] = {};
			}	
			if(!Games.currentIsMoreBouns['moreBouns']){
				Games.currentIsMoreBouns['moreBouns'] = {};
			}	
			//奖金组
			if(Games.cacheData['gameBonus'][url]){
				currentGame.addDynamicBonus(type ,Games.cacheData['gameBonus'][url]);
			}
			if(Games.cacheData['gameTips'][url]){
				me.methodTip(Games.cacheData['gameTips'][url]);
			}
			//冷热号缓存
			//缓存名称必须和手动加载的一致
			if(Games.cacheData['frequency'][freCacheName]){
				me.getHotCold(type, 'currentFre', 'lost', '当前遗漏');
			}
			//验证缓存
			//禁止异步请求数据
			if(Games.cacheData['gameBonus'][url] && Games.cacheData['frequency'][freCacheName] && Games.cacheData['gameTips'][url]){return};
			//获取游戏相关数据
			$.ajax({
				url: url,
				dataType: 'json',
				cache: false,
				success:function(result){
					if(Number(result['isSuccess']) == 1){
						data = result['data'];

						//游戏玩法提示
						if(typeof data['gameTips'] != 'undefined'){
							Games.cacheData['gameTips'][url] = data.gameTips;
							me.methodTip(data.gameTips);
						}
						//冷热号  richardgong 2015-08-26
						if(typeof data['frequency'] != 'undefined' && data['frequency'].length>0){
						//if(typeof data['frequency'] != 'undefined'){
							Games.cacheData['frequency'][freCacheName] = data['frequency'];
							me.getHotCold(type, 'currentFre', 'lost');
						}
						//奖金组
						if(data['gameTips'] && typeof data['gameTips']['actualBonus'] != 'undefined'){	
							Games.cacheData['gameBonus'][url] = data['gameTips']['actualBonus'];
							currentGame.addDynamicBonus(type, data['gameTips']['actualBonus']);
						}	
						//是否多奖金
						if(typeof(result.moreBouns) != 'undefined'){							
							Games.currentIsMoreBouns['moreBouns'][type] = result.moreBouns;							
						}
						
					}
				}
			})
		},
		//获取走势图
		getChart:function(GameMethodName, callback){
			var me = this, modeName = $.trim(GameMethodName);
				//var url = '/gameTrend/historyballs?type=' + modeName + '&extent=currentFre&line=5&lenth=30&lotteryid=99101';
				var url = Games.getCurrentGame().getGameConfig().getInstance().trendChartUrl() + modeName + '&extent=currentFre&line=5&lenth=30';
				if(typeof Games.cacheData['charts'] == 'undefined'){
					Games.cacheData['charts'] = {};
				}
				
				//缓存设置
				if(typeof Games.cacheData['charts'][modeName] != 'undefined'){
					
					if(callback){
						callback(Games.cacheData['charts'][modeName]);
					}
				}else{
					//获取游戏相关数据
					$.ajax({
						url: url,
						dataType: 'json',
						cache: false,
						success:function(result){
							if(result['isSuccess'] == 1){
								//添加走势图&&缓存
								result['data']['historyBalls'];
								Games.cacheData['charts'][modeName] = result['data']['historyBalls'];
								if(callback){
									callback(Games.cacheData['charts'][modeName]);
								}
							}else{
								try{
									console.log('服务器异常,请刷新页面重试。');
								}catch(e){

								}
							}
						}
					});
				}
				me.fireEvent('afterUpdataGamesInfo', GameMethodName);
		},
		//修改玩法提示方法
		methodTip: function(data){
			var me = this,
				cfg = me.defConfig;
			//玩法提示
			$(cfg.methodMassageDom).html(data.tips);
			//玩法实例
			$(cfg.methodExampleDom).html(data.example);
		},
		//获取冷热号码、遗漏数据
		//@param name  当前玩法类型
		//@param len   当前选择期数
		//@param type  当前频率类型: 遗漏 冷热
		//@param content   当前频率名称 中文
		getHotCold:function(name, len, type, content){
			var me = this,
				containerDom = me.container.find('.number-select-title'),
				containerDomcontent = me.container.find('.number-select-content');
				//缓存名称
				cacheName = name + type + len,				
				//种类样式
				lostClass = '.game-frequency-lost-length',
				//期数样式
				freClass = '.game-frequency-fre-length';
			//最大遗漏渲染时强行将type转为lost,	
		if(len == 'maxFre' || len == 'currentFre'){
			type = 'lost';
		}	
		var url = Games.getCurrentGame().getGameConfig().getInstance().getHotColdUrl() + '?gameMode=' + name + '&extent=' + len + '&frequencyType=' + type + '&line=5&lenth=30';
			if(type == 'lost'){
				$(freClass).hide();
				$(lostClass).show();
			}else{
				$(freClass).show();
				$(lostClass).hide();
			}			
			//渲染正确DOM按
			//因为初始化选号的时候需要加载冷热号所以将渲染dom放在这里				
			containerDom.find('a,li').removeClass('current');
			containerDom.find('.period' + len).addClass('current');
			containerDom.find('.' + type).addClass('current');
			containerDomcontent.find('.ball-title span').html(content);
			//缓存结果
			if(!Games.cacheData['frequency']){
				Games.cacheData['frequency'] = {};
			}
			if(Games.cacheData['frequency'][cacheName] ){
				me.reBuildHotCold(Games.cacheData['frequency'][cacheName], type);
			}else{
				$.get(url, function(r){
					me.reBuildHotCold(r, type);
					Games.cacheData['frequency'][cacheName] = r;
				},'json');
				
			}
		},
		//渲染冷热号码、遗漏
		//@parme r 	  后台汇集结果
		//@parme type 当前频率类型: 遗漏 冷热
		reBuildHotCold:function(r, type){
			var GameMethod = this,
				numSave = [], numList = [], minNums = 0, maxNums = 0,
				//冷热判断
				current;
				
			for(var i=0; i< r.length; i++){
				current = r[i];
				for(var name in current){
					var currentnum = current[name]['currentNum'],
						num = current[name]['pinlv'];
					numSave.push([num, currentnum]);
					numList.push(num);
					GameMethod.setBallAidData(i, currentnum, num);
				}
				minNums = Math.min.apply(Math, numList);
				maxNums = Math.max.apply(Math, numList);
				for(var j=0;j<numSave.length;j++){						
					
					if(type == 'lost'){ //遗漏值最大的用不同样式区分
						if(numSave[j][0] == maxNums){
							GameMethod.setBallAidData(i, numSave[j][1], numSave[j][0], 'ball-aid-hot');
						}
					}else{ //热号和冷号用不同样式区分
						if(numSave[j][0] == minNums){
							GameMethod.setBallAidData(i, numSave[j][1], numSave[j][0], 'ball-aid-cold');
						}
						//热
						if(numSave[j][0] == maxNums){
							GameMethod.setBallAidData(i, numSave[j][1], numSave[j][0], 'ball-aid-hot');
						}
					}
				}
				numSave = [], numList = [];
			}	
		},
		//初始化冷热/遗漏号等事件
		initHotColdEvent:function(){
			//当前期数
			var me = this,
			 	currentLen = 30,
				//当前号码
				currentType = 'lost',
				//种类样式
				typeClass = 'game-frequency-type',
				//种类样式
				lostClass = 'game-frequency-lost-length',
				//期数样式
				freClass = 'game-frequency-fre-length',
				//冷热号面板
				numMian = '.game-frequency-type li',
				//期数面板
				lenMain = '.game-frequency-lost-length a, .game-frequency-fre-length a';

			//冷热号切换			
			me['container'].on('click', numMian, function(){
				var domHTML,
					parent = $(this).parent(),
					typeName = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName();
				
				if($(this).hasClass('fre')){
					currentType = $(this).attr('data-type');
					$('.' + freClass).show();
					$('.' + lostClass).hide();
					currentLen = 30;
					domHTML = "30期";
				}else if($(this).hasClass('lost')){
					currentType =  $(this).attr('data-type');
					$('.' + freClass).hide();
					$('.' + lostClass).show();
					currentLen = 'currentFre';
					domHTML = "当前遗漏";
				}
				//选定冷热号
				Games.getCurrentGame().getCurrentGameMethod().getHotCold(typeName, currentLen, currentType, domHTML);
			})

			//期数选择
			me['container'].on('click', lenMain, function(){
				var parent = $(this).parent(),
					typeName = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
					domHTML = $(this).html(),
					currentLen = $(this).attr('data-type');
				//选定冷热号
				Games.getCurrentGame().getCurrentGameMethod().getHotCold(typeName, currentLen, currentType, domHTML);
			}).trigger('click');
			
		},
		//根据下注反选球
		reSelect:function(original){
			var me = this,
				type = me.getGameMethodName(),
				ball = original,
				i,
				len,
				j,
				len2,
				x,
				y,
				isFlag = false;
			
			me.reSet();
			
			for(i = 0,len = ball.length;i < len;i++){
				for(j = 0,len2 = ball[i].length;j < len2;j++){
					x = i;
					y = ball[i][j];
					me.setBallData(x, y, 1);
					isFlag = true;
				}
			}
			if(isFlag){
				me.updateData();
			}
		},
		//生成原始选球数据(不拆分成单注)
		//返回字符串形式的原始选球数字
		//在子类中实现/覆盖
		makePostParameter: function(original){
			var me = this,
				result = [],
				len = original.length,
				i = 0;
				
			for (; i < len; i++) {
				result = result.concat(original[i].join(''));
			}
			return result.join(',');
		},
		
		//检查数组存在某数
		arrIndexOf: function(value, arr){
		    var r = 0;
		    for(var s=0; s<arr.length; s++){
		        if(arr[s] == value){
		            r += 1;
		        }
		    }
		    return r || -1;
		},
		//重新构建选球数据
		//在子类中实现
		rebuildData:function(){
			
		},
		getBallData:function(){
			return this.balls;
		},
		//设置选球数据
		//x y value   x y 为选球数据二维数组的坐标 value 为-1 或1
		setBallData:function(x, y, value){
			var me = this,data = me.getBallData();
			if(x >= 0 && x < data.length && y >= 0 && y < data[0].length){
				data[x][y] = value;
			}
			me.fireEvent('afterSetBallData', x, y, value);
		},
		//设置遗漏冷热辅助
		//x y value   x y 为选球数据二维数组的坐标 value 为-1 或1
		//classname为冷热选球所需要的高亮效果
		setBallAidData:function(x, y, value, className){
			var me = this,
				currentName = 'ball-aid',
				data = me.getBallsAidDom(),
				className =  className ? currentName + ' ' + className : currentName;
			if(x >= 0 && x < data.length && y >= 0 && y < data[0].length){
				data[x][y].innerHTML = value;
				data[x][y].className = className;
			}
		},
		//获取冷热/遗漏号dom元素，保存结构和选球数据(me.balls)一致
		//用于显示冷热/遗漏号
		getBallsAidDom:function(){
			var me = this,cfg = me.defConfig,dataMode = me.balls;
			if(dataMode.length < 1){
				return [];
			}
			return me.ballsAidDom || (function(){
				var balls = me.container.find(cfg.ballAidDom),
					i = 0,
					len = balls.length,
					_row = [],
					result = [],
					cellnum = dataMode[0].length;
					for(;i < len;i++){
						_row.push(balls[i]);
						if((i+1)%cellnum == 0){
							result.push(_row);
							_row = [];
						}
					}
					return result;
			})();
		},
		//复位
		reSet:function(row){
			var me = this;
			me.isBallsComplete = false;
			me.rebuildData(row);
			me.updateData();
			me.fireEvent('afterReset');
		},
		//获取该玩法的名称
		getGameMethodName:function(){
			return this.defConfig.name;
		},
		//显示该游戏玩法
		show:function(){
			var me = this;
			me.fireEvent('beforeShow');
			me.container.show();
			me.fireEvent('afterShow');
		},
		//隐藏该游戏玩法
		hide:function(){
			var me = this;
			me.fireEvent('beforeHide');
			me.container.hide();
			me.fireEvent('afterHide');
		},
		//实现事件
		exeEvent:function(param, target){
			var me = this;
			if($.isFunction(me['exeEvent_' + param['action']])){
				me['exeEvent_' + param['action']].call(me, param, target);
			}
		},
		//批量选球事件
		exeEvent_batchSetBall:function(param, target){
			var me = this,
				ballsData = me.balls,
				x = Number(param['row']),
				bound = param['bound'],
				row = ballsData[x],
				i = 0,
				len = row.length,
				makearr = [],
				start = (typeof param['start'] == 'undefined') ? 0 : Number(param['start']);
				halfLen = Math.ceil((len - start)/2 + start),
				dom = $(target),
				i = start;
				
			//清空该行选球
			for(;i < len;i++){
				me.setBallData(x, i, -1);
			}
			
			switch(bound){
				case 'all':
					for(i = start;i < len;i++){
						me.setBallData(x, i, 1);
					}
					break;
				case 'big':
					for(i = halfLen;i < len;i++){
						me.setBallData(x, i, 1);
					}
					break;
				case 'small':
					//11选5时(大小选球有区别)
					var startNnum;
					if(Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"].indexOf('115')<0){
						
						startNnum = start;											
					}else{
						//趣味选球小
						if(me.defConfig['name'] == "quwei.normal.caizhongwei"){
							startNnum = start+2;
						}else{
							startNnum = start+1;		
						}			
					}
					
					for(i = startNnum;i < halfLen;i++){
						me.setBallData(x, i, 1);
					}
					break;
				case 'odd':
					for(i = start;i < len;i++){
						if((i+1)%2 != 1){
							me.setBallData(x, i, 1);
						}
					}
					break;
				case 'even':
					for(i = start;i < len;i++){
						if((i+1)%2 == 1){
							me.setBallData(x, i, 1);
						}
					}
					break;
				case 'none':
					
					break;
				default:
					break;
			}
			
			dom.addClass('current');
			me.updateData();
		},
		 //取消选球状态
		exeEvent_cancelCurrentButton: function(){
			var me = this,
			container = me.container,
			control = (typeof x != 'undefined') ? container.find('.ball-control').eq(x) : container.find('.ball-control');

			control.find('a').removeClass('current');
		}, 
		//选球事件
		//球参数 action=ball&value=2&row=0  表示动作为'选球'，球值为2，行为第1行(万位)
		//函数名称： exeEvent_动作名称
		exeEvent_ball:function(param, target){
			var me = this,el = $(target),currCls = me.defConfig.ballCurrentCls;
			//必要参数
			if(param['value'] != undefined && param['row'] != undefined){
				if(el.hasClass(currCls)){
					//取消选择
					me.setBallData(Number(param['row']), Number(param['value']), -1);
				}else{
					me.fireEvent('beforeSelect');
					//选择
					me.setBallData(Number(param['row']), Number(param['value']), 1);
				}
			}else{
				try{
					console.log('GameMethod.exeEvent_ball: lack param');
				}catch(ex){
				}
			}
			me.updateData();
		},
		//渲染球dom元素的对应状态
		batchSetBallDom:function(){
			var me = this,
				cfg = me.defConfig,
				cls = cfg.ballCurrentCls,
				balls = me.balls,
				i = 0,
				j = 0,
				len = balls.length,
				len2 = 0,
				ballsDom = me.getBallsDom(),
				_cls = '';
			//同步选球数据和选球dom
			//...
			for(;i < len;i++){
				len2 = balls[i].length;
				for(j = 0;j < len2;j++){
					if(balls[i][j] == 1){
						_cls = ballsDom[i][j].className;
						_cls = (' ' + _cls + ' ').replace(' '+cls, '');
						_cls += ' '+cls;
						ballsDom[i][j].className = _cls;
					}else{
						_cls = ballsDom[i][j].className;
						_cls = (' ' + _cls + ' ').replace(' '+cls, '');
						ballsDom[i][j].className = _cls;
					}
				}
			}
			
		},
		//当选球/取消发生，更新相关数据
		updateData:function(){
			var me = this,
				lotterys = me.getLottery();
				
			//通知其他模块更新
			me.fireEvent('updateData', {'lotterys':lotterys,'original':me.getOriginal()});
		},
		getOriginal:function(){
			var me = this,balls = me.getBallData(),
				len = balls.length,
				len2 = balls[0].length;
				i = 0,
				j = 0,
				row = [],
				result = [];
			for(;i < len;i++){
				row = [];
				for(j = 0;j < len2;j++){
					if(balls[i][j] > 0){
						row.push(j);
					}
				}
				result.push(row);
			}
			return result;
		},

		//获取总注数/获取组合结果
		//isGetNum=true 只获取数量，返回为数字
		//isGetNum=false 获取组合结果，返回结果为单注数组
		getLottery:function(isGetNum){
			var me = this,data = me.getBallData(),
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
					return [];
				}
				//计算注数
				total *= rowNum;
			}
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
		//单组去重处理
		removeSame: function(data) {
			var i = 0, result, me = this,
				numLen = this.getBallData()[0].length,
				len = data.length;
			result = Math.floor(Math.random() * numLen);
			for(;i<data.length;i++){
				if(result == data[i]){
					return arguments.callee.call(me, data);
				}
			}
			return result;
		},
		//移除一维数组的重复项
		removeArraySame:function(arr){
			var me = this,
				i = 0, 
				result,
				numLen = me.getBallData()[0].length,
				len = data.length;
				
			result = Math.floor(Math.random() * numLen);
			for(; i < arr.length; i++){
				if(result == arr[i]){
					return arguments.callee.call(me, arr);
				}
			}
			return result;
		},
		getRandomBetsNum: function(){
			return this.defConfig.randomBetsNum;
		},
		//生成单注随机数
		createRandomNum: function(){
			var me = this,
				current = [],
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
						var name = order[i]['original'].join('');
						hash[name] = name;
					}
				};
			}
			//对比结果
			if(hash[current.join('')]){
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
				currentNum, 
				ranNum,
				order = null,
				dataNum = me.getBallData(),
				name = me.defConfig.name,
				lotterys = [],
				original = [];
			
			current  = me.checkRandomBets();
			original = current;
			lotterys = me.combination(original);

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
		},
		//生成指定数目的随机投注号码，并添加进号码篮
		randomLotterys:function(num){
			var me = this,i = 0;
			Games.getCurrentGameOrder().cancelSelectOrder();
			for(;i < num; i++){
				Games.getCurrentGameOrder().add(me.randomNum());
			}
		},
		//游戏错误提示
		//主要用于进行单式投注错误提示
		//具体实现在子类中的单式投注玩法
		ballsErrorTip: function(){

		},
		//计算当前选中的球数量
		countBallsNum: function(){
			var me = this,
				num = 0,
				ball = me.getBallData();

			for (var i = ball.length - 1; i >= 0; i--) {
				if(Object.prototype.toString.call(ball[i]) == '[object Array]' && ball[i].length > 0){
					for (var j = ball[i].length - 1; j >= 0; j--) {
						if(ball[i][j] == 1){
							num++;
						};
					};
				}else{
					if(ball[i] == 1){
						num++;
					}
				}
			};

			return num;
		},
		//计算当前选中的球数量
		//限制计算某一单行内球数量
		countBallsNumInLine: function(lineNum){
			var me = this,
				num = 0,
				ball = me.getBallData();


			if(Object.prototype.toString.call(ball[lineNum]) == '[object Array]' && ball[lineNum].length > 0){
				for (var j = ball[lineNum].length - 1; j >= 0; j--) {
					if(ball[lineNum][j] == 1){
						num++;
					};
				};
			}else{
				if(ball[lineNum] == 1){
					num++;
				}
			}

			return num || -1;
		},
		//是否超出限制选球数量
		LimitMaxBalls: function(limitNum){
			var me = this,
				num = 0,
				ball = me.getBallData(),
				ballCount = Number(num);

			//当前选中的球数量
			num = me.countBallsNum();

			if(num > limitNum){
				return true;
			}else{
				return false;
			}
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
		
		//单行数组的排列组合
		//list 参与排列的数组
		//num 每组提取数量
		//last 递归中间变量
		combine:function(list, num, last){
			var result = [],i = 0;
			last = last || [];
			if (num == 0){
				return [last];
			}
			for (;i <= list.length - num; i++) {
				result = result.concat(arguments.callee(list.slice(i + 1), num - 1, last.slice(0).concat(list[i])));
			}
			return result;
		},
		//二维数组的排列组合
		//arr2 二维数组
		combination:function(arr2){
			if(arr2.length < 1){
				return [];
			}
			var w = arr2[0].length,
				h = arr2.length,
				i, j,
				m = [],
				n,
				result = [],
				_row = [];
				
			m[i = h] = 1;
			
			while (i--){
				m[i] = m[i + 1] * arr2[i].length;
			}	
			n = m[0];
			for (i = 0; i < n; i++){
				_row = [];
				for (j = 0; j < h; j++){
					_row[j] = arr2[j][~~(i % m[j] / m[j + 1])];
				}
				result[i] = _row;
			}
			return result;
		},

		//检查单式上传注数是否超限
		checkLimitBall: function(orderData){
			var me = this,
				limitNum = me['defConfig']['danshiLimitBall'],
				msg = Games.getCurrentGameMessage(),
				orderLength = Number(orderData['lotterys'].length) || 0;

			//单式限注
			if(orderLength > me['defConfig']['danshiLimitBall']){
				
				orderData['lotterys'] = [];
					msg.show({
					mask: true,
					content : ['<div class="bd text-center">',
									'<div class="pop-waring">',
										'<i class="ico-waring"></i>',
										'<div style="display:inline-block;*zoom:1;*display:inline;vertical-align:middle">最多支持' + limitNum + '注单式内容，请调整！</div>',
									'</div>',
								'</div>'].join(''),
					closeIsShow: true,
					closeFun: function(){
						this.hide();
					}
				});
			}			
		}
		
		
	};
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	host[name] = Main;
	
})(phoenix, "GameMethod", phoenix.Event);










