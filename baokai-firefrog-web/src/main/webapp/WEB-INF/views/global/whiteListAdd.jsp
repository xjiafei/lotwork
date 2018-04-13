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
		<script type="text/javascript" src="${staticFileContextPath}/static/js/ajaxfileupload.js"></script>	
		<script>
			var currentPath = "${currentContextPath}";
		</script>
	</head>
	<body>
<c:choose>
<c:when test="${fn:contains(aclList,'GLOBAL_WHITELISTIPADD')}">
		<div class="col-crumbs"><div class="crumbs" ><strong>当前位置：</strong><a href="#">全局管理</a> &gt; 新增指定IP </div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form id="J-form-upload" name="form1" enctype="multipart/form-data" method="post" action="${currentContextPath}/globeAdmin/uploadWhiteListUserFile" style="position:relative;">
							<input name="file" type="file" id="J-upload-file" class="upload-file-button" size="40" hidefocus="true" value="导入" style="display:none;" />
						</form>
						<form id="J-whiteList-form" action="${currentContextPath}/globeAdmin/saveWhiteList" method="post">
							<input type="hidden" id="mode" name="mode" value="${input.mode}" >
							<input type="hidden" id= "ipAddr" name="ipAddr" value="${input.ipAddr}">
							<input type="hidden" id= "msg" name="msg" value="${input.msg}">
							<ul class="ui-form">						
								<li id="J-input-ip">
										<label for="" class="ui-label"><b>+新增IP：</b></label>
										<input type="text" id="classA" name="classA"class="input w-1"  value="${input.classA}" maxlength="3" style="text-align:center;"><b style="font-size: 18px;"> ─</b>
										<input type="text" id="classB" name="classB" class="input w-1"  value="${input.classB}" maxlength="3" style="text-align:center;"><b style="font-size: 18px;"> ─</b>
										<input type="text" id="classC" name="classC" class="input w-1"  value="${input.classC}" maxlength="3" style="text-align:center;"><b style="font-size: 18px;"> ─</b>
										<input type="text" id="classD" name="classD" class="input w-1"  value="${input.classD}" maxlength="3" style="text-align:center;">
										<label for="" class="label w-3" style="color: red;">*IP地址段不能为空</label>
								</li>
								<li id="J-panel-upload">
								<label class="ui-label"><b>用户名：</b></label> 
								<a class="btn" id="import" href="javascript:void(0);" >+ 导入用户名<b class="btn-inner"></b></a>&nbsp;&nbsp;（支持txt/csv格式文件）							
							</li>
								<li><label class="ui-label"></label> 
									<textarea class="ui-textarea w-8" id="userAcuntP" name="userAcuntP" ></textarea>
									<input type="hidden" id= "userAcunt" name="userAcunt" value="${input.userAcunt}">
									<label class="label w-2" style="color: red;" >*用户名不能为空</label> 
								</li>
								<li><label class="ui-label"><b>备注：</b></label> 
									<input id="remarkP" name="remarkP" type="text" maxlength="40" class="input" value="">
									<input type="hidden" id= "remark" name="remark" value="${input.remark}">
									<label class="label w-3" >（最多可输入40个汉字）</label> 
								</li>
								<li><label class="ui-label"></label> 
									<a href="javascript:void(0);" id="J-button-Add"
									class="btn btn-important">提 交<b class="btn-inner"></b></a>
									<a href="javascript:void(0);" id="J-button-cancel"
									class="btn btn-important">取 消<b class="btn-inner"></b></a>
								</li>
							</ul>
						</form>					
					</div>
				</div>
			</div>
</c:when>
<c:otherwise>
<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">全局管理</a> &gt; 新增指定IP</div></div>
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