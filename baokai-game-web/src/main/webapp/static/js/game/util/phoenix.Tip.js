

//提示组件
(function(host, name, Event, $, undefined){
	var defConfig = {
		//外层容器class
		//四个箭头位置class，默认为向左箭头
		//j-ui-tip-t, j-ui-tip-r, j-ui-tip-b, j-ui-tip-l 
		//j-ui-tip-info 为绿色提示性样式
		cls:'j-ui-tip-l',
		//提示元素定位相对目标
		target:'body',
		//显示的文本
		text:'',
		//显示效果
		effectShow:function(){
			this.dom.show();
		},
		effectHide:function(){
			this.dom.hide();
		}
	},
	cls = 'j-ui-tip',
	zIndex = 500,
	//公共实例
	instance;
	
	var pros = {
		init:function(cfg){
			var me = this;
			me.dom = $('<div class="'+ cls +' '+ cfg.cls +'" style="display:none;position:absolute;left:0;top:0;z-index:'+ (zIndex++) +';"><i class="sj sj-t"></i><i class="sj sj-r"></i><i class="sj sj-b"></i><i class="sj sj-l"></i><span class="ui-tip-text">'+ cfg.text +'</span></div>').appendTo($('body'));
			me.effectShow = cfg.effectShow;
			me.effectHide = cfg.effectHide;
		},
		getTextContainer:function(){
			var me = this;
			return me._textContainer || (me._textContainer = me.dom.find('.ui-tip-text'));
		},
		getDom:function(){
			return this.dom;
		},
		setText:function(text){
			var me = this;
			me.getTextContainer().html(text);
		},
		show:function(x, y, target){
			var me = this,targetPos = (target == undefined ? $(me.defConfig.target) : $(target)).offset();
			me.dom.css({'left':targetPos.left + x,'top':targetPos.top + y});
			me.effectShow();
		},
		hide:function(){
			this.effectHide();
		},
		remove:function(){
			this.getDom().remove();
		}

	};

	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	host[name] = Main;
	
	host[name].getInstance = function(){
		return instance || (instance = new host[name]({cls:'j-ui-tip-l j-ui-tip-info'}));
	};

})(phoenix, "Tip", phoenix.Event, jQuery);








