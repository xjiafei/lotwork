/**
 * Created by user on 16/3/23.
 */

(function(host, name, Event, undefined) {
  var gameConfigData = {
    "gameType": "lhc",
    "gameTypeCn": "六合彩",
    "defaultMethod": "tema.zhixuan",
    "lotteryId": 99101,
    "userLvl": 2,
    "userId": 1198908,
    "userName": "luck888",
    "awardRetStatus": 1,
    "awardGroupRetStatus": 1,
    "backOutStartFee": 100000000,
    "backOutRadio": 500,
    "gameMethods": [{
        "title": "正码",
        "name": "zhengma",
        "isNew": 0,
        "childs": [{
          "title": "平码",
          "name": "pingma",
          "parent": "zhengma"
        }]
      },
      {
        "title": "特码",
        "name": "tema",
        "isNew": 0,
        "childs": [{
            "title": "直选",
            "name": "zhixuan",
            "parent": "tema"
          },
          {
            "title": "特肖",
            "name": "texiao",
            "parent": "tema"
          },
          {
            "title": "色波",
            "name": "saibo",
            "parent": "tema"
          },
          {
            "title": "两面",
            "name": "liangmian",
            "parent": "tema"
          }
        ]
      },
      {
        "title": "正特码",
        "name": "zhengtema",
        "isNew": 0,
        "childs": [{
            "title": "一肖",
            "name": "yixiao",
            "parent": "zhengtema"
          },
          {
            "title": "不中",
            "name": "buzhong",
            "parent": "zhengtema"
          },
          {
            "title": "连肖(中)",
            "name": "lianxiaozhong",
            "parent": "zhengtema"
          },
          {
            "title": "连肖(不中)",
            "name": "lianxiaobuzhong",
            "parent": "zhengtema"
          }
        ]
      },
      {
        "title": "连码",
        "name": "lianma",
        "isNew": 0,
        "childs": [{
          "title": "连码",
          "name": "lianma",
          "parent": "lianma"
        }]
      }
    ],
    "gameOdds": {
      //正码_平码_直选六码
      "zhixuanliuma": 7.5,
      //特码_直选_直选一码
      "zhixuanema": [40, 41, 42, 43, 44, 45, 46, 47],
      //特码_特肖_特肖(主肖,副肖)
      "texiao": [9, 11.2],
      //特码_色波_色波(红,蓝,绿)
      "saibo": [2.7, 2.85, 2.85],
      //特码_色波_半波(红大,红小,红单,红双,蓝大,蓝小,蓝单,蓝双,绿大,绿小,绿单,绿双)
      "banbo": [6.4, 4.5, 5.6, 5, 5, 6.4, 5.6, 5.6, 5.6, 6.4, 5.6, 6.4],
      //特码_两面_两面(大,小,和大,和小,单,双,和单,和双,大肖,小肖,尾大,尾小)
      "liangmian": [1.9, 1.9, 1.9, 1.9, 1.9, 1.9, 1.9, 1.9, 1.9, 1.9, 1.9, 1.9],
      //正特码_一肖_一肖(主肖,副肖)
      "yixiao": [1.7, 2],
      //正特码_不中(四不中,五不中,六不中,七不中,八不中)
      "buzhong": {
        "sibuzhong": 1.8,
        "wubuzhong": 2,
        "liubuzhong": 2.5,
        "qibuzhong": 3,
        "babuzhong": 3.6
      },
      //正特码_连肖(中)(二连肖(主肖,副肖),三连肖(主肖,副肖),四连肖(主肖,副肖),五连肖(主肖,副肖))
      "lianxiaozhong": {
        "erlianxiao": [3.8, 4.5],
        "sanlianxiao": [9.6, 11.5],
        "silianxiao": [27, 32],
        "wulianxiao": [85, 105]
      },
      //正特码_连肖(不中)(二连肖(主肖,副肖),三连肖(主肖,副肖),四连肖(主肖,副肖),五连肖(主肖,副肖))
      "lianxiaobuzhong": {
        "erlianxiao": [4.2, 3.5],
        "sanlianxiao": [9.5, 7.7],
        "silianxiao": [23, 18],
        "wulianxiao": [60, 46]
      },
      //连码_连码(三全中,三中二(中二,中三),二全中,二中特(中二,中特),特串)
      "lianma": {
        "sanquanzhong": 680,
        "sanzhonger": [20, 110],
        "erquanzhong": 68,
        "erzhongte": [51, 31],
        "techuan": 160
      }
    },
    "gameZodiac": [{
        "zodiacNameCn": "鼠",
        "zodiacName": "shu",
        "number": '08,20,32,44'
      },
      {
        "zodiacNameCn": "牛",
        "zodiacName": "niu",
        "number": '07,19,31,43'
      },
      {
        "zodiacNameCn": "虎",
        "zodiacName": "hu",
        "number": '06,18,30,42'
      },
      {
        "zodiacNameCn": "兔",
        "zodiacName": "tu",
        "number": '05,17,29,41'
      },
      {
        "zodiacNameCn": "龙",
        "zodiacName": "long",
        "number": '04,16,28,40'
      },
      {
        "zodiacNameCn": "蛇",
        "zodiacName": "she",
        "number": '03,15,27,39'
      },
      {
        "zodiacNameCn": "马",
        "zodiacName": "ma",
        "number": '02,14,26,38'
      },
      {
        "zodiacNameCn": "羊",
        "zodiacName": "yang",
        "number": '01,13,25,49'
      },
      {
        "zodiacNameCn": "猴",
        "zodiacName": "hou",
        "number": '12,24,36,48'
      },
      {
        "zodiacNameCn": "鸡",
        "zodiacName": "ji",
        "number": '11,23,35,47'
      },
      {
        "zodiacNameCn": "狗",
        "zodiacName": "gou",
        "number": '10,22,34,46'
      },
      {
        "zodiacNameCn": "猪",
        "zodiacName": "zhu",
        "number": '09,21,33,45'
      }

    ],
    "gameTips": [{
        "name": "zhengma",
        "childs": [{
          "name": "pingma",
          "rule": "第1~6球其中1号相同即中奖",
          "example": "投注：04,07,11,15,23,30,40<br>开奖：05,07,09,10,21,29,33<br>赔率：7.5<br>投注：100元<br>奖金：750元"
        }]
      },
      {
        "name": "tema",
        "childs": [{
            "name": "zhixuan",
            "rule": "当期开奖的特别号码，与下注的号码相同时中奖。",
            "example": "投注：33<br>开奖：**,**,**,**,**,**,33<br>赔率：40<br>投注：100元<br>奖金：4000元"
          },
          {
            "name": "texiao",
            "rule": "生肖顺序为 鼠 >牛 >虎 >兔 >龙 >蛇 >马 >羊 >猴 >鸡 >狗 >猪<br>如今年是猴年，就以猴为开始，依顺序将49个号码分为12个生肖(如下)，再以生肖下注。<br>猴 01 , 13 , 25 , 37 , 49<br>鸡 12 , 24 , 36 , 48<br>狗 11 , 23 , 35 , 47<br>猪 10 , 22 , 34 , 46<br>鼠 09 , 21 , 33 , 45<br>牛 08 , 20 , 32 , 44<br>虎 07 , 19 , 31 , 43<br>兔 06 , 18 , 30 , 42<br>龙 05 , 17 , 29 , 41<br>蛇 04 , 16 , 28 , 40<br>马 03 , 15 , 27 , 39<br>羊 02 , 14 , 26 , 38<br>若当期特别号，落在下注生肖范围内，视为中奖。",
            "example": "投注：羊<br>开奖：**,**,**,**,**,**,38<br>赔率：11.20<br>投注：100元<br>奖金：1120元"
          },
          {
            "name": "saibo",
            "childs": [{
                "name": "saibo",
                "rule": "香港六合彩特有球色，当开出号码的球色等於下注颜色时中奖。<br>红：01,02,07,08,12,13,18,19,23,24,29,30,34,35,40,45,46<br>蓝：03,04,09,10,14,15,20,25,26,31,36,37,41,42,47,48<br>绿：05,06,11,16,17,21,22,27,28,32,33,38,39,43,44,49",
                "example": "投注：绿<br>开奖：**,**,**,**,**,**,16<br>赔率：2.70<br>投注：100元<br>奖金：270元"
              },
              {
                "name": "banbo",
                "rule": "指大小单双和色球组合的统称。<br>以特码红大、红小、红单、红双、蓝大、蓝小、蓝单、蓝双、绿大、绿小、绿单、绿双为一个投注组合，当期特码开出符合投注组合，即视为中奖。",
                "example": "投注：大<br>开奖：**,**,**,**,**,**,29<br>赔率：1.90<br>投注：100元<br>奖金：190元"
              }
            ]
          },
          {
            "name": "liangmian",
            "rule": "指大小单双的统称。<br>大小：大于或者等于25时为大，小于或者等于24时为小，开出49为和。<br>单双：号码单数时为单，双数时为双，开出49为和。<br>大小肖：落在生肖中的：马丶羊丶猴丶鸡丶狗丶猪等范围为大肖，落在生肖中的：鼠丶牛丶虎丶兔丶龙丶蛇等范围为小肖，开出49为和。<br>和大小：十位数与个位数相加后大于等于7时为合大，十位数与个位数相加后小于等于6时为合小，开出49为和。<br>和单双：十位数与个位数相加后为单数即为合单，相加后为双数则为合双，开出49为和。<br>总和单双：当期开奖的全部号码，包含前6个号码及特别号，全部相加后总分数为单数时为总和单，全部相加后总分数为双数时为总和双。<br>尾大小：个位数大于等于5时为尾大，个位数小于等于4时为尾小，开出49为和。",
            "example": "投注：大<br>开奖：**,**,**,**,**,**,29<br>赔率：1.90<br>投注：100元<br>奖金：190元"
          }
        ]
      },
      {
        "name": "zhengtema",
        "childs": [{
            "name": "yixiao",
            "rule": "将生肖依序排列，例如今年是猴年，就以猴为开始，依顺序将49个号码分为12个生肖(如下)，再以生肖下注。<br>猴 01 , 13 , 25 , 37 , 49<br>鸡 12 , 24 , 36 , 48<br>狗 11 , 23 , 35 , 47<br>猪 10 , 22 , 34 , 46<br>鼠 09 , 21 , 33 , 45<br>牛 08 , 20 , 32 , 44<br>虎 07 , 19 , 31 , 43<br>兔 06 , 18 , 30 , 42<br>龙 05 , 17 , 29 , 41<br>蛇 04 , 16 , 28 , 40<br>马 03 , 15 , 27 , 39<br>羊 02 , 14 , 26 , 38<br>只要当期号码(所有正码与最后开出的特码)，落在下注生肖范围内，则视为中奖。(请注意：49亦算输赢，不为和)。",
            "example": "投注：猴<br>开奖：01，**，**，**，**，**，**<br>赔率：2<br>投注：100元<br>奖金：200元"
          },
          {
            "name": "buzhong",
            "rule": "挑选四到八个号球为一个组合，当期号码(所有正码与最后开出的特码)不在投注时所勾选之号球组合所属号码内，则视为中奖，其余情视为不中奖。",
            "example": "投注：02,03,21,35<br>开奖：01，08，09，22，25，28，43<br>赔率：1.8<br>投注：100元<br>奖金：180元"
          },
          {
            "name": "lianxiaozhong",
            "rule": "挑选2~5生肖(排列如同生肖)为一个组合，当期号码(所有正码与最后开出的特码)坐落于投注时所勾选之生肖组合所属号码内，所勾选之生肖皆分别至少有中一个号码，则视为中奖，其余情视为不中奖(请注意49亦算输赢，不为和)例如：如果当期号码为19、24、12、34、40、39.特码：49，所勾选三个生肖，若所有生肖的所属号码内至少一个出现于当期号码，则视为中奖。",
            "example": "投注：猴、蛇<br>开奖：19、24、12、13、39、40、49<br>赔率：4.5<br>投注：100元<br>奖金：450元"
          },
          {
            "name": "lianxiaobuzhong",
            "rule": "挑选2~5生肖(排列如同生肖)为一个组合，当期号码(所有正码与最后开出的特码)不在投注时所勾选之生肖组合所属号码内，所勾选之生肖没有一个号码出现在当期开奖号球内，则视为中奖，其余情视为不中奖。",
            "example": "投注：虎、牛<br>开奖：18、24、12、13、39、40、49<br>赔率：3.5<br>投注：100元<br>奖金：350元"
          }
        ]
      },
      {
        "name": "lianma",
        "childs": [{
            "name": "sanquanzhong",
            "rule": "所投注的每三个号码为一组合，若三个号码都是开奖号码之正码，视为中奖，其馀情形视为 不中奖。如06、07、08三个都是开奖号码之正码，视为中奖，如两个正码加上一个特别号码视为不中奖 。",
            "example": "投注：01,13,25<br>开奖：01，13,25,27,37,38,44<br>赔率：680<br>投注：100元<br>奖金：68000元"
          },
          {
            "name": "sanzhonger",
            "rule": "所投注的每三个号码为一组合，若其中2个是开奖号码中的正码，即为三中二，视为中奖；若3个都是开奖号码中的正码，即为三中二之中三，其馀情形视为不中奖，如06、07、08为一组合，开奖号码中有06、07两个正码，没有08，即为三中二，按三中二赔付；如开奖 号码中有06、07、08三个正码，即为三中二之中三，按中三赔付；如出现1个或没有，视为不中奖 。",
            "example": "投注：01,13,26<br>开奖：01，13,25,27,37,38,44<br>赔率：20<br>投注：100元<br>奖金：2000元"
          },
          {
            "name": "erquanzhong",
            "rule": "二全中： 所投注的每二个号码为一组合，二个号码都是开奖号码之正码，视为中奖，其馀情形视为不中奖，不含特码。",
            "example": "投注：01,13<br>开奖：01，13,25,27,37,38,44<br>赔率：68<br>投注：100元<br>奖金：6800元"
          },
          {
            "name": "erzhongte",
            "rule": "二中特： 所投注的每二个号码为一组合，二个号码都是开奖号码之正码，叫二中特之中二；若其中一个是正码，一个是特码，叫二中特之中特；其馀情形视为不中奖 。",
            "example": "投注：01,13<br>开奖：01，12,25,27,37,38,13<br>赔率：51<br>投注：100元<br>奖金：5100元"
          },
          {
            "name": "techuan",
            "rule": "所投注的每二个号码为一组合，其中一个是正码，一个是特别号码，视为中奖，其馀情形视为 不中奖（含二个号码都是正码之情形） 。",
            "example": "投注：01,13<br>开奖：01，12,25,27,37,38,13<br>赔率：160<br>投注：100元<br>奖金：16000元"
          }
        ]
      }

    ],
    "dynamicConfigUrl": "../js/game/lhc/data/dynamicConfig.json",
    "queryStraightOddsUrl": "../js/game/lhc/data/straightOdds.json",
    "lastNumberUrl": "../js/game/lhc/data/lastNumber.json",
    "getUserOrdersUrl": "../js/game/lhc/data/getUserOrders.json",

    "queryUserBalUrl": "/gameBet/queryUserBal",
    "sumbitUrl": "/gameBet/lhc/submit",
    "indexInit": "/gameBet/lhc/indexInit",
    "poolBouns": null,
    "isLotteryStopSale": false,

    "helpLink": "/help/queryLotteryDetail?helpId=869",
    "ballLists": null
  };
  var defConfig = {
      //当前彩种名称
      gameType: gameConfigData['gameType'],
      gameTypeCn: gameConfigData['gameTypeCn'],
      lotteryId: gameConfigData['lotteryId'],
      userId: gameConfigData['userId'],
      userName: gameConfigData['userName'],
      userLvl: gameConfigData['userLvl'],
      awardRetStatus: gameConfigData['awardRetStatus'],
      awardGroupRetStatus: gameConfigData['awardGroupRetStatus'],
      backOutStartFee: gameConfigData['backOutStartFee'],
      backOutRadio: gameConfigData['backOutRadio'],
      isLotteryStopjjSale: gameConfigData['isLotteryStopSale'],
      helpLink: gameConfigData['helpLink'],
      sourceList: gameConfigData['sourceList']
    },
    instance;
  var pros = {
    init: function() {
      var me = this;
      me.types = gameConfigData['gameMethods'];
    },
    //获取玩法类型
    getTypes: function() {
      return this.types;
    },
    getGameTypeCn: function() {
      return this.defConfig.gameTypeCn;
    },
    getDefaultMethod: function() {
      return gameConfigData['defaultMethod'];
    },
    //获取动态配置接口地址
    getDynamicConfigUrl: function() {
      return gameConfigData['dynamicConfigUrl'];
    },
    //获取生肖配置
    getZoadiacList: function() {
      return gameConfigData['gameZodiac'];
    },
    //获取特码直选赔率接口地址
    getStraightOddsUrl: function() {
      return gameConfigData['queryStraightOddsUrl'];
    },
    //获取游戏赔率
    getGameOdds: function() {
      return gameConfigData['gameOdds'];
    },
    //获取玩法提示
    getGameTips: function() {
      return gameConfigData['gameTips'];
    },
    //获取用户余额
    getUserBalUrl: function() {
      return gameConfigData['queryUserBalUrl'];
    },
    //获取投注页面显示订单接口地址
    getUserOrdersUrl: function() {
      return gameConfigData['getUserOrdersUrl'];
    },

    //获取投注提交接口地址
    submitUrl: function() {
      return gameConfigData['sumbitUrl'];
    },
    //获取首页接口
    indexInitUrl: function() {
      return gameConfigData['indexInit'];
    },
    //获取最新开奖号码
    lastNumberUrl: function() {
      return gameConfigData['lastNumberUrl'];
    },
    //name转中文
    getTitleByName: function(name, plus) {
      var me = this,
        nameArr = name.split('.'),
        nameLen = nameArr.length,
        types = me.types,
        i = 0,
        len = types.length,
        i2,
        len2,
        tempArr = [],
        result = [],
        plusMap = {
          'pingma': '平码',
          'texiao': '特肖',
          'liangmian': '两面',
          'saibo': '色波',
          'banbo': '半波',
          'zhixuanema': '直选',
          'yixiao': '一肖',
          'buzhong': '不中',
          'lianxiaozhong': '连肖(中)',
          'lianxiaobuzhong': '连肖(不中)',
          'lianma': '连码'
        };
      //循环一级
      for (; i < len; i++) {
        if (types[i]['name'] == nameArr[0]) {
          result.push(types[i]['title'].replace(/&nbsp;/g,
            ''));
          if (nameLen > 1 && types[i]['childs'].length > 0) {
            tempArr = types[i]['childs'];
            len2 = tempArr.length;
            //循环二级
            for (i2 = 0; i2 < len2; i2++) {
              if (tempArr[i2]['name'] == nameArr[1]) {
                result.push(tempArr[i2]['title'].replace(/&nbsp;/g, ''));
                if (!!plus) {
                  result.pop();
                  result.push(plusMap[plus]);
                  return result;
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
  Main.getInstance = function(cfg) {
    return instance || (instance = new Main(cfg));
  };
  host.Games.LHC[name] = Main;
})(phoenix, "Config", phoenix.Event);
