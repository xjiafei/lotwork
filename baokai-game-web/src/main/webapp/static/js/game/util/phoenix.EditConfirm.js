

//页面离开时提示页面修改，让用户确认是否离开
(function(host, name, Event, $, undefined){
	var defConfig = {
		//是否为简单模式
		//简单模式将只监听change事件，发生任意的change时间将视为内容改变
		//复杂模式将对保存的值进行一一对比，暂时未实现
		isSimpleMode:true
	};
	
	var pros = {
		init:function(cfg){
			var me = this;
			
			me.isFlag = false,
			me.data = {};
	
			$(window).bind('beforeunload', me, me._bindfn);
			
			me.doEvent();
		},
		_bindfn:function(e){
			var me = e.data,isSimpleMode = me.defConfig.isSimpleMode;
			if(isSimpleMode){
				if(me.isFlag){
					return me.getConfirmText();
				}
			}else{
				if(me.checkChange()){
					return me.getConfirmText();
				}
			}
			
		},
		//返回提示内容文本
		getConfirmText:function(){
			return "\n\n\n您所做的更改尚未保存，是否确认离开该页面？\n\n\n";
		},
		doEvent:function(){
			var me = this,doms = me.getElements(),data = me.data;
			doms.on('change, input, keyup', 'input, select, textarea', function(e){
				me.isFlag = true;
			});
			doms.on('click', 'input[type="checkbox"], input[type="radio"]', function(e){
				me.isFlag = true;
			});
		},
		//返回需要监听值改变的对象列表
		getElements:function(){
			return $(document);
		},
		//对比前后的值是否有变化
		checkChange:function(){
			return false;
		},
		unbind:function(){
			var me = this;
			$(window).unbind('beforeunload', me._bindfn);
		}

	};
	
	
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	
	host[name] = Main;

})(phoenix, "EditConfirm", phoenix.Event, jQuery);







