/**
 * 2017/02/22
 * 特码 - 一肖 玩法
 */

(function(host, GameMethod, undefined){

  var defConfig = {
    //选球倍率值容器
    oddDom:'.game-odds2 ol',
    ruleDom: '.rule-text .text-box',
    exampleDom: '.prompt-text .text-box',
    name: 'zhengtema.lianxiaozhong',
    colorNameMap: ['red','blue','green'],
    colorArr: [0,0,1,1,2,2,0,0,1,1,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,1,1,2,2,0,0,1,1,2],
    zodiacsMap:  [
        {'enName':'shu','cnName':'鼠','value':'1'},
        {'enName':'niu','cnName':'牛','value':'2'},
		{'enName':'hu','cnName':'虎','value':'3'},
		{'enName':'tu','cnName':'兔','value':'4'},
		{'enName': 'long','cnName':'龙','value':'5'},
		{'enName':'she','cnName':'蛇','value':'6'},
		{'enName': 'ma','cnName':'马','value':'7'},
        {'enName':'yang','cnName':'羊','value':'8'},      
        {'enName':'hou','cnName':'猴','value':'9'},    
        {'enName':'ji','cnName':'鸡','value':'10'},    
        {'enName': 'gou','cnName':'狗','value':'11'},    
        {'enName':'zhu','cnName':'猪','value':'12'}
      ],
    oddsMap: {
      'erlianxiao': {'cnName': '二连肖', 'limit': 2 },
      'sanlianxiao': {'cnName': '三连肖', 'limit': 3},
      'silianxiao': {'cnName': '四连肖', 'limit': 4},
      'wulianxiao': {'cnName': '五连肖', 'limit': 5}
    },
    // playType: 'erlianxiao',
    subType: 'lianxiaozhong',
    betType: 'fushi',
    preSelect: {
      // 'erlianxiao': {
      //   'fushi': [],
      //   'dan': [],
      //   'tuo': []
      // }
    },
    selectArr: [], // 选中生肖的集合
    lotteryHistory: [{content: '猴'}, {content: '猴'}, {}, {}, {}, {}] // 历史开奖数据
  }
  Games = host.Games,
  ZoadiacList = Games.getCurrentGame().getGameConfig().getInstance().getZoadiacList(),
  LHC = Games.LHC.getInstance();


  var pros = {
    init: function(cfg){
      var me = this;
      gameConfig = Games.getCurrentGame().getGameConfig().getInstance();
      me.setOdds(gameConfig);
      me.setTips(gameConfig);
      me.getLotteryHistory(gameConfig);

      me.addEvent('updatePreSelect', function(){
        me.updatePreSelect();
        me.updateBallData(me.defConfig.betType);
        me.updateHistory && me.updateHistory();
      });

      me.addEvent('afterChangeOdd', function(event, param){
        var oddType = param.type,
            preSelect = me.defConfig.preSelect;

        me.defConfig.playType = oddType;
        preSelect[oddType] = preSelect[oddType]||{
          'fushi': [],
          'dan': [],
          'tuo': []
        };
        me.fireEvent('updatePreSelect');
      });

    },
    rebuildData:function(){
        var me = this;
        me.balls = [
          [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
        ];
    },
    buildUI:function(){
        var me = this;
        me.container.html(html_all.join(''));
    },
    setOdds: function(cfg){
        var me = this,
           datas = cfg.getGameOdds();
        	odds=new Array(),
			data = null;
			for(var i = 0; i < datas.length ; i++){
				var oddData = datas[i];
				var childs=oddData.childs;
				if(datas[i].name=='lianxiaozhong'){
					odds=childs;
				}
			}
		//rebuild odds
		var typeArray = new Array();
        for(var i = 0; i < odds.length ; i++){
			var o = odds[i];
			if($.inArray(o.name, typeArray) > -1){
				
			} else {
				typeArray.push(o.name);
			}
		}	

		var newOddArray = new Array();
        for(var i = 0; i < typeArray.length ; i++){
			var oddArray = new Array();
			for(var j = 0; j < odds.length ; j++){
				var o = odds[j];
				if(o.name == typeArray[i] && o.odds!=0.0){
					oddArray.push(o.odds);
				}
			}
			newOddArray.push({"name" : typeArray[i], "odds" : oddArray});
		}		
        me.buildOdds(newOddArray);
    },
    buildOdds: function(odds){
		console.log(odds)
        var me = this,
            $dom = me.container.find('.lianxiaozhong').find(me.defConfig.oddDom),
            odds_list = [],
            oddsMap = me.defConfig.oddsMap,
            oddTxt;
        $.each(odds, function(name, val, c){
          odds_list.push('<li data-param="action=changeOdd&value='+ val.odds +'&type='+val.name+'"><span class="name">'+ oddsMap[val.name].cnName +'</span></li>');
        });
        $dom.html(odds_list.join(''));

        //读取本地缓存 调取默认倍率
        var gameName = me.defConfig.name;
        var default_cookie = $.cookie(gameName + '_user_odd'),
            $li = $dom.find('li'),
            $currentOdds = $li.eq(0);
        $.each($li,function(i, v){
            if($(v).find('.odds').text() == default_cookie){
              $currentOdds = $(v);
                return false;
            }
        });
        setTimeout(function(){
          $currentOdds.trigger('click');
        }, 0);
    },
    setTips: function(cfg){
        var me = this,
            data = null,
			exampleDom = me.defConfig.exampleDom,
            ruleDom = me.defConfig.ruleDom,
            arr = [];
		$.each(cfg.getGameTips(), function(i, item){
			if(item['name'] == 'zhengtema' ){
				data = item.childs;
			}
		});	
        $.each(data,function(i,v){
            if(v['name'] == 'lianxiaozhong' ){
				$.each(v.childs, function(i, v){
				  var $domRule = me.container.find('.lianxiaozhong').find(ruleDom),
					  $domExample = me.container.find('.lianxiaozhong').find(exampleDom);
					  $domRule.html(v['rule']);
					  $domExample.html(v['example']);
				});
            }
        });
    },
    getLotteryHistory: function(cfg){
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
    updateHistory: function(){
      var me = this,
          defConfig = me.defConfig,
          playType = defConfig.playType,
          betType = defConfig.betType,
          preSelect = defConfig.preSelect,
          orders;
      if(!playType){ return false;}
      preSelect[playType] = preSelect[playType]||{
        'fushi': [],
        'dan': [],
        'tuo': []
      };
      orders = preSelect[playType][betType];
      if(betType == 'dan'|| betType == 'tuo'){
        orders = defConfig.preSelect[playType]['dan'].concat(defConfig.preSelect[playType]['tuo']);
      }
      // if (!orders){ return false; }
      defConfig.selectArr = $.map(orders, function(obj){
        return obj.content;
      });
      var historyItem = me.container.find('.lottery-history').find('span');
      historyItem.removeClass('active').filter(function(){
        return $.inArray($(this).text(), defConfig.selectArr) !== -1;
      }).addClass('active');

    },
    buildHistory: function(lotteryHistory){
      var me = this;
      var html_history = [];
      html_history.push('<div class="lottery-history texiao-history">');
      html_history.push('<h3 class="title">历史开奖</h3>');
      html_history.push('<ul class="ball-history-ul">');

     var numberRecord, name;
      $.each(lotteryHistory, function(i, obj){
        numberRecord = obj.numberRecord.split(',');
        numberRecord = $.map(numberRecord, function(val){
          $.each(ZoadiacList, function(i, obj){
            if (obj.number.indexOf(val) != -1){
              name = obj.zodiacNameCn;
              return false;
            }
          });
          return name;
        });

        html_history.push('<li><span class="period">'+ obj.webIssueCode +'</span>');
        $.each(numberRecord, function(i, val){
          if(i == 6){
            html_history.push('<span class="ball-diliver">+</span>');
          }
          html_history.push('<span class="ball-ordinary">'+val+'</span>');
        });
        html_history.push('</li>');
      });
      html_history.push('</ul><div class="list-wave"></div></div>');

      var html_all = [];
      html_all.push(html_history.join(''));
      $(html_all).appendTo(me.container.find('.lianxiaozhong'));
    }
  }



  //html模板
  var html_lianxiaozhong = [];
  html_lianxiaozhong.push('<div class="ball-list lianxiaozhong clearfix">');
  html_lianxiaozhong.push('<div class="game-title">');
  html_lianxiaozhong.push('<h3>连肖(中)</h3>');
  html_lianxiaozhong.push('<div class="game-prompt">');
  html_lianxiaozhong.push('<a class="rule-example" href="javascript:;">玩法规则</a>');
  html_lianxiaozhong.push('<a class="prompt-example" href="javascript:;">投注示例</a>');
  html_lianxiaozhong.push('<div class="rule-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
  html_lianxiaozhong.push('<div class="prompt-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
  html_lianxiaozhong.push('</div>');
  html_lianxiaozhong.push('</div>');
  html_lianxiaozhong.push('<div class="game-odds2">');
  html_lianxiaozhong.push('<ol>');
  html_lianxiaozhong.push('<li class="active">四不中<br>&times;1.8</li>');
  html_lianxiaozhong.push('</ol>');
  html_lianxiaozhong.push('</div>');

  //号球
  var html_ball = [];
  html_ball.push('<div class="ball-list-wrap">');
  html_ball.push('<ul class="ball-list-ul">');

  $.each(defConfig.zodiacsMap,function(i,v){
      var arrBall = [];
      $.each(ZoadiacList, function(j,value){
          if(value['zodiacNameCn'] == v['cnName']){
              arrBall = value['number'].split(',');
              return;
          }
      });
      html_ball.push('<li data-param="action=preSelect&row=0&value='+ v['value'] +'&type=lianxiaozhong"><span class="balls">'+v['cnName']+'</span>');

      $.each(arrBall,function(j,value){
          html_ball.push('<span class="ball-group">'+value+'</span>');
      });
      html_ball.push('<span class="ride">x</span><span class="ball-odds">1</span></li>');
  });

  html_ball.push('</ul>');
  html_ball.push('</div>');

  // 胆拖
  var html_dantuo = [];
  html_dantuo.push('<div class="order-pre">');
  html_dantuo.push('<ul class="order-pre-tab">');
  html_dantuo.push('<li class="tab-fushi active" data-param="action=changeGameType&value=fushi">复式</li>');
  html_dantuo.push('<li class="tab-dantuo" data-param="action=changeGameType&value=dantuo">胆拖</li>');
  html_dantuo.push('</ul>');
  html_dantuo.push('<div><div class="ball-content-fushi">');
  html_dantuo.push('</div>');
  html_dantuo.push('<div class="ball-content-dantuo">');
  html_dantuo.push('<div class="dan-content active" data-param="action=switchDanTuo&value=dan">');
  html_dantuo.push('<label>胆</label>');
  html_dantuo.push('<ul class="ball-content-dan">');
  html_dantuo.push('</ul>');
  html_dantuo.push('</div>');
  html_dantuo.push('<div class="tuo-content" data-param="action=switchDanTuo&value=tuo">');
  html_dantuo.push('<label>拖</label>');
  html_dantuo.push('<ul class="ball-content-tuo">');
  html_dantuo.push('</ul>');
  html_dantuo.push('</div>');
  html_dantuo.push('</div></div>');
  html_dantuo.push('<div class="btns">');
  html_dantuo.push('<button data-param="action=confirmBall" class="ball-confirm" type="button">确定</button>');
  html_dantuo.push('<button data-param="action=removeAllPreSelect" class="ball-reset" type="button">清空</button>');
  html_dantuo.push('</div>');
  html_dantuo.push('</div>');




  //尾部
  var html_bottom = [];
  html_bottom.push('</div>');

  //拼接所有
  var html_all = [];
  html_all.push(html_lianxiaozhong.join(''));
  html_all.push(html_ball.join(''));
  html_all.push(html_dantuo.join(''));
  html_all.push(html_bottom.join(''));

  //继承GameMethod
  var Main = host.Class(pros, GameMethod);
  Main.defConfig = defConfig;
  //将实例挂在游戏管理器实例上
  LHC[defConfig.name] = new Main();

})(phoenix, phoenix.GameMethod);
