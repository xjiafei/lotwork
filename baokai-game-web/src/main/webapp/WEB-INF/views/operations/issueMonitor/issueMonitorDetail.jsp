<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title><spring:message code="gameCenter_jiangqiliuchengguanli" />-<spring:message code="gameCenter_liuchengjiankong" />-<spring:message code="gameCenter_xiangqing" />页（异常处理）</title>
	
</head>
<body>
	<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">游戏中心</a>&gt;<a href="#"><spring:message code="gameCenter_jiangqiliuchengguanli" /></a>&gt;<spring:message code="gameCenter_liuchengjiankong" /><spring:message code="gameCenter_xiangqing" />页（异常处理）</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<input type="hidden" id="lotteryId" value="${lotteryId }" />
						<input type="hidden" id="issueCode" value="${issueCode }" />
						<input type="hidden" id="url" value="${currentContextPath }" />
						<h3 class="ui-title"><a href="${currentContextPath}/gameoa/queryOrderListByIssueCode?lotteryId=${lotteryId }&issueCode=${issueCode}" class="more">去查看本期方案记录>></a></h3>
						<c:if test="${lotteryId==99602 || lotteryId==99603}">
						<h3 class="ui-title"><a href="${currentContextPath}/gameoa/querySlipListByIssueCode?lotteryId=${lotteryId }&issueCode=${issueCode}" class="more">去查看本期方案细单记录>></a></h3>
						</c:if>
						<ul class="ui-form">
							<li>
								<label class="ui-label w-3 big"><spring:message code="gameCenter_caizhongmingcheng" />：</label>
								<span class="ui-singleline">${detail.lotteryName }</span>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_qihao" />：</label>
								<span class="ui-singleline">${detail.webIssueCode }</span>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_kaijianghaoma" />：</label>
								<span class="ui-singleline">${detail.numberRecord }</span>
								<c:if test="${lotteryId==99401}">
									<a id="J-button-export" href="javascript:void(0);">方案下载<b class="btn-inner"></b></a>
									<form action="<%=request.getContextPath() %>/gameoa/downLoadGameOrders" method="post" id="J-downLoad-form">
										<input type="hidden" id="lotteryId" name="lotteryId" value="${lotteryId}"/>
										<input type="hidden" id="webIssueCode" name="webIssueCode" value="${issueCode}"/>
										<input type="hidden" id="canUpdate" value="${canUpdate}"/>
										<input type="hidden" id="saleEndTime" value="${saleEndTime}"/>
									</form>
								</c:if>
							</li>
							<li>
								<label class="ui-label w-3 big"><spring:message code="gameCenter_yichangqingkuangmiaoshu" />：</label>
								<span class="ui-singleline">${detail.warnTypeStr}</span> 	
							</li>
							
						</ul>
						<h3 class="ui-title" style="background:#EEE;"><a href="${currentContextPath}/gameoa/queryLotteryIssueWarnLog" class="more">去查看全部异常流程记录>></a>异常流程处理</h3>
						<ul class="ui-form" id="J-panel-container">
							<li>
								<label class="ui-label w-3 ">异常描述：</label>
								<span class="ui-singleline">${detail.warnTypeStr}</span> 																
							</li>
							<li>
								<label class="ui-label w-3 ">处理建议：</label>
								<span>${detail.suggestTypeStr }</span>
							</li>
							<li>
								<label class="ui-label w-3 ">建议操作项：</label>
								<!-- <span class="ico-tab" id="J-button-addtime"><span class="ico-question-text" style="display:none;">帮助说明1</span><i class="icon-question">?</i>输入官方实际开奖时间</span> -->
								<permission:hasRight moduleName="GAME_ISSUEMGR_MONITOR_SETACTUALNUM">
								<c:if test="${!empty detail.numberRecord && detail.isCanCanel==0 && detail.lotteryName!='江苏骰宝'}">
								<span class="ico-tab" id="J-button-addnumber"><span class="ico-question-text" style="display:none;">由于管理员疏忽或号源异常等原因造成第一次开奖号码错误，需要重新录入时的操作。系统将扣回由于开奖号码错误而误发的奖金，并根据新录入的号码重新进行计奖派奖</span><i class="icon-question">?</i>输入官方实际开奖号码</span>
							    </c:if>
							    </permission:hasRight>
							    <permission:hasRight moduleName="GAME_ISSUEMGR_MONITOR_PAUSE">
								<c:if test="${gameIssue.status<6 && detail.isAfterSaleEndTime == 1 && gameIssue.pasuseStatus != 0 }">
								<span class="ico-tab" id="J-button-putoff"><span class="ico-question-text" style="display:none;">人工发现流程的任何异常情况（如开奖号码错误、官方提前开奖等），希望将暂缓派奖或者对已派奖金冻结，待检查并处理异常后再做派奖或解冻奖金操作。此时可使用该功能实现。</span><i class="icon-question">?</i>暂缓派奖</span>
								</c:if>
								</permission:hasRight>
								<permission:hasRight moduleName="GAME_ISSUEMGR_MONITOR_CONTINUE">
								<c:if test="${gameIssue.pasuseStatus == 0}">
								<span class="ico-tab" id="J-button-puton"><span class="ico-question-text" style="display:none;">当系统检查出方案比较发生问题时，或者人工发现异常进行暂缓操作后，系统将暂时不做派奖操作。待人工判断可以继续派奖时，选择人工继续，让派奖行为继续执行。</span><i class="icon-question">?</i>继续派奖</span>
								</c:if>
								</permission:hasRight>
							</li>
							<li>
								<label class="ui-label w-3 "><spring:message code="gameCenter_qitacaozuoxiang" />：</label>
								<permission:hasRight moduleName="GAME_ISSUEMGR_MONITOR_REVOKEISSUE">
								<c:if test="${detail.isCanCanel==0 }">
								<span class="ico-tab" id="J-button-cancel"><span class="ico-question-text" style="display:none;">由于官方未开奖，或者方案比较发现问题涉及范围广、性质严重时，需要将当期的所有方案全部撤销（包括返点、奖金扣除）的操作。</span><i class="icon-question">?</i><spring:message code="gameCenter_chexiaobenqifangan" /></span>
								</c:if>
								</permission:hasRight>
								<permission:hasRight moduleName="GAME_ISSUEMGR_MONITOR_REVOKEPLAN">
								<c:if test="${gameIssue.lotteryId != 99701 }">
								<span class="ico-tab" id="J-button-cancel-more"><span class="ico-question-text" style="display:none;">"由于<spring:message code="gameCenter_jiangqiguizetiaozheng" />，造成用户提前追的奖期不存在等原因，需要撤销所有用户，在一段尚未开始的时间内的所有追号单。
Eg.开奖周期由5分钟调整为10分钟，一天的期数变少，用户已购买的追号单不存在了需要撤销"</span><i class="icon-question">?</i><spring:message code="gameCenter_chexiaohouxuzhuihao"/></span>
								</c:if>
								</permission:hasRight>
								<permission:hasRight moduleName="GAME_ISSUEMGR_MONITOR_SETACTUALNUM">
								<c:if test="${detail.isAfterSaleEndTime == 1 && empty detail.numberRecord && detail.lotteryName!='江苏骰宝'}">								
									<span class="ico-tab" id="J-button-addnumber-self"><span class="ico-question-text" style="display:none;">开奖号码正常情况下全部由号源系统提供，若出现号源系统无法正常提供号源的情况，平台需要自行开奖。在此处输入开奖号码后，系统将根据该开奖号码开奖。</span><i class="icon-question">?</i>输入开奖号码</span>								
								</c:if>
								</permission:hasRight>
								
								
								<a href="javascript:void(0);" param="${currentContextPath}" class="btn btn-primary" id="reTry">失败任务重做</a>
							</li>
						</ul>
						
						<div style="height:400px;"></div>
					</div>
				</div>
			</div>
		</div>
	<script id="J-tpl-noaudit" type="text/template">
审核备注：
<div><textarea style="width:400px;height:80px;"></textarea></div>
</script>

<script id="J-tpl-addtime" type="text/template">
	<ul class="ui-form ui-form-small">
		<li>
			<label for="" class="ui-label">${detail.lotteryName }：</label>
			<span class="ui-singleline">${detail.webIssueCode }期</span>
		</li>
		<li>
			<label for="" class="ui-label">平台销售截止时间：</label>
			<span class="ui-singleline">2012-08-09  00:05:50</span>
		</li>
		<li>
			<label for="" class="ui-label">输入官方实际开奖时间：</label>
			<input id="J-input-date" type="text" value="" class="input"> <span class="color-red">*</span>
		</li>
		<li>
			<label for="" class="ui-label"><spring:message code="gameCenter_yichangchulibeizhu" />：</label>
			<input id="J-input-text" type="text" value="" class="input">
		</li>
	</ul>
</script>


<script id="J-tpl-addnumber" type="text/template">
	<ul class="ui-form ui-form-small">
		<li>
			<label for="" class="ui-label">${detail.lotteryName }：</label>
			<span class="ui-singleline">${detail.webIssueCode }期</span>
		</li>
		<li>
			<label for="" class="ui-label">已开号码：</label>
			<span class="ui-singleline">${detail.numberRecord }</span>
		</li>
		<li>
			<label for="" class="ui-label"><spring:message code="gameCenter_shuruguanfangshijikaijianghaoma" />：</label>
			<input id="J-input-number1" type="text" value="" class="input"> <span class="color-red">*</span>
		</li>
		<li>
			<label for="" class="ui-label"><spring:message code="gameCenter_yichangchulibeizhu" />：</label>
			<input id="J-input-memo1" type="text" value="" class="input">
		</li>
</br>
		<li style="text-align:left;color:#999;">
			<spring:message code="gameCenter_kaijianghaomabiaozhungeshiru" />：</br>
			<span id="J-standard-number1">【时时彩/P5】: 12345</br>
【11选5】:01,02,03,04,05（用空格分开）</br>
【北京快乐8】:01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20（用空格分开）</br>
【双色球】：01,02,03,04,05,06+07</br>
【3D】:123</br>
【六合彩】:01,02,03,04,05,06,45</span>
		</li>
	</ul>
</script>


<script id="J-tpl-putoff" type="text/template">
	<ul class="ui-form ui-form-small">
		<li>
			<label for="" class="ui-label">${detail.lotteryName }：</label>
			<span class="ui-singleline">${detail.webIssueCode }期</span>
		</li>
		<li>
			<label for="" class="ui-label">&nbsp;</label>
			<span class="ui-singleline">暂缓派奖</span>
		</li>
		<li>
			<label for="" class="ui-label"><spring:message code="gameCenter_yichangchulibeizhu" />：</label>
			<input id="J-input-memo2" type="text" value="" class="input">
		</li>
	</ul>
</script>


<script id="J-tpl-puton" type="text/template">
	<ul class="ui-form ui-form-small">
		<li>
			<label for="" class="ui-label">${detail.lotteryName }：</label>
			<span class="ui-singleline">${detail.webIssueCode }期</span>
		</li>
		<li>
			<label for="" class="ui-label">&nbsp;</label>
			<span class="ui-singleline">继续派奖</span>
		</li>
		<li>
			<label for="" class="ui-label"><spring:message code="gameCenter_yichangchulibeizhu" />：</label>
			<input id="J-input-memo3" type="text" value="" class="input">
		</li>
	</ul>
</script>


<script id="J-tpl-cancel" type="text/template">
	<ul class="ui-form ui-form-small">
		<li>
			<label for="" class="ui-label">${detail.lotteryName }：</label>
			<span class="ui-singleline">${detail.webIssueCode }期</span>
		</li>
		<li>
			<label for="" class="ui-label">&nbsp;</label>
			<span class="ui-singleline">批量<spring:message code="gameCenter_chexiaobenqifangan" /></span>
		</li>
		<li>
			<label for="" class="ui-label"><spring:message code="gameCenter_yichangchulibeizhu" />：</label>
			<input id="J-input-memo4" type="text" value="" class="input">
		</li>
	</ul>
</script>



<script id="J-tpl-cancel-more" type="text/template">
	<ul class="ui-form ui-form-small">
		<li>
			<label for="" class="ui-label">${detail.lotteryName }：</label>
			<span class="ui-singleline">${detail.webIssueCode }</span>
		</li>
		<li>
			<label for="" class="ui-label">&nbsp;</label>
			<span class="ui-singleline"><spring:message code="gameCenter_piliangchexiaohouxuzhuihao" /></span>
		</li>
		<li>
			<label for="" class="ui-label"><spring:message code="gameCenter_kaishiqihao" />：</label>
			<span class="ui-singleline"><input id="J-input-number-start" type="text" value="" class="input"> <span class="color-red">*</span></span>
		</li>
		<li>
			<label for="" class="ui-label"><spring:message code="gameCenter_zhongzhiqihao" />：</label>
			<span class="ui-singleline"><input id="J-input-number-end" type="text" value="" class="input"> <span class="color-red">
		</li>
		<li>
			<label for="" class="ui-label"><spring:message code="gameCenter_yichangchulibeizhu" />：</label>
			<input id="J-input-memo5" type="text" value="" class="input">
		</li>
		<li style="color:#999;">
			<spring:message code="gameCenter_beizhuinfo" />
		</li>
	</ul>
</script>



<script id="J-tpl-addnumber-self" type="text/template">
	<ul class="ui-form ui-form-small">
		<li>
			<label for="" class="ui-label">${detail.lotteryName }：</label>
			<span class="ui-singleline">${detail.webIssueCode }期</span>
		</li>
		<li>
			<label for="" class="ui-label">输入开奖号码：</label>
			<input id="J-input-number" type="text" value="" class="input"> <span class="color-red">*</span>
		</li>
		<li>
			<label for="" class="ui-label"><spring:message code="gameCenter_yichangchulibeizhu" />：</label>
			<input id="J-input-memo" type="text" value="" class="input">
		</li>
</br>
		<li style="text-align:left;color:#999;">
						<spring:message code="gameCenter_kaijianghaomabiaozhungeshiru" />：</br>
			<span id="J-standard-number1">【时时彩/P5】: 12345</br>
【11选5】:01,02,03,04,05（用逗号（英文）分开）</br>
【北京快乐8】:01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20（用空格分开）</br>
【双色球】：01,02,03,04,05,06+07</br>
【3D】:123</br>
【六合彩】:01,02,03,04,05,06,45</span>
		</li>
	</ul>
</script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/issueMonitor/issueMonitorDetail.js"></script>
<style>
	.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
	.j-ui-tip .sj {display:none;}
	
	.ui-form-window li {margin-top:5px;margin-bottom:5px;}
	
	.pop-window-datepicker {z-index:710;}
	</style>
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>	
</body>