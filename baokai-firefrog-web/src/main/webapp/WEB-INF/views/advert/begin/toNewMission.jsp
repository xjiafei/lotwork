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
	<title>新手任务</title>
	<style>
	.table-group .table-toggle a {color:#009B7D; text-decoration:underline;}
	.j-ui-tip {border:1px solid #CCC;padding:10px;}
	.j-ui-tip .sj {display:none;}
	.row-show {position:relative;}
	.row-show-pass {position:relative;}
	.row-show-panel {display:none;position:absolute;background:#FFFFE1;border:1px solid #CCC;padding:10px;}
	.row-show-panel-current {display:block;white-space:normal;}
	.table a {text-decoration:underline;}
	</style>
</head>
<body>
		<div class="col-crumbs">
               <div class="crumbs">
                   <strong>当前位置：</strong>
                  <a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a> &gt; 新手任务
               </div>
           </div>
			<div class="col-content">
			<div class="col-main">
				<div class="main" id="DivRules">
				</div>
				<div  id="DivForm">
						<div align="right">
							<permission:hasRight moduleName="BEGIN_NEW_MISSION_LOG">
								<button class='btn btn-small'  id='missionLog' style='position:initial'>查询日志</button>
							</permission:hasRight>
							<permission:hasRight moduleName="BEGIN_NEW_MISSION_CANCEL">
								<button class='btn btn-small'  id='cancelMission' style='position:initial'>取消修改</button>
							</permission:hasRight>
							<permission:hasRight moduleName="BEGIN_NEW_MISSION_CONFIRM">
								<button class='btn btn-small'  id='confirmMission' style='position:initial' >确认修改</button>
							</permission:hasRight>
						</div>
						<form action="${currentContextPath}/begin/insertNewMission" id="J-form" method="POST">
						<!-- 綁卡 -->
						<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
							<li class="current">绑卡奖励</li>
                        </ul>
						<ul class="ui-form">
							<li>
								<label for="name" class="ui-label">绑卡奖励文案</label>
								<input type="text" name="mission.bindCardText" class="input w-10"  maxlength="100" value="${mission.bindCardText}">
							</li>
							<li>
								<label for="name" class="ui-label">定义奖励金额</label>
								<input type="text" name="mission.bindCardPremium" class="input w-2"  maxlength="100" numberTag="true" value="${mission.bindCardPremium}">&nbsp;&nbsp;&nbsp;								
								<input type="radio" name="mission.bindCardCheck" value="Y" ${mission.bindCardCheck=='Y'?'checked':''}/>人工审核
								<input type="radio" name="mission.bindCardCheck" value="N" ${mission.bindCardCheck=='N'?'checked':''}/>不审核
							</li>									
						</ul>
						<!-- 首充 -->
						<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
								<li class="current">首充奖励</li>
                        </ul>
						<ul class="ui-form">
							<li>
								<label for="name" class="ui-label">首充奖励文案</label>
								<input type="text" name="mission.chargeText" class="input w-10"  maxlength="100" value="${mission.chargeText}"/>								
							</li>		
						</ul>
						<div id="chargeArea">
						<label for="name" class="ui-label"></label>
						<label for="name" class="ui-label"></label>
						<label for="name" class="ui-label"></label>
						<label for="name" class="ui-label"></label>
						<label for="name" class="ui-label">金额&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;百分比</label>
						<c:forEach items="${charges}" var="charge"  varStatus="status">
						<ul class="ui-form">
							<label for="name" class="ui-label">充值金额</label>
							<input type="text" name="charges[${status.index}].chargeAmt" value="${charge.chargeAmt}" class="input w-2" numberTag="true"/>
							<label for="name" class="ui-label">流水倍数</label>
							<input type="text" name="charges[${status.index}].chargeFactor" value="${charge.chargeFactor}" class="input w-1" numberTag="true"/>										
							<label for="name" class="ui-label">返奖</label>
							<input type="text" name="charges[${status.index}].chargePreium" value="${charge.chargePreium}" class="input w-1" numberTag="true"/>																				
							<input type="text" name="charges[${status.index}].chargePer" value="${charge.chargePer}" class="input w-1" numberTag="true"/>%		
							<label for="name" class="ui-label">投注期限</label>
							<input type="text" name="charges[${status.index}].bettingDate" value="${charge.bettingDate}" class="input w-1" numberTag="true"/>日
							<label for="name" class="ui-label">
							<input type="hidden" name="charges[${status.index}].multipleGet" value="${charge.multipleGet}"/>
							<input type="checkbox" name="chargesChecoBox" ${charge.multipleGet=='Y'?'checked':''}/>并列领取</label>
							<input type="hidden" name="charges[${status.index}].rownum" value="${status.index+1}"/>							
						</ul>	
						</c:forEach>
						</div>
						<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
								<li class="current">首次提款奖励</li>
                        </ul>
						<ul class="ui-form">
							<li>
								<label for="name" class="ui-label">首次提款文案</label>
								<input type="text" name="mission.withdrawText" class="input w-10"  maxlength="100" value="${mission.withdrawText}" />								
							</li>		
						</ul>
						<ul class="ui-form">
							<li>
								<label for="name" class="ui-label">定义提款金额</label>
								<input type="text" name="mission.withdrawFactor" class="input w-2"   numberTag="true" value="${mission.withdrawFactor}" />
								<label for="name" class="ui-label">定义奖励金额</label>
								<input type="text" name="mission.withdrawPremium" class="input w-2"   numberTag="true" value="${mission.withdrawPremium}" />								
							</li>		
						</ul>
						</form>
				</div>
				</div>
			</div>

	<script type="text/javascript">
	
	function confirmMission(){
		if(chargeCheck()){
			$("#DivForm").hide();
			TableStyle("DivRules", 19, 1, "保存中");			
			$("#J-form").submit();			
		}
	}

	function chargeCheck(){
		var isOk = true;
		var isFactorOk=true;
		var isPriceOk=true;
		$("#chargeArea").children().each(function(){
			var chargeAmt = $(this).find("[name*=chargeAmt]").val();
			if(chargeAmt>0){
				var chargeFactor = $(this).find("[name*=chargeFactor]").val();
				var bettingDate = $(this).find("[name*=bettingDate]").val();	
				
				var chargePreium = $(this).find("[name*=chargePreium]").val();
				var chargePer = $(this).find("[name*=chargePer]").val();	
				
				if((chargeFactor>0 && bettingDate==0) || (chargeFactor ==0 && bettingDate >0)){
					isFactorOk = false;
				}
				if((chargePreium==0 && chargePer==0)){
					isPriceOk = false;
				}
			}
		});
		if(!isFactorOk){
			alert('流水倍数与投注期限输入错误');		
			isOk = false;
		}

		if(!isPriceOk){
			alert('返奖限输入错误');			
			isOk = false;
		}
		return isOk;
	}
	function cancelMission(){
		$("#DivForm").hide();
		TableStyle("DivRules", 19, 1, "载入中");			
		location.href='${currentContextPath}/begin/toNewMission';
		location.assign();
	}
	function missionLog(){
		window.open("${currentContextPath}/begin/toBeginLog?type=0");
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
		$("[name=chargesChecoBox]").click(checkMultiBox);
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