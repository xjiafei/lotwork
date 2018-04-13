<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
			<div class="col-side">
			<dl class="nav">
				<permission:hasRight moduleName="ACL_CENTER"><dt>权限中心</dt></permission:hasRight>
				<permission:hasRight moduleName="ACL_ACLGROUPMANAGER">
				<c:if test="${cate2Name == '权限组管理'}">
					<dd class="current"><a href="${currentContextPath}/aclAdmin/queryAclGroup?userId=1">权限组管理</a></dd>
				</c:if>
				<c:if test="${cate2Name != '权限组管理'}">
					<dd><a href="${currentContextPath}/aclAdmin/queryAclGroup?userId=1">权限组管理</a></dd>
				</c:if>
				</permission:hasRight>
				<permission:hasRight moduleName="ACL_CREATEACLGROUP">
				<c:if test="${cate2Name == '创建权限组'}">
					<dd class="current"><a href="${currentContextPath}/aclAdmin/toAddGroup">创建权限组</a></dd>
				</c:if>
				<c:if test="${cate2Name != '创建权限组'}">
					<dd><a href="${currentContextPath}/aclAdmin/toAddGroup?userId=1">创建权限组</a></dd>
				</c:if>
				</permission:hasRight>
				<permission:hasRight moduleName="ACL_USERMANAGER">
				<dd <c:if test="${acl_no == 'goUserManager'}">class="current" </c:if>><a href="${currentContextPath}/aclAdmin/goUserManager">用户管理</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="ACL_ADDUSER">
				<dd <c:if test="${acl_no == 'goCreateUser'}">class="current" </c:if>><a href="${currentContextPath}/aclAdmin/goCreateUser">添加用户</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="ACL_OPERATELOG">
				<dd <c:if test="${acl_no == 'goLog'}">class="current" </c:if>><a href="${currentContextPath}/aclAdmin/goLog">操作日志</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="ACL_MODFIYPASSWORD">
				<dd <c:if test="${acl_no == 'goModifyPwd'}">class="current" </c:if>><a href="${currentContextPath}/aclAdmin/goModifyPwd">修改密码</a></dd>
				</permission:hasRight>
			</dl>
			</div>
<script>

</script>