<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
			<dl class="nav">
				<permission:hasRight moduleName="MARKET_ADVERT">
					<dt>广告</dt>
				</permission:hasRight>
				
					<c:if test="${cate2Name == 'goPublish' }">
					<dd id="d1" style="display:none;" class="current"><a href="${currentContextPath}/adAdmin/queryPublishAdPage?isAll=false">广告管理（发布）</a></dd>
					 <permission:hasRight moduleName="MARKET_ADVERTMANAGERPUB"> <script>$('#d1').css("display","block") </script>  </permission:hasRight>
					</c:if>
				
				
					<c:if test="${cate2Name != 'goPublish' }">
					<dd id="d1" style="display:none;"><a href="${currentContextPath}/adAdmin/queryPublishAdPage?isAll=false">广告管理（发布）</a></dd>
					<permission:hasRight moduleName="MARKET_ADVERTMANAGERPUB"> <script>$('#d1').css("display","block") </script>  </permission:hasRight>
					</c:if>
				
				
					<c:if test="${cate2Name == 'goReview' }">
					<dd id="d2" style="display:none;" class="current"><a href="${currentContextPath}/adAdmin/queryReviewAdPage?isAll=false">广告管理（审核）</a></dd>
					<permission:hasRight moduleName="MARKET_ADVERTMANAGERAUDIT"> <script>$('#d2').css("display","block") </script>  </permission:hasRight>
					</c:if>
				
				
					<c:if test="${cate2Name != 'goReview' }">
					<dd id="d2" style="display:none;"><a href="${currentContextPath}/adAdmin/queryReviewAdPage?isAll=false">广告管理（审核）</a></dd>
					<permission:hasRight moduleName="MARKET_ADVERTMANAGERAUDIT"> <script>$('#d2').css("display","block") </script> </permission:hasRight>
					</c:if>
				
				
					<dd id="d3" style="display:none;" ><a href="${currentContextPath}/adAdmin/queryAllAdSpace">广告位管理</a></dd>
					<permission:hasRight moduleName="MARKET_ADSPACEMANAGER"> <script>$('#d3').css("display","block") </script> </permission:hasRight>
				
				<permission:hasRight moduleName="MARKET_NOTICE">
					<dt>公告</dt>
				</permission:hasRight>
				
					<dd id="d4"  style="display:none;" <c:if test="${cate2Name == 'goNoticeListPublish'}">class="current" </c:if>><a href="${currentContextPath}/adAdmin/goNoticeListPublish">公告管理（发布）</a></dd>
					<permission:hasRight moduleName="MARKET_NOTICEANAGERPUB"><script>$('#d4').css("display","block") </script>
					</permission:hasRight>
				
					<dd id="d5" style="display:none;" <c:if test="${cate2Name == 'goNoticeListAudit'}">class="current" </c:if>><a href="${currentContextPath}/adAdmin/goNoticeListAudit">公告管理（审核）</a></dd>
				<permission:hasRight moduleName="MARKET_NOTICEANAGERAUDIT"><script>$('#d5').css("display","block") </script>
				</permission:hasRight>
				
					
					<dd id="d6" style="display:none;" <c:if test="${cate2Name == 'goCreateNotice'}">class="current" </c:if>><a href="${currentContextPath}/adAdmin/goCreateNotice">新建公告</a></dd>

				<permission:hasRight moduleName="MARKET_ADDNOTICE"><c:if test="${fn:contains(aclList,'MARKET_NORMALNOTICE') || fn:contains(aclList,'MARKET_URGENCYNOTICE')}"><script>$('#d6').css("display","block") </script></c:if>
				</permission:hasRight>
				<permission:hasRight moduleName="MARKET_SUBJECT">
					<dt>专题</dt>
				</permission:hasRight>
				
					<dd id="d7" style="display:none;" <c:if test="${cate2Name == 'goTopicManager' || cate2Name == 'goModifyTopic'}">class="current" </c:if>><a href="${currentContextPath}/adAdmin/goTopicManager">专题管理（发布）</a></dd>
				<permission:hasRight moduleName="MARKET_SUBJECTMANAGER"><script>$('#d7').css("display","block") </script>
				</permission:hasRight>
				
					<dd id="d8" style="display:none;" <c:if test="${cate2Name == 'goTopicView'}">class="current" </c:if>><a href="${currentContextPath}/adAdmin/goTopicView">专题管理（查看）</a></dd>
				<permission:hasRight moduleName="MARKET_SUBJECTMANAGER"><script>$('#d8').css("display","block") </script>
				</permission:hasRight>
				
					<dd id="d9" style="display:none;" <c:if test="${cate2Name == 'goCreateTopic'}">class="current" </c:if>><a href="${currentContextPath}/adAdmin/goCreateTopic">新建专题</a></dd>
				<permission:hasRight moduleName="MARKET_ADDSUBJECT"><script>$('#d9').css("display","block") </script>
				</permission:hasRight>
				
				<permission:hasRight moduleName="CHANNEL_DEPLOY"><dt>渠道配置</dt></permission:hasRight>	
				<permission:hasRight moduleName="CHANNEL_CONFIG">
				<dd><a href="${currentContextPath}/channel/toSaveChannelTemplate">渠道参数设定</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="CHANNEL_SELECT">
				<dd><a href="${currentContextPath}/channel/channelView">渠道参数查询</a></dd>
				</permission:hasRight>
				
				
				<permission:hasRight moduleName="MARKET_ACTIVITY">
					<dt>活动</dt>
				</permission:hasRight>
				
					<dd id="d10" style="display:none;"><a href="${currentContextPath}/adAdmin/queryActivityHongBaoLog">红包明细查询</a></dd>
					<permission:hasRight moduleName="MARKET_ACTIVITY_HONGBAO"><script>$('#d10').css("display","block") </script>
				</permission:hasRight>
				
					<dd id="d11" style="display:none;"><a href="${currentContextPath}/adAdmin/queryActivityChouJianUserLog">抽奖用户查询</a></dd>
					<permission:hasRight moduleName="MARKET_ACTIVITY_CHONGJIANG"><script>$('#d11').css("display","block") </script>
				</permission:hasRight>
				
					<dd id="d12" style="display:none;"><a href="${currentContextPath}/adAdmin/queryActivityChouJianLog">中奖结果查询</a></dd>
					<permission:hasRight moduleName="MARKET_ACTIVITY_ZHONGJIANG"><script>$('#d12').css("display","block") </script>
				</permission:hasRight>
				
					<dd id="d13" style="display:none;"><a href="${currentContextPath}/adAdmin/queryActivityConfig">活动配置</a></dd>
					<permission:hasRight moduleName="MARKET_ACTIVITY_CONFIG"><script>$('#d13').css("display","block") </script>
				</permission:hasRight>
				<permission:hasRight moduleName="MARKET_ACTIVITY_ACTIVITYSHEEP"><dt>春节抽奖活动</dt></permission:hasRight>
				<dd id="d14" style="display:none;"><a href="${currentContextPath}/adAdmin/queryActivityHongBaoLogzl">活动总览</a></dd>
				<permission:hasRight moduleName="MARKET_ACTIVITY_QUERYACTIVITYSHEEPZL"><script>$('#d14').css("display","block") </script></permission:hasRight>
				<dd id="d15" style="display:none;"><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao">红包活动数据</a></dd>
				<permission:hasRight moduleName="MARKET_ACTIVITY_QUERYACTIVITYSHEEPHONGBAO"><script>$('#d15').css("display","block") </script></permission:hasRight>
				<dd id="d16" style="display:none;"><a href="${currentContextPath}/adAdmin/queryActivityYaDaXiaoLog">押大小活动数据</a></dd>
				<permission:hasRight moduleName="MARKET_ACTIVITY_QUERYACTIVITYYADAXIAO"><script>$('#d16').css("display","block") </script></permission:hasRight>
				<dd id="d17" style="display:none;"><a href="${currentContextPath}/adAdmin/queryActivityZpLog">转盘抽奖活动数据</a></dd>
				<permission:hasRight moduleName="MARKET_ACTIVITY_QUERYACTIVITYZP"><script>$('#d17').css("display","block") </script></permission:hasRight>
				<dd id="d18" style="display:none;"><a href="${currentContextPath}/adAdmin/queryActivitySheepConfig?activityId=4">活动配置</a></dd>
				<permission:hasRight moduleName="MARKET_ACTIVITY_QUERYACTIVITYCONFIG"><script>$('#d18').css("display","block") </script></permission:hasRight>
				<dd id="d19" style="display:none;"><a href="${currentContextPath}/adAdmin/queryActivityOperateLog">抽奖日志</a></dd>
				<permission:hasRight moduleName="MARKET_ACTIVITY_QUERYACTIVITYOPERATORLOG"><script>$('#d19').css("display","block") </script></permission:hasRight>

				<!-- 新手活動 -->				
				<permission:hasRight moduleName="BEGIN_MISSION">
					<dt id="d20">新手活动</dt>
				</permission:hasRight>	
				<permission:hasRight moduleName="BEGIN_BANK_CARD_CHECK">
					<dd id="d21" "><a href="${currentContextPath}/begin/toBindCardAppeal">绑卡奖励审核</a></dd>
				</permission:hasRight>	
				<permission:hasRight moduleName="BEGIN_NEW_MISSION">
					<dd id="d22" "><a href="${currentContextPath}/begin/toNewMission">新手任务</a></dd>
				</permission:hasRight>	
				<permission:hasRight moduleName="BEGIN_DAY_MISSION">
					<dd id="d23" "><a href="${currentContextPath}/begin/toDailyMission">日常任务</a></dd>
				</permission:hasRight>	
				<permission:hasRight moduleName="BEGIN_LOTTERY_SET">
					<dd id="d24" "><a href="${currentContextPath}/begin/toLotterySet">抽奖设置</a></dd>
				</permission:hasRight>	
				<permission:hasRight moduleName="BEGIN_QUESTION">
					<dd id="d25" "><a href="${currentContextPath}/begin/toNewQuestion">题目录入</a></dd>
				</permission:hasRight>	
				<permission:hasRight moduleName="BEGIN_DATA_REPORT">
					<dd id="d26" "><a href="${currentContextPath}/begin/toDataCount">数据报表</a></dd>
				</permission:hasRight>	
				
				
				<dt id="d27">临时功能</dt>
				<dd id="d28" "><a href="${currentContextPath}/adAdmin/medals">金牌录入</a></dd>
				<dd id="d28" "><a href="${currentContextPath}/adAdmin/uploadAccounts">导入名单</a></dd>
			</dl>			
