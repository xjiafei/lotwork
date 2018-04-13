<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
    <link rel="stylesheet" href="${staticFileContextPath}/static/images/common/js-ui.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/admin/admin.css" />
<title>宝开娱乐</title>
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
	src="${staticFileContextPath}/static/js/channel/channeltempalt.js"></script>
</head>

<body>
	<div class="col-crumbs">
		<div class="crumbs">
		</div>
	</div>
	<div class="col-content">
		<div class="col-main">
			<div class="main">
				<div class="ui-tab">
					<div class="ui-tab-content ui-tab-content-current">
						

						<c:if test="${status == 0}">
							<c:if test="${channel.url == null}">
								<c:redirect url="http://www.fh885.com"/>
							</c:if>
						</c:if>
						
						<c:if test="${status == 1}">
								<div class="alert alert-waring"><i></i>
									<div class="txt"><h4>登入次数以达到上限</h4></div>
								</div>
						</c:if>
						
						<c:if test="${status == 2}">
								<div class="alert alert-waring"><i></i>
									<div class="txt"><h4>登入时间已超过到期时间</h4></div>
								</div>
						</c:if>
						
						<c:if test="${status == 3}">
								<div class="alert alert-waring"><i></i>
									<div class="txt"><h4>连结暂时冻结</h4></div>
								</div>
						</c:if>
						
						<c:if test="${status == 4}">
								<div class="alert alert-waring"><i></i>
									<div class="txt"><h4>连结永久凍結</h4></div>
								</div>
						</c:if>
						
						<c:if test="${status == 5}">
								<div class="alert alert-waring"><i></i>
									<div class="txt"><h4>此ip暂时冻结</h4></div>
								</div>
						</c:if>
						
						<c:if test="${status == 6}">
								<div class="alert alert-waring"><i></i>
									<div class="txt"><h4>此ip永久凍結</h4></div>
								</div>
						</c:if>
						
						<c:if test="${status == 7}">
								<div class="alert alert-waring"><i></i>
									<div class="txt"><h4>连结已经无效，请联络客服</h4></div>
								</div>
						</c:if>
					</div>
				</div>
				
			</div>
		</div>
	</div>
</body>