<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath %>">
	<meta charset="UTF-8">
	<title><spring:message code="userLogin"/></title>
	<link href="easyui/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css" />
    <script type="text/javascript" src="easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="easyui/js/jquery.easyui.min.js"></script>
<!--     <script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script> -->
    <script type="text/javascript" src="easyui/js/outlook2.js"> </script>
</head>
<style>
.divcss5{width:610px; height:250px;margin-left:auto; margin-right:auto} 
</style>
<body>
	<div style="margin:250px 0;"></div>
	<div  class="divcss5">
	<div id="p">
		<div style="padding:20px 190px 50px 120px">
	    <form id="ff" method="post" action="/user/lg">
	    	<table cellpadding="5">
	    		<tr>
	    			<td><spring:message code="userName"/>:</td>
	    			<td><input id="userName" class="easyui-textbox" type="text" name="userName"></input></td>
	    		</tr>
	    		<tr>
	    			<td><spring:message code="password"/>:</td>
	    			<td><input id="password" name="password"  class="easyui-validatebox" type="password"/></td>
	    		</tr>
	    		<tr>
	    			<td></td>
	    			<td></td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:20px">
	    	<input name="sb" value='<spring:message code="submit"/>' type="button"  id="sb"/>
	    	<input name="qc" value='<spring:message code="clearup"/>' type="button"  id="qc"/>
	    </div>
	    </div>
	</div>
	</div>
	<script>
	
	$('#p').panel({
	    width:600,
	    height:250,
	    title:'<spring:message code="userLogin"/>',
	    doSize:true,
	    fit:false,
	    left:12,
	    top:12
	}); 
	$('#sb').click(function(){
		if($('#userName').val()==''||$('#password').val()==''){
			msgShow('<spring:message code="prompt"/>', '<spring:message code="notNull"/>', 'warning');
			return;
		}
		$('#ff').submit();
	});
	$('#qc').click(function(){
			$('#ff').form('clear');
	});
	var message = '${message}';
	if(message !=''){
		msgShow('<spring:message code="prompt"/>', message, 'warning');
	}
	
	
	</script>
</body>
</html>