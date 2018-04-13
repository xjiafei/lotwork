<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>帮助中心 彩种详情页</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.cookie.js"></script>
</head>
<body>
	<div class="clearfix">

		<div class="help-container help-article-container">
			<input type="hidden" id='loginUserId' name='loginUserId' value="${currUser.id}">
			<input type="hidden" id='loginUserName' name='loginUserName' value="${currUser.userName}">
			<input type="hidden" id='helpId' name='helpId' value="${help.id}">
			<div class="help-sort-title">
				<a href="javascript:void(0)">${cate2Name}</a></div>
			
			<div class="help-lottery-info clearfix">
				<div class="help-lotter-info-left">
						<img src="${staticFileContextPath}/dynamic/${help.lotteryLogo}" style="width:128px; height:80px" />
						<span class="help-lotter-info-name">${help.title}</span>
				</div>
				<div class="help-lotter-info-right">
					${help.preface}
				</div>
			</div>

			<div class="help-lottery-control clearfix">
				<ul>
					<c:forEach items="${help.lotteryContentStruc}" var="lottery" varStatus="status">
						<li>
							<!--<a href="#${status.count}">
								${lottery.name}
							</a>-->
                            <a href="javascript:void(0);">
								${lottery.name}
							</a>
						</li>
					</c:forEach>
				</ul>
				<a class="btn btn-important" target="_blank" href="${frontGame}${help.lotteryLink}">现在购买<b class="btn-inner"></b></a>
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