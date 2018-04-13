<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title><fmt:formatDate pattern="M月" value="${lastUpdateDate}"/>王者之争</title>
	<meta name="description" content="100万奖金邀您来战">
	<meta name="keywords" content="宝开 宝开娱乐 宝开彩票 奖金 排行">
	<link href="${staticFileContextPath}/static/images/activity/poolking/css/style.css" rel="stylesheet">
</head>
<body>
<div class="top">
	<div class="top_m">
		<a class="home" href="${frontContextPath}"></a>
	</div>
</div>
<div class="main">
	<div class="main_m">
		<div class="part">
			<h3 class="part_title">比赛赛程</h3>
			<ul class="info_ul">
				<li>活动分为三轮赛制，用户根据每一轮比赛投注总额的排名结果获取相应奖金。</li>
			</ul>
			<div class="rankboard" id="tab1">
				<ul class="tab_title">
					<c:forEach var="round" items="${rounds}" varStatus="index" >
						<li <c:if test="${round.startDate<=lastUpdateDate and round.endDate>lastUpdateDate}">class="current"</c:if>>
							<dl>
								<dt>第${index.count}轮争霸</dt>
								<dd><fmt:formatDate pattern="yyyy年MM月dd日" value="${round.startDate}"/> - <fmt:formatDate pattern="yyyy年MM月dd日" value="${round.endDate}"/></dd>
								<span class="arrow"></span>
							</dl>
						</li>
					</c:forEach>
				</ul>
				<div class="tab_content">
					<c:forEach var="round" items="${rounds}" varStatus="index" >
						<div class="content" <c:if test="${round.startDate<=lastUpdateDate and round.endDate>lastUpdateDate}">style="display: block"</c:if>>
							<table class="rank_table">
								<tr>
									<th width="20%">奖项设置</th>
									<th width="35%">累计最低投注总额</th>
									<th width="20%">获奖人数</th>
									<th width="25%">奖金</th>
								</tr>
								<c:forEach var="item" items="${round.roundItems}">
									<tr>
										<td>${item.name}</td>
										<td>≥${item.spendMoney}万</td>
										<td>${item.count} 人</td>
										<td>${item.bouns} 元</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</c:forEach>
				</div>

			</div>
		</div>

		<div class="part">
			<h3 class="part_title">规则说明</h3>
			<ul class="info_ul">
				<li>第一轮争霸：以玩家3日内高频彩累积投注额排名</li>
				<li>第二轮争霸：以玩家第一轮投注总额+第二轮投注总额排名</li>
				<li>第三轮争霸：以三轮累积总投注额排名</li>
				<li>每个名次，设有相对应的最低投注金额，奖金会根据用户排位及最低投注总额共同决定，缺一不可。根据排位情况,如未达到最低投注总额,名次将顺延至下一位,直到达到投注要求来计算名次。落空了的名次对应奖金将会累计到下一轮比赛。</li>
			</ul>
			<p class="info_2">例：用户第一轮比赛中，3日累计投注总额为280w，排位第一，由于未达到第一名最低投注总额300w的要求，名次将会顺延至第二位，第一名奖金落空，奖金会累计到下一轮比赛。</p>
			<p class="info_2">在第二轮比赛中，第一名用户销量960w，将会获得第一轮比赛一等奖奖金和第二轮比赛一等奖奖金，共计5w+7w=12w。</p>
		</div>
		<div class="part">
			<h3 class="part_title">122个获奖机会等着您哟！</h3>
			<p class="info_3">每两小时更新一次 最近更新:<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${lastUpdateDate}"/></p>
		
			<a class="history" href="history" target="_blank">查看历史排名</a>
			<div class="rankboard" id="tab2">
				<ul class="tab_title tab_title2">
					<li class="current">
						<dl>
							<dt>1 - 25 名</dt>
							<span class="arrow"></span>
						</dl>
					</li>
					<li>
						<dl>
							<dt>26 - 50 名</dt>
							<span class="arrow"></span>
						</dl>
					</li>
					<li>
						<dl>
							<dt>51 - 75 名</dt>
							<span class="arrow"></span>
						</dl>
					</li>
					<li>
						<dl>
							<dt>76 - 100 名</dt>
							<span class="arrow"></span>
						</dl>
					</li>
				</ul>
				<div class="tab_content">
					<c:forEach var="i" begin="0" end="3">
						<div class="content" <c:if test="${i eq 0}">style="display: block"</c:if>>
							<table class="rank_table">
								<tr>
									<th width="30%">当前排名</th>
									<th width="30%">用户名</th>
									<th width="40%">累计销量</th>
								</tr>
								<c:if test="${not empty userScore}">
									<tr class="current">
										<td>${userScore.rowNo>100?'请继续加油哦！':userScore.rowNo}</td>
										<td>${userScore.userName}</td>
										<td><fmt:formatNumber type="number" value="${(userScore.totalAmount-(userScore.totalAmount%100))/10000}" maxFractionDigits="2"/>元</td>
									</tr>
								</c:if>
								<c:if test="${empty scores}">
									<tr>
										<td colspan="3" rowspan="10">暂未统计</td>
									</tr>
								</c:if>
								<c:forEach var="score" items="${scores}" begin="${i*25}" end="${(i+1)*25-1}" varStatus="index">
									<tr>
										<td>${index.count + i*25}</td>
										<td>${score.userName}</td>
										<td><fmt:formatNumber type="number" value="${(score.totalAmount-(score.totalAmount%100))/10000}" maxFractionDigits="2"/>元</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</c:forEach>
				</div>
				<div class="changepage">
					<a href="javascript:;" class="left">前25名</a>
					<a href="javascript:;" class="right">后25名</a>
				</div>
			</div>

		</div>
		<div class="part">
			<h3 class="part_title">活动说明</h3>
			<ul class="info_ul">
				<li>活动返奖，参与彩种只限于宝开娱乐旗舰版高频彩种。投注必须为已开奖注单，视为有效投注。</li>
				<li>骰宝大小单双销量不计算在活动销量内。</li>
				<li>奖励计算依据用户在活动期间内的有效投注量审核计算。</li>
				<li>每一轮比赛奖励将于该轮比赛结束后次工作日发放至用户平台帐户中。</li>
				<li>活动期间禁止任何刷量作弊行为，一经发现宝开娱乐平台将取消参与活动资格，严重者将被冻结账号处理。</li>
				<li>宝开娱乐平台保留活动最终解释权。</li>
			</ul>
		</div>
		<div class="footer">
			<p>©2003-2014 宝开娱乐 All Rights Reserved </p>
		</div>
	</div>

</div>

<div class="fixed">
	<a class="backtop" href="javascript:;"></a>
	<a class="gotorank" href="javascript:;"></a>
</div>
</body>
<script src="${staticFileContextPath}/static/js/activity/poolking/jquery-1.9.1.min.js"></script>
<script src="${staticFileContextPath}/static/js/activity/poolking/tab.js"></script>
<script type="text/javascript">
	$('#tab1').tab();
	$('#tab2').tab({'changepage':'.changepage'});

	$(".backtop").click(function(){
		$('body,html').animate({scrollTop:0},500);
		return false;
	});
	$(".gotorank").click(function(){
		$('body,html').animate({scrollTop: 1500},500);
		return false;
	});
	$('.rank_table tr:even').addClass('tr_even');
</script>
</html>