<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>彩票订单详情打印</title>
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css">
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/print.css">
</head>
<body>
	<div class="ptint-body">
		<div class="ptint-btn">
			<a href="javascript:void(0);" class="btn" onclick="window.print();">打印</a>
			<p>暂时仅支持80mm宽度的打印机，详询客服，谢谢</p>
		</div>
		<table class="table table-info table-border-none">
			<tr>
				<th colspan="2">${struc.lotteryName}</th>
			</tr>
			<tr>
				<td>编号：${struc.orderCode}</td>
				<td>期号：${struc.webIssueCode}</td>
			</tr>
			<tr>
				<td>时间：${struc.formatSaleTime}</td>
				<td>金额：<span class="price"><dfn>&yen;</dfn><span><fmt:formatNumber value="${struc.totamount/10000}" pattern="#,###.##"  minFractionDigits="2"/></span></span></td>
			</tr>
		</table>
		<c:forEach items="${struc.betList }" var="betDownload" >
		<table class="table">
			<tr>
				<th colspan="3">${betDownload.gameType }</th>
			</tr>
			<tr>
				<td style="width:200px;">投注内容</td>
				<c:choose>
				<c:when test="${struc.lotteryId==99701}">
				<td>赔率</td>
				</c:when>
				<c:otherwise>
				<td>倍数</td>
				</c:otherwise>
				</c:choose>
				<td>金额</td>
			</tr>
			<c:forEach items="${betDownload.betContent}" var="betContent">
			<tr>
				<td class="lottery-number">${betContent.number }</td>
				<c:choose>
					<c:when test="${struc.lotteryId==99701}">
						<td>${betContent.singleWin }</td>
					</c:when>
					<c:otherwise>
						<td>${betContent.times }</td>
					</c:otherwise>
				</c:choose>
				<td><span class="price"><dfn>&yen;</dfn><span><fmt:formatNumber value="${betContent.subamout/10000 }" pattern="#,###.##"  minFractionDigits="2"/></span></span></td>
			</tr>
			</c:forEach>
		</table>	
		</c:forEach>
		<a href="" title="宝开彩票" class="ptint-logo">
			<img src="${staticFileContextPath}/static/images/common/logo-print.png" alt="宝开彩票">
		</a>
	</div>
</body>
</html>