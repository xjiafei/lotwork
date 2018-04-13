

//Hover 类
(function(host, name, Tab, $, undefined){
	var defConfig = {
		isDefShow:false,
		//hover的配置
		hoverDelayOut:0,
		//是否对panles也添加监听鼠标滑出事件，如果triggers和panels属于父子关系，则不需要监听panels
		hoverIsBindPanels:false
	},
	delayHander = function(e){
		var me = e.data,cfg = me.defConfig;
		clearTimeout(me._hoverDelayTimer);
		me._hoverDelayTimer = setTimeout(function(){
			me.hideHander(e);
		},cfg.hoverDelayOut);
	},
	clearDelayHander = function(e){
		var me = e.data;
		clearTimeout(me._hoverDelayTimer);
		//console.log('clear...');
	};
	
	var pros = {
		init:function(cfg){
			var me = this,fn = !!cfg.hoverDelayOut ? delayHander : me.hideHander;
			me.triggers.bind("mouseleave", me, fn);
			if(cfg.hoverIsBindPanels){
				me.panels.bind("mouseleave", me, fn);
			}
			if(!!cfg.hoverDelayOut){
				me.triggers.bind("mouseenter", me, clearDelayHander);
				if(cfg.hoverIsBindPanels){
					me.panels.bind("mouseenter", me, clearDelayHander);
				}
			}
			
		},
		hideHander:function(e){
			var me = e.data,toEl = e.relatedTarget,cls,cfg = me.defConfig;
			if(me.triggers.index(toEl) != -1 || me.panels.index(toEl) != -1){
				return;
			}
			if(cfg.hoverIsBindPanels && ($.contains(me.getTrigger(me.index).get(0),toEl) || $.contains(me.getPanel(me.index).get(0),toEl))){
				return;
			}
			me.hide();
		},
		hide:function(){
			var me = this,cls = me.defConfig.currClass,panelCls = me.defConfig.currPanelClass;
			me.getTrigger(me.index).removeClass(cls);
			me.getPanel(me.index).removeClass(panelCls);
			me.index = -1;
		}
		

	};

	
	var Main = host.Class(pros, Tab);
		Main.defConfig = defConfig;
	host[name] = Main;
	
})(phoenix, "Hover", phoenix.Tab, jQuery);










