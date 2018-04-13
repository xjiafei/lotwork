
//Drag 拖拽类
(function(host, name, Event, undefined){
		  
	var defConfig = {
		//需要拖拽的dom
		dragDom: '',
		//是否显示模拟拖动框
		showSimBox: true,
		//参照容器
		//将在该DOM区域内进行拖拽
		containerBox: 'body'
	};
	
	var pros = {
		init: function(config){
			var me = this;

			//统一数据缓存存放对象
			this.currentParam = {
				dom: '',
				top: 0,
				left: 0,
				mouseTop: 0,
				mouseLeft: 0
			}

			//绑定拖拽事件元素
			me._bindEvent();
		},
		//设置拖拽DOM原始位置参数
		_setOriginMousePosition: function(data){
			var me = this,
				y = data.y || 0,
				x = data.x || 0;

			this.currentParam['mouseTop'] = y;
			this.currentParam['mouseLeft'] = x;
		},
		//获取拖拽DOM原始位置参数
		_getOriginMousePosition: function(){
			return {
				x : me.currentParam['mouseTop'],
				y : me.currentParam['mouseLeft']
			}
		},
		//设置当前的拖拽DOM数据
		_setCurrentParam: function(data){
			var me = this,
				top = data.top || 0,
				left = data.left || 0;

			this.currentParam['top'] = top;
			this.currentParam['left'] = left;
		},
		//复位当前拖拽DOM缓存数据
		_resetCurrentParam: function(){
			var me = this;

			//复位参数
			me.currentParam['dom'] = '';
			me.currentParam['top'] = 0;
			me.currentParam['left'] = 0;
			me.currentParam['mouseTop'] = 0;
			me.currentParam['mouseLeft'] = 0;
		},
		//设置模拟拖动框状态
		setSimBoxStatus: function(status){
			this.defConfig.showSimBox = status;
		},
		//获取是否需要模拟拖动框状态
		getSimBoxStatus: function(){
			return this.defConfig.showSimBox;
		},
		//设置拖拽DOM元素
		setDragDom: function(domList){
			this.defConfig.dragDom = domList;
		},
		//获取拖拽DOM元素
		getDragDom: function(){
			return this.defConfig.dragDom;
		},
		//设置拖拽DOM元素
		setContainerBox: function(dom){
			this.defConfig.containerBox = dom;
		},
		//获取拖拽DOM元素
		getContainerBox: function(){
			return this.defConfig.containerBox;
		},
		//获取拖拽DOM元素
		removeContainerBox: function(){
			this.defConfig.containerBox = '';
		},
		//添加元素可拖拽标记
		addDragTag: function(dom){
			var me = this,
				currentDom = $(dom);

			currentDom.attr('data-drag', 'allow');
		},
		//添加元素可拖拽标记
		cancelDragTag: function(dom){
			var me = this,
				currentDom = $(dom);

			currentDom.attr('data-drag', 'ban');
		},
		//设置当前拖拽元素
		_setCurrentDragDom: function(dom){
			this.currentParam.dom = dom;
		},
		_cancelCurrentDragDom: function(dom){
			this.currentParam.dom = '';
		},
		//获取当前拖拽元素
		_getCurrentDragDom: function(dom){
			return this.currentParam.dom;
		},
		//获取当前拖拽DOM的高度宽度
		_getCurrentDomOffset: function(Dom){
			var me = this;

			return me._getCurrentDragDom().offset();
		},
		/*
		*阻止event对象的浏览器默认事件
		*并且阻止传播
		*/
		halt: function(e){
			e.preventDefault();
			e.stopPropagation();
		},
		//绑定元素鼠标事件
		_bindEvent: function(){
			var me = this;

			//绑定拖拽元素鼠标按下
			$(document).on('mousedown', me.getDragDom(), function(e){
				//选择拖拽DOM
				me.fireEvent('Selected');
				//设置当前拖拽DOM
				me._setCurrentDragDom($(e.target));
				//绑定当前DOM的mouseover事件
				me._addDragEvent(e.target);
				//记录当前的鼠标位置
				me._setOriginMousePosition({
					x : me._getMousePosition(e).x,
					y : me._getMousePosition(e).y
				})
			});

			//绑定拖拽元素鼠标放开
			$(document).on('mouseup', me.getDragDom(), function(e){
				//选择拖拽DOM
				me.fireEvent('Drop');
				//清空当前拖拽DOM存储
				me._cancelCurrentDragDom();
				//解绑当前DOM的mouseover事件
				//方便优化组件减少事件占用内存
				me._cancelDragEvent(e.target);
				//复位相关数据记录
				me.reset();
			});
			
		},
		//获取当前鼠标位置
		_getMousePosition: function(e){
			var xx = e.originalEvent.x || e.originalEvent.layerX || 0,
				yy = e.originalEvent.y || e.originalEvent.layerY || 0; 

			return {
				x : xx,
				y : yy
			}
		},
		//给指定元素添加拖拽事件
		_addDragEvent: function(dom){
			var me = this,
				currentDom = $(dom);

			//绑定拖拽事件
			currentDom.bind('mousemove.drag', function(e){
				//阻止浏览器默认事件
				me.halt(e);
				//添加可拖拽标记
				me._DraggingDom(e.target);
			});
		},
		//取消元素拖拽事件
		_cancelDragEvent: function(dom){
			var me = this,
				currentDom = $(dom);

			//绑定拖拽事件
			currentDom.unbind('mousemove.drag');
		},
		//允许拖拽状态
		_isAllowDrag: function(dom){
			var me = this,
				currentDom = dom,
				status = currentDom.attr('data-drag');

			if(status == 'ban'){
				return false;
			}
			return true;
		},
		//拖动相关Dom
		_DraggingDom: function(dom){
			var me = this,
				currentDom = $(dom),
				mousePosition = me._getMousePosition(),
				left = me._getOriginMousePosition().x - me._getMousePosition().x,
				top = me._getOriginMousePosition().y - me._getMousePosition().y;

			//是否存在禁止拖动标记
			if(!me._isAllowDrag(currentDom)){
				return;
			}

			//是否允许使用模拟框
			if(me.getSimBoxStatus()){
				//模拟框拖动
				currentDom = me._addSimBoxDrag(currentDom);
			}

			//如果存在参照容器
			if(me.getContainerBox()){
				//超出参照物容器边界停止移动
				if(!me._isReferParame(currentDom)){
					return;
				}
			}

			//
			


		},
		//模拟框拖动
		_addSimBoxDrag: function(dom){
			var me = this,
				domTop = dom.offset().top,
				domLeft = dom.offset().left, 
				html = $('<div class="simBox" style="position:absolute;border-radius:5px;box-shadow:2px 2px 2px rgba(0,0,0,0.5)"></div>');

			//模拟框的CSS设置
			html.css({
				top: domTop,
				left: domLeft
			});

			//添加到页面
			$('body').append(html);

			return html;
		},
		//是否在参照物的范围之内
		_isReferParame: function(dom){
			var me = this,
				referDom = me.getContainerBox();

			//if()
		},
		//数据复位
		reset: function(){
			var me = this;

			//清空当前拖拽数据缓存
			me._resetCurrentParam();
		}
		
	};
	
	var Main = host.Class(pros, Event);
	host[name] = Main;
	
})(phoenix, "Drag", phoenix.Event);