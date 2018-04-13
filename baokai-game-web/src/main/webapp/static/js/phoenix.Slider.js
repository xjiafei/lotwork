

//Slider 类
//Slider类基本上只是在Tab的基础上增加一些特效和控制
//todo 支持 > 1 的step步长 =========================
//todo 支持 直接控制像素位置的切换 =========================
(function(host, name, Tab, $, undefined){
	var defConfig = {
		autoPlay:3000,
		isDefShow:true,
		
		//移动方向 top|left
		//配合正负值的 step 步长参数，可控制移动方向
		sliderDirection:'top',
		//移动距离,如果不设置，将自动获取第一个panel元素的高或宽
		//sliderDistance:200,
		//移动容器(panels的父容器),如果不设置，则自动获取第一个panel元素的父容器
		//sliderParent:'#panelPar'
		//是否为不间断滚动
		sliderIsCarousel:false,
		//动画时间
		sliderDuration:300,
		//缓动效果
		sliderEasing:"easeOutQuad",
		//动画执行完后的回调函数
		//sliderCallBack:function(){},
		
		
		//自动播放
		//每次切换的索引步长,可为负值
		autoPlayStep:1,
		//控制器
		controlStep:1
		
	},
	directionMap = {"left":"width","top":"height"},
	
	
	//=============================================================
	//不间断滚动
	//=============================================================
	carouselInit = function(cfg){
		var me = this;
		me.addEvent("beforeSwitch", carouselCheckIndex);
		//修改自动播放索引规则
		if(!!cfg.autoPlay){
			me._autoPlayGetIndex = carouselAutoPlayGetIndex;
		}
		//修改索引获取规则
		me.getTriggerIndex = carouselGetTriggerIndex;
		me.getPanelIndex = carouselGetPanelIndex;
		
		//修改控制器索引规则
		me.controlGetAdjustIndex = carouselControlGetAdjustIndex;
		me.controlPre = carouselControlPre;
		me.controlNext = carouselControlNext;
		
	},
	carouselControlPre = function(){
		var me = this;
		me.controlTo(me._index + Math.abs(me.defConfig.controlStep)*-1);
	},
	carouselControlNext = function(){
		var me = this;
		me.controlTo(me._index + Math.abs(me.defConfig.controlStep));
	},
	carouselControlGetAdjustIndex = function(i){
		return i;
	},
	carouselCheckIndex = function(e, i){
		var me = this,cfg = me.defConfig,length = me.length,len = length - 1,step = cfg.autoPlayStep,panel,ci = i + step,vi = me._index,
		dire = cfg.sliderDirection,
		dest = cfg.sliderDistance;
		
		if(dire == "top"){
			if(vi > len){
				if(vi > length){
					me.getPanel(0).css({position:'static'});
					me.sliderParent.css({top:'0'});
					vi = 1;
				}else{
					me.getPanel(0).css({position:'relative',top:length*dest});
				}
			}
			if(vi < 0){
				if(vi < -1){
					me.getPanel(len).css({position:'static'});
					me.sliderParent.css({top:dest*len*-1});
					vi = len - 1;
				}else{
					me.getPanel(len).css({position:'absolute',top:dest*-1});
				}
			}
			if(vi == 1 || vi == len){
				me.getPanel(0).css({position:'static'});
				me.getPanel(len).css({position:'static'});
			}
		}else{
			if(vi > len){
				if(vi > length){
					me.getPanel(0).css({position:'static'});
					me.sliderParent.css({left:'0'});
					vi = 1;
				}else{
					me.getPanel(0).css({position:'relative',left:length*cfg.sliderDistance});
				}
			}
			if(vi < 0){
				if(vi < -1){
					me.getPanel(len).css({position:'static'});
					me.sliderParent.css({left:dest*len*-1});
					vi = len - 1;
				}else{
					me.getPanel(len).css({position:'absolute',left:dest*-1});
				}
			}
			if(vi == 1 || vi == len){
				me.getPanel(0).css({position:'static'});
				me.getPanel(len).css({position:'static'});
			}
		}
		
		//console.log(vi);
		
		me._index = vi;
	},
	carouselAutoPlayGetIndex = function(){
		var me = this,length = me.length, len = length - 1,i = me._index + me.defConfig.autoPlayStep;
		me._index = i;
		return i;
	},
	carouselGetTriggerIndex = function(){
		var me = this,length = me.length, len = length - 1,i = me._index;
		i = i > len ? (i+1)%length - 1 : i;
		i = i < 0 ? (i+1)%length - 1 : i;
		return i;
	},
	carouselGetPanelIndex = function(){
		var me = this,length = me.length, len = length - 1,i = me._index;
		/**
		i = i > len ? (i+1)%length - 1 : i;
		i = i < 0 ? (i+1)%length - 1 : i;
		**/
		return i;
	};
	
	
	var pros = {
		init:function(cfg){
			var me = this,cfg = me.defConfig,widtheight = cfg.sliderDirection == "left" ? "width" : "height";
			me.sliderParent = (cfg.sliderParent ? $(cfg.sliderParent) : me.getPanel(0).parent());
			//设置足够的宽
			me.sliderParent.css(widtheight, 100000).css('position','relative');
			//优先使用手动设置的移动步长
			cfg.sliderDistance = cfg.sliderDistance || me.getPanel(0)[directionMap[cfg.sliderDirection]]();
			
			if(!!cfg.sliderIsCarousel){
				carouselInit.call(me, cfg);
			}
			
		},
		show:function(i){
			var me = this,cfg = me.defConfig,cls = cfg.currClass,step = cfg.sliderDistance,
			ti = me.getTriggerIndex(),pi = me.getPanelIndex(),
			dire = cfg.sliderDirection == "top" ? {"top":pi*step*-1} : {"left":pi*step*-1};
			
			me.sliderParent.animate(dire,{duration:cfg.sliderDuration,easing:cfg.sliderEasing});

			me.getTrigger(me.index).removeClass(cls);
			me.getTrigger(ti).addClass(cls);
		}
		
	};

	
	var Main = host.Class(pros, Tab);
		Main.defConfig = defConfig;
	host[name] = Main;
	
})(phoenix, "Slider", phoenix.Tab, jQuery);










