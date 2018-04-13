<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>操作日志详情</title>
</head>			
<body>
			<div class="col-crumbs">
                <div class="crumbs">
                    <strong>当前位置：</strong>
                    <a href="${currentContextPath}/aclAdmin/goUserManager">权限中心</a> &gt;
                    <a href="${currentContextPath}/aclAdmin/goLog">操作日志</a> &gt; 查看详情
                </div>
            </div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						
						<ul class="ui-form">
							<li>
								<label class="ui-label" for="">编号：</label>
								<span class="ui-singleline">${detail.id}</span>
							</li>
							<li>
								<label class="ui-label" for="">时间：</label>
								<span class="ui-singleline">${detail.createTime}</span>
							</li>
							<li>
								<label class="ui-label" for="">管理员：</label>
								<span class="ui-singleline">${detail.account}</span>
							</li>
							<li>
								<label class="ui-label" for="">IP：</label>
								<span class="ui-singleline">${detail.ip}</span>
							</li>
							<li>
								<label class="ui-label" for="">访问地址：</label>
								<span class="ui-singleline">${detail.url}</span>
							</li>
							<li>
								<label class="ui-label" for="">日志：</label>
								<span class="ui-singleline">${detail.detail}</span>
							</li>
						</ul>
					</div>
				</div>
			</div>
</body>