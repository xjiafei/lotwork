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
	
		<div style="display: none"></div>
		<div class="main-cont">
			
			<div class="title">${issueCode}以下内容中，存在受限内容，系统已经为您做出调整</div>
			<table width="100%">
				<tr>
					<th>投注项</th>
					<th>计划投注金额</th>
					<th>目前可投金额</th>
				</tr>
				<c:forEach var="item" items="${issues}">
					<c:forEach items="${item.value.blockadeList }" var="temp">
						<tr>
							<td>[${temp.betType }]<br />${temp.blockadeDetail }
							</td>
							<td>${temp.beforeAmount}</td>
							<td>${temp.afterAmount}</td>
						</tr>
					</c:forEach>
				</c:forEach>				
			</table>
		</div>
	
</body>
</html>
