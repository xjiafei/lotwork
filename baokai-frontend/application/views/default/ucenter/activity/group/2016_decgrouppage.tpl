<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>宝开新春季</title>
  <meta name="viewport" content="initial-scale=1, maximum-scale=1">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <link rel="stylesheet" href="{$path_img}/images/activity/2016_DecRedBow/activityGroup/css/index.css">
</head>

<body onload="querySignUp();queryJanSignUp();">


  <div class="header">
    <div class="content">
      <a class="logo" href="/index">宝开彩票logo</a>

      <div class="cutdown">
        <div class="timer">
          <span>抽奖倒计时</span>
          <p class="timesamp" id="timesamp">30:00</p>
        </div>
        <ul class="user-list clearfix J_user_list">
          <li class="item item0 J_item">-----</li>
          <li class="item item1 J_item">-----</li>
          <li class="item item2 J_item">-----</li>
          <li class="item item3 J_item">-----</li>
          <li class="item item4 J_item">-----</li>
        </ul>
        <p class="tips J_tips"> 还没抽到你！<br>继续努力哦！ </p>
      </div>

      <div class="btns">
        <button class="btn btn-join ga_join_12" type="button" onclick="signUp();">立即报名</button>
        <button class="btn btn-joined" type="button" style="display:none;">已报名</button>
		<!-- <a class="btn btn-viewDetail" href="javascript:;">了解详情</a> -->
        <a class="btn btn-viewDetail" href="/activity/decredbowfour">了解详情</a>
      </div>

      <div class="fly">
        <div class="plan"></div>
        <div class="cloud1"></div>
        <div class="cloud2"></div>
        <div class="cloud3"></div>
      </div>
    </div>
  </div>

  <div class="content">
    <div class="reward">
      <div class="list">
        <ul class="tab-list clearfix">
          <li class="item cur J_reward_tab">第一期</li>
          <li class="item J_reward_tab">第二期</li>
          <li class="item J_reward_tab">第三期</li>
          <li class="item J_reward_tab">第四期</li>
        </ul>
        <div>
          <ul class="reward-list clearfix J_reward_list">
            <li class="reward-item">
              <div class="time">20:00</div>
              <ul class="user-list" weekpoint="0-0">
              </ul>
            </li>
            <li class="reward-item darken">
              <div class="time">21:00</div>
              <ul class="user-list" weekpoint="0-1">
              </ul>
            </li>
            <li class="reward-item darken">
              <div class="time">22:00</div>
              <ul class="user-list" weekpoint="0-2"></ul>
            </li>
            <li class="reward-item darken">
              <div class="time">23:00</div>
              <ul class="user-list" weekpoint="0-3"></ul>
            </li>
          </ul>
          <ul class="reward-list clearfix J_reward_list" style="display: none;">
            <li class="reward-item">
              <div class="time">20:00</div>
              <ul class="user-list" weekpoint="1-0"></ul>
            </li>
            <li class="reward-item darken">
              <div class="time">21:00</div>
              <ul class="user-list" weekpoint="1-1"></ul>
            </li>
            <li class="reward-item darken">
              <div class="time">22:00</div>
              <ul class="user-list" weekpoint="1-2"></ul>
            </li>
            <li class="reward-item darken">
              <div class="time">23:00</div>
              <ul class="user-list" weekpoint="1-3"></ul>
            </li>
          </ul>
          <ul class="reward-list clearfix J_reward_list" style="display: none;">
            <li class="reward-item">
              <div class="time">20:00</div>
              <ul class="user-list" weekpoint="2-0"></ul>
            </li>
            <li class="reward-item darken">
              <div class="time">21:00</div>
              <ul class="user-list" weekpoint="2-1"></ul>
            </li>
            <li class="reward-item darken">
              <div class="time">22:00</div>
              <ul class="user-list" weekpoint="2-2"></ul>
            </li>
            <li class="reward-item darken">
              <div class="time">23:00</div>
              <ul class="user-list" weekpoint="2-3"></ul>
            </li>
          </ul>
          <ul class="reward-list clearfix J_reward_list" style="display: none;">
            <li class="reward-item">
              <div class="time">20:00</div>
              <ul class="user-list" weekpoint="3-0"></ul>
            </li>
            <li class="reward-item darken">
              <div class="time">21:00</div>
              <ul class="user-list" weekpoint="3-1"></ul>
            </li>
            <li class="reward-item darken">
              <div class="time">22:00</div>
              <ul class="user-list" weekpoint="3-2"></ul>
            </li>
            <li class="reward-item darken">
              <div class="time">23:00</div>
              <ul class="user-list" weekpoint="3-3"></ul>
            </li>
          </ul>
        </div>
      </div>

      <ul class="app-tips">
        <li class="item">凡是活动期间登录宝开彩票手机端既有机会获得神秘红包。</li>
        <li class="item">用户领取神秘红包后，在提示下完成指定彩种投注，且投注金额≥神秘红包金额， 即有资格领取第二个神秘红包（再次登录即可领取）</li>
        <li class="item">宝开彩票平台保留活动最终解释权。</li>
      </ul>
    </div>

    <div class="rules">
      <a class="btn btn-viewDetail1 ga_join_1" href="javascript:;" onclick="janSignUp();">立即报名</a>
      <a class="btn btn-viewDetail1ed" href="javascript:;" style="display:none;">立即报名</a>

    <div class="rules-header">
      <ul class="tab-list">
        <li class="item J_rules_tab disabled" id="firstTab">APP首投送18 <br><small>(已结束)</small></li>
        <li class="item J_rules_tab" id="secondTab">官彩狂欢周</li>
      </ul>
    </div>

      <div class="rules-app J_rules_con">
        <h3 class="title">APP首次投注送18，连续投注还可获得888元</h3>
        <p>1. 活动时间内首次使用移动端投注即可获得18元奖金;<br>2. 使用移动端连续投注还可获得更高奖金</p>

        <div class="activity">
          <h4 class="subtitle">活动分为两期 </h4>
          <p>第一期时间：2017.1.2 0:00:00 – 2017.1.7 23:59:59</p>
          <p>第二期时间：2017.1.9 0:00:00 – 2017.1.14 23:59:59</p>

          <table class="table">
            <tbody>
              <tr>
                <th rowspan="2">日均投注额（元）</th>
                <th colspan="4">最大连续投注天数 / 返奖比例（元）</th>
              </tr>
              <tr>
                <td>6 天</td>
                <td>5 天</td>
                <td>4 天</td>
                <td>3 天</td>
              </tr>
              <tr>
                <th>100 ≤ 投注额 ＜ 1,000</th>
                <td>48</td>
                <td>24</td>
                <td>12</td>
                <td>6</td>
              </tr>
              <tr>
                <th>1,000 ≤ 投注额 ＜ 5,000</th>
                <td>288</td>
                <td>144</td>
                <td>72</td>
                <td>36</td>
              </tr>
              <tr>
                <th>投注额 ≥ 5,000</th>
                <td>888</td>
                <td>444</td>
                <td>222</td>
                <td>111</td>
              </tr>
            </tbody>
          </table>

          <p>日均销量=活动期间总销量/6日</p>
          <p>派奖范例（用户A、用户B，1月2日-1月7日投注金额如下）</p>

          <table class="table">
            <tbody>
              <tr>
                <th>日均投注额（元）</th>
                <th>1月2日</th>
                <th>1月3日</th>
                <th>1月4日</th>
                <th>1月5日</th>
                <th>1月6日</th>
                <th>1月7日</th>
              </tr>
              <tr>
                <th>用户A</th>
                <td>200</td>
                <td>2,000</td>
                <td>700</td>
                <td>-</td>
                <td>2,000</td>
                <td>50</td>
              </tr>
              <tr>
                <th>用户B</th>
                <td>450</td>
                <td>230</td>
                <td>110</td>
                <td>1,300</td>
                <td>15,278</td>
                <td>4,675</td>
              </tr>
            </tbody>
          </table>

          <p>用户A：6日日均投注金额为825元，最大连续为3天，所以该用户可获得6元。</p>
          <p>用户B：6日日均投注金额为3,673.8元，连续6天，所以该用户可获得288元奖金。</p>
        </div>

        <div class="activity-rules">
          <h4 class="subtitle">活动说明</h4>
          <ul class="rules-list">
            <li class="item">活动返奖，参与游戏只限于宝开彩票手机端。投注必须为已开奖注单，视为有效投注，奖励计算依据用户在活动期间内的有效投注量审核计算。</li>
            <li class="item">本次活动采取报名制，用户需要点击“我要报名”后才能获取活动奖金。注：VIP3（包含VIP3）以上用户无需报名即可参加。</li>
            <li class="item">奖金发放时间：活动结束后次日派奖。</li>
            <li class="item">活动期间禁止任何刷量作弊行为，一经发现宝开彩票平台将取消参与活动资格，严重者将被冻结账号处理。</li>
          </ul>
        </div>
      </div>

      <div class="rules-lottery J_rules_con">
        <h3 class="title">官彩狂欢周，你投注我就送，最高送您28888</h3>

        <div class="activity-time">
          <h4 class="subtitle">活动时间</h4>

          <p>2017年1月16日 00:00:00～1月21日 23:59:59</p>
          <p>活动期间每日用户投注平台所有官方彩种，达到以下标准即可获得相对应的奖金，最高28,888！</p>

          <table class="table">
            <tbody>
              <tr>
                <th>项目</th>
                <th>销量</th>
                <th>奖金返利比例</th>
                <th>VIP用户奖金返利比例</th>
                <th>奖金最高上限</th>
              </tr>
              <tr>
                <th rowspan="5">官方彩种</th>
                <td>≥ 1,000</td>
                <td>0.3%</td>
                <td>0.4%</td>
                <td rowspan="5">28,888</td>
              </tr>
              <tr>
                <td>≥ 10,000</td>
                <td>0.5%</td>
                <td>0.6%</td>
              </tr>
              <tr>
                <td>≥ 50,000</td>
                <td>0.7%</td>
                <td>0.8%</td>
              </tr>
              <tr>
                <td>≥ 500,000</td>
                <td>0.9%</td>
                <td>1%</td>
              </tr>
              <tr>
                <td>≥ 1,000,000</td>
                <td>1.2%</td>
                <td>1.5%</td>
              </tr>
            </tbody>
          </table>

        </div>

        <div class="activity-rule">
          <h4 class="subtitle">活动规则</h4>

          <ul class="rules-list">
            <li class="item">活动返奖，参与游戏只限于宝开彩票旗舰版及宝开彩票专业版平台官方彩种（重庆时时彩、黑龙江时时彩、新疆时时彩、天津时时彩、上海时时乐、山东11选5、江西11选5、广东11选5、北京快乐8、江苏快三、安徽快三、江苏骰宝、3D、P3/P5、双色球、六合彩）。</li>
            <li class="item">投注必须为已开奖注单，视为有效投注，骰宝大小单双、猜一个号码玩法销量不计算在内，活动销量两个平台分别独立计算。</li>
            <li class="item">本次活动采取报名制，用户需要点击“我要报名”后才能获取活动奖金。注：VIP3（包含VIP3）以上用户无需报名即可参加。</li>
            <li class="item">超级2000玩法按实际投注额的80%进行结算。<br>如：用户超级2000玩法中投注额为1,000，则结算销量为1,000*80%=800。</li>
            <li class="item">活动奖金于次日18:00点前派发。</li>
            <li class="item">活动期间禁止任何作弊行为，一经发现平台将取消参与活动资格，严重者将被冻结账号处理。</li>
            <li class="item">宝开平台保留活动最终解释权。</li>
          </ul>
        </div>
      </div>
      <div class="footer"></div>
    </div>

  </div>


  <script type="text/javascript">
    var userRankingWins = JSON.parse('{$userRankingWins}'); //已開獎名單
    var activityConfigs = JSON.parse('{$activityConfigs}'); //活動設定檔
    var configTimes = JSON.parse('{$configTimes}'); //開獎時間定義
    var isWined = '{$isWined}';//判斷是否已經中獎 0:未中獎, 1:已中獎
    var isSecondActivityEnd = '{$isSecondActivityEnd}';// 0:尚未結束, 1:已經結束
	
    (function(i, s, o, g, r, a, m) {
      i['GoogleAnalyticsObject'] = r;
      i[r] = i[r] || function() {
        (i[r].q = i[r].q || []).push(arguments)
      }, i[r].l = 1 * new Date();
      a = s.createElement(o),
        m = s.getElementsByTagName(o)[0];
      a.async = 1;
      a.src = g;
      m.parentNode.insertBefore(a, m)
    })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');

    ga('create', 'UA-63536434-1', 'auto');
    //ga('create', 'UA-71901456-1', 'auto');//dev02 QA測試用
    ga('send', 'pageview');
    ga('send', 'event', '进入聚合页', '点击【点击进入】聚合页');

    var oJoin1 = document.querySelector('.ga_join_1');
    var oJoin12 = document.querySelector('.ga_join_12');
    if (oJoin12) {
      oJoin12.addEventListener('click', function() {
        ga('send', 'event', '聚合頁-12月第四周活动报名', '点击【立即报名】按钮');
      }, false);
    }
    if (oJoin1) {
      oJoin1.addEventListener('click', function() {
        ga('send', 'event', '聚合頁-1月活动报名', '点击【立即报名】按钮');
      }, false);
    }
  </script>
  <script src="{$path_js}/js/activity/2016_DecRedBow/group/js/index.js"></script>
  <script src="{$path_js}/js/activity/2016_DecRedBow/group/js/signUp.js"></script>
</body>

</html>
