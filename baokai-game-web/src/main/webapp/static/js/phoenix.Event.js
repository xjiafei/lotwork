
//Event 类
//添加/删除/触发自定义方法
//自定义的event对象作为执行函数的第一个参数，fireEvent第二个以及以上的参数依次作为执行函数的参数
//执行函数返回明确的bool false，或者调用event.stopEvent()方法，则停止后续事件的执行

(function(host, name, undefined){
		  
	var stopEvent = function(){
		this._isStop = true;
	};
	
	var pros = {
		init:function(config){
			this._events = {};
		},
		addEvent:function(name, fn){
			if(!fn || Object.prototype.toString.call(fn) !== "[object Function]"){
				throw "Event.addEvent\u7B2C\u4E8C\u4E2A\u53C2\u6570\u5FC5\u987B\u662F\u51FD\u6570";
			}
			var me = this,_evs = me._events;
			_evs[name] = _evs[name] || [];
			_evs[name].push(fn);
		},
		removeEvent:function(name, fn){
			var me = this,_evs = me._events;
			if(!_evs[name]){
				return;
			}
			if(!fn){
				delete _evs[name];
				return;
			}
			var fns = _evs[name], i = fns.length;
			while(i){
				i--;
				if(fns[i] === fn){
					fns.splice(i, 1);
				}
			}
			if(!fns.length){
				delete _evs[name];
			}
		},
		fireEvent:function(name){
			var me = this,_evs = me._events;
			if(!_evs[name]){
				return;
			}
			var fns = _evs[name];
			if(fns._isStop){
				delete fns._isStop;
				return;
			}
			var i = 0, len = fns.length,
				ev = {type:name, data:me, stopEvent:stopEvent},
				args = [ev].concat(Array.prototype.slice.call(arguments, 1));
			for(var i = 0; i < len; i++){
				if(ev._isStop || fns[i].apply(me, args) === false){
					ev._isStop = false;
					return;	
				}
			}
		},
		stopEvent:function(name){
			var me = this,_evs = me._events;
			if(!_evs[name]){
				return;
			}
			_evs[name]._isStop = true;
		}
		
	};
	
	
	
	var Main = host.Class(pros);
	host[name] = Main;
	
})(phoenix, "Event");










