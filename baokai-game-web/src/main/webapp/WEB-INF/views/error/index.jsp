<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
	<title>系统内部错误</title>
	<script type="text/javascript">
            window.location.href = "${userContextPath}/index";
    </script>
</head>

<body>
<div><h1>系统发生内部错误.</h1></div>
<div><a href="<c:url value="${userContextPath}/index"/>">返回首页</a></div>
</body>
</html>
