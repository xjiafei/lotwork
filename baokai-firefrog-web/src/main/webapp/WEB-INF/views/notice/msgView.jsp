<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>站内信-已发信息</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/notice/msgView.js"></script>	
</head>

<body>
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="${currentContextPath}/noticeAdmin/goSysMsgManager/">通知中心</a> &gt; 查看信息</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<ul class="ui-form">
							<li>
								<label for="" class="ui-label">接受用户：</label>
								<span class="ui-singleline">${msg.accept}</span>
							</li>
							<c:if test="${!empty msg.acceptGroup}">
							<li>
								<label for="" class="ui-label">用户组：</label>
								<span class="ui-singleline" style="width: 600px; height: auto;">${msg.acceptGroup}</span>
							</li>
							</c:if>
							<c:if test="${!empty msg.acceptUser}">
							<li>
								<label for="" class="ui-label">用户名：</label>
								<span class="ui-singleline" style="width: 600px; height: auto;word-wrap:break-word;">${msg.acceptUser}</span>
							</li>
							</c:if>
							<li>
								<label for="" class="ui-label">	发送时间：</label>
								<span class="ui-singleline">${msg.sentTime}</span>
							</li>
							<li>
								<label for="" class="ui-label">消息有效时间：</label>
								<span class="ui-singleline">${msg.expiredTime}</span>
							</li>
							<li>
								<label for="" class="ui-label">操作人：</label>
								<span class="ui-singleline">${msg.operator}</span>
							</li>
							<li>
								<label for="" class="ui-label">主题：</label>
								<span class="ui-singleline">${msg.title}</span>
							</li>
							<li>
								<label for="" class="ui-label">内容：</label>
								<%-- <div class="textarea" style="display:block;margin:0 20px 0 150px;padding:10px;">
								${msg.content}
								</div> --%>
								<div class="textarea">
									<div id="J-content" name="content" rows="12" cols="140" style="overflow:scroll;width:500px;height:300px;word-break:break-all">
										${msg.content}
									</div>
								</div>
							</li>
							<li>
								<label for="" class="ui-label">消息推送：</label>
								<span class="ui-singleline">${msg.messagePush}</span>
							</li>
							<li class="ui-btn" style="padding-top:30px;">
									<a class="btn btn-important" href="javascript:history.go(-1);">关 闭<b class="btn-inner"></b></a>
								</li>
						</ul>
					</div>
				</div>
			</div>
</body>