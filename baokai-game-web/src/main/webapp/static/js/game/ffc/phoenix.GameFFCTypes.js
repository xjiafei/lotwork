

(function(host, name, Event, undefined){
	var defConfig = {
		//主面板dom
		mainPanel : '#J-play-select',
		//面板数据
		data : '',
		//面板dom
		mainDom : '.play-select',
		//html结构
		html : $('<div id="change"><ul class="play-select-title clearfix"></ul><ul class="play-select-content clearfix"></ul></div>'),
		//结果容器
		resultDom : '#change .play-select-content'
	},
	//渲染实例
	instance,
	//游戏实例
	Games = host.Games,
	//
	GameType = host.GameTypes;
	
	//渲染方法
	var pros = {
		init:function(cfg){
			var me = this;
			//缓存方法
			Games.setCurrentGameTypes(me);
			
			me.container = $(cfg.mainPanel);
			//计数
			me.count = 0;
			//渲染计数
			me.showCount = 0;
			//面板数据
			me.data = Games.getCurrentGame().getGameConfig().getInstance().getTypes();
			//面板DOM
			me.html = cfg.html;
		},
		//切换事件
		changeMode: function(modeName){
			var me = this,
				name = modeName.split('.'),
				container = me.getContainerDom(),
				cls = 'current',
				titles,
				panels,
				buttons,
				index = 0,
				currPanel;
			
			try{
				if(modeName == Games.getCurrentGame().getCurrentGameMethod().getGameMethodName()){
					return;
				}
			}catch(e){
			}

			if(container.find('.play-select-content').is(':hidden')){
				me.showTitleDom();
			}
			
			//执行自定义事件
			me.fireEvent('beforeChange', me.container, modeName);
			//执行切换
			Games.getCurrentGame().switchGameMethod(modeName);
			//执行高亮
			//文字名称
			var textName = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(modeName),
				textName = textName[(textName.length - 1)];

			titles = me.container.find('.play-select-title li');
			titles.removeClass(cls).find('span').remove();
			me.container.find('.play-select-title').find('.' + name[0]).addClass(cls).append('<span>['+textName+']</span>');
			
			panels = me.container.find('.play-select-content li');
			panels.removeClass(cls);
			currPanel = me.container.find('.play-select-content').find('.' + name[0]);
			currPanel.addClass(cls);
			
			buttons = currPanel.find('dd').removeClass(cls);
			currPanel.find('.' + name[1] + ' .' + name[2]).addClass(cls);
			
			
			titles.each(function(i){
				if($(this).hasClass(cls)){
					index = i;
					return false;
				}
			});
			me.bigTab.index = index;
			
			//alert(name[]);
			//执行自定义事件
			me.fireEvent('endChange');
		}
	};
	
	var Main = host.Class(pros, GameType);
		Main.defConfig = defConfig;
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host[name] = Main;
	
})(phoenix, "GameFFCTypes", phoenix.Event);










