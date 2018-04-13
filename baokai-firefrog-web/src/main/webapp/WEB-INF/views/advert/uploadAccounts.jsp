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
<title>临时功能-导入名单</title>
</head>
<body>
	<div class="col-crumbs">
		<div class="crumbs">
			<strong>当前位置：</strong>临时功能&gt;导入名单
		</div>
	</div>
	 
	<div class="col-content">
		<div class="col-main">
			<div class="main">
				<div class="ui-tab">
					<div class="ui-tab-content ui-tab-content-current">
						<form action="${currentContextPath}/adAdmin/uploadFiles" id="J-form-1" method="post" enctype="multipart/form-data" >
							<ul class="ui-search">
								<li><label for="" class="ui-label">调研问卷中奖名单：</label> 
								<input type="file" name="file" value=""/>
								</li>
								<li><a id="button-submit" class="btn btn-important" href="javascript:void(0);">上传<b class="btn-inner"></b></a></li>
							</ul>					
						</form>						
					</div>
				</div>
			</div>
		</div>
	</div>
	<input  type=hidden id="uploadResult" value="${uploadResult}">
	
<script>

$("#button-submit").click(function(){
	$('#J-form-1').submit();
});

if($("#uploadResult").val() != ""){
	alert($("#uploadResult").val());
}

</script>	 
</body>


