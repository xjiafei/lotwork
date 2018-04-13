<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" class="html-content">
<head>
<meta charset="UTF-8">
<title>2.2邮件-测试邮件</title>
<style>
.j-ui-tip {
	background: #FFFFE1;
	border: 1px solid #CCC;
	color: #333;
	font-size: 12px;
}

.j-ui-tip .sj {
	display: none;
}

.ui-form-window li {
	margin-top: 5px;
	margin-bottom: 5px;
}

.pop-window-datepicker {
	z-index: 710;
}
</style>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/notice/testEmail.js"></script>
</head>
<body>
	<div class="col-crumbs">
		<div class="crumbs">
			<strong>当前位置：</strong><a
				href="${currentContextPath}/noticeAdmin/goSysMsgManager/">通知中心</a>
			&gt; 邮件测试
		</div>
	</div>
	<div class="col-content">
		<div class="col-main">
			<div class="main">
				<div class="ui-tab">
					<div class="ui-tab-title clearfix">
						<ul>
							<li><a
								href="${currentContextPath}/noticeAdmin/toSaveEmailTemplate">邮件配置</a></li>
							<li class="current">邮件测试</li>
						</ul>
					</div>
					<div class="ui-tab-content ui-tab-content-current">
		
							<ul class="ui-form">							
								<li><label class="ui-label" for="">收件人邮箱：</label> 
									<input id="J-input-username" type="text" value="" class="input w-6" >
										<span class="ui-check"><i></i>*</span>
								</li>
								<li><label class="ui-label" for="">邮件标题：</label> 
									<input id="J-input-title" type="text" value="" class="input w-6" >
										<span class="ui-check"><i></i>*</span>
								</li>
								<li><label class="ui-label" for="">邮件内容：</label>
									<div class="textarea w-10">
										<textarea id="J-textarea-content" name="content" ></textarea>
										<span class="ui-check"><i></i>*</span>
									</div>
								</li>
							</ul>
						
	            
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
									<th>测试</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="mail" items="${Mail}" varStatus="c">
										<form  action="/noticeAdmin/mailtest" method="POST"> 	
										<tr>
											<td>${c.count}</td>
											<td>${mail.sendmethod}</td>
											<td>${mail.smtpserver}</td>
											<td>${mail.port}</td>
											<td>${mail.sender}</td>
											<td>${mail.account}</td>
											<td>${mail.password}</td>							
							
										<td>
											<input class="rcvmail_out"  type="hidden"  name="rcvEmail" value="">
											<input class="title_out"   type="hidden" name="title" value="">
											<input class="content_out"  type="hidden" name="content" value="">
											<input type="hidden" name="sendmethod" value="${mail.sendmethod}"> 
											<input type="hidden" name="smtpserver" value="${mail.smtpserver}">
											<input type="hidden" name="port" value="${mail.port}"> 
											<input type="hidden" name="sender" value="${mail.sender}"> 
											<input type="hidden" name="account" value="${mail.account}">
											<input type="hidden" name="password" value="${mail.password}">
											<button type="submit" class="btn btn-important J-button-submit" id="testsend">测试</button>
										</td>
										</form>
									</tr>
										
								</c:forEach>

							</tbody>
						</table>					

					</div>
							<input id="status" type="hidden" value="${mailtest}" >
				</div>


			</div>
		</div>
	</div>
	
	<script type="text/javascript">
	
 	 $(window).load(function() {
        	var val = $("#status").val();
        	if(val=="false")
        		alert("测试发送失败");
        	if(val=="true")
         		alert("测试发送成功");   	 
        });
		  
</script>
</body>
</html>