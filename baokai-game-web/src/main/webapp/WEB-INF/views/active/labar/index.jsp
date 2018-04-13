<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>宝开娱乐-宝开老虎机开户礼</title>
<meta name="description" content="宝开娱乐-宝开老虎机开户礼">
<meta name="keywords" content="宝开 宝开娱乐 宝开彩票 老虎机">
<link href="${staticFileContextPath}/static/css/activity/labar/style.css" rel="stylesheet">
</head>
<body>
<div class="window">
	<div class="main">
		<div class="header">
			<a class="logo" href="" title="宝开娱乐">宝开娱乐</a>
			<h3>宝开老虎机开户礼</h3>
		</div>
		<div class="game" id="game">
			<div class="prize">
				<div class="prize_0"></div>
				<div class="prize_1"></div>
				<div class="prize_2"></div>
				<div class="prize_3"></div>
				<div class="prize_4"></div>
				<div class="prize_5"></div>
				<div class="prize_6"></div>
				<div class="prize_7"></div>
				<div class="prize_8"></div>
				<div class="prize_9"></div>
			</div>
			<div class="gamebox">
				<div class="screen">
					<div class="light"></div>
					<div class="fruit">
						<!--<div class="fruit_1"></div>-->
						<!--<div class="fruit_2"></div>-->
						<!--<div class="fruit_3"></div>-->
					</div>
					<div class="screen_cover"></div>
				</div>
				<div class="oprate">
					<div class="stop">
						<div class="stop_1"></div>
						<div class="stop_2"></div>
						<div class="stop_3"></div>
					</div>
					<!--<div class="result">-->
						<!--<span></span><span></span><span></span><span></span>-->
					<!--</div>-->
					<!--<div class="tryagain">-->
						<!--<div class="award">立即领奖</div>-->
						<!--<div class="again">再来一次</div>-->
					<!--</div>-->
				</div>
				<div class="start">
				</div>
				<div class="start started">
				</div>
				<div class="guide"></div>
			</div>
			<div class="tips">
				<h3>玩法说明</h3>
				<ul>
					<li>活动限量500个用户参与。</li>
					<li>拉动拉杆，点击“STOP”即可得到赠送礼金礼包，最高1000元宝开老虎机奖金。</li>
					<li>用户不限拉杆次数，得到满意金额点击“领取”按钮，即可获得领取资格，每位用户仅可领取一次。</li>
					<li>请正确填写注册用户名，填写错误将无法兑现礼金。</li>
					<li>活动只限宝开平台旗舰版新用户参与，老用户伪冒新用户获得活动礼金将会被扣除。</li>
					<li>用户完成注册即可登录平台至活动专题页领取开户礼金。</li>
					<li>此次活动中宝开平台将全程监控各种恶意刷量等违规行为，一经查实宝开平台将冻结账号进行警告处理。</li>
					<li>与宝开平台其他拉新活动不重叠，奖金不可重复领取。平台保留活动最终解释权。</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="footer">
	<div class="footer_in">
		<p>© 2001-2015 宝开娱乐 All Rights Reserved</p>
	</div>
</div>

<div class="mask"></div>
<div class="pop">
	<div class="pop_close"></div>
	<div class="pop_prize p1"></div>
	<div class="line1"></div>

	<div class="line2">
		请输入手机号
		<input type="text" class="input" id="telephone" maxlength="11"/>
	</div>

	<div id="errorMsg" class="showcode">您可以多次尝试拉霸游戏以获得满意奖金。<br>
		但您只有<span>一次领奖机会</span>！
	</div>
	<div id="register1Dev" class="line3">
		<a href="javascript:;" class="register">注册领奖</a>
		<a href="javascript:;" class="playagain">再玩一次</a>
	</div>
	 
	<div id="register2Dev" style = "display:none" class="line3">
		<a href="javascript:;" target="_blank" class="register2">确定</a>
	</div>
</div>

<form id="myRedirectForm" name="myRedirectForm" action="${userContextPath}/register/?" method="post">
 <input id="cellphone" name="cellphone" type="hidden"/>
 <input id="activityType" name="activityType" type="hidden" value="${activityType}"/>
</form>

<input id="exchangeNumber" name="exchangeNumber" type="hidden"/>
<input id="exchangePrize" name="exchangePrize" type="hidden"/>
<input id="imageServer" type="hidden" value="${staticFileContextPath}"/>

</body>
<script src="${staticFileContextPath}/static/js/activity/labar/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${staticFileContextPath}/static/js/activity/labar/game.js" type="text/javascript"></script>
<script type="text/javascript">
$('#game').game();


</script>
</html>