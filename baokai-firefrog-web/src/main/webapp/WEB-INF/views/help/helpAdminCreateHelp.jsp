<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<title>帮助后台 添加普通帮助</title>
	<%String path = request.getContextPath(); %>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/help/helpAdminCreateHelp.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/help/helpCommon.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/jquery/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor-1.2.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor_lang/zh-cn.js"></script>
</head>
<body>	
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">内容中心</a>&gt; 新建帮助</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main panel-help-addcontent">
						<form id="J-form" method="post" action="${currentContextPath}/helpAdmin/createHelp">
							<ul class="ui-form">
								<li>
									<label class="ui-label">所属类目：</label>
									<select id="J-select-type" class="ui-select" name="cateId">
										<option value="">请选择</option>
										<c:forEach items="${cateList}" var="cateList">
											<option value="${cateList.id}" <c:if test="${cateList.id==defaultCateId}">selected</c:if>>${cateList.name}</option>
										</c:forEach>
									</select>
									<select id="J-select-type-2" class="ui-select" name="cateId2">
										<option value="">请选择</option>
									</select>
									<span class="ui-check"><i></i></span>
								</li>
								<li>
									<label class="ui-label">标题：</label>

									<input id="J-title" type="text" value="" class="input w-6" name="title" onblur="checkTitleBlur(this)">
									<span class="ui-check"><i></i></span>
								</li>
								<li class="checkbox-list">
									<label class="ui-label">是否推荐：</label>
									<span class="radio-list">
										<input id="radio-recommend-1" name="isRec" type="radio" value="1" class="radio"><label for="radio-recommend-1" class="label">&nbsp;是</label>
										<input id="radio-recommend-0" name="isRec" type="radio" value="0" class="radio" checked="checked"><label for="radio-recommend-0" class="label">&nbsp;否</label>
									</span>
								</li>
								<li>
									<label class="ui-label">简介：</label>
                                    <div class="textarea w-6"><textarea id="J-info" name="preface"></textarea></div>
                                    <span style="padding-top:65px; margin: 0px 0px 0px 10px;" class="ui-prompt">限100字</span><span class="ui-check" style="padding-top:65px; margin: 0px 0px 0px 10px;"><i></i></span>
								</li>
								<li style="margin-top:0;">
									<label class="ui-label">内容：</label>
									<div style="display:inline-block;">
										<textarea id="J-content" name="content"  rows="12" cols="80" style="width:100%;height:300px;">
										</textarea>
									</div>
									<span class="ui-check"><i></i></span>
								</li>
								<li>
									<label class="ui-label">序号：</label>
									<input id="J-order" name="no" style="text-align:center;" type="text" value="0" class="input w-1"><span style="margin: 0px 0px 0px 10px;" class="ui-prompt">数值越大排序越靠前</span>
									<span class="ui-check"><i></i></span>
								</li>

								<li class="ui-btn">
									<a id="J-button-submit" href="#" class="btn btn-important">确 定<b class="btn-inner"></b></a>
									<a href="javascript:history.back(-1);" class="btn btn-important">返 回<b class="btn-inner"></b></a>
								</li>
								<input type="hidden" id="currentContextPath" name="currentContextPath" value="${currentContextPath}">
							</ul>
						</form>
					</div>
				</div>
				</div>
<script>
	var cates = new Map();
	var cateIds = new Map();
	<c:forEach items="${cateList}" var="cate">
	var cateList${cate.id} = new Array();
	var cateListId${cate.id} = new Array();
	<c:forEach items="${cate.subCate}" var="subCate" varStatus="status">
	cateList${cate.id}[${status.index}] = '${subCate.name}';
	cateListId${cate.id}[${status.index}] = '${subCate.id}';
	</c:forEach>
	cates.put('${cate.id}',cateList${cate.id});
	cateIds.put('${cate.id}',cateListId${cate.id});
	</c:forEach>

if($("#J-select-type").val()>0)
{
	$("#J-select-type-2").empty();
	var index = $("#J-select-type").val();
	var subcates = cates.get(index);
	var idCates = cateIds.get(index);
	var subIds = cates;
	$('#J-select-type-2').append('<option value=-1>请选择</option>');
	for (var i=0; i<subcates.length; i++) {
		$('#J-select-type-2').append('<option value="'+idCates[i]+'">'+subcates[i]+'</option>');
	}
}
	
	
	$('#J-select-type').change(function() {
		$("#J-select-type-2").empty();
		var index = $(this).val();
		var txt=$(this).find("option:selected").text();
		if(txt=='彩种知识')
		{
			window.location.href="${currentContextPath}/helpAdmin/goCreateLottery";
		}
		var subcates = cates.get(index);
		var idCates = cateIds.get(index);
		var subIds = cates;
		$('#J-select-type-2').append('<option value=-1>请选择</option>');
		if(subcates!=null)
		for (var i=0; i<subcates.length; i++) {
			$('#J-select-type-2').append('<option value="'+idCates[i]+'">'+subcates[i]+'</option>');
		}
	});
</script>
</body>
</html>