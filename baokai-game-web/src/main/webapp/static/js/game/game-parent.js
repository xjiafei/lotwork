//Games
(function (host, name, undefined) {

    var Main = {};

    //缓存
    Main.cacheData = {};

    //当前游戏
    Main.currentGame = null;
    //玩法切换
    Main.currentGameTypes = null;
    //当前统计
    Main.currentGameStatistics = null;
    //当前号码篮
    Main.currentGameOrder = null;
    //当前追号
    Main.currentGameTrace = null;
    //投注按钮
    Main.currentGameSubmit = null;
    //当前游戏消息类
    Main.currentGameMessage = null;
    //多奖级
    Main.currentIsMoreBouns = {};


    //当前游戏
    Main.getCurrentGame = function () {
        return Main.currentGame;
    };
    Main.setCurrentGame = function (game) {
        Main.currentGame = game;
    };

    //玩法切换
    Main.getCurrentGameTypes = function () {
        return Main.currentGameTypes;
    };
    Main.setCurrentGameTypes = function (currentGameTypes) {
        Main.currentGameTypes = currentGameTypes;
    };

    //选号状态
    Main.getCurrentGameStatistics = function () {
        return Main.currentGameStatistics;
    };
    Main.setCurrentGameStatistics = function (gameStatistics) {
        Main.currentGameStatistics = gameStatistics;
    };

    //号码篮
    Main.getCurrentGameOrder = function () {
        return Main.currentGameOrder;
    };
    Main.setCurrentGameOrder = function (currentGameOrder) {
        Main.currentGameOrder = currentGameOrder;
    };

    //追号
    Main.getCurrentGameTrace = function () {
        return Main.currentGameTrace;
    };
    Main.setCurrentGameTrace = function (currentGameTrace) {
        Main.currentGameTrace = currentGameTrace;
    };

    //投注提交
    Main.getCurrentGameSubmit = function () {
        return Main.currentGameSubmit;
    };
    Main.setCurrentGameSubmit = function (currentGameSubmit) {
        Main.currentGameSubmit = currentGameSubmit;
    };

    //消息提示
    Main.getCurrentGameMessage = function () {
        return Main.currentGameMessage;
    };
    Main.setCurrentGameMessage = function (currentGameMessage) {
        Main.currentGameMessage = currentGameMessage;
    };


    host[name] = Main;

})(phoenix, "Games");


//游戏类
//所有游戏应继承该类
(function (host, name, Event, undefined) {
    var defConfig = {
            //游戏名称
            name: '',
            //资源存放目录
            basePath: staticFileContextPath + '/static/js/game/',
            //文件名前缀
            baseNamespace: 'phoenix.Games.',
            //游戏后台配置
            //dynamicConfigUrl:'simulatedata/dynamicconfig.php',
            dynamicConfigUrl: '',
            //添加事件代理的主面板
            eventProxyPanel: 'body'
        },
        Games = host.Games;
    //将来仿url类型的参数转换为{}对象格式，如 q=wahaha&key=323444 转换为 {q:'wahaha',key:'323444'}
    //所有参数类型均为字符串
    var formatParam = function (param) {
        var arr = $.trim(param).split('&'), i = 0, len = arr.length,
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
        init: function (cfg) {
            var me = this;
            me.setName(cfg.name);
            //当前web期号
            me.currentNumber = '';
            //当前期号
            me.currentIssueCode = 0;
            //设置当前游戏
            Games.setCurrentGame(me);

            //资源加载缓存
            me.loadedHas = {};
            //当前使用的玩法
            me.currentGameMethod = null;
            //游戏服务器端配置
            me.dynamicConfig = null;
            //添加游戏加载缓存
            //防止用户多次点击玩法按钮
            me.methodLoadCache = false;

            me.addEvent('afterSwitchGameMethod', function () {
                Games.getCurrentGame().getCurrentGameMethod().reSet();

                //重置加载缓存标记
                me.methodLoadCache = false;

                //切换玩法时，针对当前玩法进行倍数限制设置
                var typeName = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
                    limitObj = Games.getCurrentGame().getDynamicConfig()['gamelimit'],
                    nowGameType = Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"];
                if (limitObj[typeName]) {

                    Games.getCurrentGameStatistics().setMultipleLimit(limitObj[typeName]['maxmultiple']);
                    //n115 set specialMultiple
                    if(typeName == 'quwei.normal.dingdanshuang'){
                        Games.getCurrentGame().specialMultiple = limitObj[typeName]['specialMultiple'];
                    }

                } else { //没有返玩法限制及奖金组时，给-1
                    Games.getCurrentGameStatistics().setMultipleLimit(-1);
                }
                //切换后选中倍数给1倍
                Games.getCurrentGameStatistics().setMultipleDom(1);
                //切换后元角选择为元  richardgong 2015-06-26 注释
                //Games.getCurrentGameStatistics().setMoneyUnitDom(1);
                //切换后后收起我要追号
                if ($.trim(nowGameType) != 'slmmc' && $.trim(nowGameType) != 'sl115') {
                    Games.getCurrentGameTrace().hide();
                }
                //获取奖金，玩法描述，及遗漏，冷热数据
                Games.getCurrentGame().getCurrentGameMethod().updataGamesInfo();
            });
            me.addEvent('changeDynamicConfig', function () {
                me.updateDynamicConfig();
            });
        },
        //获取当前玩法/指定玩法的最大倍数限制
        getMaxMultipleLimit: function (type) {
            var typeName = type || Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
                limitObj = Games.getCurrentGame().getDynamicConfig()['gamelimit'];
            return Number(limitObj[typeName]['maxmultiple']);
        },

        getDynamicConfigUrl: function () {
            return Games.getCurrentGame().getGameConfig().getInstance().getDynamicConfigUrl();
        },
        //从服务器端获取数据
        //返回数据格式
        //{"isSuccess":1,"type":"消息代号","msg":"返回的文本消息","data":{xxx:xxx}}
        getServerDynamicConfig: function (callback) {
            var me = this, cfg = me.defConfig, gameType = me.getName();
            $.ajax({
                url: Games.getCurrentGame().getGameConfig().getInstance().getDynamicConfigUrl() + '?gametype=' + gameType,
                dataType: 'JSON',
                cache: false,
                success: function (data) {
                    if (Number(data['isSuccess']) == 1) {

                        data['data']['gamelimit'] = data['data']['gamelimit'][0];

                        //	var aa= data['data'];
                        //	 var ss=JSON.stringify(data['data']['gamelimit']);
                        //ss=ss.replace('[',"");
                        //ss=ss.replace(']',"");
                        //	     ss.substr(1).substring(-1,0); //替换返回字符串 首尾中括号
                        //       var gamelimit=jQuery.parseJSON(ss);
                        //     delete aa.gamelimit;
                        //	     aa.gamelimit=gamelimit;
                        me.setDynamicConfig(data['data']);
                        if ($.isFunction(callback)) {
                            callback.call(me, data['data']);
                        }
                    }

                }
            });
        },
        getDynamicConfig: function () {
            return this.dynamicConfig;
        },
        setDynamicConfig: function (cfg) {
            this.dynamicConfig = cfg;
            this.fireEvent('changeDynamicConfig', cfg);
        },
        //获取奖金组
        addDynamicBonus: function (gameMethodName, data) {
            var me = this,
                dynamicConfig = me.getDynamicConfig();

            if (typeof dynamicConfig['gamelimit'] != 'undefined') {
                if (typeof dynamicConfig['gamelimit'][gameMethodName] != 'undefined') {
                    dynamicConfig['gamelimit'][gameMethodName]['usergroupmoney'] = data;
                }
            }
        },
        //更新后台配置信息后，更新相关内容
        updateDynamicConfig: function () {
            var me = this, dConfig = me.getDynamicConfig(), lastballs = dConfig['lastballs'].split(',');

            if (dConfig['isstop'] == 1) {
                setTimeout(function () {
                    phoenix.Games.getCurrentGameMessage().show({
                        type: 'lotteryClose',
                        data: {
                            tplData: {
                                //当期彩票详情
                                lotterys: [1, 2, 3, 4, 5, 6],
                                //彩种名称
                                lotteryName: 'shishicai',
                                //开奖期数
                                lotteryPeriods: '20130528-276',
                                //开始购买时间
                                orderDate: {'year': '2013', 'month': '5', 'day': '3', 'hour': '1', 'min': '30'},
                                //提示彩票种类
                                lotteryType: [{'name': 'leli', 'pic': '#', 'url': 'http://163.com'}, {
                                    'name': 'kuaile8',
                                    'pic': '#',
                                    'url': 'http://pp158.com'
                                }]
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
            //上期开奖号码
            $('#J-lottery-info-balls').find('em').each(function (i) {
                this.innerHTML = lastballs[i];
            });


            //新奖期已开出，清空数据缓存
            //Games.cacheData = {};
            Games.cacheData['frequency'] = {};
            Games.cacheData['charts'] = {};

        },
        //事件代理，默认只监听鼠标点击事件，如需要监听其他事件，请在具体的游戏类中实现
        //例： <span data-param="action=doSelect&value=10">点击</span>
        eventProxy: function () {
            var me = this, cfg = me.defConfig, panel = $(cfg.eventProxyPanel),
                action = '';
            panel.click(function (e) {
                var q = e.target.getAttribute('data-param'), param, gameMethod;
                if (q && $.trim(q) != '') {
                    e.preventDefault();
                    param = formatParam(q);
                    gameMethod = me.getCurrentGameMethod();
                    if (gameMethod) {
                        gameMethod.exeEvent(param, e.target);
                    }
                }
            });
        },
        //根据名字返回玩法对象
        getGameMethodByName: function (name) {
            var me = this, has = me.loadedHas;
            if (me.hasOwnProperty(name) && has.hasOwnProperty(me.buildPath(name))) {
                return me[name];
            }
        },
        //切换游戏玩法
        switchGameMethod: function (name) {
            var me = this, p, has = me.loadedHas, obj;

            if (me.methodLoadCache) {
                return;
            }
            //记录当前加载缓存
            //防止用户多次点击玩法加载按钮
            me.methodLoadCache = true;

            me.fireEvent('beforeSwitchGameMethod', name);
            for (p in me) {
                if (me.getGameMethodByName(p)) {
                    if (p != name) {
                        me[p].hide();
                    } else {
                        me[p].show();
                        me.currentGameMethod = me[p];
                        me.fireEvent('afterSwitchGameMethod');
                    }

                }
            }
            if (!me.getGameMethodByName(name)) {
                me.setup(name, function () {
                    obj = me.getGameMethodByName(name);
                    obj.show();
                    me.currentGameMethod = obj;
                    me.fireEvent('afterSetup');
                    me.fireEvent('afterSwitchGameMethod');
                });
            }
        },
        getCurrentGameMethod: function () {
            return this.currentGameMethod;
        },
        //name 玩法类型.玩法组.玩法(如:'wuxing.zhixuan.fushi')
        setup: function (name, callback) {
            var me = this,
                src = me.buildPath(name),
                fn = function () {
                },
                _callback;

            //获取最后一个参数作为回调函数
            _callback = arguments.length > 0 ? arguments[arguments.length - 1] : fn;
            if (!$.isFunction(_callback)) {
                _callback = fn;
            }

            //加载脚本并缓存
            if (!me.isSetuped(name)) {
                $.ajax({
                    url: src,
                    cache: false,
                    dataType: 'script',
                    success: function () {
                        me.loadedHas[src] = true;
                        _callback.call(me);
                    },
                    error: function (xhr, type) {
                        alert('资源加载失败\n' + src + '\n错误类型：' + type);
                    }
                });
            }
        },
        //拼接路径
        buildPath: function (name) {
            var me = this,
                path = me.defConfig.basePath,
                nameSpace = me.defConfig.baseNamespace,
            //拼接名称为路径，并剔除空参数(空参数为了适应没有三级分组的游戏)
                src = path + nameSpace + name + '.js';
            return src;
        },
        //检测某模块是否已安装
        isSetuped: function (name) {
            var me = this, has = me.loadedHas, path = me.buildPath(name);
            return has.hasOwnProperty(path);
        },
        //直接设置某资源已经加载
        setSetuped: function (type, group, method) {

        },
        //返回该游戏的游戏配置
        //在子类中实现
        getGameConfig: function () {

        },
        getName: function () {
            return this.name;
        },
        setName: function (name) {
            this.name = name
        },
        setCurrentNumber: function (v) {
            this.currentNumber = v;
        },
        getCurrentNumber: function () {
            return this.currentNumber;
        },

        setCurrentIssueCode: function (v) {
            this.currentIssueCode = v;
        },
        getCurrentIssueCode: function () {
            return this.currentIssueCode;
        },
        //对最后即将进行提交的数据进行处理
        editSubmitData: function (data) {
            return data;
        },
        //奖金组处理流程
        bonusGroupProce: function () {
        },
        //过滤URL中的BALL参数
        //该玩法会在页面中的实例中被覆盖修改
        parseBallData: function (gametype, ballData) {
            var me = this,
                games = host.Games,
                order = {},
                ballArray = [],
                current = [],
                gameOrder = games.getCurrentGameOrder();

            ballArray = ballData.split('_');

            for (var i = ballArray.length - 1; i >= 0; i--) {
                current = [];
                singel = ballArray[i].split('-');

                for (var k = singel.length - 1; k >= 0; k--) {
                    current.push(singel[k].split(''));
                }
                ;

                order = {
                    'type': gametype,
                    'original': current,
                    'lotterys': current,
                    'moneyUnit': Games.getCurrentGameStatistics().getMoneyUnit(),
                    'multiple': Games.getCurrentGameStatistics().getMultip(),
                    'onePrice': Games.getCurrentGameStatistics().getOnePrice(),
                    'num': current.length
                };
                order['amountText'] = Games.getCurrentGameStatistics().formatMoney(order['num'] * order['moneyUnit'] * order['multiple'] * order['onePrice']);

                //返回投注信息
                gameOrder.add(order);
            }
            ;
        },
        //彩种外部投注情况
        //过滤URL中的彩种数据
        parseDataFormUrl: function () {
            var me = this,
                gametypes = host.Games.getCurrentGameTypes(),
                gameType = $.trim(host.util.getParam('gametype')),
                ballData = $.trim(host.util.getParam('ball'));

            //Url投注功能
            //初始化游戏种类数据
            if (gameType) {
                //切换玩法
                gametypes.changeMode(gameType);
            }

            //如果有站外投注数据
            //则开始执行过滤函数
            if (ballData) {
                //向号码栏添加格式化后的参数
                me.parseBallData(gameType, ballData);
            }
        }
    };

    var Main = host.Class(pros, Event);
    Main.defConfig = defConfig;
    host[name] = Main;

})(phoenix, "Game", phoenix.Event);


//游戏方法类
//所有具体游戏实现应继承该类
(function (host, name, Event, undefined) {
    var defConfig = {
            //玩法名称，必须是完整的名称
            //如：'wuxing.zhixuan.fushi'
            name: '',
            //父容器
            UIContainer: '#J-balls-main-panel',
            //球dom元素选择器
            ballsDom: '.ball-number',
            //球下方频率数dom选择器
            ballAidDom: '.ball-aid-hot',
            //选球高亮class
            ballCurrentCls: 'ball-number-current',
            //玩法提示信息
            methodMassageDom: '.prompt .method-tip',
            //玩法提示信息
            methodExampleDom: '.prompt .example-tip',
            //冷热遗漏号获取地址
            hotColdUrl: '',
            //限制选求重复次数
            randomBetsNum: 500,
            //单式上传限号
            danshiLimitBall: 1000
        },
        Games = host.Games;

    var pros = {
        init: function (cfg) {
            var me = this;
            //父容器
            me.UIContainer = $(cfg.UIContainer);
            //自身容器
            me.container = $('<div></div>').appendTo(me.UIContainer);
            me.buildUI();

            me.hide();

            //初始化数据结构
            me.balls = [];
            me.rebuildData();

            //所有选球dom
            me.ballsDom = me.getBallsDom();
            //选球下方辅助数字
            me.ballsAidDom = me.getBallsAidDom();
            //当前选球是否完整
            me.isBallsComplete = false;

            //由于玩法是异步延后加载并实例化，所以与其他组件的结合不能提取到外部
            //选球数据更改后触发动作
            me.addEvent('updateData', function (e, data) {
                //更新统计
                var me = this,
                    data = me.isBallsComplete ? data : {'lotterys': [], 'original': []};
                Games.getCurrentGameStatistics().updateData(data, me.getGameMethodName());
                //更新选球界面
                me.batchSetBallDom();
            });

            //面板复位时执行批量选求状态清空
            me.addEvent('afterReset', function () {
                me.exeEvent_cancelCurrentButton();
            });
            //选球动作结束执行批量选求状态清空
            me.addEvent('afterSetBallData', function () {
                me.exeEvent_cancelCurrentButton();
            });

            me.getHotCold(me.getGameMethodName(), 'currentFre', 'lost');

        },
        //获取选球dom元素，保存结构和选球数据(me.balls)一致
        getBallsDom: function () {
            var me = this, cfg = me.defConfig, dataMode = me.balls;
            if (dataMode.length < 1) {
                return [];
            }
            return me.ballsDom || (function () {
                    var balls = me.container.find(cfg.ballsDom),
                        i = 0,
                        len = balls.length,
                        _row = [],
                        result = [],
                        cellnum = dataMode[0].length;
                    for (; i < len; i++) {
                        _row.push(balls[i]);
                        if ((i + 1) % cellnum == 0 || (i + 1) == len) {
                            result.push(_row);
                            _row = [];
                        }
                    }
                    return result;
                })();
        },
        //游戏类型切换后
        //游戏相关信息的更新方法
        updataGamesInfo: function () {
            var me = this,
                type = me.getGameMethodName(),
                currentGame = Games.getCurrentGame(),
                freCacheName = type + 'lostcurrentFre';


            //url = ctx + '/gameBet/historyballs?type=' + type + '&extent=currentFre&line=5&lenth=30';
            var url = Games.getCurrentGame().getGameConfig().getInstance().getBetAwardUrl() + '?type=' + type + '&extent=currentFre&line=5&lenth=30';

            if (!Games.cacheData['gameBonus']) {
                Games.cacheData['gameBonus'] = {};
            }
            if (!Games.cacheData['gameTips']) {
                Games.cacheData['gameTips'] = {};
            }
            if (!Games.cacheData['frequency']) {
                Games.cacheData['frequency'] = {};
            }
            if (!Games.currentIsMoreBouns['moreBouns']) {
                Games.currentIsMoreBouns['moreBouns'] = {};
            }
            //奖金组
            if (Games.cacheData['gameBonus'][url]) {
                currentGame.addDynamicBonus(type, Games.cacheData['gameBonus'][url]);
            }
            if (Games.cacheData['gameTips'][url]) {
                me.methodTip(Games.cacheData['gameTips'][url]);
            }
            //冷热号缓存
            //缓存名称必须和手动加载的一致
            if (Games.cacheData['frequency'][freCacheName]) {
                me.getHotCold(type, 'currentFre', 'lost', '当前遗漏');
            }
            //验证缓存
            //禁止异步请求数据
            // if(Games.cacheData['gameBonus'][url] && Games.cacheData['frequency'][freCacheName] && Games.cacheData['gameTips'][url]){return};
            //获取游戏相关数据
            $.ajax({
                url: url,
                dataType: 'json',
                cache: false,
                success: function (result) {
                    if (Number(result['isSuccess']) == 1) {
                        data = result['data'];

                        //游戏玩法提示
                        if (typeof data['gameTips'] != 'undefined') {
                            Games.cacheData['gameTips'][url] = data.gameTips;
                            me.methodTip(data.gameTips);
                        }
                        //冷热号  richardgong 2015-08-26
                        if (typeof data['frequency'] != 'undefined' && data['frequency'].length > 0) {
                            //if(typeof data['frequency'] != 'undefined' || data['frequency'] != []){
                            Games.cacheData['frequency'][freCacheName] = data['frequency'];
                            me.getHotCold(type, 'currentFre', 'lost');
                        }
                        //奖金组
                        if (data['gameTips'] && typeof data['gameTips']['actualBonus'] != 'undefined') {
                            Games.cacheData['gameBonus'][url] = data['gameTips']['actualBonus'];
                            currentGame.addDynamicBonus(type, data['gameTips']['actualBonus']);
                        }
                        if (typeof(result.moreBouns) != 'undefined') {
                            Games.currentIsMoreBouns['moreBouns'][type] = result.moreBouns;
                        }

                        //if (!me.isRetPoint) {
                        //me.isRetPoint = true;

                        //该用户该玩法原始奖金
                        //该玩法理论奖金
                        //用户该玩法返点

                        var actualBonus = Number(data['gameTips']['actualBonus']),
                            theoryBonus = Number(data['gameTips']['theoryBonus']),
                            retPoint = Number(data['gameTips']['retPoint']),
                            per = retPoint.toFixed(2),
                            rebateSelect = phoenix.Games.getCurrentGameStatistics().rebateSelect;

                        //返点下拉菜单
                        if (rebateSelect && retPoint > 0) {
                            var userBonus = actualBonus + theoryBonus * retPoint / 100 + '';
                            //userBonus = parseInt(userBonus) == userBonus ? parseInt(userBonus) : Math.floor(userBonus*100)/100;
                            if (userBonus.indexOf('.') > -1) {
                                userBonus = userBonus.substr(0, userBonus.indexOf('.') + 3)
                            }

                            rebateSelect.setValue(userBonus + ' - ' + '0.00%');
                            $('#J-choose-rebate').show();
                            $('#J-select-rebate').html('<option retPointValue="1"  value="' + actualBonus + '">' + actualBonus + ' - ' + per + '%</option>' + '<option  selected="selected" retPointValue="2" value="' + (actualBonus + theoryBonus * per / 100) + '">' + userBonus + ' - ' + '0.00%</option>');
                            rebateSelect.getListDom().html('<a data-value="' + actualBonus + '" href="javascript:void(0);">' + actualBonus + ' - ' + per + '%</a>' + '<a data-value="' + (actualBonus + theoryBonus * per / 100) + '" href="javascript:void(0);">' + userBonus + ' - ' + '0.00%</a>');
                        } else {
                            $('#J-select-rebate').html('<option selected="selected" retPointValue="1"  value=""></option>');
                            $('#J-choose-rebate').hide();
                            //rebateSelect.hide();
                        }
                        //}

                    } else {

                    }
                }
            })
        },
        updataGamesInfo2: function () {
            var me = this,
                type = me.getGameMethodName(),
                currentGame = Games.getCurrentGame(),
                freCacheName = type + 'lostcurrentFre';


            //url = ctx + '/gameBet/historyballs?type=' + type + '&extent=currentFre&line=5&lenth=30';
            var url = Games.getCurrentGame().getGameConfig().getInstance().getBetAwardUrl() + '?type=' + type + '&extent=currentFre&line=5&lenth=30';

            if (!Games.cacheData['gameBonus']) {
                Games.cacheData['gameBonus'] = {};
            }
            if (!Games.cacheData['gameTips']) {
                Games.cacheData['gameTips'] = {};
            }
            if (!Games.cacheData['frequency']) {
                Games.cacheData['frequency'] = {};
            }
            if (!Games.currentIsMoreBouns['moreBouns']) {
                Games.currentIsMoreBouns['moreBouns'] = {};
            }
            //奖金组
            if (Games.cacheData['gameBonus'][url]) {
                currentGame.addDynamicBonus(type, Games.cacheData['gameBonus'][url]);
            }
            if (Games.cacheData['gameTips'][url]) {
                me.methodTip(Games.cacheData['gameTips'][url]);
            }
            //冷热号缓存
            //缓存名称必须和手动加载的一致
            if (Games.cacheData['frequency'][freCacheName]) {
                me.getHotCold(type, 'currentFre', 'lost', '当前遗漏');
            }
            //验证缓存
            //禁止异步请求数据
            //if(Games.cacheData['gameBonus'][url] && Games.cacheData['frequency'][freCacheName] && Games.cacheData['gameTips'][url]){return};
            //获取游戏相关数据

            $.ajax({
                url: url,
                dataType: 'json',
                cache: false,
                success: function (result) {
                    if (Number(result['isSuccess']) == 1) {
                        data = result['data'];

                        //游戏玩法提示
                        if (typeof data['gameTips'] != 'undefined') {
                            Games.cacheData['gameTips'][url] = data.gameTips;
                            me.methodTip(data.gameTips);
                        }
                        //冷热号  richardgong 2015-08-26
                        if (typeof data['frequency'] != 'undefined' && data['frequency'].length > 0) {
                            //if(typeof data['frequency'] != 'undefined' || data['frequency'] != []){
                            Games.cacheData['frequency'][freCacheName] = data['frequency'];
                            me.getHotCold(type, 'currentFre', 'lost');
                        }
                        //奖金组
                        if (data['gameTips'] && typeof data['gameTips']['actualBonus'] != 'undefined') {
                            Games.cacheData['gameBonus'][url] = data['gameTips']['actualBonus'];
                            currentGame.addDynamicBonus(type, data['gameTips']['actualBonus']);
                        }
                        if (typeof(result.moreBouns) != 'undefined') {
                            Games.currentIsMoreBouns['moreBouns'][type] = result.moreBouns;
                        }
                    } else {

                    }
                }
            })
        },
        //获取走势图
        getChart: function (GameMethodName, callback) {
            var me = this, modeName = $.trim(GameMethodName);
            //var url = '/gameTrend/historyballs?type=' + modeName + '&extent=currentFre&line=5&lenth=30&lotteryid=99101';
            var url = Games.getCurrentGame().getGameConfig().getInstance().trendChartUrl() + modeName + '&extent=currentFre&line=5&lenth=30';
            if (typeof Games.cacheData['charts'] == 'undefined') {
                Games.cacheData['charts'] = {};
            }

            //缓存设置
            if (typeof Games.cacheData['charts'][modeName] != 'undefined') {

                if (callback) {
                    callback(Games.cacheData['charts'][modeName]);
                }
            } else {
                //获取游戏相关数据
                $.ajax({
                    url: url,
                    dataType: 'json',
                    cache: false,
                    success: function (result) {
                        if (result['isSuccess'] == 1) {
                            //添加走势图&&缓存
                            result['data']['historyBalls'];
                            Games.cacheData['charts'][modeName] = result['data']['historyBalls'];
                            if (callback) {
                                callback(Games.cacheData['charts'][modeName]);
                            }
                        } else {
                            try {
                                console.log('服务器异常,请刷新页面重试。');
                            } catch (e) {

                            }
                        }
                    }
                });
            }
            me.fireEvent('afterUpdataGamesInfo', GameMethodName);
        },
        //修改玩法提示方法
        methodTip: function (data) {
            var me = this,
                cfg = me.defConfig;
            //玩法提示
            $(cfg.methodMassageDom).html(data.tips);
            //玩法实例
            $(cfg.methodExampleDom).html(data.example);
        },
        //获取冷热号码、遗漏数据
        //@param name  当前玩法类型
        //@param len   当前选择期数
        //@param type  当前频率类型: 遗漏 冷热
        //@param content   当前频率名称 中文
        getHotCold: function (name, len, type, content) {
            var me = this,
                containerDom = me.container.find('.number-select-title'),
                containerDomcontent = me.container.find('.number-select-content');
            //缓存名称
            cacheName = name + type + len,
                //种类样式
                lostClass = '.game-frequency-lost-length',
                //期数样式
                freClass = '.game-frequency-fre-length';
            //最大遗漏渲染时强行将type转为lost,
            if (len == 'maxFre' || len == 'currentFre') {
                type = 'lost';
            }
            var url = Games.getCurrentGame().getGameConfig().getInstance().getHotColdUrl() + '?gameMode=' + name + '&extent=' + len + '&frequencyType=' + type + '&line=5&lenth=30';
            if (type == 'lost') {
                $(freClass).hide();
                $(lostClass).show();
            } else {
                $(freClass).show();
                $(lostClass).hide();
            }
            //渲染正确DOM按
            //因为初始化选号的时候需要加载冷热号所以将渲染dom放在这里
            containerDom.find('a,li').removeClass('current');
            containerDom.find('.period' + len).addClass('current');
            containerDom.find('.' + type).addClass('current');
            containerDomcontent.find('.ball-title span').html(content);
            //缓存结果
            if (!Games.cacheData['frequency']) {
                Games.cacheData['frequency'] = {};
            }
            if (Games.cacheData['frequency'][cacheName]) {
                me.reBuildHotCold(Games.cacheData['frequency'][cacheName], type);
            } else {
                $.get(url, function (r) {
                    me.reBuildHotCold(r, type);
                    Games.cacheData['frequency'][cacheName] = r;
                }, 'json');

            }
        },
        //渲染冷热号码、遗漏
        //@parme r 	  后台汇集结果
        //@parme type 当前频率类型: 遗漏 冷热
        reBuildHotCold: function (r, type) {
            var GameMethod = this,
                numSave = [], numList = [], minNums = 0, maxNums = 0,
            //冷热判断
                current;

            for (var i = 0; i < r.length; i++) {
                current = r[i];
                for (var name in current) {
                    var currentnum = current[name]['currentNum'],
                        num = current[name]['pinlv'];
                    numSave.push([num, currentnum]);
                    numList.push(num);
                    GameMethod.setBallAidData(i, currentnum, num);
                }
                minNums = Math.min.apply(Math, numList);
                maxNums = Math.max.apply(Math, numList);
                for (var j = 0; j < numSave.length; j++) {

                    if (type == 'lost') { //遗漏值最大的用不同样式区分
                        if (numSave[j][0] == maxNums) {
                            GameMethod.setBallAidData(i, numSave[j][1], numSave[j][0], 'ball-aid-hot');
                        }
                    } else { //热号和冷号用不同样式区分
                        if (numSave[j][0] == minNums) {
                            GameMethod.setBallAidData(i, numSave[j][1], numSave[j][0], 'ball-aid-cold');
                        }
                        //热
                        if (numSave[j][0] == maxNums) {
                            GameMethod.setBallAidData(i, numSave[j][1], numSave[j][0], 'ball-aid-hot');
                        }
                    }
                }
                numSave = [], numList = [];
            }
        },
        //初始化冷热/遗漏号等事件
        initHotColdEvent: function () {
            //当前期数
            var me = this,
                currentLen = 30,
            //当前号码
                currentType = 'lost',
            //种类样式
                typeClass = 'game-frequency-type',
            //种类样式
                lostClass = 'game-frequency-lost-length',
            //期数样式
                freClass = 'game-frequency-fre-length',
            //冷热号面板
                numMian = '.game-frequency-type li',
            //期数面板
                lenMain = '.game-frequency-lost-length a, .game-frequency-fre-length a';

            //冷热号切换
            me['container'].on('click', numMian, function () {
                var domHTML,
                    parent = $(this).parent(),
                    typeName = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName();

                if ($(this).hasClass('fre')) {
                    currentType = $(this).attr('data-type');
                    $('.' + freClass).show();
                    $('.' + lostClass).hide();
                    currentLen = 30;
                    domHTML = "30期";
                } else if ($(this).hasClass('lost')) {
                    currentType = $(this).attr('data-type');
                    $('.' + freClass).hide();
                    $('.' + lostClass).show();
                    currentLen = 'currentFre';
                    domHTML = "当前遗漏";
                }
                //选定冷热号
                Games.getCurrentGame().getCurrentGameMethod().getHotCold(typeName, currentLen, currentType, domHTML);
            })

            //期数选择 richardgong ------!
            me['container'].on('click', lenMain, function () {
                var parent = $(this).parent(),
                    typeName = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
                    domHTML = $(this).html(),
                    currentLen = $(this).attr('data-type');
                //选定冷热号
                Games.getCurrentGame().getCurrentGameMethod().getHotCold(typeName, currentLen, currentType, domHTML);
            }).trigger('click');

        },
        //根据下注反选球
        reSelect: function (original) {
            var me = this,
                type = me.getGameMethodName(),
                ball = original,
                i,
                len,
                j,
                len2,
                x,
                y,
                isFlag = false;

            me.reSet();

            for (i = 0, len = ball.length; i < len; i++) {
                for (j = 0, len2 = ball[i].length; j < len2; j++) {
                    x = i;
                    y = ball[i][j];
                    me.setBallData(x, y, 1);
                    isFlag = true;
                }
            }
            if (isFlag) {
                me.updateData();
            }
        },
        //生成原始选球数据(不拆分成单注)
        //返回字符串形式的原始选球数字
        //在子类中实现/覆盖
        makePostParameter: function (original) {
            var me = this,
                result = [],
                len = original.length,
                i = 0;

            for (; i < len; i++) {
                result = result.concat(original[i].join(''));
            }
            return result.join(',');
        },

        //检查数组存在某数
        arrIndexOf: function (value, arr) {
            var r = 0;
            for (var s = 0; s < arr.length; s++) {
                if (arr[s] == value) {
                    r += 1;
                }
            }
            return r || -1;
        },
        //重新构建选球数据
        //在子类中实现
        rebuildData: function () {

        },
        getBallData: function () {
            return this.balls;
        },
        //设置选球数据
        //x y value   x y 为选球数据二维数组的坐标 value 为-1 或1
        setBallData: function (x, y, value) {
            var me = this, data = me.getBallData();
            if (x >= 0 && x < data.length && y >= 0 && y < data[0].length) {
                data[x][y] = value;
            }
            me.fireEvent('afterSetBallData', x, y, value);
        },
        //设置遗漏冷热辅助
        //x y value   x y 为选球数据二维数组的坐标 value 为-1 或1
        //classname为冷热选球所需要的高亮效果
        setBallAidData: function (x, y, value, className) {
            var me = this,
                currentName = 'ball-aid',
                data = me.getBallsAidDom(),
                className = className ? currentName + ' ' + className : currentName;
            // if(x >= 0 && x < data.length && y >= 0 && y < data[0].length){
            if (x >= 0 && x < data.length && data[x] && y >= 0 && y < data[x].length && data[x][y]) {
                data[x][y].innerHTML = value;
                data[x][y].className = className;
            }
        },
        //获取冷热/遗漏号dom元素，保存结构和选球数据(me.balls)一致
        //用于显示冷热/遗漏号
        getBallsAidDom: function () {
            var me = this, cfg = me.defConfig, dataMode = me.balls;
            if (dataMode.length < 1) {
                return [];
            }
            // return me.ballsAidDom || (function(){
            // 	var balls = me.container.find(cfg.ballAidDom),
            // 		i = 0,
            // 		len = balls.length,
            // 		_row = [],
            // 		result = [],
            // 		cellnum = dataMode[0].length;
            // 	for(;i < len;i++){
            // 		_row.push(balls[i]);
            // 		if( (i+1)%cellnum == 0 || i == len -1 ){
            // 			result.push(_row);
            // 			_row = [];
            // 		}
            // 	}
            // 	return result;
            // })();
            return me.ballsAidDom || (function () {
                    var balls = me.container.find(cfg.ballAidDom),
                        k = 0,
                        len = dataMode.length,
                        result = [];
                    for (i = 0; i < len; i++) {
                        var dm = dataMode[i],
                            dml = dm.length,
                            _row = [];
                        for (j = 0; j < dml; j++) {
                            if (balls[k]) {
                                _row.push(balls[k]);
                            }
                            k++;
                        }
                        result.push(_row);
                    }
                    // console.log(dataMode, result);
                    return result;
                })();
        },
        //复位
        reSet: function (row) {
            var me = this;
            me.isBallsComplete = false;
            me.rebuildData(row);
            me.updateData();
            me.fireEvent('afterReset');
        },
        //获取该玩法的名称
        getGameMethodName: function () {
            return this.defConfig.name;
        },
        //显示该游戏玩法
        show: function () {
            var me = this;
            me.fireEvent('beforeShow');
            me.container.show();
            me.fireEvent('afterShow');
        },
        //隐藏该游戏玩法
        hide: function () {
            var me = this;
            me.fireEvent('beforeHide');
            me.container.hide();
            me.fireEvent('afterHide');
        },
        //实现事件
        exeEvent: function (param, target) {
            var me = this;
            if ($.isFunction(me['exeEvent_' + param['action']])) {
                me['exeEvent_' + param['action']].call(me, param, target);
            }
        },
        //批量选球事件
        exeEvent_batchSetBall: function (param, target) {
            var me = this,
                ballsData = me.balls,
                x = Number(param['row']),
                bound = param['bound'],
                row = ballsData[x],
                i = 0,
                len = row.length,
                makearr = [],
                start = (typeof param['start'] == 'undefined') ? 0 : Number(param['start']);
            halfLen = Math.ceil((len - start) / 2 + start),
                dom = $(target),
                i = start;

            //清空该行选球
            for (; i < len; i++) {
                me.setBallData(x, i, -1);
            }

            switch (bound) {
                case 'all':
                    for (i = start; i < len; i++) {
                        me.setBallData(x, i, 1);
                    }
                    break;
                case 'big':
                    for (i = halfLen; i < len; i++) {
                        me.setBallData(x, i, 1);
                    }
                    break;
                case 'small':
                    //11选5时(大小选球有区别)
                    var startNnum;
                    if (Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"].indexOf('115') < 0) {

                        startNnum = start;
                    } else {
                        //趣味选球小
                        if (me.defConfig['name'] == "quwei.normal.caizhongwei") {
                            startNnum = start + 2;
                        } else {
                            startNnum = start + 1;
                        }
                    }

                    for (i = startNnum; i < halfLen; i++) {
                        me.setBallData(x, i, 1);
                    }

                    break;
                case 'odd':
                    for (i = start; i < len; i++) {
                        if ((i + 1) % 2 != 1) {
                            me.setBallData(x, i, 1);
                        }
                    }
                    break;
                case 'even':
                    for (i = start; i < len; i++) {
                        if ((i + 1) % 2 == 1) {
                            me.setBallData(x, i, 1);
                        }
                    }
                    break;
                case 'none':

                    break;
                default:
                    break;
            }

            dom.addClass('current');
            me.updateData();
        },
        //取消选球状态
        exeEvent_cancelCurrentButton: function () {
            var me = this,
                container = me.container,
                control = (typeof x != 'undefined') ? container.find('.ball-control').eq(x) : container.find('.ball-control');

            control.find('a').removeClass('current');
        },
        //选球事件
        //球参数 action=ball&value=2&row=0  表示动作为'选球'，球值为2，行为第1行(万位)
        //函数名称： exeEvent_动作名称
        exeEvent_ball: function (param, target) {
            var me = this, el = $(target), currCls = me.defConfig.ballCurrentCls;
            //必要参数
            if (param['value'] != undefined && param['row'] != undefined) {
                if (el.hasClass(currCls)) {
                    //取消选择
                    me.setBallData(Number(param['row']), Number(param['value']), -1);
                } else {
                    me.fireEvent('beforeSelect');
                    //选择
                    me.setBallData(Number(param['row']), Number(param['value']), 1);
                }



            } else {
                try {
                    console.log('GameMethod.exeEvent_ball: lack param');
                } catch (ex) {
                }
            }



            me.updateData();

            //n115 set specialMultiple
            if(Games.getCurrentGame().getCurrentGameMethod().getGameMethodName() == 'quwei.normal.dingdanshuang') {
                var $el = el.parent().parent().find('.'+currCls),
                    arr = [];

                var formatParam = function (param) {
                    var arr = $.trim(param).split('&'), i = 0, len = arr.length,
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
                $.each($el,function(n,v){
                    var q = v.getAttribute('data-param'), param;
                    if (q && $.trim(q) != '') {
                        param = formatParam(q);
                    }
                    arr.push(Games.getCurrentGame().specialMultiple[param.value]);
                });
                Games.getCurrentGameStatistics().setMultipleLimit(arr.sort()[0]);
                Games.getCurrentGameStatistics().multipleDom.input.trigger('keyup');
            }
        },
        //渲染球dom元素的对应状态
        batchSetBallDom: function () {
            var me = this,
                cfg = me.defConfig,
                cls = cfg.ballCurrentCls,
                balls = me.balls,
                i = 0,
                j = 0,
                len = balls.length,
                len2 = 0,
                ballsDom = me.getBallsDom(),
                _cls = '';
            //同步选球数据和选球dom
            //...
            for (; i < len; i++) {
                len2 = balls[i].length;
                for (j = 0; j < len2; j++) {
                    if (balls[i][j] == 1) {
                        _cls = ballsDom[i][j].className;
                        _cls = (' ' + _cls + ' ').replace(' ' + cls, '');
                        _cls += ' ' + cls;
                        ballsDom[i][j].className = _cls;
                    } else {
                        _cls = ballsDom[i][j].className;
                        _cls = (' ' + _cls + ' ').replace(' ' + cls, '');
                        ballsDom[i][j].className = _cls;
                    }
                }
            }

        },
        //当选球/取消发生，更新相关数据
        updateData: function () {
            var me = this,
                lotterys = me.getLottery();

            //通知其他模块更新
            me.fireEvent('updateData', {'lotterys': lotterys, 'original': me.getOriginal()});
        },
        getOriginal: function () {
            var me = this, balls = me.getBallData(),
                len = balls.length,
                len2 = balls[0].length;
            i = 0,
                j = 0,
                row = [],
                result = [];
            for (; i < len; i++) {
                row = [];
                for (j = 0; j < len2; j++) {
                    if (balls[i][j] > 0) {
                        row.push(j);
                    }
                }
                result.push(row);
            }
            return result;
        },

        //获取总注数/获取组合结果
        //isGetNum=true 只获取数量，返回为数字
        //isGetNum=false 获取组合结果，返回结果为单注数组
        getLottery: function (isGetNum) {
            var me = this, data = me.getBallData(),
                i = 0, len = data.length, row, isEmptySelect = true,
                _tempRow = [],
                j = 0, len2 = 0,
                result = [],
            //总注数
                total = 1,
                rowNum = 0;

            //检测球是否完整
            for (; i < len; i++) {
                result[i] = [];
                row = data[i];
                len2 = row.length;
                isEmptySelect = true;
                rowNum = 0;
                for (j = 0; j < len2; j++) {
                    if (row[j] > 0) {
                        isEmptySelect = false;
                        //需要计算组合则推入结果
                        if (!isGetNum) {
                            result[i].push(j);
                        }
                        rowNum++;
                    }
                }
                if (isEmptySelect) {
                    //alert('第' + i + '行选球不完整');
                    me.isBallsComplete = false;
                    return [];
                }
                //计算注数
                total *= rowNum;
            }
            me.isBallsComplete = true;
            //返回注数
            if (isGetNum) {
                return total;
            }

            if (me.isBallsComplete) {
                //组合结果
                return me.combination(result);
            } else {
                return [];
            }
        },
        //单组去重处理
        removeSame: function (data) {
            var i = 0, result, me = this,
                numLen = this.getBallData()[0].length,
                len = data.length;
            result = Math.floor(Math.random() * numLen);
            for (; i < data.length; i++) {
                if (result == data[i]) {
                    return arguments.callee.call(me, data);
                }
            }
            return result;
        },
        //移除一维数组的重复项
        removeArraySame: function (arr) {
            var me = this,
                i = 0,
                result,
                numLen = me.getBallData()[0].length,
                len = data.length;

            result = Math.floor(Math.random() * numLen);
            for (; i < arr.length; i++) {
                if (result == arr[i]) {
                    return arguments.callee.call(me, arr);
                }
            }
            return result;
        },
        getRandomBetsNum: function () {
            return this.defConfig.randomBetsNum;
        },
        //生成单注随机数
        createRandomNum: function () {
            var me = this,
                current = [],
                len = me.getBallData().length,
                rowLen = me.getBallData()[0].length;
            //随机数
            for (var k = 0; k < len; k++) {
                current[k] = [Math.floor(Math.random() * rowLen)];
                current[k].sort(function (a, b) {
                    return a > b ? 1 : -1;
                });
            }
            ;
            return current;
        },
        //限制随机投注重复
        checkRandomBets: function (hash, times) {
            var me = this,
                allowTag = typeof hash == 'undefined' ? true : false,
                hash = hash || {},
                current = [],
                times = times || 0,
                len = me.getBallData().length,
                rowLen = me.getBallData()[0].length,
                order = Games.getCurrentGameOrder().getTotal()['orders'];

            //生成单数随机数
            current = me.createRandomNum();
            //如果大于限制数量
            //则直接输出
            if (Number(times) > Number(me.getRandomBetsNum())) {
                return current;
            }
            //建立索引
            if (allowTag) {
                for (var i = 0; i < order.length; i++) {
                    if (order[i]['type'] == me.defConfig.name) {
                        var name = order[i]['original'].join('');
                        hash[name] = name;
                    }
                }
                ;
            }
            //对比结果
            if (hash[current.join('')]) {
                times++;
                return arguments.callee.call(me, hash, times);
            }
            return current;
        },
        //生成一个当前玩法的随机投注号码
        //该处实现复式，子类中实现其他个性化玩法
        //返回值： 按照当前玩法生成一注标准的随机投注单(order)
        randomNum: function () {
            var me = this,
                i = 0,
                current = [],
                currentNum,
                ranNum,
                order = null,
                dataNum = me.getBallData(),
                name = me.defConfig.name,
                lotterys = [],
                original = [];

            current = me.checkRandomBets();
            original = current;
            lotterys = me.combination(original);

            order = {
                'type': Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
                'original': original,
                'lotterys': lotterys,
                'moneyUnit': Games.getCurrentGameStatistics().getMoneyUnit(),
                'multiple': Games.getCurrentGameStatistics().getMultip(),
                'onePrice': Games.getCurrentGameStatistics().getOnePrice(),
                'num': lotterys.length
            };
            order['amountText'] = Games.getCurrentGameStatistics().formatMoney(order['num'] * order['moneyUnit'] * order['multiple'] * order['onePrice']);
            return order;
        },
        //生成指定数目的随机投注号码，并添加进号码篮
        randomLotterys: function (num) {
            var me = this, i = 0;
            Games.getCurrentGameOrder().cancelSelectOrder();
            for (; i < num; i++) {
                Games.getCurrentGameOrder().add(me.randomNum());
            }
        },
        //游戏错误提示
        //主要用于进行单式投注错误提示
        //具体实现在子类中的单式投注玩法
        ballsErrorTip: function () {

        },
        //计算当前选中的球数量
        countBallsNum: function () {
            var me = this,
                num = 0,
                ball = me.getBallData();

            for (var i = ball.length - 1; i >= 0; i--) {
                if (Object.prototype.toString.call(ball[i]) == '[object Array]' && ball[i].length > 0) {
                    for (var j = ball[i].length - 1; j >= 0; j--) {
                        if (ball[i][j] == 1) {
                            num++;
                        }
                        ;
                    }
                    ;
                } else {
                    if (ball[i] == 1) {
                        num++;
                    }
                }
            }
            ;

            return num;
        },
        //计算当前选中的球数量
        //限制计算某一单行内球数量
        countBallsNumInLine: function (lineNum) {
            var me = this,
                num = 0,
                ball = me.getBallData();


            if (Object.prototype.toString.call(ball[lineNum]) == '[object Array]' && ball[lineNum].length > 0) {
                for (var j = ball[lineNum].length - 1; j >= 0; j--) {
                    if (ball[lineNum][j] == 1) {
                        num++;
                    }
                    ;
                }
                ;
            } else {
                if (ball[lineNum] == 1) {
                    num++;
                }
            }

            return num || -1;
        },
        //是否超出限制选球数量
        LimitMaxBalls: function (limitNum) {
            var me = this,
                num = 0,
                ball = me.getBallData(),
                ballCount = Number(num);

            //当前选中的球数量
            num = me.countBallsNum();

            if (num > limitNum) {
                return true;
            } else {
                return false;
            }
        },
        //检测选球是否完整，是否能形成有效的投注
        //并设置 isBallsComplete
        checkBallIsComplete: function () {
            var me = this, data = me.getBallData(),
                i = 0, len = data.length, row, isEmptySelect = true,
                j = 0, len2 = 0;

            //检测球是否完整
            for (; i < len; i++) {
                row = data[i];
                len2 = row.length;
                isEmptySelect = true;
                for (j = 0; j < len2; j++) {
                    if (row[j] > 0) {
                        isEmptySelect = false;
                    }
                }
                if (isEmptySelect) {
                    //alert('第' + i + '行选球不完整');
                    me.isBallsComplete = false;
                    return false;
                }
            }
            return me.isBallsComplete = true;
        },

        //单行数组的排列组合
        //list 参与排列的数组
        //num 每组提取数量
        //last 递归中间变量
        combine: function (list, num, last) {
            var result = [], i = 0;
            last = last || [];
            if (num == 0) {
                return [last];
            }
            for (; i <= list.length - num; i++) {
                result = result.concat(arguments.callee(list.slice(i + 1), num - 1, last.slice(0).concat(list[i])));
            }
            return result;
        },
        //二维数组的排列组合
        //arr2 二维数组
        combination: function (arr2) {
            if (arr2.length < 1) {
                return [];
            }
            var w = arr2[0].length,
                h = arr2.length,
                i, j,
                m = [],
                n,
                result = [],
                _row = [];

            m[i = h] = 1;

            while (i--) {
                m[i] = m[i + 1] * arr2[i].length;
            }
            n = m[0];
            for (i = 0; i < n; i++) {
                _row = [];
                for (j = 0; j < h; j++) {
                    _row[j] = arr2[j][~~(i % m[j] / m[j + 1])];
                }
                result[i] = _row;
            }
            return result;
        },

        //检查单式上传注数是否超限
        checkLimitBall: function (orderData) {
            var me = this,
                limitNum = me['defConfig']['danshiLimitBall'],
                msg = Games.getCurrentGameMessage(),
                orderLength = Number(orderData['lotterys'].length) || 0;

            //单式限注
            if (orderLength > limitNum) {

                orderData['lotterys'] = [];
                msg.show({
                    mask: true,
                    content: ['<div class="bd text-center">',
                        '<div class="pop-waring">',
                        '<i class="ico-waring"></i>',
                        '<div style="display:inline-block;*zoom:1;*display:inline;vertical-align:middle">最多支持' + limitNum + '注单式内容，请调整！</div>',
                        '</div>',
                        '</div>'].join(''),
                    closeIsShow: true,
                    closeFun: function () {
                        this.hide();
                    }
                });
            }
        }


    };

    var Main = host.Class(pros, Event);
    Main.defConfig = defConfig;
    host[name] = Main;

})(phoenix, "GameMethod", phoenix.Event);


//追号区域
;(function (host, name, Event, undefined) {
    var defConfig = {
            //彩种休市提示
            /*lotteryClose : ['<div class="bd text-center">',
             '<p class="text-title text-left">非常抱歉，本彩种已休市。<br />请与<#=orderDate#>后再购买</p>',
             '<div class="lottery-numbers text-left">',
             '<div class="tltle"><#=lotteryName#> 第<strong class="color-green"><#=lotteryPeriods#></strong>期开奖号码：</div>',
             '<div class="content">',
             '<#=lotterys#>',
             '<a href="#">查看更多&raquo;</a>',
             '</div>',
             '</div>',
             '<dl class="lottery-list">',
             '<dt>您可以购买以下彩种</dt>',
             '<#=lotteryType#>',
             '</dl>',
             '</div>'].join(''),*/
            //整彩种停售
            lotteryClose: ['<div class="bd text-center">',
                '<p class="text-title text-left"><#=msg#><br /></p>',
                '<dl class="lottery-list">',
                '<dt>您可以购买以下彩种</dt>',
                '<#=lotteryType#>',
                '</dl>',
                '</div>'].join(''),
            //投注信息核对
            checkLotters: ['<div class="bd">',
                '<ul class="ui-form">',
                '<li>',
                '<label for="question1" class="ui-label">彩种：</label>',
                '<span class="ui-text-info"><#=lotteryName#></span>',
                '</li>',
                '<li>',
                '<label for="question1" class="ui-label" >期号：</label>',
                '<span class="ui-text-info"><#=lotteryDate#></span>',
                '</li>',
                '<li>',
                '<label for="answer1" class="ui-label">详情：</label>',
                '<div class="textarea" style="font-size:12px;" >',
                '<#=lotteryInfo#>',
                '</div>',
                '</li>',
                '<li>',
                '<label for="question2" class="ui-label">付款总金额：</label>',
                '<span class="ui-text-info"><span class="color-red"><#=lotteryamount#></span>元</span>',
                '</li>',
                '<li>',
                '<label for="question2" class="ui-label">付款帐号：</label>',
                '<span class="ui-text-info"><span class="color-red"><#=lotteryAcc#></span></span>',
                '</li>',
                '</ul>',
                '<p class="text-note"><#=lotteryChareg#></p>',
                '<p class="text-note"><#=lotteryCharegPlurality#></p>',
                '</div>'].join(''),

            //未到销售时间
            nonSaleTime: ['<div class="bd text-center">',
                '<p class="text-title text-left">非常抱歉，本彩种未到销售时间。<br />请与<#=orderDate#>后再购买</p>',
                '<dl class="lottery-list">',
                '<dt>您可以购买以下彩种</dt>',
                '<#=lotteryType#>',
                '</dl>',
                '</div>'].join(''),

            //正常提示
            normal: ['<div class="bd text-center">',
                '<div class="pop-waring">',
                '<i class="ico-waring <#=icon-class#>"></i>',
                '<h4 class="pop-text"><#=msg#><br /></h4>',
                '</div>',
                '</div>'].join(''),

            //无效字符提示
            invalidtext: ['<div class="bd text-center">',
                '<div class="pop-waring">',
                '<i class="ico-waring <#=icon-class#>"></i>',
                '<h4 class="pop-text"><#=msg#><br /></h4>',
                '</div>',
                '</div>'].join(''),

            //投注过期提示
            betExpired: ['<div class="bd text-center">',
                '<div class="pop-waring">',
                '<i class="ico-waring <#=icon-class#>"></i>',
                '<h4 class="pop-text"><#=msg#><br /></h4>',
                '</div>',
                '</div>'].join(''),

            //倍数超限
            multipleOver: ['<div class="bd text-center">',
                '<div class="pop-waring">',
                '<i class="ico-waring <#=icon-class#>"></i>',
                '<h4 class="pop-text"><#=msg#><br /></h4>',
                '</div>',
                '</div>'].join(''),
            //暂停销售
            pauseBet: ['<div class="bd text-center">',
                '<div class="pop-waring">',
                '<i class="ico-waring <#=icon-class#>"></i>',
                '<h4 class="pop-text"><#=msg#><br /></h4>',
                '</div>',

                '</div>'].join(''),

            //成功提示
            successTip: ['<div class="bd text-center">',
                '<div class="pop-title">',
                '<i class="ico-success"></i>',
                '<h4 class="pop-text"><#=msg#><br /></h4>',
                '</div>',
                '<p class="text-note">您可以通过”<a href="' + baseUrl + '<#=url#>" target="_black"><#=gameType#></a>“查询您的投注记录！</p>',
                '</div>'].join(''),
            //提醒选求提示
            checkBalls: ['<div class="bd text-center">',
                '<div class="pop-title">',
                '<i class="ico-waring <#=iconClass#>"></i>',
                '<h4 class="pop-text">请至少选择一注投注号码！</h4>',
                '</div>',
                '<div class="pop-btn ">',
                '<a href="javascript:void(0);" class="btn closeBtn">关 闭<b class="btn-inner"></b></a>',
                '</div>',
                '</div>'].join(''),
            //错误提示
            subFaild: ['<div class="bd text-center">',
                '<div class="pop-title">',
                '<i class="ico-error"></i>',
                '<h4 class="pop-text"><#=msg#><br /></h4>',
                '</div>',
                '<div class="pop-btn ">',
                '<a href="javascript:void(0);" class="btn closeBtn">关 闭<b class="btn-inner"></b></a>',
                '</div>',
                '</div>'].join(''),
            //余额不足
            Insufficientbalance: ['<div class="bd text-center">',
                '<div class="pop-title">',
                '<i class="ico-error"></i>',
                '<h4 class="pop-text">您的余额不足，请充值！</h4>',
                '</div>',
                '</div>'].join(''),
            //封锁变价
            blockade: ['<div class="bd panel-game-msg-blockade" id="J-blockade-panel-main">',
                '<form id="J-form-blockade-detail" action="" target="_blank" method="post"></form>',
                '<div class="game-msg-blockade-text">存在<#=blockadeType#>内容，系统已为您做出 <a href="#" data-action="blockade-detail">最佳处理</a> ，点击<span class="color-red">“确认”</span>完成投注</div>',
                '<div>',
                '<div class="game-msg-blockade-line-title">彩种：<#=gameTypeTitle#></div>',
                '<div class="game-msg-blockade-line-title">期号：<#=currentGameNumber#></div>',
                '</div>',
                '<div id="J-game-panel-msg-blockade-0">',
                '<div class="game-msg-blockade-cont" id="J-msg-panel-submit-blockade-error0"><#=blockadeData0#></div>',
                '</div>',
                '<div class="game-msg-blockade-panel-money">',
                '<div><b>付款总金额：</b><span class="color-red"><b id="J-money-blockade-adjust"><#=amountAdjust#></b></span> 元&nbsp;&nbsp;&nbsp;&nbsp;<span style="display:<#=display#>"><b>减少投入：</b><span class="color-red"><b id="J-money-blockade-change"><#=amountChange#></b></span> 元</span></div>',
                '<div><b>付款账号：</b><#=username#></div>',
                '</div>',
                '<div>',
                //'<p class="text-note">购买后请您尽量避免撤单，如撤单将收取手续费：￥<span class="handlingCharge">0.00</span>元</p>',
                //'<p class="text-note">本次投注，若未涉及到付款金额变化，将不再提示</p>',
                '<p class="text-note"><#=lotteryChareg#></p>',
                '<p class="text-note"><#=lotteryCharegPlurality#></p>',
                '</div>',
                //'<div class="textarea" style="font-size:12px;margin-top:10px;"></div>',
                '</div>'].join(''),
            //高频封锁
            overLimit: ['<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text"><#=msg#><br /></h4></div>',
                '<div class="content" style="max-height:150px;overflow:auto;">',
                //'<#=webIssueCode#>期&nbsp;<#=betMethod#><#=betDetail#> 本期最多投<#=multiple#>倍',
                '<#=contentinfo#>',
                '</div>',
                '</div>'].join('')

        },
        instance,
        closeTime = null,
        Games = host.Games;

    var pros = {
        //初始化
        init: function (cfg) {
            var me = this;
            me.win = new host.MiniWindow({
                //实例化时追加的最外层样式名
                cls: 'pop w-9'
            })
            me.mask = host.Mask.getInstance();
            //绑定隐藏完成事件
            me.reSet();
            me.win.addEvent('afterHide', function () {
                me.reSet()
            })
        },
        //彩种提示类型
        doAction: function (data) {
            var me = this,
                funName = 'rebulid' + data['type'],
                getHtml = 'getHtml' + data['type'],
                fn = function () {
                };


            if (me[funName] && $.isFunction(me[funName])) {
                fn = me[funName];
            }
            data['tpl'] = typeof data['tpl'] == 'undefined' ? me[getHtml]() : '' + data['tpl'];
            //删除type数据
            //防止在渲染的时候进行递归调用
            delete data['type'];
            //调用子类方法
            fn.call(me, data);
        },
        formatHtml: function (tpl, order) {
            var me = this, o = order, p, reg;
            for (p in o) {
                if (o.hasOwnProperty(p)) {
                    reg = RegExp('<#=' + p + '#>', 'g');
                    tpl = tpl.replace(reg, o[p]);
                }
            }
            return tpl;
        },
        //检查数组存在某数
        arrIndexOf: function (value, arr) {
            var r = 0;
            for (var s = 0; s < arr.length; s++) {
                if (arr[s] == value) {
                    r += 1;
                }
            }
            return r || -1;
        },
        //通用
        getHtmlWaring: function () {
            var cfg = this.defConfig;
            return cfg.normal;
        },
        //默认弹窗
        rebulidnormal: function (parameter) {
            var me = this, result = {};
            result['mask'] = true;
            result['closeText'] = '关 闭';
            result['closeIsShow'] = true;
            result['closeFun'] = function () {
                me.hide()
            };
            result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        //获取默认提示弹窗
        getHtmlnormal: function () {
            return this.getHtmlWaring();
        },
        /*
         //彩种核对
         phoenix.Games.getCurrentGameMessage().show({
         type : 'checkLotters',
         data : {
         tplData : {
         //当期彩票详情
         lotteryDate : '20121128-023',
         //彩种名称
         lotteryName : 'shishicai',
         //投注详情
         lotteryInfo : ,
         //彩种金额
         lotteryamount : {'year':'2013','month':'5','day':'3','hour':'1','min':'30'},
         //付款帐号
         lotteryAcc :，
         //手续费
         lotteryCharge
         }
         }
         })
         */
        rebulidcheckLotters: function (parameter) {
            var me = this,
                order = Games.getCurrentGameOrder().getTotal()['orders'],
                isFollowNumber = parameter['data']['tplData'].lotteryTraceInfo(),
                lotteryOnlyAmount = phoenix.util.ToForamtmoney(parameter['data']['tplData'].lotteryamount),
                backOutStartFee = Number(Games.getCurrentGame().getGameConfig().getInstance().defConfig["backOutStartFee"]) / 10000,//返手续费起始金额
                backOutRadio = Number(Games.getCurrentGame().getGameConfig().getInstance().defConfig["backOutRadio"]) / 10000;//返金额率

            result = {};
            result['mask'] = true;
            result['iconClass'] = '';

            //无追号
            if (isFollowNumber.length < 1) {
                if (lotteryOnlyAmount > backOutStartFee && Number(backOutStartFee) != 0 && Number(backOutRadio) != 0) {
                    parameter['data']['tplData'].lotteryChareg = '<div id="showLotteryChareg" style="display: none;">购买后请您尽量避免撤单，如撤单将收取手续费：<span class="price"><dfn>&yen;</dfn><span class="handlingCharge">' + Games.getCurrentGameStatistics().formatMoney(backOutRadio * lotteryOnlyAmount, 3) + '</span>元</span></div>';
                    parameter['data']['tplData']['lotteryCharegPlurality'] = '';
                } else {
                    parameter['data']['tplData'].lotteryChareg = '';
                    parameter['data']['tplData']['lotteryCharegPlurality'] = '';
                }
            } else {
                parameter['data']['tplData'].lotteryChareg = '';
                var html = '',
                    lotteryAmount = 0, //每一期投注金额
                    totalAmount = 0,
                    lotteryOnlyAmounts = parameter['data']['tplData'].lotteryOnlyAmount; //追号单笔金额

                html = '<div style="margin-top:-12px;" id="showLotteryChareg">购买后请您尽量避免撤单，如撤单将收取手续费：</div><ul class="textarea cancel-bets clearfix" style="font-size:12px;margin-top:10px;"> <li><span style="font-size:13px;font-weight:500">奖期</span><span style="font-size:13px;font-weight:500">撤单手续费</span></li>';
                for (var i = 0; i < isFollowNumber.length; i++) {
                    lotteryAmount = Number(isFollowNumber[i]['multiple'] * lotteryOnlyAmounts);
                    if (lotteryAmount > backOutStartFee) {
                        totalAmount += ( lotteryAmount * backOutRadio );
                        html += '<li><span>第' + isFollowNumber[i]['number'] + '期</span><span class="price"><dfn>&yen;</dfn>' + Games.getCurrentGameStatistics().formatMoney(lotteryAmount * backOutRadio, 3) + '元</span></li>';
                    }
                }
                html += '</ul>';

                if (totalAmount > 0 && Number(backOutStartFee) != 0 && Number(backOutRadio) != 0) {
                    parameter['data']['tplData']['lotteryCharegPlurality'] = html;
                } else {
                    parameter['data']['tplData']['lotteryCharegPlurality'] = '';
                }
            }

            result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        getHtmlcheckLotters: function () {
            var cfg = this.defConfig;
            return cfg.checkLotters;
        },
        /*
         //彩种关闭调用实例
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
         })

         //彩种关闭
         rebulidlotteryClose : function(parameter){
         var me = this,
         result = {};
         lotteryName = parameter['data']['tplData']['lotteryName'];
         lotteryPeriods = parameter['data']['tplData']['lotteryPeriods'];
         time = parameter['data']['tplData']['orderDate'];
         lotterys = parameter['data']['tplData']['lotterys'];
         typeArray = parameter['data']['tplData']['lotteryType'];
         result['mask'] = true;
         result['iconClass'] = '';
         result['closeIsShow'] = true;
         result['closeFun'] = function(){
         me.hide();
         };
         //彩种名称
         parameter['data']['tplData']['lotteryName'] = function(){
         return lotteryName || '';
         };
         //本次开奖期数
         parameter['data']['tplData']['lotteryPeriods'] = function(){
         return lotteryPeriods || '';
         };
         //购买日期
         parameter['data']['tplData']['orderDate'] = function(){
         return time['year'] + '年' + time['month'] + '月' + time['day'] + '日 ' + time['hour'] + ':' + time['min'];
         };
         //彩票详情
         parameter['data']['tplData']['lotterys'] = function(){
         var html  = '';
         if($.isArray(lotterys)){
         for (var i = 0; i < lotterys.length; i++) {
         html += '<em>' + lotterys[i] + '</em>';
         };
         }
         return html;
         };
         //彩票种类
         parameter['data']['tplData']['lotteryType'] = function(){
         var html  = '';
         if($.isArray(typeArray)){
         for (var i = 0; i < typeArray.length; i++) {
         html += '<dd><span style="background:url(' + typeArray[i]['pic'] +')" class="pic" title="' + typeArray[i]['name'] + '" alt="' + typeArray[i]['name'] + '"></span><a href="' + typeArray[i]['url'] + '" class="btn">去投注<b class="btn-inner"></b></a></dd>';
         };
         }
         return html;
         };
         result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
         me.show($.extend(result, parameter));
         }, */
        //彩种停售
        rebulidlotteryClose: function (parameter) {
            var me = this,
                order = Games.getCurrentGameOrder().getTotal()['orders'],
                result = {};
            result['mask'] = true;
            result['iconClass'] = '';

            result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
            me.show($.extend(result, parameter));


        },
        getHtmllotteryClose: function () {
            var cfg = this.defConfig;
            return cfg.lotteryClose;
        },
        /*
         //调用实例
         phoenix.Games.getCurrentGameMessage().show({
         type : 'nonSaleTime',
         data : {
         tplData:{
         //开始购买时间
         orderDate : {'year':'2013','month':'5','day':'3','hour':'1','min':'30'},
         //提示彩票种类
         lotteryType : [{'name':'leli','pic':'#','url':'http://163.com'},{'name':'kuaile8','pic':'#','url':'http://pp158.com'}]
         }
         }
         })
         */
        //未到销售时间
        rebulidnonSaleTime: function (parameter) {
            var me = this,
                result = {};
            time = parameter['data']['tplData']['orderDate'];
            typeArray = parameter['data']['tplData']['lotteryType'];
            result['mask'] = true;
            result['iconClass'] = '';
            result['closeIsShow'] = true;
            result['closeFun'] = function () {
                me.hide();
            };
            //购买日期
            parameter['data']['tplData']['orderDate'] = function () {
                return time['year'] + '年' + time['month'] + '月' + time['day'] + '日 ' + time['hour'] + ':' + time['min'];
            };
            //彩票种类
            parameter['data']['tplData']['lotteryType'] = function () {
                var html = '';

                if ($.isArray(typeArray)) {
                    for (var i = 0; i < typeArray.length; i++) {
                        html += '<dd><span style="background:url(' + typeArray[i]['pic'] + ')" class="pic" title="' + typeArray[i]['name'] + '" alt="' + typeArray[i]['name'] + '"></span><a href="' + typeArray[i]['url'] + '" class="btn">去投注<b class="btn-inner"></b></a></dd>';
                    }
                    ;
                }
                return html;
            };
            result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        getHtmlnonSaleTime: function () {
            var cfg = this.defConfig;
            return cfg.nonSaleTime;
        },
        //至少选择一注
        rebulidmustChoose: function (parameter) {
            var me = this, result = {};
            result['mask'] = true;
            result['iconClass'] = '';
            result['closeIsShow'] = true;
            result['closeFun'] = function () {
                me.hide();
            };
            result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        getHtmlmustChoose: function () {
            return this.getHtmlWaring();
        },
        //网络连接异常
        rebulidnetAbnormal: function (parameter) {
            var me = this, result = {};
            result['mask'] = true;
            result['iconClass'] = '';
            result['closeIsShow'] = true;
            result['closeFun'] = function () {
                me.hide();
            };
            result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        getHtmlnetAbnormal: function () {
            return this.getHtmlWaring();
        },
        //提交成功
        rebulidsuccess: function (parameter) {
            var me = this, result = {};
            result['mask'] = true;
            result['iconClass'] = '';
            result['closeIsShow'] = true;
            result['closeFun'] = function () {
                me.hide();
            };
            result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        getHtmlsuccess: function () {
            var cfg = this.defConfig;
            return cfg.successTip;
        },
        //登陆超时loginTimeout
        rebulidloginTimeout: function (parameter) {
            var me = this, result = {};
            result['mask'] = true;
            result['closeIsShow'] = true;
            result['closeFun'] = function () {
                me.hide();
            };
            result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        getHtmlloginTimeout: function () {
            return this.getHtmlWaring();
        },
        //服务器错误
        rebulidserverError: function (parameter) {
            var me = this, result = {};
            result['mask'] = true;
            result['iconClass'] = '';
            result['closeIsShow'] = true;
            result['closeFun'] = function () {
                me.hide();
            };
            result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        getHtmlserverError: function () {
            return this.getHtmlWaring();
        },
        //失败状态
        rebulidsubFaild: function (parameter) {
            var me = this, result = {};
            result['mask'] = true;
            result['iconClass'] = '';
            result['closeIsShow'] = true;
            result['closeFun'] = function () {
                me.hide();
            };
            result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        getHtmlsubFaild: function () {
            return this.getHtmlWaring();
        },
        //余额不足
        rebulidInsufficientbalance: function (parameter) {
            var me = this, result = {};
            result['mask'] = true;
            result['closeIsShow'] = true;

            result['confirmIsShow'] = true;
            result['confirmText'] = '去充值';
            result['confirmFun'] = function () {
                window.open(_logOut + '/fund');
            };
            result['closeFun'] = function () {
                me.hide();
            };
            result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        getHtmlInsufficientbalance: function () {
            return this.getHtmlWaring();
        },
        //暂停销售
        rebulidpauseBet: function (parameter) {
            var me = this, result = {};
            result['mask'] = true;
            result['confirmText'] = '投 注';
            result['confirmIsShow'] = true;
            result['confirmFun'] = function () {
                var order = Games.getCurrentGameOrder(),
                    i = 0;

                //删除指定类别的投注
                for (; i < parameter['data']['tplData']['balls'].length; i++) {
                    //indexOf
                    //order.removeData(parameter['data']['tplData']['balls'][i]['id']);
                    //当前注单去匹配
                    for (j = 0; j < order['orderData'].length; j++) {
                        if (order['orderData'][j]['id'] == parameter['data']['tplData']['balls'][i]['id']) {
                            order['orderData'].splice(j, 1)
                        }
                    }
                }
                ;
                //提交投注
                Games.getCurrentGameSubmit().submitData();
            };
            result['closeText'] = '关 闭';
            result['closeIsShow'] = true;
            result['closeFun'] = function () {
                me.hide();
            };
            //生成消息
            parameter['data']['tplData']['msg'] = function () {
                var numText = [],
                    gameConfig = Games.getCurrentGame().getGameConfig().getInstance(),
                    k = 0;
                //输出暂停销售名称集合
                for (; k < parameter['data']['tplData']['balls'].length; k++) {
                    var current = parameter['data']['tplData']['balls'][k]['type'],
                        typeText = gameConfig.getTitleByName(current);
                    if (me.arrIndexOf(typeText.join(''), numText) == -1) {
                        numText.push(typeText.join(''));
                    }
                }
                ;
                return '您的投注内容中“' + numText.join('') + '”已暂停销售，是否完成剩余内容投注？';
            };
            result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        getHtmlpauseBet: function () {
            var cfg = this.defConfig;
            return cfg.pauseBet;
        },
        //倍数超限
        rebulidmultipleOver: function (parameter) {
            var me = this, result = {};
            result['mask'] = true;
            result['iconClass'] = '';
            result['closeText'] = '关 闭';
            result['closeIsShow'] = true;
            result['closeFun'] = function () {
                me.hide();
            };
            //生成消息
            parameter['data']['tplData']['msg'] = function () {
                var numText = [],
                    gameConfig = Games.getCurrentGame().getGameConfig().getInstance(),
                    k = 0;
                //输出暂停销售名称集合
                for (; k < parameter['data']['tplData']['balls'].length; k++) {
                    var current = parameter['data']['tplData']['balls'][k]['type'],
                        typeText = gameConfig.getTitleByName(current);
                    if (me.arrIndexOf(typeText.join(''), numText) == -1) {
                        numText.push(typeText.join(''));
                    }
                }
                ;
                return '您的投注内容中“' + numText.join('') + '”超出倍数限制，请调整！';
            };
            result['content'] = me.formatHtml(parameter['tpl'], parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        getHtmlmultipleOver: function () {
            var cfg = this.defConfig;
            return cfg.multipleOver;
        },
        //无效字符
        rebulidinvalidtext: function (parameter) {
            var me = this, result = {};
            result['mask'] = true;
            result['confirmText'] = '刷新页面';
            result['confirmIsShow'] = true;
            result['confirmFun'] = function () {
                window.location.reload();
            };
            result['content'] = me.formatHtml(me.getHtmlinvalidtext(), parameter);
            me.show($.extend(result, parameter));
        },
        getHtmlinvalidtext: function () {
            var cfg = this.defConfig;
            return cfg.invalidtext;
        },
        //投注过期
        rebulidbetExpired: function (parameter) {
            var me = this, result = {};
            result['mask'] = true;
            result['closeText'] = '关 闭';
            result['closeIsShow'] = true;
            result['closeFun'] = function () {
                me.hide();
            };
            parameter['data']['tplData']['msg'] = function () {
                return '您好，' + parameter['data']['tplData']['bitDate']['expiredDate'] + '期 已截止销售，当前期为' + parameter['data']['tplData']['bitDate']['current'] + '期 ，请留意！';
            };
            result['content'] = me.formatHtml(me.getHtmlbetExpired(), parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        getHtmlbetExpired: function () {
            var cfg = this.defConfig;
            return cfg.betExpired;
        },
        //非法投注工具
        rebulidillegalTools: function (parameter) {
            var me = this, result = {};
            result['mask'] = true;
            result['confirmText'] = '刷新页面';
            result['confirmIsShow'] = true;
            result['confirmFun'] = function () {
                window.location.reload();
            };
            result['content'] = me.formatHtml(me.getHtmlbetExpired(), parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        //高频封锁
        getHtmloverLimit: function () {
            return this.defConfig.overLimit;
        },
        rebulidoverLimit: function (parameter) {
            var me = this, result = {}, tplData = parameter['data']['tplData'], overMutipleData = parameter['data']['overMutipleData'],
                blockadeData = [],
                multiple = 0,
                moneyType,
                betDetail;

            result['mask'] = true;
            result['closeText'] = '确 定';
            result['closeIsShow'] = true;
            result['closeFun'] = function () {
                me.hide();
            };
            $.each(overMutipleData, function (i) {
                betDetail = overMutipleData[i].betDetail.length > 3 ? overMutipleData[i].betDetail.substr(0, 3) + '.. <a title=' + overMutipleData[i].betDetail + '>详情</a>' : overMutipleData[i].betDetail;

                blockadeData.push(overMutipleData[i].webIssueCode + '期&nbsp;&nbsp;' + overMutipleData[i].betMethod + '&nbsp;&nbsp;');
               //模式转换
				if($.trim(overMutipleData[i].moneyunit) == '1'){
					multiple = parseFloat(overMutipleData[i].multiple) / 10;
					moneyType = '元';
				}else if($.trim(overMutipleData[i].moneyunit) == '3'){
					multiple = parseFloat(overMutipleData[i].multiple) * 10;
					moneyType = '分';
				}else{
					multiple = parseFloat(overMutipleData[i].multiple);
					moneyType = '角';
				}
                blockadeData.push(betDetail + '&nbsp;&nbsp;(' + moneyType + ')本期最多投');
                blockadeData.push('&nbsp;&nbsp;' + multiple + '倍</br>');

            });

            tplData['contentinfo'] = blockadeData.join('');

            result['content'] = me.formatHtml(me.getHtmlbetExpired(), parameter['data']['tplData']);
            me.show($.extend(result, parameter));

        },


        //低频封锁变价模板
        getHtmlblockade: function () {
            return this.defConfig.blockade;
        },
        //低频封锁变价
        rebulidblockade: function (parameter) {
            var me = this, result = {}, tplData = parameter['data']['tplData'], orderData = parameter['data']['orderData'], blockadeInfo = parameter['data']['blockadeInfo'],
                balls = orderData['balls'],
                dataHas = {},
                ballStr = '',
                typeName = '',
                arrGameName = [],
                typeNamgeBall = '',
                formatMoney = Games.getCurrentGameOrder().formatMoney,
                maxLen = 28,
            //是否在提交中
                isSubmitLoading = false,
                blockadeData0 = ['<ul class="game-msg-blockade-balls">'],
                arryBlockadeList = 0,
                coutRealBeishu = 0,
                isFollowNumber = orderData.isTrace,
                backOutStartFee = Number(Games.getCurrentGame().getGameConfig().getInstance().defConfig["backOutStartFee"]) / 10000,//返手续费起始金额
                backOutRadio = Number(Games.getCurrentGame().getGameConfig().getInstance().defConfig["backOutRadio"]) / 10000;//返金额率


            var amountAdjust = Number(orderData.amount);
            //可投为0，金额为0时
            if (amountAdjust == 0) {
                var message = Games.getCurrentGameMessage(),
                    _gameType = phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"],
                    arryLockadeList = [],
                    redNumer = true,
                    blueNumer = true,
                    blueNumers = [],//蓝球被封号
                    boolInfo = '';
                //BUG:3863
                if (_gameType == 'ssq') {
                    $.each(balls, function (i) {

                        arryLockadeList = this.lockPoint.beforeBlockadeList;
                        $.each(arryLockadeList, function (j) {
                            if (this.blockadeDetail.length > 2 && Number(this.beishu) == 0) {
                                redNumer = false;
                            }
                            if (this.blockadeDetail.length == 2 && Number(this.beishu) == 0) {
                                if ($.inArray(this.blockadeDetail, blueNumers) < 0) {
                                    blueNumers.push(this.blockadeDetail);
                                }
                                blueNumer = false;
                            }

                        });


                    });

                    if (redNumer == false && blueNumer != false) {
                        boolInfo = "<div class='pop-title'><i class='ico-waring'></i><h4 class='pop-text'>您投注的内容中红球受限，请调整！</h4></div>";
                    } else if (redNumer != false && blueNumer == false) {
                        boolInfo = "<div class='pop-title'><i class='ico-waring'></i><h4 class='pop-text'>您投注的内容中蓝球受限，受限号码" + blueNumers.join(',') + "，请调整！</h4></div>";
                    } else if (redNumer == false && blueNumer == false) {
                        boolInfo = "<div class='pop-title'><i class='ico-waring'></i><h4 class='pop-text'>您投注的内容已经全部受限，请调整！</h4></div>";
                    } else {
                        boolInfo = "<div class='pop-title'><i class='ico-waring'></i><h4 class='pop-text'>您投注的内容有受限号码,请点击最佳处理查看后，进行调整！</h4></div>";
                    }
                    message.show({
                        mask: true,
                        title: '温馨提示',
                        content: boolInfo,
                        cancelIsShow: true,
                        cancelFun: function () {
                            message.hide();
                        }
                    });

                    return false;
                }


                message.show({
                    mask: true,
                    title: '温馨提示',
                    content: "<div class='pop-title'><i class='ico-waring'></i><h4 class='pop-text'>您投注的内容已经全部受限，请调整！</h4></div>",
                    cancelIsShow: true,
                    cancelFun: function () {
                        message.hide();
                    }
                });
                return false;
            }
            //手续费没达支起始金额时不显示提示
            if (amountAdjust > backOutStartFee && Number(backOutStartFee) != 0 && Number(backOutRadio) != 0) {
                handlingCharge = Games.getCurrentGameStatistics().formatMoney(amountAdjust * backOutRadio, 3);
                parameter['data']['tplData'].lotteryChareg = '<div id="showLotteryChareg" class="text-note">购买后请您尽量避免撤单，如撤单将收取手续费：<span class="price"><dfn>&yen;</dfn><span class="handlingCharge">' + handlingCharge + '</span>元</span></div><p class="text-note">本次投注，若未涉及到付款金额变化，将不再提示</p>';
            } else {
                handlingCharge = 0;
                parameter['data']['tplData'].lotteryChareg = '<p class="text-note">本次投注，若未涉及到付款金额变化，将不再提示</p>';
            }

            parameter['data']['tplData'].lotteryCharegPlurality = '';


            /*暂时不处理
             if(isFollowNumber < 1){
             //无追号
             var amountAdjust = Number(blockadeInfo['amountAdjust']);
             handlingCharge = Games.getCurrentGameStatistics().formatMoney(amountAdjust * backOutRadio, 3);
             parameter['data']['tplData'].lotteryChareg = '<div id="showLotteryChareg" style="display: none;">购买后请您尽量避免撤单，如撤单将收取手续费：<span class="price"><dfn>&yen;</dfn><span class="handlingCharge">'+handlingCharge+'</span>元</span></div>';
             parameter['data']['tplData'].lotteryCharegPlurality = '';
             $('#showLotteryChareg').show();
             }else{
             //追号手续费待完善
             parameter['data']['tplData'].lotteryChareg = '';
             var html ='',
             lotteryAmount = 0; //每一期投注金额
             totalAmount = 0 ;

             html = '<div style="margin-top:-12px;" id="showLotteryChareg">购买后请您尽量避免撤单，如撤单将收取手续费：</div><ul class="textarea cancel-bets clearfix" style="font-size:12px;margin-top:10px;"> <li><span style="font-size:13px;font-weight:500">奖期</span><span style="font-size:13px;font-weight:500">撤单手续费</span></li>';
             for(var i = 0; i < balls.length; i++){
             lotteryAmount =Number( balls.onePrice );
             if(lotteryAmount > backOutStartFee){
             totalAmount += ( lotteryAmount * backOutRadio );
             html +=  '<li><span>第'+isFollowNumber[i]['number']+'期</span><span class="price"><dfn>&yen;</dfn>'+Games.getCurrentGameStatistics().formatMoney(lotteryAmount * backOutRadio, 3)+'元</span></li>';
             }
             }
             html +='</ul>';

             parameter['data']['tplData']['lotteryCharegPlurality'] = html;
             }*/

            result['mask'] = true;
            result['closeIsShow'] = true;
            result['closeText'] = '关 闭';
            result['confirmIsShow'] = true;
            result['confirmText'] = '确 认';
            result['closeFun'] = function () {
                me.hide();
            };

            $.each(balls, function (i) {
                //判断可投倍数
                arryBlockadeList = this.multiple;
                coutRealBeishu += arryBlockadeList;

                if (Number(arryBlockadeList) == 0) {
                    return;
                }		//可投倍数0时，不加入注单表
                dataHas['' + this['id']] = this;
                ballStr = this['ball'];
                if (ballStr.length > maxLen) {
                    ballStr = ballStr.substr(0, maxLen) + '...';
                }
                typeName = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(this['type']).join('_');
                typeNamgeBall = $.trim(typeName) + $.trim(this['ball']);

                if ($.inArray(typeNamgeBall, arrGameName) == -1) {
                    blockadeData0.push('<li data-id="' + this['id'] + '">[' + typeName + '] ' + ballStr + '</li>');
                }

                arrGameName.push(typeNamgeBall);
            });

            blockadeData0.push('</ul>');
            if (coutRealBeishu < 1) {
                result['confirmIsShow'] = false;
            }
            tplData['gameTypeTitle'] = Games.getCurrentGame().getGameConfig().getInstance().getGameTypeCn();
            tplData['blockadeData0'] = blockadeData0.join('');
            tplData['amount'] = formatMoney(blockadeInfo['amountAdjust']);
            tplData['username'] = blockadeInfo['username'];
            tplData['amountAdjust'] = formatMoney(orderData['amount']);
            tplData['amountChange'] = formatMoney(blockadeInfo['amountAdjust']);
            tplData['display'] = '';

            //非追号
            if (orderData['isTrace'] < 1) {
                tplData['currentGameNumber'] = '第 ' + Games.getCurrentGame().getCurrentNumber() + ' 期';
            } else {
                //追号
                var len2 = parameter['data']['orderData']['orders'].length;
                tplData['currentGameNumber'] = "第 " + orderData['orders'][0]['number'] + "  至  " + orderData['orders'][len2 - 1]['number'] + "   共  <strong class='color-red'>" + len2 + "</strong></span>  期";

            }


            if (blockadeInfo['type'] == 1) {
                tplData['blockadeType'] = '受限';
            } else if (blockadeInfo['type'] == 2) {
                tplData['blockadeType'] = '奖金变动';
                tplData['display'] = 'none';
            } else {
                tplData['blockadeType'] = '奖金变动及受限';
            }

            result['content'] = me.formatHtml(me.getHtmlbetExpired(), tplData);

            //再次提交注单
            result['confirmFun'] = function () {
                var message = Games.getCurrentGameMessage();
                if (isSubmitLoading) {
                    return false;
                }
                //去除倍数为0的注单
                var i = 0, len = balls.length;
                for (; i < len;) {
                    if (Number(balls[i].multiple) == 0) {
                        balls.splice(i, 1);
                        i = i;
                    } else {
                        i++;
                    }
                }

                $.ajax({
                    url: Games.getCurrentGame().getGameConfig().getInstance().submitUrl(),
                    data: JSON.stringify(orderData),
                    dataType: 'json',
                    method: 'POST',
                    contentType: "application/json; charset=utf-8",
                    beforeSend: function () {
                        isSubmitLoading = true;
                        $('.confirm').html('提交中...');
                        $('.closeTip').css('display', 'none').attr("disabled", "true");
                    },
                    success: function (r) {
                        //返回消息标准
                        // {"isSuccess":1,"type":"消息代号","msg":"返回的文本消息","data":{xxx:xxx}}
                        if (Number(r['isSuccess']) == 1) {
                            //推荐查询地址
                            var dataTrace = Number(r['isTrace']) > 0 ? 1 : 0;
                            if (dataTrace > 0) {
                                r.data.tplData["url"] = "/gameUserCenter/queryPlans?time=7";
                                r.data.tplData["gameType"] = '追号记录';
                            } else {
                                r.data.tplData["url"] = "/gameUserCenter/queryOrdersEnter?time=7";
                                r.data.tplData["gameType"] = '投注记录';
                            }
                            message.show(r);
                            Games.currentGameSubmit.clearData();
                            Games.currentGameSubmit.afterSubmitSuccess();
                            setTimeout(function () {
                                Games.getCurrentGameTrace().hide();
                                $("html,body").animate({scrollTop: $("#J-top-game-menu").offset().top}, 1000);
                            }, 2000);
                        } else {
                            message.show(r);
                        }
                    },
                    complete: function () {
                        isSubmitLoading = false;
                        me.fireEvent('afterSubmit');
                        $('.closeTip').css('display', 'display').removeAttr("disabled");
                    },
                    error: function () {
                        message.show({
                            mask: true,
                            title: '温馨提示',
                            content: "<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>方案提交失败,<br />请检查网络并重新提交！</h4></div>",
                            cancelIsShow: true,
                            cancelFun: function () {
                                message.hide();
                            }
                        });
                    }
                });
            };
            //console.log(parameter);

            me.show($.extend(result, parameter));
            host.util.toViewCenter(me.win.dom);
            //console.log(parameter);

            function htmlReplaces(msg) {
                msg = msg.replace(/ /g, "&amp;nbsp;");
                return msg;
            }

            //面板内的事件
            $('#J-blockade-panel-main').on('click', '[data-action]', function (e) {
                var el = $(this), action = $.trim(el.attr('data-action')), id = $.trim(el.parent().attr('data-id'));
                e.preventDefault();
                //console.log(action, id, dataHas[id]);
                switch (action) {
                    //查看详情
                    case 'blockade-detail' :
                        //将投注内容转换成Input内容
                        var form = $('#J-form-blockade-detail').attr("action", Games.getCurrentGame().getGameConfig().getInstance().submitUrl() + "/showBlockadeInfo"),
                            splitStr = '-';
                        form.html('');
                        //选球内容和玩法名称以 /// 分隔
                        $.each(balls, function () {
                            var me = this;
                            if (me['lockPoint']) {
                                $('<input type="hidden" value=' + htmlReplaces(JSON.stringify(orderData)) + ' name="data" />').appendTo(form);
                            }

                        });
                        form.submit();
                        break;
                    default:
                        break;
                }
            });


        },


        getHtmlillegalTools: function () {
            return this.getHtmlWaring();
        },
        //提交失败
        rebulidsubFailed: function (parameter) {
            var me = this, result = {};
            result['mask'] = true;
            result['closeText'] = '关 闭';
            result['closeIsShow'] = true;
            result['closeFun'] = function () {
                me.hide();
            };
            result['content'] = me.formatHtml(me.getHtmlbetExpired(), parameter['data']['tplData']);
            me.show($.extend(result, parameter));
        },
        getHtmlsubFailed: function () {
            return this.getHtmlWaring();
        },
        //添加题目
        setTitle: function (html) {
            var me = this, win = me.win;
            win.setTitle(html);
        },
        //添加内容
        setContent: function (html, delay) {
            var me = this, win = me.win;

            win.setContent(html, delay);
        },
        //隐藏关闭按钮
        hideClose: function () {
            var me = this, win = me.win;

            win.getCloseDom().hide();
        },
        //隐藏标题栏
        hideTitle: function () {
            var me = this, win = me.win;

            win.getTitleDom().hide();
        },

        //弹窗显示 具体参数说明
        //弹窗类型(会根据弹窗类型自动获取模版) type
        //模版 tpl  数据 tplData
        //内容:content, 绑定函数: callback, 是否遮罩: mask
        //宽度:width, 长度:height, 自动关闭时间单位S:time
        //是否显示头部: hideTitle, 是否显示关闭按钮:hideClose
        //确认按钮 是否显示: confirmIsShow 名称: confirmText 事件: confirmFun
        //取消按钮 是否显示: cancelIsShow  名称: cancelText	事件: cancelFun
        //关闭按钮 是否显示: closeIsShow   名称: closeText	事件: closeFun
        show: function (data) {
            var me = this, win = me.win;
            me.reSet();
            if (typeof data['data'] == 'undefined') {
                data['data'] = {};
            }
            data['data']['tplData'] = typeof data['data']['tplData'] == 'undefined' ? {} : data['data']['tplData'];

            if (!data) {
                return
            }

            if (data['type']) {
                me.doAction(data);
                return;
            } else {
                if (typeof data['tpl'] != 'undefined') {
                    data['content'] = me.formatHtml(data['tpl'], data['data']['tplData']);
                }
            }

            //取消自动关闭时间缓存
            if (closeTime) {
                clearTimeout(closeTime);
                closeTime = null;
            }

            //加入题目 && 内容
            me.setTitle(data['title'] || '温馨提示');
            me.setContent(data['content'] || '');

            //按钮名称
            if (data['confirmText']) {
                win.setConfirmName(data['confirmText']);
            }
            if (data['cancelText']) {
                win.setCancelName(data['cancelText']);
            }
            if (data['closeText']) {
                win.setCloseName(data['closeText']);
            }
            //按钮事件
            if (data['normalCloseFun']) {
                win.doNormalClose = data['normalCloseFun'];
            }
            if (data['confirmFun']) {
                win.doConfirm = data['confirmFun'];
            }
            if (data['cancelFun']) {
                win.doCancel = data['cancelFun'];
            }
            if (data['closeFun']) {
                win.doClose = data['closeFun'];
            }
            //按钮显示
            if (data['confirmIsShow']) {
                win.showConfirmButton();
            }
            if (data['cancelIsShow']) {
                win.showCancelButton();
            }
            if (data['closeIsShow']) {
                win.showCloseButton();
            }
            //判断是否隐藏头部和关闭按钮
            if (data['hideTitle']) {
                me.hideTitle();
            }
            if (data['hideClose']) {
                me.hideClose();
            }
            //遮罩显示
            if (data['mask']) {
                me.mask.show();
            }

            //限制窗口高度超过
            //window总体高度
            me.limitHeight();
            //richardgong 2015-07-27
            !data.needPrint && win.dom.find(".btn-link").remove();
            win.show();

            //执行回调事件
            if (data['callback']) {
                data['callback'].call(me);
            }

            //定时关闭
            if (data['time']) {
                closeTime = setTimeout(function () {
                    me.hide();
                    clearTimeout(closeTime);
                    closeTime = null;
                }, data['time'] * 1000)
            }
        },
        limitHeight: function () {
            var me = this,
                wHeight = $(window).height(),
                dom = me.getContainerDom(),
                domHeight = dom.outerHeight();

            if (domHeight > wHeight) {
                dom.height(Math.floor(wHeight * 0.9));
                dom.css({
                    'overflow': 'auto'
                });
            }
        },
        getContainerDom: function () {
            var me = this;
            return me.win.getContainerDom();
        },
        //获取内容容器DOM
        getContentDom: function () {
            var me = this;
            return me.win.getContentDom();
        },
        //弹窗隐藏
        hide: function () {
            var me = this, win = me.win;

            win.hide();
            me.reSet();
        },
        //重置
        reSet: function () {
            var me = this, win = me.win;

            me.mask.hide();
            me.setTitle('提示');
            me.setContent('');
            win.hideConfirmButton();
            win.hideCancelButton();
            win.hideCloseButton();
            win.doConfirm = function () {
            };
            win.doCancel = function () {
            };
            win.doClose = function () {
            };
            win.doNormalClose = function () {
            };
            win.setConfirmName('确 认');
            win.setCancelName('取 消');
            win.setCloseName('关 闭');
            win.reSetPosition();
        }
    }

    var Main = host.Class(pros, Event);
    Main.defConfig = defConfig;
    Main.getInstance = function (cfg) {
        return instance || (instance = new Main(cfg));
    };
    host[name] = Main;

})(phoenix, "GameMessage", phoenix.Event);


(function (host, name, Event, undefined) {
    var defConfig = {
            //主面板dom
            mainPanel: '#J-play-select',
            //面板数据
            data: '',
            //面板dom
            mainDom: '.play-select',
            //html结构
            html: $('<div id="change"><ul class="play-select-title clearfix"></ul><ul class="play-select-content clearfix"></ul></div>'),
            //结果容器
            resultDom: '#change .play-select-content'
        },
    //渲染实例
        instance,
    //游戏实例
        Games = host.Games;

    //渲染方法
    var pros = {
        init: function (cfg) {
            var me = this;
            //缓存方法
            Games.setCurrentGameTypes(me);

            me.container = $(cfg.mainPanel);
            //计数
            me.count = 0;
            //渲染计数
            me.showCount = 0;
            //面板数据
            //richardgong 2015-12-4
            me.inst = Games.getCurrentGame().getGameConfig().getInstance();
            me.data = me.inst.getTypes();
            //面板DOM
            me.html = cfg.html;
            //执行渲染前事件
            //me.fireEvent('startShow');
            //执行渲染
            me.isSupport2000 = false;
            me.isfirstimeuse2000 = me.inst.defConfig.isfirstimeuse2000;
            me.isfirstimeusediamond2000 = me.inst.defConfig.isfirstimeusediamond2000;
            console.log(me.inst.defConfig);
            setTimeout(function () {
                me._showMainHTML(me.data);
            }, 0);
        },
        //获取外部容器DOM
        getContainerDom: function () {
            return this.container;
        },
        //数据解析
        _showMainHTML: function (data, inner) {
            var me = this;
            for (var i = 0, c, l = data.length; i < l; i++) {
                c = data[i];
                //richardgong 2015-12-5
                c.isNew && (me.isSupport2000 = true);
                if (!inner) {
                    //一级列表
                    //richardgong 2015-12-4
                    me._bulidHTMl(c, 'top', c.isNew ? ' caojiduizhi' : '');
                }
                if (Object.prototype.toString.call(c.childs) == '[object Array]' && c.childs.length != 0) {
                    //二级列表
                    me._showMainHTML(c.childs, true);
                    me._bulidHTMl(c, 'child', c.isNew ? ' caojiduizhi' : '');
                } else {
                    //末级列表
                    me._bulidHTMl(c, 'terminal', c.isNew ? ' caojiduizhi' : '');
                }
            }
            //遍历结束输出html结果
            if (!inner) {
                var t = me.html.find('.play-select-title');
                if (me.isSupport2000) {
                    $('<li class="choose-label">普通玩法:</li>').insertBefore(t.find('li').eq(0));
                    $('<li class="choose-label">超级2000:</li>').insertBefore(t.find('li.caojiduizhi').eq(0));
                    //$('<li class="choose-label">&nbsp;&nbsp;&nbsp; <img src="images/2000.gif" /></li>').insertAfter(t.find('li').last());
                    $('<li class="choose-label brand2000" id ="brand2000click" ></li>').insertAfter(t.find('li').last());
                    me._appendHtml(me.html);

                    $('.play-section').addClass('chaojidiuzhi-bg');
                    setTimeout(function () {
                        $('.choose-label').off('click');
                    }, 10);
                } else {
                    me._appendHtml(me.html);
                }
            }


            //2016-2-23 richardgong

            if (me.isSupport2000 && me.isfirstimeuse2000) {
                me.show2000Guider();
            }

            if (me.isfirstimeusediamond2000) {
                me.showdiamond2000Guider();
            }

        },
        showdiamond2000Guider: function () {

            if(!$('.guide20000').size()) {
                var guiderHtml = '<div class="guide20000"><div class="guide20000-content guide20000-content2"><span class="guide20000-close guide20000-close2">x</span></div></div>';
                var iframe = $('<iframe></iframe>');
                iframe.attr('src', '/gameActive/mmc/diamondHelpguilder');
                $('body').append($(guiderHtml));
                $('.guide20000-content2').append(iframe);

                $('.guide20000-close,.guide20000-try').click(function () {
                    $('.guide20000').remove();
                    $.cookie('diamondguider', 'true');

                });
            }

        },
        show2000Guider: function () {

            var guiderHtml = '<div class="guide20000"><div class="guide20000-content"><span class="guide20000-close">x</span><span class="guide20000-try"></span></div></div>';
            if (!$('.guide20000').size()) {
                $('body').append($(guiderHtml));
                $('.guide20000-close,.guide20000-try').click(function () {
                    $('.guide20000').remove();
                    $.cookie('firstimeuse2000', '');
                });
            }
        },
        //输出dom结构
        //@type 需要指定的层级
        _bulidHTMl: function (data, type, othername) {
            var me = this,
                title = me.html.find('.play-select-title'),
                result = me.html.find('.play-select-content');
            //richardgong 2015-12-4
            var otherclass = othername || '';
            switch (type) {
                //一级
                case 'top':
                    //richardgong 2015-12-4
                    var isdiamondTag = data.isdiamond ? 'true' : '';

                    title.append('<li data-diamond="' + isdiamondTag + '" class="' + data.name + otherclass + '">' + data.title.replace('超级2000_', '') + '</li>');
                    //title.append('<li  class="'+data.name+otherclass+'">' + data.title.replace('超级2000_','') + '</li>');
                    result.append('<li class="' + data.name + otherclass + '"></li>');
                    break;
                //二级
                case 'child':
                    result.find('.' + data.parent).append('<dl class="' + data.name + otherclass + '"><dt>' + data.title + '：</dt></dl>');
                    break;
                //末级
                case 'terminal':
                    setTimeout(function () {
                        //richardgong 2016-3-28
                        var gtype = data.mode + "." + data.parent + "." + data.name;
                        var dd = $('<dd  data-type="' + gtype + '" class="' + data.name + otherclass + '">' + data.title + '</dd>');
                        data.diamond && dd.data('diamond', data.diamond);
                        result.find('.' + data.mode + ' .' + data.parent).append(dd);
                    }, 0);
                    break;
            }
        },
        _appendHtml: function (html) {
            var me = this;
            $(me.defConfig.mainDom).prepend(html);
            //绑定TAB切换方法
            me._bindTagSelect();
            //定时器队列
            setTimeout(function () {
                me.fireEvent('endShow');
            }, 10);
        },
        _bindTagSelect: function () {
            var me = this, tab;
            tab = new phoenix.Tab({
                par: '#change',
                triggers: '.play-select-title > li',
                panels: '.play-select-content > li',
                eventType: 'click',
                currPanelClass: 'current'
            });
            me.bigTab = tab;

            tab.addEvent('afterSwitch', function (i, s) {
                var dom = this.getTrigger(s),
                    name = $('#change .play-select-content .' + dom.attr('class').replace(/\s.*/g, '') + ' dd:first');
                me._getMode(name);
            });
            $('#change .play-select-content').on('click', 'dd', function () {
                me._getMode($(this));
            });
        },
        _getMode: function (dom) {
            var me = this,
                name = dom.attr('class').replace(/\s.*/g, ''),
                modeName = dom.parent('dl').attr('class').replace(' current', ''),
                parName = dom.parents('li').eq(0).attr('class').replace(/\s.*/g, ''),
                full = parName + '.' + modeName + '.' + name;

            me.changeMode(full);
        },
        showTitleDom: function () {
            var me = this,
                dom = me.getContainerDom(),
                methodListDom = dom.find('.play-select-content');

            methodListDom.show();
        },
        hiddenTitleDom: function () {
            var me = this,
                dom = me.getContainerDom(),
                methodListDom = dom.find('.play-select-content');

            methodListDom.hide();
        },
        //切换事件
        changeMode: function (modeName) {
            var me = this,
                name = modeName.split('.'),
                container = me.getContainerDom(),
                cls = 'current',
                titles,
                panels,
                buttons,
                index = 0,
                currPanel,
                numMap = {
                    'sixing': [1,1,0,0,0],
                    'qiansan': [0,0,0,1,1],
                    'zhongsan': [1,0,0,0,1],
                    'housan': [1,1,0,0,0],
                    'qianer': [0,0,0,1,1],
                    'houer': [1,1,0,0,0],
                    'yixing': [1,1,0,0,0]
                };

            try {
                if (!!Games.getCurrentGame().getCurrentGameMethod() && modeName == Games.getCurrentGame().getCurrentGameMethod().getGameMethodName()) {
                    return;
                }
            } catch (e) {
            }

            if (container.find('.play-select-content').is(':hidden')) {
                me.showTitleDom();
            }

            //执行自定义事件
            me.fireEvent('beforeChange', me.container, modeName);
            //执行切换
            Games.getCurrentGame().switchGameMethod(modeName);
            //执行高亮
            //richardgong 2015-12-4
            window.isCaojiduizhi = !!modeName.match(/_2000/);
            if (window.isCaojiduizhi) {
                $('.header').addClass('chaojidiuzhiheader');
                $('.number-section').addClass('number-section-caojiduizhi');
                $('.play-section').addClass('chaojidiuzhi-bg');
            } else {
                $('.number-section').removeClass('number-section-caojiduizhi');
                $('.play-section').removeClass('chaojidiuzhi-bg');
                $('.header').removeClass('chaojidiuzhiheader');
            }

            //添加金球样式
            var arrGold = numMap[name[0].replace(/_2000/,'')];
            if(arrGold){
                $.each($('#J-lottery-info-balls em'),function(n,v){
                    if(arrGold[n] == 1){
                        $(v).addClass('gold');
                    }else{
                        $(v).removeClass('gold');
                    }
                });
            }

            titles = me.container.find('.play-select-title li');

            titles.removeClass(cls);
            me.container.find('.play-select-title').find('.' + name[0]).addClass(cls);

            panels = me.container.find('.play-select-content li');
            panels.removeClass(cls);
            currPanel = me.container.find('.play-select-content').find('.' + name[0]);
            currPanel.addClass(cls);

            buttons = currPanel.find('dd').removeClass(cls);
            currPanel.find('.' + name[1] + ' .' + name[2]).addClass(cls);


            titles.each(function (i) {
                if ($(this).hasClass(cls)) {
                    index = i;
                    return false;
                }
            });
            me.bigTab.index = index;


            //alert(name[]);
            //执行自定义事件
            me.fireEvent('endChange');

            //richardgong2016-3-15
            var isdiamond = window.isdiamond = !!$('#change .play-select-title .current').data('diamond');
        }
    };

    var Main = host.Class(pros, Event);
    Main.defConfig = defConfig;
    Main.getInstance = function (cfg) {
        return instance || (instance = new Main(cfg));
    };
    host[name] = Main;

})(phoenix, "GameTypes", phoenix.Event);


//游戏选球统计，如注数、当前操作金额等

(function (host, name, Event, undefined) {
    var defConfig = {
            //主面板dom
            mainPanel: '#J-balls-statistics-panel',
            //倍数限制dom
            multipleLimitDom: '#J-balls-statistics-multipleLimit',
            //注数dom
            lotteryNumDom: '#J-balls-statistics-lotteryNum',
            //倍数
            multipleDom: '#J-balls-statistics-multiple',
            //总金额
            amountDom: '#J-balls-statistics-amount',
            moneyUnitDom: '#J-balls-statistics-moneyUnit',
            //元/角模式比例  1为元模式 0.1为角模式 0.01为分模式  // richardgong 2015-06-26 设置初始值 
			moneyUnit:$.cookie("moneyUnitDom-record") || 1,
			//元角模式对应的中文
			moneyUnitData:{'0.01':'分','0.1':'角','1':'元'},
            //单注价格
            onePrice: 2,
            //倍数
            multiple: 1,
            //倍数最大限制
            multipleLimit: 88
        },
        instance,
        Games = host.Games;


    var pros = {
        init: function (cfg) {
            var me = this;

            me.initControl(cfg);
        },
        initControl: function (cfg) {
            var me = this;
            Games.setCurrentGameStatistics(me);

            me.panel = $(cfg.mainPanel);
            me.moneyUnit = cfg.moneyUnit;
            me.onePrice = cfg.onePrice;
            me.multiple = cfg.multiple;
            //倍数最大限制
            me.multipleLimit = cfg.multipleLimit;
            me.setMultipleLimit(cfg.multipleLimit);
            //玩法名称
            me.gameMethodName = '';
            //已组合好的选球数据
            me.lotteryData = [];


            //倍数选择模拟下拉框
            me.multipleDom = new phoenix.Select({
                realDom: cfg.multipleDom, isInput: true, expands: {
                    inputEvent: function () {
                        var me = this;
                        me.getInput().keyup(function (e) {
                            var v = this.value,
                                moneyUnitDom = Games.getCurrentGameStatistics().getMoneyUnitDom().getValue(),//元角选中
                                multipleLimit = Games.getCurrentGameStatistics().getMultipleLimit();//当前玩法倍数限制

                            this.value = this.value.replace(/[^\d]/g, '');
                            v = Number(this.value);
                            if (v < 1) {
                                this.value = 1;
                            } else {
                               //元模式时
								if(Number(moneyUnitDom)== 1){
									this.value = v > multipleLimit ? multipleLimit : v;
								}else if(Number(moneyUnitDom)== 3){
									this.value = v > multipleLimit*100 ? multipleLimit*100 : v;
								}else{
									this.value = v > multipleLimit*10 ? multipleLimit*10 : v;
								}

                            }
                            me.setValue(this.value);

                        });
                    }
                }
            });
            me.multipleDom.setValue(me.multiple);
            me.multipleDom.addEvent('change', function (e, value, text) {
                var num = Number(value),
                    moneyUnitDom = Games.getCurrentGameStatistics().getMoneyUnitDom().getValue(),//元角选中
                    maxnum = Games.getCurrentGameStatistics().getMultipleLimit();//当前玩法倍数限制
                if(Number(moneyUnitDom)== 1){//元模式时
					if(num > maxnum){
						num = maxnum;
						this.setValue(num);
					}
				}else if(Number(moneyUnitDom)== 3){//分模式时
					if(num > maxnum * 100){
						num = maxnum * 100;
						this.setValue(num);
					}
				}else{//角模式时倍数*10处理
					if(num > maxnum * 10){
						num = maxnum * 10;
						this.setValue(num);
					}
				}	
                me.setMultiple(num);
                me.updateData({
                    'lotterys': Games.getCurrentGame().getCurrentGameMethod().getLottery(),
                    'original': Games.getCurrentGame().getCurrentGameMethod().getOriginal()
                }, Games.getCurrentGame().getCurrentGameMethod().getGameMethodName());

            });


            //元角模式模拟下拉框

            //richardgong 添加元角模式记录
            $(cfg.moneyUnitDom).data('moneyUnitDom-record', 'true');
            me.moneyUnitDom = new host.Select({realDom: cfg.moneyUnitDom});
            //在未添加change事件之前设置初始值
            me.moneyUnitDom.setValue(me.moneyUnit);
            me.moneyUnitDom.addEvent('change', function (e, value, text) {
                var gamelimit = Games.getCurrentGame().getDynamicConfig()['gamelimit'][me.gameMethodName]['maxmultiple'],
                    gameMoneyUnit = Number(value);
                me.setMoneyUnit(Number(value));
                me.updateData({
                    'lotterys': Games.getCurrentGame().getCurrentGameMethod().getLottery(),
                    'original': Games.getCurrentGame().getCurrentGameMethod().getOriginal()
                }, Games.getCurrentGame().getCurrentGameMethod().getGameMethodName());
                if(gameMoneyUnit == 1){
					me.setMultipleLimit(gamelimit);
				}else if(gameMoneyUnit == 3){
					me.setMultipleLimit(gamelimit,'fms');
				}else{
					me.setMultipleLimit(gamelimit,'jms');
				}
                me.multipleDom.setValue(1);//添加change事件之时选择倍数下拉框设置初始值为1
            });

            //初始化相关界面，使得界面和配置统一
            me.updateData({'lotterys': [], 'original': []});
            me.initRebate();


        },
        //初始化返点内容
        initRebate: function () {
            var me = this;
            //返点下拉菜单
            var awardRetStatus = phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["awardRetStatus"],
                awardGroupRetStatus = phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["awardGroupRetStatus"];
            if (awardRetStatus && awardGroupRetStatus) {
                me.rebateSelect = new host.Select({
                    realDom: '#J-select-rebate',
                    cls: 'choose-model-large'
                });
            }


        },
        getMultipleDom: function () {
            return this.multipleDom;
        },
        getMultipleTextDom: function () {
            return $('#J-balls-statistics-multiple-text');
        },
        setMultipleLimit: function (num, obj) {
            var me = this, text = '无限制';
            num = Number(num);
            if (isNaN(num)) {
                //num = 99999;
                num = (obj == undefined ? 99999 : 999999);
            }
            this.multipleLimit = num;
            if (num < 0) {
                this.multipleLimit = (obj == undefined ? 99999 : 999999);
                text = '无限制';
            } else {
                if(obj == 'jms'){
					if(num < 999999){
						text = '' + num*10 + ' 倍';
					}
				}else if(obj == 'fms'){
					if(num < 9999999){
						text = '' + num*100 + ' 倍';
					}
				}else{
					if(num < 99999){
						text = '' + num + ' 倍';
					}
				}

            }

            me.getMultipleLimitDom().html(text);
        },
        getMultipleLimit: function () {
            return this.multipleLimit;
        },
        getMoneyUnitText: function (moneyUnit) {
            return this.defConfig.moneyUnitData['' + moneyUnit];
        },
        //更新各种数据
        updateData: function (data, name) {
            var me = this,
                cfg = me.defConfig,
                count = data['lotterys'] == undefined ? 0 : data['lotterys'].length,
                price = me.onePrice,
                multiple = me.multiple,
                moneyUnit = me.moneyUnit;

            //设置投注内容
            me.setLotteryData(data);
            //设置玩法类型
            me.setGameMethodName(name);
            //设置倍数
            //由于设置会引发updateData的死循环，因此在init里手动设置一次，之后通过change事件触发updateData
            //me.setMultipleDom(multiple);
            //更新元角模式(切换换游戏时调用)
            //me.setMoneyUnitDom(moneyUnit);
            //更新注数
            me.setLotteryNumDom(count);
            //更新总金额
            me.setAmountDom(me.formatMoney(count * moneyUnit * multiple * price));

        },
        //获取当前数据
        getResultData: function () {
            var me = this,
                lotterys = me.getLotteryData();
            if (lotterys['lotterys'].length < 1) {
                return {};
            }
            return {
                type: me.getGameMethodName(),
                original: lotterys['original'],
                lotterys: lotterys['lotterys'],
                // retPointText:Number(me.rebateSelect.getValue()),
                retPointValue: Games.getCurrentGameStatistics().rebateSelect ? Games.getCurrentGameStatistics().rebateSelect.getValue() : '',
                moneyUnit: me.moneyUnit,
                num: lotterys['lotterys'].length,
                multiple: me.multiple,
                //单价
                onePrice: me.onePrice,
                //总金额
                amount: me.formatMoney(lotterys['lotterys'].length * me.moneyUnit * me.multiple * me.onePrice),
                //格式化后的总金额
                amountText: me.formatMoney(lotterys['lotterys'].length * me.moneyUnit * me.multiple * me.onePrice)
            };
        },
        //设置玩法类型
        setGameMethodName: function (name) {
            this.gameMethodName = name;
        },
        //设置玩法类型
        getGameMethodName: function (name) {
            return this.gameMethodName;
        },
        //设置元角模式
        setMoneyUnit: function (num) {
            var me = this;
            me.moneyUnit = num;
        },
        getMoneyUnit: function () {
            return this.moneyUnit;
        },
        getLotteryData: function () {
            return this.lotteryData;
        },
        setLotteryData: function (data) {
            var me = this;
            me.lotteryData = data;
        },
        //将数字保留两位小数并且千位使用逗号分隔
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
        //将被格式的金额返回成float型
        ToForamtmoney: function (num) {
            return parseFloat(num.replace(/[^\d\.-]/g, ""));
        },
        //倍数限制dom
        getMultipleLimitDom: function () {
            var me = this, cfg = me.defConfig;
            return me.multipleLimitDom || (me.multipleLimitDom = $(cfg.multipleLimitDom));
        },
        //注数
        getLotteryNumDom: function () {
            var me = this, cfg = me.defConfig;
            return me.lotteryNumDom || (me.lotteryNumDom = $(cfg.lotteryNumDom));
        },
        setLotteryNumDom: function (v) {
            var me = this;
            me.getLotteryNumDom().html(v);
        },
        //倍数
        getMultipleDom: function () {
            return this.multipleDom;
        },
        getMultip: function () {
            var me = this;
            return me.multiple;
        },
        setMultipleDom: function (v) {
            var me = this;
            me.getMultipleDom().setValue(v);
        },
        setMultiple: function (num) {
            this.multiple = num;
        },
        //元角模式
        getMoneyUnitDom: function () {
            return this.moneyUnitDom;
        },
        setMoneyUnitDom: function (v) {
            var me = this;
            me.getMoneyUnitDom().setValue(v);
        },
        hidesetMoneyUnitDom: function () {
            this.moneyUnitDom.hide();
        },
        //获取单注金额
        getOnePrice: function () {
            var me = this;
            return me.onePrice;
        },
        //总金额
        getAmountDom: function () {
            var me = this, cfg = me.defConfig;
            return me.amountDom || (me.amountDom = $(cfg.amountDom));
        },
        setAmountDom: function (v) {
            var me = this;
            me.getAmountDom().html(v);
        },
        reSet: function () {
            var me = this, cfg = me.defConfig;
            //me.multipleDom.setValue(cfg.multiple);
            //me.moneyUnitDom.setValue(cfg.moneyUnit);
            //复位操作成功
            me.fireEvent('afterStatisReset');
        }


    };

    var Main = host.Class(pros, Event);
    Main.defConfig = defConfig;
    Main.getInstance = function (cfg) {
        return instance || (instance = new Main(cfg));
    };
    host[name] = Main;

})(phoenix, "GameStatistics", phoenix.Event);


//游戏订单模块
(function (host, name, Event, undefined) {
    var defConfig = {
            //主面板dom
            containerDom: '#J-balls-order-container',
            //总注数dom
            totalLotterysNumDom: '#J-gameOrder-lotterys-num',
            //总金额dom
            totalAmountDom: '#J-gameOrder-amount',
            //当注单被选中时的样式
            selectedClass: 'game-order-current',
            //每行投注记录html模板
            //rowTemplate: '<li data-param="action=reselect&id=<#=id#>" id="gameorder-<#=id#>"><div class="result"><span style="font-family: Arial,verdana;"><span ><dfn>&yen;</dfn></span><#=amountText#><#=moneyUnitText#></span><span class="bet"><#=num#>注</span><span class="multiple"><#=multiple#>倍</span><span class="close"><a data-param="action=del&id=<#=id#>" href="javascript:void(0);">删除</a></span></div><span>[<#=typeText#>]</span><span style="position:relative" ><#=lotterysText#></span></li>',
            rowTemplate: '<li data-param="action=reselect&id=<#=id#>" id="gameorder-<#=id#>"><div class="result"><span class="retPoint"><#=retPointText#></span><span class="moneyUnitText"><#=moneyUnitText#></span><span class="bet"><#=num#>注</span><span class="multiple"><#=multiple#>倍</span><span class="price"><span><dfn>&yen;</dfn></span><#=amountText#></span><span class="close"><a data-param="action=del&id=<#=id#>" href="javascript:void(0);" title="删除">删除</a></span></div><span>[<#=typeText#>]</span><span style="position:relative" ><#=lotterysText#></span></li>',
            //显示内容截取字符串长度
            lotterysTextLength: 20,
            //投注按钮Dom
            addOrderDom: '#J-add-order'
        },

    //获取当前游戏
        Games = host.Games,
        instance,
        orderID = 1,
        Ts = Object.prototype.toString;
    //将来仿url类型的参数转换为{}对象格式，如 q=wahaha&key=323444 转换为 {q:'wahaha',key:'323444'}
    //所有参数类型均为字符串
    var formatParam = function (param) {
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
        init: function (cfg) {
            var me = this,
                cfg = me.defConfig;
            me.cacheData = {};
            me.cacheData['detailPostParameter'] = {};
            me.orderData = [];
            Games.setCurrentGameOrder(me);
            me.container = $(cfg.containerDom);
            me.totalLotterysNum = 0;
            me.totalLotterysNumDom = $(cfg.totalLotterysNumDom);
            me.totalAmount = 0.00;
            me.totalAmountDom = $(cfg.totalAmountDom);
            me.currentSelectId = 0;

            me.eventProxy();

            //当添加数据发生时，触发追号面板相关变更
            me.addEvent('afterAdd', function () {
                var tableType = Games.getCurrentGameTrace().getRowTableType();
                if (Games.getCurrentGameTrace().isOpen()) {
                    Games.getCurrentGameTrace().updateOrder();
                }
                //richardgong 2015-12-4
                $('#J-balls-order-container li').each(function () {
                    if ($(this).text().match(/超级2000/)) {
                        $(this).addClass('caojiduizhi-balls-order');
                    }
                    ;
                });
                //2016-2-24 richardgong
                var tracbtab = $('.chase-tab-t').eq(1);
                var ct = $('.chase-tab-content').eq(1);
                if (/超级2000/.test($('#J-balls-order-container').text())) {
                    ct.removeClass('.chase-tab-content-current')
                    tracbtab.hide();
                } else {
                    tracbtab.show();
                }

            });
            //删除
            me.addEvent('afterRemoveData', function () {
                var tableType = Games.getCurrentGameTrace().getRowTableType();
                if (Games.getCurrentGameTrace().isOpen()) {
                    Games.getCurrentGameTrace().updateOrder();
                }
            });
            //清空
            me.addEvent('afterResetData', function () {
                var tableType = Games.getCurrentGameTrace().getRowTableType();
                Games.getCurrentGameTrace().updateOrder(true);
            });

            //当发生玩法面板切换时，触发取消注单的选择状态
            Games.getCurrentGameTypes().addEvent('endChange', function () {
                me.cancelSelectOrder();
            });

        },
        setTotalLotterysNum: function (v) {
            var me = this;
            me.totalLotterysNum = Number(v);
            me.totalLotterysNumDom.html(v);
        },
        setTotalAmount: function (v) {
            var me = this;
            me.totalAmount = Number(v);
            me.totalAmountDom.html(me.formatMoney(v));
        },
        addData: function (order) {
            var me = this;
            me.orderData.unshift(order);
        },
        getOrderById: function (id) {
            var me = this,
                id = Number(id),
                orderData = me.orderData,
                i = 0,
                len = orderData.length;

            for (i = 0; i < len; i++) {
                if (Number(orderData[i]['id']) == id) {
                    return orderData[i];
                }
            }
        },
        removeData: function (id) {
            var me = this,
                id = Number(id),
                data = me.orderData,
                i = 0,
                len = data.length;
            for (; i < len; i++) {
                if (data[i]['id'] == id) {
                    me.fireEvent('beforeRemoveData', data[i]);
                    me.orderData.splice(i, 1);
                    me.updateData();
                    me.fireEvent('afterRemoveData');
                    break;
                }
            }
            $('#gameorder-' + id).remove();
            me.fireEvent('afterRemoveData');
        },
        reSet: function () {
            var me = this;

            me.container.empty();
            me.orderData = [];
            me.updateData();
            me.fireEvent('afterResetData');

            return me;
        },
        updateData: function (typename) {
            var me = this,
                total = me.getTotal();
            //
            //显示所有订单信息.......
            //方案注数 1000注，金额 ￥2000.00 元
            me.setTotalLotterysNum(total['count']);
            me.setTotalAmount(total['amount']);

        },
        getTotal: function () {
            var me = this,
                data = me.orderData,
                i = 0,
                len = data.length,
                count = 0,
                amount = 0;
            for (; i < len; i++) {
                count += data[i]['num'];
                amount += (data[i]['num'] * data[i]['onePrice'] * data[i]['moneyUnit'] * data[i]['multiple']);
            }
            return {
                'count': count,
                'amount': amount,
                'orders': data
            };
        },
        //获取订单允许设置的最大倍数(通过获取每个玩法倍数限制的最小值)
        //返回值 {gameMethod:'玩法名称',maxnum:999}
        getOrderMaxMultiple: function () {
            var me = this,
                limits = Games.getCurrentGame().getDynamicConfig()['gamelimit'],
                orders = me.getTotal()['orders'],
                i = 0,
                len = orders.length,
                type, multiple,
                arr = [],
                typeText = '',
                maxNum,
                _moneyUnit;
            for (; i < len; i++) { //TODO  将倍数限制multiple和MaxNum设置为常量，让所有玩法均可以追号
                type = orders[i]['type'];
                multiple = orders[i]['multiple'];
                _moneyUnit = Number(orders[i]['moneyUnit']);
                if (!limits[type]) {//判断是否有倍数限制
                    typeText = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(type);

                  //加入元角模式判断					
					if(_moneyUnit == 1){
						maxNum = 99999;
						arr.push({
							'gameMethod': type,
							'maxnum': Math.floor(maxNum / multiple)
						});
					}else if(_moneyUnit == 3){
						maxNum = 9999999;
						arr.push({
							'gameMethod': type,
							'maxnum': Math.floor(maxNum * 100/ multiple)
						});
					}else{
						maxNum = 999999;
						arr.push({
							'gameMethod': type,
							'maxnum': Math.floor(maxNum * 10/ multiple)
						});
					}
					

                } else {
                    if (_moneyUnit == 1) {
                        maxNum = Number(limits[type]['maxmultiple']) < 0 ? 99999 : Number(limits[type]['maxmultiple']);
                        //n115 specialText
                        if(type == 'quwei.normal.dingdanshuang'){
                            maxNum =  Games.getCurrentGame().specialMultiple[orders[i]['lotterys'][0]];
                        }

                        arr.push({
                            'gameMethod': type,
                            'maxnum': Math.floor(maxNum / multiple)
                        });
					}else if(_moneyUnit == 3){
						maxNum = Number(limits[type]['maxmultiple']) < 0 ? 9999999 : Number(limits[type]['maxmultiple']);
						 if(type == 'quwei.normal.dingdanshuang'){
                            maxNum =  Games.getCurrentGame().specialMultiple[orders[i]['lotterys'][0]];
                        }
						arr.push({
							'gameMethod': type,
							'maxnum': Math.floor(maxNum * 100/ multiple)
						});
                    } else {
                        maxNum = Number(limits[type]['maxmultiple']) < 0 ? 999999 : Number(limits[type]['maxmultiple']);
                        if(type == 'quwei.normal.dingdanshuang'){
                            maxNum =  Games.getCurrentGame().specialMultiple[orders[i]['lotterys'][0]];
                        }
                        arr.push({
                            'gameMethod': type,
                            'maxnum': Math.floor(maxNum * 10 / multiple)
                        });
                    }

                }
            }
            arr.sort(function (a, b) {
                return a['maxnum'] - b['maxnum'];
            });
            if (arr.length > 0) {
                return arr[0];
            } else {
                return {
                    'gameMethod': '',
                    'maxnum': 100000
                }
            }
        },
        //添加一条投注
        //order 参数可为单一对象或数组
        //接收参数 order {type:'玩法类型',lotterys:'投注具体数据',moneyUnit:'元角模式',num:'注数',multiple:'倍数',onePrice:'单价'}
        add: function (order) {
            var me = this,
                html = '',
                sameIndex = -1,
                tpl = me.defConfig.rowTemplate,
                i = 0,
                j = 0,
                traceIsOpen = Games.getCurrentGameTrace().isOpen(),
                len,
                len2;

            me.fireEvent('beforeAdd', order);

            if (order['lotterys'] && order['lotterys'].length > 0) {

                //判断是否为编辑注单
                if (me.currentSelectId > 0) {
                    order['id'] = me.currentSelectId;
                } else {
                    sameIndex = me.checkData(order);
                    //发现有相同注，则增加倍数
                    if (sameIndex != -1) {
                        me.addMultiple(order['multiple'], sameIndex); //1:修正两条以上投注内容一样时，只叠加第一次倍数, 2:弹窗不影响叠加倍数，全累加

                        Games.getCurrentGameMessage().show({
                            type: 'normal',
                            closeText: '关闭',
                            closeFun: function () {
                                this.hide();
                            },
                            data: {
                                tplData: {
                                    msg: '您选择的号码在号码篮已存在，将直接进行倍数累加'
                                }
                            }
                        });

                        //复位选球区
                        Games.getCurrentGame().getCurrentGameMethod().reSet();

                        Games.getCurrentGameStatistics().reSet(); //禁元角回复

                        me.updateData();
                        me.fireEvent('afterAdd', order);

                        return;
                    }
                    //新增唯一id标识
                    order['id'] = orderID++;
                }

                //如果追号面板被打开，则修改倍数为1倍
                order['multiple'] = !!traceIsOpen ? 1 : order['multiple'];
                order['amountText'] = me.formatMoney(order['num'] * order['moneyUnit'] * order['multiple'] * order['onePrice']);
                //如果追号面板打开，并且正在操作盈利追号或盈利率追号，则不允许进行混投
                //清空所有追号列表
                if (traceIsOpen && (Games.getCurrentGameTrace().getRowTableType() == 'yingli' || Games.getCurrentGameTrace().getRowTableType() == 'yinglilv')) {
                    //不允许混投
                    for (j = 0, len2 = me.orderData.length; j < len2; j++) {
                        if (me.orderData[j]['type'] != order['type'] || me.orderData[j]['moneyUnit'] != order['moneyUnit']) {
                            alert('盈利追号和盈利率追号不允许混投，\n 请确保玩法类型和元角模式一致');
                            return;
                        }
                    }
                }
                //原始选球数据
                order['postParameter'] = Games.getCurrentGame().getCurrentGameMethod().makePostParameter(order['original'], order);
                //倍数备份，用于恢复原始选择的倍数
                order['oldMultiple'] = order['multiple'];
                //
                var rebateSelect = Games.getCurrentGameStatistics().rebateSelect;
                if (rebateSelect) {
                    // order['retPointText'] = $('#J-select-rebate option:selected').text();
                    order['awardMode'] = Number($('#J-select-rebate option:selected').attr('retPointValue'));
                    order['retPointText'] = rebateSelect.getText();
                    order['retPointValue'] = rebateSelect.getValue();
                } else {
					order['awardMode'] = 1;
                    order['retPointText'] = '';
                    order['retPointValue'] = '';
                }

                html = me.formatRow(tpl, me.rebuildData(order));


                //是修改，则替换原有的order对象
                if (me.currentSelectId > 0) {
                    me.replaceOrder(order['id'], order);
                } else {
                    me.addData(order);
                }

            } else {
                var msg = Games.getCurrentGameMessage();

                //判断是否拥有原始球数据
                //因为单式投注初次会篡改ORDER数据结果所以LOTTERYS数据会为空
                //篡改后的数据还会保留初次的原始求数据是为了进行单式去重过滤需求
                if (!order['original'] || order['original'].length <= 0) {
                    msg.show({
                        type: 'mustChoose',
                        msg: '<span class="color-red">&nbsp;号码选择不完整，请重新选择！</span>',
                        data: {
                            tplData: {
                                msg: '<span class="color-red">&nbsp;号码选择不完整，请重新选择！</span>'

                            }
                        }
                    });
                }

                return;
            }


            //如果是修改注单则删除原有的dom
            if (me.currentSelectId > 0) {
                $(html).replaceAll($('#gameorder-' + me.currentSelectId));
                me.cancelSelectOrder();
            } else {
                $(html).prependTo(me.container);
            }

            //复位选球区
            Games.getCurrentGame().getCurrentGameMethod().reSet();

            Games.getCurrentGameStatistics().reSet(); //禁元角回复

            me.updateData();
            me.fireEvent('afterAdd', order);
        },
        //替换某个Order注单对象
        replaceOrder: function (id, newOrder) {
            var me = this,
                orders = me.orderData,
                i = 0,
                len = orders.length;
            for (; i < len; i++) {
                if (orders[i]['id'] == id) {
                    orders[i] = newOrder;
                    return;
                }
            }
        },
        render: function () {
            var me = this,
                orders = me.getTotal()['orders'],
                i = 0,
                len = orders.length,
                html = [],
                tpl = me.defConfig.rowTemplate;
            for (; i < len; i++) {
                html[i] = me.formatRow(tpl, me.rebuildData(orders[i]));
            }
            me.updateData();
            me.container.html(html.join(''));
            //richardgong 2015-12-4
            $('#J-balls-order-container li').each(function () {
                if ($(this).text().match(/超级2000/)) {
                    $(this).addClass('caojiduizhi-balls-order');
                }
                ;
            });
        },
        //填充其他数据用户界面显示
        //格式化后的数据 {typeText:'玩法类型名称',type:'玩法类型名称(英文)',lotterys:'投注具体内容',lotterysText:'显示投注具体内容的文本',moneyUnit:'元角模式',moneyUnitText:'显示圆角模式文字',num:'注数',multiple:'倍数',amount:'总金额',amountText:'显示的总金额',onePrice:'单价'}
        rebuildData: function (order) {
            var me = this,
                cfg = me.defConfig,
                gameConfig = Games.getCurrentGame().getGameConfig().getInstance(),
                typeText = gameConfig.getTitleByName(order['type']);
            order['typeText'] = typeText.join('_');
            order['lotterysText'] = order['postParameter'].length > cfg.lotterysTextLength ? order['postParameter'].substr(0, cfg.lotterysTextLength) + '... <span data-param="action=detail&id=' + order['id'] + '" class="lottery-details">详情</span><div class="lottery-details-area"><div class="num"><span class="multiple"></span><em data-param="action=detailhide" class="close">×</em></div><div class="list"></div></div>' : order['postParameter'];
            order['moneyUnitText'] = Games.getCurrentGameStatistics().getMoneyUnitText(order['moneyUnit']);
            return order;
        },
        formatRow: function (tpl, order) {
            var me = this;
            if (order.isdiamond) {
                order.diamondprice = me.formatMoney(order.multiple * order.num * order.moneyUnit);
                tpl = order.isCheckBox ? me.defConfig.diamondrowTemplatechecked : me.defConfig.diamondrowTemplate;


                var obj = $('[data-type="' + order.type + '"]').data('diamond');
                var str = '';
                for (var key in obj) {
                    str += ('' + key + ':' + obj[key] + ',');
                }
                str = str.substr(0, str.length - 1);
                order.diamondgradexplain = str;
            }

            var o = order,
                p, reg;
            for (p in o) {
                if (o.hasOwnProperty(p)) {
                    reg = RegExp('<#=' + p + '#>', 'g');
                    tpl = tpl.replace(reg, o[p]);
                }
            }


            return tpl;
        },
        //从投注结果返回原始数据
        //用来向后台POST原始结果
        originalData: function (data) {

            var me = this,
                v = [];
            for (var i = 0; i < data.length; i++) {
                for (var j = 0; j < data[i].length; j++) {
                    v[j] = v[j] || [];
                    if (!me.arrIndexOf(data[i][j], v[j])) {
                        v[j].push(data[i][j]);
                    }
                }
            }
            return v;
        },
        //检查数组存在某数
        arrIndexOf: function (value, arr) {
            var r;
            for (var s = 0; s < arr.length; s++) {
                if (arr[s] == value) {
                    r = true;
                }
                ;
            }
            return r || false;
        },
        /**
         * [判断参数是否重复]
         * @return {[type]} [description]
         */
        checkData: function (order) {
            var original, current, name,
                me = this,
                saveArray = [],
                i = 0,
                _index,
                len;
            name = order['type'];
            original = order['original'];
            for (var i = 0; i < original.length; i++) {
                saveArray.push(original[i].join(''));
            }
            ;
            moneyUnit = order['moneyUnit'];
            retPointValue = order['retPointValue'];
            //返回对象在数组的索引值index
            //未找到返回-1
            return me.searchSameResult(name, saveArray.join(), moneyUnit, retPointValue);
        },
        eventProxy: function () {
            var me = this,
                panel = me.container;
            panel.click(function (e) {

                var q = e.target.getAttribute('data-param'),
                    param;
                if (q && $.trim(q) != '') {
                    param = formatParam(q);
                    if ($.isFunction(me['exeEvent_' + param['action']])) {
                        me['exeEvent_' + param['action']].call(me, param, e.target);
                    }
                }

                //richardgong 2016-3-15
                if ($(e.target).hasClass('diamond-tips')) {
                    me.setDiamondPluse($(e.target).parent().find('input'));
                    me.updateData();
                }
            });
        },

        setDiamondPluse: function (element) {
            var me = this;
            var isCheckBox = $(element).is(':checked');
            var param = formatParam($(element).parent().find('a').attr('data-param'));
            var currentOrder = me.getCuOrder(+param.id);
            currentOrder.isCheckBox = isCheckBox;
            if (isCheckBox) {
                if (currentOrder.preonePrice) {

                    currentOrder.onePrice = (+currentOrder.preonePrice + 1).toFixed(2);
                } else {
                    currentOrder.preonePrice = currentOrder.onePrice;
                    currentOrder.onePrice = (+currentOrder.preonePrice + 1).toFixed(2);
                }

            } else {
                if (currentOrder.preonePrice) {
                    currentOrder.onePrice = currentOrder.preonePrice;
                } else {
                    currentOrder.preonePrice = null;
                }

            }
            me.updateData();
        },
        getCuOrder: function (id) {
            var me = this;
            var data = me.orderData;
            for (var i = 0; i < data.length; i++) {
                if (data[i]['id'] == id) {
                    return data[i];
                }
            }
            return {};
        },
        exeEvent_del: function (param) { //新增删除提示
            var me = this,
                id = Number(param['id']);
            if (me.currentSelectId == id) {
                Games.getCurrentGame().getCurrentGameMethod().reSet();
                me.cancelSelectOrder();
            }
            me.removeData(id);
        },
        exeEvent_detailhide: function (params, el) {
            var me = this;
            $(el).parents('.lottery-details-area').eq(0).hide();

            me.fireEvent('afterDetailHide');
        },
        exeEvent_detail: function (param, el) {
            var me = this,
                el = $(el),
                index = Number(param['id']),
                id = index,
                dom = el.next(),
                multipleArea = dom.find('.multiple'),
                result = dom.find('.list'),
                currentData = me.getTotal().orders,
                html = '';


            //隐藏之前打开的内容容器
            //避免遍历
            if (me.cacheData['currentDetailId']) {
                $('#gameorder-' + me.cacheData['currentDetailId'] + ' .lottery-details-area').hide();
            }
            //不缓存结果及注数
            for (var i = currentData.length - 1; i >= 0; i--) {
                if (currentData[i]['id'] == index) {
                    currentData = currentData[i];
                    break;
                }
            }
            //填充结果
            multipleArea.text('共 ' + currentData.num + ' 注');
            html = currentData['postParameter'];
            //缓存面板
            me.cacheData['currentDetailId'] = id;
            //位置调整
            dom.css({
                left: dom.position().left + dom.width() + 5
            });

            //渲染DOM
            result.html(html);
            //显示结果
            dom.show();

            me.fireEvent('afterDetailShow');
        },
        //号码篮点击事件
        exeEvent_reselect: function (param) {
            var me = this,
                id = Number(param['id']);
            me.selectOrderById(id);

        },
        //界面状态更新
        updateDomStatus: function () {
            var me = this,
                className = 'important',
                id = me.currentSelectId,
                addOrderButtonDom = $(me.defConfig.addOrderDom);

            if (id > 0) {
                //设置添加投注按钮样式
                addOrderButtonDom.addClass(className);
            } else {
                addOrderButtonDom.removeClass(className);
            }
        },
        //选择一个注单
        selectOrderById: function (id) {
            var me = this,
                order = me.getOrderById(id),
                original = order['original'],
                type = order['type'],
                cls = me.defConfig.selectedClass,
                dom = $('#gameorder-' + id);

            //单式不能反选
            if (me.getOrderById(id)['type'].indexOf('danshi') != -1) {
                return;
            }

            //修改选中样式
            dom.parent().children().removeClass(cls);
            dom.addClass(cls);

            //反选球
            //切换玩法面板
            Games.getCurrentGameTypes().changeMode(type);

            //设置倍数、元角模式
            Games.getCurrentGameStatistics().getMoneyUnitDom().setValue(order['moneyUnit']);
            Games.getCurrentGameStatistics().getMultipleDom().setValue(order['multiple']);

            var nowGameType = Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"];
            if ($.trim(nowGameType) != 'sl115') {
                //设置奖金返点模式
                Games.getCurrentGameStatistics().rebateSelect.setValue(order['retPointValue']);
            }

            //反选球
            Games.getCurrentGame().getCurrentGameMethod().reSelect(original);

            //标记当前选中注单
            me.currentSelectId = id;

            //更新界面
            me.updateDomStatus();

            //反选后将滚动条位置移动到合适位置
            //$(window).scrollTop($('#J-play-select').offset()['top']);
        },
        //取消选择的注单
        cancelSelectOrder: function () {
            var me = this,
                id = me.currentSelectId,
                addOrderButtonDom = $(me.defConfig.addOrderDom);

            if (id > 0) {
                $('#gameorder-' + id).removeClass(me.defConfig.selectedClass);
                me.currentSelectId = 0;
                //更新界面
                me.updateDomStatus();

                Games.getCurrentGame().getCurrentGameMethod().reSet();
            }
        },
        //将数字保留两位小数并且千位使用逗号分隔
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
        /**
         * 查询同类玩法重复结果
         * @param  {string} name [游戏玩法 例:wuxing.zhixuan.danshi]
         * @param  {string} data [投注号码 例:12345]
         */
        searchSameResult: function (name, lotteryText, moneyUnit, retPointValue) {
            var me = this,
                current, dataNum,
                i = 0,
                saveArray = [],
                data = me.getTotal().orders;
            for (; i < data.length; i++) {
                saveArray = [];
                current = data[i];
                ordersLotteryText = current['original'];
                for (var k = 0; k < ordersLotteryText.length; k++) {
                    saveArray.push(ordersLotteryText[k].join(''));
                }
                ;
                if (current.type == name && lotteryText == saveArray.join() && current.moneyUnit == moneyUnit && current.retPointValue == retPointValue) {
                    return i;
                }
            }
            return -1;
        },
        //增加某注倍数
        addMultiple: function (num, index) {
            var me = this,
                orders = me.getTotal()['orders'],
                limits, order = orders[index],
                type = order['type'],
                maxNum = 999999;
            if (Games.getCurrentGameTrace().isOpen()) {
                return;
            }
            limits = Games.getCurrentGame().getDynamicConfig()['gamelimit'];
            if (limits[type]) {
                maxNum =order['moneyUnit']==1?limits[type]['maxmultiple']:(order['moneyUnit']==3?limits[type]['maxmultiple']*100:limits[type]['maxmultiple']*10);
				maxNum = maxNum < 0 ? 99999 : maxNum;
            }
            if ((order['multiple'] + num) > maxNum) {
                setTimeout(function () {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            console.log(Games);
                            orders[index]['multiple'] = maxNum
                            orders[index]['oldMultiple'] = orders[index]['multiple'];
                            orders[index]['amount'] = orders[index]['num'] * orders[index]['moneyUnit'] * orders[index]['multiple'] * orders[index]['onePrice'];
                            orders[index]['amountText'] = me.formatMoney(orders[index]['num'] * orders[index]['moneyUnit'] * orders[index]['multiple'] * orders[index]['onePrice']);
                            me.render();

                            //复位选球区
                            Games.getCurrentGame().getCurrentGameMethod().reSet();
                            //游戏错误提示
                            //主要用于单式投注进行错误提示
                            Games.getCurrentGame().getCurrentGameMethod().ballsErrorTip();
                            Games.getCurrentGameStatistics().reSet();

                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '该组号码倍数已经超过最大限制(' + maxNum + '倍)，将调整为系统支持的最大倍数进行添加'
                            }
                        }
                    });
                }, 100);
                return;
            }


            orders[index]['multiple'] += num;
            orders[index]['oldMultiple'] = orders[index]['multiple'];
            orders[index]['amount'] = orders[index]['num'] * orders[index]['moneyUnit'] * orders[index]['multiple'] * orders[index]['onePrice'];
            orders[index]['amountText'] = me.formatMoney(orders[index]['num'] * orders[index]['moneyUnit'] * orders[index]['multiple'] * orders[index]['onePrice']);
            me.render();

            //复位选球区
            Games.getCurrentGame().getCurrentGameMethod().reSet();
            //游戏错误提示
            //主要用于单式投注进行错误提示
            Games.getCurrentGame().getCurrentGameMethod().ballsErrorTip();
            //Games.getCurrentGameStatistics().reSet();

            me.cancelSelectOrder();
        },
        //修改所有投注倍数
        editMultiples: function (num) {
            var me = this,
                orders = me.getTotal()['orders'],
                i = 0,
                len = orders.length;
            for (; i < len; i++) {
                orders[i]['multiple'] = num;
                orders[i]['amount'] = orders[i]['num'] * orders[i]['moneyUnit'] * orders[i]['multiple'] * orders[i]['onePrice'];
                orders[i]['amountText'] = me.formatMoney(orders[i]['amount']);
            }
            me.render();

            me.cancelSelectOrder();
        },
        //修改单注投注倍数
        editMultiple: function (num, index) {
            var me = this,
                orders = me.getTotal()['orders'];
            orders[index]['multiple'] = num;
            orders[index]['amount'] = orders[index]['num'] * orders[index]['moneyUnit'] * orders[index]['multiple'] * (orders[index]['preonePrice'] || orders[index]['onePrice']);
            orders[index]['amountText'] = me.formatMoney(orders[index]['amount']);
            me.render();

            me.cancelSelectOrder();
        },
        //恢复原来的投注的倍数
        restoreMultiples: function () {
            var me = this,
                orders = me.getTotal()['orders'],
                i = 0,
                len = orders.length;
            for (; i < len; i++) {
                orders[i]['multiple'] = orders[i]['oldMultiple'];
                orders[i]['amount'] = orders[i]['num'] * orders[i]['moneyUnit'] * orders[i]['multiple'] * orders[i]['onePrice'];
                orders[i]['amountText'] = me.formatMoney(orders[i]['amount']);
            }
            me.render();

            me.cancelSelectOrder();
        }
    };

    var Main = host.Class(pros, Event);
    Main.defConfig = defConfig;
    Main.getInstance = function (cfg) {
        return instance || (instance = new Main(cfg));
    };
    host[name] = Main;

})(phoenix, "GameOrder", phoenix.Event);
//追号区域
(function (host, name, Event, undefined) {
    //console.log("ggbbbb");
    var defConfig = {
            //主面板dom
            mainPanel: '#J-trace-panel',
            //高级追号类型(与tab顺序对应)
            advancedTypeHas: ['fanbei', 'yingli', 'yinglilv'],
            //追号数据表头
            dataRowHeader: '<tr><th style="width:125px;" class="text-center">序号</th><th><input data-action="checkedAll" type="checkbox"  checked="checked"/> 追号期次</th><th>倍数</th><th>金额</th><th>预计开奖时间</th></tr>',
            //追号数据列表模板
            dataRowTemplate: '<tr><td class="text-center"><#=No#></td><td><input data-action="checkedRow" class="trace-row-checked" type="checkbox" checked="checked"> <span class="trace-row-number" issueCode=<#=issueCode#>><#=traceNumber#></span></td><td><input class="trace-row-multiple" value="<#=multiple#>" type="text" style="width:50px;text-align:center;"> 倍</td><td><span class="price"><dfn>&yen;</dfn><span class="trace-row-money"><#=money#></span> 元</span> </td><td><span class="trace-row-time"><#=publishTime#></span></td></tr>',
            //高级追号盈利金额追号/盈利率追号表模板
            dataRowYingliHeader: '<tr><th class="text-center">序号</th><th><input data-action="checkedAll" type="checkbox" /> 追号期次</th><th>倍数</th><th>金额</th><th>奖金</th><th>预期盈利金额</th><th>预期盈利率</th></tr>',
            //高级盈利/盈利率追号数据列表模板
            dataRowYingliTemplate: '<tr><td class="text-center"><#=No#></td><td><input data-action="checkedRow" class="trace-row-checked" type="checkbox" checked="checked"> <span class="trace-row-number" issueCode=<#=issueCode#>><#=traceNumber#></span></td><td><input class="trace-row-multiple" value="<#=multiple#>" type="text" style="width:50px;text-align:center;"> 倍</td><td><span class="price"><dfn>&yen;</dfn><span class="trace-row-money"><#=money#></span> 元</span></td><td><span class="price"><dfn>&yen;</dfn> <span class="trace-row-userGroupMoney"><#=userGroupMoney#></span> </span>元</td><td><span class="price"><dfn>&yen;</dfn> <span class="trace-row-winTotalAmount"><#=winTotalAmout#></span> 元</span></td><td><span class="trace-row-yinglilv"><#=yinglilv#></span>%</td></tr>'
        },
        instance,
        Games = host.Games;

    //只允许输入正整数
    //v 值
    //def 默认值
    //mx 最大值
    var checkInputNum = function (v, def, mx) {
        var v = '' + v,
            mx = mx || 1000000000;
        v = v.replace(/[^\d]/g, '');
        v = v == '' ? def : (Number(v) > mx ? mx : v);
        return Number(v);
    };

    //只允许输入正整数
    var checkInputNumber = function (v) {
        v = v.replace(/[^\d]/g, '');
        return Number(v);
    };


    var pros = {
        init: function (cfg) {
            var me = this;
            Games.setCurrentGameTrace(me);
            me.panel = $(cfg.mainPanel);

            //追号tab
            me.TraceTab = null;
            //高级追号tab
            me.TraceAdvancedTab = null;

            //追号面板是否开启
            me.isOpenPanel = false;

            //订单数据
            me.orderData = null;

            //公共属性部分
            //追号类型，普通追号 高级追号
            me.traceType = 'normal';
            //追号期数
            me.times = 0;
            //追号起始期号
            me.traceStartNumber = '';
            //当前期号
            me.currentTraceNumber = '';


            //普通追号属性


            //高级追号属性
            //高级追号类型
            me.advancedType = cfg.advancedTypeHas[0];
            me.typeTypeType = 'a';


            me.initEvent();
            me.setCurrentTraceNumber();


            //配置更新后追号面板相关更新
            //重新构建期号选择列表
            Games.getCurrentGame().addEvent('changeDynamicConfig', function () {
                me.buildStartNumberSelectDom();
                me.updateTableNumber();
            });


        },
        setAdvancedType: function (i) {
            if (Object.prototype.toString.call(i) == '[object Number]') {
                this.advancedType = this.getAdvancedTypeBuIndex(i);
            } else {
                this.advancedType = i;
            }
        },
        getAdvancedType: function () {
            return this.advancedType;
        },
        getAdvancedTypeBuIndex: function (i) {
            var me = this,
                has = me.defConfig.advancedTypeHas,
                len = has.length;
            if (i < len) {
                return has[i];
            }
            return '';
        },
        initEvent: function () {
            var me = this;
            var userName = $("#userName");

            var traceTips = this.traceTips = new phoenix.Tip({
                cls: 'j-ui-tip-close j-ui-tip-yellow j-ui-tip-t',
                text: '<h4><i class="close">ⅹ</i>温馨提示</h4><p>平台功能小调整，默认勾选中奖后停止追号</p>',
            });
            $('.j-ui-tip-close .close').click(function () {
                var d = new Date()
                d.setFullYear(d.getFullYear() + 1);
                jQuery.cookie(userName + 'data', 1, {expires: d})
                traceTips.hide();
            })

            //追号显示隐藏
            $('#J-trace-switch').click(function () {

                if (this.checked) {

                    var orderAmount = Games.getCurrentGameOrder().getTotal()['amount'],
                        msg = Games.getCurrentGameMessage();

                    if (orderAmount <= 0) {
                        msg.show({
                            type: 'mustChoose',
                            msg: '请至少选择一注投注号码！',
                            data: {
                                tplData: {
                                    msg: '请至少选择一注投注号码！'
                                }
                            }
                        });
                        $('#J-trace-switch').get(0).checked = false;
                        return;
                    } else {
                        Games.getCurrentGameTrace().show();
                        $('#J-trace-iswintimesstop').get(0).checked = true;
                        var traceIswintimesstopPanel = $('#J-trace-iswintimesstop-panel');
                        if (!jQuery.cookie(userName + 'data')) {
                            traceTips.show(-100, 40, traceIswintimesstopPanel);
                        }
                    }
                    //2016-2-24 richardgong
                    var tracbtab = $('.chase-tab-t').eq(1);
                    var ct = $('.chase-tab-content').eq(1);
                    if (/超级2000/.test($('#J-balls-order-container').text())) {
                        ct.removeClass('.chase-tab-content-current')
                        tracbtab.hide();
                    } else {
                        tracbtab.show();
                    }


                } else {
                    Games.getCurrentGameTrace().hide();
                    traceTips.hide();
                }
            });


            //追号tab
            me.TraceTab = TraceTab = new host.Tab({
                par: '#J-trace-panel',
                triggers: '.chase-tab-t',
                panels: '.chase-tab-content',
                currPanelClass: 'chase-tab-content-current',
                eventType: 'click'
            });
            TraceTab.addEvent('afterSwitch', function (e, i) {
                var types = ['normal', 'advanced'];
                if (i < types.length) {
                    me.setTraceType(types[i]);
                }
                me.updateStatistics();
            });
            //高级追号tab
            me.TraceAdvancedTab = TraceAdvancedTab = new host.Tab({
                par: '#J-trace-advanced-type-panel',
                triggers: '.tab-title li',
                panels: '.tab-content li',
                eventType: 'click'
            });
            TraceAdvancedTab.addEvent('afterSwitch', function (e, i) {
                var ipts = this.getPanel(i).find('.trace-advanced-type-switch');
                me.setAdvancedType(i);
                ipts.each(function () {
                    if (this.checked) {
                        me.setTypeTypeType($(this).parent().attr('data-type'));
                        return false;
                    }
                });
            });


            //追中即停说明提示
            var TraceTip1 = new host.Hover({
                triggers: '#J-trace-iswintimesstop-hover',
                panels: '#chase-stop-tip-1',
                currPanelClass: 'chase-stop-tip-current',
                hoverDelayOut: 300
            });
            $('#chase-stop-tip-1').mouseleave(function () {
                TraceTip1.hide();
            });
            var TraceTip2 = new host.Hover({
                triggers: '#J-trace-iswinstop-hover',
                panels: '#chase-stop-tip-2',
                currPanelClass: 'chase-stop-tip-current',
                hoverDelayOut: 300
            });
            $('#chase-stop-tip-2').mouseleave(function () {
                TraceTip2.hide();
            });
            $('#J-chase-stop-switch-1').click(function (e) {
                e.preventDefault();
                $('#J-trace-iswintimesstop-panel').hide();
                $('#J-trace-iswinstop-panel').show();
                $('#J-trace-iswintimesstop').get(0).checked = false;
                $('#J-trace-iswinstop').get(0).checked = true;
                $('#J-trace-iswinstop-money').removeAttr('disabled');
                $('#J-trace-iswintimesstop-times').attr('disabled', 'disabled');

            });
            $('#J-chase-stop-switch-2').click(function (e) {
                e.preventDefault();
                $('#J-trace-iswinstop-panel').hide();
                $('#J-trace-iswintimesstop-panel').show();
                $('#J-trace-iswintimesstop').get(0).checked = true;
                $('#J-trace-iswinstop').get(0).checked = false;
                $('#J-trace-iswinstop-money').attr('disabled', 'disabled');
                $('#J-trace-iswintimesstop-times').removeAttr('disabled');
            });
            $('#J-trace-iswinstop-money').keyup(function () {
                this.value = checkInputNum(this.value, 1, 999999);
            });
            $('#J-trace-iswintimesstop-times').keyup(function () {
                this.value = checkInputNum(this.value, 1, 999999);
            });

            //是否止盈追号(按中奖次数)
            $('#J-trace-iswintimesstop').click(function () {
                var ipt = $('#J-trace-iswintimesstop-times');
                if (this.checked) {
                    ipt.attr('disabled', false).focus();
                } else {
                    ipt.attr('disabled', 'disabled');
                }
            });
            //是否止盈追号(按中奖金额)
            $('#J-trace-iswinstop').click(function () {
                var ipt = $('#J-trace-iswinstop-money');
                if (this.checked) {
                    ipt.attr('disabled', false).focus();
                } else {
                    ipt.attr('disabled', 'disabled');
                }
            });

            //普通追号事件
            //普通追号Input输入事件
            $('#J-trace-normal-times').keyup(function () {
                var maxnum = Games.getCurrentGame().getDynamicConfig()['tracemaxtimes'],
                    v = '' + this.value,
                    num,
                    list = $('#J-function-select-tab').find('.function-select-title li'),
                    cls = 'current';
                v = v.replace(/[^\d]/g, '');
                v = v == '' ? 1 : (Number(v) > maxnum ? maxnum : v);
                this.value = v;
                num = Number(v);
                //修改追号期数选项样式
                if (num > 0 && num <= 20 && (num % 5 == 0)) {
                    list.removeClass(cls).eq(num / 5 - 1).addClass(cls);
                    NormalSelectTimesTab.index = num / 5 - 1; //重新定位

                } else {
                    list.removeClass(cls);
                    NormalSelectTimesTab.index = 22;
                }

                me.buildDetail();
            });
            //$('#J-trace-normal-times').blur(function(){
            //				me.buildDetail();
            //			});
            //期数选择操作
            var NormalSelectTimesTab = new host.Tab({
                par: '#J-function-select-tab',
                triggers: '.function-select-title li',
                panels: '.function-select-panel li',
                eventType: 'click',
                index: 1
            });
            NormalSelectTimesTab.addEvent('afterSwitch', function (e, i) {
                var tab = this,
                    num = parseInt(tab.getTrigger(i).text());
                $('#J-trace-normal-times').val(num);
                me.buildDetail();
            });

            //倍数模拟下拉框
            me.normalSelectMultiple = new host.Select({
                realDom: '#J-trace-normal-multiple',
                isInput: true,
                expands: {
                    inputEvent: function () {
                        var me = this;
                        me.getInput().keyup(function (e) {
                            var v = this.value,
                                maxnum = 99999;
                            this.value = this.value.replace(/[^\d]/g, '');
                            v = Number(this.value);
                            if (v < 1) {
                                this.value = 1;
                            }
                            if (v > maxnum) {
                                this.value = maxnum;
                            }
                            me.setValue(this.value);
                        });
                    }
                }
            });
            me.normalSelectMultiple.addEvent('change', function (e, value, text) {

                var amount = me.getOrderData()['amount'],
                    num = Number(value),
                    maxObj = Games.getCurrentGameOrder().getOrderMaxMultiple(),
                    maxnum = maxObj['maxnum'],
                    Msg = Games.getCurrentGameMessage(),
                    typeTitle = '';

                if (num > maxnum) {
                    typeTitle = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(maxObj['gameMethod']).join('-');
                    Msg.show({
                        confirmIsShow: true,
                        mask: true,
                        msg: '',
                        tpl: '<div class="pop-waring"><i class="ico-waring &lt;#=icon-class#&gt;"></i><h4 class="pop-text"><#=msg#></h4></div>',
                        data: {
                            tplData: {
                                msg: '您输入的倍数超过了<b>[' + typeTitle + ']</b> 玩法的最高倍数限制，系统将自动修改为最大可输入倍数'
                            }
                        },
                        confirmFun: function () {
                            value = maxnum;
                            me.normalSelectMultiple.setValue(value);
                            Msg.hide();

                            me.getTable().find('.trace-row-multiple').val(value);
                            me.getTable().find('.trace-row-money').each(function () {
                                var el = $(this),
                                    multiple = Number(el.parent().parent().find('.trace-row-multiple').val());
                                el.html(me.formatMoney(amount * Number(value)));
                            });
                            me.updateStatistics();


                        }
                    });
                } else {
                    me.getTable().find('.trace-row-multiple').val(value);
                    me.getTable().find('.trace-row-money').each(function () {
                        var el = $(this),
                            multiple = Number(el.parent().parent().find('.trace-row-multiple').val());
                        el.html(me.formatMoney(amount * Number(value)));
                    });
                    me.updateStatistics();
                }


            });
            //数据行限制输入正整数,(可清空,失焦自动填充一倍.首字符不能为0,单选框没选中禁用，选中初始1倍值)
            //数据行限制输入正整数
            me.panel.find('.chase-table').keyup(function (e) {
                var el = $(e.target),
                    amount = me.getOrderData()['amount'];
                if (el.hasClass('trace-row-multiple')) { //处理当删除注数时，追号倍数不限制

                    var multiple = Number(el.val()),
                        tableType = me.getRowTableType(),
                        maxnum = Number(Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum']);
                    if (multiple == 0) {
                        el.val(el.val().replace(/^0/g, '')); //去除填写0场景
                        el.parent().parent().find('.trace-row-money').html('0.00');
                    } else if (multiple > maxnum) {
                        multiple = maxnum;
                        el.val(multiple);
                        el.parent().parent().find('.trace-row-money').html(me.formatMoney(amount * multiple));
                    } else {

                        el.parent().parent().find('.trace-row-money').html(me.formatMoney(amount * multiple));
                        //如果是盈利追号和盈利率追号，则需要重新计算盈利金额和盈利率
                        if (tableType == 'trace_advanced_yingli_a' || tableType == 'trace_advanced_yingli_b' || tableType == 'trace_advanced_yinglilv_a' || tableType == 'trace_advanced_yinglilv_b') {
                            me.rebuildYinglilvRows();
                        }
                    }
                    me.updateStatistics();
                }
            }).on('blur', '.trace-row-multiple', function (e) {
                var el = $(e.target),
                    multiple = Number(el.val()),
                    amount = me.getOrderData()['amount'];
                el.val(checkInputNum(el.val(), 1, Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum']));
                el.parent().parent().find('.trace-row-money').html(me.formatMoney(amount * Number(el.val())));
                me.updateStatistics();
            });


            //高级追号事件
            //创建期号列表
            setTimeout(function () {
                var nowGameType = Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"];
                if ($.trim(nowGameType) != 'slmmc' && $.trim(nowGameType) != 'sl115') { //秒秒彩无此结构，不能进入
                    me.buildStartNumberSelectDom();
                }

            }, 10);


            //追号期数
            $('#J-trace-advanced-times').keyup(function () {
                this.value = checkInputNum(this.value, 10, Number($('#J-trace-number-max').text()));
            });

            //起始倍数
            $('#J-trace-advance-multiple').keyup(function (e) {
                var el = $(e.target),
                    multiple = Number(checkInputNumber(el.val())),
                    maxnum = Number(Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum']);
                if (multiple == 0) {
                    el.val(el.val().replace(/^0/g, ''));
                } else if (multiple > maxnum) {
                    el.val(maxnum);
                } else {
                    el.val(multiple);
                }

            }).blur(function () { //失去焦点纠正为1倍
                this.value = checkInputNum(this.value, 1, Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum']);
            });

            //高级追号填写参数切换
            me.panel.find('.trace-advanced-type-switch').click(function () {
                var el = $(this),
                    par = el.parent(),
                    pars = par.parent().children(),
                    _par;
                pars.each(function (i) {
                    _par = pars.get(i);
                    if (par.get(0) != _par) {
                        //alert($(_par).html());
                        $(_par).find('input[type="text"]').attr('disabled', 'disabled');
                    } else {
                        $(_par).find('input[type="text"]').attr('disabled', false).eq(0).focus();
                        me.setTypeTypeType(par.attr('data-type'));
                    }

                    if (el.parent().hasClass('trace-input-multiple')) {
                        this.value = checkInputNum(this.value, 1, Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum']);
                    } else {
                        this.value = checkInputNum(this.value, 1, 99999);
                    }

                });
            });
            //高级追号区域输入事件
            $('#J-trace-advanced-type-panel').on('keyup', 'input[type=text]', function (e) {
                var dom = $(e.target);
                //如果是倍数输入框
                if (dom.hasClass('trace-input-multiple')) {
                    this.value = checkInputNum(this.value, 1, Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum']);
                } else {
                    this.value = checkInputNum(this.value, 1, 99999);
                }

            });


            //生成追号计划事件
            $('#J-trace-builddetail').click(function () {
                me.confirmSetting();
            });

            //数据行选择行生效/失效事件
            me.panel.find('.chase-table').click(function (e) {
                var el = $(e.target),
                    action = $.trim(el.attr('data-action')),
                    isChecked = true,
                    tableType;
                if (!!action && action != '') {
                    switch (action) {
                        case 'checkedAll':
                            isChecked = !!el.get(0).checked ? true : false;
                            tableType = me.getRowTableType();
                            me.getTable().find('.trace-row-checked').each(function () {
                                this.checked = isChecked;
                            });
                            //如果是盈利追号和盈利率追号，则需要重新计算盈利金额和盈利率
                            if (tableType == 'trace_advanced_yingli_a' || tableType == 'trace_advanced_yingli_b' || tableType == 'trace_advanced_yinglilv_a' || tableType == 'trace_advanced_yinglilv_b') {
                                me.rebuildYinglilvRows();
                            }
                            me.updateStatistics();
                            break;
                        case 'checkedRow':
                            if (el.size() > 0) {
                                tableType = me.getRowTableType();
                                //如果是盈利追号和盈利率追号，则需要重新计算盈利金额和盈利率
                                if (tableType == 'trace_advanced_yingli_a' || tableType == 'trace_advanced_yingli_b' || tableType == 'trace_advanced_yinglilv_a' || tableType == 'trace_advanced_yinglilv_b') {
                                    me.rebuildYinglilvRows();
                                }
                                me.updateStatistics();
                            }
                            break;
                        default:
                            break;
                    }
                }
            });

        },
        //创建期号列表slect元素
        buildStartNumberSelectDom: function () {
            var me = this,
                list = Games.getCurrentGame().getDynamicConfig()['gamenumbers'],
                len = list.length,
                i = 0,
                strArr = [],
                currentNumber = Games.getCurrentGame().getCurrentNumber(),
                currStr = '(当前期)',
                curr = currStr,
                oldValue,
                checkedStr = '';
            if (me.traceStartNumberSelect) {
                oldValue = me.traceStartNumberSelect.getValue();
            }

            for (; i < len; i++) {
                curr = currentNumber == list[i]['number'] ? currStr : '';
                checkedStr = (!!me.traceStartNumberSelect && (list[i]['number'] == oldValue)) ? ' selected="selected" ' : '';
                strArr.push('<option value="' + list[i]['number'] + '" ' + checkedStr + ' >' + list[i]['number'] + curr + '</option>');
            }
            $('#J-traceStartNumber').html(strArr.join(''));
            $('#J-trace-number-max').text(len);

            //起始号选择
            if (me.traceStartNumberSelect) {
                me.traceStartNumberSelect.dom.remove();
            }
            me.traceStartNumberSelect = new host.Select({
                realDom: '#J-traceStartNumber',
                cls: 'chase-trace-startNumber-select'
            });
            me.traceStartNumberSelect.addEvent('change', function (e, value, text) {
                me.setTraceStartNumber(value);
            });
        },
        //更新表格期号
        updateTableNumber: function () {
            var me = this,
                list = Games.getCurrentGame().getDynamicConfig()['gamenumbers'],
                len = list.length,
                trs1, trs2,
                currNumber = Games.getCurrentGame().getCurrentNumber(),
                startNumber,
                dom,
                numberDom,
                dateDom,
                currText = '',
                index,
                traceLastNumber = '', //上期号
            //当前期
                currStr = '<span class="icon-period-current"></span>',
                traceNo,
                dateNotieNumber = 0,
                dateNotieNumber2 = 0,
                dateNotie,
                isGameTypes = Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"];

            //当当前期号发生更变时
            if (len > 0) {
                trs1 = me.getNormalRowTable().find('tr');
                trs2 = me.getAdvancedRowTable().find('tr');

                index = me.getStartNumberIndexByNumber(startNumber);
                trs1.each(function (i) {
                    if (i == 0) { //当当前(普通)期号发生更变时,跳出表头不放在trs1对象中循环
                        return true;
                    }
                    dom = $(this);
                    numberDom = dom.find('.trace-row-number'); //当前开奖期号
                    issuecodeNumber = dom.find('.trace-row-number').attr('issuecode'), //当前开奖期编号
                        dateDom = dom.find('.trace-row-time');
                    multipleDom = dom.find('.trace-row-multiple');
                    startNumber = numberDom.text().replace(/[^\d]/g, '');
                    traceNo = dom.find('.text-center'); //序号
                    dom.find('.trace-row-multiple').removeAttr('disabled'); //被禁用倍数文本框开启，倍数1

                    if ((index + 1) < len) {
                        currText = list[index + 1]['number'] == currNumber ? currStr : '';
                        numberDom.html(list[index + 1]['number'] + currText);
                        multipleDom.val('1');
                        dateDom.text(list[index + 1]['time']);
                        traceNo.html('').html(i);
                        if (traceLastNumber != issuecodeNumber.substr(0, 8) && traceLastNumber != "" && (isGameTypes != 'fc3d' && isGameTypes != 'p5' && isGameTypes != 'ssq')) { //增加隔天期标识

                            switch (dateNotieNumber) {
                                case 0:
                                    dateNotie = '明天 ';
                                    break;
                                case 1:
                                    dateNotie = '后天 ';
                                    break;
                                case 2:
                                    dateNotie = '大后天 ';
                                    break;
                                default:
                                    dateNotie = '';
                                    break;
                            }
                            dateNotieNumber++;
                            if (jQuery.trim(dateNotie) != '') {
                                traceNo.html('').append('<div class="icon-chase-mark">' + dateNotie + ' ' + dom.find('.trace-row-number').text().substr(0, 8) + '</div>');
                            }
                        }
                        traceLastNumber = issuecodeNumber.substr(0, 8);
                        index++;
                    }

                });

                index = me.getStartNumberIndexByNumber(startNumber);
                trs2.each(function (i) {
                    if (i == 0) { //去除表头（高级）
                        return true;
                    }
                    dom = $(this);
                    numberDom = dom.find('.trace-row-number');
                    issuecodeNumber = dom.find('.trace-row-number').attr('issuecode'), //当前开奖期编号
                        dateDom = dom.find('.trace-row-time');
                    multipleDom = dom.find('.trace-row-multiple');
                    startNumber = numberDom.text().replace(/[^\d]/g, '');
                    traceNo = dom.find('.text-center'); //序号
                    dom.find('.trace-row-multiple').removeAttr('disabled'); //被禁用倍数文本框开启，倍数1

                    if ((index + 1) < len) {
                        currText = list[index + 1]['number'] == currNumber ? currStr : '';
                        numberDom.html(list[index + 1]['number'] + currText);
                        multipleDom.val('1');
                        dateDom.text(list[index + 1]['time']);
                        traceNo.html('').html(i);
                        if (traceLastNumber != issuecodeNumber.substr(0, 8) && traceLastNumber != "" && (isGameTypes != 'fc3d' && isGameTypes != 'p5' && isGameTypes != 'ssq')) { //增加隔天期标识

                            switch (dateNotieNumber2) {
                                case 0:
                                    dateNotie = '明天 ';
                                    break;
                                case 1:
                                    dateNotie = '后天 ';
                                    break;
                                case 2:
                                    dateNotie = '大后天 ';
                                    break;
                                default:
                                    dateNotie = '';
                                    break;

                            }
                            dateNotieNumber2++;
                            if (jQuery.trim(dateNotie) != '') {
                                traceNo.html('').append('<div class="icon-chase-mark">' + dateNotie + ' ' + dom.find('.trace-row-number').text().substr(0, 8) + '</div>');
                            }
                        }
                        traceLastNumber = issuecodeNumber.substr(0, 8);
                        index++;
                    }
                });

            }

        },
        //重新计算盈利金额和盈利率表格数据
        rebuildYinglilvRows: function () {
            var me = this,
                trs = me.getRowTable().find('tr'),
                orderData = me.getOrderData(),
            //单注预计中奖金额
                orderUserGroupMoney = me.getWinMoney(),

                rowDom = null,
                checkboxDom = null,
                multipleDom = null,
                multiple = 1,
                amountDom = null,
                amount = 0,
                userGroupMoneyDom = null,
                winMoneyDom = null,
                yinglilvDom = null,
                yinglilv = -1;

            //累计投注成本
            costAmount = 0;

            //console.log('rebuild');

            trs.each(function (i) {
                //第一行为表头
                if (i > 0) {
                    rowDom = $(this);
                    checkboxDom = rowDom.find('.trace-row-checked');
                    //当该行处于选中状态
                    if (checkboxDom.size() > 0 && checkboxDom.get(0).checked) {
                        multipleDom = rowDom.find('.trace-row-multiple');
                        multiple = Number(multipleDom.val());
                        amountDom = rowDom.find('.trace-row-money');
                        amount = Number(amountDom.text().replace(',', ''));
                        userGroupMoneyDom = rowDom.find('.trace-row-userGroupMoney');
                        winMoneyDom = rowDom.find('.trace-row-winTotalAmount');
                        yinglilvDom = rowDom.find('.trace-row-yinglilv');

                        costAmount += orderData['amount'] * multiple;

                        amountDom.text(me.formatMoney(orderData['amount'] * multiple));
                        userGroupMoneyDom.text(me.formatMoney(orderUserGroupMoney * multiple));
                        winMoneyDom.text(me.formatMoney(orderUserGroupMoney * multiple - costAmount));
                        yinglilv = (orderUserGroupMoney * multiple - costAmount) / costAmount * 100;
                        yinglilvDom.text(Number(yinglilv).toFixed(2));

                    }
                }
            });

        },
        isOpen: function () {
            return this.isOpenPanel;
        },
        setTypeTypeType: function (v) {
            this.typeTypeType = v;
        },
        getTypeTypeType: function () {
            return this.typeTypeType;
        },
        getIsWinStop: function () {
            var me = this,
                stopDom1 = $('#J-trace-iswintimesstop'),
                stopDom2 = $('#J-trace-iswinstop');
            if (stopDom1.get(0).checked) {
                return 1;
            }
            if (stopDom2.get(0).checked) {
                return 2;
            }
            return 0;
        },
        getTraceWinStopValue: function () {
            var me = this,
                isWinStop = me.getIsWinStop();
            if (isWinStop == 1) {
                return Number($('#J-trace-iswintimesstop-times').val());
            }
            if (isWinStop == 2) {
                return Number($('#J-trace-iswinstop-money').val());
            }
            return -1;
        },
        updateStatistics: function () {
            var me = this,
                data = me.getResultData();
            $('#J-trace-statistics-times').html(data['times']);
            $('#J-trace-statistics-lotterys-num').html(data['lotterysNum']);
            $('#J-trace-statistics-amount').html(me.formatMoney(data['amount']));
        },
        getResultData: function () {
            var me = this,
                orderData = me.getOrderData(),
                trs = me.getRowTable().find('tr'),
                rowDom, checkedDom,
                times = 0,
                lotterysNum = 0,
                amount = 0,
                traceData = [],
                par,
                result = {
                    'times': 0,
                    'lotterysNum': 0,
                    'amount': 0,
                    'orderData': orderData,
                    'traceData': [],
                    'traceType': me.getTraceType()
                },
                traceLastNumber = '', //上期号
                list = Games.getCurrentGame().getDynamicConfig()['gamenumbers'],
                issueCode,
                index,
                dateNotieNumber = 0,
                dateNotie,
                isGameTypes = Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"];

            trs.each(function (i) {

                if (i == 0) {
                    return;
                }
                rowDom = $(this);
                checkedDom = rowDom.find('.trace-row-checked'),
                    tracenumber = rowDom.find('.trace-row-number').attr('issuecode'), //当前开奖期编号
                    traceNo = rowDom.find('.text-center'); //序号

                traceNo.html('').html(i);
                if (traceLastNumber != tracenumber.substr(0, 8) && traceLastNumber != "" && (isGameTypes != 'fc3d' && isGameTypes != 'p5' && isGameTypes != 'ssq')) { //增加隔天期标识

                    switch (dateNotieNumber) {
                        case 0:
                            dateNotie = '明天 ';
                            break;
                        case 1:
                            dateNotie = '后天 ';
                            break;
                        case 2:
                            dateNotie = '大后天 ';
                            break;
                        default:
                            dateNotie = '';
                            break;

                    }
                    if (jQuery.trim(dateNotie) != '') {
                        traceNo.html('').append('<div class="icon-chase-mark">' + dateNotie + ' ' + rowDom.find('.trace-row-number').text().substr(0, 8) + '</div>');
                    }

                    dateNotieNumber++;

                }
                traceLastNumber = tracenumber.substr(0, 8);

                if (checkedDom.size() > 0 && checkedDom.get(0).checked) {
                    par = checkedDom.parent();
                    index = me.getStartNumberIndexByNumber(par.find('.trace-row-number').text());
                    index = index == -1 ? 0 : index;
                    issueCode = list[index]['issueCode'];
                    //0倍时再选中，初始倍数为1倍
                    rowDom.find('.trace-row-multiple').removeAttr('disabled');
                    if (rowDom.find('.trace-row-multiple').val() == '0') {
                        rowDom.find('.trace-row-multiple').val('1');
                        rowDom.find('.trace-row-money').text(me.formatMoney(orderData['amount'] * 1));

                    }

                    traceData.push({
                        'traceNumber': par.find('.trace-row-number').text(),
                        'issueCode': issueCode,
                        'multiple': Number(par.parent().find('.trace-row-multiple').val())
                    });
                    times++;
                    amount += Number(rowDom.find('.trace-row-money').text().replace(/,/g, ''));


                } else { //没有勾选时状态
                    rowDom.find('.trace-row-money').text('0');
                    rowDom.find('.trace-row-multiple').val('0');
                    rowDom.find('.trace-row-multiple').attr('disabled', 'disabled').css('border-color', '#CECECE');

                }


            });

            if (!!orderData) {
                lotterysNum = times * orderData['count'];
                result = {
                    'times': times,
                    'lotterysNum': lotterysNum,
                    'amount': amount,
                    'orderData': orderData,
                    'traceData': traceData,
                    'traceType': me.getTraceType()
                };
            }
            return result;
        },
        //追加或删除投注，在追号面板展开的情况下再次进行选球投注，追号相关信息追加或减少投注金额
        //isShowMessage 是否关闭提示
        updateOrder: function (isNotShowMessage) {
            var me = this,
                orderData = Games.getCurrentGameOrder().getTotal(),
                tableType = me.getRowTableType(),
                maxObj = Games.getCurrentGameOrder().getOrderMaxMultiple(),
                maxnum = maxObj['maxnum'],
                selValue = Number(me.normalSelectMultiple.getValue()),
                inputValue = Number($('#J-trace-advance-multiple').val());

            me.setOrderData(orderData);

            //按照最新的允许设置的最大倍数，设置相关的倍数输入框和下拉框
            if (selValue > maxnum) {
                me.normalSelectMultiple.setValue(maxnum);
            }
            if (inputValue > maxnum) {
                $('#J-trace-advance-multiple').val(maxnum);
            }

            //当注单发生变化时，清空盈利追号和盈利率追号表格
            if (!isNotShowMessage && (tableType == 'trace_advanced_fanbei_a' || tableType == 'trace_advanced_fanbei_b' || tableType == 'trace_advanced_yingli_a' || tableType == 'trace_advanced_yingli_b' || tableType == 'trace_advanced_yinglilv_a' || tableType == 'trace_advanced_yinglilv_b')) {
                Games.getCurrentGameMessage().show({
                    type: 'normal',
                    closeFun: function () {
                        this.hide();
                    },
                    data: {
                        tplData: {
                            msg: '您的方案已被修改，如果需要根据最新方案进行追号，请点击生成追号计划按钮'
                        }
                    }
                });
            }
            //盈利追号/盈利率追号每次都清空表格
            me.getAdvancedRowTable().html('');


            //更新表格
            me.updateDetail(orderData['amount']);

            //更新界面金额
            me.updateStatistics();
        },
        //更新详细表格单条金额
        updateDetail: function (amount) {
            var me = this,
                trs = me.getTable().find('tr'),
                rowDom = null,
                rowAmountDom = null,
                rowUserGroupMoneyDom = null,
                rowWinTotalAmountDom = null,
                rowYinglilvDom = null,
                userGroupMoney = 0,
                tableType = me.getRowTableType(),
                advancedType;
            //console.log(me.getRowTable());
            //高级追号和普通追号表格结构不一样
            if (tableType == 'trace_advanced_yingli_a' || tableType == 'trace_advanced_yingli_b' || tableType == 'trace_advanced_yinglilv_a' || tableType == 'trace_advanced_yinglilv_b') {
                me.rebuildYinglilvRows();
            } else {
                //翻倍追号自动更新表格
                advancedType = me.getAdvancedRowTable().attr('data-type');
                if (advancedType == 'trace_advanced_fanbei_a' || advancedType == 'trace_advanced_fanbei_b') {
                    trs = me.getAdvancedTable().find('tr');
                    trs.each(function () {
                        rowDom = $(this);
                        rowMoney = rowDom.find('.trace-row-money');
                        rowMultiple = Number(rowDom.find('.trace-row-multiple').val());
                        rowMoney.text(me.formatMoney(rowMultiple * amount));
                    });
                }
            }

            //普通追号每次都自动更新表格
            trs = me.getNormalTable().find('tr');
            trs.each(function () {
                rowDom = $(this);
                rowMoney = rowDom.find('.trace-row-money');
                rowMultiple = Number(rowDom.find('.trace-row-multiple').val());
                rowMoney.text(me.formatMoney(rowMultiple * amount));
            });


        },
        //计算投注内容中的预计中奖金额
        //选球内容有可能是不同的玩法内容，需要各自计算中奖将进组金额
        getWinMoney: function () {
            var me = this,
                orders = me.getOrderData()['orders'],
                i = 0,
                len = orders.length,
                winMoney = 0;
            for (; i < len; i++) {
                winMoney += me.getPlayGroupMoneyByGameMethodName(orders[i]['type']) * orders[i]['moneyUnit'];
            }
            return winMoney;
        },
        //根据追号选择条件生成详细表格
        confirmSetting: function () {
            var me = this;
            me.setOrderData(Games.getCurrentGameOrder().getTotal());
            me.buildDetail();
        },
        //检测注单中是否有多奖金的玩法,在没有混投及元角模式一致情况下调用
        isMoreBounsMethod: function () {
            var me = this,
                orders = me.getOrderData()['orders'],
                i = 0,
                len = orders.length;

            for (; i < len; i++) {
                type = orders[i]['type'];
                if (typeof(Games.currentIsMoreBouns['moreBouns'][type]) != 'undefined' && Games.currentIsMoreBouns['moreBouns'][type] == true) {

                    return orders[i].typeText;
                }
            }

            return '';
        },
        //检测当前投注列表中是否全部为同一玩法
        //且元角模式一致
        isSameGameMethod: function () {
            var me = this,
                orders = me.getOrderData()['orders'],
                type = '',
                moneyUnit = -1;
            i = 0,
                len = orders.length;
            for (; i < len; i++) {
                if (type != '') {
                    if (type != orders[i]['type']) {
                        return false;
                    }
                } else {
                    type = orders[i]['type'];
                }

                if (moneyUnit != -1) {
                    if (moneyUnit != orders[i]['moneyUnit']) {
                        return false;
                    }
                } else {
                    moneyUnit = orders[i]['moneyUnit'];
                }
            }
            return true;
        },
        getSameGameMethodName: function () {
            var me = this,
                orders = me.getOrderData()['orders'];
            if (orders.length > 0) {
                return orders[0]['type'];
            }
        },
        getSameGameMoneyUnti: function () {
            var me = this,
                orders = me.getOrderData()['orders'];
            if (orders.length > 0) {
                return orders[0]['moneyUnit'];
            }
        },
        setOrderData: function (data) {
            this.orderData = data;
        },
        getOrderData: function () {
            return this.orderData == null ? {
                'count': 0,
                'amount': 0,
                'orders': []
            } : this.orderData;
        },
        //由期号获得期号在列表中的索引值
        getStartNumberIndexByNumber: function (number) {
            var me = this,
                numberList = Games.getCurrentGame().getDynamicConfig()['gamenumbers'],
                len = numberList.length,
                i = 0;
            for (; i < len; i++) {
                if (numberList[i]['number'] == number) {
                    return i;
                }
            }
            return -1;
        },
        getStartNumberByIndex: function (index) {
            var me = this,
                numberList = Games.getCurrentGame().getDynamicConfig()['gamenumbers'];
            if (numberList.length > index) {
                return numberList[index];
            }
            return {};
        },
        //生成追号计划详情内容
        //maxMultipleNum 如果参数中有设置该参数，则最大倍数都使用该值(用于检测倍数超出最大值后重新设置倍数)
        buildDetail: function () {
            var me = this,
                type = me.getTraceType(),
                msg = Games.getCurrentGameMessage();
            //每次获取最新的投注信息
            me.setOrderData(Games.getCurrentGameOrder().getTotal());
            orderAmount = me.getOrderData()['amount'];

            //投注内容为空
            if (type != 'normal' && orderAmount <= 0) {
                msg.show({
                    type: 'mustChoose',
                    msg: '请至少选择一注投注号码！',
                    data: {
                        tplData: {
                            msg: '请至少选择一注投注号码！'
                        }
                    }
                });
                return;
            }

            if ($.isFunction(me['trace_' + type])) {
                me['trace_' + type].call(me);
            }
            me.updateStatistics();
        },
        //普通追号
        trace_normal: function () {
            var me = this,
                cfg = me.defConfig,
                tpl = cfg.dataRowTemplate,
                tplArr = [],
            //类型
                type = me.getTraceType(),
            //追号期数
                times = me.getTimes(),
            //倍数
                multiple = me.getMultiple(),
            //最大倍数限制
                maxMultiple = Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum'],
            //投注金额
                orderAmount = 0,
                i = 0,

            //当前期
                currNumber = Games.getCurrentGame().getCurrentNumber(),
                currStr = '<span class="icon-period-current"></span>',
            //当前期文本
                currNumberText = '',
            //用户选择的开始期号
                settingStartNumber = me.traceStartNumberSelect.getValue(),
                startIndex,
                numberData,
            //期号列表长度
                numberLength = Games.getCurrentGame().getDynamicConfig()['gamenumbers'].length,
                rowData;


            me.setOrderData(Games.getCurrentGameOrder().getTotal());
            orderAmount = me.getOrderData()['amount'];

            tplArr.push(cfg.dataRowHeader);


            startIndex = me.getStartNumberIndexByNumber(settingStartNumber);
            i = startIndex;
            times += i;
            for (; i < times; i++) {
                numberData = me.getStartNumberByIndex(i);
                currNumberText = numberData['number'];
                if (currNumberText == currNumber) {
                    currNumberText = currNumberText + currStr;
                }
                if (numberData['number']) {
                    rowData = {
                        'No': i + 1,
                        'traceNumber': currNumberText,
                        'issueCode': numberData['issueCode'],
                        'multiple': multiple,
                        'money': me.formatMoney(orderAmount * multiple),
                        'publishTime': numberData['time']
                    };
                    tplArr.push(me.formatRow(tpl, rowData));
                }
            }
            me.getRowTable().html(tplArr.join(''));


            //在表格上设置最后生成列表的类型，用于区分列表类型
            me.getRowTable().attr('data-type', 'trace_normal');

        },
        //高级追号
        trace_advanced: function () {
            var me = this,
                type = me.getTraceType(),
                advancedType = me.getAdvancedType(),
                typeTypeType = me.getTypeTypeType(),
                fnName = 'trace_' + type + '_' + advancedType + '_' + typeTypeType,
                noticeName = '',
                showNotice = '',
                isMoreBouns = me.isMoreBounsMethod();


            //盈利/盈利率追号不支持混投
            if (advancedType == 'fanbei') {
            } else if (!me.isSameGameMethod() && (advancedType == 'yingli' || advancedType == 'yinglilv') || isMoreBouns != '') {
                if (advancedType == 'yingli') {
                    noticeName = '盈利金额追号';
                } else if (advancedType == 'yinglilv') {
                    noticeName = '盈利率追号';
                } else {
                    noticeName = '盈利追号方式';
                }
                if (!me.isSameGameMethod()) {
                    showNotice = noticeName + '不支持混投<br />请确保您的投注都为同一玩法类型<br />且元角模式一致。';
                } else if (isMoreBouns != "") {
                    showNotice = noticeName + '时，' + isMoreBouns + ',为多奖金玩法，不支持盈利金额和盈利率追号';
                }
                //清空追号期
                me.getRowTable().html('');
                Games.getCurrentGameMessage().show({
                    type: 'mustChoose',
                    msg: '',
                    data: {
                        tplData: {
                            msg: showNotice
                        }
                    }
                });
                return;
            }

            if ($.isFunction(me[fnName])) {
                me[fnName]();
            }
            //在表格上设置最后生成列表的类型，用于区分列表类型
            me.getRowTable().attr('data-type', fnName);
        },
        //高级追号 -- 翻倍追号 -- 间隔追号
        trace_advanced_fanbei_a: function (maxnum) {

            var me = this,
                tpl = me.defConfig.dataRowTemplate,
                tplArr = [],
            //追号期数
                times = me.getTimes(),
                maxNumltipleObj = Games.getCurrentGameOrder().getOrderMaxMultiple(),
            //最大倍数限制
                maxMultiple = maxnum || maxNumltipleObj['maxnum'],
                typeTitle = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(maxNumltipleObj['gameMethod']).join('-'),
                jiangeNum = Number($('#J-trace-advanced-fanbei-a-jiange').val()),
            //基础倍数
                multipleBase = me.getMultiple(),
            //中间运算倍数
                multiple = multipleBase,
            //间隔倍数
                multiple2 = Number($('#J-trace-advanced-fanbei-a-multiple').val()),
                testData,
                i = 0,
            //间隔临时计数器
                _i = jiangeNum,

            //当前期
                currNumber = Games.getCurrentGame().getCurrentNumber(),
                currStr = '<span class="icon-period-current"></span>',
            //当前期文本
                currNumberText = '',
            //用户选择的开始期号
                settingStartNumber = me.traceStartNumberSelect.getValue(),
                startIndex,
                numberData,
            //期号列表长度
                numberLength = Games.getCurrentGame().getDynamicConfig()['gamenumbers'].length,
                rowData,
                traceLastNumber = '', //上期号
                traceNo = ''; //序号列

            tplArr.push(me.defConfig.dataRowHeader);

            startIndex = me.getStartNumberIndexByNumber(settingStartNumber);
            i = startIndex;
            times += i;
            numberData = me.getStartNumberByIndex(i);
            for (; i < times; i++) {
                //console.log(_i);
                if (_i <= 0) {
                    _i = jiangeNum;
                    multiple = multiple * multiple2;
                }
                _i--;
                //debugger;

                //倍数超限时提示
                if (multiple > maxMultiple) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            me.trace_advanced_fanbei_a(maxMultiple);
                            me.updateStatistics();
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '翻倍追号中的<b>[' + typeTitle + ']</b>的倍数超过了最大倍数限制，系统将自动调整为最大可设置倍数'
                            }
                        }
                    });
                    if (!maxnum) {
                        return;
                    }
                }

                //倍数不能超过最大允许倍数
                multiple = multiple > maxMultiple ? maxMultiple : multiple;

                numberData = me.getStartNumberByIndex(i);
                if (!numberData['number']) {
                    break;
                }
                currNumberText = numberData['number'];
                if (currNumberText == currNumber) {
                    currNumberText = currNumberText + currStr;
                }

                rowData = {
                    'No': traceNo,
                    'traceNumber': currNumberText,
                    'issueCode': numberData['issueCode'],
                    'multiple': multiple,
                    'money': me.formatMoney(orderAmount * multiple),
                    'publishTime': numberData['time']
                };

                traceLastNumber = currNumberText.substr(0, 8);
                tplArr.push(me.formatRow(tpl, rowData));

            }
            me.getRowTable().html(tplArr.join(''));
        },
        //高级追号 -- 翻倍追号 -- 前后追号
        trace_advanced_fanbei_b: function () {
            var me = this,
                tpl = me.defConfig.dataRowTemplate,
                tplArr = [],
            //追号期数
                times = me.getTimes(),
            //最大倍数限制
                maxMultiple = Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum'],
                jiangeNum = Number($('#J-trace-advanced-fanbei-a-jiange').val()),
            //基础倍数
                multipleBase = me.getMultiple(),
            //中间运算倍数
                multiple = 1,
            //间隔倍数
                multiple2 = Number($('#J-trace-advanced-fanbei-a-multiple').val()),
                testData,
                i = 0,
            //间隔临时计数器
                _i = jiangeNum,

                beforeNum = Number($('#J-trace-advanced-fanbei-b-num').val()),
                startMultiple = Number($('#J-trace-advance-multiple').val()),
                afterMultiple = Number($('#J-trace-advanced-fanbei-b-multiple').val()),


            //当前期
                currNumber = Games.getCurrentGame().getCurrentNumber(),
                currStr = '<span class="icon-period-current"></span>',
            //当前期文本
                currNumberText = '',
            //用户选择的开始期号
                settingStartNumber = me.traceStartNumberSelect.getValue(),
                startIndex,
                numberData,
            //期号列表长度
                numberLength = Games.getCurrentGame().getDynamicConfig()['gamenumbers'].length,
                rowData,
                traceLastNumber = '', //上期号
                traceNo = ''; //序号列


            tplArr.push(me.defConfig.dataRowHeader);

            startIndex = me.getStartNumberIndexByNumber(settingStartNumber);
            i = startIndex;
            times += i;
            numberData = me.getStartNumberByIndex(i);
            for (; i < times; i++) {
                if (i < (beforeNum + startIndex)) {
                    multiple = startMultiple > maxMultiple ? maxMultiple : startMultiple;
                } else {
                    multiple = afterMultiple > maxMultiple ? maxMultiple : afterMultiple;
                }

                numberData = me.getStartNumberByIndex(i);
                if (!numberData['number']) {
                    break;
                }
                currNumberText = numberData['number'];
                if (currNumberText == currNumber) {
                    currNumberText = currNumberText + currStr;
                }

                rowData = {
                    'No': traceNo,
                    'traceNumber': currNumberText,
                    'issueCode': numberData['issueCode'],
                    'multiple': multiple,
                    'money': me.formatMoney(orderAmount * multiple),
                    'publishTime': numberData['time']
                };
                traceLastNumber = currNumberText.substr(0, 8);

                tplArr.push(me.formatRow(tpl, rowData));
            }
            me.getRowTable().html(tplArr.join(''));
        },
        //高级追号 -- 盈利金额追号 -- 预期盈利金额
        trace_advanced_yingli_a: function (maxnum) {

            var me = this,
                tpl = me.defConfig.dataRowTemplate,
                tplArr = [],
            //追号期数
                times = me.getTimes(),
                maxNumltipleObj = Games.getCurrentGameOrder().getOrderMaxMultiple(),
            //最大倍数限制
                maxMultiple = maxnum || maxNumltipleObj['maxnum'],
                typeTitle = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(maxNumltipleObj['gameMethod']).join('-'),
            //基础倍数
                multipleBase = me.getMultiple(),
            //中间运算倍数
                multiple = 1,
                testData,
                i = 0,


            //玩法类型
                gameMethodType = me.getSameGameMethodName(),
            //每期必须要达到的盈利金额
                yingliMoney = Number($('#J-trace-advanced-yingli-a-money').val()),
            //元角模式
                moneyUnit = me.getSameGameMoneyUnti(),
            //用户奖金组中该玩法中每注的中奖金额
                userGroupMoney = me.getWinMoney(),
            //基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
                multipleBase,
            //启用另外表头和行模板
                tpl = me.defConfig.dataRowYingliTemplate,
                orders = me.getOrderData()['orders'],
            //投注组本金
                orderAmount = 0,
            //所有投注本金
                orderTotalAmount = 0,
            //中奖总金额
                winTotalAmout = 0,
            //盈利率
                yinglilv = 0,


            //当前期
                currNumber = Games.getCurrentGame().getCurrentNumber(),
                currStr = '<span class="icon-period-current"></span>',
            //当前期文本
                currNumberText = '',
            //用户选择的开始期号
                settingStartNumber = me.traceStartNumberSelect.getValue(),
                startIndex,
                numberData,
            //期号列表长度
                numberLength = Games.getCurrentGame().getDynamicConfig()['gamenumbers'].length,
                rowData,
                traceLastNumber = '', //上期号
                traceNo = ''; //序号列


            tplArr.push(me.defConfig.dataRowYingliHeader);

            startIndex = me.getStartNumberIndexByNumber(settingStartNumber);
            i = startIndex;
            times += i;
            numberData = me.getStartNumberByIndex(i);
            for (; i < times; i++) {
                orderAmount = 0;
                winTotalAmout = 0;
                //基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
                //	multipleBase = 1;
                //计算预计中奖金额
                $.each(orders, function (i) {
                    var order = this,
                        num = order['num'],
                        price = order['onePrice'],
                        multiple = order['multiple'],
                    //本金
                        amount = num * multiple * price * moneyUnit,
                    //单注中奖金额
                        winAmout = userGroupMoney * multiple;

                    //该投注组盈利金额
                    winTotalAmout += winAmout;

                    orderAmount += amount;
                });


                //获得倍数
                //multipleBase = me.getMultipleByMoney(userGroupMoney, yingliMoney, orderAmount, orderTotalAmount);
                //无法达到预期目标
                if (multipleBase < 0) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '您设置的盈利金额追号参数过高，无法达到您预期的目标值，请您重新修改参数设置。'
                            }
                        }
                    });
                    //清空之前表格数据
                    me.getRowTable().html('');
                    return;
                }

                //倍数超限时提示
                if (multipleBase > maxMultiple) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            me.trace_advanced_yingli_a(maxMultiple);
                            me.updateStatistics();
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '盈利金额追号中的<b>[' + typeTitle + ']</b>的倍数超过了最大倍数限制，系统将自动调整为最大可设置倍数'
                            }
                        }
                    });
                    multipleBase = maxnum;

                    if (!maxnum) {
                        return;
                    } else {
                        multipleBase = maxnum;
                    }

                }
                /*//高奖金模式不支持盈利金额追号
                if ($('#J-select-rebate option:selected').attr('retPointValue') == 2) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '高奖金模式不支持盈利金额和盈利率追号，请使用返点模式进行高级追号。'
                            }
                        }
                    });
                    //清空之前表格数据
                    me.getRowTable().html('');
                    return;
                }*/

                //richardgong 2015-07-01 重新洗牌计算
                var isShowMessageFlag = false;
                var isTooMoreTime = 99999;

                InnerFor: for (var iii = 0; iii <= 99999; iii++) {
                    var neworderAmount = orderAmount;
                    var neworderTotalAmount = orderTotalAmount;
                    var newwinTotalAmout = winTotalAmout;
                    var newyinglilv = yinglilv;

                    //花费本金
                    neworderAmount *= multipleBase;
                    //累计本金
                    neworderTotalAmount += neworderAmount;
                    //利润减去累计花费
                    newwinTotalAmout = (userGroupMoney * multipleBase) - neworderTotalAmount;
                    //盈利率
                    newyinglilv = newwinTotalAmout / neworderTotalAmount;

                    if (newwinTotalAmout < yingliMoney) {
                        multipleBase++;
                    } else {
                        isShowMessageFlag = true;
                        orderAmount = neworderAmount;
                        orderTotalAmount = neworderTotalAmount;
                        winTotalAmout = newwinTotalAmout;
                        yinglilv = newyinglilv;
                        break InnerFor;
                    }


                }
                if (multipleBase > isTooMoreTime && !isShowMessageFlag && !i) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '您设置的盈利金额追号参数过高，无法达到您预期的目标值，请您重新修改参数设置。'
                            }
                        }
                    });

                    me.getRowTable().html('');
                    return;
                } else if (multipleBase > isTooMoreTime) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '您设置的盈利金额追号参数过高，无法达到您预期的目标值，请您重新修改参数设置。'
                            }
                        }
                    });

                    break;

                }


                numberData = me.getStartNumberByIndex(i);

                //richardgong 2015-07-01 帮忙注释：这里表示，如果追号期数大于限制的期数，那么，循环跳出
                if (!numberData['number']) {
                    break;
                }

                //richardgong 2015-07-01 帮忙注释：这里用于添加当前期样式
                currNumberText = numberData['number'];
                if (currNumberText == currNumber) {
                    currNumberText = currNumberText + currStr;
                }

                rowData = {
                    'No': traceNo,
                    'traceNumber': currNumberText,
                    'issueCode': numberData['issueCode'],
                    'multiple': multipleBase,
                    'money': me.formatMoney(orderAmount),
                    'userGroupMoney': me.formatMoney(userGroupMoney * multipleBase),
                    'winTotalAmout': me.formatMoney(winTotalAmout),
                    'yinglilv': Number(yinglilv * 100).toFixed(2)
                };

                traceLastNumber = currNumberText.substr(0, 8);
                tplArr.push(me.formatRow(tpl, rowData));
            }
            me.getRowTable().html(tplArr.join(''));
            // 表格生成空白 无法实现设定的盈利参数
            me.trace_row_empty();

        },
        //高级追号 -- 盈利金额追号 -- 前后预期盈利金额
        trace_advanced_yingli_b: function (maxnum) {

            var me = this,
                tpl = me.defConfig.dataRowTemplate,
                tplArr = [],
            //追号期数
                times = me.getTimes(),
                maxNumltipleObj = Games.getCurrentGameOrder().getOrderMaxMultiple(),
            //最大倍数限制
                maxMultiple = maxnum || maxNumltipleObj['maxnum'],
                typeTitle = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(maxNumltipleObj['gameMethod']).join('-'),
            //基础倍数
                multipleBase = me.getMultiple(),
            //中间运算倍数
                multiple = 1,
                testData,
                i = 0,


            //玩法类型
                gameMethodType = me.getSameGameMethodName(),
            //前几期
                yingliNum = Number($('#J-trace-advanced-yingli-b-num').val()),
            //第一期必须要达到的盈利金额
                yingliMoney = Number($('#J-trace-advanced-yingli-b-money1').val()),
            //第二期必须要达到的盈利金额
                yingliMoney2 = Number($('#J-trace-advanced-yingli-b-money2').val()),
            //元角模式
                moneyUnit = me.getSameGameMoneyUnti(),
            //用户奖金组中该玩法中每注的中奖金额
                userGroupMoney = me.getWinMoney(),
            //基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
                multipleBase,
            //启用另外表头和行模板
                tpl = me.defConfig.dataRowYingliTemplate,
                orders = me.getOrderData()['orders'],
            //投注组本金
                orderAmount = 0,
            //所有投注本金
                orderTotalAmount = 0,
            //中奖总金额
                winTotalAmout = 0,
            //盈利率
                yinglilv = 0,


            //当前期
                currNumber = Games.getCurrentGame().getCurrentNumber(),
                currStr = '<span class="icon-period-current"></span>',
            //当前期文本
                currNumberText = '',
            //用户选择的开始期号
                settingStartNumber = me.traceStartNumberSelect.getValue(),
                startIndex,
                numberData,
            //期号列表长度
                numberLength = Games.getCurrentGame().getDynamicConfig()['gamenumbers'].length,
                rowData,
                traceLastNumber = '', //上期号
                traceNo = ''; //序号列


            tplArr.push(me.defConfig.dataRowYingliHeader);

            startIndex = me.getStartNumberIndexByNumber(settingStartNumber);
            i = startIndex;
            times += i;
            numberData = me.getStartNumberByIndex(i);
            for (; i < times; i++) {
                if ((i + 1) > (yingliNum + startIndex)) {
                    yingliMoney = yingliMoney2;
                }
                orderAmount = 0;
                winTotalAmout = 0;
                //基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
                //	multipleBase = 1;
                //计算预计中奖金额
                $.each(orders, function (i) {
                    var order = this,
                        num = order['num'],
                        price = order['onePrice'],
                        multiple = order['multiple'],
                    //本金
                        amount = num * multiple * price * moneyUnit,
                    //单注中奖金额
                        winAmout = userGroupMoney * multiple;
                    //该投注组盈利金额
                    winTotalAmout += winAmout;
                    orderAmount += amount;
                });

                //获得倍数
                //	multipleBase = me.getMultipleByMoney(userGroupMoney, yingliMoney, orderAmount, orderTotalAmount);
                //无法达到预期目标
                if (multipleBase < 0) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '您设置的盈利金额追号参数过高，无法达到您预期的目标值，请您重新修改参数设置。'
                            }
                        }
                    });
                    //清空之前表格数据
                    me.getRowTable().html('');
                    return;
                }


                //倍数超限时提示
                if (multipleBase > maxMultiple) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            me.trace_advanced_yingli_b(maxMultiple);
                            me.updateStatistics();
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '盈利金额追号中的<b>[' + typeTitle + ']</b>的倍数超过了最大倍数限制，系统将自动调整为最大可设置倍数'
                            }
                        }
                    });
                    multipleBase = maxnum;
                    if (!maxnum) {
                        return;
                    } else {
                        multipleBase = maxnum;
                    }
                }


                //richardgong 2015-07-01 重新洗牌计算
                var isShowMessageFlag = false;
                var isTooMoreTime = 99999;

                InnerFor: for (var iii = 0; iii <= 99999; iii++) {
                    var neworderAmount = orderAmount;
                    var neworderTotalAmount = orderTotalAmount;
                    var newwinTotalAmout = winTotalAmout;
                    var newyinglilv = yinglilv;

                    //花费本金
                    neworderAmount *= multipleBase;
                    //累计本金
                    neworderTotalAmount += neworderAmount;
                    //利润减去累计花费
                    newwinTotalAmout = (userGroupMoney * multipleBase) - neworderTotalAmount;
                    //盈利率
                    newyinglilv = newwinTotalAmout / neworderTotalAmount;

                    if (newwinTotalAmout < yingliMoney) {
                        multipleBase++;
                    } else {
                        isShowMessageFlag = true;
                        orderAmount = neworderAmount;
                        orderTotalAmount = neworderTotalAmount;
                        winTotalAmout = newwinTotalAmout;
                        yinglilv = newyinglilv;
                        break InnerFor;
                    }


                }
                if (multipleBase > isTooMoreTime && !isShowMessageFlag && !i) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '您设置的盈利金额追号参数过高，无法达到您预期的目标值，请您重新修改参数设置。'
                            }
                        }
                    });

                    me.getRowTable().html('');
                    return;
                } else if (multipleBase > isTooMoreTime) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '您设置的盈利金额追号参数过高，无法达到您预期的目标值，请您重新修改参数设置。'
                            }
                        }
                    });

                    break;

                }


                numberData = me.getStartNumberByIndex(i);
                if (!numberData['number']) {
                    break;
                }
                currNumberText = numberData['number'];
                if (currNumberText == currNumber) {
                    currNumberText = currNumberText + currStr;
                }

                rowData = {
                    'No': traceNo,
                    'traceNumber': currNumberText,
                    'issueCode': numberData['issueCode'],
                    'multiple': multipleBase,
                    'money': me.formatMoney(orderAmount),
                    'userGroupMoney': me.formatMoney(userGroupMoney * multipleBase),
                    'winTotalAmout': me.formatMoney(winTotalAmout),
                    'yinglilv': Number(yinglilv * 100).toFixed(2)
                };
                traceLastNumber = currNumberText.substr(0, 8);
                tplArr.push(me.formatRow(tpl, rowData));
            }
            /*//高奖金模式不支持盈利金额追号
            if ($('#J-select-rebate option:selected').attr('retPointValue') == 2) {
                Games.getCurrentGameMessage().show({
                    type: 'normal',
                    closeText: '确定',
                    closeFun: function () {
                        this.hide();
                    },
                    data: {
                        tplData: {
                            msg: '高奖金模式不支持盈利金额和盈利率追号，请使用返点模式进行高级追号。'
                        }
                    }
                });
                //清空之前表格数据
                me.getRowTable().html('');
                return;
            }*/
            me.getRowTable().html(tplArr.join(''));

            // 表格生成空白 无法实现设定的盈利参数
            me.trace_row_empty();
        },
        //高级追号 -- 盈利率追号 -- 预期盈利率
        trace_advanced_yinglilv_a: function (maxnum) {
            var me = this,
                tpl = me.defConfig.dataRowTemplate,
                tplArr = [],
            //追号期数
                times = me.getTimes(),
                maxNumltipleObj = Games.getCurrentGameOrder().getOrderMaxMultiple(),
            //最大倍数限制
                maxMultiple = maxnum || maxNumltipleObj['maxnum'],
                typeTitle = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(maxNumltipleObj['gameMethod']).join('-'),
            //基础倍数
                multipleBase = me.getMultiple(),
            //中间运算倍数
                multiple = 1,
                testData,
                i = 0,


            //玩法类型
                gameMethodType = me.getSameGameMethodName(),
            //每期必须要达到的盈利率
                yinglilv = Number($('#J-trace-advanced-yinglilv-a').val()) / 100,
                yingliMoney = 0,
            //元角模式
                moneyUnit = me.getSameGameMoneyUnti(),
            //用户奖金组中该玩法中每注的中奖金额
                userGroupMoney = me.getWinMoney(),
            //基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
                multipleBase,
            //启用另外表头和行模板
                tpl = me.defConfig.dataRowYingliTemplate,
                orders = me.getOrderData()['orders'],
            //投注组本金
                orderAmount = 0,
            //所有投注本金
                orderTotalAmount = 0,
            //中奖总金额
                winTotalAmout = 0,


            //当前期
                currNumber = Games.getCurrentGame().getCurrentNumber(),
                currStr = '<span class="icon-period-current"></span>',
            //当前期文本
                currNumberText = '',
            //用户选择的开始期号
                settingStartNumber = me.traceStartNumberSelect.getValue(),
                startIndex,
                numberData,
            //期号列表长度
                numberLength = Games.getCurrentGame().getDynamicConfig()['gamenumbers'].length,
                rowData,
                traceLastNumber = '', //上期号
                traceNo = ''; //序号列


            tplArr.push(me.defConfig.dataRowYingliHeader);

            startIndex = me.getStartNumberIndexByNumber(settingStartNumber);
            i = startIndex;
            times += i;
            numberData = me.getStartNumberByIndex(i);
            for (; i < times; i++) {
                orderAmount = 0;
                winTotalAmout = 0;
                //基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
                //		multipleBase = 1;
                //计算预计中奖金额
                $.each(orders, function (i) {
                    var order = this,
                        num = order['num'],
                        price = order['onePrice'],
                        multiple = order['multiple'],
                    //本金
                        amount = num * multiple * price * moneyUnit,
                    //单注中奖金额
                        winAmout = userGroupMoney * multiple;
                    //该投注组预计中奖金额
                    winTotalAmout += winAmout;
                    //订单本金
                    orderAmount += amount;
                });

                //	multipleBase = me.getMultipleByYinglilv(yinglilv, userGroupMoney, orderAmount, orderTotalAmount);
                //无法达到预期目标
                if (multipleBase < 0) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '您设置的盈利率追号参数过高，无法达到您预期的目标值，请您重新修改参数设置。'
                            }
                        }
                    });
                    //清空之前表格数据
                    me.getRowTable().html('');
                    return;
                }

                //倍数超限时提示
                if (multipleBase > maxMultiple) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            me.trace_advanced_yinglilv_a(maxMultiple);
                            me.updateStatistics();
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '盈利率追号中的<b>[' + typeTitle + ']</b>的倍数超过了最大倍数限制，系统将自动调整为最大可设置倍数'
                            }
                        }
                    });
                    multipleBase = maxnum;
                    if (!maxnum) {
                        return;
                    } else {
                        multipleBase = maxnum;
                    }

                }
                /*//高奖金模式不支持盈利率追号
                if ($('#J-select-rebate option:selected').attr('retPointValue') == 2) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '高奖金模式不支持盈利金额和盈利率追号，请使用返点模式进行高级追号。'
                            }
                        }
                    });
                    //清空之前表格数据
                    me.getRowTable().html('');
                    return;
                }*/


                //richardgong 2015-07-01 重新洗牌计算

                var isShowMessageFlag = false;
                var isTooMoreTime = 99999;

                InnerFor: for (var iii = 0; iii <= 99999; iii++) {
                    var neworderAmount = orderAmount;
                    var neworderTotalAmount = orderTotalAmount;
                    var newwinTotalAmout = winTotalAmout;
                    var resultyinglilv = 0;
                    //花费本金
                    neworderAmount *= multipleBase;
                    //累计本金
                    neworderTotalAmount += neworderAmount;
                    //利润减去累计花费
                    newwinTotalAmout = (userGroupMoney * multipleBase) - neworderTotalAmount;
                    resultyinglilv = newwinTotalAmout / neworderTotalAmount;
                    if (resultyinglilv < yinglilv) {
                        multipleBase++;
                    } else {
                        isShowMessageFlag = true;
                        orderAmount = neworderAmount;
                        orderTotalAmount = neworderTotalAmount;
                        winTotalAmout = newwinTotalAmout;
                        break InnerFor;
                    }
                }
                if (multipleBase > isTooMoreTime && !isShowMessageFlag && !i) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '您设置的盈利金额追号参数过高，无法达到您预期的目标值，请您重新修改参数设置。'
                            }
                        }
                    });

                    me.getRowTable().html('');
                    return;
                } else if (multipleBase > isTooMoreTime) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '您设置的盈利金额追号参数过高，无法达到您预期的目标值，请您重新修改参数设置。'
                            }
                        }
                    });
                    break;
                }


                numberData = me.getStartNumberByIndex(i);
                if (!numberData['number']) {
                    break;
                }
                currNumberText = numberData['number'];
                if (currNumberText == currNumber) {
                    currNumberText = currNumberText + currStr;
                }

                rowData = {
                    'No': traceNo,
                    'traceNumber': currNumberText,
                    'issueCode': numberData['issueCode'],
                    'multiple': multipleBase,
                    'money': me.formatMoney(orderAmount),
                    'userGroupMoney': me.formatMoney(userGroupMoney * multipleBase),
                    'winTotalAmout': me.formatMoney(winTotalAmout),
                    'yinglilv': Number(winTotalAmout / orderTotalAmount * 100).toFixed(2)
                };

                tplArr.push(me.formatRow(tpl, rowData));
            }
            traceLastNumber = currNumberText.substr(0, 8);
            me.getRowTable().html(tplArr.join(''));
            // 表格生成空白 无法实现设定的盈利参数
            me.trace_row_empty();
        },
        //高级追号 -- 盈利率追号 -- 前后预期盈利率
        trace_advanced_yinglilv_b: function (maxnum) {
            var me = this,
                tpl = me.defConfig.dataRowTemplate,
                tplArr = [],
            //追号期数
                times = me.getTimes(),
                maxNumltipleObj = Games.getCurrentGameOrder().getOrderMaxMultiple(),
            //最大倍数限制
                maxMultiple = maxnum || maxNumltipleObj['maxnum'],
                typeTitle = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(maxNumltipleObj['gameMethod']).join('-'),
            //基础倍数
                multipleBase = me.getMultiple(),
            //中间运算倍数
                multiple = 1,
                testData,
                i = 0,


            //玩法类型
                gameMethodType = me.getSameGameMethodName(),
            //前期数量期次数量
                yinglilvNum = Number($('#J-trace-advanced-yinglilv-b-num').val()),
            //前期必须达到的盈利率
                yinglilv1 = Number($('#J-trace-advanced-yingli-b-yinglilv1').val()) / 100,
            //后期必须达到的盈利率
                yinglilv2 = Number($('#J-trace-advanced-yingli-b-yinglilv2').val()) / 100,
            //盈利率变量
                yinglilvVar = 0,
                yingliMoney = 0,
            //元角模式
                moneyUnit = me.getSameGameMoneyUnti(),
            //用户奖金组中该玩法中每注的中奖金额
                userGroupMoney = me.getWinMoney(),
            //基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
                multipleBase,
            //启用另外表头和行模板
                tpl = me.defConfig.dataRowYingliTemplate,
                orders = me.getOrderData()['orders'],
            //投注组本金
                orderAmount = 0,
            //所有投注本金
                orderTotalAmount = 0,
            //中奖总金额
                winTotalAmout = 0,


            //当前期
                currNumber = Games.getCurrentGame().getCurrentNumber(),
                currStr = '<span class="icon-period-current"></span>',
            //当前期文本
                currNumberText = '',
            //用户选择的开始期号
                settingStartNumber = me.traceStartNumberSelect.getValue(),
                startIndex,
                numberData,
            //期号列表长度
                numberLength = Games.getCurrentGame().getDynamicConfig()['gamenumbers'].length,
                rowData,
                traceLastNumber = '', //上期号
                traceNo = ''; //序号列


            tplArr.push(me.defConfig.dataRowYingliHeader);

            startIndex = me.getStartNumberIndexByNumber(settingStartNumber);
            i = startIndex;
            times += i;
            numberData = me.getStartNumberByIndex(i);
            for (; i < times; i++) {
                orderAmount = 0;
                winTotalAmout = 0;
                //基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
                //	multipleBase = 1;
                //计算预计中奖金额
                $.each(orders, function (i) {
                    var order = this,
                        num = order['num'],
                        price = order['onePrice'],
                        multiple = order['multiple'],
                    //本金
                        amount = num * multiple * price * moneyUnit,
                    //单注中奖金额
                        winAmout = num * userGroupMoney * multiple;

                    //该投注组盈利金额
                    winTotalAmout += winAmout;
                    orderAmount += amount;
                });
                //前期盈利率
                if (i < (yinglilvNum + startIndex)) {
                    yinglilvVar = yinglilv1;
                } else {
                    //后期盈利率
                    yinglilvVar = yinglilv2;
                }


                //	multipleBase = me.getMultipleByYinglilv(yinglilvVar, userGroupMoney, orderAmount, orderTotalAmount);
                //无法达到预期目标
                if (multipleBase < 0) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '您设置的盈利率追号参数过高，无法达到您预期的目标值，请您重新修改参数设置。'
                            }
                        }
                    });
                    //清空之前表格数据
                    me.getRowTable().html('');
                    return;
                }

                //倍数超限时提示
                if (multipleBase > maxMultiple) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            me.trace_advanced_yinglilv_a(maxMultiple);
                            me.updateStatistics();
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '盈利率追号中的<b>[' + typeTitle + ']</b>的倍数超过了最大倍数限制，系统将自动调整为最大可设置倍数'
                            }
                        }
                    });
                    multipleBase = maxnum;
                    if (!maxnum) {
                        return;
                    } else {
                        multipleBase = maxnum;
                    }
                }

                var isShowMessageFlag = false;
                var isTooMoreTime = 99999;

                InnerFor: for (var iii = 0; iii <= 99999; iii++) {
                    var neworderAmount = orderAmount;
                    var neworderTotalAmount = orderTotalAmount;
                    var newwinTotalAmout = winTotalAmout;
                    var resultyinglilv = 0;
                    //花费本金
                    neworderAmount *= multipleBase;
                    //累计本金
                    neworderTotalAmount += neworderAmount;
                    //利润减去累计花费
                    newwinTotalAmout = (userGroupMoney * multipleBase) - neworderTotalAmount;
                    resultyinglilv = newwinTotalAmout / neworderTotalAmount;
                    if (resultyinglilv < yinglilvVar) {
                        multipleBase++;
                    } else {
                        isShowMessageFlag = true;
                        orderAmount = neworderAmount;
                        orderTotalAmount = neworderTotalAmount;
                        winTotalAmout = newwinTotalAmout;
                        break InnerFor;
                    }
                }
                if (multipleBase > isTooMoreTime && !isShowMessageFlag && !i) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '您设置的盈利金额追号参数过高，无法达到您预期的目标值，请您重新修改参数设置。'
                            }
                        }
                    });

                    me.getRowTable().html('');
                    return;
                } else if (multipleBase > isTooMoreTime) {
                    Games.getCurrentGameMessage().show({
                        type: 'normal',
                        closeText: '确定',
                        closeFun: function () {
                            this.hide();
                        },
                        data: {
                            tplData: {
                                msg: '您设置的盈利金额追号参数过高，无法达到您预期的目标值，请您重新修改参数设置。'
                            }
                        }
                    });
                    break;
                }


                numberData = me.getStartNumberByIndex(i);
                if (!numberData['number']) {
                    break;
                }
                currNumberText = numberData['number'];
                if (currNumberText == currNumber) {
                    currNumberText = currNumberText + currStr;
                }

                rowData = {
                    'No': traceNo,
                    'traceNumber': currNumberText,
                    'issueCode': numberData['issueCode'],
                    'multiple': multipleBase,
                    'money': me.formatMoney(orderAmount),
                    'userGroupMoney': me.formatMoney(userGroupMoney * multipleBase),
                    'winTotalAmout': me.formatMoney(winTotalAmout),
                    'yinglilv': Number(winTotalAmout / orderTotalAmount * 100).toFixed(2)
                };
                traceLastNumber = currNumberText.substr(0, 8);
                tplArr.push(me.formatRow(tpl, rowData));
            }
            /*//高奖金模式不支持盈利率追号
            if ($('#J-select-rebate option:selected').attr('retPointValue') == 2) {
                Games.getCurrentGameMessage().show({
                    type: 'normal',
                    closeText: '确定',
                    closeFun: function () {
                        this.hide();
                    },
                    data: {
                        tplData: {
                            msg: '高奖金模式不支持盈利金额和盈利率追号，请使用返点模式进行高级追号。'
                        }
                    }
                });
                //清空之前表格数据
                me.getRowTable().html('');
                return;
            }*/
            me.getRowTable().html(tplArr.join(''));

            // 表格生成空白 无法实现设定的盈利参数
            me.trace_row_empty();
        },
        // 检查追号生成计画表是否为空
        // 空白内容需提醒用户错误
        trace_row_empty: function () {
            if ($("#J-trace-table-advanced-body .trace-row-number").size() <= 0) {
                Games.getCurrentGameMessage().show({
                    type: 'normal',
                    closeText: '返回',
                    closeFun: function () {
                        this.hide();
                    },
                    data: {
                        tplData: {
                            msg: '设置参数有误！无法实现您设定的盈利参数！'
                        }
                    }
                });
            }
        },
        //通过固定盈利率得到倍数
        //yinglilv 指定要达到的盈利率
        //userGroupMoney 单注中奖金额
        //amount 单笔投注成本
        //amountAll 累计投注成本
        getMultipleByYinglilv: function (yinglilv, userGroupMoney, amount, amountAll) {
            //尝试倍数达到盈利率
            var i = 1,
                mx = 100000;
            for (; i < mx; i++) {
                if ((userGroupMoney * i - amount * i - amountAll) / (amount * i + amountAll) >= yinglilv) {
                    //console.log((userGroupMoney*i - amount*i - amountAll)/(amount*i + amountAll));
                    return i;
                }
            }
            //无法达到目标盈利率
            return -1;
        },
        //通过固定盈利金额得到倍数
        //userGroupMoney 单注中奖金额
        //yingliMoney 需要达到的盈利金额
        //amount 单笔投注成本
        //amountAll 累计投注成本
        getMultipleByMoney: function (userGroupMoney, yingliMoney, amount, amountAll) {
            var i = 1,
                mx = 100000;
            for (; i < mx; i++) {
                if ((userGroupMoney * i - amountAll - amount * i) > yingliMoney) {
                    return i;
                }
            }
            //无法达到目标
            return -1;
        },
        //根据玩法名称获得用户当前将进组中奖金额(以元模式为单位)
        //
        getPlayGroupMoneyByGameMethodName: function (name) {
            var groupMoney = null;
            try {
                Games.getCurrentGame().getDynamicConfig()['gamelimit'][name]['usergroupmoney'];
            } catch (err) {
            }

            var type = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
                url = Games.getCurrentGame().getGameConfig().getInstance().getBetAwardUrl() + '?type=' + type + '&extent=currentFre&line=5&lenth=30';

            if (groupMoney && Number(groupMoney) > 0) {
                return groupMoney;
            } else if (Games.cacheData['gameBonus'][url] && Games.cacheData['gameBonus'][url] > 0) { //当前奖期结束取缓存中的奖金
                return Number(Games.cacheData['gameBonus'][url]);
            } else {
                alert(Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(name) + '玩法类型奖金组读取失败，请刷新页面重试！');

            }
        },
        formatRow: function (tpl, data) {
            var me = this,
                o = data,
                p, reg;
            for (p in o) {
                if (o.hasOwnProperty(p)) {
                    reg = RegExp('<#=' + p + '#>', 'g');
                    tpl = tpl.replace(reg, o[p]);
                }
            }
            return tpl;
        },
        //将数字保留两位小数并且千位使用逗号分隔
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
        getAdvancedTable: function () {
            var me = this;
            return me._advancedTable || (me._advancedTable = $('#J-trace-table-advanced'));
        },
        getAdvancedRowTable: function () {
            var me = this;
            return me._advancedTableContainer || (me._advancedTableContainer = $('#J-trace-table-advanced-body'));
        },
        getNormalTable: function () {
            var me = this;
            return me._table || (me._table = $('#J-trace-table'));
        },
        getNormalRowTable: function () {
            var me = this;
            return me._tableContainer || (me._tableContainer = $('#J-trace-table-body'));
        },
        getTable: function () {
            var me = this;
            if (me.isAdvanced()) {
                return me._advancedTable || (me._advancedTable = $('#J-trace-table-advanced'));
            }
            return me._table || (me._table = $('#J-trace-table'));
        },
        getRowTable: function () {
            var me = this;
            if (me.isAdvanced()) {
                return me._advancedTableContainer || (me._advancedTableContainer = $('#J-trace-table-advanced-body'));
            }
            return me._tableContainer || (me._tableContainer = $('#J-trace-table-body'));
        },
        setCurrentTraceNumber: function (v) {
            var me = this;
            me.currentTraceNumber = v;
        },
        getCurrentTraceNumber: function () {
            return me.currentTraceNumber;
        },
        //追号起始期号
        setTraceStartNumber: function (v) {
            var me = this;
            me.traceStartNumber = v;
        },
        getTraceStartNumber: function () {
            return me.traceStartNumber;
        },
        getMultiple: function () {
            var me = this;
            if (me.isAdvanced()) {
                return me.getAdvancedMultiple();
            }
            return me.getNormalMultiple();
        },
        getNormalMultiple: function () {
            return Number(this.normalSelectMultiple.getValue());
        },
        getAdvancedMultiple: function () {
            return Number($('#J-trace-advance-multiple').val());
        },
        setIsWinStop: function (v) {
            var me = this;
            this.isWinStop = !!v;
        },
        getTimes: function () {
            var me = this;
            if (me.isAdvanced()) {
                return me.getAdvancedTimes();
            }
            return Number($('#J-trace-normal-times').val());
        },
        //获取追号期数(高级)
        getAdvancedTimes: function () {
            return Number($('#J-trace-advanced-times').val());
        },
        //是否为高级追号
        isAdvanced: function () {
            var me = this;
            return me.traceType == 'advanced';
        },
        //切换追号类型
        //普通追号 'normal' 或 为空
        //高级追号 'advanced'
        setTraceType: function (type) {
            var me = this;
            if (type != me.traceType) {
                type = !type ? 'normal' : type;
                me.traceType = type;
            }
        },
        getTraceType: function () {
            return this.traceType;
        },
        //获取已生成列表的追号类型
        getRowTableType: function () {
            var me = this;
            return me.getRowTable().attr('data-type');
        },
        //清空已生成的列表
        emptyRowTable: function () {
            var me = this;
            $('#J-trace-table-body').html('');
            $('#J-trace-table-advanced-body').html('');
            me.updateStatistics();
        },
        show: function () {
            var me = this;
            //展开追号区后修改所有投注倍数为1倍
            Games.getCurrentGameOrder().editMultiples(1);
            me.setOrderData(Games.getCurrentGameOrder().getTotal());
            //展开后隐藏选球区域的倍数选择框
            Games.getCurrentGameStatistics().getMultipleDom().hide();
            Games.getCurrentGameStatistics().getMultipleTextDom().show();

            me.panel.show();
            me.isOpenPanel = true;

            //渲染表格
            me.buildDetail();
        },
        hide: function () {
            var me = this;
            Games.getCurrentGameOrder().restoreMultiples();
            //收起后显示选球区域的倍数选择框
            Games.getCurrentGameStatistics().getMultipleDom().show();
            Games.getCurrentGameStatistics().getMultipleTextDom().hide();

            me.panel.hide();
            me.isOpenPanel = false;

            me.reSetTab();

            me.emptyRowTable();
            //追号状态复位
            $('#J-trace-switch').get(0).checked = false;
            $('#J-trace-iswinstop-panel').hide();
            $('#J-trace-iswintimesstop-panel').show();
            $('#J-trace-iswintimesstop').get(0).checked = false;
            this.traceTips.hide();

        },
        //复位追号区的tab以及相关输入框默认值
        reSetTab: function () {
            var me = this,
                tab1 = me.TraceTab,
                tab2 = me.TraceAdvancedTab;
            //追号tab
            tab1.triggers.removeClass(tab1.defConfig.currClass);
            tab1.triggers.eq(0).addClass(tab1.defConfig.currClass);
            tab1.panels.removeClass(tab1.defConfig.currPanelClass);
            tab1.panels.eq(0).addClass(tab1.defConfig.currPanelClass);
            tab1.index = 0;
            //高级追号tab
            tab2.triggers.removeClass(tab2.defConfig.currClass);
            tab2.triggers.eq(0).addClass(tab2.defConfig.currClass);
            tab2.panels.removeClass(tab2.defConfig.currPanelClass);
            tab2.panels.eq(0).addClass(tab2.defConfig.currPanelClass);
            tab2.index = 0;

            //恢复输入框默认值
            $('#J-trace-normal-times').val(10);
            $('#J-function-select-tab .function-select-title li').removeClass('current').eq(1).addClass('current');
            me.normalSelectMultiple.setValue(1);

            $('#J-trace-advanced-times').val(10);
            $('#J-trace-advance-multiple').val(1);
            $('#J-trace-advanced-fanbei-a-jiange').val(2);
            $('#J-trace-advanced-fanbei-a-multiple').val(2);
            $('#J-trace-advanced-fanbei-b-num').val(5);
            $('#J-trace-advanced-fanbei-b-multiple').val(3);
            $('#J-trace-advanced-yingli-a-money').val(100);
            $('#J-trace-advanced-yingli-b-num').val(2);
            $('#J-trace-advanced-yingli-b-money1').val(100);
            $('#J-trace-advanced-yingli-b-money2').val(50);
            $('#J-trace-advanced-yinglilv-a').val(10);
            $('#J-trace-advanced-yinglilv-b-num').val(5);
            $('#J-trace-advanced-yingli-b-yinglilv1').val(30);
            $('#J-trace-advanced-yingli-b-yinglilv2').val(10);


            //设置对应的tab标记属性
            me.setTraceType('normal');
            me.advancedType = me.defConfig.advancedTypeHas[0];
            me.typeTypeType = 'a';

            //恢复默认的高级选项
            $('#J-trace-advanced-type-panel').find('input[type="radio"]').each(function (i) {
                if ((i + 1) % 2 != 0) {
                    var el = $(this),
                        par = el.parent(),
                        pars = par.parent().children(),
                        _par;
                    this.checked = true;
                    pars.each(function (i) {
                        _par = pars.get(i);
                        if (par.get(0) != _par) {
                            $(_par).find('input[type="text"]').attr('disabled', 'disabled');
                        } else {
                            $(_par).find('input[type="text"]').attr('disabled', false);
                        }
                    });
                }
            });

        }
    };

    var Main = host.Class(pros, Event);
    Main.defConfig = defConfig;
    Main.getInstance = function (cfg) {
        return instance || (instance = new Main(cfg));
    };
    host[name] = Main;

})(phoenix, "GameTrace", phoenix.Event);

//游戏订单模块
(function (host, name, Event, undefined) {
    var defConfig = {
            //游戏数据提交地址
            /* URL: 'saveGameOrder',
             //手续费获取地址
             handlingChargeURL: 'jsonResult/handlingCharge'*/
            URL: '',
            //手续费获取地址
            handlingChargeURL: ''
        },
    //缓存游戏实例
        instance,
    //获取游戏类
        Games = host.Games;

    var pros = {
        //初始化
        init: function (cfg) {
            var me = this,
                cfg = me.defConfig;
            //me.URL= Games.getCurrentGame().getGameConfig().getInstance().submitUrl();
            //手续费获取地址
            //me.handlingChargeURL= Games.getCurrentGame().getGameConfig().getInstance().getHandingChargeUrl();

            //提交数据加锁
            //防止多次重复提交
            me.postLock = null;

            //缓存方法
            Games.setCurrentGameSubmit(me);
            //定单提交后解锁，按扭恢复
            me.addEvent('afterSubmit', function () {
                me.cancelPostLock();
                // $('.confirm').html('确认');
            });

        },
        //获取当前投注信息
        //提交的数据标准格式
        /**
         result = {
        //游戏类型
        gameType:'ssc',
        //订单总金额
        amount:100,
        //是否是追号
        isTrace:1,
        //追号追中即停(1为按中奖次数停止，2为按中奖金额停止)
        traceWinStop:1,
        //追号追中即停的值
        traceStopValue:1,
        //选球信息
        balls:[{ball:'1,2,3,4',type:'wuxing.zhixuan.fushi',moneyunit:0.1,multiple:1,id:2,fileMode:1},{ball:'选球数据',type:'玩法类型',moneyunit:元角模式,multiple:倍数,id:ID编号}],
        //投注信息
        orders:[{number:'201312122204',multiple:2},{number:'期号',multiple:倍数}]

        };
         **/
        //将数字保留两位小数并且千位使用逗号分隔
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
        getSubmitData: function () {
            var me = this, result = {},
                ballsData = Games.getCurrentGameOrder()['orderData'],
                i = 0,
                len = ballsData.length,
                traceInfo = Games.getCurrentGameTrace().getResultData(),
                j = 0,
                len2 = traceInfo['traceData'].length;


            result['gameType'] = Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"];//改取config.js值,
            result['isTrace'] = traceInfo['traceData'].length > 0 ? 1 : 0;
            result['traceWinStop'] = Games.getCurrentGameTrace().getIsWinStop();
            result['traceStopValue'] = Games.getCurrentGameTrace().getTraceWinStopValue();
            result['balls'] = [],
                result['ordersNumber'] = '';  //期号显示（普通投注与追号）
            for (; i < len; i++) {
                result['balls'].push({
                    'id': ballsData[i]['id'],
                    'ball': ballsData[i]['postParameter'],
                    'type': ballsData[i]['type'],
                    'moneyunit': ballsData[i]['moneyUnit'],
                    'multiple': ballsData[i]['multiple'],
                    'awardMode': ballsData[i]['awardMode'],
                    'num': ballsData[i]['num']//传递投注数
                });
            }
            result['orders'] = [];
            //非追号
            if (result['isTrace'] < 1) {
                //获得当前期号
                result['orders'].push({
                    'number': Games.getCurrentGame().getCurrentNumber(),
                    'issueCode': Games.getCurrentGame().getCurrentIssueCode(),
                    multiple: 1
                });
                //总金额
                result['amount'] = Games.getCurrentGameStatistics().formatMoney(Games.getCurrentGameOrder().getTotal()['amount']);
                result['ordersNumber'] = '第 ' + Games.getCurrentGame().getCurrentNumber() + ' 期'; //
                result['onlyAmount'] = '';
            } else {
                //追号
                for (; j < len2; j++) {
                    result['orders'].push({
                        'number': traceInfo['traceData'][j]['traceNumber'],
                        'issueCode': Number(traceInfo['traceData'][j]['issueCode']),
                        'multiple': traceInfo['traceData'][j]['multiple']
                    });
                }
                result['ordersNumber'] = "第 " + result['orders'][0]['number'] + "  至  " + result['orders'][len2 - 1]['number'] + "   共  <strong class='color-red'>" + len2 + "</strong></span>  期";
                //总金额
                result['amount'] = Games.getCurrentGameStatistics().formatMoney(traceInfo['amount'], 2);
                //单期金额
                result['onlyAmount'] = traceInfo.orderData['amount'];
            }

            return result;
        },
        //执行请求锁定动作
        doPostLock: function () {
            var me = this;
            me.postLock = true;
        },
        //取消请求锁定动作
        cancelPostLock: function () {
            var me = this;
            me.postLock = null;
        },
        //清空数据缓存
        clearData: function () {
            var order = Games.getCurrentGameOrder();
            //清空订单
            order.reSet();
            //添加取消编辑
            order.cancelSelectOrder();
            //清空
            Games.getCurrentGame().getCurrentGameMethod().reSet();
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
                        if ($.isEmptyObject(re)) {
                            return;
                        } else if (phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"] == 'jsk3' || phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["gameType"] == 'ahk3') {
                            //K3中我的追号与其它游戏模板不同
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
                                htmls += "<td>" + (re[i].parentType == 2 ? '是' : '否') + "</td>";
                                htmls += "<td>" + re[i].formatSaleTime + "</td>";
                                htmls += "<td><a target='_black' href='/gameUserCenter/queryOrderDetail?orderId=" + re[i].orderId + "' >查看</a></td></tr>";
                            });
                        }
                        else {
                            $('#program-user-orders > tbody').html('');
                            $.each(re, function (i) {
                                htmls += "<tr><td><a target=\"_black\" href=\"/gameUserCenter/queryOrderDetail?orderId=" + re[i].orderId + "\"> " + re[i].webIssueCode + "</a></td>";
                                htmls += "<td><span class='price'><dfn>&yen;</dfn>" + me.formatMoney(re[i].totamount / 10000) + "</span></td>";

                                if (Number(re[i].status) == 2) {
                                    htmls += "<td >中<span class='price color-red'><dfn>&yen;</dfn>" + me.formatMoney(re[i].totwin / 10000) + "</td></span></tr>";
                                } else {
                                    htmls += "<td >" + re[i].statusName + "</td></tr>";
                                }

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
                        if ($.isEmptyObject(re)) {
                            return;
                        } else {
                            $('#program-user-plans > tbody').html('');
                            $.each(re, function (i) {
                                htmlb += "<tr><td><a target=\"_black\" href=\"/gameUserCenter/queryPlanDetail?planid=" + re[i].planid + "\"> " + re[i].startWebIssueCode + "</a></td>";
                                htmlb += "<td>" + Number(re[i].finishIssue) + "/" + Number(re[i].totalIssue) + "</td>";
                                htmlb += "<td><span class='price'><dfn>&yen;</dfn>" + me.formatMoney(re[i].totamount / 10000) + " </span></td>";
                                switch (re[i].status) {
                                    case 0:
                                        htmlb += "<td >未开始</td></tr>";
                                        break;
                                    case 1:
                                        htmlb += "<td >进行中</td></tr>";
                                        break;
                                    case 2:
                                        htmlb += "<td >已结束</td></tr>";
                                        break;
                                    case 3:
                                        htmlb += "<td >已终止</td></tr>";
                                        break;
                                    case 4:
                                        htmlb += "<td >暂停</td></tr>";
                                        break;
                                    default:
                                        htmlb += "<td ></td></tr>";
                                        break;
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
        //余额查询
        balanceInquiry: function (htmlObj) {
            $.ajax({
                dataType: 'json',
                cache: false,
                url: Games.getCurrentGame().getGameConfig().getInstance().getUserBalUrl(),
                data: '',
                success: function (data) {
                    var _userBall = data == '' ? 0 : data,
                        _userBallF = phoenix.util.formatMoney(_userBall / 10000);
                    $("#" + htmlObj).html(_userBallF);

                }
            });
        },
        //提交游戏数据
        submitData: function () {
            var me = this,
                data = me.getSubmitData(),
                message = Games.getCurrentGameMessage();

            //提示至少选择一注
            if (data.balls.length <= 0) {
                message.show({
                    type: 'mustChoose',
                    msg: '请至少选择一注投注号码！',
                    data: {
                        tplData: {
                            msg: '请至少选择一注投注号码！'
                        }
                    }
                });
                //请求解锁
                me.cancelPostLock();
                return;
            }
            var isTure = true;
            //彩种检查
            message.show({
                type: 'checkLotters',
                msg: '请核对您的投注信息！',
                confirmIsShow: true,
                confirmFun: function () {
                    //判断加锁
                    if (me.postLock) {
                        return;
                    } else {
                        //加锁
                        me.doPostLock();
                        me.fireEvent('beforeSubmit');
                    }
                    data.ordersNumber = undefined; //去除ordersNumber，后台不接收
                    data.onlyAmount = undefined;
                    data.amount = phoenix.util.formatFloat(data.amount);

                    $.ajax({
                        url: Games.getCurrentGame().getGameConfig().getInstance().submitUrl(),
                        data: JSON.stringify(Games.getCurrentGame().editSubmitData(data)),
                        dataType: 'json',
                        method: 'POST',
                        cache: false,
                        contentType: "application/json; charset=utf-8",
                        beforeSend: function () {
                            if (isTure) {
                                $('.confirm').html('提交中...');
                                $('.cancel').css('display', 'none').attr("disabled", "true");
                                isTure = false;
                            } else {
                                return false;
                            }
                        },
                        success: function (r) {
                            //返回消息标准
                            // {"isSuccess":1,"type":"消息代号","msg":"返回的文本消息","data":{xxx:xxx}}
                            if (Number(r['isSuccess']) == 1) {

                                //推荐查询地址
                                var dataTrace = Number(data['isTrace']) > 0 ? 1 : 0;

                                if (dataTrace > 0) {
                                    r.data.tplData["url"] = "/gameUserCenter/queryPlans?time=7";
                                    r.data.tplData["gameType"] = '追号记录';

                                } else {
                                    r.data.tplData["url"] = "/gameUserCenter/queryOrdersEnter?time=7";
                                    r.data.tplData["gameType"] = '投注记录';
                                }

                                r.needPrint = true;
                                message.show(r);
                                //添加打印按钮 richardgong 2015-07-23
                                message.win.dom.find(".btn-link").remove();

                                message.win.dom.append($('<a  target="view_window" href="/gameBet/downLoadOrder?orderCode=' + r.data.projectId + '" class="btn btn-link">打印订单</a>'));

                                me.clearData();
                                me.balanceInquiry('spanBall');
                                me.afterSubmitSuccess();
                                setTimeout(function () {
                                    Games.getCurrentGameTrace().hide();
                                    $("html,body").animate({scrollTop: $("#J-top-game-menu").offset().top}, 1000);
                                }, 1500);

                            } else {
                                message.show(r);
                            }

                        },
                        complete: function () {
                            me.fireEvent('afterSubmit');
                            isTure = true;
                            $('.cancel').css('display', 'display').removeAttr("disabled");
                        },
                        error: function (xhr) {
                            if (xhr.status == '404' || xhr.status == '0' || xhr.status == '401') {
                                message.show({
                                    mask: true,
                                    title: '温馨提示',
                                    content: "<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>登录超时 请重新登入！</h4></div>",
                                    cancelIsShow: true,
                                    cancelFun: function () {
                                        window.location.reload();
                                    }
                                });
                            } else {
                                message.show({
                                    mask: true,
                                    title: '温馨提示',
                                    content: "<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>方案提交失败,<br />请检查网络并重新提交！</h4></div>",
                                    cancelIsShow: true,
                                    cancelFun: function () {
                                        message.hide();
                                    }
                                });
                            }

                        }
                    });
                },
                cancelIsShow: true,
                cancelFun: function () {
                    //请求解锁
                    me.cancelPostLock();
                    this.hide();
                },
                normalCloseFun: function () {
                    //请求解锁
                    me.cancelPostLock();
                },
                callback: function () {

                    var backOutStartFee = Number(Games.getCurrentGame().getGameConfig().getInstance().defConfig["backOutStartFee"]) / 10000,//返手续费起始金额
                        backOutRadio = Number(Games.getCurrentGame().getGameConfig().getInstance().defConfig["backOutRadio"]) / 10000,//返金额率
                        backAmount = Games.getCurrentGameStatistics().ToForamtmoney(data['amount']);

                    if (backAmount > backOutStartFee) {
                        $('#showLotteryChareg').show();
                        message.getContentDom().find('.handlingCharge').html(Games.getCurrentGameStatistics().formatMoney(backAmount * backOutRadio, 3));

                    }

                },
                data: {
                    tplData: {
                        //当期彩票详情
                        lotteryDate: data['ordersNumber'],
                        //彩种名称
                        lotteryName: Games.getCurrentGame().getGameConfig().getInstance().getGameTypeCn(),
                        //投注详情
                        lotteryInfo: function () {
                            var html = '',
                                balls = data['balls'];
                            for (var i = 0; i < balls.length; i++) {
                                var current = balls[i];
                                html += '<div style="line-height:25px;">' + Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(current['type']).join('') + ' ' + current.ball + '</div>';
                            }
                            ;
                            return html;
                        },
                        //彩种金额
                        lotteryamount: data['amount'],
                        //付款帐号
                        lotteryAcc: Games.getCurrentGame().getDynamicConfig()['username'],

                        lotteryOnlyAmount: data['onlyAmount'],

                        lotteryTraceInfo: function () {
                            var html = '';
                            if (data['isTrace'] > 0) {
                                html = data['orders'];
                            }
                            return html;
                        }


                    }
                }
            });
        }
    };

    var Main = host.Class(pros, Event);
    Main.defConfig = defConfig;
    Main.getInstance = function (cfg) {
        return instance || (instance = new Main(cfg));
    };
    host[name] = Main;

})(phoenix, "GameSubmit", phoenix.Event);










