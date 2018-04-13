<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>宝开献礼</title>
<meta name="description" content="宝开">
<meta name="keywords" content="宝开">
<link rel="stylesheet" href="{$path_img}/images/activity/2017_March/activity04/images/style.css">


</head>
<body>
<div class="allbg">
	<div class="middle">
		<div class="banner">
			<div class="gold"></div>
			<a class="logo" href="/index">宝开彩票</a>
			<div class="btn_box" id="signup_btn">
                                                          <!-- {if $signStatus == -22} -->
                                                            <a id="join" class="btn btn_join" href="#" onclick="signUp();">立即报名</a>
                                                          <!--{else if $signStatus == -23 || $signStatus == -20} -->
                                                            <a class="btn btn_finish"  href="{$game_server}/gameBet/cqssc/">已报名</a>
                                                          <!--{else} -->
                                                            <a class="btn btn_end"  href="#">结束</a>
                                                          <!--{/if} -->
                                                          </div>
		</div>
		<div class="rule">
			<h3>礼金详表</h3>
			<table>
				<tr>
					<th rowspan="2">日均投注额(单位元)</th>
					<th colspan="4">最大连续投注天数/返奖比例(单位元)</th>
					<th>奖金最高上限</th>
				</tr>
				<tr>
					<td>6天</td>
					<td>5天</td>
					<td>4天</td>
					<td>3天</td>
					<td rowspan="4">38,888</td>
				</tr>
				<tr>
					<td width="22%" class="strong">1,000≤投注额<50,000</td>
					<td>6‰</td>
					<td>3‰</td>
					<td>1.5‰</td>
					<td>0.8‰</td>
				</tr>
				<tr>
					<td width="22%" class="strong">50,000≤投注额<300,000</td>
					<td>12‰</td>
					<td>6‰</td>
					<td>3‰</td>
					<td>1.5‰</td>
				</tr>
				<tr>
					<td width="22%" class="strong">投注额≥300,000</td>
					<td>18‰</td>
					<td>9‰</td>
					<td>4.5‰</td>
					<td>2.3‰</td>
				</tr>
			</table>
			<p>活动期间连续投注3天以上，日均投注有效销量≥1000即可参与活动，投注天数以活动期间用户投注的最高连续天数来计算。<br>
				(特别提醒：活动期间只计一次最长连续投注赠送礼金哦)
			</p>
			<p class="border-dot">
				日均销量=活动期间彩票类有效销量/6日<br>
				活动奖金=总销量*最大连续投注天数对应比例
			</p>

			<h3>派奖范例</h3>
			<table>
				<tr>
					<th>日期/投注额</th>
					<th>3月27日</th>
					<th>3月28日</th>
					<th>3月29日</th>
					<th>3月30日</th>
					<th>3月31日</th>
					<th>4月1日</th>
				</tr>
				<tr>
					<td class="strong">用户A</td>
					<td>1,300</td>
					<td>2,720</td>
					<td>4,000</td>
					<td>1,048</td>
					<td>624</td>
					<td>3,702</td>
				</tr>
				<tr>
					<td class="strong">用户B</td>
					<td>1,044</td>
					<td>72,520</td>
					<td>67</td>
					<td>0</td>
					<td>15,278</td>
					<td>334,675</td>
				</tr>
			</table>
			<p>用户A日均投注金额为2,232元，最大连续为六天，所以该用户可获得六日内所有销量13,394*6‰=80.36元。<br>
				用户B日均投注金额为70,597元，连续3天，所以该用户可获得六日内所有销量423,584*1.5‰=635.38元。
			</p>
			<h3>活动规则</h3>
			<ul>
				<li>活动返奖，参与游戏只限于宝开彩票旗舰版。</li>
				<li>投注必须为已开奖注单，视为有效投注，奖励计算依据用户在活动期间内的有效投注量审核计算， PT、FHX宝开游艺、六合彩、骰宝大小单
					双销量不计算在活动销量内。</li>
				<li>本次活动采取报名制，用户需要点击“立即报名”后才能获取活动奖金。注：VIP3（包含VIP3）以上用户无需报名即可参加。</li>
				<li>超级2000玩法按实际投注额的80%进行结算。如：用户超级2000玩法中投注额为1,000，则结算销量为1,000*80%=800。</li>
				<li>奖金于活动结束后次日派发。</li>
				<li>平台新注册用户，首存优惠不得与本次活动同时享受。当首存优惠领取后，方可参与本次活动。</li>
			</ul>
		</div>
		<div class="footer">
			活动期间禁止任何刷量作弊行为，一经发现宝开彩票平台将取消参与活动资格，严重者将被冻结账号处理。宝开平台保留活动最终解释权。<br>
			©2003-2017 宝开彩票 All Rights Reserved
		</div>
	</div>
</div>
</body>
<script src="{$path_js}/js/jquery-1.9.1.min.js"></script>
<script src="{$path_js}/js/activity/2017_March/activity04/index.js"></script>
</html>