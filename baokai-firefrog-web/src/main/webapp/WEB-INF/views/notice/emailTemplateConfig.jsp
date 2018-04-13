<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" class="html-content">
<head>
	<meta charset="UTF-8">
	<title>2.1邮件-邮件配置</title>
	<style>
	.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
	.j-ui-tip .sj {display:none;}
	.ui-form-window li {margin-top:5px;margin-bottom:5px;}
	.pop-window-datepicker {z-index:710;}
	</style>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/notice/emailTemplateConfig.js"></script>
</head>
<body>
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="${currentContextPath}/noticeAdmin/goSysMsgManager/">通知中心</a> &gt; 邮件配置</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li class="current">邮件配置</li>
									<li><a href="${currentContextPath}/noticeAdmin/toTestEmailTemplate">邮件测试</a></li>
								</ul>
							</div>
							<div class="ui-tab-content ui-tab-content-current">
							
							
							
							<form action="${currentContextPath}/noticeAdmin/saveEmailTemplate" id="J-form" method="post">
								<ul class="ui-form">
									<li>
										<label class="ui-label" for="">邮件发送方式:</label>
										<select id="J-select-mailtype" class="ui-select w-3" name="upsendmethod"  required="">
											<option value="smtp" selected="selected">smtp</option>
											<!-- <option value="pop">pop</option>
											<option value="imap">imap</option> -->
										</select>
									</li>
									
									<li>
										<label class="ui-label" for="">smtp服务器:</label>
										<input id="J-input-server" type="text" value="${updateEmail.smtpserver }" class="input w-3" name="upsmtpserver"  required="" >
										<span class="ui-check"><i></i>服务器地址不能为空</span>
									</li>
									<li>
										<label class="ui-label" for="">smtp端口:</label>
										<input  type="text" value="${updateEmail.port}" class="input w-3" name="upport" id="J-input-port"  required="" >
										<span class="ui-check"><i></i>端口不能为空</span>
									</li>
								
									<li id="J-panel-switch">
										<label for="" class="ui-label"><span class="type-mailtype">smtp</span>用户身份验证：</label>
										<span class="radio-list">
										<input type="hidden" id="checkId" value="${template.smtpAuth }">
											<label for="J-user-switch-1" class="label"><input name="smtpAuth" type="radio" value="1" class="radio radio-switch" id="J-user-switch-1" checked="checked">开启</label>
											<label for="J-user-switch-2" class="label"><input name="smtpAuth" type="radio" value="0" class="radio radio-switch" id="J-user-switch-2" >关闭</label>
										</span>
										<span class="ui-text-prompt">如果<span class="type-mailtype">smtp</span>服务器要求通过身份验证才可以发邮件，请选择开启。</span>
									</li>
									<li>									
										<label class="ui-label" for="">寄件人:</label>
										<input type="text" value="${updateEmail.sender }" class="input w-3" name="upsender" id="J-input-sender" required="" >
										<input type="hidden" name="hsen" id="hsender"  value="${updateEmail.sender}"></td>
										<span class="ui-check"><i></i>寄件人邮箱不能为空</span>
									</li>
									<li >
										<label class="ui-label" for="">帐户:</label>
										<input id="J-input-username" type="text" value="${updateEmail.account }" class="input w-3" name="upaccount" required="">
										<input type="hidden" name="hacu" id="haccount"  value="${updateEmail.account}"></td>
										<span class="ui-check"><i></i>帐户不能为空</span>
									</li>
									<li >
										<label class="ui-label" for="">密码:</label>
										<input id="J-input-password"  type="text" value="${updateEmail.password }" class="input w-3" name="uppassword" required="">
										<span class="ui-check"><i></i>密码不能为空</span>
									</li>
									<li>
										<label class="ui-label" for=""></label>
										<c:if test="${updateEmail.sendmethod == null}">
										<button  id="J-button-test" class="btn btn-important insertdata" formaction="${currentContextPath}/noticeAdmin/insertEmail">确认新增</button>
										</c:if>
										<c:if test="${updateEmail.sendmethod != null}">
										<button id="editData" class="btn btn-important" formaction="${currentContextPath}/noticeAdmin/updateEmailConfigById" type="submit">确认修改</button>
										</c:if>
										<c:if test="${updateEmail.sendmethod != null}">
										<a class="btn btn-primary" href="${currentContextPath}/noticeAdmin/toSaveEmailTemplate">取消修改</a>
										</c:if>
									</li>
								</ul>
									</form>
								
									<table class="table table-info">
							<thead>
								<tr>
									<th>编号</th>
									<th>邮件发送方式</th>
									<th>smtp服务器</th>
									<th>smtp端口</th>
									<th>寄件人</th>
									<th>帐户</th>
									<th>密码</th>		
									<th>修改</th>	
									<th>删除</th>								
								</tr>
							</thead>
							<tbody>
								<c:forEach var="mail" items="${Mail}" varStatus="c">
									<form action="${currentContextPath}/noticeAdmin/getEmailByID?sender=${mail.sender}"  method="POST">
										<tr>
											<td>${c.count}</td>
											<td>${mail.sendmethod}</td>
											<td>${mail.smtpserver}</td>
											<td>${mail.port}</td>
											<td>${mail.sender}</td>
											<td>${mail.account}</td>
											<td>${mail.password}</td>
											
											<td><button class="btn btn-important" type="submit">修改</button>
												<input type="hidden" name="account" value="${mail.account}"></td>
											<td><a class="btn btn-primary deletehref" 
												href="${currentContextPath}/noticeAdmin/deleteEmailById?account=${mail.account}">删除</a></td>
									</form>
									</tr>
								</c:forEach>

							</tbody>
						</table>
								<input id="insertstatus" type="hidden"  value="${insertstatus}" >
								<input id="updatestatus" type="hidden" value="${updatestatus}" >
								<input id="deletestatus" type="hidden" value="${deletestatus}" >
							</div>
						</div>	
					</div>
				</div>
			</div>	
<script>
	  
  
  
</script>
	
</body>
</html>