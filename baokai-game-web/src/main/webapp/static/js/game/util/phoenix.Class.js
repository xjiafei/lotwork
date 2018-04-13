

//Class 类
//todo 考虑是否区分单例类,抽象类 ===================================
(function(host, name, $, undefined){
	var BEFOREINIT = "beforeInit",
		AFTERINIT = "afterInit";
	
	//构造新类时，新类构造器中的执行逻辑
	//@param {function} 新类
	//@param {object} 运行时的实例 
	//@return void
	var utilConstructor = function(Sub, cases, config){
		var config = config || {};
		if(Sub.superClass && Sub.superClass.defConfig){
			//获取父类的默认配置
			Sub.defConfig = $.extend({}, Sub.superClass.defConfig, Sub.defConfig);
		}
		//获取运行实例的config
		config = cases.defConfig = $.extend({}, Sub.defConfig, config);
		
		//从config.expands中获取覆盖实例的方法
		if(config['expands']){
			$.extend(cases, config['expands']);
		}
		
		//用实例的config去执行父类的构造器
		if(Sub.superClass){
			Sub.superClass.call(cases, config);
		}
		
		//执行子类的init函数
		if($.isFunction(Sub.prototype.init)){
			//确保是直隶父子关系才触发
			var isSubSuper = cases.constructor === Sub;
			isSubSuper && $.isFunction(config[BEFOREINIT]) && config[BEFOREINIT].call(cases, config);
			Sub.prototype.init.call(cases, config);
			isSubSuper && $.isFunction(config[AFTERINIT]) && config[AFTERINIT].call(cases, config);
			//实例的初始化列队
			//由于在父类的初始化过程中，有可能会率先调用子类或实例类的方法，这样就造成子类的init函数未被执行之前，就已经开始了主逻辑
			//因此这里添加一个初始化列队，该列队将在实例初始化(cases.init执行之后/也在config.afterInit之后)之后进行触发
			//这样将父类在实例将参数等全部准备就绪之后，才开始进行初始化的操作
			if(isSubSuper){
				for(var i = 0,len = cases._inits.length; i < len; i++){
					cases._inits[i].call(cases, cases.defConfig);
				}
			}

		}
		
		
	};
	
	//这种方式构造和继承出的类，构造器本身没有内容，内容初始化放到init函数中
	//自动调用父类的构造器|init，不必在子类中手动调用
	//从祖先类依次复制/覆盖默认配置对象
	//实例化时，从祖先类依次调用构造函器(空)和init函数，上下文为实例
	//实例化config对象中可切入beforeInit和afterInit方法
	//@param {object} 类的prototype方法集合
	//@param {function} 父类
	//@return {function} 新类
	//todo 增加支持对实例的扩展，如：pros, superClass ===========================================
	var Class = function(pros, superClass){
		//构造器函数
		var Main = function(config){
			var me = this;
			me._inits = [];
			utilConstructor(Main, me, config);
		};
		//不需要继承
		if(arguments.length < 2){
			Main.prototype = pros;
		}else{
			var Cons = function(){};
				Cons.prototype = superClass.prototype;
				Main.prototype = new Cons();
				$.extend(Main.prototype, pros);
				Main.superClass = superClass;
		}
		Main.prototype.constructor = Main;
		return Main;
	};
	host[name] = Class;
	
})(phoenix, "Class", jQuery);










