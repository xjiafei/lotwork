<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>温情互动，感恩回馈</title>
    <meta name="description" content="宝开彩票">
    <meta name="viewport" content="width=720,user-scalable=no">
    <script>
        var match,
            scale,
            TARGET_WIDTH = 720;

        if (match = navigator.userAgent.match(/Android (\d+\.\d+)/)) {
            if (parseFloat(match[1]) < 4.4) {
                if (TARGET_WIDTH == 720) TARGET_WIDTH++;
                var scale = window.screen.width / TARGET_WIDTH;
                document.querySelector('meta[name="viewport"]').setAttribute('content', 'width=' + TARGET_WIDTH + ', initial-scale = ' + scale + ', target-densitydpi=device-dpi ,user-scalable=no');
            }
        } else {
            document.querySelector('meta[name="viewport"]').setAttribute('content', 'width=' + TARGET_WIDTH);
        }
    </script>
    <link rel="stylesheet" href="{$path_img}/images/activity/2017_April/survey/css/mobile_main.css">
</head>
<body>
<div id="main">
    <div id="main-body">
        <a href="phlotto://go_lottolobby" target="_blank" id="logo-link"></a>
        <div id="activity-content">
            <div id="content-title">
                <strong>活动内容</strong>
            </div>
            <div id="content-list">
                <ul>
                    <li>活动期间填写调研问卷即可在次日获得一个6元红包</li>
                    <li>同时可参与<span style="font-size: 21px;font-weight: 900;">每周日晚20:00点</span>的抽奖活动。中奖率90%呦！</li>
                    <li class="list3">您在问卷中填写的手机号码和QQ号码，</li>
                    <li class="list3">将作为您抽奖活动中获得的话费和购物电子卡的收货信息，</li>
                    <li class="list3" style="padding:0">请您务必填写正确！</li>
                </ul>
            </div>
        </div>
        <div id="fill-survey">
            <a href="javascript:void(0)" id="start-filling"></a>
        </div>
        <div id="luck-draw">
            <div id="countdown">
                <div id="countdown-status">周日抽奖</div>
                <div id="countdown-time" class="timestamp">
                    <span id="min"></span>
                    <span id="colon">&#12288;</span>
                    <span id="sec"></span>
                </div>

            </div>
            <div class="prize-card" id="card-0"></div>
            <div class="prize-card" id="card-1"></div>
            <div class="prize-card" id="card-2"></div>
            <div class="prize-card" id="card-3"></div>
            <div class="prize-card" id="card-4"></div>
            <div class="prize-card" id="card-5"></div>
            <div class="prize-card" id="card-6"></div>
            <div class="prize-card" id="card-7"></div>
            <div class="prize-card" id="card-8"></div>
            <div class="prize-card" id="card-9"></div>
            <div class="prize-card" id="card-10"></div>
            <div class="prize-card" id="card-11"></div>
        </div>
        <div id="win-area">
            <div id="win-info">
                敬请期待第一次摇奖哦
            </div>

        </div>
        <div id="carousel">
            <div id="carousel-title">
                中奖名单
            </div>
            <div id="carousel-list">
                <div class="jcarousel-wrapper">
                    <div class="jcarousel">
                        <ul>
                            <!--一共300人-->
                            <!-- {if $awardList == 0}-->
							
                            <li class="carousel-page">
                                <ul class="winner-list">
									<!-- {for $var=1 to 5} -->
                                    <li>
                                        <ul class="winner-sublist">
											<li>&nbsp;</li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>
                                            <li>未开始抽奖</li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>	
                                        </ul>
                                    </li>
									<!-- {/for} -->
                                </ul>
                            </li>
                            <!--{else}-->
							{{assign var=count value=0}}
							{{assign var=count2 value=0}}
							<!-- {for $var1=0 to $awardListSize} -->
							
							<li class="carousel-page">
                                <ul class="winner-list">
									<!-- {for $var2=0 to 4} -->
                                    <li>
                                        <ul class="winner-sublist">
											<!-- {for $var3=0 to 9} -->
											<!-- {if $var2 >0} -->
											{{assign var=index value={$var2|cat:$var3}}}
											<!-- {else} -->
											{{assign var=index value=$var3}}
											<!-- {/if} -->
                                            <li>{$awardList[$var1][$index]}</li>
											<!-- {/for} -->
                                        </ul>
                                    </li>
									<!-- {/for} -->
                                </ul>
                            </li>
							{$count++}
							<!-- {/for} -->
							<!--{/if}-->
                        </ul>
                    </div>


                    <a href="#" class="jcarousel-control-prev"></a>
                    <a href="#" class="jcarousel-control-next"></a>

                    <p class="jcarousel-pagination">

                    </p>
                </div>
            </div>
        </div>
        <div id="activity-rule">
            <div id="rule-title">活动规则</div>
            <div id="rule-list">
                <ul>
                    <li>1.参与活动资格：从2017年1月1日开始至填写调研问卷前，在平台累计</li>
                    <li class="none-style-li">投注≥1000的用户。</li>

                    <li class="none-style-li">（温馨提示：新注册用户需要投注达到1000以后再填写调研问卷呦~</li>
                    <li class="none-style-li">之前填写视为无效。）</li>
                    <li>2.彩票类游戏投注数据时时更新，PT和FHX宝开游艺投注数据会在2小</li>
                    <li class="none-style-li">时内更新，望知晓。</li>
                    <li>3.本次活动仅限同IP同账号填写一次。</li>
                    <li>4.温馨提示：您填写的手机号码和QQ号码将作为您抽奖中话费和购物电子</li>
                    <li class="none-style-li">卡的收货信息哦~请您务必填写正确。</li>
                    <li>5.中实物奖品的用户会在中奖后平台以站内信的方式或者中奖用户联系在</li>
                    <li class="none-style-li">线客服的方式收集您的收货地址。请您务必在抽中后7日内填写收货地址。</li>
                    <li class="none-style-li">逾期视为您自动放弃。</li>
                    <li>6.宝开彩票平台保留活动最终解释权。</li>
                </ul>
            </div>
        </div>
    </div>
	<input type="hidden" id="account" value="{$account}">
	<input type="hidden" id="systemTime" value="{$systemTime}">
	<input type="hidden" id="startDate" value="{$startDate}">
	<input type="hidden" id="endDate" value="{$endDate}">
	<input type="hidden" id="award" value="{$award}">
	<input type="hidden" id="awardList" value="{$awardList}">
	<input type="hidden" id="isQualifi" value="{$isQualifi}">
	<input type="hidden" id="countHour" value="{$countHour}">
	<input type="hidden" id="countMin" value="{$countMin}">
	<input type="hidden" id="activityStart" value="{$activityStart}">
	<input type="hidden" id="token" value="{$token}"/>

</div>
<div id="popup">
    <div id="popup-info">
        <div>活动说明</div>
    </div>
    <div id="popup-button-area">
        <a href="javascript:void(0)" id="popup-close">关闭</a>
        <a href="phlotto://go_lottolobby" id="popup-finish">&#12288;
        </a>
    </div>
</div>
<div id="mask"></div>
<script src="{$path_js}/js/activity/2017_April/survey/jquery-1.12.0.min.js"></script>
<script src="{$path_js}/js/activity/2017_April/survey/jquery.jcarousel.min.js"></script>
<script src="{$path_js}/js/activity/2017_April/survey/localStorage.min.js"></script>
<script src="{$path_js}/js/activity/2017_April/survey/mobile_main.js"></script>
</body>
</html>
