<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>公告详情</title>
</head>
	<div class="">
		<div class="common-article">
			<div class="title">网站公告</div>
			 <c:choose>
	   			 <c:when test="${notice.noticelevel == '0'}">
	    			<div class="title"><div ><strong>当前位置：</strong><a href="${currentContextPath}/ad/noticeList?noticeLevel=0">网站公告</a> &gt; 重要通知</div></div>
				</c:when>
				<c:when test="${notice.noticelevel == '1'}">
					<div class="title"><div ><strong>当前位置：</strong><a href="${currentContextPath}/ad/noticeList?noticeLevel=1">网站公告</a> &gt; 派奖信息</div></div>
				</c:when>
				<c:when test="${notice.noticelevel == '2'}">
					<div class="title"><div ><strong>当前位置：</strong><a href="${currentContextPath}/ad/noticeList?noticeLevel=2">网站公告</a> &gt; 平台维护</div></div>
	    		</c:when>
				<c:when test="${notice.noticelevel == '3'}">
					<div class="title"><div ><strong>当前位置：</strong><a href="${currentContextPath}/ad/noticeList?noticeLevel=3">网站公告</a> &gt; 平台活动</div></div>
				</c:when>
			</c:choose>	
			<div class="content announcement">
				<h3>${notice.title }</h3>
				<div class="time">${notice.gmtEffectBeginStr}</div>
				<div class="announcement-bd">
					<p>${notice.content }</p>
					<a id="J-button-totop" href="javascript:void(0);" class="back">回顶部<i></i></a>
				</div>
			</div>
		</div>
	</div>
	<script>
(function($){
	
	$('#J-button-totop').click(function(){
		$(window).scrollTop(0);
	});
	
})(jQuery);
</script>