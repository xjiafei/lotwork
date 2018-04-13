<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
		<div class="col-side">
			<dl class="nav">
				<permission:hasRight moduleName="NOTICE_SITEMSG"><dt>站内信</dt></permission:hasRight>
				<permission:hasRight moduleName="NOTICE_SENDSITEMSG">
				<dd <c:if test="${cate2Name == 'goCreateMsg'}">class="current" </c:if>><a href="${currentContextPath}/noticeAdmin/goCreateMsg">发送站内信</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_SYSMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goSysMsgManager'}">class="current" </c:if>><a href="${currentContextPath}/noticeAdmin/goSysMsgManager">系统消息管理</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/noticeAdmin/goUserMsg">用户消息管理</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_EMAIL"><dt>邮件</dt></permission:hasRight>
				<permission:hasRight moduleName="NOTICE_EMAILCONFIG">
				<c:if test="${cate2Name == '邮件配置'}">
				<dd class="current"><a href="${currentContextPath}/noticeAdmin/toSaveEmailTemplate">邮件配置</a></dd>
				</c:if>
				<c:if test="${cate2Name != '邮件配置'}">
				<dd><a href="${currentContextPath}/noticeAdmin/toSaveEmailTemplate">邮件配置</a></dd>
				</c:if>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_EMAIL_TEST">
				<c:if test="${cate2Name == '邮件测试'}">
				 <dd class="current"><a href="${currentContextPath}/noticeAdmin/toTestEmailTemplate">邮件测试</a></dd>
				</c:if>
				<c:if test="${cate2Name != '邮件测试'}">
				<dd><a href="${currentContextPath}/noticeAdmin/toTestEmailTemplate">邮件测试</a></dd>
				</c:if>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_TASK"><dt>通知任务</dt></permission:hasRight>
				<permission:hasRight moduleName="NOTICE_TASKMANAGER">
				<c:if test="${cate2Name != '通知任务管理'}">
					<dd><a href="${currentContextPath}/noticeAdmin/queryNoticeTask">通知任务管理</a></dd>
				</c:if>
				<c:if test="${cate2Name == '通知任务管理'}">
					<dd class="current"><a href="${currentContextPath}/noticeAdmin/queryNoticeTask">通知任务管理</a></dd>
				</c:if>
				</permission:hasRight>
			</dl>
		</div>