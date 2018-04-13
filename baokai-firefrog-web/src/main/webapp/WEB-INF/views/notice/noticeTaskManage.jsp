<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" class="html-content">
<head>
	<meta charset="UTF-8">
	<title>4.1任务-通知任务管理</title>	
	<style>
	.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
	.j-ui-tip .sj {display:none;}
	
	.ui-form-window li {margin-top:5px;margin-bottom:5px;}
	
	.pop-window-datepicker {z-index:710;}
	</style>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/notice/noticeTaskManage.js"></script>
</head>
<body>
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="${currentContextPath}/noticeAdmin/goSysMsgManager/">通知中心</a> &gt; 通知任务管理</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form action="${currentContextPath}/noticeAdmin/updateNoticeTask" id="j-from" method="post">
						<table class="table table-info table-border" id="J-table">
							<thead>
								<tr>
									<th>任务类型</th>
									<th>任务名</th>
									<th>发送任务方式</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${treeStrucs}" var="tree" varStatus="status">
									<tr>
										<td rowspan="${tree.size }">${tree.module }</td>
										<c:forEach begin="0" end="0" items="${tree.noticeTasks }" var="noticeTask">
										<td>${noticeTask.task }</td>
										<td class="checkbox-list">
											<c:if test="${noticeTask.emailActived == 1 }">
												<c:if test="${noticeTask.emailUsed == 1 }"><label class="label" for="e"><input type="checkbox" id="e" name="email_${noticeTask.id }" class="checkbox" value="1" checked="checked">邮箱</label></c:if>
												<c:if test="${noticeTask.emailUsed != 1 }"><label class="label" for="e"><input type="checkbox" id="e" name="email_${noticeTask.id }" class="checkbox" value="1">邮箱</label></c:if>
											</c:if>
											<c:if test="${noticeTask.innerMsgActived == 1 }">
												<c:if test="${noticeTask.innerMsgUsed == 1 }"><label class="label" for="e"><input type="checkbox" id="e" name="innerMsg_${noticeTask.id }" class="checkbox" value="1" checked="checked">站内信</label></c:if>
												<c:if test="${noticeTask.innerMsgUsed != 1 }"><label class="label" for="e"><input type="checkbox" id="e" name="innerMsg_${noticeTask.id }" class="checkbox" value="1">站内信</label></c:if>
											</c:if>
											<c:if test="${noticeTask.noteActived == 1 }">
												<c:if test="${noticeTask.noteUsed == 1 }"><label class="label" for="e"><input type="checkbox" id="e" name="note_${noticeTask.id }" class="checkbox" value="1" checked="checked">桌面通知</label></c:if>
												<c:if test="${noticeTask.noteUsed != 1 }"><label class="label" for="e"><input type="checkbox" id="e" name="note_${noticeTask.id }" class="checkbox" value="1">桌面通知</label></c:if>
											</c:if>
										</td>
										<td class="link-list">
											<c:if test="${noticeTask.actived ==1 }"><a data-id="${noticeTask.id}" class="row-cancel" href="javascript:void(0)">禁用</a></c:if>
											<c:if test="${noticeTask.actived !=1 }"><a data-id="${noticeTask.id}" class="row-open" href="javascript:void(0)">启用</a></c:if>
											<c:if test="${noticeTask.innerMsgActived == 1 }">
											<a href="${currentContextPath}/noticeAdmin/toInnerMsgTemp?id=${noticeTask.id}" target="_blank">修改站内信模板</a>
											</c:if>
											<c:if test="${noticeTask.emailActived == 1 }">
											<a href="${currentContextPath}/noticeAdmin/toEmailTemp?id=${noticeTask.id}" target="_blank">修改邮件模板</a>
											</c:if>
											<c:if test="${noticeTask.noteActived == 1 }">
											<a href="${currentContextPath}/noticeAdmin/toNoticeTemp?id=${noticeTask.id}" target="_blank">修改桌面通知模板</a>
											</c:if>
										</td>
										</c:forEach>
									</tr>
									<c:forEach begin="1" items="${tree.noticeTasks }" var="noticeTask">
										<tr>
											<td>${noticeTask.task }</td>
											<td class="checkbox-list">
												<c:if test="${noticeTask.emailActived == 1 }">
													<c:if test="${noticeTask.emailUsed == 1 }"><label class="label" for="e"><input type="checkbox" id="e" name="email_${noticeTask.id }" class="checkbox" value="1" checked="checked">邮箱</label></c:if>
													<c:if test="${noticeTask.emailUsed != 1 }"><label class="label" for="e"><input type="checkbox" id="e" name="email_${noticeTask.id }" class="checkbox" value="1">邮箱</label></c:if>
												</c:if>
												<c:if test="${noticeTask.innerMsgActived == 1 }">
													<c:if test="${noticeTask.innerMsgUsed == 1 }"><label class="label" for="e"><input type="checkbox" id="e"  name="innerMsg_${noticeTask.id }" class="checkbox" value="1" checked="checked">站内信</label></c:if>
													<c:if test="${noticeTask.innerMsgUsed != 1 }"><label class="label" for="e"><input type="checkbox" id="e"  name="innerMsg_${noticeTask.id }" class="checkbox" value="1">站内信</label></c:if>
												</c:if>
												<c:if test="${noticeTask.noteActived == 1 }">
													<c:if test="${noticeTask.noteUsed == 1 }"><label class="label" for="e"><input type="checkbox" id="e" name="note_${noticeTask.id }" class="checkbox" value="1" checked="checked">桌面通知</label></c:if>
													<c:if test="${noticeTask.noteUsed != 1 }"><label class="label" for="e"><input type="checkbox" id="e" name="note_${noticeTask.id }" class="checkbox" value="1">桌面通知</label></c:if>
												</c:if>
											</td>
											<td class="link-list">
												<c:if test="${noticeTask.actived ==1 }"><a data-id="${noticeTask.id}" class="row-cancel" href="javascript:void(0)">禁用</a></c:if>
												<c:if test="${noticeTask.actived !=1 }"><a data-id="${noticeTask.id}" class="row-open" href="javascript:void(0)">启用</a></c:if>
												<c:if test="${noticeTask.innerMsgActived == 1 }">
												<a href="${currentContextPath}/noticeAdmin/toInnerMsgTemp?id=${noticeTask.id}" target="_blank">修改站内信模板</a>
												</c:if>
												<c:if test="${noticeTask.emailActived == 1 }">
												<a href="${currentContextPath}/noticeAdmin/toEmailTemp?id=${noticeTask.id}" target="_blank">修改邮件模板</a>
												</c:if>
												<c:if test="${noticeTask.noteActived == 1 }">
												<a href="${currentContextPath}/noticeAdmin/toNoticeTemp?id=${noticeTask.id}" target="_blank">修改桌面通知模板</a>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</c:forEach>
								<tr>
									<td colspan="4" class="text-center"><a href="javascript:void(0);" class="btn btn-important">保存<b class="btn-inner"></b></a></td>
								</tr>
							</tbody>
						</table>
						</form>
					</div>
				</div>
			</div>
</body>
</html>