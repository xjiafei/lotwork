<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>平台升级活动</title>
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
				<strong>当前位置：</strong><a href="#">活动</a>>红包明细查询
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>								
								<permission:hasRight moduleName="MARKET_ACTIVITY_HONGBAO">
									<li class="current"><a href="${currentContextPath}/adAdmin/queryActivityHongBaoLog">红包明细查询</a></li>
								</permission:hasRight>
								<permission:hasRight moduleName="MARKET_ACTIVITY_CHONGJIANG">
									<li ><a href="${currentContextPath}/adAdmin/queryActivityChouJianUserLog">抽奖用户查询</a></li>
								</permission:hasRight>
								<permission:hasRight moduleName="MARKET_ACTIVITY_ZHONGJIANG">
                                    <li ><a href="${currentContextPath}/adAdmin/queryActivityChouJianLog">中奖结果查询</a></li>
                                </permission:hasRight>
                                <permission:hasRight moduleName="MARKET_ACTIVITY_CONFIG">
									<li ><a href="${currentContextPath}/adAdmin/queryActivityConfig">中奖概率配置</a></li>
								</permission:hasRight>
								</ul>
							</div>
							<div class="clearfix">
						<form action="queryActivityHongBaoLog" method="post" id="J-search-form">
						<ul class="ui-search" id="J-search-panel">
							<li>
								<label for="" class="ui-label">用户名：</label>
								<input type="text" id="userName" name="userName" class="input w-3" value="${userName}">
							</li>
							
							<li class="time">
								<label for="" class="ui-label">日期：</label>
								<input id="timestart" name="timestart" type="text" value="${timestart}" class="input"> - <input id="timeend" type="text" name="timeend" value="${timeend}" class="input">
							</li>
							<li>
							<input type="hidden" id="statusBK"  name ="statusBK" value="${status}"/>
								<label for="" class="ui-label">状态：</label>
								<select id="status" name="status" class="ui-select">
									<option value="-1">全部</option>
									<option value="1">已领取</option>
									<option value="0">未领取</option>		
								</select>
							</li>
							<li>
							<input type="hidden" id="channelBK"  name ="channelBK" value="${channel}"/>
								<label for="" class="ui-label">渠道：</label>
								<select id="channel" name="channel" class="ui-select">
									<option value="-1">全部</option>
									<option value="0">pc</option>
									<option value="1">手机</option>

								</select>
							</li>
							<li>
								
								<input id="btsubmit" class="btn btn-primary" type="submit" value="搜 索">
								<a id="J-download-submit" class="btn btn-important" href="${fromGame}/gameoa/exportActivityHongBaoLog">导出报表<b class="btn-inner"></b></a>
							</li>
						</ul>
					
						<table class="table table-info" id="J-table">
							<thead>
								<tr>
									<th>日期</th>
									<th>用户名</th>
									<th>当日有效投注金额（元）</th>
									<th>获得红包</th>
									<th>红包状态</th>
									<th>领取渠道</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${struc!=null}">
							<c:forEach items="${struc}" var="struc" varStatus="configIndex">
								<tr>
									<td>${struc.betDate}</td>
									<td>${struc.account}</td>
									<td>${struc.betAmount/10000}</td>
									<td><c:if test="${struc.award==null}">0</c:if> ${struc.award}</td>
									<td><c:if test="${struc.status==1}">已领</c:if><c:if test="${struc.status==0}">未领</c:if></td>
									<td><c:if test="${struc.channel==0}">pc</c:if>
									<c:if test="${struc.channel==1}">手机</c:if>
							
									</td>
								</tr>
                      		</c:forEach>
                      		</c:if>
                                <tr class="">
									<td>总计</td>
									<td></td>
									<td>${bet/10000}</td>
									<td>${red}</td>
									<td></td>
                                    <td></td>
								</tr>
							</tbody>
						</table>
						
						<c:if test="${page!=null}">
				        	<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>				
						</c:if>
					</form>		
						</div></div></div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
    <script type="text/javascript">
	(function($){
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(6)').attr("class","current"); 
	$('.col-side .nav dd:eq(9)').attr("class","current");
	var sIndex=$("#statusBK").val();
	if(sIndex == "-1" || sIndex == "")
	{
	    $("#status").val(-1);
	}else
	{
		$("#status").val(sIndex);
	}
	var inputStart = $('#timestart'),
		inputEnd = $('#timeend');
	inputStart.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
	});
	inputEnd.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
	});
   })(jQuery);
    </script>
</body>