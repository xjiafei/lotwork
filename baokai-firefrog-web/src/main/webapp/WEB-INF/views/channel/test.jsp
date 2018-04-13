<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="http://static.joy188.com:888/static/images/common/base.min.css" rel="stylesheet">
	<link href="http://static.joy188.com:888/static/images/index/index.min.css" rel="stylesheet">

<title>Insert title here</title>
</head>
<body>
<div class="monopoly-pop" id="monopolyPop" style="display:inline;">
		<a href="javascript:void(0);" class="close" id="monopolyPopClose"></a>
		<div class="lottery-notice">
			<h3>中奖公告：</h3>
			<ul>
				<li>恭喜<span>XX</span>中的神秘大奖</li>
				<li>恭喜<span>XX1</span>中的神秘大奖</li>
				<li>恭喜<span>XX2</span>中的神秘大奖</li>
			</ul>
		</div>
		<div class="monopoly-star" id="monopolyStar"></div>
		<div class="monopoly-rules" id="monopolyRules"></div>
		<div class="dice-wrap" id="dice">
			<span class="dice dice_1" id="dice1"></span>
			<span class="dice dice_2" id="dice2"></span>
			<span class="dice dice_3" id="dice3"></span>
		</div>
		<div class="monopoly-step">
			<div class="human">
				<div class="dice-point" id="dicePoint"></div>
			</div>
			<ul id="prize" class="monopoly-step-list">
				<li id="d_1">
					<div class="monopoly-step-hover monopoly-step1-hover"></div>
					<div class="monopoly-light"></div>
				</li>
				<li id="d_2">
					<div class="monopoly-step-hover monopoly-step2-hover"></div>
					<div class="monopoly-light"></div>
				</li>
				<li id="d_3">
					<div class="monopoly-step-hover monopoly-step3-hover"></div>
					<div class="monopoly-light"></div>
				</li>
				<li id="d_4">
					<div class="monopoly-step-hover monopoly-step4-hover"></div>
					<div class="monopoly-light"></div>
				</li>
				<li id="d_5">
					<div class="monopoly-step-hover monopoly-step5-hover"></div>
					<div class="monopoly-light"></div>
				</li>
				<li id="d_6">
					<div class="monopoly-step-hover monopoly-step6-hover"></div>
					<div class="monopoly-light"></div>
				</li>
				<li id="d_7">
					<div class="monopoly-step-hover monopoly-step7-hover"></div>
					<div class="monopoly-light"></div>
				</li>
				<li id="d_8">
					<div class="monopoly-step-hover monopoly-step8-hover"></div>
					<div class="monopoly-light"></div>
				</li>
				<li id="d_9">
					<div class="monopoly-step-hover monopoly-step9-hover"></div>
					<div class="monopoly-light"></div>
				</li>
			</ul>
		</div>
		<div class="lottery-pop" id="lotteryPop">
			<a href="javascript:void(0);" class="close" id="lotteryPopClose"></a>
			<h3 id="title"></h3>
			<div class="info">恭喜您获得<strong id="msg"></strong>元</div>
			<div class="text" id="text"><span id="redBall"><span class="redBall"><em>5</em><em>7</em><em>8</em><em>11</em><em>17</em><em>24</em></span></span><span id="blueBall"><span class="blueBall"><em>7</em></span></span></div>
			<a href="javascript:void(0);" class="btn" target="_blank" id="lotteryPopBtn">立即去投注</a>
		</div>
		<div class="rules-pop" id="rulesPop">
			<a href="javascript:void(0);" class="close" id="rulesPopClose"></a>
			<h3>游戏规则</h3>
			<ul>
				<li>每天登录有<strong>一次</strong>掷骰子的机会。</li>
				<li>用户点击“丢骰子”后，<strong>三个骰子点数相加总额</strong>为棋子所移动格数，所停留格子上的奖励为当前中奖内容。</li>
				<li>格子共9格，当所掷点数<strong>超过9时</strong>，棋子将<strong>往回走</strong>。</li>
				<li>掷骰子一次机会用完，按钮将无法点击，于每天<strong>00:00</strong>重置。</li>
			</ul>
			<a href="javascript:void(0);" class="btn" id="rulesPopBtn">确定</a>
		</div>
	<div id="diceMask"></div></div>
</body>
</html>