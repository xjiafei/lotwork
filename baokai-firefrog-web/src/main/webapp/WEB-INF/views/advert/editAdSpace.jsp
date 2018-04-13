<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改广告位</title>
<script type="text/javascript" src="${staticFileContextPath}/static/js/advert/adSpace.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/ajaxfileupload.js"></script>
</head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div class="col-content">
		<div class="col-main">
			<div class="col-crumbs">
                <div class="crumbs">
                    <strong>当前位置：</strong>
                    <a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a> &gt;
                    <a href="${currentContextPath}/adAdmin/queryAllAdSpace">广告管理</a> &gt; <c:if test="${!isEdit}">新建广告位</c:if><c:if test="${isEdit}">修改广告位</c:if>
                </div>
            </div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form id="J-form-upload" name="form1" enctype="multipart/form-data" method="post" action="${currentContextPath }/adAdmin/uploadSpaceImg" target="check_file_frame">
							<input name="tempFile" type="file" id="J-upload-file" class="upload-file-button" size="40" hidefocus="true" value="导入" style="display:none;" />
						</form>
						<form id="J-form-upload-placeholder" name="form2" enctype="multipart/form-data" method="post" action="${currentContextPath }/adAdmin/uploadSpacePlaceHolderImg" target="check_file_frame_placeholder" style="display:inline;">
							<input name="tempFilePlaceHolder" type="file" id="J-upload-file-placeholder" class="upload-file-button" size="40" hidefocus="true" value="导入"  style="display:none;"/>
							<input id="imgWidth" name="imgWidth" type="hidden" value="">
							<input id="imgHeigh" name="imgHeigh" type="hidden" value="">
						</form>
						<form action="${currentContextPath}/adAdmin/saveEditAdSpace" id="J-form">
						<input type="hidden" id="J-input-id" value="${adSpaceStruc.id }"/>
						<input type="hidden" id="J-input-page-id" value="${adSpaceStruc.pageId }"/>
						<input type="hidden" id="J-input-param-id" value="${adSpaceStruc.paramId }" />
						<input type="hidden" id="J-input-img-id" value="${adSpaceStruc.dftImg }" />
						
						<input type="hidden" id="J-input-check-urlTarget" value="${adSpaceStruc.urlTarget }" />
						<input type="hidden" id="J-input-check-isDftUsed" value="${adSpaceStruc.isDftUsed }" />
						<input type="hidden" id="urlViewPic" value="${urlViewPic }" />
						<ul class="ui-form">
							<li class="checkbox-list">
								<label class="ui-label">选择页面：</label>
								<input id="J-input-page-title" type="text" class="input input-disable" value="${adSpaceStruc.pageIdStr }" disabled="disabled" />
								<c:if test="${!isEdit}">
								<a id="J-button-select-page" href="javascript:void(0)" class="ui-info">点击选择</a>
								<span class="ui-check"><i></i>请选择一个页面</span>
								</c:if>
							</li>
							<li>
								<label class="ui-label">广告位置名称：</label>
								<input id="J-input-title" type="text" class="input" value="${adSpaceStruc.name }" />  <span class="ui-prompt">(限10个字)</span>
								<span class="ui-check"><i></i>广告位名称不能为空</span>
							</li>
							<li>
								<label class="ui-label">广告位参数：</label>
								<input id="J-input-params-title" type="text" class="input input-disable" value="${adSpaceStruc.paramName }" disabled="disabled" />
								<c:if test="${!isEdit}">
								<a id="J-button-select-params" href="javascript:void(0)" class="ui-info">点击选择</a>
								<span class="ui-check"><i></i>请选择一个广告参数</span>
								</c:if>
							</li>
							
							<li class="pic-size">
								<label class="ui-label" for="name">广告位尺寸：</label>
								<span class="ui-singleline">宽 <input id="J-input-img-w" type="text" class="input input-disable" readonly value="${adSpaceStruc.width }" style="text-align:center;"> 像素</span>
								<span class="ui-singleline">高 <input id="J-input-img-h" type="text" class="input"  value="${adSpaceStruc.height }" style="text-align:center;"> 像素</span>
								<span class="ui-check"><i></i>广告位高度不能为空</span>
							</li>
							
								
							<li id="J-panel-upload">
								<label class="ui-label">上传图片：</label>
								<a class="btn" href="javascript:void(0);" onClick="document.getElementById('J-form-upload').tempFile.click();">上传图片<b class="btn-inner"></b></a>
								<span class="ui-prompt">支持格式：JPG、GIF、PNG，不可超过100k</span>
								<span id="J-msg-upload-tip" class="ui-check">广告图片不能为空</span>
							</li>
							<li style="margin:0;">
								<div style="margin-left:150px;">
									<a href="#" onclick="showImgBlank(this)" target='_blank'>
									<img id="pic1" src="${urlViewPic }${adSpaceStruc.dftImg}"  width="100"  alt="" />
									</a>
								</div>
								<iframe src="" style="margin-left:150px;height:0px;" name="check_file_frame" frameborder="0"></iframe>
							</li>
							
							<li>
								<label class="ui-label" for="name">链接打开方式：</label>
								<span class="radio-list">
									<label class="label" for="J-radio-target-blank"><input name="radioTarget" id="J-radio-target-blank" type="radio" class="radio" value="1" checked="checked" />新窗口</label>
									<label class="label" for="J-radio-target-self"><input name="radioTarget" id="J-radio-target-self" type="radio" class="radio" value="2" />原窗口</label>
								</span>
							</li>
							
							<li style="margin-bottom:0;">
								<label class="ui-label" for="name">占位图：</label>
								<span class="radio-list" id="J-panel-radio-position">
									<label class="label" for="J-radio-position-open"><input name="radioPosition" id="J-radio-position-open" type="radio" class="radio" value="1" />开启</label>
									<label class="label" for="J-radio-position-close"><input name="radioPosition" id="J-radio-position-close" type="radio" class="radio" value="0" checked="checked" />禁用</label>
								</span>
							</li>
							
							<li style="display: none;" id="J-input-placeholder-li">
							<c:forEach var='imgItem' items="${dftImgs}">
								<input type ='text' name='J-input-placeholder-img' value='${imgItem}'>
							</c:forEach>
							
							</li>
							
							
							
							<li>
								<label class="ui-label" for="name">位置状态：</label>
								<span class="ui-singleline">开启</span>
							</li>
							<li class="ui-btn">
								<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">确 定<b class="btn-inner"></b></a>
							</li>
						</ul>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
<script type="text/template" id="J-tpl-selectpages">
<div style="height:200px;overflow-y:scroll" id="J-panel-pages">
<table class="table">
<tbody>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-1" class="label"><input name="radio-page" id="radio-page-1" type="radio" class="checkbox" value="1"><span class="pageTitle">平台首页</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-2" class="label"><input name="radio-page" id="radio-page-2" type="radio" class="checkbox" value="2"><span class="pageTitle">平台顶部</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-3" class="label"><input name="radio-page" id="radio-page-3" type="radio" class="checkbox" value="3"><span class="pageTitle">平台底部</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-4" class="label"><input name="radio-page" id="radio-page-4" type="radio" class="checkbox" value="4"><span class="pageTitle">注册页</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-5" class="label"><input name="radio-page" id="radio-page-5" type="radio" class="checkbox" value="5"><span class="pageTitle">登录页 </span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-6" class="label"><input name="radio-page" id="radio-page-6" type="radio" class="checkbox" value="6"><span class="pageTitle">重庆时时彩</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-7" class="label"><input name="radio-page" id="radio-page-7" type="radio" class="checkbox" value="7"><span class="pageTitle">江西时时彩</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-8" class="label"><input name="radio-page" id="radio-page-8" type="radio" class="checkbox" value="8"><span class="pageTitle">新疆时时彩</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-9" class="label"><input name="radio-page" id="radio-page-9" type="radio" class="checkbox" value="9"><span class="pageTitle">天津时时彩</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-10" class="label"><input name="radio-page" id="radio-page-10" type="radio" class="checkbox" value="10"><span class="pageTitle">黑龙江时时彩</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-11" class="label"><input name="radio-page" id="radio-page-11" type="radio" class="checkbox" value="11"><span class="pageTitle">上海时时乐</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-12" class="label"><input name="radio-page" id="radio-page-12" type="radio" class="checkbox" value="12"><span class="pageTitle">乐利时时彩 </span></label></td>
	</tr>

	<tr>
		<td class="table-tool text-left"><label for="radio-page-13" class="label"><input name="radio-page" id="radio-page-13" type="radio" class="checkbox" value="13"><span class="pageTitle">山东11选5</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-14" class="label"><input name="radio-page" id="radio-page-14" type="radio" class="checkbox" value="14"><span class="pageTitle">江西11选5</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-15" class="label"><input name="radio-page" id="radio-page-15" type="radio" class="checkbox" value="15"><span class="pageTitle">广东11选5</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-16" class="label"><input name="radio-page" id="radio-page-16" type="radio" class="checkbox" value="16"><span class="pageTitle">重庆11选5</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-17" class="label"><input name="radio-page" id="radio-page-17" type="radio" class="checkbox" value="17"><span class="pageTitle">乐利11选5</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-18" class="label"><input name="radio-page" id="radio-page-18" type="radio" class="checkbox" value="18"><span class="pageTitle">北京快乐8</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-19" class="label"><input name="radio-page" id="radio-page-19" type="radio" class="checkbox" value="19"><span class="pageTitle">3D</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-20" class="label"><input name="radio-page" id="radio-page-20" type="radio" class="checkbox" value="20"><span class="pageTitle">排列5</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-21" class="label"><input name="radio-page" id="radio-page-21" type="radio" class="checkbox" value="21"><span class="pageTitle">双球</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-22" class="label"><input name="radio-page" id="radio-page-22" type="radio" class="checkbox" value="23"><span class="pageTitle">PT</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-24" class="label"><input name="radio-page" id="radio-page-24" type="radio" class="checkbox" value="24"><span class="pageTitle">安徽快三</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-25" class="label"><input name="radio-page" id="radio-page-25" type="radio" class="checkbox" value="25"><span class="pageTitle">六合彩</span></label></td>
	</tr>
	<tr>
		<td class="table-tool text-left"><label for="radio-page-26" class="label"><input name="radio-page" id="radio-page-26" type="radio" class="checkbox" value="26"><span class="pageTitle">VIP页</span></label></td>
	</tr>
<tr>
	
</tbody>
</table>
</div>
</html>
</script>
<script type="text/template" id="J-tpl-selectparams">
<div style="height:200px;overflow-y:scroll" id="J-panel-params">
<table class="table">
			<table class="table">
				<thead>
					<tr>
						<th></th>
						<th>参数</th>
						<th>尺寸</th>
						<th>位置</th>
						<th>页面</th>
					</tr>
				</thead>
				<tbody>
					<#=list#>
				</tbody>
			</table>
</div>
</script>
<script type="text/template" id="J-tpl-selectparams-list">
</script>
<script>
$(document).ready(function(){
	
})
</script>
</body>
</html>