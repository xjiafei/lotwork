
//追号区域
;(function(host, name, Event,undefined){
	var defConfig = {
			//父级选择器
			'parentDom' : 'body',
			//内容容器
			'html' : $('<div id="msg-tips" style="position:absolute; z-index:10; width: auto; height: 35px;padding: 0 10px;line-height:35px; background: #888" class=""></div>'),
			//触发变量标记名称
			'parameterName' : 'data-showtip',
			//显示位置
			'position':'right'
		},
	instance;
	
	var pros = {
		//初始化
		init: function(cfg){
			var me = this,
				cfg = me.defConfig;
			//html结构
			me.htmlTpl = cfg.html;
			//输出结果DOM
			$('body').append(me.htmlTpl);
			//标记名称
			me.parameterName = cfg.parameterName;
			//绑定事件
			me.bindEvents();
		},
		//绑定事件
		bindEvents: function(){
			var me = this,
				cfg = me.defConfig;

				$(cfg.parentDom).bind('mouseover', function(e){
					var el = $(e.target);
					me.showTips(el);
					me.el = el;
				})
		},
		//添加内容容器结构
		setParameterName : function(text){
			var me = this;
			me.parameterName = text;
		},
		//
		showright: function(el, referDom){
			var left = el.offset().left,
				top = el.offset().top;
			referDom.css({
				top: top,
				left: left + el.outerWidth()
			}).show()
		},
		showleft: function(el, referDom){
			var left = el.offset().left,
				top = el.offset().top;
			referDom.css({
				top: top,
				left: left - referDom.outerWidth()
			}).show()
		},
		showtop: function(el, referDom){
			var left = el.offset().left,
				top = el.offset().top;
			referDom.css({
				top: top - referDom.outerHeight(),
				left: left
			}).show()
		},
		showbottom: function(el, referDom){
			var left = el.offset().left,
				top = el.offset().top;
			referDom.css({
				top: top + referDom.outerHeight(),
				left: left
			}).show()
		},
		hide : function(){
			var me = this;
			me.htmlTpl.hide();
		},
		//显示提示
		showTips: function(el){
			var me = this,
				tpl = me.htmlTpl,
				cfg = me.defConfig,
				nameText = el.attr(me.parameterName);
			if(typeof nameText == 'undefined'){
				me.hide();
				return;
			};
			me.htmlTpl.html(nameText);
			me['show' + cfg['position']](el, me.htmlTpl);
		}
	}
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host[name] = Main;
	
})(phoenix, "Prompt",  phoenix.Event);










