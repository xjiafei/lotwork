<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title><spring:message code="gameCenter_shougongluhao" /></title>
	<style>
		.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
		.j-ui-tip .sj {display:none;}
		
		.ui-form-window li {margin-top:5px;margin-bottom:5px;}
		
		.pop-window-datepicker {z-index:710;}
		
		.search-time a {display:inline-block;border:1px solid #CCC;padding:10px 13px;color:#999;}
		.search-time a:hover { text-decoration:none;}
		.search-time a.current {border-color:#08AE8E;font-weight:bold;color:#08AE8E;}
		.table-info .ball-list{max-width:650px;white-space:normal;}
		.ball-list input{height: 40px;margin: 5px 5px 0 0;text-align: center;font-size:20px;color: #333;}
		.ssq-token{font-size: 40px;display: inline-block;width: 35px;height: 50px;line-height: 42px;margin: 5px 5px 0 0;vertical-align: middle;color:#999;}
		.dialog-content-unlock{line-height: 30px;font-size: 14px;}
		.dialog-content-unlock .code-area{width: 100%;margin: 5px 0;}
		.game-type-name{font-size: 30px;}
		.history-link{text-decoration: underline;font-size:14px;}
	</style>
</head>
<body>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="#">游戏中心</a>><spring:message code="gameCenter_shougongluhao" />
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">	
					<form id="J-ball-data">			
							<input type="hidden" id="systemTime" name="systemTime" value="<fmt:formatDate value='${systemTime}' pattern='yyyy/MM/dd HH:mm:ss'/>"/>
							<input type="hidden" id="time" name="time" value="${time}"/>
							<input type="hidden" id="lotteryId" name="lotteryId" value="${request.lotteryId}"/>	
							<c:if test="${page!=null}">
							<input type="hidden" id="pageCount" name="pageCount" value="${page.pageSize }"/>
							<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }"/>
							<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }"/>
						    </c:if>
						<h3 class="ui-title">manual Encoding-<span id="titleManual">CQSSC</span></h3>
							<ul class="ui-search" >
								<li class="search-time time" id="J-control-area" style ="height:70px">
									<a class="current" href="queryGameManualRecord?lotteryId=99101" 
										data-ballLength="5"
										data-textLimit="1"
										data-formartNum=""
										data-minNumber="0"
										data-maxNumber="9"
										data-separated=""
										data-isPureNum="true"
										value="99101"
										>CQSSC</a>
									<a href="queryGameManualRecord?lotteryId=99104" 
										data-ballLength="5"
										data-textLimit="1"
										data-formartNum=""
										data-minNumber="0"
										data-maxNumber="9"
										data-separated=""
										data-isPureNum="true"
										value="99104"
										>TJSSC</a>
									<a href="queryGameManualRecord?lotteryId=99103"
										data-ballLength="5"
										data-textLimit="1"
										data-formartNum=""
										data-minNumber="0"
										data-maxNumber="9"
										data-separated=""
										data-isPureNum="true"
										value="99103"
										>XJSSC</a>
									<a href="queryGameManualRecord?lotteryId=99105" 
										data-ballLength="5"
										data-textLimit="1"
										data-formartNum=""
										data-minNumber="0"
										data-maxNumber="9"
										data-separated=""
										data-isPureNum="true"
										value="99105"
										>HLJSSC</a>
									<a href="queryGameManualRecord?lotteryId=99102" 
										data-ballLength="5"
										data-textLimit="1"
										data-formartNum=""
										data-minNumber="0"
										data-maxNumber="9"
										data-separated=""
										data-isPureNum="true"
										value="99102"
										>JXSSC</a>
									<a href="queryGameManualRecord?lotteryId=99106" 
										data-ballLength="5"
										data-textLimit="1"
										data-formartNum=""
										data-minNumber="0"
										data-maxNumber="9"
										data-separated=""
										data-isPureNum="true"
										value="99106"
										>LLSSC</a>
									<a href="queryGameManualRecord?lotteryId=99111"
										data-ballLength="5"
										data-textLimit="1"
										data-formartNum=""
										data-minNumber="0"
										data-maxNumber="9"
										data-separated=""
										data-isPureNum="true"
										value="99111"
										>JLFFC</a>
									<a href="queryGameManualRecord?lotteryId=99114"
										data-ballLength="5"
										data-textLimit="1"
										data-formartNum=""
										data-minNumber="0"
										data-maxNumber="9"
										data-separated=""
										data-isPureNum="true"
										value="99114"
										>TXFFC</a>
									<a href="queryGameManualRecord?lotteryId=99304"
										data-ballLength="5"
										data-textLimit="2"
										data-formartNum="true"
										data-isPureNum="true"
										data-separated="/[,\s，]+/g"
										data-minNumber="1"
										data-maxNumber="11"
										value="99304"
										>CQ11-5</a>
									<a href="queryGameManualRecord?lotteryId=99302"
										data-ballLength="5"
										data-textLimit="2"
										data-formartNum="true"
										data-isPureNum="true"
										data-separated="/[,\s，]+/g"
										data-minNumber="1"
										data-maxNumber="11"
										value="99302"
										>JX11-5</a>
									<a href="queryGameManualRecord?lotteryId=99307"
										data-ballLength="5"
										data-textLimit="2"
										data-formartNum="true"
										data-isPureNum="true"
										data-separated="/[,\s，]+/g"
										data-minNumber="1"
										data-maxNumber="11"
										value="99307"
										>JS11-5</a>	
									<a href="queryGameManualRecord?lotteryId=99303"
										data-ballLength="5"
										data-textLimit="2"
										data-formartNum="true"
										data-isPureNum="true"
										data-separated="/[,\s，]+/g"
										data-minNumber="1"
										data-maxNumber="11"
										value="99303"
										>GD11-5</a>
									<a href="queryGameManualRecord?lotteryId=99305"
										data-ballLength="5"
										data-textLimit="2"
										data-formartNum="true"
										data-isPureNum="true"
										data-separated="/[,\s，]+/g"
										data-minNumber="1"
										data-maxNumber="11"
										value="99305"
										>LL11-5</a>
									<a href="queryGameManualRecord?lotteryId=99301"
										data-ballLength="5"
										data-textLimit="2"
										data-formartNum="true"
										data-isPureNum="true"
										data-separated="/[,\s，]+/g"
										data-minNumber="1"
										data-maxNumber="11"
										value="99301"
										>SD11-5</a>
									<a href="queryGameManualRecord?lotteryId=99201" 
										data-ballLength="20"
										data-textLimit="2"
										data-formartNum="true"
										data-isPureNum="true"
										data-separated="/[,\s，]+/g"
										data-minNumber="1"
										data-maxNumber="80"
										value="99201"
										>BJKL8</a>
									<a href="queryGameManualRecord?lotteryId=99107"
										data-ballLength="3"
										data-textLimit="1"
										data-formartNum=""
										data-separated=""
										data-minNumber="0"
										data-maxNumber="9"
										data-isPureNum="true"
										value="99107"
										>SSL</a>
									<a href="queryGameManualRecord?lotteryId=99108" 
										data-ballLength="3"
										data-textLimit="1"
										data-formartNum=""
										data-separated=""
										data-minNumber="0"
										data-maxNumber="9"
										data-isPureNum="true"
										value="99108"
										>3D</a>
									<a href="queryGameManualRecord?lotteryId=99109"
										data-ballLength="5"
										data-textLimit="1"
										data-formartNum=""
										data-separated=""
										data-minNumber="0"
										data-maxNumber="9"
										data-isPureNum="true"
										value="99109"
										>P5</a>
									<a href="queryGameManualRecord?lotteryId=99401"
										data-ballLength="7"
										data-textLimit="2"
										data-formartNum="true"
										data-isPureNum="true"
										data-separated="/[,\s，]+/g"
										data-minNumber="1"
										data-maxNumber="33"
										value="99401"
										>SSQ</a>
									<a class="current" href="queryGameManualRecord?lotteryId=99501" 
										data-ballLength="3"
										data-textLimit="1"
										data-formartNum=""
										data-minNumber="1"
										data-maxNumber="6"
										data-separated=""
										data-isPureNum="true"
										value="99501"
										>JSK3</a>
									<a class="current" href="queryGameManualRecord?lotteryId=99502" 
										data-ballLength="3"
										data-textLimit="1"
										data-formartNum=""
										data-minNumber="1"
										data-maxNumber="6"
										data-separated=""
										data-isPureNum="true"
										value="99502"
										>AHK3</a>
									<a class="current" href="queryGameManualRecord?lotteryId=99601" 
										data-ballLength="3"
										data-textLimit="1"
										data-formartNum=""
										data-minNumber="1"
										data-maxNumber="6"
										data-separated=""
										data-isPureNum="true"
										value="99601"
										>JSDICE</a>
									<a class="current" href="queryGameManualRecord?lotteryId=99701" 
										data-ballLength="7"
										data-textLimit="2"
										data-formartNum="true"
										data-minNumber="1"
										data-maxNumber="49"
										data-separated=""
										data-isPureNum="true"
										value="99701"
										>LHC</a>
									<a class="current" href="queryGameManualRecord?lotteryId=99602" 
										data-ballLength="3"
										data-textLimit="1"
										data-formartNum=""
										data-minNumber="1"
										data-maxNumber="6"
										data-separated=""
										data-isPureNum="true"
										value="99602"
										>JLDICE1</a>
									<a class="current" href="queryGameManualRecord?lotteryId=99603" 
										data-ballLength="3"
										data-textLimit="1"
										data-formartNum=""
										data-minNumber="1"
										data-maxNumber="6"
										data-separated=""
										data-isPureNum="true"
										value="99603"
										>JLDICE2</a>
								</li>
							</ul>
							<h3 class="ui-title">Source Management</h3>
							<ul class="ui-form">
							<li>
							<label class="ui-label"></label>
							<div class="select-position">
							<c:if test="${lastIssue!=null}">
							<input type="hidden" id="lastIssueCode" name="lastIssueCode" value="${lastIssue.issueCode }"/>
							<input type="hidden" id="lastWebIssueCode" name="lastWebIssueCode" value="${lastIssue.webIssueCode }"/>
							<input type="hidden" id="lastOpenDrawTime" name="lastOpenDrawTime" value="<fmt:formatDate value='${lastIssue.openDrawTime}' pattern='yyyy/MM/dd HH:mm:ss'/>"/>
							<input type="hidden" id="lastSaleStartTime" name="lastSaleStartTime" value="<fmt:formatDate value='${lastIssue.saleStartTime}' pattern='yyyy/MM/dd HH:mm:ss'/>"/>
							<input type="hidden" id="lastSaleEndTime" name="lastSaleEndTime" value="<fmt:formatDate value='${lastIssue.saleEndTime}' pattern='yyyy/MM/dd HH:mm:ss'/>"/>	
							<input type="hidden" id="lastTime" name="lastTime" value="${lastTime }"/>
							</c:if>
							
							<c:if test="${lastIssue==null}">
							<input type="hidden" id="lastIssueCode" name="lastIssueCode" value=""/>
							<input type="hidden" id="lastWebIssueCode" name="lastWebIssueCode" value=""/>
							<input type="hidden" id="lastOpenDrawTime" name="lastOpenDrawTime" value=""/>
							<input type="hidden" id="lastSaleStartTime" name="lastSaleStartTime" value=""/>
							<input type="hidden" id="lastSaleEndTime" name="lastSaleEndTime" value=""/>	
							<input type="hidden" id="lastTime" name="lastTime" value="${lastTime }"/>
							</c:if>
							
							
							<c:if test="${lastIssue!=null}">											
								
								<input type="hidden" id="result" name="result" value=""/>
								<input type="hidden" id="issueCode" name="issueCode" value="${lastIssue.issueCode }"/>
									<table class="table table-info text-center">
										<thead>
											<tr>
												<th></th>
												<th>Draw:<span id="J-draw-area">${lastIssue.webIssueCode }</span></th>
												<th>Drawing:<span id="J-drawing-area"><fmt:formatDate value="${lastIssue.saleEndTime}" pattern="yyyy/MM/dd HH:mm:ss"/></span></th>
												<th>Current time:<span id="J-current-time">${lastTime}   </span></th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<span class="game-type-name" id="J-game-name">CQSSC</span>
													<input id="J-game-type" type="hidden" name="game-type" value="CQSSC">
												</td>
												<td colspan="3" class="ball-list">
													<div id="J-input-area" class="" 
														data-ballLength="5"
														data-textLimit="1"
														data-formartNum=""
														data-isPureNum="true"
														data-minNumber="0"
														data-maxNumber="9"
														data-separated=""
													>
														<input type="text" name="ball-num" class="input w-1"><input type="text" name="ball-num" class="input w-1"><input type="text" name="ball-num" class="input w-1"><input type="text" name="ball-num" class="input w-1"><input type="text" name="ball-num" class="input w-1">
														<div>
													</td>
													<td>
													<permission:hasRight moduleName="GAME_ISSUEMGR_MANUAL_FIRST">
														<input type="submit" value="submit" class="btn btn-important" id="J-submit-ball">	
														</permission:hasRight>															
													</td>
												</tr>
											</tbody>
										</table>
									</form>
							</c:if>		
									
									</div>
								</li>
							</ul>
							
				
					
					<h4 class="ui-title"><a href="#" class="history-link">History</a></h4>
					    <div >
						<table class="table table-info text-center" id="J-data-table">
							<thead>
								<tr>
									<th colspan="9">Game History</th>
								</tr>
								<tr>
									<th>Draw</th>
									<th>Result</th>
									<th>Draw Status</th>
									<th>Sales Close</th>
									<th>1st Encoding</th>
									<th>1st Source</th>
									<th>2nd Encoding</th>
									<th>2nd Source</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
							<c:if test="${records!=null}">
							<c:forEach items="${records}" var="record">
								<tr id="${record.issueCode }"  >																	
									<td>${record.webIssueCode }</td>									
									<td>${record.confirmResuld}</td>
									<c:if test="${record.confirmResuld != null}">
									<td>已完成</td>
									</c:if>
									<c:if test="${record.status==0 && record.confirmResuld == null}">
									<td>未锁定</td>
									</c:if>
									<c:if test="${record.status==1}">
									<td>锁定</td>
									</c:if>
									
									<td><fmt:formatDate value="${record.saleEndTime }" pattern="yyyy/MM/dd HH:mm:ss"/></td>
									<td><fmt:formatDate value="${record.firstEncodingTime }" pattern="yyyy/MM/dd HH:mm:ss"/></td>
									<td>${record.firstUserName }</td>
									<td><fmt:formatDate value="${record.sencondEncodingTime }" pattern="yyyy/MM/dd HH:mm:ss"/></td>
									<td>${record.sencondUserName }</td>
									<td>
									<c:if test="${userId != record.sencondUserId && userId != record.firstUserId}">
									<c:if test="${record.status==1}">
									<permission:hasRight moduleName="GAME_ISSUEMGR_MANUAL_UNLOCK">
									<a href="javascript:;" issue="${record.issueCode }" result1="${record.firstResuld }"  result2="${record.sencondResuld }" data-draw="${record.issueCode }" class="btn btn-important unlock-btn" actionType = '1'>UNLOCK</a>
									</permission:hasRight>
									</c:if>
									<c:if test="${record.status==0  && record.issueCode != lastIssue.issueCode && record.confirmResuld == null}">
									<permission:hasRight moduleName="GAME_ISSUEMGR_MANUAL_DELAY_INPUT">
									<a href="javascript:;" issue="${record.issueCode }"  result1="${record.firstResuld }"  result2="${record.sencondResuld }" data-draw="${record.issueCode }" class="btn btn-important unlock-btn" actionType = '2'>delay encode</a></td>
									</permission:hasRight>
									</c:if>
									</c:if>
								</tr>
							</c:forEach>
							</c:if>
							</tbody>
						</table>
                        </div>
                        <c:if test="${page!=null}">
				        <pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
						</c:if>
			        </div>
		        </div>
					</div>
				</div>
			</div>
			
			
						
	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/order/manualRecord.js"></script>
</body>