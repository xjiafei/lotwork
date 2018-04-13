/**
 * 2017/02/22
 * 连码 - 连码 玩法
 */

(function(host, GameMethod, undefined){

  var defConfig = {
    //选球倍率值容器
    oddDom:'.game-odds2 ol',
    ruleDom: '.rule-text .text-box',
    exampleDom: '.prompt-text .text-box',
    name: 'lianma.lianma',
    colorNameMap: ['red','blue','green'],
    colorArr: [0,0,1,1,2,2,0,0,1,1,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,1,1,2,2,0,0,1,1,2],
    zodiacsMap: [
         {'enName':'shu','cnName':'鼠'},
        {'enName':'niu','cnName':'牛'},
        {'enName':'hu','cnName':'虎'},
        {'enName':'tu','cnName':'兔'},
        {'enName': 'long','cnName':'龙'},
        {'enName':'she','cnName':'蛇'},
        {'enName': 'ma','cnName':'马'},
        {'enName':'yang','cnName':'羊'},
        {'enName':'hou','cnName':'猴'},
        {'enName':'ji','cnName':'鸡'},
        {'enName': 'gou','cnName':'狗'},
        {'enName':'zhu','cnName':'猪'}
      ],
    oddsMap: {
      'sanquanzhong': {'cnName': '三全中', 'limit': 3 },
      'sanzhonger': {'cnName': '三中二', 'limit': 3},
      'erquanzhong': {'cnName': '二全中', 'limit': 2},
      'erzhongte': {'cnName': '二中特', 'limit': 2},
      'techuan': {'cnName': '特串', 'limit': 2}
    },
    // playType: 'sanquanzhong',
    subType: 'lianma',
    betType: 'fushi',
    preSelect: {
      // 'sanquanzhong': {
      //   'fushi': [],
      //   'dan': [],
      //   'tuo': []
      // }
    }
  }
  Games = host.Games,
  LHC = Games.LHC.getInstance();


  var pros = {
    init: function(cfg){
      var me = this;
      gameConfig = Games.getCurrentGame().getGameConfig().getInstance();
      me.setOdds(gameConfig);

      me.addEvent('updatePreSelect', function(){
        me.updatePreSelect();
        me.updateBallData(me.defConfig.betType);
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
        me.setTips(gameConfig);
        me.fireEvent('updatePreSelect');
      });

    },
    rebuildData:function(){
        var me = this;
        me.balls = [
          [-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
           -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
           -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
           -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
           -1,-1,-1,-1,-1,-1,-1,-1,-1]
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
				if(datas[i].name=='lianma'){
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
				if(o.name == typeArray[i]){
					oddArray.push(o.odds);
				}
			}
			newOddArray.push({"name" : typeArray[i], "odds" : oddArray});
		}		
        me.buildOdds(newOddArray);
    },
    buildOdds: function(odds){
        var me = this,
            $dom = me.container.find('.lianma').find(me.defConfig.oddDom),
            odds_list = [],
            oddsMap = me.defConfig.oddsMap,
            oddTxt;
        $.each(odds, function(name, val){
          if(val.name == 'sanzhonger'){
            oddTxt = '<span>中2&times;</span><span class="odds">'+ val.odds[1] +'</span> <span>中3&times;</span><span class="odds">'+ val.odds[2] +'</span>';
          } else if(val.name == 'erzhongte'){
            oddTxt = '<span>中2&times;</span><span class="odds">'+ val.odds[1] +'</span> <span>中特&times;</span><span class="odds">'+ val.odds[2] +'</span>';
          } else {
            oddTxt = '&times;</span><span class="odds">'+ val.odds +'</span>';
          }
          odds_list.push('<li data-param="action=changeOdd&value='+ val.odds +'&type='+val.name+'"><span class="name">'+ oddsMap[val.name].cnName+'</span><br>'+oddTxt+'</li>');
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
            data = cfg.getGameTips()[3]['childs'],
			playType = me.defConfig.playType,
            exampleDom = me.defConfig.exampleDom,
            ruleDom = me.defConfig.ruleDom,
            arr = [];
			$.each(cfg.getGameTips(), function(i, item){
				if(item['name'] == 'lianma' ){
					data = item.childs;
				}
			});	
        $.each(data,function(i,v){
			$.each(v.childs, function(i, v){
				if(v['name'] == playType ){
					var $domRule = me.container.find('.lianma').find(ruleDom),
						$domExample = me.container.find('.lianma').find(exampleDom);
						$domRule.html(v['rule']);
						$domExample.html(v['example']);
				}
			 });
            
        });
    }
  }



  //html模板
  var html_lianma = [];
  html_lianma.push('<div class="ball-list lianma">');
  html_lianma.push('<div class="game-title">');
  html_lianma.push('<h3>连码</h3>');
  html_lianma.push('<div class="game-prompt">');
  html_lianma.push('<a class="rule-example" href="javascript:;">玩法规则</a>');
  html_lianma.push('<a class="prompt-example" href="javascript:;">投注示例</a>');
  html_lianma.push('<div class="rule-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
  html_lianma.push('<div class="prompt-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
  html_lianma.push('</div>');
  html_lianma.push('</div>');
  html_lianma.push('<div class="game-odds2">');
  html_lianma.push('<ol>');
  html_lianma.push('<li class="active">四不中<br>&times;1.8</li>');
  html_lianma.push('</ol>');
  html_lianma.push('</div>');

  //号球
  var html_ball = [];
  html_ball.push('<div class="ball-list-wrap">');

  $.each([0,1,2,3,4], function(i){
    html_ball.push('<ul class="ball-list-ul">');

    $.each([1,2,3,4,5,6,7,8,9,10], function(j, v){
      var value = i*10 + v;
      if (value > 49){ return false;}
      value = value < 10 ? '0'+value : value;
      html_ball.push('<li data-param="action=preSelect&row=0&value='+value+'&type=lianma"><span class="ball '+ defConfig.colorNameMap[defConfig.colorArr[value-1]] +'">'+value+'</span><span class="ball-odds">1.8</span></li>');

    });

    html_ball.push('</ul>');
  });
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


  //选球工具
  var html_tool = [];
  html_tool.push('<div class="select-tool">');
  html_tool.push('<ul class="tool-animal">')
  $.each(defConfig.zodiacsMap,function(i,v){
      html_tool.push('<li data-param="action=batchSetPreBall&value='+ v['enName'] +'&type=zodiacs">'+ v['cnName']+'</li>');
  });
  html_tool.push('</ul>');

  html_tool.push('<div class="tool-bits">');
  html_tool.push('<span class="tool-title">尾：</span>');
  html_tool.push('<ul>');
  $.each([0,1,2,3,4,5,6,7,8,9],function(i,v){
      var now_value =  v;
      html_tool.push('<li data-param="action=batchSetPreBall&value='+ now_value +'&type=bits">'+now_value+'</li>');
  });
  html_tool.push('</ul></div>');

  html_tool.push('<div class="tool-ten">');
  html_tool.push('<span class="tool-title">头：</span>');
  html_tool.push('<ul>');

  $.each([0,1,2,3,4],function(i,v){
      var now_value =  v;
      html_tool.push('<li data-param="action=batchSetPreBall&value='+ now_value +'&type=ten">'+now_value+'</li>');
  });
  html_tool.push('</ul>');
  html_tool.push('</div>');

  html_tool.push('<div class="tool-keyboard">');
  html_tool.push('<input data-param="action=input" placeholder="键盘输入号码" type="text">');
  html_tool.push('</div>');
  html_tool.push('</div>');

  //尾部
  var html_bottom = [];
  // html_bottom.push('</ul>');
  html_bottom.push('</div>');

  //拼接所有
  var html_all = [];
  html_all.push(html_lianma.join(''));
  html_all.push(html_ball.join(''));
  html_all.push(html_dantuo.join(''));
  html_all.push(html_tool.join(''));
  html_all.push(html_bottom.join(''));

  //继承GameMethod
  var Main = host.Class(pros, GameMethod);
  Main.defConfig = defConfig;
  //将实例挂在游戏管理器实例上
  LHC[defConfig.name] = new Main();

})(phoenix, phoenix.GameMethod);
