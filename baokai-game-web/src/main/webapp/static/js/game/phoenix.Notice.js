
//消息提示类
(function(host, name, Event, $, undefined){
	var defConfig = {
		//初始化延迟加载时间
		delayTime: 0,
		//服务器端数据地址
		serviceUrl: '',
		//服务器请求方式
		//once: 单次请求
		//repeat: 多次请求[推送]
		serviceGetType: 'once',
		//服务器推送的等待时间
		msgPushWaitTime: 3,
		//消息提示弹层渐隐动画时间
		tipsHiddenTime: 5,
		//多条数据滚动间隔时间
		cycleTime: 5,
		//请求方法
		ajaxDataType:'jsonp',
		//滚动方式top&&bottom
		cycleType: 'bottom',
		//数据加载失败后重新获取数据方式
		reloadType: 'auto',
		//游戏消息通知HTML数据
		noticeHtml: '<div class="public-notice"><div class="inner"><i class="ico-close"></i><i class="ico-volume"></i><#=msg#></div></div>',
		//代理首页通知HTML数据
		noticeProxyHtml: '<div class="public-notice"><div class="innerproxy"><i class="ico-close"></i><i class="ico-volume"></i><#=msg#></div></div>',
		//消息提示HTML数据
		tipsHtml: '<div class="public-notice"><div class="inner"><i class="ico-close"></i><i class="ico-volume"></i><#=msg#></div></div>',
		//
		tipsContainer: '<ul></ul>'
	};

	
	var pros = {
		init:function(cfg){
			var me = this;

			//循环滚动时间缓存
			me.cycleTime = null;
			//服务器推送参数缓存
			//推送标记文件修改时间
			me.timestamp = 0; 

			me.addEvent('afterGetServiceData', function(e, serviceData){
				me.getMethodByName(serviceData['data']);
			});

			me.addEvent('afterGetServicePush', function(e, serviceData){
				me.getMethodByName(serviceData['data']);
			});
		},
		//获取延迟请求时间
		setdelayTime: function(timeNum){
			this.defConfig.delayTime = timeNum;
		},
		//获取延迟请求时间
		getdelayTime: function(){
			return this.defConfig.delayTime;
		},
		//修改服务器推送等待时间
		setMsgPushWaitTime: function(timeNum){
			this.defConfig.msgPushWaitTime = timeNum;
		},
		//获取服务器推送等待时间
		getMsgPushWaitTime: function(){
			return this.defConfig.msgPushWaitTime;
		},
		//获取延迟请求时间
		setServiceGetType: function(timeNum){
			this.defConfig.serviceGetType = timeNum;
		},
		//获取延迟请求时间
		getServiceGetType: function(){
			return this.defConfig.serviceGetType;
		},
		//获取服务器接口地址
		getServiceUrl: function(){
			return this.defConfig.serviceUrl;
		},
		//修改服务器接口地址
		setServiceUrl: function(url){
			this.defConfig.serviceUrl = url;
		},
		//获取通知HTML结构
		getNoticeHtml: function(){
			return this.defConfig.noticeHtml;
		},
		//获取代理首页通知HTML结构
		getProxyNoticeHtml:function(){
			return this.defConfig.noticeProxyHtml;
		},
		//修改通知HTML结构
		setNoticeHtml: function(html){
			this.defConfig.noticeHtml = html;
		},
		//获取提示类型
		getTipsHtml: function(){
			return this.defConfig.tipsHtml;
		},
		//修改提示类型
		setTipsHtml: function(html){
			this.defConfig.tipsHtml = html;
		},
		//修改滚动切换类型
		setCycleType: function(typeName){
			this.defConfig.cycleType = typeName;
		},
		//获取滚动切换类型
		getCycleType: function(){
			return this.defConfig.cycleType;
		},
		//获取通知HTML结构
		getCycleTime: function(){
			return this.defConfig.cycleTime;
		},
		//修改通知HTML结构
		setCycleTime: function(timeNum){
			this.defConfig.cycleTime = timeNum;
		},
		//输出消息提示容器DOM
		getTipsContainerHtml: function(){
			return 
		},
		//输出消息提示容器DOM
		setTipsContainerHtml: function(){

		},
		//模版替换
		formatRow:function(tpl, data){
			var me = this,o = data,p,reg;
			for(p in o){
				if(o.hasOwnProperty(p)){
					reg = RegExp('<#=' + p + '#>', 'g');
					tpl = tpl.replace(reg, o[p]);
				}
			}
			return tpl;
		},
		//初始化执行
		startLoadData: function(){
			var me = this,
				time = me.getdelayTime() || 0;

			//延迟执行数据加载
			setTimeout(function(){
				me.getServiceData();
			}, time * 20000);
		},
		//获取服务器端数据总接口
		//通过初始化参数 发起重复[推送]或者单次请求
		getServiceData: function(){
			var me = this;

			if(me.getServiceGetType() == 'once'){
				me.getServiceDataOnce();
			}else if(me.getServiceGetType() == 'push'){
				me.getServiceDataRepeat();
			}
		},
		//获取服务器端数据
		getServiceDataOnce: function(){
			var me = this,ajaxDataType = me.defConfig.ajaxDataType;

			//获取服务器端数据
			$.ajax({
				url: me.getServiceUrl(),
				cache:false,
				dataType: ajaxDataType,
				jsonp: "callBack",
				success: function(result){
					if(result['isSuccess'] == 1){
						//执行对应的接口方法
						me.fireEvent('afterGetServiceData', result);
					}else{
						//错误提示
						me.errorTips(result);
					}
				},
				error: function(){
					//错误提示
					me.errorTips({
						name: 'netError',
						msg: '服务器数据异常'
					});
				}
			})
		},
		//获取服务器端数据
		getServiceDataRepeat: function(num){
			var me = this,ajaxDataType = me.defConfig.ajaxDataType;


			//获取服务器端数据
			$.ajax({
				url: me.getServiceUrl(),
				cache:false,
				dataType: ajaxDataType,
				jsonp: "callBack",
				success: function(result){
					//var re=eval(slist);
					if(result['isSuccess'] == 1){
						//文件最后修改时间
						me.timestamp = result['timestamp'];
						//服务器端修改等待时间
						//服务器端控制压力
						if(typeof result['time'] != 'undefined' && Number(time)){
							me.setMsgPushWaitTime(Number(time));
						}
						//执行对应的接口方法
						me.fireEvent('afterGetServicePush', result);
					}else{
						//错误提示
						me.errorTips(result);
					}

				},
				error: function(xhr){
					if(xhr.status == '404' || xhr.status == '0' || xhr.status == '401' ){
						window.location.reload();
					}
				},
				complete: function(){
					//请求完成
					//重新发送请求
					me.reloadService();
				}
			})
		},
		//
		reloadService: function(){
			var me = this;

			setTimeout(function(){
				me.getServiceDataRepeat();
			}, me.getMsgPushWaitTime() * 1000)
		},
		//执行对应内部方法
		getMethodByName: function(serviceData){
			var me = this,
				i = 0,
				data, methodName,
				gameTypeNotices=phoenix.Games.getCurrentGame()==null?new GameConfig().getConfig().gameType:phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"],
				nameArr;

			me.fireEvent('beforStartMethod', serviceData);
			
			//没有数据
			if(serviceData.length <= 0){
				return;
			}

			//输出数据到过滤方法执行
			for (; i < serviceData.length; i++) {
				//方法名&&参数
				methodName = serviceData[i]['name'];
				data = serviceData[i]['tplData'];
				nameArr = methodName.split('_');
              
				//检测模块接口
				if(typeof me[methodName] != 'undefined' && $.isFunction(me[methodName])){
					//直接执行name对应的方法
					me[methodName](data);
				}else if(data.length>0 && nameArr[0] == 'ad' && nameArr[1] == 'top'){
					//共用顶部公告
					me.messageNotice(data);
				}else if(gameTypeNotices=='jsdice' && data.length>0 && nameArr[0] == 'ad'  && nameArr[1] == 'jsdice'){
					me.gameJsdice(data);
				}else if(data.length>0 && nameArr[0] == 'ad' && !!phoenix.Games.getCurrentGame() && nameArr[1] == phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"]){
					//游戏中部公告
					me.gameMessageNotice(data);
				}
				else if(data.length>0 && nameArr[0] == 'agent' && nameArr[1] == 'first'){
					//代理首页公告
					me.proxyMessageNotice(data);
				}
				else{
					try{
						//console.log('缺少对应的执行方法: ' + methodName);
					}catch(e){

					}
				}
			};
		},
		//错误消息提示
		errorTips: function(data){
			var me = this,
				name = data['name'],
				data = data['Tpldata'],
				method = me[name + 'error'];

			//执行出错提示分支
			if(typeof method != 'undefined' && $.isFunction(method)){
				method(data);
			}
		},
		//顶部消息通知
		messageNotice: function(data){
			var me = this,
			    uname=global_userID,
				key=[],
				html = [];
		 if(!$.cookie(uname+'_Notice_Close'))
		 {
           if($('body').find('.messageNotice').length == 0)
		   {
				html.push('<ul>');
				for (var i = 0; i < data.length; i++) {
					key.push(uname+"_Notice_"+data[i]['url']);
					if(!$.cookie(key.join('')))
					{
					html.push('<li><a href="javascript:void(0);" endTime='+data[i]['endTime']+' pid='+data[i]['url']+' url='+_logOut+'/ad/adNoticeQuery?id=' + data[i]['url'] + '>' + data[i]['text'] + '</a></li>');
					}
					key=[];
				};
				html.push('</ul>');
				if(html.join('').indexOf("li") > 0)
				{
					//定义HTMLdom引用
					me['messageNoticeDom'] = $(me.formatRow(me.getNoticeHtml(), {msg: html.join('')}));
					//添加CSS标识
					me['messageNoticeDom'].addClass('messageNotice');
					//输出通知模版
					$('body').prepend(me['messageNoticeDom']);
					//显示通知栏
					me.messageNoticeShow();
					//绑定事件[hover停止动画循环]
					me.bindEventNotice(me['messageNoticeDom'], 'message');
					//多条通知开始循环滚动
					me.messageCycleStart(me['messageNoticeDom'].find('ul'), 'li', 'message');
				}
		   }else
		   {
			   for (var i = 0; i < data.length; i++) {
				    key.push(uname+"_Notice_"+data[i]['url']);
					if(!$.cookie(key.join('')))
					{
				       html.push('<li><a href="javascript:void(0);" endTime='+data[i]['endTime']+' pid='+data[i]['url']+' url='+_logOut+'/ad/adNoticeQuery?id=' + data[i]['url'] + '>' + data[i]['text'] + '</a></li>');
					   
					}
					key=[];
		    	};
			    $('body').find('.messageNotice').find('ul').prepend(html);

		   }
		 }
		},
		gameJsdice:function(data){
            var me = this,
			    uname=global_userID,
				key=[],
				html = [];
			 if(!$.cookie(uname+'_Game_Close'))
			 {
			   if($('body').find('.gameMessageNotice').length == 0)
			   {
					html.push('<ul>');
					for (var i = 0; i < data.length; i++) {
							key.push(uname+"_Game_"+data[i]['url']);
							if(!$.cookie(key.join('')))
							{
							html.push('<li><a href="javascript:void(0);" endTime='+data[i]['endTime']+' pid='+data[i]['url']+' url='+_logOut+'/ad/adNoticeQuery?id=' + data[i]['url'] + '>' + data[i]['text'] + '</a></li>');
							}
							key=[];
					};
					html.push('</ul>');
					if(html.join('').indexOf("li") > 0)
					{
						//定义HTMLdom引用
						me['gameMessageNoticeDom'] = $(me.formatRow(me.getNoticeHtml(), {msg: html.join('')})); 
						//添加CSS标识
						me['gameMessageNoticeDom'].addClass('gameMessageNotice');
						//输出通知模版
						
						//$('.lottery-notice').remove();
						$('.lottery-notice').prepend(me['gameMessageNoticeDom']);
						//显示通知栏
						me.gameMessageNoticeShow();
						//绑定事件[hover停止动画循环]
						me.bindEventNotice(me['gameMessageNoticeDom'], 'gameMessage');
						//多条通知开始循环滚动
						me.messageCycleStart(me['gameMessageNoticeDom'].find('ul'), 'li', 'gameMessage');
					}else
				    {
                        $('.lottery-notice').hide();
					}
				}else
			    {
				   for (var i = 0; i < data.length; i++) {
						key.push(uname+"_Game_"+data[i]['url']);
						if(!$.cookie(key.join('')))
						{
						   html.push('<li><a href="javascript:void(0);" endTime='+data[i]['endTime']+' pid='+data[i]['url']+' url='+_logOut+'/ad/adNoticeQuery?id=' + data[i]['url'] + '>' + data[i]['text'] + '</a></li>');
						   
						}
						key=[];
				    };
					$('body').find('.gameMessageNotice').find('ul').prepend(html);
	
			    }
			  }

			/*
			var me = this,
				meNotice,
			    uname=global_userID,
				key=[],
				html = "";
			 meNotice=$('body').find('.lottery-notice');
			 meNotice.find('p').remove();
			 if(!$.cookie(uname+'_Game_Close'))
			 {
				if(data.length > 0)
				{
					for(var i=0;i<data.length;i++)
					{
						html="<p>";
						html+=data[i]['text'];
						html+="</p>";
					}
				   meNotice.prepend(html);
				   meNotice.show();
				}else
				{
				   meNotice.hide();
				}
			  }
			  */
		},
		//游戏中部消息通知
		gameMessageNotice: function(data){
			var me = this,
			    uname=global_userID,
				key=[],
				html = [];
			 if(!$.cookie(uname+'_Game_Close'))
			 {
			   if($('body').find('.gameMessageNotice').length == 0)
			   {
					html.push('<ul>');
					for (var i = 0; i < data.length; i++) {
							key.push(uname+"_Game_"+data[i]['url']);
							if(!$.cookie(key.join('')))
							{
							html.push('<li><a href="javascript:void(0);" endTime='+data[i]['endTime']+' pid='+data[i]['url']+' url='+_logOut+'/ad/adNoticeQuery?id=' + data[i]['url'] + '>' + data[i]['text'] + '</a></li>');
							}
							key=[];
					};
					html.push('</ul>');
					if(html.join('').indexOf("li") > 0)
					{
						//定义HTMLdom引用
						me['gameMessageNoticeDom'] = $(me.formatRow(me.getNoticeHtml(), {msg: html.join('')})); 
						//添加CSS标识
						me['gameMessageNoticeDom'].addClass('gameMessageNotice');
						//输出通知模版
						$('.main').prepend(me['gameMessageNoticeDom']);
						//显示通知栏
						me.gameMessageNoticeShow();
						//绑定事件[hover停止动画循环]
						me.bindEventNotice(me['gameMessageNoticeDom'], 'gameMessage');
						//多条通知开始循环滚动
						me.messageCycleStart(me['gameMessageNoticeDom'].find('ul'), 'li', 'gameMessage');
					}
				}else
			    {
				   for (var i = 0; i < data.length; i++) {
						key.push(uname+"_Game_"+data[i]['url']);
						if(!$.cookie(key.join('')))
						{
						   html.push('<li><a href="javascript:void(0);" endTime='+data[i]['endTime']+' pid='+data[i]['url']+' url='+_logOut+'/ad/adNoticeQuery?id=' + data[i]['url'] + '>' + data[i]['text'] + '</a></li>');
						   
						}
						key=[];
				    };
					$('body').find('.gameMessageNotice').find('ul').prepend(html);
	
			    }
			  }
		},
		//消息通知DOM显示
		gameMessageNoticeShow: function(){
			var me = this,
				msgDom = me['gameMessageNoticeDom'];

			msgDom.animate({
				height: 28
			});

			return me;
		},
		//消息通知DOM隐藏
		gameMessageNoticehide: function(){
			var me = this,
				msgDom = me['gameMessageNoticeDom'];

			msgDom.animate({
				height: 0
			});

			return me;
		},
		
		//代理中部消息通知
		proxyMessageNotice: function(data){
			var me = this,
				html = [];

			html.push('<ul>');
			for (var i = 0; i < data.length; i++) {
				html.push('<li><a href="' + data[i]['url'] + '">' + data[i]['text'] + '</a></li>');
			};
			html.push('</ul>');

			//定义HTMLdom引用
			me['proxyMessageNoticeDom'] = $(me.formatRow(me.getProxyNoticeHtml(), {msg: html.join('')})); 
			//添加CSS标识
			me['proxyMessageNoticeDom'].addClass('proxyMessageNotice');
			//添加样式
			me['proxyMessageNoticeDom'].css({'margin-bottom':'10px'});
			//修改代理首页样式
			$('#proxyTitle').css({'margin-bottom':'0px'});
			//输出通知模版
			$('#proxyTitle').after(me['proxyMessageNoticeDom']);
			//显示通知栏
			me.proxyMessageNoticeShow();
			//绑定事件[hover停止动画循环]
			me.bindEventNotice(me['proxyMessageNoticeDom'], 'proxyMessage');
			//多条通知开始循环滚动
			me.messageCycleStart(me['proxyMessageNoticeDom'].find('ul'), 'li', 'proxyMessage');
		},
		//消息通知DOM显示
		proxyMessageNoticeShow: function(){
			var me = this,
				msgDom = me['proxyMessageNoticeDom'];

			msgDom.animate({
				height: 28
			});

			return me;
		},
		//消息通知DOM隐藏
		proxyMessageNoticehide: function(){
			var me = this,
				msgDom = me['proxyMessageNoticeDom'];

			msgDom.animate({
				height: 0
			});

			return me;
		},
		//消息通知DOM显示
		proxyMessageNoticeShow: function(){
			var me = this,
				msgDom = me['proxyMessageNoticeDom'];

			msgDom.animate({
				height: 28
			});

			return me;
		},
		//消息通知DOM隐藏
		proxyMessageNoticehide: function(){
			var me = this,
				msgDom = me['proxyMessageNoticeDom'];

			msgDom.animate({
				height: 0
			});

			return me;
		},
		//绑定事件[hover停止动画循环]
		bindEventNotice: function(html, name){
			var me = this;

			html.bind('mouseenter', function(){
				me.messageCycleStop(name, html);
			})
			html.bind('mouseleave', function(){
				me.messageCycleStart(html.find('ul'), 'li', name);
			}) 
		},
		//消息通知循环显示
		//parentDom 循环父级容器
		//cycleDom 需要循环的DOM集合
		messageCycleStart: function(parentDom, cycleDom, name){
			var me = this,
				child = parentDom.find(cycleDom),
				height = child.eq(0).outerHeight(),
				dom;

			if(child.size() > 1){
				//循环滚动显示
				me[name] = setInterval(function(){
					dom = parentDom.find(cycleDom);

					if(me.getCycleType() == 'top'){
						dom.eq(0).animate({
							marginTop:  -height
						}, 1000,  function(){
							parentDom.append(dom.eq(0));
							dom.eq(0).css('marginTop',0);
						})
					}else if(me.getCycleType() == 'bottom'){
						dom = dom.eq(dom.length - 1);
						
						parentDom.prepend(dom);
						dom.css('marginTop', -height);
						
						dom.eq(0).animate({
							marginTop :  0
						}, 1000)
					}
				},me.getCycleTime() * 1000)	
			}

			return me;
		},
		//消息通知循环停止
		messageCycleStop: function(name, html){
			var me = this;

			if(me[name]){
				clearInterval(me[name]);
				me[name] = null;
			}

			return me;
		},
		//消息通知错误提示
		messageNoticeError: function(data){

		},
		//消息通知DOM显示
		messageNoticeShow: function(){
			var me = this,
				msgDom = me['messageNoticeDom'];

			msgDom.animate({
				height: 28
			});

			return me;
		},
		//消息通知DOM隐藏
		messageNoticehide: function(){
			var me = this,
				msgDom = me['messageNoticeDom'];

			msgDom.animate({
				height: 0
			});

			return me;
		},
		//消息通知
		messageTips: function(data){
			var me = this,
				i = 0;
            if(data!=null)
			{
				for (; i < data.length; i++) {
					var html = data[i]['html'],
						time = i * 1500;
	
					setTimeout(me.bulidTipsDoms(html), time);
				};
			}
			
		},
		//创建tips提示DOM结构
		bulidTipsDoms: function(htmls){
			var me = this;

			return function(){
				html = [];
				//添加代码片段
				html.push(htmls);
				//需要添加的HTML片段
				html = $(html.join(''));
				//添加DOM
				me.tipsContainer.prepend(html);
				//开始渐隐动画
				me.startTipsHidden(html);
				//代理按钮关闭以及HOVER事件
				me.bindEventTips(html);
			}
		},
		//添加消息提示父容器到页面
		addTipsContainerDom: function(){
			var me = this;

			//_position:absolute;_left:expression(eval(document.documentElement.scrollLeft+document.documentElement.clientWidth-this.offsetWidth)-(parseInt(this.currentStyle.marginLeft,10)||0)-(parseInt(this.currentStyle.marginRight,10)||0) - 30);_top:expression(eval(document.documentElement.scrollTop+document.documentElement.clientHeight-this.offsetHeight-(parseInt(this.currentStyle.marginTop,10)||0)-(parseInt(this.currentStyle.marginBottom,10)||0)) - 20)
			me.tipsContainer = $('<ul style="position:fixed;bottom:10px;right:80px;z-index:10000" class="game_tipscontainer ie6fixedBR"></ul>');

			$('body').append(me.tipsContainer);

			return me;
		},
		//开始提示弹层渐隐动画
		startTipsHidden: function(html){
			var me = this;

			setTimeout(function(){
				//绝对定位层不会随父级的淡出动画
				html.find('.close_btn').fadeOut(5000);
				html.fadeOut(5000, function() {
					$(this).remove();
					$(this).unbind();
				});
			}, me.defConfig.tipsHiddenTime * 1000);
		},
		//代理按钮关闭以及HOVER事件
		bindEventTips: function(html){
			var me = this,
				timeSave = null;

			//阻止隐藏事件
			html.bind('mouseenter', function(){
				if(timeSave){
					clearTimeout(timeSave);
					timeSave = null;
				}
				if($(this).is(':animated')){
					$(this).stop();
					$(this).css('opacity', '1');
				}
			});
			html.bind('mouseleave', function(){
				var that = $(this);
				timeSave = setTimeout(function(){
					me.startTipsHidden(that);	
				}, 500);
			});
			//关闭按钮
			html.find('.close_btn').bind('click', function(){
				$(this).parent().remove();
			});
		},
		//消息通知错误提示
		messageTipsError: function(){

		}
	};
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	host[name] = Main;
	
})(phoenix, "Notice", phoenix.Event, jQuery);

$(function(){
	//通知&&消息提示组件
	var gameTypeNotices=phoenix.Games.getCurrentGame()==null?new GameConfig().getConfig().gameType:phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"];
	var global_params_notice = 'ad_' + gameTypeNotices;
	var msgNoticeTips = new phoenix.Notice({
		delayTime: 2,
		serviceUrl: _logOut +'/api/jsonp/getNotices?page=all,ad_top,' +global_params_notice 
	});

	//获取服务器端消息通知数据
	msgNoticeTips.startLoadData();

	//信息通知关闭按钮
	$('body').on('click', '.messageNotice .ico-close', function(){
		msgNoticeTips.messageNoticehide().messageCycleStop('message');
		var uname=global_userID,key=uname+'_Notice_Close';
		$.cookie(key,true,{path:'/',domain:window.location.hostname.substring(window.location.hostname.indexOf('.'))});
	});
	
	$('body').on('click','.messageNotice a',function(){
		var me=$(this),pid=me.attr('pid'),url=me.attr('url'),endTime=me.attr('endTime'),unixTime='0'+endTime.substring(0,endTime.length-3),data=new Date(phoenix.util.UnixToDate('Y/m/d H:i:s',unixTime)),uname=global_userID,key=uname+"_Notice_"+pid;
		if(!$.cookie(key))
		{
		   $.cookie(key,true,{expires:data,path:'/',domain:window.location.hostname.substring(window.location.hostname.indexOf('.'))});
		}
		window.location=url;
	});

	$('body').on('click', '.gameMessageNotice .ico-close', function(){
		msgNoticeTips.gameMessageNoticehide().messageCycleStop('gameMessage');
		var uname=global_userID,key=uname+'_Game_Close';
		$.cookie(key,true,{path:'/',domain:window.location.hostname.substring(window.location.hostname.indexOf('.'))});
	});
	
	$('body').on('click','.gameMessageNotice a',function(){
		var me=$(this),pid=me.attr('pid'),url=me.attr('url'),endTime=me.attr('endTime'),unixTime='0'+endTime.substring(0,endTime.length-3),data=new Date(phoenix.util.UnixToDate('Y/m/d H:i:s',unixTime)),uname=global_userID,key=uname+"_Game_"+pid;
		if(!$.cookie(key))
		{
		   $.cookie(key,true,{expires:data,path:'/',domain:window.location.hostname.substring(window.location.hostname.indexOf('.'))});
		}
		window.location=url;
	});
	
	$('body').on('click', '.proxyMessageNotice .ico-close', function(){
		msgNoticeTips.proxyMessageNoticehide().messageCycleStop('proxyMessage');
	});

	//通知&&消息提示组件
	/*var msgTips = new phoenix.Notice({
		delayTime: 5,
		serviceUrl: _logOut +'/api/jsonp/getTips?userId='+global_userID+'&r='+Math.random(),
		serviceGetType: 'push',
		msgPushWaitTime: 8
	});*/

	//初始化消息推送
	//msgTips.addTipsContainerDom().startLoadData();
});





