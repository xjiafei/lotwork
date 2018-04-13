

//按列进行过滤的表格头
(function(host, name, Event, $, undefined){
	var index = 0,
		defConfig = {
		//类型：text:文本搜索类型  select:下拉选择类型  date:日期类型
		title:'',
		//题目限制字数
		titleLimit: 0,
		type:'text',
		//操作面板
		panel:'.sp-filter-cont',
		//聚焦时className
		focusCls:'sp-td-focus',
		//有值被选择时的className
		haveValueClass:'sp-td-havevalue',
		//默认是否展开
		isFoucs:false,
		//唯一标识
		name:'SuperSearch-' + index++,
		//显示隐藏面板class名
		showCls:'sp-filter-cont-show',
		hideCls:'sp-filter-cont-hide',
		//设置高亮标题模板
		//highLightTpl:'<span style="background:#FFFFE1;border:1px solid #CCC;padding:5px;"><#=title#></span>',
		highLightTpl:'<#=title#>',
		//高亮标题是否启用模板
		isAppTpl:true,
		//是否自动调节面板宽度与表头宽度一致，一般用于下拉类型面板
		isAutoWidth:false,
		//快捷键
		//keyCode:'ctrl+85',
		keyCode:false
	},
	doc = host.util.doc;
	
	var pros = {
		init:function(cfg){
			var me = this;
			me.isFocus = !!cfg.isFocus;
			me.name = cfg.name;
			me.dom = $(cfg.el);
			me.panel = me.dom.find(cfg.panel);
			me.title = me.getTitleDom().text();
			me.dom.bind('click', function(e){
				if(!$.contains(me.panel.get(0), e.target) && e.target != me.panel.get(0)){
					if(!me.isFocus){
						me.focus(e);
					}else{
						me.blur(e);
					}
				}
			});
			me.dom.find('.sp-filter-submit').bind('click', function(e){
				e.preventDefault();
				me.comfirm();
			});
			doc.keydown(function(e){
				if(me.isFocus && e.keyCode == 13){
					me.comfirm();
				}
			});
			doc.keydown(function(e){
				if(me.isFocus && e.keyCode == 46){
					if(cfg.type == 'select'){
						me.selectClear();
					}else{
						me.clear();
					}
					me.blur();
				}
			});
			me.group = cfg.group;
			if(me.group){
				me.addEvent('beforeFocus', function(){
					me.group.blur();
				});
				me.group.add(me);
			}
			
			me.addEvent('afterFocus', function(){
				var input = me.dom.find('input[type="text"]');
				if(input.size() > 0){
					input.get(0).focus();
				}
				
			});
			
			me.getCloseDom().click(function(e){
				e.preventDefault();
				e.stopPropagation();
				me.clear();
			});
			
			//下拉菜单形式
			if(cfg.type == 'select'){
				me.initSelect();
			}

			//多选复选形式
			if (cfg.type == 'multi') {
				me.initMulti();
			}
			
		},
		//下拉菜单形式涉及的方法
		initSelect:function(){
			var me = this,list = me.getSelectList(),lis = me.dom.find('li');
			list.click(function(e){
				e.preventDefault();
				me.select(this);
				me.showClose();
				me.comfirm();
			});

			doc.keydown(function(e){
				var index = -1,curr;
				if(e.keyCode > 36 && e.keyCode < 41){
					if(me.dom.hasClass('sp-td-focus')){
						curr = me.dom.find('.current');
						if(curr.size() > 0){
							index = lis.index(curr.get(0));
						}
						lis.removeClass('current');
						
						if(e.keyCode == 39 || e.keyCode == 40){
							index += 1;
						}else{
							index -= 1;
						}
						
						index = index > lis.length - 1 ? 0 : index;
						index = index < 0 ? lis.length - 1 : index;
						lis.eq(index).addClass('current');
					}
				}
			});
			
			me.addEvent('comfirm', function(){
				var curr = me.dom.find('.current');
				if(curr.size() > 0){
					me.getCloseDom().show();
					me.select(curr.find('a'));
				}
			})
			
		},
		getSelectList:function(){
			var me = this;
			return me._selectList || (me._selectList = me.dom.find('li a'));
		},
		select:function(it){
			var me = this,obj = $(it);
			me.setTitle(obj.text());
			obj.parent().parent().find('li').removeClass('current');
			obj.parent().addClass('current');
			me.dom.addClass(me.defConfig.haveValueClass);
		},
		selectClear:function(){
			var me = this,cls = me.getCloseDom();
			cls.hide();
			cls.parent().find('li').removeClass('current');
			me.comfirm();
			me.setTitle(me.title, true);
			me.dom.removeClass(me.defConfig.haveValueClass);
		},
		
		
		
		
		getCloseDom:function(){
			var me = this;
			return me._closeDom || (me._closeDom = me.dom.find('.sp-filter-close'));
		},
		showClose:function(){
			var me = this;
			me.getCloseDom().show();
		},
		hideClose:function(){
			var me = this;
			me.getCloseDom().hide();
		},
		getTitleDom:function(){
			var me = this;
			return me._titleDom || (me._titleDom = me.dom.find('.sp-td-title'));
		},
		setTitle:function(text, isClear){
			var me = this;

			//如果有字数限制
			if (me.defConfig.titleLimit && text.length > me.defConfig.titleLimit) {
				text = text.substr(0, me.defConfig.titleLimit) + '...';
			}
			
			if(me.defConfig.isAppTpl && !isClear){
				me.getTitleDom().html(me.defConfig.highLightTpl.replace(/<#=title#>/g, text));
			}else{
				me.getTitleDom().html(text);
			}
			
		},
		focus:function(e){
			var me = this;
			me.fireEvent('beforeFocus');
			me.dom.addClass(me.defConfig.focusCls);
			me.show();
			me.isFocus = true;
			if(me.group){
				me.group.setFocusIndexByObj(me);
			}
			me.fireEvent('afterFocus');
		},
		blur:function(e){
			var me = this;
			me.fireEvent('beforeBlur');
			me.dom.removeClass(me.defConfig.focusCls);
			me.hide();
			me.isFocus = false;
			me.fireEvent('afterBlur');
		},
		clear:function(){
			var me = this,cfg = me.defConfig,input = me.dom.find('input[type="text"]');
			input.val('');
			if(cfg.type == 'select'){
				me.selectClear();
			}
			if (cfg.type == 'multi') {
				me.multiClear();
			}
			me.dom.removeClass(me.defConfig.haveValueClass);
			me.setTitle(me.title, true);
			me.hideClose();
			me.dataChange();
		},
		show:function(){
			var me = this,cfg = me.defConfig;
			me.panel.removeClass(cfg.hideCls).addClass(cfg.showCls);
			if(cfg.isAutoWidth){
				me.panel.width(me.dom.outerWidth() - parseInt(me.panel.css('border')));
			}
			
		},
		hide:function(){
			var me = this,cfg = me.defConfig;
			me.panel.removeClass(cfg.showCls).addClass(cfg.hideCls);
		},
		comfirm:function(){
			var me = this;
			me.blur();
			me.setValue();
			me.fireEvent('comfirm');
			me.dataChange();
		},
		dataChange:function(){
			var me = this;
			if(me.group){
				me.group.dataChange();
			}
		},
		setValue:function(){
			var me = this,value = me.getValue();
			if(value != ''){
				me.setTitle(value);
				me.showClose();
				me.dom.addClass(me.defConfig.haveValueClass);
			}
		},
		getValue:function(){
			var me = this,input = me.dom.find('input[type="text"]');
			if(input.size() < 1){
				return '';
			}
			return input.val();
		},

		//返回表单值
		getFormValue:function(){
			var me = this,name = me.name,result = {};
			result[name] = me.getValue();
			return result;
		},

		//复选需要使用的方法
		initMulti: function() {
			var me = this,
				list = me.getSelectList(),
				lis = me.dom.find('li');

			list.click(function(e) {
				e.preventDefault();
				me.selectMulti(this);
				me.showClose();
				me.comfirm();
			});

			doc.keydown(function(e) {
				var index = -1,
					curr;
				if (e.keyCode > 36 && e.keyCode < 41) {
					if (me.dom.hasClass('sp-td-focus')) {
						curr = me.dom.find('.current');
						if (curr.size() > 0) {
							index = lis.index(curr.get(0));
						}
						lis.removeClass('current');

						if (e.keyCode == 39 || e.keyCode == 40) {
							index += 1;
						} else {
							index -= 1;
						}

						index = index > lis.length - 1 ? 0 : index;
						index = index < 0 ? lis.length - 1 : index;
						lis.eq(index).addClass('current');
					}
				}
			});

			me.addEvent('comfirm', function() {
				var curr = me.dom.find('.current');
				if (curr.size() > 0) {
					me.getCloseDom().show();
					me.selectMulti(curr.find('a'));
				}
			})

		},

		removeMulti: function(it) {
			var me = this;
				title = '',
				parent = $(it).parent();

			parent.removeClass();
			parent.find('.sp-filter-close').remove();
			title = $.trim(me.getMultiTitle(it));
			me.setTitle(title);
			me.panel.width(me.dom.outerWidth() - parseInt(me.panel.css('border')));

			if(title == ''){
				me.clear();
			}
		},

		getMultiTitle: function(it) {
			var me = this,
				name = [],
				obj = $(it).parent().parent().find('.current');

			$.each(obj, function(index, val) {
				name.push($(this).find('a').text());
			});

			return name.join(',');
		},

		selectMulti: function(it) {
			var me = this,
				$closeBtn = $('<span style="right: 8px;top: 13px; cursor:pointer" class="sp-filter-close"></span>'),
				obj = $(it),
				parent = obj.parent();

			parent.addClass('current');

			if (parent.css('position') != 'relative') {
				parent.css('position', 'relative');
			}

			if (parent.find('.sp-filter-close').size() <= 0) {
				parent.append($closeBtn);
				$closeBtn.click(function(e){
					me.removeMulti(it);
					e.stopPropagation();
				});
			}
				
			me.setTitle(me.getMultiTitle(it));
			me.dom.addClass(me.defConfig.haveValueClass);
		},
		
		multiClear: function() {
			var me = this,
				cls = me.getCloseDom();

			cls.hide();
			cls.parent().find('li').removeClass('current').find('.sp-filter-close').remove();
			me.comfirm();
			me.setTitle(me.title, true);
			me.dom.removeClass(me.defConfig.haveValueClass)
			me.dom.removeClass(me.defConfig.focusCls);
		}

	};
	
	
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	host[name] = Main;

})(phoenix, "SuperSearch", phoenix.Event, jQuery);







