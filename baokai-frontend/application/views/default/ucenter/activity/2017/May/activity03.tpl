<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>宝开送奖</title>
<meta name="description" content="宝开彩票-宝开送奖">
<meta name="keywords" content="宝开彩票-宝开送奖">
<link rel="stylesheet" href="{$path_img}/images/activity/2017_May/activity03/pc/images/style.css">
<script src="{$path_js}/js/jquery-1.9.1.min.js"></script>
</head>
<body>
<div class="allbg">
	<div class="middle">
		<div class="banner">
			<a class="logo" href="/index">宝开彩票</a>
			<div class="btn">
				<a id="before" class="btn-before" href="#"></a>
                <a id="joined" class="btn-joined" href="#"></a>
				<a id="done" class="btn-done" href="{$game_server}/gameBet/cqssc/"></a>
				<a id="fin" class="btn-fin" href="#"></a>
				<a id="reg" class="btn-reg" href="#" onclick="signUp();"></a>
			</div>

		</div>
		<div class="rule">
			<div class="part1">
				<p>活动期间（共六天）充值累计达到以下要求，<br>并在活动时间内有效投注大于等于累计充值金额的6倍即可领取相对应比例的奖金</p>
				<h3>普通玩家奖励</h3>
				<table>
					<tr>
						<th>活动期间累计充值金额</th>
						<th>≥500</th>
						<th>≥5,000</th>
						<th>≥50,000</th>
						<th>≥150,000</th>
					</tr>
					<tr>
						<td>充值返利比例</td>
						<td>2%</td>
						<td>3%</td>
						<td>4%</td>
						<td>5%</td>
					</tr>
					<tr class="strong">
						<td colspan="2">活动期间累计销量大于等于充值金额 6 倍</td>
						<td colspan="3">奖金上限 12888</td>
					</tr>
				</table>
				<h3>VIP玩家奖励</h3>
				<table>
					<tr>
						<th>活动期间累计充值金额</th>
						<th>≥50,000</th>
						<th>≥150,000</th>
						<th>≥300,000</th>
					</tr>
					<tr>
						<td>充值返利比例</td>
						<td>5%</td>
						<td>6%</td>
						<td>7%</td>
					</tr>
					<tr class="strong">
						<td colspan="2">活动期间累计销量大于等于充值金额 6 倍</td>
						<td colspan="2">奖金上限 25888</td>
					</tr>
				</table>
				<p>
					活动资格：活动期间累计销量/活动期间累计充值金额≥6<br>
					活动奖金=活动期间累计充值金额*返利比例
				</p>
				<p class="small">
					例：用户A活动期间累计充值6,000元，活动期间累计销量为36,000，则用户A的奖金为180元。<br>
					　　VIP用户B活动期间累计充值100,000元，活动期间累计销量为600,000，则VIP用户B的奖金为5000元
				</p>
			</div>
			<div class="part2">
				<ul>
					<li>活动返奖，参与游戏只限于宝开彩票旗舰版彩票类游戏。</li>
					<li>投注必须为已开奖注单，视为有效投注，奖励计算依据用户在活动期间内的有效投注量审核计算， PT、宝开游艺FHX、六合彩、骰宝大小单双销量不计算在活动销量内。</li>
					<li>活动统计用户充值时间为2017.5.15 00:00:00 -2017.5.20 23:59:59，销量统计时间为2017.5.15 00:00:00 -2017.5.20 23:59:59。</li>
					<li>本次活动采取报名制，用户需要点击“立即报名”后才能获取活动奖金。注：VIP3（包含VIP3）以上用户无需报名即可参加。</li>
					<li>平台新注册用户，首存优惠不得与本次活动同时享受。当首存优惠领取后，方可参与本次活动。</li>
					<li>本次活动包含上下级充值。</li>
					<li>超级2000玩法按实际投注额的80%进行结算。如：用户超级2000玩法中投注额为1,000，则结算销量为1,000*80%=800。</li>
					<li>活动奖金于活动结束后次日（2017年5月21日）18:00点前派发。</li>
				</ul>
			</div>
			<div class="ball"></div>
		</div>
		<div class="footer">
			<div class="footer-m">©2003-2017 宝开彩票 All Rights Reserved</div>
		</div>
	</div>
</div>

<input id="signStatus" type="hidden" value="{$signStatus}"/>
<input id="status" type="hidden" value="{$status}"/>
<input id="awardStatus" type="hidden" value="{$awardStatus}"/>

<script src="{$path_js}/js/activity/2017_May/activity03/index.js"></script>    
    
</body>

</html>