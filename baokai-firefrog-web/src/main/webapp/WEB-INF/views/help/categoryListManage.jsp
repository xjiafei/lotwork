<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>帮助中心 文章列表</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/help/categoryListManage.js"></script>
	<script type="text/javascript">
	var cate2Name = '${cate2Name}';
	</script>
</head>
<body>	
	<div class="col-content">
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="${currentContextPath}/helpAdmin/goHelpManager">内容中心</a> &gt; <spring:message htmlEscape="true" javaScriptEscape="true" text="${cate2Name}"></spring:message></div></div>
			<div class="col-content">
			
				<div class="col-main">
					<div class="main">
						<div id="J-panel-sort" class="panel-sort">
							<div class="control">
								<a data-action="add" data-id="0"  href="javascript:void(0);" class="btn btn-important">添加一级类目<b class="btn-inner"></b></a>
							</div>
							
							<c:forEach items="${cateStrucs}" var="cateStruc" varStatus="status">
								<div class="item clearfix">
								<span class="item-name item-first-name">${cateStruc.name}</span>
								<span data-action="edit" data-level="${cateStruc.level}" data-id="${cateStruc.id}" data-sortname='<spring:message htmlEscape="true" javaScriptEscape="true" text="${cateStruc.name}"></spring:message>' class="item-rename">重命名</span>
								<span data-action="del" data-id="${cateStruc.id}" data-sortname='${cateStruc.name}' class="item-del">删除</span>
								<span data-action="up"  data-id="${cateStruc.id}" class="item-up" data-number="${cateStruc.number}">向上</span>
								<span data-action="down" data-id="${cateStruc.id}" class="item-down" data-number="${cateStruc.number}">向下</span>
								<a data-action="add" data-id="${cateStruc.id}"  href="javascript:void(0);" class="btn btn-addchild">添加子类目<b class="btn-inner"></b></a>
								<c:forEach items="${cateStruc.subCate}" var="subCate" varStatus="subStatus">
									<div class="item-child">
										<span class="item-name">${subCate.name}</span>
										<span data-action="edit" data-level="${subCate.level}" data-id="${subCate.id}" data-sortname='${subCate.name}' class="item-rename">重命名</span>
										<span data-action="del" data-id="${subCate.id}" data-sortname='${subCate.name}' class="item-del">删除</span>
										<span data-action="up" data-id="${subCate.id}" class="item-up" data-number="${subCate.number}">向上</span>
										<span data-action="down" data-id="${subCate.id}" class="item-down" data-number="${subCate.number}">向下</span>
									</div>
								</c:forEach>
								</div>
							</c:forEach>
						</div>	
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	var parentId = "${parentId}";
	$('.btn-addchild').each(function(){
		var id = $(this).attr("data-id");
		if(id == parentId){
			$(this).parent().find('.item-first-name').addClass("item-name-open");
			$(this).parent().find('.item-first-name').siblings('.item-child').show();
		}
	});
	</script>
</body>

</html>