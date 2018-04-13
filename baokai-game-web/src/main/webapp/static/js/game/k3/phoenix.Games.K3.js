

(function(host, name, Game, undefined){
	var defConfig = {
		//游戏名称
		name:'jsk3',
		basePath:staticFileContextPath + '/static/js/game/k3/',
		baseNamespace:'phoenix.Games.K3.' 
		
	},
	instance,
	Games = host.Games;
	
	var pros = {
		init:function(){
			var me = this;
			
			//初始化事件放在子类中执行，以确保dom元素加载完毕
			me.eventProxy();
			//获取服务器端配置数据
			me.getDynamicConfig();
		},
		getGameConfig:function(){
			return host.Games.k3.Config;
		},
		bonusGroupProce: function () {
		    //匹配当前彩种是否有奖金组			
		    var me = this,
				mask = phoenix.Mask.getInstance(),
				Msg = Games.getCurrentGameMessage(),
				htlms = '',
				userid,
				strArr = {},
				indexStr,
				isConBonus = false,  //是否有奖金组
				gameTypeC = Games.getCurrentGame().getName(),
				_lotteryId = Games.getCurrentGame().getGameConfig().getInstance().defConfig["lotteryId"],//当前彩种ID
				_awardGroups = Games.getCurrentGame().getGameConfig().getInstance().defConfig["awardGroups"],//当前奖金组状态
				_userLvl = Games.getCurrentGame().getGameConfig().getInstance().defConfig["userLvl"];

		    //检查初始化时此彩种是否有分配奖金组
		    if (typeof (_awardGroups) != 'undefined') {
		        _awardGroups = _awardGroups == '' ? '' : _awardGroups;
		        if (_awardGroups.length > 0) {
		            $.each(_awardGroups, function (i) {
		                if (_awardGroups[i].betType == 1) {
		                    isConBonus = true;
		                    return;
		                }
		            });
		        }
		    }
		    //判断是否为总代
		    if (_userLvl == 0) {
		        setTimeout(function () {
		            Msg.show({
		                mask: true,
		                title: '温馨提示',
		                content: "<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>您目前是总代，没有权限投注</h4></div>",
		                confirmIsShow: true,
		                hideClose: true,
		                confirmFun: function () {
		                    Games.getCurrentGameMessage().hide();
		                    window.location.href = _logOut + '/index';
		                }
		            });
		        }, 3000);
		        return;

		    } else if (!isConBonus) {
		        //根据彩种及用户查询可分配奖金组			
		        $.ajax({
		            url: Games.getCurrentGame().getGameConfig().getInstance().getQueryGameUserAwardGroupByLotteryIdUrl(),
		            dataType: 'json',
		            async: false,
		            success: function (data) {
		                if (data.length > 0) {
		                    $.each(data, function (i) {
		                        if (data[i].lotteryId == _lotteryId) {
		                            htlms += '<label><input type="radio" class="radio" name="radionGourp" pro_awardGroupId=' + data[i].awardGroupId + ' pro_lotterySeriesCode = ' + data[i].lotterySeriesCode + '>' + data[i].awardName + '</label> &nbsp;';
		                        }
		                    });

		                    me.giveBonusGoup(htlms);
		                    //Games.getCurrentGame().getCurrentGameMethod()	
		                } else {//如果查询上级没有给可分配奖金组选择直接跳回用户中心首面
		                    setTimeout(function () {
		                        Msg.show({
		                            mask: true,
		                            title: '温馨提示',
		                            content: "<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>没分配奖金组，请联系上一级或客服人员</h4></div>",
		                            hideClose: true,
		                            confirmIsShow: true,
		                            confirmFun: function () {
		                                window.location.href = _logOut + '/index';
		                                //Games.getCurrentGameMessage().hide();	
		                            }
		                        });
		                    }, 3000);
		                    return;
		                }
		            }
		        });

		    }
		},
		//前三后三的混合组选，需要手动拆分出组六和组三进行提交
		editSubmitData:function(data){
			var me = this,list = data['balls'],i = 0,len = list.length,type = '',singleList = [],balls = [],
				temp,len2,j;
			for(;i < len; i++){
				type = list[i]['type'];
				if(type == 'housan.zuxuan.hunhe' || type == 'housan.zuxuan.hezhi' || type == 'housan.zuxuan.baodan'){
					temp = Games.getCurrentGameOrder().getOrderById(list[i]['id'])['lotterys'];
					len2 = temp.length;
					j = 0;
					for(;j < len2;j++){
						/**
						ball: "10"
						id: 1
						moneyunit: 1
						multiple: 1
						type: "housan.zuxuan.zuxuanhezhi"
						**/
						if(temp[j][0] == temp[j][1] || temp[j][1] == temp[j][2] || temp[j][0] == temp[j][2]){
							singleList.push({ball:temp[j].join(','), id:list[i]['id'], moneyunit:list[i]['moneyunit'], multiple:list[i]['multiple'], type: 'housan.zuxuan.zusan'});
						}else{
							singleList.push({ball:temp[j].join(','), id:list[i]['id'], moneyunit:list[i]['moneyunit'], multiple:list[i]['multiple'], type: 'housan.zuxuan.zuliu'});
						}
					}
				}else{
					balls.push(list[i]);
				}
			}
			balls = balls.concat(singleList);
			
			data['balls'] = balls;
			
			return data;
		},
		//更新后台配置信息后，更新相关内容
		updateDynamicConfig:function(){
			var me = this,dConfig = me.getDynamicConfig(),lastballs = dConfig['lastballs'].split(',');
			
			if(dConfig['isstop'] == 1){
				setTimeout(function(){
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
					});
				}, 1000);
				return;
			}
			
			me.setCurrentNumber(dConfig['number']);
			me.setCurrentIssueCode(dConfig['issueCode']);
			
			//头部界面更新
			//当前期期号
			$('#J-lottery-info-number').html(dConfig['number']);
			$('#J-lottery-info-currentIssue').val(dConfig['issueCode']);
			//上一期期号
			$('#J-lottery-info-lastnumber').html(dConfig['lastnumber']);
			$('#lottery-info').html(dConfig['ballInfo']);
			//和值
			var sumNumber=0;
			//上期开奖号码
			$('#J-lottery-info-balls').find('em').each(function(i){
				$(this).attr("class","number"+lastballs[i]);
				sumNumber+=parseInt(lastballs[i]);
			});
			$('.lottery-info').find('strong').text(sumNumber);
			var sumType=$('.lottery-info').find('em').eq(0);
			var numberType=$('.lottery-info').find('em').eq(1);
			sumType.text(sumNumber>10?'大':'小');
            numberType.text(sumNumber%2 ==0 ? '双':'单');
			
			//新奖期已开出，清空数据缓存
			//Games.cacheData = {};
			Games.cacheData['frequency']= {};
			Games.cacheData['charts'] = {};
		
		},
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
						}else{
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
								htmlb += "<tr><td><a target='_black' href='/gameUserCenter/queryPlanDetail?planid=" + re[i].planid + "'> " + re[i].startWebIssueCode + "</a></td>";
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
	
})(phoenix, "k3", phoenix.Game);










