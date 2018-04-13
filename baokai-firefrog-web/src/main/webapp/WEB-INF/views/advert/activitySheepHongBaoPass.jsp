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
										<span class="ico-tab ico-tab-item "><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao">全部</a></span>
										<span class="ico-tab ico-tab-item "><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao?pageStatus=1">待审核(${unCheckNum})</a></span>
										<span class="ico-tab ico-tab-item ico-tab-current"><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao?pageStatus=2">已通过</a></span>
										<span class="ico-tab ico-tab-item "><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao?pageStatus=3">未通过</a></span>
									</li>
								</ul>
							
							
						<form action="queryActivitySheepHongBao?pageStatus=2" method="post" id="J-search-form">
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
                            <input type="hidden" id="channelBK"  name ="channelBK" value="${status}"/>	
								<label for="" class="ui-label">来源：</label>
								<select id="channel" style="width:90px;" name="status" class="ui-select">
									<option value="-1">全部</option>
												<option value="1">ios2.1</option>
									<option value="2">android2.1</option>
									<option value="3">web3.0</option>
									<option value="4">web4.0</option>	
								</select>
							</li>
							
							
							<li>
								<input id="btsubmit" class="btn btn-primary" type="submit" value="搜 索">
								<a id="J-download-submit" class="btn btn-important" href="${fromGame}/gameoa/exportActivitySheepHongBao?pageStatus=2">导出报表<b class="btn-inner"></b></a>
								
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
											<th>达标时间</th>
											<th>领取时间</th>
											<th>审核人</th>
											<th>审核时间</th>

								</tr>
							</thead>
							<tbody>
						
								<c:if test="${struc!=null}">
								<c:forEach items="${struc}" var="struc" varStatus="configIndex">
								<tr>	
								
									<td><fmt:formatDate value="${struc.signTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
									<td>${struc.userName}<c:if test="${struc.vip==1}"><img src="http://static.rfonlineedu.com:888/static/images/ucenter/safe/vip.jpg"></c:if></td>
									<td>${struc.award/10000}</td>
									<td><c:if test="${struc.awardType==1}">第一个红包</c:if>
									<c:if test="${struc.awardType==2}">第二个红包</c:if>
									<c:if test="${struc.awardType==3}">第三个红包</c:if></td>
									<td>${struc.targetAward/10000}</td>
									<td><fmt:formatNumber value="${struc.allAward/10000}" pattern="#.##" minFractionDigits="2" /> </td>
									<td><c:if test="${struc.channel==1 }">ios2.1</c:if>
												<c:if test="${struc.channel==2 }">android2.1</c:if>
												<c:if test="${struc.channel==3 }">web3.0</c:if>
												<c:if test="${struc.channel==4 }">web4.0</c:if>
									</td>
									<td><fmt:formatDate value="${struc.reachTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
									<td><fmt:formatDate value="${struc.getTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
									<td>${struc.verifyName}</td>
									<td><fmt:formatDate value="${struc.verifyTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
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
    <script type="text/javascript">
	(function($){
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(6)').attr("class","current"); 
	$('.col-side .nav dd:eq(14)').attr("class","current");
	
	var sIndex=$("#statusBK").val();
	var sIndexConfig=$("#configBK").val();
	if(sIndex == "-1" || sIndex == "")
	{
	    $("#status").val(-1);
	}else
	{
		$("#status").val(sIndex);
	}
	if(sIndexConfig == "-1" || sIndexConfig == "")
	{
		$("#config").val(-1);
	}else
	{
		$("#config").val(sIndexConfig);
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
    
	<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
</body>