

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
	Games = host.Games;
	
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
			//执行渲染前事件
			//me.fireEvent('startShow');
			//执行渲染
			setTimeout(function() {
				me._showMainHTML(me.data);
			}, 0);
		},
		//获取外部容器DOM
		getContainerDom: function(){
			return this.container;
		},
		//数据解析
		_showMainHTML: function(data, inner) {		
			var me = this;
			for(var i = 0, c, l = data.length; i < l; i++) {
				c = data[i];
				if(!inner){
					//一级列表
					me._bulidHTMl(c, 'top');
				}
				if (Object.prototype.toString.call(c.childs) == '[object Array]' && c.childs.length != 0) {
					//二级列表
					me._showMainHTML(c.childs, true);
					me._bulidHTMl(c, 'child');
				} else {
					//末级列表
					me._bulidHTMl(c, 'terminal');
				}
			}
			//遍历结束输出html结果
			if (!inner) {
				me._appendHtml(me.html);
			}
		},
		//输出dom结构
		//@type 需要指定的层级
		_bulidHTMl: function(data, type) {
			var me = this,
				title = me.html.find('.play-select-title'),
				result = me.html.find('.play-select-content');

				switch(type){
					//一级
					case 'top':
					title.append('<li class="'+data.name+'">' + data.title + '</li>');
					result.append('<li class="'+data.name+'"></li>');
					break;
					//二级
					case 'child':
					result.find('.' + data.parent).append('<dl class="'+data.name+'"><dt>'+data.title+'：</dt></dl>');
					break;
					//末级
					case 'terminal':
					setTimeout(function(){
						result.find('.' +  data.mode + ' .' + data.parent).append('<dd class="'+data.name+'">'+data.title+'</dd>');
					},0);
					break;
				}
		},
		_appendHtml: function(html) {
			var me = this;
			$(me.defConfig.mainDom).prepend(html);
			//绑定TAB切换方法
			me._bindTagSelect();
			//定时器队列
			setTimeout(function(){
				me.fireEvent('endShow');
			},10);
		},
		_bindTagSelect: function() {
			var me = this,tab;
			tab = new phoenix.Tab({
				par : '#change',
				triggers : '.play-select-title > li',
				panels : '.play-select-content > li',
				eventType : 'click',
				currPanelClass: 'current'
			});
			me.bigTab = tab;
			
			tab.addEvent('afterSwitch', function(i, s) {
				var dom = this.getTrigger(s),
					name = $('#change .play-select-content .'+ dom.attr('class').replace(/\s.*/g, '') + ' dd:first');
				me._getMode(name);
			});
			$('#change .play-select-content').on('click', 'dd',function() {
				me._getMode($(this));
			});
		},
		_getMode: function(dom) {
			var me = this,
				name = dom.attr('class').replace(/\s.*/g, ''),
				modeName = dom.parent('dl').attr('class'),
				parName = dom.parents('li').eq(0).attr('class').replace(/\s.*/g, ''),
				full  = parName + '.' + modeName + '.' + name;

				me.changeMode(full);
		},
		showTitleDom: function(){
			var me = this,
				dom = me.getContainerDom(),
				methodListDom = dom.find('.play-select-content');

			methodListDom.show();
		},
		hiddenTitleDom: function(){
			var me = this,
				dom = me.getContainerDom(),
				methodListDom = dom.find('.play-select-content');

			methodListDom.hide();
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
				if(!!Games.getCurrentGame().getCurrentGameMethod() && modeName == Games.getCurrentGame().getCurrentGameMethod().getGameMethodName()){
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
			
			
			
			titles = me.container.find('.play-select-title li');
			titles.removeClass(cls);
			me.container.find('.play-select-title').find('.' + name[0]).addClass(cls);
			
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
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host[name] = Main;
	
})(phoenix, "GameTypes", phoenix.Event);










