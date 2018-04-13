<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="pg" uri="/tag-page"%>
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
									<li ><a class="current" href="${currentContextPath}/gameoa/queryActivityHongBaoLog">红包明细查询</a></li>
									<li><a href="${currentContextPath}/gameoa/queryActivityChouJianUserLog">抽奖用户查询</a></li>
                                    <li><a href="${currentContextPath}/gameoa/queryActivityChouJianLog">中奖结果查询</a></li>
									<li ><a href="${currentContextPath}/gameoa/queryActivityConfig">中奖概率配置</a></li>
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
								<label for="" class="ui-label">状态：</label>
								<select id="status" name="status" class="ui-select">
									<option value="-1">全部</option>
									<option value="1">pc</option>
									<option value="2">ios</option>
									<option value="3">android</option>
								</select>
							</li>
							<li>
								
								<input id="btsubmit" class="btn btn-primary" type="submit" value="搜 索">
								<a id="J-download-submit" class="btn btn-important" href="${currentContextPath}/gameoa/exportActivityHongBaoLog">导出报表<b class="btn-inner"></b></a>
							</li>
						</ul>
					
						<table class="table table-info" id="J-table">
							<thead>
								<tr>
									<th>日期</th>
									<th>用户名</th>
									<th>当日投注金额（元）</th>
									<th>获得红包</th>
									<th>红包状态</th>
									<th>领取渠道</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${struc!=null}">
							<c:forEach items="${struc}" var="struc" varStatus="configIndex">
								<tr>
									<th>${struc.date}</th>
									<th>${struc.account}</th>
									<th>${struc.betAmount}</th>
									<th>${struc.award}</th>
									<th>${struc.status}</th>
									<th>${struc.channel}</th>
								</tr>
                      		</c:forEach>
                      		</c:if>
                                <tr class="">
									<td>总计</td>
									<td></td>
									<td>${bet}</td>
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
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/order/manualRecord.js"></script>
    <script type="text/javascript">
	(function($){
	var inputStart = $('#timestart'),
		inputEnd = $('#timeend');
	inputStart.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
		d.addEvent('afterSetValue', function(){
			setSelect(0);
			selectTime.val(0);
		});
	});
	inputEnd.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
		d.addEvent('afterSetValue', function(){
			setSelect(0);
			selectTime.val(0);
		});
	});
   })(jQuery);
    </script>
	<script type="text/javascript">
		
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(6)').attr("class","current"); 
	$('.col-side .nav dd:eq(7)').attr("class","current");
	</script>
</body>