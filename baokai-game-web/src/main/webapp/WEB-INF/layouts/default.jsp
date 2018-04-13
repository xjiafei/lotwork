<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title><sitemesh:title default="winterFramework-demo"/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link href="${staticFileContextPath}/static/bootstrap/2.0.4/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${staticFileContextPath}/static/jquery-validation/1.9.0/validate.css" type="text/css" rel="stylesheet" />
<link href="${staticFileContextPath}/static/styles/quickstart.css" type="text/css" rel="stylesheet" />
<script src="${staticFileContextPath}/static/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="${staticFileContextPath}/static/jquery-validation/1.9.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${staticFileContextPath}/static/jquery-validation/1.9.0/messages_cn.js" type="text/javascript"></script>


<sitemesh:head/>
</head>

<body>
	<div class="container">
		<%@ include file="/WEB-INF/layouts/header.jsp"%>
		<div id="content" class="span12">
			<sitemesh:body/>
		</div>
		<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	</div>
	<script src="${staticFileContextPath}/static/bootstrap/2.0.4/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>