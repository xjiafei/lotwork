<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<title>帮助中心 搜索结果</title>
	<%String path = request.getContextPath(); %>
		<form id="queryForm" action="${currentContextPath}/help/queryListByKey" method="post">  
			<input id="pageNo" type="hidden" name="pageNo" value="${page.pageNo}">
			<input id="pageSize" type="hidden" name="pageSize" value="${page.pageSize}">
			<div class="help-search">
				<input id="text1" type="text" class="input w-8" <c:if test="${empty key}">value="请输入关键字"</c:if> <c:if test="${!empty key}">value=${key}</c:if> name="key"/>
				<input class="help-search-button" type="submit" value="  " />
				<c:forEach items="${keywordList}" var="keywordList">
					<a href="${currentContextPath}/help/queryListByKey?key=${keywordList.keyword}">${keywordList.keyword}</a>
				</c:forEach>
			</div>
		</form>
			
			<div class="help-search-content">
				<div class="help-search-info">
				共搜索到"${page.totalCount}"条关于“<span class="help-search-highlight">${key}</span>”的帮助信息，以下是搜索结果：
				<c:if test="${page.totalCount eq 0}" >
					<br>
	        		  暂时没有搜索到“<span class="help-search-highlight">${key}</span>”相关帮助，请尝试更换关键字。
				</c:if>
				</div>
				<ul class="help-search-list">
					<c:forEach items="${helpList}" var="helpList">
					<li>
						<c:if test="${helpList.type==0}">
						<p class="article-title"><a href="${currentContextPath}/help/queryGeneralDetail?helpId=${helpList.id}">${helpList.title}</a></p>
						<p class="article-text">${helpList.preface}<a href="${currentContextPath}/help/queryGeneralDetail?helpId=${helpList.id}">[详情]</a></p>
						</c:if>
						<c:if test="${helpList.type==1}">
						<p class="article-title"><a href="${currentContextPath}/help/queryLotteryDetail?helpId=${helpList.id}">${helpList.title}</a></p>
						<p class="article-text">${helpList.preface}<a href="${currentContextPath}/help/queryLotteryDetail?helpId=${helpList.id}">[详情]</a></p>
						</c:if>
					</li>
					</c:forEach>
				</ul>
				<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>	
			</div>
			
			
			
<script language=javascript>
	$('.help-main').addClass('help-main-half');
	$("#text1").focus(function(){
		$("#text1").val("");
	});
	$("[type='submit']").click(function(){ 
	   if($.trim($("#text1").val())=="请输入关键字" || $.trim($("#text1").val()) =="")
	   { 
	       return false;
	   }; 
	 });

	
	function doPre(){
		var currentPageNo = $("#pageNo").val();
		$("#pageNo").val(currentPageNo-1);
		$("#queryForm").submit();
	}

	function doNext(){
		var currentPageNo = $("#pageNo").val();
		$("#pageNo").val(Number(currentPageNo)+1);
		$("#queryForm").submit();
	}

	function doForward(){
		$("#pageNo").val($("#forwardPage").val());
		$("#queryForm").submit();
	}

	function doCurrent(pageNo){
		$("#pageNo").val(pageNo);
		$("#queryForm").submit();
	}	
	
</script>