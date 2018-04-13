/**
 * Created by user on 16/3/23.
 */


(function(host, name, Game, undefined){
    var defConfig = {
            //游戏名称
            name:'lhc',
            basePath:staticFileContextPath+'/static/js/game/lhc/',
            baseNamespace:'phoenix.Games.LHC.'
        },
        instance,
        Games = host.Games;

    var pros = {
        init:function(){
            var me = this;

            //初始化事件放在子类中执行，以确保dom元素加载完毕
            me.eventProxy();
            //键盘事件代理
            me.keyboardEventProxy();
            //获取服务器端配置数据
            me.getDynamicConfig();
        },


        getGameConfig:function(){
            return host.Games.LHC.Config;
        },
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
				if(null != _awardGroups && _awardGroups.length > 0){
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
				}, 3000);
				return;
				
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
							
							//me.giveBonusGoup(htlms);	
							//Games.getCurrentGame().getCurrentGameMethod()	
						}else{//如果查询上级没有给可分配奖金组选择直接跳回用户中心首面
							setTimeout(function(){								
								Msg.show({
										mask:true,
										title:'温馨提示',
										content:"<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>没分配奖金组，请联系上一级或客服人员</h4></div>",			
										hideClose: true,									
										confirmIsShow:true,		
										confirmFun:function(){
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

})(phoenix, "LHC", phoenix.Game);










