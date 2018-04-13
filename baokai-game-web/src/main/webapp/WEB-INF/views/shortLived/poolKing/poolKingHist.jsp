<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>9月王者之争</title>
	<meta name="description" content="100万奖金邀您来战">
	<meta name="keywords" content="宝开 宝开娱乐 宝开彩票 奖金 排行">
	<link href="${staticFileContextPath}/static/images/activity/poolking/css/style.css" rel="stylesheet">
<style>
	#tab {
		min-height: 700px;
		height: auto !important;
		*height: 700px;
		visibility: visible;
	}
</style>
</head>
<body>
<div class="sublogo">
	<div class="sub">
		<a href="${frontContextPath}"></a>
	</div>
</div>
<div class="main">
	<div class="main_m">

		<div class="part">
			<h3 class="part_title">历史排名</h3>
			<p class="info">详细中奖名单，请参阅平台公告。</p>

			<div class="rankboard" id="tab">
				<ul class="tab_title">
					<c:forEach var="round" items="${rounds}" varStatus="index" >
						<li <c:if test="${index.count eq 1}">class="current"</c:if>>
							<dl>
								<dt>第${index.count}轮排名</dt>
								<dd><fmt:formatDate pattern="yyyy年MM月dd日" value="${round.startDate}"/> - <fmt:formatDate pattern="yyyy年MM月dd日" value="${round.endDate}"/></dd>
								<span class="arrow"></span>
							</dl>
						</li>
					</c:forEach>
				</ul>
				<div class="tab_content">
					<c:forEach var="round" items="${rounds}" varStatus="index" >
						<div class="content" <c:if test="${index.count eq 1}">style="display: block"</c:if>>
							<table class="rank_table">
								<tr>
									<th width="30%">当前排名</th>
									<th width="30%">用户名</th>
									<th width="40%">累计销量</th>
								</tr>
								<c:if test="${empty round.scores}">
									<tr>
										<td colspan="3" rowspan="10">暂未统计</td>
									</tr>
								</c:if>
								<c:forEach var="score" items="${round.scores}" varStatus="scoreIndex" >
									<tr>
										<td>${scoreIndex.count}</td>
										<td>${score.userName}</td>
										<td><fmt:formatNumber type="number" value="${(score.totalAmount-(score.totalAmount%100))/10000}" maxFractionDigits="2"/>元</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</c:forEach>
				</div>

			</div>

		</div>

		<div class="footer">
			<p>©2003-2014 宝开娱乐 All Rights Reserved </p>
		</div>
	</div>

</div>
</body>
<script src="${staticFileContextPath}/static/js/activity/poolking/jquery-1.9.1.min.js"></script>
<script src="${staticFileContextPath}/static/js/activity/poolking/tab.js"></script>
<script type="text/javascript">
	$('#tab').tab();
	$('.rank_table tr:even').addClass('tr_even');
</script>
</html>