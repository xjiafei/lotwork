<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" class="html-content">
<head>
	<meta charset="UTF-8">
	<title>新建广告</title>

	<script type="text/javascript" src="${staticFileContextPath}/static/js/ajaxfileupload.js"></script>
	<style>
	.ui-form .J-panel-group {margin:0;}
	.checkbox-list {border-bottom:1px dotted #CCC;display:inline-block;padding-bottom:10px;}
	.checkbox-list-last {border:0;}
	</style>
</head>
<body>
			<div class="col-crumbs">
                <div class="crumbs">
                    <strong>当前位置：</strong>
                    <a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a> &gt;
                    <a href="${currentContextPath}/adAdmin/queryPublishAdPage?isAll=false">广告管理（发布）</a> &gt; 新建广告
                </div>
            </div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form id="J-form-upload" name="form1" enctype="multipart/form-data" method="post" action="${currentContextPath }/adAdmin/uploadImg" target="check_file_frame">
							<input name="tempFile" type="file" id="J-upload-file" class="upload-file-button" size="40" hidefocus="true" value="导入" style="display:none;" />
						</form>
					
						
						<form action="" id="J-form" method="post">
							
						<ul class="ui-form">
							<li></li>
							<li style="margin:0;" id="ralAllGroup">
								<label for="" class="ui-label">展示用户组：</label>
								<span class="checkbox-list">
									<label for="J-checkbox-all" class="label"><input id="J-checkbox-all" name="rcAll" type="checkbox" value="1" class="checkbox">全部站内用户</label>
									<label for="J-usergroup-13" class="label"><input type="checkbox" name="rcGuest" value="13" class="checkbox" id="J-usergroup-13">游客</label>
								</span>
							</li>
							<li class="J-panel-group">
								<label for="" class="ui-label"></label>
								<span class="checkbox-list">
									<label for="J-usergroup-2" class="label"><input type="checkbox" name="rcTopAgent" value="2" class="checkbox" id="J-usergroup-2">总代</label>
									<label for="J-usergroup-3" class="label"><input type="checkbox" name="rcOtAgent" value="3" class="checkbox" id="J-usergroup-3">其他代理</label>
									<label for="J-usergroup-10" class="label"><input type="checkbox" name="rcCustomer" value="10" class="checkbox" id="J-usergroup-10">玩家</label>
								</span>
							</li>
							<li class="J-panel-group">
								<label for="" class="ui-label"></label>
								<span class="checkbox-list">
									<label for="J-usergroup-11" class="label"><input type="checkbox" name="rcVip" value="11" class="checkbox" id="J-usergroup-11">VIP</label>
									<label for="J-usergroup-12" class="label"><input type="checkbox" name="rcNonVip" value="12" class="checkbox" id="J-usergroup-12">非VIP</label>
								</span>
							</li>
							<li class="J-panel-group">
								<label for="" class="ui-label"></label>
								<span class="checkbox-list checkbox-list-last">
									
								</span>
								<span class="ui-check"><i></i>请选择一个用户组</span>
							</li>
							
							<li>
								<label class="ui-label">广告投放位置：</label>
								<div class="select-position" id="J-panel-ad-position">
									<table class="table table-info table-group ">
										<thead>
											<tr>
												<th>页面/位置/尺寸</th>
												<th>预览</th>
												<th>已绑定</th>
												<th>进行中</th>
												<th>未开始</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${spaces }" var="space" varStatus="status">
											<tr>
												<td class="table-tool text-left"><label class="label"><input type="checkbox" class="checkbox" value="${space.id }">${space.pageIdStr }(${space.name }-${space.width }*${space.height })</label></td>
												<td class="table-tool"><a href="javascript:void(0);" title="预览" class="ico-preview"></a></td>
												<td>${space.allProcess }</td>
												<td>${space.inProcess }</td>
												<td>${space.noProcess }</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
									<span class="ui-check"><i></i>请至少选择一个广告位</span>
								</div>
							</li>
							<li>
								<label class="ui-label">广告名称：</label>
								<input id="J-input-title" type="text" class="input" value="" name="name"/> <span class="ui-prompt">(限20个字)</span>
								<span class="ui-check"><i></i>广告名称不能为空</span>
							</li>
							<li class="pic-size">
								<label class="ui-label" for="name">图片尺寸：</label>
								<input type="hidden" id="J-input-img-src" name="imgUrl" value="" />
								<span class="ui-singleline">宽 <input id="J-input-img-w" name type="text" class="input input-disable" value="" disabled="disabled" style="text-align:center;"> 像素</span>
								<span class="ui-singleline">高 <input id="J-input-img-h" type="text" class="input input-disable" value="" disabled="disabled" style="text-align:center;"> 像素</span>
							</li>
							<li id="J-panel-upload">
								<label class="ui-label">上传预览图：</label>
								<a class="btn" href="javascript:void(0);" onClick="document.getElementById('J-form-upload').tempFile.click();">上传图片<b class="btn-inner"></b></a>
								<span class="ui-prompt">支持格式：JPG、GIF、PNG，不可超过100k</span>
								<span id="J-msg-upload-tip" class="ui-check"><i></i>请上传预览图</span>
							</li>
							<li style="margin:0;">
								<iframe src="" style="margin-left:150px;height:50px;" name="check_file_frame" frameborder="0"></iframe>
							</li>

							<li>
								<label class="ui-label" for="name">链接地址：</label>
								<input id="J-input-link" type="text" class="input" value="" name="targetUrl">
								<span class="ui-prompt">链接地址为空则无链接</span>
                                <span class="ui-check"><i></i>请输入正确的链接地址如：http://www.google.com</span>
							</li>
							<li class="time">
								<label class="ui-label" for="name">有效期：</label>
								<input id="J-time-start" type="text" class="input" value="" name="gmtEffectBegin"> - <input id="J-time-end" type="text" class="input" value="" name="gmtEffectEnd">
								<span class="ui-check"><i></i>请选择广告有效期</span>
							</li>
							<li class="ui-btn">
								<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">提交审核<b class="btn-inner"></b></a>
								<a id="J-button-save" class="btn" href="javascript:void(0);">保 存<b class="btn-inner"></b></a>
							</li>
						</ul>
						</form>
						<input type="hidden" value="${imageServerVisit }" id="imageServerVisit">
						<div style="height:700px"></div>
					</div>
				</div>
			</div>
			<script type="text/javascript" src="${staticFileContextPath}/static/js/advert/addAdvert.js"></script>
</body>
</html>