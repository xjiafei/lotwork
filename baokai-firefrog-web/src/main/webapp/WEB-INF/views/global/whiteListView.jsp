<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
		<title>指定IP白名單</title>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/global/whiteList.js"></script>
		<script>
			var currentPath = "${currentContextPath}";
		</script>
	</head>
	<body>
<c:choose>
<c:when test="${fn:contains(aclList,'GLOBAL_WHITELISTIPVIEW')}">
		<div class="col-crumbs"><div class="crumbs" style><strong>当前位置：</strong><a href="#">全局管理</a> &gt; 查看详情页 </div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
							<ul class="ui-form">						
								<li>
										<label for="" class="ui-label"><b>IP地址：</b></label>
										${input.ipAddr}
								</li>
								<li>
										<label for="" class="ui-label"><b>地理位置：</b></label>
										${input.country}
								</li>
								<li>
										<label for="" class="ui-label"><b>操作者：</b></label>
										${input.operator}
								</li>
								<li>
										<label for="" class="ui-label"><b>操作时间：</b></label>
										${input.operationTime}
								</li>
								<li>
									<label for="" class="ui-label"><b>用户名：</b></label>
									<textarea class="ui-textarea w-8" id="userAcuntV" name="userAcuntV" >${input.userAcunt}</textarea>
								</li>
								<li>
										<label for="" class="ui-label"><b>备注：</b></label>
										<input id="remarkV" name="remarkV" type="text" maxlength="40" class="input" value="${input.remark}">
								</li>
								<li><label class="ui-label"></label> 
									<a href="javascript:void(0);" id="J-button-Close"
									class="btn btn-important">关 闭<b class="btn-inner"></b></a>
								</li>
							</ul>			
					</div>
				</div>
			</div>
</c:when>
<c:otherwise>
<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">全局管理</a> &gt; 修改页面</div></div>
			<div class="col-content">
				<div class="col-main">
					 <div class="main">
					 <div class="alert alert-waring">
                     <i></i>
                     <div class="txt">
                        <h4>权限不足，请联系管理员。</h4>
                     </div>
					 </div>			
                     </div>
                </div>
            </div>
</c:otherwise>
</c:choose>				
	</body>
</html>