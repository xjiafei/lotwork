<!doctype html>
<html lang="zh-cmn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>英雄梦之“销”里藏宝</title>
    <meta name="description" content="宝开彩票">
    <meta name="viewport" content="width=750,user-scalable=no">
    <script>
        var match,
            scale,
            TARGET_WIDTH = 750;
        if (match = navigator.userAgent.match(/Android (\d+\.\d+)/)) {
            if (parseFloat(match[1]) < 4.4) {
                TARGET_WIDTH++;
                scale = window.screen.width / TARGET_WIDTH;
                document.querySelector('meta[name="viewport"]').setAttribute('content', 'width=' + TARGET_WIDTH + ', initial-scale = ' + scale + ', target-densitydpi=device-dpi ,user-scalable=no');
            }
        } else {
            document.querySelector('meta[name="viewport"]').setAttribute('content', 'width=' + TARGET_WIDTH);
        }
    </script>    
    <link rel="stylesheet" href="{$path_img}/images/activity/2017_June/activity03/mb/images/main.css">
    <script src="{$path_js}/js/activity/2017_June/activity03/mb/jquery-3.2.0.min.js"></script>
    <script src="{$path_js}/js/activity/2017_June/activity03/mb/index.js"></script>
</head>
<body>
<div id="main">
    <div id="main-body">
          <a href="phlotto://go_lottolobby" id="logo-link"></a>
        <div id="animation-part">
            <div id="sign-up"></div>
            <div id="progress" ></div>
            <div id="float"></div>
            <div id="progress-f" class="progress-f"></div>
            <div id="crystal" ></div>
            <div id="crystal-shine" ></div>
            <div id="crystal-info">
                <div><span class="cr-blue">“</span>水晶里<span class="cr-blue">”蕴藏着巨大的魔力，</span></div>
                <div><span class="cr-blue">“等待魔法师您的</span>开启<span class="cr-blue">！</span></div>
            </div>
        </div>
        <div id="activity-content">
            <div id="content-info">
                <span id="info1">活动期间已报名用户每日投注≥1200元， 即每日可参与一次对应金额抽奖机会，隔日清零重新计算。 </span>
                <span id="info2">100%中奖哟!</span>
            </div>
            <div id="content-form">
                <table>
                    <tr>
                        <td id="td0" colspan="6">抽奖详情</td>
                    </tr>
                    <tr>
                        <td class="td1">有效投注</td>
                        <td class="td2">≥1200元</td>
                        <td class="td3">≥12000元</td>
                        <td class="td4">≥60000元</td>
                        <td class="td5">≥120000元</td>
                        <td>≥240000元</td>
                    </tr>
                    <tr>
                        <td>最高可抽</td>
                        <td class="pindTd">188元</td>
                        <td class="pindTd">288元</td>
                        <td class="pindTd">1488元</td>
                        <td class="pindTd">3388元</td>
                        <td class="pindTd">6788元</td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="activity-rules">
            <ul>
                <li>1.活动返奖，参与游戏只限于宝开彩票旗舰版。</li>
                <li>2.投注必须为已开奖注单，视为有效投注，奖励计算依据用户在活动期间内的有效投注量审核计算， PT、宝开游艺FHX、六合彩、骰 宝大小单双销量不计算在活动销量内。</li>
                <li>3.本次活动采取报名制，用户需要点击“我要报名”，达到销量即可自行点击抽奖。注：VIP3（包含VIP3）以上用户无需报名即可参加。</li>
                <li>4.活动兑换奖金仅限当日有效，活动当日没有兑换奖金，当日奖金将作废，次日00:00:00将清空前一日销量并且重新计算销量。</li>
                <li>5.超级2000玩法按实际投注额的80%进行结算。如：用户超级2000玩法中投注额为1,000，则结算销量为1,000*80%=800。</li>
                <li>6.活动期间禁止任何作弊行为，一经发现平台将取消参与活动资格，严重者将被冻结账号处理。</li>
                <li>7.宝开平台保留活动最终解释权。</li>
            </ul>
        </div>
        <div id="footer">©2003-2017 宝开彩票 All Rights Reserved</div>


    </div>
</div>
<div id="popup">

    <a href="javascript:void(0)" id="popup-close"></a>

    <div id="popup-info">

    </div>
</div>
<div id="mask"></div>
    
<input id="signStatus" type="hidden" value="{$signStatus}"/>
<input id="status" type="hidden" value="{$status}"/>
<input id="token" type="hidden" value="{$token}"/>
<input id="awardStatus" type="hidden" value="{$awardStatus}"/>
<input id="bet" type="hidden" value="{$bet}"/>
<input id="award" type="hidden" value="{$award}"/>
    
</body>
</html>
