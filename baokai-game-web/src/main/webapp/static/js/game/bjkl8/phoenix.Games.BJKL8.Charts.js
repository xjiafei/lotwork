

(function(host, name, Event, undefined){
	var defConfig = {
		//期数
		period : [30, 50, 100, 200],
		//基础参数
		Parameter : [],
		//最大刻度
		maxGradu : 0,
		//接近最大刻度的整数值
		maxIntGradu : 0,
		//平均刻度
		average : 0,
		//左侧测度分级
		graduLength : 5,
		//图表高度
		ChartHeight : 179,
		//图表宽度
		ChartWidth : 430,
		//统计组数
		dataGroupNum : 0,
		//当前游戏类型
		currentType : '',
		//后端数据接口地址
		url: '/gameTrend/getKl8ChartResult'
	},
	//数据缓存
	saveData = {},
	Games = host.Games,
	instance;

	var pros = {
		init: function(cfg){
			var me = this;

			//绑定change
			me.bindPerIod();
		},
		//获取参数
		getParamter: function(){
			return this.defConfig.Parameter;
		},
		//设置参数
		setParamter: function(data){
			//更新初始配置
			this.defConfig['Parameter'] = data;
			//计算相关配置
			this.mathNum();

			return this;
		},
		//获取最大值
		getGraduLength: function(){
			return this.defConfig.graduLength;
		},
		//设置最大值
		setGraduLength: function(data){
			this.defConfig.graduLength = data;
		},
		//获取最大值
		getMaxGradu: function(){
			return this.defConfig.maxGradu;
		},
		//设置最大值
		setMaxGradu: function(data){
			this.defConfig.maxGradu = data;
		},
		//获取平均值
		getAverage: function(){
			return this.defConfig.average;
		},
		//设置平均值
		setAverage: function(data){
			this.defConfig.average = data;
		},
		//获取接近最大值的整数
		getmaxIntGradu: function(){
			return this.defConfig.maxIntGradu;
		},
		//设置接近最大值的整数
		setmaxIntGradu: function(data){
			this.defConfig.maxIntGradu = data;
		},
		//获取数据数组
		getDataGroupNum: function(){
			return this.defConfig.dataGroupNum;
		},
		//设置数据数组
		setDataGroupNum: function(data){
			this.defConfig.dataGroupNum = data;
		},
		//获取图表高度
		getChartHeight: function(){
			return this.defConfig.ChartHeight;
		},
		//设置图表高度
		setChartHeight: function(num){
			this.defConfig.ChartHeight = num;
		},
		//获取图表宽度
		getChartWidth: function(){
			return this.defConfig.ChartWidth;
		},
		//设置图表宽度
		setChartWidth: function(num){
			this.defConfig.ChartWidth = num;
		},
		//获取期数信息
		getPeriod: function(){
			return this.defConfig.period;
		},
		//设置期数信息
		setPeriod: function(array){
			this.defConfig.period = array;
		},
		//获取期数信息
		getPostUrl: function(){
			return this.defConfig.url;
		},
		//设置期数信息
		setPostUrl: function(url){
			this.defConfig.url = url;
		},
		//获取期数信息
		getCurrentType: function(){
			return this.defConfig.currentType;
		},
		//设置期数信息
		setCurrentType: function(type){
			this.defConfig.currentType = type;
		},
		//检查当前页面是否存在实例
		checkExist: function(){
			var me = this,
				container = $('#calculate');

			//检查是否存在实例DOM结构
			if(container.size() == 0){
				return false;
			}else{
				return true;
			}
		},
		//获取参数中文名组合
		getDataListText: function(data){
			var me = this,
				nameText = [];

			for (var i = 0; i < data.length; i++) {
				nameText.push('<li class="group' + i + '">');
				for (var j = 0; j < data[i].length; j++) {
					nameText.push('<span>'+data[i][j][0]+'</span>');
				};
				nameText.push('</li>');
			};

			return nameText.join('');
		},
		//获取期数中文名组合
		getPeriodText: function(data){
			var me = this,
				nameText = '';

			for (var i = 0; i < data.length; i++) {
				if(i == 0){
					nameText += '<span class="chartsPerionButton current" data-period="'+data[i]+'">' + data[i] + '期</span>';
				}else{
					nameText += '<span class="chartsPerionButton" data-period="'+data[i]+'">' + data[i] + '期</span>';
				}
				
			};

			return nameText;
		},
		//获取参数颜色组合
		getDataGroupText: function(data){
			var me = this,
				saveArray = {},
				groupText = [];

			for (var i = 0; i < data.length; i++) {
				for (var j = 0; j < data[i].length; j++) {
					if(typeof saveArray[data[i][j][3]] == 'undefined'){
						saveArray[data[i][j][3]] = [data[i][j][0]];
					}else{
						saveArray[data[i][j][3]].push(data[i][j][0]);
					}
				};
			};

			for (var k in saveArray) {
				groupText.push('<li class="f'+k+'"><div class="color-box '+k+'"></div>');
				for (var l = 0; l < saveArray[k].length; l++) {
					var tag = saveArray[k].length == l + 1 ? '' : ',';
					groupText.push('<span>' + saveArray[k][l] + '</span>' + tag);
				};
				groupText.push('</li>');
			};

			return groupText.join('');
		},
		//获取最大值
		getMaxNum: function(data){
			var me = this,
				saveArray = [];

			for (var i = data.length - 1; i >= 0; i--) {
				for (var j = data[i].length - 1; j >= 0; j--) {
					saveArray.push(data[i][j][2]);
				};
			};

			return Math.max.apply(Math, saveArray);
		},
		//检测数据最接近的整数
		mathInteger: function(num){
			var me = this,
				i = 0,
				averageNum = 0,
				num = me.getMaxGradu();

			do{
				i += 10;
			}while(i < num);

			return i;
		},
		//检测刻度的平均值	
		mathAverage: function(){
			var me = this,
				maxNum = me.defConfig['maxIntGradu'];

			return maxNum / me.getGraduLength();		
		},
		//获取数据组平均宽度	
		mathAverageWidth: function(){
			var me = this,
				width = me.getChartWidth(),
				num = me.getDataGroupNum();
			
			return width / num;		
		},
		//计算单位高度
		mathUnitHeight: function(){
			var me = this;

			return me.getChartHeight()  / (me.getmaxIntGradu() + me.getAverage());
		},
		//计算数据
		mathNum: function(){
			var me = this,
				cfg = me.defConfig,
				data = me.getParamter();

			//最大刻度
			me.setMaxGradu(me.getMaxNum(data));
			//接近最大刻度的整数
			me.setmaxIntGradu(me.mathInteger());
			//刻度平均值
			me.setAverage(me.mathAverage());
			//获取数据组数
			me.setDataGroupNum(data.length);
		},
		//更新切换按钮DOM样式
		changeButtonUpdate: function(tag){
			var me = this,
				container = $('#calculate'),
				periodButton = container.find('.buttons');

			periodButton.find('span').removeClass('current');
			periodButton.find('span[data-period='+tag+']').addClass('current');
		},
		//装载数据
		//根据期数
		loadData: function(data, callback){
			var me = this,
				count = data['count'],
				type = data['type'],
				tag = '' + count + type, 
				period = me.getPeriod();

			//更新当前游戏TYPE
			me.setCurrentType(type);

			//更新切换按钮DOM
			me.changeButtonUpdate(count);

			if(saveData[tag]){
				//使用数据缓存
				if(me.checkExist()){
					me.setParamter(saveData[tag]).update();	
				}else{
					me.setParamter(saveData[tag]);
					try{
						//console.log('还没有实例化统计表组件！');
					}catch(e){

					}
				}
				if(callback){
					callback.call(me);
				}
				return;
			};

			$.ajax({
				url: me.getPostUrl(),
				type: 'get',
				dataType:'json',
				data: {'count': count, 'type' : type},
				success: function(r){

					//数据缓存
					if(typeof saveData[tag] == 'undefined'){
						saveData[tag] = r;
					};

					if(me.checkExist()){
						me.setParamter(r).update();	
					}else{
						me.setParamter(r);
						try{
							//console.log('还没有实例化统计表组件！');
						}catch(e){	

						}
					}

					if(callback){
						callback.call(me);
					}
				}
			})
		},
		//绑定用户切换期数
		bindPerIod: function(){
			var me = this,
				cfg = me.defConfig,
				container = $('#calculate'),
				periodArea = container.find('.button');

			$(document).on('click', '.chartsPerionButton', function(){
				var button = this;
				me.loadData({'count':$(this).attr('data-period'), 'type': me.getCurrentType()});
			})
		},
		//绑定获取数据
		//数据更新方法
		update: function(){
			var me = this,
				cfg = me.defConfig,
				data = cfg.Parameter,
				container = $('#calculate');
				databox = container.find('.databox'),
				graduation = container.find('.graduation li');

			//更新左侧刻度
			for (var i = me.getGraduLength() + 1; i >= 0 ; i--) {
				graduation.eq(me.getGraduLength() + 1- i).find('.graduNum').html(me.getAverage() * i);
			};	

			//更新右侧图标数据
			for (var i = data.length - 1; i >= 0; i--) {
				for (var j = data[i].length - 1; j >= 0; j--) {
					container.find('.' + data[i][j][1]).css('height', parseInt(me.mathUnitHeight() *  data[i][j][2]));
					container.find('.' + data[i][j][1] + ' .title').html(data[i][j][2]);
				};
			};	
		},
		//构建HTML
		getHtml: function(){
			var me = this,
				cfg = me.defConfig,
				data = cfg.Parameter,
				period = cfg.period,
				parentHtml = me.defConfig.htmlDom,
				groupNum = me.getDataGroupNum(),
				html = [];

				//父容器
				html.push('<div id="calculate">');
				//切换按钮
				html.push('<div class="buttons clearfix">' + me.getPeriodText(period) + '</div><div class="clearfix"><ul class="graduation">');
				//侧边刻度
				for (var i = me.getGraduLength() + 1; i >= 0 ; i--) {
					html.push('<li><span class="graduNum">' + me.getAverage() * i + '</span></li>');
				};
				html.push('</ul>');
				//右侧图表
				html.push('<ul class="result">');
				for (var i = 0; i < groupNum; i++) {
					html.push('<li class="dataGroup" style="width:' + me.mathAverageWidth() + 'px">');
					for (var j = 0; j < data[i].length; j++) {
						html.push('<div class="databox ' + data[i][j][1] + ' ' + data[i][j][3] +'" style="height:' + parseInt(me.mathUnitHeight() *  data[i][j][2]) +'px"><div class="title f'+ data[i][j][3]+'">' + data[i][j][2] + '</div></div>');
					};
					html.push('</li>');					
				};
				html.push('</ul></div>');
				html.push('<ul class="data-list-text clearfix">');
				html.push(me.getDataListText(data));
				html.push('</ul>');
				html.push('<ul class="data-group-text clearfix">');
				html.push(me.getDataGroupText(data));
				html.push('</ul>');
				html.push('</div>');

				return html.join('');
		},
		//销毁一个统计表的DOM
		destroy: function(){
			var example = $('#calculate');

			example.remove();
		}

	};
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
		//游戏控制单例
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host.Games.BJKL8[name] = Main;
	
})(phoenix, "Charts", phoenix.Event);





