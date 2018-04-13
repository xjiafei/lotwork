
//游戏订单模块
(function (host, name, Event, undefined) {
    var defConfig = {
        //游戏数据提交地址
       /* URL: 'saveGameOrder',
        //手续费获取地址
        handlingChargeURL: 'jsonResult/handlingCharge'*/
		URL: '',
        //手续费获取地址
        handlingChargeURL: ''
    },
    //缓存游戏实例
	instance,
    //获取游戏类
	Games = host.Games;

    var pros = {
        //初始化
        init: function (cfg) {
            var me = this,
				cfg = me.defConfig;
				//me.URL= Games.getCurrentGame().getGameConfig().getInstance().submitUrl();
				//手续费获取地址
				//me.handlingChargeURL= Games.getCurrentGame().getGameConfig().getInstance().getHandingChargeUrl();

            //提交数据加锁
            //防止多次重复提交
            me.postLock = null;
            
            //缓存方法
            Games.setCurrentGameSubmit(me);
            //定单提交后解锁，按扭恢复
            me.addEvent('afterSubmit', function () {
                me.cancelPostLock();
               // $('.confirm').html('确认');
            });

        },
        //获取当前投注信息
        //提交的数据标准格式
        /**
        result = {
        //游戏类型
        gameType:'ssc',
        //订单总金额
        amount:100,
        //是否是追号
        isTrace:1,
        //追号追中即停(1为按中奖次数停止，2为按中奖金额停止)
        traceWinStop:1,
        //追号追中即停的值
        traceStopValue:1,
        //选球信息
        balls:[{ball:'1,2,3,4',type:'wuxing.zhixuan.fushi',moneyunit:0.1,multiple:1,id:2,fileMode:1},{ball:'选球数据',type:'玩法类型',moneyunit:元角模式,multiple:倍数,id:ID编号}],
        //投注信息
        orders:[{number:'201312122204',multiple:2},{number:'期号',multiple:倍数}]
			
        };
        **/
        //将数字保留两位小数并且千位使用逗号分隔
        formatMoney: function (num) {
            var num = Number(num),
			re = /(-?\d+)(\d{3})/;

            if (Number.prototype.toFixed) {
                num = (num).toFixed(2);
            } else {
                num = Math.round(num * 100) / 100
            }
            num = '' + num;
            while (re.test(num)) {
                num = num.replace(re, "$1,$2");
            }
            return num;
        },
        getSubmitData: function () {
            var me = this, result = {},
				ballsData = Games.getCurrentGameOrder()['orderData'],
				i = 0,
				len = ballsData.length,
				traceInfo = Games.getCurrentGameTrace().getResultData(),
				j = 0,
				len2 = traceInfo['traceData'].length;


            result['gameType'] = Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"];//改取config.js值,
            result['isTrace'] = traceInfo['traceData'].length > 0 ? 1 : 0;
            result['traceWinStop'] = Games.getCurrentGameTrace().getIsWinStop();
            result['traceStopValue'] = Games.getCurrentGameTrace().getTraceWinStopValue();
            result['balls'] = [],
			result['ordersNumber'] = '';  //期号显示（普通投注与追号）
            for (; i < len; i++) {
                result['balls'].push({
                    'id': ballsData[i]['id'],
                    'ball': ballsData[i]['postParameter'],
                    'type': ballsData[i]['type'],
                    'moneyunit': ballsData[i]['moneyUnit'],
                    'multiple': ballsData[i]['multiple'],
                    'num': ballsData[i]['num']//传递投注数
                });
            }
            result['orders'] = [];
            //非追号
            if (result['isTrace'] < 1) {
                //获得当前期号
                result['orders'].push({ 'number': Games.getCurrentGame().getCurrentNumber(), 'issueCode': Games.getCurrentGame().getCurrentIssueCode(), multiple: 1 });
                //总金额
                result['amount'] = Games.getCurrentGameStatistics().formatMoney(Games.getCurrentGameOrder().getTotal()['amount']);
                result['ordersNumber'] = '第 ' + Games.getCurrentGame().getCurrentNumber() + ' 期'; //
				result['onlyAmount'] = '';
            } else {
                //追号
                for (; j < len2; j++) {
                    result['orders'].push({ 'number': traceInfo['traceData'][j]['traceNumber'], 'issueCode': Number(traceInfo['traceData'][j]['issueCode']), 'multiple': traceInfo['traceData'][j]['multiple'] });
                }
                result['ordersNumber'] = "第 " + result['orders'][0]['number'] + "  至  " + result['orders'][len2 - 1]['number'] + "   共  <strong class='color-red'>" + len2 + "</strong></span>  期";
                //总金额
                result['amount'] = Games.getCurrentGameStatistics().formatMoney(traceInfo['amount'], 2);
				//单期金额
				result['onlyAmount'] = traceInfo.orderData['amount'];
            }

            return result;
        },
        //执行请求锁定动作
        doPostLock: function () {
            var me = this;
            me.postLock = true;
        },
        //取消请求锁定动作
        cancelPostLock: function () {
            var me = this;
            me.postLock = null;
        },
        //清空数据缓存
        clearData: function () {
            var order = Games.getCurrentGameOrder();
            //清空订单
            order.reSet();
            //添加取消编辑
            order.cancelSelectOrder();
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
						if($.isEmptyObject(re)){
							return;
						}else if(phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"]=='jsk3'||phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"]=='ahk3'){
							//K3中我的追号与其它游戏模板不同 
							$('#program-user-orders > tbody').html('');
							$.each(re, function (i) {
								htmls += "<tr><td>" + re[i].orderCode + "</td>";
								htmls += "<td>" + re[i].webIssueCode + "</td>";
								htmls += "<td><span class='price'><dfn>&yen;</dfn>" + me.formatMoney(re[i].totamount / 10000) + "</span></td>";

								if (Number(re[i].status) == 2) {
									htmls += "<td >中<span class='price color-red'><dfn>&yen;</dfn>" + me.formatMoney(re[i].totwin / 10000) + "</span></td>";
								} else {
									htmls += "<td >" + re[i].statusName + "</td>";
								}
								htmls += "<td>" + (re[i].parentType==2?'是':'否') + "</td>";
								htmls += "<td>" + re[i].formatSaleTime + "</td>";
								htmls += "<td><a target='_black' href='/gameUserCenter/queryOrderDetail?orderId=" + re[i].orderId + "' >查看</a></td></tr>";
							});
						}
						else{
							$('#program-user-orders > tbody').html('');
							$.each(re, function (i) {
								htmls += "<tr><td><a target=\"_black\" href=\"/gameUserCenter/queryOrderDetail?orderId=" + re[i].orderId + "\"> " + re[i].webIssueCode + "</a></td>";
								htmls += "<td><span class='price'><dfn>&yen;</dfn>" + me.formatMoney(re[i].totamount / 10000) + "</span></td>";

								if (Number(re[i].status) == 2) {
									htmls += "<td >中<span class='price color-red'><dfn>&yen;</dfn>" + me.formatMoney(re[i].totwin / 10000) + "</td></span></tr>";
								} else {
									htmls += "<td >" + re[i].statusName + "</td></tr>";
								}

							});
						}

						$('#program-user-orders > tbody').append(htmls);
                    }
                });

                //我的追号数据渲染
				$.ajax({
                    dataType: 'json',
                    cache: false,                    
					url: Games.getCurrentGame().getGameConfig().getInstance().getUserPlansUrl(),                   
					cache: false,
                    success: function (re) {
						if($.isEmptyObject(re)){
							return;
						}else{
							$('#program-user-plans > tbody').html('');
							$.each(re, function (i) {
								htmlb += "<tr><td><a target=\"_black\" href=\"/gameUserCenter/queryPlanDetail?planid=" + re[i].planid + "\"> " + re[i].startWebIssueCode + "</a></td>";
								htmlb += "<td>" + Number(re[i].finishIssue) + "/" + Number(re[i].totalIssue) + "</td>";
								htmlb += "<td><span class='price'><dfn>&yen;</dfn>" + me.formatMoney(re[i].totamount / 10000) + " </span></td>";
								switch (re[i].status) {
									case 0: htmlb += "<td >未开始</td></tr>"; break;
									case 1: htmlb += "<td >进行中</td></tr>"; break;
									case 2: htmlb += "<td >已结束</td></tr>"; break;
									case 3: htmlb += "<td >已终止</td></tr>"; break;
									case 4: htmlb += "<td >暂停</td></tr>"; break;
									default: htmlb += "<td ></td></tr>"; break;
								}
							});
						}						
						$("#program-user-plans > tbody").append(htmlb);
                    }
                });


            } catch (err) {
                alert("网络异常，读取信息失败");
            }



        },
		//余额查询
		balanceInquiry : function(htmlObj){
			$.ajax({
				dataType:'json',
				cache:false,
				url:Games.getCurrentGame().getGameConfig().getInstance().getUserBalUrl(),
				data:'',
				success:function(data){
					var _userBall = data == '' ? 0 : data,
					_userBallF = phoenix.util.formatMoney( _userBall/10000) ;					
					$("#"+htmlObj).html(_userBallF);
					
				}
			});
		},
        //提交游戏数据
        submitData: function () {
            var me = this,
				data = me.getSubmitData(),
				message = Games.getCurrentGameMessage();

            //提示至少选择一注
            if (data.balls.length <= 0) {
                message.show({
                    type: 'mustChoose',
                    msg: '请至少选择一注投注号码！',
                    data: {
                        tplData: {
                            msg: '请至少选择一注投注号码！'
                        }
                    }
                });
                //请求解锁
                me.cancelPostLock();
                return;
            }
            var isTure = true;
            //彩种检查            
            message.show({
                type: 'checkLotters',
                msg: '请核对您的投注信息！',
                confirmIsShow: true,
                confirmFun: function () {
                    //判断加锁
                    if (me.postLock) {
                        return;
                    } else {
                        //加锁
                        me.doPostLock();
                        me.fireEvent('beforeSubmit');
                    }
                    data.ordersNumber = undefined; //去除ordersNumber，后台不接收
					data.onlyAmount = undefined;
                    data.amount = phoenix.util.formatFloat(data.amount);

                    $.ajax({
                        url: Games.getCurrentGame().getGameConfig().getInstance().submitUrl(),
                        data: JSON.stringify(Games.getCurrentGame().editSubmitData(data)),
                        dataType: 'json',
                        method: 'POST',
                        cache: false,
                        contentType: "application/json; charset=utf-8",
                        beforeSend: function () {
                            if (isTure) {
                                $('.confirm').html('提交中...');
                                $('.cancel').css('display', 'none').attr("disabled", "true");
                                isTure = false;
                            } else {
                                return false;
                            }
                        },
                        success: function (r) {
                            //返回消息标准
                            // {"isSuccess":1,"type":"消息代号","msg":"返回的文本消息","data":{xxx:xxx}}
                            if (Number(r['isSuccess']) == 1) {
								
								//推荐查询地址
								var dataTrace = Number(data['isTrace']) > 0 ? 1 : 0;
								
								if(dataTrace > 0){
									r.data.tplData["url"]="/gameUserCenter/queryPlans?time=7";
									r.data.tplData["gameType"]= '追号记录';
									
								}else{
									r.data.tplData["url"]="/gameUserCenter/queryOrdersEnter?time=7";
									r.data.tplData["gameType"]= '投注记录';
								}

                                r.needPrint = true;
								message.show(r);                          
                                //添加打印按钮 richardgong 2015-07-23            
                                message.win.dom.find(".btn-link").remove();   

                                message.win.dom.append($('<a  target="view_window" href="/gameBet/downLoadOrder?orderCode='+r.data.projectId+'" class="btn btn-link">打印订单</a>'));
                                
                                me.clearData();  																
								me.balanceInquiry('spanBall');								
                                me.afterSubmitSuccess();
								setTimeout(function(){ 
									Games.getCurrentGameTrace().hide();
									$("html,body").animate({scrollTop:$("#J-top-game-menu").offset().top},1000);
									}, 1500);
								
                            } else {
                                message.show(r);								
                            }

                        },
                        complete: function () {
                            me.fireEvent('afterSubmit');
                            isTure = true;
                            $('.cancel').css('display', 'display').removeAttr("disabled");
                        },
                        error: function (xhr) {
							if(xhr.status == '404' || xhr.status == '0' || xhr.status == '401' ){
								message.show({
									mask: true,
									title: '温馨提示',
									content: "<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>登录超时 请重新登入！</h4></div>",
									cancelIsShow: true,
									cancelFun: function () {
										window.location.reload();                              
									}
								});
							}else{
								message.show({
									mask: true,
									title: '温馨提示',
									content: "<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>方案提交失败,<br />请检查网络并重新提交！</h4></div>",
									cancelIsShow: true,
									cancelFun: function () {
										message.hide();
									}
								});
							}
                            
                        }
                    });
                },
                cancelIsShow: true,
                cancelFun: function () {
                    //请求解锁
                    me.cancelPostLock();
                    this.hide();
                },
                normalCloseFun: function () {
                    //请求解锁
                    me.cancelPostLock();
                },
                callback: function () {
					
					var backOutStartFee =Number(Games.getCurrentGame().getGameConfig().getInstance().defConfig["backOutStartFee"]) / 10000 ,//返手续费起始金额
						backOutRadio = Number(Games.getCurrentGame().getGameConfig().getInstance().defConfig["backOutRadio"]) / 10000 ,//返金额率	
						backAmount = Games.getCurrentGameStatistics().ToForamtmoney(data['amount']);
						
					if(backAmount > backOutStartFee){
						$('#showLotteryChareg').show();
						message.getContentDom().find('.handlingCharge').html(Games.getCurrentGameStatistics().formatMoney(backAmount * backOutRadio, 3));
												
					}	
					
                },
                data: {
                    tplData: {
                        //当期彩票详情
                        lotteryDate: data['ordersNumber'],
                        //彩种名称
                        lotteryName: Games.getCurrentGame().getGameConfig().getInstance().getGameTypeCn(),
                        //投注详情
                        lotteryInfo: function () {
                            var html = '',
				        		balls = data['balls'];
                            for (var i = 0; i < balls.length; i++) {
                                var current = balls[i];
                                html += '<div style="line-height:25px;">' + Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(current['type']).join('') + ' ' + current.ball + '</div>';
                            };
                            return html;
                        },
                        //彩种金额
                        lotteryamount: data['amount'],
                        //付款帐号
                        lotteryAcc: Games.getCurrentGame().getDynamicConfig()['username'],
						
						lotteryOnlyAmount : data['onlyAmount'],
						
						lotteryTraceInfo : function (){
							var html = '';
							if(data['isTrace'] > 0){
								html = data['orders'];
							}
							return html;
						}
						
						
						
                    }
                }
            });
        }
    };

    var Main = host.Class(pros, Event);
    Main.defConfig = defConfig;
    Main.getInstance = function (cfg) {
        return instance || (instance = new Main(cfg));
    };
    host[name] = Main;

})(phoenix, "GameSubmit", phoenix.Event);










