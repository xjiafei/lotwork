/**
 * countdown
 * @date    2013-07-18 10:32:57
 * #用法实例：
 * # new phoenix.countdown({
 * 		startTime : 2013/8/10,00:00:00,
 * 		endTime : 2013/8/10,00:00:00,
 * 		finishFun : function() {
 * 			this.endTime;
 * 		},
 * 		serverTimeurl : 'http://www.***.com/find',
 * 		ruleTime : 60,
 * 		shwoDom : '#timeshow'
 * })
 */
(function(host, name, Event, undefined){
	
	/*
	 * 默认配置
	 * 时间格式 : 2013/8/10,00:00:00
	 * 结束动作 : function
	 */
	var defConfig = {
		//开始时间
		'startTime' : '2013-1-1,00:00:00',
		//结束时间
		'endTime' : '2014-1-1,00:00:00',
		//定时器频率
		'frequency':1000,
		//结束提示
		'finishFun' : null,
		//当前启动计时矫正
		'isRedress': false,
		//矫正周期时间单位[秒]
		'redressTime': 10,
		//配置时间接口
		'redressUrl': './simulatedata/getnowtime.php',
		//是否
		'isLoop' : false,
		//输入时间的dom
		'showDom' : '#time'
	},
	//内部计时标记
	countNum = 0,
	//要加入定时处理的事件
	fixedEvents = [];

	var pros = {
		//初始化
		init : function() {
			var me = this;
			me.startTime = new Date(me.defConfig.startTime);
			me.endTime = new Date(me.defConfig.endTime);
			me.frequency = me.defConfig.frequency;
			
			//缓存时间读取请求
			me.timeload = null;
			
			//启动定时器
			me.continueCount();
		},
		getStartTime:function(){
			return this.startTime;
		},
		setStartTime:function(time){
			this.startTime = time;
		},
		getEndTime:function(){
			return this.endTime;
		},
		setEndTime:function(time){
			this.endTime = time;
		},
		getFrequency:function(){
			return this.frequency;
		},
		setFrequency:function(frequency){
			this.frequency = frequency;
		},
		getRedressUrl:function(){
			return this['defConfig']['redressUrl'];
		},
		setRedressUrl:function(url){
			this['defConfig']['redressUrl'] = url;
		},
		getRedressTime:function(){
			return this['defConfig']['redressTime'];
		},
		setRedressTime:function(time){
			this['defConfig']['redressTime'] = time;
		},
		//加入预处理文件
		//时间单位： s秒
		joinEvents : function(time, fn){
			fixedEvents.push([time, fn]);
		},
		//倒计时控制
		_countFun : function(now) {
			var that = this, time = {}, count = 0, thisTime = null, leftsecond,
				nowtime = that.getStartTime(),
				endTime = that.getEndTime(),
				ruleTime = this.defConfig.ruleTime;
			
			
			//时间计算定时器
			this.timeFun = setInterval(function() {
				
				//计算剩余时间
				leftsecond = thisTime || parseInt((endTime - nowtime)/1000);
				//缓存当前时间
				(thisTime == null) && (thisTime = leftsecond);
				//计时器累加
				countNum++;
				
				//格式化时间
				time.allSecond = leftsecond;
				time.w = parseInt(leftsecond/3600/24/7);
				time.d = parseInt(leftsecond/3600/24);
				time.h = parseInt((leftsecond/3600)%24);
				time.m = parseInt((leftsecond/60)%60);
				time.s = thisTime == 0 ? thisTime : parseInt(leftsecond%60);

				//执行定时队列事件
				that.doFixedEvents(countNum);
				
				//输出时间
				that._showTime(time);

				//是否需要矫正计时
				that['defConfig']['isRedress'] && (countNum%that.getRedressTime() == 0) && thisTime > that.getRedressTime() && that.redRessTime();

				//时间结束
				if(thisTime == 0) {
					//如果已经存在请求
					//还没有返回则中断
					that.timeload && that.timeload.abort();

					that._endCount();
					that.fireEvent('afterTimeFinish');
					return;
				};

				//缓存时间
				thisTime = thisTime != null ? thisTime - 1 : leftsecond - 1;	
				
			}, that.getFrequency());
		},
		//时间矫正函数
		redRessTime: function(){
			var me = this,
				timeMath = new Date().getTime();

			//如果已经存在请求
			//还没有返回则中断
			me.timeload  && me.timeload.abort();

			me.timeload = $.ajax({
				url: me.getRedressUrl(),
				type: 'GET',
				dataType: 'json'
			})
			.done(function(data) {
				
				if(Number(data['isSuccess']) == 1){
					//停止计时
					me.stopCount();
					//更新时间
					me.setStartTime(new Date(data['nowTime']).getTime() + (new Date().getTime() - timeMath));
					//恢复计时
					me.continueCount();
				}
				
			})
			.fail(function() {
				
			})
			.always(function() {
				
				me.timeload = null;
			});
			
		},
		//检查队列中需要执行的
		doFixedEvents : function(time){
			var me = this,i = 0;
			if(fixedEvents.length == 0){
				return;
			}
			//执行定时处理
			for(;i<fixedEvents.length;i++){
				if(time == fixedEvents[i][0]){
					fixedEvents[i][1].call(me);
					fixedEvents.splice(i, 1);
				}
			}
		},
		//时间显示
		_showTime : function(time) {
			var timeArea = $(this.defConfig.showDom),
				w = timeArea.find('.week'),
				d = timeArea.find('.day'),
				h = timeArea.find('.hour'),
				m = timeArea.find('.min'),
				s = timeArea.find('.sec');

				//渲染DOM
				w.text(time.w), d.text(time.d), h.text(time.h), m.text(time.m), s.text(time.s);
		},
		//缺位补零
		checkNum : function(num) {
			if(num < 10){
				return '0' + num;
			}
			return num;
		},
		//计时结束
		_endCount : function() {
			//停止
			this.stopCount();
			//清空内部计时
			countNum = 0;
			//执行回调
			this.defConfig.finishFun && this.defConfig.finishFun.call(this);
		},
		//停止计时
		stopCount : function() {
			clearInterval(this.timeFun);
			this.timeFun = null;
		},
		//恢复计时
		continueCount : function() {
			if(this.defConfig.serverTimeURl){
				this._serverTime();
			}else {
				this._countFun();
			}
		}
	},

	Main = host.Class(pros, Event);
	Main.defConfig = defConfig;
	
	host[name] = Main;
	
})(phoenix, "CountDown", phoenix.Event);
