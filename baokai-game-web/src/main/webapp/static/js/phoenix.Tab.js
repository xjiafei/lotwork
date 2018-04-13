

//Tab 类
(function(host, name, Event, $, undefined){
	var defConfig = {
		currClass:'current',
		currPanelClass:'panel-current',
		par:document,
		triggers:'.triggers li',
		panels:'.panel',
		eventType:'mouseenter',
		
		
		//初始
		isDefShow:true,
		index:0,
		isDefRandom:false,
		
		
		//延迟触发
		delay:150,
		
		
		//自动播放
		autoPlay:0,
		//鼠标划过是否停止自动切换
		autoPlayIsHoverStop:true,
		//每次切换的索引步长,可为负值
		autoPlayStep:1,
		
		
		//控制器
		controlStep:1,
		//触发控制器，自动播放暂停时间
		controlCancelAutoPlayTime:3000

	},
	
	
	//这里下面扩展部分，原则上应该使用继承来实现，为了不使继承层次过多，这里模拟插件机制
	//同时尽可能的将方法定义在局部作用域里,使用call调用，获得原型继承节约开销同样的效果，
	//另一方面也保护了这些私有方法和逻辑，仅对外暴露必要且健壮的方法
	
	
	//=============================================================
	//延迟触发
	//=============================================================
	delayEventMap = {mouseenter:'mouseleave',mouseover:'mouseout'},
	delayInit = function(cfg){
		var me = this;
		me.triggers.bind(cfg.eventType, me, function(e){
			var dom = this;
			delayCancel(e);
			var originalE = jQuery.extend(true, {}, e);
			me._delayTimer = setTimeout(function(){
				triggersHander.call(dom, originalE);
			},cfg.delay);
		});
		
		if(delayEventMap[cfg.eventType]){
			me.triggers.bind(delayEventMap[cfg.eventType], me, delayCancel);
		}
	},
	delayCancel = function(e){
		clearTimeout(e.data._delayTimer);
	},
	
	//=============================================================
	//自动播放
	//=============================================================
	autoPlayInit = function(cfg){
		var me = this;
		//对外控制接口
		me.autoPlayStart = autoPlayStart;
		me.autoPlayStop = autoPlayCancel;
		//提供重写
		me._autoPlayGetIndex = autoPlayGetIndex;
		//鼠标进入停止自动播放
		if(cfg.autoPlayIsHoverStop){
			me.triggers.bind("mouseenter", me, autoPlayCancel).bind("mouseleave", me, autoPlayStart);
			me.panels.bind("mouseenter", me, autoPlayCancel).bind("mouseleave", me, autoPlayStart);
			//手动触发也停止自动播放
			//mouseenter 事件已注册过
			if(cfg.eventType != "mouseenter"){
				me.triggers.bind(cfg.eventType, me, autoPlayCancel);
			}
		}
		
	},
	autoPlayStart = function(e){
		var me = e && e.data ? e.data : this, toEl = (e && e.relatedTarget) ? e.relatedTarget : false;
		if(toEl && ((me.triggers.index(toEl) != -1) || (me.panels.index(toEl) != -1))){
			return;
		}
		autoPlayFn.call(me);
		//console.log('start...');
	},
	autoPlayFn = function(){
		var me = this,cfg = me.defConfig;
		//autoPlayCancel.call(me);
		me._autoPlayTimer = setTimeout(function(){
			onSwitch.call(me, me._autoPlayGetIndex());
			autoPlayFn.call(me);
		}, cfg.autoPlay);
	},
	autoPlayCancel = function(e){
		var me = e && e.data ? e.data : this;
		clearTimeout(me._autoPlayTimer);
		//console.log('stop...');
	},
	autoPlayGetIndex = function(){
		var me = this,length = me.length, len = length - 1,i = me.index + me.defConfig.autoPlayStep;
		/**
		i = i > len ? (i+1)%length - 1 : i;
		i = i < 0 ? (i+1)%length - 1 : i;
		**/
		i = i > len ? 0 : i;
		i = i < 0 ? len : i;
		return me._index = i;
	},

	
	
	//=============================================================
	//辅助方法
	//=============================================================
	onSwitch = function(i){
		//console.log(i);
		var me = this,index = me.index;
		if(i === index && index != undefined){
			return;
		}
		
		me.index = index === undefined ? i : index;
		me._index = i;

		//me._index 在beforeSwitch中有可能会被修正
		me.fireEvent("beforeSwitch", me._index);
		me.fireEvent("onSwitch", me._index);
		me.fireEvent("afterSwitch", me._index);
	},
	//trigger监听事件
	triggersHander = function(e){
		var me = e.data,i = me.triggers.index(this);
		onSwitch.call(me, me._index = i < 0 ? 0 : i);
	},
	setIndex = function(e, i){
		var me = this;
		return me.index = me.getTriggerIndex();
	},
	show = function(e, i){
		this.show(i, this.index);
	};
	
	var pros = {
		init:function(cfg){
			var me = this;
			me.par = $(cfg.par);
			me.triggers = $(cfg.triggers, me.par);
			me.panels = $(cfg.panels, me.par);
			me.length = Math.max(me.triggers.length, me.panels.length);
			//jquery元素缓存
			me._cache = {triggers:{},panels:{}};
			//第一次不设置index属性
			//me.index = undefined;
			//
			//插件处理
			//=================================
			//默认显示,推迟到实例准备就绪后执行
			if(cfg.isDefShow){
				me._inits.push(function(cfg){
					var me = this, i = cfg.isDefRandom ? parseInt(Math.random()*(me.length)) : cfg.index,cls = cfg.currClass,panel_cls = cfg.currPanelClass;
					//默认显示控制操作显示/隐藏,方便后端程序输出无须初始化
					me.getTrigger(i).addClass(cls);
					me.getPanel(i).addClass(panel_cls);
					//初始呈现
					onSwitch.call(me, i);
				});
			}


			//自动播放
			if(!!cfg.autoPlay && me.length > 1){
				//这里有一个一般情况下无关紧要的逻辑bug，自动播放实际上会在实例未初始化完成之前已经启动
				//但通常自动播放使用了定时器启动，使得自动播放的执行列队到实例化之后
				//如果自动播放的启动不使用定时器启动；或者出现严重阻塞的情况，在实例化完成之前插入了执行列队，会出现问题
				//autoPlayInit.call(me, cfg);
				
				//autoPlayInit不能加入延迟执行，因为子类中有可能会来重写autoPlay定义的方法
				autoPlayInit.call(me, cfg);
				//使用延迟执行避免
				me._inits.push(function(){
					me.autoPlayStart();
				});
				
			}
			
			//延迟触发
			if(!!cfg.delay || delayEventMap[cfg.eventType]){
				delayInit.call(me, cfg);
			}else{
				//添加triggers事件
				me.triggers.bind(cfg.eventType, me, _triggersHander);
			}
			
			me.addEvent("onSwitch", show);
			//自动更新当前索引
			me.addEvent("afterSwitch", setIndex);

		},
		//提供重写方法
		//控制器索引限定规则
		controlGetAdjustIndex:function(i){
			var me = this,length = me.length, len = length - 1;
			i = i > len ? 0 : i;
			i = i < 0 ? len : i;
			return i;
		},
		//controlTo 参数为负值有bug
		//优化标记
		controlTo:function(i){
			var me = this,len = me.length,mn = i > 0 ? 1 : -1,cfg = me.defConfig;
			
			//手动操作时，暂停自动播放一段时间
			if(!!cfg.autoPlay && !!cfg.controlCancelAutoPlayTime){
				me.autoPlayStop();
				clearTimeout(me._controlCancelAutoPlayTimer);
				me._controlCancelAutoPlayTimer = setTimeout(function(){
					me.autoPlayStart();
				},cfg.controlCancelAutoPlayTime);
			}
			
			onSwitch.call(me, me.controlGetAdjustIndex(i));
		},
		controlPre:function(){
			var me = this;
			me.controlTo(me.index + Math.abs(me.defConfig.controlStep)*-1);
		},
		controlNext:function(){
			var me = this;
			me.controlTo(me.index + Math.abs(me.defConfig.controlStep));
		},
		show:function(i){
			var me = this, cls = me.defConfig.currClass,panelCls = me.defConfig.currPanelClass,ti = me.getTriggerIndex(),pi = me.getPanelIndex();
			me.getTrigger(me.index).removeClass(cls);
			me.getTrigger(ti).addClass(cls);
			me.getPanel(me.index).removeClass(panelCls);
			me.getPanel(pi).addClass(panelCls);
		},
		getTrigger:function(i){
			var me = this,cache = me._cache;
			return cache.triggers[i] || (cache.triggers[i] = me.triggers.eq(i));
		},
		getPanel:function(i){
			var me = this,cache = me._cache;
			return cache.panels[i] || (cache.panels[i] = me.panels.eq(i));
		},
		getTriggerIndex:function(i){
			return this._index;
		},
		getPanelIndex:function(i){
			return this._index;
		}
	
	};
	
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	host[name] = Main;
	
})(phoenix, "Tab", phoenix.Event, jQuery);










