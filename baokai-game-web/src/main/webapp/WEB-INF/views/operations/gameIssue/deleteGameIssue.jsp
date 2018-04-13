<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<title>奖期规则查看页-奖期删除</title>


</head>
<body>
<div id="tab_menu_id" style="display:none">issueRuleMenu</div>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>><a href="${currentContextPath}/gameoa/gameIssueIndex?lotteryId=99101&ruleId="><spring:message code="gameCenter_jiangqiguize"/></a>>删除奖期
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
							<div class="ui-tab-title clearfix">
								<ul>
									<jsp:include page="../lotteryConfigHeader.jsp"></jsp:include>
								</ul>
							</div>
					
					
						<h3 class="ui-title"><a href="<%=request.getContextPath() %>/gameoa/queryGameIssues?lotteryId=${lotteryId}" >&lt;&lt; 返回奖期列表</a></h3>
						
						<div class="ui-tab">
								
								
								<ul class="ui-form ">
									<li>
										<label for="" class="ui-label w-4"><spring:message code="gameCenter_caizhongmingcheng" />：</label>
										<input type="text" value="${lotteryName}" disabled="disabled" class="input  w-2"/>
										<input type="hidden" id="lotteryId" value="${lotteryId }"/>
										<input type="hidden" id="tomoDay" value="${tomoDay }"/>
									</li>
									<li>
										<label for="" class="ui-label w-4">删除时间段：</label>
										 <input type="text" value="" id="J-date-exception-start"  autocomplete="off" class="input  w-3"  name="times"  /> - <input type="text" id="J-date-exception-end" value="" class="input  w-3"  autocomplete="off"  name="times"  />
									</li>											
									<li class="ui-btn">
										<label for="" class="ui-label w-1"></label>
										<a id="J_Submit_Button_a"  class="btn btn-important">确 认<b class="btn-inner"></b></a>
										<a class="btn" id="j_clean_button_a">取 消<b class="btn-inner"></b></a>
									</li>
								</ul>

								
								<div style="height:800px;"></div>
								
							</div>
						
					</div>
				</div>
			</div>
    <div style="position:absolute;left:100px;margin-top:300px;display:none;" class="pop w-8">
        <div class="hd"><i class="close"></i>温馨提示</div>
        <div class="bd">
            <div class="pop-title">
                <i class="ico-error"></i>
                <h4 class="pop-text">操作失败，请检查网络，并重试</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn">关 闭<b class="btn-inner"></b></a>
            </div>
        </div>
    </div>
    
    	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
    <script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js"></script>	
    <script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gameIssue/deleteGameIssue.js"></script>
</body>