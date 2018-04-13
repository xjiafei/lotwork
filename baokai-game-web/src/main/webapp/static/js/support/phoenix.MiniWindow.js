

//模拟弹出窗口
//内部已经绑定了在IE6下模拟fixed效果
;(function(host, name, Event, $, undefined){
	var util = host.util,
	defConfig = {
		//点击按钮
		confirmButtonText : 'confirm',
		//取消按钮
		cancelButtonText : 'cancel',
		//关闭按钮
		closeButtonText : 'closeTip',
		//实例化时追加的最外层样式名
		cls:'',
		//窗体内主内容html字符串
		contentHtml:'',
		//在主体内容生成之后，执行afterSetConent函数时的延迟时间，以确保内容生成完成或者其内部的资源加载完成
		//也可以再每次调用setContent的时候，设置延迟参数值
		afterSetContentDelay:0,
		effectShow:function(){
			var me = this;
			util.toViewCenter(me.dom);
			this.dom.show();
		},
		effectHide:function(){
			this.dom.hide();
		},
		zIndex:700,
		//是否使用fixed模式
		isFixed:true
	},
	doc = $(document),
	instance;
	
	var pros = {
		init:function(cfg){
			var me = this,position = cfg.isFixed ? 'fixed' : 'absolute';
			position = util.isIE6 ? 'absolute' : position;
			me.dom = $('<div class="j-ui-miniwindow '+ cfg.cls +'" style="z-index:'+ cfg.zIndex +';position:'+ position +';"><div class="hd"><i class="close closeBtn"></i><span class="title">提示</span></div><div class="bd"></div><a href="javascript:void(0);"  style="display:none" class="btn confirm">确 认<b class="btn-inner"></b></a><a href="javascript:void(0);"  style="display:none" class="btn cancel">取 消<b class="btn-inner"></b></a><a href="javascript:void(0);" class="btn closeTip" style="display:none">关 闭<b class="btn-inner"></b></a></div>').appendTo('body');
			me.effectShow = cfg.effectShow;
			me.effectHide = cfg.effectHide;
			me.dom.on('click', '.closeBtn', function(){
				me.doTopClose();
			})
			me.dom.find('.' + me.defConfig.confirmButtonText).bind('click', function(){
				me.dom.find('a').css('display','none');
				me.dom.find('.bd').css('display','none');
				me.dom.find('.hd').after('<div class="bd-load">正在处理中<b class="load-miniwindow"></b></div>');
				me.doConfirm();
				me.dom.find('.bd').show();
				me.dom.find('.bd-load').remove();
				//me.doConfirm=function(){};//防止多次提交
			});
			me.dom.find('.' + me.defConfig.cancelButtonText).bind('click', function(){
				me.doCancel();
			});
			me.dom.find('.' + me.defConfig.closeButtonText).bind('click', function(){
				me.doClose();	
			});
		},
		//执行确定事件
		doConfirm: function(){
		},
		//执行取消事件
		doCancel: function(){
		},
		//执行关闭事件
		doClose: function(){
		},
		//执行title关闭事件
		doTopClose: function(){
                    var me = this;
                    me.hide();
		},
		//获取确认按钮对象
		getConfirmButtonDom: function(){
			var me= this;
			return me.getContainerDom().find('.' + me.defConfig.confirmButtonText);
		},
		//获取取消按钮对象
		getCancelButtonDom: function(){
			var me= this;
			return me.getContainerDom().find('.' + me.defConfig.cancelButtonText);
		},
		//获取关闭按钮对象
		getCloseButtonDom: function(){
			var me= this;
			return me.getContainerDom().find('.' + me.defConfig.closeButtonText);
		},
		//修改确认按钮名称
		setConfirmName: function(text){
			var me= this;
			me.getConfirmButtonDom().text(text);
		},
		//修改取消按钮名称
		setCancelName: function(text){
			var me= this;
			me.getCancelButtonDom().text(text);
		},
		//修改关闭按钮名称
		setCloseName: function(text){
			var me= this;
			me.getCloseButtonDom().text(text);
		},
		//显示确认按钮对象
		showConfirmButton: function(){
			var me= this;
			me.getConfirmButtonDom().show();
		},
		//显示取消按钮对象
		showCancelButton: function(){
			var me= this;
			me.getCancelButtonDom().show();
		},
		//显示关闭按钮对象
		showCloseButton: function(){
			var me= this;
			me.getCloseButtonDom().show();
		},
		//隐藏确认按钮对象
		hideConfirmButton: function(){
			var me= this;
			me.getConfirmButtonDom().hide();
		},
		//隐藏取消按钮对象
		hideCancelButton: function(){
			var me= this;
			me.getCancelButtonDom().hide();
		},
		//隐藏关闭按钮对象
		hideCloseButton: function(){
			var me= this;
			me.getCloseButtonDom().hide();
		},
		setTitle:function(html){
			this.getTitleDom().html(html);
		},
		setContent:function(html, delay){
			var me = this,cfg = me.defConfig,delayTime = 0;
			me.getContentDom().html(html);
			
			delayTime = !!cfg.afterSetContentDelay ? cfg.afterSetContentDelay : delayTime;
			delayTime = !!delay ? delay : delayTime;
			if(delayTime > 0){
				setTimeout(function(){
					me.fireEvent('afterSetContent');
				},delayTime);
			}else{
				me.fireEvent('afterSetContent');
			}
		},
		show:function(){
			var me = this;
			me.fireEvent('beforeShow');
			this.effectShow();
			me.fireEvent('afterShow');
			if(util.isIE6 && me.defConfig.isFixed){
				me._IE6Fixed = util.startFixed(me.dom);
			}
		},
		hide:function(){
			var me = this;
			me.effectHide();
			me.fireEvent('afterHide');
			if(util.isIE6 && me.defConfig.isFixed){
				me._IE6Fixed.stop();
			}
		},
		getContainerDom: function(){
			var me = this;
			return me._containerDom || (me._containerDom = me.dom);
		},
		getCloseDom: function(){
			var me = this;
			return me._closeDom || (me._closeDom = me.dom.find('.close'));
		},
		getTitleDom:function(){
			var me = this;
			return me._titleDom || (me._titleDom = me.dom.find('.title'));
		},
		getContentDom:function(){
			var me = this;
			return me._ContentDom || (me._ContentDom = me.dom.find('.bd'));	
		}
	};
	
	
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	
	//可生成多个实例
	host[name] = Main;
	//也可以重复使用实例
	host[name].getInstance = function(){
		return instance || new Main(defConfig);	
	};

})(phoenix, "MiniWindow", phoenix.Event, jQuery);







