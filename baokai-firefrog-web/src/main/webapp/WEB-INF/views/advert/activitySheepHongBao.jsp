<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>羊年红包</title>
	<style>
	.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
	.j-ui-tip .sj {display:none;}
	</style>
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	
	</script>
	
</head>
<body>
	
		<div class="col-main">
			<!-- 从被装饰页面获取body标签内容 -->
			
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="#">活动</a>>中奖结果查询
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li ><a href="${currentContextPath}/adAdmin/queryActivityHongBaoLogzl">活动总览</a></li>
									<li class="current"><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao">红包活动数据</a></li>	
                                    <li ><a href="${currentContextPath}/adAdmin/queryActivityYaDaXiaoLog">押大小活动数据</a></li>                            
									<li ><a href="${currentContextPath}/adAdmin/queryActivityZpLog">转盘抽奖活动数据</a></li>
									<li ><a href="${currentContextPath}/adAdmin/queryActivitySheepConfig?activityId=4">活动配置</a></li>
									<li ><a href="${currentContextPath}/adAdmin/queryActivityOperateLog">操作日志</a></li>
								</ul>
							</div>
							<div class="clearfix">
							
							<ul class="ui-form">
									<li>
										<label class="ui-label w-1">&nbsp;</label>
										<span class="ico-tab ico-tab-item ico-tab-current"><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao">全部</a></span>
										<span class="ico-tab ico-tab-item"><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao?pageStatus=1">待审核(${unCheckNum})</a></span>
										<span class="ico-tab ico-tab-item"><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao?pageStatus=2">已通过</a></span>
										<span class="ico-tab ico-tab-item"><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao?pageStatus=3">未通过</a></span>
									</li>
								</ul>
							
							
						<form action="queryActivitySheepHongBao" method="post" id="J-search-form">
						<ul class="ui-search" id="J-search-panel">
							<li>
								<label for="" class="ui-label">用户名：</label>
								<input type="text" id="userName" name="userName" value="${userName}" class="input w-2">
							</li>
							
							<li class="time">
								<label for="" class="ui-label">报名时间：</label>
								<input id="timestart" name="timestart" type="text" value="${timestart}" class="input"> - <input id="timeend" type="text" name="timeend" value="${timeend}" class="input">
							</li>
							<li>
							<input type="hidden" id="statusBK"  name ="statusBK" value="${status}"/>									
								<label for="" class="ui-label">状态：</label>
								<select id="status" style="width:90px;" name="status" class="ui-select">
									<option value="-1">全部</option>
									<option value="1">未开始</option>	
									<option value="2">可申领</option>	
									<option value="5">进行中</option>
									<option value="3">已放弃</option>									
									<option value="6">已达标</option>		
									<option value="7">待审核</option>	
									<option value="8">审核通过</option>
									<option value="9">审核不通过</option>	
									<option value="10">已过期</option>																				
								</select>
							</li>
                            <li>
                            <input type="hidden" id="channelBK"  name ="channelBK" value="${channel}"/>	
								<label for="" class="ui-label">来源：</label>
								<select id="channel" style="width:90px;" name="channel" class="ui-select">	
									<option value="-1">全部</option>
									<option value="1">ios2.1</option>
									<option value="2">android2.1</option>
									<option value="3">web3.0</option>
									<option value="4">web4.0</option>								
								</select>
							</li>
							
							<li>
							<input type="hidden" id="updateStatusBK"  name ="updateStatusBK" value="${updateStatus}"/>	
											<label class="ui-label">操作状态：</label>
											<select id="updateStatus" class="ui-select" name="updateStatus">
												<option value="-1" selected="">全部</option>
												<option value="1">待发布</option>
											</select>
										</li>
							<li>
								<input id="btsubmit" class="btn btn-primary" type="submit" value="搜 索">
								<a id="J-download-submit" class="btn btn-important" href="${fromGame}/gameoa/exportActivitySheepHongBao?pageStatus=0">导出报表<b class="btn-inner"></b></a>
								
							</li>
						</ul>
						
						<table class="table table-info" id="J-table">
							<thead>
								<tr>
									<th>报名时间</th>
											<th>用户名</th>
											<th>红包金额</th>
											<th>红包类型</th>
											<th>目标投注金额</th>
											<th>累计投注金额</th>
											<th>来源</th>
											<th>状态</th>
											<th>操作</th>
								</tr>
							</thead>
							<tbody>
						
								<c:if test="${struc!=null}">
								<c:forEach items="${struc}" var="struc" varStatus="configIndex">
								<tr data-id="${struc.id}" data-name="${struc.userName}" data-amount="${struc.allAward}">	
								
								
									<td><span type="hidden" data-id="${struc.id}"></span>
									<span type="hidden" data-username="${struc.userName}"></span>
									<span type="hidden" data-amount="${struc.allAward/10000}"></span>
							
									<span type="hidden" data-actionuser="${struc.updateName}"></span>
									<span type="hidden" data-reason="${struc.updateReason}"></span>
									<span type="hidden" data-updateamount="${struc.updateAward/10000}"></span>
									<span type="hidden" data-updatedamount="${struc.updateAward/10000 + struc.allAward/10000}"></span>
									
									<fmt:formatDate value="${struc.signTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
									<td>${struc.userName}<c:if test="${struc.vip==1}"><img src="http://static.rfonlineedu.com:888/static/images/ucenter/safe/vip.jpg"></c:if></td>
									<td>${struc.award/10000}</td>
									<td>
									<c:if test="${struc.awardType==1}">第一个红包</c:if>
									<c:if test="${struc.awardType==2}">第二个红包</c:if>
									<c:if test="${struc.awardType==3}">第三个红包</c:if>
									</td>
									<td>${struc.targetAward/10000}</td>
									
									<td  <c:if test="${struc.updateStatus==1}">class="color-red"</c:if>  >
									<fmt:formatNumber value="${struc.allAward/10000 + struc.updateAward/10000 }" pattern="#.##" minFractionDigits="2" />
									
								
									</td>
									<td><c:if test="${struc.channel==1 }">ios2.1</c:if>
												<c:if test="${struc.channel==2 }">android2.1</c:if>
												<c:if test="${struc.channel==3 }">web3.0</c:if>
												<c:if test="${struc.channel==4 }">web4.0</c:if>
									</td>
									<td>
									<c:if test="${struc.status==1}">未开始</c:if>
									<c:if test="${struc.status==2}">可申领</c:if>
									<c:if test="${struc.status==3}">已放弃</c:if>
									<c:if test="${struc.status==5}">进行中</c:if>
									<c:if test="${struc.status==6}">已达标</c:if>
									<c:if test="${struc.status==7}">待审核</c:if>	
									<c:if test="${struc.status==8}">审核通过</c:if>	
									<c:if test="${struc.status==9}">审核不通过</c:if>	
									<c:if test="${struc.status==10}">已过期</c:if>			
									</td>
									 
									<td>
									<a data-detail href="javascript:void(0);">详情</a>
									<c:if test="${struc.updateStatus==null || struc.updateStatus==0}">
									<permission:hasRight moduleName="MARKET_ACTIVITY_UPDATEACTIVITYSHEEPHONGBAO"><a data-action="update" href="javascript:void(0);">更新</a></permission:hasRight>
									</c:if>
									<c:if test="${struc.updateStatus==1}">
									<permission:hasRight moduleName="MARKET_ACTIVITY_PUBLISHACTIVITYSHEEPHONGBAO"><a class="color-red" data-actionid="2" data-actionuser="Panda" data-action="publish" href="javascript:void(0);" data-reason="用户帐变消失，给用户手动补录">发布</a></permission:hasRight>
									</c:if>
									</td>
									
	
								</tr>
								</c:forEach>
                      		</c:if>
					
							</tbody>
						</table>
                        
					<c:if test="${page!=null}">
				        	<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
						
							</c:if>
					</form>		
					</div>
				</div>
			</div>
		</div>
	</div>
    <script type="text/javascript"  src="${staticFileContextPath}/static/js/advert/sheepHongBao.js">

  
	</script>
    
	<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
</body>