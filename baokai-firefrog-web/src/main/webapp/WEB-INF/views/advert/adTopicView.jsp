<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>专题管理(查看)</title>
	<style>
		.row-link-hidden {display:none;}
		.ico-close,.ico-open {cursor:pointer;}
	</style>
	
</head>

<body>
			<div class="col-crumbs">
                <div class="crumbs">
                    <strong>当前位置：</strong>
                    <a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a> &gt; 专题管理(查看)
                </div>
            </div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
					<form action="${currentContextPath }/adAdmin/queryTopicList" id="J-form">
						<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
						<input type="hidden" name="queryType" value="2">
						<ul class="ui-search">
							<li>
								<label for="" class="ui-label">专题标题：</label>
								<input type="text" value="${search.title }" name="title" class="input w-3">
							</li>
							<li>
								<label class="ui-label">专题类型：</label>
								<select class="ui-select w-2" name="cateId">
									<option value="-1">请选择</option>
									<c:forEach items="${cateList}" var="cateList" varStatus="status">
										<option value="${cateList.id}" <c:if test="${search.cateId==cateList.id }">selected</c:if>  >${cateList.name}</option>
									</c:forEach>
								</select>
							</li>
							<li><a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">搜 索<b class="btn-inner"></b></a></li>
						</ul>
					</form>
					<c:if test="${empty list}">
						<div class="alert alert-waring">
							<i></i>
							<div class="txt">
								<h4>没有符合条件的记录，请更改查询条件！</h4>
							</div>
						</div>
					</c:if>
					<c:if test="${!empty list}">
						<table class="table table-info table-group" id="J-table">
							<thead>
								<tr>
									<th>编号</th>
									<th>专题标题</th>
									<th>专题类别</th>
									<th>活动链接</th>
									<th>创建时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="list" varStatus="status">
								<tr>
									<td>${list.id}</td>
									<td>${list.title}</td>
									<td>${list.cateName}</td>
									<td class="table-toggle">
										<span class="row-links">
											<c:forEach items="${list.urls}" var="urls" varStatus="status">
												<a href="${urls}" target="_blank"   <c:if test="${status.index>2 }">class="row-link-hidden"</c:if>  >
											${urls}
											</a>  
											<c:if test="${!status.last}">     
												<br <c:if test="${status.index>1}">class="row-link-hidden"</c:if>   />
											</c:if>
											<c:if test="${status.last && status.index>2}"> 
												<i class="ico-close"></i>
											</c:if>
											</c:forEach>
										</span>
									</td>
									<td>${list.gmtCreatedStr}</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>	
					</c:if>
				</div>
			</div>
<script type="text/javascript" src="${staticFileContextPath}/static/js/advert/adTopicManager.js"></script>	
</body>