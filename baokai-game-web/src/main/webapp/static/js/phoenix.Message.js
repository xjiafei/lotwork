
//消息类，继承自 MiniWindow
//扩展了按钮功能
;(function(host, name, Event,undefined){
	var defConfig = {
		
	},
	instance,
	closeTime = null;
	
	var pros = {
		//初始化
		init: function(cfg){
			var me = this;
			me.win = new host.MiniWindow({
				//实例化时追加的最外层样式名
				cls:'pop w-9'
			});
			me.mask = host.Mask.getInstance();
			//绑定隐藏完成事件
			me.reSet();
			me.win.addEvent('afterHide', function(){
				me.reSet();
			});
		},
		//彩种提示类型
		doAction: function(data){
			var me = this,
				funName = 'rebulid' + data['type'],
				getHtml = 'getHtml' + data['type'],
				fn = function(){
				};



			if(me[funName] && $.isFunction(me[funName])){
				fn = me[funName];
			}
			data['tpl']  = typeof data['tpl'] == 'undefined' ? me[getHtml]() : '' + data['tpl'];

			fn.call(me, data);
		},
		formatHtml:function(tpl, order){
			var me = this,o = order,p,reg;
			for(p in o){
				if(o.hasOwnProperty(p)){
					reg = RegExp('<#=' + p + '#>', 'g');
					tpl = tpl.replace(reg, o[p]);
				}
			}
			return tpl;
		},
		//检查数组存在某数
		arrIndexOf: function(value, arr){
		    var r = 0;
		    for(var s=0; s<arr.length; s++){
		        if(arr[s] == value){
		            r += 1;
		        }
		    }
		    return r || -1;
		},
		//添加题目
		setTitle: function(html){
			var me = this, win = me.win;
			win.setTitle(html);
		},
		//添加内容
		setContent: function(html, delay){
			var me = this, win = me.win;

			win.setContent(html, delay);
		},
		//隐藏关闭按钮
		hideClose: function(){
			var me = this, win = me.win;

			win.getCloseDom().hide();
		},
		//隐藏标题栏
		hideTitle: function(){
			var me = this, win = me.win;

			win.getTitleDom().hide();
		},
		//弹窗显示 具体参数说明
		//弹窗类型(会根据弹窗类型自动获取模版) type
		//模版 tpl  数据 tplData
		//内容:content, 绑定函数: callback, 是否遮罩: mask
		//宽度:width, 长度:height, 自动关闭时间单位S:time
		//是否显示头部: hideTitle, 是否显示关闭按钮:hideClose 
                //上方关闭按钮 事件: topCloseFun
		//确认按钮 是否显示: confirmIsShow 名称: confirmText 事件: confirmFun
		//取消按钮 是否显示: cancelIsShow  名称: cancelText	事件: cancelFun
		//关闭按钮 是否显示: closeIsShow   名称: closeText	事件: closeFun
		show: function(data){
			var me = this, win = me.win;
			data['tplData'] = typeof data['tplData'] == 'undefined' ? {} : data['tplData'];

			if(!data){return}

			if(data['type']){
				me.doAction(data);
				return;
			}else{
				if(typeof data['tpl'] != 'undefined'){
					data['content'] = me.formatHtml(data['tpl'], data['tplData']);
				}
			}

			//取消缓存关闭时间
			if(closeTime){
				clearTimeout(closeTime);
				closeTime = null;
			}
			//加入题目 && 内容
			me.setTitle(data['title'] || '提示');
			me.setContent(data['content'] || '');

			//按钮名称
			if(data['confirmText']){
				win.setConfirmName(data['confirmText']);
			}
			if(data['cancelText']){
				win.setCancelName(data['cancelText']);
			}
			if(data['closeText']){
				win.setCloseName(data['closeText']);
			}
			//按钮事件
			if(data['confirmFun']){
				me.win.doConfirm = data['confirmFun'];
			}
			if(data['cancelFun']){
				me.win.doCancel = data['cancelFun'];
			}
			if(data['closeFun']){
				me.win.doClose = data['closeFun'];
			}
			if(data['topCloseFun']){
				me.win.doTopClose = data['topCloseFun'];
			}
			//按钮显示
			if(data['confirmIsShow']){				
				win.showConfirmButton();
			}
			if(data['cancelIsShow']){
				win.showCancelButton();
			}
			if(data['closeIsShow']){
				win.showCloseButton();
			}


			//判断是否隐藏头部和关闭按钮
			if(data['hideTitle']){
				me.hideTitle();
			}
			if(data['hideClose']){
				me.hideClose();
			}
			//遮罩显示
			if(data['mask']){
				me.mask.show();
			}
			win.show();
			//执行回调事件
			if(data['callback']){
				data['callback'].call(me);
			}
			//定时关闭
			if(data['time']){
				closeTime = setTimeout(function(){
					me.hide();
					clearTimeout(closeTime);
					closeTime = null;
				}, data['time'] * 1000)
			}
		},
		//获取内容容器DOM
		getContentDom : function(){
			var me = this;
			return me.win.getContentDom();
		},
		//弹窗隐藏
		hide: function(){
			var me = this, win = me.win;

			win.hide();
			me.reSet();
		},
		//重置
		reSet: function(){
			var me = this, win = me.win;

			me.mask.hide();
			me.setTitle('提示');
			me.setContent('');
			win.hideConfirmButton();
			win.hideCancelButton();
			win.hideCloseButton();
			win.doConfirm = function(){};
			win.doCancel = function(){};
			win.doClose = function(){};
		}
	}
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host[name] = Main;
	
})(phoenix, "Message",  phoenix.Event);










