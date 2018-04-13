<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="UTF-8">
    <title>开春送礼</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <!-- <link rel="shortcut icon" href="/favicon.ico"> -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
      <link rel="stylesheet" href="{$path_img}/images/activity/2017_FebOpenSpring/css/index.css">
</head>

<body onload="query20170202SignUp();query20170203SignUp();">

  <div class="pane pane-1 J_pane">
    <div class="contain">
      <a class="logo" href="/index">宝开平台logo</a>

      <h1 class="title">首串金元宵，签到拿红包</h1>

      <div class="reward1">
        <p class="rules">活动期间每天签到投注满<span> 666 </span>元即可抽取随机红包一个</p>
        <ul class="calendar clearfix J_calendar">
          <li class="item">
            <div class="date">
              <div class="thumb date-3"></div>
              <div class="mask"></div>
            </div>
            <button class="btn " type="button">领取</button>
            <p class="tips"></p>
          </li>
          <li class="item">
            <div class="date">
              <div class="thumb date-4"></div>
              <div class="mask"></div>
            </div>
            <button class="btn " type="button">领取</button>
            <p class="tips"></p>
          </li>
          <li class="item">
            <div class="date">
              <div class="thumb date-5"></div>
              <div class="mask"></div>
            </div>
            <button class="btn " type="button">领取</button>
            <p class="tips"></p>
          </li>
          <li class="item">
            <div class="date">
              <div class="thumb date-6"></div>
              <div class="mask"></div>
            </div>
            <button class="btn" type="button">领取</button>
            <p class="tips"></p>
          </li>
          <li class="item">
            <div class="date">
              <div class="thumb date-7"></div>
              <div class="mask"></div>
            </div>
            <button class="btn " type="button">领取</button>
            <p class="tips"></p>
          </li>
          <li class="item">
            <div class="date">
              <div class="thumb date-8"></div>
              <div class="mask"></div>
            </div>
            <button class="btn " type="button">领取</button>
            <p class="tips"></p>
          </li>
          <li class="item">
            <div class="date">
              <div class="thumb date-9"></div>
              <div class="mask"></div>
            </div>
            <button class="btn" type="button">领取</button>
            <p class="tips"></p>
          </li>
          <li class="item">
            <div class="date">
              <div class="thumb date-10"></div>
              <div class="mask"></div>
            </div>
            <button class="btn" type="button">领取</button>
            <p class="tips"></p>
          </li>
          <li class="item">
            <div class="date">
              <div class="thumb date-11"></div>
              <div class="mask"></div>
            </div>
            <button class="btn" type="button">领取</button>
            <p class="tips"></p>
          </li>
        </ul>

      </div>
      <div class="reward2 clearfix">
        <p class="rule">如当期当天未领取视为自动放弃 · <span>连续签到还可获得更高金额红包</span></p>
        <div class="max-continue">
          <!-- <i class="double">X2</i> -->
          <p class="continue"><strong class="num J_continueDay">0</strong>天</p>
          <!-- <a href="javascript:;" class="receive">点击领取</a> -->
        </div>

        <ul class="match-list">
          <li class="item ">连续 3 天 - 获取 16 元红包 <small>两次连续3天，可领取2次</small></li>
          <li class="item ">连续 6 天 - 获取 36 元红包</li>
          <li class="item ">连续 9 天 - 获取 66 元红包</li>
        </ul>
      </div>

    </div>
  </div>


  <div class="pane pane-2 J_pane">
    <div class="contain">
      <h1 class="title">二串齐欢聚，充值领资金</h1>

      <button id="btn2-join" class="btn btn-join" type="button" onclick="febSecondSignUp();"> 立即报名</button>
       <button id="btn2-joined" class="btn btn-joined" type="button" style="display:none;">立即报名</button>
      <p class="tips">VIP3（包含VIP3）以上用户无需报名</p>

      <div class="rules">
        <p>每日充值≥500 并在当日达到相应流水要求后，次日即可领取对应活动奖金</p>
        <p>每日18点前派发前一天符合条件的用户奖金</p>
        <p>用户点击“立即报名”后即可参加</p>

        <table class="table">
          <tbody>
            <tr class="caption">
              <th colspan="6">当日累计存款金额</th>
            </tr>
            <tr>
              <th>流水要求</th>
              <td>6 倍</td>
              <td>10 倍</td>
              <td>15 倍</td>
              <td>20 倍</td>
              <td>30 倍</td>
            </tr>
            <tr>
              <th>普通用户赠送比例</th>
              <td>8%</td>
              <td>15%</td>
              <td>25%</td>
              <td>35%</td>
              <td>50%</td>
            </tr>
            <tr>
              <th>VIP用户赠送比例</th>
              <td>10%</td>
              <td>18%</td>
              <td>30%</td>
              <td>40%</td>
              <td>60%</td>
            </tr>
			<tr>
              <th>奖金上限</th>
              <td colspan="5">8,888元</td>
            </tr>
          </tbody>
        </table>

        <div class="notice">
          <p>*流水要求 = 当日总销量 / 当日累计充值金额</p>
          <p>*赠送奖金 = 当日累计充值金额 X 赠送比例</p>
          <p>例：用户A当日累计充值1,000元，当日销量为15,000，打够了15倍流水，奖金为1,000*25%=250元。VIP用户奖金为1,000*30%=300元</p>
        </div>
      </div>
    </div>
  </div>

  <div class="pane pane-3 J_pane">
    <div class="contain">
      <h1 class="title">三串再相逢，报名添鸿运</h1>

      <button id="btn3-join"class="btn btn-join" type="button" onclick="febThirdSignUp();">立即报名</button> 
      <button id="btn3-joined" class="btn btn-joined" type="button" style="display:none;">立即报名</button>
      <p class="tips">VIP3（包含VIP3）以上用户无需报名</p>

      <div class="rules">
        <p>累计用户3日内净盈利金额，给予鸿运加奖奖励</p>
        <p>活动结束后次日18点之前派发到用户账户</p>
        <p>用户点击“立即报名”后即可参加</p>

        <table class="table">
          <tbody>
            <tr class="caption">
              <th colspan="5">所有彩种</th>
            </tr>
            <tr>
              <th>3日净盈利</th>
              <td>≥ 1,000</td>
              <td>≥ 10,000</td>
              <td>≥ 30,000</td>
              <td>≥ 50,000</td>
            </tr>
            <tr>
              <th>鸿运金比例</th>
              <td>1%</td>
              <td>1.5%</td>
              <td>2%</td>
              <td>2.5%</td>
            </tr>
            <tr>
              <th>最高上限</th>
              <td colspan="4">5,888</td>
            </tr>
          </tbody>
        </table>

		<div class="notice">
          <p>盈利鸿运金计算方式（不包含PT收益）：</p>
          <p>（客户返奖-客户实际投注额） * 1%～2.5%鸿运金比例＝鸿运金奖金</p>
        </div>
      </div>
    </div>
  </div>

  <div class="pane pane-4 J_pane">
    <div class="contain">
      <h1 class="title">活动说明</h1>

      <ul class="rule">
        <li class="item">活动返奖，参与游戏只限于宝开彩票旗舰版及宝开彩票专业版彩票类游戏。<br>投注必须为已开奖注单，视为有效投注，两个平台销量分别独立计算。</li>
        <li class="item">有领取过签到红包后，即有资格领取连续红包。</li>
        <li class="item">充值包含上下级充值。</li>
        <li class="item">骰宝大小单双销量、六合彩销量、PT和大乐博销量不计算在活动销量内。</li>
        <li class="item">超级2000玩法销量按实际销量的80%计算，例：用户在超级2000投注1,000，活动按照1,000*80%=800进行计算。</li>
        <li class="item">奖励计算依据用户在活动当日内的有效投注量审核计算，两个平台销量分别独立计算。</li>
        <li class="item">活动期间禁止任何刷量作弊行为，一经发现宝开彩票平台将取消参与活动资格，严重者将被冻结账号处理。</li>
        <li class="item">宝开彩票平台保留活动最终解释权.</li>
      </ul>
    </div>
  </div>

  <div class="footer">© 2003 - 2017 宝开彩票  All rights reserved.</div>

  <ul class="nav J_nav">
    <li class="item one current">1</li>
    <li class="divide"></li>
    <li class="item two">2</li>
    <li class="divide"></li>
    <li class="item three">3</li>
    <li class="divide"></li>
    <li class="item four">解</li>
  </ul>

  <div class="popup-wrap J_popup">
    <div class="mask"></div>
    <div class="popup">
      <div class="close J_popup_close">关闭</div>
      <div class="popup-body">
        <h3 class="title">签到红包</h3>
        <p class="text">恭喜您抽中“<span class="reward">0</span> 元”</p>
      </div>
      <div class="popup-footer">
        <button class="btn btn-confirm J_popup_close" type="button">确定</button>
      </div>
      <div class="popup-boder-bottom"></div>
    </div>
  </div>

</body>
  <script src=" {$path_js}/js/activity/2017_FebOpenSpring/js/index.js"></script>
  <script src="{$path_js}/js/activity/2017_FebOpenSpring/js/signUp.js"></script>
</html>
