<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><decorator:title default="运营后台" /></title>
<jsp:include page="headerStatic.jsp" flush="true" />
<decorator:head />
</head>
<body>
<%-- 2016.03.14 Tool Bar 整合 Start--%>
	<jsp:include page="../../../../layouts/header-admin-toolbar.jsp" flush="true" />
<%-- 2016.03.14 Tool Bar 整合 End--%>
<%-- <jsp:include page="menu.jsp" flush="true" /> --%>

	<div class="col-content">
		<div class="col-side">
			<jsp:include page="sider.jsp" flush="true" />
		</div>
		<div class="col-main">
			<!-- 从被装饰页面获取body标签内容 -->
			<decorator:body />
		</div>
	</div>
</body>
</html>