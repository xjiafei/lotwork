<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <title></title>
    <style>
        .loadding img {
            font-size: 14px;
            max-width: 70px;
            max-height: 52px;
        }

        .loadding {
            text-align: center;
            position: fixed;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
            z-index: 99999;
        }

        .loadding-bg {
            opacity: 1;
            background: #fff;
            position: absolute;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;

        }

        .spinner {
            height: 70px;
            width: 70px;
            text-align: center;
            position: absolute;
            margin: auto;
            left: 0;
            right: 0;
            bottom: 0;
            top: 0;

        }

        .spinner > div {
            width: 18px;
            height: 18px;
            background-color: #545067;
            border-radius: 100%;
            display: inline-block;
            -webkit-animation: sk-bouncedelay 1.4s infinite ease-in-out both;
            animation: sk-bouncedelay 1.4s infinite ease-in-out both;
        }

        .spinner .bounce1 {
            -webkit-animation-delay: -0.32s;
            animation-delay: -0.32s;
        }

        .spinner .bounce2 {
            -webkit-animation-delay: -0.16s;
            animation-delay: -0.16s;
        }

        @-webkit-keyframes sk-bouncedelay {
            0%, 80%, 100% {
                -webkit-transform: scale(0)
            }
            40% {
                -webkit-transform: scale(1.0)
            }
        }

        @keyframes sk-bouncedelay {
            0%, 80%, 100% {
                -webkit-transform: scale(0);
                transform: scale(0);
            }
            40% {
                -webkit-transform: scale(1.0);
                transform: scale(1.0);
            }
        }

    </style>
    <div class="loadding">
        <div class="loadding-bg"></div>
        <div class="spinner">
            <div class="bounce1"></div>
            <div class="bounce2"></div>
            <div class="bounce3"></div>
        </div>

    </div>
    <link rel="stylesheet" href="${staticFileContextPath}/static/images/wap/shierShengXiao/app.min.css?v=2">

    
    <script>
        window.debug = false;
        var returnUrl = '';
        var basePath = '';
        var postbasePath = location.origin;
    </script>
</head>
<body>
	<script src="${staticFileContextPath}/static/js/wap/shierShengXiao/game.js?v=2"></script>
	<script src="${staticFileContextPath}/static/js/wap/shierShengXiao/app.min.js?v=2"></script>
    <script src="${currentContextPath}/gameBet/${lotteryCode}/config"></script>
    
    <script type="text/javascript">
        window.debug = false;
        var returnUrl = '${userContextPath}';
        var basePath = '';
        var introUrl = '${userContextPath}'+phoenix.Games.SSC.Config.defConfig.helpLink.replace('/help/','/help/wap/');
        var backToPcUrl = '/gameBet/${lotteryCode}?isPass=true';
        
        phoenix.Games.SSC.Config.pros.init();
         configData = {
            "gameTypeCn":phoenix.Games.SSC.Config.defConfig.gameTypeCn,
            "defaultMethod": phoenix.Games.SSC.Config.pros.getDefaultMethod(),
            "gameMethods": phoenix.Games.SSC.Config.pros.getTypes(),
            "records": {},
            "gameLimits": {},
            "awardGroups": phoenix.Games.SSC.Config.defConfig.awardGroups,
            "awardGroupRetStatus": phoenix.Games.SSC.Config.defConfig.awardGroupRetStatus,
            "isSupport2000": phoenix.Games.SSC.Config.defConfig.isSupport2000
        };

		
		  // submit
        var submitUrl = phoenix.Games.SSC.Config.pros.submitUrl();
        configData.submitUrl = submitUrl;
        
        // 投注紀錄
        var getUserOrdersUrl = phoenix.Games.SSC.Config.pros.getUserOrdersUrl();
        configData.getUserOrdersUrl = getUserOrdersUrl;

        // 提交用户奖金组设置的接口
        var saveBetAwardUrl = phoenix.Games.SSC.Config.pros.getSaveProxyBetGameAwardGroupUrl();
        configData.saveBetAwardUrl = saveBetAwardUrl;
                
        // 调用接口获取动态配置数据, 如投注倍数的限制等
        var dynamicConfigUrl = phoenix.Games.SSC.Config.pros.getDynamicConfigUrl();
        configData.dynamicConfigUrl = dynamicConfigUrl;
        $.getJSON(dynamicConfigUrl, function(data){
            configData.gameLimits = data.data.gamelimit[0];
        });

        // 调用接口获取用户账户余额
        var queryUserBalUrl = phoenix.Games.SSC.Config.pros.getUserBalUrl();
        configData.queryUserBalUrl = queryUserBalUrl;

        if(/#\/drawing|#\/submit|#\/intro/.test(location.hash)){
            location.hash = '#/pick';
        }
    </script>

<ion-nav-view>
    <ion-footer-bar>
        <div class="chip-area">
            <div class="l-area">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                        <div class="swiper-slide" data-price="1">
                            <div class="img chipitem0"><span>1</span><img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/clip0.png?v=2"alt=""></div>
                        </div>
                        <div class="swiper-slide" data-price="5">
                            <div class="img  chipitem1"><span>5</span><img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/clip1.png?v=2"alt=""></div>
                        </div>
                        <div class="swiper-slide" data-price="10">
                            <div class="img chipitem2"><span>10</span><img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/clip2.png?v=2"alt=""></div>
                        </div>
                        <div class="swiper-slide" data-price="20">
                            <div class="img chipitem3"><span>20</span><img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/clip3.png?v=2"alt=""></div>
                        </div>
                        <div class="swiper-slide" data-price="50">
                            <div class="img chipitem4"><span>50</span><img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/clip4.png?v=2"alt=""></div>
                        </div>
                        <div class="swiper-slide" data-price="100">
                            <div class="img chipitem5"><span>100</span><img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/clip5.png?v=2"alt=""></div>
                        </div>
                        <div class="swiper-slide" data-price="1000">
                            <div class="img chipitem6"><span>1000</span><img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/clip6.png?v=2"alt=""></div>
                        </div>
                    </div>
                    <!-- Add Pagination -->
                    <div class="swiper-button-next"></div>
                    <div class="swiper-button-prev"></div>
                </div>
            </div>
            <div class="r-area">
                <img class="longpress" ng-click="clearData()"
                     src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/cancel.png"/>

                <img class="submit" ng-click="submit()"
                     ng-src="{{(isadvance ?'${staticFileContextPath}/static/images/wap/shierShengXiao/img/startedadvance.png':'${staticFileContextPath}/static/images/wap/shierShengXiao/img/started.png')}}"/>
            </div>

        </div>
    </ion-footer-bar>
</ion-nav-view>
<!--================================================== pick ======================================-->
<script id="templates/pick.html" type="text/ng-template">
  <ion-view ng-click="cloaseAllWindow()">
    <div class="my-content" has-bouncing="false">
        <div class="morespecial morespecial2">
            <div class="bbb">
                <div class="bbb2">
                    <ion-scroll direction="y" scrollbar-y="true" zooming="false" class="scroll2" has-bouncing="true">
                        <div class="morespecial-c-bg morespecial-c-bg2">
                            <div class="morespecial-c">
                                <h1>游戏说明</h1>
                                <h2>基础知识</h2>
                                <p>十二生肖游戏是以秒开时时彩为基础，适应手机平台特点开发的小游戏。
                                    因此您会看到 转轮上的数字 以及个位、十位的标识；投注详情里每条记录后括号中的说明；</p>
                                <p>秒开时时彩是由宝开娱乐集团根据十五年彩票销售经验，依据广受彩民喜爱的时时彩游戏提炼、优化而来；</p>
                                <p>秒秒彩由用户自主（用户点击）立即开奖，每期开奖包含五位，分别为：万位、千位、百位、十位、个位。每一位从0-9之间的随机开出一个数字；
                                    同时，每一位又称为 一个"星"，故有 五星、四星、三星、二星、一星的说法。即五个玩法群，顾名思义玩法群下面还会有很多种不同玩法；</p>

                                <h3>十二生肖与秒秒彩 玩法对应关系</h3>
                                <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/1.jpg" alt="">

                                <h3>各玩法中奖条件及奖金</h3>
                                <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/2.jpg" alt="">

                                <h3>1080P未删节版说明</h3>

                                <p>轮盘中外圈采用的是秒秒彩 个位 开出的结果。0-9十个数字分别对应 鼠牛兔蛇马羊猴鸡狗猪 十种生肖。例如
                                    当用户押注猴时，等同于押注了个位的6。当开奖结果为6时（此时指针指向猴），用户即为中奖。
                                    这实际是秒秒彩 一星 玩法群中的 个位复式 玩法。因此在投注记录中的括号内有如下提示：</p>

                                <code>
                                    个位：猴
                                    （一星_复式 个位 -,-,-,-,6）
                                </code>

                                <p>注：提示信息中从左至右依次显示 万,千,百,十,个位的投注内容，之间用 , 号隔开。
                                    - 表示在某一位上用户未投注。
                                </p>

                                <p>轮盘中内圈采用的是秒秒彩 十位 开出的结果。0-9十个数字中奇数对应虎、偶数对应龙。例如
                                    当用户押注龙时，等同于押注了十位的所有偶数(0,2,4,6,8)。当开出结果为任意偶数，比如8（此时指针指向龙8），用户即为中奖。</p>

                                <p>这实际是秒秒彩 一星 玩法群中的 十位复式 玩法。因此在投注记录中的括号内有如下提示：</p>

                                <code> 十位： 龙（一星复式 -,-,-,02468,-）</code>

                                <p>高级玩法同时取决于个、十 两位的开奖结果。例如 当用户选择了龙8和猴6进行押注时，等同于押注了8（十位）、6（个位）。当同时开出十位为8、个位为6（此时指针指向
                                    猴和龙8）,用户即为中奖。</p>
                                <p>这实际是秒秒彩 二星 玩法群中的，后二_直选复式 玩法。因此在投注记录中的括号内有如下提示:</p>

                                <code>
                                    高级 ： 龙8、猴6

                                    （后二_直选复式 -,-,-,8,6）
                                </code>


                                <p>除此之外，秒秒彩中还有着丰富多样的玩法（五星复试奖金180000元），您可以进入秒秒彩游戏中进行了解；</p>

                            </div>
                        </div>
                    </ion-scroll>
                </div>
                <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/download.png">
            </div>
        </div>
        <div class="morespecial {{showrc?'block':''}}" ng-click="clickmorespecial()">
            <div class="bbb">
                <div class="bbb2">
                    <ion-scroll direction="y" scrollbar-y="true" zooming="false" class="scroll2" has-bouncing="false">
                        <div class="morespecial-c-bg">
                            <div class="morespecial-c">
                                <table class="table table-hover" cellpadding="0" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th>期号</th>
                                        <th>开奖结果</th>
                                        <th>投注金额</th>
                                        <th>中奖金额</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr ng-repeat="item in config.records"  ng-click="recordetail(item.orderId, item.orderCode, $event)">
                                        <td><span class="time">{{item.formatSaleTime | date:'MM-dd HH:mm:ss'}}</span></td>
                                        <td><span class="d">{{item.numberRecord}}</span></td>
                                        <td><span class="d">{{item.totamount}}</span></td>
                                        <td><span class="d">{{item.totwin ? ((item.totwin | number:2)+'元'):'未中奖'}}</span></td>
                                    	<td><span class="d">&gt;</span></td>
									</tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </ion-scroll>
                </div>
                <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/download.png">
            </div>
        </div>
        <div class="titles">
            <div class="row">
                <div class="col">
                    <div class="mychoose"><img ng-click="showRecord()" src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/recordbutton.png" alt=""></div>
                </div>
                <div class="col text-right">
                    <div class="issuss" ng-click="showRecord()">

                        <div class="issuss-c">
                            <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/beforeissue.png" alt="">
                            <div class="issuss-ccc">
                                <div class="issue-data">
                                    <span class="n">{{numberMaps[0]}}</span>
                                    <span class="w">{{numberMaps[1]}}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="mod"></div>
        <div class="rotation-bg">
            <div class="myissuesshow">
                <div class="myissuesshow-c">
                    <span class="n">{{numberMaps[0]}}</span>
                    <span class="w">{{numberMaps[1]}}</span>
                </div>
            </div>

            <div class="select-opotion">
                <div class="ll" ng-click="goexpation2()"><img ng-src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/lefttrim.png"
                                                              my-touchstart="onTouch($event,$element)" class="touchimg"
                                                              alt=""></div>
                <div class="rr text-right" ng-click="openModal()">
                    <div class="advancepieliu">{{advancepieliu}}</div>
                    <img on-touchstart="onTouch($event,$element)"
                         class="touchimg" ng-src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/righttrim01.png" alt=""></div>
            </div>

            <div class="bd">

                <div class="peiliu">{{geweipieliu}}</div>
                <div class="mainrotation">
                    <div class="centerpoint">
                        <div class="chipitem-contain">
                        </div>
                    </div>
                </div>
                <div class="arrowdrawing"></div>
                <div class="bigdrawing">
                    <div class="jiang" ng-if="geweiWing">
                        <div class="hh3"><img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/tit_price.png" alt=""></div>
                        <div class="pp3">{{geweiWing | number:1}}</div>
                    </div>
                </div>
                <div class="minpeiliu">{{shiweipieliu}}</div>
                <div class="minrotation">
                    <div class="dragontigerdrawing dragontigerdrawing_r">
                        <table class="jiang" ng-if="shiweiWing">
                            <tr>
                                <td>
                                    <span class="hh3"><img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/tit_price.png" alt=""></span><br>
                                    <span class="pp3">{{shiweiWing | number:1}}</span>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="mindrawing"></div>
            </div>

            <div class="bottom-choose">
                <div class="row">
                    <div class="col">
                        <div class="balances">
                            <table cellspacing="0" cellpadding="0" class="c">
                                <tr style="visibility: hidden;">
                                    <td>space</td>
                                </tr>
                                <tr>
                                    <td><span class="balance-number">{{config.balance | number:2}}</span>
                                        <!--<a href="">&lt;!&ndash;<img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/charge.png" alt=""/>&ndash;&gt;</a>-->
                                    </td>
                                </tr>
                            </table>
                        </div>

                    </div>
                    <div class="col text-right">
                        <div class="prewin">
                            <table cellspacing="0" cellpadding="0" class="c">
                                <tr style="visibility: hidden;">
                                    <td>space</td>
                                </tr>
                                <tr>
                                    <td><span class="balance-number">{{config.winMoney | number:2}}</span>
                                    </td>
                                </tr>
                            </table>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="advancedpopup">
        <div class="advanced">
            <div class="close-bottun"><img ng-click="confirmPopupClose()" src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/close.png" alt=""/></div>
            <div class="h33 h332"><b>高级玩法</b><span>{{advaceTip}}</span></div>
            <!--选号容器-->
            <div class="number-select-content">
                <!--业务模板数据-->
                <div class="number-select-list" ng-repeat="(ballLabel,balls) in ballsTree">
                    <div class="gp" ng-if="ballLabel=='个位'">
                        <span class="h33"><span class="one-word">{{ ballLabel}}</span></span>
                    <span class="itemgroup">
                        <span ng-repeat="(k,v) in range(0,10)"
                              class="item-detail"
                              ng-click="result(balls,k,currentMethodName,v,$event)"><img
                                ng-src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/ami{{v}}.png" alt="">

                        </span>
                    </span>
                    </div>
                    <div class="gp" ng-if="ballLabel=='十位'">
                        <span class="h33"><span class="one-word">{{ ballLabel}}</span></span>
                    <span class="itemgroup">
                        <span
                                ng-repeat="(k,v) in range(0,10)"
                                ng-if="$index % 2 ==0"
                                class="item-detail"
                                ng-click="result(balls,k,currentMethodName,v,$event)"><img
                                ng-src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/dragon{{v}}.png" alt="">

                        </span>
                        <span ng-repeat="(k,v) in range(0,10)"
                              ng-if="$index % 2 != 0"
                              class="item-detail"
                              ng-click="result(balls,k,currentMethodName,v,$event)"><img
                                ng-src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/dragon{{v}}.png" alt="">
                        </span>
                    </span>
                    </div>
                </div>
                <!--业务模板数据-->
            </div>


            <div class="playnow">
                <div class="ballpool">
                    <div class="num">{{advancepieliu}}</div>
                    <div>请在此下注</div>
                </div>
            </div>
        </div>
    </div>
  </ion-view>
</script>
    <!--================================================== recordetail ======================================-->
    <script id="templates/recordetail.html" type="text/ng-template">
      <div style="overflow: scroll">
      <div id="about" class="nano">
		
          <div class="nano-content">
              <table class="table table-hover record-detail" cellpadding="0" cellspacing="0">
                  <tbody ng-repeat="item in recordetailData">
                  <tr>
                      <td><span class="d">{{item.betDetail.myreplace()}}</span></td>
                      <td><span class="d">投注</span></td>
                      <td><span class="d"> 奖金 </span></td>
                  </tr>
                  <tr>
                      <td><span class="d">（{{item.gamePlayName}} {{item.betDetail}}）</span></td>
                      <td><span class="d">{{item.totamount/10000 | number:2}}元</span></td>
                      <td><span class="d"> {{item.award ? ((item.award/10000 | number:2)+'元')   : '未中奖'}}</span></td>
                  </tr>
                  <tr class="emptys">
                      <td>空</td>
                      <td>空</td>
                      <td>空</td>
                  </tr>
                  </tbody>
              </table>
          </div>
      </div>
	  </div>
      <div class="whatsthat"><a ng-click="goexpation()">括号里的内容是什么？</a></div>
    </script>

<script>
    angular.bootstrap(document, ['starter']);
</script>
<div style="display:none ">
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/1.jpg?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/ami4.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/balance.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/cancel.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/clip2.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/comfirm.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/dragon4.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/high_light_circle.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/minrotation.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/righttrim02.png?v=2"/>

    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/2.jpg?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/ami5.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/beforeissue.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/canceladvance.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/clip3.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/download.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/dragon5.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/high_light_circle_r.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/minrotation_num.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/righttrim_active.png?v=2"/>

    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/ami0.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/ami6.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/bg.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/charge.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/clip4.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/dragon0.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/dragon6.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/high_light_inside.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/pick.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/started.png?v=2"/>

    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/ami1.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/ami7.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/bigrotation.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/chip.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/clip5.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/dragon1.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/dragon7.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/high_light_outside.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/prewin.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/startedadvance.png?v=2"/>

    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/ami2.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/ami8.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/bigrotationnum.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/clip0.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/clip6.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/dragon2.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/dragon8.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/lefttrim.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/recordbutton.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/tit_price.png?v=2"/>

    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/ami3.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/ami9.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/buttom-bg.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/clip1.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/close.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/dragon3.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/dragon9.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/lefttrim_active.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/righttrim01.png?v=2"/>
    <img src="${staticFileContextPath}/static/images/wap/shierShengXiao/img/titlebg.png?v=2"/>

</div>
</body>
</html>
