<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
	<title>501 - 系统内部错误</title>
</head>

<body>
<div><h1>系统发生内部错误.</h1></div>
<div><a href="<c:url value="/"/>">返回首页</a></div>
</body>
</html>
