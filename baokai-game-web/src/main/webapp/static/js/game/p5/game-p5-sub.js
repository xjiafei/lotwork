

(function(host, name, Game, undefined){
	var defConfig = {
		//游戏名称
		name:'P5',
		basePath:staticFileContextPath+'/static/js/game/p5/',
		baseNamespace:'phoenix.Games.P5.' 
		
	},
	Games = host.Games,
	instance;
	
	var pros = {
		init:function(){
			var me = this;
			
			//初始化事件放在子类中执行，以确保dom元素加载完毕
			me.eventProxy();
			//获取服务器端配置数据
			me.getDynamicConfig();
		},
		getGameConfig:function(){
			return host.Games.P5.Config;
		},
		/*配置奖金组流程
		  1：查询cookice中actualBonusSet，用户在登录时创建此cookice值，
		  2：actualBonusSet为空时，执行查询可配置奖金组，（注：代理有多个奖金组可选，玩家单一不用再分配）
		  3：奖金组配置后刷新页面
		*/
		bonusGroupProce : function(){
			//匹配当前彩种是否有奖金组			
			var me = this,
				mask = phoenix.Mask.getInstance(),		
				Msg = Games.getCurrentGameMessage(),
				htlms = '',
				userid , 
				strArr = {},
				indexStr,
				isConBonus = false ,  //是否有奖金组
				gameTypeC =  Games.getCurrentGame().getName(),
				_lotteryId = Games.getCurrentGame().getGameConfig().getInstance().defConfig["lotteryId"],//当前彩种ID
				_awardGroups = Games.getCurrentGame().getGameConfig().getInstance().defConfig["awardGroups"],//当前奖金组状态
				_userLvl = Games.getCurrentGame().getGameConfig().getInstance().defConfig["userLvl"];
			
			//检查初始化时此彩种是否有分配奖金组
			if(typeof(_awardGroups) != 'undefined'){
				_awardGroups = _awardGroups == '' ? '' : _awardGroups; 
				if(_awardGroups.length > 0){
					$.each(_awardGroups, function (i){
						if(_awardGroups[i].betType == 1){						
							isConBonus = true ;
							return;
						}					
					}); 
				}				
			}			
						
			//判断是否为总代
			if(_userLvl == 0){
				setTimeout(function(){
					Msg.show({
						mask:true,
						title:'温馨提示',
						content:"<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>您目前是总代，没有权限投注</h4></div>",							
						confirmIsShow:true,
						hideClose: true,
						confirmFun:function(){						
							Games.getCurrentGameMessage().hide();	
							window.location.href = _logOut + '/index';
						}
					});
					return;
				}, 3000);	
				
				
			}else if(!isConBonus){	
				//根据彩种及用户查询可分配奖金组			
				$.ajax({
					url:Games.getCurrentGame().getGameConfig().getInstance().getQueryGameUserAwardGroupByLotteryIdUrl(), 
					dataType:'json',
					async:false,
					success:function(data){
						if(data.length > 0){
							$.each(data, function (i){
								if(data[i].lotteryId == _lotteryId){							
									htlms += '<label><input type="radio" class="radio" name="radionGourp" pro_awardGroupId='+ data[i].awardGroupId +' pro_lotterySeriesCode = '+ data[i].lotterySeriesCode+'>'+ data[i].awardName +'</label> &nbsp;';	
								}					
							}); 
							
							me.giveBonusGoup(htlms);	
							//Games.getCurrentGame().getCurrentGameMethod()	
						}else{//如果查询上级没有给可分配奖金组选择直接跳回用户中心首面
							setTimeout(function(){
								Msg.show({
									mask:true,
									title:'温馨提示',
									content:"<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>没分配奖金组，请联系上一级或客服人员</h4></div>",							
									confirmIsShow:true,	
									hideClose: true,									
									confirmFun:function(){
										window.location.href = _logOut + '/index';
										//Games.getCurrentGameMessage().hide();	
									}
								});
								return;
							}, 3000);	
							
						}
					}
				});			
				
			}
		},		
		//进行弹窗奖金组配置
		giveBonusGoup:function(htlms){		
			var mask = phoenix.Mask.getInstance(),				
				Msg = Games.getCurrentGameMessage();
				setTimeout(function(){
					Msg.show({				
						mask:true,				
						title:'温馨提示',				
						content:'<div class="text-center"><p class="text-title">请选择一个奖金组，便于您投注时使用。</p><p class="radio-list"> ' + htlms+'</p><p class="text-note">(注：您投注时使用的奖金组一经设定，不可修改。<a href="'+_logOut+'/applycenter/querybonusdetails/" target="_Blank">查看奖金详情</a>)</p></div>',
						confirmIsShow:true,		
						cancelIsShow:true,
						cancelText : '返回首页',
						closeIsShow :false,
						hideClose:true,					
						confirmFun:function(){	
							//确认提交选择奖金组	
							var pro_awardgroupid = $("input:radio[name='radionGourp']:checked").attr('pro_awardgroupid');						
								
							if(pro_awardgroupid == '' || pro_awardgroupid == undefined){ return false;}
							$.ajax({
								url:Games.getCurrentGame().getGameConfig().getInstance().getSaveProxyBetGameAwardGroupUrl() + '?awardGroupId='+pro_awardgroupid,
								dataType:'json',							
								//contentType: 'application/json',
								async:false,
								success:function(data){
									if(data['status'] == '1'){

										Msg.show({
											mask:true,				
											title:'温馨提示',				
											content:'<div class="bd text-center"><div class="pop-title"><i class="ico-success"></i><h4 class="pop-text">奖金组配置成功!	<br>刷新当前游戏页面。</h4></div></div>',
											closeIsShow :true,	
											hideClose: true,
											closeFun:function(){ 
												 location.reload();                  
												Games.getCurrentGameMessage().hide();				
											}	
										});
									}else if(data['status'] == '2'){
										Msg.show({
											mask:true,				
											title:'温馨提示',				
											content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您已经选择过奖金组!<br>刷新当前游戏页面。</h4></div></div>',
											closeIsShow :true,	
											hideClose: true,
											closeFun:function(){ 
												 location.reload();
											}	
										});
									}else{
										Msg.show({
											mask:true,
											title:'温馨提示',
											content:"<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>奖金组配置提交失败,<br />请检查网络并重新提交！</h4></div>",
											cancelIsShow:true,
											hideClose: true,
											cancelFun:function(){
												Games.getCurrentGameMessage().hide();	
											}
										});
									}	
									
								},
								error: function() {
									Msg.show({
										mask:true,
										title:'温馨提示',
										content:"<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>奖金组配置提交失败,<br />请检查网络并重新提交！</h4></div>",
										cancelIsShow:true,
										hideClose: true,
										cancelFun:function(){
											Games.getCurrentGameMessage().hide();	
										}
									});
									
								} 
							});
						},				
						cancelFun:function(){        
							window.location.href = _logOut + '/index';           
							Games.getCurrentGameMessage().hide();				
						}					
					});	
				}, 3000);					
			
		},	
		//（现不用拆单提交）
		editSubmitData : function(data){
			var me = this,list = data['balls'],i = 0,len = list.length,type = '',singleList = [],balls = [],
				temp,len2,j;
			for(;i < len; i++){
				type = list[i]['type'];				
					balls.push(list[i]);				
			}
			balls = balls.concat(singleList);
			
			data['balls'] = balls;
			
			return data;
		},
		//整个彩种停售弹层（，玩法停售直接隐藏）
		isLotteryStopSale:function(){	
			var isture = Games.getCurrentGame().getGameConfig().getInstance().defConfig["isLotteryStopSale"];	
			if(isture){
				setTimeout(function(){
					phoenix.Games.getCurrentGameMessage().show({
					   type : 'lotteryClose',
					   hideClose: true,
					   data : {
						   tplData:{
								//开始购买时间
								msg : '您好，当前彩种已停售!',
								//提示彩票种类
								lotteryType : ' <dd><span class="pic"><img src="'+staticFileContextPath+'/static/images/game/tancenglogo/llssc.jpg"/></span><a class="btn" href="/gameBet/llssc">去投注</a></dd><dd><span class="pic"><img src="'+staticFileContextPath+'/static/images/game/tancenglogo/ll115.jpg"/></span><a class="btn" href="/gameBet/ll115">去投注</a></dd>'
						   }
					   }
					});
				}, 3000);			 
				
			}	
		}
		
		
	
	};
	
	var Main = host.Class(pros, Game);
		Main.defConfig = defConfig;
		//游戏控制单例
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host.Games[name] = Main;
	
})(phoenix, "P5", phoenix.Game);

(function(host, name, GameMethod, undefined){
	var defConfig = {
		name:'sanxing.zhixuan.danshi',
		//iframe编辑器
		editorobj: '.content-text-balls',
		//FILE上传按钮
		uploadButton: '#file',
		//单式导入号码示例
		exampleText: '12345 33456 87898 <br />12345 33456 87898 <br />12345 33456 87898 ',
		//玩法提示
		tips: '五星直选单式玩法提示',
		//选号实例
		exampleTip: '这是单式弹出层提示',
		//中文 全角符号  中文
		checkFont : /[\u4E00-\u9FA5]|[/\n]|[/W]/g,
		//过滤方法
		filtration : /[\s]|[,]|[;]|[<br>]|[，]|[；]/i,
		//验证是否纯数字
		checkNum : /^[0-9]*$/,
		//单式玩法提示
		normalTips : '<p style="color:#888;font-size:12px;line-height:170%;">' + ['说明：',
					'1、请参照"标准格式样本"格式录入或上传方案。',
					'2、每一注号码之间的间隔符支持 回车 空格[ ] 逗号[,] 分号[;] 冒号[:] 竖线 [|]',
					'3、文件格式必须是.txt格式。',
					'4、文件较大时会导致上传时间较长，请耐心等待！',
					'5、将文件拖入文本框即可快速实现文件上传功能。',
					'6、导入文本内容后将覆盖文本框中现有的内容。'].join('<br>') + '</p>'
		
	},
	Games = host.Games,
	P5 = host.Games.P5;
	
	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;

			//IE Range对象
			me.ieRange = '';
			//正确结果
			me.vData = [];
			//所有结果
			me.aData = [];
			
			me.tData = [];
			//出错提示记录
			me.errorData = [];
			//重复记录
			me.sameData = [];
			//初级触发
			me.firstfocus = true;
			//机选标记
			me.ranNumTag = false;
			//初次投注不去重处理
			me.isFirstAdd = true;

			//在单式的情况下点击选择按钮
			//复位所有的彩种状态栏
			Games.getCurrentGameStatistics().addEvent('afterStatisReset', function(e, orderData){
				var that = this,
					cfg = that.defConfig;
					methodName = that.getGameMethodName();

				if(methodName == me.defConfig.name){
					that.multipleDom.setValue(cfg.multiple);
					that.moneyUnitDom.setValue(cfg.moneyUnit);
				}
			});
			
			Games.getCurrentGameOrder().addEvent('beforeAdd', function(e, orderData){
				var that = this,
					data = me.tData,
					html = '';

				if(orderData['type'] == me.defConfig.name){
					
					//使用去重后正确号码进行投注
					if(me.isFirstAdd){
						if(!me['ranNumTag']){
							//阻止本次提交
							orderData['lotterys'] = [];
							//改变去重控制标记
							me.isFirstAdd = null;
							//重新输出去重后号码
							me.updateData();
							//重新投注
							Games.getCurrentGameOrder().add(Games.getCurrentGameStatistics().getResultData());
						}
						
					}else{
						//如果存在重复和错误号进行提示
						if(me.errorData.join('') != '' || me.sameData.join('') != ''){
							me.ballsErrorTip();
						}
						//复位去重标记
						me.isFirstAdd = true;
					}
				}

			});
		},
		initFrame:function(){
			var me = this;
			me.win = me.container.find(me.defConfig.editorobj)[0].contentWindow;
			me.doc = me.win.document;
			
			me._bulidEditDom();

			//查看标准格式样本按钮
			var tip = host.Tip.getInstance();
			me.container.find('.balls-example-danshi-tip').click(function(e){
				e.preventDefault();
				var dom = $(this);
				tip.setText(me.getExampleText());
				tip.show(dom.outerWidth() + 10, 0, this);
			}).mouseout(function(){
				tip.hide();
			});
			
		},
		getExampleText:function(){
			return this.defConfig.exampleText;
		},
		rebuildData:function(){
			var me = this;
			me.balls = [
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
						];
		},
		buildUI:function(){
			var me = this;
			me.container.html(me.getHTML());
		},
		//单式不能反选
		reSelect:function(){
			
		},
		//单式没有选球dom
		batchSetBallDom:function(){
			
		},
		//获取默认提示文案
		getNormalTips: function(){
			return this.defConfig.normalTips
		},
		//显示默认提示文案
		showNormalTips: function(){
			var me = this;
			me.replaceText(me.getNormalTips.call(me));
			me.firstfocus = true;
		},
		//建立可编辑的文字区域
		_bulidEditDom: function(){
			var me = this,
				headHTML =	'';

			me.doc.designMode = 'On';//可编辑
			me.doc.contentEditable = true;
			//但是IE与FireFox有点不同，为了兼容FireFox，所以必须创建一个新的document。
			me.doc.open();
			headHTML='<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" />';
			headHTML=headHTML+'<style>*{margin:0;padding:0;font-size:14px;}</style>';
			headHTML=headHTML+'</head>';
			me.doc.writeln('<html>'+headHTML+'<body style="word-break: break-all">' + me.getNormalTips() + '</body></html>');
			me.doc.close();
			//绑定事件
			me.bindPress();
			//IE回车输出<br> 与 FF 统一；
			if(document.all){
				me.doc.onkeypress = function(){
					return me._ieEnter()
				};
			};
		},
		//IE回车修改
		_ieEnter: function(){
			var me = this,
				e = me.win.event;
			if(e.keyCode == 13){
				this._saveRange();
				this._insert("<br/>");
				return false;
			}
		},
		//编辑器中插入文字
		_insert: function(text) {//插入替换字符串
			var me = this;
				
			if (!!me.ieRange) {
				me.ieRange.pasteHTML(text);
				me.ieRange.select();
				me.ieRange = false; //清空下range对象
			} else {//焦点不在html编辑器内容时
				me.win.focus();
				if (document.all) {
					me.doc.body.innerHTML += text; //IE插入在最后
				} else {//Firefox
					var sel = win.getSelection();
					var rng = sel.getRangeAt(0);
					var frg = rng.createContextualFragment(text);
					rng.insertNode(frg);
				}
			}
		},
		//IE下保存Range对象
		_saveRange: function(){
			if(!!document.all&&!me.ieRange){//是否IE并且判断是否保存过Range对象
				var sel = me.doc.selection;
				me.ieRange = sel.createRange();
				if(sel.type!='Control'){//选择的不是对象
					var p = me.ieRange.parentElement();//判断是否在编辑器内
					if(p.tagName=="INPUT"||p == document.body)me.ieRange=false;
				}
			}
		},
		//返回结果HTML
		getHtml: function(){
			var me = this;
			return me.doc ? $(me.doc.body).html() : '';
		},
		//返回结果text
		getText: function(){
			var me = this;
			return me.doc ? $(me.doc.body).text() : '';
		},
		//修改HTML
		//返回结果HTML
		replaceText: function(text){
			var me = this,
				text = text.replace(/\n/g, '<br>');
				
			if(me.doc && text){
				$(me.doc.body).html(text);
			}
		},
		//绑定IFRAME按钮PRESS
		bindPress: function(){
			var me = this,
				uploadButton = me.container.find(me.defConfig.uploadButton),
				agentValue = window.navigator.userAgent.toLowerCase();
			//绑定按钮事件
			$(me.doc).bind('input',function(){
				me.updateData();
			})
			//iE不支持INPUT事件
			//而且IE propertychange事件不能绑定该DOM类型
			if(agentValue.indexOf('msie')>0){
				$(me.doc.body).bind('keyup',function(){
					me.updateData();
				})
				$(me.doc.body).bind('blur',function(){
					me.updateData();
				})
			}
			$(me.doc).bind('focus',function(){
				if(me.firstfocus){
					me.replaceText(' ');
					me.firstfocus = false;
				}	
			})
			$(me.doc).bind('blur',function(){
				var content = me.getText();
				if($.trim(content) == ''){
					me.showNormalTips();
				}
			})
			$(me.doc.body).bind('focus',function(){
				if(me.firstfocus){
					me.replaceText(' ');
					me.firstfocus = false;
				}	
			})
			$(me.doc.body).bind('blur',function(){
				var content = me.getText();
				if($.trim(content) == ''){
					me.showNormalTips();
				}
			})
			//绑定用户上传按钮
			uploadButton.bind('change', function(){
				var form = $(this).parent();
				me.checkFile(this, form);
			})
		},
		//处理各种结果
		iterator: function(data) {
			var me= this,
				cfg = me.defConfig,
				result = data,
				count = [],
				breakNum = 0,
				offset = 0;
			
			for (var i = 0; i < data.length; i++) {
				if(cfg.filtration.test(data.charAt(i))){
					count.push(data.substr(breakNum, i - breakNum));
					breakNum = i+1;
				}
			}
			
			return count;
		},
		//检测结果重复
		checkResult: function(data, array){
			//检查重复
			for (var i = array.length - 1; i >= 0; i--) {
				if(array[i].join('') == data){
					return false;
				}
			};
			return true;
		},
		//正则过滤输入框HTML
		//提取正确的投注号码
		filterLotters : function(data){
			var me = this,
				result = '';

			result = data.replace(/<br>+|&nbsp;+/gi, ' ');
			result = result.replace(/[\s]|[,]+|[;]+|[，]+|[；|]+/gi, ' ');
			result = result.replace(/<(?:"[^"]*"|'[^']*'|[^>'"]*)+>/g, ' ');
			result = result.replace(me.defConfig.checkFont,'') +  ' ';

			return result;
		},
		//检测选球是否完整，是否能形成有效的投注
		//并设置 isBallsComplete 
		checkBallIsComplete:function(data){
			var me = this,
				i = 0,
				result = [];

			if(me['isFirstAdd']){
					me.aData = [];
					me.vData = [];
					me.sameData = [];
					me.errorData = [];
					me.tData = [];

					me.vDataBack = {};
					me.aDataBack = {};
					me.tDataBack = {};
					me.sameDataBack = {};
					me.errorDataBack = {};

				//按规则进行拆分结果
				result = me.iterator(me.filterLotters(data)) || [];
				
				//判断结果
				for(;i<result.length;i++){
					var splitData = result[i].split(''),
						current = result[i];

					if(me.defConfig.checkNum.test(current) && current.length == me.balls.length){
						
						//1为正确重复结果 2正确结果[去重]
						(me['vDataBack'][current]) ? me['sameData'].push(splitData) : me['tData'].push(splitData);
						//create hash
						me['vDataBack'][current] = splitData;
						//正确结果[不去重]
						me['vData'].push(splitData);
					}else{

						//1为错误重复结果 2为错误结果[去重]
						(me['errorDataBack'][current]) ? me['sameData'].push(splitData) : me['errorData'].push(splitData);
						//错误结果[不去重]
						me['errorDataBack'][current] = splitData;
					}

					//全部结果
					(!me['aDataBack'][current]) ? me['aData'].push(splitData) : '';
					//所有结果[不去重]
					me['aDataBack'][current] = splitData;
				}
			}
			
			///校验
			if(me.vData.length > 0){
				me.isBallsComplete = true;
				if(me.isFirstAdd){
					return me.vData;
				}else{
					if(me.tData.length > 0){
						return me.tData;
					}else{
						return [];
					}	
				}
			}else{
				me.isBallsComplete = false;
				return [];
			}
		},
		//返回正确的索引
		countInstances: function(mainStr, subStr){
			var count = [];
			var offset = 0;
			do{
				offset = mainStr.indexOf(subStr, offset);
				if(offset != -1){
					count.push(offset);
					offset += subStr.length;
				}
			}while(offset != -1)
			return count;
		},
		//三项操作提示
		//显示正确项
		//排除错误项
		removeOrderError: function(){
			var me= this, result = me.vData.length > 0 ? '' : ' ';
			if(me.firstfocus){
				return;
			}
			for (var i = 0; i < me.vData.length; i++) {
				result += me.vData[i].join('') + '&nbsp;';
			};
			me.errorDataTips();
			if($.trim(result) == ''){
				me.showNormalTips();
				return;
			}
			me.replaceText(result);
			me.checkBallIsComplete(result);
		},
		//排除重复项
		removeOrderSame: function(){
			var me= this, result = me.aData.length > 0 ? '' : ' ';
			if(me.firstfocus){
				return;
			}
			for (var i = 0; i < me.aData.length; i++) {
				result += me.aData[i].join('') + '&nbsp;';
			}
			me.sameDataTips();
			me.replaceText(result);
			me.checkBallIsComplete(result);
		},
		//清空选区
		removeOrderAll: function(){
			var me=this;
			if(me.firstfocus){
				return;
			}
			me.replaceText(' ');
			me.sameData = [];
			me.aData = [];
			me.tData = [];
			me.vData = [];
			//清空选号状态
			Games.getCurrentGameStatistics().reSet();
			me.showNormalTips();
		},
		//检测上传
		checkFile: function(dom, form){
			var result = dom.value,
				fileext=result.substring(result.lastIndexOf("."),result.length),
				fileext=fileext.toLowerCase();
			if (fileext != '.txt') {
				alert("对不起，导入数据格式必须是.txt格式文件哦，请您调整格式后重新上传，谢谢 ！");            
				return false;
			}
			form[0].submit();
		},
		//接收文件
		getFile: function(result){
			var me = this,
				resetDom = me.container.find(':reset');

				if(!result){return};
				me.replaceText(result);
				me.firstfocus = false;
				me.updateData();
				resetDom.click();
		},
		//出错提示
		//暂时搁置
		errorTip: function(html, data){
			var me = this,
				start, end,
				indexData = [];
			
			alert(me.errorData.join())
		},
		sameDataTips: function(){
			var me = this,
				sameData = me.sameData,
				sameDataHtmlText = '',
				sameGroupText = '',
				msg = Games.getCurrentGameMessage(),
				indexData = [];

			if(sameData.join('') == ''){return};

			for (var i = 0; i < sameData.length; i++) {
				sameData[i] = sameData[i].join('');
			};
			sameDataHtmlText = '<h4 class="pop-text" style="display:block;font-weight:bold">以下号码重复，已进行自动过滤</h4><p class="pop-text" style="display:block">' + sameData.join(', ') + '</p>';

			msg.show({
				mask: true,
				content : ['<div class="bd text-center">',
								'<div class="pop-waring">',
									'<i class="ico-waring <#=icon-class#>"></i>',
									'<div style="display:inline-block;*zoom:1;*display:inline;vertical-align:middle">' + sameDataHtmlText + '</div>',
								'</div>',
							'</div>'].join(''),
				closeIsShow: true,
				closeFun: function(){
					this.hide();
				}
			})
		},
		errorDataTips: function(){
			var me = this,
				errorData = me.errorData,
				errorDataHtmlText = '',
				errorGroupText = '',
				msg = Games.getCurrentGameMessage(),
				indexData = [];
			if(errorData.join('') == ''){return};
			for (var i = 0; i < errorData.length; i++) {
				errorData[i] = errorData[i].join('');
			};
			errorDataHtmlText = '<h4 class="pop-text" style="display:block;font-weight:bold">以下号码错误，已进行自动过滤</h4><p class="pop-text" style="display:block">' + errorData.join(', ') + '</p>';
			msg.show({
				mask: true,
				content : ['<div class="bd text-center">',
								'<div class="pop-waring">',
									'<i class="ico-waring <#=icon-class#>"></i>',
									'<div style="display:inline-block;*zoom:1;*display:inline;vertical-align:middle">' + errorDataHtmlText + '</div>',
								'</div>',
							'</div>'].join(''),
				closeIsShow: true,
				closeFun: function(){
					this.hide();
				}
			})
		},
		//单式出错提示
		ballsErrorTip: function(html, data){
			var me = this,
				errorData = me.errorData,
				sameData = me.sameData,
				errorDataHtmlText = '',
				sameDataHtmlText = '',
				errorGroupText = '',
				sameGroupText = '',
				msg = Games.getCurrentGameMessage(),
				indexData = [];
		
			//重复号码
			if(sameData.join('') != ''){
				for (var i = 0; i < sameData.length; i++) {
					sameData[i] = sameData[i].join('');
				};
				sameDataHtmlText = '<h4 class="pop-text" style="display:block;font-weight:bold">以下号码重复，已进行自动过滤</h4><p class="pop-text" style="display:block">' + sameData.join(', ') + '</p>';
			}
			//错误号码
			if(errorData.join('') != ''){
				for (var i = 0; i < errorData.length; i++) {
					errorData[i] = errorData[i].join('');
				};
				errorDataHtmlText = '<h4 class="pop-text" style="display:block;font-weight:bold">以下号码错误，已进行自动过滤</h4><p class="pop-text" style="display:block">' + errorData.join(', ') + '</p>';
			}

			msg.show({
				mask: true,
				content : ['<div class="bd text-center">',
								'<div class="pop-waring">',
									'<i class="ico-waring <#=icon-class#>"></i>',
									'<div style="display:inline-block;*zoom:1;*display:inline;vertical-align:middle">' + sameDataHtmlText + errorDataHtmlText + '</div>',
								'</div>',
							'</div>'].join(''),
				closeIsShow: true,
				closeFun: function(){
					this.hide();
				}
			})
		},
		//复位
		//单式需提到子类方法实现
		reSet:function(){
			var me = this;
			me.isBallsComplete = false;
			me.rebuildData();
			me.updateData();
			if(!me.ranNumTag){
				me.showNormalTips();
			};
			//重置机选标记
			me.removeRanNumTag();
		},

		//生成后端参数格式
		makePostParameter: function(data, order){
			var me = this,
				result = [],
				data = order['lotterys'],
				i = 0;

			for (; i < data.length; i++) {
				result = result.concat(data[i].join(''));
			}
			return result.join(' ');
		},
		//获取组合结果
		getLottery:function(){
			var me = this, data = me.getHtml();
			if(data == ''){
				return;
			}
			//返回投注
			return me.checkBallIsComplete(data);
		},
		//单组去重处理
		removeSameNum: function(data) {
			var i = 0, result, me = this,
				numLen = this.getBallData()[0].length;
				len = data.length;
			result = Math.floor(Math.random() * numLen);
			for(;i<data.length;i++){
				if(result == data[i]){
					return arguments.callee.call(me, data);
				}
			}
			return result;
		},
		//清空重复号码记录
		emptySameData: function(){
			this.sameData  = [];
		},
		//清空错误号码记录
		emptyErrorData: function(){
			this.errorData = [];
		},
		//增加单式机选标记
		addRanNumTag: function(){
			var me = this;
			me.ranNumTag = true;
			me.emptySameData();
			me.emptyErrorData();
		},
		getTdata : function(){
			return this.tData; 
		},
		getOriginal:function(){
			var me = this,balls = me.getBallData(),
				len = balls.length,
				len2 = balls[0].length;
				i = 0,
				j = 0,
				row = [],
				tData = me.getTdata(),
				data = me.getHtml(),
				result = [];

			for(;i < len;i++){
				row = [];
				for(j = 0;j < len2;j++){
					if(balls[i][j] > 0){
						row.push(j);
					}
				}
				result.push(row);
			};

			if(tData.length > 0){
				result = me.getTdata();
			}
			return result;
		},
		//去除单式机选标记
		removeRanNumTag: function(){
			this.ranNumTag = false;
		},
		//限制随机投注重复
		checkRandomBets: function(hash,times){
			var me = this,
				allowTag = typeof hash == 'undefined' ? true : false,
				hash = hash || {},
				current = [],
				times = times || 0,
				len = me.getBallData().length,
				rowLen = me.getBallData()[0].length,
				order = Games.getCurrentGameOrder().getTotal()['orders'];

			//生成单数随机数
			current = me.createRandomNum(); 

			//如果大于限制数量
			//则直接输出
			if(Number(times) > Number(me.getRandomBetsNum())){
				return current;
			}

			//建立索引
			if(allowTag){
				for (var i = 0; i < order.length; i++) {
					if(order[i]['type'] == me.defConfig.name){
						var name = order[i]['original'].join('').replace(/,/g,'');
						hash[name] = name;
					}
				};
			}
			//对比结果
			if(hash[current.join('')]){
				times++;
				return arguments.callee.call(me, hash, times);
			}

			return current;
		},
		//生成一个当前玩法的随机投注号码
		//该处实现复式，子类中实现其他个性化玩法
		//返回值： 按照当前玩法生成一注标准的随机投注单(order)
		randomNum:function(){
			var me = this,
				i = 0, 
				current = [], 
				currentNum, 
				ranNum,
				order = null,
				dataNum = me.getBallData(),
				name = me.defConfig.name,
				lotterys = [],
				original = [];
			
			//增加机选标记
			me.addRanNumTag();

			current  = me.checkRandomBets();
			original = [current.join('').split('')];
			lotterys = me.combination(current);
				
			//生成投注格式
			order = {
				'type':  Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
				'original':original,
				'lotterys':lotterys,
				'moneyUnit': Games.getCurrentGameStatistics().getMoneyUnit(),
				'multiple': Games.getCurrentGameStatistics().getMultip(),
				'onePrice': Games.getCurrentGameStatistics().getOnePrice(),
				'num': lotterys.length
			};
			order['amountText'] = Games.getCurrentGameStatistics().formatMoney(order['num'] * order['moneyUnit'] * order['multiple'] * order['onePrice']);
			return order;		
		},
		getHTML:function(){
			//html模板
			var html_all = [];
				html_all.push('<div class="balls-import clearfix">');
					html_all.push('<form id="form1" name="form1" enctype="multipart/form-data" method="post" action="'+Games.getCurrentGame().getGameConfig().getInstance().getUploadPath()+'" target="check_file_frame" style="position:relative;padding-bottom:10px;">');
					html_all.push('<input name="file" type="file" id="file" size="40" hidefocus="true" value="导入" style="outline:none;-ms-filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=0);filter:alpha(opacity=0);opacity: 0;position:absolute;top:0px; left:0px; width:107px; height:30px;z-index:1;background:#000" />');
					html_all.push('<input type="button" class="balls-import-input" value="" onclick=document.getElementById("form1").file.click()><a class="balls-example-danshi-tip" href="#">查看标准格式样本</a>');
					html_all.push('<input type="reset" style="outline:none;-ms-filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=0);filter:alpha(opacity=0);opacity: 0;width:0px; height:0px;z-index:1;background:#000" />');
					html_all.push('<iframe src="'+Games.getCurrentGame().getGameConfig().getInstance().getUploadPath()+'" name="check_file_frame" style="display:none;"></iframe>');
					html_all.push('</form>');
					html_all.push('<div class="panel-select" ><iframe style="width:100%;height:100%;border:0 none;background-color:#F9F9F9;" class="content-text-balls"></iframe></div>');
					html_all.push('<div class="panel-btn">');
					html_all.push('<a class="remove-error" id="" href="javascript:void(0);"><i></i>删除错误项</a>');
					html_all.push('<a class="remove-same" id="" href="javascript:void(0);"><i></i>删除重复项</a>');
					html_all.push('<a class="remove-all" id="" href="javascript:void(0);"><i></i>清空文本框</a>');
					html_all.push('</div>');
				html_all.push('</div>');
			return html_all.join('');
		}
	};


	
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
		P5[name] = Main;
		
})(phoenix, 'Danshi', phoenix.GameMethod);




(function(host, name, GameTypes, undefined){
	var defConfig = {
		navDom : '#J-play-select .play-select-title'
	},
	//渲染实例
	instance,
	//游戏实例
	Games = host.Games;
	
	//渲染方法
	var pros = {
		init:function(cfg){
			var me = this;
			
			setTimeout(function(){
				me.divideNav();
			},0);
		},
		//划分导航层级
		divideNav: function(){
			var me = this,
				navDom = $(me.defConfig.navDom);

			navDom.find('li.p3sanxing').before('<div class="superior pailie3">排列3：</div>');
			navDom.find('li.p5houer').before('<div class="superior pailie5">排列5：</div>');
		}

	};
	
	var Main = host.Class(pros, GameTypes);
		Main.defConfig = defConfig;
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host[name] = Main;
	
})(phoenix, "P5GameTypes", phoenix.GameTypes);













(function(host, name, message, undefined){
	var defConfig = {
		
	},
	Games = host.Games,
	instance;

	var pros = {
		init: function(cfg){
			var me = this;
			Games.setCurrentGameMessage(me);
		}
	};
	
	var Main = host.Class(pros, message);
		Main.defConfig = defConfig;
		//游戏控制单例
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host.Games.P5[name] = Main;
	
})(phoenix, "Message", phoenix.GameMessage);









