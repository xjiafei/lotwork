<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" class="html-content">
<head>
	<meta charset="UTF-8">
	<title>复制组</title>
</head>
<body>
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong>
                <a href="${currentContextPath}/aclAdmin/goUserManager">权限中心</a> &gt;
                <a href="${currentContextPath}/aclAdmin/queryAclGroup?userId=${sessionScope.info.id}">权限组管理</a> &gt; 复制权限组
            </div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form action="${currentContextPath }/aclAdmin/copyGroup?userId=1" id="info-content" method="post">
							<ul class="ui-form">
								<li>
									<label class="ui-label" for="">组名称：</label>
									<input id="groupName" name="name" type="text" value="" class="input w-4" />
									<span class="ui-text-prompt">2-20位字符组成</span>
                                    <div class="ui-check"><i class="error"></i><span>2-20位字符组成</span></div>
								</li>
								<li>
									<label class="ui-label" for="">复制的组：</label>
									<span class="ui-singleline"><spring:message htmlEscape="true" javaScriptEscape="true" text="${name }"></spring:message></span>
								</li>
								<li class="radio-list">
									<label class="ui-label" for="">是否启用：</label>
									<label class="label" for="open"><input id="open" type="radio" name="inUser" class="radio" value="1" checked="checked">开启（默认）</label>
									<label class="label" for="off"><input id="off" type="radio" name="inUser" class="radio" value="0">禁用</label>
									<input id="id" name="id" type="hidden" value="${id }">
								</li>
								<li>
									<label class="ui-label" for=""></label>
									<a id="sub" class="btn btn-important" href="javascript:void(0);">复 制<b class="btn-inner"></b></a>
								</li>
							</ul>
						</form>
					</div>
				</div>
			</div>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/acl/copyGroup.js"></script>
</body>
</html>