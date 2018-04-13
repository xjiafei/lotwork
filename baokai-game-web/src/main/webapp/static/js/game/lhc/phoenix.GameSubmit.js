
//游戏订单模块
(function(host, name, Event, undefined){
	var defConfig = {
		//游戏数据提交地址
		URL : '/gameBet/lhc/submit',
		noOddEnBetType : ["lianma.lianma.sanzhonger",
							"lianma.lianma.erzhongte",
							"zhengtema.lianxiaozhong.erlianxiao",
							"zhengtema.lianxiaozhong.sanlianxiao",
							"zhengtema.lianxiaozhong.silianxiao",
							"zhengtema.lianxiaozhong.wulianxiao",
							"zhengtema.lianxiaobuzhong.erlianxiao",
							"zhengtema.lianxiaobuzhong.sanlianxiao",
							"zhengtema.lianxiaobuzhong.silianxiao",
							"zhengtema.lianxiaobuzhong.wulianxiao"]
	},
	//缓存游戏实例
	instance,
	//获取游戏类
	Games = host.Games;

	var pros = {
		//初始化
		init:function(cfg){
			var me = this,
				cfg = me.defConfig;

			//提交数据加锁
			//防止多次重复提交
			me.postLock = null;
			//缓存方法
			Games.setCurrentGameSubmit(me);
		},
		//获取当前投注信息
		//提交的数据标准格式
		/**
		result = {
			//游戏类型
			gameType:'ssc',
			//订单总金额
			amount:100,

			//选球信息
			balls:[{ball:'1,2,3,4',type:'wuxing.zhixuan.fushi',moneyunit:0.1,multiple:1,id:2},{ball:'选球数据',type:'玩法类型',moneyunit:元角模式,multiple:倍数,id:ID编号}],
			//投注信息
			orders:[{number:'201312122204',multiple:2},{number:'期号',multiple:倍数}]

		};
		**/
		getSubmitData: function(){
			var me = this,result = {},
				ballsData = Games.getCurrentGameOrder().getOrderData(),
				j = 0;
			result['balls'] = [];
			for(var i in ballsData){
				var arr = ballsData[i];
				for(j=0; j<arr.length; j++) {
					var enBetType = arr[j]['type']+'.'+arr[j]['playType'];
					if(undefined == arr[j]['lotterys']['dan']){
						//console.log("-----------not dan tou---------------");
						if(defConfig.noOddEnBetType.indexOf(enBetType) > -1){
							result['balls'].push({
								'id':arr[j]['id'],
								'moneyunit':1,
								'multiple':1,
								'num':arr[j]['num'],
								'type':arr[j]['type']+'.'+arr[j]['playType'],
								'amount':arr[j]['amount'],
								'lotterys':arr[j]['lotterys'],
								'ball':arr[j]['content'],
								'odds':0 // 三中二,  二中特 有兩種賠率, 所以先放0
							});
						} else {
							result['balls'].push({
								'id':arr[j]['id'],
								'moneyunit':1,
								'multiple':1,
								'num':arr[j]['num'],
								'type':arr[j]['type']+'.'+arr[j]['playType'],
								'amount':arr[j]['amount'],
								'lotterys':arr[j]['lotterys'],
								//'content':arr[j]['content'],
								'ball':arr[j]['content'],
								'odds':arr[j]['odds']
							});
						}
					} else {
						//console.log("------------dan tou--------------");
						if(defConfig.noOddEnBetType.indexOf(enBetType) > -1){
							result['balls'].push({
								'id':arr[j]['id'],
								'moneyunit':1,
								'multiple':1,
								'num':arr[j]['num'],
								'type':arr[j]['type']+'.'+arr[j]['playType'],
								'amount':arr[j]['amount'],
								'lotterys':"dan:" + arr[j]['lotterys']['dan'] + ";tuo:" + arr[j]['lotterys']['tuo'],
								//'content':arr[j]['content'],
								'ball':"胆:" + arr[j]['content']['dan'] + ";拖:" + arr[j]['content']['tuo'],
								'odds':0// 三中二,  二中特 有兩種賠率, 所以先放0
							});
						} else {
							result['balls'].push({
								'id':arr[j]['id'],
								'moneyunit':1,
								'multiple':1,
								'num':arr[j]['num'],
								'type':arr[j]['type']+'.'+arr[j]['playType'],
								'amount':arr[j]['amount'],
								'lotterys':"dan:" + arr[j]['lotterys']['dan'] + ";tuo:" + arr[j]['lotterys']['tuo'],
								//'content':arr[j]['content'],
								'ball':"胆:" + arr[j]['content']['dan'] + ";拖:" + arr[j]['content']['tuo'],
								'odds':arr[j]['odds']
							});
						}
					}
					
				}
			}
			result['isTrace'] = 0;
			result['orders'] = [];
			//获得当前期号
			result['orders'].push({'number':Games.getCurrentGame().getCurrentNumber(),
			'issueCode':Games.getCurrentGame().getDynamicConfig().issueCode});
			//总金额
			result['amount'] = Games.getCurrentGameOrder().getTotal()['amount'];
			
			var _awardGroups = Games.getCurrentGame().getGameConfig().getInstance().defConfig["awardGroups"]
			var awardGroupId = null;
			for(var i = 0 ; i < _awardGroups.length ; i++){
				awardGroupId = _awardGroups[i].gid;
			}
			result['awardGroupId'] = awardGroupId;
			return result;
		},
		//执行请求锁定动作
		doPostLock: function(){
			var me = this;
			me.postLock = true;
		},
		//取消请求锁定动作
		cancelPostLock: function(){
			var me = this;
			me.postLock = null;
		},
		//清空数据缓存
		clearData : function(){
			var order = Games.getCurrentGameOrder();
			//清空订单
			order.reSet();

			//清空
			Games.getCurrentGame().getCurrentGameMethod().reSet();
		},
		//注单成功后，更新我的方案，我的追号及余额
		afterSubmitSuccess: function () {
			var htmls = '', htmlb = '', me = this;
			try {
				//我的方案数据渲染
				$.ajax({
					dataType: 'json',
					cache: false,
					url: Games.getCurrentGame().getGameConfig().getInstance().getUserOrdersUrl(),
					cache: false,
					success: function (re) {
						if($.isEmptyObject(re)) {
							return;
						}else{
							$('#J-gameHistory').html('');
							$.each(re, function (i) {
								//因html append 關係，各span 銜接處需要多個空行，以防跑版
								htmls += '<li><a href="/gameUserCenter/queryOrderDetail?orderId='+ re[i].orderId + '" target="_black"><span>'+ re[i].webIssueCode+'</span> <span class="yellow"><dfn>&yen;</dfn>'+ me.formatMoney(re[i].totamount / 10000) +'.00</span>';
								if (Number(re[i].status) == 2) {

									htmls += ' <span class="price color-red"><dfn>&yen;</dfn>' + me.formatMoney(re[i].totwin / 10000) + '</span></a></li>';
								} else {
									htmls += ' <span>'+ re[i].statusName + '</span></a></li>';
								}

							});
						}

						$('#J-gameHistory').append(htmls);
					}
				});

			} catch (err) {
				alert("网络异常，读取信息失败");
			}

		},
		//提交游戏数据
		submitData: function(){
			var me = this,
				data = me.getSubmitData(),
				message = Games.getCurrentGameMessage();
			//提示至少选择一注
			if(data.balls.length <= 0){
				message.show({
					type : 'mustChoose',
					msg : '请至少选择一注投注号码！',
					data : {
						tplData : {
							msg : '请至少选择一注投注号码！'
						}
					}
				});
				//请求解锁
				me.cancelPostLock();
				return;
			}
			//检查是否有未输入金额
			if(Games.getCurrentGameOrder().checkOrderAmount()) return;

			var isTure = true;
			//彩种检查
			message.show({
				type : 'checkLotters',
				msg : '请核对您的投注信息！',
				confirmIsShow: true,
				confirmFun : function(){
					console.log(data)
					//判断加锁
					if (me.postLock) {
						return;
					} else {
						//加锁
						me.doPostLock();
						me.fireEvent('beforeSubmit');
					}
     					$.ajax({
						url: me.defConfig.URL,
						data: JSON.stringify(data),
						contentType: "application/json;charset=UTF-8",
						dataType: 'json',
						method: 'POST',
						beforeSend: function () {
							if (isTure) {
								$('.confirm').html('提交中...');
								$('.cancel').css('display', 'none').attr("disabled", "true");
								isTure = false;
							} else {
								return false;
							}
						},
						success: function(r){
						//返回消息标准
						// {"isSuccess":1,"type":"消息代号","msg":"返回的文本消息","data":{xxx:xxx}}
							if(Number(r['isSuccess']) == 1){
								r.data.tplData["url"]="/gameUserCenter/queryOrdersEnter?time=7";
								r.data.tplData["gameType"]= '投注记录';
								message.show(r);
								me.clearData();
								me.fireEvent('afterSubmitSuccess', r);
							}else{
								message.show(r);
								me.fireEvent('afterSubmitError', r);
							}
							me.afterSubmitSuccess();
							//请求解锁
							me.cancelPostLock();
						},
						complete: function(){
							me.fireEvent('afterSubmit');
						}
					});
				},
				cancelIsShow: true,
				cancelFun : function(){
					//请求解锁
					me.cancelPostLock();
					this.hide();
				},
				normalCloseFun: function(){
					//请求解锁
					me.cancelPostLock();
				},
				callback: function(){

				},
				data : {
					tplData : {
						//当期彩票详情
				        lotteryDate : data['orders'][0]['number'],
				        //彩种名称
				        lotteryName : Games.getCurrentGame().getGameConfig().getInstance().getGameTypeCn(),
				        //投注详情
				        lotteryInfo : function(){
				        	var html = '',
				        		balls = data['balls'];
				        	for (var i = 0; i < balls.length; i++) {
				        		var current = balls[i];
				        		html += '<div style="height:25px;line-height:25px;">' + Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(current['type']).join('') + ' ' + current['ball'] + '</div>';
				        	};
				        	return html;
				        },
				        //彩种金额
				        lotteryamount : data['amount']+'',
				        //付款帐号
				        lotteryAcc : Games.getCurrentGame().getDynamicConfig()['username']
					}
				}
			});
		},
		formatMoney:function(num){
			var num = Number(num),
				re = /(-?\d+)(\d{3})/;
			if(Number.prototype.toFixed){
				num = (num).toFixed(2);
			}else{
				num = Math.round(num*100)/100
			}
			num  =  '' + parseInt(num);
			while(re.test(num)){
				num = num.replace(re,"$1,$2");
			}
			return num;
		}
	};

	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host[name] = Main;

})(phoenix, "GameSubmit", phoenix.Event);
