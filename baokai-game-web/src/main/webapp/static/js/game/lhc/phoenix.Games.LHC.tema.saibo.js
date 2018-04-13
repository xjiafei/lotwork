/**
 * 2017/02/22
 * 特码 - 色波 玩法
 */

(function(host, GameMethod, undefined){

  var defConfig = {
    //选球倍率值容器
    ballOddDom:'.ball-odds',
    ruleDom: '.rule-text .text-box',
    exampleDom: '.prompt-text .text-box',
    name: 'tema.saibo',
    colorNameMap: ['red','blue','green'],
   colorArr: [0,0,1,1,2,2,0,0,1,1,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,1,1,2,2,0,0,1,1,2],
    colorMap: [
      {'enName':'red','cnName':'红','value':'1','lhcCode':'RED'},
      {'enName':'blue','cnName':'蓝','value':'2','lhcCode':'BLUE'},
      {'enName':'green','cnName':'绿','value':'3','lhcCode':'GREEN'}
    ],
    banboMap: [
        {'enName':'hongda','cnName':'红大','value':'1','lhcCode':'REDBIG'},
        {'enName':'landa','cnName':'蓝大','value':'2','lhcCode':'BLUEBIG'},
        {'enName':'lvda','cnName':'绿大','value':'3','lhcCode':'GREENBIG'},
        {'enName':'hongxiao','cnName':'红小','value':'4','lhcCode':'REDSMALL'},
        {'enName':'lanxiao','cnName':'蓝小','value':'5','lhcCode':'BLUESMALL'},
        {'enName':'lvxiao','cnName':'绿小','value':'6','lhcCode':'GREENSMALL'},
        {'enName':'hongdan','cnName':'红单','value':'7','lhcCode':'REDODD'},
        {'enName':'landan','cnName':'蓝单','value':'8','lhcCode':'BLUEODD'},
        {'enName':'lvdan','cnName':'绿单','value':'9','lhcCode':'GREENODD'},
        {'enName':'hongshuang','cnName':'红双','value':'10','lhcCode':'REDEVEN'},
        {'enName':'lanshuang','cnName':'蓝双','value':'11','lhcCode':'BLUEEVEN'},
        {'enName':'lvshuang','cnName':'绿双','value':'12','lhcCode':'GREENEVEN'}
    ],
    lotteryHistory: [] // 历史开奖数据
  }
  Games = host.Games,
  LHC = Games.LHC.getInstance();

  var pros = {
    init: function(cfg){
      var me = this;
      gameConfig = Games.getCurrentGame().getGameConfig().getInstance();
      me.setOdds(gameConfig);
      me.setTips(gameConfig);
      me.getLotteryHistory(gameConfig);
    },
    rebuildData:function(){
        var me = this;
        me.balls = [
            [-1,-1,-1],
            [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
        ];
    },
    buildUI:function(){
        var me = this;
        me.container.html(html_all);
    },
     setOdds: function(cfg){
        var me = this,
            datas = cfg.getGameOdds(),
			dataSapo = new Array(),
			tmpDataSapo = new Array(),
			dataBanbo = new Array();
			for(var i = 0; i < datas.length ; i++){
				var oddData = datas[i];
				if(oddData.name == 'saibo'){
					var childs=oddData.childs;
					for(var j = 0; j < childs.length ; j++){
						if(childs[j].name=='saibo'){
							dataSapo.push(childs[j]);
						}else{
							dataBanbo.push(childs[j]);							
						}
					}
				}
				
				
			}
        me.buildOdds(dataSapo, dataBanbo);
    },
    buildOdds: function(saiboArr, banboArr){		
		console.log('saiboArr' + JSON.stringify(saiboArr));
		console.log('banboArr' + JSON.stringify(banboArr));
        var me = this,
            $saiboDom = me.container.find('.saibo').find(defConfig.ballOddDom),
            $banboDom = me.container.find('.banbo').find(defConfig.ballOddDom);
        $.each($saiboDom, function(i, obj){
			var orderId = $(obj).parent().attr("order-id");
			console.log('orderId=' + orderId);
			$.each(defConfig.colorMap, function(j, saiboObj){
				console.log('saiboObj.value=' + saiboObj.value);
				if(saiboObj.value == orderId){
					$.each(saiboArr, function(k, saibo){
						if(saibo.lhcCode == saiboObj.lhcCode){
							$(obj).text(saibo.odds);
						}
					});
				}
			});
        });
        $.each($banboDom, function(i, obj){
			var orderId = $(obj).parent().attr("order-id");
			$.each(defConfig.banboMap, function(j, banboObj){
				if(banboObj.value == orderId){
					$.each(banboArr, function(k, banbo){
						if(banbo.lhcCode == banboObj.lhcCode){
							$(obj).text(banbo.odds);
						}
					});
				}
			});
        });

    },
    setTips: function(cfg){
        var me = this,
            data = null,
            arr = [];
		$.each(cfg.getGameTips(), function(i, item){
			if(item['name'] == 'tema' ){
				data = item.childs;
			}
		});	

        $.each(data,function(i,v){
            if(v['name'] == 'saibo' ){
              $.each(v.childs, function(i, v){
                var $domRule = me.container.find('.'+v['name']).find(defConfig.ruleDom),
                    $domExample = me.container.find('.'+v['name']).find(defConfig.exampleDom);
                    $domRule.html(v['rule']);
                    $domExample.html(v['example']);
              });
            }
        });
    }, getLotteryHistory: function(cfg){
      var me = this,
		  url = cfg.getHistoryLotteryAwardUrl();
		  
       $.ajax({
         url: url,
		 type: 'get',
		 dataType:'json',
		 data:{historynum:6}
       }).success(function(res){      
		    if (res.body.result.status == '1'){ // 成功
				 me.buildHistory(  res.body.result.gameIssues );
			} else { // 失败
				alert('获取历史开奖数据失败，请稍后刷新重试！');
			}
			
       }).fail(function(err){
			alert('获取历史开奖数据失败，请稍后刷新重试！');
       });
    },
    buildHistory: function(lotteryHistory){
      var me = this;
      var html_history = [];
      html_history.push('<div class="lottery-history saibo-history">');
      html_history.push('<h3 class="title">历史开奖</h3>');
      html_history.push('<ul class="ball-history-ul">');
	  
       var numberRecord, name;
      $.each(lotteryHistory, function(i, obj){
        numberRecord = obj.numberRecord.split(',');
        numberRecord = $.map(numberRecord, function(val){
          if (val <= 24){
            name = '小';
          } else if (val == 49){
            name = '和';
          } else if (val > 24){
            name = '大';
          }
          if (val%2 == 0){
            name += '双';
          } else {
            name += '单';
          }
          return {
            name: name,
            color: defConfig.colorNameMap[defConfig.colorArr[val-1]]
          };
        });

        html_history.push('<li><span class="period">'+ obj.webIssueCode +'</span>');
        $.each(numberRecord, function(i, val){
          if(i == 6){
            html_history.push('<span class="ball-diliver">+</span>');
          }
          html_history.push('<span class="ball-ordinary '+val.color+'">'+val.name+'</span>');
        });
        html_history.push('</li>');
      });

      html_history.push('</ul><div class="list-wave"></div></div>');

      var html_all = [];
      html_all.push(html_history.join(''));
      $(html_all).appendTo(me.container);
    }
  }


  //html模板
  var html_shengxiao = [];
  //色波
  var html_color = [];
  html_color.push('<div class="ball-list saibo">');
  html_color.push('<div class="game-title"><h3>色波</h3>');
  html_color.push('<div class="game-prompt">');
  html_color.push('<a class="rule-example"  href="javascript:;">玩法规则</a>');
  html_color.push('<a class="prompt-example" href="javascript:;">投注示例</a>');
  html_color.push('<div class="rule-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
  html_color.push('<div class="prompt-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
  html_color.push('</div>');
  html_color.push('</div>');

  html_color.push('<ul class="ball-list-ul">')

  $.each(defConfig.colorMap,function(i,v){
      html_color.push('<li order-id="' + v['value'] + '" data-param="action=ball&row=0&value='+ v['value'] +'&type=saibo">'+ '<span class="ball '+ v['enName'] +'">'+ v['cnName']+'</span><span class="ride">x</span><span class="ball-odds">1</span></li>');
  });
  html_color.push('</ul></div>');


  var html_banbo = [];
  html_banbo.push('<div class="ball-list banbo">');
  html_banbo.push('<div class="game-title"><h3>半波</h3>');
  html_banbo.push('<div class="game-prompt">');
  html_banbo.push('<a class="rule-example"  href="javascript:;">玩法规则</a>');
  html_banbo.push('<a class="prompt-example" href="javascript:;">投注示例</a>');
  html_banbo.push('<div class="rule-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
  html_banbo.push('<div class="prompt-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
  html_banbo.push('</div>');
  html_banbo.push('</div>');

  html_banbo.push('<ul class="ball-list-ul">');

  $.each(defConfig.banboMap,function(i,v){
      html_banbo.push('<li order-id="' + v['value'] + '" data-param="action=ball&row=1&value='+ v['value'] +'&type=banbo">'+ '<span class="ball '+ defConfig.colorNameMap[i%3] +'">'+ v['cnName']+'</span><span class="ride">x</span><span class="ball-odds">1</span></li>');
  });
  html_banbo.push('</ul></div>');


  //拼接所有
  var html_all = [];
  html_all.push(html_color.join(''));
  html_all.push(html_banbo.join(''));

  //继承GameMethod
  var Main = host.Class(pros, GameMethod);
  Main.defConfig = defConfig;
  //将实例挂在游戏管理器实例上
  LHC[defConfig.name] = new Main();

})(phoenix, phoenix.GameMethod);
