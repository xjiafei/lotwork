<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>2000info</title>
<link rel="stylesheet" href="${staticFileContextPath}/static/images/activity/2000info/css/style.css" />

</head>
<body>
<div class="loading">
	<img src="${staticFileContextPath}/static/images/activity/2000info/images/oval.svg">
</div>
<div class="space">
	<div class="space_body"></div>
	<div class="logo"></div>
	<div class="bonus1950"></div>
	<div class="bonus1960"></div>
	<div class="bonus1980"></div>
	<div class="track"></div>
	<div class="planetA"></div>
	<div class="planetB"></div>
	<div class="circle"></div>
	<div class="slogan">
		<div class="poster"></div>
	</div>
	<div class="mouse">
		<div class="mouse-btn"></div>
	</div>
	<div class="light"></div>
	<div class="title"></div>
</div>
<div class="container">
	<div class="depart_rule">
		<div class="rule_m">
			<div class="ctitle title1">游戏规则</div>
			<p class="p_center">超级2000目前仅支持“<span>后三</span>”、“<span>后二</span>”及“<span>一星</span>”内的所有玩法<br>
				基本的中奖规则与之前的玩法一致，仅在中奖后做二次判断</p>
			<div class="rule_2000"></div>
			<div class="rule_double"></div>
		</div>
	</div>
	<div class="depart_award">
		<div class="award_m">
			<div class="ctitle title2">单注奖金详情列表</div>
			<table class="award_table">
				<tr>
					<td width="70" class="noborder_td"></td>
					<td width="270">玩法</td>
					<td width="280" class="strong_td">2000模式</td>
					<td width="270">对子模式</td>
				</tr>
				<tr>
					<td class="type_td">一星</td>
					<td>定位胆</td>
					<td class="strong_td">20</td>
					<td>10</td>
				</tr>
				<tr>
					<td class="type_td">二星</td>
					<td>直选</td>
					<td class="strong_td">200</td>
					<td>100</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>直选和值</td>
					<td class="strong_td">200</td>
					<td>100</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>跨度</td>
					<td class="strong_td">200</td>
					<td>100</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>组选复式</td>
					<td class="strong_td">100</td>
					<td>50</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>组选和值</td>
					<td class="strong_td">100</td>
					<td>50</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>组选包胆</td>
					<td class="strong_td">100</td>
					<td>50</td>
				</tr>
				<tr>
					<td class="type_td">三星</td>
					<td>直选</td>
					<td class="strong_td">2000</td>
					<td>1000</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>直选和值</td>
					<td class="strong_td">2000</td>
					<td>1000</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>跨度</td>
					<td class="strong_td">2000</td>
					<td>1000</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>组选和值</td>
					<td class="strong_td">666</td>
					<td>333</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>组三</td>
					<td class="strong_td">666</td>
					<td>333</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>组六</td>
					<td class="strong_td">333</td>
					<td>166</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>组选包胆一等</td>
					<td class="strong_td">666</td>
					<td>333</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>组选包胆二等</td>
					<td class="strong_td">333</td>
					<td>166</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>一码不定位</td>
					<td class="strong_td">7.3</td>
					<td>3.6</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>二码不定位</td>
					<td class="strong_td">36</td>
					<td>18</td>
				</tr>
				<tr>
					<td class="noborder_td"></td>
					<td>&nbsp;</td>
					<td class="strong_td"></td>
					<td></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="depart_game">
		<div class="game_m">
			<div class="ctitle title3"></div>
			<ul class="clearfix">
				<li><a target="_blank" href="/gameBet/cqssc?select2000=1" target="_blank"></a></li>
				<li><a target="_blank" href="/gameBet/hljssc?select2000=1" target="_blank"></a></li>
				<li><a target="_blank" href="/gameBet/xjssc?select2000=1" target="_blank"></a></li>
				<li><a target="_blank" href="/gameBet/tjssc?select2000=1" target="_blank"></a></li>
			</ul>
		</div>
	</div>
	<div class="depart_qa">
		<div class="qa_m">
			<div class="ctitle title4"></div>
			<ul class="qa-list qa-list-current">
				<li>
					<div class="q"><i class="ico-q">Q</i>超级2000和传统玩法有什么不同？</div>
					<div class="a"><i class="ico-a">A</i>超级2000为宝开独创、业界唯一最高奖金玩法,投注方式与传统玩法完全相同,但在大部分情形下所派发的奖金将高于传统模式玩法。</div>
				</li>
				<li>
					<div class="q"><i class="ico-q">Q</i>为什么有些玩法不支持超级2000？我想在前三玩超级2000可以吗？</div>
					<div class="a"><i class="ico-a">A</i>因超级2000模式将万位与千位视为判定奖金模式的位置,因此目前有些玩法不支持超级2000。</div>
				</li>
				<li>
					<div class="q"><i class="ico-q">Q</i>我如果在传统模式下和超级2000都投注可以吗？</div>
					<div class="a"><i class="ico-a">A</i>是可以的,您的投注金额与奖金的派发规则都是分开计算。</div>
				</li>
			</ul>
		</div>
		<div class="footer">©2003-2016 宝开娱乐 All Rights Reserved </div>
	</div>

</div>
</body>

<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery-1.9.1.min.js" ></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.mousewheel.js" ></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/activity/2000info/index.js" ></script>

</html>