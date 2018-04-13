<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<title>帮助中心 首页</title>
		<form action="${currentContextPath}/help/queryListByKey" method="post">  
			<input id="pageNo" type="hidden" name="pageNo" value="${page.pageNo}" />
			<input id="pageSize" type="hidden" name="pageSize" value="${page.pageSize}" />
			<div class="help-search">
				<input id="text1" type="text" class="input w-8" value="请输入关键字" name="key"/>
				<input class="help-search-button" type="submit" value="  " />
				<c:forEach items="${keywordList}" var="keywordList">
					<a href="${currentContextPath}/help/queryListByKey?key=${keywordList.keyword}">${keywordList.keyword}</a>
				</c:forEach>
			</div>
		</form>
			
		<div class="help-list">
			<div class="title">平台自助服务</div>
			<ul class="help-quick">
				<li class="help-quick-password"><a href="${currentContextPath}/safepersonal/safecodeedit">修改登录密码</a></li>
                <c:if test="${empty user.withdrawPasswd}">
                    <li class="help-quick-password-safe"><a href="${currentContextPath}/safepersonal/safecodeset">设置安全密码</a></li>
                </c:if>
                <c:if test="${!empty user.withdrawPasswd}">
                    <li class="help-quick-password-safe"><a href="${currentContextPath}/safepersonal/safecodeedit?issafecode=1">修改安全密码</a></li>
                </c:if>
                <c:if test="${empty user.quStruc}">
                    <li class="help-quick-question"><a href="${currentContextPath}/safepersonal/safequestset">设置安全问题</a></li>
                </c:if>
                <c:if test="${!empty user.quStruc}">
                    <li class="help-quick-question"><a href="${currentContextPath}/safepersonal/safequestedit">修改安全问题</a></li>
                </c:if>
                <c:if test="${empty user.email}">
                    <li class="help-quick-email"><a href="${currentContextPath}/safepersonal/bindmail">绑定安全邮箱</a></li>
                </c:if>
                <c:if test="${!empty user.email}">
                    <li class="help-quick-email"><a href="${currentContextPath}/safepersonal/rebindmail">修改安全邮箱</a></li>
                </c:if>
			</ul>
			
			<div class="title">最受关注的问题</div>
			<ul class="help-article-list">
				<c:forEach items="${hotQuestion}" var="hotQuestion">
					<li>&gt; <a target="_blank" href="${currentContextPath}/help/queryGeneralDetail?helpId=${hotQuestion.id}">${hotQuestion.title}</a></li>
				</c:forEach>
			</ul>
		</div>
		
		<div class="help-side">
			<div class="title">热门彩种</div>
			<ul class="game-list">
				<c:forEach items="${hotLottery}" var="hotLottery">
				<li>
					<a href="javascript:void(0)" onclick="toLotteryHelp('${hotLottery.id}')">
						<img src="${urlViewPic}${hotLottery.lotteryLogo}" style="width:128px; height:80px" />
						<span class="game-name">${hotLottery.title}</span>
						<span>${hotLottery.lotteryAdvert}</span>
					</a>
				</li>
				</c:forEach>
			</ul>
		</div>
		
<script language=javascript>
	$("#text1").focus(function(){
		$("#text1").val("");
	});
	$("[type='submit']").click(function(){ 
	     if($.trim($("#text1").val())=="请输入关键字" || $.trim($("#text1").val()) =="")
	     { 
		      return false;
		 }; 
	});

	function toLotteryHelp(id){
		window.location.href = baseUrl + "/help/queryLotteryDetail?helpId=" +id+ "&cate2Name=" + encodeURI(encodeURI('热门彩种'));
	}
</script>
		
