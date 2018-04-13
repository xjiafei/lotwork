<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>公告详情</title>
</head>
<div class="g_33 common-main">
    <div class="common-article">
   		<div class="title">网站公告</div>
							
		<div class="ui-tab" id="news-tab">
				<div class="ui-tab-title clearfix">
					<ul>
						<li class="${noticelevel == '0'?'current':''}"><a href="${currentContextPath}/ad/noticeList?noticeLevel=0" >重要通知</a></li>
						<li class="${noticelevel == '1'?'current':''}"><a href="${currentContextPath}/ad/noticeList?noticeLevel=1" >派奖信息</a></li>
						<li class="${noticelevel == '2'?'current':''}"><a href="${currentContextPath}/ad/noticeList?noticeLevel=2">平台维护</a></li>
						<li class="${noticelevel == '3'?'current':''}"><a href="${currentContextPath}/ad/noticeList?noticeLevel=3">平台活动</a></li>
						<c:if test="${sessionScope.info.vipLvl>0}">
							<li class="${noticelevel == '4'?'current':''}"><a href="${currentContextPath}/ad/noticeList?noticeLevel=4">VIP资讯</a></li>
						</c:if>
					</ul>
				</div>
		</div>
		<div>
			<div class="content announcement">
					<table class="table table-info">
					<tbody>
					<c:forEach items="${list}" var="list">
					<tr>
						<td class="text-left"><a href="${currentContextPath}/ad/adNoticeQuery?id=${list.id}" target="_blank">${list.title}</a></td>
						<td class="text-right">${list.gmtEffectBeginStr}</td>
					</tr>
					</c:forEach>
					</tbody>
				</table>
				<form action="${currentContextPath}/ad/noticeList" id="J-form">
					<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
					<input type="hidden" name="status" value="${search.status}" id="status">
					<input type="hidden" name="orderBy" value="${search.orderBy}" id="orderBy">
					<input type="hidden" name="noticeLevel" value="${noticelevel}" id="noticeLevel">
				</form>
				<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
			</div>
		</div>
    </div>
</div>
<script type="text/javascript">
    function doPre(){
        var currentPageNo = $("#pageNo").val();
        $("#pageNo").val(parseInt(currentPageNo)-1);
         $("#noticeLevel").val(${noticelevel});
        $("#J-form").submit();
    }

	//var theTab = new phoenix.Tab({
	//		par           : '#news-tab',
	//		triggers      : '.ui-tab-title li',
	//		panels        : '.ui-tab-content .content',
	//		currClass     : 'current',
	//		currPanelClass: 'current',
	//		eventType: 'click'
	//	});
		
    function doNext(){
        var currentPageNo = $("#pageNo").val();
        $("#pageNo").val(parseInt(currentPageNo)+1);
        $("#noticeLevel").val(${noticelevel});
        $("#J-form").submit();
    }

	//theTab.addEvent("onSwitch", function(e, index) {
  	//	 var currentPageNo = $("#pageNo").val();
  	//	  $("#pageNo").val(0);
  	//	  $("#noticelevel").val(index);
  	//	  $("#J-form").submit();
  	//	  console.log('ggg' + index); // Prints "Example of an event"
	//});


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
       $("#noticeLevel").val(${noticelevel});
        $("#J-form").submit();
    }
    function doCurrent(pageNo){
        $("#pageNo").val(pageNo);
         $("#noticeLevel").val(${noticelevel});
        $("#J-form").submit();
    }
</script>