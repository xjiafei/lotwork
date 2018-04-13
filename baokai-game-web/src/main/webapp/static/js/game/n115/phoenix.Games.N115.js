

(function(host, name, Game, undefined){
	var defConfig = {
		//游戏名称
		name:'N115',
		basePath:staticFileContextPath + '/static/js/game/n115/',
		baseNamespace:'phoenix.Games.N115.' 
		
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
			return host.Games.N115.Config;
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
						hideClose: true,				
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
		editSubmitData:function(data){
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
	
})(phoenix, "N115", phoenix.Game);

