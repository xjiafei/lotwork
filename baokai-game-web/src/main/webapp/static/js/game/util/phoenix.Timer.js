

//定时器辅助类，借助事件类的通知机制实现执行动作 
(function(host, name, Event, $, undefined){
	var defConfig = {
		//触发频率,单位毫秒
		time:500,
		//是否开启一个新定时器，开启新定时器相当于新产生一个实例
		//不开启则首先寻找已有的同频率的定时器并加入，如果没有则实例化一个
		isNew:false,
		//默认加入的要执行的函数
		fn:function(){}
	},
	cases = [];
	
	var pros = {
		init:function(cfg){
			var me = this;
			cases.push(me);
			
			me.time = cfg.time;
			me.addEvent('exelist', cfg.fn);
			
			me.execute();
		},
		add:function(fn){
			this.addEvent('exelist', fn);
		},
		remove:function(fn){
			this.removeEvent('exelist', fn);
		},
		execute:function(){
			var me = this;
			me.fireEvent('exelist');
			me._timer = setTimeout(function(){
				me.execute();
			}, me.time);
		},
		stop:function(){
			var me = this;
			me.remove();
			clearTimeout(me._timer);
		}
		
	};
	
	
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	
	//静态类
	host[name] = function(cfg){
		var len = cases.length,i = 0;
		if(cfg.isNew){
			return new Main(cfg);
		}else if(!!cfg && !!cfg.time && !!cfg.fn){
			for(;i < len; i++){
				if(cases[i].time == cfg.time){
					cases[i].add(cfg.fn);
					return cases[i];
				}
			}
			return new Main(cfg);
		}
	};

})(phoenix, "Timer", phoenix.Event, jQuery);







