<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>发财元宝争夺赛</title>
	<meta name="description" content="100万奖金邀您来战">
	<meta name="keywords" content="宝开 宝开娱乐 宝开彩票 奖金 排行">
	<link href="${staticFileContextPath}/static/images/activity/monkeyActivity/css/style.css" rel="stylesheet">
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
		<a href="http://www.fh885.com"></a>
	</div>
</div>
<div class="main_sub">
	<div class="main_sub_m">
		<div class="part">
			<h3 class="part_title ptitle6">我的奖金</h3>
			<table class="rank_table">
				<tr>
					<th width="15%">获奖时间</th>
					<th width="10%">当日排名</th>
					<th width="15%">用户名</th>
					<th width="15%">累计销量</th>
					<th width="15%">元宝级别</th>
					<th width="15%">幸运发财金</th>
					<th width="15%">开运利是</th>
				</tr>
				<c:forEach var="score" items="${scores}">
				<tr>
					<td><fmt:formatDate pattern="yyyy/MM/dd" value="${score.createTime}"/></td>
					<td>${score.rowNo}</td>
					<td>${score.userAccount}</td>
					<td><fmt:formatNumber type="number" value="${(score.totAmount-(score.totAmount%100))/10000}" maxFractionDigits="2"/></td>
					<td><c:if test="${score.lv == 1}">财神元宝</c:if>
						<c:if test="${score.lv == 2}">发财元宝</c:if>
						<c:if test="${score.lv == 3}">招福元宝</c:if>
						<c:if test="${score.lv == 4}">招财元宝</c:if>
						<c:if test="${score.lv == 5}">幸运元宝</c:if>
						<c:if test="${score.lv == 6}">钻石元宝</c:if>
						<c:if test="${score.lv == 7}">白金元宝</c:if>
						<c:if test="${score.lv == 8}">黄金元宝</c:if>
						<c:if test="${score.lv == 9}">白银元宝</c:if>
						<c:if test="${score.lv == 10}">青铜元宝</c:if>
						<c:if test="${score.lv == 11}"></c:if></dd></td>
					<td><fmt:formatNumber type="number" value="${score.luckyMoney}" maxFractionDigits="2"/></td>
					<td><fmt:formatNumber type="number" value="${score.kaiyun}" maxFractionDigits="2"/></td>
				</tr>
				</c:forEach>
			</table>
			<table class="rank_table">
				<tr>
					<th class="bg_none" width="15%"></th>
					<th class="bg_none" width="10%"></th>
					<th class="bg_none" width="15%"></th>
					<th class="bg_none" width="15%"></th>
					<th width="15%">累计奖金</th>
					<th width="15%"><fmt:formatNumber type="number" value="${totLuckyMoney}" maxFractionDigits="2"/></th>
					<th width="15%"><fmt:formatNumber type="number" value="${totKaiyun}" maxFractionDigits="2"/></th>
				</tr>
			</table>
		</div>
		<div class="footer">
			<p>©2003-2016 宝开娱乐 All Rights Reserved </p>
		</div>
	</div>
</div>
</body>
<script src="${staticFileContextPath}/static/js/activity/poolking/jquery-1.9.1.min.js"></script>

</html>