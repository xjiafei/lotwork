/**
 * 2017/02/22
 * 特码 - 两面 玩法
 */

(function(host, GameMethod, undefined){

  var defConfig = {
    //选球倍率值容器
    ballOddDom:'.ball-odds',
    ruleDom: '.rule-text .text-box',
    exampleDom: '.prompt-text .text-box',
    name: 'tema.liangmian',
    colorNameMap: ['red','blue','green'],
    colorArr: [0,0,1,1,2,2,0,0,1,1,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,1,1,2,2,0,0,1,1,2],
    halfMap: [
        {'enName':'da','cnName':'大','value':'1'},
        {'enName':'xiao','cnName':'小','value':'2'},
        {'enName':'heda','cnName':'和大','value':'3'},
        {'enName':'hexiao','cnName':'和小','value':'4'},
        {'enName':'dan','cnName':'单','value':'5'},
        {'enName':'shuang','cnName':'双','value':'6'},
        {'enName':'hedan','cnName':'和单','value':'7'},
        {'enName':'heshuang','cnName':'和双','value':'8'},
        {'enName':'daxiao','cnName':'大肖','value':'9'},
        {'enName':'xiaoxiao','cnName':'小肖','value':'10'},
        {'enName':'weida','cnName':'尾大','value':'11'},
        {'enName':'weixiao','cnName':'尾小','value':'12'}
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
 			data = null;
			for(var i = 0; i < datas.length ; i++){
				var oddData = datas[i];
				if(oddData.name == 'liangmian'){
					data = oddData;
				}
			}
        me.buildOdds(data['childs']);
    },
    buildOdds: function(odds){
        var me = this;
        var $dom = me.container.find('.liangmian').find(defConfig.ballOddDom);
        $.each($dom, function(i, obj){
          $(obj).html(odds[0].odds);
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
            if(v['name'] == 'liangmian' ){
 				$.each(v.childs, function(i, v){
                     var $domRule = me.container.find(defConfig.ruleDom),
                         $domExample = me.container.find(defConfig.exampleDom);
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
  //两面
  var html_half = [];
  html_half.push('<div class="ball-list liangmian">');
  html_half.push('<div class="game-title"><h3>两面</h3>');
  html_half.push('<div class="game-prompt">');
  html_half.push('<a class="rule-example" href="javascript:;">玩法规则</a>');
  html_half.push('<a class="prompt-example" href="javascript:;">投注示例</a>');
  html_half.push('<div class="rule-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
  html_half.push('<div class="prompt-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
  html_half.push('</div>');
  html_half.push('</div>');
  html_half.push('<ul class="ball-list-ul">');
  $.each(defConfig.halfMap,function(j,v){
      html_half.push('<li data-param="action=ball&row=0&value='+ v['value'] +'&type=liangmian"><span class="balls">'+ v['cnName'] +'</span><span class="ride">x</span><span class="ball-odds">1</span></li>');
  });
  html_half.push('</ul></div>');

  //拼接所有
  var html_all = [];
  html_all.push(html_half.join(''));

  //继承GameMethod
  var Main = host.Class(pros, GameMethod);
  Main.defConfig = defConfig;
  //将实例挂在游戏管理器实例上
  LHC[defConfig.name] = new Main();

})(phoenix, phoenix.GameMethod);
