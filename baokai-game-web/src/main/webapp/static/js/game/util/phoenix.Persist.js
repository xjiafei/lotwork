;(function($, undefined) {
	//组件命名
	phoenix.Persist = function(setting) {

	//将函数指针重新命名
	//方便语义化程序逻辑
	var defaults = this,
		space = /\s+/,
		spaceAll = /\s/g,
		spaceBefore = /^.*\s/,
		spaceAfter = /\s.*$/,
		eventsMode = /^.*:|\s/g,
		dataRoute = /:.*$/

	/**
	 *[model 数据模型]
	 *@return {[type]}
	 */
	var model = Persist.model = function() {
		
		//[私有属性]数据模型存储
		var globalData = {};

		/**
		 * [add 添加&&修改数据]
		 * @param {[string]} name [要增加的数据名称]
		 * @param {[object || string || num]} data [要增加的数据]
		 * @param {[string]} type [参数的存储方式]
		 * @param {[mode]} string [是否为绑定函数]
		 * @param {[function]} callback [回调函数]
		 */
		var _modify = function(name, data, type, mode, callback ){
			var route, routes, gather,
				model = globalData,
				shortName = name.replace(spaceAll,''),
				name = $.trim(name),
				changeData;

			//检验数据名称
			if (!name && typeof name != 'string') {
				return;
			}

			//校验事件绑定
			if (data && mode) {
				defaults.m.set('bindMethod ' + name.replace(/\s/g,''), data);
				return;
			}

			/**
			 * [数据更改作为回调函数]
			 */
			changeData = function() {
				//判断是否多级路径
				if(space.test(name)) {
					//分隔数据
					routes = name.split(space);
					//遍历数据格式
					for(var i = 0, l = routes.length, current; i < l; i++){ 
						current = routes[i];
						//检查数据类型
						if(typeof model[current] === 'undefined') {
							model[current] = {};
						}
						//多层数据底层
						if (i == l - 1) {
							//检查是否存在数据
							if(typeof data!= 'undefined'){
								
								/*
								 * 检查事件存档是否已经存在
								 * 如果存在则改变数据类型为 Array 类型
	 							 */
								if(type == 'inset' && !($.isEmptyObject(model[current])) || type == 'inset' && typeof model[current] == 'function'){
									//检查数据是否是Array
									//已经存在数据则执行push操作加入事件
									if(model[current].constructor != Array){
										model[current] = [model[current]];	
									}
									//追加事件
									model[current].push(data);
								}else{
									model[current] = data;
								}
							}
						}
						//存储数据
						model = model[current];
					}
				} else {
					//单层数据
					if (typeof data != 'undefined') {
						//插入数据
						insetData(name, data, type)
					} else {
						//存储数据
						globalData[name] = {};
					}
				}
				//执行回调
				if (callback) {
					callback();
				}	
			}

			/**
			 * 触发参数change事件
			 * 查看数据模型中是否存在连锁事件并触发
			 */
			if(data && typeof defaults.m.get('bindMethod ' + shortName + 'change') != 'undefined') {
				//执行绑定事件
				defaults.c.triggerEvent(shortName + 'change', [defaults.m.get(name), data], changeData);
			}else{
				changeData();
			}
		};

		//单层路径inset数据
		var insetData = function(name, data, type){
			if(type == 'inset' && !($.isEmptyObject(globalData[name]))){
				if(globalData[name].constructor != Array){
					globalData[name] = [globalData[name]];	
				}
				globalData[name].push(data);
			}else{
				globalData[name] = data;
			}
		};

		//获取数据
		var _getMethod = function(name, callback){
			var routes = name.split(space),
				model = globalData;
			
			for(var i = 0, l = routes.length, current; i < l; i++){ 
				current = routes[i];
				if(typeof model[current] === 'undefined') {
					//methodGather.tip(name + '对象不存在请检查')
					return;
				}
				model = model[current];
			}
			//执行回调
			if (callback) {
				callback();
			}
			return model;
		};

		//删除数据方法
		var _deleteData = function(name, callback) {
			var routes = name.split(space),
				model = globalData;

			for(var i = 0, l = routes.length, current; i < l; i++){ 
				current = routes[i];
				if(typeof model[current] === 'undefined') {
					methodGather.tip('删除对象不存在请检查' + name)
					return;
				}
				if (i == l - 1) {
					try{
						delete model[current];	
					} catch(e){
						model[current] = undefined;
					}
				}
				model = model[current];
			}
			//执行回调
			if (callback) {
				callback();
			}
		};

		//向外部暴露model层方法
		return {
			/**
			 * [add 添加&&修改数据]
			 *详细参数参见modify方法
			 */
			add : function(name, callback) {

				//增加
				_modify(name, callback);
			},

			/**
			 *[创建]修改数据的一个键值
			 *详细参数参见modify方法
			 */
			set : function(name, data, type, mode, callback) {
				
				//修改方法类同于添加方法
				_modify(name, data, type, mode, callback);
			},

			/**
			 *获取数据
			 * @param  {[string]} name  [要获取的属性名称]
			 * @param  {[function]} callback [回调函数]
			 */
			get : function(name, callback) {
			
				if(name){
					//单层路径直接返回对象
					return space.test(name) ?  _getMethod(name) : globalData[name];
				} else {
					//如果不指定属性名称
					//则返回当前的数据模型
					return globalData;
				}
			},

			//删除数据
			destroy : function(name, callback){
				//单层路径直接删除对象
				if(name){
					space.test(name) ?  _deleteData(name) : delete globalData[name];
				};
			},

			//清空数据
			remove : function(name, callback){
				//修改方法类同于添加方法
				_modify(name, {}, callback);
			}
		};
	};

	/**
	 * [controller 控制器]
	 * @return {[type]}
	 */
	var controller = Persist.controller = {
		
		//本地存储
		LS : storage,

		//路由选择
		routeChange : function (data, callback) {
			var href = window.location;
			var namedParam    = /:\w+/g;
			var splatParam    = /\*\w+/g;
			var escapeRegExp  = /[-[\]{}()+?.,\\^$|#\s]/g;
			
			if($.isEmptyObject(data)) return;

			//执行当前路由匹配事件
			for(var i in data){
				if(!methodGather.isRegExp(i))
				if(i.test(href)){
					defaults.route[data[i]]();
				}
			}
			if(callback) {
				callback();
			}
		},

		//插件选择
		pluginLoad : function (data, callback) {
			//必须含有异步插件
			if(typeof $.use == 'undefined' || $.isEmptyObject(data)) return;
			//执行当前路由匹配事件
			for(var i in data){
				$.use(data[i]);
			}
			if(callback) {
				callback();
			}
		},

		/**
		 * 绑定事件修改机制
		 * @param  {[string]} eventName  [要绑定的事件名称]
		 * @param  {[string]} route [要获取的属性路径]
		 */
		bindEvent : function (eventName, fun) {
			var dataName = eventName && eventName.indexOf('change') != -1 ?  eventName.replace(eventsMode, '') : '',
				eventMode = eventName.replace(dataRoute, ' ');

			//没有数据提示
			if(!eventName || !fun || typeof dataName != 'string' || typeof fun != 'function') {
				methodGather.tip('请检查参数或检查事件名称写法:change:'+ eventName); 
				return false;
			}

			//绑定change事件
			defaults.m.set(eventMode + dataName, fun, 'inset', true);
		},

		/**
		 * 绑定事件修改机制
		 * @param  {[string]} eventName  [要解除绑定的事件名称]
		 */
		unbindEvent: function(eventName, callback){
			var dataName = eventName && eventName.indexOf('change') != -1  ?  eventName.replace(eventsMode, '') : '',
				eventMode = eventName.replace(dataRoute, '');

			if(!eventName){
				methodGather.tip('请输入绑定事件名称' + eventName);
				return;
			}
			//如果是all则解除绑定全部元素
			if(eventName == 'all'){
				defaults.m.remove(eventMode);
				return;
			}

			//没有数据跳出
			if(typeof defaults.m.get('bindMethod ' + eventMode + dataName) == 'undefined') {
				methodGather.tip('当前数据模型没有绑定事件' + eventName); 
				return;
			}

			//销毁绑定事件
			defaults.m.destroy('bindMethod ' + eventMode + dataName, callback);
		},

		/**
		 * 绑定事件修改机制
		 * @param  {[string]} eventName  [要绑定的事件名称]
		 * @param  {[array]} data [要获取的路径]
		 * 
		 */
		triggerEvent: function(eventName, data, callback) {
			var dataName = eventName && eventName.indexOf('change') != -1  ?  eventName.replace(eventsMode, '') : '',
				eventMode = eventName.replace(dataRoute, ' ');

			//没有数据跳出
			if(!eventName) {
				methodGather.tip('请填写事件名称' + eventName); 
				return;
			}
			//执行绑定事件
			methodGather.executeEvents(defaults.m.get('bindMethod ' + eventName), data, callback);
		}
	};

	/**
	 * [view 视图控制]
	 * @return {[type]}
	 */
	var view = Persist.view = {
		
		//对应绑定事件
		events : {

		},

		/**
		 *模版引擎渲染
		 *@param {[object:json]} data [需要渲染的数据集合]
		 *@param {[string]} html [需要进行渲染的HTML片段]
		 */
		render: function(html, data) {
			return Mustache.render(html, data);
		},

		//注册view层事件
		loadViewEvent: function(data) {
			$.extend(true, this, data);
			//注册单次view绑定事件
			methodGather._bindEvents(data);
		}
	};

	/**
	 * [路由控制]
	 */
	var route = Persist.route = {
		//路由列表
		routes : {}
	};

	/**
	 * [插件控制]
	 */
	var plugin = Persist.plugin = {
		HTMLEngine : 'mustache'
	};

	var methodGather = Persist.methodGather = {
		//检查方法
		_extractParameters: function(route, fragment) {
     		return route.exec(fragment).slice(1);
    	},

    	isRegExp : function(obj) {
	    	return toString.call(obj) == '[object RegExp]';
		},

		//建立组件模型和方法
		bulidModel: function(){
			this.ejectionMethod();	//建立组件方法
			this.bulidData();		//建立模型数据
		},

		//加载插件 && 加载路由事件
		executeSetting: function() {
			defaults.c.pluginLoad(defaults.plugin, defaults.c.routeChange(defaults.route.routes));
		},

		//建立组件依赖的数据模型
		bulidData: function() {
			defaults.m.add('bindMethod');	//建立BIND事件存储器
			defaults.m.add('route');		//建立路由事件存储器
		},

		//递归处理
		recursion: function(data, name){
			//判断对象是否含有该属性
			if (data.hasOwnProperty(name)) {
				return data[name]
			} else {
				for(var i in data){
					return this.recursion(data[i], name);
				}
			}
		},

		//提醒[开发者提示]
		tip: function(message){
			//输出提示
			if(console && typeof console.log == 'function'){
				console.log(message);
			}
		},

		//执行事件集合i
		executeEvents: function(eventsGather, data, callback){
			var that = {
				'current' : data[0],
				'change' : data[1]
			};

			if(typeof eventsGather == 'object' && eventsGather.constructor == Array){
				for(var i = 0, l = eventsGather.length; i < l; i++){
					if(eventsGather.call(that) === false){return}
					callback();
				}	
			} else {
				if(eventsGather.call(that) === false){return}
				return callback();
			}
		},

		//注册view事件
		_bindEvents : function(data){
			var domName,eventName;
			//绑定events注册事件
			for(var i in data.events){
				domName = i.replace(spaceBefore,'');
				eventName = i.replace(spaceAfter,'');
				$('body').delegate(domName, eventName, function() {
					view[data.events[i]]();
				});
			}		
		},

		//抛出组件个控制层的方法
		ejectionMethod: function() {
			defaults.m = Persist.model();		//数据层方法
			defaults.c = Persist.controller;	//控制器方法
			defaults.v = Persist.view;			//视图方法
			defaults.route = Persist.route;		//路由控制
			defaults.plugin = Persist.plugin;	//插件控制
		}
	}

	methodGather.bulidModel();

	//初始化
	this.init();
};

/**
 * 抛出对象原型方法
 * 只向外部提供初始化方法
 */
phoenix.Persist.prototype = {

	//初始化组件对象
	init: function() {
		Persist.methodGather.executeSetting();
	}
}
})(jQuery,undefined);