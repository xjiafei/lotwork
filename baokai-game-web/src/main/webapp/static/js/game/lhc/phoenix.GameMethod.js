/**
 * Created by user on 16/3/23.
 */

//游戏方法类
//所有具体游戏实现应继承该类
(function(host, name, Event, undefined) {
  var defConfig = {
      //玩法名称，必须是完整的名称
      //如：'wuxing.zhixuan.fushi'
      name: '',
      //父容器
      UIContainer: '#J-game-box',
      //球dom元素选择器
      ballsDom: '.ball-list-ul li',

      //选球高亮class
      ballCurrentCls: 'active',
      //选球倍率值容器
      ballOddDom: '.ball-odds',
      //玩法提示信息
      methodMassageDom: '.prompt .method-tip',
      //玩法提示信息
      methodExampleDom: '.prompt .example-tip',
      //限制金额长度
      amountDigit: 999999

    },
    Games = host.Games;
  //将来仿url类型的参数转换为{}对象格式，如 q=wahaha&key=323444 转换为 {q:'wahaha',key:'323444'}
  //所有参数类型均为字符串
  var formatParam = function(param) {
    var arr = $.trim(param).split('&'),
      i = 0,
      len = arr.length,
      paramArr,
      result = {};
    for (; i < len; i++) {
      paramArr = arr[i].split('=');
      if (paramArr.length > 0) {
        if (paramArr.length == 2) {
          result[paramArr[0]] = paramArr[1];
        } else {
          result[paramArr[0]] = '';
        }
      }
    }
    return result;
  };

  var pros = {
    init: function(cfg) {
      var me = this;
      //父容器
      me.UIContainer = $(cfg.UIContainer);
      //自身容器
      me.container = $('<div></div>').appendTo(me.UIContainer);
      me.buildUI();
      //
      me.hide();
      //
      //初始化数据结构
      me.balls = [];
      me.rebuildData();
      me.straightOdds = '40';
      //
      //所有选球dom
      me.ballsDom = me.getBallsDom();

      //由于玩法是异步延后加载并实例化，所以与其他组件的结合不能提取到外部
      //选球数据更改后触发动作
      me.addEvent('updateData', function(e, data) {

        //更新选球界面
        me.batchSetBallDom();

        // 更新历史开奖界面
        me.updateHistory && me.updateHistory();
      });

      //特码直选批量添加号码事件监听
      me.addEvent('directTypebatchSet', function(e, data) {
        me.setBallToOrder();
      });
      //选球结束时 将球添加到结果栏
      me.addEvent('afterSelectBall', function(e, order) {
        Games.getCurrentGameOrder().add(order);
      });

      //面板复位时执行批量选求状态清空
      me.addEvent('afterReset', function() {

      });


      //
      //选球动作结束执行批量选求状态清空
      //me.addEvent('afterSetBallData', function(e, x, y, v){
      //    me.batchSetBallDom();
      //})
    },
    //获取选球dom元素，保存结构和选球数据(me.balls)一致
    getBallsDom: function() {
      var me = this,
        cfg = me.defConfig,
        dataMode = me.balls;
      if (dataMode.length < 1) {
        return [];
      }
      return me.ballsDom || (function() {
        var balls = me.container.find(cfg.ballsDom),
          i = 0,
          j = 1,
          len = balls.length,
          _row = [],
          result = [],
          cellnum = dataMode[0].length;
        for (; i < len; i++, j++) {
          _row.push(balls[i]);
          if ((j) % cellnum == 0 || (i + 1) == len) {
            result.push(_row);
            _row = [];
            j = 0;
            cellnum = dataMode[result.length] && dataMode[result.length].length;
          }
        }
        return result;
      })();
    },
    //游戏类型切换后
    //游戏相关信息的更新方法
    updataGamesInfo: function() {
      var me = this,
        type = me.getGameMethodName(),
        url = 'simulatedata/getBetAward.php?type=' + type + '&extent=currentFre&line=5&lenth=30&lotteryid=99101&userid=31';

      if (!Games.cacheData['gameTips']) {
        Games.cacheData['gameTips'] = {};
      }

    },
    //修改玩法提示方法
    methodTip: function(data) {
      var me = this,
        cfg = me.defConfig;
      //玩法提示
      $(cfg.methodMassageDom).html(data.tips);
      //玩法实例
      $(cfg.methodExampleDom).html(data.example);
    },

    //重新构建选球数据
    //在子类中实现
    rebuildData: function() {

    },
    getBallData: function() {
      return this.balls;
    },
    //设置选球数据
    //x y value   x y 为选球数据二维数组的坐标 value 为-1 或1
    setBallData: function(x, y, value) {
      var me = this,
        data = me.getBallData();
      me.fireEvent('beforeSetBallData', x, y, value);
      if (x >= 0 && x < data.length && y >= 0 && y < data[x].length) {
        data[x][y] = value;
      }
      me.fireEvent('afterSetBallData', x, y, value);
    },
    //复位
    reSet: function(row) {
      var me = this,
        $li = me.UIContainer.find('.select-tool li');
      me.rebuildData(row);
      me.resetTool();
      me.updateData();
      me.fireEvent('afterReset');
    },
    //快速选球工具 重置
    resetTool: function() {
      var $li = this.UIContainer.find('.select-tool li');
      $li.removeClass('active');
    },
    //获取该玩法的名称
    getGameMethodName: function() {
      return this.defConfig.name;
    },
    //显示该游戏玩法
    show: function() {
      var me = this;
      me.fireEvent('beforeShow', me.container);
      me.container.show();
      me.fireEvent('afterShow', me.container);
    },
    //隐藏该游戏玩法
    hide: function() {
      var me = this;
      me.fireEvent('beforeHide', me.container);
      me.container.hide();
      me.fireEvent('afterHide', me.container);
    },
    //实现事件
    exeEvent: function(param, target) {
      var me = this;
      if ($.isFunction(me['exeEvent_' + param['action']])) {
        me['exeEvent_' + param['action']].call(me, param, target);
      }
    },
    //批量选球事件
    exeEvent_batchSetBall: function(param, target) {
      var me = this,
        ballsData = me.balls,
        x = 0,
        type = param['type'],
        i = 0,
        len = ballsData[x].length,
        start = (typeof param['start'] == 'undefined') ? 0 : Number(param['start']),
        halfLen = Math.ceil((len - start) / 2 + start),
        value = param['value'],
        zoadiacList = Games.getCurrentGame().getGameConfig().getInstance().getZoadiacList() || [],
        colorArr = me.defConfig.colorArr,
        currCls = me.defConfig.ballCurrentCls,
        $dom = $(target);
      switch (type) {
        case 'zodiacs':
          $.each(zoadiacList, function(j, v) {
            if (value != '' && value == v['zodiacName']) {
              var arr_num = v['number'].split(",");
              $.each(arr_num, function(k, v) {
                me.setBallData(x, Number(v) - 1, 1);
              })
            }
          })
          break;
        case 'half':
		  var newBallsData = ballsData[x].slice(0, ballsData[x].length - 1); //包含49個號碼, 大小需要將49號去掉
		  len = newBallsData.length;
		  halfLen = Math.ceil((len - start) / 2 + start);
          if (value != '' && value == 'da') {
            for (i = halfLen; i < len; i++) {
              me.setBallData(x, i, 1);
            }
          }
          if (value != '' && value == 'xiao') {
            for (i = start; i < halfLen; i++) {
              me.setBallData(x, i, 1);
            }
          }
          if (value != '' && value == 'dan') {
            for (i = start; i < len; i++) {
              if ((i + 1) % 2 == 1) {
                me.setBallData(x, i, 1);
              }
            }
          }
          if (value != '' && value == 'shuang') {
            for (i = start; i < len; i++) {
              if ((i + 1) % 2 != 1) {
                me.setBallData(x, i, 1);
              }
            }
          }
          break;
        case 'color':
          if (value != '' && value == 'red') {
            for (; i < len; i++) {
              if (colorArr[i] == 0) {
                me.setBallData(x, i, 1);
              }
            }
          }
          if (value != '' && value == 'blue') {
            for (; i < len; i++) {
              if (colorArr[i] == 1) {
                me.setBallData(x, i, 1);
              }
            }
          }
          if (value != '' && value == 'green') {
            for (; i < len; i++) {
              if (colorArr[i] == 2) {
                me.setBallData(x, i, 1);
              }
            }
          }
          break;

        case 'bits':
          for (; i < len; i++) {
            var after_num = (i + 1) % 10;
            if (value == after_num) {
              me.setBallData(x, i, 1);
            }
          }
          break;
        case 'ten':
          for (; i < len; i++) {
            var after_num = parseInt((i + 1) / 10);
            if (value == after_num) {
              me.setBallData(x, i, 1);
            }
          }
          break;

        default:
          break;
      }
      $dom.addClass(currCls);
      me.fireEvent('directTypebatchSet');
      me.updateData();
    },

    //选球事件
    //球参数 action=ball&value=2&row=0  表示动作为'选球'，球值为2，行为第1行(万位)
    //函数名称： exeEvent_动作名称
    exeEvent_ball: function(param, target) {
      var me = this,
        el = $(target),
        currCls = me.defConfig.ballCurrentCls;

      if (el.hasClass(currCls)) {
        var data = {};
        data['lotterys'] = param['value'];
        data['row'] = param['row'];
        data['type'] = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName();
        Games.getCurrentGameOrder().deleteOrderByBall(data);

      } else {
        //必要参数
        if (param['value'] != undefined && param['row'] != undefined) {
          //选择
          me.setBallData(Number(param['row']), Number(param['value'] - 1), 1);
          var order = {};
          order['odds'] = $(target).find('.ball-odds').html();
          order['row'] = param['row'] || 0;
          order['lotterys'] = param['value'];
          order['content'] = $(target).find('.ball').html() || $(target).find('.balls').html();
          order['contentText'] = order['content'];
          order['type'] = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName();
          order['playType'] = !param['type'] ? '' : param['type'];
		  order['num'] = 1;
          me.fireEvent('afterSelectBall', order)
        } else {
          try {
            console.log('GameMethod.exeEvent_ball: lack param');
          } catch (ex) {}
        }
      }

      me.updateData();
    },
    //直选玩法 键盘输入选球
    exeEvent_input: function(param, target) {
      var me = this,
        el = $(target),
        domArr = me.ballsDom[0],
        val = '';
      val = me.regMatchNum2(el.val());
      el.val(val);
      if (val.length < 2) return;
      if (val.length == 2) {
        $.each(domArr, function(i, v) {
          var $dom = $(v);
          if ($dom.find('.ball').html() == val) {
            $dom.trigger('click');
            el.val('');
            return;
          }
        });
      }
      if (val.length > 2) {
        el.val('');
      }

    },
    //直选玩法 键盘输入金额
    exeEvent_amount: function(param, target) {
      var me = this,
        el = $(target);
      val = me.regMatchNum(el.val());
      el.removeClass('flash');
      if (val > defConfig.amountDigit) {
        val = Number(defConfig.amountDigit);
      }
      el.val(val);
      Games.getCurrentGameOrder().setOrderAmountById(param['id'], val);
      Games.getCurrentGameOrder().updateData();
    },
    exeEvent_setAmount: function(param, target) {
      var me = this,
        el = $(target),
        $li = Games.getCurrentGameOrder().container.find('li input');
      val = me.regMatchNum(el.val());
      if (val > defConfig.amountDigit) {
        val = Number(defConfig.amountDigit);
      }
      if (val == 0) {
        return
      }
      el.val(val);
      $li.val(val);
	 
	  Games.getCurrentGameOrder().setOrderAmountByAll(val);

	  

      Games.getCurrentGameOrder().updateData();
    },
    //直选玩法 隐藏倍率选择
    exeEvent_toggle: function(param, target) {
      var me = this,
        el = $(target),
        content = el.html(),
        ol = el.parent().parent().find('ol');

      if (content == '+') {
        el.html('-');
        //缩小
        ol.find('li').show();
      } else {
        el.html('+');
        ol.find('li').hide();
        ol.find('.active').show();
      }
    },
    exeEvent_clear: function(param, target) {
      var me = this;
      if (Games.getCurrentGameOrder().getTotal()['count'] <= 0) {
        return false;
      }
      Games.getCurrentGameMessage().show({
        mask: true,
        title: '温馨提示',
        content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确认删除号码篮内全部内容吗?</h4></div></div>',
        confirmIsShow: true,
        cancelIsShow: true,
        cancelFun: function() {
          Games.getCurrentGameMessage().hide();
        },
        confirmFun: function() {
          Games.getCurrentGameOrder().reSet();
          me.reSet();
          Games.getCurrentGameMessage().hide();
        }
      });
    },
    //直选玩法 隐藏倍率选择
    exeEvent_changeOdd: function(param, target) {
      var me = this,
        el = $(target),
        val = param['value'],
        ol = el.parent().parent().find('ol');
      if (el.hasClass('active')) {
        return
      };
      ol.find('li').removeClass('active');
      el.addClass('active');
      me.changeCurrentOdd(val);
      me.orderToShow(Games.getCurrentGameOrder().getOrderData());

      var gameName = Games.getCurrentGame().getCurrentGameMethod().defConfig.name;
      if (gameName == 'tema.zhixuan') {
        //设置赔率缓存
        $.cookie('user_odd', val);
      } else {
        $.cookie(gameName + '_user_odd', val);
      }
      me.fireEvent('afterChangeOdd', param);
    },
    exeEvent_changeGameType: function(param, target) { //切换复式、胆拖
      var me = this;
      $(target).addClass('active').siblings().removeClass('active');
      $('.ball-content-' + param.value).show().siblings().hide();
      if (param.value == 'dantuo') {
        $('.ball-content-' + param.value).find('.active').trigger('click');
      } else {
        me.defConfig.betType = param.value;
      }
      // me.updateBallData(param.value);
      me.fireEvent('updatePreSelect');
      $('.select-tool .active').removeClass('active');
    },
    exeEvent_switchDanTuo: function(param, target) { //切换胆、拖
      var me = this;
      $(target).addClass('active').siblings().removeClass('active');
      me.defConfig.betType = param.value;
    },
    exeEvent_preSelect: function(param, target) { //预选
      var me = this,
        el = $(target),
        currCls = me.defConfig.ballCurrentCls;

      if (el.hasClass(currCls)) {
        var data = {};
        data['odds'] = $(target).find('.ball-odds').html();
        data['lotterys'] = param['value'];
        data['row'] = param['row'];
        data['type'] = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName();
        data['sub'] = param['type'];
        data['betType'] = me.defConfig.betType;
        me.removePreSelect(data);
      } else {
        //必要参数
        if (param['value'] != undefined && param['row'] != undefined) {
          //选择
          var order = {};
          order['odds'] = $(target).find('.ball-odds').html();
          order['row'] = param['row'] || 0;
          order['lotterys'] = param['value'];
          order['content'] = $(target).find('.ball').html() || $(target).find('.balls').html();
          order['type'] = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName();
          order['sub'] = param['type'];
          order['betType'] = me.defConfig.betType;
          if (me.checkMaximum(order)) {
            return false;
          }
          me.addPreSelect(order);
        } else {
          try {
            console.log('GameMethod.exeEvent_preSelect: lack param');
          } catch (ex) {}
        }
      }

      me.fireEvent('updatePreSelect');
    },
    exeEvent_delPreSelect: function(param, target) { // 删除指定预选球
      var me = this;
      var data = {};
      data['odds'] = $(target).find('.ball-odds').html();
      data['lotterys'] = param['value'];
      data['row'] = param['row'];
      data['type'] = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName();
      data['sub'] = param['type'];
      data['betType'] = me.defConfig.betType;
      data['subType'] = me.defConfig.subType;

      me.removePreSelect(data);
      me.fireEvent('updatePreSelect');
    },
    exeEvent_removeAllPreSelect: function(param, target) { // 移除当前所有预选
      var me = this,
        betType = param.betType || me.defConfig.betType,
        oddType = me.getCurrentOddsByDom(),
        preSelect = me.defConfig.preSelect[oddType];

      if (betType == 'dan' || betType == 'tuo') {
        preSelect['dan'] = [];
        preSelect['tuo'] = [];
      } else {
        preSelect[betType] = [];
      }

      $('.select-tool .active').removeClass('active');
      me.fireEvent('updatePreSelect');
    },
    exeEvent_confirmBall: function(param, target) { // 确认下注
      var me = this,
        defConfig = me.defConfig,
        oddType = me.getCurrentOddsByDom(),
        preSelect = defConfig.preSelect[oddType],
        subType = defConfig.subType,
        playType = defConfig.playType,
        $odds = $('.'+ subType +' .game-odds2 ol li'),
        $odd = $odds.filter('.active'),
        row = $odds.index($odd),
        getGameOdds = Games.getCurrentGame().getGameConfig().getInstance().getGameOdds(),
        odds ,//=getGameOdds[subType][playType],
        lotterys,
        contents,
		oddArray= new Array(),
		tmpOddArray= new Array();

		for(var i=0; i<getGameOdds.length; i++){
			var childs=getGameOdds[i].childs;
			
			if(getGameOdds[i].name==subType){
				for(var j=0;j<childs.length; j++){
					var child=childs[j];
					if(child.name==playType){
						tmpOddArray.push(child.odds);
					}
				}
			}
		}
		tmpOddArray.sort(function(a, b){return a-b;});
		var name = me.defConfig.name;
		var $dom = me.container.find('.'+subType).find('.ball-list-wrap').find('.ball-list-ul').find('li.active');
	switch (name) {
        case 'zhengtema.lianxiaozhong':		  
		$dom.each(function(idx, node){
			var tmpSize = $(node).find(".ball-group").size();
				if(tmpSize > 4   ){
					if(typeof oddArray !== 'undefined' && oddArray.length > 0 && oddArray[0]== tmpOddArray[0]){

					}else{
						oddArray.push(tmpOddArray[0]);
					}
				} else if( tmpSize < 5 ) {
					if(typeof oddArray !== 'undefined' && oddArray.length > 0 && oddArray[0]== tmpOddArray[tmpOddArray.length-1]){

					}else{
						oddArray.push(tmpOddArray[tmpOddArray.length-1]);
					}
					
					
					
				}
			});
          break;
		 case 'zhengtema.lianxiaobuzhong':
          $dom.each(function(idx, node){
			var tmpSize = $(node).find(".ball-group").size();
				if(tmpSize < 5   ){
					if(typeof oddArray !== 'undefined' && oddArray.length > 0 && oddArray[0]== tmpOddArray[0]){

					}else{
						oddArray.push(tmpOddArray[0]);
					}
				} else if( tmpSize > 4 ) {
					if(typeof oddArray !== 'undefined' && oddArray.length > 0 && oddArray[0]== tmpOddArray[tmpOddArray.length-1]){

					}else{
						oddArray.push(tmpOddArray[tmpOddArray.length-1]);
					}
					
					
					
				}
			});
          break;
        default:
		oddArray.push(tmpOddArray);
      }
		
	  odds=oddArray;
      var order = {};
      order['odds'] = odds.toString();
      order['row'] = row || 0;
      order['contentText'] = $odd.find('.name').html();
      order['content'] = order['contentText'];
      order['type'] = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName();
      order['sub'] = defConfig.subType;
      order['betType'] = defConfig.betType;
      order['playType'] = defConfig.playType;
      if (order['betType'] == 'dan' || order['betType'] == 'tuo') {
        lotterys = { dan: [], tuo: [] };
        contents = { dan: [], tuo: [] };
        $.each(['dan', 'tuo'], function(i, name) {
          $.each(preSelect[name], function(i, obj) {
            lotterys[name].push(obj.lotterys);
            contents[name].push(obj.content);
          });
          lotterys[name] = lotterys[name].join(',');
        });
        order['lotterys'] = lotterys;
        order['content'] = contents;
      } else {
        lotterys = [];
        contents = [];
        $.each(preSelect[order.betType], function(i, obj) {
          lotterys.push(obj.lotterys);
          contents.push(obj.content);
        });
        order['lotterys'] = lotterys.join(',');
        order['content'] = contents.join(',');
      }
      if (me.validate(order)) {
        return false;
      }
      order['amount'] = me.getAmount(order);
	  order['num'] = order['amount'];
      me.fireEvent('afterSelectBall', order);
      me.exeEvent_removeAllPreSelect.call(me, {
        betType: order['betType']
      });
    },
    //批量预选球事件
    exeEvent_batchSetPreBall: function(param, target) {
      var me = this,
        ballsData = me.balls,
        x = 0,
        type = param['type'],
        i = 0,
        len = ballsData[x].length,
        start = (typeof param['start'] == 'undefined') ? 0 : Number(param['start']),
        halfLen = Math.ceil((len - start) / 2 + start),
        value = param['value'],
        zoadiacList = Games.getCurrentGame().getGameConfig().getInstance().getZoadiacList() || [],
        colorArr = me.defConfig.colorArr,
        currCls = me.defConfig.ballCurrentCls,
        $dom = $(target),
        $ballsDom = me.ballsDom[0],
        $ball,
        maximum = true;
      switch (type) {
        case 'zodiacs':
          $.each(zoadiacList, function(j, v) {
            if (value != '' && value == v['zodiacName']) {
              var arr_num = v['number'].split(",");

              if (me.checkMaximum({}, arr_num.length)) {
                maximum = false;
                return false;
              }
              $.each(arr_num, function(k, v) {
                $ball = $($ballsDom[v - 1]);
                !$ball.hasClass('active') && $ball.trigger('click');
              })
            }
          })
          break;
        case 'bits':
          if ((value == 0 && me.checkMaximum({}, 4)) || (value != 0 && me.checkMaximum({}, 5))) {
            maximum = false;
            return false;
          }
          for (; i < len; i++) {
            var after_num = (i + 1) % 10;
            if (value == after_num) {
              $ball = $($ballsDom[i]);
              !$ball.hasClass('active') && $ball.trigger('click');
            }
          }
          break;
        case 'ten':
          if ((value == 0 && me.checkMaximum({}, 9)) || (value != 0 && me.checkMaximum({}, 10))) {
            maximum = false;
            return false;
          }
          for (; i < len; i++) {
            var after_num = parseInt((i + 1) / 10);
            if (value == after_num) {
              $ball = $($ballsDom[i]);
              !$ball.hasClass('active') && $ball.trigger('click');
            }
          }
          break;

        default:
          break;
      }
      if(maximum){
        $dom.addClass(currCls);
      } else {
        // me.showTips('球数超过限制！');
      }
    },
    changeCurrentOdd: function(val) {
      var me = this,
          name = me.defConfig.name;
      me.straightOdds = val;
      switch (name) {
        case 'zhengtema.lianxiaozhong':		  
          val = val.split(',');
		  val.sort(function(a, b){return a-b;});
		  
          $.each(me.ballsDom, function(i, v) {
           // $(v).find(me.defConfig.ballOddDom).text(val.pop())
           //  .eq(0).text(val.pop());
			$dom=$(v).find(me.defConfig.ballOddDom);
			$dom.each(function(idx, node){
				var tmpSize = $("li[data-param='" + $(node).parent().attr("data-param") + "']").children(".ball-group").size();
				if(tmpSize > 4){
					$(node).html(val[0]);
					$(node).eq(0).html(val[0]);
				} else {
					$(node).html(val[val.length - 1]);
					$(node).eq(0).html(val[val.length - 1]);
				}
			});
          });
          break;
		 case 'zhengtema.lianxiaobuzhong':
		          val = val.split(',');
		  val.sort(function(a, b){return a-b;});
		  
          $.each(me.ballsDom, function(i, v) {
           // $(v).find(me.defConfig.ballOddDom).text(val.pop())
           //  .eq(0).text(val.pop());
			$dom=$(v).find(me.defConfig.ballOddDom);
			$dom.each(function(idx, node){
				var tmpSize = $("li[data-param='" + $(node).parent().attr("data-param") + "']").children(".ball-group").size();
				if(tmpSize < 5){
					$(node).html(val[0]);
					$(node).eq(0).html(val[0]);
				} else {
					$(node).html(val[val.length-1 ]);
					$(node).eq(0).html(val[val.length -1]);
				}
			});
          });
          break;
        default:
          $.each(me.ballsDom, function(i, v) {
            $(v).find(me.defConfig.ballOddDom).html(val);
          });
      }
    },
    //渲染球dom元素的对应状态
    batchSetBallDom: function() {
      var me = this,
        cfg = me.defConfig,
        cls = cfg.ballCurrentCls,
        balls = me.balls,
        i = 0,
        j = 0,
        len = balls.length,
        len2 = 0,
        ballsDom = me.getBallsDom();
      //同步选球数据和选球dom
      for (; i < len; i++) {
        len2 = balls[i].length;
        for (j = 0; j < len2; j++) {
          if (balls[i][j] == 1) {
            if ($(ballsDom[i][j]).hasClass(cls)) {} else {
              $(ballsDom[i][j]).addClass(cls);
            }
          } else {
            $(ballsDom[i][j]).removeClass(cls);
          }
        }
      }

    },
    setBallToOrder: function() {
      var me = this,
        cfg = me.defConfig,
        cls = cfg.ballCurrentCls,
        balls = me.balls,
        i = 0,
        j = 0,
        len = balls.length,
        len2 = 0,
        ballsDom = me.getBallsDom();
      for (; i < len; i++) {
        len2 = balls[i].length;
        for (j = 0; j < len2; j++) {
          if (balls[i][j] == 1) {
            var order = {},
              $target = $(ballsDom[i][j]),
              param = formatParam($target.attr('data-param'));

            order['odds'] = $target.find('.ball-odds').html();
            order['row'] = param['row'] || 0;
            order['lotterys'] = param['value'] || Number($target.find('.ball').html()) + '';
            order['content'] = $target.find('.ball').html() || $target.find('.balls').html();
            order['type'] = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName();
			order['sub'] = param.type;
			order['contentText'] = order['content'];
			order['type'] = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName();
			order['playType'] = !param['type'] ? '' : param['type'];
			order['num'] = 1;			
            Games.getCurrentGameOrder().add(order);
          }
        }
      }
    },
    //当选球/取消发生，更新相关数据
    updateData: function() {
      var me = this;
      //通知其他模块更新
      me.fireEvent('updateData');
    },
    orderToShow: function(obj) {
      var me = this,
        orderData = obj,
        type = me.getGameMethodName(),
        lottery = orderData[type],
        ballsPos = 0;
      me.reSet();
      if (!orderData) return;
      if (orderData.hasOwnProperty(type)) {
        //直选不同赔率渲染不同界面
        if (type == 'tema.zhixuan') {
          var filterLottery = [];
          $.each(lottery, function(i, v) {
            if (v['odds'] == me.straightOdds) {
              filterLottery.push(v);
            }
          });
          lottery = filterLottery;
        }
        $.each(lottery, function(i, v) {
          ballsPos = Number(v['lotterys']) - 1;
          me.setBallData(v['row'], ballsPos, 1);
        });
        me.updateData();
      }

    },
    addPreSelect: function(order) { //添加单个预选
      var me = this,
        type = me.defConfig.betType,
        preSelect = me.defConfig.preSelect,
        oddType = me.getCurrentOddsByDom();

      preSelect[oddType] = preSelect[oddType] || {};
      preSelect[oddType][type] = preSelect[oddType][type] || [];

      var orderList = preSelect[oddType][type];
      orderList.push(order);
    },
    getCurrentOddsByDom: function() {
      var me = this;
      return me.defConfig.playType;
    },
    removePreSelect: function(data) { //移除单个预选
      var me = this,
        betType = data.betType,
        oddType = me.getCurrentOddsByDom(),
        preSelectList = me.defConfig.preSelect[oddType];

      if (betType == 'dan' || betType == 'tuo') {
        betType = ['dan', 'tuo'];
      } else {
        betType = [betType];
      }

      $.each(betType, function(i, type) {
        preSelectList[type] = $.map(preSelectList[type], function(obj) {
          if (obj.odds == data.odds && obj.row == data.row && obj.lotterys == data.lotterys) {
            return null;
          } else {
            return obj;
          }
        });
      });

      $('.select-tool .active').removeClass('active');
    },
    checkOrderRepeat: function(order) { //检查重复，（好像没有必要）
      var me = this,
        type = me.defConfig.betType,
        oddType = me.getCurrentOddsByDom(),
        preSelect = me.defConfig.preSelect[oddType],
        list, r;
      list = preSelect[type];
      $.each(list, function(i, obj) {
        if (obj.odds == order.odds && obj.row == order.row && obj.lotterys == order.lotterys) {
          r = true;
        }
      });
      return r;
    },
    updatePreSelect: function() { // 根据数据更新预选框DOM

      var me = this,
        oddType = me.getCurrentOddsByDom(),
        betType = me.defConfig.betType,
        subType = me.defConfig.subType,
        preSelect = me.defConfig.preSelect;
        preSelect[oddType] = preSelect[oddType]||{
          'fushi': [],
          'dan': [],
          'tuo': []
        };
      $.each(me.defConfig.preSelect[oddType], function(name, list) {
        var html = [];
        $.each(list, function(i, ball) {
          html.push(me.preSelectDom(ball));
        });
        if (!html.length){
          $('.'+ subType +' .ball-content-' + name).html('');
        } else {
          $('.'+ subType +' .ball-content-' + name).html(html.join(''));
        }

      });

    },
    preSelectDom: function(ball){
      var me = this,
          defConfig = me.defConfig,
          lotterys = Number(ball.lotterys),
          colorArr = defConfig.colorArr,
          colorNameMap = defConfig.colorNameMap,
          name = defConfig.name,
          html;

      if (name == 'zhengtema.lianxiaozhong' || name == 'zhengtema.lianxiaobuzhong'){
        html =  '<li data-param="action=delPreSelect&type='+ball['sub']+'&value=' + ball.lotterys + '&row=' + ball.row + '"><span class="ball">' + ball.content + '</span><span class="ball-odds">' + ball.odds + '</span></li>';
      } else {
        html = '<li data-param="action=delPreSelect&type='+ball['sub']+'&value=' + ball.lotterys + '&row=' + ball.row + '"><span class="ball ' + colorNameMap[colorArr[lotterys - 1]] + '">' + ball.content + '</span><span class="ball-odds">' + ball.odds + '</span></li>';
      }
      return html;
    },
    updateBallData: function(type) { // 根据数据更新选球状态
      var me = this,
        oddType = me.getCurrentOddsByDom(),
        preSelectList = me.defConfig.preSelect[oddType],
        list;
      me.rebuildData();
      if (preSelectList) {
        type = type || me.defConfig.betType;

        if (type == 'dan' || type == 'tuo') {
          list = preSelectList['dan'].concat(preSelectList['tuo']);
        } else {
          list = preSelectList[type];
        }
        list && $.each(list, function(i, obj) {
          me.setBallData(Number(obj['row']), Number(obj['lotterys'] - 1), 1);
        });
      }

      me.batchSetBallDom();
    },
    checkMaximum: function(order, adjust) { // 检查球数是否超过限制
      // 胆在不同玩法有不同个数限制
      var me = this,
        defConfig = me.defConfig,
        betType = order.betType || defConfig.betType,
        oddType = me.getCurrentOddsByDom(),
        balls,
        adjust = adjust || 1,
        limit;

      if (!me.defConfig.preSelect[oddType] || !me.defConfig.preSelect[oddType][betType]) {
        return false;
      } else {
        balls = me.defConfig.preSelect[oddType][betType];
      }
      if (betType == 'fushi') {
        return (balls.length > (14 - adjust));
      } else if (betType == 'dan') {
        limit = defConfig.oddsMap[defConfig.playType]['limit'];
        return (balls.length >= (limit - adjust));
      } else if (betType == 'tuo') {
        return (balls.length > (7 - adjust));
      }
    },
    validate: function(order) {
      //下注报错提示
      var me = this,
        oddsMap = me.defConfig.oddsMap,
        betType = order['betType'],
        lotterys = order['lotterys'],
        hasError = false;

      $.each(oddsMap, function(i, obj) {
        if (obj.cnName == order['contentText']) {

          if (betType == 'fushi' && lotterys.split(',').length < obj.limit) {
            me.showTips('球数至少选择' + obj.limit + '球');
            hasError = true;
          } else if (betType == 'dan' || betType == 'tuo') {
            var dan = lotterys['dan'] && lotterys['dan'].split(','),
              tuo = lotterys['tuo'] && lotterys['tuo'].split(',');
            if (dan.length > (obj.limit - 1) || !dan.length || !tuo.length || ((dan.length + tuo.length)< obj.limit )) {
              hasError = true;
              me.showTips('号码选择不完整，请重新选择！');
            }
          }
        }
      });
      return hasError;
    },
    showTips: function(text) {
      Games.getCurrentGameMessage().show({
        mask: true,
        title: '温馨提示',
        content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">' + text + '</h4></div></div>',
        cancelIsShow: true,
        cancelText: '关闭',
        cancelFun: function() {
          Games.getCurrentGameMessage().hide();
        }
      });
    },
    getRandomBetsNum: function() {
      return this.defConfig.randomBetsNum;
    },
    //格式化纯数字
    regMatchNum: function(str) {
      var reg = /\D/g;
      return Number(str.replace(reg, ''));
    },
    //格式化数字字符串
    regMatchNum2: function(str) {
      var reg = /\D/g;
      return str.replace(reg, '');
    },
    // 计算注数
    getAmount: function(order) {
      var me = this,
        amount = 1,
        lotterys = order['lotterys'],
        limit;
      $.each(me.defConfig.oddsMap, function(i, obj) {
        if (obj.cnName == order['contentText']) {
          limit = obj.limit;
        }
      });
      if (order['betType'] == 'fushi') {
        lotterys = lotterys.split(',');
        amount = me.calCombin(lotterys.length, limit);
      } else if (order['betType'] == 'dan' || order['betType'] == 'tuo') {
        var dan = lotterys['dan'].split(','),
          tuo = lotterys['tuo'].split(',');
        amount = me.calCombin(tuo.length, limit - dan.length);
      }
      return amount;
    },
    //计算组合数 C(4,2) 表示4取2
    calCombin: function(max, min) {
      var me = this;
      return me.factorial(max) / (me.factorial(max - min) * me.factorial(min));
    },
    // 阶乘
    factorial: function factorial(num) {
      num = parseInt(num);
      if(isNaN(num)){ return false;}
      if (num <= 1)
        return 1;
      else
        return num * factorial(num - 1);
    }

  };

  var Main = host.Class(pros, Event);
  Main.defConfig = defConfig;
  host[name] = Main;

})(phoenix, "GameMethod", phoenix.Event);
