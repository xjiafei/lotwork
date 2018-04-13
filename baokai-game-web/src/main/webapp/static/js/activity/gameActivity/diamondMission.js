(function ($, gameMission) {
    function F(t, o) {
        this.client = new gameMission.Client('SLMMC_DIAMOND_COLLECT');
        this.opts = $.extend({
            submitUrl: 'result.json',
            infoLink: userServer + '/ht/july/active1.html',
            barWidth: '201',
            btnClassMaps: ['gaming', 'prizing', 'complete'],
            //body html模板
            tpl_body:
                    '<div class="collectMission">' +
                    '<div class="collectBar">' +
                    '<a class="gameInstruction" target="_blank" href="{{link}}"></a>' +
                    '<a class="missionOpen" href="javascript:;"></a>' +
                    '</div>' +
                    '<div class="collectMain">' +
                    '<div class="collectBox prizes3">' +
                    '<div data-status="{{bar4}}" class="progressBar bar4"></div>' +
                    '<div data-status="{{bar3}}" class="progressBar bar3"></div>' +
                    '<p class="status">完成<span class="complete">{{times3}}</span>次，剩余<span class="surplus">{{surplus3}}</span>次，奖金 {{bonus3}} 元</p>' +
                    '<div class="btnBox">' +
                    '<p><span class="diamond diamond4">{{diamonds4}}</span>/{{diamondTimes4}}</p>' +
                    '<p><span class="diamond diamond3">{{diamonds3}}</span>/{{diamondTimes3}}</p>' +
                    '<a data-level="{{level3}}" class=" {{btnStatus3}} btn" href="javascript:;"></a>' +
                    '</div>' +
                    '</div>' +
                    '<div class="collectBox prizes2">' +
                    '<div data-status="{{bar2}}" class="progressBar bar2"></div>' +
                    '<div data-status="{{bar1}}" class="progressBar bar1"></div>' +
                    '<p class="status">完成<span class="complete">{{times2}}</span>次，剩余<span class="surplus">{{surplus2}}</span>次，奖金 {{bonus2}} 元</p>' +
                    '<div class="btnBox">' +
                    '<p><span class="diamond diamond2">{{diamonds2}}</span>/{{diamondTimes2}}</p>' +
                    '<p><span class="diamond diamond1">{{diamonds1}}</span>/{{diamondTimes1}}</p>' +
                    '<a data-level="{{level2}}" class=" {{btnStatus2}} btn" href="javascript:;"></a>' +
                    '</div>' +
                    '</div>' +
                    '<div class="collectBox prizes1">' +
                    '<div data-status="{{bar0}}" class="progressBar bar0"></div>' +
                    '<p class="status">完成<span class="complete">{{times1}}</span>次，剩余<span class="surplus">{{surplus1}}</span>次，奖金 {{bonus1}} 元</p>' +
                    '<div class="btnBox">' +
                    '<p><span class="diamond diamond0">{{diamonds0}}</span>/{{diamondTimes0}}</p>' +
                    '<a data-level="{{level1}}" class=" {{btnStatus1}} btn" href="javascript:;"></a>' +
                    '</div>' +
                    '</div>' +
                    '<a class="missionClose" href="javascript:;"></a>' +
                    '<a class="gameInstruction" target="_blank" href="{{link}}"></a>' +
                    '</div>' +
                    '</div>'
        }, o);
        this.$t = $(t);
        this.debugs = this.opts.debugs;
        this.missions = {};
        this.dfd = new defferred();
    }
    
    function defferred(){
        var callbacks = [];
        var dfd =  {
            then:function(resolveCallback,rejectCallback){
                callbacks.push({resolveCallback:resolveCallback,rejectCallback:rejectCallback});
                return this;
            },
            resolve:function(args){
                var callback = callbacks.shift();
                if(callback&&callback.resolveCallback){
                    callback.resolveCallback(args);
                }
                return this;
            },
            reject:function(args){
                var callback = callbacks.shift();
                if(callback&&callback.rejectCallback){
                    callback.rejectCallback(args);
                }
                return this;
            }
        };
        return dfd;
    }
    
    F.prototype = {
        init: function () {
            var _this = this;
            var dfd = _this.dfd;
            dfd.then(function(){
                _this.queryMission();
            })
            .then(function(){
                _this.queryData();
            })
            .then(function(){
                _this.missionUIDataInit();
            })
            .then(function(){
                _this.missionUIinit();
            });
            dfd.resolve();
            return this;
        },
        queryMission: function () {
            var _this = this;
            var missions = _this.missions;
            _this.client.queryMissions(function (data) {
                if(data.length>0){
                    for (var i = 0; i <= 2; i++) {
                        var missionData = data[i];
                        missions['level' + (i + 1)] = {
                            level: missionData['itemSeq'], // 任务等级,
                            name: missionData['name'], //名称
                            bonus: missionData['params']['bonus'],//奖金
                            times: 0, // 奖品已经完成次数
                            missionTime: missionData['params']['missionTime'],// 奖品總次數
                            surplus: missionData['params']['missionTime'], // 奖品剩余次数
                            status: 0, // 按钮状态  0：正常 1：可领取 2：已完成
                        };
                    }
                    _this.dfd.resolve();
                }else{
                    _this.dfd.reject();
                }
            });
        },
        queryData: function () {
            var _this = this;
            var missions = _this.missions;
            _this.client.queryUserMissionData(function (data) {
                for (var i = 0; i <= 2; i++) {
                    var mission = missions['level' + (i + 1)];
                    var userMissionData = data[i];
                    var surplus = mission.missionTime - userMissionData['completeTimes'];
                    var isAllComplete = surplus<=0;
                    mission['times'] = userMissionData['completeTimes'];
                    mission['surplus'] = surplus;
                    var targets = userMissionData['targets'];
                    var diamonds = {};
                    var isFinish = true;
                    for (var j = 0; j < targets.length; j++) {
                        var target = targets[j];
                        var achieve = target['collectTimes'] >= target['needTime']||isAllComplete;
                        diamonds['diamonds' + target['diamond']] = {
                            ref: target['diamond'], // 0钻石 
                            achieve: achieve, //是否完成
                            number: achieve ? target['needTime'] : target['collectTimes'],  // 完成数量
                            total:target['needTime']
                        };
                        isFinish = isFinish && achieve;
                    }
                    mission['targets'] = diamonds;
                    if(isAllComplete){
                        mission['status'] = 2;
                    }else{
                        mission['status'] = isFinish ? 1 : 0;
                    }
                }
                _this.dfd.resolve();
            });
        },
        showPrize: function () {
            var _this = this;
            var missions = _this.missions;
            var dfd = _this.dfd;
            if(!$.isEmptyObject(missions)){
                dfd.then(function(){
                    _this.queryData();
                })
                .then(function(){
                    _this.missionUIDataInit();
                })
                .then(function(){
                    var $box = _this.$box;
                    var gameData = _this.gameData;
                    for(var key in gameData){
                        //更新收集钻石数量
                        $box.find('.diamond'+key).html(gameData[key]['number']);
                        //更新进度条
                        $box.find('.bar'+key).data('status', gameData[key]['bar']);
                    }
                    _this.showBar();
                    _this.changeBtnStatus();
                    _this.updateCompleteTimes();
                });
                dfd.resolve();
            }
        },
        // 初始化
        missionUIDataInit: function () {
            var _this = this;
            var data = _this.missions;
            var decorateData = {};
            this.gameData = [];
            $.each(data, function (n, v) {
                decorateData['level' + v.level] = v['level'];
                decorateData['times' + v.level] = v['times'];
                decorateData['surplus' + v.level] = v['surplus'];
                decorateData['bonus' + v.level] = v['bonus'];
                decorateData['btnStatus' + v.level] = _this.opts.btnClassMaps[v['status']];
                var targets = v['targets'];
                $.each(targets, function (n, v) {
                    decorateData[n] = v['number'];
                    decorateData['diamondTimes' + v.ref] = v['total'];
                    decorateData['bar' + v.ref] = v['number'] / v['total'];
                    _this.gameData[v.ref] = {number:v['number'],bar:decorateData['bar' + v.ref]};
                });
            });
            decorateData['link'] = this.opts.infoLink;
            this.decorateData = decorateData;
            _this.dfd.resolve();
        },
        missionUIinit: function(){
            var decorateData = this.decorateData;
            this.$t.append(this.render(decorateData, this.opts.tpl_body));
            this.$box = this.$t.find('.collectMission');
            this.showBar();
            this.eventInit();
        },
        eventInit: function () {
            var _this = this,
                    missions = _this.missions,
                    $box = this.$box || $('.collectMission');
            if ($box) {
                var $close = $box.find('.missionClose'),
                        $open = $box.find('.missionOpen'),
                        $btn = $box.find('.btn');
                $close.click(function () {
                    $box.addClass('close');
                });
                $open.click(function () {
                    $box.removeClass('close');
                });
                $btn.click(function () {

                    var data = {},
                            $this = $(this);
                    data.level = $this.data('level');
                    $this.addClass('not-activebtn');
                    if ($this.hasClass('complete'))
                        return;
                    _this.getResult(data, function (r) {
                        _this.showPrize();
                        if (Number(r) == 1) {
                            var mission = missions['level'+data.level];
                            var msg = mission.name+'完成，奖金'+mission.bonus+'元！';
                            phoenix.Games.getCurrentGameMessage().show({
                                type: 'normal',
                                closeText: '关闭',
                                closeFun: function () {
                                    this.hide();
                                },
                                data: {
                                    tplData: {
                                        msg: msg
                                    }
                                }
                            });
                        } else if(Number(r) == 2){
                            alert('你已经领过奖励');
                        } else if(Number(r) == 3){
                            alert('不符合领奖条件');
                        } else {
                            alert('连线异常');
                        }
                        $this.removeClass('not-activebtn');
                    });
                });
            }
        },
        getResult: function (data, callback) {
            var _this = this;
            _this.client.completeMissions(data.level, callback);
        },
        showBar: function () {
            var _this = this,
                    $bar = this.$box.find('.progressBar');
            $.each($bar, function (n, v) {
                var width = $(v).data('status') * _this.opts.barWidth;
                $(v).animate({width: width}, 800);
            });
        },
        changeBtnStatus: function(){
            var _this = this;
            var $box = _this.$box;
            var missions = _this.missions;
            var classMaps = _this.opts.btnClassMaps;
            for (var i = 0; i <= 2; i++) {
                var mission = missions['level' + (i + 1)]
                var btn = $box.find('.prizes' + mission['level'] + ' .btn');
                $.each(classMaps, function (n, v) {
                    btn.removeClass(v);
                });
                btn.addClass(classMaps[mission['status']]);
            }
        },
        updateCompleteTimes: function(){
            var _this = this;
            var $box = _this.$box;
            var missions = _this.missions;
            for (var i = 0; i <= 2; i++) {
                var mission = missions['level' + (i + 1)];
                var $completeTime = $box.find('.prizes' + mission['level'] + ' .complete');
                var $totalTime = $box.find('.prizes' + mission['level'] + ' .surplus'); 
                $completeTime.text(mission['times']);
                $totalTime.text(mission['surplus']);
            }
            
        },
        render: function (data, tpl) {
            var _this = this,
                    markup = tpl;
            for (p in data) {
                markup = _this.template(markup, p, data[p]);
            }
            return markup;
        },
        template: function (markup, key, value, reg) {
            reg = reg || RegExp('{{' + key + '}}', 'g');
            return markup.replace(reg, value);
        }

    }

    $.fn.collectDiamonds = function (o) {
        var instance;
        this.each(function () {
            instance = $.data(this, 'collectDiamonds');
            if (instance) {

            } else {
                instance = $.data(this, 'collectDiamonds', new F(this, o));
            }
        });
        return instance;
    }
})(jQuery, GameMission);