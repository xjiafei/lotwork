//模拟下拉框组件
(function(host, name, Event, $, undefined) {
	var defConfig = {
		//最外层添加的class样式
		cls: 'ui-simulation-slide',
		//是否同时能输入
		isInput: false,
		//对应的真实select
		realDom: '',
		//模拟select模板
		tpl: '<div class="slide-checkbox"><div class="checklist"><#=loopItems#></div><div class="current-check-background"></div></div>',
		//单行元素模板
		itemTpl: '<a class="<#=style#>" data-value="<#=value#>" href="javascript:void(0);"><#=text#></a>'
	};

	var pros = {
		init: function(cfg) {
			var me = this;
			me.realDom = $(cfg.realDom);
			me.realDom.hide();
			me.dom = null;
			me.listDom = null;
			me.buildSelect();
		},
		buildSelect: function() {
			var me = this,
				cfg = me.defConfig,
				tpl = cfg.tpl,
				itemTpl = cfg.itemTpl,
				items = me.getRealDom().options,
				len = items.length,
				i = 0,
				itemStrArr = [],
				currValue = '',
				currStyle = '',
				currText = '';
			for (; i < len; i++) {
				if (i == me.getRealDom().selectedIndex) {
					currValue = items[i].value;
					currText = items[i].text;
					currStyle = 'current';
				} else {
					currStyle = '';
				}

				itemStrArr[i] = itemTpl.replace(/<#=style#>/g, currStyle).replace(/<#=value#>/g, items[i].value).replace(/<#=text#>/g, items[i].text);
			}
			tpl = tpl.replace(/<#=loopItems#>/g, itemStrArr.join(''));
			me.dom = $(tpl);
			me.dom.addClass(cfg.cls);
			me.dom.insertBefore(me.getRealDom());

			me.dom.click(function(e) {
				var el = e.target;
				//如果是选项点击
				if ( !! el.getAttribute('data-value')) {
					me.setValue(el.getAttribute('data-value'));
				}

				me.controlDomStyle(el);
			});

			me.setValue(currValue);
		},
		getListDom: function() {
			var me = this;
			return me.listDom || (me.listDom = me.dom.find('.checklist'));
		},
		getRealDom: function() {
			return this.realDom.get(0);
		},
		getBackgroundDom: function() {    
			return this.dom.find('.current-check-background');
		},
		controlDomStyle: function(el) {
			var me = this,
				currentBgDom = me.getBackgroundDom(),
				indexNum = $(el).index();


			me.getListDom().find('a').removeClass('current');
			$(el).addClass('current');	
			currentBgDom.css('left', indexNum * currentBgDom.width() + 3);
		},
		setValue: function(value) {
			var me = this,
				dom = me.getRealDom(),
				index = dom.selectedIndex,
				options = dom.options,
				len = options.length,
				i = 0,
				text = '';
			for (; i < len; i++) {
				if (value == options[i].value) {
					options[i].selected = true;
					text = options[i].text;
				} else {
					options[i].selected = false;
				}
			}
			text = text == '' ? value : text;
			me.fireEvent('change', value, text);
		},
		getValue: function() {
			var me = this,
				dom = me.getRealDom(),
				index = dom.selectedIndex;

			return dom.options[index].value;
		},
		getText: function() {
			var dom = this.getRealDom(),
				index = dom.selectedIndex;
			return dom.options[index].text;
		},
		show: function() {
			this.dom.show();
		},
		hide: function() {
			this.dom.hide();
		}

	};

	var Main = host.Class(pros, Event);
	Main.defConfig = defConfig;
	host[name] = Main;


})(phoenix, "SlideCheckBox", phoenix.Event, jQuery);