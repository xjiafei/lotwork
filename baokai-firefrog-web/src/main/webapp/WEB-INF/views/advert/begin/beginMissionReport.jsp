<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>数据报表</title>
	<style>
		.panel-field-urgent {display:none;}
		.ui-form .J-panel-group {margin:0;}
		.checkbox-list {border-bottom:1px dotted #CCC;display:inline-block;padding-bottom:10px;}
		.checkbox-list-last {border:0;}
	</style>
</head>
<body>
		<div class="col-crumbs">
               <div class="crumbs">
                   <strong>当前位置：</strong>
                  <a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a> &gt; 数据报表
               </div>
           </div>
			<div class="col-content">
			<div class="col-main">
				<div class="main" id="DivRules">
				</div>
				<div id="DivForm">
				<form action="${currentContextPath}/begin/getMissionReport" id="J-form" method="GET">
				<ul class="ui-search">
					<li class="time">
						<input id="startTime" name="startTime" type="text" value="${startTime}" class="input"> - <input id="endTime" type="text" name="endTime" value="${endTime}" class="input">
					</li>
					<li>
					<permission:hasRight moduleName="BEGIN_DATA_REPORT_QUERY">
						<a id="J-button-search" href="javascript:void(0);" class="btn btn-important">查询<b class="btn-inner"></b></a>					
					</permission:hasRight>
					<permission:hasRight moduleName="BEGIN_DATA_REPORT_EXCEL">
						<a id="exportReport" href="javascript:void(0);" class="btn btn-important">导出数据到EXCEL<b class="btn-inner"></b></a>
					</permission:hasRight>
					</li>
				</ul>
				</form>
				<ul class="ui-form">
					<li>
						<label for="name" class="ui-label">同期用户参与人数：</label>
						${vo.activityMissionCount}
						<label for="name" class="ui-label">同期完成绑卡数：</label>
						${vo.bindcardCount}
						<label for="name" class="ui-label">同期绑卡比例：</label>
						${vo.bindcardPercent}%
					</li>		
				</ul>
				<ul class="ui-form">
					<li>
						<label for="name" class="ui-label">同期完成首充数：</label>
						${vo.chargeCount}
						<label for="name" class="ui-label">同期首充比例：</label>
						${vo.chargePercent}%
						<label for="name" class="ui-label">同期完成首提数：</label>
						${vo.withDrawCount}
						<label for="name" class="ui-label">同期首提比例：</label>
						${vo.withdrawPercent}%
					</li>																			
				</ul>
				<ul class="ui-form">
					<li>
						<label for="name" class="ui-label">同期人均首存金额：</label>
						${vo.chargeAmtPercen}
						<label for="name" class="ui-label">同期人均投注金额：</label>
						${vo.orderAmtPercen}
						<label for="name" class="ui-label">同期抽奖人数：</label>
						${vo.lotteryPersion}
						<label for="name" class="ui-label">同期派奖额：</label>
						${vo.totalLotteryAmt}
						<label for="name" class="ui-label">同期派奖次数：</label>
						${vo.totalLotteryTime}
					</li>		
				</ul>
			</div>
			</div>
</div>
	<script type="text/javascript">
	
	
	
	$(function(){
		//datePicker setting
		var inputStart = $('#startTime'),inputEnd = $('#endTime');
		var now = new Date();
		now.setHours(0)
		now.setSeconds(0);
		now.setMinutes(0);
		var bDate= now.format('yyyy-MM-dd');
		now.setHours(23)
		now.setSeconds(59);
		now.setMinutes(59);
		var eDate= now.format('yyyy-MM-dd');
		
		if(!$('#startTime').val()){
			inputStart.val(bDate);
		}
		
		if(!$('#endTime').val()){
			inputEnd.val(eDate);
		}
		
		inputStart.focus(function(){
			var d = new phoenix.DatePicker({input:this,isShowTime:false});
			d.show();
		});
		
		inputEnd.focus(function(){
			var d = new phoenix.DatePicker({input:this,isShowTime:false});
			d.show();
		});
		
		$("#J-button-search").click(function(){
			$("#J-form").get(0).submit();
		});
		
		//导出名单
		$("#exportReport").click(function(){
			var startTime = $('#startTime').val();
			var endTime = $('#endTime').val(); 
			window.open("${currentContextPath}/begin/downloadMissionReport?"+"startTime="+startTime+"&endTime="+endTime);
		});
		
	});
	
	</script>
	
</body>