<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/bet/showBlockadeInfo.js"></script>
<title>封锁<c:if test="${empty noBJ}">变价</c:if></title>
<style>
* {
	padding: 0;
	margin: 0;
}

.main-cont {
	width: 700px;
	margin: 0 auto;
	margin-top: 50px;
}

.main-cont table {
	font-size: 12px;
	border-collapse: collapse;
	border: 1px solid #CCC;
	border-right: 0;
	border-top: 0;
}

.main-cont table th,.main-cont table td {
	border: 1px solid #CCC;
	border-left: 0;
	border-bottom: 0;
	padding: 10px;
}

.main-cont table th {
	background: #09AC8D;
	color: #FFF;
}

.main-cont table td {
	text-align: center;
	padding: 10px;
}

.title {
	font-size: 12px;
	font-weight: bold;
	text-align: center;
	padding: 20px;
}
</style>
</head>
<body>

	<c:forEach var="item" items="${issues}">
		<div style="display: none"></div>
		<div class="main-cont">
			<c:if test="${!empty item.value.pointsList }">
				<div class="title">${item.key} 以下内容中，有部分号码存在奖金变动</div>
				<table width="100%" id='pointsTable'>
					<tr>
						<th>投注项</th>
						<th>发生奖金变动的号码</th>
						<th>目前奖金</th>
						<th>对应奖金的购买倍数</th>
					</tr>
					<c:forEach items="${item.value.pointsList}" var="temp">
						<tr class="c_${temp.betType}">
							<td class="td_c_${temp.betType}">[${temp.betType }]<br />${temp.betDetail
								}
							</td>
							<td>${temp.point }</td>
							<td><fmt:formatNumber value="${temp.retValue/10000}"
									pattern="#0.00#" maxFractionDigits="2"/></td>

							<td>${temp.mult }</td>
						</tr>
					</c:forEach>
				</table>
				<br />
				<br />
			</c:if>
			<c:if test="${!empty item.value.blockadeList}">
			<div class="title">${item.key}以下内容中，存在受限内容，系统已经为您做出调整</div>
			<table width="100%">
				<tr>
					<th>投注项</th>
					<th>计划投注倍数</th>
					<th>目前可投倍数</th>
				</tr>
				<c:forEach items="${item.value.blockadeList }" var="temp">
					<tr>
						<td>[${temp.betType }]<br />${temp.blockadeDetail }
						</td>
						<td>${temp.beishu}</td>
						<td>${temp.realBeishu }</td>
					</tr>
				</c:forEach>				
			</table>
		</div>
		</c:if>
	</c:forEach>
</body>
</html>
