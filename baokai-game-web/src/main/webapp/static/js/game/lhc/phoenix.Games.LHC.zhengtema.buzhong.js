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
    name: 'zhengtema.buzhong',
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
      'sibuzhong': {'cnName': '四不中', 'limit': 4 },
      'wubuzhong': {'cnName': '五不中', 'limit': 5},
      'liubuzhong': {'cnName': '六不中', 'limit': 6},
      'qibuzhong': {'cnName': '七不中', 'limit': 7},
      'babuzhong': {'cnName': '八不中', 'limit': 8}
    },
    playType: '',
    subType: 'buzhong',
    betType: 'fushi',
    preSelect: {
      // 'sibuzhong': {
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
        me.fireEvent('updatePreSelect');
      });

      me.setOdds(gameConfig);
      me.setTips(gameConfig);
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
        	odd={},
			data = null;
			for(var i = 0; i < datas.length ; i++){
				var oddData = datas[i];
				var childs=oddData.childs;
				if(datas[i].name=='buzhong'){
					odd=childs;
				}
			}
        me.buildOdds(odd);
    },
    buildOdds: function(odds){
        var me = this,
            $dom = me.container.find('.buzhong').find(me.defConfig.oddDom),
            odds_list = [],
            oddsMap = me.defConfig.oddsMap;
        $.each(odds, function(name, val){
          odds_list.push('<li data-param="action=changeOdd&value='+ val.odds +'&type='+val.name+'"><span class="name">'+ oddsMap[val.name].cnName +'</span><br>&times;<span class="odds">'+ val.odds +'</span></li>');
        });

        $dom.html(odds_list.join(''));

        //读取本地缓存 调取默认倍率
        var gameName = me.defConfig.name;
        var default_cookie = $.cookie(gameName + '_user_odd'),
            $li = $dom.find('li'),
            hasDefault = false;
        $.each($li,function(i, v){
            if($(v).find('.odds').text() == default_cookie){
                setTimeout(function(){
                  $(v).trigger('click');
                }, 0);
				hasDefault = true;
                return false;
            }
        });
		if (!hasDefault){
            setTimeout(function(){
                $li.eq(0).trigger('click');
            }, 0);
        }
    },
    setTips: function(cfg){
        var me = this,
            data = null,
            arr = [];
		$.each(cfg.getGameTips(), function(i, item){
			if(item['name'] == 'zhengtema' ){
				data = item.childs;
			}
		});
        $.each(data,function(i,v){
            if(v['name'] == 'buzhong' ){
              	$.each(v.childs, function(i, v){
                     var $domRule = me.container.find('.buzhong').find(defConfig.ruleDom),
                         $domExample = me.container.find('.buzhong').find(defConfig.exampleDom);
						 $domRule.html(v['rule']);
						 $domExample.html(v['example']);
				}); 
            }
        });
    }
  }




  //html模板
  var html_buzhong = [];
  html_buzhong.push('<div class="ball-list buzhong">');
  html_buzhong.push('<div class="game-title">');
  html_buzhong.push('<h3>不中</h3>');
  html_buzhong.push('<div class="game-prompt">');
  html_buzhong.push('<a class="rule-example" href="javascript:;">玩法规则</a>');
  html_buzhong.push('<a class="prompt-example" href="javascript:;">投注示例</a>');
  html_buzhong.push('<div class="rule-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
  html_buzhong.push('<div class="prompt-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
  html_buzhong.push('</div>');
  html_buzhong.push('</div>');
  html_buzhong.push('<div class="game-odds2">');
  html_buzhong.push('<ol>');
  //html_buzhong.push('<li class="active">四不中<br>&times;1.8</li>');
  html_buzhong.push('</ol>');
  html_buzhong.push('</div>');

  //号球
  var html_ball = [];
  html_ball.push('<div class="ball-list-wrap">');

  $.each([0,1,2,3,4], function(i){
    html_ball.push('<ul class="ball-list-ul">');

    $.each([1,2,3,4,5,6,7,8,9,10], function(j, v){
      var value = i*10 + v;
      if (value > 49){ return false;}
      value = value < 10 ? '0'+value : value;
      html_ball.push('<li data-param="action=preSelect&row=0&value='+value+'&type=buzhong"><span class="ball '+ defConfig.colorNameMap[defConfig.colorArr[value-1]] +'">'+value+'</span><span class="ball-odds">1.8</span></li>');

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
  html_all.push(html_buzhong.join(''));
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
