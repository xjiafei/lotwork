<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib uri="/tag-page" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户管理</title>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/acl/userManager.js"></script>
<style>
.pop .btn.confirm {
	background:
		url("${staticFileContextPath}/static/images/common/ui-btn.png") repeat
		scroll 0 -240px transparent;
	border: 0 none;
	border-radius: 0 0 0 0;
	color: #FFFFFF;
	height: 34px;
	line-height: 34px
}

.pop .btn.confirm .btn-inner {
	background:
		url("${staticFileContextPath}/static/images/common/ui-btn.png") repeat
		scroll right -240px transparent;
	height: 34px;
	width: 15px;
}

.color-yellow {
	color: #CC3300
}
</style>

</head>

<body>
<permission:hasRight moduleName="ACL">
	<div class="col-crumbs">
		<div class="crumbs">
			<strong>当前位置：</strong><a href="#">权限中心</a> &gt; 查看用户
		</div>
	</div>
	<div class="col-content">
		<div class="col-main">
			<div class="main">


				<form id="J-form" action="${currentContextPath}/aclAdmin/searchUser">
					<input type="hidden" name="pageNo" value="${page.pageNo}"
						id="pageNo">
					<input type="hidden" id="searchGroup" name="searchGroup" value="${search.groupId }">
					<ul class="ui-search">
						<li class="name"><select class="ui-select" name="searchType">
								<option value="0"
									<c:if test="${search.searchType==0 }">selected</c:if>>用户名</option>
								<option value="1"
									<c:if test="${search.searchType==1 }">selected</c:if>>email</option>
								<option value="3"
									<c:if test="${search.searchType==3 }">selected</c:if>>电话</option>
						</select> <input type="text" name="searchValue" id="J-search-username"
							value="${search.searchValue}" class="input"></li>
						<li><label class="ui-label">权限组：</label> <select
							class="ui-select" name="groupId" id="groupId">
								${groups.box}
						</select></li>
						<li><label class="ui-label">状态：</label> <select
							class="ui-select" name="status">
								<option value="-1">所有状态</option>
								<option value="0"
									<c:if test="${0==search.status }">selected </c:if>>正常</option>
								<option value="1"
									<c:if test="${1==search.status }">selected </c:if>>锁定</option>
						</select></li>
						<li><a href="javascript:void(0);" id="J-button-submit"
							class="btn btn-important">搜 索<b class="btn-inner"></b></a></li>
					</ul>
				</form>
				<c:if test="${empty list}">
					<div class="alert alert-waring">
						<i></i>
						<div class="txt">
							<h4>没有符合条件的记录，请更改查询条件！</h4>
						</div>
					</div>
				</c:if>

				<c:if test="${!empty list}">
					<table class="table table-info">
						<thead>
							<tr>
								<th>用户名</th>
								<th>Email</th>
								<th>电话</th>
								<th>所属权限组</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="list" varStatus="status">
								<tr>
									<td class="username">${list.account}</td>
									<td>${list.email}</td>
									<td>${list.telephone}</td>
									<td>${list.groupName}</td>
									<c:if test="${list.status==0 }">
										<td>正常</td>
									</c:if>
									<c:if test="${list.status==1 }">
										<td class="color-red">锁定</td>
									</c:if>
									<c:if test="${list.status==-1 }">
										<td class="color-yellow">待删除</td>
									</c:if>
									<td><permission:hasRight moduleName="ACL_USER_VIEW">
											<a class="report"
												href="${currentContextPath}/aclAdmin/searchLog?account=${list.account}">操作日志</a>
										</permission:hasRight> <permission:hasRight moduleName="ACL_USER_MODYFY">
											<a class="edit"
												href="${currentContextPath}/aclAdmin/goModify?id=${list.id}">修改</a>
										</permission:hasRight> <permission:hasRight moduleName="ACL_USER_LOCK">
											<c:if test="${list.status!=-1 }">
												<c:if test="${list.status!=1 }">
													<a class="lock" href="javascript:void(0);"
														data-id="${list.id}">锁定</a>
												</c:if>
												<c:if test="${list.status==1 }">
													<a class="unlock" href="javascript:void(0);"
														data-id="${list.id}">解锁</a>
												</c:if>
											</c:if>
										</permission:hasRight> <c:if test="${list.status==-1 }">
											<a class="delete" href="javascript:void(0);"
												data-id="${list.id}">删除</a>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}"
						pageSize="${page.pageSize}" />
				</c:if>
			</div>
		</div>
	</div>
	<script>
		function doPre() {
			var currentPageNo = $("#pageNo").val();
			$("#pageNo").val(parseInt(currentPageNo) - 1);
			$("#J-form").submit();
		}

		function doNext() {
			var currentPageNo = $("#pageNo").val();
			$("#pageNo").val(parseInt(currentPageNo) + 1);
			$("#J-form").submit();
		}

		function doForward(index) {
			if (index == -1) {
				var reg = /^[0-9]+$/;
				if (reg.test($("#forwardPage").val())) {
					$("#pageNo").val(parseInt($("#forwardPage").val()));
				} else {
					return;
				}
			} else {
				$("#pageNo").val(index);
			}
			$("#J-form").submit();
		}

		function doCurrent(pageNo) {
			$("#pageNo").val(pageNo);
			$("#J-form").submit();
		}
		 var searchGroup = $("#searchGroup").val();
		if(searchGroup!=null&&searchGroup!=""){
			$("#groupId").val(searchGroup);
		}
	</script>
	</permission:hasRight>
</body>