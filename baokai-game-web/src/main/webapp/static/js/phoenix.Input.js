

//Input简单包装
(function(host, name, Event, $, undefined){
	var defConfig = {
		el:null,
		//focus时增加样式
		focusClass:'input-focus',
		//设置一个默认文字，focus时消失
		defText:' '
	},
	focusHander = function(e){
		var me = e.data;
		me.fireEvent("beforeFocus");
		me.fireEvent("onFocus");
		me.fireEvent("afterFocus");
	},
	blurHander = function(e){
		var me = e.data;
		me.fireEvent("beforeBlur");
		me.fireEvent("onBlur");
		me.fireEvent("afterBlur");
	},
	clearDefText = function(e){
		var me = this,cfg = me.defConfig,v = $.trim(me.dom.val());
		if(v == cfg.defText){
			me.dom.val("");
		}
	},
	setDefText = function(){
		var me = this,cfg = me.defConfig,v = $.trim(me.dom.val());
		if(v == ""){
			me.dom.val(cfg.defText);
		}else{
		    addClass.call(me);
		}
	},
	addClass = function(e){
		this.dom.addClass(this.defConfig.focusClass);
	},
	removeClass = function(){
		this.dom.removeClass(this.defConfig.focusClass);
	};
	
	var pros = {
		init:function(cfg){
			var me = this,el;
			if(!cfg.el || $.trim(cfg.el) == ""){
				return;
			}
			me.dom  = $(cfg.el);
			me.dom.bind("focus", me, focusHander);
			me.dom.bind("blur", me, blurHander);

			if(cfg.focusClass){
				me.addEvent("onFocus", addClass);
				me.addEvent("onBlur", removeClass);
			}
			if(cfg.defText){
				me.dom.val(cfg.defText);
				me.addEvent("onFocus", clearDefText);
				me.addEvent("onBlur", setDefText);
			}

		},
		getDefText:function(){
			return this.defConfig.defText;
		},
		getValue:function(){
			return this.dom.val();
		}

	};
	
	
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	host[name] = Main;

})(phoenix, "Input", phoenix.Event, jQuery);







