<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
		<div class="col-side">
			<dl class="nav">
				<permission:hasRight moduleName="FUND_DEMO">
				<dt>充值相关</dt>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_SENDSITEMSG">
				<dd <c:if test="${cate2Name == 'goCreateMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=sv1&swithval=1">异常充值处理</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_SYSMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goSysMsgManager'}">class="current" </c:if>><a href="${currentContextPath}/fundAdmin/Rechargemange/index?parma=bypass">充值相关配置</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">申诉相关配置</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">唯一附言管理</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">充值申诉处理</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_EMAIL">
				<dt>提现相关</dt>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">风险提现处理</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">提现相关配置</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">可提现额度白名单</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">提现申诉处理</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_EMAIL">
				<dt>人工资金操作</dt>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">人工资金操作审核流程</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">人工批量操作</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_EMAIL">
				<dt>银行卡相关</dt>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">银行管理</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">银行卡管理</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">用户银行卡绑定管理</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">银行卡黑名单管理</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_EMAIL">
				<dt>统计报表</dt>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">充值明细表</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">提现明细表</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">充提报表(总代)</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">游戏币明细(总代)</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">账户明细表</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">总代盈亏报表</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_EMAIL">
				<dt>转账相关</dt>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">转账明细</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="NOTICE_USERMSGMANAGER">
				<dd <c:if test="${cate2Name == 'goUserMsg'}">class="current" </c:if>><a href="${currentContextPath}/admin/Rechargemange/index?parma=abtf">转账限额配置</a></dd>
				</permission:hasRight>
			</dl>
		</div>