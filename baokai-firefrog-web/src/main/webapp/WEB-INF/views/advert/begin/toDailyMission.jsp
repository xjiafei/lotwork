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
	<title>日常任务</title>
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
                  <a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a> &gt; 日常任务
               </div>
           </div>
			<div class="col-content">
			<div class="col-main">			
				<div class="main" id="DivRules">
				</div>
				<div  id="DivForm">
						<div align="right">
							<permission:hasRight moduleName="BEGIN_DAILY_MISSION_LOG">
								<button class='btn btn-small'  id='missionLog' style='position:initial'>查询日志</button>
							</permission:hasRight>	
							<permission:hasRight moduleName="BEGIN_DAILY_MISSION_CANCEL">
								<button class='btn btn-small'  id='cancelMission' style='position:initial'>取消修改</button>
							</permission:hasRight>	
							<permission:hasRight moduleName="BEGIN_DAILY_MISSION_CONFIRM">
								<button class='btn btn-small'  id='confirmMission' style='position:initial' >确认修改</button>
							</permission:hasRight>	
						</div>
						<form action="${currentContextPath}/begin/insertDailyMission" id="J-form" method="POST">
						<!-- 累计投注 -->
						<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
							<li class="current">累计投注（天）</li>
                        </ul>
						<c:forEach items="${tolbets}" var="tolbet"  varStatus="status">
						<ul class="ui-form">
							<label for="name" class="ui-label">累计投注天数</label>
							<input type="text" name="beginNewTolbets[${status.index}].tolbetDate" value="${tolbet.tolbetDate}" class="input w-2" numberTag="true"/>
							<label for="name" class="ui-label">&nbsp;&nbsp;&nbsp;</label>
							<input type="hidden" name="beginNewTolbets[${status.index}].isAmount" value="${tolbet.isAmount}"/>
							<input type="checkbox"name="tolbetIsAmountCheckBox" ${tolbet.isAmount=='Y'?'checked':''}/>&nbsp;&nbsp;&nbsp;奖励金额							
							<input type="text" name="beginNewTolbets[${status.index}].amount" value="${tolbet.amount}" class="input w-2" numberTag="true"/>
							<label for="name" class="ui-label">&nbsp;&nbsp;&nbsp;</label>
							<input type="hidden" name="beginNewTolbets[${status.index}].isLottery" value="${tolbet.isLottery}"/>														
							<input type="checkbox"name="tolbetIsLotteryCheckBox" ${tolbet.isLottery=='Y'?'checked':''}/>&nbsp;&nbsp;&nbsp;抽奖机会
							<input type="text" name="beginNewTolbets[${status.index}].lottery" value="${tolbet.lottery}" class="input w-2" numberTag="true"/>次
							<input type="hidden" name="beginNewTolbets[${status.index}].rownum" value="${status.index+1}"/>	
							<select name="beginNewTolbets[${status.index}].lotteryType" class="ui-select w-2" >
								<option value="">&nbsp;</option>
								<option value="0" <c:if test="${tolbet.lotteryType==0}">selected</c:if>>铜</option>
								<option value="1" <c:if test="${tolbet.lotteryType==1}">selected</c:if>>银</option>
								<option value="2" <c:if test="${tolbet.lotteryType==2}">selected</c:if>>金</option>
							</select>													
						</ul>	
						</c:forEach>
						<!-- 每日答题 -->
						<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
								<li class="current">每日答题</li>
                        </ul>
                        
                        <ul class="ui-form">
							<label for="name" class="ui-label">随机下限</label>
							<input type="text" name="beginNewMission.dayAnsLow" value="${beginNewMission.dayAnsLow}" class="input w-2" numberTag="true"/>
							<label for="name" class="ui-label">&nbsp;&nbsp;&nbsp;</label>
							随机上限&nbsp;&nbsp;&nbsp;<input type="text" name="beginNewMission.dayAnsHigh" value="${beginNewMission.dayAnsHigh}" class="input w-2" numberTag="true"/>	
							&nbsp;&nbsp;&nbsp;
							<select name="beginNewMission.dayAnsUnit" class="ui-select w-2">
								<option value="">选择奖励单位</option>
								<option value="1" <c:if test="${beginNewMission.dayAnsUnit==1}">selected</c:if>>元</option>
								<option value="0" <c:if test="${beginNewMission.dayAnsUnit==0}">selected</c:if>>角</option>
							</select>
						</ul>	
                        <c:forEach items="${dayqueses}" var="dayques"  varStatus="status">
						<ul class="ui-form">
							<label for="name" class="ui-label">连续答题天数</label>
							<input type="text" name="beginNewDayqueses[${status.index}].answerDate" value="${dayques.answerDate}" class="input w-2" numberTag="true"/>
							<label for="name" class="ui-label">&nbsp;&nbsp;&nbsp;</label>
							<input type="hidden" name="beginNewDayqueses[${status.index}].isAmount" value="${dayques.isAmount}"/>														
							<input type="checkbox"name="dayquesIsAmountChecoBox" ${dayques.isAmount=='Y'?'checked':''}/>&nbsp;&nbsp;&nbsp;奖励金额							
							<input type="text" name="beginNewDayqueses[${status.index}].amount" value="${dayques.amount}" class="input w-2" numberTag="true"/>
							<label for="name" class="ui-label">&nbsp;&nbsp;&nbsp;</label>
							<input type="hidden" name="beginNewDayqueses[${status.index}].isLottery" value="${dayques.isLottery}"/>														
							<input type="checkbox"name="dayquesIsLotteryChecoBox" ${dayques.isLottery=='Y'?'checked':''}/>&nbsp;&nbsp;&nbsp;抽奖机会
							<input type="text" name="beginNewDayqueses[${status.index}].lottery" value="${dayques.lottery}" class="input w-2" numberTag="true"/>次
							<input type="hidden" name="beginNewDayqueses[${status.index}].rownum" value="${status.index+1}"/>														
							<select name="beginNewDayqueses[${status.index}].lotteryType" class="ui-select w-2" >
								<option value="">&nbsp;</option>
								<option value="0" <c:if test="${dayques.lotteryType==0}">selected</c:if>>铜</option>
								<option value="1" <c:if test="${dayques.lotteryType==1}">selected</c:if>>银</option>
								<option value="2" <c:if test="${dayques.lotteryType==2}">selected</c:if>>金</option>
							</select>
						</ul>	
						</c:forEach>
						
						<!-- 每日投注 -->
						<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
								<li class="current">每日投注</li>
                        </ul>
                        <ul class="ui-form">
							<label for="name" class="ui-label">当日最少投注金额</label>
							<input type="text" name="beginNewMission.dayBetFactor" value="${beginNewMission.dayBetFactor}" class="input w-2" numberTag="true"/>						
						</ul>	
                        
                        <c:forEach items="${daybets}" var="daybet"  varStatus="status">
						<ul class="ui-form">
							<label for="name" class="ui-label">当日投注金额</label>
							<input type="text" name="beginNewDaybets[${status.index}].daybetAmount" value="${daybet.daybetAmount}" class="input w-2" numberTag="true"/>
							<label for="name" class="ui-label">&nbsp;&nbsp;&nbsp;</label>
							<input type="hidden" name="beginNewDaybets[${status.index}].isAmount" value="${daybet.isAmount}"/>														
							<input type="checkbox"name="daybetIsAmountChecoBox" ${daybet.isAmount=='Y'?'checked':''}/>&nbsp;&nbsp;&nbsp;奖励金额							
							<input type="text" name="beginNewDaybets[${status.index}].amount" value="${daybet.amount}" class="input w-2" numberTag="true"/>
							<label for="name" class="ui-label">&nbsp;&nbsp;&nbsp;</label>
							<input type="hidden" name="beginNewDaybets[${status.index}].isLottery" value="${daybet.isLottery}"/>														
							<input type="checkbox"name="daybetIsLotteryChecoBox" ${daybet.isLottery=='Y'?'checked':''}/>&nbsp;&nbsp;&nbsp;抽奖机会
							<input type="text" name="beginNewDaybets[${status.index}].lottery" value="${daybet.lottery}" class="input w-2" numberTag="true"/>次
							<input type="hidden" name="beginNewDaybets[${status.index}].rownum" value="${status.index+1}"/>
							<select name="beginNewDaybets[${status.index}].lotteryType" class="ui-select w-2">
								<option value="">&nbsp;</option>
								<option value="0" <c:if test="${daybet.lotteryType==0}">selected</c:if>>铜</option>
								<option value="1" <c:if test="${daybet.lotteryType==1}">selected</c:if>>银</option>
								<option value="2" <c:if test="${daybet.lotteryType==2}">selected</c:if>>金</option>
							</select>			
						</ul>	
						</c:forEach>
						
						</form>
				</div>
				</div>				
			</div>

	<script type="text/javascript">
	
	function confirmMission(){
		var isDataOk = true;
		
		//累計投注獎勵金額無輸入
		$("input[name=tolbetIsAmountCheckBox]:checked").each(
			function(){
				if($(this).next().val()==0){
					isDataOk = false;
					return false;
				}
			}
		);

		//累計投注抽獎機會無輸入
		$("input[name=tolbetIsLotteryCheckBox]:checked").each(
			function(){
				var ul= $(this).parent();
				var lottery = $(ul).find("[name*=lottery]").val();
				var lotteryType = $(ul).find("[name*=lotteryType]").val();
				
				if(lottery==0 || lottery=='' || lotteryType==''){
					isDataOk = false;
					return false;
				}
			}
		);
			
		//每日答題獎勵金額無輸入
		$("input[name=dayquesIsAmountChecoBox]:checked").each(
				function(){
					if($(this).next().val()==0){
						isDataOk = false;
						return false;
					}
				}
		);
		
		//每日投注獎勵金額無輸入
		$("input[name=dayquesIsLotteryChecoBox]:checked").each(
				function(){
					var ul= $(this).parent();
					var lottery = $(ul).find("[name*=lottery]").val();
					var lotteryType = $(ul).find("[name*=lotteryType]").val();
					
					if(lottery==0 || lottery==''|| lotteryType==''){
						isDataOk = false;
						return false;
					}
				}
		);
		
		//每日投注抽獎機會無輸入
		$("input[name=daybetIsAmountChecoBox]:checked").each(
			function(){
				if($(this).next().val()==0){
					isDataOk = false;
					return false;
				}
			}
		);
		
		//累計投注抽獎機會無輸入
		$("input[name=daybetIsLotteryChecoBox]:checked").each(
				function(){
					var ul= $(this).parent();
					var lottery = $(ul).find("[name*=lottery]").val();
					var lotteryType = $(ul).find("[name*=lotteryType]").val();
					
					if(lottery==0 || lottery==''|| lotteryType==''){
						isDataOk = false;
						return false;
					}
				}
		);
		
		if(!isDataOk){
			alert("填写错误，请确认勾选项内容填写正确");
			return false;
		}
		
		var tolBetFlag = true;
		//判斷累計投注天數問題
		$("input[name*=tolbetDate]").each(function(){
			if($(this).val()>0){
				var isAmount = $(this).parent().find(":checkbox[name=tolbetIsAmountCheckBox]").prop("checked");
				var isLottery = $(this).parent().find(":checkbox[name=tolbetIsLotteryCheckBox]").prop("checked");		
				if(!isLottery && !isAmount){
					tolBetFlag = false;
				}
			}
		});
		if(!tolBetFlag){
			alert("累计投注天數填写错误，请确认内容填写正确");
			return false;
		}
		
		var dayAnsFlag = true;
		//判斷每日答题
		$("input[name*=answerDate]").each(function(){
			if($(this).val()>0){
				var isAmount = $(this).parent().find(":checkbox[name=dayquesIsAmountChecoBox]").prop("checked");
				var isLottery = $(this).parent().find(":checkbox[name=dayquesIsLotteryChecoBox]").prop("checked");		
				if(!isLottery && !isAmount){
					dayAnsFlag = false;
				}
			}
		});
		if(!dayAnsFlag){
			alert("连续答题天数填写错误，请确认内容填写正确");
			return false;
		}
		
		var dayBetFlag = true;
		var dayBetAmountFlag = true;
		//判斷每日投注
		$("input[name*=daybetAmount]").each(function(){
			if($(this).val()>0){

				if($(this).val()>100000){
					dayBetAmountFlag = false;
				}
				var isAmount = $(this).parent().find(":checkbox[name=daybetIsAmountChecoBox]").prop("checked");
				var isLottery = $(this).parent().find(":checkbox[name=daybetIsLotteryChecoBox]").prop("checked");		
				
				var amount = $(this).parent().find("input[name*=amount]").val();
				
				if(!isLottery && !isAmount){
					dayBetFlag = false;
				}
			}
		});
		if(!dayBetFlag){
			alert("每日投注天数填写错误，请确认内容填写正确");
			return false;
		}
		
		if(!dayBetAmountFlag){
			alert("每日投注金额不得超过十万元 ");
			return false;
		}
		
		$("#DivForm").hide();
		TableStyle("DivRules", 19, 1, "保存中");			
		$("#J-form").submit();
	}
	
	function cancelMission(){
		$("#DivForm").hide();
		TableStyle("DivRules", 19, 1, "载入中");			
		location.href='${currentContextPath}/begin/toDailyMission';
		location.assign();
	}
	function missionLog(){
		window.open("${currentContextPath}/begin/toBeginLog?type=1");
	}
	
	function checkMultiBox(){
		var checked= $(this).get(0).checked;
		if(checked){
			$(this).prev().val('Y');
		}else{
			$(this).prev().val('N');
		}
	}
	$(function(){
		$("#confirmMission").click(confirmMission);
		$("#cancelMission").click(cancelMission);		
		$("#missionLog").click(missionLog);		
		$("[name=tolbetIsAmountCheckBox],[name=tolbetIsLotteryCheckBox]").click(checkMultiBox);
		$("[name=dayquesIsAmountChecoBox],[name=dayquesIsLotteryChecoBox]").click(checkMultiBox);
		$("[name=daybetIsAmountChecoBox],[name=daybetIsLotteryChecoBox]").click(checkMultiBox);
		numberTagDefault();
	});
	
	function numberTagDefault(){
		$("[numberTag=true]").each(function(){
			var obj = this;
			$(obj).keyup(function(){
				$(this).val($(this).val().replace(/[^0-9]/g,''));
				if(!jQuery.isNumeric($(this).val())){
					$(this).val(0);
				}
			})
			$(obj).blur(function(){
				$(this).val($(this).val().replace(/[^0-9]/g,''));
				if(!jQuery.isNumeric($(this).val())){
					$(this).val(0);
				}
			})
			if(!jQuery.isNumeric($(this).val())){
				$(this).val(0);
			}
		});
	}
	</script>

</body>