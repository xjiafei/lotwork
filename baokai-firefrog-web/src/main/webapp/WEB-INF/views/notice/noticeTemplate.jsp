<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" class="html-content">
<head>
	<meta charset="UTF-8">
	<title>3.1.3系统-通知任务管理-系统任务--桌面通知模板编辑</title>
	<style>
	.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
	.j-ui-tip .sj {display:none;}
	.ui-form-window li {margin-top:5px;margin-bottom:5px;}
	.pop-window-datepicker {z-index:710;}
	</style>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/jquery/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor-1.2.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor_lang/zh-cn.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/notice/noticeTemplate.js"></script>
</head>
<body>
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="${currentContextPath}/noticeAdmin/goSysMsgManager/">通知中心</a> &gt; <a href="#">系统任务</a> &gt; 桌面通知模板编辑</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title">桌面通知模板编辑</h3>
						<form action="${currentContextPath}/noticeAdmin/saveNoteTemp" id="J-form" method="post">
						<ul class="ui-form">
							<input type="hidden" name="id" value="${id}">
							<li>
								<label for="" class="ui-label">内容：</label>
								<div class="textarea" style="width:60%">
									<textarea id="J-content"  rows="12" cols="80" style="width:100%;height:300px;" name="temp">${task.noteTemp }</textarea>
								</div>
								<div style="padding-left:150px;"><span class="ui-check"><i></i>内容不能为空</span></div>
							</li>
							<li class="ui-btn">
								<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">保 存<b class="btn-inner"></b></a>
								<a class="btn" href="${currentContextPath}/noticeAdmin/queryNoticeTask">取消<b class="btn-inner"></b></a>
							</li>
						</ul>
						</form>		
					</div>
				</div>
			</div>
</body>
</html>