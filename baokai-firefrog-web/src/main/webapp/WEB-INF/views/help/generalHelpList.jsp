<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<head>
	<title>帮助中心 文章列表</title>
</head>
<body>
	<div class="clearfix">
		<div class="help-container help-list-container">
		
				<div class="help-list-title">   
				<spring:message htmlEscape="true" javaScriptEscape="true" text="${cate2Name}"></spring:message>
				</div>
				
				<ul class="help-list-normal clearfix">
					<c:forEach items="${helps}" var="help" varStatus="status">
						<li>
							<p class="help-list-name"><span class="help-list-num">${status.count}</span><a href="${currentContextPath}/help/queryGeneralDetail?helpId=${help.id}">${help.title }</a>
								<c:if test="${help.isRec == 1 }">
									<span class="help-list-hot"></span>
								</c:if>
							</p>
							<p class="help-list-text">
								${help.preface}... <a href="${currentContextPath}/help/queryGeneralDetail?helpId=${help.id}" class="more">[详情]</a>
							</p>
						</li>
					</c:forEach>	
				</ul>
				<form id="queryForm" action="${currentContextPath}/help/queryGeneralHelp?cate2Name=${cate2Name}&cateId2=${query.cateId2}&orderBy=no" method="post">
					<!-- 分页标签start  -->					
					<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
					<!-- 分页标签end  -->
				</form>

	  </div>
	</div>
	<script type="text/javascript">
	function doPre(){
		var currentPageNo = $("#pageNo").val();
		$("#pageNo").val(parseInt(currentPageNo)-1);
		$("#queryForm").submit();
	}
	
	function doNext(){
		var currentPageNo = $("#pageNo").val();
		$("#pageNo").val(parseInt(currentPageNo)+1);
		$("#queryForm").submit();
	}
	
	function doForward(index){
        if(index == -1){
        	var reg = /^[0-9]+$/;
        	if(reg.test($("#forwardPage").val())){
    		$("#pageNo").val(parseInt($("#forwardPage").val()));}
        	else{
        		return;
        	}
        }else{ 
        	$("#pageNo").val(index);
        } 
		$("#queryForm").submit();
	}
	
	function doCurrent(pageNo){
		$("#pageNo").val(pageNo);
		$("#queryForm").submit();
	}
	</script>
</body>