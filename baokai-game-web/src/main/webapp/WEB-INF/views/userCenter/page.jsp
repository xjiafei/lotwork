<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>分页</title>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/dateUtil.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/page.js">></script>	
<script type="text/javascript">
	var baseUrl = '${staticFileContextPath}';
</script>
<script type="text/javascript">
	
</script>
</head>
<div class="page-wrapper">
						<span class="page-text"> <span class="lower">共${page.totalCount}条记录</span>
						</span>

						<div class="page page-right">
							<c:if test="${page.pageNo!=1}">
								<a href="#" onclick="doPre()" class="prev">上一步</a>
							</c:if>

							<c:if test="${page.totalPages<=10}">
								<c:forEach begin="1" end="${page.totalPages}" var="i">
									<a href="#" onclick="doCurrent(${i})"
										<c:if test="${page.pageNo==i}">class="current"</c:if>>${i}</a>
								</c:forEach>
							</c:if>

							<c:if test="${page.totalPages>10 && page.pageNo>=5}">
								<c:forEach begin="${page.pageNo-4}" end="${page.pageNo+4}"
									var="i">
									<c:if test="${i<= page.totalPages}">
									<a href="#" onclick="doCurrent(${i})"
										<c:if test="${page.pageNo==i}">class="current"</c:if>>${i}</a>
									</c:if>	
								</c:forEach>
								<a href="javascript:void(0)"
									onclick="doForward(${page.pageNo+5})">...</a>
							</c:if>

							<c:if test="${page.totalPages>10 && page.pageNo<5}">
								<c:forEach begin="1" end="10" var="i">
									<a href="#" onclick="doCurrent(${i})"
										<c:if test="${page.pageNo==i}">class="current"</c:if>>${i}</a>
								</c:forEach>
								<a href="javascript:void(0)" onclick="doForward(11)">.1.</a>
							</c:if>
							<c:if test="${page.pageNo!=page.totalPages}">
								<a href="#" onclick="doNext()" class="next">下一步</a>
							</c:if>
							<span class="page-few">到第 <input id="forwardPage"
								type="text" value="" class="input"> /${page.totalPages}页
							</span> <input type="button" value="确 认" class="page-btn"
								onclick="doForward(-1)">
						</div>
					</div>


</html>