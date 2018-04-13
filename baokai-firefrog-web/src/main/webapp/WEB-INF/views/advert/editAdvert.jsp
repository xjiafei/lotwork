<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" class="html-content">
<head>
	<meta charset="UTF-8">
	<title>修改广告</title>

	<script type="text/javascript" src="${staticFileContextPath}/static/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/advert/editAdvert.js"></script>
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
                    <a href="${currentContextPath}/adAdmin/queryPublishAdPage?isAll=false">广告管理（发布）</a> &gt; 修改广告
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
									<label for="J-checkbox-all" class="label">
									<c:if test="${adStruc.rcAll==1 }">
									<input id="J-checkbox-all" name="rcAll" type="checkbox" value="1" class="checkbox" checked="checked">
									</c:if>
									<c:if test="${adStruc.rcAll!=1 }">
									<input id="J-checkbox-all" name="rcAll" type="checkbox" value="1" class="checkbox">
									</c:if>
									全部站内用户</label>
									<label for="J-usergroup-13" class="label">
									<c:if test="${adStruc.rcGuest==1 }">
									<input type="checkbox" name="rcGuest" value="13" class="checkbox" id="J-usergroup-13" checked="checked">
									</c:if>
									<c:if test="${adStruc.rcGuest!=1 }">
									<input type="checkbox" name="rcGuest" value="13" class="checkbox" id="J-usergroup-13">
									</c:if>
									游客</label>
								</span>
							</li>
							<li class="J-panel-group">
								<label for="" class="ui-label"></label>
								<span class="checkbox-list">
									<label for="J-usergroup-2" class="label">
									<c:if test="${adStruc.rcTopAgent==1 }">
									<input type="checkbox" name="rcTopAgent" value="2" class="checkbox" id="J-usergroup-2" checked="checked">
									</c:if>
									<c:if test="${adStruc.rcTopAgent!=1 }">
									<input type="checkbox" name="rcTopAgent" value="2" class="checkbox" id="J-usergroup-2">
									</c:if>
									总代</label>
									<label for="J-usergroup-3" class="label">
									<c:if test="${adStruc.rcOtAgent==1 }">
									<input type="checkbox" name="rcOtAgent" value="3" class="checkbox" id="J-usergroup-3" checked="checked">
									</c:if>
									<c:if test="${adStruc.rcOtAgent!=1 }">
									<input type="checkbox" name="rcOtAgent" value="3" class="checkbox" id="J-usergroup-3">
									</c:if>
									其他代理</label>
									<label for="J-usergroup-10" class="label">
									<c:if test="${adStruc.rcCustomer==1 }">
									<input type="checkbox" name="rcCustomer" value="10" class="checkbox" id="J-usergroup-10" checked="checked">
									</c:if>
									<c:if test="${adStruc.rcCustomer!=1 }">
									<input type="checkbox" name="rcCustomer" value="10" class="checkbox" id="J-usergroup-10">
									</c:if>
									玩家</label>
								</span>
							</li>
							<li class="J-panel-group">
								<label for="" class="ui-label"></label>
								<span class="checkbox-list">
									<label for="J-usergroup-11" class="label">
									<c:if test="${adStruc.rcVip==1 }">
									<input type="checkbox" name="rcVip" value="11" class="checkbox" id="J-usergroup-11" checked="checked">
									</c:if>
									<c:if test="${adStruc.rcVip!=1 }">
									<input type="checkbox" name="rcVip" value="11" class="checkbox" id="J-usergroup-11">
									</c:if>
									VIP</label>
									<label for="J-usergroup-12" class="label">
									<c:if test="${adStruc.rcNonVip==1 }">
									<input type="checkbox" name="rcNonVip" value="12" class="checkbox" id="J-usergroup-12" checked="checked">
									</c:if>
									<c:if test="${adStruc.rcNonVip!=1 }">
									<input type="checkbox" name="rcNonVip" value="12" class="checkbox" id="J-usergroup-12" >
									</c:if>
									非VIP</label>
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
												<td class="table-tool text-left"><label class="label">
												<input name="spaceCheckBox" type="checkbox" class="checkbox" value="${space.id }">${space.pageIdStr }(${space.name }-${space.width }*${space.height })</label></td>
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
								<input id="J-input-title" type="text" class="input" value="${adStruc.name }" name="name"/> <span class="ui-prompt">(限20个字)</span>
								<span class="ui-check"><i></i>广告名称不能为空</span>
							</li>
							<li class="pic-size">
								<label class="ui-label" for="name">图片尺寸：</label>
								<input type="hidden" id="J-input-img-src" name="imgUrl" value="${adStruc.imgUrl }" />
								<input type="hidden" id="id" name="id" value="${adStruc.id }">
								<input type="hidden" id="selectSpaces" value="${selectSpaces }">
								<span class="ui-singleline">宽 <input id="J-input-img-w" name type="text" class="input input-disable" value="${spaceStruc.width }" disabled="disabled" style="text-align:center;"> 像素</span>
								<span class="ui-singleline">高 <input id="J-input-img-h" type="text" class="input input-disable" value="${spaceStruc.height }" disabled="disabled" style="text-align:center;"> 像素</span>
							</li>
							<li id="J-panel-upload">
								<label class="ui-label">上传图片：</label>
								<a class="btn" href="javascript:void(0);" onClick="document.getElementById('J-form-upload').tempFile.click();">上传图片<b class="btn-inner"></b></a>
								<span class="ui-prompt">支持格式：JPG、GIF、PNG，不可超过100k</span>
								<span id="J-msg-upload-tip" class="ui-check"><i></i>广告图片不能为空</span>
							</li>
							<li style="margin:0;">
								<div id="view" style="margin-left:150px;height:30px;" frameborder="0">
								<img src="${imageServerVisit }${adStruc.imgUrl}"  width="40" alt="" />
								</div>
								<iframe src="" style="margin-left:150px;height:40px;" name="check_file_frame" frameborder="0"></iframe>
								
							</li>

							<li>
								<label class="ui-label" for="name">链接地址：</label>
								<input id="J-input-link" type="text" class="input" value="${adStruc.targetUrl }" name="targetUrl">
								<span class="ui-prompt">链接地址为空则无链接</span>
							</li>
							<li class="time">
								<label class="ui-label" for="name">有效期：</label>
								<input id="J-time-start" type="text" class="input" value="${begin }" name="gmtEffectBegin"> - <input id="J-time-end" type="text" class="input" value="${end }" name="gmtEffectEnd">
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
			<script type="text/javascript" src="${staticFileContextPath}/static/js/advert/editAdvert.js"></script>
</body>
</html>