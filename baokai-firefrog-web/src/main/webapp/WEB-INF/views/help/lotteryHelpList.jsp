<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>帮助中心 彩种列表</title>
	<%String path = request.getContextPath(); %>	
</head>
<body>

	
	<div class="clearfix">
		<div class="help-container help-article-container">
			
			<div class="help-sort-title">
				<spring:message htmlEscape="true" javaScriptEscape="true" text="${cate2Name}"></spring:message>
			</div>
			
			<ul class="help-sort-list">
				<c:forEach items="${helps}" var="help" varStatus="status">
						<li>
							<a href="javaScript:void(0)" onclick="toLotteryDetail('${help.id}','${cate2Name}')">
								<img src="${staticFileContextPath}/dynamic/${help.lotteryLogo}" style="width:128px; height:80px" />
								<span class="game-name">${help.title}</span>
								<span><spring:message htmlEscape="true" javaScriptEscape="true" text="${help.lotteryAdvert}"></spring:message></span>
							</a>
						</li>
				</c:forEach>
			</ul>
	  </div>
	</div>
	<script type="text/javascript">
		$('.help-main').addClass('help-main-half');
		function toLotteryDetail(id,name){
			window.location.href = baseUrl + "/help/queryLotteryDetail?helpId="+ id + "&cate2Name=" + encodeURI(encodeURI(name));
		}
		
	</script>
</body>
</html>