/**
 * Created by user on 16/3/22.
 */

//游戏类
(function(host, name, Event, undefined){
    var defConfig = {
            //游戏名称
            name:'',
            //资源存放目录
            basePath:'../game/',
            //文件名前缀
            baseNamespace:'phoenix.Games.',
            //游戏后台配置
            dynamicConfigUrl:'simulatedata/dynamicConfig.php',
            //添加事件代理的主面板
            eventProxyPanel:'body',
            colorNameMap: ['red','blue','green'],
            colorArr: [0,0,1,1,2,2,0,0,1,1,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,0,1,2,2,0,0,1,1,2,2,0,1,1,2,2,0,0,1,1,2]
        },
        Games = host.Games;
    //将来仿url类型的参数转换为{}对象格式，如 q=wahaha&key=323444 转换为 {q:'wahaha',key:'323444'}
    //所有参数类型均为字符串
    var formatParam = function(param){
        var arr = $.trim(param).split('&'),i = 0,len = arr.length,
            paramArr,
            result = {};
        for(;i < len;i++){
            paramArr = arr[i].split('=');
            if(paramArr.length > 0){
                if(paramArr.length == 2){
                    result[paramArr[0]] = paramArr[1];
                }else{
                    result[paramArr[0]] = '';
                }
            }
        }
        return result;
    };


    var pros = {
        init:function(cfg){
            var me = this;
            me.setName(cfg.name);
            //当前期号web
            me.currentNumber = '';
			//当前期号
            me.currentIssueCode = '';
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

            me.addEvent('afterSwitchGameMethod', function(){
                //重置加载缓存标记
                me.methodLoadCache = false;
                //将投注栏数据渲染到号码栏
                var nowMethod = Games.getCurrentGame().getCurrentGameMethod(),
                    nowData = Games.getCurrentGameOrder().getOrderData();
                nowMethod && nowMethod.orderToShow(nowData);
                //获取
                //Games.getCurrentGame().getCurrentGameMethod().updataGamesInfo();

                me.promptInit();
                // 切换玩法时，清空（复式，胆拖）预选球
                nowMethod.defConfig.preSelect = {};
                nowMethod.fireEvent('updatePreSelect');
            });
            me.addEvent('changeDynamicConfig', function(){
                me.updateDynamicConfig();
            });
        },
        //获取当前玩法/指定玩法的最大倍数限制
        getMaxMultipleLimit:function(type){
            var typeName = type || Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
                limitObj = Games.getCurrentGame().getDynamicConfig()['gamelimit'];
            return Number(limitObj[typeName]['maxmultiple']);
        },
        getDynamicConfigUrl:function(){
            return Games.getCurrentGame().getGameConfig().getInstance().getDynamicConfigUrl();
        },
        //从服务器端获取数据
        //返回数据格式
        //{"isSuccess":1,"type":"消息代号","msg":"返回的文本消息","data":{xxx:xxx}}
        getServerDynamicConfig:function(callback){
            var me = this,cfg = me.defConfig,gameType = me.getName();
            $.ajax({
                url:me.getDynamicConfigUrl() + '?gametype=' + gameType,
                dataType:'JSON',
                success:function(data){
                    if(Number(data['isSuccess']) == 1){
                        me.setDynamicConfig(data['data']);
                        if($.isFunction(callback)){
                            callback.call(me, data['data']);
                        }
                    }
                }
            });
        },
        getDynamicConfig:function(){
            return this.dynamicConfig;
        },
        setDynamicConfig:function(cfg){
            this.dynamicConfig = cfg;
            this.fireEvent('changeDynamicConfig', cfg);
        },
        addDynamicBonus:function(gameName, data){
            if(typeof this['dynamicConfig']['gamelimit'] != 'undefined'){
                if(typeof this['dynamicConfig']['gamelimit'][gameName] != 'undefined'){
                    this['dynamicConfig']['gamelimit'][gameName]['usergroupmoney'] = data;
                }
            }
        },
        //更新后台配置信息后，更新相关内容
        updateDynamicConfig:function(){
            var me = this,dConfig = me.getDynamicConfig(),lastballs = dConfig['lastballs'].split(',');

            if(dConfig['isstop'] == 1){
                setTimeout(function(){
                    phoenix.Games.getCurrentGameMessage().show({
                        type : 'lotteryClose',
                        data : {
                            tplData : {
                                //当期彩票详情
                                lotterys : [1,2,3,4,5,6],
                                //彩种名称
                                lotteryName : 'liuhecai',
                                //开奖期数
                                lotteryPeriods : '20130528-276',
                                //开始购买时间
                                orderDate : {'year':'2013','month':'5','day':'3','hour':'1','min':'30'},
                                //提示彩票种类
                                lotteryType : [{'name':'leli','pic':'#','url':'http://163.com'},{'name':'kuaile8','pic':'#','url':'http://pp158.com'}]
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
            //开奖日
            $('#J-lottery-info-day').html(dConfig['lotteryday']);


            //上一期期号
            $('#J-lottery-info-lastnumber').html(dConfig['lastnumber']);
            //上期开奖号码
            $('#J-lottery-info-balls').find('em').each(function(i){
                this.innerHTML = lastballs[i];
                $(this).attr('class',defConfig.colorNameMap[defConfig.colorArr[Number(lastballs[i])-1]]);
            });

            //新奖期已开出，清空数据缓存
            Games.cacheData = {};
        },
        //事件代理，默认只监听鼠标点击事件，如需要监听其他事件，请在具体的游戏类中实现
        //例： <span data-param="action=doSelect&value=10">点击</span>
        eventProxy:function(){
            var me = this,cfg = me.defConfig,panel = $(cfg.eventProxyPanel),
                action = '';
            panel.click(function(e){
                var q = e.target.getAttribute('data-param'),param,gameMethod;
                var parentData = $(e.target).parent().attr('data-param'),
                    $target = $(e.target);
                if(parentData && $.trim(parentData) != '') {
                    q = parentData;
                    $target = $(e.target).parent();
                }
                if(q && $.trim(q) != ''){
                    e.preventDefault();
                    param = formatParam(q);
                    gameMethod = me.getCurrentGameMethod();
                    if(gameMethod){
                        gameMethod.exeEvent(param, $target);
                    }
                }
            });
        },
        keyboardEventProxy:function(){
            var me = this,cfg = me.defConfig,panel = $(cfg.eventProxyPanel);
            panel.keyup(function(e){
                var q = e.target.getAttribute('data-param'),param,gameMethod,
                    $target = $(e.target);
                if(q && $.trim(q) != ''){
                    e.preventDefault();
                    param = formatParam(q);
                    gameMethod = me.getCurrentGameMethod();
                    if(gameMethod){
                        gameMethod.exeEvent(param, $target);
                    }
                }
            });
        },
        //根据名字返回玩法对象
        getGameMethodByName:function(name){
            var me = this,has = me.loadedHas;
            if(me.hasOwnProperty(name) && has.hasOwnProperty(me.buildPath(name))){
                return me[name];
            }
        },
        //切换游戏玩法
        switchGameMethod:function(name){
			console.log('name= ' +name);
            var me = this,p,has = me.loadedHas,obj;

            if(me.methodLoadCache){
                return;
            }
            //记录当前加载缓存
            //防止用户多次点击玩法加载按钮
            me.methodLoadCache = true;
            me.fireEvent('beforeSwitchGameMethod');
            for(p in me){
                if(me.getGameMethodByName(p)){
                    if(p != name){
                        me[p].hide();
                    }else{
                        me[p].show();
                        me.currentGameMethod = me[p];
                        me.fireEvent('afterSwitchGameMethod');
                    }
                }
            }
            if(!me.getGameMethodByName(name)){
                me.setup(name, function(){
                    obj = me.getGameMethodByName(name);
                    obj.show();
                    me.currentGameMethod = obj;
                    me.fireEvent('afterSetup');
                    me.fireEvent('afterSwitchGameMethod');
                });
            }
        },
        getCurrentGameMethod:function(){
            return this.currentGameMethod;
        },
        //name 玩法类型.玩法组.玩法(如:'wuxing.zhixuan.fushi')
        setup:function(name, callback){
            var me = this,
                src = me.buildPath(name),
                fn = function(){},
                _callback;

            //获取最后一个参数作为回调函数
            _callback = arguments.length > 0 ? arguments[arguments.length - 1] : fn;
            if(!$.isFunction(_callback)){
                _callback = fn;
            }

            //加载脚本并缓存
            if(!me.isSetuped(name)){
                $.ajax({
                    url:src,
                    cache:false,
                    dataType:'script',
                    success:function(){
                        me.loadedHas[src] = true;
                        _callback.call(me);
                    },
                    error:function(xhr, type,errorThrown){
                        alert('资源加载失败\n' + src + '\n错误类型：' + type);
                        console.log(errorThrown);
                    }
                });
            }
        },
        //拼接路径
        buildPath:function(name){
            var me = this,
                path = me.defConfig.basePath,
                nameSpace = me.defConfig.baseNamespace,
            //拼接名称为路径，并剔除空参数(空参数为了适应没有三级分组的游戏)
                src = path + nameSpace + name + '.js';
            return src;
        },
        //检测某模块是否已安装
        isSetuped:function(name){
            var me = this,has = me.loadedHas,path = me.buildPath(name);
            return has.hasOwnProperty(path);
        },

        //返回该游戏的游戏配置
        //在子类中实现
        getGameConfig:function(){

        },
        getName:function(){
            return this.name;
        },
        setName:function(name){
            this.name = name
        },
        setCurrentNumber:function(v){
            this.currentNumber = v;
        },
        getCurrentNumber:function(){
            return this.currentNumber;
        },
		setCurrentIssueCode:function(v){
            this.currentIssueCode = v;
        },
        getCurrentIssueCode:function(){
            return this.currentIssueCode;
        },
        //对最后即将进行提交的数据进行处理
        editSubmitData:function(data){
            return data;
        },

        promptInit: function(){
            //玩法说明和示例
            $('.prompt-example').off('mouseenter').on('mouseenter', function(){
                var $this = $(this);
                $this.parent().find('.prompt-text').css({
                    top : $this.position().top + 26,
                    left : $this.position().left + $this.width()/2
                }).show();
            }).off('mouseleave').on('mouseleave',function(){
                $(this).parent().find('.prompt-text').hide();
            });
            $('.rule-example').off('mouseenter').on('mouseenter', function(){
                var $this = $(this);
                $this.parent().find('.rule-text').css({
                    top : $this.position().top + 26,
                    left : $this.position().left + $this.width()/2
                }).show();
            }).off('mouseleave').on('mouseleave',function(){
                $(this).parent().find('.rule-text').hide();
            });

        }
    };

    var Main = host.Class(pros, Event);
    Main.defConfig = defConfig;
    host[name] = Main;

})(phoenix, "Game", phoenix.Event);
