/**
 * 2017/02/22
 * 特码 - 一肖 玩法
 */

(function(host, GameMethod, undefined){

  var defConfig = {
    //选球倍率值容器
    ballOddDom:'.ball-odds',
    ruleDom: '.rule-text .text-box',
    exampleDom: '.prompt-text .text-box',
    name: 'zhengtema.yixiao',
    colorNameMap: ['red','blue','green'],
    colorArr: [0,0,1,1,2,2,0,0,1,1,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,1,1,2,2,0,0,1,1,2],
    yixiaoMap: [
        {'enName':'shu','cnName':'鼠','value':'1'},
        {'enName': 'ma','cnName':'马','value':'2'},
        {'enName':'niu','cnName':'牛','value':'3'},
        {'enName':'yang','cnName':'羊','value':'4'},
        {'enName':'hu','cnName':'虎','value':'5'},
        {'enName':'hou','cnName':'猴','value':'6'},
        {'enName':'tu','cnName':'兔','value':'7'},
        {'enName':'ji','cnName':'鸡','value':'8'},
        {'enName': 'long','cnName':'龙','value':'9'},
        {'enName': 'gou','cnName':'狗','value':'10'},
        {'enName':'she','cnName':'蛇','value':'11'},
        {'enName':'zhu','cnName':'猪','value':'12'}
      ],
    selectArr: [], // 选中生肖的集合
    lotteryHistory: [{content: '猴'}, {content: '猴'}] // 历史开奖数据
  },
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
			odd=new Array(),
			data = null;
			for(var i = 0; i < datas.length ; i++){
				var oddData = datas[i];
				var childs=oddData.childs;
				for(var j = 0; j < childs.length ; j++){
					if(childs[j].name=='yixiao'){
						odd.push(childs[j].odds);
					}
				}
			}
        me.buildOdds(odd);
    },
    buildOdds: function(arr){
        var me = this;
		
		console.log(me );
        var $dom = me.container.find('.yixiao').find(defConfig.ballOddDom);
		console.log($dom );
		arr.sort(function(a, b){return a-b;});
		
		$dom.each(function(idx, node){
			var tmpSize = $("li[data-param='" + $(node).parent().attr("data-param") + "']").children(".ball-group").size();
			if(tmpSize > 4){
				$(node).html(arr[0]);
				$(node).eq(0).html(arr[0]);
			} else {
				$(node).html(arr[arr.length - 1]);
				$(node).eq(0).html(arr[arr.length - 1]);
			}
		});
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
            if(v['name'] == 'yixiao' ){
				$.each(v.childs, function(i, v){
				  var $domRule = me.container.find('.'+v['name']).find(defConfig.ruleDom),
					  $domExample = me.container.find('.'+v['name']).find(defConfig.exampleDom);
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
      var me = this;
      var orders = Games.getCurrentGameOrder().getTotal().orders['zhengtema.yixiao'];
      if (!orders){ return false; }
      defConfig.selectArr = $.map(orders, function(obj){
        return obj.content;
      });

      var historyItem = me.container.find('.lottery-history').find('li span');
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
      $(html_all).appendTo(me.container);
    }
  }



  //html模板
  var html_shengxiao = [];
  //生肖
  html_shengxiao.push('<div class="ball-list yixiao">');
  html_shengxiao.push('<div class="game-title"><h3>一肖</h3>');
  html_shengxiao.push('<div class="game-prompt">');
  html_shengxiao.push('<a class="rule-example" href="javascript:;">玩法规则</a>');
  html_shengxiao.push('<a class="prompt-example" href="javascript:;">投注示例</a>');
  html_shengxiao.push('<div class="rule-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
  html_shengxiao.push('<div class="prompt-text"><span class="box-arrow"></span><p class="text-box"></p></div>');
  html_shengxiao.push('</div>');
  html_shengxiao.push('</div>');
  html_shengxiao.push('<ul class="ball-list-ul">');
  $.each(defConfig.yixiaoMap,function(i,v){
      var arrBall = [];
      $.each(ZoadiacList,function(j,value){
          if(value['zodiacNameCn'] == v['cnName']){
              arrBall = value['number'].split(',');
              return;
          }
      });
      html_shengxiao.push('<li data-param="action=ball&row=0&value='+ v['value'] +'&type=yixiao"><span class="balls">'+v['cnName']+'</span>');

      $.each(arrBall,function(j,value){
          html_shengxiao.push('<span class="ball-group">'+value+'</span>');
      });
      html_shengxiao.push('<span class="ride">x</span><span class="ball-odds">1</span></li>');
  });
  html_shengxiao.push('</ul>');
  html_shengxiao.push('</div>');

  //拼接所有
  var html_all = [];
  html_all.push(html_shengxiao.join(''));

  //继承GameMethod
  var Main = host.Class(pros, GameMethod);
  Main.defConfig = defConfig;
  //将实例挂在游戏管理器实例上
  LHC[defConfig.name] = new Main();

})(phoenix, phoenix.GameMethod);
