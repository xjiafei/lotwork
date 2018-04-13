

//按列进行过滤的表格头组合
(function(host, name, Event, $, undefined){
	var defConfig = {
		//是否开启tab键
		isUseTab:true
	},
	doc = host.util.doc;
	
	var pros = {
		init:function(cfg){
			var me = this;
			me.focusIndex = -1;
			me.items = [];
			me.data = {};
			
			doc.keydown(function(e){
				if(me.defConfig.isUseTab && e.keyCode == 9){
					e.preventDefault();
					me.focusNext();
					//console.log('tab');
				}
				//快捷键
				if(e.ctrlKey){
					//不监听ctrl键自身
					if(e.keyCode != 17){
						me.focusByKeyCode(e);
						
					}
				}
			});
			
		},
		focusByKeyCode:function(e){
			var me = this,its = me.items,i = 0,len = its.length,tplArr = [],obj;
			for(;i < len;i++){
				if(its[i].defConfig.keyCode){
					tplArr = its[i].defConfig.keyCode.split('+');
					if(tplArr[0] == 'ctrl' && Number(tplArr[1]) == e.keyCode){
						if(its[i].isFocus){
							me.blur();
						}else{
							me.setFocusIndexByObj(its[i]);
							me.focus(me.focusIndex);
						}
						e.preventDefault();
						break;
					}
				}
			}
		},
		add:function(obj){
			/*var me = this,its = me.items;
			its.push(obj);*/
			var me = this,its = me.items,name=obj.defConfig.name;
			me.remove(name);
			its.push(obj);
		},
		remove:function(name){
			var me = this,its = me.items,i = 0,len = its.length,obj;
			for(;i < len;i++){
				if(its[i].defConfig.name == name){
					obj = its[i];
					its.splice(i, 1);
					break;
				}
			}
			return obj;
		},
		setFocusIndexByObj:function(obj){
			var me = this,its = me.items,len = its.length,i = 0;
			for(;i < len;i++){
				if(its[i] == obj){
					me.focusIndex = i;
					break;
				}
			}
		},
		focus:function(i){
			var me = this,its = me.items,len = its.length;
			if(i >= 0 && i < len){
				if(me.focusIndex >= 0){
					its[me.focusIndex].blur();
				}
				its[i].focus();
				me.focusIndex = i;
			}else{
				me.blur();
			}
		},
		//聚焦前一项
		focusPre:function(){
			var me = this;
			me.focus(me.focusIndex - 1);
		},
		//聚焦下一项
		focusNext:function(){
			var me = this;
			if(me.focusIndex >= 0){
				me.focus(me.focusIndex + 1);
			}else{
				me.focus(0);
			}
		},
		blur:function(){
			var me = this,i = 0,its = me.items,len = its.length;
			for(;i < len;i++){
				its[i].blur();
			}
			me.focusIndex = -1;
			$('body').focus();
			//console.log(1);
		},
		getFormData:function(){
			var me = this,i = 0,its = me.items,_dt,len = its.length,p,data = {};
			for(;i < len;i++){
				_dt = its[i].getFormValue();
				for(p in _dt){
					if(_dt.hasOwnProperty(p)){
						data[p] = _dt[p];
					}
				}
			}
			return data;
		},
		dataChange:function(){
			var me = this,data = me.getFormData(),p;
			for(p in data){
				if(data.hasOwnProperty(p)){
					if(typeof me.data[p] == 'undefined'){
						me.data = data;
						arguments.callee.call(me);
						me.fireEvent('dataChange');
					}else{
						if(me.data[p] != data[p]){
							me.data = data;
							me.fireEvent('dataChange');
							break;
						}
					}
				}
			}
			
		}
	};
	
	
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	host[name] = Main;

})(phoenix, "SuperSearchGroup", phoenix.Event, jQuery);







