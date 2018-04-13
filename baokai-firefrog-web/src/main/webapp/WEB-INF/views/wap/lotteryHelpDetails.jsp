<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<title>帮助中心 彩种详情页</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.cookie.js"></script>
	<link href="${staticFileContextPath}/static/images/common/base.css" rel="stylesheet">
	<link href="${staticFileContextPath}/static/images/help/wap_help.css" rel="stylesheet">
</head>
<body>
	<div class="clearfix">

		<div class="help-container help-article-container">
			<input type="hidden" id='loginUserId' name='loginUserId' value="${currUser.id}">
			<input type="hidden" id='loginUserName' name='loginUserName' value="${currUser.userName}">
			<input type="hidden" id='helpId' name='helpId' value="${help.id}">
			
			<div class="help-lottery-info clearfix">
				<div class="help-lotter-info-left">
					<span class="help-lotter-info-name">${help.title}</span>
				</div>
				<div class="help-lotter-info-right">
					${help.preface}
				</div>
			</div>
			
			<c:forEach items="${help.lotteryContentStruc}" var="lottery" varStatus="status">
				<div class="help-lotter-text clearfix">
					<div class="help-lotter-text-title"><a name="${status.count}" style="text-decoration:none">${fn:replace(lottery.name, "<br>", "")}<%-- <spring:message htmlEscape="true" javaScriptEscape="true" text="${fn:replace(lottery.name, "<br>", "")}"> </spring:message>--%></a></div>
					<div class="help-lotter-content">
						${lottery.content}
					</div>
				</div>
			</c:forEach>	
	  </div>
	</div>
	<script type="text/javascript">
        $(document).ready(function(){
            //没有cookie，认为是新的浏览记录
            if($.cookie("viewUserId"+$("#loginUserId").val()+"|"+$("#helpId").val()) == null){
                $.cookie("viewUserId"+$("#loginUserId").val()+"|"+$("#helpId").val(),new Date().getTime(),{expires:30});
                var updateurl= baseUrl +"/helpAdmin/updateBrowsenum"
                $.ajax({
                    type: "post",
                    url: updateurl,
                    data : "id="+$("#helpId").val()+"&browsenum=1&num="+Math.random(),
                    success: function(data,textStatus){
                    },
                    error: function(xhr,status,errMsg){
                    }
                });
            }
            $('.help-main').addClass('help-main-half');
        });
	</script>
</body>
</html>