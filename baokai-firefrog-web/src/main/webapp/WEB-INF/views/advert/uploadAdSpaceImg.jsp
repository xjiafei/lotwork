<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" class="html-content">
<head>
	<meta charset="UTF-8">
	<title>图片上传</title>
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
    <link rel="stylesheet" href="${staticFileContextPath}/static/images/common/js-ui.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/admin/admin.css" />
	<style>
	html,body {height:auto;}
	.thumbnail img {width:40px;height:auto}
	</style>
    <script type="text/javascript" src="${staticFileContextPath}/static/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.base.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Class.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Event.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.util.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tab.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Hover.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tip.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Mask.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.MiniWindow.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Message.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.DatePicker.js"></script>
	
</head>
<body>
<a href="javascript:void(0);" class="thumbnail"><img src="${urlViewPic}${adSpaceStruc.dftImg}" width="40" alt="" /></a>
<script>
var msg = {'status':'${operater.status}','message':'${operater.message}','width':'${operater.width}','height':'${operater.height}','fileUrl':'${operater.fileUrl}'};
try{
	global_img(msg);
}catch(e){
	
}
function global_img(data){
	if(data.status == 100){
		$("#pic1",parent.document).attr("src",$("#urlViewPic",parent.document).val()+data.fileUrl);
   		$("#J-input-img-id",parent.document).val(data.fileUrl);
		$('#J-msg-upload-tip',parent.document).hide();
	}else{
		$("#pic1",parent.document).attr("src",'');
		$("#J-input-img-id",parent.document).val(data.fileUrl);
		$('#J-msg-upload-tip',parent.document).html('<i></i>' + data.message).show();
	}
}
</script>
</body>
</html>