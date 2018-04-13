<!DOCTYPE html>
<html>

  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>吉利骰宝</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <!-- <link rel="shortcut icon" href="/favicon.ico"> -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="{$path_img}/images/activity/2016_NovRedBow/activity/index.css">
  </head>
  <body onload="querySignUp();">

  <div class="nav">
    <div class="content">

      <a href="/index" class="logo">宝开彩票logo</a>

      <ul class="menu clearfix J_nav">
        <li class="item current J_item"><a href="#">首页</a></li>
        <li class="item J_item"><a href="#">彩种概要</a></li>
        <li class="item J_item"><a href="#">我们的优势</a></li>
        <li class="item J_item"><a href="#">玩法介绍</a></li>
        <li class="item J_item"><a href="#">新手攻略</a></li>
        <li class="item J_item"><a href="#">立即体验</a></li>
      </ul>
    </div>
  </div>

  <div class="screen screen-1 J_screen">
    <div class="mask"></div>
    <div class="content J_content">
      <div class="btns">
        <a href="#" class="button hongbao J_hongbao">了解详情</a>
        <a href="/activity/redbowactivity" class="button jiangjing">了解详情</a>
        <a href="#" class="baoming" onclick="signUp();">立即报名</a>
		<a href="#" id="baomingAlready" class="baoming already" style="display:none;">已报名</a>
      </div>
      <i class="arrow J_next_screen">更多精采内容</i>

      <div class="dialog J_dialog_rules">
        <div class="dialog-mask"></div>
        <div class="dialog-body">
          <div class="btn-close J_close"></div>
          <h3 class="title">咯噔一下 <span>红包</span>拿下</h3>
          <p class="desc">活动规则：<br>2016年10月31日开始，只要登陆平台就有机会领取体验金，并且只要您在规定彩种投注已领取红包全额，在第二次登陆平台即可获得更高金额的神秘礼金！</p>
        </div>
      </div>
    </div>
  </div>

  <div class="screen screen-2 J_screen">
    <div class="content J_content">
      <ul class="list clearfix">
        <li class="item">最受欢迎的益智游戏</li>
        <li class="item">宝开品牌新自营彩种</li>
        <li class="item">玩转三颗骰子</li>
        <li class="item">即开型24小时不间断</li>
        <li class="item">216种机遇</li>
      </ul>
    </div>
  </div>

  <div class="screen screen-3 J_screen">
    <div class="content J_content">
      <ul class="list clearfix">
        <li class="item safe">
          <div class="thumb"></div>
          <p class="title">安全性</p>
          <p class="subtitle">最顶级的安全保障</p>
          <p class="desc">采用与国际标准同步的瑞士精密硬件设备开奖，通过量子级随机，完全无预知、不可猜测的理论和实验，确保公平公正、无坚不摧的程序安全防护。</p>
        </li>
        <li class="item shejiao">
          <div class="thumb"></div>
          <p class="title">社交元素</p>
          <p class="subtitle">首家拥有社交元素游戏</p>
          <p class="desc">高模拟现场赌桌投注场景，身临其境的赌场真体验，拥有个人专属精美头像和昵称。</p>
        </li>
        <li class="item bianjie">
          <div class="thumb"></div>
          <p class="title">投注便捷</p>
          <p class="subtitle">财富来得如此容易</p>
          <p class="desc">高手伴你同行，不仅能观看高手下注，还能与高手一起玩，增加赢率的一大契机。</p>
        </li>
      </ul>
      <i class="arrow J_next_screen"></i>
    </div>
  </div>


  <div class="screen screen-4 J_screen">
    <div class="content J_content">
      <div class="swiper-container">
        <div class="swiper-wrapper">
          <div class="swiper-slide">
            <div class="daxiao">
              <h1 class="title"><span>大小单双：</span>低风险，赢在起跑线上</h1>
              <ul class="list clearfix">
                <li class="item"><i>大</i>开奖号码三个号和值为11-17即中奖</li>
                <li class="item"><i>单</i>开奖号码三个号和值为奇数即中奖</li>
                <li class="item"><i>小</i>开奖号码三个号和值为4-10即中</li>
                <li class="item"><i>双</i>开奖号码三个号和值为偶数即中奖<br>（均不含豹子号）</li>
              </ul>
            </div>
          </div>
          <div class="swiper-slide">
            <div class="caihao">
              <h1 class="title"><span>猜一个号 ：</span>尝试进阶玩法，提高稳定收益</h1>
              <p class="desc">从1-6中至少选择1个号码，所选号码在开奖号码中出现1次，即中赔率为单骰的奖金；所选号码在开奖号码中出现2次，即中赔率为双骰的奖金；所选号码在开奖号码中出现3次，即中赔率为全骰的奖金</p>
            </div>
          </div>
          <div class="swiper-slide">
            <div class="hezhi">
              <h1 class="title"><span>和值 ：</span>若不突破自我，怎成就高回报？</h1>
              <p class="desc">从1-至少选择1个和值（3个号码之和）进行投注，所选和值与开奖的3个号码的和值相同即中奖</p>
            </div>
          </div>
        </div>
        <div class="swiper-pagination"></div>
      </div>
    </div>
  </div>

  <div class="screen screen-5 J_screen">
    <div class="content J_content">
      <div class="swiper-container">
        <div class="swiper-wrapper">
          <div class="swiper-slide">
            <ul class="list">
              <li class="item clearfix">
                <div class="thumb"></div>
                <div class="desc">
                  <h3 class="title">循序渐进</h3>
                  <p>低风险策略：大小单双玩法，低风险意味着低回报</p>
                  <p>中风险策略：和值可选择概率较高的9、10、11区域下注</p>
                  <p>高风险策略：采用组合押注，例如和值与对子，当骰子总和为8时，只会出现对1、对2或对3</p>
                </div>
              </li>
              <li class="item clearfix">
                <div class="thumb thumb-2"></div>
                <div class="desc">
                  <h3 class="title">翻倍原则</h3>
                  <p>每一局投注金额是上一局的2倍，采用翻倍投注的玩法，即使前面都不盈利，只要有一局中即可回本且盈利</p>
                </div>
              </li>
              <li class="item clearfix">
                <div class="thumb thumb-3"></div>
                <div class="desc">
                  <h3 class="title">资金管理</h3>
                  <p>设置止损线，运气好时赢的资金保存下来，扩大投注周期，陶冶情操，以备再战</p>
                </div>
              </li>
            </ul>
          </div>
          <div class="swiper-slide">
            <div class="tools">
              <a href="#" class="btn J_bianjie"></a>
              <a href="#" class="btn step J_step"></a>
            </div>
          </div>
          <div class="swiper-slide">
            <div class="qa">
              <ul>
                <li class="item">
                  <p class="question">Q:哪个玩法中奖几率最高？</p>
                  <p class="answer">A:大小单双玩法中奖几率约50%，低风险，稳定有保障。</p>
                </li>
                <li class="item">
                  <p class="question">Q:娱乐厅和至尊厅是否需要账间互转？</p>
                  <p class="answer">A:不需要，您充值以后即可立即进入游戏界面投注。</p>
                </li>
                <li class="item">
                  <p class="question">Q:娱乐厅和至尊厅有什么区别？</p>
                  <p class="answer">A:最低投注、赔付限额、销售时间、界面风格均不相同；【娱乐厅】最低投注只需1元，【至尊厅】单注最高可赚百万。</p>
                </li>
                <li class="item">
                  <p class="question">Q:其他玩家在赌桌上可以看到我吗？</p>
                  <p class="answer">A:如果您当局投注金额进入了榜单前7名，那么就很有可能会出现在其他所有用户的赌桌上。</p>
                </li>
                <li class="item">
                  <p class="question">Q:我怎么观察高手下注？</p>
                  <p class="answer">A:您进入游戏页面在销售开始之后，可以观看哪个玩法区上有气泡提示，例如玩法区“大”，气泡内容：投注+10000元，即为其他高手在下注哦。</p>
                </li>
                <li class="item">
                  <p class="question">Q:我怎么知道高手是否赢钱？</p>
                  <p class="answer">A:您可以查看高手头像上是否有中奖气泡提示，例如：赢得+100000元，参考高手下注，中奖不是难事！</p>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="swiper-pagination"></div>
      </div>

    </div>
  </div>

  <div class="screen screen-6 J_screen">
    <div class="content J_content">
      <a href="{$game_server}/gameBet/jldice1" class="btn funny">立即体验</a>
      <a href="{$game_server}/gameBet/jldice2" class="btn super">立即体验</a>
    </div>
  </div>
  <div class="footer">
    <p class="copyright">©2003-2016 宝开彩票 All Rights Reserved</p>
  </div>

  <ul class="sidebar">
    <li class="item"><a class="index J_toTop" href="#">彩种首页</a></li>
    <li class="item"><a class="try J_toBottom" href="#">立即体验</a></li>
    <!-- <li class="item"><a class="collect" href="#">一键收藏</a></li> -->
  </ul>

  <div class="dialog dialog-step J_dialog_step">
    <div class="dialog-mask"></div>
    <div class="dialog-body">
      <div class="btn-close J_close"></div>
    </div>
  </div>
  <div class="dialog dialog-tools J_dialog_tools">
    <div class="dialog-mask"></div>
    <div class="dialog-body">
      <div class="btn-close J_close"></div>
    </div>
  </div>

  <script src="{$path_js}/js/activity/2016_NovRedBow/index.js"></script>
  <script src="{$path_js}/js/activity/2016_NovRedBow/signUp.js"></script>
  
  </body>
  
  
</html>
