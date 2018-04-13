(function (host, name, Event, undefined) {
    var gameConfigData = {
        "gameType": "slmmc",
        "gameTypeCn": "顺利秒秒彩",
        "defaultMethod": "housan.zhixuan.fushi",
        "lotteryId": 99112,
        "userLvl": 4,
        "userId": 1241100,
        "userName": "kang",
        "awardRetStatus": 0,
        "awardGroupRetStatus": 1,
        "lhcStatus": 1,
        "backOutStartFee": 0,
        "backOutRadio": 0,
        "isSupport2000": true,
        "isfirstimeuse2000": false,
        "isfirstimeusediamond2000":true,
        "gameMethods": [{
            "title": "五星",
            "name": "wuxing",
            "isdiamond": false,
            "isNew": 0,
            "childs": [{
                "title": "直选",
                "name": "zhixuan",
                "parent": "wuxing",
                "childs": [{"title": "复式", "name": "fushi", "parent": "zhixuan", "mode": "wuxing"}, {
                    "title": "单式",
                    "name": "danshi",
                    "parent": "zhixuan",
                    "mode": "wuxing"
                }]
            }, {
                "title": "组选",
                "name": "zuxuan",
                "parent": "wuxing",
                "childs": [{
                    "title": "组选120",
                    "name": "zuxuan120",
                    "parent": "zuxuan",
                    "mode": "wuxing"
                }, {"title": "组选60", "name": "zuxuan60", "parent": "zuxuan", "mode": "wuxing"}, {
                    "title": "组选30",
                    "name": "zuxuan30",
                    "parent": "zuxuan",
                    "mode": "wuxing"
                }, {"title": "组选20", "name": "zuxuan20", "parent": "zuxuan", "mode": "wuxing"}, {
                    "title": "组选10",
                    "name": "zuxuan10",
                    "parent": "zuxuan",
                    "mode": "wuxing"
                }, {"title": "组选5", "name": "zuxuan5", "parent": "zuxuan", "mode": "wuxing"}]
            }, {
                "title": "不定位",
                "name": "budingwei",
                "parent": "wuxing",
                "childs": [{
                    "title": "一码不定位",
                    "name": "yimabudingwei",
                    "parent": "budingwei",
                    "mode": "wuxing"
                }, {
                    "title": "二码不定位",
                    "name": "ermabudingwei",
                    "parent": "budingwei",
                    "mode": "wuxing"
                }, {"title": "三码不定位", "name": "sanmabudingwei", "parent": "budingwei", "mode": "wuxing"}]
            }, {
                "title": "趣味",
                "name": "quwei",
                "parent": "wuxing",
                "childs": [{
                    "title": "一帆风顺",
                    "name": "yifanfengshun",
                    "parent": "quwei",
                    "mode": "wuxing"
                }, {
                    "title": "好事成双",
                    "name": "haoshichengshuang",
                    "parent": "quwei",
                    "mode": "wuxing"
                }, {"title": "三星报喜", "name": "sanxingbaoxi", "parent": "quwei", "mode": "wuxing"}, {
                    "title": "四季发财",
                    "name": "sijifacai",
                    "parent": "quwei",
                    "mode": "wuxing"
                }]
            }],
            "gameType": null,
            "gameTypeCn": null,
            "gameTips": null
        }, {
            "title": "四星",
            "name": "sixing",
            "isdiamond": true,
            "isNew": 0,
            "childs": [{
                "title": "直选",
                "name": "zhixuan",
                "parent": "sixing",
                "childs": [{"title": "复式", "name": "fushi", "parent": "zhixuan", "mode": "sixing"}, {
                    "title": "单式",
                    "name": "danshi",
                    "parent": "zhixuan",
                    "mode": "sixing"
                }]
            }, {
                "title": "组选",
                "name": "zuxuan",
                "parent": "sixing",
                "childs": [{
                    "title": "组选24",
                    "name": "zuxuan24",
                    "parent": "zuxuan",
                    "mode": "sixing"
                }, {"title": "组选12", "name": "zuxuan12", "parent": "zuxuan", "mode": "sixing"}, {
                    "title": "组选6",
                    "name": "zuxuan6",
                    "parent": "zuxuan",
                    "mode": "sixing"
                }, {"title": "组选4", "name": "zuxuan4", "parent": "zuxuan", "mode": "sixing"}]
            }, {
                "title": "不定位",
                "name": "budingwei",
                "parent": "sixing",
                "childs": [{
                    "title": "一码不定位",
                    "name": "yimabudingwei",
                    "parent": "budingwei",
                    "mode": "sixing"
                }, {"title": "二码不定位", "name": "ermabudingwei", "parent": "budingwei", "mode": "sixing"}]
            }],
            "gameType": null,
            "gameTypeCn": null,
            "gameTips": null
        }, {
            "title": "前三",
            "name": "qiansan",
            "isdiamond": false,
            "isNew": 0,
            "childs": [{
                "title": "直选",
                "name": "zhixuan",
                "parent": "qiansan",
                "childs": [{"title": "复式", "name": "fushi", "parent": "zhixuan", "mode": "qiansan"}, {
                    "title": "单式",
                    "name": "danshi",
                    "parent": "zhixuan",
                    "mode": "qiansan"
                }, {"title": "直选和值", "name": "hezhi", "parent": "zhixuan", "mode": "qiansan"}, {
                    "title": "跨度",
                    "name": "kuadu",
                    "parent": "zhixuan",
                    "mode": "qiansan"
                }]
            }, {
                "title": "组选",
                "name": "zuxuan",
                "parent": "qiansan",
                "childs": [{"title": "组选和值", "name": "hezhi", "parent": "zuxuan", "mode": "qiansan"}, {
                    "title": "组三",
                    "name": "zusan",
                    "parent": "zuxuan",
                    "mode": "qiansan"
                }, {"title": "组六", "name": "zuliu", "parent": "zuxuan", "mode": "qiansan"}, {
                    "title": "混合组选",
                    "name": "hunhezuxuan",
                    "parent": "zuxuan",
                    "mode": "qiansan"
                }, {"title": "组选包胆", "name": "baodan", "parent": "zuxuan", "mode": "qiansan"}, {
                    "title": "组三单式",
                    "name": "zusandanshi",
                    "parent": "zuxuan",
                    "mode": "qiansan"
                }, {"title": "组六单式", "name": "zuliudanshi", "parent": "zuxuan", "mode": "qiansan"}]
            }, {
                "title": "不定位",
                "name": "budingwei",
                "parent": "qiansan",
                "childs": [{
                    "title": "一码不定位",
                    "name": "yimabudingwei",
                    "parent": "budingwei",
                    "mode": "qiansan"
                }, {"title": "二码不定位", "name": "ermabudingwei", "parent": "budingwei", "mode": "qiansan"}]
            }],
            "gameType": null,
            "gameTypeCn": null,
            "gameTips": null
        }, {
            "title": "中三",
            "name": "zhongsan",
            "isdiamond": true,
            "isNew": 0,
            "childs": [{
                "title": "直选",
                "name": "zhixuan",
                "parent": "zhongsan",
                "childs": [{"title": "复式", "name": "fushi", "parent": "zhixuan", "mode": "zhongsan"}, {
                    "title": "单式",
                    "name": "danshi",
                    "parent": "zhixuan",
                    "mode": "zhongsan"
                }, {"title": "直选和值", "name": "hezhi", "parent": "zhixuan", "mode": "zhongsan"}, {
                    "title": "跨度",
                    "name": "kuadu",
                    "parent": "zhixuan",
                    "mode": "zhongsan"
                }]
            }, {
                "title": "组选",
                "name": "zuxuan",
                "parent": "zhongsan",
                "childs": [{"title": "组选和值", "name": "hezhi", "parent": "zuxuan", "mode": "zhongsan"}, {
                    "title": "组三",
                    "name": "zusan",
                    "parent": "zuxuan",
                    "mode": "zhongsan"
                }, {"title": "组六", "name": "zuliu", "parent": "zuxuan", "mode": "zhongsan"}, {
                    "title": "混合组选",
                    "name": "hunhezuxuan",
                    "parent": "zuxuan",
                    "mode": "zhongsan"
                }, {"title": "组选包胆", "name": "baodan", "parent": "zuxuan", "mode": "zhongsan"}, {
                    "title": "组三单式",
                    "name": "zusandanshi",
                    "parent": "zuxuan",
                    "mode": "zhongsan"
                }, {"title": "组六单式", "name": "zuliudanshi", "parent": "zuxuan", "mode": "zhongsan"}]
            }, {
                "title": "不定位",
                "name": "budingwei",
                "parent": "zhongsan",
                "childs": [{
                    "title": "一码不定位",
                    "name": "yimabudingwei",
                    "parent": "budingwei",
                    "mode": "zhongsan"
                }, {"title": "二码不定位", "name": "ermabudingwei", "parent": "budingwei", "mode": "zhongsan"}]
            }],
            "gameType": null,
            "gameTypeCn": null,
            "gameTips": null
        }, {
            "title": "后三",
            "name": "housan",
            "isdiamond": true,
            "isNew": 0,
            "childs": [{
                "title": "直选",
                "name": "zhixuan",
                "parent": "housan",
                "childs": [{"title": "复式", "name": "fushi", "parent": "zhixuan", "mode": "housan"}, {
                    "title": "单式",
                    "name": "danshi",
                    "parent": "zhixuan",
                    "mode": "housan"
                }, {"title": "直选和值", "name": "hezhi", "parent": "zhixuan", "mode": "housan"}, {
                    "title": "跨度",
                    "name": "kuadu",
                    "parent": "zhixuan",
                    "mode": "housan"
                }]
            }, {
                "title": "组选",
                "name": "zuxuan",
                "parent": "housan",
                "childs": [{"title": "组选和值", "name": "hezhi", "parent": "zuxuan", "mode": "housan"}, {
                    "title": "组三",
                    "name": "zusan",
                    "parent": "zuxuan",
                    "mode": "housan"
                }, {"title": "组六", "name": "zuliu", "parent": "zuxuan", "mode": "housan"}, {
                    "title": "混合组选",
                    "name": "hunhezuxuan",
                    "parent": "zuxuan",
                    "mode": "housan"
                }, {"title": "组选包胆", "name": "baodan", "parent": "zuxuan", "mode": "housan"}, {
                    "title": "组三单式",
                    "name": "zusandanshi",
                    "parent": "zuxuan",
                    "mode": "housan"
                }, {"title": "组六单式", "name": "zuliudanshi", "parent": "zuxuan", "mode": "housan"}]
            }, {
                "title": "不定位",
                "name": "budingwei",
                "parent": "housan",
                "childs": [{
                    "title": "一码不定位",
                    "name": "yimabudingwei",
                    "parent": "budingwei",
                    "mode": "housan"
                }, {"title": "二码不定位", "name": "ermabudingwei", "parent": "budingwei", "mode": "housan"}]
            }],
            "gameType": null,
            "gameTypeCn": null,
            "gameTips": null
        }, {
            "title": "前二",
            "name": "qianer",
            "isdiamond": false,
            "isNew": 0,
            "childs": [{
                "title": "直选",
                "name": "zhixuan",
                "parent": "qianer",
                "childs": [{"title": "直选复式", "name": "fushi", "parent": "zhixuan", "mode": "qianer"}, {
                    "title": "直选单式",
                    "name": "danshi",
                    "parent": "zhixuan",
                    "mode": "qianer"
                }, {"title": "直选和值", "name": "hezhi", "parent": "zhixuan", "mode": "qianer"}, {
                    "title": "跨度",
                    "name": "kuadu",
                    "parent": "zhixuan",
                    "mode": "qianer"
                }]
            }, {
                "title": "组选",
                "name": "zuxuan",
                "parent": "qianer",
                "childs": [{"title": "组选复式", "name": "fushi", "parent": "zuxuan", "mode": "qianer"}, {
                    "title": "组选单式",
                    "name": "danshi",
                    "parent": "zuxuan",
                    "mode": "qianer"
                }, {"title": "组选和值", "name": "hezhi", "parent": "zuxuan", "mode": "qianer"}, {
                    "title": "组选包胆",
                    "name": "baodan",
                    "parent": "zuxuan",
                    "mode": "qianer"
                }]
            }],
            "gameType": null,
            "gameTypeCn": null,
            "gameTips": null
        }, {
            "title": "后二",
            "name": "houer",
            "isdiamond": true,
            "isNew": 0,
            "childs": [{
                "title": "直选",
                "name": "zhixuan",
                "parent": "houer",
                "childs": [{"title": "直选复式", "name": "fushi", "parent": "zhixuan", "mode": "houer"}, {
                    "title": "直选单式",
                    "name": "danshi",
                    "parent": "zhixuan",
                    "mode": "houer"
                }, {"title": "直选和值", "name": "hezhi", "parent": "zhixuan", "mode": "houer"}, {
                    "title": "跨度",
                    "name": "kuadu",
                    "parent": "zhixuan",
                    "mode": "houer"
                }]
            }, {
                "title": "组选",
                "name": "zuxuan",
                "parent": "houer",
                "childs": [{"title": "组选复式", "name": "fushi", "parent": "zuxuan", "mode": "houer"}, {
                    "title": "组选单式",
                    "name": "danshi",
                    "parent": "zuxuan",
                    "mode": "houer"
                }, {"title": "组选和值", "name": "hezhi", "parent": "zuxuan", "mode": "houer"}, {
                    "title": "组选包胆",
                    "name": "baodan",
                    "parent": "zuxuan",
                    "mode": "houer"
                }]
            }],
            "gameType": null,
            "gameTypeCn": null,
            "gameTips": null
        }, {
            "title": "一星",
            "name": "yixing",
            "isdiamond": true,
            "isNew": 0,
            "childs": [{
                "title": "定位胆",
                "name": "dingweidan",
                "parent": "yixing",
                "childs": [{"title": "复式", "name": "fushi", "parent": "dingweidan", "mode": "yixing"}]
            }],
            "gameType": null,
            "gameTypeCn": null,
            "gameTips": null
        }],
        "awardGroups": [{
            "gid": 58,
            "awardGroupId": 12656282,
            "awardName": "奖金组1500",
            "betType": 0,
            "directRet": 300,
            "threeoneRet": 300,
            "superRet": 0,
            "lhcYear": 0,
            "lhcColor": 0,
            "createTime": 1425462531594,
            "updateTimte": 1456381924129,
            "awardGroupRetStatus": 1
        }, {
            "gid": 57,
            "awardGroupId": 12656281,
            "awardName": "奖金组1700",
            "betType": 0,
            "directRet": 300,
            "threeoneRet": 300,
            "superRet": 0,
            "lhcYear": 0,
            "lhcColor": 0,
            "createTime": 1425462531592,
            "updateTimte": 1456381924130,
            "awardGroupRetStatus": 1
        }, {
            "gid": 56,
            "awardGroupId": 12656280,
            "awardName": "奖金组1800",
            "betType": 1,
            "directRet": 300,
            "threeoneRet": 300,
            "superRet": 0,
            "lhcYear": 0,
            "lhcColor": 0,
            "createTime": 1425462531589,
            "updateTimte": 1456381924130,
            "awardGroupRetStatus": 1
        }],
        "dynamicConfigUrl": "/gameBet/slmmc/dynamicConfig",
        "uploadPath": "/gameBet/slmmc/betFile",
        "queryUserBalUrl": "/gameBet/queryUserBal",
        "getUserOrdersUrl": "/gameBet/slmmc/getUserOrders",
        "getUserPlansUrl": "/gameBet/slmmc/getUserPlans",
        "getHandingChargeUrl": "/gameBet/slmmc/handlingCharge?amount=",
        "getBetAwardUrl": "/gameBet/slmmc/getBetAward",
        "getHotColdUrl": "/gameBet/slmmc/frequency",
        "trendChartUrl": "/gameBet/slmmc/trendChart?type=",
        "getLotteryLogoPath": "/static/images/game/logos/logo-slmmc.png",
        "queryGameUserAwardGroupByLotteryIdUrl": "/gameBet/slmmc/queryGameUserAwardGroupByLotteryId",
        "saveProxyBetGameAwardGroupUrl": "/gameBet/slmmc/saveBetAward",
        "sumbitUrl": "/gameBet/slmmc/submit",
        "indexInit": "/gameBet/slmmc/indexInit",
        "poolBouns": null,
        "isLotteryStopSale": false,
        "lastNumberUrl": "/gameBet/slmmc/lastNumber",
        "sourceList": [],
        "helpLink": "/help/queryLotteryDetail?helpId=877",
        "chips": [1, 2, 5, 10, 20, 50, 100, 500, 1000, 5000],
        "chipsSelected": [10, 20, 50, 100, 500],
        "ballLists": null,
        "gameOdds": null,
        "gameZodiac": null,
        "gameTips": null,
        "queryStraightOddsUrl": "/gameBet/slmmc/straightOdds"
    };
    var defConfig = {
            //当前彩种名称
            gameType: gameConfigData['gameType'],
            gameTypeCn: gameConfigData['gameTypeCn'],
            lotteryId: gameConfigData['lotteryId'],
            awardGroups: gameConfigData['awardGroups'],
            userId: gameConfigData['userId'],
            userName: gameConfigData['userName'],
            userLvl: gameConfigData['userLvl'],
            awardRetStatus: gameConfigData['awardRetStatus'],
            awardGroupRetStatus: gameConfigData['awardGroupRetStatus'],
            backOutStartFee: gameConfigData['backOutStartFee'],
            backOutRadio: gameConfigData['backOutRadio'],
            isLotteryStopSale: gameConfigData['isLotteryStopSale'],
            isfirstimeuse2000: gameConfigData['isfirstimeuse2000'],
            isfirstimeusediamond2000: gameConfigData['isfirstimeusediamond2000'],
            helpLink: gameConfigData['helpLink'],
            sourceList: gameConfigData['sourceList']
        },
        instance;
    var pros = {
        init: function () {
            var me = this;
            me.types = gameConfigData['gameMethods'];
        },
        //获取玩法类型
        getTypes: function (isFilterClose) {
            return this.types;
        },
        getGameTypeCn: function () {
            return this.defConfig.gameTypeCn;
        },
        getDefaultMethod: function () {
            return gameConfigData['defaultMethod'];
        },
        //获取动态配置接口地址
        getDynamicConfigUrl: function () {
            return gameConfigData['dynamicConfigUrl'];
        },
        //获取单式上传接口地址
        getUploadPath: function () {
            return gameConfigData['uploadPath'];
        },
        //获取用户余额
        getUserBalUrl: function () {
            return gameConfigData['queryUserBalUrl'];
        },
        //获取投注页面显示订单接口地址
        getUserOrdersUrl: function () {
            return gameConfigData['getUserOrdersUrl'];
        },
        //获取单式上传接口地址
        getUserPlansUrl: function () {
            return gameConfigData['getUserPlansUrl'];
        },
        //获取撤销手续费接口地址
        getHandingChargeUrl: function () {
            return gameConfigData['getHandingChargeUrl'];
        },
        //获取彩种logo地址
        getLotteryLogoPath: function () {
            return gameConfigData['getLotteryLogoPath'];
        },
        //获取玩法走势图接口地址
        trendChartUrl: function () {
            return gameConfigData['trendChartUrl'];
        },
        //查询玩法描述和默认冷热球及用户投注方式奖金接口地址
        getBetAwardUrl: function () {
            return gameConfigData['getBetAwardUrl'];
        },
        //获取冷热遗漏接口地址
        getHotColdUrl: function () {
            return gameConfigData['getHotColdUrl'];
        },
        //查询奖金组
        getQueryGameUserAwardGroupByLotteryIdUrl: function () {
            return gameConfigData['queryGameUserAwardGroupByLotteryIdUrl'];
        },
        //保存代理投注奖金组
        getSaveProxyBetGameAwardGroupUrl: function () {
            return gameConfigData['saveProxyBetGameAwardGroupUrl'];
        },
        //获取投注提交接口地址
        submitUrl: function () {
            return gameConfigData['sumbitUrl'];
        },
        //获取首页接口
        indexInitUrl: function () {
            return gameConfigData['indexInit'];
        },
        //获取最新开奖号码
        lastNumberUrl: function () {
            return gameConfigData['lastNumberUrl'];
        },
        //name  wuxing.zhixuan.fushi
        getTitleByName: function (name) {
            var me = this,
                nameArr = name.split('.'),
                nameLen = nameArr.length,
                types = me.types,
                i = 0,
                len = types.length,
                i2,
                len2,
                i3,
                len3,
                tempArr = [],
                result = [];
            //循环一级
            for (; i < len; i++) {
                if (types[i]['name'] == nameArr[0]) {
                    if (gameConfigData['gameType'].indexOf('115') < 0) {
                        result.push(types[i]['title'].replace(/&nbsp;/g, ''));
                    }
                    if (nameLen > 1 && types[i]['childs'].length > 0) {
                        tempArr = types[i]['childs'];
                        len2 = tempArr.length;
                        //循环二级
                        for (i2 = 0; i2 < len2; i2++) {
                            //console.log(tempArr[i2]['name']);
                            if (tempArr[i2]['name'] == nameArr[1]) {
                                if (gameConfigData['gameType'].indexOf('115') > 0) {
                                    result.push(tempArr[i2]['title'].replace(/&nbsp;/g, ''));
                                }
                                if (nameLen > 2 && tempArr[i2]['childs'].length > 0) {
                                    tempArr = tempArr[i2]['childs'];
                                    len3 = tempArr.length;
                                    //循环三级
                                    for (i3 = 0; i3 < len3; i3++) {
                                        if (tempArr[i3]['name'] == nameArr[2]) {
                                            if (tempArr[i3]['headline']) {
                                                return tempArr[i3]['headline'];
                                            }
                                            if ($.inArray(tempArr[i3]['title'].replace(/&nbsp;/g, ''), result) == -1) {
                                                result.push(tempArr[i3]['title'].replace(/&nbsp;/g, ''));
                                            }
                                            return result;
                                        }
                                    }
                                } else {
                                    return result;
                                }
                            }
                        }
                    } else {
                        return result;
                    }
                }
            }
            return '';
        }

    };

    var Main = host.Class(pros, Event);
    Main.defConfig = defConfig;
    Main.getInstance = function (cfg) {
        return instance || (instance = new Main(cfg));
    };

    host.Games.SSC[name] = Main;

})(phoenix, "Config", phoenix.Event);