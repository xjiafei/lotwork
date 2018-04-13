
//追号区域
;(function(host, name, Event,undefined){
	var defConfig = {
			//彩种休市提示
			/*lotteryClose : ['<div class="bd text-center">',
							'<p class="text-title text-left">非常抱歉，本彩种已休市。<br />请与<#=orderDate#>后再购买</p>',
							'<div class="lottery-numbers text-left">',
								'<div class="tltle"><#=lotteryName#> 第<strong class="color-green"><#=lotteryPeriods#></strong>期开奖号码：</div>',
								'<div class="content">',
									'<#=lotterys#>',
									'<a href="#">查看更多&raquo;</a>',
								'</div>',
							'</div>',
							'<dl class="lottery-list">',
								'<dt>您可以购买以下彩种</dt>',
								'<#=lotteryType#>',
							'</dl>',
						'</div>'].join(''),*/
			//整彩种停售
			lotteryClose : ['<div class="bd text-center">',
							'<p class="text-title text-left"><#=msg#><br /></p>',							
							'<dl class="lottery-list">',
								'<dt>您可以购买以下彩种</dt>',
								'<#=lotteryType#>',
							'</dl>',
						'</div>'].join(''),
			//投注信息核对
			checkLotters : ['<div class="bd">',
									'<ul class="ui-form">',
										'<li>',
											'<label for="question1" class="ui-label">彩种：</label>',
											'<span class="ui-text-info"><#=lotteryName#></span>',											
										'</li>',
										'<li>',
											'<label for="question1" class="ui-label" >期号：</label>',
											'<span class="ui-text-info"><#=lotteryDate#></span>',
										'</li>',
										'<li>',
											'<label for="answer1" class="ui-label">详情：</label>',
											'<div class="textarea" style="font-size:12px;" >',
												'<#=lotteryInfo#>',
											'</div>',
										'</li>',
										'<li>',
											'<label for="question2" class="ui-label">付款总金额：</label>',
											'<span class="ui-text-info"><span class="color-red"><#=lotteryamount#></span>元</span>',
										'</li>',
										'<li>',
											'<label for="question2" class="ui-label">付款帐号：</label>',
											'<span class="ui-text-info"><span class="color-red"><#=lotteryAcc#></span></span>',
										'</li>',
									'</ul>',									
									'<p class="text-note"><#=lotteryChareg#></p>',
									'<p class="text-note"><#=lotteryCharegPlurality#></p>',									
							'</div>'].join(''),


			//未到销售时间
			nonSaleTime : ['<div class="bd text-center">',
							'<p class="text-title text-left">非常抱歉，本彩种未到销售时间。<br />请与<#=orderDate#>后再购买</p>',
							'<dl class="lottery-list">',
								'<dt>您可以购买以下彩种</dt>',
								'<#=lotteryType#>',
							'</dl>',
						'</div>'].join(''),

			//正常提示
			normal : ['<div class="bd text-center">',
								'<div class="pop-waring">',
									'<i class="ico-waring <#=icon-class#>"></i>',
									'<h4 class="pop-text"><#=msg#><br /></h4>',
								'</div>',
							'</div>'].join(''),

			//无效字符提示
			invalidtext : ['<div class="bd text-center">',
								'<div class="pop-waring">',
									'<i class="ico-waring <#=icon-class#>"></i>',
									'<h4 class="pop-text"><#=msg#><br /></h4>',
								'</div>',
							'</div>'].join(''),

			//投注过期提示
			betExpired : ['<div class="bd text-center">',
								'<div class="pop-waring">',
									'<i class="ico-waring <#=icon-class#>"></i>',
									'<h4 class="pop-text"><#=msg#><br /></h4>',
								'</div>',
							'</div>'].join(''),

			//倍数超限
			multipleOver : ['<div class="bd text-center">',
								'<div class="pop-waring">',
									'<i class="ico-waring <#=icon-class#>"></i>',
									'<h4 class="pop-text"><#=msg#><br /></h4>',
								'</div>',
							'</div>'].join(''),		  
			//暂停销售
			pauseBet : ['<div class="bd text-center">',
							'<div class="pop-waring">',
								'<i class="ico-waring <#=icon-class#>"></i>',
								'<h4 class="pop-text"><#=msg#><br /></h4>',
							'</div>',							
							
						'</div>'].join(''),
			
			//成功提示
			successTip : ['<div class="bd text-center">',
								'<div class="pop-title">',
									'<i class="ico-success"></i>',
									'<h4 class="pop-text"><#=msg#><br /></h4>',
								'</div>',
								'<p class="text-note">您可以通过”<a href="'+baseUrl+'<#=url#>" target="_black"><#=gameType#></a>“查询您的投注记录！</p>',
							'</div>'].join(''),
			//提醒选求提示
			checkBalls : ['<div class="bd text-center">',
							'<div class="pop-title">',
								'<i class="ico-waring <#=iconClass#>"></i>',
								'<h4 class="pop-text">请至少选择一注投注号码！</h4>',
							'</div>',
							'<div class="pop-btn ">',
								'<a href="javascript:void(0);" class="btn closeBtn">关 闭<b class="btn-inner"></b></a>',
							'</div>',
						'</div>'].join(''),
			//错误提示
			subFaild : ['<div class="bd text-center">',
							'<div class="pop-title">',
								'<i class="ico-error"></i>',
								'<h4 class="pop-text"><#=msg#><br /></h4>',
							'</div>',
							'<div class="pop-btn ">',
								'<a href="javascript:void(0);" class="btn closeBtn">关 闭<b class="btn-inner"></b></a>',
							'</div>',
						'</div>'].join(''),
			//余额不足
			Insufficientbalance : ['<div class="bd text-center">',
									'<div class="pop-title">',
										'<i class="ico-error"></i>',
										'<h4 class="pop-text">您的余额不足，请充值！</h4>',
									'</div>',							
								'</div>'].join(''),
			//高频封锁
			overLimit : ['<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text"><#=msg#><br /></h4></div>',
							'<div class="content" style="max-height:150px;overflow:auto;">',
								//'<#=webIssueCode#>期&nbsp;<#=betMethod#><#=betDetail#> 本期最多投<#=multiple#>倍',	
								'<#=contentinfo#>',
							'</div>',
							'</div>'].join('')
		},
	instance,
	closeTime = null,
	Games = host.Games;
	
	var pros = {
		//初始化
		init: function(cfg){
			var me = this;
			me.win = new host.MiniWindow({
				//实例化时追加的最外层样式名
				cls:'pop w-9'
			})
			me.mask = host.Mask.getInstance();
			//绑定隐藏完成事件
			me.reSet();
			me.win.addEvent('afterHide', function(){
				me.reSet()
			})
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
			//删除type数据
			//防止在渲染的时候进行递归调用
			delete data['type'];
			//调用子类方法
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
		//通用
		getHtmlWaring: function(){
			var cfg = this.defConfig;
			return cfg.normal;
		},
		//默认弹窗
		rebulidnormal: function(parameter){
			var me = this, result = {};			
				result['mask'] = true;
				result['closeText'] = '关 闭';
				result['closeIsShow'] = true;
				result['closeFun'] = function(){
					me.hide()
				};
				result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		//获取默认提示弹窗
		getHtmlnormal: function(){
			return this.getHtmlWaring();
		},
		/*
			//彩种核对
			phoenix.Games.getCurrentGameMessage().show({
			   type : 'checkLotters',
			   data : {
			   		tplData : {
				   		//当期彩票详情
				        lotteryDate : '20121128-023',
				        //彩种名称
				        lotteryName : 'shishicai',
				        //投注详情
				        lotteryInfo : ,
				        //彩种金额
				        lotteryamount : {'year':'2013','month':'5','day':'3','hour':'1','min':'30'},
				        //付款帐号
				        lotteryAcc :，
				       	//手续费
				       	lotteryCharge 
			   		}
				}
			})
		 */
		rebulidcheckLotters : function(parameter){
			var me = this,
				order = Games.getCurrentGameOrder().getTotal()['orders'],
				isFollowNumber = parameter['data']['tplData'].lotteryTraceInfo(),
				lotteryOnlyAmount = phoenix.util.ToForamtmoney(parameter['data']['tplData'].lotteryamount),
				backOutStartFee =Number(Games.getCurrentGame().getGameConfig().getInstance().defConfig["backOutStartFee"]) / 10000 ,//返手续费起始金额
				backOutRadio = Number(Games.getCurrentGame().getGameConfig().getInstance().defConfig["backOutRadio"]) / 10000 ;//返金额率
				
				result = {};	
				result['mask'] = true;
				result['iconClass'] = '';		
				
				//无追号	
				if(isFollowNumber.length < 1){	
					if( lotteryOnlyAmount > backOutStartFee && Number(backOutStartFee) !=0 && Number(backOutRadio) != 0){
						parameter['data']['tplData'].lotteryChareg = '<div id="showLotteryChareg" style="display: none;">购买后请您尽量避免撤单，如撤单将收取手续费：<span class="price"><dfn>&yen;</dfn><span class="handlingCharge">'+Games.getCurrentGameStatistics().formatMoney(backOutRadio * lotteryOnlyAmount, 3)+'</span>元</span></div>';
						parameter['data']['tplData']['lotteryCharegPlurality'] = '';							
					}else{
						parameter['data']['tplData'].lotteryChareg = '';
						parameter['data']['tplData']['lotteryCharegPlurality'] = '';			
					}	
				}else{
					parameter['data']['tplData'].lotteryChareg = '';
					var html ='',
						lotteryAmount = 0, //每一期投注金额						
						totalAmount = 0 ,
						lotteryOnlyAmounts = parameter['data']['tplData'].lotteryOnlyAmount; //追号单笔金额
						
						html = '<div style="margin-top:-12px;" id="showLotteryChareg">购买后请您尽量避免撤单，如撤单将收取手续费：</div><ul class="textarea cancel-bets clearfix" style="font-size:12px;margin-top:10px;"> <li><span style="font-size:13px;font-weight:500">奖期</span><span style="font-size:13px;font-weight:500">撤单手续费</span></li>';
						for(var i = 0; i < isFollowNumber.length; i++){			
							lotteryAmount =Number(isFollowNumber[i]['multiple'] * lotteryOnlyAmounts );
							if(lotteryAmount > backOutStartFee){
								totalAmount += ( lotteryAmount * backOutRadio );								
								html +=  '<li><span>第'+isFollowNumber[i]['number']+'期</span><span class="price"><dfn>&yen;</dfn>'+Games.getCurrentGameStatistics().formatMoney(lotteryAmount * backOutRadio, 3)+'元</span></li>';	
							}																		
						}
						html +='</ul>';
						
					if( totalAmount > 0 && Number(backOutStartFee) !=0 && Number(backOutRadio) != 0){
						parameter['data']['tplData']['lotteryCharegPlurality'] = html;						
					}else{
						parameter['data']['tplData']['lotteryCharegPlurality'] = '';			
					}				
				}
				
				result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		getHtmlcheckLotters : function(){
			var cfg = this.defConfig;
			return cfg.checkLotters;
		},
		/*
			//彩种关闭调用实例
			phoenix.Games.getCurrentGameMessage().show({
			   type : 'lotteryClose',
			   data : {
			   		tplData : {
				   		//当期彩票详情
				        lotterys : [1,2,3,4,5,6],
				        //彩种名称
				        lotteryName : 'shishicai',
				        //开奖期数
				        lotteryPeriods : '20130528-276',
				        //开始购买时间
				        orderDate : {'year':'2013','month':'5','day':'3','hour':'1','min':'30'},
				        //提示彩票种类
				        lotteryType : [{'name':'leli','pic':'#','url':'http://163.com'},{'name':'kuaile8','pic':'#','url':'http://pp158.com'}]
			   		}
				}
			})
		
		//彩种关闭
		rebulidlotteryClose : function(parameter){
			var me = this,
				result = {};		
				lotteryName = parameter['data']['tplData']['lotteryName'];
				lotteryPeriods = parameter['data']['tplData']['lotteryPeriods'];
				time = parameter['data']['tplData']['orderDate'];
				lotterys = parameter['data']['tplData']['lotterys'];
				typeArray = parameter['data']['tplData']['lotteryType'];
				result['mask'] = true;
				result['iconClass'] = '';
				result['closeIsShow'] = true;
				result['closeFun'] = function(){
					me.hide();
				};
				//彩种名称
				parameter['data']['tplData']['lotteryName'] = function(){
					return lotteryName || '';
				};
				//本次开奖期数
				parameter['data']['tplData']['lotteryPeriods'] = function(){
					return lotteryPeriods || '';
				};
				//购买日期
				parameter['data']['tplData']['orderDate'] = function(){
					return time['year'] + '年' + time['month'] + '月' + time['day'] + '日 ' + time['hour'] + ':' + time['min'];
				};
				//彩票详情
				parameter['data']['tplData']['lotterys'] = function(){
					var html  = '';
					if($.isArray(lotterys)){
						for (var i = 0; i < lotterys.length; i++) {
							html += '<em>' + lotterys[i] + '</em>';
						};
					}
					return html;
				};
				//彩票种类
				parameter['data']['tplData']['lotteryType'] = function(){
					var html  = '';
					if($.isArray(typeArray)){
						for (var i = 0; i < typeArray.length; i++) {
							html += '<dd><span style="background:url(' + typeArray[i]['pic'] +')" class="pic" title="' + typeArray[i]['name'] + '" alt="' + typeArray[i]['name'] + '"></span><a href="' + typeArray[i]['url'] + '" class="btn">去投注<b class="btn-inner"></b></a></dd>';
						};
					}	
					return html;
				};
				result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		}, */
		//彩种停售
		rebulidlotteryClose : function(parameter){
			var me = this,
				order = Games.getCurrentGameOrder().getTotal()['orders'],
				result = {};	
				result['mask'] = true;
				result['iconClass'] = '';
				
				result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
				me.show($.extend(result, parameter));				


		},
		getHtmllotteryClose : function(){
			var cfg = this.defConfig;
			return cfg.lotteryClose;
		},
		/*
			//调用实例
			phoenix.Games.getCurrentGameMessage().show({
			   type : 'nonSaleTime',
			   data : {
			       tplData:{
						//开始购买时间
				        orderDate : {'year':'2013','month':'5','day':'3','hour':'1','min':'30'},
				        //提示彩票种类
				        lotteryType : [{'name':'leli','pic':'#','url':'http://163.com'},{'name':'kuaile8','pic':'#','url':'http://pp158.com'}]
			       }
			   }
			})
		 */
		//未到销售时间
		rebulidnonSaleTime : function(parameter){
			var me = this,
				result = {};
				time = parameter['data']['tplData']['orderDate'];
				typeArray = parameter['data']['tplData']['lotteryType'];
				result['mask'] = true;
				result['iconClass'] = '';
				result['closeIsShow'] = true;
				result['closeFun'] = function(){
					me.hide();
				};
				//购买日期
				parameter['data']['tplData']['orderDate'] = function(){
					return time['year'] + '年' + time['month'] + '月' + time['day'] + '日 ' + time['hour'] + ':' + time['min'];
				};
				//彩票种类
				parameter['data']['tplData']['lotteryType'] = function(){
					var html  = '';

					if($.isArray(typeArray)){
						for (var i = 0; i < typeArray.length; i++) {
							html += '<dd><span style="background:url(' + typeArray[i]['pic'] +')" class="pic" title="' + typeArray[i]['name'] + '" alt="' + typeArray[i]['name'] + '"></span><a href="' + typeArray[i]['url'] + '" class="btn">去投注<b class="btn-inner"></b></a></dd>';
						};
					}	
					return html;
				};
				result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		getHtmlnonSaleTime : function(){
			var cfg = this.defConfig;
			return cfg.nonSaleTime;
		},
		//至少选择一注
		rebulidmustChoose : function(parameter){
			var me = this, result = {};		
				result['mask'] = true;
				result['iconClass'] = '';
				result['closeIsShow'] = true;
				result['closeFun'] = function(){
					me.hide();
				};
				result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		getHtmlmustChoose : function(){
			return this.getHtmlWaring();
		},
		//网络连接异常
		rebulidnetAbnormal : function(parameter){
			var me = this, result = {};		
				result['mask'] = true;
				result['iconClass'] = '';
				result['closeIsShow'] = true;
				result['closeFun'] = function(){
					me.hide();
				};
				result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		getHtmlnetAbnormal : function(){
			return this.getHtmlWaring();
		},
		//提交成功
		rebulidsuccess : function(parameter){
			var me = this, result = {};			
				result['mask'] = true;
				result['iconClass'] = '';
				result['closeIsShow'] = true;
				result['closeFun'] = function(){
					me.hide();
				};
				result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		getHtmlsuccess : function(){
			var cfg = this.defConfig;
			return cfg.successTip;
		},
		//登陆超时loginTimeout
		rebulidloginTimeout : function(parameter){
			var me = this, result = {};			
				result['mask'] = true;
				result['closeIsShow'] = true;
				result['closeFun'] = function(){
					me.hide();
				};
				result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		getHtmlloginTimeout : function(){
			return this.getHtmlWaring();
		},
		//服务器错误
		rebulidserverError : function(parameter){
			var me = this, result = {};			
				result['mask'] = true;
				result['iconClass'] = '';
				result['closeIsShow'] = true;
				result['closeFun'] = function(){
					me.hide();
				};
				result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		getHtmlserverError : function(){
			return this.getHtmlWaring();
		},
		//失败状态
		rebulidsubFaild : function(parameter){
			var me = this, result = {};			
				result['mask'] = true;
				result['iconClass'] = '';
				result['closeIsShow'] = true;
				result['closeFun'] = function(){
					me.hide();
				};
				result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		getHtmlsubFaild : function(){
			return this.getHtmlWaring();
		},
		//余额不足
		rebulidInsufficientbalance : function(parameter){
			var me = this, result = {};			
				result['mask'] = true;
				result['closeIsShow'] = true;
				
				result['confirmIsShow'] = true;
				result['confirmText'] = '去充值';
				result['confirmFun'] = function(){
					window.open(_logOut + '/fund');
				};
				result['closeFun'] = function(){
					me.hide();
				};
				result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		getHtmlInsufficientbalance : function(){
			return this.getHtmlWaring();
		},
		//暂停销售
		rebulidpauseBet : function(parameter){
			var me = this, result = {};			
				result['mask'] = true;
				result['confirmText'] = '投 注';
				result['confirmIsShow'] = true;
				result['confirmFun'] = function(){
					var order = Games.getCurrentGameOrder(),
						i = 0;
						
					//删除指定类别的投注
					for (; i < parameter['data']['tplData']['balls'].length; i++) {
						//indexOf
						//order.removeData(parameter['data']['tplData']['balls'][i]['id']);	
						//当前注单去匹配
						for(j =0; j < order['orderData'].length; j++){
							if(order['orderData'][j]['id'] == parameter['data']['tplData']['balls'][i]['id']){
								order['orderData'].splice(j,1)
							}
						}
					};
					//提交投注
					Games.getCurrentGameSubmit().submitData();
				};
				result['closeText'] = '关 闭';
				result['closeIsShow'] = true;
				result['closeFun'] = function(){
					me.hide();
				};
				//生成消息
				parameter['data']['tplData']['msg'] = function(){
					var numText = [],
						gameConfig = Games.getCurrentGame().getGameConfig().getInstance(),
						k = 0;
						//输出暂停销售名称集合
						for (; k < parameter['data']['tplData']['balls'].length; k++) {
							var current = parameter['data']['tplData']['balls'][k]['type'],
								typeText = gameConfig.getTitleByName(current);
							if(me.arrIndexOf(typeText.join(''), numText) == -1){
								numText.push(typeText.join(''));								
							}
						};
						return '您的投注内容中“' + numText.join('') + '”已暂停销售，是否完成剩余内容投注？';
				};
				result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		getHtmlpauseBet : function(){
			var cfg = this.defConfig;
			return cfg.pauseBet;
		},
		//倍数超限
		rebulidmultipleOver : function(parameter){
			var me = this, result = {};			
				result['mask'] = true;
				result['iconClass'] = '';
				result['closeText'] = '关 闭';
				result['closeIsShow'] = true;
				result['closeFun'] = function(){
					me.hide();
				};
				//生成消息
				parameter['data']['tplData']['msg'] = function(){
					var numText = [],
						gameConfig = Games.getCurrentGame().getGameConfig().getInstance(),
						k = 0;
						//输出暂停销售名称集合
						for (; k < parameter['data']['tplData']['balls'].length; k++) {
							var current = parameter['data']['tplData']['balls'][k]['type'],
								typeText = gameConfig.getTitleByName(current);
							if(me.arrIndexOf(typeText.join(''), numText) == -1){
								numText.push(typeText.join(''));								
							}
						};
						return '您的投注内容中“' + numText.join('') + '”超出倍数限制，请调整！';
				};
				result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		getHtmlmultipleOver : function(){
			var cfg = this.defConfig;
			return cfg.multipleOver;
		},
		//无效字符
		rebulidinvalidtext : function(parameter){
			var me = this, result = {};			
				result['mask'] = true;
				result['confirmText'] = '刷新页面';
				result['confirmIsShow'] = true;
				result['confirmFun'] = function(){
					window.location.reload();
				};
				result['content'] = me.formatHtml(me.getHtmlinvalidtext(), parameter);
				me.show($.extend(result, parameter));
		},
		getHtmlinvalidtext : function(){
			var cfg = this.defConfig;
			return cfg.invalidtext;
		},
		//投注过期
		rebulidbetExpired : function(parameter){
			var me = this, result = {};			
				result['mask'] = true;
				result['closeText'] = '关 闭';
				result['closeIsShow'] = true;
				result['closeFun'] = function(){
					me.hide();
				};
				parameter['data']['tplData']['msg'] = function(){
						return '您好，' + parameter['data']['tplData']['bitDate']['expiredDate'] + '期 已截止销售，当前期为' + parameter['data']['tplData']['bitDate']['current'] + '期 ，请留意！';
				};
				result['content'] = me.formatHtml(me.getHtmlbetExpired(), parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		getHtmlbetExpired : function(){
			var cfg = this.defConfig;
			return cfg.betExpired;
		},
		//非法投注工具
		rebulidillegalTools : function(parameter){
			var me = this, result = {};			
				result['mask'] = true;
				result['confirmText'] = '刷新页面';
				result['confirmIsShow'] = true;
				result['confirmFun'] = function(){
					window.location.reload();
				};
				result['content'] = me.formatHtml(me.getHtmlbetExpired(), parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		getHtmlillegalTools : function(){
			return this.getHtmlWaring();
		},
		//高频封锁
		getHtmloverLimit : function(){
			return this.defConfig.overLimit;
		},
		rebulidoverLimit : function(parameter){
			var me = this, result = {},tplData = parameter['data']['tplData'],overMutipleData = parameter['data']['overMutipleData'],
				blockadeData = [],				
				multiple = 0,
				moneyType ,
				betDetail;
				
				result['mask'] = true;
				result['closeText'] = '确 定';
				result['closeIsShow'] = true;
				result['closeFun'] = function(){
					me.hide();
				};				
				$.each(overMutipleData, function(i){
					betDetail = overMutipleData[i].betDetail.length > 3 ? overMutipleData[i].betDetail.substr(0, 3) + '.. <a title='+overMutipleData[i].betDetail+'>详情</a>' : overMutipleData[i].betDetail;
					
					blockadeData.push(overMutipleData[i].webIssueCode + '期&nbsp;&nbsp;'+ overMutipleData[i].betMethod + '&nbsp;&nbsp;');
					//模式转换
					if($.trim(overMutipleData[i].moneyunit) == '1'){
						multiple = parseFloat(overMutipleData[i].multiple) / 10;
						moneyType = '元';
					}else if($.trim(overMutipleData[i].moneyunit) == '3'){
						multiple = parseFloat(overMutipleData[i].multiple)* 10;
						moneyType = '分';
					}else{
						multiple = parseFloat(overMutipleData[i].multiple);
						moneyType = '角';
					}
					blockadeData.push(betDetail + '&nbsp;&nbsp;('+moneyType+')本期最多投');				
					blockadeData.push('&nbsp;&nbsp;' + multiple +'倍</br>');
				
				});		
				
				tplData['contentinfo'] = blockadeData.join('');				
				
				result['content'] = me.formatHtml(me.getHtmlbetExpired(), parameter['data']['tplData']);				
				me.show($.extend(result, parameter));
				
		},
		//提交失败
		rebulidsubFailed : function(parameter){
			var me = this, result = {};			
				result['mask'] = true;
				result['closeText'] = '关 闭';
				result['closeIsShow'] = true;
				result['closeFun'] = function(){
					me.hide();
				};
				result['content'] = me.formatHtml(me.getHtmlbetExpired(), parameter['data']['tplData']);
				me.show($.extend(result, parameter));
		},
		getHtmlsubFailed : function(){
			return this.getHtmlWaring();
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
		//确认按钮 是否显示: confirmIsShow 名称: confirmText 事件: confirmFun
		//取消按钮 是否显示: cancelIsShow  名称: cancelText	事件: cancelFun
		//关闭按钮 是否显示: closeIsShow   名称: closeText	事件: closeFun
		show: function(data){
			var me = this, win = me.win;
			me.reSet();
			if(typeof data['data'] == 'undefined'){
				data['data'] = {};
			}
			data['data']['tplData'] = typeof data['data']['tplData'] == 'undefined' ? {} : data['data']['tplData'];
		
			if(!data){return}

			if(data['type']){
				me.doAction(data);
				return;
			}else{
				if(typeof data['tpl'] != 'undefined'){
					data['content'] = me.formatHtml(data['tpl'], data['data']['tplData']);
				}
			}

			//取消自动关闭时间缓存
			if(closeTime){
				clearTimeout(closeTime);
				closeTime = null;
			}

			//加入题目 && 内容
			me.setTitle(data['title'] || '温馨提示');
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
			if(data['normalCloseFun']){
				win.doNormalClose = data['normalCloseFun'];
			}
			if(data['confirmFun']){
				win.doConfirm = data['confirmFun'];
			}
			if(data['cancelFun']){
				win.doCancel = data['cancelFun'];
			}
			if(data['closeFun']){
				win.doClose = data['closeFun'];
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
			//限制窗口高度超过
			//window总体高度
			me.limitHeight();
			//richardgong 2015-08-04
			!data.needPrint && win.dom.find(".btn-link").remove();
			
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
		limitHeight: function(){
			var me = this,
				wHeight = $(window).height(),
				dom = me.getContainerDom(),
				domHeight = dom.outerHeight();

			if(domHeight > wHeight){
				dom.height(Math.floor(wHeight * 0.9));
				dom.css({
					'overflow':'auto'
				});
			}
		},
		getContainerDom : function(){
			var me = this;
			return me.win.getContainerDom();
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
			win.doNormalClose = function(){}; 
			win.setConfirmName('确 认');
			win.setCancelName('取 消');
			win.setCloseName('关 闭');
			win.reSetPosition();
		}
	}
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host[name] = Main;
	
})(phoenix, "GameMessage",  phoenix.Event);










