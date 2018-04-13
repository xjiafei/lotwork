<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" class="html-content">
<head>
	<meta charset="UTF-8">
	<title>查看组权限</title>
	<style>
	.tree-list #J-tree-container,.tree-list #J-tree-container2 {padding-bottom:20px;}
	.ui-tree-node:hover {background:#FFFFe1;}
	.ui-tree-check,.ui-tree-del {float:right;height:24px;line-height:24px;padding-right:5px;}
	.ui-tree-del {cursor:pointer}
	</style>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/acl/showGroupAuth.js"></script>
</head>
<body>
			<div class="col-crumbs">
                <div class="crumbs">
                    <strong>当前位置：</strong>
                    <a href="${currentContextPath}/aclAdmin/goUserManager">权限中心</a> &gt;
                    <a href="${currentContextPath}/aclAdmin/queryAclGroup?userId=${sessionScope.info.id}">权限组管理</a> &gt; 查看组权限
                </div>
            </div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title">你正在查看的是组<spring:message htmlEscape="true" javaScriptEscape="true" text="${name }"></spring:message>的权限</h3>
						<ul class="tree-list clearfix">
							<li class="tree-list-layout">
								<h3 class="title">可分配权限</h3>
								<div class="content" id="J-tree-container">
								</div>
							</li>
							<li>
								<h3 class="title">已授予权限</h3>
								<div class="content" id="J-tree-container2">
									
								</div>
							</li>
						</ul>
						<input id="id" type="hidden" value="${id }">
						<input id="pid" type="hidden" value="${pid }">
					</div>
				</div>
			</div>

</body>
</html>