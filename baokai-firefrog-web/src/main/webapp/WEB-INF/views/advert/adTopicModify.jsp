<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>修改专题</title>
</head>

<body>
			<div class="col-crumbs">
                <div class="crumbs">
                    <strong>当前位置：</strong>
                    <a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a> &gt;
                    <a href="${currentContextPath}/adAdmin/goTopicManager">专题管理（发布）</a> &gt; 修改专题
                </div>
            </div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
					<form action="${currentContextPath}/adAdmin/modifyTopic" id="J-form" method="post">
					<input type="hidden" id="linkNum" value="${linkNum}">
					<input type="hidden" name="id" value="${topic.id}">
						<ul class="ui-form">
							<li>
								<label class="ui-label">专题标题：</label>
								<input id="J-input-title" type="text" class="input" value="${topic.title}" name="title"/>  <span class="ui-prompt">(限40个字)</span>
								<span class="ui-check"><i></i></span>
							</li>
							<li class="ui-combination">
								<label class="ui-label">专题类别：</label>
								<select id="J-select-type" class="ui-select" name="cateId">
									<option value="-1">请选择</option>
									<c:forEach items="${cateList}" var="cateList" varStatus="status">
										<option value="${cateList.id}"  <c:if test="${cateList.id==topic.cateId}">selected</c:if>  >${cateList.name}</option>
									</c:forEach>
									<option value="-2">新建类别</option>
								</select>
								<input id="J-type-text" type="text" class="input" value="" name="cateName" style="display:none;" />
								<span class="ui-check"><i></i></span>
							</li>
							<li>
								<label class="ui-label" for="name">填写链接：</label>
								<input type="text" class="input" value="${url0}" name="link0">
								<span class="ui-check"><i></i></span>
								<a id="J-button-link-add" class="ui-text-info" href="javascript:void(0)">新增</a>
							</li>
							<li class="ui-btn">
								<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">确 定<b class="btn-inner"></b></a>
							</li>
						</ul>
					</form>
					</div>
				</div>
			</div>
<script type="text/javascript" src="${staticFileContextPath}/static/js/advert/adTopicModify.js"></script>			
<script>
<c:forEach items="${topic.urls}" var="url" varStatus="status">
	<c:if test="${status.index>0}">
	var par = $('#J-button-link-add').parent();
	var txt="<li class='ui-other'><input name='<#=name#>' type='text' class='input' value='${url}' /><span class='ui-check'><i></i></span><a class='ui-text-info row-delete' href='javascript:void(0)'>&nbsp;删除</a></li>";
	$(txt.replace(/<#=name#>/g, 'link' + ${status.index})).insertAfter(par);
	</c:if>
</c:forEach>
</script>			
</body>