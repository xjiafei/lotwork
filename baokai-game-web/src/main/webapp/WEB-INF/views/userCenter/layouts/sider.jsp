<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core"%>

		<div class="g_5">
			<div class="common-side">
				<div class="nav-side">
					<dl class="side-bet">
						<dt class="title">投注管理</dt>
						<dd><a href="${currentContextPath}/gameUserCenter/queryOrdersEnter?time=7">投注记录</a></dd>
						<dd><a href="${currentContextPath}/gameUserCenter/queryPlans?time=7">追号记录</a></dd>
						<dd><a href="${userContextPath}/bet/fuddetail">账户明细</a></dd>
					</dl>
					<dl class="side-safe">
						<dt class="title">账户安全</dt>
						<dd><a href="${userContextPath}/safepersonal/safecenter">安全中心</a></dd>
						<dd><a href="${userContextPath}/bindcard">银行卡管理</a></dd>
						<dd><a href="${userContextPath}/safepersonal/personalinfo">个人资料</a></dd>
						<dd><a href="${userContextPath}/safepersonal/safecodeedit">密码管理</a></dd>
						<dd><a href="${userContextPath}/applycenter/querybonusdetails/">奖金详情</a></dd>
					</dl>
					<dl class="side-service">
						<dt class="title">服务</dt>
						<dd><a href="${userContextPath}/Service2/inbox"  style="text-decoration:none;">站内信&nbsp;<span class="side-message-num" id="radiuscount" >&nbsp;&nbsp;<span id="noreadmsg">0</span>&nbsp;&nbsp;</span></a></dd>
						<dd><a href="${userContextPath}/Service/shownoticetask">通知设置</a></dd>
						<!-- <dd><a href="${userContextPath}/fundappeal/appealrechargelist">催到账<span class="side-message-num" style="display: none;margin-left:3px">&nbsp;&nbsp;<span id="fundAppealNoticeCount">0</span>&nbsp;&nbsp;</span></a></dd> -->
					</dl>
					<t:if test="${userlvl!=-1&&isAllZero!=0}">
					<dl class="side-proxy">
					
						<dt class="title">代理中心</dt>
						
						<dd><a href="${userContextPath}/proxy/index">代理首页</a></dd>
						<dd><a href="${userContextPath}/applycenter/index">开户中心</a></dd>
						<dd><a href="${userContextPath}/proxy/cusmag">客户管理</a></dd>
						
						<dd><a href="${currentContextPath}/gameUserCenter/queryCurrentUserReport">报表查询</a></dd>
					
					</dl>
						</t:if>
					<dl class="side-funds">
						<dt class="title">资金</dt>
						<dd><a href="${userContextPath}/fund">我要充值</a></dd>
						<dd><a href="${userContextPath}/withdraw">我要提现</a></dd>
						<dd><a href="${userContextPath}/transfer">我要转账</a></dd>			
					</dl>
				</div>
			</div>
		</div>
        <%-- tool bar 整合 <script type="text/javascript" src="${staticFileContextPath}/js/userCenter/left.js"></script> --%>
        
